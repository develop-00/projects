package com.blog.apis.payloads;

import lombok.Data;

@Data
public class JWTAuthResponse {
	private String token;
	private UserDTO user;

}
