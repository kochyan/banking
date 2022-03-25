<template>
  <v-container>
    <v-data-table :headers="individualEntityHeaders"
                  :items="individualEntityItems"
                  :no-data-text="'Данные не найдены'"
                  :hide-default-footer="true"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>
            Физические лица
          </v-toolbar-title>
        </v-toolbar>
      </template>
      <template v-slot:footer>
        <v-dialog v-model="dialogEdit">
          <v-card>
            <v-card-title class="justify-center">
              Редактирование физического лица
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-container class="my-5">
                  <v-row class="justify-center">
                    <v-col cols="4">
                      <v-text-field
                          clearable
                          dense
                          label="Имя"
                          outlined
                          v-model="editIndividualEntity.firstname"
                          :rules="individualEntityFirstnameRules"
                      >
                      </v-text-field>
                    </v-col>
                    <v-col cols="4">
                      <v-text-field
                          clearable
                          dense
                          label="Фамилия"
                          outlined
                          v-model="editIndividualEntity.lastname"
                          :rules="individualEntityLastnameRules"
                      >
                      </v-text-field>
                    </v-col>
                    <v-col cols="4">
                      <v-text-field
                          clearable
                          dense
                          label="Отчество"
                          outlined
                          v-model="editIndividualEntity.patronymic"
                          :rules="individualEntityPatronymicRules"
                      >
                      </v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
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
              Подтвердите удаление физического лица
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row justify="center">
                  <v-col class="font-weight-bold col-2">
                    ФИО:
                  </v-col>
                  <v-col class="pl-10 col-2">
                    {{ deleteIndividualEntity.firstname }}
                  </v-col>
                  <v-col class="pl-10 col-2">
                    {{ deleteIndividualEntity.lastname }}
                  </v-col>
                  <v-col class="pl-10 col-2">
                    {{ deleteIndividualEntity.patronymic }}
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
      url: {
        individualEntity: '/individual-entity'
      },

      individualEntityHeaders: [
        {text: "Имя", value: "firstname"},
        {text: "Фамилия", value: "lastname"},
        {text: "Отчество", value: "patronymic"},
        {text: "Действия", value: "actions", sortable: false},
      ],
      individualEntityItems: [],

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

      emptyIndividualEntity: {
        id: null,
        firstname: null,
        lastname: null,
        patronymic: null,
      },
      editIndividualEntity: {
        id: null,
        firstname: null,
        lastname: null,
        patronymic: null,
      },
      deleteIndividualEntity: {
        id: null,
        firstname: null,
        lastname: null,
        patronymic: null,
      },

      alertParams: {
        statusCode: 0,
        message: ""
      },

      individualEntityFirstnameRules: [
        value => !!value || 'Обязательное поле',
        value => (value && value.trim().length >= 3) || 'Минимум 3 символа',
      ],
      individualEntityLastnameRules: [
        value => !!value || 'Обязательное поле',
        value => (value && value.trim().length >= 3) || 'Минимум 3 символа',
      ],
      individualEntityPatronymicRules: [
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
        this.getIndividualEntities()
      }
    },

    getIndividualEntities() {
      this.axios.get(
          process.env.VUE_APP_ROOT_API + this.url.individualEntity,
      ).then(response => {
        console.log(response.data)
        this.individualEntityItems = response.data
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
      this.editIndividualEntity = Object.assign({}, item)
      this.editIdx = this.individualEntityItems.indexOf(item)
      this.dialogEdit = true
    },

    commitChanges() {
      this.axios.put(
          process.env.VUE_APP_ROOT_API + this.url.individualEntity,
          this.editIndividualEntity,
          {
            headers: {
              'ContentType': 'application/json'
            }
          }
      ).then((response) => {
            this.individualEntityItems.splice(this.editIdx, 1, response.data)
            this.closeEdit()
          }
      ).catch(error => {
        this.alertParams.statusCode = error.response.status
        this.alertParams.message = this.getAlertMessage(error.response.status)
      })
    },
    confirmEdit() {
      if (this.isEditFormValid()) {
        this.commitChanges()
      } else console.log('edit form is not valid')
    },
    isEditFormValid() {
      return this.isFirstnameValid() && this.isLastnameValid() && this.isPatronymicValid()
    },
    isFirstnameValid() {
      return this.editIndividualEntity.firstname.trim().length >= 3;
    },
    isLastnameValid() {
      return this.editIndividualEntity.lastname.trim().length >= 3;
    },
    isPatronymicValid() {
      return this.editIndividualEntity.patronymic.trim().length >= 3;
    },
    closeEdit() {
      this.editIndividualEntity = Object.assign({}, this.emptyIndividualEntity)
      this.editIdx = null
      this.dialogEdit = false
    },

    openDeleteDialog(item) {
      this.deleteIndividualEntity = Object.assign({}, item)
      this.deleteIdx = this.individualEntityItems.indexOf(item)
      this.dialogDelete = true
    },

    commitDelete() {
      this.axios.delete(
          process.env.VUE_APP_ROOT_API + this.url.individualEntity + '/' + this.deleteIndividualEntity.id,
      ).then(() => {
        this.individualEntityItems.splice(this.deleteIdx, 1)
        this.closeDelete()
      }).catch(error => {
        this.alertParams.statusCode = error.response.status
        this.alertParams.message = this.getAlertMessage(error.response.status)
      })
    },

    closeDelete() {
      this.deleteIndividualEntity = Object.assign({}, this.emptyIndividualEntity)
      this.deleteIdx = null
      this.dialogDelete = false
    },

    clearAlertParams() {
      this.alertParams.message = ''
      this.alertParams.statusCode = 0
    },
  }
}
</script>