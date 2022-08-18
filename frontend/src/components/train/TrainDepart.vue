<template>
  <div class="background">
    <div class="up"></div>
    <div class="down">
      <div class="text-box">
        <div class="name-wrap">
          <div class="button-name">역무원</div>
        </div>
        <div class="text-wrap">
          <vue-writer
            :array="['출발하겠습니다 !']"
            :typeSpeed="70"
            :iterations="1"
          />
        </div>
        <div class="button-wrap">
          <router-link
            :to="{
              name: 'train-move',
              query: { dest: this.$route.query.dest },
            }"
            class="button-next"
            @click="clickSound()"
            >다음</router-link
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted } from "vue";
import { useStore } from "vuex";
import useSound from "vue-use-sound";
import clickSfx from "@/assets/Click.wav";
export default {
  setup() {
    const [clickSound] = useSound(clickSfx);
    var store = useStore();
    console.log(store.state.locationStore.location_url);

    onMounted(() => {
      const audio_0 = new Audio(require("@/assets/출발-카드.wav"));
      audio_0.play();
    });
    return {
      clickSound,
    };
  },
};
</script>

<style scoped>
.background {
  background-image: url("~@/assets/[04_역출발]에노덴_해달.png");
}
</style>
