import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Base from "../components/Base";
import { signup } from "../services/user-service";
import { toast } from "react-toastify";
import {
	Card,
	CardHeader,
	Container,
	Label,
	CardBody,
	Form,
	FormGroup,
	Input,
	Button,
	Row,
	Col,
	FormFeedback,
} from "reactstrap";

const Signup = () => {
	const navigate = useNavigate();
	const [data, setData] = useState({
		name: "",
		email: "",
		password: "",
		about: "",
	});

	const [error, setError] = useState({
		errors: {},
		isError: false,
	});

	//handle change
	const handleChange = (event, property) => {
		setData({ ...data, [property]: event.target.value });
	};

	//resetting the form
	const resetData = () => {
		setData({
			name: "",
			email: "",
			password: "",
			about: "",
		});
	};

	//submit the form
	const submitForm = (event) => {
		event.preventDefault();

		// if (error.isError) {
		//     toast.error("Form data is invalid");
		//     setError({...error, isError: false});
		//     return;
		// }
		console.log(data);

		//data validation to be implemented

		//call server api for sending data
		signup(data)
			.then((resp) => {
				console.log(resp);
				console.log("success log");
				toast.success("User is registered successfully. User id: " + resp.id);
				setData({
					name: "",
					email: "",
					password: "",
					about: "",
				});
				navigate("/login");
			})
			.catch((error) => {
				console.log(error);
				console.log("Error log");

				//handle errors
				setError({
					errors: error,
					isError: true,
				});
			});
	};

	return (
		<Base>
			<Container>
				<Row className="mt-4">
					<Col sm={{ size: 6, offset: 3 }}>
						<Card color="dark" outline>
							<CardHeader>
								<h3>Fill Information to Register</h3>
							</CardHeader>
							<CardBody>
								{/* creating form */}

								<Form onSubmit={submitForm}>
									{/* Name Field */}
									<FormGroup>
										<Label for="name">Enter Name: </Label>
										<Input
											type="text"
											placeholder="Enter Here"
											id="name"
											onChange={(event) => handleChange(event, "name")}
											value={data.name}
											invalid={
												error.errors?.response?.data?.name ? true : false
											}
										/>

										<FormFeedback>
											{error.errors?.response?.data?.name}
										</FormFeedback>
									</FormGroup>

									{/* Email Field */}
									<FormGroup>
										<Label for="email">Enter Email: </Label>
										<Input
											type="email"
											placeholder="Enter Here"
											id="email"
											onChange={(event) => handleChange(event, "email")}
											value={data.email}
											invalid={
												error.errors?.response?.data?.email ? true : false
											}
										/>

										<FormFeedback>
											{error.errors?.response?.data?.email}
										</FormFeedback>
									</FormGroup>

									{/* Password Field */}
									<FormGroup>
										<Label for="password">Enter Password: </Label>
										<Input
											type="password"
											placeholder="Enter Here"
											id="password"
											onChange={(event) => handleChange(event, "password")}
											value={data.password}
											invalid={
												error.errors?.response?.data?.password ? true : false
											}
										/>

										<FormFeedback>
											{error.errors?.response?.data?.password}
										</FormFeedback>
									</FormGroup>

									{/* About Field */}
									<FormGroup>
										<Label for="about">Enter About: </Label>
										<Input
											type="textarea"
											placeholder="Enter Here"
											id="about"
											style={{ height: "250px" }}
											onChange={(event) => handleChange(event, "about")}
											value={data.about}
											invalid={
												error.errors?.response?.data?.about ? true : false
											}
										/>

										<FormFeedback>
											{error.errors?.response?.data?.about}
										</FormFeedback>
									</FormGroup>

									<Container className="text-center">
										<Button color="dark">Register</Button>
										<Button
											color="secondary"
											type="reset"
											className="ms-2"
											onClick={resetData}
										>
											Reset
										</Button>
									</Container>
								</Form>
							</CardBody>
						</Card>
					</Col>
				</Row>
			</Container>
		</Base>
	);
};

export default Signup;
