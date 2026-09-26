package com.thanhtam.ecommerce.identity.domain.entities;

import com.thanhtam.ecommerce.identity.domain.enums.AccountStatus;
import com.thanhtam.ecommerce.identity.domain.enums.ClientRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(
        name = "clients",
        indexes = {
                @Index(name = "idx_client_email", columnList = "email", unique = true),
                @Index(name = "idx_client_status", columnList = "account_status")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client extends BaseEntity {
    // thông tin tài khoản
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", length = 36, nullable = false, updatable = false)
    UUID id;

    @Column(name = "client_name",nullable = false, unique = true, length = 100)
    String clientName;

    @Column(name = "password_hash",nullable = false)
    String passwordHash;

    @Column(name = "first_name", length = 100)
    String firstName;

    @Column(name = "last_name", length = 100)
    String lastName;

    @Column(name = "phone_number", length = 100, unique = true)
    String phone;

    @Column(name = "email", length = 100, unique = true)
    String email;

//  trạng thái tài khoản
    @Enumerated(EnumType.STRING)
        @Column(name = "account_status", length = 40, nullable = false)
    AccountStatus accountStatus;

    @Column(name = "is_phone_verified", nullable = false)
            @Builder.Default
    boolean isPhoneVerified = false;

    @Column(name = "is_email_verified", nullable = false)
    @Builder.Default
    boolean isMailVerified = false;

    @Column(name = "failed_login_attempts", nullable = false)
    @Builder.Default
    int failedLoginAttempts = 0;

    @Column(name = "password_change_at")
    Instant passwordChangeAt;

    @Column(name="avatar_url")
    String avatarUrl;
    //phân quyền
    @Enumerated(EnumType.STRING)
    @Column(name = "roles", nullable = false)
    ClientRole roles;


    public static Client create(String clientName, String passwordHash, String email) {
        return Client.builder()
                .clientName(clientName)
                .passwordHash(passwordHash)
                .email(email)
                .accountStatus(AccountStatus.PENDING_VERIFICATION)
                .roles(ClientRole.CLIENT)
                .isMailVerified(false)
                .isPhoneVerified(false)
                .failedLoginAttempts(0)
                .build();
    }

}
