package com.madeyepeople.pocketpt.domain.chattingMessage.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@ToString
public class ChattingMessageCreateRequest {
    private Long chattingRoomId;

    private Long chattingParticipantId;

    private String content;
}
