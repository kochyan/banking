import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login'
import SideBar from "@/components/SideBar";
import MainPage from "@/views/MainPage";

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: Login,
        meta: {
            title: 'Login'
        }
    },

    {
        path: '/',
        name: 'MainPage',
        components: {
            default: MainPage,
            sidebar: SideBar
        }
    },
]

const router = new VueRouter({
    routes
})

export default router