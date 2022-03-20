<template>
  <v-container>
    <v-dialog width="500" v-model="alertDialog">
      <v-card class="mx-auto">
        <v-card-title>
          <v-alert type="warning" width="100%">
            {{ errorMessage }}
          </v-alert>
        </v-card-title>
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="alertDialog = false">
            Ок
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>

export default {
  props: {
    'message': {
      type: String,
      required: false
    },
    'statusCode': Number,
  },

  data() {
    return {
      allErrors: {
        500: "Что-то пошло не так",
        403: "Недостаточно прав для доступа к ресурсу"
      },

      url: {
        login: "/login"
      },

      alertDialog: false,
      errorStatusCode: '',
      errorMessage: '',
    }
  },

  watch: {
    message: function () {
      this.errorMessage = this.message
    },

    statusCode: function () {
      this.errorStatusCode = this.statusCode;
    },

    errorStatusCode: function () {
      this.initDialog();
    },

    alertDialog: function () {
      if (this.alertDialog === false) {
        this.$emit('onclearparams');
      }
    }
  },

  methods: {
    initDialog() {
      if (this.statusCode === 0) {
        return
      }

      if (this.message === '') {
        if (this.errorStatusCode === 401) {
          this.clearStore()
          if (this.$route.path !== this.url.login) {
            this.$router.push(this.url.login)
          }
        } else if (this.errorStatusCode === 500) {
          this.errorMessage = this.allErrors["500"];
          this.alertDialog = true;
        } else if (this.errorStatusCode === 403) {
          this.errorMessage = this.allErrors["403"];
          this.alertDialog = true;
        }

        this.$emit('onclearparams')

      } else {
        this.errorMessage = this.message;
        this.alertDialog = true;
      }
    },
    clearStore() {
      this.$store.dispatch('setUsernameAsync', '')
      this.$store.dispatch('setRolesAsync', [])
      this.$store.dispatch('setPrivilegesAsync', [])
    }
  },
}
</script>
