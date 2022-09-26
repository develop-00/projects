package com.blog.apis.payloads;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {


	private Integer categoryId;

	@NotBlank
	@Size(min = 4, message = "Title must be atleast 4 character long.")
	private String categoryTitle;

	@NotBlank
	@Size(min = 10, message = "Description must be atleast 10 character long.")
	private String categoryDescription;

}
