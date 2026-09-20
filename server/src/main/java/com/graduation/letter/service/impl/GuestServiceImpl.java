package com.graduation.letter.service.impl;

import com.graduation.letter.common.Formatter;
import com.graduation.letter.exception.ApiException;
import com.graduation.letter.exception.ErrorCode;
import com.graduation.letter.model.guest.Guest;
import com.graduation.letter.model.guest.GuestRequest;
import com.graduation.letter.model.guest.GuestResponse;
import com.graduation.letter.model.guest.UpdateGuestRequest;
import com.graduation.letter.repository.GuestRepository;
import com.graduation.letter.service.interfaces.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;

    @Override
    public List<GuestResponse> importGuests(List<GuestRequest> requests) {

        List<Guest> savedGuests = new ArrayList<>();

        for (GuestRequest request : requests) {
            String phoneNumber = Formatter.normalizeIdentifier(request.phoneNumber());
            Optional<Guest> existingGuest = guestRepository.findByPhoneNumberAndActiveTrue(phoneNumber);

            Guest guest;

            if (existingGuest.isPresent()) {
                guest = existingGuest.get();
                guest.update(request);
                guest.setPhoneNumber(phoneNumber);
            }
            else {
                guest = new Guest(request);
                guest.setPhoneNumber(phoneNumber); // Ensure normalized number is saved
            }

            savedGuests.add(guestRepository.save(guest));
        }

        return savedGuests.stream()
                .map(GuestResponse::new)
                .toList();
    }

    @Override
    public GuestResponse createGuest(GuestRequest request) {

        String phoneNumber = Formatter.normalizeIdentifier(request.phoneNumber());
        Optional<Guest> existingGuest = guestRepository.findByPhoneNumberAndActiveTrue(phoneNumber);

        if (existingGuest.isPresent()) {
            log.info("Guest with phone number {} already exists", phoneNumber);

            throw new ApiException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Guest with phone number " + phoneNumber + " already exists");
        }

        Guest guest = new Guest(request);
        guest.setPhoneNumber(phoneNumber); // Ensure normalized number is saved

        Guest savedGuest = guestRepository.save(guest);

        return new GuestResponse(savedGuest);
    }

    @Override
    @Cacheable(value = "guests", key = "#id")
    public GuestResponse getGuestById(String id) {
        UUID uuid = Formatter.parseUUID(id);

        Guest guest = guestRepository.findByIdAndActiveTrue(uuid)
                .orElseThrow(() -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "Guest not found with id: " + id));

        return new GuestResponse(guest);
    }

    @Override
    @Cacheable(value = "guests", key = "#phoneNumber")
    public GuestResponse getGuestByPhoneNumber(String phoneNumber) {
        String normalizedPhoneNumber = Formatter.normalizeIdentifier(phoneNumber);

        Guest guest = guestRepository.findByPhoneNumberAndActiveTrue(normalizedPhoneNumber)
                .orElseThrow(() -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "Guest not found with phone number: " + phoneNumber));

        return new GuestResponse(guest);
    }

    @Override
    @Cacheable(value = "guests", key = "#phoneNumber")
    public GuestResponse getGuestByPhoneNumber(String phoneNumber, Boolean throwIfNotFound) {
        String normalizedPhoneNumber = Formatter.normalizeIdentifier(phoneNumber);

        Optional<Guest> guestOptional = guestRepository.findByPhoneNumberAndActiveTrue(normalizedPhoneNumber);

        if (guestOptional.isEmpty() && throwIfNotFound) {
            throw new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "Guest not found with phone number: " + phoneNumber);
        }

        return guestOptional.map(GuestResponse::new).orElse(null);
    }

    @Override
    public List<GuestResponse> getAllGuests(Long page, Long size) {
        if (page == null || page < 1 || size == null || size < 1) {
            throw new IllegalArgumentException("page and size must be >= 1");
        }

        Pageable pageable = Pageable.ofSize(Math.toIntExact(size)).withPage(Math.toIntExact(page - 1));
        List<Guest> guests = guestRepository.findAllByActiveTrue(pageable);

        return guests.stream()
                .map(GuestResponse::new)
                .toList();
    }

    @Override
    @Caching(
            put = { @CachePut(value = "guests", key = "#id") },
            evict = { @CacheEvict(value = "guests", key = "#result.phoneNumber") }
    )
    public GuestResponse updateGuest(String id, UpdateGuestRequest request) {

        UUID uuid = Formatter.parseUUID(id);

        Guest guest = guestRepository.findByIdAndActiveTrue(uuid)
                .orElseThrow(() -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "Guest not found with id: " + id));

        guest.setName(request.name());

        Guest savedGuest = guestRepository.save(guest);

        return new GuestResponse(savedGuest);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "guests", key = "#id"),
            @CacheEvict(value = "guests", key = "#result.phoneNumber")
    })
    public GuestResponse deleteGuest(String id) {
        UUID uuid = Formatter.parseUUID(id);

        Guest guest = guestRepository.findByIdAndActiveTrue(uuid)
                .orElseThrow(() -> new ApiException(ErrorCode.RESOURCE_NOT_FOUND, "Guest not found with id: " + id));

        guest.setActive(false);

        Guest savedGuest = guestRepository.save(guest);
        return new GuestResponse(savedGuest);
    }
}
