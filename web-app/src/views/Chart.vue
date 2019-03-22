<template>
  <v-container>
    <v-flex sm-12>
      <bar-chart v-if='loaded' :chart-data="ds" :options="options" :height="height"></bar-chart>
    </v-flex>
  </v-container>
</template>

<script>

import BarChart from "../components/Chart.js";
import axios from "axios";

export default {

  components: {BarChart},

  data: () => ({
    height: 300,
    options: {
      title: {
        display: true,
        text: "Numbers of Articles Published by Year"
      },
      legend: {
        display: false
      }
    },
    loaded: false,
    ds: null
  }),
  
  mounted() {
    this.fillData();
  },

  methods: {
    fillData() {
      var datacollection = {
        labels: null,
        datasets: [{
          backgroundColor: "#3F51B5",
          hoverBackgroundColor: "#EC407A",
          data: null
        }]
      };
      var vm = this
      axios
        .get("http://localhost:8080/api/count", {
          method: 'GET',
          mode: 'no-cors',
          headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
          },
          withCredentials: false,
        })
        .then((response) => {
          datacollection.labels = response.data.map((x) => x.year);
          datacollection.datasets[0].data = response.data.map((x) => x.count)
          vm.ds = datacollection;
        }).catch((err) => 
          {
            console.log(err)
          }
        ).then(vm.loaded = true);
      
    }
  }
}
</script>
