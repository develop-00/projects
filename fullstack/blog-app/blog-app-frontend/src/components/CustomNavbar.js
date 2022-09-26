import React, { useEffect, useState } from "react";
import { useContext } from "react";
import { NavLink as ReactLink, useNavigate } from "react-router-dom";

import {
	Collapse,
	Navbar,
	NavbarToggler,
	NavbarBrand,
	Nav,
	NavItem,
	NavLink,
	UncontrolledDropdown,
	DropdownToggle,
	DropdownMenu,
	DropdownItem,
} from "reactstrap";
import { getCurrentUserDetail, isLoggedIn } from "../auth";
import { doLogout } from "../auth";
import userContext from "../context/userContext";

const CustomNavbar = (args) => {
	const userContextData = useContext(userContext);
	let navigate = useNavigate();

	const [isOpen, setIsOpen] = useState(false);

	const [login, setLogin] = useState(false);

	const [user, setuser] = useState(undefined);

	useEffect(() => {
		setLogin(isLoggedIn());
		setuser(getCurrentUserDetail());
	}, [login]);

	const toggle = () => setIsOpen(!isOpen);

	const logout = () => {
		doLogout(() => {
			//logged out
			setLogin(false);
			userContextData.setUser({
				data: "",
				isLoggedIn: false,
			});
			navigate("/");
		});
	};

	return (
		<div>
			<Navbar {...args} className="px-5">
				<NavbarBrand tag={ReactLink} to="/">
					MyBlogs
				</NavbarBrand>
				<NavbarToggler onClick={toggle} />
				<Collapse isOpen={isOpen} navbar>
					<Nav className="me-auto" navbar>
						<NavItem>
							<NavLink tag={ReactLink} to="/">
								New Feed
							</NavLink>
						</NavItem>
						<NavItem>
							<NavLink tag={ReactLink} to="/about">
								About
							</NavLink>
						</NavItem>
						<NavItem>
							<NavLink tag={ReactLink} to="/services">
								Services
							</NavLink>
						</NavItem>

						<UncontrolledDropdown nav inNavbar>
							<DropdownToggle nav caret>
								More
							</DropdownToggle>
							<DropdownMenu right>
								<DropdownItem>Contact</DropdownItem>
								<DropdownItem>LinkedIn</DropdownItem>
								<DropdownItem divider />
								<DropdownItem>Github</DropdownItem>
								<DropdownItem>Youtube</DropdownItem>
								<DropdownItem>Facebook</DropdownItem>
								<DropdownItem>Instagram</DropdownItem>
							</DropdownMenu>
						</UncontrolledDropdown>
					</Nav>

					<Nav navbar>
						{login && (
							<>
								<NavItem>
									<NavLink tag={ReactLink} to="/user/profile-info">
										Profile
									</NavLink>
								</NavItem>
								<NavItem>
									<NavLink tag={ReactLink} to="/user/dashboard">
										{user.name}
									</NavLink>
								</NavItem>
								<NavItem>
									<NavLink onClick={logout}>Logout</NavLink>
								</NavItem>
							</>
						)}

						{!login && (
							<>
								<NavItem>
									<NavLink tag={ReactLink} to="/login">
										Login
									</NavLink>
								</NavItem>

								<NavItem>
									<NavLink tag={ReactLink} to="/signup">
										Signup
									</NavLink>
								</NavItem>
							</>
						)}
					</Nav>
				</Collapse>
			</Navbar>
		</div>
	);
};

export default CustomNavbar;
