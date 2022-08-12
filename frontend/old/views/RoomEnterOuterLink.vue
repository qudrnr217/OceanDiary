<template>
  <div>
    <div class="this_background"></div>
    <div class="my_diary"></div>
    <div class="entry_modal"><room-enter></room-enter></div>
  </div>
</template>

<script>
import RoomEnter from "@/components/room/RoomEnter.vue";
export default {
  name: "RoomEnterOuterLink",
  components: {
    RoomEnter,
  },
};
</script>

<style scoped>
.my_diary {
  position: absolute;
  width: 172px;
  height: 114px;
  left: 1700px;
  top: 50px;

  background: url("~@/assets/아이콘/icon_ticket.png");
}
.this_background {
  position: absolute;
  width: 1920px;
  height: 1080px;
  left: 0px;
  top: 0px;

  background: url("~@/assets/arrival_ocean.png");
}
.entry_modal {
  position: absolute;
  width: 1200px;
  height: 942px;
  left: 408px;
  top: 69px;
}
</style>
