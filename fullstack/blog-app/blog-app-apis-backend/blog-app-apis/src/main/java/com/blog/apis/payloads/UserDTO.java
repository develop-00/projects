package com.blog.apis.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

	private Integer id;

	@NotEmpty
	@Size(min = 4, message = "Username must be at least 4 character long.")
	private String name;

	@Email(message = "Your email address is not valid.")
	@NotBlank(message = "Email is required and must not be empty")
	private String email;

	@NotEmpty
	@Size(min = 3, max = 10, message = "Password must be between 3 and 10 characters.(inclusive)")
	// @Pattern(regexp = )
	private String password;

	@NotEmpty
	private String about;

	private Set<RoleDTO> roles = new HashSet<>();

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

}
