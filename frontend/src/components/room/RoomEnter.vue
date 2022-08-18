<template>
  <div class="center">
    <div class="box main-box">
      <div class="box-header">
        <div class="logo">
          <img src="" ref="icon" class="icon-symbol" />
          {{ item.title }}
        </div>
        <div class="numbers">● {{ item.curNum }} / {{ item.maxNum }}</div>
      </div>
      <div class="box-list">
        <img :src="imageFile" class="thumbnail" />
        <div class="rules">{{ item.rule }}</div>
        <input
          v-if="!item.isOpen"
          type="password"
          v-model="inputPassword"
          placeholder="비밀번호 4자리"
          id="password-input"
        />
        <div class="room-create-button">
          <div class="button-next" @click="enter()">입 장</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// 기능 1. 방 상세정보를 띄운다.
// 기능 2. 잠겨있다면, 비밀번호를 입력 받는다.
// 기능 3. 입장 시 필요한 처리를 수행하고 화면을 넘긴다.
// 방 상세정보를 넘겨 받는다.
import { ref, reactive } from "vue";
import { getImageFile, getRoomInfo, joinRoom } from "@/api/webrtc.js";
import { icons, indexes } from "@/const/const.js";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    let inputPassword = ref("");
    const urlParams = new URL(location.href).searchParams;
    const roomId = urlParams.get("roomId");

    const item = reactive({
      roomId: 1,
      sessionId: "",
      categoryId: "",
      createdBy: 1,
      title: "",
      rule: "",
      imageId: 1,
      maxNum: 1,
      curNum: 1,
      isOpen: true,
    });
    const imageFile = ref("");
    const iconPath = ref("");
    const icon = ref(null);
    getRoomInfo(
      roomId,
      (response) => {
        item.roomId = response.data.roomId;
        item.sessionId = response.data.sessionId;
        item.categoryId = response.data.categoryId;
        item.createdBy = response.data.createdBy;
        item.title = response.data.title;
        item.rule = response.data.rule;
        item.imageId = response.data.imageId;
        item.maxNum = response.data.maxNum;
        item.curNum = response.data.curNum;
        item.isOpen = response.data.isOpen;
        getImageFile(
          item.imageId,
          (response) => {
            imageFile.value = response.data.base64Str;
          },
          (error) => {
            console.log(error);
          }
        );
        iconPath.value = require(`@/assets/아이콘/${
          icons[indexes[item.categoryId.toLowerCase()]]
        }`);
        icon.value.src = iconPath.value;
      },
      (error) => {
        console.log(error);
      }
    );
    const enter = () => {
      console.log(inputPassword.value);
      if (!item.isOpen && inputPassword.value.length != 4) {
        alert("비밀번호 4자리를 입력하세요!");
        return;
      }
      joinRoom(
        store.state.userStore.token,
        store.state.userStore.name,
        store.state.userStore.social,
        item.roomId,
        inputPassword.value,
        (response) => {
          store.commit("roomStore/SET_ROOM_ID", item.roomId);
          store.commit(
            "roomStore/SET_PARTICIPANT_ID",
            response.data.participantId
          );
          store.commit("roomStore/SET_OPENVIDU_TOKEN", response.data.token);
          store.commit(
            "roomStore/SET_CONNECTION_ID",
            response.data.connectionId
          );
          router.push({
            name: "festival-room",
            query: { dest: item.categoryId },
          });
        },
        (error) => {
          alert("비밀번호가 맞지 않습니다!");
          console.log(error);
          console.log(store.state.userStore.social);
        }
      );
    };
    return {
      item,
      icon,
      imageFile,
      inputPassword,
      enter,
    };
  },
};
</script>

<style scoped>
.main-box {
  width: 800px;
  min-width: 800px;
  height: 500px;
  padding: 40px 80px;
}
.box-header {
  height: 15%;
  font-size: 50px;
}
.lock {
  display: flex;
  align-items: center;
  float: right;
}
.logo {
  display: flex;
  float: left;
}
.icon-symbol {
  width: 50px;
  height: 50px;
  margin-right: 20px;
}
.icon-lock {
  width: 50px;
  height: 50px;
}
.room-create-input {
  border: solid 2px #d1d8df;
  border-radius: 10px;
  width: 200px;
  height: 50px;
  font-size: 30px;
  font-family: "retro";
}
.room-create-form {
  width: 80%;
  text-align: left;
  font-size: 30px;
}
.room-create-form tr {
  padding-bottom: 20px;
}
.room-create-form th {
  padding-left: 20px;
  padding-top: 15px;
  display: flex;
  align-items: start;
}
.room-create-form .room-create-input {
  width: 100%;
}
.room-create-button {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 30px;
}
.thumbnail {
  height: 250px;
}
.numbers {
  float: right;
  font-size: 24px;
  display: flex;
  align-items: center;
}
.box-list {
  justify-content: center;
  text-align: center;
}
.rules {
  margin: 20px;
  font-size: 24px;
}
#password-input {
  width: 150px;
  height: 30px;
  font-size: large;
  font-family: "retro";
}
</style>
