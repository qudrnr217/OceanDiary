<template>
  <div class="background" ref="bg">
    <div class="up"></div>
    <div class="down">
      <div class="text-box">
        <div class="name-wrap">
          <div class="button-name">{{ jobs[index] }}</div>
        </div>
        <div class="text-wrap">
          <vue-writer
            :array="[`${greetings[index]}`]"
            :typeSpeed="50"
            :iterations="1"
          />
        </div>
        <div class="button-wrap">
          <router-link
            :to="{
              name: 'room-list',
              query: { dest: this.$route.query.dest },
            }"
            class="button-next"
            >다음</router-link
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, ref } from "vue";
import { roomOtterImages, indexes, jobs, greetings } from "@/const/const.js";
export default {
  name: "room-arrive",
  setup() {
    const urlParams = new URL(location.href).searchParams;
    const dest = urlParams.get("dest");
    var index = indexes[dest];

    const bg = ref(null);
    onMounted(() => {
      const url = require(`@/assets/${roomOtterImages[index]}`);
      bg.value.style = `background-image: url(${url});`;
    });

    return {
      index,
      greetings,
      jobs,
      bg,
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
