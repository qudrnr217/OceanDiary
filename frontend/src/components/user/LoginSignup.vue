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
      <input type="text" v-model="state.userinfo.name" class="name-input" />
      <div class="ticket-title">정기권</div>
    </div>
    <div class="email-wrap">
      <div class="name-box">이메일</div>
      <input type="email" v-model="state.userinfo.email" class="name-input" />
      <img
        src="@/assets/아이콘/[아이콘]정기권_상단.png"
        alt="아이콘"
        class="ticket-img"
      />
    </div>
    <div class="birth-wrap">
      <div class="name-box">생년월일</div>
      <input class="year-box" v-model="state.date.year" />
      <div class="year-name">년</div>
      <input class="month-box" v-model="state.date.month" />
      <div class="year-name">월</div>
      <input class="day-box" v-model="state.date.day" />
      <div class="year-name">일</div>
    </div>
    <div class="submit-wrap">
      <button class="button-next" @click="submit()">발 급</button>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { signup } from "@/api/login.js";
import { reactive } from "vue";

export default {
  setup() {
    const router = useRouter();
    const store = useStore();
    const state = reactive({
      userinfo: {
        email: "",
        name: "",
        birth: new Date(),
        oauthId: "",
      },
      date: {
        year: "",
        month: "",
        day: "",
      },
    });

    const urlParams = new URL(location.href).searchParams;
    const social = urlParams.get("social");
    const oauthId = urlParams.get("oauthId");

    var submit = () => {
      state.userinfo.birth.setFullYear(state.date.year);
      state.userinfo.birth.setMonth(state.date.month);
      state.userinfo.birth.setDate(state.date.day);
      state.userinfo.oauthId = oauthId;

      signup(
        social,
        state.userinfo,
        (response) => {
          console.log("회원가입 요청 : 성공!");
          // vuex에 회원 정보 업데이트
          store.commit("userStore/SET_NAME", response.data.name);
          store.commit("userStore/SET_TOKEN", response.data.accessToken);
          store.commit("userStore/SET_USERID", response.data.userId);
          router.push({ name: "station_where" });
        },
        (error) => {
          console.log(error);
        }
      );
    };
    /* (TODO) 로그인 사항에 대한 예외처리를 구현합니다.
    const checkInput = () => {

    }
    */
    return {
      state,
      submit,
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
