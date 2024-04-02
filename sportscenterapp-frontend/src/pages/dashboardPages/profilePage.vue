<template>
    <Dashboard>
        <profile />
    </Dashboard>
  </template>
  
  <script>
  import Dashboard from '@/pages/Dashboard'
  import profile from '@/components/profile'
  
  export default {
    name: 'profilePage',
    components: {
      Dashboard,
      profile
    }
  }
  </script>

  <style>

  </style>