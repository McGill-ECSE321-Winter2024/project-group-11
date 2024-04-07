<template>
  <div class="classType-table">
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
      <button class="edit-btn" @click="editClassTypes" :disabled="activeIndex === null">Edit</button>
      <button class="del-btn" @click="deleteClassTypes" :disabled="activeIndex === null">Delete</button>
      <button class="approve-btn" @click="approveClassTypes" :disabled="activeIndex === null">Approve</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: {
    classTypes: Array // Array of class types
  },
  data() {
    return {
      activeIndex: null, // Index of the currently active row
      showEditClassTypePopup: false,
    };
  },
  methods: {
    editClassTypes() {
      if (this.activeIndex !== null) {
        // Emit event to parent component to edit the active class type
        this.$emit('edit-classType', this.activeIndex);
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
            console.log(err);
          })

      }
    },
    approveClassTypes() {
      if (this.activeIndex != null) {
        // Emit event to parent component to delete the active session
        this.$emit('approve-classType', this.activeIndex);
      }
    },
    setActiveRow(index) {
      // Set the active index to the clicked row index
      this.activeIndex = index;
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
