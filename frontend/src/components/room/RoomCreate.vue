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
            @click="show = !show"
            v-if="show"
          />
          <input type="password" class="room-create-input" v-show="show" />
          <img
            src="@/assets/아이콘/[아이콘]방_공개.png"
            class="icon-lock"
            @click="show = !show"
            v-if="!show"
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
                class="room-create-input"
              />
            </td>
          </tr>
          <tr>
            <th>규칙 :</th>
            <td>
              <textarea class="room-create-input" style="height: 180px" />
            </td>
          </tr>
          <tr>
            <th>인원 :</th>
            <td>
              <input
                type="number"
                class="room-create-input"
                style="width: 30%"
              />
            </td>
          </tr>
          <tr>
            <th>이미지 :</th>
            <td>
              <div class="button-next" style="width: 30%">첨부파일</div>
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
import { useRouter } from "vue-router";
import { onMounted, reactive, ref } from "vue";
import { icons, indexes, types } from "@/const/const.js";
import { createRoom } from "@/api/webrtc.js";
export default {
  setup() {
    const router = useRouter();

    const urlParams = new URL(location.href).searchParams;
    const dest = urlParams.get("dest");
    const index = indexes[dest];
    const type = types[dest];
    const iconPath = require(`@/assets/아이콘/${icons[index]}`);

    const roomInfo = reactive({
      categoryId: "",
      title: "",
      rule: "",
      maxNum: 0,
      isOpen: false,
      pw: "1234",
    });
    roomInfo.categoryId = dest.toUpperCase();
    const imageFile = ref("");
    var show = ref(false);
    var icon = ref(null);

    const create = () => {
      createRoom(
        roomInfo,
        imageFile,
        () => {
          alert("방을 생성했습니다!");
          router.push({
            name: "lobby",
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
      show,
      iconPath,
      icon,
      type,
      create,
    };
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
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
