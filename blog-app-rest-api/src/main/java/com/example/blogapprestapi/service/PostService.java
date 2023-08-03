package com.example.blogapprestapi.service;

import java.util.List;

import com.example.blogapprestapi.model.Post;
import com.example.blogapprestapi.payload.PostDto;
import com.example.blogapprestapi.payload.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto);
	PostDto getPost(Long id);
	Post getPostById(Long id);
	List<PostDto> getAllPostByCategory(Long CategoryId);
	PostResponse getAllPost(int pageNo,int pageSize,String sortBy,String sortDir);
	PostDto deletePost(Long id);
	PostDto updatePost(Long id,PostDto postDto);
	
	

}
