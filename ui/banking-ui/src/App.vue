<template>
  <v-app id="app">
    <div class="mx-0 d-flex flex">
      <router-view name="sidebar"/>
      <router-view/>
    </div>
  </v-app>

</template>

<script>

export default {
  name: 'App',

  data: () => ({
    //
  }),
};
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;

}
</style>