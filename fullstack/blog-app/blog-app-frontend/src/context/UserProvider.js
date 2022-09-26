import React, { useState } from "react";
import { useEffect } from "react";
import { getCurrentUserDetail, isLoggedIn } from "../auth";

import UserContext from "./userContext";

function UserProvider({ children }) {
	const [user, setUser] = useState({
		data: {},
		isLoggedIn: false,
	});

	useEffect(() => {
		setUser({
			data: getCurrentUserDetail(),
			isLoggedIn: isLoggedIn(),
		});
	}, []);

	return (
		<UserContext.Provider value={{ user, setUser }}>
			{children}
		</UserContext.Provider>
	);
}

export default UserProvider;
