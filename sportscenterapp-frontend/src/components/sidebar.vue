<template>
  <div class="sidebar">
    <div class="nav-links">
      <router-link to="/dashboard/profile" class="nav-link" active-class="active-link">Profile</router-link>
      <router-link v-if="isOwner" to="/dashboard/infos" class="nav-link" active-class="active-link">Center Infos</router-link>
      <router-link v-if="isOwner" to="/dashboard/sessions" class="nav-link" active-class="active-link">Sessions</router-link>
      <router-link v-if="isInstructor" to="/dashboard/instructor-sessions" class="nav-link" active-class="active-link">Sessions</router-link>
      <router-link v-if="isOwner || isInstructor" to="/dashboard/class-types" class="nav-link" active-class="active-link">Class Types</router-link>
      <router-link v-if="isOwner"to="/dashboard/instructors" class="nav-link" active-class="active-link">Instructors</router-link>
      <router-link v-if="isCustomer" to="/dashboard/registrations" class="nav-link" active-class="active-link">My Registrations</router-link>
    </div>
    <button class="logout-button" @click="logout">Logout</button>
  </div>
</template>

  <script>

  export default {
    data() {
      return {
        isOwner: false,
        isInstructor: false,
        isCustomer: false
      }
    },
    name: 'sidebar',
    mounted() {
      this.userVisibility();
    },
    methods: {
      logout() {
        localStorage.removeItem('token');
        this.$router.push('/');
        console.log("logout");
      },
      userVisibility() {
        const localObj = JSON.parse(localStorage.getItem('token'));
        if (localObj.userType === 'Owner') {
          this.isOwner = true;
        } else if (localObj.userType === 'Instructor') {
          this.isInstructor = true;
        } else {
          this.isCustomer = true;
        }
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

  .active-link {
  background-color: #90e0ef;
  color: #01161e;
}
  </style>
