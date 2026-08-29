package com.graduation.letter.service.impl;

import com.graduation.letter.model.guest.GuestRequest;
import com.graduation.letter.model.guest.GuestResponse;
import com.graduation.letter.service.GuestService;

import java.util.List;

public class GuestServiceImpl implements GuestService {
    @Override
    public GuestResponse createGuest(GuestRequest request) {
        return null;
    }

    @Override
    public GuestResponse getGuestById(String id) {
        return null;
    }

    @Override
    public GuestResponse getGuestByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public List<GuestResponse> getAllGuests(Long page, Long size) {
        return List.of();
    }

    @Override
    public GuestResponse updateGuest(String id, GuestRequest request) {
        return null;
    }

    @Override
    public void deleteGuest(String id) {

    }
}
