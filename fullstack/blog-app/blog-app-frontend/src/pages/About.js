import Base from "../components/Base";
import userContext from "../context/userContext";

const About = () => {
	return (
		<userContext.Consumer>
			{(object) => (
				<Base>
					<h1>This is about page</h1>
					<p>We are buildinng blog website</p>
					{/* <h3>
						Welcome user: {object.user.isLoggedIn && object.data.user.name}
					</h3> */}
				</Base>
			)}
		</userContext.Consumer>
	);
};

export default About;
