import JoditEditor from "jodit-react";
import React, { useContext } from "react";
import { useState } from "react";
import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";
import {
	Button,
	Card,
	CardBody,
	Container,
	Form,
	Input,
	Label,
} from "reactstrap";
import Base from "../components/Base";
import userContext from "../context/userContext";
import { loadPost, updatePost } from "../services/post-service";
import loadAllCategories from "../services/category-service";
import { useRef } from "react";

function UpdateBlog() {
	const { blogId } = useParams();
	const object = useContext(userContext);

	const navigate = useNavigate();
	const [post, setPost] = useState(null);
	const [categories, setCategories] = useState([]);

	const editor = useRef(null);

	useEffect(() => {
		loadAllCategories()
			.then((data) => {
				setCategories(data);
			})
			.catch((error) => {
				console.log(error);
			});

		// load the blog from databsase
		loadPost(blogId)
			.then((data) => {
				setPost({ ...data, categoryId: data.category.categoryId });
			})
			.catch((error) => {
				console.log(error);
				toast.error("Error in loading the blog");
			});
	}, []);

	useEffect(() => {
		// if(!post){
		if (post && object.user.data && post.user.id !== object.user.data.user.id) {
			toast.error("This is not your post");
			navigate("/");
		}
		// }
	}, []);

	const resetEditor = () => {
		setPost({
			title: "",
			content: "",
			categoryId: "",
		});
	};

	const handleChange = (event, fieldName) => {
		setPost({
			...post,
			[fieldName]: event.target.value,
		});
	};

	const updateBlogPost = (event) => {
		event.preventDefault();
		updatePost(
			{ ...post, category: { categoryId: post.categoryId } },
			post.postId
		)
			.then((response) => {
				toast.success("Post updated");
			})
			.catch((error) => {
				console.log(error);
				toast.error("Error in updating post");
			});
	};

	const updateHtml = () => {
		return (
			<div className="wrapper">
				<Card className="shadow mt-3">
					<CardBody>
						<h3>Update post from here</h3>
						<Form onSubmit={updateBlogPost}>
							<div className="my-3">
								<Label for="title">Post Title</Label>
								<Input
									type="text"
									id="title"
									placeholder="Enter Here"
									onChange={(event) => handleChange(event, "title")}
									name="title"
									value={post.title}
								/>
							</div>

							<div className="my-3">
								<Label for="content">Post Content</Label>
								<JoditEditor
									ref={editor}
									value={post.content}
									onChange={(newContent) =>
										setPost({ ...post, content: newContent })
									}
									// config={config}
								/>
							</div>

							{/* image file field */}
							<div className="mt-3">
								<Label for="image">Select Post Banner</Label>
								<Input type="file" id="image" onChange={""} />
							</div>

							<div className="my-3">
								<Label for="category">Post Category</Label>
								<Input
									type="select"
									id="category"
									placeholder="Enter Here"
									name="categoryId"
									onChange={(event) => handleChange(event, "categoryId")}
									defaultValue={0}
									value={post.categoryId}
								>
									<option value={0} disabled>
										---Select Category---
									</option>
									{categories?.map((category) => {
										return (
											<option
												value={category.categoryId}
												key={category.categoryId}
											>
												{category.categoryTitle}
											</option>
										);
									})}
								</Input>
							</div>

							<Container className="text-center">
								<Button type="submit" className="ms-2" color="dark">
									Update Post
								</Button>
								<Button
									type="reset"
									className="ms-2"
									color="secondary"
									onClick={resetEditor}
								>
									Reset Content
								</Button>
							</Container>
						</Form>
					</CardBody>
				</Card>
			</div>
		);
	};

	return (
		<Base>
			<Container>{post && updateHtml()}</Container>
		</Base>
	);
}

export default UpdateBlog;
