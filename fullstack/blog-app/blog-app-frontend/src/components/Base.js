import CustomNavbar from "./CustomNavbar";

const Base = ({ title = "Welcome to our website", children }) => {
	return (
		<div className="container-fluid p-0 m-0">
			<CustomNavbar color="dark" dark expand="md" fixed="" />
			{children}
			<p>This is a footer</p>
		</div>
	);
};

export default Base;
