import { axiosProvider } from "./helper";

export const signup = (user) => {
	return axiosProvider
		.post("/auth/register", user)
		.then((response) => response.data);
};

export const loginUser = (loginDetail) => {
	return axiosProvider
		.post("/auth/login", loginDetail)
		.then((response) => response.data);
};
