package com.recrutement.modules.role;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.recrutement.modules.base.BaseEntity;
import com.recrutement.modules.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {
	@Column(unique=true, length = 50)
	@NotBlank(message = "Role name is required")
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	private Set<User> users;

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + "]";
	}

	
}