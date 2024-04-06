<template>
  <div class="profile">
    <h2>Center Information</h2>
    <div v-if="!editing" class="info-group">
      <label>Address:</label>
      <span>{{ infos.adress }}</span>
    </div>
    <div v-if="!editing" class="info-group">
      <label>Week:</label>
      <span>{{ infos.week }}</span>
    </div>
    <div v-if="!editing" class="info-group">
      <label>Weekend:</label>
      <span>{{ infos.weekend }}</span>
    </div>
    <div v-if="!editing">
      <button class="btn-57" @click="editProfile">Edit</button>
    </div>
    <form v-else @submit.prevent="saveChanges" class="info-group">
      <popup v-if="this.errMsg" :error-message="this.errMsg" />
      <div class="form-group">
        <label for="adress">Address:</label>
        <input type="text" id="adress" v-model="editedInfos.adress" class="input" autocomplete="off" placeholder="adress">
      </div>
      <div class="form-group">
        <label for="week">Week:</label>
        <input type="text" id="week" v-model="editedInfos.week" class="input">
      </div>
      <div class="form-group">
        <label for="weekend">Weekend:</label>
        <input type="text" id="weekend" v-model="editedInfos.weekend" class="input">
      </div>
      <button type="submit" class="btn-57">Save Changes</button>
      <button class="btn-57" @click="cancelEdit">Cancel</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';
import popup from '@/components/popup';

export default {
  components: {popup},
  data() {
    return {
      infos: {
        adress: '',
        week: '',
        weekend: ''
      },
      editedInfos: {
        adress: '',
        week: '',
        weekend: ''
      },
      editing: false,
      errMsg: ''
    };
  },
  created() {
    // Fetch initial data when component is created
    this.fetchCenterData();
  },
  methods: {
    fetchCenterData() {
      axios.get('http://localhost:8080/center/1')
        .then(response => {
          const data = response.data;
          this.infos.adress = data.adress;
          this.infos.week = data.weekSchedule;
          this.infos.weekend = data.weekendSchedule;
        })
        .catch(error => {
          this.errMsg = error.response.data;
        });
    },
    editProfile() {
      this.editing = true;
      this.editedInfos = { ...this.infos };
    },
    saveChanges() {
      const { adress, week, weekend } = this.editedInfos;
      axios.put(`http://localhost:8080/center/1`, { adress, weekSchedule: week, weekendSchedule: weekend })
        .then(response => {
          // Update local state with edited data
          this.infos = { ...this.editedInfos };
          this.editing = false;
        })
        .catch(error => {
          this.errMsg = error.response.data;
        });
    },
    cancelEdit() {
      this.editing = false;
      this.editedInfos = { ...this.infos };
    }
  }
};
</script>

  
  <style scoped>
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
  }
  
  .input {
    border: 1px solid #ccc;
    height: 48px;
    width: 100%;
    outline: none;
    padding-left: 16px;
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
    margin-right: 10px;
  }
  
  button:hover {
    background-color: #0056b3;
  }
  
  button:active {
    background-color: #0056b3;
    transform: translateY(1px);
  }
  </style>
  