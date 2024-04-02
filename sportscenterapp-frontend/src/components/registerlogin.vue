<template>
  <div>
    <div class="container">
      <div class="box1">
        <loginform @triggerAnimation="triggerAnimation" :class="{ 'fade-out': ismaskMoved }" class="login-form" />
      </div>

      <div class="box2">
        <registerform @triggerAnimation="triggerAnimation" :class="{ 'fade-out': !ismaskMoved }" />
      </div>
      <div class="mask" :class="{ 'move-left': ismaskMoved}">
        <transition name="fade" mode="out-in">
          <div v-if="!ismaskMoved">
            <p>Don't have an account?</p>
            <button @click="triggerAnimation">Create an Account</button>
          </div>
        </transition>
        
        <transition name="fade" mode="out-in">
          <div v-if="ismaskMoved">
            <p>Already have an account?</p>
            <button @click="triggerAnimation">Login</button>
          </div>
        </transition>
      </div>
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
  align-items: center;
  margin-top: 48px;
  margin-bottom: 48px;
  min-height: 700px;
  min-width: 1400px;
  height: 700px;
  box-shadow: 7px 7px 50px #000000;
  background-color: white;
}

.box1,
.box2 {
  background-color: white;
  width: 700px;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.mask {
  height: 100%;
  width: 700px;
  position: absolute;
  margin-left: 700px;
  z-index: 2;
  transition: transform 0.5s ease-in-out; /* Adjust transition duration */
  background: linear-gradient(45deg,#a3cef1, #6ab0df, #003554);
  background-size: 400% 400%;
  animation: gradient 8s ease infinite;
  animation-direction: alternate;
  display: flex;
  justify-content: center;
  align-items: center;
}

p {
  color: white;
  font-size: 48px;
  font-weight: bolder;
}


button {
  align-items: center;
  appearance: none;
  background-clip: padding-box;
  background-color: initial;
  background-image: none;
  border-style: none;
  box-sizing: border-box;
  color: #fff;
  cursor: pointer;
  display: inline-block;
  flex-direction: row;
  flex-shrink: 0;
  font-family: Eina01,sans-serif;
  font-size: 16px;
  font-weight: 800;
  justify-content: center;
  line-height: 24px;
  margin: 0;
  min-height: 48px; /* Updated height */
  outline: none;
  overflow: visible;
  padding: 0 16px; /* Adjusted padding */
  pointer-events: auto;
  position: relative;
  text-align: center;
  text-decoration: none;
  text-transform: none;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
  vertical-align: middle;
  width: 100%; /* Updated width */
  word-break: keep-all;
  z-index: 0;
  border-radius: 24px; /* Added border-radius */
}



  button:before,
  button:after {
    border-radius: 80px;
  }

  button:before {
    background-image: linear-gradient(#003554 0, #072433 100%);
    content: "";
    display: block;
    height: 100%;
    left: 0;
    overflow: hidden;
    position: absolute;
    top: 0;
    width: 100%;
    z-index: -2;
  }

  button:after {
    background-color: initial;
    background-image: linear-gradient(92.83deg, #00a6fb 0%, #02456b 90%);
    bottom: 4px;
    content: "";
    display: block;
    left: 4px;
    overflow: hidden;
    position: absolute;
    right: 4px;
    top: 4px;
    transition: all 100ms ease-out;
    z-index: -1;
  }

  button:hover:not(:disabled):before {
    background-image: linear-gradient(#003554 0, #072433 100%);
  }

  button:hover:not(:disabled):after {
    bottom: 0;
    left: 0;
    right: 0;
    top: 0;
    transition-timing-function: ease-in;
    opacity: 0;
  }

  button:active:not(:disabled) {
    color: #ccc;
  }

  button:active:not(:disabled):before {
    background-image: linear-gradient(92.83deg, #ff7426 0, #f93a13 100%);
  }

  button:active:not(:disabled):after {
    background-image: linear-gradient(#541a0f 0, #0c0d0d 100%);
    bottom: 4px;
    left: 4px;
    right: 4px;
    top: 4px;
  }

  button:disabled {
    cursor: default;
    opacity: .24;
  }

.fade-leave-active {
  transition: opacity;
}

.fade-enter-active {
  transition: opacity;

}
.fade-enter, .fade-leave-to {
  opacity: 0;
}

.fade-enter {
  transition-delay: 2s; /* Adjust transition delay */
}

@keyframes gradient {
  0% {
    background-position: 0%;
  }
  100% {
    background-position: 100%;
  }
}

.move-left {
  transform: translateX(-700px);
}

.fade-out {
  opacity: 0;
}

.register-form,
.login-form {
  opacity: 1;
  transition: opacity 1s ease-in-out;
  width: 100%;
}

.register-form.fade-out,
.login-form.fade-out {
  opacity: 0;
}
</style>
