package com.graduation.letter.service.impl;

import com.graduation.letter.common.Formatter;
import com.graduation.letter.mapper.GuestMapper;
import com.graduation.letter.model.guest.Guest;
import com.graduation.letter.model.guest.GuestRequest;
import com.graduation.letter.model.guest.GuestResponse;
import com.graduation.letter.model.guest.UpdateGuestRequest;
import com.graduation.letter.repository.GuestRepository;
import com.graduation.letter.service.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;


    @Override
    public List<GuestResponse> importGuests(List<GuestRequest> requests) {

        List<Guest> savedGuests = new ArrayList<>();

        for (GuestRequest request : requests) {
            String phoneNumber = Formatter.normalizePhoneNumber(request.phoneNumber());
            Optional<Guest> existingGuest = guestRepository.findByPhoneNumberAndActiveTrue(phoneNumber);

            if (existingGuest.isPresent()) {
                log.info("Guest with phone number {} already exists during import", phoneNumber);
                continue;
            }

            Guest guest = guestMapper.toEntity(request);
            guest.setPhoneNumber(phoneNumber); // Ensure normalized number is saved
            savedGuests.add(guestRepository.save(guest));
        }

        return savedGuests.stream()
                .map(guestMapper::toResponse)
                .toList();
    }

    @Override
    public GuestResponse createGuest(GuestRequest request) {

        String phoneNumber = Formatter.normalizePhoneNumber(request.phoneNumber());
        Optional<Guest> existingGuest = guestRepository.findByPhoneNumberAndActiveTrue(phoneNumber);

        if (existingGuest.isPresent()) {
            log.info("Guest with phone number {} already exists", phoneNumber);
            return null;
        }

        Guest guest = guestMapper.toEntity(request);
        guest.setPhoneNumber(phoneNumber); // Ensure normalized number is saved

        return guestMapper.toResponse(guestRepository.save(guest));
    }

    @Override
    public GuestResponse getGuestById(String id) {
        UUID uuid = Formatter.parseUUID(id);

        Guest guest = guestRepository.findByIdAndActiveTrue(uuid)
                .orElseThrow(() -> new RuntimeException("Guest not found with id: " + id));

        return guestMapper.toResponse(guest);
    }

    @Override
    public GuestResponse getGuestByPhoneNumber(String phoneNumber) {
        String normalizedPhoneNumber = Formatter.normalizePhoneNumber(phoneNumber);

        Guest guest = guestRepository.findByPhoneNumberAndActiveTrue(normalizedPhoneNumber)
                .orElseThrow(() -> new RuntimeException("Guest not found with phone number: " + phoneNumber));

        return guestMapper.toResponse(guest);
    }

    @Override
    public List<GuestResponse> getAllGuests(Long page, Long size) {

        Pageable pageable = Pageable.ofSize(size.intValue()).withPage(page.intValue() - 1);

        List<Guest> guests = guestRepository.findAllByActiveTrue(pageable);

        return guests.stream()
                .map(guestMapper::toResponse)
                .toList();
    }

    @Override
    public GuestResponse updateGuest(String id, UpdateGuestRequest request) {

        UUID uuid = Formatter.parseUUID(id);

        Guest guest = guestRepository.findByIdAndActiveTrue(uuid)
                .orElseThrow(() -> new RuntimeException("Guest not found with id: " + id));

        guest.setName(request.name());

        return guestMapper.toResponse(guestRepository.save(guest));
    }

    @Override
    public void deleteGuest(String id) {
        UUID uuid = Formatter.parseUUID(id);

        Guest guest = guestRepository.findByIdAndActiveTrue(uuid)
                .orElseThrow(() -> new RuntimeException("Guest not found with id: " + id));

        guest.setActive(false);
        guestRepository.save(guest);
    }
}
