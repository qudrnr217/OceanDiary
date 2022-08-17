<template>
  <div class="background">
    <div>
      <div class="up"></div>
      <div class="down">
        <div class="text-box">
          <div class="name-wrap">
            <div class="button-name">역무원</div>
          </div>
          <div class="text-wrap">
            <vue-writer
              :array="['이번 역은 하루의 끝입니다. 방문해주셔서 감사합니다.']"
              :typeSpeed="50"
              :iterations="1"
            />
          </div>
          <div class="button-wrap">
            <router-link :to="{ name: 'main' }" class="button-next"
              >다음</router-link
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.background {
  background-image: url("@/assets/[08_로그아웃]하루끝_해달.png");
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
