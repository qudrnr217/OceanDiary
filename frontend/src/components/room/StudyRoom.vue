<template>
  <div class="total-wrap">
    <div class="title-wrap">
      <img src="@/assets/아이콘/coffe_icon.png" alt="" class="title-icon" />
      <div class="title-name">두 시의 도서관 스터디</div>
      <div class="exit-btn">
        <button class="button-next">나가기</button>
      </div>
    </div>
    <div class="user">
      <div class="user-wrap">
        <div class="camera-wrap">
          <div class="user1"></div>
          <div class="user2"></div>
          <div class="user3"></div>
          <div class="user4"></div>
          <div class="user5"></div>
          <div class="user6"></div>
        </div>
        <div class="chat-wrap">
          <div class="share">
            <div class="share-icons">
              <img
                src="@/assets/아이콘/[아이콘]배경음악_ON.png"
                alt=""
                class="bgm-icon"
              />
              <img
                src="@/assets/아이콘/[아이콘]마이크_ON.png"
                alt=""
                class="mic-icon"
              />
              <img
                src="@/assets/아이콘/[아이콘]카메라_ON.png"
                alt=""
                class="camera-icon"
              />
              <img
                src="@/assets/아이콘/[아이콘]화면공유.png"
                alt=""
                class="share-icon"
              />
              <img
                src="@/assets/아이콘/[아이콘]설정.png"
                alt=""
                class="config-icon"
              />
            </div>
          </div>
          <div class="chat"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.total-wrap {
  width: 100vw;
  height: 100vh;
}

.title-wrap {
  width: 100%;
  height: 7%;
  background-color: red;

  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.title-icon {
  width: 5%;
  position: relative;
  left: 2%;
}

.title-name {
  width: 47%;
  position: relative;
  left: 3%;
  font-size: 2vw;
}

.exit-btn {
  width: 100%;

  display: flex;
  justify-content: flex-end;
  align-items: center;
  /* background-color: blueviolet; */

  position: relative;
  right: 3%;
}

.user {
  width: 100%;
  height: 90%;
  display: flex;
  justify-content: center;
  align-items: flex-start;
}

.user-wrap {
  width: 90%;
  height: 97%;
  background-color: burlywood;
  padding: 1rem;

  background: rgba(255, 255, 255, 0.5);
  border-radius: 6px;
}

.button-next {
  width: 7vw;
  height: 5vh;
  font-size: 2vw;
  background: #f7d794;
  border-radius: 8px;
}

.camera-wrap {
  width: 100%;
  height: 22%;
  position: relative;
  left: 0%;
  background-color: aquamarine;

  display: flex;
  justify-content: flex-start;
  align-items: center;

  position: relative;
  right: 0.5%;
}

.user1 {
  width: 15.4%;
  height: 95%;
  background-color: brown;
  border-radius: 10px;
  position: relative;
}
.user2 {
  width: 15.4%;
  height: 95%;
  background-color: brown;
  position: relative;
  left: 1.5%;
  border-radius: 10px;
}
.user3 {
  width: 15.4%;
  height: 95%;
  background-color: brown;
  position: relative;
  left: 3%;
  border-radius: 10px;
}
.user4 {
  width: 15.4%;
  height: 95%;
  background-color: brown;
  position: relative;
  left: 4.5%;
  border-radius: 10px;
}
.user5 {
  width: 15.4%;
  height: 95%;
  background-color: brown;
  position: relative;
  left: 6%;
  border-radius: 10px;
}
.user6 {
  width: 15.4%;
  height: 95%;
  background-color: brown;
  position: relative;
  left: 7.5%;
  border-radius: 10px;
}

.chat-wrap {
  width: 100%;
  height: 78%;
  background-color: chartreuse;
}
.share {
  width: 66.5%;
  height: 100%;
  background-color: blueviolet;
}
.share-icons {
  width: 100%;
  height: 12%;
  background-color: red;
  position: relative;
  top: 88%;

  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.bgm-icon {
  width: 5%;
  position: relative;
  left: 25%;
}
.mic-icon {
  width: 5%;
  position: relative;
  left: 30%;
}
.camera-icon {
  width: 5%;
  position: relative;
  left: 35%;
}
.share-icon {
  width: 5%;
  position: relative;
  left: 40%;
}
.config-icon {
  width: 5%;
  position: relative;
  left: 45%;
}
</style>
