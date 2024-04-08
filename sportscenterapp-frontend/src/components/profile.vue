<template>
  <div class="profile">
    <h2>User Profile</h2>
    <div v-for="(value, key) in user" :key="key" v-if="!editing && key !== 'id' && key !== 'token'"  class="info-group">
      <label>{{ keyConverter[key] }}</label>
      <span>{{value}}</span>
    </div>
    <div v-if="!editing">
      <button class="btn-57" @click="editProfile">Edit</button>
    </div>
    <form v-else @submit.prevent="saveChanges" class="info-group">
      <popup v-if="this.errMsg" :error-message="this.errMsg" />
      <div v-for="(value, key) in editedUser" :key="key" v-if="editing && key !== 'id' && key !== 'token'"  class="info-group">
        <label>{{ keyConverter[key] }}</label>
        <input type="text" :id="key" v-model="editedUser[key]" class="input" autocomplete="off" :readonly="isImmutableData(key)">
      </div>
      <button type="submit" class="btn-57">Save Changes</button>
      <button class="btn-57" @click="cancelEdit">Cancel</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";
import Popup from "./popup.vue";
export default {
  components: {Popup},
  data() {
    return {
      user: null,
      editedUser: null,
      editing: false,
      errMsg: "",
      keyConverter: {
        firstName: "First Name",
        lastName: "Last Name",
        password: "Password",
        email: "Email Address",
        yearsOfExperience: "Years of Experience",
        biography: "Biography",
        accountBalance: "Account Balance"
      }
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
    async saveChanges() {
      this.errMsg = "";
      this.user = { ...this.editedUser };
      await this.updateUserData();
      console.log(this.errMsg);
      if (this.errMsg) {
        return;
      }
      this.editing = false;
    },
    cancelEdit() {
      this.editing = false;
      this.editedUser = { ...this.user };
    },
    updateUserData() {
      const storageObj = JSON.parse(localStorage.getItem('token'));
      const userType = storageObj.userType;

      // Array to store all the requests (so that they all resolve first)
      const requests = [];
      if (userType === "Customer") {

        requests.push(
          axios.put(`http://localhost:8080/customer/${storageObj.id}/update-balance?updated-balance=${this.editedUser.accountBalance}`)
            .catch(err => {
              this.errMsg = err.response.data;
            }),
          axios.put(`http://localhost:8080/customer/${storageObj.id}?password=${this.editedUser.password}`)
            .catch(err => {
              this.errMsg = err.response.data;
            })
        );
      } else if (userType === "Instructor") {
        requests.push(
          axios.put(`http://localhost:8080/instructors/${storageObj.id}/password?newPassword=${this.editedUser.password}`)
            .catch(err => {
              this.errMsg = err.response.data;
            }),
          axios.put(`http://localhost:8080/instructors/${storageObj.id}/bio?bio=${this.editedUser.biography}`)
            .catch(err => {
              this.errMsg = err.response.data;
            }),
          axios.put(`http://localhost:8080/instructors/${storageObj.id}/yrOfExp?experience=${this.editedUser.yearsOfExperience}`)
            .catch(err => {
              this.errMsg = err.response.data;
            })
        );
      } else {
        // Owner or other user types
        axios.put(`http://localhost:8080/owner/${storageObj.id}/password?newPassword=${this.editedUser.password}`)
          .catch(err => {
            this.errMsg = err.response.data;
          })
      }

      return Promise.all(requests);
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
    },
    isImmutableData(key) {
      return key === "firstName" || key === "lastName" || key === "email";
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
