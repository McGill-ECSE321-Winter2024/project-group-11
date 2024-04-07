<template>
  <div>
    <navbar />
    <div class="heading">
      <h1 class="title">Offered Sessions</h1>
    </div>

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
        <button>Register</button>
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

<style scoped>

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

  h2 {
    color: #333;
    margin-bottom: 10px;
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

  .title {
    margin: 16px;
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

</style>


