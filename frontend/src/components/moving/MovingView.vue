<template>
  <div class="hello">
    <!-- :style="{ backgroundImage: require(`../assets/moving2.png`) }" -->
    <!-- <img
      :src="require(`@/assets/${cur}`)"
      alt="이동중"
      @click="ShowDefaultRotate"
    /> -->

    <div id="demo">
      <!-- <transition name="fade" mode="out-in"> -->
      <transition name="slide-fade">
        <img
          :src="require(`@/assets/${cur}`)"
          alt="이동중"
          v-if="show"
          @change="change_img()"
        />
      </transition>
    </div>
  </div>
</template>

<script>
import { onMounted } from "@vue/runtime-core";
import { ref } from "@vue/reactivity";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
// import { onMounted } from "@vue/runtime-core";
export default {
  name: "HelloWorld",
  // el: "#demo",
  // watch: {
  //   show(newShow, oldShow) {
  //     console.log("new:" + newShow);
  //     console.log("old:" + oldShow);
  //     console.log(this.ObjectCnt);
  //     setTimeout(() => {
  //       this.show = !this.show;
  //       // this.ObjectCnt = (this.ObjectCnt + 1) % 3;
  //       this.change_img;
  //     }, 2000);
  //   },
  // },
  setup() {
    const store = useStore();
    var show = ref(true);
    var ObjectArray = [];
    var ObjectCnt = ref(0);
    ObjectArray.push("moving1.png");
    ObjectArray.push("moving2.png");
    ObjectArray.push("moving3.png");
    var cur = ref("moving1.png");
    const router = useRouter();
    var change_img = () => {
      // console.log("num:" + num);
      // console.log(show.value);
      show.value = !show.value;
      cur.value = ObjectArray[1];

      setTimeout(() => {
        change_img2();
      }, 1300);
    };
    var change_img2 = () => {
      cur.value = ObjectArray[1];
      show.value = !show.value;

      setTimeout(() => {
        show.value = !show.value;
        cur.value = ObjectArray[2];
      }, 3000);

      setTimeout(() => {
        change_img3();
      }, 1300);
    };

    var change_img3 = () => {
      show.value = !show.value;
      setTimeout(() => {
        show.value = !show.value;
        console.log("push!push!");
        console.log(store.state.locationStore.location_url);
        router.push({ name: store.state.locationStore.location_url });
      }, 3000);
    };

    onMounted(() => {
      var audio_0 = new Audio(require("@/assets/Train2.wav"));
      audio_0.play();
      var audio_1 = new Audio(require("@/assets/Train1.wav"));
      audio_1.play();
      setTimeout(() => {
        // console.log("hi");
        change_img();
      }, 2000);
    });

    return {
      cur,
      show,
      change_img,
      ObjectCnt,
    };
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* .hello {
  width: 1920px;
  height: 1080px;
  background-image: url("../assets/moving1.png");
} */

.slide-fade-enter-active {
  transition: all 0.3s ease;
}
.slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active below version 2.1.8 */ {
  transform: translateX(10px);
  opacity: 0;
}

img {
  width: 100vw;
  height: 100vh;
}
</style>
