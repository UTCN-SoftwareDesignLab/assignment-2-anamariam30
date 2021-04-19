import Vue from "vue";
import VueRouter from "vue-router";
import UserList from "../views/UserList.vue";
import BookList from "../views/BookList.vue";
import BookView from "../views/BookView.vue";
import FetchBookList from "../views/FetchBookList.vue";

import { auth as store } from "../store/auth.module";
import Login from "../views/Login";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/users",
    name: "Users",
    component: UserList,
    beforeEnter: (to, from, next) => {
      if (store.getters.isAdmin) {
        next();
      } else {
        next({ name: "BookView" });
      }
    },
  },
  {
    path: "/viewbooks",
    name: "BookView",
    component: BookView,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Login" });
      }
    },
  },

  {
    path: "/fetchbooks",
    name: "FetchBooks",
    component: FetchBookList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Login" });
      }
    },
  },
  {
    path: "/books",
    name: "Books",
    component: BookList,
    beforeEnter: (to, from, next) => {
      if (store.state.status.loggedIn) {
        next();
      } else {
        next({ name: "Login" });
      }
    },
  },
 
];

const router = new VueRouter({
  routes,
});

export default router;
