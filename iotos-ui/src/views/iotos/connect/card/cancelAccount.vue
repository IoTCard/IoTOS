<template>
  <div class="app-container">
    <el-row :gutter="20">


      <el-col :span="4" :xs="24" v-show="option_show">
        <el-aside  style="background-color: #eef1f600;">
          <el-input
            v-model="deptName"
            :placeholder="$t('common.pleaseChoose')"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 10px;"
          />
          <div class="head-container">
            <el-checkbox v-model="deptExpand" @change="handleCheckedTreeExpand($event, 'dept')">{{ $t('common.expand_collapse') }}</el-checkbox>
            <el-checkbox v-model="deptNodeAll" @change="handleCheckedTreeNodeAll($event, 'dept')">{{ $t('common.selectAll_notSelectAll') }}</el-checkbox>
            <el-checkbox v-model="deptCheckStrictly" @change="handleCheckedTreeConnect($event, 'dept')">{{ $t('common.fatherSonlinkage') }}</el-checkbox>



            <el-tree
              :data="deptOptions"
              show-checkbox
              :filter-node-method="filterNode"
              default-expand-all
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


        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="queryParams.advancedSearch" label-width="68px">

          <el-form-item :label="$t('cardInfoDetails.tFrom.status_show_id')" >
            <el-select
              v-model="queryParams.status_show_id"
              :placeholder="$t('cardInfoDetails.tFrom.status_show_id')"
              clearable
              size="small"
              style="width: 110px"
            >
              <el-option
                v-for="dict in cardStatusShowCancelOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>

          <el-form-item :label="$t('cardInfoDetails.tFrom.status_id')"  v-hasPermi="['iotos:sys:headquarters']">
            <el-select
              v-model="queryParams.status_id"
              :placeholder="$t('cardInfoDetails.tFrom.status_id')"
              clearable
              size="small"
              style="width: 130px"
            >
              <el-option
                v-for="dict in cardStatusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>

          <el-form-item  v-hasPermi="['iotos:card:getGrouping']">
            <span class="el-form-item__label" style="font-weight: 700;">{{ $t('cardInfoDetails.tFrom.customize_grouping') }}</span>
            <el-select
              v-model="queryParams.customize_grouping"
              :placeholder="$t('cardInfoDetails.tFrom.customize_grouping')"
              clearable
              size="small"
              style="width: 160px"
            >
              <el-option
                v-for="dict in cardGroupingOptions"
                :label="dict"
                :value="dict"
              />
            </el-select>
          </el-form-item>


          <el-form-item :label="$t('common.timeType')" >
            <el-select
              v-model="queryParams.dateType"
              :placeholder="$t('common.timeType')"
              clearable
              size="small"
              style="width: 110px"
            >
              <el-option
                v-for="dict in dateTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="selTime"
              type="datetimerange"
              :picker-options="pickerOptions"
              :range-separator="$t('common.to')"
              value-format="yyyy-MM-dd"
              :start-placeholder="$t('common.startDate')"
              :end-placeholder="$t('common.endDate')"
              style="width: 360px"
              align="right">
            </el-date-picker>
          </el-form-item>

          <el-form-item :label="$t('common.sortType')" >
            <el-select
              v-model="queryParams.order_by_type"
              :placeholder="$t('common.sortType')"
              clearable
              size="small"
              style=" width: 110px"
            >
              <el-option
                v-for="dict in orderByTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />

            </el-select>
            <el-select
              v-model="queryParams.order_by_rule"
              :placeholder="$t('common.descendingOrder')"
              clearable
              size="small"
              style="width: 110px"
            >
              <el-option
                v-for="dict in orderByRuleOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />

            </el-select>
          </el-form-item>

        </el-form>

        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="queryParams.advancedSearch">
          <el-form-item prop="status">
            <span class="el-form-item__label" style="font-weight: 700;">{{ $t('common.startStopConditions') }} </span>
            <el-select
              v-model="queryParams.betweenType"
              :placeholder="$t('common.startStopConditions')"
              clearable
              size="small"
              style="width: 110px"
            >

              <el-option
                v-for="dict in betweenTypeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model="queryParams.startValue"
              :placeholder="$t('common.startNumberSegment')"
              clearable
              size="small"
              style="width: 200px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-input
              v-model="queryParams.endValue"
              :placeholder="$t('common.endNumberSegment')"
              clearable
              size="small"
              style="width: 200px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>



          <el-form-item :label="$t('common.multiDimension')" label-width="67px">
            <el-select
              v-model="queryParams.dimensionField"
              :placeholder="$t('common.dimensionField')"
              clearable
              style="width: 110px"
            >
              <el-option
                v-for="dict in dimensionFieldOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-input
              v-model="queryParams.dimensionValue"
              :placeholder="$t('common.dimensionValue')"
              clearable
              size="small"
              style="width: 360px"
              @keyup.enter.native="handleQuery"
            >
              <el-select
                v-model="queryParams.compareType"
                :placeholder="$t('common.dimensionCondition')"
                clearable
                slot="prepend"
                style="width: 110px"
              >
                <el-option
                  v-for="dict in compareTypeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-input>
          </el-form-item>



          <el-row :gutter="10" class="mb8" style="margin-left: 15px;">
            <el-form-item prop="status">
              <span class="el-form-item__label" style="font-weight: 700;">{{  $t('cardInfoDetails.tFrom.operator') }}</span>
              <el-select
                v-model="queryParams.operator"
                :placeholder="$t('cardInfoDetails.tFrom.operator')"
                clearable
                multiple
                size="small"
                style="width: 320px"
              >
                <el-option
                  v-for="dict in operatorOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />
              </el-select>
            </el-form-item>

            <el-form-item>
            <span class="el-form-item__label" style="font-weight: 700;"
                  v-hasPermi="['iotos:card:queryChannel']">{{  $t('cardInfoDetails.tFrom.channel_id') }}</span>
              <el-select
                v-model="queryParams.channel_id"
                :placeholder="$t('cardInfoDetails.tFrom.channel_id')"
                clearable
                multiple
                size="small"
                style="width: 690px"
              >
                <el-option
                  v-for="dict in channelOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                />

              </el-select>
            </el-form-item>



          </el-row>

        </el-form>





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
                v-hasPermi="['iotos:card:list']"
              >{{ $t('common.belongsTo') }}
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="info"
                plain
                icon="el-icon-upload2"
                size="mini"
                @click="openImportTable"
                v-hasPermi="['iotos:card:add']"
              >{{ $t('common.import') }} </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
                v-hasPermi="['iotos:card:export']"
              >{{ $t('common.export') }}</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                plain
                icon="el-icon-share"
                size="mini"
                @click="toDivide"
                v-hasPermi="['iotos:card:divideCard']"
              >{{ $t('common.toDivide') }}</el-button>
            </el-col>

            <el-col :span="1.5">
              <el-dropdown>
                <el-button
                  type="warning"
                  plain
                  icon="el-icon-user-solid"
                  size="mini"
                  style="width: 110px;"
                >{{ $t('common.batchOperation') }}
                </el-button>
                <el-dropdown-menu slot="dropdown">


                  <el-dropdown-item>
                    <el-button
                      type="success"
                      plain
                      icon="el-icon-mobile-phone"
                      style="width: 150px;margin-bottom:10px;"
                      size="mini"
                      @click="openQueryIMEI"
                      v-hasPermi="['iotos:card:synImei']"
                    >{{ $t('card_index.batchQueryIMEI') }}
                    </el-button>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-button
                      type="danger"
                      plain
                      icon="el-icon-switch-button"
                      style="width: 150px;margin-bottom:10px;"
                      size="mini"
                      @click="openBusinessHandling"
                      v-hasPermi="['iotos:card:businessHandling']"
                    >{{ $t('card_index.batchShutdown') }}
                    </el-button>
                  </el-dropdown-item>



                  <el-dropdown-item>
                    <el-button
                      type="warning"
                      plain
                      icon="el-icon-refresh"
                      style="width: 150px;margin-bottom:10px;"
                      size="mini"
                      @click="openRenew"
                      v-hasPermi="['iotos:card:edit']"
                    >{{ $t('card_index.updateCardInformation') }}
                    </el-button>
                  </el-dropdown-item>

                </el-dropdown-menu>
              </el-dropdown>
            </el-col>



            <right-toolbar :showSearch.sync="queryParams.advancedSearch" v-hasPermi="['iotos:card:list']" @queryTable="getList"
                           :columns="columns"></right-toolbar>
          </el-row>
        </el-form>


        <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
          <el-table-column :label="columns[0].label" align="center"  prop="c_no" v-if="columns[0].visible" width="140" />
          <el-table-column :label="columns[1].label" align="center"  prop="msisdn" v-if="columns[1].visible" width="140">
            <template slot-scope="scope">
              <span @click="viewDetails(scope.row)" class="link-type">{{ scope.row.msisdn }}</span>
            </template>
          </el-table-column>
          <el-table-column :label="columns[2].label" align="center" prop="iccid" v-if="columns[2].visible" width="180"/>
          <el-table-column :label="columns[3].label" align="center" prop="imsi" v-if="columns[3].visible" width="140" />
          <el-table-column :label="columns[4].label" align="center" prop="imei" v-if="columns[4].visible" width="140" />
          <el-table-column :label="columns[5].label" align="center"  v-if="columns[5].visible" width="80">
            <template slot-scope="scope">
              <span >{{ tools.getDkeyValue(cardStatusShowOptions, scope.row.status_show_id)}}</span>
            </template>
          </el-table-column>
          <el-table-column :label="columns[6].label" align="center"  v-if="columns[6].visible" width="180"  :show-overflow-tooltip="true" >
            <template slot-scope="scope">
              <span >{{  tools.getDkeyValue(channelOptions, scope.row.channel_id)}}  </span>
            </template>
          </el-table-column>
          <el-table-column :label="columns[7].label" align="center"  v-if="columns[7].visible"    >
            <template slot-scope="scope">
              <span >{{ tools.getkeyValue(deptsOptions, scope.row.dept_id,"dept_id","dept_name")}}</span>
            </template>
          </el-table-column>
          <el-table-column :label="columns[8].label" align="center" prop="c_used" v-if="columns[8].visible" width="80"/>
          <el-table-column :label="columns[9].label" align="center" prop="c_left" v-if="columns[9].visible" width="80"/>

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
                v-hasPermi="['iotos:card:edit']"
              >{{ $t('common.edie') }}</el-button>
              <el-button
                size="mini"
                type="text"
                @click="viewDetails(scope.row)"
                v-hasPermi="['iotos:card:find']"
              >{{ $t('common.details') }}</el-button>

              <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" >
                <el-button size="mini" type="text" icon="el-icon-d-arrow-right">{{ $t('common.moreOperations') }}</el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="synInfo" v-hasPermi="['iotos:card:synInfo']">{{ $t('card_index.oneKeySync') }}</el-dropdown-item>
                  <el-dropdown-item command="synFlow"  v-hasPermi="['iotos:card:synFlow']">{{ $t('card_index.syncUsage') }}</el-dropdown-item>
                  <el-dropdown-item command="synStatus" v-hasPermi="['iotos:card:synStatus']">{{ $t('card_index.syncStatus') }}</el-dropdown-item>
                  <el-dropdown-item command="synActivateDate" v-hasPermi="['iotos:card:synActivateDate']">{{ $t('card_index.synActivateDate') }}</el-dropdown-item>
                  <el-dropdown-item command="synImei" v-hasPermi="['iotos:card:synImei']">{{ $t('card_index.syncIMEI') }}</el-dropdown-item>
                  <el-dropdown-item command="synRealName" v-hasPermi="['iotos:card:synRealName']">{{ $t('card_index.syncRealName') }}</el-dropdown-item>


                </el-dropdown-menu>
              </el-dropdown>



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


    <!-- 导入 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          {{ $t('common.uploadText') }}
          <em>{{ $t('common.uploadTextEm') }}</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-link type="info" style="font-size:18px;color: #03A9F4;vertical-align: baseline;" @click="importTemplate"> {{ $t('common.downloadTemplate') }}</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">{{ $t('common.uploadTip') }}</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-if="submitFileFormBtn" @click="submitFileForm">{{ $t('common.sure') }}</el-button>
        <el-button @click="upload.open = false">{{ $t('common.cancel') }}</el-button>
      </div>
    </el-dialog>


    <!-- 更新卡列表-->
    <el-dialog  :title="$t('common.renew')" :visible.sync="renew_show" width="600px" :close-on-click-modal="false" append-to-body>

      <cardInfoRenew ref="cardInfoRenew"  style="margin-top: 10px;"
                     @setObj="setObj" :renew_wExecute="renew_wExecute" :renew_show="renew_show"   :numberTypeOptions="numberTypeOptions"
      />
    </el-dialog>



    <!--划卡-->
    <el-dialog  :visible.sync="divide_show" width="460px" append-to-body>
      <el-form ref="formDivide" :model="formDivide" label-width="80px">
        <el-row>
          <el-col :span="24">

            <div class="head-container">
              <el-input
                v-model="treeName"
                :placeholder="$t('common.rs.deptName')"
                clearable
                size="small"
                prefix-icon="el-icon-search"
                style="margin-bottom: 10px"
              />
            </div>
            <div class="head-container">
              <el-tree :data="deptOptions" :check-on-click-node="false" ref="tree" :filter-node-method="filterNode"
                       show-checkbox :check-strictly="true"  node-key="id"  :default-expand-all="treeExpand"
                       :props="defaultProps" @check-change="treeNodeClick"  :empty-text="$t('common.noData')">
              </el-tree>
            </div>
          </el-col>

        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button v-hasPermi="['iotos:card:divide']" v-if="divideSBtn" type="primary" @click="divide">{{ $t('common.preserve') }}</el-button>
      </div>
    </el-dialog>


    <!-- 卡列表更新 -->
    <el-dialog  :title="$t('common.renew')" :visible.sync="renew_show" width="600px" :close-on-click-modal="false" append-to-body>

      <cardInfoRenew ref="cardInfoRenew"
                     @setObj="setObj" :renew_wExecute="renew_wExecute" :renew_show="renew_show"   :numberTypeOptions="numberTypeOptions"
      />
    </el-dialog>

    <!--卡列表详情-->
    <el-dialog :visible.sync="show_ds" width="85%" :close-on-click-modal="false" append-to-body>
      <!-- 基础信息 子组件-->
      <cardInfoDetails ref="cardInfoDetails"
                       @setObj="setObj"  :detailsEditexecute="detailsEditexecute" :show_Details="show_ds"   :sel="sel"
                       :deptsOptions="deptsOptions" :cardStatusShowOptions="cardStatusShowOptions"   :channelOptions="channelOptions"
      />

      <!-- 拓展模块 信息 子组件-->
      <cardInfoModule ref="cardInfoModule"
                      @setObj="setObj"  :moduleEditexecute="moduleEditexecute" :show_Details="show_ds"   :sel="sel"
      />


    </el-dialog>


    <!-- API业务办理 -->
    <el-dialog  :title="$t('cardApiBusinessHandling.title')" :visible.sync="apiBH_show" width="600px" :close-on-click-modal="false" append-to-body>

      <cardApiBusinessHandling ref="cardApiBusinessHandling"
                               @setObj="setObj" :apiBH_wExecute="apiBH_wExecute" :apiBH_show="apiBH_show"
      />
    </el-dialog>


    <!--    查询IMEI -->
    <el-dialog  :title="$t('apiInquireIMEI.title')" :visible.sync="IMEI_show" width="600px" :close-on-click-modal="false" append-to-body>

      <apiInquireIMEI ref="cardApiBusinessHandling"
                      @setObj="setObj" :apiBH_wExecute="IMEI_wExecute" :apiBH_show="IMEI_show"
      />
    </el-dialog>

    <!--    修改卡信息-->
    <el-dialog  :title="$t('card_index.editCard')" :visible.sync="edit_show" width="600px" :close-on-click-modal="false" append-to-body>
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
        <el-form-item :label="$t('card_index.updForm.delivery_date')"  >
          <el-date-picker
            v-model="updForm.delivery_date"
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


    </el-dialog>

  </div>
</template>

<script>
import {
  listCard,
  cardExport,
  divideCard,
  getGrouping,
  synInfo,
  synFlow,
  synStatus,
  synRealName,
  synActivateDate,
  synImei,
  editCardPublic
} from "@/api/iotos/connect/card";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import tools from "@/utils/iotos/tools";
import {getToken} from "@/utils/auth";
import {deptTreeSelect} from "@/api/system/user";
import {getDeptName} from "@/api/system/dept";
import cardInfoDetails from "./cardInfoDetails";
import cardInfoModule from "./cardInfoModule";
import cardApiBusinessHandling from "./batchOperation/cardApiBusinessHandling";
import cardInfoRenew from "./batchOperation/cardInfoRenew";
import apiInquireIMEI from "./batchOperation/apiInquireIMEI";


export default {
  name: "cancelAccount",
  components: {
    cardInfoRenew,
    cardInfoDetails,
    cardInfoModule,
    cardApiBusinessHandling,
    apiInquireIMEI
  },
  watch: {
    // 根据名称筛选企业树
    deptName(val) {
      this.$refs.dept.filter(val);
    },
    // 根据名称筛选企业树
    treeName(val) {
      this.$refs.tree.filter(val);
    },

    //高级查询 是否显示
    'queryParams.advancedSearch'(val){
      if(val){//展开高级查询时 触发获取对应字典数据 [减少用不到的系统请求，优化界面加载速度]
        //加载 排序规则
        if (tools.isNull(window['orderByRuleOptions'])) {
          this.orderByRuleOptions = window['orderByRuleOptions'];
        } else {
          this.getDicts("order_by_rule").then(response => {
            window['orderByRuleOptions'] = response.data;
            this.orderByRuleOptions = window['orderByRuleOptions'];
          });
        }
        //加载 排序依据
        if (tools.isNull(window['orderByTypeOptions'])) {
          this.orderByTypeOptions = window['orderByTypeOptions'];
        } else {
          this.getDicts("card_order_by_type").then(response => {
            window['orderByTypeOptions'] = response.data;
            this.orderByTypeOptions = window['orderByTypeOptions'];
          });
        }
        //加载 维度条件
        if (tools.isNull(window['compareTypeOptions'])) {
          this.compareTypeOptions = window['compareTypeOptions'];
        } else {
          this.getDicts("compare_type").then(response => {
            window['compareTypeOptions'] = response.data;
            this.compareTypeOptions = window['compareTypeOptions'];
          });
        }
        //加载 多维度字段
        if (tools.isNull(window['dimensionFieldOptions'])) {
          this.dimensionFieldOptions = window['dimensionFieldOptions'];
        } else {
          this.getDicts("card_dimension_field").then(response => {
            window['dimensionFieldOptions'] = response.data;
            this.dimensionFieldOptions = window['dimensionFieldOptions'];
          });
        }

        //加载 起止条件类型
        if (tools.isNull(window['betweenTypeOptions'])) {
          this.betweenTypeOptions = window['betweenTypeOptions'];
        } else {
          this.getDicts("card_sel_between_type").then(response => {
            window['betweenTypeOptions'] = response.data;
            this.betweenTypeOptions = window['betweenTypeOptions'];
          });
        }


        //加载 时间查询类型
        if (tools.isNull(window['dateTypeOptions'])) {
          this.dateTypeOptions = window['dateTypeOptions'];
        } else {
          this.getDicts("card_sel_date_type").then(response => {
            window['dateTypeOptions'] = response.data;
            this.dateTypeOptions = window['dateTypeOptions'];
          });
        }

       if (tools.isNull(window['cardGroupingOptions'])) {
          this.cardGroupingOptions = window['cardGroupingOptions'];
        } else {
          this.loadCardGrouping();//加载 分组
        }


      }
    },



  },

  data() {
    return {
      tools: tools,
      show_ds: false,//详情查看
      detailsEditexecute: true,//详情基础信息 是否已加载
      moduleEditexecute: true,//信息拓展模块 是否已加载


      mainwidth: 24,//宽度
      loading: true,// 遮罩层
      ids: [], // 选中数组
      single: true,// 非单个禁用
      multiple: true,  // 非多个禁用
      total: 0,// 总条数
      dataList: null,
      title: "",// 弹出层标题
      typeOptions: [],//查询类型
      formCol: 18,

      cardStatusShowCancelOptions: [{dictValue:'7',dictLabel:'已销户'}],

      cardStatusShowOptions: [],
      templateOptions: [],// 模板 字典
      channelOptions: [],//通道



      // 查询参数
      queryParams: {
        advancedSearch: false,// 显示搜索条件
        status_id: null,
        dateType: null,
        pageNum: 1,
        pageSize: 10,
        type: null,
        value: null,
        status: [],
        startValue: null,
        endValue: null,
        customize_grouping: null,
        betweenType: null,
        dimensionField: null,
        compareType: null,
        dimensionValue: null,
        order_by_type: null,
        order_by_rule: null,
        operator: null,

      },
      // 列信息
      columns: [
        {key: 0, label: this.$t('card_index.table.i_1'), visible: false},
        {key: 1, label: this.$t('card_index.table.i_2'), visible: true},
        {key: 2, label: this.$t('card_index.table.i_3'), visible: true},
        {key: 3, label: this.$t('card_index.table.i_4'), visible: true},
        {key: 4, label: this.$t('card_index.table.i_5'), visible: true},
        {key: 5, label: this.$t('card_index.table.i_6'), visible: true},
        {key: 6, label: this.$t('card_index.table.i_7'), visible: true},
        {key: 7, label: this.$t('card_index.table.i_8'), visible: true},
        {key: 8, label: this.$t('card_index.table.i_9'), visible: true},
        {key: 9, label: this.$t('card_index.table.i_10'), visible: true}
      ],
      showButton:false,

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
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/iotos/card/save"
      },
      submitFileFormBtn: true,//导入 btn

      numberTypeOptions:[],//卡号类型
      renew_wExecute: false,//卡信息修改 界面是否已加载
      renew_show: false,//卡信息修改 展示



      divide_show: false,//卡划分
      divideSBtn: true,//卡划分 保存
      treeName: undefined,
      formDivide: {
        dept_id: '',
        user_id: '',
      },
      userArr: [],//企业下用户数组
      selectList: [],
      treeExpand:true,//树组件是否展开
      deptsOptions: [],//企业数组

      sel:{//选中数据
        c_no:''
      },
      cardStatusOptions:[],//卡状态描述
      dateTypeOptions:[],//卡列表时间类型
      pickerOptions: {
        shortcuts: [{
          text: this.$t('common.lastWeek'),
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: this.$t('common.lastRecentMonth'),
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: this.$t('common.lastThreeMonths'),
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      selTime: [],//时间选择
      headquartersBool:false,//是否总部
      cardGroupingOptions: [],//卡列表-分组
      betweenTypeOptions: [],//起止条件类型 [字典]
      dimensionFieldOptions: [],//多维度字段 [字典]
      compareTypeOptions: [],//维度条件 [字典]
      orderByTypeOptions: [],//排序依据 [字典]
      orderByRuleOptions: [],//排序规则 [字典]
      operatorOptions: [],//运营类型 [字典]

      apiBH_wExecute: false,//API业务办理 界面是否已加载
      apiBH_show: false,//API业务办理 展示
      IMEI_wExecute: false,//查询IMEI 界面是否已加载
      IMEI_show: false,//查询IMEI 展示
      edit_show: false,//编辑卡信息 展示


      wUpdBatch: false,//是否批量操作
      updForm:{
        remarks:null,
        updateNotFilled:'0',
        delivery_date:null,
        imei:null,
        customize_grouping:null,
        iccid:null,
      },
      deliveryOptions: {
        shortcuts: [{
          text: this.$t("common.today"),
          onClick(picker) {
            picker.$emit('pick', tools.gitData());
          }
        }, {
          text: this.$t("common.yesterday"),
          onClick(picker) {
            picker.$emit('pick', tools.getBeforeDate(1));
          }
        }, {
          text: this.$t("common.weekAgo"),
          onClick(picker) {
            picker.$emit('pick', tools.getBeforeDate(7));
          }
        }]
      },
      whetherOptions: [],//是否



    };
  },
  created() {
    let str = this.$route.params;
    //console.log(str)


    //加载 是否
    if (tools.isNull(window['whetherOptions'])) {
      this.whetherOptions = window['whetherOptions'];
    } else {
      this.getDicts("iotos_whether").then(response => {
        window['whetherOptions'] = response.data;
        this.whetherOptions = window['whetherOptions'];
      });
    }

    //加载 卡状态描述
    if (tools.isNull(window['cardStatusOptions'])) {
      this.cardStatusOptions = window['cardStatusOptions'];
    } else {
      this.getDicts("card_status_id").then(response => {
        window['cardStatusOptions'] = response.data;
        this.cardStatusOptions = window['cardStatusOptions'];
      });
    }

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


    //加载 卡号类型
    if (tools.isNull(window['numberTypeOptions'])){
      this.numberTypeOptions = window['numberTypeOptions'];
    }else{
      this.getDicts("number_type").then(response => {
        window['numberTypeOptions'] = response.data;
        this.numberTypeOptions = window['numberTypeOptions'];
      });
    }


    if (tools.isNull(window['card_sel_type'])) {
      this.typeOptions = window['card_sel_type'];
    } else {
      this.getDicts("iotos_card_sel_type").then(response => {
        window['card_sel_type'] = response.data;
        this.typeOptions = window['card_sel_type'];
      });
    }


    //加载 通道
    if (tools.isNull(window['channelOptions'])) {
      this.channelOptions = window['channelOptions'];
    } else {
      let pwdStr = tools.encryptSy({});
      this.getNameOpen(pwdStr).then(response => {
        let jsonObj = JSON.parse(tools.Decrypt(response));
        window['channelOptions'] = jsonObj.data;
        this.channelOptions = window['channelOptions'];
      });
    }


    //加载 卡状态
    if (tools.isNull(window['cardStatusShowOptions'])) {
      this.cardStatusShowOptions = window['cardStatusShowOptions'];
    } else {
      this.getDicts("card_status_show_id").then(response => {
        window['cardStatusShowOptions'] = response.data;
        this.cardStatusShowOptions = window['cardStatusShowOptions'];
      });
    }



    this.getTreeselect();

    this.queryParams.type = "0";
    this.getList();


  },
  methods: {

    loadCardGrouping() {
      this.cardGroupingOptions = [];
      let map = {};
      if (this.$refs.dept != undefined & this.$refs.dept != null & this.$refs.dept != 'undefined') {
        if (this.$refs.dept.getCheckedKeys().length > 0) {
          map.dept_id = this.$refs.dept.getCheckedKeys();
        }
      }
      let pwdStr = tools.encryptSy(map);
      getGrouping(pwdStr).then(response => {
          let jsonObj = JSON.parse(tools.Decrypt(response));
          if (jsonObj.code == 200) {
            window['cardGroupingOptions'] = jsonObj.data;
            this.cardGroupingOptions = window['cardGroupingOptions'];
          } else {
            this.msgError(jsonObj.msg);
          }
          this.loading = false;
        }
      );

    },




    //打开卡列表 更新
    openRenew(){
      this.renew_wExecute = false;
      this.renew_show = true;
    },

    //打开 批量API业务办理
    openBusinessHandling(){
      this.apiBH_wExecute = false;
      this.apiBH_show = true;
    },
    //打开 查询IMEI
    openQueryIMEI(){
      this.IMEI_wExecute = false;
      this.IMEI_show = true;
    },


    //提供给子组件 修改父组件属性
    setObj(Key, obj) {
      //console.log(obj);
      switch (Key) {
        case 'setRenew_wExecute':
          this.renew_wExecute = obj;//设置  更新卡列表  是否已经加载
          break;
        case 'setRenew_show':
          this.renew_show = obj;//设置  更新卡列表  是否显示
          break;
        case 'setDetailsEditexecute':
          this.detailsEditexecute = obj;//设置  基础信息  是否已经加载
          break;
        case 'setModuleEditexecute':
          this.moduleEditexecute = obj;//设置  信息拓展模块  是否已经加载
          break;
        case 'setSel':
          this.sel = obj;
          break;
        case 'setApiBH_wExecute':
          this.apiBH_wExecute = obj;//设置  API业务办理  是否已经加载
          break;
        case 'setApiBH_show':
          this.apiBH_show = obj;//设置  API业务办理  是否显示
          break;
        case 'setIMEI_wExecute':
          this.IMEI_wExecute = obj;//设置  查询IMEI  是否已经加载
          break;
        case 'setIMEI_show':
          this.IMEI_show = obj;//设置  查询IMEI  是否显示
          break;
        case 'openEditShow':
          this.edit_show = true;
          this.wUpdBatch = false;
          this.updForm = obj;
          break;

      }
    },


    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.updatel.isUploading = true;
    },


    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, this.$t('common.feedback'), {dangerouslyUseHTMLString: true});
      this.getList();
    },
    /** 下载模板操作 */
    importTemplate() {
      tools.downloadTemplate("/getcsv/importCard.xls");
    },


    // 提交上传文件
    submitFileForm() {
      this.submitFileFormBtn = false;
      this.$refs.upload.submit();
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
        if(this.deptOptions.length>20){//企业数据大于 20条之后 不做展开显示
          this.treeExpand = false;
        }
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
      //时间选择
      this.queryParams.startDate = null;
      this.queryParams.endDate = null;
      if (tools.isNull(this.queryParams.dateType) && this.selTime != null) {
        this.queryParams.startDate = this.selTime[0];
        this.queryParams.endDate = this.selTime[1];
      }
      this.queryParams.status_show_id = '7';
      this.queryParams.advancedSearch = true;
    },

    /** 查询 */
    getList() {
      this.loading = true;
      this.getParams();
      let pwdStr = tools.encryptSy(this.queryParams);
      listCard(pwdStr).then(response => {
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.edit_show = true;
      this.wUpdBatch = false;
      this.updForm.iccid = row.iccid;

    },
    /** 详情按钮操作 */
    viewDetails(row) {
      this.show_ds = true;
      this.detailsEditexecute = false;
      this.moduleEditexecute = false;


      this.sel.iccid = row.iccid;
      this.sel.c_no = row.c_no;
      this.sel.c_left = row.c_left;
      this.sel.c_used = row.c_used;


    },



    /** 打开导入表弹窗 */
    openImportTable() {
      this.upload.title = this.$t("common.import");
      this.upload.open = true;
      this.submitFileFormBtn = true;
    },
    /** 导出 */
    handleExport() {
      this.loading = true;
      this.getParams();
      let pwdStr = tools.encryptSy(this.queryParams);
      listCard(pwdStr).then(response => {
          let jsonObj = JSON.parse(tools.Decrypt(response));
          if (jsonObj.code == 200) {
            this.dataList = jsonObj.data.data;
            this.total = jsonObj.data.Pu.rowCount;
            if(this.total>5000){//大于 X 提示确认
              this.$confirm(this.$t("common.chosen")+' 【'+this.total+'】'+this.$t("common.articleData")+this.$t("common.whetherToConfirm")+'？', this.$t("common.hint"), {
                confirmButtonText: this.$t("common.sure"),
                cancelButtonText: this.$t("common.cancel"),
                type: 'warning'
              }).then(() => {
                this.cardExportFun();
              }).catch(() => {});
            }else{
              this.cardExportFun();
            }
          } else {
            this.$message.error(jsonObj.msg);
          }
          this.loading = false;
        }
      );

    },

    cardExportFun(){
      this.getParams();
      let pwdStr = tools.encryptSy(this.queryParams);
      cardExport(pwdStr).then(response => {
        let jsonObj = JSON.parse(tools.Decrypt(response));
        let msg = jsonObj.msg;
        if (jsonObj.code == 200) {
          this.$message.success(msg);
        } else {
          this.$message.error(msg);
        }
      })
    },




    //划分
    toDivide(){
      this.divide_show = true;
    },





    // tree单选
    treeNodeClick(data, checked) {
      this.userArr = [];
      const node = this.$refs.tree.getNode(data);
      const str = [];
      // 获取当前选择的id在数组中的索引
      const indexs = this.selectList.indexOf(data.id);
      // 如果不存在数组中，并且数组中已经有一个id并且checked为true的时候，代表不能再次选择。
      if (indexs < 0 && this.selectList.length === 1 && checked) {
        this.$message.error(this.$t("card_index.rs.radio"));
        // 设置已选择的节点为false 很重要
        this.$refs.tree.setChecked(data, false);
      } else if (this.selectList.length === 0 && checked) {
        // 发现数组为空 并且是已选择
        // 防止数组有值，首先清空，再push
        this.selectList = [];
        this.formDivide.dept_id = '';
        this.selectList.push(data.id);
        this.formDivide.dept_id = data.id;
        this.formDivide.dept_name = data.label;

      } else if (indexs >= 0 && this.selectList.length === 1 && !checked) {
        // 再次直接进行赋值为空操作
        this.selectList = [];
        this.formDivide.dept_id = '';
      }
    },


    /** 划卡提交 */
    divide() {
      this.divideSBtn = false;
      if (tools.VerificationsText(this, this.formDivide.dept_id, this.$t("card_index.rs.dept_id")) == true) {
        //1.先按当期筛选条件查询出数据 询问是否保存
        this.handleQuery();
        let map = this.queryParams;
        map.set_dept_id = this.formDivide.dept_id;
        map.set_dept_name = this.formDivide.dept_name;
        let pwdStr = tools.encryptSy(map);
        tools.openAsk(this, 'warning', this.$t("common.ask.ask")+" [ " + this.total + " ] "+this.$t("card_index.ask.cardDivid_2")+"  [ " + this.formDivide.dept_name + " ] ？", this.divideSave, pwdStr);
      }
    },

    //划卡保存
    divideSave(pwdStr) {
      divideCard(pwdStr).then(response => {
        this.divideSBtn = true;
        let jsonObj = JSON.parse(tools.Decrypt(response));
        let msg =  jsonObj.msg;
        if (jsonObj.code == 200) {
          this.$modal.msgSuccess(msg);
          this.handleQuery();
          this.divide_show = false;
        } else {
          this.$message.error(msg);
        }
      })
    },

    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "synInfo":
          this.synCardInfo(row);
          break;
        case "synFlow":
          this.synCardFlow(row);
          break;
        case "synStatus":
          this.synCardStatus(row);
          break;
        case "synRealName":
          this.synCardRealName(row);
          break;
        case "synActivateDate":
          this.synCardActivateDate(row);
          break;
        case "synImei":
          this.synCardImei(row);
          break;


        default:
          break;
      }
    },

    synCardInfo(row){//一键同步
      synInfo(this.synCommonParameter(row)).then(response => {
        this.synCommonFeedback(response);
      })
    },

    //同步用量
    synCardFlow(row){
      synFlow(this.synCommonParameter(row)).then(response => {
        this.synCommonFeedback(response);
      })
    },

    //同步状态
    synCardStatus(row){
      synStatus(this.synCommonParameter(row)).then(response => {
        this.synCommonFeedback(response);
      })
    },

    //同步实名状态
    synCardRealName(row){
      synRealName(this.synCommonParameter(row)).then(response => {
        this.synCommonFeedback(response);
      })
    },

    //同步 激活时间
    synCardActivateDate(row){
      synActivateDate(this.synCommonParameter(row)).then(response => {
        this.synCommonFeedback(response);
      })
    },

    //同步 激活时间
    synCardImei(row){
      synImei(this.synCommonParameter(row)).then(response => {
        this.synCommonFeedback(response);
      })
    },



    //同步公共返回 反馈
    synCommonFeedback(response){
      let jsonObj = JSON.parse(tools.Decrypt(response));
      let msg = jsonObj.msg;
      if (jsonObj.code == 200) {
        this.$message.success(msg);
        this.getList();
      } else {
        this.$message.error(msg);
      }
    },
    //同步公共 参数加密
    synCommonParameter(row){
      let map = {};
      map.iccid = row.iccid;
      let pwdStr = tools.encryptSy(map);
      return pwdStr;
    },

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
          this.updForm.delivery_date = tools.isNull(this.updForm.delivery_date)?this.updForm.delivery_date:'';
          this.updForm.customize_grouping = tools.isNull(this.updForm.customize_grouping)?this.updForm.customize_grouping:'';

          title += this.$t('card_index.updForm.remarks')+" ["+this.updForm.remarks+"]";
          title += "IMEI: ["+this.updForm.imei+"]";
          title += this.$t('card_index.updForm.delivery_date')+" ["+this.updForm.delivery_date+"]";
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
          if(tools.isNull(this.updForm.delivery_date)){
            bool = true;
            title += this.$t('card_index.updForm.delivery_date')+" ["+this.updForm.delivery_date+"]";
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
          this.edit_show = false;
        } else {
          this.$message.error(msg);
        }
      })
    },



  }
};
</script>



