<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import tools from "@/utils/iotos/tools";
import * as echarts from 'echarts';
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'

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
    p_legend:{type: Object,default: null },//类别
    p_series:{type: Object,default: null },//
    p_color:{type: Array,default: [] },//
    pieChartExecuteBool: {
      type: Boolean,
      default: false
    },//是否再次加载
    setObjOne: Function,//父组件赋值 函数

  },
  watch:{
    p_series: {
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
      // console.log(this.p_legend);
      // console.log(this.p_series);
      // console.log(this.p_color);

      this.chart = echarts.init(this.$el, 'macarons');
      this.setOptions(this.p_series);
    },

    setOptions(p_series){
      //初始化属性
      let p_legend = this.p_legend;
      let p_color = this.p_color;
      let left = 'center';
      let itemStyle = {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      };
      let labelLine = {
        show: false
      };
      let data = [
        { value: 320, name: 'Industries' },
        { value: 240, name: 'Technology' },
        { value: 149, name: 'Forex' },
        { value: 100, name: 'Gold' },
        { value: 59, name: 'Forecasts' }
      ];
      let seriesMap =  {
        name: 'WEEKLY WRITE ARTICLES',
        type: 'pie',
        radius: [15, 95],
        center: ['50%', '38%'],
        data: [
          { value: 320, name: 'Industries' },
          { value: 240, name: 'Technology' },
          { value: 149, name: 'Forex' },
          { value: 100, name: 'Gold' },
          { value: 59, name: 'Forecasts' }
        ],
        animationEasing: 'cubicInOut',
        animationDuration: 600,
        avoidLabelOverlap: false,
        emphasis: {
          label: {
            show: true,
            fontSize: '20',
            fontWeight: 'bold'
          }
        },
      };

      let bottom = '10';
      let color = [];
      if(p_legend!=null){
        left = tools.isNull(p_legend.left)?p_legend.left:left;
        bottom = tools.isNull(p_legend.bottom)?p_legend.left:bottom;
      }
      color = p_color!=null && p_color.length>0?p_color:color;
      if(p_series!=null){
        seriesMap.name = tools.isNull(p_series.name)?p_series.name:seriesMap.name;
        seriesMap.type = tools.isNull(p_series.type)?p_series.type:seriesMap.type;

        seriesMap.radius = p_series.radius!=null && p_series.radius.length>0?p_series.radius:seriesMap.radius;
        seriesMap.center = p_series.center!=null && p_series.center.length>0?p_series.center:seriesMap.center;
        seriesMap.data = p_series.data!=null && p_series.data.length>0?p_series.data:seriesMap.data;
        seriesMap.animationEasing = tools.isNull(p_series.animationEasing)?p_series.animationEasing:seriesMap.animationEasing;
        seriesMap.animationDuration = tools.isNull(p_series.animationDuration)?p_series.animationDuration:seriesMap.animationDuration;
        seriesMap.avoidLabelOverlap = tools.isNull(p_series.avoidLabelOverlap)?p_series.avoidLabelOverlap:seriesMap.avoidLabelOverlap;
        seriesMap.avoidLabelOverlap = tools.isNull(p_series.avoidLabelOverlap)?p_series.avoidLabelOverlap:seriesMap.avoidLabelOverlap;
        if(tools.isNull(p_series.roseType)){
          seriesMap.roseType = p_series.roseType;
        }
        if(tools.isNull(p_series.itemStyle)){
          seriesMap.itemStyle = p_series.itemStyle;
        }
        if(tools.isNull(p_series.labelLine)){
          seriesMap.labelLine = p_series.labelLine;
        }
      }
      let legend_data = [];
      if(seriesMap.data!=null){//放入类别
        for (let i = 0; i < seriesMap.data.length; i++) {
          legend_data.push(seriesMap.data[i].name);
        }
      }
      //console.log(legend_data)

      let setConfig = {
        color: color,
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          left: left,//是否 局中类型
          bottom: bottom,//距离底部高度
          data: legend_data //类别
        },
        series: [//鼠标事件 触发
          seriesMap
        ]
      };


      this.chart.setOption(setConfig);
   },


  }
}
</script>
