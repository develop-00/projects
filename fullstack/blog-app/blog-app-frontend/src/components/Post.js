import React from "react";
import { useContext } from "react";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Button, Card, CardBody, CardText } from "reactstrap";
import { getCurrentUserDetail, isLoggedIn } from "../auth";
import userContext from "../context/userContext";

function Post({
	post = {
		id: -1,
		title: "This is default post title",
		content: "This is default post content",
	},
	deletePost,
}) {
	const [user, setUser] = useState({ id: "" });
	const [login, setLogin] = useState(false);
	const userContextData = useContext(userContext);

	useEffect(() => {
		setUser(getCurrentUserDetail());
		setLogin(isLoggedIn());
	}, []);

	return (
		<Card className="shadow-sm mt-3">
			<CardBody>
				<h2>{post.title}</h2>
				<CardText
					dangerouslySetInnerHTML={{
						__html: post.content.substring(0, 100).concat("..."),
					}}
				></CardText>
				<div>
					<Link className="btn btn-dark" to={`/posts/${post.postId}`}>
						Read More
					</Link>

					{userContextData.user.isLoggedIn &&
					user &&
					user.id === post.user.id ? (
						<Button
							onClick={() => deletePost(post)}
							color="danger"
							className="ms-2"
						>
							Delete
						</Button>
					) : (
						""
					)}

					{userContextData.user.isLoggedIn &&
					user &&
					user.id === post.user.id ? (
						<Button
							tag={Link}
							to={`/user/update-blog/${post.postId}`}
							color="info"
							className="ms-2"
						>
							Update Post
						</Button>
					) : (
						""
					)}
				</div>
			</CardBody>
		</Card>
	);
}

export default Post;
