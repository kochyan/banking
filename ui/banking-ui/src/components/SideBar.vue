<template>
  <div>
    <v-navigation-drawer
        v-model="drawer"
        :color="color"
        :expand-on-hover="expandOnHover"
        :mini-variant="miniVariant"
        :right="right"
        :permanent="permanent"
        :src="bg"
        dark
    >
      <v-list
          dense
          nav
          class="py-0"
      >
        <v-list-item two-line :class="miniVariant && 'px-0'">
          <v-list-item-content>
            <v-list-item-title>{{ username }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-divider></v-divider>

        <v-list-item
            v-for="item in getAllowedSideBarRows()"
            :key="item.title"
            link
            :to="item.path"
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
import VueCookie from "vue-cookie";

export default {
  data() {
    return {
      drawer: true,
      right: false,
      permanent: true,
      expandOnHover: false,
      background: false,

      color: 'primary',
      colors: [
        'primary',
        'blue',
        'success',
        'red',
        'teal',
      ],

      username: null,
      roles: [],
      privileges: [],

      sideBarRows: [
        {title: 'Главная', icon: 'mdi-format-list-bulleted', path: this.$store.getters.ROUTES.mainPage},
        {title: 'Админка', icon: 'mdi-file-multiple', path: this.$store.getters.ROUTES.adminPanel},
      ],
    }
  },

  computed: {
    bg() {
      return this.background ? 'https://cdn.vuetifyjs.com/images/backgrounds/bg-2.jpg' : undefined
    },

    miniVariant() {
      if (this.$vuetify.breakpoint.xs || this.$vuetify.breakpoint.sm) {
        return true;
      }
      return false;
    },
  },

  created() {
    this.initData()

    if (!this.isAuthenticated()) {
      this.$router.push(this.$store.getters.ROUTES.login);
    }
  },

  methods: {
    initData() {
      this.username = VueCookie.get('username');
      this.roles = VueCookie.get('roles')
      this.privileges = VueCookie.get('privileges')
    },
    getAllowedSideBarRows() {
      if (this.privileges.includes('CAN_USE_ADMIN_PANEL') === false) {
        this.sideBarRows = this.sideBarRows.filter(row => row.title !== "Админка")
      }
      return this.sideBarRows
    },
    isAuthenticated() {
      if (this.username) {
        if (this.username.trim() !== '' && this.username !== 'undefined') {
          return true
        }
      }
      return false
    }
  }
}
</script>
