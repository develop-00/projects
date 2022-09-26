package com.blog.apis.services;

import com.blog.apis.payloads.CommentDTO;

public interface CommentService {

	CommentDTO createComment(CommentDTO commentDTO, Integer postId);

	void deleteComment(Integer commentId);

}
