<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Add book" : "Books" }}
        </v-toolbar>

        <v-form>
          <v-text-field
            :readonly="onlyRead"
            v-model="book.title"
            label="Title"
          />
          <v-text-field
            :readonly="onlyRead"
            v-model="book.author"
            label="Author"
          />
          <v-text-field
            :readonly="onlyRead"
            v-model="book.genre"
            label="Genre"
          />
          <v-text-field
            :readonly="onlyRead"
            v-model="book.quantity"
            label="Quantity"
          />
          <v-text-field
            :readonly="onlyRead"
            v-model="book.price"
            label="Price"
          />
        </v-form>
        <v-card-actions>
          <v-btn v-if="onlyRead == false" @click="persist">
            {{ isNew ? "Create" : "Save" }}
          </v-btn>

          <v-btn @click="removes" v-show="isNew == false" :disabled="this.book.quantity<=0&&onlyRead">
            {{ onlyRead ? "Sell" : "Delete" }}
          </v-btn>

          <v-btn @click="close"> Close </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "BookDialog",
  props: {
    book: Object,
    opened: Boolean,
    fromSearch: Boolean,
    onlyRead: Boolean,
  },
  methods: {
    close() {
      this.$emit("closeTheDialog");
    },
    removes() {
      if (this.onlyRead) {
        api.books
          .edit({
            id: this.book.id,
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            quantity: this.book.quantity-1,
            price: this.book.price,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.books.remove(this.book.id).then(() => this.$emit("refresh"));
      }
    },

    persist() {
      if (this.isNew) {
        api.books
          .create({
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            quantity: this.book.quantity,
            price: this.book.price,
          })
          .then(() => this.$emit("refresh"));
      } else {
        api.books
          .edit({
            id: this.book.id,
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            quantity: this.book.quantity,
            price: this.book.price,
          })
          .then(() => this.$emit("refresh"));
      }
    },
  },
  computed: {
    isNew: function () {
      return !this.book.id;
    },
  },
};
</script>

<style scoped></style>
