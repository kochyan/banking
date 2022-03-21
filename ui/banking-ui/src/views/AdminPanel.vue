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
          <LogTable />
        </div>
      </v-tab-item>
    </v-tabs-items>
  </v-container>
</template>

<script>


import LogTable from "@/components/adminpanel/LogTable";
export default {
  components: {LogTable},
  data() {
    return {
      tabs: ["Логи"],
      tab: null,
    }
  }
}

</script>