package Raani.service;

import Raani.dto.AuthResponse;
import Raani.dto.LoginRequest;
import Raani.dto.RegisterRequest;
import Raani.dto.VerifyOtpRequest;
import Raani.model.Admin;
import Raani.repository.AdminRepository;
import Raani.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public String register(RegisterRequest request) {
        if (adminRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already taken");
        }

        String otp = generateOtp();

        Admin admin = new Admin();
        admin.setUsername(request.username());
        admin.setPassword(passwordEncoder.encode(request.password()));
        admin.setVerified(false);
        admin.setOtp(otp);
        admin.setOtpExpiresAt(LocalDateTime.now().plusHours(1));
        admin.setCreatedAt(LocalDateTime.now());

        adminRepository.save(admin);
        emailService.sendOtp(otp, request.username());

        return "Registration started. An OTP has been sent for verification.";
    }

    public String verifyOtp(VerifyOtpRequest request) {
        Admin admin = adminRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));

        if (admin.isVerified()) {
            throw new IllegalArgumentException("Admin is already verified");
        }

        if (admin.getOtpExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP has expired. Please register again.");
        }

        if (!admin.getOtp().equals(request.otp())) {
            throw new IllegalArgumentException("Invalid OTP");
        }

        admin.setVerified(true);
        admin.setOtp(null);
        admin.setOtpExpiresAt(null);
        adminRepository.save(admin);

        return "Account verified successfully. You can now log in.";
    }

    public AuthResponse login(LoginRequest request) {
        Admin admin = adminRepository.findByUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!admin.isVerified()) {
            throw new IllegalArgumentException("Account not verified. Please verify your OTP first.");
        }

        if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(admin.getUsername());
        return new AuthResponse(token, admin.getUsername());
    }

    public String resendOtp(String username) {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));

        if (admin.isVerified()) {
            throw new IllegalArgumentException("Admin is already verified");
        }

        String otp = generateOtp();
        admin.setOtp(otp);
        admin.setOtpExpiresAt(LocalDateTime.now().plusHours(1));
        adminRepository.save(admin);

        emailService.sendOtp(otp, username);

        return "A new OTP has been sent.";
    }

    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
