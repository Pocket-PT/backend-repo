package com.madeyepeople.pocketpt.global.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CustomExceptionMessage {
    // sign up
    OAUTH2_NECESSARY_INFO_NOT_FOUND("Provider로부터 필수 제공 정보를 받아오지 못했습니다."),
    PROFILE_IMAGE_PARSING_ERROR("profile image URL 파싱에서 에러가 발생했습니다."),
    ;

    private final String message;

    public String getMessage() {
        return this.message;
    }
}