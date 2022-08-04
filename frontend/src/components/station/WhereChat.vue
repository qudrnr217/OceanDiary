<template>
  <div class="text-box">
    <div class="name-wrap">
      <div class="button-name">역무원</div>
    </div>
    <div class="text-wrap">
      <vue-writer
        :array="['어디로 가시나요?']"
        :typeSpeed="70"
        :iterations="1"
      />
    </div>
    <div class="button-wrap">
      <router-link to="/station/map" class="button-next">다음</router-link>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.text-box {
  position: relative;
  top: 55%;
}
</style>
