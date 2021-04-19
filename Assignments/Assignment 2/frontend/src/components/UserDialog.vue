<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value ="opened"
     >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
         Edit User
        </v-toolbar>
        <v-form>
          <v-text-field v-model="user.name" label="Username" />
          <v-text-field v-model="user.email" label="Email" />
          <v-text-field v-model="user.roles" label="Roles" />
        
           <v-text-field v-if="this.isNew" v-model="user.password" label="Password" />
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            Save
          </v-btn>
          <v-btn @click="removes">
          Delete
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
  name: "UserDialog",
  props: {
    user: Object,
    opened: Boolean,
  },
  methods: {
    close(){
     this.$emit("closeTheDialog");
      
    },
    removes(){
      api.users.remove(this.user.id) 
     .then(() => this.$emit("refresh")); 
    },

    persist() {
      if (!this.isNew) {
        api.users.edit({
            id: this.user.id,
            name: this.user.name,
        
          })
          .then(() => this.$emit("refresh"));
          }else{
            api.users.create({
              name: this.user.name,
              password:this.user.password,
              email:this.user.email,
              roles:this.user.roles
            })
            .then(() => this.$emit("refresh"));
            
          }
      }
  },
   computed: {
    isNew: function () {
      return !this.user.id;
    },
  },
};
</script>

<style scoped></style>
