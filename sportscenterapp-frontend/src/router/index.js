import Vue from 'vue'
import Router from 'vue-router'
import homepage from '@/pages/Homepage'
import RegisterPage from '@/pages/RegisterPage'
import AboutPage from '@/pages/AboutPage'
import Dashboard from '@/pages/Dashboard'
import profilePage from '@/pages/dashboardPages/profilePage'
import centerinfosPage from '@/pages/dashboardPages/centerinfosPage'
import sessionPage from '@/pages/dashboardPages/sessionPage'
import classtypesPage from '@/pages/dashboardPages/classtypesPage'
import instructorsPage from '@/pages/dashboardPages/instructorsPage'
import customerPage from '@/pages/dashboardPages/customerPage'
import registrationPage from '@/pages/dashboardPages/registrationPage'
import ClassesPage from '@/pages/ClassesPage'


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
      path: '/classes',
      name: 'classes',
      component: ClassesPage
    },

    {
      path: '/dashboard',
      name: 'dashboard',
      component: Dashboard,
    },

    {
      path: '/dashboard/profile',
      name: 'profile',
      component: profilePage
      },

      {
      path: '/dashboard/infos',
      name: 'infos',
      component: centerinfosPage
      },

      {
        path: '/dashboard/sessions',
        name: 'sessions',
        component: sessionPage
      },

      {
        path: '/dashboard/class-types',
        name: 'classtypes',
        component: classtypesPage
      },

      {
        path: '/dashboard/instructors',
        name: 'instructors',
        component: instructorsPage
      },

      {
        path: '/dashboard/customers',
        name: 'customers',
        component: customerPage
      },

      {
        path: '/dashboard/registrations',
        name: 'registration',
        component: registrationPage
      },





      


  ]
})
