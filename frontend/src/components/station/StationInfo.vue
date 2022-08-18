<template>
  <div class="station-info">
    <div class="station-title-wrap">
      <img
        src="@/assets/아이콘/[아이콘]위치포인트.png"
        alt="위치아이콘"
        class="point-icon"
      />
      <span style="font-size: 40px">&nbsp;{{ name }}</span>
    </div>
    <div class="station-image-wrap">
      <img
        :src="require(`@/assets/${image}`)"
        alt="이미지"
        class="station-img"
      />
    </div>
    <div class="station-text-wrap">
      <span style="font-size: 20px">{{ explain }}</span>
      <div class="button-wrap">
        <router-link
          :to="{ name: 'train-depart', query: { dest: link } }"
          class="button-next"
          @click="clickSound()"
          >입장</router-link
        >
      </div>
    </div>
  </div>
</template>

<script scoped>
import useSound from "vue-use-sound";
import clickSfx from "@/assets/Click.wav";
export default {
  setup() {
    const [clickSound] = useSound(clickSfx);
    return {
      clickSound,
    };
  },
  props: ["name", "explain", "image", "link"],
};
</script>

<style scoped>
.station-info {
  width: 60%;
  height: 100%;
}
.station-title-wrap {
  height: 15%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.station-image-wrap {
  height: 60%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.station-text-wrap {
  padding: 20px;
  height: 20%;
  align-items: center;
  justify-content: center;
  text-align: center;
}
.point-icon {
  width: 50px;
  height: 50px;
}
.station-title {
  font-size: 40px;
}

.text-wrap {
  text-align: center;
  margin: auto;
  margin-bottom: 20px;
  white-space: nowrap;
}
.button-wrap {
  justify-content: center;
  margin-top: 20px;
}
.station-img {
  height: 100%;
  width: 80%;
  margin-top: 20px;
  margin: auto;
}
</style>
