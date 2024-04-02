<template>
    <div class="dashboard-page">
      <navbar />
      <div class="main-container">
        <sidebar class="sidebar" />
        <div class="main-content">
          <slot class="content"></slot>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import navbar from '@/components/Navbar'
  import sidebar from '@/components/sidebar'


  export default {
    name: 'Dashboard',
    components: {
      navbar,
      sidebar,
    }
    
}
  </script>
  
  <style>
  .dashboard-page {
    display: flex;
    flex-direction: column;
    height: 100vh;
  }

  .content{
    margin: 24px;
    width: calc(100% - 48px);
    height: calc(100% - 48px);
  }
  
  .main-container {
    display: flex;
    flex: 1; /* Take remaining space */
  }
  
  .sidebar {
    width: 20%;
  }
  
  .main-content {
    flex: 1; /* Take remaining space */
  }
  
  body {
    background-color: aliceblue;
  }
  </style>
  