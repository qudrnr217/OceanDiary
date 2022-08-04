<template>
  <div class="background">
    <!-- <div class="banner"></div> -->
    <!-- <div class="text-box">
      <div class="name-wrap">
        <div class="button-name">역무원</div>
      </div>
      <div class="text-wrap">
        <vue-writer
          :array="[
            '반갑습니다 ! 승차권 확인 도와드리겠습니다. 이름은 무엇인가요?',
          ]"
          :typeSpeed="70"
          :iterations="1"
        />
      </div>
      <div class="button-wrap">
        <router-link to="/station/input" class="button-next">다음</router-link>
      </div>
    </div> -->

    <router-view />
  </div>
</template>

<script>
export default {
  name: "HelloWorld",
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.background {
  background-image: url("~@/assets/[03_티켓확인]타는곳(기존이미지).png");
}
.banner {
  height: 60vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
