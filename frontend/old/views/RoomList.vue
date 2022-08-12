<template>
  <div>
    <div class="this_background"></div>
    <div class="my_diary"></div>
    <div class="this_board_shadow"></div>
    <div class="this_board"></div>
    <div class="this_title">바다</div>
    <div class="this_icon"></div>
    <div class="this_create_room_button"></div>
    <div class="room_card">
      <room-card></room-card>
    </div>
    <input type="text" class="input_txt" />
  </div>
</template>

<script>
import RoomCard from "@/components/room/RoomCard.vue";
export default {
  name: "RoomList",
  components: {
    RoomCard,
  },
};
</script>

<style scoped>
.input_txt {
  position: absolute;
}
.room_card {
  position: absolute;
  width: 1040px;
  height: 200px;
  left: 460px;
  top: 244px;
  overflow-y: scroll;
  overflow-x: hidden;
}
.this_background {
  position: absolute;
  width: 1920px;
  height: 1080px;
  left: 0px;
  top: 0px;

  background: url("~@/assets/arrival_ocean.png");
}
.my_diary {
  position: absolute;
  width: 172px;
  height: 114px;
  left: 1700px;
  top: 50px;

  background: url("~@/assets/아이콘/icon_ticket.png");
}
.this_board_shadow {
  position: absolute;
  width: 1200px;
  height: 871px;
  left: 360px;
  top: 116px;

  background: #d1d8df;
  border-radius: 20px;
}
.this_board {
  position: absolute;
  width: 1200px;
  height: 854px;
  left: 360px;
  top: 116px;

  background: #f5f5f5;
  border-radius: 20px;
}
.this_title {
  position: absolute;
  width: 973px;
  height: 105px;
  left: 487px;
  top: 139px;

  font-family: "NeoDunggeunmo Pro";
  font-style: normal;
  font-weight: 400;
  font-size: 48px;
  line-height: 48px;
  display: flex;
  align-items: center;

  color: #000000;
}
.this_icon {
  position: absolute;
  width: 50px;
  height: 50px;
  left: 410px;
  top: 166px;

  background: url("~@/assets/아이콘/icon_locationpoint.png");
}
.this_create_room_button {
  position: absolute;
  width: 50px;
  height: 50px;
  left: 1460px;
  top: 166px;

  background: url("~@/assets/아이콘/icon_createroom.png");
}
</style>
