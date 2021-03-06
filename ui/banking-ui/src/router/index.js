import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login'
import SideBar from "@/components/SideBar";
import MainPage from "@/views/MainPage";
import AdminPanel from "@/views/AdminPanel";
import Payment from "@/views/Payment"

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
    {
        path: '/admin',
        name: 'AdminPanel',
        components: {
            default: AdminPanel,
            sidebar: SideBar
        }
    },

    {
        path: '/payment',
        name: 'Payment',
        components: {
            default: Payment,
            sidebar: SideBar
        }
    },
]

const router = new VueRouter({
    routes
})

export default router
