import Vue from 'vue'
import Router from 'vue-router'
import homepage from '@/components/Homepage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: homepage
    }
  ]
})
