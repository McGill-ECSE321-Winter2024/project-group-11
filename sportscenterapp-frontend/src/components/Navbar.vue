<template>
    <div class="navbar">
      <img src="@/assets/logo.jpg" alt="logo" class="logo">
      <div class="nav-links">
        <router-link to="/" class="nav-link">Home</router-link>
        <router-link to="/about" class="nav-link">About</router-link>
        <router-link to="/classes" class="nav-link">Classes</router-link>
        <router-link to="/gallery" class="nav-link">Gallery</router-link>
        <router-link to="/enroll" class="nav-link enroll-button">Enroll Now</router-link>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'navbar'
  }
  </script>
  
  <style scoped>
  .navbar {
    display: flex;
    justify-content: space-between; /* Move the logo to the left */
    align-items: center;
    background-color: #01161e;
    padding: 20px;
  }
  
  .logo {
    width: 64px;
    border-radius: 3px;
  }
  
  .nav-links {
    display: flex;
  }
  
  .nav-link {
    color: white;
    text-decoration: none;
    margin-right: 0px;
    padding: 8px 16px;
    transition: 0.3s;
  }
  
  .nav-link:hover {
    background-color: #90e0ef;
    color: #01161e;
  }
  
  .enroll-button {
    background-color: #90e0ef;
    border: none;
    margin-right: 16px;
    transition: 0.3s;
  }
  
  .enroll-button:hover {
    background-color: #8de1f0c9;
    color: white;
  }
  </style>
  