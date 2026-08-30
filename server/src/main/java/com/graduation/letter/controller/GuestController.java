package com.graduation.letter.controller;

import com.graduation.letter.common.ApiResponse;
import com.graduation.letter.common.ResponseFactory;
import com.graduation.letter.common.SuccessCode;
import com.graduation.letter.model.guest.GuestRequest;
import com.graduation.letter.model.guest.GuestResponse;
import com.graduation.letter.model.guest.UpdateGuestRequest;
import com.graduation.letter.service.interfaces.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    private final ResponseFactory responseFactory;

    @PostMapping("/import")
    public ResponseEntity<ApiResponse<Object>> importGuests(
            @RequestBody @Valid List<GuestRequest> requests
    ) {
        List<GuestResponse> createdGuests = guestService.importGuests(requests);

        return responseFactory.success(createdGuests, SuccessCode.CREATED);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createGuest(
            @RequestBody @Valid GuestRequest request
    ) {
        GuestResponse createdGuest = guestService.createGuest(request);

        return responseFactory.success(createdGuest, SuccessCode.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getGuestById(@PathVariable("id") String id) {
        GuestResponse guest = guestService.getGuestById(id);
        return responseFactory.success(guest, SuccessCode.OK);
    }

    @GetMapping(params = "phoneNumber")
    public ResponseEntity<ApiResponse<Object>> getGuestByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        GuestResponse letter = guestService.getGuestByPhoneNumber(phoneNumber);
        return responseFactory.success(letter, SuccessCode.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAllGuests(
            @RequestParam(value = "page", defaultValue = "1") Long page,
            @RequestParam(value = "size", defaultValue = "10") Long size
    ) {
        List<GuestResponse> guests = guestService.getAllGuests(page, size);

        return responseFactory.success(guests, SuccessCode.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateGuest(
            @PathVariable("id") String id,
            @RequestBody @Valid UpdateGuestRequest request
    ) {
        GuestResponse updatedLetter = guestService.updateGuest(id, request);

        return responseFactory.success(updatedLetter, SuccessCode.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteGuest(@PathVariable("id") String id) {
        guestService.deleteGuest(id);

        return responseFactory.success("Guest deleted successfully", SuccessCode.OK);
    }
}
