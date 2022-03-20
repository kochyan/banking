<template>
  <v-container class="fill-height" fluid>
    <v-card class="mx-auto" width="600">
      <v-container>
        <v-text-field
            v-model="loginRequestBodyDto.username"
            label="Логин"
            aria-required="true"
            :rules="usernameRules"
        >
        </v-text-field>
      </v-container>
      <v-container>
        <v-text-field
            v-model="loginRequestBodyDto.password"
            label="Пароль"
            aria-required="true"
            type="password"
            :rules="passwordRules"
        >
        </v-text-field>
      </v-container>
      <v-container>
        <v-row
            class="justify-center"
        >
          <v-btn
              @click="login"
              class="primary ma-3"
              width="150px"
          >
            Войти
          </v-btn>
        </v-row>
      </v-container>
    </v-card>
    <AlertDialog
        v-bind:message="alertParams.message"
        v-bind:statusCode="alertParams.statusCode"
        @onclearparams="clearAlertParams"
    />
  </v-container>
</template>

<script>
// @ is an alias to /src

import AlertDialog from "@/components/AlertDialog";

export default {
  components: {AlertDialog},
  metaInfo: {
    title: 'Авторизация'
  },

  data() {
    return {
      loginRequestBodyDto: {
        username: '',
        password: '',
      },
      url: {
        login: "/auth/login"
      },

      usernameRules: [
        value => !!value || 'Обязательное поле',
        value => (value && value.trim().length >= 3) || 'Минимум 3 символа',
      ],
      passwordRules: [
        value => !!value || 'Обязательное поле',
        value => (value && value.trim().length >= 3) || 'Минимум 3 символа',
      ],

      alertParams: {
        statusCode: 0,
        message: ""
      }
    }
  },

  methods: {
    login() {
      if (this.isFormValid()) {
        this.submitForm()
      } else {
        console.log('form data is not valid')
      }
    },
    submitForm() {
      this.axios.post(
          process.env.VUE_APP_ROOT_API + this.url.login,
          this.loginRequestBodyDto,
          {
            headers: {
              'ContentType': 'application/json'
            }
          }
      ).then((response) => {
            let data = response.data

            if (data) {
              this.updateStoreData(data)
              this.$router.push('/')
            }
          }
      ).catch(error => {
        this.alertParams.statusCode = error.response.status
        this.alertParams.message = this.getAlertMessage(error.response.status)
      })
    },
    isFormValid() {
      return this.isUsernameValid() && this.isPasswordValid()
    },
    isUsernameValid() {
      return this.loginRequestBodyDto.username && this.loginRequestBodyDto.username.trim().length >= 3
    },
    isPasswordValid() {
      return this.loginRequestBodyDto.password && this.loginRequestBodyDto.password.trim().length >= 3
    },
    updateStoreData(data) {
      this.$store.dispatch('setUsernameAsync', data)
      this.$store.dispatch('setRolesAsync', data)
      this.$store.dispatch('setPrivilegesAsync', data)
    },
    clearAlertParams() {
      this.alertParams.message = ''
      this.alertParams.statusCode = 0
    },
    getAlertMessage(errorCode) {
      if (errorCode === 401) {
        return 'Неверный логин или пароль'
      }

      return ''
    },
  }
}

</script>