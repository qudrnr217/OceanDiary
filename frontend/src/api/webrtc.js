import { authorizedApiInstance } from "@/api/index.js";
// import { useStore } from "vuex";
// const store = useStore();

async function getRoomList(token, category, lastRoomId, success, fail) {
  const authApi = authorizedApiInstance(token);
  if (token == "") return null;
  await authApi
    .get(`/api/rooms?category=${category}&lastRoomId=${lastRoomId}`)
    .then(success)
    .catch(fail);
}
async function createRoom(token, data, success, fail) {
  const authApi = authorizedApiInstance(token);
  if (token == "") return null;
  await authApi.post(`/api/rooms`, data).then(success).catch(fail);
}
async function joinRoom(token, data, success, fail) {
  const authApi = authorizedApiInstance(token);
  if (token == "") return null;
  await authApi.post(`/api/rooms`, data).then(success).catch(fail);
}
export { getRoomList, createRoom, joinRoom };
