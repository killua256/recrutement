package com.recrutement.modules.user.dto;

import java.util.Date;

import com.recrutement.modules.base.BaseDto;
import com.recrutement.modules.documents.DocumentDto;
import com.recrutement.modules.role.RoleDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseDto {

	private String firstname;
	private String lastname;
	private String username;
	private String displayName;
	private String email;
	private RoleDto role;
	private Boolean activated;
	private Date activatedAt;
	private String phone;
	private Boolean MFAEnabled;
	private Date lastLoggedInDate;
	private DocumentDto avatar;
	private DocumentDto cover;

}
