<template>
  <div class="outer">
    <div class="user-wrap"></div>
    <div class="user" v-for="user in user_info.users" :key="user.connectionId">
      <img src="@/assets/아이콘/아바타.png" alt="" class="avatar" />
      {{ user.name }}
    </div>
  </div>
</template>

<script>
import { GetUserInfo } from "@/api/webrtc.js";
import { onMounted } from "@vue/runtime-core";
import { reactive } from "@vue/reactivity";
export default {
  props: ["roomId"],
  setup(props) {
    var user_info = reactive({
      curNum: undefined,
      users: undefined,
    });

    onMounted(() => {
      //   console.log("방정보: " + props.roomId);
      GetUserInfo(props.roomId, (response) => {
        // console.log(response);
        user_info.curNum = response.data.roomInfo.curNum;
        // console.log(response.data.participantList);
        user_info.users = response.data.participantList;
      });
    });

    return {
      user_info,
    };
  },
};
</script>

<style>
.outer {
  width: 100%;
  height: 100%;
}
.user {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding: 1%;
}
.avatar {
  width: 4%;
}
</style>
