package com.graduation.letter.service.interfaces;

import com.graduation.letter.model.guest.GuestRequest;
import com.graduation.letter.model.guest.GuestResponse;
import com.graduation.letter.model.guest.UpdateGuestRequest;

import java.util.List;

public interface GuestService {

    List<GuestResponse> importGuests(List<GuestRequest> requests);

    GuestResponse createGuest(GuestRequest request);

    GuestResponse getGuestById(String id);

    GuestResponse getGuestByPhoneNumber(String phoneNumber);

    GuestResponse getGuestByPhoneNumber(String phoneNumber, Boolean throwIfNotFound);

    List<GuestResponse> getAllGuests(Long page, Long size);

    GuestResponse updateGuest(String id, UpdateGuestRequest request);

    GuestResponse deleteGuest(String id);
}
