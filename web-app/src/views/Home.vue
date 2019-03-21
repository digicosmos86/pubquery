<template>
  <v-container>
    <v-flex xs12 sm6>
      <h4 class="display-1">Search by Author</h4>
    </v-flex>
    
    <v-flex xs12 sm6>
      <v-text-field prepend-icon="search" label="Author Last Name"
                    clearable v-model="authorLastName">
      </v-text-field>
    </v-flex>


    <v-data-table :headers="headers" :items="articles" class="elevation-1">
      <template v-slot:headers="props">
        <tr>
          <th
            v-for="header in props.headers"
            :key="header.text"
          >
            {{ header.text }}
          </th>
        </tr>
      </template>
      <template v-slot:items="props">
        <tr :active="props.selected" @click="props.selected = !props.selected">
          <td class="text-s-left">{{ props.item.pmid }}</td>
          <td class="text-s-left">{{ props.item.title }}</td>
          <td class="text-s-left">{{ props.item.date }}</td>
          <td class="text-s-left">{{ props.item.authors }}</td>
        </tr>
      </template>
      <template v-slot:no-data>
        <v-alert :value="true" color="error" icon="warning">
          Sorry, your search returned no data.
        </v-alert>
      </template>
    </v-data-table>

    
  </v-container>
</template>

<script>
  import axios from "axios";
  import _ from "lodash";

  export default {
    data: () => (
      {
        authorLastName: "",
        headers: [
          {
            text: "PMID",
            align: "left",
          },
          {
            text: "Title",
            align: "left",
          },
          {
            text: "Date",
            align: "left",
          },
          {
            text: "Authors",
            align: "left",
          }
        ],
        articles: []
      }),

    watch: {
        authorLastName: function(newLastName, oldLastName) {
          if (newLastName === "") {
            return;
          }
          this.debouncedSearch()
        }
    },

    created () {
      this.debouncedSearch = _.debounce(this.search, 500)
    },

    mounted () {
      axios
        .get("http://localhost:8080/api/articles", {
          method: 'GET',
          mode: 'no-cors',
          headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
          },
          withCredentials: false,
        })
        .then((response) => {
          this.articles = response.data;
        }).catch((err) => 
          {
            this.articles = [];
            console.log(err)
          }
        )
    },
    methods: {
      search: function() {
        if (this.authorLastName !== "") {
          var vm = this;
          return axios
          .get("http://localhost:8080/api/search/" + this.authorLastName, {
            method: 'GET',
            mode: 'no-cors',
            headers: {
              'Access-Control-Allow-Origin': '*',
              'Content-Type': 'application/json',
            },
            withCredentials: false,
          })
          .then((response) => {
            vm.articles = response.data;
          }).catch((err) => 
            {
              this.articles = [];
              console.log(err);
            }
          )
        }
      }
    }
  }
</script>
