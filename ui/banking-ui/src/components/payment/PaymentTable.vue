<template>
  <v-container>
    <v-data-table :headers="paymentEntityHeaders"
                  :items="paymentEntityItems"
                  :no-data-text="'Данные не найдены'"
                  :hide-default-footer="true"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>
            Платежи
          </v-toolbar-title>
        </v-toolbar>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon
            small
            @click="openDeleteDialog(item)"
            v-if="canDelete"
        >
          mdi-delete
        </v-icon>
      </template>
    </v-data-table>
    <AlertDialog
        v-bind:message="alertParams.message"
        v-bind:statusCode="alertParams.statusCode"
        @onclearparams="clearAlertParams"
    />
  </v-container>
</template>

<script>
import AlertDialog from "@/components/AlertDialog";
import VueCookie from "vue-cookie";

export default {
  components: {AlertDialog},

  data() {
    return {
      url: {
        paymentEntity: '/payment'
      },

      paymentEntityHeaders: [
        {text: "Плательщик", value: "individual"},
        {text: "Сумма", value: "value"},
        {text: "Цель платежа", value: "purpose"},
        {text: "Получатель", value: "legal"},
        {text: "Расчетный счет", value: "checkingAccount"},
        {text: "Отделение банка", value: "bankBranch"},
        {text: "Дата", value: "data"},
        {text: "Действия", value: "actions", sortable: false}
      ],
      paymentEntityItems: [],

      emptyPaymentEntity: {},
      deletePaymentEntity: {},

      privileges: [],

      canSee: false,
      canDelete: false,

      deleteIdx: null,

      dialogDelete: false,

      alertParams: {
        statusCode: 0,
        message: ""
      },
    }
  },

  created() {
    this.initData()
  },

  methods: {
    initData() {
      this.privileges = VueCookie.get('privileges')
      this.checkPrivileges()

      if (this.canSee) {
        this.getPayments()
      }
    },

    getPayments() {
      this.axios.get(
          process.env.VUE_APP_ROOT_API + this.url.paymentEntity,
      ).then(response => {
        this.paymentEntityItems = response.data
      }).catch((error) => {
        this.alertParams.statusCode = error.response.status
        this.alertParams.message = ''
      });
    },

    checkPrivileges() {
      this.canSee = this.privileges.includes('CAN_SELECT')
      this.canDelete = this.privileges.includes('CAN_DELETE')
    },

    clearAlertParams() {
      this.alertParams.message = ''
      this.alertParams.statusCode = 0
    },


    openDeleteDialog(item) {
      this.deletePaymentEntity = Object.assign({}, item)
      this.deleteIdx = this.paymentEntityItems.indexOf(item)
      this.dialogDelete = true
    },

    commitDelete() {
      this.axios.delete(
          process.env.VUE_APP_ROOT_API + this.url.paymentEntity + '/' + this.deletePaymentEntity.id,
      ).then(() => {
        this.paymentEntityItems.splice(this.deleteIdx, 1)
        this.closeDelete()
      }).catch(error => {
        this.alertParams.statusCode = error.response.status
        this.alertParams.message = this.getAlertMessage(error.response.status)
      })
    },

    closeDelete() {
      this.deleteLegalEntity = Object.assign({}, this.emptyPaymentEntity)
      this.deleteIdx = null
      this.dialogDelete = false
    },
  }
}
</script>