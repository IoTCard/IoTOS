<template>
  <el-col :xs="xsW" :sm="smW" :md="mdW" :lg="lgW" class="card-panel-col" >
    <router-link v-if="tools.isNull(toUrl)" :to="toUrl" class="link-type">
      <div class="card-panel">
        <div :class="iconClass">
          <svg-icon :icon-class="icon" class-name="card-panel-icon"/>
        </div>
        <div class="card-panel-description">
          <div class="card-panel-text">
            {{ title }}
          </div>
          <div class="card-panel-text">
                      <span class="card-panel-text-font">
                          <count-to :start-val="0" :end-val="val" :duration="duration" class="card-panel-num"/>
                       </span> {{ unit }}
          </div>
        </div>
      </div>
    </router-link>
    <div class="card-panel" v-if="!tools.isNull(toUrl)" >
      <div :class="iconClass">
        <svg-icon :icon-class="icon" class-name="card-panel-icon"/>
      </div>
      <div class="card-panel-description">
        <div class="card-panel-text">
          {{ title }}
        </div>
        <div class="card-panel-text">
                      <span class="card-panel-text-font">
                          <count-to :start-val="0" :end-val="val" :duration="duration" class="card-panel-num"/>
                       </span> {{ unit }}
        </div>
      </div>
    </div>
  </el-col>
</template>

<script>
import CountTo from 'vue-count-to'
import tools from "@/utils/iotos/tools";

export default {

  name: 'myCol',
  props: {
    smW: Number,
    xsW: Number,
    mdW: Number,
    lgW: Number,
    duration: Number,
    title: String,//panel-text
    unit: String,//单位
    val: Number,//显示值
    icon: String,//图标
    iconClass: String,//图标 Class
    toUrl: {type: String,default: null },//跳转路径
  },
  components: {
    CountTo,
  },
  data() {
    return {
      tools:tools
    }
  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>
.panel-group {
  .card-panel-col {
  }

  .card-panel {
    height: 60px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-one {
        background: #40c9c6;
      }

      .icon-tow {
        background: #36a3f7;
      }

      .icon-three {
        background: #f4516c;
      }

      .icon-four {
        background: #34bfa3
      }
    }

    .icon-one {
      color: #40c9c6;
    }

    .icon-tow {
      color: #36a3f7;
    }

    .icon-three {
      color: #f4516c;
    }

    .icon-four {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      padding: 10px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
      margin-right: 5px;
    }

    .card-panel-icon {
      float: left;
      font-size: 40px;
    }

    .card-panel-description {
      font-weight: bold;
      margin: 10px;

      .card-panel-text {
        line-height: 13px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 13px;
        margin-bottom: 12px;
      }
      .card-panel-text-font {
        font-weight: 700;
        font-size: 1.24rem;
        color: #3b4e66;
        margin-right: 0.1rem;
      }

      .card-panel-num {
        font-size: 18px;
      }
    }
  }
}

@media (max-width:550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
