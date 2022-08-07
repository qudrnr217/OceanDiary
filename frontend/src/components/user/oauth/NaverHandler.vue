<template>
  <div>Oauth</div>
</template>
<script>
// import { useStore } from "vuex";
// import { API_BASE_URL } from "@/constant/index";
import axios from "axios";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
// import naverLogin from "@/api/login.js";

export default {
  name: "NaverHandler",
  mounted() {
    const router = useRouter();
    const store = useStore();
    // const store = useStore();
    const urlParams = new URL(location.href).searchParams;
    const code = urlParams.get("code");
    console.log(code);
    if (code) {
      console.log("axios 시작!");
      axios({
        method: "post",
        url: "https://i7a406.p.ssafy.io/api/naver/login",
        data: { code: code },
      }).then((data) => {
        console.log(data.data.oauthId);
        store.commit("userStore/SET_OAUTH", data.data.oauthId);
        //아이디가 있을 경우
        if (data.data.isExist) {
          router.push({ name: "station_where" });
        } else {
          //아이디가 없을 경우
          router.push({ name: "login-signup" });
          console.log("hi");
          console.log(store.state.userStore.oauth);
        }
      });
    }
  },
};
</script>
