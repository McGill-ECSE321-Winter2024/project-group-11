<template>
  <div class="register-form">
    <popup v-if="state.errorMessage" :error-message="state.errorMessage" />
    <popup v-if="state.successMessage" :error-message="state.successMessage" popup-color="#77DD77" />
    <h2>Register</h2>
    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="first-name">FIRST NAME</label>
        <br>
        <input type="text" id="first-name" v-model="state.firstName" required class="input" autocomplete="off" placeholder="First name" @focus="clearErrorMessage">
      </div>
      <div class="form-group">
        <label for="last-name">LAST NAME</label>
        <br>
        <input type="text" id="last-name" v-model="state.lastName" required class="input" autocomplete="off" placeholder="Last name" @focus="clearErrorMessage">
      </div>
      <div class="form-group">
        <label for="email">EMAIL</label>
        <br>
        <input type="email" id="email" v-model="state.email" required class="input" autocomplete="off" placeholder="Email" @focus="clearErrorMessage">
      </div>
      <div class="form-group">
        <label for="password">PASSWORD</label>
        <br>
        <input type="password" id="password" v-model="state.password" required class="input" autocomplete="off" placeholder="Password" @input="checkPasswordStrength" @click="showPasswordValidator" @blur="hidePasswordValidator" @focus="clearErrorMessage">
      </div>
      <div class="form-group">
        <label for="confirm-password">CONFIRM PASSWORD</label>
        <br>
        <input type="password" id="confirm-password" v-model="state.confirmPassword" required class="input" autocomplete="off" placeholder="Confirm password" @focus="clearErrorMessage">
      </div>
      <transition name="fade" mode="out-in">
        <div v-if="state.showPasswordStrength" class="password-check">
          <div class="check-length" :class="{ 'green': state.passwordLength, 'red': !state.passwordLength }">At least 8 characters</div>
          <div class="check-uppercase" :class="{ 'green': state.uppercasePresent, 'red': !state.uppercasePresent }">At least 1 uppercase</div>
          <div class="check-lowercase" :class="{ 'green': state.lowercasePresent, 'red': !state.lowercasePresent }">At least 1 lowercase</div>
          <div class="check-num" :class="{ 'green': state.numberPresent, 'red': !state.numberPresent }">At least 1 number</div>
          <div class="check-char" :class="{ 'green': state.specialCharPresent, 'red': !state.specialCharPresent }">At least 1 special character</div>
        </div>
      </transition>
      <button type="submit" class="btn-57">Register</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';
import { reactive } from 'vue';
import popup from '@/components/popup'

export default {
  components: {
    popup
  },
  setup() {
    const state = reactive({ // Use reactive() to make state reactive
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      confirmPassword: '',
      showPasswordStrength: false,
      passwordLength: false,
      uppercasePresent: false,
      lowercasePresent: false,
      numberPresent: false,
      specialCharPresent: false,
      errorMessage: '',
      successMessage: '',
    });

    const submitForm = () => {
      if (state.password !== state.confirmPassword) {
        state.errorMessage = "Passwords do not match";
        return;
      }
      const requestBody = {
        firstName: state.firstName,
        lastName: state.lastName,
        email: state.email,
        password: state.password,
        id: 0,
        accountBalance: 0,
        token: ""
      }

      axios.post('http://localhost:8080/customer', requestBody)
        .then(response => {
        console.log(response.data);
        state.successMessage = "Account created successfully, please log in !"
        //Clear the form
        state.firstName = '';
        state.lastName = '';
        state.email = '';
        state.password = '';
        state.confirmPassword = '';
      }).catch(err => {
        state.errorMessage = err.response.data;
      });
      
      console.log('Form Submitted');
    };

    const checkPasswordStrength = () => {
      state.passwordLength = state.password.length >= 8;
      state.uppercasePresent = /[A-Z]/.test(state.password);
      state.lowercasePresent = /[a-z]/.test(state.password);
      state.numberPresent = /\d/.test(state.password);
      state.specialCharPresent = /[^\w\s]/.test(state.password);
    };

    const showPasswordValidator = () => {
      state.showPasswordStrength = true;
    };

    const hidePasswordValidator = () => {
      state.showPasswordStrength = false;
    };


    const clearErrorMessage = () => {
      state.errorMessage = '';
    };

    return {
      state,
      submitForm,
      checkPasswordStrength,
      showPasswordValidator,
      hidePasswordValidator,
      clearErrorMessage
    };
  }
};
</script>



<style scoped>

.red {
  color: red;
}

.green {
  color: green;
}
.register-form {
  background-color: white;
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 36px;

}

.input {
  border-radius: 24px;
  border: none;
  background-color: #e9ecef;
  height: 48px;
  width: 100%;
  outline: none;
  padding-left: 16px;
}

.input::placeholder {
  color: black;
  opacity: 0.4;
}

label {
  font-weight: bold;
  color: black;
}

.password-check {
  background-color: #f9f9f9; 
  padding: 12px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 100%;
  margin-bottom: 24px;
}

button {
  align-items: center;
  appearance: none;
  background-clip: padding-box;
  background-color: initial;
  background-image: none;
  border-style: none;
  box-sizing: border-box;
  color: #fff;
  cursor: pointer;
  display: inline-block;
  flex-direction: row;
  flex-shrink: 0;
  font-family: Eina01,sans-serif;
  font-size: 16px;
  font-weight: 800;
  justify-content: center;
  line-height: 24px;
  margin: 0;
  min-height: 48px; /* Updated height */
  outline: none;
  overflow: visible;
  padding: 0 16px; /* Adjusted padding */
  pointer-events: auto;
  position: relative;
  text-align: center;
  text-decoration: none;
  text-transform: none;
  user-select: none;
  touch-action: manipulation;
  vertical-align: middle;
  width: 100%; 
  word-break: keep-all;
  z-index: 0;
  border-radius: 24px;
}

button:before,
button:after {
  border-radius: 80px;
}

button:before {
  background-image: linear-gradient(92.83deg, #a3cef1 0, #6096ba 100%);
  content: "";
  display: block;
  height: 100%;
  left: 0;
  overflow: hidden;
  position: absolute;
  top: 0;
  width: 100%;
  z-index: -2;
}

button:after {
  background-color: initial;
  background-image: linear-gradient(#003554 0, #072433 100%);
  bottom: 4px;
  content: "";
  display: block;
  left: 4px;
  overflow: hidden;
  position: absolute;
  right: 4px;
  top: 4px;
  transition: all 100ms ease-out;
  z-index: -1;
}

button:hover:not(:disabled):before {
  background: linear-gradient(92.83deg, #a3cef1 0%, #6096ba 100%);
}

button:hover:not(:disabled):after {
  bottom: 0;
  left: 0;
  right: 0;
  top: 0;
  transition-timing-function: ease-in;
  opacity: 0;
}

button:active:not(:disabled) {
  color: #ccc;
}

button:active:not(:disabled):before {
  background-image: linear-gradient(0deg, rgba(0, 0, 0, .2), rgba(0, 0, 0, .2)), linear-gradient(92.83deg, #ff7426 0, #f93a13 100%);
}

button:active:not(:disabled):after {
  background-image: linear-gradient(#541a0f 0, #0c0d0d 100%);
  bottom: 4px;
  left: 4px;
  right: 4px;
  top: 4px;
}

button:disabled {
  cursor: default;
  opacity: .24;
}

.fade-enter-active {
  transition: opacity 0.5s;
  transition-delay: 0.1s;
}

.fade-leave-active{
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to  {
  opacity: 0;
}

</style>
