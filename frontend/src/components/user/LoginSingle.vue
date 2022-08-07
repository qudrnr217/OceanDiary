<template>
  <div class="box main-box">
    <div class="guide-wrap">
      <div class="guide-text">
        <VueWriter
          :array="['1회용 승차권은 한 번만 사용 가능합니다.']"
          :typespeed="1"
          :iterations="1"
        ></VueWriter>
      </div>
    </div>
    <div class="content-wrap">
      <div class="ticket">
        <div id="ticket-top">
          <div id="ticket-left">
            <p style="font-size: 50px">&nbsp;&nbsp; 1회권</p>
            <p style="font-size: 32px">&nbsp;&nbsp; 2022.08.03</p>
          </div>
          <div id="ticket-right">
            <img src="~@/assets/아이콘/[아이콘]조개.png" class="ticket-icon" />
          </div>
        </div>
        <div id="ticket-bottom">
          <p style="font-size: 20px">바닷마을을 방문해주셔서 감사합니다.</p>
        </div>
      </div>
    </div>
    <div class="button-wrap">
      <!-- <div class="button-metro">출 발</div> -->
      <router-link to="/station/home" class="button-metro">출 발</router-link>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.guide-wrap {
  height: 20%;
  text-align: left;
  padding-left: 30px;
  display: flex;
  align-items: center;
}
.guide-text {
  color: grey;
  font-size: 30px;
}
.content-wrap {
  display: flex;
  width: 100%;
  height: 60%;
  display: flex;
  justify-content: center;
  align-content: center;
}
.content {
  height: 100%;
  width: 50%;
  display: flex;
  justify-content: center;
  align-content: center;
  padding: 50px;
}
.content:hover {
  animation: flash;
  animation-duration: 1s; /* don't forget to set a duration! */
}
.ticket {
  width: 40%;
  height: 100%;
  background: #ffe3e8;
  border-radius: 20px;
}
.ticket-icon {
  width: 70%;
  height: 70%;
  padding-top: 30px;
}
#ticket-top {
  width: 100%;
  height: 70%;
  display: flex;
}
#ticket-bottom {
  width: 100%;
  height: 30%;
  text-align: center;
}
#ticket-left {
  width: 50%;
  height: 100%;
}
#ticket-right {
  width: 50%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-content: center;
}
.button-wrap {
  padding-right: 30px;
}
</style>
