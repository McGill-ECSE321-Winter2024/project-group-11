<template>
    <div class="popup-wrapper">
      <div class="popup" :style="{ backgroundColor: popupColor }">
        {{ errorMessage }}
      </div>
    </div>
  </template>
  
  <script>

export default {
    name: 'popup',

    props: {
    errorMessage: {
      type: String,
      required: true
    },
    popupColor: {
        type: String,
        default: '#ff0062'
    }
  }
};

</script>
  <style>
    .popup-wrapper {
      width: 100%;
      top: 20px;
      margin-bottom: 16px;
      
    }
    .popup {
      padding: 20px;
      color: white;
      background: #ff0062;
      border-radius: 10px;
      box-shadow: 1px 3px 5px rgba(0,0,0,0.2);
      max-width: 400px;
      margin: 0 auto;
      text-align: center;
    }
  </style>