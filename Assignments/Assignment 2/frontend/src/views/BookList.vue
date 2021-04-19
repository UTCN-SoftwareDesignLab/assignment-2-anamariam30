<template>
  <v-card>
    <v-card-title>
      <h4>Our Books</h4>
       <v-btn plain color="blue"  @click="refreshList"> Refresh </v-btn>
      <v-spacer></v-spacer>
      <v-btn color="blue" outlined plain @click="addBook"> Add Book </v-btn>

      <v-btn color="blue" outlined plain @click="generateReportCSV"
        >Generate CSV Report</v-btn
      >

        <v-btn color="blue" outlined plain @click="generateReportPDF"
        >Generate PDF Report</v-btn
      >
  
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        label="Search"
        append-icon="mdi-magnify"
        single-line
        hide-details
      ></v-text-field>
        <v-btn plain color="blue"  @click="searchBook"> Search </v-btn>
     
    </v-card-title>

    <v-data-table :headers="headers" :items="books" @click:row="editBook">
    </v-data-table>

    <BookDialog
      :opened="dialogVisible"
      :book="selectedBook"
      :onlyRead="false"
      @refresh="refreshList"
      @closeTheDialog="closeDialog"
    ></BookDialog>
    <router-link to="users">
      <v-btn outlined color="blue"> Users </v-btn>
    </router-link>
        <router-link to="fetchbooks">
        <v-btn plain color="blue"> Search Online</v-btn>
      </router-link>
  </v-card>
</template>

<script>
import api from "../api";
import BookDialog from "../components/BookDialog";

export default {
  name: "BookList",
  components: { BookDialog },
  data() {
    return {
      books: [],
      search: "",
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
   
   async searchBook(){
    this.books= await api.books.findMatch(this.search)
   },
   async generateReportPDF() {
      await api.books.generate("PDF");
    },
    async generateReportCSV() {
      await api.books.generate("CSV");
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
