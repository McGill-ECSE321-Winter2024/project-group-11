<template>
  <div class="profile">
    <h2>User Profile</h2>
    <div v-for="(value, key) in user" :key="key" v-if="!editing && key !== 'id' && key !== 'token'"  class="info-group">
      <label>{{ key }}</label>
      <span>{{value}}</span>
    </div>
    <div v-if="!editing">
      <button class="btn-57" @click="editProfile">Edit</button>
    </div>
    <form v-else @submit.prevent="saveChanges" class="info-group">
      <div class="form-group">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" v-model="editedUser.firstName" class="input" autocomplete="off" placeholder="First Name">
      </div>
      <div class="form-group">
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" v-model="editedUser.lastName" class="input" autocomplete="off" placeholder="Last Name">
      </div>
      <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" v-model="editedUser.email" class="input" autocomplete="off" placeholder="Email">
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="editedUser.password" class="input" autocomplete="off" placeholder="Password">
      </div>
      <div class="form-group">
        <label for="balance">Balance:</label>
        <input type="number" id="balance" v-model="editedUser.balance" class="input" autocomplete="off" placeholder="Balance">
      </div>
      <button type="submit" class="btn-57">Save Changes</button>
      <button class="btn-57" @click="cancelEdit">Cancel</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      user: null,
      editedUser: null,
      editing: false
    };
  },
  mounted() {
    this.retrieveUserData();
  },
  methods: {
    editProfile() {
      this.editing = true;
      this.editedUser = { ...this.user };
    },
    saveChanges() {
      this.user = { ...this.editedUser };
      this.editing = false;
      axios.put('')
    },
    cancelEdit() {
      this.editing = false;
      this.editedUser = { ...this.user };
    },

    async retrieveUserData() {
      try {
        let storageObj = JSON.parse(localStorage.getItem('token'));
        if (!storageObj) {
          alert("Could not retrieve user info");
        }
        let userType = "";

        const user = storageObj.userType;
        const id = storageObj.id;
        if (user === "Instructor") {
          userType = "instructors";
        } else if (user === "Owner") {
          userType = "owner";
        } else if (user === "Customer") {
          userType = "customer"
        } else {
          console.log("didnt fetch");
        }
        console.log(`http://localhost:8080/${userType}/${id}`);
        const response = await axios.get(`http://localhost:8080/${userType}/${id}`);
        if (response.status !== 200) {
          alert("Could not retrieve user info");
        }
        console.log(response.data);
        this.user = response.data;
      } catch(err) {
        console.log(`Error caused by ${err}`);
      }
    }
  }
};
</script>



<style scoped>



.info-group label {
  font-weight: bold;
  color: black;
  display: block;
  margin-bottom: 5px;
}

.info-group span {
  display: flex;
  align-items: center;
  border: none;
  height: 48px;
  padding-left: 16px;
  margin-bottom: 10px;
  background-color: #e9ecef;
}




.profile {
  background-color: white;
  width: 100%;
  height: 100%;
  padding: 20px;
}

.input {
  border: 1px solid #ccc;
  height: 48px;
  width: 100%;
  outline: none;
  padding-left: 16px;
  margin-bottom: 10px;
}

.input::placeholder {
  color: black;
  opacity: 0.4;
}

label {
  font-weight: bold;
  color: black;
  display: block;
  margin-bottom: 5px;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 10px;
}

button:hover {
  background-color: #0056b3;
}

button:active {
  background-color: #0056b3;
  transform: translateY(1px);
}
</style>
