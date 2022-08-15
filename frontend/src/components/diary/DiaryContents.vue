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
        <div class="right">스탬프 목록 : {{ userInfo.stamps }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import { getDiaryContents } from "@/api/diary.js";
// import { useRouter } from "vue-router";
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
      stamps: [],
    });
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
    onMounted(() => {
      console.log(getUserId());
      getDiaryContents(
        getToken(),
        getUserId(),
        (response) => {
          console.log(response);
          userInfo.name = response.data.userInfo.name;
          userInfo.email = response.data.userInfo.email;
          userInfo.birth = response.data.userInfo.birth;
          userInfo.visitedAt = response.data.userInfo.visitedAt;
          userInfo.stamps = response.data.stamps;
        },
        (error) => {
          console.log(error);
        }
      );
    });
    return {
      userInfo,
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
  width: 40%;
  top: 5%;
  right: 5%;
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
</style>
