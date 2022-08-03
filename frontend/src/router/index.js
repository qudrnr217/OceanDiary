import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import UserView from "../views/UserView.vue";
import LoginMenu from "../components/user/LoginMenu.vue";
import LoginSocial from "../components/user/LoginSocial.vue";
import LoginSingle from "../components/user/LoginSingle.vue";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/login",
    name: "login",
    component: UserView,
    children: [
      {
        path: "/login/menu",
        name: "login-menu",
        component: LoginMenu,
      },
      {
        path: "/login/social",
        name: "login-social",
        component: LoginSocial,
      },
      {
        path: "/login/single",
        name: "login-single",
        component: LoginSingle,
      },
    ],
  },
  // {
  //   path: "/about",
  //   name: "about",
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () =>
  //     import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  // },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
