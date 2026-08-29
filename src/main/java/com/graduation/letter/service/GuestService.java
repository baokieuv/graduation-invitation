package com.graduation.letter.service;

import com.graduation.letter.model.guest.GuestRequest;
import com.graduation.letter.model.guest.GuestResponse;
import com.graduation.letter.model.letter.TemplateRequest;
import com.graduation.letter.model.letter.TemplateResponse;

import java.util.List;

public interface GuestService {


    GuestResponse createGuest(GuestRequest request);

    GuestResponse getGuestById(String id);

    GuestResponse getGuestByPhoneNumber(String phoneNumber);

    List<GuestResponse> getAllGuests(Long page, Long size);

    GuestResponse updateGuest(String id, GuestRequest request);

    void deleteGuest(String id);
}
