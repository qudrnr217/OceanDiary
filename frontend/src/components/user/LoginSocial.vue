<template>
  <div class="box main-box">
    <div class="guide-wrap">
      <div class="guide-text">
        <VueWriter
          :array="['로그인 방식을 선택해주세요.']"
          :typespeed="1"
          :iterations="1"
        ></VueWriter>
      </div>
    </div>
    <div class="content-wrap">
      <div
        class="content"
        href="https://nid.naver.com/oauth2.0/authorize?client_id=Xa0_QAw9WqjfHiiycTsd&response_type=code&redirect_uri=http://localhost:8080/oauth/naver"
      >
        <img src="~@/assets/이미지/[이미지]구글.png" class="social-logo" />
      </div>
      <div
        class="content"
        href="https://i7a406.p.ssafy.io:443/kakao/login?redirect_uri=https://i7a406.p.ssafy.io/oauth/kakao"
      >
        <img src="~@/assets/이미지/[이미지]카카오톡.png" class="social-logo" />
      </div>
      <div class="content">
        <!-- <router-link :to="{ name: 'login-single' }" class="button-ticket"> -->
        <img
          src="~@/assets/이미지/[이미지]네이버.png"
          class="social-logo"
          @click="sendNaverUrl()"
        />
        <!-- </router-link> -->
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
// import { login } from "@/api/login.js";
export default {
  data() {
    return {
      code: "",
      state: "",
    };
  },
  beforeCreate() {
    // const urlParams = new URL(location.href).searchParams;
    // const code = urlParams.get("code");
    // if (code != null) login(code);

    axios({
      methods: "get",
      url: "https://i7a406.p.ssafy.io/api/naver/state",
    }).then((response) => {
      console.log(response);
      this.state = response.data.state;
      console.log(this.state);
      // this.state = response.data.state;
    });
  },

  mounted() {},
  methods: {
    /*
    1. 카카오 서버에 인가코드 요청을 보낸다.
    2. 로그인 창이 생성된다.
    3. 로그인 성공 시 인가코드가 반환된다.
    4. 반환된 인가 코드로 백엔드에 로그인 요청을 보낸다.
    5. isExist가 False라면, 회원가입 화면으로 넘어간다.
    6. isExist가 True라면, 바로 승차화면으로 넘어간다.
     */
    kakaoLogin() {
      window.Kakao.init("ad93b4574b9fa55858c5944c66785045"); // Kakao Developers에서 요약 정보 -> JavaScript 키
      const params = {
        redirectUri: "http://localhost:8080/login/social",
      };
      window.Kakao.Auth.authorize(params);
    },

    sendNaverUrl() {
      // console.log(this.state);
      location.href = `https://nid.naver.com/oauth2.0/authorize?client_id=vQpyQoy56bjYmRbrlq5L&response_type=code&redirect_uri=https://i7a406.p.ssafy.io/oauth2/redirect&state=${this.state}`;
    },
  },
};
</script>

<style scoped>
.guide-wrap {
  height: 20%;
  text-align: left;
  padding-left: 30px;
  display: flex;
  align-items: center;
}
.guide-text {
  color: grey;
  font-size: 30px;
}
.content-wrap {
  display: flex;
  width: 100%;
}
.content {
  height: 100%;
  width: 50%;
  display: flex;
  justify-content: center;
  align-content: center;
  padding: 50px;
}
.content:hover {
  animation: flash;
  animation-duration: 1s; /* don't forget to set a duration! */
}
.social-logo {
  width: 100%;
  border-radius: 25%;
  filter: drop-shadow(3px 3px 3px #000);
}
.name {
  width: 100%;
  height: 50%;
  display: flex;
  align-items: center;
  text-align: center;
  justify-content: center;
  margin: auto;
}
.image {
  height: 50%;
  display: flex;
  justify-content: center;
  align-content: center;
}
.image-ticket {
  width: 70%;
  height: 100%;
  margin: auto;
}
#left {
  float: left;
}
#right {
  float: right;
}
</style>
