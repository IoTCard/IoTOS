<template>
  <div>
    <svg-icon icon-class="question" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'IoTOSDoc',
  data() {
    return {
      url: 'http://www.iotos.top'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
