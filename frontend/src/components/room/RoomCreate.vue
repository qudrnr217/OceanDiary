<template>
  <div class="background-wrap">
    <div class="text-box">
      <div class="text-header">
        <img src="@/assets/아이콘/coffe_icon.png" alt="location 아이콘" />
        <div class="text-title">스터디생성</div>
        <div class="right-wrap">
          <img
            src="@/assets/아이콘/icon_lock.png"
            class="lock"
            alt="방생성"
            @click="isOpen = !isOpen"
            v-if="!isOpen"
          />
          <input
            type="password"
            class="password"
            v-show="!isOpen"
            v-model="pw"
          />
          <img
            src="@/assets/아이콘/[아이콘]방_공개.png"
            class="lock"
            alt="방생성"
            @click="isOpen = !isOpen"
            v-if="isOpen"
          />
        </div>
      </div>
      <div class="text-middle">
        <div class="title-wrap">
          <div class="title-name">제목 :</div>
          <input type="text" class="title_text" v-model="title" />
        </div>
        <div class="rule-wrap">
          <div class="rule">규칙 :</div>
          <input type="text" class="rule-text" v-model="rule" />
        </div>
        <div class="count-wrap">
          <div class="count">인원 :</div>
          <input
            type="number"
            class="count-text"
            v-model="maxNum"
            max="6"
            min="1"
          />
          <div class="count-last">명</div>
        </div>
        <div class="img-wrap">
          <div class="image">이미지 :</div>
          <div class="select-file">파일 선택</div>
          <form action="">
            <input type="file" @change="fileSlc" id="file" name="file" />
          </form>

          <!-- <img src="" alt=""> -->
        </div>
      </div>
      <div class="text-footer">
        <button class="button-next" @click="create_room()">생성</button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import { useStore } from "vuex";
import axios from "axios";
import { useRouter } from "vue-router";
export default {
  setup() {
    const router = useRouter();
    const store = useStore();
    var location_name = store.state.locationStore.location_name;
    var show = ref(false);
    var title = ref("");
    var rule = ref("");
    var maxNum = ref(1);
    var isOpen = ref(true);
    var pw = ref("");
    var img = ref("");
    var slc2 = ref("");

    var fileSlc = (event) => {
      console.log(event);
      console.log(event.target.files[0]);
      slc2.value = event.target.files[0];
    };

    var create_room = () => {
      // const data = new FormData();
      const data = new FormData();
      console.log(slc2.value);
      const form = {
        categoryId: "LIBRARY",
        title: title.value,
        rule: rule.value,
        maxNum: maxNum.value,
        isOpen: isOpen.value,
        pw: pw.value,
      };

      data.append(
        "form",
        new Blob([JSON.stringify(form)], { type: "application/json" })
      );
      data.append("file", slc2.value);

      console.log(store.state.userStore.token);
      // console.log("hi");
      // console.log(data);
      axios(
        {
          method: "post",
          // url: "https://i7a406.p.ssafy.io/api/room",
          // url: "/api/rooms",
          url: "http://localhost:8080/api/rooms",
          data: data,
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: store.state.userStore.token,
          },
        },
        { withCredentials: true }
      ).then((data) => {
        console.log(data);
        const array = data.data.token.split('"');
        const tmp = array[0].split("=");
        const tmp2 = tmp[1].split("&");
        // console.log(tmp[0]);
        // console.log(tmp[1]);
        // console.log(tmp[2]);
        // console.log(tmp2[0]);
        // console.log(tmp2[1]);

        // var openvidu_token = tmp[2];
        var session_id = tmp2[0];
        store.commit("roomStore/SET_ROOM_ID", data.data.roomId);
        store.commit("roomStore/SET_PARTICIPANT_ID", data.data.participantId);
        store.commit("roomStore/SET_OPENVIDU_TOKEN", data.data.token);
        store.commit("roomStore/SET_CONNECTION_ID", data.data.connectionId);
        store.commit("roomStore/SET_SESSION_ID", session_id);
        console.log(store.state.roomStore.roomId);
        console.log(store.state.roomStore.participantId);
        console.log(store.state.roomStore.openvidu_token);
        console.log(store.state.roomStore.connectionId);
        console.log(store.state.roomStore.sessionId);

        router.push({ name: "library_study" });
      });
    };

    return {
      location_name,
      title,
      show,
      rule,
      maxNum,
      isOpen,
      pw,
      img,
      slc2,
      fileSlc,
      create_room,
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
  height: 75%;
  /* background-color: orange; */
}

.lock {
  width: 3vw;
  /* height: 5vh; */
  position: relative;
  /* left: 50%; */
}

.text-box > .text-footer {
  height: 10%;
  /* background-color: aqua; */
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.right-wrap {
  width: 80%;
  /* background-color: aquamarine; */
  display: flex;
  justify-content: flex-end;
}

.password {
  border: 2px solid #d1d8df;
  border-radius: 16px;
  width: 30%;
}

.text-middle > .title-wrap {
  /* background-color: cadetblue; */
  height: 20%;

  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.text-middle > .rule-wrap {
  /* background-color: yellow; */
  height: 35%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.text-middle > .count-wrap {
  /* background-color: violet; */
  height: 15%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.text-middle > .img-wrap {
  /* background-color: tomato; */
  height: 30%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.title-name {
  position: relative;
  left: 10%;
}

.title_text {
  width: 76%;
  height: 60%;
  position: relative;
  left: 14%;
  border: 2px solid #d1d8df;
  border-radius: 16px;
}

.rule {
  position: relative;
  left: 10%;
}

.rule-text {
  width: 76%;
  height: 80%;
  position: relative;
  left: 14%;
  border: 2px solid #d1d8df;
  border-radius: 16px;
}

.count {
  position: relative;
  left: 10%;
}

.count-text {
  width: 8%;
  height: 60%;
  position: relative;
  left: 14%;
  border: 2px solid #d1d8df;
  border-radius: 16px;
}

.count-last {
  position: relative;
  left: 17%;
}

.image {
  position: relative;
  left: 10%;
}

.select-file {
  width: 15%;
  height: 25%;
  background: #d1d8df;
  border: 2px solid #d1d8df;
  border-radius: 16px;
  position: relative;
  left: 12%;

  display: flex;
  justify-content: center;
  align-items: center;
}

.button-next {
  background: #f7d794;
}
/* <div class="text-middle">
        <div class="title-wrap"></div>
        <div class="rule-wrap"></div>
        <div class="count-wrap"></div>
        <div class="img-wrap"></div> */
</style>
