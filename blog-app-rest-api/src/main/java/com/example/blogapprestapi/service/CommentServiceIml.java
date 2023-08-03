package com.example.blogapprestapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.blogapprestapi.exception.BlogApiException;
import com.example.blogapprestapi.exception.ResourceNotFoundException;
import com.example.blogapprestapi.model.Comment;
import com.example.blogapprestapi.model.Post;
import com.example.blogapprestapi.payload.CommentDto;
import com.example.blogapprestapi.repository.CommentRepository;

@Service
public class CommentServiceIml implements CommentService {

	private CommentRepository commentRepository;
	private PostService postService;
	private ModelMapper modelMapper;

	public CommentServiceIml(CommentRepository commentRepository, PostService postService, ModelMapper modelMapper) {
		super();
		this.commentRepository = commentRepository;
		this.postService = postService;
		this.modelMapper = modelMapper;
	}

	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = modelMapper.map(commentDto, Comment.class);

		return comment;
	}

	private CommentDto mapToDto(Comment comment) {
		CommentDto commentDto = modelMapper.map(comment, CommentDto.class);

		return commentDto;
	}

	@Override
	public CommentDto crateCommnet(Long postId, CommentDto commentDto) {

		Comment comment = mapToEntity(commentDto);
		Post post = postService.getPostById(postId);

		// set relavant post to the comment
		comment.setPost(post);

		Comment newComment = commentRepository.save(comment);

		return mapToDto(newComment);

	}

	@Override
	public Comment getComment(Long commentId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
		return comment;
	}

	@Override
	public List<CommentDto> getAllCommnetsOfPost(Long postId) {
		List<Comment> comments = commentRepository.findByPostId(postId);

		List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

		return commentDtos;

	}

	@Override
	public CommentDto getComment(Long postId, Long commentId) {
		Post post = postService.getPostById(postId);
		Comment comment = getComment(commentId);

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "this comment not belongs to a any post");
		}

		return mapToDto(comment);
	}

	@Override
	public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
		Post post = postService.getPostById(postId);
		Comment comment = getComment(commentId);

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "this comment not belongs to a any post");
		}
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setDescription(comment.getDescription());
		Comment updateComment = commentRepository.save(comment);

		return mapToDto(updateComment);

	}

	@Override
	public CommentDto deleteComment(Long postId, Long commentId) {
		Post post = postService.getPostById(postId);
		Comment comment = getComment(commentId);

		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "your rquested comment id not belonhs any post");
		}

		commentRepository.delete(comment);

		return mapToDto(comment);

	}

}
