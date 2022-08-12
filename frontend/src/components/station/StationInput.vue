<template>
  <div class="center">
    <div class="text-box">
      <div class="name-wrap">
        <vue-writer
          :array="['이름이 무엇인가요 ?']"
          :typeSpeed="70"
          :iterations="1"
        />
      </div>
      <div class="text-wrap">
        <input type="text" id="name-input" />
      </div>
      <div class="button-wrap">
        <router-link
          :to="{
            name: 'station-chat',
            params: {
              nextLink: '/station/map',
              speech: '어디로 가시나요?',
            },
          }"
          class="button-next"
          >다음</router-link
        >
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.name-wrap {
  color: darkgrey;
  font-size: 24px;
}
.text-box {
  width: 500px;
}
#name-input {
  width: 100%;
  height: 50%;
  font-size: large;
  font-family: "retro";
}
</style>
