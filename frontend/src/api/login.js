import { apiInstance } from "@/api/index.js";

const api = apiInstance();

async function kakaoLogin(code, success, fail) {
  await api
    .post(`/api/kakao/login`, JSON.stringify(code))
    .then(success)
    .catch(fail);
}
async function naverState(success, fail) {
  await api.get(`/api/naver/state`).then(success).catch(fail);
}
async function naverLogin(code, success, fail) {
  await api
    .post(`/api/naver/login`, JSON.stringify(code))
    .then(success)
    .catch(fail);
}

async function signup(social, info, success, fail) {
  await api
    .post(`/api/${social}/signup`, JSON.stringify(info))
    .then(success)
    .catch(fail);
}

export { kakaoLogin, naverState, naverLogin, signup };
