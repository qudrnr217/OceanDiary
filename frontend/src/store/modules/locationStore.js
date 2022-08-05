// import { LOCATION } from "../mutation-types";
const locationStore = {
  namespaced: true,

  state: () => ({
    location_url: "",
    location_name: "",
  }),
  mutations: {
    SET_LOCATION_URL(state, location_url) {
      state.location_url = location_url;
    },
    SET_LOCATION_NAME(state, location_name) {
      state.location_name = location_name;
    },
  },
};

export default locationStore;
