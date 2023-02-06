package com.recrutement.modules.role;

import com.recrutement.modules.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto extends BaseDto {

	private Long id;
	private String name;
}
