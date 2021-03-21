Vue.component("certificate_table", {
    data:function () {
        return {
            certificets :undefined
        }
    },
    template: '<div style="margin: 10px;padding: 5px"> ' +
        '<table class="table" >' +
        '<thead>' +
                '<th>Subject Name</th>' +
                '<th>Email</th>' +
                '<th>Start Date</th>' +
                '<th>End Date</th>' +
                '<th>Is Revoked</th>' +
                '<th>Revoke</th>' +
            '</thead>' +
            '<tbody>' +
                '<tr v-for="item in certificets" v-bind:key="item.serialNumber">' +
                    '<td>{{item.commonName}}</td>' +
                    '<td>{{item.email}}</td>' +
                    '<td>{{item.startDate}}</td>' +
                    '<td>{{item.endDate}}</td>' +
                    '<td>{{item.revoked}}</td>' +
                    '<td><button type="submit" v-on:click="revoke(item.serialNumber)">Revoke</button></td>' +
                '</tr>' +
            '</tbody>' +
        '</table>' +
        '</div>'
     ,
  methods: {
      revoke:function (serialNumber){
        axios
            .put("cert/revoke/"+serialNumber)
            .then((res)=>alert("Certificate revoked"),window.location.reload());
      }
  },created(){
        axios
            .get("cert/all")
            .then((res) => {this.certificets=res.data,
                console.log(res.data) });
    }
});