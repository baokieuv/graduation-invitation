package com.graduation.letter.controller;

import com.graduation.letter.model.guest.GuestRequest;
import com.graduation.letter.model.guest.GuestResponse;
import com.graduation.letter.model.guest.UpdateGuestRequest;
import com.graduation.letter.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PostMapping("/import")
    public ResponseEntity<List<GuestResponse>> importGuests(
            @RequestBody @Valid List<GuestRequest> requests
    ) {
        List<GuestResponse> createdGuests = guestService.importGuests(requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGuests);
    }

    @PostMapping
    public ResponseEntity<GuestResponse> createGuest(
            @RequestBody @Valid GuestRequest request
    ) {
        GuestResponse createdGuest = guestService.createGuest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGuest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestResponse> getGuestById(@PathVariable("id") String id) {
        GuestResponse letter = guestService.getGuestById(id);
        return ResponseEntity.ok(letter);
    }

    @GetMapping(params = "phoneNumber")
    public ResponseEntity<GuestResponse> getGuestByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        GuestResponse letter = guestService.getGuestByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(letter);
    }

    @GetMapping
    public ResponseEntity<List<GuestResponse>> getAllGuests(
            @RequestParam(value = "page", defaultValue = "1") Long page,
            @RequestParam(value = "size", defaultValue = "10") Long size
    ) {
        List<GuestResponse> guests = guestService.getAllGuests(page, size);
        return ResponseEntity.ok(guests);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuestResponse> updateGuest(
            @PathVariable("id") String id,
            @RequestBody @Valid UpdateGuestRequest request
    ) {
        GuestResponse updatedLetter = guestService.updateGuest(id, request);
        return ResponseEntity.ok(updatedLetter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGuest(@PathVariable("id") String id) {
        guestService.deleteGuest(id);
        return ResponseEntity.ok("Guest deleted successfully");
    }
}
