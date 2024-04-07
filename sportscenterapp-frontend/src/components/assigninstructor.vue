<template>
  <div class="profile">
    <h2>Select Instructor</h2>
    <popup :error-message="this.errorMessage" v-if="this.errorMessage" />
    <form @submit.prevent="assignInstructor" class="info-group">
      <div class="form-group">
        <label for="instructor">Instructors: </label>
        <select id="instructor" v-model="session.instructor" class="input">
          <option value="null">Select an Instructor</option>
          <option v-for="i in instructors" :value="i.id" :key="i.id">
            {{ i.firstName }} {{ i.lastName }}
          </option>
        </select>
      </div>
      <div class="button-group">
      <button type="button" @click="cancel" class="btn-57">Cancel</button>
      <button type="submit" class="btn-57">Save changes</button>
    </div>
    </form>
  </div>
</template>

<script>
import axios from 'axios';
import popup from './popup.vue';

export default {
  components: {popup},
  props: {sessionId: Number},
  data() {
    return {
      session: {
        id: '',
        roomNumber: '',
        price: 0,
        remainingCapacity: 0,
        date: '',
        startTime: '',
        endTime: '',
        classType: null,
        instructor: null
      },
      classtypes: [],
      instructors: [],
      errorMessage: '',
      successMessage: ''
    };
  },

  mounted() {
    this.fetchClassTypes();
    this.fetchInstructors();
    this.fetchSession(this.sessionId);
  },

  methods: {

      fetchSession(id) {
          axios.get(`http://localhost:8080/session/${id}`)
      .then(res => {
        this.session = res.data;
        this.session.classType = res.data.classType.id;
        this.session.instructor = res.data.instructor.id;
      })
      .catch(err => {
        console.log(err);
      });
          
  },

  submitForm() {
      this.$emit('edit-instructor');
      this.$emit('close');
  },
    assignInstructor() {

      if (!this.session.instructor) {
      this.errorMessage = 'Please select an instructor.';
      return; // Prevent form submission
    }
     

      const id = this.session.id;
      const instructorId = this.session.instructor;
      axios.put(`http://localhost:8080/session/${id}/instructor/${instructorId}`)
        .then(response => {
          console.log('Assigned Instructor Successfully', response.data);

          this.clearForm();
          this.$emit('edit-instructor', response.data);
          this.$emit('close');
        })
        .catch(error => {
          console.error('Error assigning Instructor', error.response.data);
          this.errorMessage = error.response.data;
        });
    },

    cancel() {
      this.clearForm();
      this.$emit('close');
    },

    clearForm() {
      this.session = {
        id: '',
        roomNumber: '',
        price: 0,
        capacity: 0,
        date: '',
        startTime: '',
        endTime: '',
        classType: ''
      };
    },


    fetchClassTypes() {
      axios.get('http://localhost:8080/classtypes/approved')
        .then(res => {
          this.classtypes = res.data;
        })
        .catch(err => {
          console.log(err.response.data);
        });
    },

    fetchInstructors(){
      axios.get('http://localhost:8080/instructors')
        .then(res => {
          this.instructors = res.data;
        })
        .catch(err => {
          console.log(err.response.data);
        });
    }
  }
};
</script>



<style scoped>

h2 {
  margin-bottom: 48px;
}

.button-group{
  display: flex;
  justify-content: space-between;
}
.info-group label {
  font-weight: bold;
  color: black;
  display: block;
  margin-bottom: 5px;
}

.info-group span {
  display: flex; 
  align-items: center;
  border: none;
  height: 48px;
  padding-left: 16px;
  margin-bottom: 10px;
  background-color: #e9ecef;
}

.profile {
  background-color: white;
  width: 100%;
  height: 40%;
  padding: 20px;
  z-index: 99;
  position: fixed;
  
}

.input {
  border: 1px solid #ccc;
  height: 48px;
  width: 100%;
  outline: none;
  padding-left: 16px;
  padding-right: 16px;
  margin-bottom: 10px;
}

.input::placeholder {
  color: black;
  opacity: 0.4;
}

label {
  font-weight: bold;
  color: black;
  display: block;
  margin-bottom: 5px;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

button:active {
  background-color: #0056b3;
  transform: translateY(1px);
}
</style>
