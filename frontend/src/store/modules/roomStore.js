const roomStore = {
  namespaced: true,

  state: () => ({
    roomId: "",
    participantId: "",
    openvidu_token: "",
    connectionId: "",
    sessionId: "",
    openvidu_token2: "",
    isScreen: false,
    chat: [],
    leave_connectionId: "",
  }),
  mutations: {
    SET_ROOM_ID(state, roomId) {
      state.roomId = roomId;
    },
    SET_PARTICIPANT_ID(state, participantId) {
      state.participantId = participantId;
    },
    SET_OPENVIDU_TOKEN(state, openvidu_token) {
      state.openvidu_token = openvidu_token;
    },
    SET_CONNECTION_ID(state, connectionId) {
      state.connectionId = connectionId;
    },
    SET_SESSION_ID(state, sessionId) {
      state.sessionId = sessionId;
    },
    SET_OPENVIDU_TOKEN2(state, openvidu_token2) {
      state.openvidu_token2 = openvidu_token2;
    },
    SET_IS_SCREEN(state, isScreen) {
      state.isScreen = isScreen;
    },
    SET_CHAT(state, chat) {
      state.chat.push(chat);
    },
    SET_INIT_CHAT(state) {
      state.chat = [];
    },
    SET_LEAVE_CONNECTION_ID(state, leave_connectionId) {
      state.leave_connectionId = leave_connectionId;
    },
  },
};

export default roomStore;
