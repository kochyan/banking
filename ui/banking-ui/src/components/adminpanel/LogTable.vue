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
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      headers: [
        {text: 'Автор', value: 'author'},
        {text: 'Событие', value: 'message'},
        {text: 'Дата', value: 'created'},
      ],
      items: [
        {
          'author': '',
          'message': '',
          'created': '',
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
        this.items = response.data
      }).catch((error) => {
        this.alertParams.statusCode = error.response.status
        this.alertParams.message = ''
      });
    },
    initData() {
      this.checkPrivileges();
      if (this.canSee) {
        this.getLogs();
      }
    },
    onOptionsUpdate(option) {
      this.tableOptions.page = option.page;
      this.tableOptions.itemsPerPage = option.itemsPerPage;
    },
    checkPrivileges() {
      this.canSee = this.$store.getters.PRIVILEGES.includes('CAN_USE_ADMIN_PANEL');
    }
  },
}

</script>