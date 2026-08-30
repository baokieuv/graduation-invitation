package com.graduation.letter.service.interfaces;

import com.graduation.letter.model.invitation.InvitationResponse;

public interface InvitationService {
    InvitationResponse getInvitation(String phoneNumber);
}
