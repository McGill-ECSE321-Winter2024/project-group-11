<template>
  <div class="classType-table">
    <popup :error-message="this.errorMessage" v-if="this.errorMessage" />
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Difficulty Level</th>
        <th>Approved</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(classType, index) in classTypes" :key="index" :class="{ 'active-row': activeIndex === index }" @click="setActiveRow(index)">
        <td>{{ classType.id }}</td>
        <td>{{ classType.name }}</td>
        <td>{{ classType.description }}</td>
        <td>{{classType.difficultyLevel}}</td>
        <td>{{ classType.approved }}</td>
      </tr>
      </tbody>
    </table>
    <div class="action-buttons">
      <button class="edit-btn" @click="editClassTypes" :disabled="activeIndex === null" v-if="showButton">Edit</button>
      <button class="del-btn" @click="deleteClassTypes" :disabled="activeIndex === null" v-if="showButton">Delete</button>
      <button class="approve-btn" @click="approveClassTypes" :disabled="activeIndex === null" v-if="showButton">Approve</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import popup from "./popup.vue";

export default {
  props: {
    classTypes: Array // Array of class types
  },
  data() {
    return {
      activeIndex: null, // Index of the currently active row
      showEditClassTypePopup: false,
      showButton: false,
      errorMessage:''
    };
  },
  components: {
    popup
  },
  mounted () {
    this.loadClassTypes();
    this.showModifyButton();
  },
  methods: {
    editClassTypes() {
      if (this.activeIndex !== null) {
        // Emit event to parent component to edit the active class type
        this.$emit('edit-classType', this.classTypes[this.activeIndex].id);
      }
    },
    deleteClassTypes() {
      if (this.activeIndex !== null) {
        // Emit event to parent component to delete the active classtype
        console.log(`active classType: ${this.activeIndex} and ${this.classTypes[this.activeIndex].id}`)
        const id = this.classTypes[this.activeIndex].id;
        if (!id) {
          console.log("id could not be parsed")
          return;
        }
        axios.delete(`http://localhost:8080/classtype/${id}`)
          .then(res => {
            this.$emit('delete-classType', this.activeIndex);
          })
          .catch(err => {
            this.showErrorMessage('Error deleting class type');
          })

      }
    },
    approveClassTypes() {
      if (this.activeIndex != null) {
        // Emit event to parent component to delete the active session
        this.$emit('approve-classType', this.classTypes[this.activeIndex].id);
      }
    },
    setActiveRow(index) {
      // Set the active index to the clicked row index
      this.activeIndex = index;
    },
    showModifyButton() {
      const storedObj = JSON.parse(localStorage.getItem('token'));
      if (storedObj) {
        console.log(storedObj.userType)
        this.showButton = storedObj.userType === 'Owner';
      }
      else {
        this.showButton = false
      }
    },
    loadClassTypes() {
      axios.get('http://localhost:8080/classtypes')
        .then(res => {
          this.classTypes = res.data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    showErrorMessage(message){
      this.errorMessage = message;
      setTimeout(() => {
        this.errorMessage = '';
      }, 3000);
    },
    }
};
</script>

<style scoped>
.classType-table {
  border-collapse: collapse;
  margin: 24px;
  margin-right: 24px;
  font-size: 0.9em;
  font-family: sans-serif;
  min-width: 400px;
}

.classType-table thead tr {
  background-color: #003566;
  color: #ffffff;
  text-align: left;
}

.classType-table h2 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.classType-table table {
  width: 100%;
  border-collapse: collapse;
}

.classType-table th,
.classType-table td {
  padding: 12px 15px;
}

.classType-table tbody tr {
  border-bottom: 1px solid #dddddd;
}

.classType-table tbody tr:nth-of-type(even) {
  background-color: #f3f3f3;
}

.classType-table tbody tr:last-of-type {
  border-bottom: 2px solid #003566;
}

.classType-table tbody tr.active-row {
  font-weight: bold;
  color: #003566;
  background-color: #90e0ef;
}

.action-buttons {
  margin-top: 10px;
  display: flex;
  justify-content: flex-end;
}

.del-btn {
  background-color: #ef233c;
  color: white;
  border-radius: 4px;
  width: 128px;
  height: 32px;
  border: none;
  font-weight: bold;
  //margin-left: 10px;
}

.del-btn:hover{
  background-color: #d90429;
}

.edit-btn {
  background-color: #B6BBC4;
  color: white;
  border-radius: 4px;
  width: 128px;
  height: 32px;
  border: none;
  font-weight: bold;
  margin-right: 10px;
}

.edit-btn:hover{
  background-color:#003566;
}

.approve-btn {
  background-color:#006400;
  color: white;
  border-radius: 4px;
  width: 128px;
  height: 32px;
  border: none;
  font-weight: bold;
  margin-left: 10px;
  margin-right: 10px;
}

.approve-btn:hover{
  background-color: #33FF5E;
}


</style>
