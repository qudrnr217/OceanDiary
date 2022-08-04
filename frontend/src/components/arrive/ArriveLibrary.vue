<template>
  <div class="background">
    <div class="banner">
      <!-- <img
        src="../assets/아이콘/[아이콘]정기권_상단.png"
        alt="티켓사진"
        class="ticket"
      > -->
    </div>
    <div class="text-box">
      <div class="name-wrap">
        <div class="button-name">역무원</div>
      </div>
      <div class="text-wrap">
        <vue-writer
          :array="['이번 역은 도서관 역입니다. 즐거운 시간 보내시길 바랍니다.']"
          :typeSpeed="70"
          :iterations="1"
        />
      </div>
      <div class="button-wrap">
        <div class="button-next">다음</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "HelloWorld",
  props: {
    msg: String,
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.background {
  background-image: url("~@/assets/[05_도착]도서관.png");
}
.banner {
  height: 60vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.button-next {
  background: #aabc61;
}
</style>
