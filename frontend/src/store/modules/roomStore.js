const roomStore = {
  namespaced: true,

  state: () => ({
    roomId: "",
    participantId: "",
    openvidu_token: "",
    connectionId: "",
    sessionId: "",
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
  },
};

export default roomStore;
