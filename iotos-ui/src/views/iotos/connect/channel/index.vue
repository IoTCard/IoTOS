<template>
  <div class="app-container">
    <el-row :gutter="20">

      <el-col :span="mainwidth" :xs="24">


        <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px" style="margin-bottom: 10px">
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-input
                v-model="queryParams.value"
                :placeholder="$t('common.queryValue')"
                clearable
                size="small"
                style="width: 350px"
                @keyup.enter.native="handleQuery"
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
            </el-col>
            <el-col :span="1.5">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('common.search') }}</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('common.reset') }}</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleSave"
                v-hasPermi="['iotos:channel:add']"
              >{{ $t('common.save') }}
              </el-button>
            </el-col>

            <right-toolbar :showSearch.sync="showSearch" v-hasPermi="['iotos:channel:list']" @queryTable="getList"
                           :columns="columns"></right-toolbar>
          </el-row>
        </el-form>


        <el-table v-loading="loading" :data="dataList" >
          <el-table-column :label="columns[0].label" align="center"  prop="c_no" v-if="columns[0].visible" width="210">
            <template slot-scope="scope">
              <span @click="viewDetails(scope.row)" class="link-type">{{ scope.row.c_no }}</span>
            </template>
          </el-table-column>
          <el-table-column :label="columns[1].label" align="center"  prop="template" v-if="columns[1].visible"
                           width="150">
            <template slot-scope="scope">
              {{ tools.getDkeyValue(templateOptions, scope.row.template) }}
            </template>
          </el-table-column>
          <el-table-column :label="columns[2].label"  prop="name" v-if="columns[2].visible" />
          <el-table-column :label="columns[3].label" prop="nick_name" v-if="columns[3].visible"/>
          <el-table-column :label="columns[4].label" align="center" v-if="columns[4].visible"
                           :show-overflow-tooltip="true" width="100">
            <template slot-scope="scope">
              <span :class="scope.row.status=='0'?'myGreen':'mygRed'">{{
                  tools.getDkeyValue(statusOptions, scope.row.status)
                }}</span>
            </template>
          </el-table-column>
          <el-table-column :label="columns[5].label" align="center"  v-if="columns[5].visible"
                           :show-overflow-tooltip="true" width="80">
            <template slot-scope="scope">
              <span :class="scope.row.w_polling=='1'?'myGreen':'mygRed'">{{
                  tools.getDkeyValue(whetherOptions, scope.row.w_polling)
                }}</span>
            </template>
          </el-table-column>
          <el-table-column :label="columns[6].label" align="center" prop="card_count" v-if="columns[6].visible" width="150"/>
          <el-table-column :label="columns[7].label" align="center" prop="card_total" v-if="columns[7].visible" width="110"/>
          <el-table-column :label="columns[8].label" align="center" prop="card_used" v-if="columns[8].visible" width="110"/>
          <el-table-column :label="columns[9].label" align="center" prop="card_left" v-if="columns[9].visible" width="110"/>

          <el-table-column
            :label="$t('common.operate')"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['iotos:channel:edit']"
              >{{ $t('common.edie') }}</el-button>
              <el-button
                size="mini"
                type="text"
                @click="viewDetails(scope.row)"
                v-hasPermi="['iotos:channel:find']"
              >{{ $t('common.details') }}</el-button>
              <el-button
                size="mini"
                type="text"
                @click="handleDelete(scope.row)"
                v-hasPermi="['iotos:channel:remove']"
              >{{ $t('common.delete') }}</el-button>
            </template>
          </el-table-column>

        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!-- 详情查看 -->

    <el-dialog :title="title" :visible.sync="show_ds" width="80%" append-to-body :close-on-click-modal="false">
      <el-form :model="aMap"  label-width="60px">
      <el-row>
              <el-col :span="6">
                <el-form-item :label="$t('common.name')" >
                  <el-input v-model="pMap.name" :placeholder="$t('common.pleaseEnter')+$t('common.name')"/>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item :label="$t('common.nickName')" >
                  <el-input v-model="pMap.nick_name" :placeholder="$t('common.pleaseEnter')+$t('common.nickName')"/>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item :label="$t('channel_index.form.template')" >
                  <el-select v-model="pMap.template" :placeholder="$t('common.pleaseChoose')">
                    <el-option
                      v-for="item in templateOptions"
                      :key="item.dictValue"
                      :label="item.dictLabel"
                      :value="item.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item :label="$t('channel_index.form.polling')" >
                  <el-select v-model="pMap.w_polling" :placeholder="$t('common.pleaseChoose')">
                    <el-option
                      v-for="item in whetherOptions"
                      :key="item.dictValue"
                      :label="item.dictLabel"
                      :value="item.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item :label="$t('common.state')" >
                  <el-select v-model="pMap.status" :placeholder="$t('common.pleaseChoose')">
                    <el-option
                      v-for="item in statusOptions"
                      :key="item.dictValue"
                      :label="item.dictLabel"
                      :value="item.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

      </el-form>
      <el-form :model="aMap"  label-width="100px">

      <el-tabs v-model="activeName" type="border-card" >

        <el-tab-pane :label="$t('channel_index.form.basicConfiguration')" name="config">
          <el-row>
            <el-col :span="12">
              <el-form-item :label="$t('channel_index.form.requestAddress')">
                <el-input v-model="aMap.url"  @blur="verifyUrl"
                          :placeholder="$t('common.rs.http')"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :label="$t('channel_index.form.configurationOne')">
                <el-input v-model="aMap.parameter_one" :placeholder="$t('common.pleaseEnter')+$t('channel_index.form.configurationOne')"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :label="$t('channel_index.form.configurationTwo')">
                <el-input v-model="aMap.parameter_tow" :placeholder="$t('common.pleaseEnter')+$t('channel_index.form.configurationTwo')"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :label="$t('channel_index.form.ConfigurationTree')">
                <el-input v-model="aMap.parameter_three" :placeholder="$t('common.pleaseEnter')+$t('channel_index.form.ConfigurationTree')"/>
              </el-form-item>
            </el-col>
          </el-row>



        </el-tab-pane>

         <el-tab-pane :label="$t('channel_index.form.synchronizationStrategy')" name="synTactics">
            <el-row>
              <el-col :span="8">
                <el-form-item :label="$t('channel_index.form.importAutomatically')">
                  <el-select  v-model="aMap.w_sync_upstream" clearable :placeholder="$t('common.pleaseChoose')">
                    <el-option
                      v-for="item in whetherOptions"
                      :key="item.dictValue"
                      :label="item.dictLabel"
                      :value="item.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item :label="$t('common.notify')">
                  <el-select  v-model="aMap.sync_change_notification" clearable :placeholder="$t('common.pleaseChoose')">
                    <el-option
                      v-for="item in whetherOptions"
                      :key="item.dictValue"
                      :label="item.dictLabel"
                      :value="item.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item :label="$t('channel_index.form.synchronizationType')">
                  <el-select  v-model="aMap.sync_data_type" clearable :placeholder="$t('common.pleaseChoose')">
                    <el-option
                      v-for="item in syncDataTypeOptions"
                      :key="item.dictValue"
                      :label="item.dictLabel"
                      :value="item.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="16">
                <el-form-item :label="$t('channel_index.form.syncField')">
                  <el-select
                    v-model="aMap.sync_field"
                    :placeholder="$t('channel_index.form.syncField')"
                    clearable
                    multiple
                    size="small"
                    style="width: 550px"
                  >
                    <el-option
                      v-for="dict in channel_syn_field"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    />
                  </el-select>

                </el-form-item>
              </el-col>
            </el-row>

          </el-tab-pane>

        <el-tab-pane :label="$t('channel_index.form.auxiliaryRegistration')" name="sessionInformation">
          <el-row>
            <el-col :span="24">
              <el-form-item  :label="$t('common.loginAddress')">
                <a target="_blank" :href="aMap.login_url" style="color:blue;">{{ aMap.login_url }}</a>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-form-item :label="$t('common.loginAddress')">
                <el-input v-model="aMap.login_url" @blur="verifyLogin_url"
                          :placeholder="$t('common.rs.http')"/>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item :label="$t('common.loginAccount')">
                <el-input v-model="aMap.login_account" :placeholder="$t('common.pleaseEnter')+$t('common.loginAccount')"/>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item :label="$t('common.loginPassword')">
                <el-input v-model="aMap.login_pwd" :placeholder="$t('common.pleaseEnter')+$t('common.loginPassword')"/>
              </el-form-item>
            </el-col>
          </el-row>

        </el-tab-pane>


        </el-tabs>




      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="saveBtn" v-hasPermi="['iotos:channel:add']" type="primary" @click="Save">{{ $t('common.save') }}</el-button>
        <el-button v-if="edieBtn" v-hasPermi="['iotos:channel:edit']" type="primary" @click="Edie">{{ $t('common.preserve') }} </el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import {
  listChanne,
  getChanne,
  saveChanne,
  editChanne,
  delChanne,
} from "@/api/iotos/connect/channel";
import tools from "@/utils/iotos/tools";
import '@/assets/styles/iotos.css';

export default {
  name: "channel",
  components: {

  },
  data() {
    return {






      //保存 编辑 按钮
      saveBtn: false,
      edieBtn: false,
      tools: tools,
      show_ds: false,//详情查看

      channel_syn_field:[],//同步字段
      mainwidth: 24,//宽度
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      dataList: null,
      // 弹出层标题
      title: "",
      // 状态数据字典
      typeOptions: [],
      syncDataTypeOptions:[],
      activeName: "config",
      formCol: 24,

      statusOptions: [],
      templateOptions: [],// 模板 字典
      whetherOptions: [],//是否
      pMap: {
        id: "",
        c_no: "",
        template: "",
        dept_id: "",
        name: "",
        nick_name: "",
        status:'',
        w_polling:''
      },

      aMap: {
        id: "",
        c_no: "",
        url: "",
        parameter_one: "",
        parameter_tow: "",
        parameter_three: "",
        login_url:'',
        login_account:'',
        login_pwd:'',
        remarks:'',
        w_sync_upstream:'',
        sync_change_notification:'',
        sync_data_type:'0',
        sync_field:[]
      },


      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: null,
        value: null,
        status: [],
      },
      // 列信息
      columns: [
        {key: 0, label: this.$t('channel_index.table.i_1'), visible: true},
        {key: 1, label: this.$t('channel_index.table.i_2'), visible: true},
        {key: 2, label: this.$t('channel_index.table.i_3'), visible: true},
        {key: 3, label: this.$t('channel_index.table.i_4'), visible: true},
        {key: 4, label: this.$t('channel_index.table.i_5'), visible: true},
        {key: 5, label: this.$t('channel_index.table.i_6'), visible: true},
        {key: 6, label: this.$t('channel_index.table.i_7'), visible: true},
        {key: 7, label: this.$t('channel_index.table.i_8'), visible: true},
        {key: 8, label: this.$t('channel_index.table.i_9'), visible: true},
        {key: 9, label: this.$t('channel_index.table.i_10'), visible: true},

      ],
      showButton:false,



    };
  },
  created() {

    let str = this.$route.params;


    if (window['channel_sel_type'] != undefined && window['channel_sel_type'] != null && window['channel_sel_type'] != '') {
      this.typeOptions = window['channel_sel_type'];
    } else {
      this.getDicts("iotos_channel_sel_type").then(response => {
        window['channel_sel_type'] = response.data;
        this.typeOptions = window['channel_sel_type'];
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


    //加载 状态
    if (window['statusOptions'] != undefined && window['statusOptions'] != null && window['statusOptions'] != '') {
      this.statusOptions = window['statusOptions'];
    } else {
      this.getDicts("sys_job_status").then(response => {
        window['statusOptions'] = response.data;
        this.statusOptions = window['statusOptions'];
      });
    }


    //加载 同步类型
    if(window['syncDataTypeOptions']!=undefined &&  window['syncDataTypeOptions']!=null && window['syncDataTypeOptions']!=''){
      this.syncDataTypeOptions = window['syncDataTypeOptions'];
    }else{
      this.getDicts("up_sync_type").then(response => {
        window['syncDataTypeOptions'] = response.data;
        this.syncDataTypeOptions = window['syncDataTypeOptions'];
      });
    }


    //加载 同步字段
    if (window['channel_syn_field'] != undefined && window['channel_syn_field'] != null && window['channel_syn_field'] != '') {
      this.channel_syn_field = window['channel_syn_field'];
    } else {
      this.getDicts("channel_syn_field").then(response => {
        window['channel_syn_field'] = response.data;
        this.channel_syn_field = window['channel_syn_field'];
      });
    }

    //加载 模板
    if (window['templateOptions'] != undefined && window['templateOptions'] != null && window['templateOptions'] != '') {
      this.templateOptions = window['templateOptions'];
    } else {
      this.getDicts("channel_template").then(response => {
        window['templateOptions'] = response.data;
        this.templateOptions = window['templateOptions'];
      });
    }


    if(tools.isNull(str)==true && tools.isNull(str.pwdStr)==true){
      let jsonObj = JSON.parse(tools.Decrypt(str.pwdStr));
      if(tools.isNull(jsonObj.value)){
        this.queryParams.value = jsonObj.value;
        this.queryParams.type = jsonObj.type;
      }
    }else {
      this.queryParams.type = "1";
    }
    this.getList();

  },
  methods: {

    verifyLogin_url() {
      let url = this.aMap.login_url;
      let reg = /(http|https):\/\/([\w.]+\/?)\S*/;
      if (reg.test(url)) {
        this.$modal.msgSuccess(this.$t('common.GCGCorrect'));
      } else {
        this.$message.error(this.$t('common.rs.http'));
      }
    },
    verifyUrl() {
      let url = this.aMap.url;
      let reg = /(http|https):\/\/([\w.]+\/?)\S*/;
      if (reg.test(url)) {
        this.$modal.msgSuccess(this.$t('common.GCGCorrect'));
      } else {
        this.$message.error(this.$t('common.rs.http'));
      }
    },




    /*获取查询参数*/
    getParams() {

    },

    /** 查询 */
    getList() {
      this.loading = true;
      this.getParams();
      this.queryParams.page = 1;
      let pwdStr = tools.encryptSy(this.queryParams);
      listChanne(pwdStr).then(response => {
          let jsonObj = JSON.parse(tools.Decrypt(response));
        if (jsonObj.code == 200) {
            this.dataList = jsonObj.data.data;
            this.total = jsonObj.data.Pu.rowCount;
          } else {
            this.$message.error(jsonObj.msg);
          }
          this.loading = false;
        }
      );
    },



    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.page = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {};
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.handleQuery();
    },

    /** 新增按钮操作 */
    handleSave() {
      this.showButton=false;
      this.pMap = {w_polling: "1",status: "0",template: this.templateOptions[0].dictValue};//清空数据
      this.aMap = {w_sync_upstream:"0",sync_change_notification:"1",sync_data_type:"0"};//清空数据


      //初始化 新增 数据
      this.show_ds = true;
      this.saveBtn = true;
      this.edieBtn = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.showButton=false;
      this.saveBtn = false;
      this.edieBtn = true;
      this.finddata(row);
    },
    /** 详情按钮操作 */
    viewDetails(row) {
      this.showButton=true;
      //console.log(row);
      this.saveBtn = false;
      this.edieBtn = false;
      this.finddata(row);
    },
    /*获取详细信息*/
    finddata(row) {
      this.aMap = {};//清空数据
      this.pMap = {};//清空数据
      let map = {};
      map.c_no = row.c_no;
      let pwdStr = tools.encryptSy(map);
      getChanne(pwdStr).then(response => {
        let jsonObj = JSON.parse(tools.Decrypt(response));
        if (jsonObj.code == 200) {
            this.aMap = tools.IoTOSFormatObj(jsonObj.data.aMap,['sync_field']);
            this.pMap = tools.IoTOSFormatObj(jsonObj.data.pMap,null);
          this.show_ds = true;
        } else {
          if (jsonObj.deptId == "100") {
            this.$message.error(jsonObj.msg);
          } else {
            this.$message.error(this.$t("common.fail"));
          }
        }
        this.loading = false;
      });
    },





    //新增
    Save() {
      this.showButton=false;
      let _this = this;
      if (tools.VerificationsText(_this, _this.pMap.template, this.$t("channel_index.rs.template")) == true &&
        tools.VerificationsText(_this, _this.pMap.name, this.$t("common.rs.name")) == true &&
        tools.VerificationsText(_this, _this.pMap.nick_name, this.$t("common.rs.nickName")) == true &&
        tools.VerificationsText(_this, _this.aMap.url, this.$t("channel_index.rs.requestAddress")) == true &&
        tools.VerificationsText(_this, _this.aMap.parameter_one, this.$t("channel_index.rs.configurationOne")) == true &&
        tools.VerificationsText(_this, _this.aMap.parameter_tow, this.$t("channel_index.rs.configurationTwo")) == true &&
        tools.VerificationsText(_this, _this.aMap.parameter_three, this.$t("channel_index.rs.ConfigurationTree")) == true &&
        tools.VerificationsText(_this, _this.pMap.w_polling, this.$t("channel_index.rs.polling")) == true &&
        tools.VerificationsText(_this, _this.pMap.status,  this.$t("common.rs.state")) == true
      ) {
        let url = this.aMap.url;
        let cd_addressurl = this.aMap.login_url;

        let reg = /(http|https):\/\/([\w.]+\/?)\S*/;
        this.aMap.login_url = this.aMap.login_url!=undefined?this.aMap.login_url:"";
        this.aMap.url = this.aMap.url!=undefined?this.aMap.url:"";

        if (tools.isNull(url) == false || reg.test(url)  ) {
          if(tools.isNull(cd_addressurl) == false || reg.test(cd_addressurl)){
            let map = {};
            map.pMap = this.pMap;
            map.aMap = this.aMap;
            let pwdStr = tools.encryptSy(map);
            saveChanne(pwdStr).then(response => {
              let jsonObj = JSON.parse(tools.Decrypt(response));
              if (jsonObj.code == 200) {
                this.$modal.msgSuccess(jsonObj.msg);
                this.show_ds = false;
                //更新数据
                this.queryParams.page = 1;
                this.getList();
              } else {
                let msg = jsonObj.data != null && jsonObj.data != '' ? jsonObj.data : jsonObj.msg;
                this.$message.error(msg);
              }
            })
          }else {
            this.$message.error(this.$t("common.loginAddress")+" "+this.$t("common.rs.http"));
          }
        } else {
          this.$message.error(this.$t("channel_index.form.requestAddress")+" "+this.$t("common.rs.http"));
          this.pMap.login_url = null;
        }
      }
    },
    //编辑
    Edie() {
      this.showButton=false;
      let _this = this;
      if (
          tools.VerificationsText(_this, _this.pMap.template, this.$t("channel_index.rs.template")) == true &&
          tools.VerificationsText(_this, _this.pMap.name, this.$t("common.rs.name")) == true &&
          tools.VerificationsText(_this, _this.pMap.nick_name, this.$t("common.rs.nickName")) == true &&
        tools.VerificationsText(_this, _this.aMap.url, this.$t("channel_index.rs.requestAddress")) == true &&
        tools.VerificationsText(_this, _this.aMap.parameter_one, this.$t("channel_index.rs.configurationOne")) == true &&
          tools.VerificationsText(_this, _this.aMap.parameter_tow, this.$t("channel_index.rs.configurationTwo")) == true &&
          tools.VerificationsText(_this, _this.aMap.parameter_three, this.$t("channel_index.rs.ConfigurationTree")) == true &&
          tools.VerificationsText(_this, _this.pMap.w_polling, this.$t("channel_index.rs.polling")) == true &&
          tools.VerificationsText(_this, _this.pMap.status,  this.$t("common.rs.state")) == true) {

        let url = this.aMap.url;
        let cd_addressurl = this.aMap.login_url;
        let reg = /(http|https):\/\/([\w.]+\/?)\S*/;
        this.aMap.login_url = this.aMap.login_url!=undefined?this.aMap.login_url:"";
        this.aMap.url = this.aMap.url!=undefined?this.aMap.url:"";

        if (tools.isNull(url) == false || reg.test(url)  ) {
          if(tools.isNull(cd_addressurl) == false || reg.test(cd_addressurl)){

              let map = {};
              map.pMap = this.pMap;
              map.aMap = this.aMap;
              let pwdStr = tools.encryptSy(map);
              editChanne(pwdStr).then(response => {
                let jsonObj = JSON.parse(tools.Decrypt(response));
                if (jsonObj.code == 200) {
                  this.$modal.msgSuccess(jsonObj.msg);
                  this.show_ds = false;
                  //更新数据
                  this.queryParams.page = 1;
                  this.getList();
                } else {
                  let msg = jsonObj.data != null && jsonObj.data != '' ? jsonObj.data : jsonObj.msg;
                  this.$message.error(msg);
                }
              })
            }else {
            this.$message.error(this.$t("common.loginAddress")+" "+this.$t("common.rs.http"));
          }
        }else {
          this.$message.error(this.$t("channel_index.form.requestAddress")+" "+this.$t("common.rs.http"));
          this.pMap.login_url = null;
        }
      }

    },


    /** 删除按钮操作 */
    handleDelete(row) {
      this.showButton=false;
      let map = {};
      map.c_no = row.c_no;
      let pwdStr = tools.encryptSy(map);
      tools.openAsk(this, 'warning', this.$t("common.ask.wDelName") + row.name + " ？", this.delRouteFun, pwdStr);

    },

    //删除通道
    delRouteFun(pwdStr) {
      delChanne(pwdStr).then(response => {
        let jsonObj = JSON.parse(tools.Decrypt(response));
        if (jsonObj.code == 200) {
          this.$modal.msgSuccess(jsonObj.msg);
          this.getList();
        } else {
          let msg = jsonObj.data != null && jsonObj.data != '' ? jsonObj.data : jsonObj.msg;
          this.$message.error(msg);
        }
      })
    },





  }
};
</script>


