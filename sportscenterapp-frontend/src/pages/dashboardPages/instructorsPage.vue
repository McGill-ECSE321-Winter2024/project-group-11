<template>
    <Dashboard>
      <div class="register-form">
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
          <button type="submit" @click="createInstructor" class="btn-57">Register</button>
        </form>
      </div>
    </Dashboard>
  </template>

  <script>
  import Dashboard from '@/pages/Dashboard'
  import axios from "axios";
  export default {
    data() {
      return {
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: ''
      };
    },
    name: 'instructors',
    components: {
      Dashboard
    },
    methods: {
      createInstructor() {
        console.log("HIII")
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
            alert("Instructor created");
        }).catch(err => {
            alert(err.response.data);
          });
      }
    }
  }
  </script>

  <style>

  </style>
