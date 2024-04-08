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
import registrationPage from '@/pages/dashboardPages/registrationPage'
import ClassesPage from '@/pages/ClassesPage'
import instructorSession from "../pages/dashboardPages/instructorSession.vue";
import axios from "axios";



Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: homepage,
      meta: {requiresAuth: 0}
    },
    {
      path: '/authentication',
      name: 'authentication',
      component: RegisterPage,
      meta: {requiresAuth: 0}
    },
    {
      path: '/about',
      name: 'about',
      component: AboutPage,
      meta: {requiresAuth: 0}
    },

    {
      path: '/classes',
      name: 'classes',
      component: ClassesPage,
      meta: {requiresAuth: 0}
    },

    {
      path: '/dashboard',
      name: 'dashboard',
      component: Dashboard,
      meta: {requiresAuth: 1}
    },

    {
      path: '/dashboard/profile',
      name: 'profile',
      component: profilePage,
      meta: {requiresAuth: 1}
    },

    {
      path: '/dashboard/infos',
      name: 'infos',
      component: centerinfosPage,
      meta: {requiresAuth: 3}
    },

    {
      path: '/dashboard/sessions',
      name: 'sessions',
      component: sessionPage,
      meta: {requiresAuth: 3}
    },

    {
      path: '/dashboard/class-types',
      name: 'classtypes',
      component: classtypesPage,
      meta: {requiresAuth: 2}
    },

    {
      path: '/dashboard/instructors',
      name: 'instructors',
      component: instructorsPage,
      meta: {requiresAuth: 3}
    },

    {
      path: '/dashboard/registrations',
      name: 'registration',
      component: registrationPage,
      meta: {requiresAuth: 1}
    },
    {
      path:'/dashboard/instructor-sessions',
      name: 'instructorSession',
      component: instructorSession,
      meta: {requiresAuth: 2}
    }
  ]
});

router.beforeEach((to, from, next) => {
  const localObj = JSON.parse(localStorage.getItem('token'));
  const securityLevel = to.meta.requiresAuth;
  console.log(securityLevel);
  console.log(to.name);
  if (securityLevel === 0) {
    next();
  }
  if (securityLevel === 1 && localObj.userType === 'Customer') {
    next();
  }
  if (securityLevel === 2 && localObj.userType === 'Instructor') {
    next();
  }
  if (securityLevel === 3 && localObj.userType === 'Owner') {
    next();
  }
  if (to.name === "classtypes" && localObj.userType === 'Owner') {
    next();
  }
  if (to.name === 'dashboard' && localObj !== null) {
    next();
  }
  if (to.name === 'profile' && localObj !== null) {
    next();
  }
  if (securityLevel > 0 && localObj == null
    || securityLevel > 1 && localObj.userType === 'Customer'
    || securityLevel > 2 && localObj.userType === 'Instructor' ) {
    next('/');
  }
});

export default router;
