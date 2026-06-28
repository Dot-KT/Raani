# Moniepoint (Monnify) Payment Integration

## Overview

Moniepoint's developer product is called **Monnify**. It provides APIs for generating temporary/reserved bank accounts that customers can transfer to, with webhook notifications on payment completion.

---

## Sign Up & Credentials

Register at [Monnify Dashboard](https://app.monnify.com) to obtain:

| Credential | Description |
|------------|-------------|
| **API Key** | Public key for authentication |
| **Secret Key** | Private key for authentication |
| **Contract Code** | Merchant identifier tied to your account (labeled **"Business Code"** on the dashboard) |

---

## Environments

| Environment | Base URL |
|-------------|----------|
| Sandbox (testing) | `https://sandbox.monnify.com` |
| Production (live) | `https://api.monnify.com` |

---

## Payment Flow

```
Customer places order
        |
        v
Backend calls Monnify to reserve a temporary account
        |
        v
Customer receives account number + amount to transfer
        |
        v
Customer transfers via bank app
        |
        v
Monnify sends webhook to backend confirming payment
        |
        v
Backend marks order as PAID
```

---

## API Endpoints

### 1. Authentication — Get Access Token

```
POST {{base_url}}/api/v1/auth/login
```

**Headers:**
```
Authorization: Basic base64(apiKey:secretKey)
Content-Type: application/json
```

**Response:**
```json
{
  "requestSuccessful": true,
  "responseMessage": "success",
  "responseBody": {
    "accessToken": "eyJhbGciOiJSUzI1NiJ9...",
    "expiresIn": 3600
  }
}
```

Use the returned `accessToken` as `Authorization: Bearer <token>` on all subsequent requests.

---

### 2. Reserve Account (Generate Temp Account for Customer)

```
POST {{base_url}}/api/v2/bank-transfer/reserved-accounts
```

**Headers:**
```
Authorization: Bearer <accessToken>
Content-Type: application/json
```

**Request Body:**
```json
{
  "accountReference": "order_665f1a2b",
  "accountName": "Raani - Amina Bello",
  "currencyCode": "NGN",
  "contractCode": "<your_contract_code>",
  "customerEmail": "customer@email.com",
  "customerName": "Amina Bello",
  "bvn": "21212121212",
  "getAllAvailableBanks": false
}
```

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `accountReference` | String | Yes | Unique reference (e.g. order ID) |
| `accountName` | String | Yes | Name displayed on the temp account |
| `currencyCode` | String | Yes | Always `"NGN"` |
| `contractCode` | String | Yes | Your Monnify contract code |
| `customerEmail` | String | Yes | Customer's email |
| `customerName` | String | Yes | Customer's full name |
| `bvn` | String | Yes | Customer's BVN or NIN |
| `getAllAvailableBanks` | Boolean | No | `true` to get accounts across all partner banks |

**Response:**
```json
{
  "requestSuccessful": true,
  "responseMessage": "success",
  "responseBody": {
    "contractCode": "8389328412",
    "accountReference": "order_665f1a2b",
    "accountName": "Raani - Amina Bello",
    "currencyCode": "NGN",
    "customerEmail": "customer@email.com",
    "accountNumber": "1234567890",
    "bankName": "Moniepoint MFB",
    "bankCode": "035",
    "status": "ACTIVE"
  }
}
```

---

### 3. Webhook — Payment Completion Notification

Monnify sends a `POST` request to your configured webhook URL when payment is received.

**Configure webhook URL** in your Monnify Dashboard under: `Developers > Webhook URLs > Transaction Completion`

**Webhook Payload (from Monnify to your server):**
```json
{
  "eventType": "SUCCESSFUL_TRANSACTION",
  "eventData": {
    "transactionReference": "MNFY|20250615|123456",
    "paymentReference": "order_665f1a2b",
    "amountPaid": "45000.00",
    "paidOn": "2025-06-15 10:30:00",
    "paymentStatus": "PAID",
    "paymentDescription": "Payment for order",
    "currency": "NGN",
    "customer": {
      "email": "customer@email.com",
      "name": "Amina Bello"
    }
  }
}
```

**Security — Validate the webhook:**
- Every webhook includes a **signature header**
- Hash the payload using your **Secret Key** and verify it matches the signature
- Whitelist Monnify's IP addresses
- Check for duplicate notifications
- Respond with **HTTP 200** immediately, process logic after

---

## Merchant Details

| Field | Value |
|-------|-------|
| Account Name | RAA RAW FOODS |
| Account Number | 6697046687 |
| Bank | Moniepoint MFB |

---

## Implementation Plan (Backend)

### New files to create:

| File | Purpose |
|------|---------|
| `model/Payment.java` | Payment entity (reference, amount, status, delivery link) |
| `model/PaymentStatus.java` | Enum: PENDING, PAID, FAILED, EXPIRED |
| `repository/PaymentRepository.java` | MongoDB repository for payments |
| `service/MonnifyService.java` | Auth, reserve account, verify transaction |
| `service/PaymentService.java` | Payment CRUD + webhook processing |
| `controller/PaymentWebhookController.java` | Receives Monnify webhook notifications |
| `controller/PaymentController.java` | Admin endpoints for viewing payments |
| `config/MonnifyConfig.java` | API key, secret, contract code, base URL |

### Configuration needed in `application.yaml`:

```yaml
monnify:
  api-key: <your_api_key>
  secret-key: <your_secret_key>
  contract-code: <your_contract_code>
  base-url: https://sandbox.monnify.com  # switch to https://api.monnify.com for production
```

---

## References

- [Monnify Reserved Account Docs](https://developers.monnify.com/docs/collections/customer-reserved-account)
- [Monnify API Reference](https://developers.monnify.com/api)
- [Monnify Authentication](https://teamapt.atlassian.net/wiki/spaces/MON/pages/212008633/Authentication)
- [Monnify Webhook Events](https://developers.monnify.com/docs/webhooks/event-types)
- [Monnify Webhook Setup](https://developers.monnify.com/docs/integration-tools/webhooks/)
