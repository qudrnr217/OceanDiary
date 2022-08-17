<template>
  <div class="chat-main">
    <div class="my-chat" v-if="connectionId === data.from">
      <div class="my-name">{{ data.name }}</div>

      <div class="my-data">{{ data.data }}</div>
    </div>
    <div class="others-chat" v-else>
      <div class="others-name">{{ data.name }}</div>
      <div class="others-content">{{ data.data }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["data", "connectionId"],
  setup(props) {
    console.log(props.data);
    console.log(props.connectionId);
  },
};
</script>

<style>
.chat-content {
  width: 100%;
  height: 100%;
}

.my-chat {
  position: relative;
  left: 50%;
}

.my-content {
  width: 40%;
  height: 100%;
  background: #3b81ee;
  border-radius: 30px;
}

.my-data {
  color: white;
}
</style>
