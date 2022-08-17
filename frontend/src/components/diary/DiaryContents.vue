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
        <img
          src="@/assets/아이콘/[아이콘]노선도_버튼.png"
          class="icon-line"
          alt="노선도"
        />
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
          <!-- <div v-for="stamp of stamps" :key="stamp.id" class="stamp">
            <img class="stamp-img" :src="stamp.stampPath" />
            <div class="stamp-detail">
              <div class="stamp-date">{{ stamp.date }}</div>
              <div class="stamp-time">{{ stamp.totalTime }}</div>
              <div class="stamp-type">{{ stamp.title }}에서</div>
            </div>
          </div> -->
          <div class="top">
            <div class="first">
              <div class="stamp">
                <div class="stamp-detail">
                  <div class="stamp-date">{{ stamps[0].date }}</div>
                  <div class="stamp-time">
                    {{ stamps[pageIndex + 0].totalTime }}
                  </div>
                  <div class="stamp-type">
                    {{ stamps[pageIndex + 0].title }}에서
                  </div>
                </div>
              </div>
            </div>
            <div class="second">
              <div class="stamp">
                <img class="stamp-img" :src="stamps[pageIndex + 1].stampPath" />
                <div class="stamp-detail">
                  <div class="stamp-date">{{ stamps[pageIndex + 1].date }}</div>
                  <div class="stamp-time">
                    {{ stamps[pageIndex + 1].totalTime }}
                  </div>
                  <div class="stamp-type">
                    {{ stamps[pageIndex + 1].title }}에서
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="middle">
            <div class="middle-center">
              <div class="stamp">
                <img class="stamp-img" :src="stamps[pageIndex + 2].stampPath" />
                <div class="stamp-detail">
                  <div class="stamp-date">{{ stamps[pageIndex + 2].date }}</div>
                  <div class="stamp-time">
                    {{ stamps[pageIndex + 2].totalTime }}
                  </div>
                  <div class="stamp-type">
                    {{ stamps[pageIndex + 2].title }}에서
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="bottom">
            <div class="first">
              <div class="stamp">
                <img class="stamp-img" :src="stamps[pageIndex + 3].stampPath" />
                <div class="stamp-detail">
                  <div class="stamp-date">{{ stamps[pageIndex + 3].date }}</div>
                  <div class="stamp-time">
                    {{ stamps[pageIndex + 3].totalTime }}
                  </div>
                  <div class="stamp-type">
                    {{ stamps[pageIndex + 3].title }}에서
                  </div>
                </div>
              </div>
            </div>
            <div class="second">
              <div class="stamp">
                <img class="stamp-img" :src="stamps[pageIndex + 4].stampPath" />
                <div class="stamp-detail">
                  <div class="stamp-date">{{ stamps[pageIndex + 4].date }}</div>
                  <div class="stamp-time">
                    {{ stamps[pageIndex + 4].totalTime }}
                  </div>
                  <div class="stamp-type">
                    {{ stamps[pageIndex + 4].title }}에서
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getDiaryContents } from "@/api/diary.js";
// import { useRouter } from "vue-router";
import { names, indexes, stampImages } from "@/const/const.js";
import { reactive } from "@vue/reactivity";
import { onMounted } from "vue";
import { useStore } from "vuex";
export default {
  setup() {
    const store = useStore();
    const userInfo = reactive({
      name: "",
      birth: "",
      email: "",
      visitedAt: "",
    });
    // id, title, totalTime, stampPath
    const stamps = reactive([]);
    const pageIndex = 0;

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
      console.log(userId);
      return userId;
    };
    getDiaryContents(
      getToken(),
      getUserId(),
      (response) => {
        console.log(response);
        userInfo.name = response.data.userInfo.name;
        userInfo.email = response.data.userInfo.email;
        userInfo.birth = response.data.userInfo.birth;
        userInfo.visitedAt = response.data.userInfo.visitedAt;
        //stamps.value = response.data.stamps;
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
            totalTime: "02:31:19",
          },
          {
            id: 3,
            category: "CAFE",
            exitTime: "2022-08-17 11:11:11",
            totalTime: "11:31:19",
          },
          {
            id: 4,
            category: "OCEAN",
            exitTime: "2022-08-17 11:11:11",
            totalTime: "02:31:19",
          },
          {
            id: 5,
            category: "CAFE",
            exitTime: "2022-08-17 11:11:11",
            totalTime: "11:31:19",
          },
          {
            id: 6,
            category: "OCEAN",
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
        for (var item of fakeData) {
          const id = item.id;
          const category = item.category;
          const totalTime = item.totalTime;
          const date = item.exitTime.split(" ")[0];
          let size = 0;
          const hour = Number(totalTime.split(":")[0]);
          if (hour >= 10) size = 3;
          else if (hour >= 6 && hour < 10) size = 2;
          else if (hour >= 2 && hour < 6) size = 1;
          else size = 0;
          const index = indexes[category.toLowerCase()];
          const data = {};
          data.id = id;
          data.title = names[index];
          data.totalTime = totalTime;
          data.date = date;
          console.log(index + "," + size);
          console.log(stampImages[index][size]);
          data.stampPath = require(`@/assets/아이콘/${stampImages[index][size]}`);
          console.log(data.stampPath);
          stamps.push(data);
        }
      },
      (error) => {
        console.log(error);
      }
    );
    onMounted(() => {
      console.log(getUserId());
      console.log("테스트 : " + stamps[0].stampPath);
      console.log(stamps[0].date);
    });
    return {
      userInfo,
      stamps,
      pageIndex,
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
  top: 5%;
  right: 1%;
  position: absolute;
}
.icon-symbol {
  width: 50px;
  height: 50px;
  margin-right: 20px;
}
.icon-line {
  width: 130px;
  height: 50px;
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
  height: 50%;
  /* transform: rotate(10deg); */
}
.stamp-img {
  display: flex;
}
.top {
  height: 33%;
  position: relative;
  display: flex;
}
.middle {
  height: 33%;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
}
.bottom {
  height: 33%;
  position: relative;
  display: flex;
}
.first {
  width: 50%;
  height: 100%;
}
.second {
  width: 50%;
  height: 100%;
}
.middle-center {
  width: 50%;
  height: 100%;
  position: absolute;
}
</style>
