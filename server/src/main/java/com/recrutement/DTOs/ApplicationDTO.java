package com.recrutement.DTOs;

import com.recrutement.modules.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationDTO {

    private User user;
    private JobOfferDTO jobOffer;
    private String status;
}
