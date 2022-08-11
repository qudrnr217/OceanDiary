<template>
  <div class="background-wrap">
    <div class="text-box">
      <div class="text-header">
        <img
          src="@/assets/아이콘/icon_locationpoint.png"
          alt="location 아이콘"
        />
        <div class="text-title">{{ title }}</div>
        <img
          src="@/assets/아이콘/[아이콘]방생성.png"
          class="create-btn"
          alt="방생성"
          @click="create_room()"
        />
      </div>
      <div class="text-middle">
        <!-- <div class="room" v-for=""></div> -->
        <button @click="insideroom()">입장</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { onMounted } from "@vue/runtime-core";

export default {
  setup() {
    const store = useStore();
    var title = store.state.locationStore.location_name;
    const router = useRouter();
    var create_room = () => {
      console.log(store.state.locationStore.create_name);
      router.push({ name: store.state.locationStore.create_name });
    };

    var insideroom = () => {
      axios({
        method: "post",
        url: `/api/rooms/2`,
        // url: `http://localhost:8080/api/rooms/${store.state.roomStore.roomId}`,
        pw: "123",
        headers: {
          Authorization:
            "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIyIiwiYXVkIjoi6rCV67OR6rWtIiwiZXhwIjoxNjYwMTk2NTA3fQ.wu_3QT_TehiHa64eV40EP4aBZE4E8zqNvJP_oE8KkN3XVjvGUH7XUIW4jjh1Nnws",
        },
      }).then((data) => {
        console.log(data);
      });
    };

    onMounted(() => {
      axios({
        method: "get",
        url: `/api/rooms?category=LIBRARY&lastRoomId=0`,
        headers: {
          Authorization: store.state.userStore.token,
        },
      }).then((response) => {
        console.log(response);
      });
    });

    return {
      title,
      create_room,
      insideroom,
    };
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.background-wrap {
  width: 100vw;
  height: 100vh;
  /* background-color: red; */

  display: flex;
  justify-content: center;
  align-items: center;
}

.background-wrap > .text-box {
  /* background-color: blue; */
  width: 55vw;
  height: 80vh;
}

.text-box > .text-header {
  height: 10%;
  padding: 1em;
  /* background-color: purple; */

  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.text-box > .text-middle {
  height: 80%;
  /* background-color: orange; */
}

.create-btn {
  width: 3vw;
  /* height: 5vh; */
  position: relative;
  left: 83%;
}
</style>
