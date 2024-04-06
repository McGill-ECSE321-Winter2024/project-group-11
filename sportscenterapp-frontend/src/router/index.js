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
import axios from "axios";



Vue.use(Router)

const router = new Router({
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
      meta: {requiresAuth: true}
    },

    {
      path: '/dashboard/profile',
      name: 'profile',
      component: profilePage,
      meta: {requiresAuth: true}
    },

    {
      path: '/dashboard/infos',
      name: 'infos',
      component: centerinfosPage,
      meta: {requiresAuth: true}
    },

    {
      path: '/dashboard/sessions',
      name: 'sessions',
      component: sessionPage,
      meta: {requiresAuth: true}
    },

    {
      path: '/dashboard/class-types',
      name: 'classtypes',
      component: classtypesPage,
      meta: {requiresAuth: true}
    },

    {
      path: '/dashboard/instructors',
      name: 'instructors',
      component: instructorsPage,
      meta: {requiresAuth: true}
    },

    {
      path: '/dashboard/customers',
      name: 'customers',
      component: customerPage,
      meta: {requiresAuth: true}
    },

    {
      path: '/dashboard/registrations',
      name: 'registration',
      component: registrationPage,
      meta: {requiresAuth: true}
    }
  ]
});

router.beforeEach((to, from, next) => {
  const isAuthed = !!localStorage.getItem('token');
  if (to.meta.requiresAuth && !isAuthed) {
    next('/authentication');
  }
  const token = JSON.parse(localStorage.getItem('token')).token;
  
  const userType = JSON.parse(localStorage.getItem('token')).userType;
  if (to.name === 'instructors' && userType !== 'Owner') {
    next('/dashboard');
  }
  next();
});

export default router;
