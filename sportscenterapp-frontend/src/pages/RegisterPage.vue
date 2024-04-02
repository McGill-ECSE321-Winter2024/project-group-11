<template>
    <div class="register-page">
      <navbar />
      <registerlogin />
      
    </div>
  </template>


<script>
import navbar from '@/components/Navbar'
import registerlogin from '@/components/registerlogin'
import login from '@/components/loginform'
import register from '@/components/registerform'

export default {
    name: 'RegisterPage',
    components: {
      navbar,
      registerlogin,
      login,
      register
    }
}
</script>

<style>
    body {
        background-color: aliceblue;
    }
</style>