<template>
  <div class="background">
    <div
      style="padding: 20px; float: right; font-size: 30px; color: white"
      @click="skip"
    >
      건너뛰기
    </div>
  </div>
</template>

<script>
import { onMounted } from "@vue/runtime-core";
import { useRouter } from "vue-router";
export default {
  name: "train-move",
  setup() {
    const router = useRouter();
    const urlParams = new URL(location.href).searchParams;
    const dest = urlParams.get("dest");
    var isSkipped = false;
    var audio_0 = null;
    var audio_1 = null;
    const skip = () => {
      isSkipped = true;
      audio_0.pause();
      audio_1.pause();
      router.push({
        name: "train-arrive",
        query: { dest: dest },
      });
    };
    onMounted(() => {
      audio_0 = new Audio(require("@/assets/Train2.wav"));
      audio_0.play();
      audio_1 = new Audio(require("@/assets/Train1.wav"));
      audio_1.play();

      setTimeout(() => {
        if (isSkipped == false) {
          router.push({
            name: "train-arrive",
            query: { dest: dest },
          });
        }
      }, 5000);
    });
    return { skip };
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.background {
  background-image: url(@/assets/move.gif);
}

img {
  width: 100%;
  height: 100%;
  border: 0px;
  margin: 0px;
}
</style>
