package com.madeyepeople.pocketpt.domain.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.madeyepeople.pocketpt.domain.account.constants.Role;
import com.madeyepeople.pocketpt.global.common.BaseEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;


@Entity
@Getter
@ToString
@NoArgsConstructor
// TODO: @SQLDelete 로 삭제시 is_deleted = true 로 변경되게 하기
public class Account extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long accountId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Nonnull
    private String provider;

    @Nonnull
    private String email;

    @Column(name = "oauth2_id")
    private Long oauth2Id;

    private String name;

    private String phoneNumber;

    private String nickname;

    private String profilePictureUrl;

    private String gender;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

    // oauth2 provider가 제공하는 access token. 회원 탈퇴를 위해 필요.
    private String oauth2AccessToken;
    @JsonIgnore
    private String password;


    @Builder
    public Account(Long oauth2Id, String email, String provider, String nickname, String oauth2AccessToken, String password, String profilePictureUrl) {
        this.oauth2Id = oauth2Id;
        this.provider = provider;
        this.email = email;
        this.nickname = nickname;
        this.oauth2AccessToken = oauth2AccessToken;
        this.profilePictureUrl = profilePictureUrl;
        this.password = password;
    }

    public Account update(String nickname, String oauth2AccessToken) {
        this.nickname = nickname;
        this.oauth2AccessToken = oauth2AccessToken;
        return this;
    }
}
