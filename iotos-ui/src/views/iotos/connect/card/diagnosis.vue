<template>
  <div class="app-container" >

    <el-input
      v-model="queryParams.value"
      :placeholder="$t('common.queryValue')"
      size="small"
      style="width: 350px"
      @keyup.enter.native="oneDiagnosis"
    >
      <el-select
        v-model="queryParams.type"
        :placeholder="$t('common.queryConditions')"
        clearable
        slot="prepend"
        style="width: 110px"
      >
        <el-option
          v-for="dict in typeOptions"
          :key="dict.dictValue"
          :label="dict.dictLabel"
          :value="dict.dictValue"
        />
      </el-select>
    </el-input>

    <el-button style="margin-left: 10px;" type="primary"  size="mini" @click="oneDiagnosis" :disabled="diagnosisLoading">
      <svg-icon icon-class="diagnosis"  />
      {{ lgCode.oneDiagnosis }}
    </el-button>
    <el-button type="primary"  size="mini" @click="cardMatching" :disabled="diagnosisLoading">
      <svg-icon icon-class="match"  />
      {{ lgCode.cardMatching }}
    </el-button>

 <div v-if="matchShow" style="margin-top: 20px;">
    <div>
      <span > {{  lgCode.prefixMatching }}：</span>
      <span style="font-size: 23px;width: 150px;"> <span class="lightGreen">{{ match.px.prefix }}</span> *</span>
      <span style="margin-left: 40px;">{{  lgCode.sum }} <span>{{ match.px.count }}</span> {{  lgCode.articleData }}</span>
    </div>
    <div style="margin-top: 10px;">
      <span >  {{  lgCode.referenceNumber }}：</span>
      <span style="font-size: 23px;"><span class="lightGreen">{{ match.px.prefix  }}</span>{{ match.px.value }}</span>
      <el-button type="primary" v-if="tools.isNull(match.px.prefix)" style="margin-left: 20px" size="mini" @click="fillInDiagnosis" >
        {{ lgCode.diagnosis }}
      </el-button>
      <span style="margin-left: 40px;">  {{  lgCode.traffic_sync_time }}：{{ match.px.traffic_sync_time }}</span>
    </div>
  </div>



<!-- 基础信息 子组件-->
      <cardInfoDetails style="margin-top: 10px;" v-show="show_ds" ref="cardInfoDetails"
                       @setObj="setObj"  :detailsEditexecute="detailsEditexecute" :show_Details="show_ds"   :sel="sel"
                       :deptsOptions="deptsOptions" :cardStatusShowOptions="cardStatusShowOptions"   :channelOptions="channelOptions"
      />

    <el-tabs tab-position="left" style="" v-model="activeTabs" v-show="show_tabs"  @tab-click="handleClick">
          <el-tab-pane label="API诊断" name="apiDs">
              <dsEcV5 :v-if="cnTemplate=='oneLink_ECV5'" ref="cardInfoModule" @setObj="setObj"  :dsEditexecute="dsEditexecute" :show_Details="show_apiDs"   :sel="sel" />
          </el-tab-pane>
          <el-tab-pane label="平台信息" name="iMe">
            <!-- 拓展模块 信息 子组件-->
            <cardInfoModule ref="cardInfoModule" @setObj="setObj"  :moduleEditexecute="moduleEditexecute" :show_Details="show_iMe"   :sel="sel"/>
          </el-tab-pane>
    </el-tabs>


    <el-empty v-show="show_empty" :description="lgCode.empty"></el-empty>

    <!-- 修改卡信息-->
    <el-dialog  :title="$t('card_index.editCard')" :visible.sync="edit_show" width="600px" :close-on-click-modal="false" append-to-body>
      <editCard ref="editCard"  @setObj="setObj" :wUpdBatch="wUpdBatch" :edit_show="edit_show"
                :whetherOptions="whetherOptions" :updForm="updForm" :deliveryOptions="deliveryOptions"
      />
    </el-dialog>


  </div>
</template>

<script>
import {
  findCard,
  cardMatch,
} from "@/api/iotos/connect/card";
  import tools from "@/utils/iotos/tools";
  import {getDeptName} from "@/api/system/dept";
  import cardInfoDetails from "./cardInfoDetails";
  import cardInfoModule from "./cardInfoModule";
  import editCard from "./components/editCard";
  import dsEcV5 from "./components/dsEcV5";



  export default {
    name: "diagnosis",
    components: {
      cardInfoDetails,
      cardInfoModule,
      editCard,
      dsEcV5,
    },
    data() {
      return {
        tools:tools,
        matchShow:false,
        match:{
          px:{
            count:'0',
            value:'',
            prefix:'',
            traffic_sync_time:'',
          },
        },
        // 查询参数
        queryParams: {
          type: "1",
          value: null,
        },
        typeOptions:[],
        lgCode:{
          cardMatching:this.$t('diagnosis.cardMatching'),
          oneDiagnosis:this.$t('diagnosis.oneDiagnosis'),
          empty:this.$t('diagnosis.empty'),
          notNull:this.$t('common.notNull'),
          traffic_sync_time:this.$t('cardInfoDetails.tFrom.traffic_sync_time'),
          referenceNumber:this.$t('diagnosis.referenceNumber'),
          prefixMatching:this.$t('diagnosis.prefixMatching'),
          articleData:this.$t('common.articleData'),
          sum:this.$t('common.sum'),
          diagnosis:this.$t('diagnosis.diagnosis'),

        },
        sel:{//选中数据
          c_no:''
        },
        renew_show:true,
        detailsEditexecute:true,
        show_ds:false,
        show_iMe:false,
        show_apiDs:false,
        show_tabs:false,
        activeTabs:'apiDs',
        show_empty:false,
        deptsOptions: [],//企业数组
        cardStatusShowOptions: [],
        channelOptions: [],//通道
        moduleEditexecute: true,//信息拓展模块 是否已加载
        dsEditexecute: true,//信息拓展模块 是否已加载
        diagnosisLoading: false,
        updForm:{
          remarks:null,
          updateNotFilled:'0',
          delivery_date:null,
          imei:null,
          customize_grouping:null,
          iccid:null,
        },
        edit_show: false,//编辑卡信息 展示
        wUpdBatch: false,//是否批量操作
        whetherOptions: [],//是否
        deliveryOptions: tools.getDeliveryOptions(this),
        cnTemplate:'',//通道类型
      }
    },
    created: function () {
      let str = this.$route.params;
      //console.log(str)

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
      //加载 卡状态
      if (window['cardStatusShowOptions'] != undefined && window['cardStatusShowOptions'] != null && window['cardStatusShowOptions'] != '') {
        this.cardStatusShowOptions = window['cardStatusShowOptions'];
      } else {
        this.getDicts("card_status_show_id").then(response => {
          window['cardStatusShowOptions'] = response.data;
          this.cardStatusShowOptions = window['cardStatusShowOptions'];
        });
      }
      //加载 是否
      if (window['whetherOptions'] != undefined && window['whetherOptions'] != null && window['whetherOptions'] != '') {
        this.whetherOptions = window['whetherOptions'];
      } else {
        this.getDicts("iotos_whether").then(response => {
          window['whetherOptions'] = response.data;
          this.whetherOptions = window['whetherOptions'];
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
      //加载 智能诊断查询条件
      if (window['diagnosisOptions'] != undefined && window['diagnosisOptions'] != null && window['diagnosisOptions'] != '') {
        this.typeOptions = window['diagnosisOptions'];
      } else {
        this.getDicts("diagnosis_sel_type").then(response => {
          window['diagnosisOptions'] = response.data;
          this.typeOptions = window['diagnosisOptions'];
        });
      }
      //触发诊断
      if(tools.isNull(str)==true && tools.isNull(str.pwdStr)==true){
          let jsonObj = JSON.parse(tools.Decrypt(str.pwdStr));
          if(tools.isNull(jsonObj.value)){
            this.queryParams.value = jsonObj.value;
            this.oneDiagnosis();
          }
      }


    },

    methods: {

      //载入 一键诊断
      fillInDiagnosis(){
        this.queryParams.value = JSON.parse(JSON.stringify(this.match.px.prefix+this.match.px.value));
        this.oneDiagnosis();
      },



      //一键诊断
      oneDiagnosis(){
        let _this = this;
        this.show_ds = false;
        this.show_tabs = false;
        this.show_apiDs = false;
        this.activeTabs = 'apiDs';
        this.matchShow = false;

        if(tools.VerificationsText(_this, _this.queryParams.value, this.$t("diagnosis.rs.value")+this.lgCode.notNull) == true){
          this.diagnosisLoading = true;
          this.show_empty = false;
          let map = {};
          map.type = "0";
          map.value = this.queryParams.value;
          let pwdStr = tools.encryptSy(map);
          findCard(pwdStr).then(response => {
              let jsonObj = JSON.parse(tools.Decrypt(response));
              if (jsonObj.code == 200) {
                let row = jsonObj.data;
                if(tools.isNull(row)){
                  this.show_ds = true;
                  this.show_tabs = true;
                  this.show_apiDs = true;
                  this.detailsEditexecute = false;
                  this.dsEditexecute = false;

                  this.sel.iccid = row.iccid;
                  this.sel.c_no = row.c_no;
                  this.sel.c_left = row.c_left;
                  this.sel.c_used = row.c_used;
                  this.sel.channel_id = row.channel_id;
                }else {
                  this.show_empty = true;
                  this.diagnosisLoading = false;
                }
              } else {
                this.$message.error(jsonObj.msg);
              }


            });

        }
      },
      //卡号匹对
      cardMatching(){
        let _this = this;
        this.show_ds = false;
        this.show_tabs = false;
        this.show_apiDs = false;
        this.show_empty = false;

        if(tools.VerificationsText(_this, _this.queryParams.value, this.$t("diagnosis.rs.value")+this.lgCode.notNull) == true){
          if(_this.queryParams.value.length>4){
            //重置查询条件
            this.matchShow = false;
            this.match = {
              px:{
                count:'0',
                value:'',
                traffic_sync_time:'',
              },
            };

            let pwdStr = tools.encryptSy(this.queryParams);
            cardMatch(pwdStr).then(response => {
              let jsonObj = JSON.parse(tools.Decrypt(response));
              if (jsonObj.code == 200) {
                let data = jsonObj.data;
                if(tools.isNull(data)){
                  _this.match.px.cardCount = data.cardCount;
                  if(data.cardCount>0){
                    let row = data.matchMap;
                    this.sel.iccid = row.iccid;
                    _this.match.px.count = data.cardCount;
                    _this.match.px.value = row.msisdn.substring(data.prefix.length);
                    _this.match.px.prefix = data.prefix;
                    _this.match.px.traffic_sync_time = row.traffic_sync_time;
                    _this.matchShow = true;
                    this.show_ds = true;
                    this.detailsEditexecute = false;
                    this.sel.iccid = row.iccid;
                    this.sel.c_no = row.c_no;
                    this.sel.channel_id = row.channel_id;
                  }else{
                    _this.show_empty = true;
                    _this.diagnosisLoading = false;
                  }
                }else {
                  this.show_empty = true;
                  this.diagnosisLoading = false;
                }
              } else {
                this.$message.error(jsonObj.msg);
              }


            });


          }else {
            tools.MessageShow(_this,this.$t('diagnosis.rs.length'));
          }
        }
      },
      //提供给子组件 修改父组件属性
      setObj(Key, obj) {
        //console.log(obj);
        switch (Key) {
          case 'setRenew_wExecute':
            this.renew_wExecute = obj;//设置  更新卡列表  是否已经加载
            break;
          case 'setRenew_show':
            this.renew_show = obj;//设置  更新卡列表  是否显示
            break;
          case 'setDetailsEditexecute':
            this.detailsEditexecute = obj;//设置  基础信息  是否已经加载
            break;
          case 'setModuleEditexecute':
            this.moduleEditexecute = obj;//设置  信息拓展模块  是否已经加载
            break;
          case 'setSel':
            this.sel = obj;
            break;
          case 'setIMEI_wExecute':
            this.IMEI_wExecute = obj;//设置  查询IMEI  是否已经加载
            break;
          case 'setIMEI_show':
            this.IMEI_show = obj;//设置  查询IMEI  是否显示
            break;
          case 'openEditShow':
            this.edit_show = true;
            this.wUpdBatch = false;
            this.updForm = obj;
            break;
          case 'setEditShow':
            this.edit_show = obj;
            break;
          case 'setDsEditexecute':
            this.dsEditexecute = obj;//设置  API诊断  是否已经加载
            break;
          case 'setCnTemplate':
            this.cnTemplate = obj;//设置  通道模板
            break;
          case 'setDiagnosisLoading':
            this.diagnosisLoading = obj;//更新 只读状态
            break;
        }
      },

      handleClick(tab, event) {
        //console.log(tab, event);
        let name = tab.name;
        switch (name){
          case 'apiDs':
            this.show_apiDs = true;
            break;
          case 'iMe':
            this.show_iMe = true;
            this.moduleEditexecute = false;
            break;
        }


      },

    },
  }
</script>


<style  scoped>
.header-icon{
  font-size: 20px;
  margin-right: 10px;
}
.drow{
  padding: 5px 10px 0px 10px;
}
</style>

