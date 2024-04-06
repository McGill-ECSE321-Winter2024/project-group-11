<template>
  <div class="session-table">
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Biography</th>
        <th>Years of Experience</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(instructor, index) in instructors" :key="index" :class="{ 'active-row': activeIndex === index }" @click="setActiveRow(index)">
        <td>{{ instructor.id }}</td>
        <td>{{ instructor.firstName }}</td>
        <td>{{ instructor.lastName }}</td>
        <td>{{ instructor.biography }}</td>
        <td>{{ instructor.yearsOfExperience }}</td>
      </tr>
      </tbody>
    </table>
    <div class="action-buttons">
      <button class="edit-btn" @click="editActiveInstructor" :disabled="activeIndex === null">Edit</button>
      <button class="del-btn" @click="deleteActiveInstructor" :disabled="activeIndex === null">Delete</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  props: {
    instructors: Array // Array of sports sessions
  },
  data() {
    return {
      activeIndex: null // Index of the currently active row
    };
  },
  methods: {
    editActiveInstructor() {
      if (this.activeIndex !== null) {
        // Emit event to parent component to edit the active session
        this.$emit('edit-instructor', this.instructors[this.activeIndex].id);
      }
    },
    deleteActiveInstructor() {
      if (this.activeIndex !== null) {
        // Emit event to parent component to delete the active session
        console.log(`active instructor: ${this.activeIndex} and ${this.instructors[this.activeIndex].id}`)
        const id = this.instructors[this.activeIndex].id;
        if (!id) {
          console.log("id could not be parsed")
          return;
        }
        axios.delete(`http://localhost:8080/instructors/${id}`)
          .then(res => {
            this.$emit('delete-instructor', this.activeIndex);
          })
          .catch(err => {
            console.log(err);
          })

      }
    },
    setActiveRow(index) {
      // Set the active index to the clicked row index
      this.activeIndex = index;
    }
  }
};
</script>

<style scoped>
.session-table {
  border-collapse: collapse;
  margin: 24px;
  font-size: 0.9em;
  font-family: sans-serif;
  min-width: 400px;
}

.session-table thead tr {
  background-color: #003566;
  color: #ffffff;
  text-align: left;
}

.session-table h2 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.session-table table {
  width: 100%;
  border-collapse: collapse;
}

.session-table th,
.session-table td {
  padding: 12px 15px;
}

.session-table tbody tr {
  border-bottom: 1px solid #dddddd;
}

.session-table tbody tr:nth-of-type(even) {
  background-color: #f3f3f3;
}

.session-table tbody tr:last-of-type {
  border-bottom: 2px solid #003566;
}

.session-table tbody tr.active-row {
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
  margin-left: 10px;
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
}

.edit-btn:hover{
  background-color:#003566;
}


</style>
