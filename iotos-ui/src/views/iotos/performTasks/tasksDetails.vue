<template>
  <div>

    <div class="main">
      <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
        <el-tab-pane name="1">
            <span slot="label">
              <span><i class="el-icon-circle-check"></i>
                {{ $t('tasksDetails.success') }}
                <el-badge :value="totalSum" v-if="totalSum>0" :max="99" class="myBadge" type="primary"/>
              </span>
            </span>
          <el-col :span="1.5" style="margin-bottom: 10px; margin-left: -10px">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              :loading="exportLoading"
              @click="successExport"
              v-hasPermi="['iotos:performTasks:tasksDetailsExport']"
            >{{ $t('common.export') }}
            </el-button>
          </el-col>

          <el-table v-loading="successLoading"   :data="successList" >
            <el-table-column :label="columns_3[0].label" align="center"  prop="iccid" width="180" />
            <el-table-column :label="columns_3[1].label" align="center"  prop="type" width="160" >
              <template slot-scope="scope">
                {{ tools.getDkeyValue(tasksDetailsTypeOptions, scope.row.type)}}
              </template>
            </el-table-column>
            <el-table-column :label="columns_3[2].label" align="center"  prop="state" width="60" >
              <template slot-scope="scope">
                {{ tools.getDkeyValue(resultStatusOptions, scope.row.state)}}
              </template>
            </el-table-column>
            <el-table-column :label="columns_3[3].label" align="center"  prop="starting_time" width="140" />
            <el-table-column :label="columns_3[4].label" align="center"  prop="end_time" width="140" />
            <el-table-column :label="columns_3[5].label" align="center"  prop="create_time" width="140" />
            <el-table-column :label="columns_3[6].label" align="center"  prop="remark" width="300" :show-overflow-tooltip="true" />
          </el-table>

          <pagination
            v-show="totalSum>0"
            :total="totalSum"
            :page.sync="successParams.pageNum"
            :limit.sync="successParams.pageSize"
            @pagination="pbSel(activeName)"
          />
        </el-tab-pane>

        <el-tab-pane name="2">
            <span slot="label">
              <span><i class="el-icon-warning-outline"></i>

                {{ $t('tasksDetails.fail') }}
                <el-badge :value="failtotal" v-if="failtotal>0" :max="99" class="myBadge"/>
              </span>
            </span>
          <el-col :span="1.5" style="margin-bottom: 10px; margin-left: -10px">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              :loading="exportFail"
              @click="failExport"
              v-hasPermi="['iotos:performTasks:tasksDetailsExport']"
            >{{ $t('common.export') }}
            </el-button>
          </el-col>

          <el-table v-loading="failLoading"   :data="failList" >
            <el-table-column :label="columns_3[0].label" align="center"  prop="iccid" width="180" />
            <el-table-column :label="columns_3[1].label" align="center"  prop="type" width="160" >
              <template slot-scope="scope">
                {{ tools.getDkeyValue(tasksDetailsTypeOptions, scope.row.type)}}
              </template>
            </el-table-column>
            <el-table-column :label="columns_3[2].label" align="center"  prop="state" width="60" >
              <template slot-scope="scope">
                {{ tools.getDkeyValue(resultStatusOptions, scope.row.state)}}
              </template>
            </el-table-column>
            <el-table-column :label="columns_3[3].label" align="center"  prop="starting_time" width="140" />
            <el-table-column :label="columns_3[4].label" align="center"  prop="end_time" width="140" />
            <el-table-column :label="columns_3[5].label" align="center"  prop="create_time" width="140" />
            <el-table-column :label="columns_3[6].label" align="center"  prop="remark" width="300" :show-overflow-tooltip="true" />
          </el-table>

          <pagination
            v-show="failtotal>0"
            :total="failtotal"
            :page.sync="failParams.pageNum"
            :limit.sync="failParams.pageSize"
            @pagination="pbSel(activeName)"
          />
        </el-tab-pane>

        <el-tab-pane name="0">
            <span slot="label">
              <span><i class="el-icon-loading"></i>
                {{ $t('tasksDetails.pending') }}
                <el-badge :value="pendingtotal" v-if="pendingtotal>0" :max="99" class="myBadge" type="info"/>
              </span>
            </span>
          <el-col :span="1.5" style="margin-bottom: 10px; margin-left: -10px">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              :loading="exportPending"
              @click="pendingExport"
              v-hasPermi="['iotos:performTasks:tasksDetailsExport']"
            >{{ $t('common.export') }}
            </el-button>
          </el-col>

          <el-table v-loading="pendingLoading"   :data="pendingList" >
            <el-table-column :label="columns_3[0].label" align="center"  prop="iccid" width="180" />
            <el-table-column :label="columns_3[1].label" align="center"  prop="type" width="160" >
              <template slot-scope="scope">
                {{ tools.getDkeyValue(tasksDetailsTypeOptions, scope.row.type)}}
              </template>
            </el-table-column>
            <el-table-column :label="columns_3[2].label" align="center"  prop="state" width="60" >
              <template slot-scope="scope">
                {{ tools.getDkeyValue(resultStatusOptions, scope.row.state)}}
              </template>
            </el-table-column>
            <el-table-column :label="columns_3[3].label" align="center"  prop="starting_time" width="140" />
            <el-table-column :label="columns_3[4].label" align="center"  prop="end_time" width="140" />
            <el-table-column :label="columns_3[5].label" align="center"  prop="create_time" width="140" />
            <el-table-column :label="columns_3[6].label" align="center"  prop="remark" width="300" :show-overflow-tooltip="true" />
          </el-table>

          <pagination
            v-show="pendingtotal>0"
            :total="pendingtotal"
            :page.sync="pendingParams.pageNum"
            :limit.sync="pendingParams.pageSize"
            @pagination="pbSel(activeName)"
          />
        </el-tab-pane>
      </el-tabs>
    </div>

    <span v-if="show_taskDs ==true && detailsEditexecute == false">
        {{ loadData(this.sel) }}
    </span>
  </div>

</template>

<script>
import {
  tasksDetailsList,
  tasksDetailsExport
} from "@/api/iotos/performTasks/performTasks";
import tools from "@/utils/iotos/tools";

export default {

  props: {
    show_taskDs: Boolean,//当前界面是否显示
    detailsEditexecute: Boolean,//加载是否 已执行
    sel: Object,
  },
  name: "tasksDetails",
  closePage(){
    this.$emit("setObj", "setShow_taskDs", false);
  },
  data() {
    return {
      activeName: "1",//选中 面板
      successList: [],//成功
      failList: [],//失败
      pendingList: [],//待处理
      successLoading:false,//成功 [遮罩]
      failLoading:false,//失败 [遮罩]
      pendingLoading:false,//待处理 [遮罩]

      tools: tools,
      //成功分页
      totalSum: 0,
      successParams: {
        pageNum: 1,
        pageSize: 10,
      },

      //失败分页
      failtotal: 0,// 总条数
      failParams: {
        pageNum: 1,
        pageSize: 10,
      },

      //待处理分页
      pendingtotal: 0,
      pendingParams: {
        pageNum: 1,
        pageSize: 10,
      },

      exportLoading: false,//控制成功导出
      exportFail: false,//控制失败导出
      exportPending: false,//控制待处理导出

      columns_3: [//执行任务详情
        {key: 0, label: this.$t('tasksDetails.table_3.i_1'), visible: true},
        {key: 1, label: this.$t('tasksDetails.table_3.i_2'), visible: true},
        {key: 2, label: this.$t('tasksDetails.table_3.i_3'), visible: true},
        {key: 3, label: this.$t('tasksDetails.table_3.i_4'), visible: true},
        {key: 4, label: this.$t('tasksDetails.table_3.i_5'), visible: true},
        {key: 5, label: this.$t('tasksDetails.table_3.i_6'), visible: true},
        {key: 6, label: this.$t('tasksDetails.table_3.i_7'), visible: true},
      ],
      tasksDetailsTypeOptions:[],//执行任务详情类型
      resultStatusOptions:[],//通用结果状态
    }
  },
  created() {
    //加载 执行任务详情类型
    if (tools.isNull(window['tasksDetailsTypeOptions'])){
      this.tasksDetailsTypeOptions = window['tasksDetailsTypeOptions'];
    } else {
      this.getDicts("tasks_details_type").then(response => {
        window['tasksDetailsTypeOptions'] = response.data;
        this.tasksDetailsTypeOptions = window['tasksDetailsTypeOptions'];
      });
    }
    //加载 通用结果状态
    if (tools.isNull(window['resultStatusOptions'])) {
      this.resultStatusOptions = window['resultStatusOptions'];
    } else {
      this.getDicts("result_status").then(response => {
        window['resultStatusOptions'] = response.data;
        this.resultStatusOptions = window['resultStatusOptions'];
      });
    }


  },



  methods: {
    loadData(sel) {
      this.$emit("setObj", "setDetailsEditexecute", true);//已加载

      this.pbSel('1');
      this.pbSel('2');
      this.pbSel('0');
    },


    getSuccess(map) {
      map.t_no = this.sel.t_no;
      let pwdStr = tools.encryptSy(map);
      tasksDetailsList(pwdStr).then(response => {
          let jsonObj = JSON.parse(tools.Decrypt(response));
          if (jsonObj.code == 200) {
            if (map.state == "1") {
              this.successList = jsonObj.data.data;
              this.totalSum = jsonObj.data.Pu.rowCount;
              if(this.totalSum>0){
                this.activeName = "1";
              }
            } else if (map.state == "2") {
              this.failList = jsonObj.data.data;
              this.failtotal = jsonObj.data.Pu.rowCount;
              if(this.failtotal>0){
                this.activeName = "2";
              }
            } else if (map.state == "0") {
              this.pendingList = jsonObj.data.data;
              this.pendingtotal = jsonObj.data.Pu.rowCount;
              if(this.pendingtotal>0){
                this.activeName = "0";
              }
            }
          } else {
            this.$message.error(jsonObj.msg);
          }
        }
      );
    },
    /**成功导出*/
    successExport() {
      let map = {};
      map.state = this.activeName;
      this.export(map);

    },
    /**失败导出*/
    failExport() {
      let map = {};
      map.state = this.activeName;
      this.export(map);
    },
    /**待处理导出*/
    pendingExport() {
      let map = {};
      map.state = this.activeName;
      this.export(map);
    },


    export(map){
      map.t_no = this.sel.t_no;
      let pwdStr = tools.encryptSy(map);
      tasksDetailsExport(pwdStr).then(response => {
        let jsonObj = JSON.parse(tools.Decrypt(response));
        let msg =  jsonObj.msg;
        if (jsonObj.code == 200) {
          this.$message.success(msg);
          this.$emit("setObj", "refreshTaskList", true);//刷新父窗体
        } else {
          this.$message.error(msg);
        }
      })
    },

    /**标签页*/
    handleClick(tab) {
      this.pbSel(tab.name);
    },
    pbSel(name) {
      let map = {};
      if (name == "1") {
        map = this.successParams;
      } else if (name == "2") {
        map = this.failParams;
      } else if (name == "0") {
        map = this.pendingParams;
      }
      map.state = name;
      this.getSuccess(map);
    },


  },
}
</script>

<style scoped>
.myBadge {
  top: 25px;
  right: 15px;
  float: right;
  -webkit-transform: translateY(-50%) translateX(100%);
}
</style>
