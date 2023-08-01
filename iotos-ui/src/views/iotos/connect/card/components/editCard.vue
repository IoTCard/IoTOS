<template>
  <div>
    <el-form ref="form" :model="updForm" label-width="100px">
      <el-row>
        <el-col :span="12">
          <el-form-item :label="$t('card_index.updForm.customize_grouping')"  >
            <el-input v-model="updForm.customize_grouping" :placeholder="$t('common.pleaseEnter')"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="IMEI :"  >
            <el-input v-model="updForm.imei" :placeholder="$t('common.pleaseEnter')"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!--  发货日期-->
      <el-form-item :label="$t('card_index.updForm.deliver_date')"  >
        <el-date-picker
          v-model="updForm.deliver_date"
          :placeholder="$t('common.pleaseChoose')"
          size="small"
          type="date"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
          :picker-options="deliveryOptions">
        </el-date-picker>
      </el-form-item>
      <el-form-item :label="$t('card_index.updForm.remarks')"  >
        <el-input v-model="updForm.remarks" type="textarea" :placeholder="$t('common.pleaseEnter')"></el-input>
      </el-form-item>
      <el-form-item :label="$t('card_index.updForm.updateNotFilled')" label-width="140px">
        <el-radio-group>
          <label class="el-radio " v-for="(item,index) in whetherOptions">
                                    <span class="el-radio__input my_checkbox__inner">
                                    <input type="radio" v-model="updForm.updateNotFilled"
                                           name="Button"
                                           :value="item.dictValue"/>
                                     </span>
            <span class="el-radio__label ">{{ item.dictLabel }}</span>
          </label>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" v-if="wUpdBatch" @click="edidBatch">{{ $t('common.sure') }}</el-button>
      <el-button type="primary" v-if="!wUpdBatch" @click="edidOn">{{ $t('common.sure') }}</el-button>
      <el-button @click="edit_show=false">{{ $t('common.cancel') }}</el-button>
    </div>
  </div>
</template>

<script>



import tools from "@/utils/iotos/tools";
import {editCardPublic} from "@/api/iotos/connect/card";

export default {
  props: {
    setObj: Function,//父组件赋值 函数
    edit_show: Boolean,//当前界面是否显示
    wUpdBatch: Boolean,//当前界面是否显示
    whetherOptions: Array,//是否
    deliveryOptions: Object,//时间选择 快捷键
    updForm: Object,
  },
  name: "editCard",
  data() {
    return {

    }
  },
  methods: {


//批量编辑
    edidBatch(){},
    //编辑
    edidOn(){
      let _this = this;
      if(tools.VerificationsText(_this, _this.updForm.iccid, this.$t("card_index.rs.updIccid")) == true){
        let  updateNotFilled = this.updForm.updateNotFilled;
        let bool = false;
        let title = "";

        if(updateNotFilled=='1'){
          bool = true;
          this.updForm.remarks = tools.isNull(this.updForm.remarks)?this.updForm.remarks:'';
          this.updForm.imei = tools.isNull(this.updForm.imei)?this.updForm.imei:'';
          this.updForm.deliver_date = tools.isNull(this.updForm.deliver_date)?this.updForm.deliver_date:'';
          this.updForm.customize_grouping = tools.isNull(this.updForm.customize_grouping)?this.updForm.customize_grouping:'';

          title += this.$t('card_index.updForm.remarks')+" ["+this.updForm.remarks+"]";
          title += "IMEI: ["+this.updForm.imei+"]";
          title += this.$t('card_index.updForm.deliver_date')+" ["+this.updForm.deliver_date+"]";
          title += this.$t('card_index.updForm.customize_grouping')+" ["+this.updForm.customize_grouping+"]";

        }else {
          if(tools.isNull(this.updForm.remarks)){
            bool = true;
            title += this.$t('card_index.updForm.remarks')+" ["+this.updForm.remarks+"]";
          }
          if(tools.isNull(this.updForm.imei)){
            bool = true;
            title += "IMEI: ["+this.updForm.imei+"]";
          }
          if(tools.isNull(this.updForm.deliver_date)){
            bool = true;
            title += this.$t('card_index.updForm.deliver_date')+" ["+this.updForm.deliver_date+"]";
          }
          if(tools.isNull(this.updForm.customize_grouping)){
            bool = true;
            title += this.$t('card_index.updForm.customize_grouping')+" ["+this.updForm.customize_grouping+"]";
          }

        }

        if(bool){
          let pwdStr = tools.encryptSy(this.updForm);
          tools.openAsk(this,'warning', this.$t("card_index.ask.upd")+title+' ?', this.editCardSub,pwdStr,null);
        }else {
          this.$message.error(this.$t("card_index.rs.unfilled"));
        }
      }
    },


    editCardSub(pwdStr){
      editCardPublic(pwdStr).then(response => {
        let jsonObj = JSON.parse(tools.Decrypt(response));
        let msg = jsonObj.msg;
        if (jsonObj.code == 200) {
          this.$message.success(msg);
          this.$emit("setObj", "setEditShow", false);//已加载
        } else {
          this.$message.error(msg);
        }
      })
    },



  },
}
</script>
