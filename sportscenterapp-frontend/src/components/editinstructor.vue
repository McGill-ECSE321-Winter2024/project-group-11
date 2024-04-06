<template>
  <div class="profile">
    <popup :error-message="this.errorMessage" v-if="this.errorMessage" />
    <h2>Edit Instructor account</h2>
    <form @submit.prevent="submitForm" class="info-group">
      <div class="form-group">
        <label for="firstName">First name:</label>
        <input type="text" id="firstName" v-model="instructor.firstName" class="input" autocomplete="off" placeholder="first name" readonly>
      </div>
      <div class="form-group">
        <label for="lastName">Last name:</label>
        <input type="text" id="lastName" v-model="instructor.lastName" class="input" autocomplete="off" placeholder="last name" readonly>
      </div>
      <div class="form-group">
        <label for="email">Email:</label>
        <input type="text" id="email" v-model="instructor.email" class="input" autocomplete="off" placeholder="email" readonly>
      </div>
      <div class="form-group">
        <label for="password">Password: </label>
        <input type="text" id="password" v-model="instructor.password" class="input" autocomplete="off" placeholder="password">
      </div>
      <div class="form-group">
        <label for="biography">Biography:</label>
        <input type="text" id="confirmPassword" v-model="instructor.biography" class="input" autocomplete="off" placeholder="biography">
      </div>
      <div class="form-group">
      <label for="biography">Years of Experience:</label>
      <input type="text" id="experience" v-model="instructor.yearsOfExperience" class="input" autocomplete="off" placeholder="experience">
  </div>
    </form>
    <div class="button-group">
      <button type="button" @click="cancel" class="btn-57">Cancel</button>
      <button type="submit" @click="submitForm" class="btn-57">Confirm changes</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { showErrMsg } from "./loginform.vue";
import popup from "./popup.vue";
export default {
  props: {
    instructorId: Number
  },
  data() {
    return {
      instructor: {
        firstName: '',
        lastName: '',
        email: '',
        biography: '',
        password: '',
        yearsOfExperience: 0
      },
      errorMessage: ""
    };
  },
  mounted() {
    this.fetchInstructor(this.instructorId);
  },
  components: {
    popup
  },
  methods: {
    cancel() {
      this.$emit('close');
    },
    fetchInstructor(id) {
      axios.get(`http://localhost:8080/instructors/${id}`)
        .then(res => {
          this.instructor = res.data;
        })
        .catch(err => {
          console.log(err);
        });

    },
    async submitForm() {
      await this.editInstructor();
      console.log("EDITED");
      if (this.errorMessage) {
        return;
      }
      this.$emit('edit-instructor');
      this.$emit('close');
    },
    async editInstructor() {
      const requests = [];
      requests.push(
        axios.put(`http://localhost:8080/instructors/${this.instructorId}/password?newPassword=${this.instructor.password}`)
          .catch(err => {
            showErrMsg.call(this, err.response.data);
          }),
        axios.put(`http://localhost:8080/instructors/${this.instructorId}/bio?bio=${this.instructor.biography}`)
          .catch(err => {
            showErrMsg.call(this, err.response.data);
          }),
        axios.put(`http://localhost:8080/instructors/${this.instructorId}/yrOfExp?experience=${this.instructor.yearsOfExperience}`)
          .catch(err => {
            showErrMsg.call(this, err.response.data);
          })
      );
      return Promise.all(requests);

    },
  }
};
</script>

<style scoped>

.button-group{
  display: flex;
  justify-content: space-between;
}
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
  z-index: 99;
  position: fixed;

}

.input {
  border: 1px solid #ccc;
  height: 48px;
  width: 100%;
  outline: none;
  padding-left: 16px;
  padding-right: 16px;
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
}

button:hover {
  background-color: #0056b3;
}

button:active {
  background-color: #0056b3;
  transform: translateY(1px);
}
</style>
