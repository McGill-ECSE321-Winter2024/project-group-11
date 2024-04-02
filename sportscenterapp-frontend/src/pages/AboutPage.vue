<template>
    <div class="about-page">
      <navbar />
      
      <div class="card-form">
        <h2>Add New Card</h2>
        <form @submit.prevent="addCard">
          <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" v-model="newCard.title" required>
          </div>
          <div class="form-group">
            <label for="description">Description:</label>
            <textarea id="description" v-model="newCard.description" required></textarea>
          </div>
          <div class="form-group">
            <label for="imageSrc">Image URL:</label>
            <input type="url" id="imageSrc" v-model="newCard.imageSrc" required>
          </div>
          <button type="submit">Add Card</button>
        </form>
      </div>
  
      <div class="card-container">
        <Card v-for="(card, index) in cards" :key="index" :imageSrc="card.imageSrc" :title="card.title" :description="card.description" />
      </div>
    </div>
  </template>
  
  <script>
  import navbar from '@/components/Navbar'
  import Card from '@/components/card'
  
  export default {
    name: 'AboutPage',
    components: {
      navbar,
      Card
    },
    data() {
      return {
        newCard: {
          title: '',
          description: '',
          imageSrc: ''
        },
        cards: []
      }
    },
    methods: {
      addCard() {
        this.cards.push({ ...this.newCard });
        // Clear the form after adding the card
        this.newCard = {
          title: '',
          description: '',
          imageSrc: ''
        };
      }
    }
  }
  </script>
  
  <style scoped>
  body {
    background-color: aliceblue;
    margin: 10px; /* Added margin around the window */
  }
  
  .card-form {
    margin: 24px;
  }
  
  .form-group {
    margin-bottom: 10px;
  }
  
  form input[type="text"],
  form textarea,
  form input[type="url"] {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  form button {
    padding: 10px 20px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  form button:hover {
    background-color: #0056b3;
  }
  
  .card-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    
    margin-left: 24px; /* Added margin to the left of the card container */
    margin-right: 24px;
  }
  
  .card-container .card {
    margin-left: 24px;
  }
  
  </style>
  