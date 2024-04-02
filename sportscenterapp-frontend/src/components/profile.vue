<template>
  <div class="profile">
    <h2>User Profile</h2>
    <div v-if="!editing" class="info-group">
      <label>First Name:</label>
      <span>{{ user.firstName }}</span>
    </div>
    <div v-if="!editing" class="info-group">
      <label>Last Name:</label>
      <span>{{ user.lastName }}</span>
    </div>
    <div v-if="!editing" class="info-group">
      <label>Email:</label>
      <span>{{ user.email }}</span>
    </div>
    <div v-if="!editing" class="info-group">
      <label>Password:</label>
      <span>{{ user.password }}</span>
    </div>
    <div v-if="!editing" class="info-group">
      <label>Balance:</label>
      <span>{{ user.balance }}</span>
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
export default {
  data() {
    return {
      user: {
        firstName: 'John',
        lastName: 'Doe',
        email: 'john@example.com',
        password: 'p@ssword321412',
        balance: 1000
      },
      editedUser: {
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        balance: 0
      },
      editing: false
    };
  },
  methods: {
    editProfile() {
      this.editing = true;
      this.editedUser = { ...this.user };
    },
    saveChanges() {
      this.user = { ...this.editedUser };
      this.editing = false;
    },
    cancelEdit() {
      this.editing = false;
      this.editedUser = { ...this.user };
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