<template>
    <div class="profile">
      <h2>Center informations</h2>
      <div v-if="!editing" class="info-group">
        <label>Address:</label>
        <span>{{ infos.address }}</span>
      </div>
      <h2>Schedule</h2>
      <div v-if="!editing" class="info-group">
        <label>Monday:</label>
        <span>{{ infos.Monday }}</span>
      </div>
      <div v-if="!editing" class="info-group">
        <label>Tuesday:</label>
        <span>{{ infos.Tuesday }}</span>
      </div>
      <div v-if="!editing" class="info-group">
        <label>Wednesday:</label>
        <span>{{ infos.Wednesday }}</span>
      </div>
      <div v-if="!editing" class="info-group">
        <label>Thursday:</label>
        <span>{{ infos.Thursday }}</span>
      </div>
      <div v-if="!editing" class="info-group">
        <label>Friday:</label>
        <span>{{ infos.Friday }}</span>
      </div>
      <div v-if="!editing" class="info-group">
        <label>Saturday:</label>
        <span>{{ infos.Saturday }}</span>
      </div>
      <div v-if="!editing" class="info-group">
        <label>Sunday:</label>
        <span>{{ infos.Sunday }}</span>
      </div>
      <div v-if="!editing">
        <button class="btn-57" @click="editProfile">Edit</button>
      </div>
      <form v-else @submit.prevent="saveChanges" class="info-group">
        <div class="form-group">
          <label for="address">Address:</label>
          <input type="text" id="address" v-model="editedInfos.address" class="input" autocomplete="off" placeholder="Address">
        </div>
        <div class="form-group">
          <label for="monday">Monday:</label>
          <input type="text" id="monday" v-model="editedInfos.Monday" class="input" autocomplete="off" placeholder="Monday">
        </div>
        <div class="form-group">
          <label for="tuesday">Tuesday:</label>
          <input type="text" id="tuesday" v-model="editedInfos.Tuesday" class="input" autocomplete="off" placeholder="Tuesday">
        </div>
        <div class="form-group">
          <label for="wednesday">Wednesday:</label>
          <input type="text" id="wednesday" v-model="editedInfos.Wednesday" class="input" autocomplete="off" placeholder="Wednesday">
        </div>
        <div class="form-group">
          <label for="thursday">Thursday:</label>
          <input type="text" id="thursday" v-model="editedInfos.Thursday" class="input" autocomplete="off" placeholder="Thursday">
        </div>
        <div class="form-group">
          <label for="friday">Friday:</label>
          <input type="text" id="friday" v-model="editedInfos.Friday" class="input" autocomplete="off" placeholder="Friday">
        </div>
        <div class="form-group">
          <label for="saturday">Saturday:</label>
          <input type="text" id="saturday" v-model="editedInfos.Saturday" class="input" autocomplete="off" placeholder="Saturday">
        </div>
        <div class="form-group">
          <label for="sunday">Sunday:</label>
          <input type="text" id="sunday" v-model="editedInfos.Sunday" class="input" autocomplete="off" placeholder="Sunday">
        </div>
        <button type="submit" class="btn-57">Save Changes</button>
        <button class="btn-57" @click="cancelEdit">Cancel</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        infos: {
          address: '333, nothing street, H3A',
          Monday: '9:00 am to 9:00 pm',
          Tuesday: '9:00 am to 9:00 pm',
          Wednesday: '9:00 am to 9:00 pm',
          Thursday: '9:00 am to 9:00 pm',
          Friday: '9:00 am to 9:00 pm',
          Saturday : '9:00 am to 5:00 pm',
          Sunday : '9:00 am to 5:00 pm'
        },
        editedInfos: {
          address: '',
          Monday: '',
          Tuesday: '',
          Wednesday: '',
          Thursday: '',
          Friday: '',
          Saturday : '',
          Sunday : ''
        },
        editing: false
      };
    },
    methods: {
      editProfile() {
        this.editing = true;
        this.editedInfos = { ...this.infos };
      },
      saveChanges() {
        this.infos = { ...this.editedInfos };
        this.editing = false;
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
  