package com.example.blogapprestapi.service;

import java.util.List;

import com.example.blogapprestapi.model.Comment;
import com.example.blogapprestapi.payload.CommentDto;

public interface CommentService {
	CommentDto crateCommnet(Long postId,CommentDto commentDto);
	
	List<CommentDto> getAllCommnetsOfPost(Long postId);
	
	CommentDto getComment(Long PostId,Long commentId);
	Comment getComment(Long commentId);
	
	CommentDto updateComment(Long postId,Long commentId,CommentDto commentDto);
	CommentDto deleteComment(Long postId,Long commentId);
}
