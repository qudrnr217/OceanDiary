<template>
  <div class="background">
    <div class="banner">
      <!-- <img
        src="../assets/아이콘/[아이콘]정기권_상단.png"
        alt="티켓사진"
        class="ticket"
      > -->
    </div>
    <div class="text-box">
      <div class="name-wrap">
        <div class="button-name">역무원</div>
      </div>
      <div class="text-wrap">
        <vue-writer
          :array="['이번 역은 도서관 역입니다. 즐거운 시간 보내시길 바랍니다.']"
          :typeSpeed="70"
          :iterations="1"
        />
      </div>
      <div class="button-wrap">
        <!-- <div class="button-next">다음</div> -->
        <!-- <router-link to="/library_bg/home" class="button-next"
          >다음</router-link
        > -->
        <button class="button-next" @click="next_page()">다음</button>
      </div>
    </div>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { onMounted } from "vue";

export default {
  name: "HelloWorld",
  setup() {
    const router = useRouter();
    const store = useStore();
    onMounted(() => {
      const audio_0 = new Audio(require("@/assets/공통-도착.wav"));
      audio_0.play();
    });
    var next_page = () => {
      store.commit("locationStore/SET_LOCATION_NAME", "도서관");
      router.push("/library_bg/home");
      store.commit("locationStore/SET_CREATE_NAME", "library_create");
    };
    return {
      next_page,
    };
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.background {
  background-image: url("~@/assets/[05_도착]도서관.png");
}
.banner {
  height: 60vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.button-next {
  background: #aabc61;
}
</style>
