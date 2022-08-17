<template>
  <div class="chat-content">{{ data.data }}</div>
</template>

<script>
export default {
  props: ["data"],
  setup(props) {
    console.log(props.data);
  },
};
</script>

<style></style>
