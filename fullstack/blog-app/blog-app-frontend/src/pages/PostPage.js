import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { toast } from "react-toastify";
import {
	Button,
	Card,
	CardBody,
	CardText,
	Col,
	Container,
	Input,
	Row,
} from "reactstrap";
import Base from "../components/Base";
import { createComment, loadPost } from "../services/post-service";
import { BASE_URL } from "../services/helper";
import { isLoggedIn } from "../auth";

const PostPage = () => {
	const { postId } = useParams();
	const [post, setPost] = useState(null);
	const [comment, setComment] = useState({
		content: "",
	});

	useEffect(() => {
		//load post from server
		loadPost(postId)
			.then((data) => {
				//console.log(data);
				setPost(data);
			})
			.catch((error) => {
				console.log(error);
				toast.error("Error in loading post.");
			});
	}, []);

	const printDate = (numbers) => {
		return new Date(numbers).toDateString();
	};

	const submitComment = () => {
		if (!isLoggedIn()) {
			toast.error("You need to login first.");
			return;
		}
		if (comment.content.trim() === "") {
			return;
		}
		createComment(comment, post.postId)
			.then((data) => {
				console.log(data);
				toast.success("Comment added!!!");
				setPost({
					...post,
					comments: [...post.comments, data.data],
				});
				setComment({
					content: "",
				});
			})
			.catch((error) => {
				console.log(error);
			});
	};

	return (
		<Base>
			<Container className="mt-4">
				<Link to="/">Home</Link> / {post && <Link to="/">{post.title}</Link>}
				<Row>
					<Col md={{ size: 12 }}>
						<Card className="mt-3 ps-2 ">
							{post && (
								<CardBody>
									<CardText>
										Posted by <b>{post.user.name}</b> on
										<b> {printDate(post.addedDate)}</b>
									</CardText>

									<CardText>
										<span className="text-muted">
											{post.category.categoryTitle}
										</span>
									</CardText>

									<div
										className="divider"
										style={{
											width: "100%",
											height: "1px",
											background: "#e2e2e2",
										}}
									></div>

									<CardText className="mt-3">
										<h2>{post.title}</h2>
									</CardText>

									<div
										className="image-conatiner mt-5 container text-center px-0 shadow"
										style={{ maxWidth: "75%" }}
									>
										<img
											className="img-fluid"
											src={`${BASE_URL}/post/image/${post.imageName}`}
											alt=""
										/>
									</div>

									<CardText
										className="mt-5"
										dangerouslySetInnerHTML={{ __html: post.content }}
									></CardText>
								</CardBody>
							)}
						</Card>
					</Col>
				</Row>
				<Row className="my-3">
					<Col md={{ size: 9, offset: 1 }}>
						<h3>Comments ({post ? post.comments.length : 0})</h3>

						{/* create comment */}
						<Card className="mt-3">
							<CardBody>
								<Input
									type="textarea"
									placeholder="Enter comment here"
									value={comment.content}
									onChange={(event) => {
										setComment({ content: event.target.value });
									}}
								/>
								<Button onClick={submitComment} color="dark" className="mt-3 ">
									Submit
								</Button>
							</CardBody>
						</Card>

						{/* show comments */}
						{post &&
							post.comments &&
							post.comments.map((comment, index) => {
								return (
									<Card className="mt-3" key={index}>
										<CardBody>
											<CardText>{comment.content}</CardText>
										</CardBody>
									</Card>
								);
							})}
					</Col>
				</Row>
			</Container>
		</Base>
	);
};

export default PostPage;
