package com.recrutement.modules.verifToken;

import lombok.*;
import com.recrutement.modules.user.User;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "verif_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifToken {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column
    private String value;
    @Column
    private VerifTokenType type;
    @Column
    private Date expiration;
    @ManyToOne()
    private User user;
}
