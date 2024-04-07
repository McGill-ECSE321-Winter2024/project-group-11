<template>
  <div>
    <navbar />
    <div class="heading">
      <h1 class="title">Instructors</h1>
    </div>

    <div class="card-container">
      <div v-for="instructor in instructors" :key="instructor.id" class="card">
        <div class="image-container">
          <img class="instructor-image" src="https://media.licdn.com/dms/image/D4E03AQFWIM6hT9bL5g/profile-displayphoto-shrink_800_800/0/1675634843779?e=2147483647&v=beta&t=G630xyNMuXgKcMSDCd1d3IIPjLjdKFqhRz-9QOYMc4s" alt="Instructor Image" />
        </div>
        <div class="instructor-details">
          <h2>{{ instructor.firstName }} {{ instructor.lastName }}</h2>
          <p>Biography: {{ instructor.biography }}</p>
          <p>Years of Experience: {{ instructor.yearsOfExperience }} </p>
          <p>Contact: {{ instructor.email }}</p>
        </div>
      </div>
    </div>

  </div>
</template>



<script>
import axios from 'axios';
import navbar from '@/components/Navbar';

export default {
  name: 'instructors',
  components: {
    navbar,
  },
  data() {
    return {
      instructors: []    // Initialize instructors array
    };
  },
  mounted() {
    this.fetchInstructors(); // Fetch instructors when component mounts
  },
  methods: {
    fetchInstructors() {
      axios.get('http://localhost:8080/instructors')
        .then(response => {
          this.instructors = response.data; // Assign the fetched instructors to the instructors array
        })
        .catch(error => {
          console.error("There was an error fetching the instructors: ", error.response);
        });
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

  .image-container {
    width: 400px;
    flex: 0 0 auto; /* Don't allow the image container to grow or shrink */
    margin-right: 20px;
  }

  .instructor-image {
    width: 100%;
    height: 100%;
    object-fit:contain;
  }

    .instructor-details {
      flex: 1;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      height: auto;
      margin: 8px;
    }

.card-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around; /* This will space out the cards nicely */
  margin: 15px; /* Adjust the spacing between cards */
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

h2 {
  color: #333;
  margin-bottom: 10px;
}

p {
  color: #666;
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

