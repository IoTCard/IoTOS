import tools from "@/utils/iotos/tools";

export default {
  common:{//通用
    success:"操作成功",
    fail:"操作失敗",
    save:"新增",
    edie:"編輯",
    delete:"刪除",
    preserve:"保存",
    find:"查看",
    details:"詳情",
    operate:"操作",
    name:"名稱",
    nickName:"暱稱",
    search:"搜索",
    reset:"重置",
    state:"狀態",
    loginAddress:"登錄地址",
    loginAccount:"登錄賬號",
    loginPassword:"登錄密碼",
    showSearch:"顯示搜索",
    hideSearch:"隱藏搜索",
    refresh:"刷新",
    VisibleColumn:"顯隱列",
    queryConditions:"查詢條件",
    queryValue:"查詢值",
    show:"顯示",
    hide:"隱藏",
    pleaseChoose:"請選擇",
    notify:'通知',
    GCGCorrect:'語法正確',
    networkAnomaly:"網絡異常",
    whetherToConfirm:"是否確認 ",
    belongsTo:"所屬企業",
    import:"導入",
    export:"導出",
    toDivide:"劃分",
    batchOperation:"批量操作",
    uploadText:"將文件拖到此處，或",
    uploadTextEm:"點擊上傳",
    downloadTemplate:"下載模板",
    uploadTip:"提示：僅允許導入'xls'或'xlsx'格式文件！",
    sure:"確定",
    cancel:"取消",
    numberType:"號碼類型",
    pleaseEnter:"請輸入",
    expand_collapse:"展開/折疊",
    selectAll_notSelectAll:"全選/全不選",
    fatherSonlinkage:"父子聯動",
    feedback:"反饋信息",
    renew:"更新",
    performTasks:"執行任務",
    revoke:"撤銷",
    download:"下載",
    noData:"暫無數據",
    copySuccess:"複製成功",
    used:"已使用",
    left:"剩餘",
    flowUsed:"已使用",
    dayFlow:"日用量",
    synchronization:"信息同步中",
    synced:"已同步信息",
    sessionCreationTime:"會話創建時間",
    returnedMessages:"返回信息",
    hint:"提示",
    chosen:"當前已選擇 ",
    articleData:" 條數據 ",

    currentMonth:"當月",
    lastMonth:"上月",
    aYearAgo:"一年前",
    data:'條 數據',
    moreOperations:"更多",
    noDataFound:"未找到匹對數據！",

    today:"今天",
    yesterday:"昨天",
    weekAgo:"一周前",
    startDate:"開始日期",
    endDate:"結束日期",
    timeType:'時間類型',
    to:'至',
    sortType:'排序類型',
    descendingOrder:'升降序',
    startStopConditions:'起止條件',
    startNumberSegment:'開始號段',
    endNumberSegment:'結束號段',
    multiDimension :'多維度',
    dimensionField:'維度字段',
    dimensionValue:'維度值',
    dimensionCondition:'維度條件',
    lastWeek:'最近一周',
    lastRecentMonth:'最近一個月',
    lastThreeMonths:'最近三個月',

    open:'張',
    indivual:'個',
    share:'份',
    yuan:'元',

    month:'月',
    day:'日',


    ask:{//通用詢問
      wDelName:"是否確認 [刪除] ‘名稱‘ 為：",
      ask:"確定將",
      execute:"是否執行",

    },
    rs:{//通用提示
      http:"請輸入 以http://或者以https://開頭地址",
      state:"請選擇 狀態！",
      name:"請輸入 名稱",
      nickName:"請輸入 暱稱",
      deptName:"請輸入企業名稱",
    }
  },




  index:{
    cardCount:"卡總數",
    proxyCardCount:"代理卡總數",
    directCardCount:"直屬卡總數",
    deptCount:"企業總數",
    loginCount:"本月登錄IP總數",
    saveCount:"本月新增卡號",
    activationCount:"本月激活卡號",
    onlineUser:"在線用戶",
    basic:{
      basicInfo:"基礎信息",
      statisticalDeadline:"統計截止日期：",
      ps:"統計時間與當前時間 默認時差 15分鐘（PS:15分鐘內數據不會變更）",

    },

    dailyData:"昨日用量",
    monthlyData:"本月用量",
    statisticalCutoff:"統計截止: ",
    yesterdayTopRanking:"TOP排行-昨日",
    thisMonthTopRanking:"TOP排行-本月截止昨日",
    monthlyDosage:"月用量GB",
    dailyDosage:"日用量GB",
    dosage:"用量(MB)",

    hint:{
      daily:"日數據顯示昨日用量排行榜[MB]，折線圖用量規格為[GB]。",
      month:"月數據顯示當月用量排行榜[MB]，折線圖用量規格為[GB]。",
    },
    cardNumberBulletinBoard:'卡號看板',
    LoginBulletinBoard:'登錄看板',
    lifeCycleTitle:'生命週期分佈',
    activateTrend:'激活月增長趨勢',
    deptActivateTitle:'企業激活比例',
    deptProportionTitle:'企業拿卡比例',
    deptUsageTitle:'企業用量比例',
    BasicKanban:"基礎信息看板",
    channelTitle:'通道卡號比例',
    loginPieTitle:'用戶登錄次數比例',
    loginMapTitle:'用戶登錄地圖標記',

  },

  tasksDetails:{//執行任務詳情
    success:"成功",
    fail:"失敗",
    pending:"待處理",

    table_3:{
      i_1:'ICCID',
      i_2:'業務類型',
      i_3:'狀態',
      i_4:'開始時間',
      i_5:'結束時間',
      i_6:'創建時間',
      i_7:'備註',

    },

  },



  apiInquireIMEI:{//批量查詢 IMEI
    title:"查詢IMEI",

    ask:{//詢問
      upd:"是否確認 執行批量查詢 IMEI ?",
    },
    rs:{
      file:"請選擇上傳文件！",
    }

  },


  cardApiBusinessHandling:{//API業務辦理
    title:"業務辦理",
    openClose:"停復機 & 斷開網",
    gprsReset:"網絡重置",
    shutdown:"斷開網",
    stop:"停復機",
    flexibleChange:"靈活變更狀態",
    tip:{
      reset:"遇到上網功能異常的時候可以通過調用該接口來重置恢復GPRS上網功能；（選擇 ‘是’ 時執行）",
      openStop:"停復機：對卡號執行 停機 復機 操作；（PS：長期停機可能會產生停機保號費用，且可能會銷戶卡號！具體根據上游運營商而定！）",
      openClose:"斷開網：對卡號執行 關閉網絡連接 打開網絡連接 操作；（PS：該功能類似手機數據流量打開關閉。）",
      flexibleChange:"靈活變更卡狀態：適配部分上游API，（PS：且停機原因為主動申請停機時才能複機 其他原因需要聯繫客戶經理處理！）",
    },
    ask:{//詢問
      upd:"是否確認 針對上傳卡號執行 ",
    },
    rs:{
      flexibleChange:"請選擇需要 靈活變更的狀態！",
      file:"請選擇上傳文件！",
      openClose:"請選擇需要 停復機 或 斷開網的操作類型！",
      gprsReset:"請選擇是否需要 執行 網絡重置！",
    }

  },


  cardInfoRenew:{//卡信息更新
    dividerTitle:"卡信息批量更新",
    tip:{
      number:"如：號碼類型 選擇【ICCID】則 ICCID 為必填！且根據匹對的ICCID 進行修改更新操作",
    },
    ask:{//通用詢問
      upd:"是否確認 批量更新卡信息？",
    },
    rs:{
      batchType:"請選擇 號碼類型！",
      file:"請選擇上傳文件！",
    }

  },

  performTasks_index:{//執行任務
    table:{
      i_1:'任務名稱',
      i_2:'編號',
      i_3:'任務類別',
      i_4:'開始時間',
      i_5:'結束時間',
      i_6:'企業',
      i_7:'操作賬號',
      i_8:'失效日期',
      i_9:'備註',
    },
    table_1:{
      i_1:'文件名',i_2:'開始時間',
      i_3:'結束時間',
      i_4:'類型',
      i_5:'下載次數',
    },

    table_2:{
      i_1:'企業',
      i_2:'暱稱',
      i_3:'IP',
      i_4:'下載時間',
    },
    fileList:"執行任務-文件列表",
    downloadList:"文件-下載記錄",
    tasksDetails:"執行任務-詳情",


    ask:{
      revokeCardDivid_1:"已分配給",
      revokeCardDivid_2:"進行操作撤回？",

    },

  },

  cardInfoModule: {//卡 拓展信息
    cardSession:"會話信息",
    cardApiBusiness:"變更記錄",
    cbefore:" 變更前 ",
    cafterward:" 變更後 ",
    source:" 來源 ",

    last30Days:"最近30天",
    flowRecord:"流量使用",
    session:{
      accessMode:"無線接入模式",
      accessMethod:"接入方式",
      onlineStatus:"在線狀態",
      iPAddress:"IP地址",
      hint1:"請核對API信息（未產生用量不會有在線記錄！）",
      hint2:"僅支持 上游開放查詢會話信息API獲取",
      error1:"暫未獲取到會話信息",
    }

  },




  cardInfoDetails:{//卡基礎信息

    copyCardInfo:"複製 基礎信息",



    tFrom:{
      operator:"運營商",
      roaming_country:"漫遊國家",
      internet_signal:"網絡信號",
      ip_attribution:"IP歸屬地",
      status_show_id:"卡狀態",
      status_id:"狀態描述",

      type:"卡類型",
      network_type:"網絡類型",
      nedd_real_name:"需要實名",
      w_real_name:"已實名",
      deliver_date:"發貨日期",
      activate_date:"激活時間",
      traffic_sync_time:"用量同步時間",
      state_sync_time:"狀態同步時間",
      channel_id:"所屬通道",
      package_id:"所屬資費組",
      u_code:"所屬用卡人",
      w_polling:"輪詢",
      automatic_renewal:"自動續費",
      w_sms:"短信",
      w_voice:"語音",
      w_network_break:"未訂購斷網",
      shutdown_threshold:"停機閾值",
      prodinsteff_time:"生效時間",
      prodinstexp_time:"失效時間",
      connection_status:"網絡狀態",
      w_pool:"流量池",
      balance:"預存餘額",
      payment_key:"支付密碼",
      customize_grouping:"分組",
      remarks:"備註",

      first_time_using:"首次用量使用時間",
      create_time:"創建時間",
      storage_date:"入庫日期",
      delivery_date:"出庫日期",







    }

  },


  card_index:{// card
    updateGroupAndNotes:'更新分組及備註',
    batchQueryIMEI:'查詢IMEI',
    batchShutdown:'業務辦理',
    updateCardInformation:'更新卡信息',
    oneKeySync:'一鍵同步',
    syncUsage:'同步用量',
    syncStatus:'同步狀態',
    syncRealName:'同步實名狀態',
    syncIMEI:'同步IMEI',
    synActivateDate:'同步激活時間',
    editCard:"更新卡信息",
    updForm:{
      remarks:"備註：",
      updateNotFilled:"更新未填寫屬性：",
      delivery_date:"發貨日期：",
      customize_grouping:"自定義分組：",
    },

    table:{
      i_1:'編號',
      i_2:'MSISDN',
      i_3:'ICCID',
      i_4:'IMSI',
      i_5:'IMEI',
      i_6:'卡狀態',
      i_7:'通道',
      i_8:'企業',
      i_9:'已用(MB)',
      i_10:'剩餘(MB)',
    },

    rs:{//表單驗證
      dept_id:'請選擇所屬企業！ ',
      radio:'單選操作請勿多選！ ',
      updIccid:'操作參數缺失！請刷新後重試！ ',
      unfilled:'請選擇需要更新的信息！ ',
    },
    ask:{
      upd:'確認更新 ',
      cardDivid_2:"條數據  劃分給",
    }

  },

  channel_index:{// channel
    table:{
      i_1:'編號',
      i_2:'模板',
      i_3:'名稱',
      i_4:'暱稱',
      i_5:'狀態',
      i_6:'輪詢',
      i_7:'卡總數',
      i_8:'總用量',
    },
    form:{//表單
      template:'模板',
      polling:'輪詢',
      basicConfiguration:'基礎配置',
      synchronizationStrategy:'同步策略',
      auxiliaryRegistration:'輔助登記',
      requestAddress:'請求地址',
      configurationOne:'配置一',
      configurationTwo:'配置二',
      ConfigurationTree:'配置三',
      importAutomatically:'自動導入',
      synchronizationType:'同步類型',
      syncField:'同步字段',
    },
    rs:{//表單驗證
      template:'模板 不能為空！ ',
      polling:'請選擇 輪詢！ ',
      state:'請選擇 狀態',
      requestAddress:'請求地址 不能為空！ ',
      configurationOne:'配置一 不能為空！ ',
      configurationTwo:'配置二 不能為空！ ',
      ConfigurationTree:'配置三 不能為空！ ',
    }


  },

  login: {
    title: 'IoTOS後台管理系統',
    logIn: '登錄',
    username: '賬號',
    password: '密碼',
    r_password: '記住密碼',
    vcode_title: '驗證碼',
    rs_username: '用戶名不能為空',
    rs_password: '密碼不能為空',
    rs_vcode: '驗證碼不能為空',
    login_pt: '登錄中',
  },
  tagsView: {
    refresh: '刷新頁面',
    close: '關閉當前',
    closeOthers: '關閉其它',
    closeAll: '關閉所有',
    closeLeft: '關閉左側',
    closeRight: '關閉右側',
  },
  settings: {
    title: '主題風格設置',
    theme: '主題顏色',
    topNav: '開啟 TopNav',
    tagsViews: '開啟 Tags-Views',
    fixedHeader: '固定 Header',
    sidebarLogo: '側邊欄 Logo',
    dynamicTitle: '動態標題',
    layoutConfiguration: '系統佈局配置',
    saveSetting: '保存配置',
    resetSetting: '重置配置',
  },
  Navbar:{//右標題操作
    personal_center:'個人中心',
    layout_settings:'佈局設置',
    sign_out:'退出登錄',
    source_address:'源碼地址',
    document_address:'文檔地址',
    full_screen:'全屏',
    layout_size:'佈局大小',
  },


  menu:{//菜單解析
    index:'首頁',
    System_Management:'系統管理',
    System_monitoring:'系統監控',
    System_Tool:'系統工具',
    IoTOS_official_website:'關於 IoTOS',
    User_Management:'用戶管理',
    role_management:'角色管理',
    menu_management:'菜單管理',
    department_management:'部門管理',
    job_management:'崗位管理',
    dictionary_management:'字典管理',
    parameter_settings:'參數設置',
    announcement:'通知公告',
    log_management:'日誌管理',
    online_user:'在線用戶',
    timed_task:'定時任務',
    data_monitoring:'數據監控',
    service_monitoring:'服務監控',
    cache_monitoring:'緩存監控',
    cache_list:'緩存列表',
    form_building:'表單構建',
    code_generation:'代碼生成',
    system_interface:'系統接口',
    operation_log:'操作日誌',
    login_log:'登錄日誌',
    user_query:'用戶查詢',
    new_user:'用戶新增',
    user_modification:'用戶修改',
    user_delete:'用戶刪除',
    user_export:'用戶導出',
    user_import:'用戶導入',
    reset_Password:'重置密碼',
    role_query:'角色查詢',
    new_role:'角色新增',
    role_modification:'角色修改',
    role_deletion:'角色刪除',
    character_export:'角色導出',
    menu_query:'菜單查詢',
    New_menu:'菜單新增',
    menu_modification:'菜單修改',
    menu_delete:'菜單刪除',
    Department_inquiry:'部門查詢',
    new_department:'部門新增',
    department_modification:'部門修改',
    department_delete:'部門刪除',
    job_search:'崗位查詢',
    new_jobs:'崗位新增',
    job_modification:'崗位修改',
    post_delete:'崗位刪除',
    post_export:'崗位導出',
    dictionary_lookup:'字典查詢',
    dictionary_added:'字典新增',
    dictionary_modification:'字典修改',
    dictionary_deletion:'字典刪除',
    Dictionary_export:'字典導出',
    parameter_query:'參數查詢',
    New_parameters:'參數新增',
    parameter_modification:'參數修改',
    parameter_deletion:'參數刪除',
    Parameter_export:'參數導出',
    Announcement_query:'公告查詢',
    Announcement_added:'公告新增',
    Announcement_modification:'公告修改',
    Announcement_delete:'公告刪除',
    Operation_query:'操作查詢',
    operation_delete:'操作刪除',
    log_export:'日誌導出',
    login_query:'登錄查詢',
    Login_to_delete:'登錄刪除',
    account_unlock:'賬戶解鎖',
    online_search:'在線查詢',
    Forced_withdrawal_in_batches:'批量強退',
    single_forced_exit:'單條強退',
    task_query:'任務查詢',
    new_task:'任務新增',
    task_modification:'任務修改',
    task_delete:'任務刪除',
    status_modification:'狀態修改',
    task_export:'任務導出',
    generate_query:'生成查詢',
    generate_modification:'生成修改',
    generate_delete:'生成刪除',
    import_code:'導入代碼',
    preview_code:'預覽代碼',
    generate_code:'生成代碼',
    connect:'連接',
    traffic_card:'流量卡',
    aisle:'通道',
    card_list:'卡列表',
    List_of_pin_numbers:'銷號列表',
    billing_group:'計費組',
    Recharge_management:'充值管理',
    performTasks:'執行任務',
    headquarters:'總部標識',
    personal_center:'個人中心',
    downloadFile:'任務文件下載',
    flieDownload:'查看文件下載記錄',
    dictionaryData:'字典數據',
    assigningRoles:'分配角色',
    assignUsers:'分配用戶',
    schedulingLog:'調度日誌',
    modifyGeneration:'修改生成配置',
    accountCenter:'賬號中心',
  },

  IoTOS:{
    introduce:'IoTOS 基於多個物聯網管理系統API（如:中國移動 oneLink 等 後續接入API） 開放能力，不僅集成了上游強大的API管理及基礎的 數據同步算法 功能，而且提供了多語言國際化方案。並通過 高效靈活的同步算法、系統構架業務分離 等靈活高效的數據運營模塊，讓企業與上游之間建立強鏈接，從而進一步通過多元化的管理運營方案，幫助企業提高物聯網卡運營效率，強化運營能力，拓展可盈利空間。 ',
    currentVersion:'當前版本：',
    gitEE:'Gitee 地址',
    gitHub:'GitHub 地址',
    free:'￥免費開源',
    receiveAlibabaCloudCoupons:'￥領取阿里雲優惠券',
    doc:'文檔地址',
    technologySelection:'技術選型',
    backendTechnology:'後端技術',
    frontEndTechnology:'前端技術',
    contactInformation:'聯繫信息',
    officialWebsite:'官網',
    weChat:'微信',
    mail:'郵箱',
    updateLog:'更新日誌',
    v100:{
      l00:'IoTOSc系統正式發布；',
      l01:'多語言國際化；',
      l02:'通道、卡列表、用量記錄、基礎業務；',
      l03:'卡號自動同步載入策略；',
      l04:'API目前僅支持 OneLink EcV5 接口(最終會依賴該接口作為一個全面對接的展示業務 敬請期待！)；',
      l05:'OneLink EcV5同步算法策略 隊列多線程中使用java多線程套娃式顯著提升算法同步效率；',
      l06:'用量、生命週期補償算法完善；',
      l07:'首頁統計數據完善；',
    },
    visitMainSite:'訪問主站點',
  },



}
