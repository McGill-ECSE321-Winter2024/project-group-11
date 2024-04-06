<template>
    <div class="profile">
      <h2>Create a Session</h2>
      <popup :error-message="this.errorMessage" v-if="this.errorMessage" />
      <form @submit.prevent="editSession" class="info-group">
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
          <input type="number" id="capacity" v-model="session.remainingCapacity" class="input" autocomplete="off" placeholder="Capacity" required>
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
          classType: null
        },
        classtypes: [],
        errorMessage: '',
        successMessage: ''
      };
    },
  
    mounted() {
      this.fetchClassTypes();
      this.fetchSession(this.sessionId);
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
        return `${this.session.startTime}`;
      },
      formattedEndTime() {
        if (!this.session.endTime) return '';
        // Ensure endTime is in "HH:mm:ss" format
        return `${this.session.endTime}`;
      }
    },
  
    methods: {

        fetchSession(id) {
            axios.get(`http://localhost:8080/session/${id}`)
        .then(res => {
          this.session = res.data;
          this.session.classType = res.data.classType.id;
        })
        .catch(err => {
          console.log(err);
        });
            
    },

    submitForm() {
        this.$emit('edit-session');
        this.$emit('close');
    },
      editSession() {

        const startTimeUTC = this.formattedStartTime;
        const endTimeUTC = this.formattedEndTime;
  
       
        const sessionData = {
          roomNumber: this.session.roomNumber,
          price: this.session.price,
          remainingCapacity: this.session.remainingCapacity,
          date: this.formattedDate,
          startTime: startTimeUTC,
          endTime: endTimeUTC,
          classType: { id: this.session.classType },
          id : this.session.id
        }
  
        console.log(sessionData)
        const id = this.session.id;
        axios.put(`http://localhost:8080/session/${id}`, sessionData)
          .then(response => {
            console.log('Session edited successfully:', response.data);

            this.clearForm();
            this.$emit('edit-session', response.data);
            this.$emit('close');
          })
          .catch(error => {
            console.error('Error editing session:', error.response.data);
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
  