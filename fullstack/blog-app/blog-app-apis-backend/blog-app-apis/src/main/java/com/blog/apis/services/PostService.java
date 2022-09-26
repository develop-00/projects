package com.blog.apis.services;

import java.util.List;

import com.blog.apis.payloads.PostDTO;
import com.blog.apis.payloads.PostResponse;

public interface PostService {

	// create
	PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

	// update
	PostDTO updatePost(PostDTO postDTO, Integer postId);

	// delete
	void deletePost(Integer postId);

	// get all posts
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// get single post
	PostDTO getPostById(Integer postId);

	// get all posts by category
	List<PostDTO> getPostsByCategory(Integer categoryId);

	// get all posts by users
	List<PostDTO> getPostsByUser(Integer userId);

	// search posts by keyword
	List<PostDTO> searchPosts(String keyword);
}
