<template>
    <div class="session-table">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Room Number</th>
            <th>Price</th>
            <th>Capacity</th>
            <th>Date</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Instructor</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(session, index) in sessions" :key="index" :class="{ 'active-row': activeIndex === index }" @click="setActiveRow(index)">
            <td>{{ session.id }}</td>
            <td>{{ session.classType }}</td>
            <td>{{ session.roomNumber }}</td>
            <td>{{ session.price }}</td>
            <td>{{ session.capacity }}</td>
            <td>{{ session.date }}</td>
            <td>{{ session.startTime }}</td>
            <td>{{ session.endTime }}</td>
            <td>{{ session.instructor }}</td>
          </tr>
        </tbody>
      </table>
      <div class="action-buttons">
        <button class="edit-btn" @click="editActiveSession" :disabled="activeIndex === null">Edit</button>
        <button class="del-btn" @click="deleteActiveSession" :disabled="activeIndex === null">Delete</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      sessions: Array // Array of sports sessions
    },
    data() {
      return {
        activeIndex: null // Index of the currently active row
      };
    },
    methods: {
      editActiveSession() {
        if (this.activeIndex !== null) {
          // Emit event to parent component to edit the active session
          this.$emit('edit-session', this.activeIndex);
        }
      },
      deleteActiveSession() {
        if (this.activeIndex !== null) {
          // Emit event to parent component to delete the active session
          this.$emit('delete-session', this.activeIndex);
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
    margin-right: 24px;
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
  