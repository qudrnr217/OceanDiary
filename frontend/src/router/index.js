import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import StationView from "../views/StationView.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/login",
    name: "login",
    component: () => import("@/views/UserView.vue"),
    children: [
      {
        path: "/login/menu",
        name: "login-menu",
        component: () => import("@/components/user/LoginMenu.vue"),
      },
      {
        path: "/login/social",
        name: "login-social",
        component: () => import("@/components/user/LoginSocial.vue"),
      },
      {
        path: "/login/single",
        name: "login-single",
        component: () => import("@/components/user/LoginSingle.vue"),
      },
      {
        path: "/login/signup",
        name: "login-signup",
        component: () => import("@/components/user/LoginSignup.vue"),
      },
    ],
  },
  {
    path: "/oauth2/redirect",
    name: "OauthHandler",
    component: () => import("@/components/user/oauth/NaverHandler.vue"),
  },

  {
    path: "/station",
    name: "station",
    component: StationView,
    children: [
      {
        path: "home",
        name: "station_home",
        component: () => import("@/components/station/NameChat.vue"),
      },
      {
        path: "input",
        name: "station_input",
        component: () => import("@/components/station/InputBox.vue"),
      },
      {
        path: "where",
        name: "station_where",
        component: () => import("@/components/station/WhereChat.vue"),
      },
      {
        path: "map",
        name: "station_map",
        component: () => import("@/components/station/StationMap.vue"),
      },
      {
        path: "depart",
        name: "station_depart",
        component: () => import("@/components/moving/DepartView.vue"),
      },
    ],
  },
  {
    path: "/moving",
    name: "station_moving",
    component: () => import("@/components/moving/MovingView.vue"),
  },
  {
    path: "/cafe",
    name: "station_cafe",
    component: () => import("@/components/arrive/ArriveCafe.vue"),
  },
  {
    path: "/ocean",
    name: "station_ocean",
    component: () => import("@/components/arrive/ArriveOcean.vue"),
  },
  {
    path: "/festival",
    name: "station_festival",
    component: () => import("@/components/arrive/ArriveFestival.vue"),
  },
  {
    path: "/house",
    name: "station_house",
    component: () => import("@/components/arrive/ArriveHome.vue"),
  },
  {
    path: "/library",
    name: "station_library",
    component: () => import("@/components/arrive/ArriveLibrary.vue"),
  },
  {
    path: "/festival_bg",
    name: "festival",
    component: () => import("@/views/FestivalView.vue"),
    children: [
      {
        path: "home",
        name: "festival_home",
        component: () => import("@/components/room/RoomList.vue"),
      },
      {
        path: "create",
        name: "festival_create",
        component: () => import("@/components/room/RoomCreate.vue"),
      },
    ],
  },
  {
    path: "/cafe_bg",
    name: "cafe",
    component: () => import("@/views/CafeView.vue"),
    children: [
      {
        path: "home",
        name: "cafe_home",
        component: () => import("@/components/room/RoomList.vue"),
      },
      {
        path: "create",
        name: "cafe_create",
        component: () => import("@/components/room/RoomCreate.vue"),
      },
    ],
  },
  {
    path: "/house_bg",
    name: "house",
    component: () => import("@/views/HouseView.vue"),
    children: [
      {
        path: "home",
        name: "house_home",
        component: () => import("@/components/room/RoomList.vue"),
      },
      {
        path: "create",
        name: "house_create",
        component: () => import("@/components/room/RoomCreate.vue"),
      },
    ],
  },
  {
    path: "/library_bg",
    name: "library",
    component: () => import("@/views/LibraryView.vue"),
    children: [
      {
        path: "home",
        name: "library_home",
        component: () => import("@/components/room/RoomList.vue"),
      },
      {
        path: "create",
        name: "library_create",
        component: () => import("@/components/room/RoomCreate.vue"),
      },
    ],
  },
  {
    path: "/ocean_bg",
    name: "ocean",
    component: () => import("@/views/OceanView.vue"),
    children: [
      {
        path: "home",
        name: "ocean_home",
        component: () => import("@/components/room/RoomList.vue"),
      },
      {
        path: "create",
        name: "ocean_create",
        component: () => import("@/components/room/RoomCreate.vue"),
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
