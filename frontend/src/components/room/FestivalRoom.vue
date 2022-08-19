<template>
  <div class="total-wrap">
    <div class="title-wrap">
      <img src="@/assets/아이콘/coffe_icon.png" alt="" class="title-icon" />
      <div class="title-name">Festival</div>
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
            <!-- <user-video
              v-for="sub in state.subscribers"
              :key="sub.stream.connection.connectionId"
              :streamManager="sub"
            /> -->
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
            <div class="empty"></div>
            <div class="chat">
              <div class="chat-header">
                <div
                  class="participant"
                  @click="state.current_title = 'participant'"
                >
                  <img src="@/assets/아이콘/참가자.png" alt="" />
                  참가자
                </div>
                <div class="chat-title" @click="state.current_title = 'chat'">
                  <img src="@/assets/아이콘/채팅.png" alt="" />
                  채팅
                </div>
                <div class="game-wrap">
                  <!-- 라이어 게임 컴포넌트 시작 -->
                  <button
                    class="button-metro"
                    id="show-liargame-modal"
                    @click="startLiarGame"
                  >
                    라이어 게임 GO
                  </button>
                  <transition name="modal">
                    <LiarGame
                      v-if="state.showLiarGameModal"
                      @close="endLiarGame"
                      v-bind:categoryChosen="state.categoryChosen"
                      @decideCategory="decideKeyword"
                    >
                      <template v-slot:header>
                        <h3>라이어 게임 제시어는</h3>
                      </template>
                      <template v-slot:body>
                        {{ state.liargameKeyword }}</template
                      >
                    </LiarGame>
                  </transition>
                  <!-- 끝 -->
                </div>
                <div class="game-wrap">
                  <!-- 콜마이네임 게임 컴포넌트 시작 -->
                  <button
                    class="button-metro"
                    id="show-liargame-modal"
                    @click="setUpCallMyNameGame"
                  >
                    콜마이네임 게임 GO
                  </button>
                  <transition name="modal">
                    <CallMyNameGame
                      v-if="state.showCallMyNameGameModal"
                      :participants="state.participants"
                      :showCallMyNameGameModal="state.showCallMyNameGameModal"
                      @close="state.showCallMyNameGameModal = false"
                      @startCallMyNameGame="startCallMyNameGame"
                    >
                    </CallMyNameGame>
                  </transition>
                  <!-- 끝 -->
                </div>
              </div>
              <div class="chat-box" v-if="state.current_title === 'chat'">
                <div class="chat-content">
                  <chat-view
                    v-for="ch in state.chat"
                    :key="ch.name"
                    :data="ch"
                    :connectionId="state.connectionId"
                  />
                </div>

                <div class="chat-footer">
                  <div class="line"></div>
                  <input
                    type="text"
                    class="chat-input"
                    v-model="message"
                    @keyup.enter="submit_msg()"
                  />
                  <img
                    src="@/assets/아이콘/메세지보내기.png"
                    alt=""
                    class="submit-btn"
                    @click="submit_msg()"
                  />
                  <!-- <button class="submit-btn" @click="submit_msg()">전송</button> -->
                </div>
              </div>
              <div
                class="participant-box"
                v-if="state.current_title === 'participant'"
              >
                <div
                  class="user-list"
                  v-if="state.current_title === 'participant'"
                >
                  <user-list
                    :roomId="state.roomId"
                    :key="state.participant_reload"
                    :callMyName="state.callMyName"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="share-icons">
            <img
              v-if="is_img"
              src="@/assets/스크린아이콘/[아이콘]소리ON.png"
              alt=""
              class="bgm-icon"
              @click="OnMusic(1)"
            />
            <img
              v-if="!is_img"
              src="@/assets/스크린아이콘/[아이콘]소리OFF.png"
              alt=""
              class="bgm-icon"
              @click="OnMusic(0)"
            />

            <img
              src="@/assets/스크린아이콘/[아이콘]마이크ON.png"
              alt=""
              class="mic-icon"
              @click="mic_toggle()"
              v-if="is_mic"
            />
            <img
              src="@/assets/스크린아이콘/[아이콘]마이크OFF.png"
              alt=""
              class="mic-icon"
              @click="mic_toggle()"
              v-if="!is_mic"
            />

            <img
              src="@/assets/스크린아이콘/[아이콘]영상ON.png"
              alt=""
              class="camera-icon"
              @click="camera_toggle()"
              v-if="is_camera"
            />
            <img
              src="@/assets/스크린아이콘/[아이콘]영상OFF.png"
              alt=""
              class="camera-icon"
              @click="camera_toggle()"
              v-if="!is_camera"
            />
            <img
              src="@/assets/스크린아이콘/[아이콘]화면공유OFF.png"
              alt=""
              class="share-icon"
              @click="screen_share()"
              v-if="!is_share"
            />
            <img
              src="@/assets/스크린아이콘/[아이콘]화면공유ON.png"
              alt=""
              class="share-icon"
              @click="screen_share()"
              v-if="is_share"
            />
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
import { leaveRoom } from "@/api/webrtc.js";
import { useRouter } from "vue-router";
import { GetUserInfo } from "@/api/webrtc.js";
import UserList from "./component/UserList.vue";
import ChatView from "./component/ChatView.vue";
import TimeView from "./component/TimeView.vue";
import moment from "moment";

// 라이어 게임 관련 import 시작
import { liarGameKeywords, callMyNameKeywords } from "@/const/const";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import LiarGame from "./component/LiarGame";
import CallMyNameGame from "./component/CallMyNameGame";
// 끝

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
    LiarGame,
    CallMyNameGame,
  },

  setup() {
    const store = useStore();
    var message = ref("");
    const router = useRouter();
    var is_img = ref(true);
    var is_mic = ref(true);
    var is_camera = ref(true);
    var is_share = ref(false);
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
      connectionId: store.state.roomStore.connectionId,
      reload: 0,
      current_title: "chat",
      sub_name: [],
      pub_name: undefined,
      chat: [],
      leave_connectionId: store.state.roomStore.leave_connectionId,
      audio_0: undefined,
      // 게임 관련 state 시작
      showLiarGameModal: false,
      showCallMyNameGameModal: false,
      liargameCategory: "",
      liargameKeyword: "",
      stompClient: null,
      categoryChosen: false,
      participants: [],
      callMyName: {},
      // 게임 관련 state 끝

      //참가자 누적시간 reload
      participant_reload: 0,
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

    function sendMessage(callMyName, keyword, liar) {
      if (keyword && state.stompClient) {
        let chatMessage = {
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
      if (callMyName && state.stompClient) {
        let chatMessage = {
          roomId: store.state.roomStore.roomId,
          participantId: store.state.roomStore.participantId,
          name: store.state.userStore.name,
          message: `callMyName=${callMyName}`,
        };
        state.stompClient.send(
          `/pub/rooms/${store.state.roomStore.roomId}/messages`,
          {},
          JSON.stringify(chatMessage)
        );
      }
    }

    function onMessageReceived(payload) {
      var message = JSON.parse(payload.body).message;
      if (message.includes("keyword")) {
        state.showLiarGameModal = true;

        let words = message.split("&");
        let liar = words[1].substr(5);
        setTimeout(() => {
          if (store.state.roomStore.participantId == liar) {
            state.liargameKeyword = "당신은 라이어입니다.";
          } else {
            state.liargameKeyword = words[0].substr(8);
          }
          state.categoryChosen = true;
        }, 200);
      }

      if (message.includes("callMyName")) {
        state.callMyName = {};
        let callMyNameParticipants = message.substr(11).split(",");
        for (let callMyNameParticipant of callMyNameParticipants) {
          let callMyName = callMyNameParticipant.split("&");
          if (store.state.roomStore.participantId == callMyName[0]) continue;
          state.callMyName[callMyName[0]] = callMyName[1];
        }
      }
    }

    let startLiarGame = () => {
      state.showLiarGameModal = true;
    };

    let decideKeyword = (category) => {
      state.liargameCategory = category;
      GetUserInfo(
        store.state.roomStore.roomId,
        ({ data }) => {
          // 카테고리에 대한 키워드 선정 및 라이어 선정
          let participantList = data.participantList;
          let liar =
            participantList[Math.floor(Math.random() * participantList.length)]
              .participantId;
          let liargameKeyword =
            liarGameKeywords["keywords"][state.liargameCategory][
              Math.floor(
                Math.random() *
                  liarGameKeywords["keywords"][state.liargameCategory].length
              )
            ];
          sendMessage(null, liargameKeyword, liar);
        },
        (error) => {
          console.log(error);
        }
      );
    };

    let endLiarGame = () => {
      state.liargameKeyword = "";
      state.liargmaeCategory = "";
      state.showLiarGameModal = false;
      state.categoryChosen = false;
    };

    let setUpCallMyNameGame = () => {
      state.showCallMyNameGameModal = true;
      GetUserInfo(
        store.state.roomStore.roomId,
        ({ data }) => {
          // 카테고리에 대한 키워드 선정 및 라이어 선정
          let participantList = data.participantList;
          state.participants = participantList;
        },
        (error) => {
          console.log(error);
        }
      );
    };

    let startCallMyNameGame = (checkedParticipants) => {
      if (checkedParticipants.length == 0) {
        alert("1명이상의 참가자를 지정해주세요.");
        return;
      }
      state.showCallMyNameGameModal = false;
      let population = Array.from(callMyNameKeywords);
      let populationSize = population.length;
      let i = 0;
      let res = "";
      while (population.length > populationSize - checkedParticipants.length) {
        let sample = population.splice(
          Math.floor(Math.random() * population.length),
          1
        )[0];
        res += checkedParticipants[i] + "&" + sample + ",";
        i++;
      }
      sendMessage(res.slice(0, res.length - 1));
    };
    // 게임 관련 methods 끝
    onMounted(() => {
      // 웹소켓 connect 시작
      connect();
      // 웹소켓 connect 끝
      console.log(state.openvidu_token);
      var test = state.openvidu_token;
      var test2 = test.split('"');
      var test3 = test2[0].split("sessionId=");
      var test4 = test3[1].split("&token=");
      state.mySessionId = test4[0];
      joinSession();
      state.audio_0 = new Audio(require("@/assets/배경음악/축제/축제.mp3"));
      // for (;;) {
      state.audio_0.play();
      state.audio_0.loop = true;
      // }

      //참가자 누적시간 reload
      setInterval(() => {
        state.participant_reload += 1;
      }, 60000);
    });

    var OnMusic = (idx) => {
      is_img.value = !is_img.value;
      if (idx === 1) {
        state.audio_0.pause();
      } else {
        state.audio_0.play();
      }
    };

    watch(store.state.roomStore.isScreen, () => {});
    watchEffect(() => {
      console.log("watch!!!: " + store.state.roomStore.isScreen);
    });

    //카메라 On/Off
    var v_toggle = true;
    var camera_toggle = () => {
      v_toggle = !v_toggle;
      // state.publisher.properties.publishVideo = false;
      is_camera.value = !is_camera.value;
      // state.publisher.properties.publishVideo = false;
      state.publisher.publishVideo(v_toggle);
    };

    //마이크 On/Off
    var m_toggle = true;
    var mic_toggle = () => {
      console.log("마이크 On/Off!!!");
      m_toggle = !m_toggle;
      is_mic.value = !is_mic.value;

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
      is_share.value = true;
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
                  is_share.value = false;
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
              state.publisher2 = undefined;
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

      state.session.on("streamDestroyed", ({ stream }) => {
        console.log("스트림이다~~~~: ", stream);
        const index = state.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          state.subscribers.splice(index, 1);
        }
      });

      //사람 나갔을 때
      state.session.on("connectionDestroyed", (stream) => {
        state.reload += 1;

        console.log("subscriber가 나갔습니다 ㅠㅠ!");
        state.publisher2 = undefined;

        console.log("stream: ", stream);
        // console.log(stream);

        const index = state.subscribers.indexOf(
          stream.connection.stream.StreamManager,
          0
        );

        console.log("인덱스 : " + index);
        if (index > -1) {
          state.subscribers.splice(index, 1);
          state.subscribers([...state.subscribers]);
        }

        // var length = state.subscribers.length;
        // console.log(state.publisher);
        // for (var i = 0; i < length; i++) {
        //   console.log(length);
        //   console.log(i);
        //   console.log(state.subscribers[i].stream.connection.connectionId);
        //   // console.log(state.subscribers[i]);
        //   console.log("나간 커넥션 아이디: " + state.leave_connectionId);
        //   // state.subscribers[0] = undefined;
        //   if (
        //     state.subscribers[i].stream.connection.connectionId ===
        //     store.state.roomStore.leave_connectionId
        //   ) {
        //     // const index = state.subscribers.indexOf(i);
        //     // console.log("iiiiiiiiiiiiiiiiiiiii: " + i);
        //     // state.subscribers[i] = undefined;
        //     // if (index >= 0) {
        //     //   state.subscribers.splice(index, 1);
        //     // }
        //     console.log("안녕하세요!");
        //     state.subscribers[i] = undefined;
        //   }
        // }
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
        message.value = "";
        let chat = document.querySelector(".chat-content");
        chat.scrollTop = chat.scrollHeight;
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
      store.commit(
        "roomStore/SET_LEAVE_CONNECTION_ID",
        store.state.roomStore.connectionId
      );
      console.log(
        "퍼블리셔 커넥션 아이디: " + store.state.roomStore.connectionId
      );
      state.audio_0.pause();
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
      console.log(state.roomId);
      GetUserInfo(state.roomId, (response) => {
        var category = response.data.roomInfo.categoryId;
        console.log("카테고리: " + category);
        var participant = response.data.participantList;
        var length = participant.length;
        for (var i = 0; i < length; i++) {
          console.log("participant :", participant[i]);
          if (participant[i].participantId === state.participantId) {
            var enterTime = participant[i].enterTime;
            enterTime = moment(enterTime);
            enterTime = enterTime.format("YYYY-MM-DD HH:mm:ss");
            // console.log(date);
            // console.log(enterTime);
          }
        }
        // var exitTime = Date.now();
        // exitTime = moment(exitTime);
        // exitTime = exitTime.format("YYYY-MM-DD HH:mm:ss");
        // // console.log("exit_date: " + exit_date);
        // GetStamp(
        //   store.state.userStore.token,
        //   enterTime,
        //   exitTime,
        //   category,
        //   (response) => {
        //     console.log("response: ", response);
        //   },
        //   (error) => {
        //     console.log(error);
        //   }
        // );
      });

      if (state.session) {
        state.publisher = undefined;
        // state.subscribers = [];
        state.session.disconnect();
        state.OV = undefined;
        state.session = undefined;

        store.commit("roomStore/SET_INIT_CHAT");
      }

      leaveRoom(
        store.state.userStore.token,
        state.roomId,
        state.participantId,
        (response) => {
          console.log(response);
          console.log("세션 나가기 성공!");
          console.log("subscribers count : ", state.subscribers);

          router.push({
            name: "room-list",
            query: { dest: "cafe" },
          });
        }
      );
    };

    var updateMainVideoStreamManager = (stream) => {
      if (state.mainStreamManager === stream) return;
      state.mainStreamManager = stream;
    };

    return {
      state,
      message,
      is_mic,
      is_camera,
      joinSession,
      updateMainVideoStreamManager,
      camera_toggle,
      mic_toggle,
      screen_share,
      submit_msg,
      LeaveSession,
      is_img,
      m_toggle,
      OnMusic,
      is_share,

      // 라이어 게임 메소드 return 시작
      startLiarGame,
      decideKeyword,
      endLiarGame,
      setUpCallMyNameGame,
      startCallMyNameGame,
      // 라이어 게임 메소드 return 끝
    };
  },
};
</script>

<style scoped>
.background {
  width: 100vw;
  height: 100vh;
}
.total-wrap {
  width: 100vw;
  height: 100vh;
}

.title-wrap {
  width: 100%;
  height: 7%;
  /* background-color: red; */

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
  /* background-color: blueviolet; */

  position: relative;
  right: 3%;
}

.user {
  width: 100%;
  height: 90%;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  position: relative;
  bottom: 1.5%;
}

.user-wrap {
  width: 90%;
  height: 97%;
  background-color: burlywood;
  padding: 1rem;
  min-width: 1280px;

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
  bottom: 1.5%;
  /* background-color: aquamarine; */

  display: flex;
  justify-content: flex-start;
  align-items: center;

  position: relative;
  right: 0.5%;
}

.user1 {
  width: 15.4%;
  height: 95%;
  /* background-color: brown; */
  border-radius: 10px;
  position: relative;
  display: flex;
  /* border: solid 2px; */
  /* border: 2px; */
}
.user2 {
  width: 15.4%;
  height: 95%;
  /* background-color: brown; */
  position: relative;
  left: 1.5%;
  border-radius: 10px;
}
.user3 {
  width: 15.4%;
  height: 95%;
  /* background-color: brown; */
  position: relative;
  left: 3%;
  border-radius: 10px;
}
.user4 {
  width: 15.4%;
  height: 95%;
  /* background-color: brown; */
  position: relative;
  left: 4.5%;
  border-radius: 10px;
}
.user5 {
  width: 15.4%;
  height: 95%;
  /* background-color: brown; */
  position: relative;
  left: 6%;
  border-radius: 10px;
}
.user6 {
  width: 15.4%;
  height: 95%;
  /* background-color: brown; */
  position: relative;
  left: 7.5%;
  border-radius: 10px;
}

.chat-wrap {
  width: 100%;
  height: 78%;
  /* background-color: chartreuse; */
  position: relative;
}

.share {
  width: 100%;
  height: 92%;
  position: relative;
  /* top: 3.5%; */
  top: 20px;
  /* background-color: #f7d794; */

  display: flex;
}

.empty {
  width: 2%;
  height: 100%;
  background: rgba(255, 255, 255, 0);
}

.chat {
  width: 32%;
  height: 100%;
  /* background-color: black; */
}
.share-icons {
  width: 66.5%;
  height: 8%;
  /* background-color: red; */
  /* position: relative; */
  position: absolute;
  top: 95%;
  display: flex;
  /* justify-content: center; */
  align-items: center;
}

.bgm-icon {
  width: 3%;
  position: relative;
  left: 32.5%;
}
.mic-icon {
  width: 3%;
  position: relative;
  left: 37.5%;
}
.camera-icon {
  width: 3%;
  position: relative;
  left: 42.5%;
}
.share-icon {
  width: 3%;
  position: relative;
  left: 47.5%;
}
.config-icon {
  width: 3%;
  position: relative;
  left: 52.5%;
}

.share-icons {
  position: absolute;
  bottom: 10%;
}

.share-screen {
  width: 66%;
  height: 100%;
  background-color: white;
}

.chat-header {
  width: 100%;
  height: 10%;
  /* background-color: white; */
  display: flex;
  position: relative;
}

.chat-box {
  width: 100%;
  height: 90%;
  background-color: white;
  position: relative;
}

.chat-footer {
  width: 100%;
  height: 12%;
  /* background-color: cornflowerblue; */
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 88%;
}
.chat-content {
  width: 100%;
  height: 86%;
  /* overflow-y: auto; */
  overflow-x: hidden;
  display: flex;
  flex-direction: column;
}

.chat-input {
  position: absolute;
  width: 70%;
  height: 50%;
  border: 1px solid #d1d8df;
  border-radius: 30px;
}

.submit-btn {
  position: absolute;
  width: 7%;
  height: auto;
  left: 88%;
  /* top: 1px; */
}

.participant {
  width: 50%;
  height: auto;
  background: white;
  display: flex;
  justify-content: center;
  align-items: center;
  /* padding-left: 1%; */

  background: #f7d794;
  border-top-left-radius: 6px;
  border-top-right-radius: 6px;
}

.participant:hover {
  border: 2px solid #3b81ee;
}

.chat-title {
  width: 50%;
  height: auto;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;

  /* background: #f7d794; */
  border-top-left-radius: 6px;
  border-top-right-radius: 6px;
}

.chat-title:hover {
  border: 2px solid #3b81ee;
}

.participant-box {
  width: 100%;
  height: 90%;
  background: beige;
}

.line {
  width: 100%;
  height: 1%;
  background-color: #d1d8df;
  position: absolute;
  bottom: 110%;
  /* border: solid #d1d8df; */
  /* color: #d1d8df; */
}

.time {
  background-image: url("@/assets/배경화면/Bonfire.gif");
  background-repeat: no-repeat;
  background-size: cover;
}
</style>
