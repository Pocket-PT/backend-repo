package com.madeyepeople.pocketpt.domain.chattingRoom.dto.request;

import com.madeyepeople.pocketpt.domain.chattingParticipant.dto.request.ChattingParticipantCreateRequest;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
public class ChattingRoomCreateByParticipantListRequest {
    // @NotBlank(message = "식당 이름은 빈칸일 수 없습니다")
    // @NotNull(message = "식당 카테고리는 공백이 올 수 없습니다.")

    private Long hostId;
    private List<ChattingParticipantCreateRequest> chattingParticipantCreateRequestList;

}
