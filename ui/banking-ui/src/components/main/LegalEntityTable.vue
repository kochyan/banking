<template>
  <v-container>
    <v-data-table :headers="legalEntityHeaders"
                  :items="legalEntityItems"
                  :no-data-text="'Данные не найдены'"
                  :hide-default-footer="true"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>
            Юридические лица
          </v-toolbar-title>
        </v-toolbar>
      </template>
      <template v-slot:footer>
        <v-dialog v-model="dialogEdit">
          <v-card>
            <v-card-title class="justify-center">
              Редактирование юридического лица
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-tabs
                    v-model="editTab"
                    fixed-tabs
                >
                  <v-tabs-slider color="blue"></v-tabs-slider>
                  <v-tab
                      v-for="item in editTabs"
                      :key="item"
                  >
                    {{ item }}
                  </v-tab>
                </v-tabs>
                <v-tabs-items v-model="editTab">
                  <v-tab-item>
                    <v-container class="my-5">
                      <v-row class="justify-center">
                        <v-col cols="6">
                          <v-text-field
                              clearable
                              dense
                              label="Название"
                              outlined
                              v-model="editLegalEntity.name"
                              :rules="legalEntityNameRules"
                          >
                          </v-text-field>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-tab-item>
                  <v-tab-item>
                    <v-container class="my-5">
                      <v-data-table :headers=" checkingAccountsHeaders"
                                    :items="checkingAccountsItems"
                                    :no-data-text="'Данные не найдены'"
                                    :hide-default-footer="true"
                      >
                        <template v-slot:item.actions="{ item }">
                          <v-icon
                              @click="hideCheckingAccount(item)"
                              v-if="canEdit"
                          >
                            mdi-delete
                          </v-icon>
                        </template>
                      </v-data-table>
                    </v-container>
                  </v-tab-item>
                </v-tabs-items>
              </v-container>
            </v-card-text>
            <v-card-actions class="justify-center">
              <v-btn
                  @click="confirmEdit"
                  color="primary"
                  text
              >
                Oк
              </v-btn>
              <v-btn
                  @click="closeEdit"
                  color="primary"
                  text
              >
                Отмена
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete">
          <v-card min-height="200px" min-width="300px" class="pa-7">
            <v-card-title class="justify-center">
              Подтвердите удаление юридического лица
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row justify="start">
                  <v-col class="font-weight-bold col-auto">
                    Название:
                  </v-col>
                  <v-col class="pl-10 col-auto">
                    {{ deleteLegalEntity.name }}
                  </v-col>
                </v-row>
                <v-row justify="start">
                  <v-col class="font-weight-bold col-auto">
                    Расчетные счета:
                  </v-col>
                  <v-col class="pl-10 col-auto">
                    {{ deleteLegalEntity.checkingAccounts.slice(0, 2).map(e => e.value).join(', ') }}
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions class="justify-center">
              <v-btn
                  @click="commitDelete"
                  color="primary"
                  text
              >
                Oк
              </v-btn>
              <v-btn
                  @click="closeDelete"
                  color="primary"
                  text
              >
                Отмена
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon
            small
            class="mr-2"
            @click="openEditDialog(item)"
            v-if="canEdit"
        >
          mdi-pencil
        </v-icon>
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
      editTab: null,
      editTabs: ['Название', 'Расчетные счета'],

      url: {
        legalEntity: '/legal-entity'
      },

      legalEntityHeaders: [
        {text: "Название", value: "name"},
        {text: "Действия", value: "actions", sortable: false},
      ],
      legalEntityItems: [],

      checkingAccountsHeaders: [
        {text: "Значение", value: "value"},
        {text: "Значение", value: "value"},
        {text: "Действия", value: "actions", sortable: false},
      ],
      checkingAccountsItems: [],

      privileges: [],

      canSee: false,
      canCreate: false,
      canEdit: false,
      canDelete: false,

      dialogAdd: false,
      dialogEdit: false,
      dialogDelete: false,

      editIdx: null,
      deleteIdx: null,

      emptyLegalEntity: {
        id: null,
        name: null,
        checkingAccounts: [{
          id: null,
          value: null,
          paymentPurposes: [{
            id: null,
            name: null
          }]
        }]
      },
      editLegalEntity: {
        id: null,
        name: null,
        checkingAccounts: [{
          id: null,
          value: null,
          paymentPurposes: [{
            id: null,
            name: null
          }]
        }]
      },
      deleteLegalEntity: {
        id: null,
        name: null,
        checkingAccounts: [{
          id: null,
          value: null,
          paymentPurposes: [{
            id: null,
            name: null
          }]
        }]
      },

      alertParams: {
        statusCode: 0,
        message: ""
      },

      legalEntityNameRules: [
        value => !!value || 'Обязательное поле',
        value => (value && value.trim().length >= 3) || 'Минимум 3 символа',
      ],
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
        this.getEagerLegalEntities()
      }
    },

    getEagerLegalEntities() {
      this.axios.get(
          process.env.VUE_APP_ROOT_API + this.url.legalEntity + '/eager',
      ).then(response => {
        this.legalEntityItems = response.data
      }).catch((error) => {
        this.alertParams.statusCode = error.response.status
        this.alertParams.message = ''
      });
    },

    checkPrivileges() {
      this.canSee = this.privileges.includes('CAN_SELECT')
      this.canCreate = this.privileges.includes('CAN_INSERT')
      this.canEdit = this.privileges.includes('CAN_UPDATE')
      this.canDelete = this.privileges.includes('CAN_DELETE')
    },

    openEditDialog(item) {
      this.editLegalEntity = Object.assign({}, item)
      this.checkingAccountsItems = Array.from(item.checkingAccounts)
      this.editIdx = this.legalEntityItems.indexOf(item)
      this.dialogEdit = true
    },

    commitChanges() {
      this.axios.put(
          process.env.VUE_APP_ROOT_API + this.url.legalEntity,
          this.editLegalEntity,
          {
            headers: {
              'ContentType': 'application/json'
            }
          }
      ).then((response) => {
            this.legalEntityItems.splice(this.editIdx, 1, response.data)
            this.closeEdit()
          }
      ).catch(error => {
        this.alertParams.statusCode = error.response.status
        this.alertParams.message = this.getAlertMessage(error.response.status)
      })
    },
    confirmEdit() {
      if (this.editLegalEntity.name.length >= 3) {
        this.editLegalEntity.checkingAccounts = Array.from(this.checkingAccountsItems)
        this.commitChanges()
      } else console.log('wrong name length')
    },
    closeEdit() {
      this.editLegalEntity = Object.assign({}, this.emptyLegalEntity)
      this.checkingAccountsItems = []
      this.editIdx = null
      this.dialogEdit = false
    },

    openDeleteDialog(item) {
      this.deleteLegalEntity = Object.assign({}, item)
      this.deleteIdx = this.legalEntityItems.indexOf(item)
      this.dialogDelete = true
    },

    commitDelete() {
      this.axios.delete(
          process.env.VUE_APP_ROOT_API + this.url.legalEntity + '/' + this.deleteLegalEntity.id,
      ).then(() => {
        this.legalEntityItems.splice(this.deleteIdx, 1)
        this.closeDelete()
      }).catch(error => {
        this.alertParams.statusCode = error.response.status
        this.alertParams.message = this.getAlertMessage(error.response.status)
      })
    },

    closeDelete() {
      this.deleteLegalEntity = Object.assign({}, this.emptyLegalEntity)
      this.deleteIdx = null
      this.dialogDelete = false
    },

    clearAlertParams() {
      this.alertParams.message = ''
      this.alertParams.statusCode = 0
    },

    hideCheckingAccount(item) {
      const idx = this.checkingAccountsItems.indexOf(item)
      this.checkingAccountsItems.splice(idx, 1)
    },
  }
}
</script>