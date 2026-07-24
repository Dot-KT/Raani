package Raani.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/whatsapp/webhook")
public class WhatsAppWebhookController {

    @Value("${whatsapp.verify-token}")
    private String verifyToken;

    /**
     * Webhook verification — Meta sends a GET request with a challenge
     * when you first register the webhook URL.
     */
    @GetMapping
    public ResponseEntity<String> verify(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String token,
            @RequestParam("hub.challenge") String challenge) {

        if ("subscribe".equals(mode) && verifyToken.equals(token)) {
            log.info("Webhook verified successfully");
            return ResponseEntity.ok(challenge);
        }

        log.warn("Webhook verification failed — invalid token");
        return ResponseEntity.status(403).body("Forbidden");
    }

    /**
     * Incoming webhooks — Meta sends POST requests here with message
     * notifications, status updates, etc.
     */
    @PostMapping
    public ResponseEntity<String> receive(@RequestBody JsonNode payload) {
        log.info("Webhook received: {}", payload.toString());

        try {
            JsonNode entries = payload.get("entry");
            if (entries != null && entries.isArray()) {
                for (JsonNode entry : entries) {
                    JsonNode changes = entry.get("changes");
                    if (changes != null && changes.isArray()) {
                        for (JsonNode change : changes) {
                            JsonNode value = change.get("value");
                            if (value != null && value.has("messages")) {
                                JsonNode messages = value.get("messages");
                                for (JsonNode message : messages) {
                                    String from = message.get("from").asText();
                                    String type = message.get("type").asText();
                                    String body = "text".equals(type)
                                            ? message.get("text").get("body").asText()
                                            : "[" + type + "]";
                                    log.info("Message from {}: {}", from, body);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error processing webhook", e);
        }

        // Always return 200 so Meta doesn't retry
        return ResponseEntity.ok("EVENT_RECEIVED");
    }
}
