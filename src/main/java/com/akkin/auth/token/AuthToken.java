package com.akkin.auth.token;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    public AuthToken(Long memberId) {
        this.memberId = memberId;
        this.accessToken = UUID.randomUUID().toString();
        this.refreshToken = UUID.randomUUID().toString();
    }

    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now();
        this.expiredAt = this.createdAt.plusYears(1);
    }

    public void reIssuance() {
        this.accessToken = UUID.randomUUID().toString();
        this.refreshToken = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.expiredAt = this.createdAt.plusYears(1);
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }
}
