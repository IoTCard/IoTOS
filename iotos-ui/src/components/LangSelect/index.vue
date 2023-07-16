<template>
  <el-dropdown  trigger="click" class="international right-menu-item" @command="handleSetLanguage">
    <div>
      <svg-icon class-name="international-icon" style="color: rgb(59 164 255);" icon-class="language" />
    </div>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item :disabled="language==='zh'" command="zh">
        中文-简体
      </el-dropdown-item>
      <el-dropdown-item :disabled="language==='zh_tw'" command="zh_tw">
        中文-繁体
      </el-dropdown-item>
      <el-dropdown-item :disabled="language==='en'" command="en">
        English
      </el-dropdown-item>



    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
import {setLg} from "@/api/system/user";
import tools from "@/utils/iotos/tools";
import {getToken} from "@/utils/auth";

export default {
  computed: {
    language() {
      return this.$store.getters.language
    }
  },
  methods: {
    handleSetLanguage(lang) {
      this.$i18n.locale = lang;
      this.$store.dispatch('app/setLanguage', lang)
   /*   this.$message({
        message: 'success',
        type: 'success'
      })*/
      let  token = getToken();
      if(tools.isNull(token)){
        let map ={};
        if(lang=='zh'){
          map.lgCode = "zh-CN";
        }else if(lang=='zh_tw'){
          map.lgCode = "zh-TW";
        }else {
          map.lgCode = "en";
        }
        let pwdStr = tools.encryptSy(map);
        setLg(pwdStr).then(response => {
          let jsonObj = JSON.parse(tools.Decrypt(response));
          if (jsonObj.code == 200) {
            this.$modal.msgSuccess(jsonObj.msg);
          } else {
            this.$message.error(jsonObj.msg);
          }
        })
      }



    }
  }
}
</script>
<style lang="scss" scoped>

.right-menu-item {
  display: inline-block;
  padding: 0 8px;
  height: 100%;
  font-size: 18px;
  color: #5a5e66;
  vertical-align: text-bottom;

  &.hover-effect {
    cursor: pointer;
    transition: background .3s;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }
}


</style>

