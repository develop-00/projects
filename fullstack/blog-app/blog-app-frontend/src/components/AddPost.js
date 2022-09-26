import loadAllCategories from "../services/category-service";
import {
	Card,
	CardBody,
	Form,
	Input,
	Label,
	Container,
	Button,
} from "reactstrap";
import { useEffect, useRef, useState } from "react";
import JoditEditor from "jodit-react";
import { createPost, uploadPostImage } from "../services/post-service";
import { getCurrentUserDetail } from "../auth";
import { toast } from "react-toastify";

const AddPost = () => {
	const editor = useRef(null);
	// const [content, setContent] = useState("");

	const [categories, setCategories] = useState([]);

	const [user, setUser] = useState(undefined);
	// const config = useMemo({
	// 	placeholder: "Start Typing...",
	// });

	const [post, setPost] = useState({
		title: "",
		content: "",
		categoryId: "",
	});

	const [image, setImage] = useState(null);

	const fieldChanged = (event) => {
		setPost({ ...post, [event.target.name]: event.target.value });
	};

	const contentFieldChanged = (data) => {
		setPost({ ...post, content: data }); //content -> 'content' resetting on save.
	};

	useEffect(() => {
		setUser(getCurrentUserDetail());
		loadAllCategories()
			.then((data) => {
				setCategories(data);
			})
			.catch((error) => {
				console.log(error);
			});
	}, []);

	const submitPost = (event) => {
		event.preventDefault();

		if (post.title.trim() === "") {
			toast.error("Post title is required");
			return;
		}

		if (post.content.trim() === "") {
			toast.error("Post content is required");
			return;
		}

		if (post.categoryId.trim() === "") {
			toast.error("Select category.");
			return;
		}

		// submit the form on server.
		post["userId"] = user.id;
		createPost(post)
			.then((data) => {
				uploadPostImage(image, data.postId)
					.then((data) => {
						toast.success("Image uploaded");
					})
					.catch((error) => {
						toast.error("Error in uploading image");
						console.log(error);
					});
				toast.success("Post Created");
				resetEditor();
			})
			.catch((error) => {
				toast.error("Post not created due to some error");
				// console.log(error);
			});
	};

	const resetEditor = () => {
		setPost({
			title: "",
			content: "",
			categoryId: "",
		});
	};

	//handling file change event
	const handleFileChange = (event) => {
		console.log(event.target.files[0]);
		setImage(event.target.files[0]);
	};

	return (
		<div className="wrapper">
			<Card className="shadow mt-3">
				<CardBody>
					<h3>Whats going on your mind ?</h3>
					<Form onSubmit={submitPost}>
						<div className="my-3">
							<Label for="title">Post Title</Label>
							<Input
								type="text"
								id="title"
								placeholder="Enter Here"
								onChange={fieldChanged}
								name="title"
							/>
						</div>

						<div className="my-3">
							<Label for="content">Post Content</Label>
							<JoditEditor
								ref={editor}
								value={post.content}
								onChange={(newContent) => contentFieldChanged(newContent)}
								// config={config}
							/>
						</div>

						{/* image file field */}
						<div className="mt-3">
							<Label for="image">Select Post Banner</Label>
							<Input type="file" id="image" onChange={handleFileChange} />
						</div>

						<div className="my-3">
							<Label for="category">Post Category</Label>
							<Input
								type="select"
								id="category"
								placeholder="Enter Here"
								name="categoryId"
								onChange={fieldChanged}
								defaultValue={0}
							>
								<option value={0} disabled>
									---Select Category---
								</option>
								{categories.map((category) => {
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
								Create Post
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

export default AddPost;
