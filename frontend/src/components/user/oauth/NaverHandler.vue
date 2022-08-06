<template>
  <div>Oauth</div>
</template>
<script>
// import { useStore } from "vuex";
// import { API_BASE_URL } from "@/constant/index";
import axios from "axios";
// import naverLogin from "@/api/login.js";

export default {
  name: "NaverHandler",
  mounted() {
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
        console.log("hi");
        console.log(data);
      });
    }

    // 만약 url에 코드가 포함되어 있다면, 인가 코드가 발급된 것이다.
    //   if (code) {
    //     naverLogin(
    //       code,
    //       (response) => {
    //         return {
    //           name: response.name,
    //           email: response.email,
    //           birth: response.birth,
    //           oauthid: response.oauthid,
    //         };
    //         if (response.isExist) {
    //         } else {
    //         }
    //       },
    //       () => {
    //         alert("인증에 실패했습니다!");
    //       }
    //     );
    //     store.dispatch("token/setToken", token);
    //     store.dispatch("token/setLogin");

    //     axios({
    //       method: "get",
    //       url: API_BASE_URL + "/api/user/profile",
    //       headers: store.getters["token/getHeaders"],
    //     })
    //       .then(({ data }) => {
    //         return {
    //           name: data.data.name,
    //           email: data.data.email,
    //         };
    //       })
    //       .then((profile) => {
    //         store.dispatch("token/setProfile", profile);
    //         ElNotification({
    //           title: "Login Success!",
    //           message: `환영합니다! ${profile.name}님!`,
    //           type: "success",
    //           duration: "2500",
    //           customClass: "font-jua",
    //         });
    //         this.$router.push({ name: "Home" });
    //       })
    //       .catch((err) => {
    //         console.log("err", err);
    //       });
    //   } else {
    //     this.$router.push("/");
    //   }
  },
};
</script>
