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
            <div class="share-screen">
              <user-video :streamManager="state.publisher2" />
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
            <div class="chat-header"></div>
            <div class="chat-box">{{ state.screen_msg }}</div>
            <div class="chat-footer">
              <input type="text" class="chat-input" v-model="message" />
              <button class="submit-btn" @click="submit_msg()">전송</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 룰렛 컴포넌트 및 라이어 게임 컴포넌트 시작 -->
    <RouletteComponent></RouletteComponent>
    <button id="show-liargame-modal" @click="startLiarGame">
      라이어 게임 시작
    </button>
    <transition name="modal">
      <LiarGame
        v-if="state.showModal"
        @close="endLiarGame"
        v-bind:categoryChosen="state.categoryChosen"
        @decideCategory="decideKeyword"
      >
        <template v-slot:header>
          <h3>라이어 게임 제시어는</h3>
        </template>
        <template v-slot:body> {{ state.liargameKeyword }}입니다.</template>
      </LiarGame>
    </transition>
    <!-- 끝 -->
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./video/UserVideo";
import { ref, reactive } from "@vue/reactivity";
import { useStore } from "vuex";
import { onMounted, watch } from "@vue/runtime-core";
// import { reactive, ref } from "@vue/reactivity";/
import { getRoomDetails } from "@/api/webrtc.js";

// 라이어 게임 및 룰렛 관련 import 시작
import { liarGameKeywords } from "@/const/const";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import RouletteComponent from "./RouletteComponent.vue";
import LiarGame from "./LiarGame";
// 끝

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = "https://" + "i7a406.p.ssafy.io" + ":5443";
const OPENVIDU_SERVER_SECRET = "A406";
export default {
  name: "App",

  components: {
    UserVideo,
    RouletteComponent,
    LiarGame,
  },

  setup() {
    const store = useStore();
    var message = ref("");

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
      // 게임 관련 state 시작
      showModal: false,
      liargameCategory: "",
      liargameKeyword: "",
      stompClient: null,
      categoryChosen: false,
      // 게임 관련 state 끝
      mySessionId: 123,
      myUserName: "Participant" + Math.floor(Math.random() * 100),
    });

    // 게임 관련 methods 시작
    function connect() {
      var socket = new SockJS("/api/ws");
      state.stompClient = Stomp.over(socket);
      console.log(state.stompClient);
      state.stompClient.connect({}, onConnected, onError);
    }

    function onConnected() {
      // Subscribe to the Public Topic
      state.stompClient.subscribe(
        `/sub/rooms/${store.state.roomStore.roomId}`,
        onMessageReceived
      );
      // Tell your username to the server
      state.stompClient.send(
        `/pub/rooms/${store.state.roomStore.roomId}`,
        {},
        JSON.stringify({
          roomId: store.state.roomStore.roomId,
          participantId: store.state.roomStore.participantId,
          name: store.state.userStore.name,
          message: `Hello it's me ${store.state.userStore.name}`,
        })
      );
    }

    function onError(error) {
      console.log(
        "Could not connect to WebSocket server. Please refresh this page to try again!",
        error
      );
    }

    function sendMessage(event, keyword, liar) {
      if (keyword && state.stompClient) {
        var chatMessage = {
          roomId: store.state.roomStore.roomId,
          participantId: store.state.roomStore.participantId,
          name: store.state.userStore.name,
          message: `keyword=${keyword}&liar=${liar}`,
        };
        state.stompClient.send(
          `/pub/rooms/${store.state.roomStore.roomId}/messages`,
          {},
          JSON.stringify(chatMessage)
        );
      }
      event.preventDefault();
    }

    function onMessageReceived(payload) {
      var message = JSON.parse(payload.body).message;
      if (message.includes("keyword")) {
        let words = message.split("&");
        let liar = words[1].substr(5);
        state.categoryChosen = true;
        state.showModal = true;
        console.log(liar);
        if (store.state.roomStore.participantId == liar) {
          state.liargameKeyword = "당신은 라이어";
        } else {
          state.liargameKeyword = words[0].substr(8);
        }
      }
    }

    onMounted(() => {
      connect();
    });

    // 라이어 게임 시작
    let startLiarGame = () => {
      state.showModal = true;
    };

    let decideKeyword = (category) => {
      state.liargameCategory = category;
      state.categoryChosen = true;
      getRoomDetails(
        store.state.userStore.token,
        store.state.roomStore.roomId,
        ({ data }) => {
          // 카테고리에 대한 키워드 선정 및 라이어 선정
          let participantList = data.participantList;
          let liar =
            participantList[Math.floor(Math.random() * participantList.length)]
              .participantId;
          state.liargameKeyword =
            liarGameKeywords["keywords"][state.liargameCategory][
              Math.floor(
                Math.random() *
                  liarGameKeywords["keywords"][state.liargameCategory].length
              )
            ];
          sendMessage(event, state.liargameKeyword, liar);
        },
        (error) => {
          console.log(error);
        }
      );
    };

    let endLiarGame = () => {
      state.liargameKeyword = "";
      state.liargmaeCategory = "";
      state.showModal = false;
      state.categoryChosen = false;
    };
    // 게임 관련 methods 끝

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

      state.session.on("signal:my-chat", (event) => {
        state.screen_msg = event.data;
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
      });
    };

    watch(state.screen_msg, (new_msg, old_msg) => {
      console.log("watch hi!");
      console.log(new_msg);
      console.log(old_msg);
    });

    //화면 공유
    var screen_share = () => {
      console.log("화면공유!!");
      var getToken = (mySessionId) => {
        return createSession(mySessionId).then((sessionId) =>
          createToken(sessionId)
        );
      };

      // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
      var createSession = (sessionId) => {
        return new Promise((resolve, reject) => {
          axios
            .post(
              `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
              JSON.stringify({
                customSessionId: sessionId,
              }),
              {
                auth: {
                  username: "OPENVIDUAPP",
                  password: OPENVIDU_SERVER_SECRET,
                },
              }
            )
            .then((response) => response.data)
            .then((data) => resolve(data.id))
            .catch((error) => {
              if (error.response.status === 409) {
                resolve(sessionId);
              } else {
                console.warn(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
                );
                if (
                  window.confirm(
                    `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                  )
                ) {
                  location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
                }
                reject(error.response);
              }
            });
        });
      };

      // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
      var createToken = (sessionId) => {
        return new Promise((resolve, reject) => {
          axios
            .post(
              `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
              {},
              {
                auth: {
                  username: "OPENVIDUAPP",
                  password: OPENVIDU_SERVER_SECRET,
                },
              }
            )
            .then((response) => response.data)
            .then((data) => resolve(data.token))
            .catch((error) => reject(error.response));
        });
      };

      state.OV = new OpenVidu();
      state.sessionScreen = state.OV.initSession();
      getToken().then((token) => {
        state.sessionScreen
          .connect(token)
          .then(() => {
            state.publisher2 = state.OV.initPublisher("html-element-id", {
              videoSource: "screen",
            });

            state.publisher2.once("accessAllowed", (event) => {
              console.log("event: " + event);
              state.publisher2.stream
                .getMediaStream()
                .getVideoTracks()[0]
                .addEventListener("ended", () => {
                  console.log('User pressed the "Stop sharing" button');
                });
              state.sessionScreen.publish(state.publisher2);
            });

            state.publisher2.once("accessDenied", (event) => {
              console.log("accessDenied event: " + event);
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
      });

      console.log("screen_share : ~~~~", state.sessionScreen);
      console.log("screen_share : ~~~~", state.publisher2);
    }; // end of share

    //세션에 들어갈 때

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

      state.session.on("publisherStartSpeaking", (event) => {
        const array = event.connection.data.split('"');
        const tmp = array[3].split(",");
        const targetPlayerId = tmp[1];
        console.log(event.connection.data);
        console.log(targetPlayerId);
        // if (state.playerId === targetPlayerId) {
        //   // state.playerMe.isTalking = true;
        // } else {
        //   for (let i = 0; i < state.playersGameInfo.length; i++) {
        //     if (state.playersGameInfo[i].playerId === targetPlayerId) {
        //       state.playersGameInfo[i].isTalking = true;
        //       break;
        //     }
        //   }
        // }
      });
      state.session.on("publisherStopSpeaking", (event) => {
        const array = event.connection.data.split('"');
        const tmp = array[3].split(",");
        const targetPlayerId = tmp[1];
        console.log(event.connection.data);
        console.log(targetPlayerId);
        // if (state.playerId === targetPlayerId) {
        //   state.playerMe.isTalking = false;
        // } else {
        //   for (let i = 0; i < state.playersGameInfo.length; i++) {
        //     if (state.playersGameInfo[i].playerId === targetPlayerId) {
        //       state.playersGameInfo[i].isTalking = false;
        //       break;
        //     }
        //   }
        // }
      });
    }; //end of join Session

    var updateMainVideoStreamManager = (stream) => {
      if (state.mainStreamManager === stream) return;
      state.mainStreamManager = stream;
    };

    return {
      state,
      message,
      startLiarGame,
      decideKeyword,
      endLiarGame,
      joinSession,
      updateMainVideoStreamManager,
      camera_toggle,
      mic_toggle,
      screen_share,
      submit_msg,
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
  background-color: blue;
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
}

.chat-box {
  width: 100%;
  height: 78%;
  background-color: beige;
}

.chat-footer {
  width: 100%;
  height: 12%;
  background-color: cornflowerblue;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  position: relative;
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

#show-liargame-modal {
  position: absolute;
  left: 60%;
  top: 30%;
}
</style>
