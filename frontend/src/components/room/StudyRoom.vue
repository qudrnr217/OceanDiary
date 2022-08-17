<template>
  <div class="total-wrap">
    <div class="title-wrap">
      <img src="@/assets/아이콘/coffe_icon.png" alt="" class="title-icon" />
      <div class="title-name">두 시의 도서관 스터디</div>
      <div class="exit-btn">
        <button class="button-next" @click="LeaveSession()">나가기</button>
      </div>
    </div>
    <div class="user">
      <div class="user-wrap">
        <div class="camera-wrap">
          <div class="user1">
            <user-video
              :streamManager="state.publisher"
              :participantId="state.participantId"
              :name="state.pub_name"
            />
          </div>

          <div class="user2">
            <user-video
              :streamManager="state.subscribers[0]"
              :name="state.sub_name[0]"
            />
          </div>
          <div class="user3">
            <user-video
              :streamManager="state.subscribers[1]"
              :name="state.sub_name[1]"
            />
          </div>
          <div class="user4">
            <user-video
              :streamManager="state.subscribers[2]"
              :name="state.sub_name[2]"
            />
          </div>
          <div class="user5">
            <user-video
              :streamManager="state.subscribers[3]"
              :name="state.sub_name[3]"
            />
          </div>
          <div class="user6">
            <user-video
              :streamManager="state.subscribers[4]"
              :name="state.sub_name[4]"
            />
          </div>
        </div>
        <div class="chat-wrap">
          <div class="share">
            <div class="share-screen">
              <user-video
                :streamManager="state.publisher2"
                v-if="state.publisher2"
              />
              <time-view class="time" v-else :key="state.reload" />
            </div>

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
                @click="mic_toggle()"
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
                @click="screen_share()"
              />
              <img
                src="@/assets/아이콘/[아이콘]설정.png"
                alt=""
                class="config-icon"
              />
            </div>
          </div>
          <div class="chat">
            <div class="chat-header">
              <div
                class="participant"
                @click="state.current_title = 'participant'"
              >
                참가자
              </div>
              <div class="chat-title" @click="state.current_title = 'chat'">
                채팅
              </div>
            </div>
            <div class="chat-box" v-if="state.current_title === 'chat'">
              <chat-view v-for="ch in state.chat" :key="ch.name" :data="ch" />

              <div class="chat-footer">
                <input type="text" class="chat-input" v-model="message" />
                <button class="submit-btn" @click="submit_msg()">전송</button>
              </div>
            </div>
            <div class="user-list" v-if="state.current_title === 'participant'">
              <user-list :roomId="state.roomId" :key="state.reload" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./video/UserVideo";
import { ref, reactive } from "@vue/reactivity";
import { useStore } from "vuex";
import { onMounted, watch, watchEffect } from "@vue/runtime-core";
// import { reactive, ref } from "@vue/reactivity";/
import { LeaveRoom } from "@/api/webrtc.js";
import { useRouter } from "vue-router";
import UserList from "./component/UserList.vue";
import ChatView from "./component/ChatView.vue";
import TimeView from "./component/TimeView.vue";
axios.defaults.headers.post["Content-Type"] = "application/json";
const OPENVIDU_SERVER_URL = "https://" + "i7a406.p.ssafy.io" + ":5443";
const OPENVIDU_SERVER_SECRET = "A406";
export default {
  name: "App",

  components: {
    UserVideo,
    UserList,
    TimeView,
    ChatView,
  },

  setup() {
    const store = useStore();
    var message = ref("");
    const router = useRouter();

    const state = reactive({
      screen_msg: undefined,
      OV: undefined,
      OV2: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      camera: true,
      openvidu_token: store.state.roomStore.openvidu_token,
      sessionScreen: undefined,
      publisher2: undefined,
      mySessionId: 123,
      participantId: store.state.roomStore.participantId,
      myUserName: "Participant" + Math.floor(Math.random() * 100),
      roomId: store.state.roomStore.roomId,
      openvidu_token2: undefined,
      isScreen: false,
      screen_connection_id: undefined,
      connectionId: undefined,
      reload: 0,
      current_title: "participant",
      sub_name: [],
      pub_name: undefined,
      chat: [],
      leave_connectionId: store.state.roomStore.leave_connectionId,
    });

    onMounted(() => {
      console.log(state.openvidu_token);
      var test = state.openvidu_token;
      var test2 = test.split('"');
      var test3 = test2[0].split("sessionId=");
      var test4 = test3[1].split("&token=");
      state.mySessionId = test4[0];

      joinSession();
    });

    watch(store.state.roomStore.isScreen, () => {});
    watchEffect(() => {
      console.log("watch!!!: " + store.state.roomStore.isScreen);
    });

    //카메라 On/Off
    var v_toggle = true;
    var camera_toggle = () => {
      v_toggle = !v_toggle;
      // state.publisher.properties.publishVideo = false;

      // state.publisher.properties.publishVideo = false;
      state.publisher.publishVideo(v_toggle);
    };

    //마이크 On/Off
    var m_toggle = true;
    var mic_toggle = () => {
      console.log("마이크 On/Off!!!");
      m_toggle = !m_toggle;

      state.publisher.publishAudio(m_toggle);
    };

    //메세지 전송
    var submit_msg = () => {
      console.log("메세지 전송버튼 !!!");
      state.session
        .signal({
          data: message.value,
          to: [],
          type: "my-chat",
        })
        .then(() => {
          console.log("Message successfully sent");
        })
        .catch((error) => {
          console.error(error);
        });

      state.session.on("connectionCreated", (event) => {
        console.log("Connection@@@: ", event.connection);
      });
    };

    // var leave_screen = () => {
    //   if (state.sessionScreen) state.sessionScreen.disconnect();

    //   state.sessionScreen = undefined;
    //   state.publisher2 = undefined;
    //   // state.subscribers = [];
    //   state.OV2 = undefined;
    // };

    //화면 공유
    var screen_share = () => {
      axios({
        method: "post",
        url: `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${state.mySessionId}/connection`,
        headers: {
          "Content-type": "application/json",
          Authorization:
            "Basic " + btoa(`OPENVIDUAPP:${OPENVIDU_SERVER_SECRET}`),
        },
        data: {
          type: "WEBRTC",
          role: "PUBLISHER",
        },
      }).then((data) => {
        console.log(data);
        state.openvidu_token2 = data.data.token;
        state.screen_session_id = data.data.sessionId;
      });

      setTimeout(() => {
        state.OV2 = new OpenVidu();
        state.sessionScreen = state.OV2.initSession();
        state.sessionScreen
          .connect(state.openvidu_token2)
          .then(() => {
            var publisher2 = state.OV2.initPublisher("html-element-id", {
              videoSource: "screen",
              publishAudio: false,
            });
            state.sessionScreen.publish(publisher2);
            state.publisher2 = publisher2;
            publisher2.once("accessAllowed", (event) => {
              console.log(event);
              publisher2.stream
                .getMediaStream()
                .getVideoTracks()[0]
                .addEventListener("ended", () => {
                  console.log('User pressed the "Stop sharing" button');
                  // state.sessionScreen.unsubscribe(state.publisher2);
                  // state.sessionScreen.unpublish(state.publisher2);

                  store.commit("roomStore/SET_IS_SCREEN", false);
                  // leave_screen();
                  state.sessionScreen.disconnect();
                  // state.publisher2 = undefined;
                  axios({
                    method: "delete",
                    url: `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${state.mySessionId}/connection/${state.screen_connection_id}`,
                    headers: {
                      Authorization:
                        "Basic " +
                        btoa(`OPENVIDUAPP:${OPENVIDU_SERVER_SECRET}`),
                    },
                  }).then((data) => {
                    console.log(data);
                    state.publisher2 = undefined;
                    state.sessionScreen.disconnect();
                  });
                });
            });

            publisher2.once("accessDenied", (event) => {
              console.log(event);
              console.warn("ScreenShare: Access Denied");
            });
          })
          .catch((error) => {
            console.warn(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      }, 1000);
    }; // end of share

    //세션에 들어갈 때

    var joinSession = () => {
      state.reload += 1;
      console.log("JoinSession!!");
      console.log(state.openvidu_token);
      // --- Get an OpenVidu object ---
      state.OV = new OpenVidu();

      //대화창 초기화
      store.commit("roomStore/SET_INIT_CHAT");

      // --- Init a session ---
      state.session = state.OV.initSession();

      state.session.on("streamCreated", ({ stream }) => {
        state.reload += 1;
        console.log("subscriber 환영~~~~");
        // console.log("stream: ", stream);
        // console.log("stream: ", stream.typeOfVideo);
        if (stream.typeOfVideo === "SCREEN") {
          if (state.sessionScreen === undefined) {
            console.log(state.sessionScreen);
            state.publisher2 = undefined;
          }
          // const subscriber = state.session.subscribe(stream);
          store.commit("roomStore/SET_IS_SCREEN", true);
          console.log("화면공유 시작!!!");
          console.log("커넥션아이디!:" + stream.connection.connectionId);
          state.screen_connection_id = stream.connection.connectionId;
          state.publisher2 = state.session.subscribe(stream);
        } else {
          const subscriber = state.session.subscribe(stream);
          state.subscribers.push(subscriber);

          console.log(
            "subscribers: ",
            state.subscribers[0].stream.connection.data.split('"')[11]
          );
          console.log("subscriber: ", subscriber);
          state.sub_name.push(subscriber.stream.connection.data.split('"')[11]);
          console.log("name: " + state.sub_name);
          // console.log("chat : " + store.state.roomStore.chat);
        }

        // console.log("subscriber: ", state.subscriber);
        // console.log("participantId", state.participantId);
      });

      //사람 나갔을 때
      state.session.on("connectionDestroyed", ({ stream }) => {
        state.reload += 1;

        console.log("subscriber가 나갔습니다 ㅠㅠ!");
        state.publisher2 = undefined;

        console.log("stream: " + stream);
        // console.log(stream);

        var length = state.subscribers.length;
        console.log(state.publisher);
        for (var i = 0; i < length; i++) {
          console.log(length);
          console.log(i);
          console.log(state.subscribers[i].stream.connection.connectionId);
          // console.log(state.subscribers[i]);
          console.log("나간 커넥션 아이디: " + state.leave_connectionId);
          // state.subscribers[0] = undefined;
          if (
            state.subscribers[i].stream.connection.connectionId ===
            store.state.roomStore.leave_connectionId
          ) {
            // const index = state.subscribers.indexOf(i);
            // console.log("iiiiiiiiiiiiiiiiiiiii: " + i);
            // state.subscribers[i] = undefined;
            // if (index >= 0) {
            //   state.subscribers.splice(index, 1);
            // }
            console.log("안녕하세요!");
            state.subscribers[i] = undefined;
          }
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
            publishAudio: false, // Whether you want to start publishing with your audio unmuted or not
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
          state.pub_name =
            state.publisher.stream.connection.data.split('"')[11];
          // console.log(JSON.stringify(state.publisher));
        })
        .catch((error) => {
          console.log(
            "There was an error connecting to the session:",
            error.code,
            error.message
          );
        });

      state.session.on("signal", (event) => {
        state.screen_msg = event.data;
        // state.chat.push({
        //   data: event.data,
        //   from: event.from,
        // });

        store.commit("roomStore/SET_CHAT", {
          data: event.data,
          from: event.from.connectionId,
          name: event.from.data.split('"')[11],
        });
        console.log(store.state.roomStore.chat);
        state.chat = store.state.roomStore.chat;

        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
      });

      // state.session.on("publisherStartSpeaking", (event) => {
      //   const array = event.connection.data.split('"');
      //   const tmp = array[3].split(",");
      //   const targetPlayerId = tmp[1];
      //   console.log(event.connection.data);
      //   console.log(targetPlayerId);
      //   if (state.playerId === targetPlayerId) {
      //     // state.playerMe.isTalking = true;
      //   } else {
      //     for (let i = 0; i < state.playersGameInfo.length; i++) {
      //       if (state.playersGameInfo[i].playerId === targetPlayerId) {
      //         state.playersGameInfo[i].isTalking = true;
      //         break;
      //       }
      //     }
      //   }
      // });
      // state.session.on("publisherStopSpeaking", (event) => {
      //   const array = event.connection.data.split('"');
      //   const tmp = array[3].split(",");
      //   const targetPlayerId = tmp[1];
      //   console.log(event.connection.data);
      //   console.log(targetPlayerId);
      //   // if (state.playerId === targetPlayerId) {
      //   //   state.playerMe.isTalking = false;
      //   // } else {
      //   //   for (let i = 0; i < state.playersGameInfo.length; i++) {
      //   //     if (state.playersGameInfo[i].playerId === targetPlayerId) {
      //   //       state.playersGameInfo[i].isTalking = false;
      //   //       break;
      //   //     }
      //   //   }
      //   // }
      // });
    }; //end of join Session

    var LeaveSession = () => {
      console.log(event);
      store.commit(
        "roomStore/SET_LEAVE_CONNECTION_ID",
        store.state.roomStore.connectionId
      );
      console.log(
        "퍼블리셔 커넥션 아이디: " + store.state.roomStore.connectionId
      );

      console.log(store.state.roomStore.leave_connectionId);

      // state.reload += 1;
      // var length = state.subscribers.length;
      // for (var i = 0; i < length; i++) {
      //   if (
      //     state.subscribers[i].stream.connection.connectionId ==
      //     store.state.roomStore.connectionId
      //   ) {
      //     console.log("안녕하세요~");
      //   }
      // }

      if (state.session) {
        state.publisher = undefined;
        // state.subscribers = [];
        state.session.disconnect();
        state.OV = undefined;
        state.session = undefined;
        store.commit("roomStore/SET_INIT_CHAT");
      }
      LeaveRoom(state.roomId, state.participantId, (response) => {
        console.log(response);
        console.log("세션 나가기 성공!");
        console.log("subscribers count : ", state.subscribers);

        router.push({
          name: "room-list",
          query: { dest: "cafe" },
        });
      });
    };

    var updateMainVideoStreamManager = (stream) => {
      if (state.mainStreamManager === stream) return;
      state.mainStreamManager = stream;
    };

    return {
      state,
      message,

      joinSession,
      updateMainVideoStreamManager,
      camera_toggle,
      mic_toggle,
      screen_share,
      submit_msg,
      LeaveSession,
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
  position: relative;
}
.share {
  width: 66.5%;
  height: 100%;
  background-color: blueviolet;
  position: relative;
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

.share-icons {
  position: absolute;
  bottom: 10%;
}

.share-screen {
  width: 100%;
  height: 88%;
  /* background-color: blue; */
}

.chat {
  width: 33.5%;
  height: 100%;
  background-color: darkkhaki;
  position: absolute;
  left: 66.5%;
  bottom: 0%;
}

.chat-header {
  width: 100%;
  height: 10%;
  background-color: coral;
  display: flex;
}

.chat-box {
  width: 100%;
  height: 90%;
  background-color: beige;
  position: relative;
}

.chat-footer {
  width: 100%;
  height: 12%;
  background-color: cornflowerblue;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  position: absolute;
  top: 88%;
}

.chat-input {
  position: absolute;
  width: 75%;
  height: 85%;
}

.submit-btn {
  position: absolute;
  width: 23%;
  height: 90%;
  left: 77%;
}

.participant {
  width: 50%;
  height: auto;
  background-color: #f7d794;
  display: flex;
  justify-content: center;
  align-items: center;
}

.chat-title {
  width: 50%;
  height: auto;
  background-color: cadetblue;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
