<template>
  <div class="text-box">
    <!-- <div class="text-wrap">
      <vue-writer
        :array="['어디로 가시나요?']"
        :typeSpeed="70"
        :iterations="1"
      />
    </div> -->
    <div class="station-info-wrap" :key="componentKey">
      <station-info
        v-if="show"
        :name="name"
        :explanation="explanation"
        :img="img"
      ></station-info>
    </div>

    <div class="station-wrap">
      <img src="@/assets/일자노선도.png" alt="노선도" />
    </div>
    <div class="circle-wrap">
      <div class="ssafy-station"></div>
      <div class="ocean-station" @mouseover="show_info1(), forceRender()"></div>
      <div
        class="library-station"
        @mouseover="show_info2(), forceRender()"
      ></div>
      <div class="cafe-station" @mouseover="show_info3(), forceRender()"></div>
      <div
        class="festival-station"
        @mouseover="show_info4(), forceRender()"
      ></div>
      <div class="home-station" @mouseover="show_info5(), forceRender()"></div>
    </div>
    <!-- <div class="button-wrap">
      <div class="button-next">다음</div>
    </div> -->
  </div>
</template>

<script>
import StationInfo from "@/components/station/StationInfo.vue";
import { ref } from "vue";
import { useStore } from "vuex";
export default {
  components: {
    StationInfo,
  },

  setup() {
    const store = useStore();
    var show = ref(false);
    var name = ref("");
    var explanation = ref("");
    var componentKey = ref(0);
    var img = ref("");
    var show_info1 = () => {
      //   console.log("마우스 오버!");
      show.value = true;
      name.value = "바다";
      explanation.value = "~ 바닷가 ~";
      img.value = require("@/assets/[06_방목록]바다.png");
      store.commit("locationStore/SET_LOCATION_URL", "station_ocean");
    };
    var show_info2 = () => {
      //   console.log("마우스 오버!");
      show.value = true;
      name.value = "도서관";
      explanation.value = "~ 도서관 ~";
      img.value = require("@/assets/[05_도착]도서관.png");
      store.commit("locationStore/SET_LOCATION_URL", "station_library");
    };
    var show_info3 = () => {
      //   console.log("마우스 오버!");
      show.value = true;
      name.value = "카페";
      explanation.value = "~ 커피향이 가득한 바닷마을 인기 카페 ~";
      img.value = require("@/assets/[05_도착]카페.png");
      store.commit("locationStore/SET_LOCATION_URL", "station_cafe");
    };
    var show_info4 = () => {
      //   console.log("마우스 오버!");
      show.value = true;
      name.value = "축제";
      explanation.value = "~ 축제 ~";
      img.value = require("@/assets/[06_방목록]축제.png");
      store.commit("locationStore/SET_LOCATION_URL", "station_festival");
    };
    var show_info5 = () => {
      //   console.log("마우스 오버!");
      show.value = true;
      name.value = "집";
      explanation.value = "~ 집 ~";
      img.value = require("@/assets/[06_방목록]집.png");
      store.commit("locationStore/SET_LOCATION_URL", "station_house");
    };

    var forceRender = () => {
      componentKey.value += 1;
    };

    return {
      show,
      name,
      explanation,
      show_info1,
      show_info2,
      show_info3,
      show_info4,
      show_info5,
      componentKey,
      forceRender,
      img,
    };
  },
};
</script>

<style scoped>
.text-wrap {
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  /* background-color: blue; */
  width: 100%;
  height: auto;
}

.text-box {
  position: relative;
  /* bottom: 50vh; */
  top: 10vh;
  height: 75vh;
  left: 3vw;
  width: 70vw;
  /* background-color: red; */
}

.circle:hover {
  transform: scale(1.2);
}

.station-wrap {
  max-width: 1920px;
  width: 100%;
  height: 10vh;
  margin: 0 auto;
  /* background-color: purple; */

  position: relative;
  top: 14vh;
}

.station-wrap > img {
  display: block;
  width: 100%;
}

.station-info-wrap {
  width: 100%;
  /* background-color: brown; */
  height: 50vh;
}

.circle-wrap {
  max-width: 1920px;
  width: 100%;
  height: 10vh;
  margin: 0 auto;
  /* background-color: red; */

  position: relative;
  bottom: -4vh;
  display: flex;
}

.circle-wrap > .ssafy-station {
  display: block;
  width: 3.1vw;
  height: 3.1vw;
  /* background-color: orange; */

  border-radius: 100%;

  position: relative;
  left: 2vw;
}

.circle-wrap > .ocean-station {
  display: block;
  width: 3.1vw;
  height: 3.1vw;
  /* background-color: purple; */

  border-radius: 100%;

  position: relative;
  left: 11.5vw;

  cursor: pointer;
}
.circle-wrap > .library-station {
  display: block;
  width: 3.1vw;
  height: 3.1vw;
  /* background-color: bisque; */

  border-radius: 100%;

  position: relative;
  left: 21vw;
  cursor: pointer;
}
.circle-wrap > .cafe-station {
  display: block;
  width: 3.1vw;
  height: 3.1vw;
  /* background-color: aqua; */

  border-radius: 100%;

  position: relative;
  left: 31vw;
  cursor: pointer;
}
.circle-wrap > .festival-station {
  display: block;
  width: 3.1vw;
  height: 3.1vw;
  /* background-color: deeppink; */

  border-radius: 100%;

  position: relative;
  left: 41vw;
  cursor: pointer;
}
.circle-wrap > .home-station {
  display: block;
  width: 3.1vw;
  height: 3.1vw;
  /* background-color: gold; */

  border-radius: 100%;

  position: relative;
  left: 50.5vw;
  cursor: pointer;
}

.button-wrap {
  height: 6vh;
  background-color: red;
}
</style>
