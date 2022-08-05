<template>
  <div>
    <div class="this_shadow"></div>
    <div class="this_board"></div>
    <div class="icon_coffee"></div>
    <div class="eclipse"></div>
    <!-- 이거 모양 찌그러지는데 어떻게 할지 논의 -->
    <div class="room_count">1/6</div>
    <div class="copy_link"></div>
    <div class="room_title">두 시의 카페 스터디</div>
    <div class="room_preview"></div>
    <div class="rule_constant_text">규칙 :</div>
    <div class="rule_variant_text">캠 ON, 하루 4시간 이상 필수</div>
    <div class="enter_password">비밀번호를 입력하세요.</div>
    <div class="password_input"></div>
    <div class="enter_shadow"></div>
    <div class="enter_button"></div>
    <div class="enter_text">입장</div>
  </div>
</template>

<script>
export default {
  name: "RoomEnter",
};
</script>

<style scoped>
.enter_shadow {
  position: absolute;
  width: 102px;
  height: 39.22px;
  left: 1045px;
  top: 858.07px;

  background: #f8c65d;
  border-radius: 8px;
}
.enter_text {
  position: absolute;
  width: 49px;
  height: 19.19px;
  left: 1071px;
  top: 865.24px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 24px;
  line-height: 24px;
  display: flex;
  align-items: center;

  color: #000000;
}
.enter_button {
  position: absolute;
  width: 102px;
  height: 39.22px;
  left: 1045px;
  top: 855.07px;

  background: #f7d794;
  border-radius: 8px;
}
.password_input {
  position: absolute;
  width: 679px;
  height: 68.54px;
  left: 260px;
  top: 767.3px;

  border: 2px solid #d1d8df;
  border-radius: 16px;
}
.enter_password {
  position: absolute;
  width: 425px;
  height: 30px;
  left: 414px;
  top: 701px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 36px;
  display: flex;
  align-items: center;

  color: #000000;
}
.rule_variant_text {
  position: absolute;
  width: 446px;
  height: 30.09px;
  left: 322px;
  top: 565.82px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 36px;
  display: flex;
  align-items: center;

  color: #000000;
}
.rule_constant_text {
  position: absolute;
  width: 95px;
  height: 30.09px;
  left: 149px;
  top: 565.82px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 36px;
  display: flex;
  align-items: center;

  color: #000000;
}
.room_preview {
  position: absolute;
  width: 692px;
  height: 389.36px;
  left: 254px;
  top: 129.79px;

  background: url("~@/assets/room_preview.png");
  border-radius: 20px;
}
.room_title {
  position: absolute;
  width: 771px;
  height: 113px;
  left: 137px;
  top: 2px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 48px;
  line-height: 48px;
  display: flex;
  align-items: center;

  color: #000000;
}
.copy_link {
  position: absolute;
  width: 50px;
  height: 41.79px;
  left: 1112px;
  top: 42.63px;

  background: url("~@/assets/아이콘/[아이콘]링크복사.png");
}
.room_count {
  position: absolute;
  width: 73.46px;
  height: 20.06px;
  left: 986.54px;
  top: 53.49px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 24px;
  line-height: 24px;
  display: flex;
  align-items: center;

  color: #cf833e;
}
.eclipse {
  position: absolute;
  width: 28.81px;
  height: 16.72px;
  left: 939px;
  top: 55.17px;

  background: #cf833e;
}
.this_shadow {
  position: absolute;
  width: 1200px;
  height: 942px;
  left: 0px;
  top: 0px;

  background: #d1d8df;
  border-radius: 20px;
}
.this_board {
  position: absolute;
  width: 1200px;
  height: 923.61px;
  left: 0px;
  top: 0px;

  background: #f5f5f5;
  border-radius: 20px;
}
.icon_coffee {
  position: absolute;
  width: 50px;
  height: 41.79px;
  left: 50px;
  top: 30.09px;

  background: url("~@/assets/아이콘/[아이콘]카테고리_카페.png");
}
</style>
