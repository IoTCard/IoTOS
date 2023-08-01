<template>
  <div class="app-container">
    <el-row :gutter="20">


      <el-col :span="4" :xs="24" v-show="option_show">
        <el-aside  style="background-color: #eef1f600;">
          <div class="head-container">
            <el-input
              v-model="deptName"
              :placeholder="$t('common.pleaseChoose')"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
            />
          </div>
          <div class="head-container">
            <el-checkbox v-model="deptExpand" @change="handleCheckedTreeExpand($event, 'dept')">{{ $t('common.expand_collapse') }}</el-checkbox>
            <el-checkbox v-model="deptNodeAll" @change="handleCheckedTreeNodeAll($event, 'dept')">{{ $t('common.selectAll_notSelectAll') }}</el-checkbox>
            <el-checkbox v-model="deptCheckStrictly" @change="handleCheckedTreeConnect($event, 'dept')">{{ $t('common.fatherSonlinkage') }}</el-checkbox>



            <el-tree
              :data="deptOptions"
              show-checkbox
              :filter-node-method="filterNode"
              :default-expand-all="treeExpand"
              node-key="id"
              ref="dept"
              highlight-current
              :check-strictly="!deptCheckStrictly"
              @node-click="handleNodeClick"
              :props="defaultProps"/>

          </div>

        </el-aside>
      </el-col>


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
                size="mini"
                @click="agentShow"
                v-hasPermi="['iotos:performTasks:list']"
              >{{ $t('common.belongsTo') }}
              </el-button>
            </el-col>

            <right-toolbar :showSearch.sync="showSearch" v-hasPermi="['iotos:performTasks:list']" @queryTable="getList"
                           :columns="columns"></right-toolbar>
          </el-row>
        </el-form>


        <el-table v-loading="loading" :data="dataList" >
          <el-table-column :label="columns[0].label" align="center"  prop="name" v-if="columns[0].visible" width="300" :show-overflow-tooltip="true" />
          <el-table-column :label="columns[1].label" align="center"  prop="t_no" v-if="columns[1].visible" width="210" >
              <template slot-scope="scope">
                <span v-if=" scope.row.w_details==1" @click="openExecuteTaskDetails(scope.row)" class="link-type">{{ scope.row.t_no }}</span>
                <span v-if=" scope.row.w_details==0" >{{ scope.row.t_no }}</span>
              </template>
          </el-table-column>
          <el-table-column :label="columns[2].label" align="center"  prop="ts_type" v-if="columns[2].visible" width="180">
            <template slot-scope="scope">
              <span @click="viewDetails(scope.row)" class="link-type">{{ tools.getDkeyValue(taskTypeOptions, scope.row.ts_type)}}</span>
            </template>
          </el-table-column>
          <el-table-column :label="columns[3].label" align="center" prop="starting_time" v-if="columns[3].visible" width="180" />
          <el-table-column :label="columns[4].label" align="center" prop="end_time" v-if="columns[4].visible" width="180" />
          <el-table-column :label="columns[5].label" align="center" prop="dept_name" v-if="columns[5].visible" />
          <el-table-column :label="columns[6].label" align="center" prop="user_name" v-if="columns[6].visible" width="100"   />
          <el-table-column :label="columns[7].label" align="center" prop="expiration_date" v-if="columns[7].visible" width="140"   />
          <el-table-column :label="columns[8].label" align="center" prop="remark" v-if="columns[8].visible" width="210" :show-overflow-tooltip="true"  />

          <el-table-column
            :label="$t('common.operate')"
            align="center"
            width="80"
            v-if="columns[9].visible"
            class-name="small-padding fixed-width"
            v-hasPermi="['iotos:sys:headquarters']"
          >

            <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  v-if="scope.row.ts_type=='cardDivid'"
                  @click="revokeCardDivid(scope.row)"
                  v-hasPermi="['iotos:sys:headquarters']"
                >{{ $t('common.revoke') }}</el-button>

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


    <el-dialog   width="900px"
               :title="$t('performTasks_index.fileList')" :visible.sync="taskFlie_show">
      <hr />
      <el-row :gutter="20">

        <el-table v-loading="fileLoading"  :data="flieList" >
          <el-table-column :label="columns_1[0].label" align="center"  prop="show_type"  width="160" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <span >{{ tools.getDkeyValue(taskNameOptions, scope.row.show_type) }}</span>
            </template>
          </el-table-column>

          <el-table-column :label="columns_1[1].label" align="center"  prop="starting_time" width="160" />
          <el-table-column :label="columns_1[2].label" align="center"  prop="end_time" width="160" />
          <el-table-column :label="columns_1[3].label" align="center"  prop="type" >
          <template slot-scope="scope">
            <span >{{ tools.getDkeyValue(flieTypeOptions, scope.row.type) }}</span>
          </template>
          </el-table-column>
          <el-table-column :label="columns_1[4].label" align="center"  prop="download_times" v-hasPermi="['iotos:sys:headquarters']" >
            <template slot-scope="scope">
              <span @click="openDownloadRecord(scope.row)" class="link-type">{{ scope.row.download_times }}</span>
            </template>
          </el-table-column>
          <el-table-column
            :label="$t('common.download')"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="downloadCsv(scope.row)"
              v-hasPermi="['iotos:performTasks:list']"
            >CSV</el-button>

            <el-button
              size="mini"
              type="text"
              @click="downloadExcle(scope.row)"
              v-hasPermi="['iotos:performTasks:list']"
            >XLS</el-button>

          </template>
        </el-table-column>

        </el-table>

      </el-row>
    </el-dialog>

    <el-dialog   width="720px"
               :title="$t('performTasks_index.downloadList')" :visible.sync="download_show">
      <el-row  :gutter="20">
        <el-table v-loading="downloadLoading"   :data="downloadList" >
          <el-table-column :label="columns_2[0].label" align="center"  prop="dl_dept_id" width="200" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              {{ tools.getkeyValue(deptsOptions, scope.row.dl_dept_id,"dept_id","dept_name")}}
            </template>
          </el-table-column>
          <el-table-column :label="columns_2[1].label" align="center"  prop="nick_name" width="180" />
          <el-table-column :label="columns_2[2].label" align="center"  prop="ip" width="140" />
          <el-table-column :label="columns_2[3].label" align="center"  prop="create_time" width="180" />
        </el-table>

        <pagination
          v-show="totalDownloadSum>0"
          :total="totalDownloadSum"
          :page.sync="downloadParams.pageNum"
          :limit.sync="downloadParams.pageSize"
          @pagination="findDownloadList()"
        />

      </el-row>
    </el-dialog>



    <el-dialog   width="1200px"
                 :title="$t('performTasks_index.tasksDetails')" :visible.sync="show_taskDs">
      <tasksDetails ref="tasksDetails"
                      @setObj="setObj" :detailsEditexecute="detailsEditexecute" :show_taskDs="show_taskDs" :sel="sel"
      />

    </el-dialog>





  </div>
</template>

<script>
import {
  listPerformTasks,
  findFile,
  downloadList
} from "@/api/iotos/performTasks/performTasks";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import tools from "@/utils/iotos/tools";
import {deptTreeSelect} from "@/api/system/user";
import {rollbackDivid} from "@/api/iotos/connect/card";
import {getDeptName} from "@/api/system/dept";
import tasksDetails from "./tasksDetails";




export default {
  name: "performTasks",
  components: {
    tasksDetails
  },
  data() {
    return {

      tools: tools,
      mainwidth: 24,//宽度
      loading: true,// 遮罩层
      showSearch: false, // 显示搜索条件
      // 总条数
      total: 0,
      dataList: null,
      title: "",// 弹出层标题
      typeOptions: [],
      taskTypeOptions: [],// 执行任务 字典
      taskNameOptions: [],//任务文件名
      flieTypeOptions: [],//文件来源类型



      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: "0",
        value: null,
        status: [],
      },
      // 列信息
      columns: [
        {key: 0, label: this.$t('performTasks_index.table.i_1'), visible: true},
        {key: 1, label: this.$t('performTasks_index.table.i_2'), visible: true},
        {key: 2, label: this.$t('performTasks_index.table.i_3'), visible: true},
        {key: 3, label: this.$t('performTasks_index.table.i_4'), visible: true},
        {key: 4, label: this.$t('performTasks_index.table.i_5'), visible: true},
        {key: 5, label: this.$t('performTasks_index.table.i_6'), visible: true},
        {key: 6, label: this.$t('performTasks_index.table.i_7'), visible: true},
        {key: 7, label: this.$t('performTasks_index.table.i_8'), visible: true},
        {key: 8, label: this.$t('performTasks_index.table.i_9'), visible: false},
        {key: 9, label: this.$t('common.operate'),visible: false}
      ],

      option_show: false, //企业所属查询
      deptCheckStrictly: true,
      deptExpand: true,
      deptNodeAll: false,
      deptName: undefined,// 企业名称
      deptOptions: undefined,// 企业树选项
      defaultProps: {
        children: "children",
        label: "label"
      },

      flieList:[],//执行任务 文件详情
      taskFlie_show: false,//详情查看

      columns_1: [//执行任务 文件列表
        {key: 0, label: this.$t('performTasks_index.table_1.i_1'), visible: true},
        {key: 1, label: this.$t('performTasks_index.table_1.i_2'), visible: true},
        {key: 2, label: this.$t('performTasks_index.table_1.i_3'), visible: true},
        {key: 3, label: this.$t('performTasks_index.table_1.i_4'), visible: true},
        {key: 4, label: this.$t('performTasks_index.table_1.i_5'), visible: true}
      ],
      treeExpand:true,//树组件是否展开
      downloadList:[],//下载记录
      download_show:false,//下载记录 显示
      columns_2: [//执行任务 文件列表
        {key: 0, label: this.$t('performTasks_index.table_2.i_1'), visible: true},
        {key: 1, label: this.$t('performTasks_index.table_2.i_2'), visible: true},
        {key: 2, label: this.$t('performTasks_index.table_2.i_3'), visible: true},
        {key: 3, label: this.$t('performTasks_index.table_2.i_4'), visible: true},
      ],
      deptsOptions: [],//企业数组
      totalDownloadSum: 0,
      downloadParams:{
        pageNum: 1,
        pageSize: 10,
      },
      downloadLoading: true,// 遮罩层 [下载列表]
      fileLoading: true,// 遮罩层 [文件列表]



      detailsEditexecute:false,//执行任务详情  是否已经加载
      show_taskDs:false,//执行任务详情  是否显示
      sel:{},//选中数据

    };
  },
  created() {

    //加载 企业名称
    if (tools.isNull(window['deptsOptions'])) {
      this.deptsOptions = window['deptsOptions'];
    } else {
      getDeptName().then(response => {
        let jsonObj = JSON.parse(tools.Decrypt(response));
        window['deptsOptions'] = jsonObj.data;
        this.deptsOptions = window['deptsOptions'];
      });
    }

    if (tools.isNull(window['task_sel_type'])) {
      this.typeOptions = window['task_sel_type'];
    } else {
      this.getDicts("iotos_task_sel_type").then(response => {
        window['task_sel_type'] = response.data;
        this.typeOptions = window['task_sel_type'];
      });
    }


    //加载 执行任务类别
    if (tools.isNull(window['taskTypeOptions'])) {
      this.taskTypeOptions = window['taskTypeOptions'];
    } else {
      this.getDicts("iotos_task_type").then(response => {
        window['taskTypeOptions'] = response.data;
        this.taskTypeOptions = window['taskTypeOptions'];
      });
    }


    //加载 任务文件名
    if (tools.isNull(window['taskNameOptions'])) {
      this.taskNameOptions = window['taskNameOptions'];
    } else {
      this.getDicts("iotos_task_name").then(response => {
        window['taskNameOptions'] = response.data;
        this.taskNameOptions = window['taskNameOptions'];
      });
    }

    //加载 任务文件名
    if (tools.isNull(window['flieTypeOptions'])) {
      this.flieTypeOptions = window['flieTypeOptions'];
    } else {
      this.getDicts("task_flie_type").then(response => {
        window['flieTypeOptions'] = response.data;
        this.flieTypeOptions = window['flieTypeOptions'];
      });
    }



    this.getTreeselect();
    this.getList();


  },
  methods: {

    //提供给子组件 修改父组件属性
    setObj(Key, obj) {
      switch (Key) {
        case 'setDetailsEditexecute':
          this.detailsEditexecute = obj;//设置  执行任务详情  是否已经加载
          break;
        case 'setShow_taskDs':
          this.show_taskDs = obj;//设置  执行任务详情  是否显示
          break;
        case 'refreshTaskList'://刷新当前界面
          this.getList();
          break;
      }
    },

    /**
     * 下载 CSV
     * @param row
     */
    downloadCsv(row){
      let _this = this;
      tools.downloadCsv(row.url,{"t_no":row.t_no,"fid":row.id});
      //延迟加载 刷新详情
      setTimeout(function () {
        if(_this.taskFlie_show){
          _this.finddata(row);
        }
      }, 400);
    },

    /**
     * 下载 CSV 转换为 Excle
     * @param row
     */
    downloadExcle(row){
      let _this = this;
      tools.downloadExcle(row.url,{"t_no":row.t_no,"fid":row.id});
      //延迟加载 刷新详情
      setTimeout(function () {
        if(_this.taskFlie_show){
          _this.finddata(row);
        }
      }, 2000);
    },



    //打开卡列表 更新
    openRenew(){
      this.renew_wExecute = false;
      this.renew_show = true;
    },


    //下载次数详情
    openDownloadRecord(row){
      this.downloadParams.t_no = row.t_no;
      this.downloadParams.fid = row.id;
      this.findDownloadList();
    },
    findDownloadList() {
      this.downloadLoading = true;
      let pwdStr = tools.encryptSy(this.downloadParams);
      downloadList(pwdStr).then(response => {
          let jsonObj = JSON.parse(tools.Decrypt(response));
          if (jsonObj.code == 200) {
            this.downloadList = jsonObj.data.data;
            this.totalDownloadSum = jsonObj.data.Pu.rowCount;
            this.download_show = true;
          } else {
            this.$message.error(jsonObj.msg);
          }
          this.downloadLoading = false;
        }
      );
    },






    /*企业查询拓展*/
    agentShow() {
      let _this = this;
      _this.option_show = !_this.option_show;
      _this.mainwidth = _this.option_show ? 20 : 24;
    },
    /** 查询企业下拉树结构 */
    getTreeselect() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
        /*if(this.deptOptions.length>20){//企业数据大于 20条之后 不做展开显示
          this.treeExpand = false;
        }*/
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },

    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.dept_id = [data.id];
      this.getList();
    },
    // 树权限（展开/折叠）
    handleCheckedTreeExpand(value, type) {
      if (type == 'dept') {
        let treeList = this.deptOptions;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.dept.store.nodesMap[treeList[i].id].expanded = value;
        }
      }
    },
    // 树权限（全选/全不选）
    handleCheckedTreeNodeAll(value, type) {
      if (type == 'dept') {
        //console.log(value);
        this.$refs.dept.setCheckedNodes(value ? this.deptOptions : []);
      }
    },
    // 树权限（父子联动）
    handleCheckedTreeConnect(value, type) {
      if (type == 'menu') {
        this.menuCheckStrictly = value ? true : false;
      } else if (type == 'dept') {
        this.$refs.dept.setCheckedNodes(false ? this.deptOptions : []);
        this.deptCheckStrictly = value ? true : false;

      }
    },



    /*获取查询参数*/
    getParams() {
      if (this.$refs.dept != undefined & this.$refs.dept != null & this.$refs.dept != 'undefined') {
        if (this.$refs.dept.getCheckedKeys().length > 0) {
          this.queryParams.dept_id = this.$refs.dept.getCheckedKeys();
        }
      }
    },

    /** 查询 */
    getList() {
      this.loading = true;
      this.getParams();
      let pwdStr = tools.encryptSy(this.queryParams);
      listPerformTasks(pwdStr).then(response => {
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

    /** 打开执行任务详情 */
    openExecuteTaskDetails(row) {
      this.show_taskDs = true;
      this.detailsEditexecute = false;
      this.sel.t_no = row.t_no;
    },

    /** 详情按钮操作 */
    viewDetails(row) {
      this.finddata(row);
    },
    /*获取详细信息*/
    finddata(row) {
      this.fileLoading = true;
      this.flieList = [];//清空数据
      let map = {};
      map.t_no = row.t_no;
      let pwdStr = tools.encryptSy(map);
      findFile(pwdStr).then(response => {
        let jsonObj = JSON.parse(tools.Decrypt(response));
        if (jsonObj.code == 200) {
          this.flieList = jsonObj.data;
          this.taskFlie_show = true;
        } else {
          if (jsonObj.deptId == "100") {
            this.$message.error(jsonObj.msg);
          } else {
            this.$message.error(jsonObj.msg);
          }
        }
        this.fileLoading = false;
      });
    },


    //撤销划卡
    revokeCardDivid(row){
      let map = {"t_no":row.t_no};
      let pwdStr = tools.encryptSy(map);
      tools.openAsk(this, 'warning', this.$t("common.ask.ask")+" "+this.$t("performTasks_index.ask.revokeCardDivid_1")+" [ " + row.dept_name + " ] "+this.$t("performTasks_index.ask.revokeCardDivid_2"), this.revokeDivid, pwdStr);

    },

    //划卡 撤销
    revokeDivid(pwdStr) {
      rollbackDivid(pwdStr).then(response => {
        let jsonObj = JSON.parse(tools.Decrypt(response));
        let msg =  jsonObj.msg;
        if (jsonObj.code == 200) {
          this.$modal.msgSuccess(msg);
        } else {
          this.$message.error(msg);
        }
      })
    },


  }
};
</script>


