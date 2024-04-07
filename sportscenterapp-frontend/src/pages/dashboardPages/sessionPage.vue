<template>
  <div>
    <Dashboard>
      <div class="main-content">
        <div class="button-container">
          <button @click="showCreateSessionPopup = true">Create Session</button>
        </div>
        <sessiontable :sessions="sessions" @edit-session="editSession" @delete-session="deleteSession" @edit-instructor="assignInstructor" />
      </div>
    </Dashboard>
    
    <div class="modal-overlay" v-if="showCreateSessionPopup">
        <createviewsessions class ="modal-content" @close="showCreateSessionPopup = false" @create-session="addSession" />
    </div>

    <div class="modal-overlay" v-if="showEditSessionPopup">
        <editsession :sessionId="chosenSession" class="modal-content" @close="showEditSessionPopup = false" @edit-session="editSession" />
    </div>

    <div class="modal-overlay" v-if="showAssignPopup">
        <assigninstructor :sessionId="chosenSession" class="modal-content" @close="showAssignPopup = false" @edit-instructor="assignInstructor" />
    </div>

  </div>
</template>

<script>
import axios from 'axios'
import Dashboard from '@/pages/Dashboard'
import createviewsessions from '@/components/createviewsessions'
import sessiontable from '@/components/sessiontable'
import editsession from '@/components/editsession'
import assigninstructor from '@/components/assigninstructor'

export default {
  name: 'sessionPage',
  components: {
    Dashboard,
    sessiontable,
    createviewsessions,
    editsession,
    assigninstructor
  },
  data() {
    return {
      showCreateSessionPopup: false,
      showEditSessionPopup: false,
      showAssignPopup: false,
      sessions: [],
      chosenSession: null,
      errorMessage: ''
    };
  },
  mounted(){
    this.fetchSessions();
  },
  methods: {
    addSession(session) {

      this.fetchSessions();
      this.showCreateSessionPopup = false;
    },
    editSession(index) {
      this.chosenSession = index;
      this.showEditSessionPopup = true;
      this.fetchSessions();
    },

    assignInstructor(index){
      this.chosenSession = index;
      this.showAssignPopup = true;
      this.fetchSessions();

    },
    deleteSession(index) {
      this.fetchSessions();
    },
    fetchSessions() {
        axios.get('http://localhost:8080/session/')
          .then(res => {
            this.sessions = res.data;
          })
          .catch(err => {
            console.log(err.response.data);
            this.errorMessage = err.response.data;
          })
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