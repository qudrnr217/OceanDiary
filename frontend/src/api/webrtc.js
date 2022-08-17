import { authApiInstance } from "@/api/index.js";
import { apiInstance } from "@/api/index.js";

/* Without auth header */
const api = apiInstance();

// 방 목록
async function getRoomList(category, lastRoomId, success, fail) {
  await api
    .get(`/api/rooms?category=${category}&lastRoomId=${lastRoomId}`)
    .then(success)
    .catch(fail);
}
// 방 이미지 파일
async function getImageFile(imageId, success, fail) {
  await api.get(`/api/image/${imageId}`).then(success).catch(fail);
}
// 방 입장
async function joinRoom(roomId, password, success, fail) {
  await api
    .post(`/api/rooms/${roomId}`, JSON.stringify({ pw: password }))
    .then(success)
    .catch(fail);
}
// 방 퇴장
async function leaveRoom(roomId, participantId, success, fail) {
  await api
    .delete(`/api/rooms/${roomId}/participants/${participantId}`)
    .then(success)
    .catch(fail);
}
// 방 참여자 정보
async function getUserInfo(roomId, success, fail) {
  await api.get(`/api/rooms/${roomId}/detail`).then(success).catch(fail);
}

/* With auth header */
// 퇴장 시 서비스 이용 로그 생성
async function createLog(token, enterTime, exitTime, category, success, fail) {
  const authApi = authApiInstance(token);
  const data = {
    enterTime: enterTime,
    exitTime: exitTime,
    category: category,
  };
  await authApi
    .post(`/api/diary/stamp`, JSON.stringify(data))
    .then(success)
    .catch(fail);
}
// 방 생성
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

export {
  getRoomList,
  getImageFile,
  joinRoom,
  leaveRoom,
  getUserInfo,
  createLog,
  createRoom,
};
