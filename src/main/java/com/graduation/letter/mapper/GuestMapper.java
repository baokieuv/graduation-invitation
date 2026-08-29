package com.graduation.letter.mapper;

import com.graduation.letter.model.guest.Guest;
import com.graduation.letter.model.guest.GuestRequest;
import com.graduation.letter.model.guest.GuestResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuestMapper {
    Guest toEntity(GuestRequest request);

    GuestResponse toResponse(Guest letter);
}
