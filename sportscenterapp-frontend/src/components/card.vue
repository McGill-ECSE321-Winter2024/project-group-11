<template>
    <div class="card">
      <img :src="imageSrc" alt="Card Image" class="card-image">
      <div class="card-content">
        <h2 class="card-title">{{ title }}</h2>
        <p class="card-description">{{ description }}</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'Card',
    props: {
      imageSrc: {
        type: String,
        required: true
      },
      title: {
        type: String,
        required: true
      },
      description: {
        type: String,
        required: true
      }
    }
  }
  </script>
  
  <style scoped>
  .card {
    width: 200px; 
    height: 200px; 
    border: 1px solid #ccc;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease;
  }
  
  .card:hover {
    transform: scale(1.15);
    cursor: pointer;
  }
  
  .card-image {
    width: 100%;
    height: 120px;
    object-fit: cover;
  }
  
  .card-content {
    padding: 10px;
  }
  
  .card-title {
    font-size: 16px;
    margin-bottom: 5px;
  }
  
  .card-description {
    font-size: 14px;
    color: #555;
  }
  </style>
  