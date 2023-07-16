<template>
  <div class="">


    <el-card >
      <div slot="header" >
        <span>{{ $t("index.basic.basicInfo") }}</span>
        <span style="float: right;margin-right: 10px;">
                {{ $t("index.basic.statisticalDeadline") }} {{ frontPage.last_upd_time }}
                <el-tooltip style="color:#3aa4ff;" placement="top">
                    <div slot="content">
                      {{ $t("index.basic.ps") }}
                    </div>
                    <i class="el-icon-question"></i>
                  </el-tooltip>
                <el-button type="primary" style="margin-left: 10px;" size="mini"  @click="findIndex">{{
                    $t("common.refresh")
                  }}</el-button>
        </span>
      </div>
      <div class="body">

        <el-row :gutter="10" class="panel-group">
          <myCol ref="card_count" :xsW="24" :smW="8" :mdW="6" :lgW="6" :duration="8" :title="$t('index.cardCount')"
                 :unit="$t('common.open')" :val="frontPage.card_count" icon="sim" toUrl="/connect/cardList/"
                 iconClass="card-panel-icon-wrapper icon-one"/>
          <myCol ref="proxyCardCount" :xsW="24" :smW="8" :mdW="6" :lgW="6" :duration="8"
                 :title="$t('index.proxyCardCount')" :unit="$t('common.open')" :val="frontPage.agent_card_count"
                 icon="proxyCardCount" iconClass="card-panel-icon-wrapper icon-tow"/>
          <myCol ref="directCardCount" :xsW="24" :smW="8" :mdW="6" :lgW="6" :duration="8"
                 :title="$t('index.directCardCount')" :unit="$t('common.open')" :val="frontPage.dept_card_count"
                 icon="sim1" iconClass="card-panel-icon-wrapper icon-tow"/>
          <myCol ref="deptCount" :xsW="24" :smW="8" :mdW="6" :lgW="6" :duration="8" :title="$t('index.deptCount')"
                 :unit="$t('common.indivual')" :val="frontPage.dept_count" icon="dept"
                 iconClass="card-panel-icon-wrapper icon-tow"/>
        </el-row>

        <el-row :gutter="10" class="panel-group" style="margin-top: 10px;">
          <myCol v-if="headquarters" ref="loginCount" :xsW="24" :smW="8" :mdW="6" :lgW="6" :duration="8" :title="$t('index.loginCount')"
                 :unit="$t('common.indivual')" :val="frontPage.login_ip_count" icon="ip"
                 iconClass="card-panel-icon-wrapper icon-one"/>
          <myCol ref="saveCount" :xsW="24" :smW="8" :mdW="6" :lgW="6" :duration="8" :title="$t('index.saveCount')"
                 :toUrl="'/connect/cardList/'+ tools.encryptSy({'dateType':'create_time','selTime':[monthFirstDay,frontPage.last_upd_time.split(' ')[0]]})"
                 :unit="$t('common.open')" :val="frontPage.card_add_count" icon="increase"
                 iconClass="card-panel-icon-wrapper icon-three"/>
          <myCol ref="activationCount" :xsW="24" :smW="8" :mdW="6" :lgW="6" :duration="8"
                 :title="$t('index.activationCount')" :unit="$t('common.open')" :val="frontPage.card_activation_count"
                 :toUrl="'/connect/cardList/'+ tools.encryptSy({'dateType':'activate_date','selTime':[monthFirstDay,frontPage.last_upd_time.split(' ')[0]]})"
                 icon="activation" iconClass="card-panel-icon-wrapper icon-three"/>
          <router-link v-hasPermi="['monitor:online:list']" v-if="headquarters"  :to="'/monitor/online/'">
            <myCol ref="onlineUser" :xsW="24" :smW="8" :mdW="6" :lgW="6" :duration="8" :title="$t('index.onlineUser')"
                   :unit="$t('common.indivual')" :val="onlineUserCount" icon="onlineUser"
                   iconClass="card-panel-icon-wrapper icon-tow"/>
            </router-link>
        </el-row>


      </div>
    </el-card>


    <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">

      <el-tab-pane :label="$t('index.cardNumberBulletinBoard')" name="basic">
        <el-tabs tab-position="right" v-model="activeNameBasic" @tab-click="handleClickBasic">
          <el-tab-pane :label="$t('index.BasicKanban')" name="usage_line">
            <el-row :gutter="10">
              <el-col :xs="24" :sm="24" :md="8" :lg="8"  >
                <el-card class="update-log">
                  <div slot="header" class="clearfix">
                    <span>{{ $t("index.lifeCycleTitle") }}</span>
                  </div>
                  <div class="body" >
                    <pie-chart  v-if="life_cycle_show" :p_legend="p_legend" :p_series="life_cycle_p_series" :p_color="life_cycle_p_color" height="230px"/>
                  </div>
                </el-card>
              </el-col>
              <el-col :xs="24" :sm="24" :md="16" :lg="16">
                <el-card >
                <div slot="header" >
                  <el-row>
                    <el-col :xs="24" :sm="24" :md="16" :lg="16">
                        <span>{{ usageTitle+" "+frontPage.record_date }}</span>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="8" :lg="8">
                        <span style="float: right;margin-right: 10px;width: 100%;">
                             <el-row :gutter="5">
                              <el-col :xs="16" :sm="16" :md="16" :lg="16">
                                <i class="el-icon-bottom"></i>
                                {{ topRanking }}
                                <el-tooltip  placement="top">
                                  <div slot="content">
                                    {{ $t("index.statisticalCutoff") }} {{ frontPage.record_date }};<br/>
                                    {{ hintData }}
                                  </div>
                                  <i class="el-icon-question myOrange"></i>
                                </el-tooltip>
                              </el-col>
                              <el-col :xs="8" :sm="8" :md="8" :lg="8">
                                <el-switch
                                  style="display: block;text-align: right;"
                                  v-model="usageType"
                                  active-color="#3c97fd"
                                  @change="changeUsageType"
                                  inactive-color="#67c23a"
                                  :active-text="$t('common.month')"
                                  :inactive-text="$t('common.day')">
                                </el-switch>
                              </el-col>
                            </el-row>
                       </span>
                    </el-col>
                  </el-row>
                </div>
                <div class="body">
                  <el-row>
                    <el-col :xs="24" :sm="24" :md="16" :lg="16">
                      <line-chart v-if="usage_show" :chart-data="usageLineData" :p_xAxis="usage_p_xAxis" :p_colorArr="usage_p_colorArr"
                                  :p_showLable="usage_p_showLable" :p_config="p_config" height="230px"/>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="8" :lg="8">

                      <el-table :data="usage_table">
                        <el-table-column label="ICCID" align="left" width="180">
                          <template slot-scope="scope">
                            <router-link
                              :to="'/connect/cardList/'+ tools.encryptSy({'type':'0','value':scope.row.iccid})"
                              class="link-type">
                              <span>{{ scope.row.iccid }}</span>
                            </router-link>
                          </template>
                        </el-table-column>
                        <el-table-column :label="$t('index.statisticalCutoff')" align="right" prop="reveal_used"/>
                      </el-table>
                    </el-col>
                  </el-row>
                </div>
              </el-card>
              </el-col>
            </el-row>

           <el-card style="margin-top: 10px;">
              <div slot="header" >
                <span>{{ $t("index.activateTrend") }}</span>

              </div>
              <div class="body">
                <el-row>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24">
                    <line-chart style="width: 100%;" :chart-data="frontPage.activate_line.data" :p_xAxis="frontPage.activate_line.p_xAxis" :p_colorArr="activate_p_colorArr"
                                :p_showLable="activate_p_showLable" :p_config="p_config"  height="285px"/>
                  </el-col>
                </el-row>
              </div>
            </el-card>

          </el-tab-pane>
          <el-tab-pane :label="$t('index.deptActivateTitle')" name="dept_activate">
              <el-row>
                <el-col :xs="24" :sm="24" :md="24" :lg="24">
                  <pie ref="dept_activate"  v-if="dept_activate_show" :option="dept_activate_p_series"   height="630px" width="1570px"  />
                </el-col>
              </el-row>
          </el-tab-pane>

          <el-tab-pane :label="$t('index.deptProportionTitle')" name="dept_proportion">
            <el-row>
              <el-col :xs="24" :sm="24" :md="24" :lg="24">
                <pie ref="dept_proportion"  :option="dept_proportion_p_series"   height="630px" width="1570px"  />
              </el-col>
            </el-row>
          </el-tab-pane>

          <el-tab-pane :label="$t('index.deptUsageTitle')" name="dept_usage">
            <el-row>
              <el-col :xs="24" :sm="24" :md="24" :lg="24">
                <pie ref="dept_usage"  :option="dept_usage_p_series"   height="630px" width="1570px"  />
              </el-col>
            </el-row>
          </el-tab-pane>

          <el-tab-pane :label="$t('index.channelTitle')" name="channel" v-if="headquarters" >
            <el-row>
              <el-col :xs="24" :sm="24" :md="24" :lg="24">
                <pie ref="channel"  :option="channel_p_series"  height="630px" width="1570px"  />
              </el-col>
            </el-row>
          </el-tab-pane>

        </el-tabs>

      </el-tab-pane>
      <el-tab-pane :label="$t('index.LoginBulletinBoard')" name="LoginBulletinBoard"  v-if="headquarters" >
        <el-tabs tab-position="right" v-model="activeNameLogin" @tab-click="handleClickLogin">
          <el-tab-pane :label="$t('index.loginPieTitle')" name="login_pie">
            <el-row>
              <el-col :xs="24" :sm="24" :md="24" :lg="24">
                <pie ref="login_pie"   :option="login_pie_p_series" height="630px" width="1570px"  />
              </el-col>
            </el-row>
          </el-tab-pane>


<!--          <el-tab-pane :label="$t('index.loginPieTitle')" name="login_map">
            <el-row>
              <el-col :xs="24" :sm="24" :md="24" :lg="24">
                全球地图
              </el-col>
            </el-row>
          </el-tab-pane>-->

        </el-tabs>
      </el-tab-pane>

    </el-tabs>

    <div  v-hasPermi="['monitor:online:list']">
      <div v-if="loadingOnelinCount==false" >
        {{this.getOnelinCount()}}
      </div>
    </div>


  </div>
</template>

<script>
import LineChart from './dashboard/LineChart'
import PieChart from './dashboard/PieChart'
import tools from "@/utils/iotos/tools";
import myCol from './components/myCol'
import {find} from "@/api/iotos/sys/index";
import {getDeptName} from "@/api/system/dept";
import pie from './iotos/echarts/pie'
import  { list } from "@/api/monitor/online";
import Cookies from "js-cookie";


export default {
  name: 'index',
  components: {
    LineChart,
    PieChart,
    myCol,
    pie
  },
  data() {
    return {
      tools: tools,
      usageType: false,//用量查看方式
      monthFirstDay:tools.getMonthFirstDay(),
      usage_p_xAxis: [],
      usage_p_colorArr: this.usageType?['#3c97fd']:['#67c23a'],//折线图 颜色
      usage_p_showLable: this.usageType?{data: this.$t("index.monthlyDosage")}:{data: this.$t("index.dailyDosage")},
      usageTitle: this.usageType?this.$t("index.monthlyData"):this.$t("index.dailyData"),
      topRanking: this.usageType?this.$t("index.thisMonthTopRanking"):this.$t("index.yesterdayTopRanking"),
      hintData: this.usageType?this.$t("index.hint.month"):this.$t("index.hint.daily"),
      usage_table: [],
      usage_show:true,

      p_config: {"smooth": false, 'areaStyle_show': "true"},
      duration: 1500,//
      p_legend: {
        top: '5%',
        left: 'center'
      },
      life_cycle_p_series: {
        center: ['50%', '43%'],
        name: this.$t("index.lifeCycleTitle"),
        type: 'pie',
        radius: ['20%', '60%'],

        data: [
          {value: 1, name: '库存'},
          {value: 2, name: '待激活'},
          {value: 3, name: '可测试'},
          {value: 4, name: '已激活'},
          {value: 5, name: '已停机'},
          {value: 6, name: '预销户'},
          {value: 7, name: '已销户'},
          {value: 8, name: '未知'}
        ]
      },
      life_cycle_show:true,
      life_cycle_p_color: ['#b180f5', '#0186ff', '#61c5f9', '#38cb8e', '#fa535f', '#fe9e74', '#899caf', '#303133'],

      activeName: "basic",//默认选择
      activeNameBasic: "usage_line",//默认选择
      activeNameLogin: "login_pie",//默认选择
      frontPage: {
        id: '',
        dept_id: '',
        record_date: '',
        create_time: '',
        last_upd_time: '',
        usage_line: {
          usage_month:{
            data: {},
            p_xAxis:[],
          },
          usage_day:{
            data: {},
            p_xAxis:[],
          },
        },
        activate_line: {
          data: {},
          p_xAxis:[],
        },
        usage_table: {
          usage_table_month:[],
          usage_table_day: [],
        },
        life_cycle_pie: {},
        dept_proportion_pie: {},
        dept_activate_pie: {},
        dept_usage_pie: {},
        channel_pie: {},
        card_count: 0,
        agent_card_count: 0,
        dept_card_count: 0,
        dept_count: 0,
        login_ip_count: 0,
        login_pie: {},
        login_map: {},
        card_add_count: 0,
        card_activation_count: 0,
      },
      usageLineData: {},
      onlineUserCount: 0,//在线用户数
      activate_p_xAxis: [],
      activate_p_colorArr: ['rgb(58, 164, 255)'],//折线图 颜色
      activate_p_showLable:{data: this.$t("index.activateTrend")},

      dept_activate_show:true,

      pieCommonLabel:{
        formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
        backgroundColor: '#eee',
        borderColor: '#aaa',
        borderWidth: 1,
        borderRadius: 4,
        rich: {
          a: {
            color: '#999',
            lineHeight: 22,
            align: 'center'
          },

          hr: {
            borderColor: '#aaa',
            width: '100%',
            borderWidth: 0.5,
            height: 0
          },
          b: {
            fontSize: 16,
            lineHeight: 33
          },
          per: {
            color: '#eee',
            backgroundColor: '#334455',
            padding: [2, 4],
            borderRadius: 2
          }
        }
      },

      dept_activate_p_series:{
        series: [
          {
            tooltip: {
              trigger: 'item',
              formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            name: this.$t("index.deptActivateTitle"),
            type: 'pie',
            center: ['50%', '45%'],
            radius: ['40%', '55%'],
            label: this.pieCommonLabel,
            data: []
          }
        ]
     },

      dept_proportion_p_series:{
      series: [
        {
          name: this.$t("index.deptProportionTitle"),
          type: 'pie',
          center: ['50%', '45%'],
          radius: ['40%', '55%'],
          label: this.pieCommonLabel,
          data: []
        }
      ]
    },
      dept_usage_p_series:{
        series: [
          {
            name: this.$t("index.deptUsageTitle"),
            type: 'pie',
            center: ['50%', '45%'],
            radius: ['40%', '55%'],
            label: this.pieCommonLabel,
            data: []
          }
        ]
      },
      channel_p_series:{
        series: [
          {
            name: this.$t("index.channelTitle"),
            type: 'pie',
            center: ['50%', '45%'],
            radius: ['40%', '55%'],
            label: this.pieCommonLabel,
            data: []
          }
        ]
      },
      login_pie_p_series:{
        series: [
          {
            name: this.$t("index.loginPieTitle"),
            type: 'pie',
            center: ['50%', '45%'],
            radius: ['40%', '55%'],
            label: this.pieCommonLabel,
            data: [

            ]
          }
        ]
      },

      cardStatusShowOptions: [],
      deptsOptions: [],//企业数组
      channelOptions: [],//通道
      loadingOnelinCount:true,
      headquarters:false,
    }
  },
  mounted() {

  },
  created() {
    //加载 卡状态
    if (window['cardStatusShowOptions'] != undefined && window['cardStatusShowOptions'] != null && window['cardStatusShowOptions'] != '') {
      this.cardStatusShowOptions = window['cardStatusShowOptions'];
    } else {
      this.getDicts("card_status_show_id").then(response => {
        window['cardStatusShowOptions'] = response.data;
        this.cardStatusShowOptions = window['cardStatusShowOptions'];
      });
    }
    //加载 企业名称
    if (window['deptsOptions'] != undefined && window['deptsOptions'] != null && window['deptsOptions'] != '') {
      this.deptsOptions = window['deptsOptions'];
    } else {
      getDeptName().then(response => {
        let jsonObj = JSON.parse(tools.Decrypt(response));
        window['deptsOptions'] = jsonObj.data;
        this.deptsOptions = window['deptsOptions'];
      });
    }

    //加载 通道
    if (window['channelOptions'] != undefined && window['channelOptions'] != null && window['channelOptions'] != '') {
      this.channelOptions = window['channelOptions'];
    } else {
      let pwdStr = tools.encryptSy({});
      this.getNameOpen(pwdStr).then(response => {
        let jsonObj = JSON.parse(tools.Decrypt(response));
        window['channelOptions'] = jsonObj.data;
        this.channelOptions = window['channelOptions'];
      });
    }


    this.dept_activate_p_series.series[0].label = this.pieCommonLabel;
    this.login_pie_p_series.series[0].label = this.pieCommonLabel;
    this.channel_p_series.series[0].label = this.pieCommonLabel;
    this.dept_usage_p_series.series[0].label = this.pieCommonLabel;
    this.dept_proportion_p_series.series[0].label = this.pieCommonLabel;




    this.findIndex();
  },
  methods: {


    findIndex(){
      let pwdStr = tools.encryptSy({});
      find(pwdStr).then(response => {
        let jsonObj = JSON.parse(tools.Decrypt(response));
        let msg = jsonObj.msg;

        if (jsonObj.code == 200) {
          let rData = jsonObj.data.data;
          let bool = jsonObj.data.bool;
          if (jsonObj.deptId == '100'){
            this.headquarters = true;
            let username = Cookies.get("username");
            if(username!='iotos'){//过滤演示账号访问提示无权限
              this.loadingOnelinCount = false;
            }
          }
          if(bool){
            if(rData!=null){
              //console.log(rData);
              let frontPage = rData;
              this.frontPage = frontPage;
              let usage_line = {
                usage_month:{
                  data:[],
                  p_xAxis:[],
                },
                usage_day:{
                  data:[],
                  p_xAxis:[],
                },
              };
              let life_cycle_pie = {};
              let dept_proportion_pie = {};
              let dept_activate_pie = {};
              let dept_usage_pie = {};
              let channel_pie = {};

              //用量增长趋势
              if(tools.isNull(frontPage.usage_line)){
                frontPage.usage_line = JSON.parse(frontPage.usage_line);
                let data_month = [],data_day = [],p_xAxis_month = [],p_xAxis_day = [];
                for (let i = 0; i < frontPage.usage_line.usage_month.length; i++) {
                  let obj = frontPage.usage_line.usage_month[i];
                  p_xAxis_month.push(obj.record_date);
                  data_month.push(''+obj.total_day_used);
                }
                for (let i = 0; i < frontPage.usage_line.usage_day.length; i++) {
                  let obj = frontPage.usage_line.usage_day[i];
                  let strArr = obj.record_date.split("-");
                  p_xAxis_day.push(strArr[1]+"-"+strArr[2]);
                  data_day.push(''+obj.total_day_used);
                }
                usage_line.usage_month.data = {data:data_month};
                usage_line.usage_month.p_xAxis = p_xAxis_month;
                usage_line.usage_day.data = {data:data_day};
                usage_line.usage_day.p_xAxis = p_xAxis_day;
              }
              this.frontPage.usage_line = usage_line;
              if(tools.isNull(frontPage.activate_line)){
                //激活增长趋势
                this.frontPage.activate_line = this.getLineData(frontPage.activate_line,'groupType','count');
              }
              //用量排行榜
              if(tools.isNull(frontPage.usage_table)){
                this.frontPage.usage_table = JSON.parse(frontPage.usage_table);
              }
              //生命周期占比
              if(tools.isNull(frontPage.life_cycle_pie)){
                frontPage.life_cycle_pie = JSON.parse(frontPage.life_cycle_pie);
                let statusShowList = frontPage.life_cycle_pie.statusShowList;
                let list = [],newList = [];
                if(statusShowList.length!=8){
                  //补 0
                  for (let i = 0; i < 8; i++) {
                    let bool = false;
                    let obj = null;
                    for (let j = 0; j < statusShowList.length; j++) {
                      obj = statusShowList[j];
                      if(obj.groupType==i){
                        bool = true;
                        break;
                      }
                    }
                    if(!bool){
                      let map = {};
                      map.groupType = i;
                      map.count = 0;
                      newList.push(map);
                    }else {
                      newList.push(obj);
                    }
                  }
                }


                for (let i = 0; i < newList.length; i++) {
                    let obj = newList[i];
                    let map = {};
                    map.name = tools.getDkeyValue(cardStatusShowOptions,obj.groupType);
                    map.value = obj.count;
                    list.push(map);
                }




                let firstElement = list.shift(); // 移除第一个元素并将其保存到变量中
                list.push(firstElement); // 将保存的第一个元素添加到数组的末尾
                this.life_cycle_p_series.data = list;
              }
              //企业拿卡占比
              if(tools.isNull(frontPage.dept_proportion_pie))
              {
                let dept_proportion_pie = JSON.parse(frontPage.dept_proportion_pie);
                for (let i = 0; i < dept_proportion_pie.length; i++) {//写入企业名称
                  let obj = dept_proportion_pie[i];
                  obj.groupType = tools.getkeyValue(deptsOptions, obj.groupType,"dept_id","dept_name");
                }
                 dept_proportion_pie = tools.replaceListMapKey(dept_proportion_pie,{ groupType: 'name', count: 'value' });
                this.dept_proportion_p_series.series[0].data = dept_proportion_pie;
              }
              //企业激活占比
              if(tools.isNull(frontPage.dept_activate_pie))
              {
                let dept_activate_pie = JSON.parse(frontPage.dept_activate_pie);
                for (let i = 0; i < dept_activate_pie.length; i++) {//写入企业名称
                  let obj = dept_activate_pie[i];
                  obj.groupType = tools.getkeyValue(deptsOptions, obj.groupType,"dept_id","dept_name");
                }
                dept_activate_pie = tools.replaceListMapKey(dept_activate_pie,{ groupType: 'name', count: 'value' });
                this.dept_activate_p_series.series[0].data = dept_activate_pie;
              }
              //企业用量占比
              if(tools.isNull(frontPage.dept_usage_pie))
              {
                let dept_usage_pie = JSON.parse(frontPage.dept_usage_pie);
                for (let i = 0; i < dept_usage_pie.length; i++) {//写入企业名称
                  let obj = dept_usage_pie[i];
                  obj.groupType = tools.getkeyValue(deptsOptions, obj.groupType,"dept_id","dept_name")
                }
                dept_usage_pie = tools.replaceListMapKey(dept_usage_pie,{ groupType: 'name', sum: 'value' });
                this.dept_usage_p_series.series[0].data = dept_usage_pie;
              }
              //通道卡号占比
              if(tools.isNull(frontPage.channel_pie))
              {
                let channel_pie = JSON.parse(frontPage.channel_pie);
                for (let i = 0; i < channel_pie.length; i++) {//写入企业名称
                  let obj = channel_pie[i];
                  obj.groupType =  tools.getDkeyValue(channelOptions, obj.groupType);
                }
                channel_pie = tools.replaceListMapKey(channel_pie,{ groupType: 'name', count: 'value' });
                this.channel_p_series.series[0].data = channel_pie;
              }
              //用户登录次数比例
              if(tools.isNull(frontPage.login_pie))
              {
                let login_pie = JSON.parse(frontPage.login_pie);
                login_pie = tools.replaceListMapKey(login_pie,{ groupType: 'name', count: 'value' });
                this.login_pie_p_series.series[0].data = login_pie;
              }







              this.changeUsageType();
            }
          }
        } else {
          this.$message.error(msg);
        }
      })


    },

    //获取在线用户数
    getOnelinCount(){
      if(this.loadingOnelinCount==false){
        this.loadingOnelinCount = true;
        list().then(response => {
          this.onlineUserCount = response.total;
          let _this = this;
          setTimeout(function(){
            _this.loadingOnelinCount = false;
          },180*1000);//3 分钟 刷新一次
        });
      }
    },



    //获取 LineData
    getLineData(list,data_key,p_key){
      let map = {};
      if(tools.isNull(list)){
        list = JSON.parse(list);
        let data_list = [],p_xAxis = [];
        for (let i = 0; i < list.length; i++) {
          let obj = list[i];
          p_xAxis.push(obj[data_key]);
          data_list.push(''+obj[p_key]);
        }
        map.data = {};
        map.data.data = data_list;
        map.p_xAxis = p_xAxis;
      }
      return map;
    },



    handleClick() {

    },
    handleClickBasic() {

      switch (this.activeNameBasic){
        case 'dept_activate':

          break;
        case 'usage_line':

          break;

      }
    },
    changeUsageType(){
        this.usage_show = false;
        if(this.usageType){
          this.usage_p_colorArr = ['#3c97fd']
          this.usage_p_showLable = {data: this.$t("index.monthlyDosage")};
          this.topRanking = this.$t("index.thisMonthTopRanking");
          this.hintData = this.$t("index.hint.month");
          this.usageTitle = this.$t("index.monthlyData");
          if(tools.isNull(this.frontPage.usage_line.usage_month)) {
            this.usageLineData = this.frontPage.usage_line.usage_month.data;
            this.usage_p_xAxis = this.frontPage.usage_line.usage_month.p_xAxis;
          }
          if(tools.isNull(this.frontPage.usage_table)){
            this.usage_table = this.frontPage.usage_table.usage_table_month;
          }

        }else {
          this.usage_p_colorArr = ['#67c23a'];
          this.usage_p_showLable = {data: this.$t("index.dailyDosage")};
          this.topRanking = this.$t("index.yesterdayTopRanking");
          this.hintData = this.$t("index.hint.month");
          this.usageTitle = this.$t("index.dailyData");

          if(tools.isNull(this.frontPage.usage_line.usage_day)) {
            this.usageLineData = this.frontPage.usage_line.usage_day.data;
            this.usage_p_xAxis = this.frontPage.usage_line.usage_day.p_xAxis;
          }
          if(tools.isNull(this.frontPage.usage_table)){
            this.usage_table = this.frontPage.usage_table.usage_table_day;
          }

        }
      this.usage_show = true;
    },

    handleClickLogin() {

    },



  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
.myOrange{
  color: orange;
}


</style>
<style>

.el-tabs--border-card > .el-tabs__content{
  padding: 7.5px;
}
.el-table--small .el-table__cell{
  padding: 6px 0;
}
</style>

