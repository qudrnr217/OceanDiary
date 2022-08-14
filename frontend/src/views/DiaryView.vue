<template>
  <div class="background">
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
  background-image: url("~@/assets/[07_스터디종료]나의다이어리배경.png");
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
