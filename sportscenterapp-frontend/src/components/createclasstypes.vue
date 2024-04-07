<template>
  <div class="profile">
    <h2>Create a Class Type</h2>
    <form @submit.prevent="createClassType" class="info-group">
      <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" id="name" v-model="classType.name" class="input" autocomplete="off" placeholder="Name">
      </div>
      <div class="form-group">
        <label for="description">Description:</label>
        <input type="text" id="description" v-model="classType.description" class="input" autocomplete="off" placeholder="Description">
      </div>
      <div class="form-group">
        <label for="difficultyLevel">Difficulty Level:</label>
        <select id="difficultyLevel" v-model="classType.difficultyLevel" class="input">
          <option value="">Select Level</option>
          <option value="Beginner">Beginner</option>
          <option value="Intermediate">Intermediate</option>
          <option value="Advanced">Advanced</option>
        </select>
      </div>
    </form>
    <div class="button-group">
      <button type="button" @click="cancel" class="btn-57">Cancel</button>
      <button type="submit" @click="createClassType" class="btn-57">Create Class Type</button>
      <button type="submit" @click="proposeClassType" class="btn-57">Propose Class type</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {showErrMsg} from "./loginform.vue";


export default {
  data() {
    return {
      classType: {
        name: '',
        description: '',
        difficultyLevel: '',
        approved: ''
      }
    };
  },
  methods: {
    createClassType() {
      const body = {
        name: this.classType.name,
        description: this.classType.description,
        difficultyLevel: this.classType.difficultyLevel,
        approved: 'true'
      }
      axios.post('http://localhost:8080/classtype', body)
        .then(res => {
          showErrMsg.call(this, "Class type created");
          this.name = '';
          this.description = '';
          this.difficultyLevel = '';
          this.approved = '';
        }).catch(err => {
        showErrMsg.call(this, err.response.data);
      });
      console.log(body)
      console.log("Creating class type:", this.classType);
      this.$emit('create-classType', this.classType);
      this.clearForm();
    },
    proposeClassType() {
      const body = {
        name: this.classType.name,
        description: this.classType.description,
        difficultyLevel: this.classType.difficultyLevel,
        approved: 'false'
      }
      axios.post('http://localhost:8080/classtype', body)
        .then(res => {
          showErrMsg.call(this, "Class type proposed");
          this.name = '';
          this.description = '';
          this.difficultyLevel = '';
          this.approved = '';
        }).catch(err => {
        showErrMsg.call(this, err.response.data);
      });
      console.log(body)
      console.log("Proposing class type:", this.classType);
      this.$emit('create-classType', this.classType);
      this.clearForm();
    },
    cancel() {
      this.clearForm();
      this.$emit('close');
    },
    clearForm() {
      this.classType = {
        name: '',
        description: '',
        difficultyLevel: '',
      };
    }
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
