<template>
  <div class="text-box">
    <div class="name-wrap">
      <!-- <div class="button-name">역무원</div> -->
    </div>
    <div class="text-wrap">
      <vue-writer
        :array="['이름이 무엇인가요 ?']"
        :typeSpeed="70"
        :iterations="1"
      />
    </div>
    <input
      type="text"
      class="input-wrap"
      id="name"
      placeholder="이름을 입력해주세요"
    />
    <div class="button-wrap">
      <router-link to="/station/where" class="button-next">다음</router-link>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.text-box {
  margin: 35vh 20vw;
}

.text-wrap {
  position: relative;
  bottom: 80px;
}

.input-wrap {
  text-align: center;
  width: 25vw;
  height: 10vh;
  border-radius: 10px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  position: relative;
  bottom: 60px;
}

.button-wrap {
  position: relative;
  bottom: 8vh;
}
</style>
