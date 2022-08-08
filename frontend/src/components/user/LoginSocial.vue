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
      <div class="content" @click="sendGoogleUrl()">
        <img src="~@/assets/이미지/[이미지]구글.png" class="social-logo" />
      </div>
      <div class="content" @click="sendKakaoUrl()">
        <img src="~@/assets/이미지/[이미지]카카오톡.png" class="social-logo" />
      </div>
      <div class="content" @click="sendNaverUrl()">
        <img src="~@/assets/이미지/[이미지]네이버.png" class="social-logo" />
      </div>
    </div>
  </div>
</template>

<script>
import { naverState } from "@/api/login.js";
export default {
  data() {
    return {
      naverState: "",
    };
  },
  beforeCreate() {
    naverState(
      (response) => {
        console.log("네이버 상태코드 발급완료 : " + response.data.state);
        this.naverState = response.data.state;
      },
      (error) => {
        console.log(error);
      }
    );
  },

  mounted() {},
  methods: {
    sendGoogleUrl() {
      alert("준비중입니다.");
    },
    sendKakaoUrl() {
      location.href = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=2cf03b4e3f6b70ea1253b492a2047118&redirect_uri=https://i7a406.p.ssafy.io/oauth2/kakao`;
    },
    sendNaverUrl() {
      location.href = `https://nid.naver.com/oauth2.0/authorize?&state=${this.naverState}&client_id=vQpyQoy56bjYmRbrlq5L&response_type=code&redirect_uri=https://i7a406.p.ssafy.io/oauth2/naver`;
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
