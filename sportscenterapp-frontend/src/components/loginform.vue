<template>
    <div class="login-form">
      <h2>Login</h2>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="email">EMAIL</label>
          <br>
          <input placeholder="Email" type="email" id="email" v-model="email" required class="input" autocomplete="off">
        </div>
        <div class="form-group">
          <label for="password">PASSWORD</label>
          <br>
          <input placeholder="Password" type="password" id="password" v-model="password" required class="input" autocomplete="off">
        </div>
        <button type="submit" class="btn-57">Login</button>
      </form>
    </div>
  </template>

  <script>
  import axios from 'axios';
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
        const requestBody = {
          email: "string",
          password: "string",
          userType: "Customer"
        }

        axios.post('http://localhost:8080/authentication/login', requestBody)
          .then(response => {
            console.log(response.data);
            const jsonObject = response.data;
            const jsonString = JSON.stringify(jsonObject);
            localStorage.setItem('loginInfo', jsonString);
          }).catch(err => {
          console.log(err.response.data)
        });
        console.log('Form Submitted');
      }
        // After successful login, you may want to redirect the user or perform other actions
      }


  };
  </script>


<style scoped>

  .login-form{
    background-color: white;
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin: 36px;

  }

  input{

    border-radius: 24px;
    border: none;
    background-color: #e9ecef;
    height: 48px;
    width: 100%;
    outline: none;
    padding-left: 16px;



  }

  input::placeholder{
    color: black;
    opacity: 0.4;


  }


  label {
    font-weight: bold;
    color: black;
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
  -webkit-user-select: none;
  touch-action: manipulation;
  vertical-align: middle;
  width: 100%; /* Updated width */
  word-break: keep-all;
  z-index: 0;
  border-radius: 24px; /* Added border-radius */
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



</style>

