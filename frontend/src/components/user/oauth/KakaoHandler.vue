<template>
  <div>Oauth</div>
</template>
<script>
import { useStore } from "vuex";
import { API_BASE_URL } from "@/constant/index";
import axios from "axios";

export default {
  name: "KakaoHandler",
  mounted() {
    const store = useStore();
    // 분기처리
    // isExist => 승차장(/start/depart/으로 가고
    // !isExist => 없으면 회원가입으로 가고,,,,,

    if (store.token) {
      store.dispatch("token/setToken", token);
      store.dispatch("token/setLogin");

      axios({
        method: "get",
        url: API_BASE_URL + "/api/user/profile",
        headers: store.getters["token/getHeaders"],
      })
        .then(({ data }) => {
          return {
            name: data.data.name,
            imageUrl: data.data.imageUrl,
            email: data.data.email,
          };
        })
        .then((profile) => {
          store.dispatch("token/setProfile", profile);
          ElNotification({
            title: "Login Success!",
            message: `환영합니다! ${profile.name}님!`,
            type: "success",
            duration: "2500",
            customClass: "font-jua",
          });
          this.$router.push({ name: "Home" });
        })
        .catch((err) => {
          console.log("err", err);
        });
    } else {
      this.$router.push("/");
    }
  },
};
</script>
