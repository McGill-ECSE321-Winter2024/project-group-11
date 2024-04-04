<template>
    <Dashboard>
        <h1>PAGE FOR CREATING INSTRUCTOR ACCOUNTS</h1>
        <h2>Accessible by owner</h2>
        <p> The owner can create instructors</p>
        <loginform />
    </Dashboard>
  </template>
  
  <script>
  import Dashboard from '@/pages/Dashboard'
  import loginform from '@/components/loginform'
  
  export default {
    name: 'instructors',
    components: {
      Dashboard,
      loginform
    }
  }
  </script>

  <style>

  </style>