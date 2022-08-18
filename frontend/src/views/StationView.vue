<template>
  <router-view />
  <DiaryNav></DiaryNav>
</template>

<script>
import DiaryNav from "@/components/diary/DiaryIcon.vue";
export default {
  components: {
    DiaryNav,
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.background {
  background-image: url("~@/assets/[03_티켓확인]타는곳.png");
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
