<template>
    <Dashboard>
        <h1>PAGE THAT LISTS ALL CUSTOMERS</h1>
        <h2>Accessible by owner</h2>
        <p> The owner can see a list of the customers</p>
    </Dashboard>
  </template>
  
  <script>
  import Dashboard from '@/pages/Dashboard'
  
  export default {
    name: 'customers',
    components: {
      Dashboard
    }
  }
  </script>

  <style>

  </style>