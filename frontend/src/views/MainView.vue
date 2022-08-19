<template>
  <div class="background">
    <div class="up">
      <vue-writer
        :array="['바닷마을 다이어리']"
        :typeSpeed="200"
        :iterations="1"
        class="title"
      />
    </div>
    <router-link
      :to="{ name: 'login-menu' }"
      class="button start-btn"
      @click="startSound()"
      >시 작</router-link
    >
  </div>
</template>

<script>
import useSound from "vue-use-sound";
import startSfx from "@/assets/Start.wav";

export default {
  setup() {
    const [startSound] = useSound(startSfx);
    return {
      startSound,
    };
  },
  name: "HelloWorld",
  props: {
    msg: String,
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.background {
  background-image: url("~@/assets/[00_시작]메인화면.png");
}
</style>
