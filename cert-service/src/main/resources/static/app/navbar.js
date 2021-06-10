Vue.component('navbar', {
    template: `
<div id="navbar">
  <div id="navigation">
    <nav class="navbar navbar-expand-lg navbar-dark static-top">
      <div class="container">
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ml-auto">

            <li class="nav-item active" v-if='!loggedIn'>
              <router-link to="/" class="btn" exact>Home</router-link>
            </li>

            <li class="nav-item active" v-if='loggedIn'>
              <router-link to="/create" class="btn" exact>Create</router-link>
            </li>

            <li class="nav-item active" v-if='loggedIn'>
              <router-link to="/all" class="btn" exact>Certificates</router-link>
            </li>

          </ul>

          <router-link to='/login' class="nav-link" exact>
            <button v-if="!loggedIn" class="btn" id='btnLogin'>Log In</button>
          </router-link>

          <button v-if="loggedIn" class="btn" id='btnLogout' v-on:click='logout()'>Log Out</button>

        </div>
      </div>
    </nav>
  </div>
</div>
`,
    data: function () {
        return {
            loggedIn: localStorage.getItem('accessToken') ? true : false,
        }
    },
    methods: {
        logout: function () {
            localStorage.removeItem('accessToken');
            this.$router.push('/login');
            window.location.reload();
        }
    },
});
