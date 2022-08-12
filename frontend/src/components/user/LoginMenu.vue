<template>
  <div class="box main-box">
    <div class="guide-wrap">
      <vue-writer
        :array="['티켓을 선택하여 로그인하세요!']"
        :typeSpeed="70"
        :iterations="1"
      />
    </div>
    <div class="content-wrap">
      <div id="left" class="ticket-wrap">
        <router-link :to="{ name: 'login-social' }" class="button-ticket">
          <div class="name">
            <h1><span style="font-size: 50px">정기권</span> 구입</h1>
          </div>
          <div class="image">
            <img
              src="~@/assets/아이콘/[아이콘]정기권_상단.png"
              class="image-ticket"
            />
          </div>
        </router-link>
      </div>
      <div id="right" class="ticket-wrap">
        <router-link :to="{ name: 'login-single' }" class="button-ticket">
          <div class="name">
            <h1><span style="font-size: 50px">1회권</span> 구입</h1>
          </div>
          <div class="image">
            <img
              src="~@/assets/아이콘/[아이콘]1회권_상단.png"
              class="image-ticket"
            />
          </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.content-wrap {
  height: 85%;
}
.ticket-wrap {
  height: 100%;
  width: 50%;
  display: flex;
  justify-content: center;
  align-content: center;
}
.button-ticket {
  margin: auto;
  width: 80%;
  height: 80%;
  background: #d1d8df;
  border-radius: 10px;
}
.button-ticket:hover {
  box-shadow: 0px 10px 0px #72ab45;
}
.name {
  width: 100%;
  height: 50%;
  display: flex;
  align-items: center;
  text-align: center;
  justify-content: center;
  margin: auto;
}
.image {
  height: 50%;
  display: flex;
  justify-content: center;
  align-content: center;
}
.image-ticket {
  width: 70%;
  height: 100%;
  margin: auto;
}
#left {
  float: left;
}
#right {
  float: right;
}
</style>
