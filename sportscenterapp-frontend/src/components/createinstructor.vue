<template>
  <div class="profile">
    <popup :error-message="this.errorMessage" v-if="this.errorMessage" :popup-color="popupColor"/>
    <h2>Create Instructor account</h2>
    <form @submit.prevent="submitForm" class="info-group">
      <div class="form-group">
        <label for="roomNumber">First name:</label>
        <input type="text" id="firstName" v-model="instructor.firstName" class="input" autocomplete="off" placeholder="first name">
      </div>
      <div class="form-group">
        <label for="roomNumber">Last name:</label>
        <input type="text" id="lastName" v-model="instructor.lastName" class="input" autocomplete="off" placeholder="last name">
      </div>
      <div class="form-group">
        <label for="email">Email:</label>
        <input type="text" id="email" v-model="instructor.email" class="input" autocomplete="off" placeholder="email">
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="text" id="password" v-model="instructor.password" class="input" autocomplete="off" placeholder="password">
      </div>
      <div class="form-group">
        <label for="confirmPassword">Confirm password:</label>
        <input type="text" id="confirmPassword" v-model="instructor.confirmPassword" class="input" autocomplete="off" placeholder="password">
      </div>
    </form>
    <div class="button-group">
      <button type="button" @click="cancel" class="btn-57">Cancel</button>
      <button type="submit" @click="submitForm" class="btn-57">Create Instructor</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { showErrMsg } from "./loginform.vue";
import popup from "./popup.vue";
export default {
  data() {
    return {
      instructor: {
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: '',
      },
      errorMessage: "",
      popupColor: "red"
    };
  },
  components: {
    popup
  },
  methods: {
    cancel() {
      this.clearForm();
      this.$emit('close');
    },
    clearForm() {
      this.instructor = {
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: '',
      };
    },
    async submitForm() {
      if (this.instructor.password !== this.instructor.confirmPassword) {
        await showErrMsg.call(this, "Passwords do not match");
        return;
      }
      const body = {
        firstName: this.instructor.firstName,
        lastName: this.instructor.lastName,
        email: this.instructor.email,
        password: this.instructor.password,
        token: "",
        yearsOfExperience: "0",
        biography: ""
      }
      axios.post('http://localhost:8080/instructor', body)
        .then(async (res) => {
          this.popupColor = "green";
          await showErrMsg.call(this, "Instructor created");
          this.popupColor = "red";
          this.$emit('create-instructor');
          this.$emit('close');
        })
        .catch(err => {
          showErrMsg.call(this, err.response.data);
        });

    },
  }
};
</script>

<style scoped>

.button-group{
  display: flex;
  justify-content: space-between;
}
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
  z-index: 99;
  position: fixed;

}

.input {
  border: 1px solid #ccc;
  height: 48px;
  width: 100%;
  outline: none;
  padding-left: 16px;
  padding-right: 16px;
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
}

button:hover {
  background-color: #0056b3;
}

button:active {
  background-color: #0056b3;
  transform: translateY(1px);
}
</style>
