<template>
  <div class="background"></div>
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
    onMounted(() => {
      var audio_0 = new Audio(require("@/assets/Train2.wav"));
      audio_0.play();
      var audio_1 = new Audio(require("@/assets/Train1.wav"));
      audio_1.play();

      setTimeout(() => {
        console.log("hi");
        router.push({
          name: "train-arrive",
          query: { dest: dest },
        });
      }, 5000);
    });
    return {};
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
