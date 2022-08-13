import { authApiInstance } from "@/api/index.js";

async function getRoomList(token, category, lastRoomId, success, fail) {
  const authApi = authApiInstance(token);
  await authApi
    .get(`/api/rooms?category=${category}&lastRoomId=${lastRoomId}`)
    .then(success)
    .catch(fail);
}
async function createRoom(token, roomInfo, imageFile, success, fail) {
  const authApi = authApiInstance(token);
  const data = new FormData();
  data.append(
    "form",
    new Blob([JSON.stringify(roomInfo)], { type: "application/json" })
  );
  data.append("file", imageFile);
  await authApi.post(`/api/rooms`, data).then(success).catch(fail);
}
async function joinRoom(token, roomId, password, success, fail) {
  const authApi = authApiInstance(token);
  await authApi
    .post(`/api/rooms/${roomId}`, JSON.stringify({ pw: password }))
    .then(success)
    .catch(fail);
}
export { getRoomList, createRoom, joinRoom };
