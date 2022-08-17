<template>
  <div class="chat-main">
    <div class="my-chat" v-if="connectionId === data.from">
      <div class="my-name">{{ data.name }}</div>
      <div class="my-data">{{ data.data }}</div>
    </div>
    <div class="others-chat" v-else>
      <div class="others-name">{{ data.name }}</div>
      <div class="others-data">{{ data.data }}</div>
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
  padding: 8px;
  left: 50%;
}

.others-chat {
  position: relative;
  padding: 8px;
}

.my-content {
  width: 40%;
  height: 100%;
  background: #3b81ee;
  border-radius: 30px;
}

.others-name {
  background: #e5e5e9;
  border-radius: 30px;
}

.my-data {
  width: 40%;
  height: auto;
  background: #3b81ee;
  color: white;
  border-radius: 30px;
  padding: 15px;
}

.others-data {
  width: 30%;
  height: auto;
  background: #e5e5e9;
  border-radius: 30px;
  padding: 15px;
}
</style>
