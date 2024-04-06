<template>
  <div>
    <Dashboard>
      <div class="main-content">
        <popup v-if="this.errorMessage" :error-message="this.errorMessage" />
        <div class="button-container">
          <button @click="showCreateInstructorPopup = true">Create Instructor account</button>
        </div>
        <instructortable :instructors="instructors" @delete-instructor="deleteInstructor" @edit-instructor="editInstructor"/>
      </div>
    </Dashboard>
    <div class="modal-overlay" v-if="showCreateInstructorPopup">
      <createinstructor class="modal-content" @close="showCreateInstructorPopup = false" @create-instructor="addInstructor" />
    </div>
    <div class="modal-overlay" v-if="showEditInstructorPopup">
      <editinstructor :instructorId="chosenInstructor" class ="modal-content" @close="showEditInstructorPopup = false"  @edit-instructor="fetchInstructors"/>
    </div>
  </div>
</template>

  <script>
  import Dashboard from '@/pages/Dashboard'
  import axios from "axios";
  import popup from "../../components/popup.vue";
  import instructortable from "../../components/instructortable.vue";
  import createinstructor from "../../components/createinstructor.vue";
  import editinstructor from "../../components/editinstructor.vue";
  export default {
    data() {
      return {
        errorMessage: '',
        showCreateInstructorPopup: false,
        showEditInstructorPopup: false,
        instructors: [],
        chosenInstructor: null
      };
    },
    name: 'instructors',
    components: {
      popup,
      Dashboard,
      instructortable,
      createinstructor,
      editinstructor
    },
    mounted() {
      this.fetchInstructors()
    },
    methods: {
      addInstructor() {
        this.fetchInstructors();
      },
      deleteInstructor() {
        this.fetchInstructors();
      },
      editInstructor(index) {
        this.chosenInstructor = index;
        this.showEditInstructorPopup = true;
      },
      fetchInstructors() {
        axios.get('http://localhost:8080/instructors')
          .then(res => {
            this.instructors = res.data;
          })
          .catch(err => {
            console.log(err.response.data);
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
