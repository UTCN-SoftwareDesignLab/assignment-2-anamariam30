<template>
  <v-card>
    <v-card-title>
      <h4>Our Books</h4>
      <v-btn plain color="blue"  @click="refreshList"> Refresh </v-btn>
      <v-spacer></v-spacer>

      <v-spacer></v-spacer>
      <v-text-field
       v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>

      <v-btn color="blue" outlined plain @click="searchBook"
        >Search</v-btn
      >

    </v-card-title>

    <v-data-table  :headers="headers" :items="books"  @click:row="editBook">
    </v-data-table>

    <BookDialog
      :opened="dialogVisible"
      :book="selectedBook"
      :onlyRead="true"
      
      @refresh="refreshList"
      @closeTheDialog="closeDialog"
    ></BookDialog>
   
  </v-card>
</template>

<script>
import api from "../api";
import BookDialog from "../components/BookDialog";

export default {
  name: "BookView",
  components: { BookDialog },
  data() {
    return {
      books: [],
      search: '',
      headers: [
        {
          text: "Title",
          align: "start",
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Price", value: "price" },
        { text: "Quantity", value: "quantity" },
      ],
      dialogVisible: false,
      selectedBook: {},
    };
  },
  methods: {
    async searchBook() {

     this.books=await api.books.findMatch(this.search);
     this.search=''

    },
    editBook(book) {
      this.selectedBook = book;
      this.dialogVisible = true;
    },
    addBook() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedBook = {};
      this.books = await api.books.allItems();
    },
    closeDialog() {
      this.dialogVisible = false;
      this.selectedBook = {};
    },
  },
  created() {
    this.refreshList();
  },
  
};
</script>

<style scoped></style>
