package com.madeyepeople.pocketpt.domain.chattingMessage.service;

import com.madeyepeople.pocketpt.domain.chattingMessage.dto.request.ChattingFileCreateRequest;
import com.madeyepeople.pocketpt.domain.chattingMessage.dto.request.ChattingMessageCreateRequest;
import com.madeyepeople.pocketpt.domain.chattingMessage.dto.response.ChattingMessageResponse;
import com.madeyepeople.pocketpt.domain.chattingMessage.entity.ChattingMessage;
import com.madeyepeople.pocketpt.domain.chattingMessage.mapper.ToChattingMessageEntity;
import com.madeyepeople.pocketpt.domain.chattingMessage.mapper.ToChattingMessageResponse;
import com.madeyepeople.pocketpt.domain.chattingMessage.repository.ChattingMessageRepository;
import com.madeyepeople.pocketpt.domain.chattingParticipant.dto.response.ChattingParticipantResponse;
import com.madeyepeople.pocketpt.domain.chattingParticipant.entity.ChattingParticipant;
import com.madeyepeople.pocketpt.domain.chattingParticipant.mapper.ToChattingParticipantResponse;
import com.madeyepeople.pocketpt.domain.chattingParticipant.repository.ChattingParticipantRepository;
import com.madeyepeople.pocketpt.domain.chattingRoom.dto.response.ChattingRoomResponse;
import com.madeyepeople.pocketpt.domain.chattingRoom.entity.ChattingRoom;
import com.madeyepeople.pocketpt.domain.chattingRoom.mapper.ToChattingRoomResponse;
import com.madeyepeople.pocketpt.domain.chattingRoom.repository.ChattingRoomRepository;
import com.madeyepeople.pocketpt.global.result.ResultCode;
import com.madeyepeople.pocketpt.global.result.ResultResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChattingMessageService {
    private final ChattingMessageRepository chattingMessageRepository;

    private final ChattingRoomRepository chattingRoomRepository;

    private final ChattingParticipantRepository chattingParticipantRepository;

    private final ToChattingMessageEntity toChattingMessageEntity;

    private final ToChattingMessageResponse toChattingMessageResponse;

    @Transactional
    public Map<String, Object> createChattingMessage(ChattingMessageCreateRequest chattingMessageCreateRequest) {
        // [1] 채팅방 유효성 검사
        Optional<ChattingRoom> foundChattingRoom = chattingRoomRepository.findByChattingRoomId(chattingMessageCreateRequest.getChattingRoomId());

        // [2] 채팅 sender 유효성 검사
        Optional<ChattingParticipant> chattingParticipant = chattingParticipantRepository.findById(chattingMessageCreateRequest.getChattingParticipantId());

        // [3] 채팅 메시지 저장 및 정보 담기
        ChattingMessage chattingMessage = toChattingMessageEntity.toChattingMessageCreateEntity(chattingParticipant.get(), chattingMessageCreateRequest);
        ChattingMessage savedChattingMessage = chattingMessageRepository.save(chattingMessage);
        ChattingMessageResponse chattingMessageResponse = toChattingMessageResponse.toChattingMessageCreateResponse(foundChattingRoom.get().getChattingRoomId(), chattingParticipant.get().getChattingParticipantId(), savedChattingMessage);

        // [4] 채팅방 id, 채팅 sender id, 채팅 메시지 정보가 담긴 chattingMessageResponse
        ResultResponse resultResponse = new ResultResponse(ResultCode.CHATTING_MESSAGE_CREATE_SUCCESS, chattingMessageResponse);

        Map<String, Object> map = new HashMap<>();
        map.put("chattingRoomId", chattingMessageResponse.getChattingRoomId());
        map.put("resultResponse", resultResponse);

        return map;
    }

    @Transactional
    public ResultResponse createChattingFile(ChattingFileCreateRequest chattingFileCreateRequest) {
        return null;
    }
}
