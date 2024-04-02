import Vue from 'vue'
import Router from 'vue-router'
import homepage from '@/pages/Homepage'
import RegisterPage from '@/pages/RegisterPage'
import AboutPage from '@/pages/AboutPage'
import Dashboard from '@/pages/Dashboard'
import profilePage from '@/pages/dashboardPages/profilePage'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: homepage
    },
    {
      path: '/authentication',
      name: 'authentication',
      component: RegisterPage
    },
    {
      path: '/about',
      name: 'about',
      component: AboutPage
    },

    {
      path: '/gallery',
      name: 'gallery',
      component: Dashboard,
    },

    {
      path: '/gallery/profile',
      name: 'profile',
      component: profilePage
      }
  ]
})
