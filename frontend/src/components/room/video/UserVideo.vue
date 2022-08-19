<template>
  <div v-if="streamManager">
    <div class="cam">
      <ov-video :stream-manager="streamManager" class="camera" />
      <div id="explain">
        <!-- <p>{{ clientData }}</p> -->
        {{ name }}
        <!-- {{participant}} -->
      </div>
    </div>
  </div>
  <div v-else>
    <img src="@/assets/x표시.png" alt="" class="empty" />
  </div>
</template>

<script>
import OvVideo from "./OvVideo";

export default {
  name: "UserVideo",

  components: {
    OvVideo,
  },

  props: {
    streamManager: Object,
    camera: Boolean,
    participantId: String,
    name: String,
  },

  computed: {
    clientData() {
      const { clientData } = this.getConnectionData();
      return clientData;
    },
  },

  // watch: {
  //   isTalking(newIsTalking) {
  //     console.log(newIsTalking);
  //   },
  // },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
  },
};
</script>

<style>
.cam {
  /* background-color: black; */
  display: flex;
  position: relative;
}
#explain {
  width: 100%;
  height: 100%;
  position: absolute;
  opacity: 0;
  color: white;
  top: 45%;
  left: 20%;
}
.cam:hover #explain {
  opacity: 1;
}

.cam:hover .camera {
  opacity: 0.3;
}

.empty {
  width: 90%;
  height: auto;
  border-radius: 10px;
}
</style>
