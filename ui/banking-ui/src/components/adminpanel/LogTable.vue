<template>
  <v-container>
    <v-data-table
        :headers="headers"
        :items="items"
        :items-per-page="15"
        :footer-props='{"itemsPerPageText": "Записей на странице"}'
        :no-data-text="'Данные не обнаружены'"
        :options="tableOptions"
        @update:options="onOptionsUpdate"
    >
    </v-data-table>
    <AlertDialog
        v-bind:message="alertParams.message"
        v-bind:statusCode="alertParams.statusCode"
        @onclearparams="clearAlertParams"
    />
  </v-container>
</template>

<script>
import VueCookie from "vue-cookie";
import AlertDialog from "@/components/AlertDialog";

export default {
  components: {AlertDialog},
  data() {
    return {
      privileges: [],
      headers: [
        {text: 'Дата', value: 'created'},
        {text: 'Автор', value: 'author'},
        {text: 'Текст', value: 'message'},
        {text: 'Уровень', value: 'level'},
      ],
      items: [
        {
          'created': '',
          'author': '',
          'message': '',
          'level': ''
        }
      ],
      tableOptions: {
        page: 1,
        itemsPerPage: 15,
      },

      canSee: false,
      url: {
        log: '/log'
      },
      alertParams: {
        statusCode: 0,
        message: ''
      }
    }
  },

  created() {
    this.initData()
  },

  methods: {
    getLogs() {
      this.axios.get(
          process.env.VUE_APP_ROOT_API + this.url.log,
      ).then(response => {
        if (response.data) {
          this.items = this.formatData(response.data)
        }
      }).catch((error) => {
        this.alertParams.statusCode = error.response.status
        this.alertParams.message = ''
      });
    },
    initData() {
      this.privileges = VueCookie.get('privileges')
      this.checkPrivileges();
      if (this.canSee) {
        this.getLogs();
      } else {
        console.log('you dont have permissions to see logs')
      }
    },
    onOptionsUpdate(option) {
      this.tableOptions.page = option.page;
      this.tableOptions.itemsPerPage = option.itemsPerPage;
    },
    formatData(data) {
      let formatted = []

      for (let i = 0; i < data.length; i++) {
        data[i].created = this.convertDate(data[i].created)
        formatted.push(data[i])
      }

      return formatted
    },
    convertDate(rowDate) {
      return new Date(rowDate).toLocaleString()
    },
    checkPrivileges() {
      this.canSee = this.privileges.includes('CAN_USE_ADMIN_PANEL');
    },
    clearAlertParams() {
      this.alertParams.statusCode = 0
      this.alertParams.message = ''
    },
  },
}

</script>