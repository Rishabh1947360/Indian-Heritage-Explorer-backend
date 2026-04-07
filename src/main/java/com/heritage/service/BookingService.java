package com.heritage.service;

import com.heritage.dto.ApiResponse;
import com.heritage.dto.BookingDto;
import com.heritage.dto.BookingRequest;
import com.heritage.entity.Booking;
import com.heritage.entity.User;
import com.heritage.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EmailService emailService;

    // POST /api/bookings
    @Transactional
    public ApiResponse<BookingDto> createBooking(BookingRequest request, User user) {
        Booking booking = Booking.builder()
                .user(user)
                .siteId(request.getSiteId())
                .siteName(request.getSiteName())
                .location(request.getLocation())
                .state(request.getState())
                .image(request.getImage() != null ? request.getImage() : "")
                .visitDate(request.getVisitDate())
                .visitors(request.getVisitors())
                .amount(request.getAmount())
                .status(Booking.BookingStatus.booked)
                .build();

        Booking saved = bookingRepository.save(booking);

        // Send confirmation email (async)
        emailService.sendBookingConfirmationEmail(
                user.getEmail(),
                user.getName(),
                saved.getSiteName(),
                saved.getLocation(),
                saved.getVisitDate().toString(),
                saved.getVisitors(),
                saved.getAmount().doubleValue()
        );

        return ApiResponse.ok("Booking confirmed!", BookingDto.from(saved));
    }

    // GET /api/bookings/me
    public ApiResponse<List<BookingDto>> getMyBookings(Long userId) {
        List<BookingDto> bookings = bookingRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(BookingDto::from)
                .toList();

        return ApiResponse.ok(bookings);
    }

    // PATCH /api/bookings/{id}/cancel
    @Transactional
    public ApiResponse<Void> cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findByIdAndUserId(bookingId, userId)
                .orElse(null);

        if (booking == null) {
            return ApiResponse.error("Booking not found.");
        }

        if (booking.getStatus() == Booking.BookingStatus.cancelled) {
            return ApiResponse.error("Booking is already cancelled.");
        }

        booking.setStatus(Booking.BookingStatus.cancelled);
        bookingRepository.save(booking);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Booking cancelled successfully.")
                .build();
    }
}
