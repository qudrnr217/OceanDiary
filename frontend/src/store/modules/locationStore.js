// import { LOCATION } from "../mutation-types";
const locationStore = {
  namespaced: true,

  state: () => ({
    location_url: "",
  }),
  mutations: {
    SET_LOCATION_URL(state, location_url) {
      state.location_url = location_url;
    },
  },
};

export default locationStore;
