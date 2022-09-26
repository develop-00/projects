import React, { useState, useEffect } from "react";
import { deletePostService, loadAllPosts } from "../services/post-service";
import {
	Row,
	Col,
	Pagination,
	PaginationItem,
	PaginationLink,
	Container,
} from "reactstrap";
import Post from "./Post";
import { toast } from "react-toastify";
import InfiniteScroll from "react-infinite-scroll-component";

const NewFeed = () => {
	const [postContent, setPostContent] = useState({
		content: [],
		totalPages: "",
		totalElements: "",
		pageSize: "",
		pageNumber: "",
		isLastPage: false,
	});

	const [currentPage, setCurrentPage] = useState(0);

	useEffect(() => {
		//load all the post from server
		changePage(currentPage);
	}, [currentPage]);

	const changePage = (pageNumber = 0, pageSize = 5) => {
		// stop next if at last page
		if (pageNumber > postContent.pageNumber && postContent.isLastPage) {
			return;
		}

		// stop previous if at first page
		if (pageNumber < postContent.pageNumber && postContent.pageNumber === 0) {
			return;
		}

		loadAllPosts(pageNumber, pageSize)
			.then((data) => {
				setPostContent({
					content: [...postContent.content, ...data.content],
					totalPages: data.totalPages,
					totalElements: data.totalElements,
					pageSize: data.pageSize,
					pageNumber: data.pageNumber,
					isLastPage: data.isLastPage,
				});
				window.scroll(0, 0);
			})
			.catch((error) => {
				toast.error("Error in loading posts.");
			});
	};

	const deletePost = (post) => {
		deletePostService(post.postId)
			.then((res) => {
				console.log(res);
				toast.success("Post is deleted.");

				let newPostContents = postContent.content.filter(
					(p) => p.postId !== post.postId
				);
				setPostContent({ ...postContent, content: newPostContents });
			})
			.catch((error) => {
				console.log(error);
				toast.error("Post can not be deleted");
			});
	};

	const changePageInfinte = () => {
		setCurrentPage(currentPage + 1);
	};

	return (
		<div className="container-fluid">
			<Row>
				<Col md={{ size: 12 }}>
					<h2>Blogs Count: {postContent?.totalElements}</h2>

					<InfiniteScroll
						dataLength={postContent.content.length}
						next={changePageInfinte}
						hasMore={!postContent.isLastPage}
						loader={<h4>Loading...</h4>}
						endMessage={
							<p style={{ textAlign: "center" }}>
								<b>Yay! You have seen it all</b>
							</p>
						}
					>
						{postContent?.content.map((post) => (
							<Post deletePost={deletePost} post={post} key={post.postId} />
						))}
					</InfiniteScroll>

					{/* <Container className="mt-3">
						<Pagination>
							<PaginationItem
								onClick={() => changePage(postContent.pageNumber - 1)}
								disabled={postContent.pageNumber === 0}
							>
								<PaginationLink previous>Previous</PaginationLink>
							</PaginationItem>

							{[...Array(postContent.totalPages)].map((item, index) => {
								return (
									<PaginationItem
										active={index === postContent.pageNumber}
										key={index}
										onClick={() => changePage(index)}
									>
										<PaginationLink>{index + 1}</PaginationLink>
									</PaginationItem>
								);
							})}

							<PaginationItem
								onClick={() => changePage(postContent.pageNumber + 1)}
								disabled={postContent.isLastPage}
							>
								<PaginationLink next>Next</PaginationLink>
							</PaginationItem>
						</Pagination>
					</Container> */}
				</Col>
			</Row>
		</div>
	);
};

export default NewFeed;
