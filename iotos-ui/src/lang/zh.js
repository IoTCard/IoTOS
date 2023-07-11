import tools from "@/utils/iotos/tools";
import carousel_1 from "@/assets/images/carousel/1.png";
import carousel_2 from "@/assets/images/carousel/2.png";
import carousel_3 from "@/assets/images/carousel/3.png";
import carousel_4 from "@/assets/images/carousel/4.png";

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

    loginImgList:[
      {
        title:"IoTOS开源系列",
        describe:" 基于多API开放能力集成 极致高效同步算法；多语言国际化、自动化管理 提供移动端App便捷管控 拿来即用",
        btn1Title:"专题介绍",
        btn2Title:"获取源码",
      },
      {
        title:"开放式协议 Apache-2.0",
        describe:"二次开发可商用，可衍生作品；商业和非商业环境中自由使用、修改和分发开源软件，同时保护了软件的作者和贡献者的权益。它鼓励开放合作和共享创新，促进了开源软件社区的繁荣发展",
        btn1Title:"专题介绍",
        btn2Title:"获取源码",
      },
      {
        title:"PC、Android、小程序、IOS",
        describe:"后台提供PC端操作，移动端采用 uni-app 开发支持多端打包上架 编写一套代码，可发布到iOS、Android、Web（响应式）、以及各种小程序（微信/支付宝/百度/头条/飞书/QQ/快手/钉钉/淘宝）、快应用等多平台",
        btn1Title:"专题介绍",
        btn2Title:"移动端源码",
      },
      {
        title:"多运营商（国际）、第三方接入",
        describe:"提供多家运营商接口对接（包含常用国际运营商接入），灵活的套餐包包装；后续可供支持套餐及时充值到账多上游套餐包装等；详细功能可关注公众号联系我们获取更多合作交流机会。",
        btn1Title:"专题介绍",
        btn2Title:"获取源码",
      }
    ],


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
    v121:{
      l00:'登录页增加可修改数据轮播；',
      l01:'登录页增加国内常用运营商平台网站、国际平台运营商网站、关于-IoTOS链接；',
      l02:'登录页增加平台能力说明、开放协议说明、系列产品说明；',
      l03:'增加适配移动端系统通知登录后通过IoTOS-IM下发公告通知；',
      l04:'优化登录界面默认语言为 ‘中文’；',
    },
    visitMainSite:'访问主站点',
    a:'<p><strong class="ql-size-huge" style="color: rgb(230, 0, 0);"><img src="https://gitee.com/chinaiot/iotos/raw/master/readme-pic/cn/logo.png" alt="IoTOS logo.png"></strong></p><h1>IoTOS v1.0.0</h1><h2>一款高效实用 IoTCard 管理 &amp; 运营系统。</h2><blockquote>IoTOS 目前取名范围过大，其主要用于IoTCard 管理业务以高效、健壮、灵活设计 SaaS、多语言、机器人推送、自动化管理、数据同步多类型算法为主要业务。</blockquote><p>中文 /&nbsp;<a href="https://gitee.com/chinaiot/iotos/blob/master/README.en.md" rel="noopener noreferrer" target="_blank" style="color: rgb(9, 94, 171); background-color: transparent;">English</a></p><p><a href="https://gitee.com/link?target=https%3A%2F%2Fgithub.com%2FIoTCard%2FIoTOS" rel="noopener noreferrer" target="_blank" class="ql-size-huge" style="color: rgb(9, 94, 171); background-color: transparent;"><strong><img src="https://img.shields.io/github/license/IoTCard/IoTOS?style=flat-square&amp;logo=github&amp;color=616ae5" alt="GitHub license"></strong></a>&nbsp;<a href="https://gitee.com/link?target=https%3A%2F%2Fgithub.com%2FIoTCard%2FIoTOS" rel="noopener noreferrer" target="_blank" class="ql-size-huge" style="color: rgb(9, 94, 171); background-color: transparent;"><strong><img src="https://img.shields.io/github/stars/IoTCard/IoTOS?style=flat-square&amp;logo=github&amp;color=616ae5" alt="GitHub stars"></strong></a>&nbsp;<a href="https://gitee.com/link?target=https%3A%2F%2Fgithub.com%2FIoTCard%2FIoTOS" rel="noopener noreferrer" target="_blank" class="ql-size-huge" style="color: rgb(9, 94, 171); background-color: transparent;"><strong><img src="https://img.shields.io/github/forks/IoTCard/IoTOS?style=flat-square&amp;logo=github&amp;color=616ae5" alt="GitHub forks"></strong></a>&nbsp;<a href="https://gitee.com/chinaiot/iotos/stargazers" rel="noopener noreferrer" target="_blank" class="ql-size-huge" style="color: rgb(9, 94, 171); background-color: transparent;"><strong><img src="https://gitee.com/chinaiot/iotos/badge/star.svg?theme=dark" alt="star"></strong></a>&nbsp;<a href="https://gitee.com/chinaiot/iotos/members" rel="noopener noreferrer" target="_blank" class="ql-size-huge" style="color: rgb(9, 94, 171); background-color: transparent;"><strong><img src="https://gitee.com/chinaiot/iotos/badge/fork.svg?theme=dark" alt="fork"></strong></a></p><p><br></p><h1>一、 关于 IoTOS</h1><h2>1.1 介绍</h2><p>IoTOS 是基于 SpringBoot、Vue、Mybatis、RabbitMq、Mysql、Redis 的开源 IoTCard（物联卡管理） 系统，是企业私域管理与运营的综合解决方案。</p><p>IoTOS 基于多个物联网管理系统API（如:中国移动 oneLink 等 后续接入API） 开放能力，不仅集成了上游强大的API管理及基础的 数据同步算法 功能，而且提供了多语言国际化方案。并通过 高效灵活的同步算法、系统构架业务分离 等灵活高效的数据运营模块，让企业与上游之间建立强链接，从而进一步通过多元化的管理运营方案，帮助企业提高物联网卡运营效率，强化运营能力，拓展科盈利空间。</p><p>目前主要运用于 物联网卡 服务行业领域。</p><h2>1.2 能力</h2><p>IoTOS-IoTCard 基于物联网多上游API接口综合业务管理，遵从高效数据同步并维护运营，再到建立系统套餐分发进行资费营销，最后在营销、运营 等服务提供 过程中再次创造价值的流程。</p><p>整个系统暂且 分为五大模块：</p><ul><li>首页&nbsp;：统计分析展板；</li><li>连接&nbsp;：流量卡、设备、通道 运营管理 配置查看；</li><li>账号中心&nbsp;：企业、用户、角色 管理分配权限 账号；</li><li>自动化&nbsp;：自动化管理规则、模板、推送配置、触发条件 等；</li><li>系统管理&nbsp;：菜单、参数、字典、系统监控、系统工具、定时任务 等；</li></ul><p>欢迎有兴趣的 开发者、商业合作、业务探讨 等 通过下方的联系方式联系</p><h2>1.3 优势</h2><p>IoTOS 基于多个上游运营商能力接口 开放能力平台综合业务管理运营物联网卡 但不仅限于物联网卡，可为 物联卡运营、多个第三方接口对接需求 等垂直场景提供API对接管理基础架构，主要优势有：</p><ul><li>多语言国际化操作(UI界面、后台回复、单用户喜好语言)、机器人推送（国内主流机器人、国际主流社交软件）</li><li>高效API同步能力采用队列多线程实例开启接口多线程（队列多线程中再增加java多线程）更具不同运营商接口独立特定同步算法体系</li><li>自动化规则定义 规则触发模板 通知配置 触发条件等</li><li>开源协议采用 Apache-2.0 (使用者可以自由修改，进行商业使用，没有开源要求)</li><li>采用主流 Java 架构，具备高拓展性、灵活性</li><li>对外提供内部 API，低成本二次开发</li></ul><h2>1.4 愿景</h2><p>IoTOS 不做物联网专家，而是通过开源让每个企业、每个开发者 都是自己的物联网专家：</p><ul><li>集成上游多API基础能力，拿来即用</li><li>针对垂直行业提供一站式服务运营解决方案</li><li>开放自身平台能力，让更多的开发者参与进来</li><li>与企业、开发者 共同助力 物联网行业共建发展未来</li></ul><h1>二、关于项目</h1><h2>2.1 在线体验</h2><p>开源演示地址：<a href="https://gitee.com/link?target=http%3A%2F%2Fdemo.iotos.top%2F" rel="noopener noreferrer" target="_blank" style="color: rgb(9, 94, 171); background-color: transparent;">http://demo.iotos.top/</a></p><p><strong class="ql-size-huge" style="color: rgb(230, 0, 0);">默认账号密码：iotos，iotos.top</strong></p><p><em>（注意：演示环境已屏蔽管理权限和相关操作）</em></p><h2>2.2 系统架构</h2><p>IoTOS V1.0.0 整体系统架构如下：</p><h2>2.3 技术栈</h2><p>本项目基于&nbsp;<a href="https://gitee.com/y_project/RuoYi-Vue" rel="noopener noreferrer" target="_blank" style="color: rgb(9, 94, 171); background-color: transparent;">RuoYi-Vue</a>&nbsp;后台开发框架，感谢&nbsp;<a href="https://gitee.com/y_project/RuoYi-Vue" rel="noopener noreferrer" target="_blank" style="color: rgb(9, 94, 171); background-color: transparent;">RuoYi-Vue</a>&nbsp;的开源。</p><ul><li>前端技术栈：ES6、Vue、Vue-router、Vue-cli、Axios、Element-ui；</li><li>后端技术栈：Spring Boot、Mybatis-plus、Mybatis、RabbitMQ；</li></ul><h2>2.4 项目结构</h2><p>后端结构</p><pre class="ql-syntax" spellcheck="false">├── iotos-admin              // 后台服务主程序\n' +
      '├── iotos-common             // 公共组件模块\n' +
      '├── iotos-common-syn-ap      // API请求配置中心\n' +
      '├── iotos-consumer-admin\t // 后台主程序-消费者\n' +
      '├── iotos-consumer-task      // 定时任务-消费者\n' +
      '├── iotos-framework\t         // 框架核心\n' +
      '├── iotos-generator          // 代码生成\n' +
      '├── iotos-quartz             // 定时任务\n' +
      '├── iotos-system             // 系统代码\n' +
      '</pre><p>前端结构</p><pre class="ql-syntax" spellcheck="false">├── iotos-ui\t\t\t     // 后台项目\n' +
      '</pre><h2>2.5 项目部署</h2><p>点击下方链接进入查看项目部署方式：</p><p><a href="https://gitee.com/link?target=http%3A%2F%2Fwww.iotos.top%2F" rel="noopener noreferrer" target="_blank" style="color: rgb(9, 94, 171); background-color: transparent;">如何快速部署 IoTOS</a></p><p><strong class="ql-size-huge" style="color: rgb(230, 0, 0);">建议服务器最低配置：</strong></p><p><strong class="ql-size-huge" style="color: rgb(230, 0, 0);">类型配置操作系统CentOS8CPU4核内存8G带宽5M硬盘50G</strong></p><h1>三、常见问题</h1><p>点击下方链接进入帮助手册查看常见问题的相关描述及更多帮助：</p><p><a href="https://gitee.com/link?target=http%3A%2F%2Fwww.iotos.top%2F" rel="noopener noreferrer" target="_blank" style="color: rgb(9, 94, 171); background-color: transparent;">常见问题</a></p><h1>四、最近更新&amp;后续开发方向</h1><h2>后续开发方向</h2><ul><li>&nbsp;完善教程、按文档、视频 等资料文件形式 深入讲解 快速使用、了解运营、二次开发拓展、可发展方向等</li><li>&nbsp;移动端适配企业查询、用户绑定、同样适配国际化；</li><li>&nbsp;OneLink EcV5接口业务继续延展：<code style="color: rgba(0, 0, 0, 0.8); background-color: rgb(247, 247, 249);">智能诊断</code>&nbsp;定义为平台数据诊断 API接口诊断且更具不同上游会展示出更多可诊断项界面等；</li><li>&nbsp;<code style="color: rgba(0, 0, 0, 0.8); background-color: rgb(247, 247, 249);">自动化</code>&nbsp;业务模块定义完善、包括机器人对接等；</li><li>&nbsp;<code style="color: rgba(0, 0, 0, 0.8); background-color: rgb(247, 247, 249);">支付配置</code>&nbsp;支付收款 国内主流支付接入、国际支付接入等；</li><li>&nbsp;<code style="color: rgba(0, 0, 0, 0.8); background-color: rgb(247, 247, 249);">套餐定义</code>&nbsp;套餐包含多类型 多配置 多组合等方式实现；</li><li>&nbsp;<code style="color: rgba(0, 0, 0, 0.8); background-color: rgb(247, 247, 249);">企业预存</code>、<code style="color: rgba(0, 0, 0, 0.8); background-color: rgb(247, 247, 249);">用户预存</code>&nbsp;企业预存金额、订单返佣、用户充值预存 自主设置预付费扣费等；</li><li>&nbsp;<code style="color: rgba(0, 0, 0, 0.8); background-color: rgb(247, 247, 249);">充值续费</code>&nbsp;企业或用户 充值、续费、预存 等业务延展；</li><li>&nbsp;<code style="color: rgba(0, 0, 0, 0.8); background-color: rgb(247, 247, 249);">中控分析</code>&nbsp;包含但不限于 分析 企业、用户 充值数据（按时间、金额、交易类型、复购率、活性、数据同步成功率、交易订单时间区间 套餐结余 等）</li><li>&nbsp;<code style="color: rgba(0, 0, 0, 0.8); background-color: rgb(247, 247, 249);">商城</code>&nbsp;发售商品 购买下单流程、分销、物流</li></ul><h2>V1.0.0 更新日志</h2><ul><li>&nbsp;多语言国际化；</li><li>&nbsp;通道、卡列表、用量记录、基础业务；</li><li>&nbsp;卡号自动同步载入策略；</li><li>&nbsp;API目前仅支持 OneLink EcV5 接口(最终会依赖该接口作为一个全面对接的展示业务 敬请期待！)；</li><li>&nbsp;OneLink EcV5同步算法策略 队列多线程中使用java多线程套娃式显著提升算法同步效率；</li><li>&nbsp;用量、生命周期补偿算法完善；</li><li>&nbsp;首页统计数据完善；</li></ul><h1>五、联系我们</h1><p>如果你想加入我们的开源交流群、有任何 IoTOS 产品上的想法、意见或建议，或商务上的合作需求，请扫码添加 IoTOS 项目作者，加入群聊：</p><ul><li>微信</li></ul><p><strong class="ql-size-huge" style="color: rgb(230, 0, 0);"><img src="https://gitee.com/chinaiot/iotos/raw/master/readme-pic/cn/contact1.jpg" alt="WeChat"></strong></p><ul><li>微信公众号</li></ul><p><strong class="ql-size-huge" style="color: rgb(230, 0, 0);"><img src="https://gitee.com/chinaiot/iotos/raw/master/readme-pic/cn/gzhewm.gif" alt="WeChatPublicAccount"></strong></p><h1>六、捐赠支持</h1><p>如果您是企业的经营者并且有计划将&nbsp;<code style="color: rgba(0, 0, 0, 0.8); background-color: rgb(247, 247, 249);">IoTOS</code>&nbsp;用在公司的经营产品中，欢迎进行长期捐赠。长期捐赠有商业上的益处有：</p><ul><li>积极响应，快速维护，及时更新；</li><li>企业名称、Logo 及官网链接将长期展示在开源仓库、<code style="color: rgba(0, 0, 0, 0.8); background-color: rgb(247, 247, 249);">IoTOS</code>&nbsp;官网及宣发材料中；</li><li>捐赠金额同比例抵扣未来&nbsp;<code style="color: rgba(0, 0, 0, 0.8); background-color: rgb(247, 247, 249);">IoTOS</code>&nbsp;的付费产品价格。</li></ul><p>如果您对长期赞助&nbsp;<code style="color: rgba(0, 0, 0, 0.8); background-color: rgb(247, 247, 249);">IoTOS</code>&nbsp;团队感兴趣，或者有其他好想法，欢迎联系开发团队微信&nbsp;<code style="color: rgba(0, 0, 0, 0.8); background-color: rgb(247, 247, 249);">iotos_top</code>，或发送邮件到&nbsp;<code style="color: rgba(0, 0, 0, 0.8); background-color: rgb(247, 247, 249);">card@iotos.top</code>。</p>',
  },


}
