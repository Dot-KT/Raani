package Raani.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private static final String ADMIN_EMAIL = "7devanle@gmail.com";

    public void sendOtp(String otp, String adminUsername) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(ADMIN_EMAIL);
        message.setTo(ADMIN_EMAIL);
        message.setSubject("Raani Admin Registration OTP");
        message.setText("A new admin \"" + adminUsername + "\" is trying to register.\n\n"
                + "OTP: " + otp + "\n\n"
                + "This OTP expires in 1 hour.\n"
                + "If you did not expect this, ignore this email.");

        mailSender.send(message);
    }
}
