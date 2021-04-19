<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value ="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
       Add book
        </v-toolbar>
         <v-form v-if="opened">
          <v-text-field v-model="fetchBook.volumeInfo.title" label="Title" />
          <v-text-field v-model="fetchBook.volumeInfo.authors" label="Author" />
          <v-text-field v-model="fetchBook.volumeInfo.categories" label="Genre" />
          <v-text-field v-model="quantity" label="Quantity" />
          <v-text-field v-model="price" label="Price" />
         </v-form>
        <v-card-actions>
          <v-btn @click="persist">
          Add
          </v-btn>

          <v-btn @click="close">
          Close
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "FetchBookDialog",
  props: {
    fetchBook: Object,
    opened: Boolean,
  },

  data() {
    return {
      price:0,
      quantity:0,
    };
  },

  methods: {
    close() {
      this.$emit("closeTheDialog");
    },

    persist() {
      api.books
        .create({
          title: this.fetchBook.volumeInfo.title,
          author: this.fetchBook.volumeInfo.authors[0],
          genre: this.fetchBook.volumeInfo.categories[0],
          quantity: this.quantity,
          price: this.price,
        }).then(() => this.$emit("closeTheDialog")); 
    },
  },
};
</script>

<style scoped></style>
