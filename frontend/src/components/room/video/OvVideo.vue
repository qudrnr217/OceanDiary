<template>
  <video autoplay class="video-size" />
</template>

<script>
export default {
  name: "OvVideo",

  props: {
    streamManager: Object,
  },

  mounted() {
    this.streamManager.addVideoElement(this.$el);
  },
};
</script>

<style scoped>
.video-size {
  width: 88%;
  height: auto;
  border-radius: 15px;
}
</style>
