<template>
  <div>

              <el-form  label-width="120px">
                <el-form-item >
                  <span slot="label">
                    {{ $t('common.numberType') }}
                    <el-tooltip placement="right">
                      <div slot="content">
                        {{ $t('cardInfoRenew.tip.number') }}
                      </div>
                      <i class="el-icon-question"></i>
                    </el-tooltip>
                  </span>
                    <el-select
                      v-model="updFome.batchType"
                      :placeholder="$t('common.pleaseChoose')+$t('common.numberType')"
                      clearable
                      size="small"
                      @change="formChange()"
                      style="width: 200px"
                    >
                      <el-option
                        v-for="dict in numberTypeOptions"
                        :key="dict.dictValue"
                        :label="dict.dictLabel"
                        :value="dict.dictValue"
                      />
                    </el-select>
                </el-form-item>




                <el-form-item>
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
                <el-form-item>
                  <div style="float: right" >
                    <el-button type="primary" v-if="submitFileFormBtn" @click="submitFileForm">{{ $t('common.sure') }}</el-button>
                    <el-button @click="closePage">{{ $t('common.cancel') }}</el-button>
                  </div>
                </el-form-item>
              </el-form>
    <!-- 触发加载数据函数-->
    <span v-if="renew_show ==true && renew_wExecute == false">
        {{ LoadEx() }}
    </span>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";
import tools from "@/utils/iotos/tools";

export default {
  props: {
    setObj: Function,//父组件赋值 函数
    renew_wExecute: Boolean,// 加载时 是否 已执行
    renew_show: Boolean,//当前界面是否显示
    numberTypeOptions:Array,//卡号类型
  },

  name: "cardInfoRenew",
  data() {
    return {
      submitFileFormBtn:true,//导入 btn
      updFome:{
        batchType:'',//修改依据
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
        url: process.env.VUE_APP_BASE_API + "/iotos/card/edit",
        pwdStr:'',//携带参数
      },


    };
  },

  created() {

  },
  methods: {

    LoadEx() {
      this.$emit("setObj", "setRenew_wExecute", true);//已加载

    },

    closePage(){
      this.$emit("setObj", "setRenew_show", false);
    },



    formChange(){
      this.getPwdStr();
    },
    // 加密数据
    getPwdStr(){
      let pwdStr = tools.encrypt(JSON.stringify(this.updFome));
      this.upload.pwdStr = pwdStr;
    },


    /** 下载模板操作 */
    importTemplate() {
      tools.downloadTemplate("/getcsv/updateCard.xls");
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
      this.$emit("setObj", "setRenew_show", false);//成功后 关闭界面
    },
    // 提交上传文件
    submitFileForm() {
      var files = this.$refs.upload.uploadFiles;//判断files数组的长度是否大于0，不大于0 则未选择附件
      if(files.length>0){
        if(tools.VerificationsText(this, this.updFome.batchType, this.$t("cardInfoRenew.rs.batchType")) == true ){
          tools.openAsk(this,'warning', this.$t("cardInfoRenew.ask.upd"), this.uploadUp,this, this.FalseFun,null);
        }
      }else{
        tools.open(this,this.$t("cardInfoRenew.rs.file"));
      }
    },



    uploadUp(_this){
      _this.$refs.upload.submit();
    },

    FalseFun(pwdStr){
      this.submitFileFormBtn = true;
    },








  }
};
</script>

