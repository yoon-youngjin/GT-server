package dev.yoon.gridgetest.domain.user.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    @Column(name = "password", length = 200)
    private String value;

    @Builder
    public Password(final String value) {
        this.value = encodePassword(value);
    }

    public static Password from(String value) {
        return Password.builder()
                .value(value)
                .build();
    }

    private String encodePassword(final String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public boolean isMatches(String rawPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, this.value);
    }

    public void changePassword(final String newPassword) {
        value = encodePassword(newPassword);
    }

}
