<template>
  <div>
    <div class="main">
      <div style="float: right;margin-top: -35px;margin-right: 50px;">
        <router-link  v-hasPermi="['iotos:card:diagnosis']" style="margin-right: 10px;"
           v-if="showDiagnosis" :to="'/connect/diagnosis/'+ tools.encryptSy({'value':sel.iccid})">
            <el-button  size="mini" type="primary" @click="closeShow">{{ $t("diagnosis.oneDiagnosis") }}</el-button>
        </router-link>
        <router-link  v-hasPermi="['iotos:channel:list']" style="margin-right: 10px;"
                      v-if="showDiagnosis" :to="'/connect/channel/'+ tools.encryptSy({'value':sel.channel_id,'type':'2'})">
          <el-button  size="mini" type="primary" @click="closeShow" >{{ $t("cardInfoDetails.tFrom.channel_id") }}</el-button>
        </router-link>
        <el-button  size="mini" type="primary" @click="copyCardInfo">{{ $t("cardInfoDetails.copyCardInfo") }}</el-button>
        <el-button  size="mini" type="primary" @click="edieOpen">{{ $t("common.edie") }}</el-button>


      </div>
        <el-table
          :data="tablefrom"
          :show-header="false"
          border>
          <el-table-column prop="label1" >
            <template slot-scope="scope" >
              <b >{{ scope.row.label1 }}</b>
            </template>
          </el-table-column>
          <el-table-column prop="value1" width="135px"/>
          <el-table-column prop="label2">
            <template slot-scope="scope">
              <b>{{ scope.row.label2 }}</b>
            </template>
          </el-table-column>
          <el-table-column prop="value2" width="160px"/>
          <el-table-column prop="label3">
            <template slot-scope="scope">
              <b>{{ scope.row.label3 }}</b>
            </template>
          </el-table-column>
          <el-table-column prop="value3"/>
          <el-table-column prop="label4">
            <template slot-scope="scope">
              <b>{{ scope.row.label4 }}</b>
            </template>
          </el-table-column>
          <el-table-column prop="value4" width="135px"/>
          <el-table-column prop="label5">
            <template slot-scope="scope">
              <b>{{ scope.row.label5 }}</b>
            </template>
          </el-table-column>
          <el-table-column prop="value5"/>
        </el-table>

        <el-table
          :data="tablefrom_2"
          :show-header="false"
          border>
          <el-table-column prop="label1" width="285px">
            <template slot-scope="scope">
              <b>{{ scope.row.label1 }}</b>
            </template>
          </el-table-column>
          <el-table-column prop="value1"/>
          <el-table-column prop="label2"width="285px">
            <template slot-scope="scope">
              <b>{{ scope.row.label2 }}</b>
            </template>
          </el-table-column>
          <el-table-column prop="value2"/>

        </el-table>



    </div>
    <!-- 触发加载数据函数-->
    <span v-if="show_Details ==true && detailsEditexecute == false">
        {{ LoadEx() }}
    </span>


  </div>
</template>

<script>
  import {
    getCard
  } from "@/api/iotos/connect/card";
  import tools from "@/utils/iotos/tools";


  export default {
    props: {
      setObj: Function,//父组件赋值 函数
      detailsEditexecute: Boolean,//加载是否 已执行
      show_Details: Boolean,//当前界面是否显示
      sel: Object,
      deptsOptions: Array,//企业信息
      cardStatusShowOptions: Array,//卡状态
      channelOptions: Array,//通道
      showDiagnosis: {
        type: Boolean,
        default: false
      },//诊断按钮 是否显示

    },
    name: "cardInfoDetails",
    data() {
      return {

        tablefrom_2: [],//备注信息
        headquarters: false,//总部
        tools: tools,
        tablefrom:[],
        stateOptions:[],
        cardTypeOptions:[],//卡类型
        cardNetworkTypeOptions:[],//卡网络类型
        whetherOptions:[],//自定义是否
        cardConnectionStatusOptions:[],//断开网状态
        cardStatusOptions:[],//卡状态描述

        dsUpdForm:{
          remarks:null,
          updateNotFilled:'0',
          delivery_date:null,
          imei:null,
          customize_grouping:null,
          iccid:null,
        },


      }
    },
    created() {



      //加载 卡类型
      if (tools.isNull(window['cardTypeOptions'])) {
        this.cardTypeOptions = window['cardTypeOptions'];
      } else {
        this.getDicts("card_type").then(response => {
          window['cardTypeOptions'] = response.data;
          this.cardTypeOptions = window['cardTypeOptions'];
        });
      }

      //加载 卡网络类型
      if (tools.isNull(window['cardNetworkTypeOptions'])) {
        this.cardNetworkTypeOptions = window['cardNetworkTypeOptions'];
      } else {
        this.getDicts("card_network_type").then(response => {
          window['cardNetworkTypeOptions'] = response.data;
          this.cardNetworkTypeOptions = window['cardNetworkTypeOptions'];
        });
      }

      //加载 是否
      if (tools.isNull(window['whetherOptions'])) {
        this.whetherOptions = window['whetherOptions'];
      } else {
        this.getDicts("iotos_whether").then(response => {
          window['whetherOptions'] = response.data;
          this.whetherOptions = window['whetherOptions'];
        });
      }

      //加载 断开网状态
      if (tools.isNull(window['cardConnectionStatusOptions'])) {
        this.cardConnectionStatusOptions = window['cardConnectionStatusOptions'];
      } else {
        this.getDicts("card_connection_status").then(response => {
          window['cardConnectionStatusOptions'] = response.data;
          this.cardConnectionStatusOptions = window['cardConnectionStatusOptions'];
        });
      }



      //加载 卡状态描述
      if (tools.isNull(window['cardStatusOptions'])) {
        this.cardStatusOptions = window['cardStatusOptions'];
      } else {
        this.getDicts("card_status_id").then(response => {
          window['cardStatusOptions'] = response.data;
          this.cardStatusOptions = window['cardStatusOptions'];
        });
      }


    },
    methods: {

      LoadEx() {
        this.$emit("setObj", "setDetailsEditexecute", true);//已加载
        this.attributionInfoList = [];//归属信息
        this.finddata(this.sel);
      },


      /*获取单卡详细信息*/
      finddata(row) {
        this.tablefrom = [];//清空数据
        this.tablefrom_2 = [];//清空数据

        this.dsUpdForm={
          remarks:null,
            updateNotFilled:'0',
            delivery_date:null,
            imei:null,
            customize_grouping:null,
            iccid:null,
        };



        let map = {};
        map.c_no = row.c_no;
        let pwdStr = tools.encryptSy(map);
        getCard(pwdStr).then(response => {
            let jsonObj = JSON.parse(tools.Decrypt(response));
            this.headquarters = jsonObj.deptId == '100' ? true : false;

            if (jsonObj.code == 200) {
              row = jsonObj.data;
              let sel = {"iccid":row.iccid,"c_used":row.c_used,"c_left":row.c_left,"first_time_using":row.first_time_using,"channel_id":row.channel_id};
              this.$emit("setObj", "setSel", sel);//更新选中数据

              this.tablefrom.push({"label1":"MSISDN","value1":row.msisdn,"label2":"ICCID","value2":row.iccid,"label3":"IMSI","value3":row.imsi,"label4":"IMEI","value4":row.imei,"label5":this.$t("cardInfoDetails.tFrom.w_polling"),"value5":tools.getDkeyValue(this.whetherOptions, row.w_polling)});
              let map1 = {"label1":this.$t("cardInfoDetails.tFrom.operator"),"value1":tools.getVal(row.operator),"label2":this.$t("cardInfoDetails.tFrom.roaming_country"),"value2":row.roaming_country,
                "label3":this.$t("cardInfoDetails.tFrom.internet_signal"),"value3":row.internet_signal,"label4":this.$t("cardInfoDetails.tFrom.ip_attribution"),"value4":row.ip_attribution,
                "label5":this.$t("cardInfoDetails.tFrom.shutdown_threshold"),"value5":row.shutdown_threshold};






              let map2 = {},map3 = {},map4 = {},map5 = {},map6 = {},map7 = {};

              map2.label1 = this.$t("cardInfoDetails.tFrom.status_show_id");
              map2.value1 = tools.getDkeyValue(this.cardStatusShowOptions, row.status_show_id);
              map2.label2 = this.$t("cardInfoDetails.tFrom.type");
              map2.value2 = tools.getDkeyValue(this.cardTypeOptions, row.type);
              map2.label3 = this.$t("cardInfoDetails.tFrom.network_type");
              map2.value3 = tools.getDkeyValue(this.cardNetworkTypeOptions, row.network_type);
              map2.label4 = this.$t("cardInfoDetails.tFrom.nedd_real_name")+" ["+tools.getDkeyValue(this.whetherOptions, row.nedd_real_name)+"]";
              map2.value4 = this.$t("cardInfoDetails.tFrom.w_real_name")+" ["+tools.getDkeyValue(this.whetherOptions, row.w_real_name)+"]";
              map2.label5 = this.$t("cardInfoDetails.tFrom.connection_status");
              map2.value5 = tools.getDkeyValue(this.cardConnectionStatusOptions, row.connection_status);




              map3.label1 = this.$t("cardInfoDetails.tFrom.deliver_date");
              map3.value1 = row.deliver_date;
              map3.label2 = this.$t("cardInfoDetails.tFrom.activate_date");
              map3.value2 = row.activate_date;
              map3.label3 = this.$t("cardInfoDetails.tFrom.traffic_sync_time");
              map3.value3 = row.traffic_sync_time;
              map3.label4 = this.$t("cardInfoDetails.tFrom.state_sync_time");
              map3.value4 = row.state_sync_time;
              map3.label5 = this.$t("cardInfoDetails.tFrom.w_network_break");
              map3.value5 = tools.getDkeyValue(this.whetherOptions, row.w_network_break);




              map4.label1 = this.$t("common.belongsTo");
              map4.value1 = tools.getkeyValue(deptsOptions, row.dept_id,"dept_id","dept_name");
              map4.label2 = this.$t("cardInfoDetails.tFrom.channel_id");
              map4.value2 = tools.getDkeyValue(channelOptions, row.channel_id);
              map4.label3 = this.$t("cardInfoDetails.tFrom.package_id");
              map4.value3 = row.package_id;
              map4.label4 =this.$t("cardInfoDetails.tFrom.u_code");
              map4.value4 = row.u_code;
              map4.label5 = this.$t("cardInfoDetails.tFrom.w_pool");
              map4.value5 = tools.getDkeyValue(this.whetherOptions, row.w_pool)+ " ["+tools.getVal(row.pool_code)+"]";





              map5.label1 = this.$t("cardInfoDetails.tFrom.balance")+ " ["+tools.getVal(row.balance)+"]";
              map5.value1 = this.$t("cardInfoDetails.tFrom.payment_key")+ " ["+tools.getVal(row.payment_key)+"]";
              map5.label2 = this.$t("cardInfoDetails.tFrom.automatic_renewal");
              map5.value2 = tools.getDkeyValue(this.whetherOptions, row.automatic_renewal);
              map5.label3 = this.$t("cardInfoDetails.tFrom.w_sms");
              map5.value4 = tools.getDkeyValue(this.whetherOptions, row.w_sms)+" ["+row.sms_number+"]";
              map5.label4 = this.$t("cardInfoDetails.tFrom.w_voice");
              map5.value4 = tools.getDkeyValue(this.whetherOptions, row.w_voice);
              map5.label5 = this.$t("cardInfoDetails.tFrom.prodinsteff_time")+ " ["+tools.getVal(row.prodinsteff_time)+"]";
              map5.value5 = this.$t("cardInfoDetails.tFrom.prodinstexp_time")+ " ["+tools.getVal(row.prodinstexp_time)+"]";


              this.tablefrom_2.push({"label1":this.$t("cardInfoDetails.tFrom.customize_grouping"),"value1":row.customize_grouping,"label2":this.$t("cardInfoDetails.tFrom.remarks"),"value2":row.remarks});

              if(this.headquarters){
                map2.value1 += " [" + tools.getDkeyValue(this.cardStatusOptions, row.status_id) + "]";
                map1.value1 += tools.isNull( row.supplier)?" 供应商 [" + row.supplier + "]":"";

                this.tablefrom_2.push({"label1":this.$t("cardInfoDetails.tFrom.storage_date")+" ["+tools.getVal(row.storage_date)+"]","value1":this.$t("cardInfoDetails.tFrom.delivery_date")+" ["+tools.getVal(row.delivery_date)+"]"
                  ,"label2":this.$t("cardInfoDetails.tFrom.create_time")+" ["+tools.getVal(row.create_time)+"]","value2":this.$t("cardInfoDetails.tFrom.first_time_using")+" ["+tools.getVal(row.first_time_using)+"]"});

              }
              this.tablefrom.push(map1);
              this.tablefrom.push(map2);
              this.tablefrom.push(map3);
              this.tablefrom.push(map4);
              this.tablefrom.push(map5);

              this.dsUpdForm.iccid = row.iccid;
              this.dsUpdForm.remarks = tools.getValDef(row.remarks,null);
              this.dsUpdForm.delivery_date = tools.getValDef(row.delivery_date,null);
              this.dsUpdForm.imei = tools.getValDef(row.imei,null);
              this.dsUpdForm.customize_grouping = tools.getValDef(row.customize_grouping,null);

            } else {
              this.$message.error(jsonObj.msg);
            }
            this.loading = false;
          }
        );

      },

      //打开父级 编辑组件
      edieOpen(){

        this.$emit("setObj", "openEditShow", this.dsUpdForm);//已加载
      },
      //关闭当前窗口
      closeShow(){
        this.$emit("setObj", "setShowDs",false);
      },

      //复制 基础信息
      copyCardInfo() {
        let copyText = "";
        for (let i = 0; i < this.tablefrom.length; i++) {
          let obj = this.tablefrom[i];
          let value1 = tools.getVal(obj.value1);
          let value2 = tools.getVal(obj.value2);
          let value3 = tools.getVal(obj.value3);
          let value4 = tools.getVal(obj.value4);
          let value5 = tools.getVal(obj.value5);
          copyText += obj.label1+" : "+value1+" , "+obj.label2+" : "+value2+" , "+obj.label3+" : "+value3+" , "+obj.label4+" : "+value4+" , "+obj.label5+" : "+value5;
          copyText +="\n";
        }
        for (let i = 0; i < this.tablefrom_2.length; i++) {
          let obj = this.tablefrom_2[i];
          copyText += obj.label1+" : "+tools.getVal(obj.value1)+" , "+obj.label2+" : "+tools.getVal(obj.value2);
          copyText +="\n";
        }
        let _this = this;
        tools.copyThat(copyText,_this);
      },

    },
  }
</script>




