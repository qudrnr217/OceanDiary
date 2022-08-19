const stampStore = {
  namespaced: true,
  state: {
    totalTime: "09:13:13",
    category: "OCEAN",
  },
  getters: {
    checkStampInfo: function (state) {
      return state.stampInfo;
    },
  },
  mutations: {
    SET_TOTAL_TIME: (state, totalTime) => {
      state.totalTime = totalTime;
    },
    SET_CATEGORY: (state, category) => {
      state.category = category;
    },
  },
};

export default stampStore;
