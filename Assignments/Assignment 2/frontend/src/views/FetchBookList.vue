<template>
  <v-card>
    <v-card-title>
      <h4>Books to be added</h4>
       
      <v-spacer></v-spacer>
      <router-link to="books">
        <v-btn outlined plain color="blue">Back</v-btn>
      </router-link>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        label="Search"
        append-icon="mdi-magnify"
        single-line
        hide-details
      ></v-text-field>
      <v-btn plain color="blue" @click="searchApi"> Search </v-btn>
    </v-card-title>

    <v-data-table
      :headers="headers"
      :items="fetchBooks"
      @click:row="addFetchBook"
    >
    </v-data-table>

    <FetchBookDialog
      :opened="dialogVisible"
      :fetchBook="selectedFetchBook"
      @closeTheDialog="closeDialog"
    ></FetchBookDialog>
  </v-card>
</template>

<script>
import api from "../api";
import FetchBookDialog from "../components/FetchBookDialog";

export default {
  name: "FetchBookList",
  components: { FetchBookDialog },
  data() {
    return {
      search: "",
      fetchBooks: [],
      price:0,
      quantity:0,
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "volumeInfo.title",
        },
        { text: "Author", value: "volumeInfo.authors" },
        { text: "Genre", value: "volumeInfo.categories" },
        { text: "Published Date", value: "volumeInfo.publishedDate" },
      ],
      dialogVisible: false,
      selectedFetchBook: {},
    };
  },
  methods: {
    addFetchBook(fetchBook) {
      this.selectedFetchBook = fetchBook;
      this.dialogVisible = true;
    },

    async searchApi() {
      this.fetchBooks = await api.books.search(this.search);
      this.search = "";

    },

    closeDialog() {
      this.dialogVisible = false;
      this.selectedFetchBook = {};
    }
    },
  created() {
    this.dialogVisible = false;
    this.selectedFetchBook = {};
    this.fetchBooks=[]
  },

};
</script>

<style scoped></style>
