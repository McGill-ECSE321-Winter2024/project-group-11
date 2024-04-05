<template>
  <div>
    <Dashboard>
      <div class="main-content">
        <div class="button-container">
          <button @click="showCreateSessionPopup = true">Create Session</button>
        </div>
        <sessiontable :sessions="sessions" @edit-session="editSession" @delete-session="deleteSession" />
      </div>
    </Dashboard>
    
    <div class="modal-overlay" v-if="showCreateSessionPopup">
        <createviewsessions class ="modal-content" @close="showCreateSessionPopup = false" @create-session="addSession" />
    </div>
  </div>
</template>

<script>
import Dashboard from '@/pages/Dashboard'
import createviewsessions from '@/components/createviewsessions'
import sessiontable from '@/components/sessiontable'

export default {
  name: 'sessionPage',
  components: {
    Dashboard,
    sessiontable,
    createviewsessions
  },
  data() {
    return {
      showCreateSessionPopup: false,
      sessions: [
        {
          id: 1,
          roomNumber: 'A101',
          price: 20,
          capacity: 15,
          date: '2024-04-05',
          startTime: '09:00',
          endTime: '10:30',
          classType: 'Yoga',
          instructor: 'John'
        },
        {
          id: 2,
          roomNumber: 'B204',
          price: 15,
          capacity: 20,
          date: '2024-04-06',
          startTime: '11:00',
          endTime: '12:30',
          classType: 'Cardio',
          instructor: 'Timmy'
        },
        // Add more dummy sessions as needed
      ]
    };
  },
  methods: {
    addSession(session) {
      this.sessions.push(session);
      this.showCreateSessionPopup = false;
    },
    editSession(index) {
      // Implement edit session logic here if needed
    },
    deleteSession(index) {
      this.sessions.splice(index, 1);
    }
  }
};
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