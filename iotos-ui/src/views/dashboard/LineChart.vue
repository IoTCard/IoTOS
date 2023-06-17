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
      default: '350px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      required: true
    },

    p_xAxis:{type: Array,default: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'] },//X 轴列
    p_colorArr:{type: Array,default: null },//线条 颜色数组
    p_showLable:{type: Object,default: null },//数据 key 对应显示名称
    p_config:{type: Object,default: null },//样式 配置
    lineChartExecuteBool: {
      type: Boolean,
      default: false
    },//是否再次加载
    setObjOne: Function,//父组件赋值 函数
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val)
      }
    },
    lineChartExecuteBool: {
      deep: true,  // 深度监听
      handler(newVal,oldVal) {
        //console.log("lineChartExecuteBool "+newVal)
        if(newVal==true){
          //console.log("lineChartExecuteBool 再次加载")
          this.initChart();
        }
      }
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
      this.$emit("setObjOne", "setLineChartExecuteBool", false);//已 再次加载

      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions(this.chartData)
    },
    setOptions(chartData) {

      // console.log(this.p_xAxis);
      // console.log(this.p_colorArr);
      // console.log(this.p_showLable);
      // console.log(this.p_config);
      // console.log(this.chartData)

      let series_data = [];
      let legend_data = [];

      let i = 0 ;
      let p_config = this.p_config;
      let smooth = false;//是否平滑过渡
      let areaStyle_show = "false";//是否 显示阴影部分

      if(p_config!=null){
        smooth = tools.isNull(p_config.smooth)?p_config.smooth:smooth;
        areaStyle_show = tools.isNull(p_config.areaStyle_show)?p_config.areaStyle_show:areaStyle_show;
      }


      //赋值数据
      for (let key in chartData) {
        //console.log(key);
        //console.log(chartData[key]);
        let color = this.p_colorArr[i];
        color = tools.isNull(color)?color:"#3c97fd";

        let lable = this.p_showLable[key];
        let Obj_map = {
          name: lable, itemStyle: {
            normal: {
              color: color,
              lineStyle: {
                color: color,
                width: 2
              }
            }
          },
          label: {
            show: true,
            position: 'top'
          },
          smooth: smooth,
          type: 'line',
          data: chartData[key],
          animationDuration: 600,//动画执行时间
          animationEasing: 'cubicInOut'
        };
        if(areaStyle_show=="true"){
          Obj_map.areaStyle = {};
        }

        i+=1;
        legend_data.push(lable);
        series_data.push(Obj_map);
      }

      this.chart.setOption({
        xAxis: {
          data: this.p_xAxis,
          type: 'category',
          boundaryGap: false,
          axisTick: {
            show: false
          }
        },
        grid: {
          left: 10,
          right: 10,
          bottom: 20,
          top: 30,
          containLabel: true
        },
        tooltip: this.p_tooltip,
        yAxis: {
          axisTick: {
            show: false
          }
        },
        legend: {
          data: legend_data
        },
        series: series_data
      })
    }
  }
}
</script>
