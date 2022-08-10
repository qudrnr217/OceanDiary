// // import Vue from "vue";
// import Vuex from "vuex";
// // import createPersistedState from "vuex-persistedstate";

// // Vue.use(Vuex);

// import locationStore from "@/store/modules/locationStore";

// const store = new Vuex.Store({
//   modules: {
//     locationStore,
//   },
//   plugins: [
//     // createPersistedState({
//     //   // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
//     //   storage: sessionStorage,
//     // }),
//   ],
// });

// export default store;

import { createStore } from "vuex";
// import createPersistedState from "vuex-persistedstate";
import locationStore from "./modules/locationStore";
import userStore from "./modules/userStore";
import createPersistedState from "vuex-persistedstate";
import roomStore from "./modules/roomStore";
export default createStore({
  modules: { locationStore, userStore, roomStore },
  plugins: [
    createPersistedState({
      paths: ["userStore", "roomStore"],
    }),
  ],
});
