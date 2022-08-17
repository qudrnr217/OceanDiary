<template>
  <div class="box main-box">
    <div class="guide-wrap">
      <vue-writer
        :array="['1회용 승차권을 발급합니다!']"
        :typeSpeed="70"
        :iterations="1"
      ></vue-writer>
    </div>
    <div class="content-wrap">
      <div class="ticket">
        <div id="ticket-top">
          <div id="ticket-left">
            <table>
              <tr>
                <th colspan="2"><span style="font-size: 50px">1회권</span></th>
              </tr>
              <tr>
                <th>발급일</th>
                <td>{{ year }}.{{ month }}.{{ date }}</td>
              </tr>
              <tr>
                <th>사용처</th>
                <td>바닷마을 노선</td>
              </tr>
              <tr>
                <th>발급번호</th>
                <td>A406-0012</td>
              </tr>
            </table>
          </div>
          <div id="ticket-right">
            <img src="~@/assets/아이콘/[아이콘]조개.png" class="ticket-icon" />
          </div>
        </div>
        <div id="ticket-bottom" style="font-size: 20px; line-height: 150%">
          <span style="color: red">! 주의사항 !</span><br />로그아웃 시 기록이
          삭제됩니다.
        </div>
      </div>
    </div>
    <div class="button-wrap">
      <router-link
        :to="{
          name: 'station-chat',
          params: {
            nextLink: '/station/input',
            speech:
              '반갑습니다. 승차권 확인 도와드리겠습니다. 이름이 무엇인가요?',
          },
        }"
        class="button-metro"
        >발 급</router-link
      >
    </div>
  </div>
</template>

<script>
export default {
  setup() {
    const day = new Date();
    const year = day.getFullYear();
    const month = day.getMonth() + 1;
    const date = day.getDate();
    return { year, month, date };
  },
};
</script>

<style scoped>
.content-wrap {
  height: 70%;
  display: flex;
  justify-content: center;
  align-content: center;
}
.ticket {
  margin: 30px;
  width: 40%;
  height: 80%;
  background: #ffe3e8;
  border-radius: 20px;
}
.ticket-icon {
  height: 120px;
  padding-top: 40px;
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
  width: 60%;
  height: 100%;
  display: flex;
  margin: auto;
  align-items: center;
  justify-content: center;
}
#ticket-right {
  width: 40%;
  height: 100%;
  display: flex;
  justify-content: left;
  align-content: center;
}
.button-wrap {
  height: 15%;
  padding-right: 30px;
}
</style>
