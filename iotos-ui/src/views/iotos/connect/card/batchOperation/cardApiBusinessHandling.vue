<template>
  <div>

              <el-form  label-width="120px">


                <el-form-item v-if="subForm.executionType=='fileUpload'">
                  <el-upload
                    ref="upload"
                    :limit="1"
                    accept=".xlsx, .xls"
                    :headers="upload.headers"
                    :action="upload.url"
                    :disabled="upload.isUploading"
                    :on-progress="handleFileUploadProgress"
                    :on-success="handleFileSuccess"
                    :auto-upload="false"
                    :data="{pwdStr:this.upload.pwdStr}"
                    drag
                  >
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">
                      {{ $t('common.uploadText') }}
                      <em>{{ $t('common.uploadTextEm') }}</em>
                    </div>
                    <div class="el-upload__tip" slot="tip">
                      <el-link type="info" style="font-size:18px;color: #03A9F4;vertical-align: baseline;" @click="importTemplate">{{ $t('common.downloadTemplate') }}</el-link>
                    </div>
                    <div class="el-upload__tip" style="color:red" slot="tip">{{ $t('common.uploadTip') }}</div>
                  </el-upload>
                </el-form-item>
                <el-form-item v-if="subForm.executionType=='textField'">
                  <el-input  v-model="subForm.iccids" @blur="vICCIDS" type="textarea" :placeholder="$t('cardApiBusinessHandling.tip.textareaP')"></el-input>
                </el-form-item>
                  <el-form-item  :label="$t('cardApiBusinessHandling.gprsReset')">
                    <el-radio-group>
                      <label class="el-radio " v-for="(item,index) in executionTypeOptions">
                                        <span class="el-radio__input my_checkbox__inner">
                                        <input type="radio" v-model="subForm.executionType"
                                               name="executionType"
                                               @change="changeExecutionType"
                                               :value="item.dictValue"/>
                                         </span>
                        <span class="el-radio__label ">{{item.dictLabel}}</span>
                      </label>
                    </el-radio-group>
                  </el-form-item>
                <el-tabs v-model="activeName"  @tab-click="handleClick">

                  <el-tab-pane :label="$t('cardApiBusinessHandling.openClose')" name="openClose">
                    <el-form-item style="margin: auto">
                      <span slot="label">
                        {{ $t('cardApiBusinessHandling.stop') }}
                        <el-tooltip placement="right">
                          <div slot="content">
                            {{ $t('cardApiBusinessHandling.tip.openStop') }}
                          </div>
                          <i class="el-icon-question"></i>
                        </el-tooltip>
                      </span>
                          <el-radio-group>
                            <label class="el-radio " v-for="(item,index) in cardOpenStopOptions">
                                        <span class="el-radio__input my_checkbox__inner">
                                        <input type="radio" v-model="subForm.w_openStop"
                                               name="w_openStop"
                                               @click="formChange()"
                                               :value="item.dictValue"/>
                                         </span>
                              <span class="el-radio__label ">{{item.dictLabel}}</span>
                            </label>
                          </el-radio-group>
                    </el-form-item>

                    <el-form-item style="margin: auto">
                      <span slot="label">
                        {{ $t('cardApiBusinessHandling.shutdown') }}
                         <el-tooltip placement="right">
                          <div slot="content">
                            {{ $t('cardApiBusinessHandling.tip.openClose') }}
                          </div>
                          <i class="el-icon-question"></i>
                        </el-tooltip>
                      </span>
                      <el-radio-group>
                        <label class="el-radio " v-for="(item,index) in cardOpenCloseOptions">
                                        <span class="el-radio__input">
                                        <input type="radio" v-model="subForm.w_openClose"
                                               name="w_openClose"
                                               @click="formChange()"
                                               :value="item.dictValue"/>
                                         </span>
                          <span class="el-radio__label ">{{item.dictLabel}}</span>
                        </label>
                      </el-radio-group>
                    </el-form-item>

                  </el-tab-pane>


                  <el-tab-pane :label="$t('cardApiBusinessHandling.gprsReset')" name="gprsReset">
                    <el-form-item style="margin: auto">
                      <span slot="label">
                        {{ $t('common.ask.execute') }}
                        <el-tooltip placement="right">
                          <div slot="content">
                            {{ $t('cardApiBusinessHandling.tip.reset') }}
                          </div>
                          <i class="el-icon-question"></i>
                        </el-tooltip>
                      </span>
                      <el-radio-group>
                        <label class="el-radio " v-for="(item,index) in whetherOptions">
                                        <span class="el-radio__input">
                                        <input type="radio" v-model="subForm.w_reset"
                                               name="w_reset"
                                               @click="formChange()"
                                               :value="item.dictValue"/>
                                         </span>
                          <span class="el-radio__label ">{{item.dictLabel}}</span>
                        </label>
                      </el-radio-group>
                    </el-form-item>
                  </el-tab-pane>
                  <el-tab-pane :label="$t('cardApiBusinessHandling.flexibleChange')" name="flexibleChange">
                    <el-form-item  >
                      <span slot="label">
                        {{ $t('cardApiBusinessHandling.flexibleChange') }}
                        <el-tooltip placement="right">
                          <div slot="content">
                            {{ $t('cardApiBusinessHandling.tip.flexibleChange') }}
                          </div>
                          <i class="el-icon-question"></i>
                        </el-tooltip>
                      </span>
                      <el-select
                        v-model="subForm.flexibleChange"
                        :placeholder="$t('common.pleaseChoose')"
                        @change="formChange()"
                        size="small"
                        style="width: 150px"
                      >
                        <el-option
                          v-for="dict in flexibleChangeOptions"
                          :key="dict.dictValue"
                          :label="dict.dictLabel"
                          :value="dict.dictValue"
                        />
                      </el-select>
                    </el-form-item>
                  </el-tab-pane>

                </el-tabs>



                <el-form-item>
                  <div style="float: right" >
                    <el-button type="primary" v-if="submitFileFormBtn" @click="submitFileForm">{{ $t('common.sure') }}</el-button>
                    <el-button @click="closePage">{{ $t('common.cancel') }}</el-button>
                  </div>
                </el-form-item>
              </el-form>
    <!-- 触发加载数据函数-->
    <span v-if="apiBH_show ==true && apiBH_wExecute == false">
        {{ LoadEx() }}
    </span>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import tools from "@/utils/iotos/tools";
import {textFieldHandling} from "@/api/iotos/connect/card";




export default {
  props: {
    setObj: Function,//父组件赋值 函数
    apiBH_wExecute: Boolean,// 加载时 是否 已执行
    apiBH_show: Boolean,//当前界面是否显示
  },

  name: "cardApiBusinessHandling",
  data() {
    return {
      submitFileFormBtn:true,//导入 btn
      subForm:{
        w_openStop:'0',
        w_openClose:'0',
        w_reset:'0',
        flexibleChange:'0',
        iccids:'',
        executionType:'textField'
      },
      // 导入参数
      upload: {
        // 是否显示弹出层（导入）
        open: false,
        // 弹出层标题（导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/iotos/card/businessHandling",
        pwdStr:'',//携带参数
      },


      cardOpenStopOptions:[],
      cardOpenCloseOptions:[],
      executionTypeOptions:[],
      activeName: "openClose",//默认选择
      whetherOptions:[],//自定义是否
      flexibleChangeOptions:[],//灵活变更

    };
  },

  created() {


    //加载 执行类型
    if (tools.isNull(window['executionTypeOptions'])) {
      this.executionTypeOptions = window['executionTypeOptions'];
    } else {
      this.getDicts("execution_type").then(response => {
        window['executionTypeOptions'] = response.data;
        this.executionTypeOptions = window['executionTypeOptions'];
      });
    }




    //加载 断开网操作
    if (tools.isNull(window['cardOpenCloseOptions'])) {
      this.cardOpenCloseOptions = window['cardOpenCloseOptions'];
    } else {
      this.getDicts("card_openclose").then(response => {
        window['cardOpenCloseOptions'] = response.data;
        this.cardOpenCloseOptions = window['cardOpenCloseOptions'];
      });
    }

    //加载 停复机操作
    if (tools.isNull(window['cardOpenStopOptions'])) {
      this.cardOpenStopOptions = window['cardOpenStopOptions'];
    } else {
      this.getDicts("card_openstop").then(response => {
        window['cardOpenStopOptions'] = response.data;
        this.cardOpenStopOptions = window['cardOpenStopOptions'];
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

    //加载 灵活变更
    if (tools.isNull(window['flexibleChangeOptions'])) {
      this.flexibleChangeOptions = window['flexibleChangeOptions'];
    } else {
      this.getDicts("card_flexiblechange").then(response => {
        window['flexibleChangeOptions'] = response.data;
        this.flexibleChangeOptions = window['flexibleChangeOptions'];
      });
    }


  },
  methods: {

    vICCIDS(){
      this.subForm.iccids = this.subForm.iccids.replace(/[^a-zA-Z0-9\n]/g, '');
    },


    changeExecutionType(e){
      //console.log(e.target.value)
    },


    handleClick(tab, event) {

    },


    formChange(){
      this.getPwdStr();
    },


    LoadEx() {
      this.$emit("setObj", "setApiBH_wExecute", true);//已加载

    },

    closePage(){
      this.$emit("setObj", "setApiBH_show", false)
    },




    // 加密数据
    getPwdStr(){

      let map = {};
      switch (this.activeName) {
        case 'openClose':
          map.w_openStop = this.subForm.w_openStop;
          map.w_openClose = this.subForm.w_openClose;
          break;
        case 'gprsReset':
          map.w_reset = this.subForm.w_reset;
          break;
        case 'flexibleChange':
          map.flexibleChange = this.subForm.flexibleChange;
          break;
      }
      map.activeName = this.activeName;
      //console.log(map)
      let pwdStr = tools.encryptSy(map);
      this.upload.pwdStr = pwdStr;
      return map;
    },


    /** 下载模板操作 */
    importTemplate() {
      tools.downloadTemplate("/getcsv/importICCID.xls");
    },




    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, this.$t('common.feedback'), { dangerouslyUseHTMLString: true });
      this.submitFileFormBtn = true;
      this.$emit("setObj", "setApiBH_show", false);//成功后 关闭界面
    },
    // 提交上传文件
    submitFileForm() {
      let _this = this;
      let eType = this.subForm.executionType;

      let sMap = this.getPwdStr();

        let bool = false;
        switch (this.activeName) {
          case 'openClose':
            this.subForm.w_openStop;
            this.subForm.w_openClose;
            if(this.subForm.w_openStop!=0 || this.subForm.w_openClose!=0){
              bool = true;
            }else {
              tools.open(_this,this.$t("cardApiBusinessHandling.rs.openClose"));
            }
            break;
          case 'gprsReset':
            if(this.subForm.w_reset!=0){
              bool = true;
            }else {
              tools.open(_this,this.$t("cardApiBusinessHandling.rs.gprsReset"));
            }
            break;
          case 'flexibleChange':
            if(tools.VerificationsText(_this, this.subForm.flexibleChange, this.$t("cardApiBusinessHandling.rs.flexibleChange")) == true ){
              bool = true;
            }
            break;
        }
        if(bool){
          if(eType=='textField'){
            if(tools.VerificationsText(_this, this.subForm.iccids, this.$t("cardApiBusinessHandling.rs.iccids"))){
              let onList = tools.textareaGet(_this.subForm.iccids);
              const newList = [...new Set(onList)];
              let repeatCount = onList.length>newList.length?tools.numberSub(onList.length,newList.length):0;
              if(repeatCount>0){
                this.$message.error(this.$t("common.repeat")+" [ "+repeatCount+" ] "+this.$t("common.articleData"));
              }
              if(tools.VerificationsText(_this, newList, this.$t("cardApiBusinessHandling.rs.iccids"))){
                let iccidList =[];
                for (let i = 0; i < newList.length; i++) {
                  iccidList.push({"iccid":newList[i]});
                }
                sMap.iccidList = iccidList;
                let pwdStr = tools.encryptSy(sMap);
                tools.openAsk(_this,'warning', this.$t("cardApiBusinessHandling.ask.txUpd"), this.subTextField,pwdStr, null,null);
              }
            }
          }else {
          var files = this.$refs.upload.uploadFiles;//判断files数组的长度是否大于0，不大于0 则未选择附件
            if(files.length>0){
              tools.openAsk(_this,'warning', this.$t("cardApiBusinessHandling.ask.upd"), this.uploadUp,_this, this.FalseFun,null);
            }else{
              tools.open(this,this.$t("cardApiBusinessHandling.rs.file"));
            }
          }
        }

    },

    //文本域操作提交
    subTextField(pwdStr){
      this.submitFileFormBtn = false;
      textFieldHandling(pwdStr).then(response => {
        let jsonObj = JSON.parse(tools.Decrypt(response));
        let msg = jsonObj.msg;
        if (jsonObj.code == 200) {
          this.$message.success(msg);
          this.upload.open = false;
          this.submitFileFormBtn = true;
          this.$emit("setObj", "setApiBH_show", false);//成功后 关闭界面
        } else {
          this.$message.error(msg);
        }
      })
    },

    uploadUp(_this){
      this.submitFileFormBtn = false;
      _this.$refs.upload.submit();
    },

    FalseFun(pwdStr){
      this.submitFileFormBtn = true;
    },








  }
};
</script>

