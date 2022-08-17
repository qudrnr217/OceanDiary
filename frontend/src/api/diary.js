import { authApiInstance } from "@/api/index.js";

async function getStamp(token, enterTime, exitTime, category, success, fail) {
  const api = authApiInstance(token);
  const data = {
    enterTime: enterTime,
    exitTime: exitTime,
    category: category,
  };
  await api
    .post(`/api/diary/stamp`, JSON.stringify(data))
    .then(success)
    .catch(fail);
}

export { getStamp };
