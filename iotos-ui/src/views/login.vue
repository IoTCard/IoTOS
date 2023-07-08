<template>
  <div class="" >
    <div  style="">
      <el-menu
        :default-active="activeIndex2"
        class="el-menu-demo home-header-container"
        mode="horizontal"
        @select="handleSelect"
        text-color="black"
        active-text-color="rgb(59, 164, 255)"
        style="width: 90%;"
      >
        <el-menu-item index="1"> <a herf="http://www.iotos.top" target="_blank"><img  :src="logo" style="width: 80px;" /></a></el-menu-item>
        <el-menu-item index="2"><a href="http://demo.iotos.top/" target="_blank">连接管理</a></el-menu-item>
        <el-menu-item index="3"><a href="https://gitee.com/chinaiot/iotos" target="_blank">源码获取</a></el-menu-item>
      </el-menu>


      <div class="block home-header-container">
        <el-carousel :interval="3000" style="height: 600px;width: 100%;" >
          <el-carousel-item v-for="item in loginImgList" :style="{height:'600px',backgroundImage:`url(${item.url})`,backgroundSize:'auto',backgroundRepeat: 'no-repeat no-repeat'}" :key="item.index">
            <div style="width:600px;height:400px;position: absolute; top: 125px;left: 7%;  z-index: 998;">
              <div class="section-banner-content-normal">
                <h1>{{ item.title }}</h1>
                <p>{{ item.describe }}</p>
                <a v-if="tools.isNull(item.btn1Title)" :href="item.btn1Url" :target="tools.isNull(item.btn1Type)?item.btn1Type:'_self'">{{ item.btn1Title }}</a>
                <a v-if="tools.isNull(item.btn2Title)" :href="item.btn2Url" :target="tools.isNull(item.btn2Type)?item.btn2Type:'_self'">{{ item.btn2Title }}</a>
              </div>
            </div>
          </el-carousel-item>
        </el-carousel>
      </div>

    </div>

    <div style="position: absolute; top: 125px;right: 10%;  z-index: 999;">
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
        <h3 class="title">{{ $t('login.title') }} <lang-select class="set-language" /> </h3>

        <el-form-item prop="username">
          <el-input v-model="loginForm.username" type="text" auto-complete="off" :placeholder="$t('login.username')">
            <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            auto-complete="off"
            :placeholder="$t('login.password')"
            @keyup.enter.native="handleLogin"
          >
            <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
          </el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-input
            v-model="loginForm.code"
            auto-complete="off"
            :placeholder="$t('login.vcode_title')"
            style="width: 63%"
            @keyup.enter.native="handleLogin"
          >
            <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
          </el-input>
          <div class="login-code">
            <img :src="codeUrl" @click="getCode" class="login-code-img"/>
          </div>
        </el-form-item>
        <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">{{ $t('login.r_password') }}</el-checkbox>
        <el-form-item style="width:100%;">
          <el-button
            :loading="loading"
            size="medium"
            type="primary"
            style="width:100%;"
            @click.native.prevent="handleLogin"
          >
            <span v-if="!loading">{{ $t('login.logIn') }}</span>
            <span v-else>{{ $t('login.login_pt') }}...</span>
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <div style="height: 245px;width: 100%;padding: 0 5%;background-color: #fbfbfb;">
      <div class="footer-left" >
        <div class="column">
          <ul>
            <li v-for="(item,index) in linkList1" >
              <a class="item"  :href="item.url" :target="index==0 ?'_self':'_blank'">
                <span :style="index==0 ? 'font-size: 25px;' : 'font-size: 15px;'">  {{ item.title }}</span>
              </a>
            </li>
          </ul>
        </div>
        <div class="column">
          <ul>
            <li v-for="(item,index) in linkList2" >
              <a class="item"  :href="item.url" :target="index==0 ?'_self':'_blank'">
                <span  :style="index==0 ? 'font-size: 25px;' : 'font-size: 15px;'">  {{ item.title }}</span>
              </a>
            </li>
          </ul>
        </div>
        <div class="column">
          <ul>
            <li v-for="(item,index) in linkList3" >
              <a class="item"  :href="item.url" :target="index==0 ?'_self':'_blank'">
                <span  :style="index==0 ? 'font-size: 25px;' : 'font-size: 15px;'">  {{ item.title }}</span>
              </a>
            </li>
          </ul>
        </div>
        <div class="column">
          <ul>
            <li v-for="(item,index) in linkList4" >
              <a class="item"  :href="item.url" :target="index==0 ?'_self':'_blank'">
                <span  :style="index==0 ? 'font-size: 25px;' : 'font-size: 15px;'">  {{ item.title }}</span>
              </a>
            </li>
          </ul>
        </div>
      </div>


      <div class="footer-right">
        <div class="qrcode ">
          <img alt="微信订阅号" src="http://www.iotos.top/dyh_iotos.jpg">
          <p >微信订阅号</p>
        </div>
        <div class="qrcode ">
          <img alt="微信服务号" src="http://www.iotos.top/fwh_iotos.jpg">
          <p >微信服务号</p>
        </div>
        <div class="column">
          <ul>
            <li v-for="(item,index) in linkIoTOSList" >
              <a class="item"  :href="item.url" :target="index==0 ?'_self':'_blank'">
                <svg-icon :icon-class="item.icon" v-show="index!=0"   class-name="font-icon"/>
                <span  :style="index==0 ? 'font-size: 25px;' : 'font-size: 15px;'">  {{ item.title }}</span>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!--  底部  -->
    <div class="el-login-footer">
      <div></div>

      <span>Copyright © 2023 iotos.top All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import LangSelect from '@/components/LangSelect'
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'
import logoImg from '@/assets/logo/logo.png'
import carousel_1 from '@/assets/images/carousel/1.png'
import carousel_2 from '@/assets/images/carousel/2.png'
import carousel_3 from '@/assets/images/carousel/3.png'
import carousel_4 from '@/assets/images/carousel/4.png'

import tools from "@/utils/iotos/tools";




export default {
  name: "Login",
  components: { LangSelect },
  data() {
    return {
      codeUrl: "",
      cookiePassword: "",
      activeIndex2:'2',
      tools:tools,
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: true,
        code: "",
        uuid: "",

      },
      logo: logoImg,
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: this.$t('login.rs_username') }
        ],
        password: [
          { required: true, trigger: "blur", message: this.$t('login.rs_password') }
        ],
        code: [{ required: true, trigger: "change", message: this.$t('login.rs_vcode') }]
      },
      loading: false,
      redirect: undefined,

      loginImgList:[
        {
          url:carousel_1,
          title:this.$t('login.loginImgList[0].title'),
          describe:this.$t('login.loginImgList[0].describe'),
          btn1Title:this.$t('login.loginImgList[0].btn1Title'),
          btn1Url:"https://mp.weixin.qq.com/s/-rNuGZlG2BEYK759SYLGNg",
          btn1Type:"_blank",
          btn2Title:this.$t('login.loginImgList[0].btn2Title'),
          btn2Url:"https://gitee.com/chinaiot/iotos",
          btn2Type:"_blank",
        },
        {
          url:carousel_2,
          title:this.$t('login.loginImgList[1].title'),
          describe:this.$t('login.loginImgList[1].describe'),
          btn1Title:this.$t('login.loginImgList[1].btn1Title'),
          btn1Url:"",
          btn1Type:"_blank",
          btn2Title:this.$t('login.loginImgList[1].btn2Title'),
          btn2Url:"https://gitee.com/chinaiot/iotos",
          btn2Type:"_blank",
        },
        {
          url:carousel_3,
          title:this.$t('login.loginImgList[2].title'),
          describe:this.$t('login.loginImgList[2].describe'),
          btn1Title:this.$t('login.loginImgList[2].btn1Title'),
          btn1Url:"https://mp.weixin.qq.com/s/-TZwX12N3uV7Q0ZfXlJNwQ",
          btn1Type:"_blank",
          btn2Title:this.$t('login.loginImgList[2].btn2Title'),
          btn2Url:"https://gitee.com/chinaiot/iotos-app",
          btn2Type:"_blank",
        },
        {
          url:carousel_4,
          title:this.$t('login.loginImgList[3].title'),
          describe:this.$t('login.loginImgList[3].describe'),
          btn1Title:this.$t('login.loginImgList[3].btn1Title'),
          btn1Url:"",
          btn1Type:"",
          btn2Title:this.$t('login.loginImgList[3].btn2Title'),
          btn2Url:"https://gitee.com/chinaiot/iotos",
          btn2Type:"_blank",
        }
      ],

      linkList1:[
        {
          url:"#",
          title:"中国移动",
        },
        {
          url:"https://ec.iot.10086.cn/",
          title:"oneLink",
        },
        {
          url:"https://open.iot.10086.cn/",
          title:"oneNet",
        },
        {
          url:"https://api.iot.10086.cn/",
          title:"能力开放平台",
        },
        {
          url:"https://iot.bigdata.10086.cn/app/",
          title:"大数据",
        }
      ],
      linkList2:[
        {
          url:"#",
          title:"中国电信",
        },
        {
          url:"https://www.ctwing.cn/",
          title:"ctwing",
        },
        {
          url:"https://cmp.ctwing.cn:4821/",
          title:"5G-CMP",
        },
        {
          url:"https://www.ct10649.com/#/",
          title:"4G-CMP",
        },
        {
          url:"https://global.ct10649.com/",
          title:"国际业务-DCP",
        }
      ],
      linkList3:[
        {
          url:"#",
          title:"中国联通",
        },
        {
          url:"https://cmp.10646.cn/",
          title:"雁飞·智连-CMP",
        },
        {
          url:"https://gw.10646.cn/views/abilityIndex/abilityIndex/abilityIndex.html",
          title:"能力开放平台",
        },
        {
          url:"https://sso.10646.cn/",
          title:"IOT Connect",
        }
      ],
      linkList4:[
        {
          url:"#",
          title:"国际平台",
        },
        {
          url:"https://www.smart.com.kh/zh/",
          title:"SMART",
        },
        {
          url:"https://www.globe.com.ph/",
          title:"GLOBE",
        },
        {
          url:"https://www.hktchina.com/",
          title:"HTK",
        },
        {
          url:"https://www.sitecore.com/zh-cn",
          title:"SITECORE",
        },
        {
          url:"https://www.south.com.hk/",
          title:"SCT",
        }

      ],
      linkIoTOSList:[
        {
          url:"#",
          title:"关于-IoTOS",
        },
        {
          icon:'website',
          url:"http://www.iotos.top/",
          title:"官网",
        },
        {
          icon:'doc',
          url:"http://doc.iotos.top/",
          title:"文档网站",
        },
        {
          icon:'testweb',
          url:"http://demo.iotos.top/",
          title:"演示站点",
        },
        {
          icon:'gitee',
          url:"https://iot.bigdata.10086.cn/app/",
          title:"Gitee-源码",
        },
        {
          icon:'github',
          url:"",
          title:"GitHub-源码",
        }
      ],
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    getCode() {
      getCodeImg().then(res => {
        this.codeUrl = "data:image/gif;base64," + res.img;
        this.loginForm.uuid = res.uuid;
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {

        if (valid) {
          this.loading = true;
          Cookies.set("username", this.loginForm.username, { expires: 30 });
          if (this.loginForm.rememberMe) {
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          let _this = this;
          this.$store
            .dispatch("Login", this.loginForm)
            .then(() => {
              this.loading = false;
              //自动解析 用户系统语言
              let rCode = Cookies.get("LgCode");
              let lang = rCode!=null?rCode:"";
              let lgCode = "zh";
              if(lang=='zh-CN'){
                lgCode = "zh";
              }else if(lang=='zh-TW'){
                lgCode = "zh_tw";
              }else {
                lgCode = "en";
              }
              _this.$i18n.locale = lgCode;
              _this.$store.dispatch('app/setLanguage', lgCode);

              this.$router.push({ path: this.redirect || "/" });
            })
            .catch(() => {
              this.loading = false;
              this.getCode();
            });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">

.font-icon {
  font-size: 25px;
  margin-right: 10px;
}
.column>ul>li>a:hover {
  color: rgb(59, 164, 255);
}

.qrcode {
  float: right;
  padding: 10px;
  margin-right: 50px;
}
.qrcode img{
  width: 150px;
}
.qrcode>p{
  text-align: center;
}

.section-banner-content-normal h1{
  font-size: 40px;
  color: #fff;
  margin: 0;
}

.section-banner-content-normal p{
  height: 110px;
  margin: 30px 0;
  font-size: 16px;
  color: #fff;
  line-height: 1.7;
}

.section-banner-content-normal a{
  display: inline-block;
  width: 140px;
  height: 40px;
  background-color: rgb(59, 164, 255);
  color: #fff;
  cursor: pointer;
  text-align: center;
  line-height: 40px;
  font-size: 14px;
  border-radius: 10px;
}

.section-banner-content-normal a+a{
  background-color: #fff;
  color: rgb(59, 164, 255);
  margin-left: 12px;
}


.login {
  display: flex;
  justify-content: center;
  align-items: center;
  //height: 100%;
  //background-image: url("../assets/images/login-background.jpg");
  //background-size: cover;
}
.el-menu--horizontal > .el-menu-item{
  height: 50px;
  line-height: 50px;
}


.home-header-container{
  width: 100%;
  width-min:1200px;
  height: 100%;
  margin: 0 auto;
}
.iotosBlue{
  color:rgb(59, 164, 255);
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: rgb(59, 164, 255);
}

.login-form {
  border-radius: 6px;
  background: #ffffff2b;
  width: 400px;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  background-color: black;
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 38px;
}
.footer-left .column{
  float: left;
  width: 250px;
}
.column>ul>li{
  list-style: none;
  line-height: 35px;
}
.footer-left{
  float: left;
}
.footer-right{
  float: right;
  width: 650px;
}
</style>
