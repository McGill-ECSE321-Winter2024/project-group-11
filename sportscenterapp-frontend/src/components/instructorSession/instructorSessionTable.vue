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
        <td>{{ session.classType.name }}</td>
        <td>{{ session.roomNumber }}</td>
        <td>{{ session.price }}</td>
        <td>{{ session.remainingCapacity }}</td>
        <td>{{ session.date }}</td>
        <td>{{ session.startTime }}</td>
        <td>{{ session.endTime }}</td>
        <td>{{ session.instructor ? session.instructor.firstName : "Not Assigned" }}</td>
      </tr>
      </tbody>
    </table>
    <div class="action-buttons">
      <button class="edit-btn" @click="instructSession" :disabled="activeIndex === null">Instruct Session</button>
      <button class="del-btn" @click="dropSession" :disabled="activeIndex === null">Drop Session</button>
    </div>
    <popup v-if="this.errorMessage" :error-message="this.errorMessage" />
  </div>
</template>

<script>
import axios from "axios";
import popup from "../popup.vue";
import { showErrMsg} from "../loginform.vue";

export default {
  components: {
    popup
  },
  props: {
    sessions: Array // Array of sports sessions
  },
  data() {
    return {
      activeIndex: null,
      errorMessage: ""
    };
  },
  methods: {
    instructSession() {
      if (this.activeIndex !== null) {
        // Emit event to parent component to edit the active session
        const session = this.sessions[this.activeIndex];
        if (session.instructor) {
          return;
        }
        const sessionId = session.id;
        const localObj = JSON.parse(localStorage.getItem('token'));
        if (!localObj) {
          return;
        }
        const instructorId = localObj.id;
        axios.put(`http://localhost:8080/session/${sessionId}/instructor/${instructorId}`)
          .then(res => {
            this.$emit('edit-session', this.activeIndex);
          })
          .catch(err => {
            showErrMsg.call(this, err.response.data);
          })

      }
    },
    dropSession() {
      if (this.activeIndex !== null) {
        const sessionId = this.sessions[this.activeIndex].id;
        console.log(sessionId);
        axios.put(`http://localhost:8080/session/${sessionId}/instructor`)
          .then(res => {
            this.$emit('drop-session');
          })
          .catch(err => {
            showErrMsg.call(this, err.response.data);
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
