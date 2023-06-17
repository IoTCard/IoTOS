<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import * as echarts from 'echarts';
require('echarts/theme/macarons')
import resize from '../../dashboard/mixins/resize'

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    },
    option:{type: Object,default: null },//

  },
  watch:{
    option: {
      deep: true,
      handler(val) {
        this.setOptions(val)
      }
    },
    pieChartExecuteBool: {
      deep: true,  // 深度监听
      handler(newVal,oldVal) {
        if(newVal==true){
          this.initChart();
        }
      }
    }
  },
  data() {
    return {
      chart: null
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      this.$emit("setObjOne", "setPieChartExecuteBool", false);//已 再次加载

      this.chart = echarts.init(this.$el, 'macarons');
      this.setOptions(this.option);
    },

    setOptions(option){
      this.chart.setOption(option);
   },


  }
}
</script>
