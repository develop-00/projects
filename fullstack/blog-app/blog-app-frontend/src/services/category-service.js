import { axiosProvider } from "./helper";

const loadAllCategories = () => {
	return axiosProvider.get("/categories/").then((response) => {
		return response.data;
	});
};

export default loadAllCategories;
