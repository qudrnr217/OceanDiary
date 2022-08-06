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
  background-image: url("~@/assets/[06_방목록]카페.png");
  width: 100vw;
  height: 100vh;
}
.banner {
  height: 60vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
