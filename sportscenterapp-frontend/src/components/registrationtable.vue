<template>
    <div class="registration-table">
      <table>
        <thead>
          <tr>
            <th>Date</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Class Name</th>
            <th>Room Number</th>
            <th>Instructor Email</th>

          </tr>
        </thead>
        <tbody>
          <tr v-for="(registration, index) in registrations" :key="index" :class="{ 'active-row': activeIndex === index }" @click="setActiveRow(index)">
            <td>{{ registration.date }}</td>
            <td>{{ registration.session.startTime }}</td>
            <td>{{ registration.session.endTime }}</td>
            <td>{{ registration.session.classType.name }}</td>
            <td>{{ registration.session.roomNumber }}</td>
            <td>{{ registration.session.instructor.email }}</td>


          </tr>
        </tbody>
      </table>
      <div class="action-buttons">
        <button class="del-btn" @click="deleteActiveRegistration" :disabled="activeIndex === null">Delete</button>
      </div>
    </div>
  </template>

  <script>
  import axios from 'axios'
  export default {
    props: {
      registrations: Array
    },
    data() {
      return {
        activeIndex: null
      };
    },
    methods: {
      deleteActiveRegistration() {
        if (this.activeIndex !== null) {
        console.log(`active registration: ${this.activeIndex} and ${this.registrations[this.activeIndex].id}`)
        const id = this.registrations[this.activeIndex].id;
        if (!id) {
          console.log("id could not be parsed")
          return;
        }
        axios.delete(`http://localhost:8080/register/${id}`)
          .then(res => {
            this.$emit('delete-registration', this.activeIndex);
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
  .registration-table {
    border-collapse: collapse;
    margin: 24px;
    margin-right: 24px;
    font-size: 0.9em;
    font-family: sans-serif;
    min-width: 400px;
  }

  .registration-table thead tr {
    background-color: #003566;
    color: #ffffff;
    text-align: left;
  }

  .registration-table h2 {
    font-size: 1.5rem;
    margin-bottom: 1rem;
  }

  .registration-table table {
    width: 100%;
    border-collapse: collapse;
  }

  .registration-table th,
  .registration-table td {
    padding: 12px 15px;
  }

  .registration-table tbody tr {
    border-bottom: 1px solid #dddddd;
  }

  .registration-table tbody tr:nth-of-type(even) {
    background-color: #f3f3f3;
  }

  .registration-table tbody tr:last-of-type {
    border-bottom: 2px solid #003566;
  }

  .registration-table tbody tr.active-row {
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



  </style>

