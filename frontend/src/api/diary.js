import { authApiInstance } from "@/api/index.js";

async function getDiaryContents(token, userId, success, fail) {
  const authApi = authApiInstance(token);
  await authApi
    .get(`/api/diary/user?userId=${userId}`)
    .then(success)
    .catch(fail);
}

export { getDiaryContents };
