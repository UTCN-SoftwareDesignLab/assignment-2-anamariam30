<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-btn color="blue" outlined plain @click="addUser"> Add User </v-btn>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="users"
      :search="search"
      @click:row="editUser"
    ></v-data-table>
    
    <UserDialog
      :opened="dialogVisible"
      :user="selectedUser"
      @refresh="refreshList"
      @closeTheDialog="closeDialog"
    ></UserDialog>
    <router-link to="books">
      <v-btn outlined color="blue">
       Books
      </v-btn>
    </router-link>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "../components/UserDialog";
export default {
  name: "UserList",
  components: { UserDialog },
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Email", value: "email" },
        { text: "Roles", value: "roles" },
      ],
      dialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    addUser(){
      this.dialogVisible = true;
    },
    editUser(user) {
      this.selectedUser = user;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedUser = {};
      this.users = await api.users.allUsers();
    },
    closeDialog() {
      this.dialogVisible = false;
      this.selectedUser = {};
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
