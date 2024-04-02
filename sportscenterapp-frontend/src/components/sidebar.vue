<template>
    <div class="sidebar">
      <div class="nav-links">
        <router-link to="/gallery/profile" class="nav-link">Profile</router-link>
        <router-link to="/center-infos" class="nav-link">Center Infos</router-link>
        <router-link to="/classes" class="nav-link">Classes</router-link>
        <router-link to="/class-types" class="nav-link">Class Types</router-link>
        <router-link to="/instructors" class="nav-link">Instructors</router-link>
        <router-link to="/customers" class="nav-link">Customers</router-link>
        <router-link to="/registrations" class="nav-link">Registrations</router-link>
      </div>
      <button class="logout-button" @click="logout">Logout</button>
    </div>
  </template>
  
  <script>
  export default {
    name: 'sidebar',
    methods: {
      logout() {
        console.log("logout");
      }
    }
  }
  </script>
  
  <style scoped>
  .sidebar {
    background-color: #01161e;
    padding: 20px;
    display: flex;
    flex-direction: column;
    height: 100%;
  }
  
  .nav-links {
    display: flex;
    flex-direction: column;
    flex-grow: 1; 
  }
  
  .nav-link {
    color: white;
    text-decoration: none;
    margin-bottom: 10px;
    padding: 8px 16px;
    transition: 0.3s;
    border: none; 
    background: none; 
    cursor: pointer; 
  }
  
  .nav-link:hover {
    background-color: #90e0ef;
    color: #01161e;
  }
  
  .logout-button {
    background-color: #c70039;
    border: none;
    color: white;
    padding: 8px 16px;
    cursor: pointer;
    transition: background-color 0.3s;
    margin-top: auto; 
  }
  
  .logout-button:hover {
    background-color: #ff5733;
  }
  </style>
  