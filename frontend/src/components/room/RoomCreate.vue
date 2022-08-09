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
export default {
  setup() {
    const store = useStore();
    var location_name = store.state.locationStore.location_name;
    var show = ref(false);
    var title = ref("");
    var rule = ref("");
    var maxNum = ref(1);
    var isOpen = ref(true);
    var pw = ref(null);
    var img = ref(null);

    var create_room = () => {
      const data = new FormData();
      const form = {
        categoryId: store.state.locationStore.location_name,
        title: title,
        rule: rule,
        maxNum: maxNum,
        isOpen: isOpen,
        pw: pw,
      };
      // frm.append("categoryId", store.state.locationStore.location_name);
      // frm.append("title", title);
      // frm.append("rule", rule);
      // frm.append("maxNum", maxNum);
      // frm.append("isOpen", isOpen);
      // frm.append("pw", pw);
      data.append("form", new Blob([JSON.stringify(form)]), {
        type: "application/json",
      });
      data.append("file", img);
      console.log(store.state.userStore.token);
      console.log("hi");
      axios(
        {
          method: "post",
          // url: "https://i7a406.p.ssafy.io/api/room",
          url: "/api/room",
          data: data,
          headers: {
            "Content-Type": "multipart/form-data",
            // Authorization: store.state.userStore.token,
          },
        },
        { withCredentials: true }
      ).then((data) => {
        console.log(data);
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
