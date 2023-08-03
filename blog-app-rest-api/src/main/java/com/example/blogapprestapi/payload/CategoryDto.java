package com.example.blogapprestapi.payload;

import java.util.Set;

import com.example.blogapprestapi.model.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

	private Long id;

	private String title;

	private String description;

	private String content;
	private Set<Post> posts;

}
