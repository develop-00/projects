import { useContext } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
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
} from "reactstrap";
import { doLogin } from "../auth";
import Base from "../components/Base";
import userContext from "../context/userContext";
import { loginUser } from "../services/user-service";

const Login = () => {
	const userContextData = useContext(userContext);

	const navigate = useNavigate();
	const [loginDetail, setLoginDetail] = useState({
		username: "",
		password: "",
	});

	const handleChange = (event, field) => {
		setLoginDetail({ ...loginDetail, [field]: event.target.value });
	};

	const handleReset = () => {
		setLoginDetail({
			username: "",
			password: "",
		});
	};

	const handleFormSubmit = (event) => {
		event.preventDefault();
		console.log(loginDetail);

		//data validation
		if (
			loginDetail.username.trim() === "" ||
			loginDetail.password.trim() === ""
		) {
			toast.error("Username and Password is required");
			return;
		}

		//submit data to server to generate token
		loginUser(loginDetail)
			.then((data) => {
				//save the data to local storage
				doLogin(data, () => {
					console.log("login detail is saved to local storage");

					userContextData.setUser({
						data: data.user,
						isLoggedIn: true,
					});

					//redirect to user dashboard page

					navigate("/user/dashboard");
				});
				console.log(data);
				toast.success("Login successful");
			})
			.catch((error) => {
				console.log(error);
				if (error.status === 400 || error.status === 404) {
					toast.error(error.response.data.message);
				} else {
					toast.error("Something went wrong");
				}
			});
	};

	return (
		<Base>
			<Container>
				<Row className="mt-4">
					<Col sm={{ size: 6, offset: 3 }}>
						<Card color="dark" outline>
							<CardHeader>
								<h3>Login Here</h3>
							</CardHeader>
							<CardBody>
								{/* creating form */}

								<Form onSubmit={handleFormSubmit}>
									{/* Email Field */}
									<FormGroup>
										<Label for="email">Enter Email: </Label>
										<Input
											type="email"
											placeholder="Enter Here"
											id="email"
											value={loginDetail.username}
											onChange={(event) => handleChange(event, "username")}
										/>
									</FormGroup>

									{/* Password Field */}
									<FormGroup>
										<Label for="password">Enter Password: </Label>
										<Input
											type="password"
											placeholder="Enter Here"
											id="password"
											value={loginDetail.password}
											onChange={(event) => handleChange(event, "password")}
										/>
									</FormGroup>

									<Container className="text-center">
										<Button color="dark">Login</Button>
										<Button
											color="secondary"
											type="reset"
											className="ms-2"
											onClick={handleReset}
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

export default Login;
