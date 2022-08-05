<template>
  <div>
    <div class="this_shadow"></div>

    <div>
      <div class="this_board"></div>
    </div>
    <div class="icon_coffee"></div>
    <div class="study_sangsung">스터디 생성</div>
    <div class="icon_lock"></div>
    <div class="rectangle_password"></div>
    <input type="password" class="input_password input_style" />
    <div class="this_title">제목 :</div>
    <input class="input_title input_style" />
    <div class="rectangle_title"></div>
    <div class="this_rule">규칙</div>
    <textarea
      name=""
      id=""
      cols="30"
      rows="10"
      class="input_rule input_style"
    ></textarea>
    <div class="rectangle_rule"></div>
    <div class="this_members">인원 :</div>
    <input
      type="number"
      value="3"
      min="1"
      max="6"
      class="input_myung input_style"
    />
    <div class="this_myung">명</div>
    <div class="rectangle_myung"></div>
    <div class="image_input"></div>
    <div class="image_text">이미지 :</div>
    <div class="image_notification">파일 선택</div>
    <div class="image_thumbnail"></div>
    <div class="generate_shadow"></div>
    <div class="generate_button"></div>
    <div class="generate_text">생성</div>
  </div>
</template>

<script>
export default {
  name: "RoomCreate",
};
</script>

<style scoped>
.input_style {
  border: none;
  background: transparent;
  z-index: 100;
}
.this_shadow {
  position: absolute;
  width: 1190px;
  height: 871px;
  left: 10px;
  top: 0px;

  background: #d1d8df;
  border-radius: 20px;
}
.this_board {
  position: absolute;
  width: 1200px;
  height: 854px;
  left: 0px;
  top: 0px;

  background: #f5f5f5;
  border-radius: 20px;
}
.icon_coffee {
  position: absolute;
  width: 50px;
  height: 50px;
  left: 60px;
  top: 50px;
  background: url("~@/assets/아이콘/[아이콘]카테고리_카페.png");
}
.study_sangsung {
  position: absolute;
  width: 422px;
  height: 105px;
  left: 137px;
  top: 23px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 48px;
  line-height: 48px;
  display: flex;
  align-items: center;

  color: #000000;
}
.icon_lock {
  position: absolute;
  width: 50px;
  height: 50px;
  left: 835px;
  top: 50px;

  background: url("~@/assets/아이콘/icon_lock.png");
}
.rectangle_password {
  position: absolute;
  width: 248px;
  height: 82px;
  left: 912px;
  top: 40px;

  border: 2px solid #d1d8df;
  border-radius: 16px;
}
.input_password {
  position: absolute;
  width: 211px;
  height: 36px;
  left: 937px;
  top: 63px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 36px;
  /* identical to box height */

  display: flex;
  align-items: center;

  color: #000000;
}
.this_title {
  position: absolute;
  width: 95px;
  height: 36px;
  left: 137px;
  top: 181px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 36px;
  /* identical to box height */

  display: flex;
  align-items: center;

  color: #000000;
}
.input_title {
  position: absolute;
  width: 811px;
  height: 36px;
  left: 322px;
  top: 181px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 36px;
  /* identical to box height */

  display: flex;
  align-items: center;

  color: #000000;
}
.rectangle_title {
  position: absolute;
  width: 865px;
  height: 82px;
  left: 295px;
  top: 158px;

  border: 2px solid #d1d8df;
  border-radius: 16px;
}
.this_rule {
  position: absolute;
  width: 95px;
  height: 36px;
  left: 137px;
  top: 299px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 36px;
  /* identical to box height */

  display: flex;
  align-items: center;

  color: #000000;
}
.input_rule {
  position: absolute;
  width: 811px;
  height: 150px;
  left: 322px;
  top: 299px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 36px;

  color: #000000;
}
.this_members {
  position: absolute;
  width: 95px;
  height: 36px;
  left: 137px;
  top: 498px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 36px;
  /* identical to box height */

  display: flex;
  align-items: center;

  color: #000000;
}
.rectangle_rule {
  position: absolute;
  width: 865px;
  height: 173px;
  left: 295px;
  top: 276px;

  border: 2px solid #d1d8df;
  border-radius: 16px;
}
.rectangle_myung {
  position: absolute;
  width: 100px;
  height: 61px;
  left: 295px;
  top: 485px;

  border: 2px solid #d1d8df;
  border-radius: 16px;
}
.this_myung {
  position: absolute;
  width: 36px;
  height: 36px;
  left: 441px;
  top: 498px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 36px;
  /* identical to box height */

  display: flex;
  align-items: center;

  color: #000000;
}
.input_myung {
  position: absolute;
  width: 70px;
  height: 77px;
  left: 330px;
  top: 478px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 36px;
  display: flex;
  align-items: center;

  color: #000000;
}
.image_text {
  position: absolute;
  width: 131px;
  height: 36px;
  left: 137px;
  top: 588px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 36px;
  /* identical to box height */

  display: flex;
  align-items: center;

  color: #000000;
}
.image_input {
  position: absolute;
  width: 182px;
  height: 61px;
  left: 295px;
  top: 575px;

  background: #d1d8df;
  border: 2px solid #d1d8df;
  border-radius: 16px;
}
.image_notification {
  position: absolute;
  width: 156px;
  height: 36px;
  left: 308px;
  top: 588px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 36px;
  /* identical to box height */

  display: flex;
  align-items: center;

  color: #57606f;
}
.image_thumbnail {
  position: absolute;
  width: 269px;
  height: 169px;
  left: 509px;
  top: 575px;

  background: url("~@/assets/thumbnail_for_createroom.png");
  border-radius: 20px;
}
.generate_button {
  position: absolute;
  width: 102px;
  height: 44px;
  left: 1058px;
  top: 757px;

  background: #f7d794;
  border-radius: 8px;
}
.generate_shadow {
  position: absolute;
  width: 102px;
  height: 44px;
  left: 1058px;
  top: 760px;

  background: #f8c65d;
  border-radius: 8px;
}
.generate_text {
  position: absolute;
  width: 64px;
  height: 24px;
  left: 1085px;
  top: 768px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 24px;
  line-height: 24px;
  /* identical to box height */

  display: flex;
  align-items: center;

  color: #000000;
}
</style>
