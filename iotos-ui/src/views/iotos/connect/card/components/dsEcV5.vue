<template>
  <div>
    <div style="width: 100%;font-size: 30px;height: 50px;padding:10px;pborder: 1px solid red">
      <i  :style="{color: dgForm.iColor}" :class="dgForm.iClass"/>
      <span style="margin-left:10px;font-size: 25px">{{ dgForm.iTitle }}</span>
    </div>
    <el-progress :show-text="false"	 style="margin-bottom: 10px;" :stroke-width="10"  :percentage="percentage" :color="customColor"></el-progress>

    <el-collapse v-model="activeNames">

      <el-card v-if="dgForm.te1.show">
        <el-collapse-item  name="1"  >
          <template slot="title">
            <i :class="dgForm.te1.class" :style="{color:dgForm.te1.color}"></i> {{ lgCode.te1.title }}
          </template>
          <el-row class="drow">
            <el-col :span="6">
              {{ lgCode.te1.i_1 }}: {{ dgForm.te1.value1 }}
            </el-col>
            <el-col :span="6" v-if="dgForm.te1.value2Sohw">
              {{ lgCode.te1.i_2 }}: {{ dgForm.te1.value2 }}
            </el-col>
            <el-col :span="6">
              {{ lgCode.te1.i_3 }}
              <el-tooltip placement="top">
                <div slot="content">
                  {{ lgCode.te1.i_3top }}
                </div>
                <i class="el-icon-question iotosBlue"></i>
              </el-tooltip>:{{ dgForm.te1.value3 }}
            </el-col>
            <el-col :span="6">
              {{ lgCode.te1.i_4 }}:{{ dgForm.te1.value4 }}
            </el-col>
          </el-row>
          <div  v-if="dgForm.te1.showSuggested">
            <el-divider class="myDivider"/>
            <div>
              <span class="myBold">{{ lgCode.suggestedSolution }}</span>{{ lgCode.te1.solution }}
            </div>
          </div>
        </el-collapse-item>
      </el-card>

      <el-card v-if="dgForm.te2.show">
        <el-collapse-item  name="2">
          <template slot="title">
            <i :class="dgForm.te2.class" :style="{color:dgForm.te2.color}"></i> {{ lgCode.te2.title }}
            <el-tooltip placement="right">
              <div slot="content">
                {{ lgCode.te2.titletop }}
              </div>
              <i class="el-icon-info orgColor" ></i>
            </el-tooltip>
          </template>
          <el-table v-loading="loadingService" :data="serviceTypeList" >
            <el-table-column :label="columns[0].label" align="center" prop="serviceType"  >
              <template slot-scope="scope">
                {{ tools.getDkeyValue(ecv5ServiceTypeOptions, scope.row.serviceType) }}
              </template>
            </el-table-column>
            <el-table-column :label="columns[1].label" align="center" prop="apnName"  />
            <el-table-column :label="columns[2].label" align="center" prop="serviceStatus"  >
              <template slot-scope="scope">
                <span :class="scope.row.serviceStatus=='1'?'myGreen':'mygRed'">{{ tools.getDkeyValue(ecv5ServiceStatusOptions, scope.row.serviceStatus) }}</span>
              </template>
            </el-table-column>
            <el-table-column :label="columns[3].label" align="center" prop="statusTime"  >
              <template slot-scope="scope">
                {{ scope.row.statusTime.replace(/(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})/, "$1-$2-$3 $4:$5:$6") }}
              </template>
            </el-table-column>
          </el-table>
        </el-collapse-item>
      </el-card>

      <el-card v-if="dgForm.te3.show">
        <el-collapse-item  name="3">
          <template slot="title">
            <i :class="dgForm.te3.class" :style="{color:dgForm.te3.color}"></i> {{ lgCode.te3.title }}
          </template>
          <el-row class="drow">
            <el-col :span="3">
              {{ lgCode.te3.i_1 }}: {{ dgForm.te3.value1 }}
            </el-col>
            <el-col :span="5">
              {{ lgCode.te3.i_2 }}: {{ dgForm.te3.value2 }}
            </el-col>
            <el-col :span="16">
              {{ lgCode.te3.i_3 }}:
              <el-checkbox-group
                v-model="restrictedArea">
                <el-checkbox 	v-for="city in restrictedAreaOptions" :label="city.dictValue" :key="city.dictValue">{{city.dictLabel}}</el-checkbox>
              </el-checkbox-group>
            </el-col>
          </el-row>
        </el-collapse-item>

      </el-card>

      <el-card v-if="dgForm.te4.show">
        <el-collapse-item  name="4">
          <template slot="title">
            <i :class="dgForm.te4.class" :style="{color:dgForm.te4.color}"></i> {{ lgCode.te4.title }}
          </template>

          <el-steps align-center :active="dgForm.cardApiBusinessList.length" >
            <el-step :title="item.create_time" :description="'['+tools.getDkeyValue(ecv5ChangeStatusOptions, item.descStatus)+']'+$t('cardInfoModule.cafterward')+'['+tools.getDkeyValue(ecv5ChangeStatusOptions, item.targetStatus)+']'"
                     v-for="item in  dgForm.cardApiBusinessList">
            </el-step>
          </el-steps>
        </el-collapse-item>
      </el-card>
    </el-collapse>

    <!-- 触发加载数据函数-->
    <span v-if="show_Details ==true && dsEditexecute == false">
        {{ LoadEx() }}
    </span>


  </div>
</template>

<script>
import {
  queryFStatus,
  queryApiTariff,
  querySimStopReason,
  querySimProduct,
  querySimRLStatus,
  querySimRLArea,
  queryOnOffStatus,
  synStatus,
  queryCardBindStatus,
  querySimChangeHistory,
} from "@/api/iotos/connect/card";
  import tools from "@/utils/iotos/tools";
  import '@/assets/styles/iotos.css';


  export default {
    props: {
      setObj: Function,//父组件赋值 函数
      dsEditexecute: Boolean,//加载是否 已执行
      show_Details: Boolean,//当前界面是否显示
      sel: Object,

    },
    name: "cardInfoDetails",
    data() {
      return {
        tools:tools,
        activeNames:[],//展开页
        loadingService:false,
        serviceTypeList:[],

        // 列信息
        columns: [
          {key: 0, label: this.$t('dsEcV5.table.i_1')},
          {key: 1, label: this.$t('dsEcV5.table.i_2')},
          {key: 2, label: this.$t('dsEcV5.table.i_3')},
          {key: 3, label: this.$t('dsEcV5.table.i_4')},

        ],
        ecv5ServiceStatusOptions:[],
        ecv5ServiceTypeOptions:[],
        restrictedAreaOptions:[],
        restrictedArea:[],
        percentage: 0,
        customColor: 'rgb(59, 164, 255)',
        dgForm:{
          te1:{color:'#4CAF50',class:'header-icon el-icon-success',value1:'--',value2:'--',value3:'--',value4:'--',showSuggested:false, show:false,value2Sohw:false},
          te2:{color:'#4CAF50',class:'header-icon el-icon-success',show:false},
          te3:{color:'#4CAF50',class:'header-icon el-icon-success',value1:'--',value2:'--',show:false},
          te4:{color:'#4CAF50',class:'header-icon el-icon-success',show:false},
          cardApiBusinessList: [],//业务变更 数组
          iColor:'#3ba4ff',
          iClass:'el-icon-loading',
          iTitle:this.$t('dsEcV5.dTitle.i_1')
        },
        lgCode:{
          dTitle:{
            i_2:this.$t('dsEcV5.dTitle.i_2'),
          },
          te1:{
            title:this.$t('dsEcV5.te1.title'),
            i_1:this.$t('dsEcV5.te1.i_1'),
            i_2:this.$t('dsEcV5.te1.i_2'),
            i_3:this.$t('dsEcV5.te1.i_3'),
            i_3top:this.$t('dsEcV5.te1.i_3top'),
            i_4:this.$t('dsEcV5.te1.i_4'),
            solution:this.$t('dsEcV5.te1.solution'),
          },
          te2:{
            title:this.$t('dsEcV5.te2.title'),
            titletop:this.$t('dsEcV5.te2.titletop'),
          },
          te3:{
            title:this.$t('dsEcV5.te3.title'),
            i_1:this.$t('dsEcV5.te3.i_1'),
            i_2:this.$t('dsEcV5.te3.i_2'),
            i_3:this.$t('dsEcV5.te3.i_3'),
          },
          te4:{
            title:this.$t('dsEcV5.te4.title'),
          },
          suggestedSolution:this.$t('dsEcV5.suggestedSolution'),
        },
        ecv5ChangeStatusOptions:[],
        cardStatusShowOptions:[],
        cardStatusOptions:[],
        ecv5OnoffStatusOptions:[],
        whetherOptions:[],
        color:{
          green:'#4CAF50',
          red:'#ff5f57',
          blue:'#3ba4ff'
        },
        ecv5BindStatus:[],
      }
    },
    created() {
      //加载 是否
      if (window['whetherOptions'] != undefined && window['whetherOptions'] != null && window['whetherOptions'] != '') {
        this.whetherOptions = window['whetherOptions'];
      } else {
        this.getDicts("iotos_whether").then(response => {
          window['whetherOptions'] = response.data;
          this.whetherOptions = window['whetherOptions'];
        });
      }
      //加载 卡状态描述
      if (window['cardStatusOptions'] != undefined && window['cardStatusOptions'] != null && window['cardStatusOptions'] != '') {
        this.cardStatusOptions = window['cardStatusOptions'];
      } else {
        this.getDicts("card_status_id").then(response => {
          window['cardStatusOptions'] = response.data;
          this.cardStatusOptions = window['cardStatusOptions'];
        });
      }
      //加载 卡状态
      if (window['cardStatusShowOptions'] != undefined && window['cardStatusShowOptions'] != null && window['cardStatusShowOptions'] != '') {
        this.cardStatusShowOptions = window['cardStatusShowOptions'];
      } else {
        this.getDicts("card_status_show_id").then(response => {
          window['cardStatusShowOptions'] = response.data;
          this.cardStatusShowOptions = window['cardStatusShowOptions'];
        });
      }
      //加载 ECV5服务状态
      if (window['ecv5ServiceStatusOptions'] != undefined && window['ecv5ServiceStatusOptions'] != null && window['ecv5ServiceStatusOptions'] != '') {
        this.ecv5ServiceStatusOptions = window['ecv5ServiceStatusOptions'];
      } else {
        this.getDicts("ecv5_service_status").then(response => {
          window['ecv5ServiceStatusOptions'] = response.data;
          this.ecv5ServiceStatusOptions = window['ecv5ServiceStatusOptions'];
        });
      }
      //加载 ECV5通信功能服务类型
      if (window['ecv5ServiceTypeOptions'] != undefined && window['ecv5ServiceTypeOptions'] != null && window['ecv5ServiceTypeOptions'] != '') {
        this.ecv5ServiceTypeOptions = window['ecv5ServiceTypeOptions'];
      } else {
        this.getDicts("ecv5_service_type").then(response => {
          window['ecv5ServiceTypeOptions'] = response.data;
          this.ecv5ServiceTypeOptions = window['ecv5ServiceTypeOptions'];
        });
      }
      //加载 ECV5限制区域
      if (window['restrictedAreaOptions'] != undefined && window['restrictedAreaOptions'] != null && window['restrictedAreaOptions'] != '') {
        this.restrictedAreaOptions = window['restrictedAreaOptions'];
      } else {
        this.getDicts("ecv5_restricted_area").then(response => {
          window['restrictedAreaOptions'] = response.data;
          this.restrictedAreaOptions = window['restrictedAreaOptions'];
        });
      }
      //加载 ECV5变更状态类型
      if (window['ecv5ChangeStatusOptions'] != undefined && window['ecv5ChangeStatusOptions'] != null && window['ecv5ChangeStatusOptions'] != '') {
        this.ecv5ChangeStatusOptions = window['ecv5ChangeStatusOptions'];
      } else {
        this.getDicts("ecv5_change_status").then(response => {
          window['ecv5ChangeStatusOptions'] = response.data;
          this.ecv5ChangeStatusOptions = window['ecv5ChangeStatusOptions'];
        });
      }
      //加载 ECV5开关机状态
      if (window['ecv5OnoffStatusOptions'] != undefined && window['ecv5OnoffStatusOptions'] != null && window['ecv5OnoffStatusOptions'] != '') {
        this.ecv5OnoffStatusOptions = window['ecv5OnoffStatusOptions'];
      } else {
        this.getDicts("ecv5_onoff_status").then(response => {
          window['ecv5OnoffStatusOptions'] = response.data;
          this.ecv5OnoffStatusOptions = window['ecv5OnoffStatusOptions'];
        });
      }
      //加载 ECV5开关机状态
      if (window['ecv5BindStatus'] != undefined && window['ecv5BindStatus'] != null && window['ecv5BindStatus'] != '') {
        this.ecv5BindStatus = window['ecv5BindStatus'];
      } else {
        this.getDicts("ecv5_bind_status").then(response => {
          window['ecv5BindStatus'] = response.data;
          this.ecv5BindStatus = window['ecv5BindStatus'];
        });
      }

    },
    methods: {

      LoadEx() {
        this.activeNames = [];
        this.$emit("setObj", "setDsEditexecute", true);//已加载
        this.te1Load();
      },
      //诊断板块一
      te1Load(){
        this.percentage = 0;//进度条
        //重置状态
        this.dgForm = {
          te1:{color:'#4CAF50',class:'header-icon el-icon-success',value1:'--',value2:'--',value3:'--',value4:'--',showSuggested:false, show:false,value2Sohw:false},
          te2:{color:'#4CAF50',class:'header-icon el-icon-success',show:false},
          te3:{color:'#4CAF50',class:'header-icon el-icon-success',value1:'--',value2:'--',show:true},
          te4:{color:'#4CAF50',class:'header-icon el-icon-success',show:true},
          cardApiBusinessList: [],//业务变更 数组
          iColor:'#3ba4ff',
          iClass:'el-icon-loading',
          iTitle:this.$t('dsEcV5.dTitle.i_1')
        };
        this.serviceTypeList = [];
        this.restrictedArea = [];
        this.dgForm.te1.show = true;
        this.addPercentage(10);//进度条

        this.synCardStatus();//同步状态
        this.queryOOStatus();//设备状态（开关机状态）
        this.queryCardFStatus();//冻结状态查询

        this.queryProduct(); //单卡通信功能开通查询


        this.queryRLStatus(); //物联卡区域限制状态查询
        this.queryRLArea();//物联卡区域限制区域查询
        this.queryBindStatus(); //物联卡机卡分离状态查询
        this.queryChangeHist();//单卡状态变更历史查询
        this.addPercentage(10);//进度条

      },
      //同步状态
      synCardStatus(){
        synStatus(this.synCommonParameter()).then(response => {
          let retuenList = this.synCommonFeedback(response);
          if(tools.isNull(retuenList)){
            let row = retuenList[0].rData;
            let value = tools.getDkeyValue(this.cardStatusShowOptions, row.status_show_id);
             value += " [" + tools.getDkeyValue(this.cardStatusOptions, row.status_id) + "]";
            this.dgForm.te1.value1 = value;
            let eArr = ['2','3','4'];
            if(!tools.VerificationValIsArray(eArr,row.status_show_id)){
              this.dgForm.te1.showSuggested = true;
              this.addActiveNames("1","te1");
              if(row.status_show_id=="5"){//已停机 停机原因
                this.queryStopReason();
              }
            }
          }
          this.addPercentage(10);//进度条
        })
      },
      //查询停机原因
      queryStopReason(){
        querySimStopReason(this.synCommonParameter()).then(response => {
          let retuenList = this.synCommonFeedback(response);
          if(tools.isNull(retuenList)){
            let result = retuenList[0].rData.result;
            let stopReason = result[0].stopReason;
            let value2 = '';
            if(stopReason=="000000000000"){
              value2 = "该卡当前不处于“已停机”或系统暂无停机原因";
            }else{
              value2 = this.causeAnalysis(stopReason);
              this.addActiveNames("1","te1");
            }
            this.dgForm.te1.value2 = value2;
            this.dgForm.te1.value2Sohw = true;
          }
        })
      },
      //设备状态（开关机状态）
      queryOOStatus(){
        queryOnOffStatus(this.synCommonParameter()).then(response => {
          let retuenList = this.synCommonFeedback(response);
          if(tools.isNull(retuenList)){
            let result = retuenList[0].rData.result;
            let status = result[0].status;
            if(status=='0'){
              this.addActiveNames("1","te1");
            }
            this.dgForm.te1.value3 = tools.getDkeyValue(this.ecv5OnoffStatusOptions, status);
          }
          this.addPercentage(10);//进度条
        })
      },



      //展开面板
      addActiveNames(val,te){
        if(!tools.VerificationValIsArray(this.activeNames,val)){
          this.activeNames.push(val);
          this.dgForm[te].color = this.color.red;
          this.dgForm[te].class = 'header-icon el-icon-error';
        }
      },

      //冻结状态查询
      queryCardFStatus(){
        queryFStatus(this.synCommonParameter()).then(response => {
          let retuenList = this.synCommonFeedback(response);
          if(tools.isNull(retuenList)){
            let result = retuenList[0].rData.result;
            let map = result[0];
            if(tools.isNull(map)){
              let rs = map.manageStopRestartStatus;
              let reason = map.reason;
              let value4 = tools.getDkeyValue(this.whetherOptions, rs);
              if(tools.isNull(reason)){
                value4 +="["+reason+"]"
              }
              this.dgForm.te1.value4 = value4;
            }
          }
          this.addPercentage(10);//进度条
        })
      },


      //单卡通信功能开通查询
      queryProduct(){
        this.loadingService = true;
        querySimProduct(this.synCommonParameter()).then(response => {
          let retuenList = this.synCommonFeedback(response);
          this.dgForm.te2.show = true;
          if(tools.isNull(retuenList)){
            let result= retuenList[0].rData.result;
            if(tools.isNull(result)==true && result.length>0){
              this.serviceTypeList = result[0].serviceTypeList;
              if(this.serviceTypeList[0].serviceStatus=='0'){
                this.addActiveNames("2","te2");
              }
              this.loadingService = false;
            }
          }
          this.addPercentage(10);//进度条
        })
      },


      //物联卡区域限制状态查询
      queryRLStatus(){
        querySimRLStatus(this.synCommonParameter()).then(response => {
          let retuenList = this.synCommonFeedback(response);
          if(tools.isNull(retuenList)){
            let result= retuenList[0].rData.result;
            if(tools.isNull(result)==true && result.length>0){
              let regionLimitStatus = result[0].regionLimitStatus;
              if(tools.install(regionLimitStatus)){
                let value1 = tools.getDkeyValue(this.whetherOptions, regionLimitStatus);
                this.dgForm.te3.value1 = value1;
                if(regionLimitStatus=='1'){
                  this.addActiveNames("3","te3");
                }
              }
            }
          }
          this.addPercentage(10);//进度条
        })
      },

    //物联卡区域限制区域查询
      queryRLArea(){
        querySimRLArea(this.synCommonParameter()).then(response => {
          let retuenList = this.synCommonFeedback(response);
          if(tools.isNull(retuenList)){
            let result= retuenList[0].rData.result;
            if(tools.isNull(result)==true && result.length>0){
              let restrictedArea = result[0].restrictedArea;
              if(tools.install(restrictedArea)){
                this.restrictedArea = restrictedArea;
              }
            }
          }
          this.addPercentage(10);//进度条
        })
      },

      //物联卡机卡分离状态查询
      queryBindStatus(){
        queryCardBindStatus(this.synCommonParameter()).then(response => {
          let retuenList = this.synCommonFeedback(response);
          if(tools.isNull(retuenList)){
            let result= retuenList[0].rData.result;
            if(tools.isNull(result)==true && result.length>0){
              let bStats = result[0].result;
              let sepTime = result[0].sepTime;
              let value2 = tools.getDkeyValue(this.ecv5BindStatus, bStats);
              if(tools.isNull(sepTime)){
                value2 +="["+sepTime+"]"
              }
              this.dgForm.te3.value2 = value2;
            }
          }
          this.addPercentage(10);//进度条
        })
      },
      //单卡状态变更历史查询
      queryChangeHist(){
        querySimChangeHistory(this.synCommonParameter()).then(response => {
          let retuenList = this.synCommonFeedback(response);
          if(tools.isNull(retuenList)){
            let result= retuenList[0].rData.result;
            if(tools.isNull(result)==true && result.length>0){
              let changeHistoryList = result[0].changeHistoryList;
              if(tools.isNull(changeHistoryList)){
                changeHistoryList =  tools.listMapSort(changeHistoryList,['changeDate'],'asc');
                changeHistoryList.sort((a, b) => {//依据时间字段排序
                  const dateA = new Date(a.changeDate);
                  const dateB = new Date(b.changeDate);
                  return dateA - dateB;
                });
                this.dgForm.cardApiBusinessList = changeHistoryList;
                if(changeHistoryList[changeHistoryList.length-1].targetStatus=='4'){
                  this.addActiveNames("4","te4");
                }
              }
            }
          }
          this.addPercentage(10);//进度条
        })
      },

      //进度条增长
      addPercentage(val){
        this.percentage = tools.accAdd(this.percentage,val);
        if(this.percentage==100){//诊断完成
            this.dgForm.iClass = 'el-icon-circle-check';
            this.dgForm.iColor = this.color.green;
            this.dgForm.iTitle = this.lgCode.dTitle.i_2;
            //诊断完成之后将诊断记录储存至数据库 【待完善】
            this.$emit("setObj", "setDiagnosisLoading", false);//取消只读

        }
      },


      //停机原因解析（这里不使用国际化语言配置 ）
      causeAnalysis(stopReason){
        let value2 = "";
        if(stopReason.substring(0,1)=="2"){
          value2 += " + 窄带网商品到期失效停机";
        }
        if(stopReason.substring(1,2)=="2"){
          value2 += " + 机卡分离停机";
        }
        if(stopReason.substring(3,4)=="2"){
          value2 += " + M2M 管理停机";
        }
        if(stopReason.substring(4,5)=="2"){
          value2 += " + 信控双向停机";
        }
        if(stopReason.substring(8,9)=="2"){
          value2 += " + 主动（申请）双向停机";
        }
        if(stopReason.substring(9,10)=="2"){
          value2 += " + 区域限制（位置固定）管理型停机";
        }
        if(stopReason.substring(10,11)=="2"){
          value2 += " + 强制双向停机";
        }
        value2 = value2.substring(0,3)==" + "?value2.substring(3,value2.length):value2;
        return value2;
      },



      //同步公共返回 反馈
      synCommonFeedback(response){
        let jsonObj = JSON.parse(tools.Decrypt(response));
        let msg = jsonObj.msg;
        if (jsonObj.code == 200) {
          if(tools.isNull(jsonObj.data) && tools.isNull(jsonObj.data.retuenList) && jsonObj.data.retuenList.length>0) {
            return jsonObj.data.retuenList;
          }
        } else {
          this.$message.error(msg);
        }
      },
      //同步公共 参数加密
      synCommonParameter(){
        let map = {};
        map.iccid = this.sel.iccid;
        let pwdStr = tools.encryptSy(map);
        return pwdStr;
      },



    },
  }
</script>




