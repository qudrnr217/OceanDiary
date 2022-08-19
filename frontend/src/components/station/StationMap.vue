<template>
  <div class="background">
    <div class="text-box">
      <div class="station-info-wrap">
        <station-info
          v-if="show"
          :name="names[key]"
          :explain="explains[key]"
          :image="roomImages[key]"
          :link="links[key]"
        ></station-info>
      </div>
      <div class="station-select">
        <div class="station-wrap">
          <img src="@/assets/일자노선도.png" alt="노선도" />
        </div>
        <div class="circle-wrap">
          <div class="ssafy-station"></div>
          <div class="ocean-station" @mouseover="setKey(0), hoverSound()"></div>
          <div
            class="library-station"
            @mouseover="setKey(1), hoverSound()"
          ></div>
          <div class="cafe-station" @mouseover="setKey(2), hoverSound()"></div>
          <div
            class="festival-station"
            @mouseover="setKey(3), hoverSound()"
          ></div>
          <div class="home-station" @mouseover="setKey(4), hoverSound()"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import StationInfo from "@/components/station/StationInfo.vue";
import { names, explains, roomImages, links } from "@/const/const.js";
import { ref } from "vue";
import useSound from "vue-use-sound";
import hoverSfx from "@/assets/Hover.wav";
export default {
  components: {
    StationInfo,
  },

  setup() {
    // hover에 따라 station-info의 이미지 변경
    var show = ref(false);
    var key = ref(0);
    const [hoverSound] = useSound(hoverSfx);

    const setKey = (index) => {
      show.value = true;
      key.value = index;
    };
    return {
      names,
      explains,
      roomImages,
      links,
      show,
      key,
      setKey,
      hoverSound,
    };
  },
};
</script>

<style scoped>
.text-wrap {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: auto;
}

.text-box {
  height: 700px;
  width: 1024px;
  min-width: 1024px;
}

.circle:hover {
  transform: scale(1.2);
}
.station-info-wrap {
  height: 70%;
  display: flex;
  justify-content: center;
}

/* 노선도 관련*/
.station-wrap {
  max-width: 1920px;
  width: 100%;
  height: 10vh;
  margin: 0 auto;

  position: relative;
  top: 20px;
}

.station-wrap > img {
  display: block;
  width: 100%;
}

.circle-wrap {
  max-width: 1920px;
  width: 100%;
  height: 10vh;
  margin: 0 auto;
  position: relative;
  top: -8vh;
  display: flex;
}

.circle-wrap > .ssafy-station {
  display: block;
  width: 3.1vw;
  height: 3.1vw;
  border-radius: 100%;
  position: relative;
  left: 2vw;
}

.circle-wrap > .ocean-station {
  display: block;
  width: 50px;
  height: 50px;
  border-radius: 100%;
  position: relative;
  left: 160px;
  cursor: pointer;
}
.circle-wrap > .library-station {
  display: block;
  width: 50px;
  height: 50px;
  border-radius: 100%;
  position: relative;
  left: 295px;
  cursor: pointer;
}
.circle-wrap > .cafe-station {
  display: block;
  width: 50px;
  height: 50px;
  border-radius: 100%;
  position: relative;
  left: 440px;
  cursor: pointer;
}
.circle-wrap > .festival-station {
  display: block;
  width: 50px;
  height: 50px;
  border-radius: 100%;
  position: relative;
  left: 575px;
  cursor: pointer;
}
.circle-wrap > .home-station {
  display: block;
  width: 50px;
  height: 50px;
  border-radius: 100%;
  position: relative;
  left: 715px;
  cursor: pointer;
}
</style>
