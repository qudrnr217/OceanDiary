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
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
