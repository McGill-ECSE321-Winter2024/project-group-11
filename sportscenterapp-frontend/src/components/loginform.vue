<template>
    <div class="login-form">
      <h2>Login</h2>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="email">Email:</label>
          <input type="email" id="email" v-model="email" required>
        </div>
        <div class="form-group">
          <label for="password">Password:</label>
          <input type="password" id="password" v-model="password" required>
        </div>
        <button type="submit">Login</button>
      </form>
      <button @click="triggerAnimation">Trigger Animation</button>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        email: '',
        password: ''
      };
    },
    methods: {
      submitForm() {
        // Here you would typically send the login data to your backend for authentication
        console.log('Login Form Submitted');
        console.log('Email:', this.email);
        console.log('Password:', this.password);
  
        // After successful login, you may want to redirect the user or perform other actions
      },
      triggerAnimation() {
        // Call the function in another component to trigger animation
        this.$emit('triggerAnimation');
      }
    }
  };
  </script>