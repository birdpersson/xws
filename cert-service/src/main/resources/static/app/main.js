const Login = { template: "<login></login>" };
const Create = { template: "<create></create>" };
const CertificateTable={ template:"<certificate_table></certificate_table> "};
const Navbar = { template: '<navbar></navbar>' }

const routes = [
  { path: "/login", component: Login },
  { path: "/create", component: Create },
  { path: "/all", component: CertificateTable },
];

const router = new VueRouter({
  routes
});

axios.defaults.headers.common["Authorization"] = "Bearer " + localStorage.getItem("accessToken");

const app = new Vue({
  router,
}).$mount("#app");