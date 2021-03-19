const Login = { template: "<login></login>" };
const Create = { template: "<create></create>" };

const routes = [
  { path: "/login", component: Login },
  { path: "/create", component: Create },
];

const router = new VueRouter({
  routes
});

axios.defaults.headers.common["Authorization"] = "Bearer " + localStorage.getItem("accessToken");

const app = new Vue({
  router,
}).$mount("#app");