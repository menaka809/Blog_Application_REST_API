package com.example.blogapprestapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.blogapprestapi.exception.ResourceNotFoundException;
import com.example.blogapprestapi.model.Category;
import com.example.blogapprestapi.model.Post;
import com.example.blogapprestapi.payload.PostDto;
import com.example.blogapprestapi.payload.PostResponse;
import com.example.blogapprestapi.repository.CategoryRepository;
import com.example.blogapprestapi.repository.PostRepository;

@Service
public class PostServiceIml implements PostService {

	Logger logger = LoggerFactory.getLogger(getClass());

	private PostRepository postRepository;

	private ModelMapper modelMapper;

	private CategoryRepository categoryRepository;

	public PostServiceIml(PostRepository postRepository, ModelMapper modelMapper,
			CategoryRepository categoryRepository) {
		super();
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
		this.categoryRepository = categoryRepository;
	}

	private PostDto mapToDto(Post post) {
		PostDto postResponse = modelMapper.map(post, PostDto.class);

		return postResponse;

	}

	private Post mapToEntity(PostDto postDto) {
		Post post = modelMapper.map(postDto, Post.class);

		return post;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postDto.getCategoryId()));
		Post post = mapToEntity(postDto);
		post.setCategory(category);
		postRepository.save(post);
		PostDto responseDto = mapToDto(post);
		return responseDto;

	}

	@Override
	public Post getPostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		return post;

	}

	@Override
	public PostDto getPost(Long id) {

		Post post = getPostById(id);

		return mapToDto(post);

	}

	@Override
	public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		// create pageable instance
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

		// get list of post pages using pageable instance
		Page<Post> posts = postRepository.findAll(pageable);

		// get content of page object
		List<Post> listOfPost = posts.getContent();

		List<PostDto> contents = listOfPost.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(contents);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());

		return postResponse;

	}

	@Override
	public PostDto deletePost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));

		postRepository.delete(post);
		return mapToDto(post);
	}

	@Override
	public PostDto updatePost(Long id, PostDto postDto) {
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postDto.getCategoryId()));
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		post.setCategory(category);
		postRepository.save(post);
		PostDto updateResponse = mapToDto(post);

		return updateResponse;

	}

	@Override
	public List<PostDto> getAllPostByCategory(Long CategoryId) {
		List<PostDto> posts = postRepository.findByCategoryId(CategoryId).stream().map((post) -> mapToDto(post))
				.collect(Collectors.toList());

		return posts;
	}

}
