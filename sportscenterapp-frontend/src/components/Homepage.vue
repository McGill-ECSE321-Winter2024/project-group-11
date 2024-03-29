<template>
    <div class="homepage">
      <navbar />  
      <div class="content">
        <img src="@/assets/manworkout.jpg" class="background-img">
        <div class="img-text">Welcome to the sports center</div>
        <div class="white-section">
          <div class="card-row">
            <card 
              v-for="(cardData, index) in cardDataList"
              :key="index"
              :imageSrc="cardData.imageSrc"
              :title="cardData.title"
              :description="cardData.description"
              class="card"
            />
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import navbar from '@/components/Navbar'
  import card from '@/components/Card' 
  import catImage from '@/assets/cat1.jpg'
  import catImage2 from '@/assets/cat2.jpg'
  
  export default {
    name: 'homepage',
    components: {
      navbar,
      card
    },
    data() {
      return {
        msg: 'Welcome to the sports center',
        cardDataList: [
          {
            imageSrc: catImage,
            title: 'Card 1',
            description: 'Description for Card 1'
          },
          {
            imageSrc: catImage2,
            title: 'Card 2',
            description: 'Description for Card 2'
          },
          // Add more card data as needed
        ]
      }
    }
  }
  </script>

<style>

body, html {
    overflow-x: hidden;
}

.homepage {
    position: relative;
    width: 100%;
    height: 100%;
}

.content {
    position: relative;
    width: 100%;
    height: 100%;
}

.background-img {
    width: 100%;
    height: 60%;
    object-fit: cover;
}

.white-section {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 40%;
    background-color: white;
    display: flex;
    justify-content: space-around; /* Align cards horizontally */
    align-items: center; /* Align cards vertically */
}

.img-text {
    position: absolute;
    top: 24px;
    left: 30%;
    color: white;
    font-weight: bold;
    font-size: 4vw;
    width: 100%;
}

.card-row {
    display: flex;
    justify-content: space-around;
}

.card {
    margin-right: 24px;
}
</style>
