package com.findar.bookstore.model.entity;

import com.findar.bookstore.model.audith.AuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "otp")
@Builder
public class OtpBase extends AuditEntity {

    private String email;

    private String otpCode;

    private Integer attempt;

    private Boolean active;

}
