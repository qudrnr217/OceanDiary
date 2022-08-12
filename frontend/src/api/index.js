import axios from "axios";

const API_BASE_URL = "https://i7a406.p.ssafy.io";

function apiInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}
export { apiInstance };
