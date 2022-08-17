<template>
  <div class="background">
    <div class="center">
      <div class="text-box">
        <div class="name-wrap">
          <vue-writer
            :array="['이름이 무엇인가요 ?']"
            :typeSpeed="70"
            :iterations="1"
          />
        </div>
        <div class="text-wrap">
          <input type="text" v-model="userName" id="name-input" />
        </div>
        <div class="button-wrap">
          <div class="button-next" @click="move">다음</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { ref } from "vue";

export default {
  setup() {
    const router = useRouter();
    const store = useStore();

    const userName = ref("");
    const move = () => {
      if (userName.value == "") {
        alert("이름을 입력하세요!");
        return;
      } else {
        store.commit("userStore/SET_NAME", userName.value);
      }
      router.push({
        name: "station-chat",
        params: {
          nextLink: "/station/map",
          speech: "어디로 가시나요?",
        },
      });
    };
    return { move, userName };
  },
};
</script>

<style scoped>
.name-wrap {
  color: darkgrey;
  font-size: 24px;
}
.text-box {
  width: 500px;
}
#name-input {
  width: 100%;
  height: 50%;
  font-size: large;
  font-family: "retro";
}
</style>
