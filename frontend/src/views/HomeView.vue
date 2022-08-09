<template>
  <div class="background">
    <div class="banner">
      <div class="title">나뭇잎마을 다이어리</div>
    </div>
    <div class="button">
      <router-link :to="{ name: 'login-menu' }" class="start-btn"
        >시 작</router-link
      >
    </div>
  </div>
</template>

<script>
export default {
  name: "HelloWorld",
  props: {
    msg: String,
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.background {
  background-image: url("~@/assets/[00_시작]메인화면.png");
}

.button {
  position: relative;
  top: 15%;
}
</style>
