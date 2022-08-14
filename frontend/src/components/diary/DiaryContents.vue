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
          left
          <img
            src="@/assets/아이콘/[아이콘]조개머니.png"
            class="card"
            alt="조개머니"
          />
          <div class="user-info">
            유저정보
            <div class="name">이름 : {{ userInfo.name }}</div>
            <div class="birth">생년월일 : {{ userInfo.birth }}</div>
            <div class="email">이메일 : {{ userInfo.email }}</div>
            <div class="visited-at">
              마지막 방문일 : {{ userInfo.visitedAt }}
            </div>
          </div>
        </div>
        <div class="right">right</div>
      </div>
    </div>
  </div>
</template>

<script>
//import { getDiaryContents } from "@/api/diary.js";
export default {
  // setup() {
  //   const getToken = () => {
  //     const token = store.state.userStore.token;
  //     if (token == "") {
  //       console.log(`토큰 정보가 없습니다!(${token})`);
  //       return null;
  //     }
  //     return token;
  //   };
  //   onMounted(() => {
  //     getDiaryContents(
  //       getToken(),
  //       userId,
  //       (response) => {},
  //       (error) => {
  //         console.log(error);
  //       }
  //     );
  //   });
  //   return {
  //     userInfo,
  //   };
  // },
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
  top: 10%;
  left: 5%;
  position: absolute;
}
.right {
  width: 40%;
  top: 10%;
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
.img-diary {
  width: 100%;
  height: 100%;
}
.card {
  width: 100%;
  height: 100%;
}
</style>
