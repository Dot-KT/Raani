# Raani - WhatsApp Commerce Bot

## Overview

Raani is a WhatsApp-based commerce bot that allows customers to browse items, place orders, and track deliveries. It also sends monthly purchase reminders. The backend is built with Spring Boot and uses MongoDB for persistence.

---

## System Architecture

```
+------------------+        +---------------------+        +------------------+
|                  |        |                     |        |                  |
|  WhatsApp Users  | <----> |  WhatsApp Business  | <----> |  Spring Boot     |
|  (Customers)     |        |  API (Cloud API)    |        |  Backend         |
|                  |        |                     |        |                  |
+------------------+        +---------------------+        +--------+---------+
                                                                    |
                                                           +--------+---------+
                                                           |                  |
                                                           |  MongoDB         |
                                                           |  (Local)         |
                                                           |                  |
                                                           +------------------+
```

---

## Core Components

### 1. WhatsApp Webhook Controller

- Receives incoming messages from WhatsApp Cloud API via webhook
- Sends outbound messages (item catalog, confirmations, reminders)
- Handles webhook verification (GET) and message ingestion (POST)

### 2. Conversation Flow Engine

Manages the stateful conversation with each customer:

```
New Customer Message
        |
        v
  Is Registered? --NO--> Collect Name + Address --> Save Customer
        |
       YES
        |
        v
  Show Item Catalog
        |
        v
  Customer Selects Item
        |
        v
  Confirm Quantity
        |
        v
  Create Delivery Record --> Send Order Confirmation
```

**Conversation states:**
- `NEW` - First contact, collecting customer details
- `AWAITING_NAME` - Waiting for customer name
- `AWAITING_ADDRESS` - Waiting for customer address
- `BROWSING` - Viewing item catalog
- `SELECTING_QUANTITY` - Choosing quantity for a selected item
- `CONFIRMING_ORDER` - Confirming the order before placement

### 3. Scheduled Reminder Service

- Runs on the **15th** (mid-month) and **last day** of each month
- Queries all customers with deliveries in the current month
- Sends a WhatsApp message summarizing their purchases (items, quantities, total spent)

---

## Data Models

### Customer

```
{
  _id: ObjectId,
  name: String,
  phoneNumber: String,       // WhatsApp number, unique identifier
  address: String,
  registeredAt: DateTime,
  conversationState: String   // tracks where they are in the chat flow
}
```

### Item (Product Catalog)

```
{
  _id: ObjectId,
  name: String,
  description: String,
  price: Double,
  available: Boolean
}
```

### Delivery (Order/Purchase)

```
{
  _id: ObjectId,
  customerId: ObjectId,       // ref -> Customer
  item: {
    itemId: ObjectId,         // ref -> Item
    name: String,             // denormalized for quick access
    price: Double
  },
  quantity: Integer,
  totalPrice: Double,         // price * quantity
  status: String,             // PLACED | PROCESSING | IN_TRANSIT | DELIVERED | CANCELLED
  currentLocation: String,    // manually updated by admin
  orderedAt: DateTime,
  updatedAt: DateTime
}
```

---

## Package Structure

```
com.raani
├── config/
│   └── MongoConfig.java
│   └── WhatsAppConfig.java
├── controller/
│   └── WebhookController.java          // WhatsApp webhook endpoint
├── model/
│   ├── Customer.java
│   ├── Item.java
│   └── Delivery.java
├── repository/
│   ├── CustomerRepository.java
│   ├── ItemRepository.java
│   └── DeliveryRepository.java
├── service/
│   ├── WhatsAppService.java            // send/receive WhatsApp messages
│   ├── ConversationService.java        // manage chat flow & state
│   ├── CustomerService.java            // customer CRUD
│   ├── ItemService.java                // item catalog operations
│   ├── DeliveryService.java            // order/delivery CRUD + status updates
│   └── ReminderService.java            // scheduled monthly reminders
├── dto/
│   ├── WebhookPayload.java             // incoming webhook structure
│   └── WhatsAppMessage.java            // outbound message structure
└── scheduler/
    └── MonthlyReminderScheduler.java   // cron-triggered reminder job
```

---

## API Endpoints

### Webhook (WhatsApp)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/webhook` | Webhook verification (token challenge) |
| POST | `/webhook` | Receive incoming WhatsApp messages |

### Admin (for future admin UI)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/customers` | List all customers |
| GET | `/api/deliveries` | List all deliveries |
| PUT | `/api/deliveries/{id}/status` | Update delivery status |
| PUT | `/api/deliveries/{id}/location` | Update delivery location |
| GET | `/api/items` | List all items |
| POST | `/api/items` | Add new item |
| PUT | `/api/items/{id}` | Update item |
| DELETE | `/api/items/{id}` | Remove item |

---

## Scheduler Configuration

```
# Mid-month reminder: 15th of every month at 9:00 AM
0 0 9 15 * ?

# End-of-month reminder: last day of every month at 9:00 AM
0 0 9 L * ?
```

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| Framework | Spring Boot 3.x |
| Database | MongoDB (local) |
| WhatsApp Integration | WhatsApp Business Cloud API |
| HTTP Client | Spring WebClient / RestTemplate |
| Scheduling | Spring `@Scheduled` |
| Build Tool | Maven |

---

## Key Design Decisions

1. **Conversation state stored in Customer document** - Keeps the flow simple; no need for a separate session store. State resets after order placement.
2. **Item details denormalized in Delivery** - Avoids joins when generating reminders or order summaries. Price at time of purchase is preserved even if catalog price changes.
3. **Admin endpoints exposed separately** - Clean separation between the WhatsApp-facing webhook and admin operations. Admin UI will consume these REST endpoints.
4. **MongoDB chosen for flexibility** - Schema-less nature suits the evolving catalog and delivery tracking fields. Easy local setup.
