import Vue from 'vue'
import Vuex from 'vuex'
import VueCookie from 'vue-cookie'

Vue.use(Vuex)
Vue.use(VueCookie)
export default new Vuex.Store({
    state: {
        username: null,
        roles: [],
        privileges: [],
        routes: {
            login: '/login',
            mainPage: '/',
            adminPanel: '/admin',
            payment: '/payment'
        },
        cookieOptions: {
            expires: 'Session'
        }
    },
    getters: {
        USERNAME: state => state.username,
        ROLES: state => state.roles,
        PRIVILEGES: state => state.privileges,
        ROUTES: state => state.routes,
        ERROR_MESSAGES: state => state.errorMessages,

        CHECK_PRIVILEGE: state => privilege => {
            if (state.privileges) {
                return state.privileges.includes(privilege)
            }
            return false
        },
        CHECK_ROLE: state => role => {
            if (state.roles) {
                return state.roles.includes(role)
            }
            return false
        },
    },
    mutations: {
        setUsername: (state, username) => {
            Vue.set(state, 'username', username)
            VueCookie.set('username', username, state.cookieOptions)
        },
        clearUsername: state => state.username = null,

        setRoles: (state, roles) => {
            Vue.set(state, 'roles', roles)
            VueCookie.set('roles', roles, state.cookieOptions)
        },
        clearRoles: state => state.roles = null,

        setPrivileges: (state, privileges) => {
            Vue.set(state, 'privileges', privileges)
            VueCookie.set('privileges', privileges, state.cookieOptions)
        },
        clearPrivileges: state => state.privileges = null,
    },

    actions: {
        async setUsernameAsync(context, payload) {
            context.commit('setUsername', payload.username)
        },
        async setRolesAsync(context, payload) {
            context.commit('setRoles', payload.roles)
        },
        async setPrivilegesAsync(context, payload) {
            context.commit('setPrivileges', payload.privileges)
        },
    },
    modules: {}
})
