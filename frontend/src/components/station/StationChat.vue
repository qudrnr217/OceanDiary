<template>
  <div>
    <div class="up"></div>
    <div class="down">
      <div class="text-box">
        <div class="name-wrap">
          <div class="button-name">역무원</div>
        </div>
        <div class="text-wrap">
          <vue-writer :array="[speech]" :typeSpeed="70" :iterations="1" />
        </div>
        <div class="button-wrap">
          <router-link :to="nextLink" class="button-next">다음</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "station-chat",
  props: {
    nextLink: String,
    speech: String,
  },
};
</script>

<style scoped></style>
