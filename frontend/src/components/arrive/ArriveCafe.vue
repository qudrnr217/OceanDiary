<template>
  <div class="background">
    <div class="banner">
      <!-- <img
        src="../assets/아이콘/[아이콘]정기권_상단.png"
        alt="티켓사진"
        class="ticket"
      /> -->
    </div>
    <div class="text-box">
      <div class="name-wrap">
        <div class="button-name">역무원</div>
      </div>
      <div class="text-wrap">
        <vue-writer
          :array="['이번 역은 카페 역입니다. 즐거운 시간 보내시길 바랍니다.']"
          :typeSpeed="70"
          :iterations="1"
        />
      </div>
      <div class="button-wrap">
        <!-- <div class="button-next">다음</div> -->
        <!-- <router-link to="/cafe_bg/home" class="button-next">다음</router-link> -->
        <button class="button-next" @click="post_title()">다음</button>
      </div>
    </div>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import { useStore } from "vuex";

export default {
  name: "HelloWorld",
  setup() {
    const router = useRouter();
    const store = useStore();
    var post_title = () => {
      store.commit("locationStore/SET_LOCATION_NAME", "카페");
      console.log(store.state.locationStore.location_name);
      router.push("/cafe_bg/home");
    };

    return {
      post_title,
    };
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.background {
  background-image: url("~@/assets/[06_방목록]카페.png");
}
.banner {
  height: 60vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.button-next {
  background: #f7d794;
}
</style>
