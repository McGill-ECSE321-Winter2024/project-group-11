<template>
  <div>
    <navbar />
    <div class="heading">
      <h1 class="title">Offered Sessions</h1>
    </div>
    <popup :error-message="successMsg" v-if="this.successMsg" popup-color="#77DD77"/>

    <div class="card-container">
      <div v-for="session in sessions" :key="session.id" class="card">
        <div class="image-container">
          <img class="session-image" src="https://www.fitpro.com/blog/wp-content/uploads/2022/07/iStock-927938242-cropped.jpg" alt="Session Image" />
        </div>

        <div class="session-details">
          <h2>{{ session.classType.name }}</h2>
          <p>
            Description: {{ session.classType.description }}
          </p>
          <p>Date & Time: {{ session.date }}: {{ session.startTime }} - {{ session.endTime }}</p>
          <p>Price: ${{ session.price }} | Remaining Capacity: {{ session.remainingCapacity }}</p>
          <p>Room: {{ session.roomNumber }}</p>
          <div>
            <p v-if="session.instructor">
              Instructor: {{ session.instructor.firstName }} {{ session.instructor.lastName }}
            </p>
            <p v-else>
              No instructor assigned
            </p>
          </div>
          <span :class="{
            'beginner': session.classType.difficultyLevel === 'Beginner',
            'intermediate': session.classType.difficultyLevel === 'Intermediate',
            'advanced': session.classType.difficultyLevel === 'Advanced'
          }">
            {{ session.classType.difficultyLevel }}
          </span>
        </div>
        <button @click="registerSession(session)">Register</button>
      </div>
    </div>

    <div class="modal-overlay" v-if="showConfirmPopup" @click="showConfirmPopup = false">
        <div v-if="userType === 'Customer'" class="modal-content">
          <popup :error-message="this.errorMessage" v-if="this.errorMessage" />
          <h1>Confirm Registration</h1>
          <h3>{{selectedSession.classType.difficultyLevel}} {{selectedSession.classType.name}}</h3>
          <div class="Time">
            <h3>Date: {{selectedSession.date}}</h3>
            <h3>Start: {{ selectedSession.startTime }}</h3>
            <h3>End: {{ selectedSession.endTime }}</h3>
          </div>
          <div class="price">
            <h3> Price: {{ selectedSession.price}}$</h3>
            <h3>Current balance: {{ customer.accountBalance }}$</h3>
          </div>
          <div class="popup-buttons">
            <button class="cancel" @click="showConfirmPopup = false">Cancel</button>
            <button @click="confirmRegistration(selectedSession)">Confirm</button>
          </div>
        </div>

        <div v-if="userType !== 'Customer'" class="modal-content">
          <h1>Want to join us? Create an account!</h1>
          <router-link to="/authentication" class="create"><button>Enroll now</button></router-link>
    </div>
    </div>



  </div>
</template>

<script>
import axios from 'axios';
import navbar from '@/components/Navbar';
import popup from '@/components/popup';

export default {
  name: 'classes',
  components: {
    navbar,
    popup
  },
  data() {
    return {
      sessions: [],
      showConfirmPopup: false,
      selectedSession: null,
      userType: null,
      customer: null,
      errorMessage:'',
      successMsg:''
    };
  },
  mounted() {
    this.fetchSessions(); // Fetch sessions when component mounts
    this.getLoggedInUser();
  },
  methods: {
    fetchSessions() {
      axios.get('http://localhost:8080/session/')
        .then(response => {
          this.sessions = response.data; // Assign the fetched sessions to the sessions array
        })
        .catch(error => {
          console.error("There was an error fetching the sessions: ", error.response);
        });
    },
    showSuccessMessage(message) {
    this.successMsg = message;
    setTimeout(() => {
      this.successMsg = ''; // Clear the success message after 3 seconds
    }, 3000); // 3000 milliseconds = 3 seconds
  },

    getLoggedInUser(){
      const localObj = JSON.parse(localStorage.getItem('token'));
        if (!localObj) {
          this.userType = null;
          return;
        }

        this.user = localObj;
        this.userType = localObj.userType;
    },

    registerSession(session){
      this.showConfirmPopup = true;
      this.selectedSession = session;
      this.customer = this.fetchCustomer();
    },

    confirmRegistration(selectedSession){
      const currentDate = new Date();

      const hours = ("0" + currentDate.getHours()).slice(-2);
      const minutes = ("0" + currentDate.getMinutes()).slice(-2);
      const formattedTime = `${hours}:${minutes}:00`;

      const year = currentDate.getFullYear();
      const month = ("0" + (currentDate.getMonth() + 1)).slice(-2);
      const day = ("0" + currentDate.getDate()).slice(-2);
      const formattedDate = `${year}-${month}-${day}`;


      const body = {
        date: formattedDate,
        time: formattedTime,
        session: selectedSession,
        customer: this.customer,
        id:0
      };

      console.log(body);
      axios.post('http://localhost:8080/register', body)
      .then(response => {
          console.log('Registration successful:', response.data);
          this.showSuccessMessage('Successfully registered to session');
        })
        .catch(error => {
          console.log('Error registering session:', error.response.data);
          this.errorMessage = error.response.data;
        })

    },

    fetchCustomer(){
      const localObj = JSON.parse(localStorage.getItem('token'));
        if (!localObj) {
          return;
        }
        const id = localObj.id;

        axios.get(`http://localhost:8080/customer/${id}/`)
          .then(res => {
            this.customer = res.data;
          })
          .catch(err => {
            console.log(err.data);
          })



    }
  }
}
</script>

<style scoped>

.heading {
  margin: 50px;
}

.title {
  font-weight: bold;
}

  .card {
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    padding: 20px;
    margin: 16px;
    display: flex;
    flex-direction: row;
    max-height: 400px; /* Set a maximum height for the card */
  }

  .card:hover {
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
  }

  .image-container {
    width: 400px;
    flex: 0 0 auto; /* Don't allow the image container to grow or shrink */
    margin-right: 20px;
  }

  .session-image {
    width: 100%;
    height: 100%;
    object-fit:contain;
  }

  .session-details {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: auto;
    margin: 8px;
  }

  .Time, .price {
    display: flex;
    justify-content: space-between;
  }

  h2 {
    color: #333;
    margin-bottom: 10px;
  }

  h3 {
    margin: 10px;
  }

  p {
    color: #666;
  }

  .beginner {
    color: green;
    font-weight: bold;
    font-size: 1.2em;
  }

  .intermediate {
    color: yellow;
    font-weight: bold;
    font-size: 1.2em;
  }

  .advanced {
    color: red;
    font-weight: bold;
    font-size: 1.2em;
  }


  .cancel {
    --color: red;
    border-color:red ;
  }


  button {
 --color: #00A97F;
 padding: 0.8em 1.7em;
 background-color: transparent;
 border-radius: .3em;
 position: relative;
 overflow: hidden;
 cursor: pointer;
 transition: .5s;
 font-weight: 400;
 font-size: 17px;
 border: 1px solid;
 font-family: inherit;
 text-transform: uppercase;
 color: var(--color);
 z-index: 1;
 align-self: flex-end;

}

button::before, .button::after {
 content: '';
 display: block;
 width: 50px;
 height: 50px;
 transform: translate(-50%, -50%);
 position: absolute;
 border-radius: 50%;
 z-index: -1;
 background-color: var(--color);
 transition: 1s ease;
}

button::before {
 top: -1em;
 left: -1em;
}

button::after {
 left: calc(100% + 1em);
 top: calc(100% + 1em);
}

button:hover::before, .button:hover::after {
 height: 410px;
 width: 410px;
}

button:hover {
 color: rgb(10, 25, 30);
}

button:active {
 filter: brightness(.8);
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

.popup-buttons {
  display: flex;
  justify-content: space-between;
}

.create {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  margin-right: 10px;
  text-decoration: none;
}

.create:hover {
  text-decoration: none;
}

</style>


