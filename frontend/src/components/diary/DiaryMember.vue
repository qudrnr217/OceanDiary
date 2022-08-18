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

        <router-link :to="{ name: 'station-map' }">
          <img
            src="@/assets/아이콘/[아이콘]노선도_버튼.png"
            class="icon-line"
            alt="노선도"
          />
        </router-link>
      </div>
      <div class="box-contents">
        <img src="@/assets/다이어리.png" class="img-diary" />
        <div class="left">
          <div class="img">
            <img
              src="@/assets/아이콘/[아이콘]조개머니.png"
              class="card"
              alt="조개머니"
            />
          </div>
          <div class="user-info">
            <div class="name">이름 : {{ userInfo.name }}</div>
            <br />
            <div class="birth">생년월일 : {{ userInfo.birth }}</div>
            <br />
            <div class="email">이메일 : {{ userInfo.email }}</div>
            <br />
            <div class="visited-at">
              마지막 방문일 : {{ userInfo.visitedAt }}
            </div>
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
            <div class="first">
              <div class="stamp">
                <img :src="stampsDOM[0].stampPath" />
                <div class="stamp-detail">
                  <div class="stamp-date">
                    {{ stampsDOM[0].date }}
                  </div>
                  <div class="stamp-time">
                    {{ stampsDOM[0].totalTime }}
                  </div>
                  <div class="stamp-type">{{ stampsDOM[0].title }}에서</div>
                </div>
              </div>
            </div>
            <div class="second">
              <div class="stamp">
                <img
                  :src="stampsDOM[1].stampPath"
                  v-if="stampsDOM[1].stampPath != ``"
                />
                <div class="stamp-detail">
                  <div class="stamp-date">
                    {{ stampsDOM[1].date }}
                  </div>
                  <div class="stamp-time">
                    {{ stampsDOM[1].totalTime }}
                  </div>
                  <div class="stamp-type">{{ stampsDOM[1].title }}에서</div>
                </div>
              </div>
            </div>
          </div>
          <div class="middle">
            <div class="middle-center">
              <div class="stamp" v-if="stampsDOM[4].stampPath != ``">
                <img :src="stampsDOM[4].stampPath" />
                <div class="stamp-detail">
                  <div class="stamp-date">
                    {{ stampsDOM[4].date }}
                  </div>
                  <div class="stamp-time">
                    {{ stampsDOM[4].totalTime }}
                  </div>
                  <div class="stamp-type">{{ stampsDOM[4].title }}에서</div>
                </div>
              </div>
            </div>
          </div>
          <div class="bottom">
            <div class="first">
              <div class="stamp" v-if="stampsDOM[2].stampPath != ``">
                <img :src="stampsDOM[2].stampPath" />
                <div class="stamp-detail">
                  <div class="stamp-date">
                    {{ stampsDOM[2].date }}
                  </div>
                  <div class="stamp-time">
                    {{ stampsDOM[2].totalTime }}
                  </div>
                  <div class="stamp-type">{{ stampsDOM[2].title }}에서</div>
                </div>
              </div>
            </div>
            <div class="second">
              <div class="stamp" v-if="stampsDOM[3].stampPath != ``">
                <img :src="stampsDOM[3].stampPath" />
                <div class="stamp-detail">
                  <div class="stamp-date">
                    {{ stampsDOM[3].date }}
                  </div>
                  <div class="stamp-time">
                    {{ stampsDOM[3].totalTime }}
                  </div>
                  <div class="stamp-type">{{ stampsDOM[3].title }}에서</div>
                </div>
              </div>
            </div>
          </div>
          <div class="paging">
            <img
              src="@/assets/아이콘/[아이콘]이전페이지.png"
              class="icon-before"
              alt="이전페이지"
              @click="movePage(0)"
            />
            <img
              src="@/assets/아이콘/[아이콘]다음페이지.png"
              class="icon-next"
              alt="다음페이지"
              @click="movePage(1)"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getDiaryContents } from "@/api/diary.js";
import { names, indexes, stampImages } from "@/const/const.js";
import { reactive } from "@vue/reactivity";
import { onMounted } from "vue";
import { useStore } from "vuex";
export default {
  setup() {
    const fakeData = [
      {
        id: 1,
        category: "CAFE",
        exitTime: "2022-08-17 11:11:11",
        totalTime: "11:31:19",
      },
      {
        id: 2,
        category: "OCEAN",
        exitTime: "2022-08-17 11:11:11",
        totalTime: "06:31:19",
      },
      {
        id: 3,
        category: "FESTIVAL",
        exitTime: "2022-08-17 11:11:11",
        totalTime: "02:31:19",
      },
      {
        id: 4,
        category: "FESTIVAL",
        exitTime: "2022-08-17 11:11:11",
        totalTime: "11:31:19",
      },
      {
        id: 5,
        category: "HOUSE",
        exitTime: "2022-08-17 11:11:11",
        totalTime: "11:31:19",
      },
      {
        id: 6,
        category: "HOUSE",
        exitTime: "2022-08-17 11:11:11",
        totalTime: "02:31:19",
      },
      {
        id: 7,
        category: "CAFE",
        exitTime: "2022-08-17 11:11:11",
        totalTime: "11:31:19",
      },
    ];
    const store = useStore();
    const userInfo = reactive({
      name: "",
      birth: "",
      email: "",
      visitedAt: "",
    });
    // DOM 과 바인딩할 변수
    const stampsDOM = reactive([
      {
        id: 0,
        title: "",
        date: "",
        totalTime: "",
        stampPath: "",
      },
      {
        id: 0,
        title: "",
        date: "",
        totalTime: "",
        stampPath: "",
      },
      {
        id: 0,
        title: "",
        date: "",
        totalTime: "",
        stampPath: "",
      },
      {
        id: 0,
        title: "",
        date: "",
        totalTime: "",
        stampPath: "",
      },
      {
        id: 0,
        title: "",
        date: "",
        totalTime: "",
        stampPath: "",
      },
    ]);
    // API 결과를 저장할 변수
    const stampsAPI = [];
    // API 결과를 바인딩된 변수에 넣어주는 함수
    let pageIndex = 0;
    const refreshStamp = () => {
      for (var i = 0; i < 5; i++) {
        if (pageIndex + i >= stampsAPI.length) {
          stampsDOM[i] = {
            id: 0,
            title: "",
            date: "",
            totalTime: "",
            stampPath: "",
          };
        } else stampsDOM[i] = stampsAPI[pageIndex + i];
      }
    };
    const movePage = (flag) => {
      if (flag === 0 && pageIndex != 0) pageIndex -= 5;
      else if (flag === 1 && pageIndex + 5 < stampsAPI.length) pageIndex += 5;
      refreshStamp();
    };
    const getToken = () => {
      const token = store.state.userStore.token;
      if (token == "") {
        console.log(`토큰 정보가 없습니다!(${token})`);
        return null;
      }
      return token;
    };
    const getUserId = () => {
      const userId = store.state.userStore.userId;
      if (userId == "") {
        console.log(`사용자 ID가 없습니다!(${userId})`);
        return null;
      }
      return userId;
    };
    // 00. DiaryContent 들을 가져온다.
    getDiaryContents(
      getToken(),
      getUserId(),
      // 01. 성공 시, 다음을 처리한다.
      (response) => {
        // 02. 현재 사용자의 기본 정보를 가져온다.
        userInfo.name = response.data.userInfo.name;
        userInfo.email = response.data.userInfo.email;
        userInfo.birth = response.data.userInfo.birth;
        userInfo.visitedAt = response.data.userInfo.visitedAt;
        // 03. 데이터를 파싱하여, 스탬프의 등급을 포함한 데이터를 저장한다.
        for (var item of fakeData) {
          // 03-00. 데이터 값을 가져온다.
          const id = item.id;
          const totalTime = item.totalTime;
          const date = item.exitTime.split(" ")[0];
          const index = indexes[item.category.toLowerCase()];

          // 03-01. 등급(size)을 결정한다.
          let size = 0;
          const hour = Number(totalTime.split(":")[0]);
          if (hour >= 10) size = 3;
          else if (hour >= 6 && hour < 10) size = 2;
          else if (hour >= 2 && hour < 6) size = 1;
          else size = 0;

          // 03-02. 형식에 맞게 데이터를 구성하고, stampsAPI 에 붙여준다.
          const data = {};
          data.id = id;
          data.title = names[index];
          data.totalTime = totalTime;
          data.date = date;
          data.stampPath = require(`@/assets/아이콘/${stampImages[index][size]}`);
          stampsAPI.push(data);
        }
        // 04. stampsAPI를 stampsDOM으로 옮겨준다.
        refreshStamp();
      },
      (error) => {
        console.log(error);
      }
    );
    onMounted(() => {
      console.log(stampsDOM);
    });
    return {
      userInfo,
      stampsDOM,
      pageIndex,
      movePage,
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
.icon-line {
  width: 100px;
  height: 40px;
  float: right;
  margin: auto 20px;
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
  height: 35%;
}
.button-modify {
  float: right;
  width: 20%;
}
.btn {
  height: 15%;
}
.stamp {
  width: 50%;
  height: 100%;
  /* transform: rotate(10deg); */
}
.stamp-img {
  display: flex;
}
.top {
  height: 30%;
  top: 2%;
  position: relative;
  display: flex;
}
.middle {
  height: 25%;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}
.bottom {
  height: 30%;
  position: relative;
  display: flex;
}
.paging {
  height: 15%;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}
.first {
  width: 50%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  transform: rotate(20deg);
}
.second {
  width: 50%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  transform: rotate(-20deg);
}
.middle-center {
  width: 50%;
  height: 100%;
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
}
.icon-before {
  width: 4%;
  height: 30%;
  margin: auto 10px;
  cursor: pointer;
}
.icon-next {
  width: 4%;
  height: 30%;
  margin: auto 10px;
  cursor: pointer;
}
</style>
