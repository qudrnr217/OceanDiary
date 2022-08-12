<template>
  <div class="center">
    <div class="box main-box">
      <div class="box-header">
        <div class="logo">
          <img src="" ref="icon" class="icon-symbol" />
          {{ title }}
        </div>
        <img
          src="@/assets/아이콘/[아이콘]방생성.png"
          class="icon-create"
          alt="방생성"
          @click="create_room()"
        />
      </div>
      <div class="box-list">
        <div v-for="item of items" :key="item.roomId">
          <div class="room-card">
            <div class="room-card-thumbnail-wrap">
              <div class="room-card-thumbnail"></div>
            </div>
            <div class="room-card-text-wrap">
              <div class="room-card-title">
                {{ item.title }}
              </div>
            </div>
            <div class="room-card-button-wrap">
              <div ref="capacity" class="room-card-capacity">
                ● {{ item.curNum }} / {{ item.maxNum }}
              </div>
              <div ref="button" class="room-card-button">
                <div class="button-next" @click="enterRoom()">입장</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import { onMounted, ref } from "vue";
import { names, icons, indexes } from "@/const/const.js";
import axios from "axios";
import { useStore } from "vuex";
// import { getRoomList } from "@/api/webrtc.js";

export default {
  setup() {
    const router = useRouter();
    const store = useStore();

    const urlParams = new URL(location.href).searchParams;
    const dest = urlParams.get("dest");
    const index = indexes[dest];
    const title = names[index];
    const iconPath = require(`@/assets/아이콘/${icons[index]}`);
    // const items = getRoomList(dest, 0);
    const items = [
      {
        roomId: 1,
        imageId: 1,
        title: "방1입니다.",
        maxNum: 6,
        curNum: 1,
        isOpen: false,
      },
      {
        roomId: 2,
        imageId: 2,
        title: "방2입니다.",
        maxNum: 6,
        curNum: 2,
        isOpen: true,
      },
      {
        roomId: 3,
        imageId: 3,
        title: "방3입니다.",
        maxNum: 6,
        curNum: 2,
        isOpen: true,
      },
    ];
    const enterRoom = () => {
      axios({
        method: "post",
        url: `https://i7a406.p.ssafy.io/api/rooms/57`,
        headers: {
          Authorization: store.state.userStore.token,
        },
        data: {
          pw: "1234",
        },
      }).then((response) => {
        alert("방을 생성했습니다!");
        store.commit(
          "roomStore/SET_PARTICIPANT_ID",
          response.data.participantId
        );
        store.commit("roomStore/SET_OPENVIDU_TOKEN", response.data.token);
        store.commit("roomStore/SET_CONNECTION_ID", response.data.connectionId);
        router.push({
          name: "festival-room",
          query: { dest: dest },
        });
      });
    };
    const create_room = () => {
      console.log("Let's go!");
      router.push({
        name: "room-create",
        query: { dest: dest },
      });
    };
    /*
    (TODO) 색상 변경이 theme color로 바뀌지 않는 버그
    const darkColor = themeColors[index][0];
    const brightColor = themeColors[index][1];
    onMounted(() => {
      capacity.value.style = `color: ${darkColor};`;
      button.value.style = `background: ${brightColor};`;
    });
    */
    const icon = ref(null);
    const capacity = ref(null);
    const button = ref(null);
    onMounted(() => {
      icon.value.src = iconPath;
    });
    return {
      title,
      iconPath,
      items,
      icon,
      capacity,
      button,
      create_room,
      enterRoom,
    };
  },
};
</script>

<style scoped>
.main-box {
  height: 70%;
  padding: 40px 80px;
}
.box-header {
  height: 15%;
  font-size: 50px;
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
.icon-create {
  width: 50px;
  height: 50px;
  float: right;
}
.room-card {
  background-color: #d1d8df;
  border-radius: 20px;
  width: 100%;
  height: 150px;
  margin-bottom: 20px;
  display: flex;
}
.room-card-thumbnail-wrap {
  width: 30%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.room-card-text-wrap {
  width: 55%;
}
.room-card-button-wrap {
  width: 15%;
}
.room-card-thumbnail {
  width: 80%;
  height: 80%;
  background-color: blue;
  border-radius: 20px;
}
.room-card-title {
  height: 35%;
  font-size: 30px;
  display: flex;
  align-items: center;
}
.room-card-info {
  height: 65%;
}
.room-card-capacity {
  height: 35%;
  display: flex;
  align-items: center;
  font-size: 24px;
}
.room-card-button {
  height: 55%;
  display: flex;
  align-items: end;
  justify-content: center;
}
.box-list {
  height: 85%;
  overflow: scroll;
}
</style>
