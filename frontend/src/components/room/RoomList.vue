<template>
  <div class="background-wrap">
    <div class="text-box">
      <div class="text-header">
        <img
          src="@/assets/아이콘/icon_locationpoint.png"
          alt="location 아이콘"
        />
        <div class="text-title">{{ title }}</div>
        <img
          src="@/assets/아이콘/[아이콘]방생성.png"
          class="create-btn"
          alt="방생성"
          @click="create_room()"
        />
      </div>
      <div class="text-middle"></div>
    </div>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import { useStore } from "vuex";

export default {
  setup() {
    const store = useStore();
    var title = store.state.locationStore.location_name;
    const router = useRouter();
    var create_room = () => {
      console.log(store.state.locationStore.create_name);
      router.push({ name: store.state.locationStore.create_name });
    };
    return {
      title,
      create_room,
    };
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.background-wrap {
  width: 100vw;
  height: 100vh;
  /* background-color: red; */

  display: flex;
  justify-content: center;
  align-items: center;
}

.background-wrap > .text-box {
  /* background-color: blue; */
  width: 55vw;
  height: 80vh;
}

.text-box > .text-header {
  height: 10%;
  padding: 1em;
  /* background-color: purple; */

  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.text-box > .text-middle {
  height: 80%;
  /* background-color: orange; */
}

.create-btn {
  width: 3vw;
  /* height: 5vh; */
  position: relative;
  left: 83%;
}
</style>
