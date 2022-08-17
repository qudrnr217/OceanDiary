<template>
  <div class="center">
    <div class="box main-box">
      <div class="box-header">
        <div class="logo">
          <img src="" ref="icon" class="icon-symbol" />
          {{ type }} 생성
        </div>
        <div class="lock">
          <img
            src="@/assets/아이콘/icon_lock.png"
            class="icon-lock"
            @click="roomInfo.isOpen = !roomInfo.isOpen"
            v-if="!roomInfo.isOpen"
          />
          <input
            type="password"
            class="room-create-input"
            placeholder="비밀번호 4자리"
            v-model="roomInfo.pw"
            v-show="!roomInfo.isOpen"
          />
          <img
            src="@/assets/아이콘/[아이콘]방_공개.png"
            class="icon-lock"
            @click="roomInfo.isOpen = !roomInfo.isOpen"
            v-if="roomInfo.isOpen"
          />
        </div>
      </div>
      <div class="box-list">
        <table class="room-create-form">
          <tr>
            <th>제목 :</th>
            <td>
              <input
                v-model="roomInfo.title"
                type="text"
                placeholder="제목을 입력하세요"
                class="room-create-input"
              />
            </td>
          </tr>
          <tr>
            <th>규칙 :</th>
            <td>
              <textarea
                v-model="roomInfo.rule"
                placeholder="규칙을 입력하세요"
                class="room-create-input"
                style="height: 180px"
              />
            </td>
          </tr>
          <tr>
            <th>인원 :</th>
            <td>
              <select
                v-model="roomInfo.maxNum"
                type="number"
                class="room-create-input"
                style="width: 30%"
              >
                <option v-for="n in 5" :value="n + 1" :key="n + 1">
                  {{ n + 1 }}
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <th>이미지 :</th>
            <td>
              <input type="file" style="width: 100%" @change="fileInput" />
            </td>
          </tr>
        </table>
        <div class="room-create-button">
          <div class="button-metro" @click="create()">생 성</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { onMounted, reactive, ref } from "vue";
import { icons, indexes, types } from "@/const/const.js";
import { createRoom } from "@/api/webrtc.js";
export default {
  setup() {
    const router = useRouter();
    const store = useStore();
    console.log("현재 저장된 토큰 : " + store.state.userStore.token);
    const urlParams = new URL(location.href).searchParams;
    const dest = urlParams.get("dest");
    const index = indexes[dest];
    const type = types[dest];
    const numSelect = [2, 3, 4, 5, 6];
    const iconPath = require(`@/assets/아이콘/${icons[index]}`);

    const roomInfo = reactive({
      categoryId: "",
      title: "",
      rule: "",
      maxNum: 0,
      isOpen: true,
      pw: "",
    });
    roomInfo.categoryId = dest.toUpperCase();
    const imageFile = ref("");
    var icon = ref(null);
    const fileInput = (event) => {
      imageFile.value = event.target.files[0];
    };
    const isFormDataValid = () => {
      // 비밀번호 4자리
      if (!roomInfo.isOpen && roomInfo.pw.length != 4) {
        alert("비밀번호는 4자리입니다.");
        return false;
      }
      // 빈 제목
      if (roomInfo.title == "") {
        alert("제목을 입력해주세요.");
        return false;
      }
      // 인원 선택 여부
      if (roomInfo.maxNum == "") {
        alert("최대 인원을 선택해주세요.");
        return false;
      }
      // 이미지 파일 첨부 여부
      if (imageFile.value == "") {
        alert("대표 이미지를 첨부해주세요.");
        return false;
      }

      return true;
    };
    const create = () => {
      console.log("방 생성 절차를 시작합니다.");
      if (!isFormDataValid()) return;
      createRoom(
        store.state.userStore.token,
        roomInfo,
        imageFile.value,
        (response) => {
          alert("방을 생성했습니다!");
          store.commit("roomStore/SET_ROOM_ID", response.data.roomId);
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
            query: { dest: dest },
          });
        },
        (error) => {
          console.log(error);
        }
      );
    };
    onMounted(() => {
      icon.value.src = iconPath;
    });
    return {
      roomInfo,
      iconPath,
      icon,
      type,
      create,
      fileInput,
      numSelect,
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
</style>
