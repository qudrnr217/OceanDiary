import axios from "axios";
import { API_BASE_URL } from "@/const/url.js";
// const API_BASE_URL = "https://i7a406.p.ssafy.io";

function apiInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}
function authApiInstance(token) {
  const instance = axios.create({
    headers: {
      "Content-type": "application/json",
      Authorization: token,
    },
  });
  return instance;
}
export { apiInstance, authApiInstance };
