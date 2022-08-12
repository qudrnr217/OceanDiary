<template>
  <div class="total-wrap">
    <div class="title-wrap">
      <img src="@/assets/아이콘/coffe_icon.png" alt="" class="title-icon" />
      <div class="title-name">두 시의 도서관 스터디</div>
      <div class="exit-btn">
        <button class="button-next" @click="joinSession()">나가기</button>
      </div>
    </div>
    <div class="user">
      <div class="user-wrap">
        <div class="camera-wrap">
          <div class="user1">
            <user-video :streamManager="state.publisher" />
          </div>
          <div class="user2">
            <user-video :streamManager="state.subscribers[0]" />
          </div>
          <div class="user3">
            <user-video :streamManager="state.subscribers[1]" />
          </div>
          <div class="user4">
            <user-video :streamManager="state.subscribers[2]" />
          </div>
          <div class="user5">
            <user-video :streamManager="state.subscribers[3]" />
          </div>
          <div class="user6">
            <user-video :streamManager="state.subscribers[4]" />
          </div>
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
                @click="camera_toggle()"
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
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./video/UserVideo";
import { reactive } from "@vue/reactivity";
import { useStore } from "vuex";
// import { reactive, ref } from "@vue/reactivity";/

axios.defaults.headers.post["Content-Type"] = "application/json";

// const OPENVIDU_SERVER_URL = "https://" + "i7a406.p.ssafy.io" + ":5443";
// const OPENVIDU_SERVER_SECRET = "A406";
export default {
  name: "App",

  components: {
    UserVideo,
  },

  setup() {
    const store = useStore();

    const state = reactive({
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      camera: true,
      openvidu_token: store.state.roomStore.openvidu_token,

      mySessionId: store.state.roomStore.sessionId,
      myUserName: "Participant" + Math.floor(Math.random() * 100),
    });
    var toggle = true;
    //카메라 On/Off
    var camera_toggle = () => {
      console.log("camera toggle!!!");
      console.log("poublisher: ", state.publisher);
      console.log("subscribers: ", state.subscribers);
      console.log("session: ", state.session);
      console.log(
        "누구냐!:?" + state.publisher.session.connection.connectionId
      );

      console.log("현재 커넥션 id: " + store.state.roomStore.connectionId);
      //publisher 일 경우
      if (
        state.publisher.session.connection.connectionId ===
        store.state.roomStore.connectionId
      ) {
        console.log("hi");
      } else if (
        state.subscribers[0].stream.connection.connectionId ===
        store.state.roomStore.connectionId
      ) {
        console.log("bye");
      }
      //subscriber 일 경우

      toggle = !toggle;
      // state.publisher.properties.publishVideo = false;

      // state.publisher.properties.publishVideo = false;
      state.publisher.publishVideo(toggle);
    };

    var joinSession = () => {
      console.log("JoinSession!!");
      console.log(state.openvidu_token);
      // --- Get an OpenVidu object ---
      state.OV = new OpenVidu();

      // --- Init a session ---
      state.session = state.OV.initSession();

      //새로운 사람 입장
      state.session.on("streamCreated", ({ stream }) => {
        console.log("subscriber 환영~~~~");
        console.log("stream: ", stream);
        const subscriber = state.session.subscribe(stream);

        state.subscribers.push(subscriber);
        console.log("subscriber: ", subscriber);
      });

      //사람 나갔을 때
      state.session.on("streamDestoryed", ({ stream }) => {
        const index = state.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          state.subscribers.splice(index, 1);
        }
      });

      state.session.on("exception", ({ exception }) => {
        console.warn(exception);
        // exception;
      });
      1;

      // --- Connect to the session with a valid user token ---

      state.session
        .connect(state.openvidu_token, {
          clientData: state.myUserName,
        })
        .then(() => {
          let publisher = state.OV.initPublisher(undefined, {
            audioSource: undefined, // The source of audio. If undefined default microphone
            videoSource: undefined, // The source of video. If undefined default webcam
            publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
            publishVideo: true, // Whether you want to start publishing with your video enabled or not
            resolution: "640x480", // The resolution of your video
            frameRate: 30, // The frame rate of your video
            insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
            mirror: false, // Whether to mirror your local video or not
          });

          state.mainStreamManager = publisher;
          state.publisher = publisher;

          state.session.publish(state.publisher);
          console.log("publisher: ", state.publisher);
          // console.log(JSON.stringify(state.publisher));
        })
        .catch((error) => {
          console.log(
            "There was an error connecting to the session:",
            error.code,
            error.message
          );
        });

      // state.session.on("publisherStartSpeaking", (event) => {
      //   const array = event.connection.data.split('"');
      //   const tmp = array[3].split(",");
      //   const targetPlayerId = tmp[1];
      // console.log(event.connection.data);
      // console.log(targetPlayerId);
      // if (state.playerId === targetPlayerId) {
      //   state.playerMe.isTalking = true;
      // } else {
      //   for (let i = 0; i < state.playersGameInfo.length; i++) {
      //     if (state.playersGameInfo[i].playerId === targetPlayerId) {
      //       state.playersGameInfo[i].isTalking = true;
      //       break;
      //     }
      //   }
      // }
      // });
      // state.session.on("publisherStopSpeaking", (event) => {
      //   const array = event.connection.data.split('"');
      //   const tmp = array[3].split(",");
      //   const targetPlayerId = tmp[1];
      //   if (state.playerId === targetPlayerId) {
      //     state.playerMe.isTalking = false;
      //   } else {
      //     for (let i = 0; i < state.playersGameInfo.length; i++) {
      //       if (state.playersGameInfo[i].playerId === targetPlayerId) {
      //         state.playersGameInfo[i].isTalking = false;
      //         break;
      //       }
      //     }
      //   }
      // });
    }; //end of join Session

    var updateMainVideoStreamManager = (stream) => {
      if (state.mainStreamManager === stream) return;
      state.mainStreamManager = stream;
    };

    return {
      state,
      joinSession,
      updateMainVideoStreamManager,
      camera_toggle,
    };
  },
};
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
  background-color: blueviolet;

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
  font-size: 1vw;
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
