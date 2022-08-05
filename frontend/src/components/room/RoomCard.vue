<template>
  <div>
    <div class="card"></div>
    <div class="roomphoto"></div>
    <div class="button_rectangle_shadow"></div>
    <div class="button_rectangle"></div>
    <div class="title">ROOM TITLE</div>
    <!-- title 길이 제한 필요해요(지금 width 지정 안해줘서 방 제목 길때 창 뚫고 나올것임) -->
    <div class="onair_dot"></div>
    <div class="onair_num">4/6</div>
    <div class="entry">입장</div>
  </div>
</template>

<script>
export default {
  name: "RoomCard",
};
</script>

<style scoped>
.card {
  position: absolute;
  width: 1000px;
  height: 200px;
  left: 0px;
  top: 0px;

  background: #d1d8df;
  border-radius: 20px;
}
.button_rectangle {
  position: absolute;
  width: 102px;
  height: 44px;
  left: 879px;
  top: 131px;

  background: #f7d794;
  border-radius: 8px;
}

/* Rectangle 35 */

.button_rectangle_shadow {
  position: absolute;
  width: 102px;
  height: 44px;
  left: 879px;
  top: 134px;

  background: #f8c65d;
  border-radius: 8px;
}

/* 입장 */

.entry {
  position: absolute;
  width: 48px;
  height: 24px;
  left: 906px;
  top: 142px;

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
.title {
  position: absolute;
  height: 24px;
  left: 320px;
  top: 24px;

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
.roomphoto {
  position: absolute;
  width: 269px;
  height: 169px;
  left: 17px;
  top: 15px;

  background: url("~@/assets/tmproomthumbnail.png");
  border-radius: 20px;
}
.onair_dot {
  position: absolute;
  width: 20px;
  height: 20px;
  left: 897px;
  top: 26px;
  background: url("~@/assets/아이콘/Ellipse1.png");
}
.onair_num {
  position: absolute;
  width: 51px;
  height: 24px;
  left: 930px;
  top: 24px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 24px;
  line-height: 24px;
  /* identical to box height */

  display: flex;
  align-items: center;

  color: #cf833e;
}
</style>
