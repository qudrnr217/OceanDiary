<template>
  <div>
    <div class="info-wrap">
      <img
        src="@/assets/아이콘/[아이콘]위치포인트.png"
        alt="위치아이콘"
        class="location"
      />
      <div class="info-name">
        {{ name }}
      </div>
    </div>
    <div class="img-wrap">
      <img :src="img" alt="이미지" class="arrive-img" />
    </div>
    <div class="text-wrap">
      <vue-writer :array="[explanation]" :typeSpeed="70" :iterations="1" />
    </div>
    <div class="button-wrap">
      <router-link to="/station/depart" class="button-next">입장</router-link>
    </div>
  </div>
</template>

<script scoped>
import { watch } from "vue";

export default {
  props: ["name", "explanation", "componentKey", "img"],

  setup(props) {
    console.log(props.name);
    console.log(props.explanation);
    console.log(props.copmponentKey);
    console.log(props.img);

    watch(props.explanation, (newEx, oldEx) => {
      console.log(newEx);
      console.log(oldEx);
    });
  },
};
</script>

<style scoped>
.info-wrap {
  display: flex;
  /* background-color: red; */
  /* position: relative; */
  height: 6vh;
}

.info-wrap > .info-name {
  font-size: 3vw;

  position: relative;
  left: 20vw;
}

.info-wrap > .location {
  position: relative;
  left: 19vw;
}

.img-wrap {
  /* background-color: blue; */
  width: 100%;
  height: auto;
  display: flex;
  justify-content: center;
  align-content: center;
  position: relative;
  top: 5vh;
}

.arrive-img {
  width: 30vw;
  height: 40vh;
  position: relative;

  /* margin: 50px 300px 50px 200px; */
}

.text-wrap {
  position: relative;
  left: 19vw;
  top: 6vh;
  /* background-color: aqua; */
}

.station-wrap {
  top: 10vh;
}
</style>
