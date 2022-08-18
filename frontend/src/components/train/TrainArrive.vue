<template>
  <div class="background" ref="bg">
    <div class="up"></div>
    <div class="down">
      <div class="text-box">
        <div class="name-wrap">
          <div class="button-name">역무원</div>
        </div>
        <div class="text-wrap">
          <vue-writer
            :array="[
              `이번 역은 ${stations[index]} 역입니다. 즐거운 시간 보내시길 바랍니다.`,
            ]"
            :typeSpeed="50"
            :iterations="1"
          />
        </div>
        <div class="button-wrap">
          <router-link
            :to="{
              name: 'room-arrive',
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
import { onMounted, ref } from "vue";
import { arriveOtterImages, stations, indexes } from "@/const/const.js";
import useSound from "vue-use-sound";
import clickSfx from "@/assets/Click.wav";
export default {
  name: "train-arrive",
  setup() {
    const urlParams = new URL(location.href).searchParams;
    const dest = urlParams.get("dest");
    var index = indexes[dest];
    const [clickSound] = useSound(clickSfx);

    const bg = ref(null);
    onMounted(() => {
      const url = require(`@/assets/${arriveOtterImages[index]}`);
      bg.value.style = `background-image: url(${url});`;
    });
    return {
      index,
      stations,
      bg,
      clickSound,
    };
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.banner {
  height: 60vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.ticket {
  width: 172px;
  height: 114px;
  position: relative;
  left: 40vw;
  bottom: 20vh;
}
</style>
