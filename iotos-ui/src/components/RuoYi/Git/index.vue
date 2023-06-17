<template>
  <div>
    <svg-icon icon-class="github" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'IotOSGit',
  data() {
    return {
      url: 'https://github.com/IoTCard/IoTOS'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
