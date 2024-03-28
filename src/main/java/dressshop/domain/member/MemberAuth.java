package dressshop.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class MemberAuth {

    @Id @GeneratedValue
    @Column(name = "auth_id")
    private Long id;

    private String authType;
    private String authRole;
}
