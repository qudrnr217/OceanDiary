<template>
  <div class="center">
    <div class="box main-box">
      <div class="box-header">
        <div class="logo">
          <img
            src="@/assets/아이콘/[아이콘]나의다이어리.png"
            class="icon-symbol"
          />
          나의 다이어리
        </div>
        <router-link :to="{ name: 'end' }">
          <img
            src="@/assets/아이콘/[아이콘]종료버튼.png"
            class="icon-exit"
            alt="종료버튼"
          />
        </router-link>
      </div>
      <div class="box-contents">
        <img src="@/assets/다이어리.png" class="img-diary" />
        <div class="left">
          <div class="ticket">
            <div id="ticket-top">
              <div id="ticket-left">
                <table>
                  <tr>
                    <th colspan="2">
                      <span style="font-size: 25px">1회권</span>
                    </th>
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
                <img
                  src="~@/assets/아이콘/[아이콘]조개.png"
                  class="ticket-icon"
                />
              </div>
            </div>
            <div id="ticket-bottom" style="font-size: 15px; line-height: 150%">
              <span style="color: red">! 주의사항 !</span><br />종료 시 기록이
              삭제됩니다.
            </div>
          </div>
          <div class="user-info">
            <div class="name">이름 : {{ userName }}</div>
          </div>
          <div class="btn">
            <img
              src="@/assets/아이콘/[아이콘]수정버튼.png"
              class="button-modify"
              alt="수정버튼"
            />
          </div>
        </div>
        <div class="right">
          <div class="top">
            <div class="middle-center">
              <div class="stamp">
                <img :src="stampInfo.stampPath" />
                <div class="stamp-detail">
                  <div class="stamp-date">
                    {{ year }}-{{ month }}-{{ date }}
                  </div>
                  <div class="stamp-time">
                    {{ stampInfo.totalTime }}
                  </div>
                  <div class="stamp-type">{{ stampInfo.title }}에서</div>
                </div>
              </div>
            </div>
          </div>
          <div class="middle">
            <div class="middle-center">
              <div class="str">
                나만의 기록을 남기고 싶다면, <br />
                아래 버튼을 클릭해주세요.
              </div>
            </div>
          </div>
          <router-link :to="{ name: 'login-social' }">
            <div class="bottom">
              <img
                src="@/assets/아이콘/[아이콘]카드발급버튼.png"
                class="icon-create"
                alt="카드발급버튼"
              />
            </div>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { reactive } from "vue";
import { names, indexes, stampImages } from "@/const/const.js";
export default {
  setup() {
    const day = new Date();
    const year = day.getFullYear();
    const month = day.getMonth() + 1;
    const date = day.getDate();

    const store = useStore();

    const stampInfo = reactive({
      totalTime: "",
      title: "",
      stampPath: "",
    });

    const totalTime = store.state.stampStore.totalTime;
    const category = store.state.stampStore.category;
    const index = indexes[category.toLowerCase()];

    let size = 0;
    const hour = Number(totalTime.split(":")[0]);
    if (hour >= 10) size = 3;
    else if (hour >= 6 && hour < 10) size = 2;
    else if (hour >= 2 && hour < 6) size = 1;
    else size = 0;

    stampInfo.totalTime = totalTime;
    stampInfo.title = names[index];
    stampInfo.stampPath = require(`@/assets/아이콘/${stampImages[index][size]}`);

    const userName = store.state.userStore.name;
    return {
      stampInfo,
      userName,
      year,
      month,
      date,
    };
  },
};
</script>

<style scoped>
.main-box {
  height: 70%;
  padding: 40px 80px;
}
.box-header {
  height: 15%;
  font-size: 50px;
}
.box-contents {
  height: 85%;
  position: relative;
}
.left {
  width: 40%;
  height: 90%;
  top: 5%;
  left: 5%;
  position: absolute;
}
.right {
  width: 48%;
  height: 90%;
  top: 2%;
  right: 1%;
  position: absolute;
}
.icon-symbol {
  width: 50px;
  height: 50px;
  margin-right: 20px;
}
.icon-exit {
  width: 100px;
  height: 40px;
  float: right;
}
.logo {
  display: flex;
  float: left;
}
.img {
  height: 50%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.img-diary {
  width: 100%;
  height: 100%;
}
.card {
  width: 70%;
  height: auto;
}
.user-info {
  height: 20%;
}
.name {
  height: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 20px;
}
.button-modify {
  float: right;
  width: 20%;
}
.btn {
  height: 10%;
}
.stamp {
  width: 50%;
  height: auto;
  bottom: 10%;
  /* transform: rotate(10deg); */
}
.stamp-img {
  display: flex;
}
.top {
  height: 50%;
  top: 2%;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}
.middle {
  height: 20%;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}
.str {
  width: 100%;
  color: grey;
  font-size: 18px;
}
.bottom {
  height: 20%;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}
.middle-center {
  width: 56%;
  height: 100%;
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
}
.content-wrap {
  height: 70%;
  display: flex;
  justify-content: center;
  align-content: center;
}
.ticket {
  margin: 30px;
  width: 90%;
  height: 45%;
  background: #ffe3e8;
  border-radius: 20px;
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
.ticket-icon {
  height: 80px;
  padding: 30px 30px;
}
.icon-create {
  width: 40%;
  height: 70%;
}
</style>
