<template>
  <div class="clock-wrap">
    <div id="clock">
      <p class="date">
        {{ state.date }}
      </p>
      <p class="time">
        {{ state.time }}
      </p>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
export default {
  setup() {
    var state = reactive({
      time: undefined,
      data: undefined,
    });
    var week = ["SUN", "MON", "TUE", "WEN", "THU", "FRI", "SAT"];

    var updateTime = () => {
      var cd = new Date();
      state.time =
        zeroPadding(cd.getHours(), 2) +
        ":" +
        zeroPadding(cd.getMinutes(), 2) +
        ":" +
        zeroPadding(cd.getSeconds(), 2);
      state.date =
        zeroPadding(cd.getFullYear(), 4) +
        "-" +
        zeroPadding(cd.getMonth() + 1, 2) +
        "-" +
        zeroPadding(cd.getDate(), 2) +
        " " +
        week[cd.getDay()];
    };

    var zeroPadding = (num, digit) => {
      var zero = "";
      for (var i = 0; i < digit; i++) {
        zero += 0;
      }
      return (zero + num).slice(-digit);
    };
    setInterval(updateTime, 1000);

    return {
      state,
      week,
    };
  },
};
</script>

<style>
.clock-wrap {
  width: 100%;
  height: 100%;
  position: relative;

  /* background-color: aqua; */
}

#clock {
  width: 100%;
  margin: 0 auto;
  height: auto;
  /* outline: 3px solid orange; */
  padding: 10px;
  /* background-color: black; */
  font-size: 4rem;
  color: black;
}

.date {
  display: flex;
  justify-content: center;
  align-items: center;
}

.time {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
