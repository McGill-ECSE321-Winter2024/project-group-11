<template>
    <div class="register-form">
      <h2>Register</h2>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="first-name">First Name:</label>
          <input type="text" id="first-name" v-model="firstName" required>
        </div>
        <div class="form-group">
          <label for="last-name">Last Name:</label>
          <input type="text" id="last-name" v-model="lastName" required>
        </div>
        <div class="form-group">
          <label for="email">Email:</label>
          <input type="email" id="email" v-model="email" required>
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <input type="password" id="password" v-model="password" required>
        </div>
        <div class="form-group">
          <label for="confirm-password">Confirm Password:</label>
          <input type="password" id="confirm-password" v-model="confirmPassword" required>
        </div>
        <button type="submit">Register</button>
      </form>
      <button @click="triggerAnimation">Trigger Animation</button>
    </div>
  </template>
  
  <script>
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
    methods: {
      submitForm() {
        if (this.password !== this.confirmPassword) {
          alert("Passwords do not match");
          return;
        }
        console.log('Form Submitted');
      },
      triggerAnimation() {
        // Call the function in another component to trigger animation
        this.$emit('triggerAnimation');
      }
    }
  };
  </script>
  
  <style>
  /* Add your styles here */
  </style>
  