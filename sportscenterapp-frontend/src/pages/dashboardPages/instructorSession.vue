<template>
  <div>
    <Dashboard>
      <div class="main-content">
        <instructor-session-table :sessions="sessions" @edit-session="editSession" @drop-session="dropSession" />
      </div>
    </Dashboard>
  </div>
</template>

<script>
import axios from 'axios';
import Dashboard from '@/pages/Dashboard'
import instructorSessionTable from "../../components/instructorSession/instructorSessionTable.vue";

export default {
  name: 'instructorSession',
  components: {
    Dashboard,
    instructorSessionTable
  },
  data() {
    return {
      sessions: []
    };
  },
  mounted(){
    this.fetchSessions();
  },
  methods: {
    async editSession() {
      this.sessions = [];
      await this.fetchSessions();
    },
    async dropSession() {
      this.sessions = [];
      await this.fetchSessions();
    },
    fetchSessions() {
      const data = JSON.parse(localStorage.getItem('token'));
      if (!data) {
        return;
      }
      const requests = []
      requests.push(
      axios.get(`http://localhost:8080/session/instructor/${data.id}`)
        .then(res => {
          this.sessions.push(...res.data);
        })
        .catch(err => {
          console.log(err.response.data);
        }),
      axios.get(`http://localhost:8080/session/empty`)
        .then(res => {
          this.sessions.push(...res.data);
        })
        .catch(err => {
          console.log(err);
        })
      );
      return Promise.all(requests);
    }
  }
}
</script>

<style scoped>


.button-container {
  display: flex;
  justify-content: flex-end;
  margin-right: 24px;
  margin-top: 24px;

}

button {
  background-color: #77DD77;
  color: white;
  border-radius: 4px;
  width: 256px;
  height: 48px;
  border: none;
  font-weight: bold;
}

button:hover {
  background-color: #2aaa2a;
}


.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent black overlay */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999; /* Ensure the modal is on top of other content */
}

/* Styles for the modal content */
.modal-content {
  padding: 20px;
  border-radius: 8px;
  min-width: 400px;
  max-width: 700px;
  max-height: 800px;
  height: auto;
  overflow-y: auto; /* Enable scrolling if the content exceeds the height */
  position: absolute;
  top: 50;
  left: 35%;
}

/* Close button styles */
.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  cursor: pointer;
}
</style>
