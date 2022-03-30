<template>
  <v-container>
    <v-tabs
        v-model="tab"
        fixed-tabs
    >
      <v-tabs-slider color="blue"></v-tabs-slider>
      <v-tab
          v-for="item in tabs"
          :key="item"
      >
        {{ item }}
      </v-tab>
    </v-tabs>
    <v-tabs-items v-model="tab">
      <v-tab-item>
        <div>
          <PaymentTable />
        </div>
      </v-tab-item>
      <v-tab-item>
        <div>
          <h5>Add</h5>
        </div>
      </v-tab-item>
    </v-tabs-items>
  </v-container>
</template>
<script>
import PaymentTable from "@/components/payment/PaymentTable";

export default {
  components: {PaymentTable},

  data() {
    return {
      tabs: ["Просмотр", "Добавить"],
      tab: null,
    }
  },
}
</script>
