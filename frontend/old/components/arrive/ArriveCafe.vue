<template>
  <div class="background">
    <div class="banner"></div>
    <div class="text-box">
      <div class="name-wrap">
        <div class="button-name">역무원</div>
      </div>
      <div class="text-wrap">
        <vue-writer
          :array="['이번 역은 카페 역입니다. 즐거운 시간 보내시길 바랍니다.']"
          :typeSpeed="70"
          :iterations="1"
        />
      </div>
      <div class="button-wrap">
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
      store.commit("locationStore/SET_LOCATION_NAME", "카페");
      console.log(store.state.locationStore.location_name);
      router.push("/cafe_bg/home");
      store.commit("locationStore/SET_CREATE_NAME", "cafe_create");
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
  background-image: url("~@/assets/[06_방목록]카페.png");
}
.banner {
  height: 60vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.button-next {
  background: #f7d794;
}
</style>
