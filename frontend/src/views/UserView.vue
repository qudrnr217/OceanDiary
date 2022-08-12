<template>
  <div class="background">
    <div class="main-wrap">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
.background {
  background-image: url("~@/assets/[02_발권]에노시마역.png");
}
.guide-wrap {
  width: 100%;
  height: 15%;
  display: flex;
  align-items: end;
  color: darkgrey;
  font-size: 24px;
  margin-left: 50px;
  line-height: 150%;
}
.content-wrap {
  width: 100%;
  display: flex;
}
</style>
