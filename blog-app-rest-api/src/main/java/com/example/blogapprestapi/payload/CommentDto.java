package com.example.blogapprestapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	
	private Long id;
	@NotEmpty(message = "name should not be empty or null")
	private String name;
	@NotEmpty(message = "email should not be empty or null")
	@Email
	private String email; 
	@NotEmpty
	@Size(min=5,message = "description should have at least 5 characters")
	private String description;
	
	
}
