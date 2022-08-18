import { authApiInstance } from "@/api/index.js";
import { apiInstance } from "@/api/index.js";

/* Without auth header */
const api = apiInstance();
async function getRoomList(category, lastRoomId, success, fail) {
  await api
    .get(`/api/rooms?category=${category}&lastRoomId=${lastRoomId}`)
    .then(success)
    .catch(fail);
}
// GET /api/rooms/{roomId}/info
async function getRoomInfo(roomId, success, fail) {
  await api.get(`/api/rooms/${roomId}/info`).then(success).catch(fail);
}
async function getImageFile(imageId, success, fail) {
  await api.get(`/api/image/${imageId}`).then(success).catch(fail);
}

async function joinRoom(token, name, social, roomId, password, success, fail) {
  if (social != "NONE") {
    const authApi = authApiInstance(token);
    await authApi
      .post(`/api/rooms/${roomId}`, JSON.stringify({ pw: password }))
      .then(success)
      .catch(fail);
  } else {
    await api
      .post(
        `/api/rooms/${roomId}?name=${name}`,
        JSON.stringify({ pw: password })
      )
      .then(success)
      .catch(fail);
  }
}

async function leaveRoom(token, social, roomId, participantId, success, fail) {
  if (social != "NONE") {
    const authApi = authApiInstance(token);
    await authApi
      .delete(`/api/rooms/${roomId}/participants/${participantId}`)
      .then(success)
      .catch(fail);
  } else {
    await api
      .delete(`/api/rooms/${roomId}/participants/${participantId}`)
      .then(success)
      .catch(fail);
  }
}
async function GetUserInfo(roomId, success, fail) {
  await api.get(`/api/rooms/${roomId}/detail`).then(success).catch(fail);
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
async function GetStamp(token, enterTime, exitTime, category, success, fail) {
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

export {
  getRoomList,
  getImageFile,
  joinRoom,
  leaveRoom,
  GetUserInfo,
  createRoom,
  GetStamp,
  getRoomInfo,
};
