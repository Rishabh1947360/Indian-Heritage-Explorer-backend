package com.heritage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromAddress;

    @Async
    public void sendOtpEmail(String toEmail, String name, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromAddress);
            helper.setTo(toEmail);
            helper.setSubject("🕌 Your OTP for Indian Heritage Explorer");
            helper.setText(buildOtpHtml(name, otp, toEmail), true);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send OTP email: " + e.getMessage());
        }
    }

    @Async
    public void sendWelcomeEmail(String toEmail, String name) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromAddress);
            helper.setTo(toEmail);
            helper.setSubject("🎉 Welcome to Indian Heritage Explorer!");
            helper.setText(buildWelcomeHtml(name), true);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send welcome email: " + e.getMessage());
        }
    }

    @Async
    public void sendBookingConfirmationEmail(String toEmail, String name,
                                              String siteName, String location,
                                              String date, int visitors, double amount) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromAddress);
            helper.setTo(toEmail);
            helper.setSubject("✅ Booking Confirmed — Indian Heritage Explorer");
            helper.setText(buildBookingHtml(name, siteName, location, date, visitors, amount), true);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send booking email: " + e.getMessage());
        }
    }

    private String buildOtpHtml(String name, String otp, String email) {
        return """
            <div style="font-family:Arial,sans-serif;max-width:500px;margin:0 auto;">
              <div style="background:linear-gradient(135deg,#FF9933,#138808);padding:30px;text-align:center;border-radius:10px 10px 0 0;">
                <h1 style="color:white;margin:0;">🇮🇳 Indian Heritage Explorer</h1>
              </div>
              <div style="padding:30px;background:#FFF8DC;border-radius:0 0 10px 10px;">
                <h2>Hello%s! 🙏</h2>
                <p>Use the OTP below to complete your login:</p>
                <div style="background:white;border:2px solid #FF9933;border-radius:12px;padding:20px;text-align:center;margin:20px 0;">
                  <h1 style="color:#FF9933;font-size:42px;letter-spacing:10px;margin:0;">%s</h1>
                </div>
                <p style="color:#888;font-size:14px;">⏳ This OTP expires in <strong>10 minutes</strong>.</p>
              </div>
            </div>
            """.formatted(name != null ? ", " + name : "", otp);
    }

    private String buildWelcomeHtml(String name) {
        return """
            <div style="font-family:Arial,sans-serif;max-width:500px;margin:0 auto;">
              <div style="background:linear-gradient(135deg,#FF9933,#138808);padding:30px;text-align:center;border-radius:10px 10px 0 0;">
                <h1 style="color:white;margin:0;">🇮🇳 Welcome!</h1>
              </div>
              <div style="padding:30px;background:#FFF8DC;border-radius:0 0 10px 10px;">
                <h2>Namaste, %s! 🙏</h2>
                <p>Your account has been created on <strong>Indian Heritage Explorer</strong>.</p>
                <ul>
                  <li>🏛️ Browse India's heritage sites</li>
                  <li>📅 Book guided tours</li>
                  <li>🎨 Connect with local artisans</li>
                </ul>
              </div>
            </div>
            """.formatted(name);
    }

    private String buildBookingHtml(String name, String siteName, String location,
                                     String date, int visitors, double amount) {
        return """
            <div style="font-family:Arial,sans-serif;max-width:500px;margin:0 auto;">
              <div style="background:linear-gradient(135deg,#FF9933,#138808);padding:30px;text-align:center;border-radius:10px 10px 0 0;">
                <h1 style="color:white;margin:0;">Booking Confirmed! 🎉</h1>
              </div>
              <div style="padding:30px;background:#FFF8DC;border-radius:0 0 10px 10px;">
                <p>Hello <strong>%s</strong>,</p>
                <div style="background:white;border-radius:10px;padding:20px;margin:20px 0;">
                  <table style="width:100%%;border-collapse:collapse;">
                    <tr><td style="padding:8px;color:#555;">Site:</td><td style="padding:8px;font-weight:bold;">%s</td></tr>
                    <tr><td style="padding:8px;color:#555;">Location:</td><td style="padding:8px;">%s</td></tr>
                    <tr><td style="padding:8px;color:#555;">Date:</td><td style="padding:8px;">%s</td></tr>
                    <tr><td style="padding:8px;color:#555;">Visitors:</td><td style="padding:8px;">%d</td></tr>
                    <tr style="border-top:2px solid #FF9933;">
                      <td style="padding:8px;">Total Amount:</td>
                      <td style="padding:8px;font-weight:bold;color:#FF9933;">₹%.0f</td>
                    </tr>
                  </table>
                </div>
                <p style="color:#888;font-size:14px;">📌 Please carry a valid photo ID for entry.</p>
              </div>
            </div>
            """.formatted(name, siteName, location, date, visitors, amount);
    }
}
