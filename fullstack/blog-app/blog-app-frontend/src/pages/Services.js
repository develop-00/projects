import Base from "../components/Base";

import userContext from "../context/userContext";

const Services = () => {
	return (
		<userContext.Consumer>
			{(object) => (
				<Base>
					<h1>This is service page</h1>
					<h3>
						Welcome to services{" "}
						{object.user.isLoggedIn && object.user.data.user.name}
					</h3>
				</Base>
			)}
		</userContext.Consumer>
	);
};

export default Services;
