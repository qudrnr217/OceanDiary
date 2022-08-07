<template>
  <div class="text-box">
    <div class="text-wrap">
      <VueWriter
        :array="[`정보를 입력한 후, '발급' 버튼을 눌러주세요.`]"
        :typespeed="1"
        :iterations="1"
      ></VueWriter>
    </div>
    <div class="name-wrap">
      <div class="name-box">이름</div>
      <input type="text" v-model="name" class="name-input" />
      <div class="ticket-title">정기권</div>
    </div>
    <div class="email-wrap">
      <div class="name-box">이메일</div>
      <input type="email" v-model="email" class="name-input" />
      <img
        src="@/assets/아이콘/[아이콘]정기권_상단.png"
        alt="아이콘"
        class="ticket-img"
      />
    </div>
    <div class="birth-wrap">
      <div class="name-box">생년월일</div>
      <input class="year-box" v-model="year" />
      <div class="year-name">년</div>
      <input class="month-box" v-model="month" />
      <div class="year-name">월</div>
      <input class="day-box" v-model="day" />
      <div class="year-name">일</div>
    </div>
    <div class="submit-wrap">
      <button class="button-next" @click="submit_info()">발 급</button>
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity";
import axios from "axios";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
// import { useCookies } from "vue3-cookies";
export default {
  setup() {
    // const cookies = useCookies();
    const router = useRouter();
    const store = useStore();
    let name = ref("");
    let email = ref("");
    let year = ref("");
    let month = ref("");
    let day = ref("");

    var submit_info = () => {
      axios({
        method: "post",
        url: "https://i7a406.p.ssafy.io/api/naver/signup",
        data: {
          email: email.value,
          name: name.value,
          birth: year.value + "-" + month.value + "-" + day.value,
          oauthId: store.state.userStore.oauth,
        },
      }).then((response) => {
        console.log(response);
        store.commit("userStore/SET_NAME", response.data.name);
        store.commit(
          "userStore/SET_TOKEN",
          "Bearer " + response.data.accessToken
        );
        store.commit("userStore/SET_USERID", response.data.userId);
        // cookies.set("userName", response.data.name);
        // cookies.set("userToken", "Bearer " + response.data.accessToken);
        // cookies.set("userId", response.data.userId);

        console.log(store.state.userStore.name);
        console.log(store.state.userStore.token);
        console.log(store.state.userStore.userId);

        router.push({ name: "station_where" });
      });
    };

    return {
      name,
      email,
      year,
      month,
      day,
      submit_info,
      //   cookies,
    };
  },
};
</script>

<style>
.text-box {
  width: 55vw;
  height: 55vh;
}
.text-wrap {
  /* background-color: red; */
  height: 15%;
  color: #989898;
}

.name-wrap {
  /* background-color: yellow; */
  height: 25%;
}

.email-wrap {
  /* background-color: pink; */
  height: 25%;
  display: flex;
  align-items: center;
}

.birth-wrap {
  /* background-color: aqua; */
  height: 25%;
  display: flex;
  align-items: center;
}
.submit-wrap {
  /* background-color: chartreuse; */
  height: 15%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.name-box {
  width: 10%;
  height: 40%;
  background: #d1d8df;
  border-radius: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.name-input {
  width: 40%;
  height: 40%;
  border-radius: 15px;

  position: relative;
  left: 2%;
}

.ticket-title {
  position: relative;
  left: 23%;
}

.year-box {
  width: 9%;
  height: 40%;
  border-radius: 15px;
  position: relative;
  left: 2%;
}

.month-box {
  width: 6%;
  height: 40%;
  border-radius: 15px;
  position: relative;
  left: 2%;
}

.day-box {
  width: 6%;
  height: 40%;
  border-radius: 15px;
  position: relative;
  left: 2%;
}

.year-name {
  position: relative;
  left: 2%;
}

.button-next {
  background: #72ab46;
  color: #ffffff;
}

.ticket-img {
  width: 30%;
  height: auto;
  position: relative;
  top: 8%;
  left: 12%;
}
</style>
