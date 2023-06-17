<template>
  <div >
    <!--这里是标签页-->
    <div class="main" >

      <el-tabs v-model="activeName" type="border-card"  @tab-click="handleClick">

        <el-tab-pane :label="$t('cardInfoModule.flowRecord')" name="flowRecord">

          <el-row :gutter="10" style="height: 300px;">
            <el-col :xs="24" :sm="24" :md="6" :lg="6">
              <pie-chart style="margin-top: 15px;" :p_legend="p_legend" :p_series="p_series" :p_color="p_color"/>
            </el-col>
            <el-col :xs="24" :sm="24" :md="18" :lg="18">
              <div style="float: right;margin-top: -5px;margin-right: 50px;height: 40px;">
                <div style="float: right">
                  <el-button  @click="last30Days">{{ $t('cardInfoModule.last30Days') }}</el-button>

                  <el-date-picker
                    v-model="recordTime"
                    type="month"
                    value-format="yyyy-MM"
                    :picker-options="pickerOptions"
                    style="width: 160px;margin-left: 20px;"
                    @change="changeRecordTime"
                    align="right">
                  </el-date-picker>
                </div>
              </div>
              <line-chart style="margin-top: 15px;" :chart-data="recordData" :p_xAxis="p_xAxis" :p_colorArr="p_colorArr" :p_showLable="p_showLable" :p_config="p_config" />
            </el-col>
          </el-row>


        </el-tab-pane>

        <el-tab-pane :label="$t('cardInfoModule.cardSession')" name="cardSession">

          <el-divider content-position="left">{{ onlineTitle }}
            <el-button @click="getOnlineStatus" size="mini" type="primary" style="margin-left: 20px">{{ $t('common.refresh')  }}</el-button>
          </el-divider>

          <el-card shadow="always">

            <el-table
              :data="tableSession"
              :show-header="false"
              border>
              <el-table-column prop="label1" >
                <template slot-scope="scope" >
                  <b >{{ scope.row.label1 }}</b>
                </template>
              </el-table-column>
              <el-table-column prop="value1"/>
              <el-table-column prop="label2">
                <template slot-scope="scope">
                  <b>{{ scope.row.label2 }}</b>
                </template>
              </el-table-column>
              <el-table-column prop="value2"/>
              <el-table-column prop="label3">
                <template slot-scope="scope">
                  <b>{{ scope.row.label3 }}</b>
                </template>
              </el-table-column>
              <el-table-column prop="value3"/>
            </el-table>
          </el-card>

        </el-tab-pane>

        <el-tab-pane :label="$t('cardInfoModule.cardApiBusiness')" name="cardApiBusiness">
          <el-steps align-center :active="cardApiBusinessList.length" >
            <el-step :title="item.create_time" :description="'['+tools.getDkeyValue(tasksDetailsTypeOptions, item.type)+']'+$t('cardInfoModule.source')+'['+tools.getDkeyValue(apiSourceTypeOptions, item.source_type)+']'+' ['+tools.getDkeyValue(resultStatusOptions, item.state)+']'"
                     v-for="item in  cardApiBusinessList">
            </el-step>
          </el-steps>
        </el-tab-pane>


      </el-tabs>
    </div>

    <!-- 触发加载数据函数-->
    <span v-if="show_Details ==true && moduleEditexecute == false">
        {{ LoadEx() }}
    </span>



  </div>
</template>

<script>
import {
  synSession,
  getApiBusinessList,
} from "@/api/iotos/connect/card";
import tools from "@/utils/iotos/tools";
import LineChart from '../../../dashboard/LineChart'
import PieChart from '../../../dashboard/PieChart'
import {
  listUsedRecord
} from "@/api/iotos/connect/usedRecord";


export default {
  props: {
    setObj: Function,//父组件赋值 函数
    moduleEditexecute: Boolean,//加载是否 已执行
    show_Details: Boolean,//当前界面是否显示
    sel: Object,

  },
  components: {
    LineChart,
    PieChart
  },
  watch:{
    sel: {
      deep: true,  // 深度监听
      handler(newVal,oldVal) {
        //console.log(newVal,oldVal)
        if(tools.isNull(newVal.c_used)==true && tools.isNull(newVal.c_left)==true){
            let data = [
              { value: newVal.c_used, name: this.$t("common.used")+" "+newVal.c_used+" MB" },
              { value: newVal.c_left, name: this.$t("common.left")+" "+newVal.c_left+" MB" },
            ];
            this.p_series.data = data;
           //模拟调用一下 折线统计图修改
          /*this.lineChartExecuteBool = true;
          this.recordData.expectedData = tools.generateRandomArray(30, 1, 100, 2, 0);*/
          }
        if(tools.isNull(newVal.first_time_using)==true){

        }
      }
    }
  },
  name: "cardInfoModule",
  data() {
    return {
      activeName: "flowRecord",//默认选择


      onlineTitle: this.$t("cardInfoModule.cardSession")+' ('+this.$t("common.synchronization")+')',
      tableSession: [],//在线状态信息
      tools:tools,
      cardApiBusinessList: [],//业务变更 数组
      activeNames: [0],
      cardValidType: [ ], // 资费计划状态 字典

      cardApiBusiness_load:false,//是否加载  【变更记录】 whetherload
      flowRecord_load:false,//是否加载  【用量信息】
      cardSession_load:false,//是否加载  【会话信息】
      headquartersBool: false,//内部人员

      recordData:{
        expectedData: [],//tools.generateRandomArray(30, 1, 100, 2, 0),
      },
      p_xAxis:[],//['01-01', '01-02', '01-03', '01-04', '01-05', '01-06', '01-07', '01-08', '01-09', '01-10', '01-11', '01-12', '01-13', '01-14', '01-15', '01-16', '01-17', '01-18', '01-19', '01-20', '01-21', '01-22', '01-23', '01-24', '01-25', '01-26', '01-27', '01-28', '01-29', '01-30'],
      p_colorArr:['#3c97fd', '#67c23a'],//折线图 颜色
      p_showLable:{expectedData: this.$t("common.dayFlow")+"MB"},
      p_config: {"smooth": false, 'areaStyle_show': "true"},

      p_legend:{
        top: '10%',
        left: 'center'
      },
      p_series:{
        center : ['45%', '50%'],
        name: this.$t("common.flowUsed"),
        type: 'pie',
        radius: ['20%', '60%'],
        data: [
          { value: 0, name: this.$t("common.used") },
          { value: 0, name: this.$t("common.left") },
        ]
      },
      p_color:['#75b5fd','#8bc34a' ],



      recordTime:'',
      pickerOptions: {//限制 日期
        disabledDate(time) {
          return time.getTime() > Date.now() - 8.64e6;//如果不包括今天。就是return time.getTime() > Date.now() - 24*3600*1000;
        },
        shortcuts: [{
          text: this.$t('common.currentMonth'),
          onClick(picker) {
            picker.$emit('pick', tools.getMonthStringFromDateOffset(0));
          }
        }, {
          text: this.$t('common.lastMonth'),
          onClick(picker) {
            picker.$emit('pick', tools.getMonthStringFromDateOffset(-1));
          }
        }, {
          text: this.$t('common.aYearAgo'),
          onClick(picker) {
            picker.$emit('pick', tools.getMonthStringFromDateOffset(-12));
          }
        }]
      },
      onelinkSessionStatusOptions:[],
      onelinkRatTypeOptions:[],
      tasksDetailsTypeOptions:[],//API业务变更来源
      apiSourceTypeOptions:[],//执行任务详情类型
      resultStatusOptions:[],//通用结果状态

    }
  },
  created() {

    //加载 oneLink在线状态
    if (window['onelinkSessionStatusOptions'] != undefined && window['onelinkSessionStatusOptions'] != null && window['onelinkSessionStatusOptions'] != '') {
      this.onelinkSessionStatusOptions = window['onelinkSessionStatusOptions'];
    } else {
      this.getDicts("onelink_session_status").then(response => {
        window['onelinkSessionStatusOptions'] = response.data;
        this.onelinkSessionStatusOptions = window['onelinkSessionStatusOptions'];
      });
    }

    //加载 onelink_rat接入方式
    if (window['onelinkRatTypeOptions'] != undefined && window['onelinkRatTypeOptions'] != null && window['onelinkRatTypeOptions'] != '') {
      this.onelinkRatTypeOptions = window['onelinkRatTypeOptions'];
    } else {
      this.getDicts("onelink_rat_type").then(response => {
        window['onelinkRatTypeOptions'] = response.data;
        this.onelinkRatTypeOptions = window['onelinkRatTypeOptions'];
      });
    }



    //加载 onelink_rat接入方式
    if (window['onelinkRatTypeOptions'] != undefined && window['onelinkRatTypeOptions'] != null && window['onelinkRatTypeOptions'] != '') {
      this.onelinkRatTypeOptions = window['onelinkRatTypeOptions'];
    } else {
      this.getDicts("onelink_rat_type").then(response => {
        window['onelinkRatTypeOptions'] = response.data;
        this.onelinkRatTypeOptions = window['onelinkRatTypeOptions'];
      });
    }

    //API业务变更来源
    if (window['apiSourceTypeOptions'] != undefined && window['apiSourceTypeOptions'] != null && window['apiSourceTypeOptions'] != '') {
      this.apiSourceTypeOptions = window['apiSourceTypeOptions'];
    } else {
      this.getDicts("api_source_type").then(response => {
        window['apiSourceTypeOptions'] = response.data;
        this.apiSourceTypeOptions = window['apiSourceTypeOptions'];
      });
    }

    //执行任务详情类型
    if (window['tasksDetailsTypeOptions'] != undefined && window['tasksDetailsTypeOptions'] != null && window['tasksDetailsTypeOptions'] != '') {
      this.tasksDetailsTypeOptions = window['tasksDetailsTypeOptions'];
    } else {
      this.getDicts("tasks_details_type").then(response => {
        window['tasksDetailsTypeOptions'] = response.data;
        this.tasksDetailsTypeOptions = window['tasksDetailsTypeOptions'];
      });
    }

    //通用结果状态
    if (window['resultStatusOptions'] != undefined && window['resultStatusOptions'] != null && window['resultStatusOptions'] != '') {
      this.resultStatusOptions = window['resultStatusOptions'];
    } else {
      this.getDicts("result_status").then(response => {
        window['resultStatusOptions'] = response.data;
        this.resultStatusOptions = window['resultStatusOptions'];
      });
    }



  },

  methods: {






    LoadEx() {
      this.$emit("setObj", "setModuleEditexecute", true);//已加载
      this.cardApiBusinessList = [];//业务变更 数组
      this.tableSession = [];//在线状态信息
      this.activeName = "flowRecord";//默认选择 ‘流量使用’ 做展示
      //console.log(this.sel);
      this.flowRecord_load = false;
      this.cardSession_load = false;
      this.cardApiBusiness_load = false;
      this.loadUsedRecord(tools.gitDatayyyyMM());//默认当月

    },


    handleClick(tab, event) {
      switch (tab.name) {
        case 'cardApiBusiness'://API业务变更
          if(!this.cardApiBusiness_load ) {
            this.loadApiBusinessList();
          }
          break;
        case 'flowRecord'://用量记录
          if(!this.flowRecord_load ) {
            this.loadUsedRecord(tools.gitDatayyyyMM());//默认当月
          }
          break;
        case 'cardSession'://在线信息
          if(!this.cardSession_load ) {
            this.getOnlineStatus();
          }
          break;


      }

    },

    //查询用量记录
    loadUsedRecord(record_date){
      let str = record_date.split("-");
      let yyyyMM = str[0]+str[1];
      let map = {};
      map.iccid = this.sel.iccid;
      map.record_date = record_date;
      map.yyyyMM = yyyyMM;
      map.pageNum = 1;
      map.pageSize = 31;
      //console.log(map)
      let pwdStr = tools.encrypt(JSON.stringify(map));
      listUsedRecord(pwdStr).then(res => {
        let jsonObj = JSON.parse(tools.Decrypt(res));
        //console.log(jsonObj)
        if (jsonObj.code == 200) {
            let dataList = jsonObj.data.data;
            let total = jsonObj.data.Pu.rowCount;
            if(total>0){
              //重置数据
              this.recordData.expectedData = [];
              this.p_xAxis = [];
              let expectedData = [];
              let p_xAxis = [];
              for (let i = 0; i < dataList.length; i++) {
                let data = dataList[i];
                p_xAxis.push(data.MMdd);
                expectedData.push(data.reveal_day_used);
              }
              this.recordData.expectedData = expectedData;
              this.p_xAxis = p_xAxis;
              this.flowRecord_load = true;
            }else {
              this.$message.error(this.$t("common.noDataFound"));
            }
        } else {
          this.$message.error(jsonObj.msg);
        }
      })
    },





    //会话信息 更新
    getOnlineStatus() {
      this.tableSession = [];//重置数据
      let map = {};
      map.iccid = this.sel.iccid;
      let pwdStr = tools.encrypt(JSON.stringify(map));
      synSession(pwdStr).then(res => {
        let jsonObj = JSON.parse(tools.Decrypt(res));
        this.headquartersBool = jsonObj.deptId=='100'?true:false;
        let retuenList = jsonObj.data.retuenList;
        if (jsonObj.code == 200 && retuenList!=null && retuenList[0]!=null) {
          let obj = retuenList[0].rData;
          let map1 = {};
          let map2 = {};
          let template = jsonObj.data.template;
          this.cardSession_load=true;//是否加载  【会话信息】
          let createDate = '';
          if (template == "oneLink_ECV5" ) {
            map1.value1 = tools.getDkeyValue(this.onelinkSessionStatusOptions, obj.status);
            map1.value2 = tools.getDkeyValue(this.onelinkRatTypeOptions, obj.rat);
            map1.value3 = obj.apnId;

            map2.value1 = obj.ip;
            map2.value2 = obj.ipv6;
            map2.value3 = obj.ipv6Prefix;
            createDate = obj.createDate;
          }
          map1.label1 = this.$t("cardInfoModule.session.onlineStatus");
          map1.label2 = this.$t("cardInfoModule.session.accessMethod");
          map1.label3 = "APN";
          this.tableSession.push(map1);

          map2.label1 = this.$t("cardInfoModule.session.iPAddress");
          map2.label2 = "ipv6";
          map2.label3 = "ipv6Prefix";
          this.tableSession.push(map2);
          if (tools.isNull(createDate)) {
            this.onlineTitle = this.$t("cardInfoModule.cardSession")+' ('+this.$t("common.sessionCreationTime")+' : ' + createDate + ')';
          } else {
            this.onlineTitle = this.$t("cardInfoModule.cardSession")+' ('+this.$t("common.synchronization")+')';
          }
        } else {
          let map1 = {};
          map1.label1 = this.$t("common.returnedMessages");
          map1.label2 = "";
          map1.value2 = "";
          if (this.headquartersBool) {
            map1.label2 = this.$t("cardInfoModule.session.hint1");
            map1.value2 = this.$t("cardInfoModule.session.hint2");
          }else{
            jsonObj.msg = this.$t("cardInfoModule.session.error1");
          }
          map1.value1 = jsonObj.msg;
          this.tableSession.push(map1);
          this.onlineTitle = this.$t("cardInfoModule.cardSession")+' ('+this.$t("common.synced")+')';
        }

      })
    },



    //同步用量
    synFlow(iccid) {
      let _this = this;
      let map = {};
      map.iccid = iccid;
      let pwdStr = tools.encrypt(JSON.stringify(map));

    },


    //切换
    changeRecordTime(){
      if(tools.isNull(this.recordTime)){
        this.loadUsedRecord(this.recordTime);
      }
    },
    //近30 天 用量
    last30Days(){
      this.loadUsedRecord(tools.gitDatayyyyMM());//默认当月
    },

    //加载 API 业务变更记录
    loadApiBusinessList(){
      this.cardApiBusinessList = [];//业务变更 数组
      let map = {};
      map.iccid = this.sel.iccid;
      map.starRow = 0;
      map.pageSize = 4;
      //console.log(map)
      let pwdStr = tools.encrypt(JSON.stringify(map));
      getApiBusinessList(pwdStr).then(res => {
        let jsonObj = JSON.parse(tools.Decrypt(res));
        //console.log(jsonObj)
        if (jsonObj.code == 200) {
          let dataList = jsonObj.data;
          if(dataList!=null && dataList.length>0){
            let cardApiBusinessList = [];
            for (let i = dataList.length-1; i >=0 ; i--) {
              cardApiBusinessList.push(dataList[i]);
            }
            this.cardApiBusinessList = cardApiBusinessList;
            this.cardApiBusiness_load = true;
          }
        }
      })
    },


  },
}
</script>

