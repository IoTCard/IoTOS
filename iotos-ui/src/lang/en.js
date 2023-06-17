import tools from "@/utils/iotos/tools";

export default {
  common:{//Common
    success: "The operation was successful",
    fail: "Operation failed",
    save: "Add",
    edie: "edit",
    delete: "Delete",
    preserve: "preserve",
    find: "View",
    details: "Details",
    operate: "operation",
    name: "Name",
    nickName: "nickname",
    search: "search",
    reset: "reset",
    state: "state",
    loginAddress: "login address",
    loginAccount: "login account",
    loginPassword: "login password",
    showSearch: "Show search",
    hideSearch: "Hide Search",
    refresh: "Refresh",
    VisibleColumn: "Visible column",
    queryConditions: "query conditions",
    queryValue: "query value",
    show: "display",
    hide: "Hide",
    pleaseChoose: "Please choose",
    notify: 'Notification',
    GCGCorrect: 'Grammar correct',
    networkAnomaly: "Network Anomaly",
    whetherToConfirm: "Whether to confirm",
    belongsTo: "Affiliated Enterprise",
    import: "import",
    export: "export",
    toDivide: "Divide",
    batchOperation: "Batch operation",
    uploadText: "Drag the file here, or",
    uploadTextEm: "Click to upload",
    downloadTemplate: "Download Template",
    uploadTip: "Tip: Only 'xls' or 'xlsx' format files are allowed to be imported!",
    sure: "OK",
    cancel: "Cancel",
    numberType: "number type",
    pleaseEnter: "Please enter",
    expand_collapse: "Expand/Collapse",
    selectAll_notSelectAll: "Select All/Not Select All",
    fatherSonlinkage: "Father-son linkage",
    feedback: "Feedback",
    renew: "Renew",
    performTasks: "Perform tasks",
    revoke: "revoke",
    download: "download",
    noData: "No data yet",
    copySuccess: "Copy successful",
    used: "used",
    left: "remaining",
    flowUsed: "Used",
    dayFlow: "daily usage",
    synchronization: "Information synchronization",
    synced: "Synced information",
    sessionCreationTime: "session creation time",
    returnedMessages: "returned messages",
    hint: "hint",
    chosen: "Currently selected",
    articleData: "article data",

    currentMonth: "current month",
    lastMonth: "Last month",
    aYearAgo: "One year ago",
    data: 'article data',
    moreOperations: "More",
    noDataFound: "No matching data found!",

    today: "today",
    yesterday: "Yesterday",
    weekAgo: "A week ago",
    startDate: "start date",
    endDate: "end date",
    timeType:'time type',
    to:'to',
    sortType:'sort type',
    descendingOrder: 'descending order',
    startStopConditions: 'Start and stop conditions',
    startNumberSegment: 'start number segment',
    endNumberSegment:'end number segment',
    multiDimension : 'multi-dimensional',
    dimensionField: 'dimension field',
    dimensionValue:'dimension value',
    dimensionCondition: 'dimension condition',
    lastWeek: 'The last week',
    lastRecentMonth:'The last month',
    lastThreeMonths:'The last three months',

    open:'Zhang',
    individual: 'one',
    share:'share',
    yuan: 'Yuan',

    month:'month',
    day:'day',


    ask:{//General inquiry
      wDelName: "Are you sure [Delete] 'Name' is:",
      ask: "OK",
      execute: "Whether to execute",

    },
    rs:{//General prompt
      http: "Please enter an address starting with http:// or https://",
      state: "Please select a state!",
      name: "Please enter a name",
      nickName: "Please enter a nickname",
      deptName: "Please enter the company name",
    }
  },




  index: {
    cardCount: "total number of cards",
    proxyCardCount: "Total number of proxy cards",
    directCardCount: "Total number of direct cards",
    deptCount: "Total number of enterprises",
    loginCount: "Total number of IP logins this month",
    saveCount: "Add new card numbers this month",
    activationCount: "Activation card number this month",
    onlineUser: "Online User",
    basic: {
      basicInfo: "Basic information",
      statisticalDeadline: "Statistical Deadline:",
      ps: "The default time difference between the statistical time and the current time is 15 minutes (PS: the data will not change within 15 minutes)",

    },

    dailyData: "Yesterday's usage",
    monthlyData: "This month's usage",
    statisticalCutoff: "statistical cutoff: ",
    yesterdayTopRanking: "TOP Ranking - Yesterday",
    thisMonthTopRanking: "TOP ranking - this month ends yesterday",
    monthlyDosage: "Monthly Dosage GB",
    dailyDosage: "Daily Dosage GB",
    dosage: "Dosage (MB)",

    hint: {
      daily: "The daily data shows yesterday's usage leaderboard [MB], and the line chart usage specification is [GB].",
      month: "The monthly data shows the current month's usage leaderboard [MB], and the line chart usage specification is [GB].",
    },
    cardNumberBulletinBoard: 'card number board',
    LoginBulletinBoard: 'Login Kanban',
    lifeCycleTitle: 'Life Cycle Distribution',
    activateTrend: 'Activate monthly growth trend',
    deptActivateTitle: 'Enterprise activation ratio',
    deptProportionTitle: 'Proportion of enterprises taking cards',
    deptUsageTitle: 'Enterprise usage ratio',
    BasicKanban: "Basic Information Kanban",
    channelTitle:'channel card number ratio',
    loginPieTitle: 'Proportion of user login times',
    loginMapTitle: 'User login map marker',

  },

  tasksDetails:{//execution task details
    success: "Success",
    fail: "Failure",
    pending: "pending",

    table_3: {
      i_1:'ICCID',
      i_2:'Business type',
      i_3:'Status',
      i_4:'start time',
      i_5:'End time',
      i_6:'Creation time',
      i_7:'Remarks',

    },

  },



  apiInquireIMEI:{//Batch query IMEI
    title: "Query IMEI",

    ask:{//ask
      upd: "Are you sure to perform batch query IMEI?",
    },
    rs: {
      file: "Please choose to upload a file!",
    }

  },


  cardApiBusinessHandling:{//API business handling
    title: "Business Handling",
    openClose: "Shutdown & disconnect network",
    gprsReset: "network reset",
    shutdown: "disconnect network",
    stop: "Stop and resume machine",
    flexibleChange: "flexible change state",
    tip:{
      reset: "When the Internet access function is abnormal, you can reset and restore the GPRS Internet access function by calling this interface; (execute when you select 'Yes')",
      openStop: "Stop and restart: execute the shutdown and restart operation on the card number; (PS: Long-term shutdown may result in a shutdown insurance fee, and may cancel the account card number! It depends on the upstream operator!)",
      openClose: "Disconnect from the network: perform the operation of closing the network connection and opening the network connection for the card number; (PS: this function is similar to opening and closing mobile phone data traffic.)",
      flexibleChange: "Flexibly change the status of the card: adapt to some upstream APIs, (PS: and the reason for the downtime is an active application for downtime, you can only resume the machine. For other reasons, you need to contact the account manager to deal with it!)",
    },
    ask:{//ask
      upd: "Whether to confirm the execution of the uploaded card number",
    },
    rs: {
      flexibleChange: "Please select the state that needs to be changed flexibly!",
      file: "Please choose to upload a file!",
      openClose: "Please select the type of operation that requires shutdown or disconnection!",
      gprsReset: "Please choose whether to perform network reset!",
    }

  },


  cardInfoRenew:{//Card information update
    dividerTitle: "Card information batch update",
    tip:{
      number: "For example: if you select [ICCID] for the number type, the ICCID is required! And the modification and update operation will be performed according to the matching ICCID",
    },
    ask:{//General inquiry
      upd: "Are you sure to update card information in batches?",
    },
    rs: {
      batchType: "Please select the number type!",
      file: "Please choose to upload a file!",
    }

  },

  performTasks_index:{//Perform tasks
    table: {
      i_1:'task name',
      i_2:'Number',
      i_3: 'task category',
      i_4:'start time',
      i_5:'End time',
      i_6: 'Enterprise',
      i_7:'Operation account',
      i_8:'expiration date',
      i_9:'Remarks',
    },
    table_1: {
      i_1:'file name',
      i_2:'start time',
      i_3:'end time',
      i_4:'type',
      i_5:'Download times',
    },

    table_2: {
      i_1: 'Enterprise',
      i_2:'nickname',
      i_3:'IP',
      i_4:'Download time',
    },
    fileList: "execute task - file list",
    downloadList: "File - Download Record",
    tasksDetails: "execution task-details",


    ask: {
      revokeCardDivid_1: "allocated to",
      revokeCardDivid_2: "Revoke the operation?",

    },

  },

  cardInfoModule: {//Card extended information
    cardSession: "session information",
    cardApiBusiness: "Change Record",
    cbefore: "before the change",
    cafterward: "after the change",
    source: "source",

    last30Days: "The last 30 days",
    flowRecord: "flow usage",
    session: {
      accessMode: "Wireless Access Mode",
      accessMethod: "Access Method",
      onlineStatus: "Online Status",
      iPAddress: "IP address",
      hint1: "Please check the API information (there will be no online records if there is no usage generated!)",
      hint2: "Only supports upstream open query session information API acquisition",
      error1: "The session information has not been obtained yet",
    }

  },




  cardInfoDetails:{//card basic information

    copyCardInfo: "Copy basic information",



    tFrom: {
      operator: "operator",
      roaming_country: "roaming country",
      internet_signal: "Internet signal",
      ip_attribution: "IP attribution",
      status_show_id: "Card Status",
      status_id: "status description",

      type: "card type",
      network_type: "Network type",
      nedd_real_name: "A real name is required",
      w_real_name: "Realized",
      deliver_date: "Delivery date",
      activate_date: "Activation time",
      traffic_sync_time: "Traffic synchronization time",
      state_sync_time: "state synchronization time",
      channel_id: "Channel to which it belongs",
      package_id: "belonging tariff group",
      u_code: "Cardholder",
      w_polling: "Polling",
      automatic_renewal: "Automatic renewal",
      w_sms: "SMS",
      w_voice: "Voice",
      w_network_break: "Unsubscribed network break",
      shutdown_threshold: "shutdown threshold",
      prodinsteff_time: "effective time",
      prodinstexp_time: "Expiration time",
      connection_status: "Network Status",
      w_pool: "traffic pool",
      balance: "pre-deposited balance",
      payment_key: "payment password",
      customize_grouping: "Grouping",
      remarks: "Remarks",

      first_time_using: "Using time for the first time",
      create_time: "Creation time",
      storage_date: "Storage date",
      delivery_date: "Delivery date",







    }

  },


  card_index:{//card
    updateGroupAndNotes: 'Update group and notes',
    batchQueryIMEI: 'query IMEI',
    batchShutdown: 'business processing',
    updateCardInformation: 'update card information',
    oneKeySync: 'one key synchronization',
    syncUsage: 'Sync usage',
    syncStatus: 'Sync Status',
    syncRealName: 'Sync real name status',
    syncIMEI: 'Sync IMEI',
    synActivateDate: 'synchronous activation time',
    editCard: "Update card information",
    updForm: {
      remarks: "Remarks:",
      updateNotFilled: "Update unfilled attributes:",
      delivery_date: "Delivery date:",
      customize_grouping: "Customize grouping:",
    },

    table: {
      i_1:'Number',
      i_2:'MSISDN',
      i_3:'ICCID',
      i_4:'IMSI',
      i_5:'IMEI',
      i_6:'card status',
      i_7:'channel',
      i_8: 'Enterprise',
      i_9:'Used (MB)',
      i_10: 'remaining (MB)',
    },

    rs:{//form validation
      dept_id: 'Please select the company you belong to! ',
      radio:'Single selection operation, please do not select multiple selections! ',
      updIccid: 'The operation parameter is missing! Please refresh and try again! ',
      unfilled:'Please select the information to be updated! ',
    },
    ask: {
      upd:'Confirm update',
      cardDivid_2: "The piece of data is divided into",
    }

  },

  channel_index: {// channel
    table: {
      i_1:'Number',
      i_2:'Template',
      i_3:'name',
      i_4:'nickname',
      i_5:'Status',
      i_6: 'Polling',
      i_7: 'Total number of cards',
      i_8:'Total usage',
    },
    form:{//form
      template:'template',
      polling: 'polling',
      basicConfiguration: 'basic configuration',
      synchronizationStrategy: 'Synchronization Strategy',
      auxiliaryRegistration: 'auxiliary registration',
      requestAddress: 'request address',
      configurationOne: 'configuration one',
      configurationTwo: 'configuration two',
      ConfigurationTree: 'Configuration three',
      importAutomatically: 'Automatically import',
      synchronizationType: 'synchronization type',
      syncField: 'Sync field',
    },
    rs:{//form validation
      template:'The template cannot be empty! ',
      polling:'Please choose polling! ',
      state: 'Please select a state',
      requestAddress: 'The request address cannot be empty! ',
      configurationOne:'Configuration One cannot be empty! ',
      configurationTwo:'Configuration two cannot be empty! ',
      ConfigurationTree: 'Configuration three cannot be empty! ',
    }


  },

  login: {
    title: 'IoTOS background management system',
    logIn: 'login',
    username: 'Account',
    password: 'password',
    r_password: 'Remember password',
    vcode_title: 'Verification code',
    rs_username: 'Username cannot be empty',
    rs_password: 'Password cannot be empty',
    rs_vcode: 'Verification code cannot be empty',
    login_pt: 'logging in',
  },
  tagsView: {
    refresh: 'Refresh the page',
    close: 'Close the current',
    closeOthers: 'Close others',
    closeAll: 'Close all',
    closeLeft: 'Close the left',
    closeRight: 'Close the right',
  },
  settings: {
    title: 'Theme Style Settings',
    theme: 'theme color',
    topNav: 'Open TopNav',
    tagsViews: 'Open Tags-Views',
    fixedHeader: 'Fixed Header',
    sidebarLogo: 'sidebar Logo',
    dynamicTitle: 'Dynamic title',
    layoutConfiguration: 'System layout configuration',
    saveSetting: 'Save configuration',
    resetSetting: 'Reset configuration',
  },
  Navbar:{//right title operation
    personal_center:'personal center',
    layout_settings: 'layout settings',
    sign_out: 'Log out',
    source_address:'source address',
    document_address: 'document address',
    full_screen: 'full screen',
    layout_size: 'Layout size',
  },


  menu:{//menu analysis
    index:'Home',
    System_Management: 'System Management',
    System_monitoring: 'system monitoring',
    System_Tool: 'system tool',
    IoTOS_official_website: 'About IoTOS',
    User_Management: 'User Management',
    role_management: 'role management',
    menu_management: 'menu management',
    department_management: 'Department Management',
    job_management: 'job management',
    dictionary_management: 'dictionary management',
    parameter_settings: 'parameter settings',
    announcement: 'Notice Announcement',
    log_management: 'log management',
    online_user: 'online user',
    timed_task: 'timed task',
    data_monitoring: 'data monitoring',
    service_monitoring: 'Service Monitoring',
    cache_monitoring: 'cache monitoring',
    cache_list: 'cache list',
    form_building: 'form construction',
    code_generation: 'code generation',
    system_interface: 'system interfacemouth',
    operation_log: 'operation log',
    login_log: 'login log',
    user_query: 'User query',
    new_user: 'Add new user',
    user_modification: 'user modification',
    user_delete: 'User delete',
    user_export: 'User export',
    user_import: 'user import',
    reset_Password: 'reset password',
    role_query: 'role query',
    new_role: 'Add new role',
    role_modification: 'role modification',
    role_deletion: 'role deletion',
    character_export: 'role export',
    menu_query: 'menu query',
    New_menu: 'New Menu',
    menu_modification: 'menu modification',
    menu_delete: 'menu delete',
    Department_inquiry: 'Department inquiry',
    new_department: 'New department',
    department_modification: 'department modification',
    department_delete: 'department delete',
    job_search: 'job search',
    new_jobs: 'New jobs',
    job_modification: 'job modification',
    post_delete: 'post delete',
    post_export: 'post export',
    dictionary_lookup: 'dictionary lookup',
    dictionary_added: 'dictionary added',
    dictionary_modification: 'dictionary modification',
    dictionary_deletion: 'dictionary deletion',
    Dictionary_export: 'dictionary export',
    parameter_query: 'parameter query',
    New_parameters: 'Add parameters',
    parameter_modification: 'parameter modification',
    parameter_deletion: 'parameter deletion',
    Parameter_export: 'parameter export',
    Announcement_query: 'Announcement query',
    Announcement_added: 'Announcement added',
    Announcement_modification: 'Announcement modification',
    Announcement_delete: 'Announcement delete',
    Operation_query: 'operation query',
    operation_delete: 'operation delete',
    log_export: 'log export',
    login_query: 'login query',
    Login_to_delete: 'login delete',
    account_unlock: 'account unlock',
    online_search: 'Online search',
    Forced_withdrawal_in_batches: 'Batch forced withdrawal',
    single_forced_exit: 'Single forced exit',
    task_query: 'task query',
    new_task: 'Add new task',
    task_modification: 'task modification',
    task_delete: 'task delete',
    status_modification: 'status modification',
    task_export: 'task export',
    generate_query: 'generate query',
    generate_modification: 'Generate modification',
    generate_delete: 'Generate delete',
    import_code: 'import code',
    preview_code: 'Preview code',
    generate_code:'generate code',
    connect:'connect',
    traffic_card: 'traffic card',
    aisle:'channel',
    card_list: 'card list',
    List_of_pin_numbers: 'Pin number list',
    billing_group: 'billing group',
    Recharge_management: 'Recharge management',
    performTasks:'Perform tasks',
    headquarters:'headquarters logo',
    personal_center:'personal center',
    downloadFile: 'Task file download',
    flieDownload:'View file download records',
    dictionaryData: 'dictionary data',
    assigningRoles: 'assigning roles',
    assignUsers: 'assign users',
    schedulingLog: 'scheduling log',
    modifyGeneration: 'Modify the generation configuration',
    accountCenter:'Account Center',
  },
  IoTOS: {
    introduce:'IoTOS is based on the open capabilities of multiple IoT management system APIs (such as: China Mobile oneLink and other follow-up access APIs), which not only integrates powerful upstream API management and basic data synchronization algorithm functions, but also provides multi-language internationalization plan. And through flexible and efficient data operation modules such as efficient and flexible synchronization algorithms and system architecture business separation, a strong link between the enterprise and the upstream can be established, so as to further help enterprises improve the operational efficiency of the Internet of Things card through diversified management and operation solutions, and strengthen Operational capacity, expand the profitable space. ',
    currentVersion: 'Current version:',
    gitEE:'Gitee address',
    gitHub:'GitHub address',
    free:'￥free open source',
    receiveAlibabaCloudCoupons:'￥Receive Alibaba Cloud Coupons',
    doc:'document address',
    technologySelection: 'Technology Selection',
    backendTechnology: 'backend technology',
    frontEndTechnology: 'front-end technology',
    contactInformation: 'Contact Information',
    officialWebsite: 'official website',
    weChat:'WeChat',
    mail:'mailbox',
    updateLog: 'update log',
    v100: {
      l00: 'IoTOSc system is officially released;',
      l01:'multilingual internationalization;',
      l02:'Channel, card list, usage record, basic business;',
      l03:'Card number automatic synchronization loading strategy;',
      l04: 'The API currently only supports the OneLink EcV5 interface (will eventually rely on this interface as a comprehensive docking display business, so stay tuned!);',
      l05:'OneLink EcV5 synchronization algorithm strategy uses java multi-thread nesting dolls in queue multi-threading to significantly improve algorithm synchronization efficiency;',
      l06: 'Usage, life cycle compensation algorithm is perfect;',
      l07: 'The statistics on the homepage are perfect;',
    },
    visitMainSite: 'Visit the main site',
  },




}
