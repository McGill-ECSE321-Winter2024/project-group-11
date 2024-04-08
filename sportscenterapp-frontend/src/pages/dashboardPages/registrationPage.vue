<template>
    <Dashboard>
        <h1>PAGE FOR SEEING ALL CUSTOMER REGISTRATIONS</h1>
        <h2>Accessible by cusstomer</h2>
        <p> The customer can see/delete his registrations</p>
        <registrationtable :registrations="registrations" @delete-registration="deleteRegistration" />

    </Dashboard>
  </template>

  <script>
  import axios from 'axios';
  import Dashboard from '@/pages/Dashboard'
  import registrationtable from '@/components/registrationtable'



export default {
  name: 'registrationPage',
  components: {
    Dashboard,
    registrationtable
  },
  data() {
    return {
      registrations: []
    };
  },
  mounted(){
    this.fetchRegistrations();
  },
  methods: {
    addRegistration(registration) {

      this.fetchRegistrations();
    },
    deleteRegistration(index) {
      this.registrations.splice(index, 1);
    },
    fetchRegistrations() {
      const tokenString = localStorage.getItem('token');
      if (!tokenString) {
        this.errorMessage = 'You must be logged in to view registrations.';
        return;
      }

      const token = JSON.parse(tokenString);
      const customerId = token.id; // Assuming the token object has an 'id' field
      if (!customerId) {
        this.errorMessage = 'Invalid token. Please log in again.';
        return;
      }

      console.log(customerId);
    // In your registrationPage component, after fetching registrations:
    axios.get(`http://localhost:8080/register/customer/${customerId}`)
      .then(res => {
        // Wrap the single registration object in an array
        this.registrations = [res.data];
        console.log('Registration fetched:', this.registrations);
      })
      .catch(err => {
        console.error('Error fetching registration:', err.response.data);
        this.errorMessage = err.response.data;
      });

    }
}
}
</script>

<style >


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
