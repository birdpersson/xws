Vue.component("create", {
  data: function () {
    return {
      certificate: {
        startDate: "",
        endDate: "",

        commonName: "",
        organizationName: "",
        organizationalUnitName: "",
        countryName: "",
        email: "",
        ca: false,
        root: false,

        issuerSerialNumber: null,
      },
      certificates: [],
      error: false,
    };
  },
  template: `
<div id="create">
  <div class="container-fluid">
    <div class="row no-gutter">
      <div class="col-md-8 col-lg-6">
        <div class="login d-flex alignlogin d-flex align-items-center py-5">
          <div class="container">
            <div class="row">
              <div class="col-md-9 col-lg-8 mx-auto">

                <h3 class="login-heading mb-4">Create Certificate</h3>
                <form class="form-signin">

                  <div class="form-label-group">
                    <b>Enter common name</b>
                    <input v-model="certificate.commonName" class="form-control">
                    <br>
                  </div>

                  <div class="form-label-group">
                    <b>Enter organization name</b>
                    <input v-model="certificate.organizationName" class="form-control">
                    <br>
                  </div>

                  <div class="form-label-group">
                    <b>Enter organizational unit name</b>
                    <input v-model="certificate.organizationalUnitName" class="form-control">
                    <br>
                  </div>

                  <div class="form-label-group">
                    <b>Enter country name</b>
                    <input v-model="certificate.countryName" class="form-control">
                    <br>
                  </div>

                  <div class="form-label-group">
                    <b>Enter email address</b>
                    <input v-model="certificate.email" class="form-control">
                    <br>
                  </div>

                  <div class="form-label-group">
                    <b>Enter start date</b>
                    <input v-model="certificate.startDate" type="date" class="form-control">
                    <br>
                  </div>

                  <div class="form-label-group">
                    <b>Enter end date</b>
                    <input v-model="certificate.endDate" type="date" class="form-control">
                    <br>
                  </div>

                  <b>Certificate Authority</b>
                  <input v-model="certificate.ca" type="checkbox">
                  <br>

                  <b>Self signed (Root)</b>
                  <input v-model="certificate.root" type="checkbox">
                  <br>

                  <div v-show="!certificate.root" class="form-label-group">
                    <b>Choose issuer</b>
                    <select v-model="certificate.issuerSerialNumber">
                      <option v-for="item in certificates" :value="item.serialNumber">
                        {{item.commonName}}
                      </option>
                    </select>
                    <br>
                  </div>

                  <button type="button"
                    class="btn btn-lg btn-primary btn-block btn-login text-uppercase font-weight-bold mb-2"
                    v-on:click="create()">Create</button>

                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
`
  ,
  methods: {
    create: function () {
      axios
        .post("cert/create", this.certificate)
        .then(Response => { console.log(Response) });
    },

    arrange() {
      for (let i = 0; i < this.certificates.length; i++) {
        this.names.push(this.certificates[i].issuerCommonName);
      }
    },
  },
  created() {
    axios
      .get("cert/ca")
      .then(Response => {
        this.certificates = Response.data;
        console.log(this.certificates);
      });
  },
});