import { apiInstance } from "@/api/index.js";
import { userStore } from "@/store/modules/userStore.js";
const api = apiInstance();

async function getRoomList(category, lastRoomId, success, fail) {
  const token = userStore.token;
  if (token == "") return null;
  api.headers = { Authorization: token };
  await api
    .get(`/rooms?category=${category}&lastRoomId=${lastRoomId}`)
    .then(success)
    .catch(fail);
}

async function createRoom(roomInfo, imageFile, success, fail) {
  const token = userStore.token;
  if (token == "") return null;
  api.headers = { Authorization: token };
  const data = {
    form: roomInfo,
    file: imageFile,
  };
  await api.post(`/rooms`, JSON.stringify(data)).then(success).catch(fail);
}
export { getRoomList, createRoom };
