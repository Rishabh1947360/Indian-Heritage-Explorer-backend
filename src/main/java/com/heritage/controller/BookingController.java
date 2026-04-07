package com.heritage.controller;

import com.heritage.dto.ApiResponse;
import com.heritage.dto.BookingDto;
import com.heritage.dto.BookingRequest;
import com.heritage.entity.User;
import com.heritage.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    // POST /api/bookings
    @PostMapping
    public ResponseEntity<ApiResponse<BookingDto>> createBooking(
            @Valid @RequestBody BookingRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(201).body(bookingService.createBooking(request, user));
    }

    // GET /api/bookings/me
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<BookingDto>>> getMyBookings(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(bookingService.getMyBookings(user.getId()));
    }

    // PATCH /api/bookings/{id}/cancel
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelBooking(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        ApiResponse<Void> response = bookingService.cancelBooking(id, user.getId());
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }
}
