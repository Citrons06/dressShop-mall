package dressshop.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberAuth {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    DISABLE("ROLE_DISABLE");

    private final String value;
}
