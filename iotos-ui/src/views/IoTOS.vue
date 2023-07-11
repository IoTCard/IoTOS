<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <div class="shields-wrapper">
          <h2>IoTOS</h2>
          <a target="_blank" class="shields-wrapper-image" href='https://github.com/IoTCard/IoTOS'><img  alt="GitHub license" src="https://img.shields.io/github/license/IoTCard/IoTOS?style=flat-square&logo=github&color=616ae5"></a>
          <a target="_blank" class="shields-wrapper-image" href='https://github.com/IoTCard/IoTOS'><img  alt="GitHub stars" src="https://img.shields.io/github/stars/IoTCard/IoTOS?style=flat-square&logo=github&color=616ae5"></a>
          <a target="_blank" class="shields-wrapper-image" href='https://github.com/IoTCard/IoTOS'><img alt="GitHub forks" src="https://img.shields.io/github/forks/IoTCard/IoTOS?style=flat-square&logo=github&color=616ae5"></a>
          <a target="_blank" class="shields-wrapper-image" href='https://gitee.com/chinaiot/iotos/stargazers'><img src='https://gitee.com/chinaiot/iotos/badge/star.svg?theme=dark' alt='star'></a>
          <a target="_blank" class="shields-wrapper-image" href='https://gitee.com/chinaiot/iotos/members'><img src='https://gitee.com/chinaiot/iotos/badge/fork.svg?theme=dark' alt='fork'></a>
        </div>

        <p>
          {{ $t("IoTOS.introduce") }}
        </p>
        <p>
          <b>{{ $t("IoTOS.currentVersion") }}</b> <span>{{ version }}</span>
        </p>
        <p>
          <el-button plain size="mini" type="danger">{{ $t("IoTOS.free") }}</el-button>
          <el-button
            type="danger"
            size="mini"
            plain
            @click="goTarget('https://www.aliyun.com/minisite/goods?userCode=3mwvbeng')"
          > {{ $t("IoTOS.receiveAlibabaCloudCoupons") }}</el-button>

        </p>
        <p>
          <el-button
            type="primary"
            size="mini"
            plain
            @click="goTarget('https://gitee.com/chinaiot/iotos')"
          >{{ $t("IoTOS.gitEE") }}</el-button>
          <el-button
            type="primary"
            size="mini"
            plain
            @click="goTarget('https://github.com/IoTCard/IoTOS')"
          >{{ $t("IoTOS.gitHub") }}</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-notebook-1"
            plain
            @click="goTarget('http://www.iotos.top/')"
          >{{ $t("IoTOS.doc") }}</el-button>
          <el-button
            size="mini"
            icon="el-icon-paperclip"
            plain
            @click="goTarget('http://demo.iotos.top')"
          >{{ $t("IoTOS.visitMainSite") }}</el-button>

        </p>
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>{{ $t("IoTOS.technologySelection") }}</h2>
          </el-col>
        </el-row>
        <el-row>

          <el-col :span="6">
            <h4>{{ $t("IoTOS.backendTechnology") }}</h4>
            <ul>
              <li>SpringBoot</li>
              <li>Spring Security</li>
              <li>JWT</li>
              <li>MyBatis</li>
              <li>Druid</li>
              <li>RabbitMQ</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>{{ $t("IoTOS.frontEndTechnology") }}</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element-ui</li>
              <li>Axios</li>
              <li>Sass</li>
              <li>Quill</li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>{{ $t("IoTOS.contactInformation") }}</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-s-promotion"></i> {{ $t("IoTOS.officialWebsite") }}：<el-link
              href="http://www.iotos.top"
              target="_blank"
            >http://www.iotos.top</el-link
            >
            </p>
            <div>
              <i class="el-icon-chat-dot-round"></i> {{ $t("IoTOS.weChat") }}：iotos_top
              <div style="width: 100%;height: 175px;">
                <img style="width: 130px;float: left;" src="../assets/images/contact1.jpg"/>
                <img style="width: 130px;margin-top: 20px;float: left;" src="../assets/images/contact2.jpg"/>
              </div>
            </div>
            <div>
              <i class="el-icon-service"></i> {{ $t("IoTOS.mail") }}：card@iotos.top
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>{{ $t("IoTOS.updateLog") }}</span>
          </div>
          <el-collapse accordion v-model="version">


            <el-collapse-item title="v1.2.1 - 2023-07-08"  name="v1.2.1">
              <ol>
                <li>{{ $t("IoTOS.v121.l00") }}</li>
                <li>{{ $t("IoTOS.v121.l01") }}</li>
                <li>{{ $t("IoTOS.v121.l02") }}</li>
                <li>{{ $t("IoTOS.v121.l03") }}</li>
                <li>{{ $t("IoTOS.v121.l04") }}</li>

              </ol>
            </el-collapse-item>
            <el-collapse-item title="v1.0.0 - 2023-06-18"  name="v1.0.0">
              <ol>
                <li>{{ $t("IoTOS.v100.l00") }}</li>
                <li>{{ $t("IoTOS.v100.l01") }}</li>
                <li>{{ $t("IoTOS.v100.l02") }}</li>
                <li>{{ $t("IoTOS.v100.l03") }}</li>
                <li>{{ $t("IoTOS.v100.l04") }}</li>
                <li>{{ $t("IoTOS.v100.l05") }}</li>
                <li>{{ $t("IoTOS.v100.l06") }}</li>
                <li>{{ $t("IoTOS.v100.l07") }}</li>



              </ol>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <img style="width: 530px;float: left;" src="../assets/images/gzhewm.gif"/>

          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "v1.2.1",

    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    }
  }
};
</script>

<style scoped lang="scss">
.shields-wrapper-image{
  margin-right: 5px;
}
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 5px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

