<template>
  <div class="roulette_main">
    <button @click="calculatePrize()">시작</button>
    <input v-model="state.segment" />
    <button @click="addSegment()">Add Segment</button>
    <button @click="deleteSegment()">Delete Segment</button>
    <canvas id="rouletteCanvas" width="880" height="300">
      Canvas not supported, use another browser.
    </canvas>
    <img id="prizePointer" src="@/assets/이미지/basic_pointer.png" alt="V" />
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import { Winwheel } from "@/const/winwheel";
import { onMounted } from "@vue/runtime-core";

export default {
  name: "RouletteComponent",

  setup() {
    // const store = useStore();
    const state = reactive({
      winner: null,
      segment: null,
      segments: [],
      theWheel: null,
    });

    function addSegment() {
      var letters = "0123456789ABCDEF";
      var color = "#";
      for (var i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
      }
      console.log(state.theWheel);
      state.theWheel.addSegment({
        text: state.segment,
        fillStyle: color,
      });
      state.segments.push({
        text: state.segment,
      });
      state.theWheel.draw();
    }

    function deleteSegment() {
      state.theWheel.deleteSegment();
      state.theWheel.draw();
      state.segments.pop();
    }

    function calculatePrize() {
      let segmentNumber = Math.floor(Math.random()) * state.segments.length + 1;
      let stopAt = state.theWheel.getRandomForSegment(segmentNumber);
      state.winner = segmentNumber;
      state.theWheel.animation.stopAngle = stopAt;
      state.theWheel.startAnimation();
    }

    onMounted(() => {
      state.theWheel = new Winwheel({
        canvasId: "rouletteCanvas",
        numSegments: 0,
        lineWidth: 3,
        strokeStyle: "#0D56A6",
        segments: state.segments,
        animation: {
          type: "spinToStop",
          duration: 3,
          spins: 3,
        },
      });
      state.theWheel.draw();
    });

    return {
      state,
      addSegment,
      deleteSegment,
      calculatePrize,
    };
  },
};
</script>
<style scoped>
.roulette_main {
  position: absolute;
  left: 40%;
  bottom: 40%;
}
.rouletteCanvas {
  position: absolute;
  left: 0%;
  bottom: 0%;
}
#prizePointer {
  position: absolute;
  left: 47%;
  top: 3%;
  z-index: 999;
}
</style>
