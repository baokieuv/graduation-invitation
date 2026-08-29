package com.graduation.letter.repository;

import com.graduation.letter.model.guest.Guest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GuestRepository extends JpaRepository<Guest, UUID> {

    Optional<Guest> findByIdAndActiveTrue(UUID id);

    Optional<Guest> findByPhoneNumberAndActiveTrue(String phoneNumber);

    List<Guest> findAllByActiveTrue(Pageable pageable);
}
