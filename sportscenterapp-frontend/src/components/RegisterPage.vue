<template>
    <div class="register-page">
      <navbar />  
      <registerlogin />
    </div>
  </template>


<script>
import navbar from '@/components/Navbar'
import registerlogin from '@/components/registerlogin'

export default {
    name: 'RegisterPage',
    components: {
      navbar,
      registerlogin
    }
}
</script>

<style>
    body {
        background-color: aliceblue;
    }
</style>