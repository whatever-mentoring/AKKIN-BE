package com.akkin.member;

import com.akkin.common.BaseTimeEntity;
import com.akkin.gulbi.domain.Gulbi;
import com.akkin.auth.apple.dto.AppleUser;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Gulbi> gulbis = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Builder
    public Member(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Member(AppleUser appleUser) {
        this.name = appleUser.getName();
        this.email = appleUser.getEmail();
    }

    protected Member() {
    }
}
