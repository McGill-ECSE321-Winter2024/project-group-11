<template>
    <div class="profile">
      <h2>Create a Session</h2>
      <popup :error-message="this.errorMessage" v-if="this.errorMessage" />
      <form @submit.prevent="createSession" class="info-group">
        <div class="form-group">
          <label for="roomNumber">Room Number:</label>
          <input type="text" id="roomNumber" v-model="session.roomNumber" class="input" autocomplete="off" placeholder="Room Number" required>
        </div>
        <div class="form-group">
          <label for="price">Price:</label>
          <input type="number" id="price" v-model="session.price" class="input" autocomplete="off" placeholder="Price" required>
        </div>
        <div class="form-group">
          <label for="capacity">Capacity:</label>
          <input type="number" id="capacity" v-model="session.capacity" class="input" autocomplete="off" placeholder="Capacity" required>
        </div>
        <div class="form-group">
          <label>Date:</label>
          <input type="date" id="date" v-model="session.date" class="input" autocomplete="off" required>
        </div>
        <div class="form-group">
          <label for="startTime">Start Time:</label>
          <input type="time" id="startTime" v-model="session.startTime" class="input" autocomplete="off" required>
        </div>
        <div class="form-group">
          <label for="endTime">End Time:</label>
          <input type="time" id="endTime" v-model="session.endTime" class="input" autocomplete="off" required>
        </div>
        <div class="form-group">
          <label for="instructor">Instructor:</label>
          <select id="instructor" v-model="session.instructor" class="input">
            <option value="">Select Instructor</option>
            <option v-for="i in instructors" :value="i.id" :key="i.id">
              {{ i.firstName }} {{ i.lastName }}
            </option>
          </select>
        </div>
        <div class="form-group">
          <label for="classType">Class Type:</label>
          <select id="classType" v-model="session.classType" class="input">
            <option value="">Select Class Type</option>
            <option v-for="c in classtypes" :value="c.id" :key="c.id">
              {{ c.difficultyLevel }} {{ c.name }}
            </option>
          </select>
        </div>
        <div class="button-group">
        <button type="button" @click="cancel" class="btn-57">Cancel</button>
        <button type="submit" class="btn-57">Create Session</button>
      </div>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import popup from './popup.vue';
  
  export default {
    components: {popup},
    data() {
      return {
        session: {
          id: '',
          roomNumber: '',
          price: 0,
          capacity: 0,
          date: '',
          startTime: '',
          endTime: '',
          instructor: '',
          classType: ''
        },
        instructors: [],
        classtypes: [],
        errorMessage: '',
        successMessage: ''
      };
    },
  
    created() {
      this.fetchInstructors();
      this.fetchClassTypes();
    },
  
    computed: {
      formattedDate() {
        if (!this.session.date) return '';
        const date = new Date(this.session.date);
        return date.toISOString().slice(0, 10); // Format as "yyyy-MM-dd"
      },
      formattedStartTime() {
        if (!this.session.startTime) return '';
        // Ensure startTime is in "HH:mm:ss" format
        return `${this.session.startTime}:00`;
      },
      formattedEndTime() {
        if (!this.session.endTime) return '';
        // Ensure endTime is in "HH:mm:ss" format
        return `${this.session.endTime}:00`;
      }
    },
  
    methods: {
      createSession() {

        const startTimeUTC = this.formattedStartTime;
        const endTimeUTC = this.formattedEndTime;
  
       
        const sessionData = {
          roomNumber: this.session.roomNumber,
          price: this.session.price,
          remainingCapacity: this.session.capacity,
          date: this.formattedDate,
          startTime: startTimeUTC,
          endTime: endTimeUTC,
          classType: { id: this.session.classType }
        };

        if (this.session.instructor) {
          sessionData.instructor = { id: this.session.instructor }
        }
  
        axios.post('http://localhost:8080/session/', sessionData)
          .then(response => {
            console.log('Session created successfully:', response.data);


            this.clearForm();
            this.$emit('create-session', response.data);
          })
          .catch(error => {
            console.error('Error creating session:', error.response.data);
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
          instructor: '',
          classType: ''
        };
      },
  
      fetchInstructors() {
        axios.get('http://localhost:8080/instructors')
          .then(res => {
            this.instructors = res.data;
          })
          .catch(err => {
            console.log(err.response.data);
          });
      },
  
      fetchClassTypes() {
        axios.get('http://localhost:8080/classtypes/approved')
          .then(res => {
            this.classtypes = res.data;
          })
          .catch(err => {
            console.log(err.response.data);
          });
      }
    }
  };
  </script>
  

  
  <style scoped>

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
    height: 100%;
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
  