<template>
    <Dashboard>
      <div class="register-form">
        <popup v-if="this.errorMessage" :error-message="this.errorMessage" />
        <h2>Create Instructor</h2>
        <form @submit.prevent="submitForm">
          <div class="form-group">
            <label for="first-name">FIRST NAME</label>
            <br>
            <input type="text" id="first-name" v-model="firstName" required class="input" autocomplete="off" placeholder="First name">
          </div>
          <div class="form-group">
            <label for="last-name">LAST NAME</label>
            <br>
            <input type="text" id="last-name" v-model="lastName" required class="input" autocomplete="off" placeholder="Last name">
          </div>
          <div class="form-group">
            <label for="email">EMAIL</label>
            <br>
            <input type="email" id="email" v-model="email" required class="input" autocomplete="off" placeholder="Email">
          </div>
          <div class="form-group">
            <label for="password">PASSWORD</label>
            <br>
            <input type="password" id="password" v-model="password" required class="input" autocomplete="off" placeholder="Password">
          </div>
          <div class="form-group">
            <label for="confirm-password">CONFIRM PASSWORD</label>
            <br>
            <input type="password" id="confirm-password" v-model="confirmPassword" required class="input" autocomplete="off" placeholder="Confirm password">
          </div>
          <button type="submit" class="btn-57">Register</button>
        </form>
      </div>
    </Dashboard>
  </template>

  <script>
  import Dashboard from '@/pages/Dashboard'
  import axios from "axios";
  import popup from "../../components/popup.vue";
  import { showErrMsg} from "../../components/loginform.vue";

  export default {
    comments: {
      popup
    },
    data() {
      return {
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: '',
        errorMessage: ''
      };
    },
    name: 'instructors',
    components: {
      popup,
      Dashboard
    },
    methods: {
      submitForm() {
        if (this.password !== this.confirmPassword) {
          showErrMsg.call(this, "Passwords do not match");
          return;
        }
        const body = {
          firstName: this.firstName,
          lastName: this.lastName,
          email: this.email,
          password: this.password,
          token: "",
          yearsOfExperience: "0",
          biography: ""
        }
        axios.post('http://localhost:8080/instructor', body)
          .then(res => {
            showErrMsg.call(this, "Instructor created");
            this.firstName = '';
            this.lastName = '';
            this.email = '';
            this.password = '';
            this.confirmPassword = '';
        }).catch(err => {
            showErrMsg.call(this, err.response.data);
          });
      }
    }
  }
  </script>

  <style>

  </style>
