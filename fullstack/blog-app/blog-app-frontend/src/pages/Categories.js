import React from "react";
import { useState } from "react";
import { useEffect } from "react";
import { useParams } from "react-router-dom";
import { toast } from "react-toastify";
import { Col, Container, Row } from "reactstrap";
import Base from "../components/Base";
import CategorySideMenu from "../components/CategorySideMenu";
import NewFeed from "../components/NewFeed";
import Post from "../components/Post";
import {
	deletePostService,
	loadPostByCategory,
} from "../services/post-service";

function Categories() {
	const [posts, setPosts] = useState([]);
	const { categoryId } = useParams();

	useEffect(() => {
		loadPostByCategory(categoryId)
			.then((data) => {
				setPosts([...data]);
			})
			.catch((error) => {
				console.log(error);
				toast.error("Error in loading posts");
			});
	}, [categoryId]);

	const deletePost = (post) => {
		deletePostService(post.postId)
			.then((res) => {
				console.log(res);
				toast.success("Post is deleted.");
				let newPosts = posts.filter((p) => p.postId !== post.postId);
				setPosts([...newPosts]);
			})
			.catch((error) => {
				console.log(error);
				toast.error("Post can not be deleted");
			});
	};

	return (
		<Base>
			<Container className="mt-3">
				<Row>
					<Col md={2} className="pt-5">
						<CategorySideMenu />
					</Col>
					<Col md={10}>
						{posts.length <= 0 ? (
							<h2>No post in the category</h2>
						) : (
							<h2>Blogs Count: {posts?.length}</h2>
						)}
						{posts &&
							posts.map((post, index) => {
								return <Post post={post} key={index} deletePost={deletePost} />;
							})}
					</Col>
				</Row>
			</Container>
		</Base>
	);
}

export default Categories;
