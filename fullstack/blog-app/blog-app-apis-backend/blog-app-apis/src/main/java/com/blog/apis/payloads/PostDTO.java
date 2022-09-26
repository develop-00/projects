package com.blog.apis.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

	private Integer postId;

	//changed according to github source code

	private String title;

	private String content;

	private String imageName;

	private Date addedDate;

	private CategoryDTO category;

	private UserDTO user;

	private Set<CommentDTO> comments = new HashSet<>();

//	@NotBlank
//	@Size(min = 4, max = 50, message = "Post title must be between 4 and 50 characters.")
//	private String postTitle;
//
//	@NotBlank
//	@Size(min = 5, max = 5000, message = "Post should be between 5 and 5000 characters.")
//	private String postContent;
//
//	private String imageName;
//	private Date addedDate;
//	private CategoryDTO category;
//	private UserDTO user;
//
//	private Set<CommentDTO> comments = new HashSet<>();

}
