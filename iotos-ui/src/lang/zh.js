import tools from "@/utils/iotos/tools";

export default {
  common:{//通用
    success:"操作成功",
    fail:"操作失败",
    save:"新增",
    edie:"编辑",
    delete:"删除",
    preserve:"保存",
    find:"查看",
    details:"详情",
    operate:"操作",
    name:"名称",
    nickName:"昵称",
    search:"搜索",
    reset:"重置",
    state:"状态",
    loginAddress:"登录地址",
    loginAccount:"登录账号",
    loginPassword:"登录密码",
    showSearch:"显示搜索",
    hideSearch:"隐藏搜索",
    refresh:"刷新",
    VisibleColumn:"显隐列",
    queryConditions:"查询条件",
    queryValue:"查询值",
    show:"显示",
    hide:"隐藏",
    pleaseChoose:"请选择",
    notify:'通知',
    GCGCorrect:'语法正确',
    networkAnomaly:"网络异常",
    whetherToConfirm:"是否确认 ",
    belongsTo:"所属企业",
    import:"导入",
    export:"导出",
    toDivide:"划分",
    batchOperation:"批量操作",
    uploadText:"将文件拖到此处，或",
    uploadTextEm:"点击上传",
    downloadTemplate:"下载模板",
    uploadTip:"提示：仅允许导入'xls'或'xlsx'格式文件！",
    sure:"确定",
    cancel:"取消",
    numberType:"号码类型",
    pleaseEnter:"请输入",
    expand_collapse:"展开/折叠",
    selectAll_notSelectAll:"全选/全不选",
    fatherSonlinkage:"父子联动",
    feedback:"反馈信息",
    renew:"更新",
    performTasks:"执行任务",
    revoke:"撤销",
    download:"下载",
    noData:"暂无数据",
    copySuccess:"复制成功",
    used:"已使用",
    left:"剩余",
    flowUsed:"已使用",
    dayFlow:"日用量",
    synchronization:"信息同步中",
    synced:"已同步信息",
    sessionCreationTime:"会话创建时间",
    returnedMessages:"返回信息",
    hint:"提示",
    chosen:"当前已选择 ",
    articleData:" 条数据 ",

    currentMonth:"当月",
    lastMonth:"上月",
    aYearAgo:"一年前",
    data:'条 数据',
    moreOperations:"更多",
    noDataFound:"未找到匹对数据！",

    today:"今天",
    yesterday:"昨天",
    weekAgo:"一周前",
    startDate:"开始日期",
    endDate:"结束日期",
    timeType:'时间类型',
    to:'至',
    sortType:'排序类型',
    descendingOrder:'升降序',
    startStopConditions:'起止条件',
    startNumberSegment:'开始号段',
    endNumberSegment:'结束号段',
    multiDimension :'多维度',
    dimensionField:'维度字段',
    dimensionValue:'维度值',
    dimensionCondition:'维度条件',
    lastWeek:'最近一周',
    lastRecentMonth:'最近一个月',
    lastThreeMonths:'最近三个月',

    open:'张',
    indivual:'个',
    share:'份',
    yuan:'元',

    month:'月',
    day:'日',


    ask:{//通用询问
      wDelName:"是否确认 [删除] ‘名称‘ 为：",
      ask:"确定将",
      execute:"是否执行",

    },
    rs:{//通用提示
      http:"请输入 以http://或者以https://开头地址",
      state:"请选择 状态！",
      name:"请输入 名称",
      nickName:"请输入 昵称",
      deptName:"请输入企业名称",
    }
  },




  index:{
    cardCount:"卡总数",
    proxyCardCount:"代理卡总数",
    directCardCount:"直属卡总数",
    deptCount:"企业总数",
    loginCount:"本月登录IP总数",
    saveCount:"本月新增卡号",
    activationCount:"本月激活卡号",
    onlineUser:"在线用户",
    basic:{
      basicInfo:"基础信息",
      statisticalDeadline:"统计截止日期：",
      ps:"统计时间与当前时间 默认时差 15分钟（PS:15分钟内数据不会变更）",

    },

    dailyData:"昨日用量",
    monthlyData:"本月用量",
    statisticalCutoff:"统计截止: ",
    yesterdayTopRanking:"TOP排行-昨日",
    thisMonthTopRanking:"TOP排行-本月截止昨日",
    monthlyDosage:"月用量GB",
    dailyDosage:"日用量GB",
    dosage:"用量(MB)",

    hint:{
      daily:"日数据显示昨日用量排行榜[MB]，折线图用量规格为[GB]。",
      month:"月数据显示当月用量排行榜[MB]，折线图用量规格为[GB]。",
    },
    cardNumberBulletinBoard:'卡号看板',
    LoginBulletinBoard:'登录看板',
    lifeCycleTitle:'生命周期分布',
    activateTrend:'激活月增长趋势',
    deptActivateTitle:'企业激活比例',
    deptProportionTitle:'企业拿卡比例',
    deptUsageTitle:'企业用量比例',
    BasicKanban:"基础信息看板",
    channelTitle:'通道卡号比例',
    loginPieTitle:'用户登录次数比例',
    loginMapTitle:'用户登录地图标记',

  },

  tasksDetails:{//执行任务详情
    success:"成功",
    fail:"失败",
    pending:"待处理",

    table_3:{
      i_1:'ICCID',
      i_2:'业务类型',
      i_3:'状态',
      i_4:'开始时间',
      i_5:'结束时间',
      i_6:'创建时间',
      i_7:'备注',

    },

  },



  apiInquireIMEI:{//批量查询 IMEI
    title:"查询IMEI",

    ask:{//询问
      upd:"是否确认 执行批量查询 IMEI ?",
    },
    rs:{
      file:"请选择上传文件！",
    }

  },


  cardApiBusinessHandling:{//API业务办理
    title:"业务办理",
    openClose:"停复机 & 断开网",
    gprsReset:"网络重置",
    shutdown:"断开网",
    stop:"停复机",
    flexibleChange:"灵活变更状态",
    tip:{
      reset:"遇到上网功能异常的时候可以通过调用该接口来重置恢复GPRS上网功能；（选择 ‘是’ 时执行）",
      openStop:"停复机：对卡号执行 停机 复机 操作；（PS：长期停机可能会产生停机保号费用，且可能会销户卡号！具体根据上游运营商而定！）",
      openClose:"断开网：对卡号执行 关闭网络连接 打开网络连接 操作；（PS：该功能类似手机数据流量打开关闭。）",
      flexibleChange:"灵活变更卡状态：适配部分上游API，（PS：且停机原因为主动申请停机时才能复机 其他原因需要联系客户经理处理！）",
    },
    ask:{//询问
      upd:"是否确认 针对上传卡号执行 ",
    },
    rs:{
      flexibleChange:"请选择需要 灵活变更的状态！",
      file:"请选择上传文件！",
      openClose:"请选择需要 停复机 或 断开网的操作类型！",
      gprsReset:"请选择是否需要 执行 网络重置！",
    }

  },


  cardInfoRenew:{//卡信息更新
    dividerTitle:"卡信息批量更新",
    tip:{
      number:"如：号码类型 选择【ICCID】则 ICCID 为必填！且根据匹对的ICCID 进行修改更新操作",
    },
    ask:{//通用询问
      upd:"是否确认 批量更新卡信息？",
    },
    rs:{
      batchType:"请选择 号码类型！",
      file:"请选择上传文件！",
    }

  },

  performTasks_index:{//执行任务
    table:{
      i_1:'任务名称',
      i_2:'编号',
      i_3:'任务类别',
      i_4:'开始时间',
      i_5:'结束时间',
      i_6:'企业',
      i_7:'操作账号',
      i_8:'失效日期',
      i_9:'备注',
    },
    table_1:{
      i_1:'文件名',
      i_2:'开始时间',
      i_3:'结束时间',
      i_4:'类型',
      i_5:'下载次数',
    },

    table_2:{
      i_1:'企业',
      i_2:'昵称',
      i_3:'IP',
      i_4:'下载时间',
    },
    fileList:"执行任务-文件列表",
    downloadList:"文件-下载记录",
    tasksDetails:"执行任务-详情",


    ask:{
      revokeCardDivid_1:"已分配给",
      revokeCardDivid_2:"进行操作撤回？",

    },

  },

  cardInfoModule: {//卡 拓展信息
    cardSession:"会话信息",
    cardApiBusiness:"变更记录",
    cbefore:" 变更前 ",
    cafterward:" 变更后 ",
    source:" 来源 ",

    last30Days:"最近30天",
    flowRecord:"流量使用",
    session:{
      accessMode:"无线接入模式",
      accessMethod:"接入方式",
      onlineStatus:"在线状态",
      iPAddress:"IP地址",
      hint1:"请核对API信息（未产生用量不会有在线记录！）",
      hint2:"仅支持 上游开放查询会话信息API获取",
      error1:"暂未获取到会话信息",
    }

  },




    cardInfoDetails:{//卡基础信息

    copyCardInfo:"复制 基础信息",



    tFrom:{
      operator:"运营商",
      roaming_country:"漫游国家",
      internet_signal:"网络信号",
      ip_attribution:"IP归属地",
      status_show_id:"卡状态",
      status_id:"状态描述",

      type:"卡类型",
      network_type:"网络类型",
      nedd_real_name:"需要实名",
      w_real_name:"已实名",
      deliver_date:"发货日期",
      activate_date:"激活时间",
      traffic_sync_time:"用量同步时间",
      state_sync_time:"状态同步时间",
      channel_id:"所属通道",
      package_id:"所属资费组",
      u_code:"所属用卡人",
      w_polling:"轮询",
      automatic_renewal:"自动续费",
      w_sms:"短信",
      w_voice:"语音",
      w_network_break:"未订购断网",
      shutdown_threshold:"停机阈值",
      prodinsteff_time:"生效时间",
      prodinstexp_time:"失效时间",
      connection_status:"网络状态",
      w_pool:"流量池",
      balance:"预存余额",
      payment_key:"支付密码",
      customize_grouping:"分组",
      remarks:"备注",

      first_time_using:"首次用量使用时间",
      create_time:"创建时间",
      storage_date:"入库日期",
      delivery_date:"出库日期",







    }

  },


  card_index:{// card
    updateGroupAndNotes:'更新分组及备注',
    batchQueryIMEI:'查询IMEI',
    batchShutdown:'业务办理',
    updateCardInformation:'更新卡信息',
    oneKeySync:'一键同步',
    syncUsage:'同步用量',
    syncStatus:'同步状态',
    syncRealName:'同步实名状态',
    syncIMEI:'同步IMEI',
    synActivateDate:'同步激活时间',
    editCard:"更新卡信息",
    updForm:{
      remarks:"备注：",
      updateNotFilled:"更新未填写属性：",
      delivery_date:"发货日期：",
      customize_grouping:"自定义分组：",
    },

    table:{
      i_1:'编号',
      i_2:'MSISDN',
      i_3:'ICCID',
      i_4:'IMSI',
      i_5:'IMEI',
      i_6:'卡状态',
      i_7:'通道',
      i_8:'企业',
      i_9:'已用(MB)',
      i_10:'剩余(MB)',
    },

    rs:{//表单验证
      dept_id:'请选择所属企业！',
      radio:'单选操作请勿多选！',
      updIccid:'操作参数缺失！请刷新后重试！',
      unfilled:'请选择需要更新的信息！',
    },
    ask:{
      upd:'确认更新 ',
      cardDivid_2:"条数据  划分给",
    }

  },

channel_index:{// channel
  table:{
    i_1:'编号',
    i_2:'模板',
    i_3:'名称',
    i_4:'昵称',
    i_5:'状态',
    i_6:'轮询',
    i_7:'卡总数',
    i_8:'总用量',
  },
  form:{//表单
      template:'模板',
      polling:'轮询',
      basicConfiguration:'基础配置',
      synchronizationStrategy:'同步策略',
      auxiliaryRegistration:'辅助登记',
      requestAddress:'请求地址',
      configurationOne:'配置一',
      configurationTwo:'配置二',
      ConfigurationTree:'配置三',
      importAutomatically:'自动导入',
      synchronizationType:'同步类型',
      syncField:'同步字段',
    },
    rs:{//表单验证
      template:'模板 不能为空！',
      polling:'请选择 轮询！',
      state:'请选择 状态',
      requestAddress:'请求地址 不能为空！',
      configurationOne:'配置一 不能为空！',
      configurationTwo:'配置二 不能为空！',
      ConfigurationTree:'配置三 不能为空！',
    }


  },

  login: {
    title: 'IoTOS后台管理系统',
    logIn: '登录',
    username: '账号',
    password: '密码',
    r_password: '记住密码',
    vcode_title: '验证码',
    rs_username: '用户名不能为空',
    rs_password: '密码不能为空',
    rs_vcode: '验证码不能为空',
    login_pt: '登录中',
  },
  tagsView: {
    refresh: '刷新页面',
    close: '关闭当前',
    closeOthers: '关闭其它',
    closeAll: '关闭所有',
    closeLeft: '关闭左侧',
    closeRight: '关闭右侧',
  },
  settings: {
    title: '主题风格设置',
    theme: '主题颜色',
    topNav: '开启 TopNav',
    tagsViews: '开启 Tags-Views',
    fixedHeader: '固定 Header',
    sidebarLogo: '侧边栏 Logo',
    dynamicTitle: '动态标题',
    layoutConfiguration: '系统布局配置',
    saveSetting: '保存配置',
    resetSetting: '重置配置',
  },
  Navbar:{//右标题操作
    personal_center:'个人中心',
    layout_settings:'布局设置',
    sign_out:'退出登录',
    source_address:'源码地址',
    document_address:'文档地址',
    full_screen:'全屏',
    layout_size:'布局大小',
  },


  menu:{//菜单解析
    index:'首页',
    System_Management:'系统管理',
    System_monitoring:'系统监控',
    System_Tool:'系统工具',
    IoTOS_official_website:'关于 IoTOS',
    User_Management:'用户管理',
    role_management:'角色管理',
    menu_management:'菜单管理',
    department_management:'企业管理',
    job_management:'岗位管理',
    dictionary_management:'字典管理',
    parameter_settings:'参数设置',
    announcement:'通知公告',
    log_management:'日志管理',
    online_user:'在线用户',
    timed_task:'定时任务',
    data_monitoring:'数据监控',
    service_monitoring:'服务监控',
    cache_monitoring:'缓存监控',
    cache_list:'缓存列表',
    form_building:'表单构建',
    code_generation:'代码生成',
    system_interface:'系统接口',
    operation_log:'操作日志',
    login_log:'登录日志',
    user_query:'用户查询',
    new_user:'用户新增',
    user_modification:'用户修改',
    user_delete:'用户删除',
    user_export:'用户导出',
    user_import:'用户导入',
    reset_Password:'重置密码',
    role_query:'角色查询',
    new_role:'角色新增',
    role_modification:'角色修改',
    role_deletion:'角色删除',
    character_export:'角色导出',
    menu_query:'菜单查询',
    New_menu:'菜单新增',
    menu_modification:'菜单修改',
    menu_delete:'菜单删除',
    Department_inquiry:'企业查询',
    new_department:'企业新增',
    department_modification:'企业修改',
    department_delete:'企业删除',
    job_search:'岗位查询',
    new_jobs:'岗位新增',
    job_modification:'岗位修改',
    post_delete:'岗位删除',
    post_export:'岗位导出',
    dictionary_lookup:'字典查询',
    dictionary_added:'字典新增',
    dictionary_modification:'字典修改',
    dictionary_deletion:'字典删除',
    Dictionary_export:'字典导出',
    parameter_query:'参数查询',
    New_parameters:'参数新增',
    parameter_modification:'参数修改',
    parameter_deletion:'参数删除',
    Parameter_export:'参数导出',
    Announcement_query:'公告查询',
    Announcement_added:'公告新增',
    Announcement_modification:'公告修改',
    Announcement_delete:'公告删除',
    Operation_query:'操作查询',
    operation_delete:'操作删除',
    log_export:'日志导出',
    login_query:'登录查询',
    Login_to_delete:'登录删除',
    account_unlock:'账户解锁',
    online_search:'在线查询',
    Forced_withdrawal_in_batches:'批量强退',
    single_forced_exit:'单条强退',
    task_query:'任务查询',
    new_task:'任务新增',
    task_modification:'任务修改',
    task_delete:'任务删除',
    status_modification:'状态修改',
    task_export:'任务导出',
    generate_query:'生成查询',
    generate_modification:'生成修改',
    generate_delete:'生成删除',
    import_code:'导入代码',
    preview_code:'预览代码',
    generate_code:'生成代码',
    connect:'连接',
    traffic_card:'流量卡',
    aisle:'通道',
    card_list:'卡列表',
    List_of_pin_numbers:'销号列表',
    billing_group:'计费组',
    Recharge_management:'充值管理',
    performTasks:'执行任务',
    headquarters:'总部标识',
    personal_center:'个人中心',
    downloadFile:'任务文件下载',
    flieDownload:'查看文件下载记录',
    dictionaryData:'字典数据',
    assigningRoles:'分配角色',
    assignUsers:'分配用户',
    schedulingLog:'调度日志',
    modifyGeneration:'修改生成配置',
    accountCenter:'账号中心',
  },


  IoTOS:{
    introduce:'IoTOS 基于多个物联网管理系统API（如:中国移动 oneLink 等 后续接入API） 开放能力，不仅集成了上游强大的API管理及基础的 数据同步算法 功能，而且提供了多语言国际化方案。并通过 高效灵活的同步算法、系统构架业务分离 等灵活高效的数据运营模块，让企业与上游之间建立强链接，从而进一步通过多元化的管理运营方案，帮助企业提高物联网卡运营效率，强化运营能力，拓展可盈利空间。',
    currentVersion:'当前版本：',
    gitEE:'Gitee 地址',
    gitHub:'GitHub 地址',
    free:'￥免费开源',
    receiveAlibabaCloudCoupons:'￥领取阿里云优惠券',
    doc:'文档地址',
    technologySelection:'技术选型',
    backendTechnology:'后端技术',
    frontEndTechnology:'前端技术',
    contactInformation:'联系信息',
    officialWebsite:'官网',
    weChat:'微信',
    mail:'邮箱',
    updateLog:'更新日志',
    v100:{
      l00:'IoTOSc系统正式发布；',
      l01:'多语言国际化；',
      l02:'通道、卡列表、用量记录、基础业务；',
      l03:'卡号自动同步载入策略；',
      l04:'API目前仅支持 OneLink EcV5 接口(最终会依赖该接口作为一个全面对接的展示业务 敬请期待！)；',
      l05:'OneLink EcV5同步算法策略 队列多线程中使用java多线程套娃式显著提升算法同步效率；',
      l06:'用量、生命周期补偿算法完善；',
      l07:'首页统计数据完善；',
    },
    visitMainSite:'访问主站点',
  },


}