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
          <LegalEntityTable />
        </div>
      </v-tab-item>
    </v-tabs-items>
  </v-container>
</template>
<script>
import LegalEntityTable from "@/components/main/LegalEntityTable";

export default {
  components: {LegalEntityTable},

  data() {
    return {
      tabs: ["Юридические лица", "Физические лица"],
      tab: null,
    }
  },
}
</script>