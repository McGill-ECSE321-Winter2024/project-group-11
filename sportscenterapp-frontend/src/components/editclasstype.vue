<template>
  <div class="profile">
    <popup :error-message="this.errorMessage" v-if="this.errorMessage" />
    <h2>Create a Class Type</h2>
    <form @submit.prevent="submitForm" class="info-group">
      <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" id="name" v-model="classType.name" class="input" autocomplete="off" placeholder="Name" >
      </div>
      <div class="form-group">
        <label for="description">Description:</label>
        <input type="text" id="description" v-model="classType.description" class="input" autocomplete="off" placeholder="Description" >
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
      <div class="form-group">
        <label for="image">Image URL:</label>
        <input type="text" id="image" v-model="classType.image" class="input" autocomplete="off" placeholder="Image URL">
      </div>
      <div class="button-group">
        <button type="button" @click="cancel" class="btn-57">Cancel</button>
        <button type="submit" class="btn-57">Confirm Changes</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";
import {showErrMsg} from "./loginform.vue";
import popup from "./popup.vue";

export default {
  components: {popup},
  props: {
    classTypeId: Number
  },
  data() {
    return {
      classType: {
        name: '',
        description: '',
        difficultyLevel: '',
        approved: '',
        image: ''
      },
      errorMessage: ""
    };
  },
  mounted() {
    this.fetchClassType(this.classTypeId);
    console.log(this.classTypeId);
  },
  methods: {
    fetchClassType(id) {
      axios.get(`http://localhost:8080/classtypes/${id}`)
        .then(res => {
          this.classType = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    async submitForm() {
      await this.editClassType();
      if (this.errorMessage) {
        return;
      }
      this.$emit("edit-classType");
      this.$emit('close');
    },
    async editClassType() {
      const body = {
        name: this.classType.name,
        description: this.classType.description,
        difficultyLevel: this.classType.difficultyLevel,
        approved: '',
        image: this.image
      }
      axios.put(`http://localhost:8080/classtypes/${this.classTypeId}`, body)
        .then(res => {
          console.log("Class type updated", res.data);
          this.clearForm();
          this.$emit('edit-classType', res.data);
          this.$emit('close');
        }).catch(err => {
        console.error("Error updating class type", err.data);
        this.errorMessage = err.response.data;
      });
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
        image: ''
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
