import { privateAxios, axiosProvider } from "./helper";

const createPost = (postData) => {
	return privateAxios
		.post(
			`/user/${postData.userId}/category/${postData.categoryId}/posts`,
			postData
		)
		.then((response) => response.data);
};

//get all posts
const loadAllPosts = (pageNumber, pageSize) => {
	return axiosProvider
		.get(
			`/posts?pageNumber=${pageNumber}&pageSize=${pageSize}&sortBy=addedDate&sortDir=desc`
		)
		.then((response) => response.data);
};

// load single post of given id
const loadPost = (postId) => {
	return axiosProvider
		.get(`/posts/${postId}`)
		.then((response) => response.data);
};

//comment
const createComment = (comment, postId) => {
	return privateAxios.post(`/post/${postId}/comments`, comment);
};

//upload post banner image
const uploadPostImage = (image, postId) => {
	let formData = new FormData();
	formData.append("image", image);
	return privateAxios
		.post(`/post/image/upload/${postId}`, formData, {
			header: {
				"Content-Type": "multipart/form-data",
			},
		})
		.then((response) => response.data);
};

//get category wise posts
const loadPostByCategory = (categoryId) => {
	return privateAxios
		.get(`/category/${categoryId}/posts`)
		.then((response) => response.data);
};

const loadPostsByUser = (userId) => {
	return privateAxios
		.get(`/user/${userId}/posts`)
		.then((response) => response.data);
};

const deletePostService = (postId) => {
	return privateAxios
		.delete(`posts/${postId}`)
		.then((response) => response.data);
};

//update post
const updatePost = (post, postId) => {
	return privateAxios
		.put(`/posts/${postId}`, post)
		.then((response) => response.data);
};

export {
	createPost,
	loadAllPosts,
	loadPost,
	createComment,
	uploadPostImage,
	loadPostByCategory,
	loadPostsByUser,
	deletePostService,
	updatePost,
};
