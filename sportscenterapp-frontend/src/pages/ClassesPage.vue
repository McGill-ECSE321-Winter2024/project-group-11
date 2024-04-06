<template>
  <div>
    <navbar />
    <div class="heading">
      <h1 class="title">Offered Sessions</h1>
    </div>

    <div class="card-container">
      <div v-for="session in sessions" :key="session.id" class="card">
        <h2>{{ session.classType.name }}</h2>
        <p> Description: {{ session.classType.description }}</p>
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
        <span :class="{'beginner': session.classType.difficultyLevel === 'Beginner',
                       'intermediate': session.classType.difficultyLevel === 'Intermediate',
                       'advanced': session.classType.difficultyLevel === 'Advanced'}">
                 {{ session.classType.difficultyLevel }}
        </span>
      </div>
    </div>

  </div>
</template>



<script>
import axios from 'axios';
import navbar from '@/components/Navbar';

export default {
  name: 'classes',
  components: {
    navbar,
  },
  data() {
    return {
      sessions: [], // Initialize sessions array
    };
  },
  mounted() {
    this.fetchSessions(); // Fetch sessions when component mounts
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
    }
  }
}
</script>


<style>
.heading {
  margin: 50px;
}

.title {
  font-weight: bold;
}

.card-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around; /* This will space out the cards nicely */
  margin: 15px; /* Adjust the spacing between cards */
}

.card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin: 15px;
  transition: box-shadow 0.3s ease-in-out;
  flex: 0 1 calc(33.333% - 30px);
}

.card:hover {
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}

h2 {
  color: #333;
  margin-bottom: 10px;
}

p {
  color: #666;
  line-height: 1.6;
}

h3 {
  color: #333;
  margin-top: 20px;
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

</style>


