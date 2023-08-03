package com.example.blogapprestapi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blogapprestapi.payload.CommentDto;
import com.example.blogapprestapi.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/posts")
@Tag(name="CRUD Rest APIs for  Comment Resource")
public class CommentController {

	private CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}

	// add comments to the post
	@Operation(summary = "Create Comment REST API",description = "Create Comment REST API is used to save the Comment into database")
	@ApiResponse(responseCode = "201",description = "Http status 201 CREATED")
	@PostMapping("/{postId}/comments")
	public ResponseEntity<CommentDto> addComment(@PathVariable Long postId, @Valid @RequestBody CommentDto commentDto) {
		CommentDto newComment = commentService.crateCommnet(postId, commentDto);

		return new ResponseEntity<CommentDto>(newComment, HttpStatus.CREATED);

	}

	// get All comments related to the post
	@Operation(summary = "Get Comments REST API",description = "Get Comments REST API is used to get the All Comments from database")
	@ApiResponse(responseCode = "200",description = "Http status 200 OK")
	@GetMapping("/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getAllCommentsOfPost(@PathVariable Long postId) {
		List<CommentDto> allComments = commentService.getAllCommnetsOfPost(postId);

		return new ResponseEntity<List<CommentDto>>(allComments, HttpStatus.OK);

	}

	// get one specific comment related to the post
	@Operation(summary = "Get Comment REST API",description = "Get Comment REST API is used to get the Comment from database")
	@ApiResponse(responseCode = "200",description = "Http status 200 OK")
	@GetMapping("/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updateComments(@PathVariable Long postId, @PathVariable Long id) {
		CommentDto getComment = commentService.getComment(postId, id);
		return new ResponseEntity<CommentDto>(getComment, HttpStatus.OK);

	}

	// update comment of the post
	@Operation(summary = "Update Comment REST API",description = "Update Comment REST API is used to Update the Comment in database")
	@ApiResponse(responseCode = "200",description = "Http status 200 OK")
	@PutMapping("/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> getAllCommentsOfPost(@PathVariable Long postId, @PathVariable Long id,
			@Valid @RequestBody CommentDto commentDto) {
		CommentDto updateComment = commentService.updateComment(postId, id, commentDto);
		return new ResponseEntity<CommentDto>(updateComment, HttpStatus.OK);

	}

	// delete comment of the post
	@Operation(summary = "Delete Comment REST API",description = "Delete  Comment REST API is used to Delete  the Comment from database")
	@ApiResponse(responseCode = "200",description = "Http status 200 OK")
	@DeleteMapping("/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> deleteCommentsFromPost(@PathVariable Long postId, @PathVariable Long id) {
		CommentDto deleteComment = commentService.deleteComment(postId, id);
		return new ResponseEntity<CommentDto>(deleteComment, HttpStatus.OK);

	}

}
