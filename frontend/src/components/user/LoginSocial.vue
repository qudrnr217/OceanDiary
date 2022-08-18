<template>
  <div class="box main-box">
    <div class="guide-wrap">
      <vue-writer
        :array="['로그인 방식을 선택해주세요.']"
        :typeSpeed="70"
        :iterations="1"
      ></vue-writer>
    </div>
    <div class="content-wrap">
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
import { REDIRECT_URL } from "@/const/url.js";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
export default {
  data() {
    return {
      naverState: "",
    };
  },
  beforeCreate() {
    const store = useStore();
    const router = useRouter();
    if (store.state.userStore.isLogin) {
      router.push({
        name: "station-chat",
        params: { nextLink: "map", speech: "어디로 가시나요?" },
      });
      return;
    }
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
      location.href = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=2cf03b4e3f6b70ea1253b492a2047118&redirect_uri=${REDIRECT_URL}/oauth2/kakao`;
    },
    sendNaverUrl() {
      location.href = `https://nid.naver.com/oauth2.0/authorize?&state=${this.naverState}&client_id=vQpyQoy56bjYmRbrlq5L&response_type=code&redirect_uri=${REDIRECT_URL}/oauth2/naver`;
      // location.href = `https://nid.naver.com/oauth2.0/authorize?&state=${this.naverState}&client_id=vQpyQoy56bjYmRbrlq5L&response_type=code&redirect_uri=http://localhost:8080/oauth2/naver`;
    },
  },
};
</script>

<style scoped>
.content {
  width: 50%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-content: center;
  padding: 50px;
}
.content:hover {
  box-shadow: 0px 10px 0px #72ab45;
}
.social-logo {
  width: 280px;
  border-radius: 25%;
}
#left {
  float: left;
}
#right {
  float: right;
}
</style>
