package com.example.blogapprestapi.payload;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class PostDto {
	
	
	private Long id;

	@NotEmpty
	@Size(min=2, message = "tittle should have at least 2 characters")
	private String title;
	@NotEmpty
	@Size(min=5, message = "description should have at least 5 characters")
	private String description;
	@NotEmpty
	private String content;
	
	private Set<CommentDto> comments;
	private Long categoryId;

}
