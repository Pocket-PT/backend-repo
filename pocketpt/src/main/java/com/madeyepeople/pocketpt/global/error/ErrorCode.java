package com.madeyepeople.pocketpt.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Global
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E001", "서버 오류"),
    INPUT_INVALID_VALUE(HttpStatus.NOT_ACCEPTABLE, "E002", "잘못된 입력"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "E003", "JWT 관련 오류"),


    // Account
    EXAMPLE_USER_ERROR(HttpStatus.BAD_REQUEST, "E099", "테스트용 예시 에러코드"),
    JWT_VALIDATION_ERROR(HttpStatus.UNAUTHORIZED, "EA001", "JWT 유효성 예외 발생"),
    ACCOUNT_ALREADY_REGISTERED(HttpStatus.CONFLICT, "EA002", "이미 회원가입된 사용자입니다."),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "EA003", "존재하지 않는 사용자"),
    TRAINER_CAREER_ERROR(HttpStatus.NOT_ACCEPTABLE, "EA004", "트레이너 이력 관련 오류"),
    TRAINER_MONTHLY_PT_PRICE_ERROR(HttpStatus.NOT_ACCEPTABLE, "EA005", "트레이너 월별 단가 관련 오류"),
    ACCOUNT_PURPOSE_ERROR(HttpStatus.NOT_ACCEPTABLE, "EA006", "목표 관련 오류"),
    PHYSICAL_INFO_ERROR(HttpStatus.NOT_ACCEPTABLE, "EA007", "신체정보 관련 오류"),


    // PtMatching
    PT_MATCHING_ERROR(HttpStatus.NOT_ACCEPTABLE, "EPM001", "PT matching 관련 오류"),

    // Chatting Room
    CHATTING_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "ECR001", "존재하지 않는 채팅방"),
    TOP_CHATTING_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "ECR002", "존재하지 않는 상단고정 채팅방"),
    TOP_CHATTING_ROOM_COUNT_EXCEEDED(HttpStatus.NOT_ACCEPTABLE, "ECR003", "상단고정 채팅방 개수 초과"),

    // Chatting Participant
    CHATTING_PARTICIPANT_NOT_FOUND(HttpStatus.NOT_FOUND, "ECP001", "존재하지 않는 채팅 참여자"),

    // Chatting Message
    CHATTING_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "ECM001", "존재하지 않는 채팅 메시지"),

    // Chatting Message Bookmark
    CHATTING_MESSAGE_BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "ECMB001", "존재하지 않는 채팅 메시지 북마크"),

    // File
    CHATTING_FILE_ERROR(HttpStatus.BAD_REQUEST, "EF001", "파일 관련 오류"),

    // historicalData
    HISTORICAL_DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "EHD001", "존재하지 않는 HistoricalData"),
    HISTORICAL_DATA_ERROR(HttpStatus.BAD_REQUEST, "EHD002", "HistoricalData 관련 오류"),

    // historicalDataFile
    HISTORY_DATA_FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "EHDF001", "존재하지 않는 HistoricalDataFile"),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
        // 결과 예시 - "Validation error - Reason why it isn't valid"
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }
}