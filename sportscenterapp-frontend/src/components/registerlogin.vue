<template>
  <div>
    <div class="container">
      <div class="box1">
        <div class="register-form">
          <registerform @triggerAnimation="triggerAnimation":class="{ 'fade-out': ismaskMoved }" />
        </div>
      </div>

      <div class="box2">
        <div class="login-form">
          <loginform @triggerAnimation="triggerAnimation":class="{ 'fade-out': !ismaskMoved }" />
        </div>
      </div>
      <div class="mask" :class="{ 'move-left': ismaskMoved }"></div>
    </div>
  </div>
</template>

<script>
import registerform from './registerform.vue';
import loginform from './loginform.vue';
export default {
  components: {
    registerform,
    loginform
  },
  data() {
    return {
      ismaskMoved: false
    };
  },
  methods: {
    triggerAnimation() {
      this.ismaskMoved = !this.ismaskMoved;
    }
  }
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  position: relative;
}

.box1 {
  background-color: orange;
  height: 200px;
  width: 200px;
}

.box2 {
  background-color: green;
  height: 200px;
  width: 200px;
}


.mask {
  background-color: blue;
  height: 200px;
  width: 200px;
  position: absolute;
  margin-left: 200px;
  z-index: 2;
  transition: transform 1.5s ease-in-out;
}

.move-left {
  transform: translateX(-200px);
}

.fade-out {
  opacity: 0;
}

.register-form {
  opacity: 1;
  transition: opacity 1.5s ease-in-out; /* Add transition for opacity */
}

.register-form.fade-out {
  opacity: 0;
}

.login-form {
  opacity: 1;
  transition: opacity 1.5s ease-in-out; /* Add transition for opacity */
}

.login-form.fade-out {
  opacity: 0;
}
</style>
