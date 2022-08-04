<template>
  <div class="background">
    <div class="banner">
      <h1>바닷마을 다이어리</h1>
    </div>
    <div class="text-box">
      <div class="name-wrap">
        <div class="button-name">역무원</div>
      </div>
      <div class="text-wrap">
        <vue-writer
          :array="[
            '안녕하세요, 바닷마을 다이어리입니다. 정말 반갑습니다. 수달과 해달의 차이점을 아시나요?',
          ]"
          :typeSpeed="70"
          :iterations="1"
        />
      </div>
      <div class="button-wrap">
        <!-- <div class="button-next">다음</div> -->
        <router-link to="/station/home" class="button-next">다음</router-link>
      </div>
    </div>
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
  background-image: url("~@/assets/[00_시작]메인화면.png");
}
.banner {
  height: 60vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
