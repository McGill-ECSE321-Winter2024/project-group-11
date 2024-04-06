<template>
    <div class="registration-table">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Room Number</th>
            <th>Date</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Instructor</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(registration, index) in registrations" :key="index" :class="{ 'active-row': activeIndex === index }" @click="setActiveRow(index)">
            <td>{{ registration.id }}</td>
            <td>{{ registration.session.classType.name }}</td>
            <td>{{ registration.session.roomNumber }}</td>
            <td>{{ registration.date }}</td>
            <td>{{ registration.session.startTime }}</td>
            <td>{{ registration.session.endTime }}</td>
            <td>{{ registration.session.instructor.firstName }}</td>
          </tr>
        </tbody>
      </table>
      <div class="action-buttons">
        <button class="cnl-btn" @click="cancelActiveRegistratino" :disabled="activeIndex === null">Cancel</button>
      </div>
    </div>
  </template>

  <script>
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
      cancelActiveRegistration() {
        if (this.activeIndex !== null) {
          // Emit event to parent component to cancel the active registration
          this.$emit('cancel-registration', this.activeIndex);
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

  .cnl-btn {
    background-color: #ef233c;
    color: white;
    border-radius: 4px;
    width: 128px;
    height: 32px;
    border: none;
    font-weight: bold;
    margin-left: 10px;
  }

  .cnl-btn:hover{
    background-color: #d90429;
  }



  </style>

