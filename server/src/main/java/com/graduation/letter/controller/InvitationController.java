package com.graduation.letter.controller;

import com.graduation.letter.common.ApiResponse;
import com.graduation.letter.common.Formatter;
import com.graduation.letter.common.ResponseFactory;
import com.graduation.letter.common.SuccessCode;
import com.graduation.letter.model.invitation.InvitationResponse;
import com.graduation.letter.service.interfaces.InvitationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/invitation")
public class InvitationController {

    private final InvitationService invitationService;

    private final ResponseFactory responseFactory;

    @GetMapping(params = "identifier")
    public ResponseEntity<ApiResponse<Object>> getInvitation(@RequestParam("identifier") String identifier) {
        String normalizedPhoneNumber = Formatter.normalizeIdentifier(identifier);

        InvitationResponse response = invitationService.getInvitation(normalizedPhoneNumber);

        return responseFactory.success(response, SuccessCode.OK);
    }
}
