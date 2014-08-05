create schema if not exists `user` default character set utf8;
use user;

drop table if exists `T_USER`;
create table `T_USER`(
    `ID` int not null auto_increment,
    `USER_NAME` varchar(100) not null null default '' comment '用户名' ,
    `PASSWORD` varchar(100) not null default '' comment '用户密码' ,
    `EMAIL` varchar(50) not null default '' comment '用户邮箱' ,
    `PHONE` varchar(20) not null default '' comment '用户手机号码,进行一个简单的加密' ,
    `STATUS` smallint not null default 0 comment '用户状态：\n0：正常使用\n1：屏蔽\n2：删除',
    `CHANNEL_ID` int not null default 0 comment '用户注册渠道',
    `DT_REGISTER_TIME` datetime not null default '0000-00-00 00:00:00' comment '用户创建时间',
    `REGISTER_GAME_ID` int not null default 0 comment '注册游戏id',
    `REGISTER_IP` varchar(50) not null default '' comment '注册ip',
    `REFEREE_ID` int not null default 0 comment '推荐注册用户id',
    `USER_TYPE` smallint not null default 0 comment '用户类型：\n0:云游普通注册用户\n1:其他渠道注册用户\n2:公司员工用户（注册邮箱判断）\n3:渠道商\4:CP',
    `CP_ID` int not null comment 'cp 在网站注册的id',
    `REMARK` varchar(50) not null default '' comment '备注信息',
     primary key(ID),
     key ACS_USER_LOGIN_IDX(USER_NAME, PASSWORD),
     key ACS_USER_REGISTER_USER_TYPE_CHANNEL_IDX(REGISTER_GAME_ID, USER_TYPE, CHANNEL_ID, DT_REGISTER_TIME)
)engine=innodb
default charset=utf8
comment='用户基本信息表'
partition by key(ID) partitions 200;

表设计思路：
1、用户表中的user_name存储用户名或者是第三方渠道的用户id
2、用户登录从redis中获取username对应的id每次查询带上id这样能够通过id分区进行过滤，首次登录没id，但是使用email和phone登录的用户就不能使用索引
3、表中建立了两个索引，主要查询时组合索引最左侧匹配原则
4、表中的username和channelid 是唯一索引，如果用户使用的手机或者邮箱进行注册，那么username字段就自动生产一个唯一的值


drop table if exists `T_USER_INFO`;
create table `T_USER_INFO`(
    `USER_ID` int not null,
    `NICKNAME` varchar(100)  default '' comment '用户昵称',
    `REAL_NAME` varchar(50)  default '' comment '用户真实姓名',
    `ID_CARD_NO` varchar(50)  default '' comment '用户身份证号',
    `QQ` varchar(50) default '' comment 'QQ号码',
    `DT_BIRTHDAY` datetime  default '0000-00-00 00:00:00' comment '生日',
    `AVATAR_URL` varchar(200) default '' comment '用户头像地址',
    `GENDER` smallint default 0 comment '性别',
    `DT_LASTLOING_TIME` datetime default '0000-00-00 00:00:00' comment '最后登录的时间',
    `LASTLOGIN_GAME_ID` int default 0 comment '最后玩的游戏id',
    primary key (USER_ID)
)engine=innodb default charset=utf8
comment='用户扩展信息表'
partition by key(USER_ID) partitions 200;
表设计思路：
1、该表是和用户信息对应的不常用的字段存储表，可能存在user表中有info表中没有的情况

drop table if exists `T_USER_SECURITY`;
create table `T_USER_SECURITY`(
    `ID` int not null auto_increment,
    `USER_ID` int not null,
    `QUESTION` varchar(100) not null default '' comment '密保问题',
    `PID` int not null default 0 comment '问题的id',
    primary key(ID),
    key ACS_USER_QUESTION_IDX(USER_ID, PID)
)engine=innodb
comment='用户密保安全表'
default charset=utf8;
表设计思路：
1、问题与回答使用同一个表的同一个字段，回答的pid是提问的id作为关联，pid默认是0 条件查询所有问题

drop table if exists `T_USER_LOGIN_LOG`;
create table `T_USER_LOGIN_LOG`(
    `USER_ID` int not null,
    `LOGIN_IP` varchar(50) not null default '' comment '登录ip',
    `LOGIN_UA` varchar(30) not null default '' comment '登录设备',
    `DT_LOGIN_DATE` datetime default '0000-00-00 00:00:00' comment '登录时间',
    `GAME_ID` int not null default 0 comment '登录游戏的id',
    `CHANNEL_ID` int not null default 0 comment '渠道id',
    key ACS_USER_LOG_IDX(GAME_ID, DT_LOGIN_DATE)
)engine=myisam
default charset=utf8
comment='用户登录日志表'
partition by range columns (DT_LOGIN_DATE)(
 partition p0 values less than ('2014-06-01'),
 partition p1 values less than ('2015-01-01'),
 partition p2 values less than ('2015-06-01'),
 partition p3 values less than ('2016-01-01'),
 partition p4 values less than ('2016-06-01'),
 partition p5 values less than ('2017-01-01'),
 partition p6 values less than ('2017-06-01'),
 partition p7 values less than ('2018-01-01'),
 partition p8 values less than ('2018-06-01'),
 partition p9 values less than ('2019-01-01'),
 partition p10 values less than ('2019-06-01'),
 partition p11 values less than ('2020-01-01'),
 partition p12 values less than ('2020-06-01'),
 partition p13 values less than ('2021-01-01'),
 partition p14 values less than ('2021-06-01'),
 partition p15 values less than ('2022-01-01'),
 partition p16 values less than ('2022-06-01'),
 partition p17 values less than ('2023-01-01'),
 partition p18 values less than ('2023-06-01'),
 partition p19 values less than ('2024-01-01'),
 partition p20 values less than ('2024-06-01'),
 partition p21 values less than ('2025-01-01'),
 partition p22 values less than ('2025-06-01'),
 partition p23 values less than ('2026-01-01'),
 partition p24 values less than ('2026-06-01'),
 partition p25 values less than ('2027-01-01'),
 partition p26 values less than ('2027-06-01'),
 partition p27 values less than ('2028-01-01'),
 partition p28 values less than ('2028-06-01'),
 partition p29 values less than MAXVALUE);
表设计思路：
1、该表是用户登录的日志信息表，每次登录新增记录
2、可以定期创建该表的备份 备份表使用engine=archive 表大小会比myisam小很多
###################支付####################################
create schema if not exists `pay` default character set utf8;
use pay;

drop table if exists `T_PAY_ORDER`;
create table `T_PAY_ORDER`(
    `ID` int not null auto_increment ,
    `USER_ID` int not null default 0 comment '充值用户id',
    `LOGIN_ACCOUNT` varchar(50) default '' comment '登录用户名|邮箱|手机号',
    `TO_USER_ID` int not null default 0 comment '给其他用户充值id',
    `GAME_ID` int not null default 0 comment '游戏id',
    `GAME_SERVER_ID` int not null default 0 comment '充值游戏大区id',
    `CHANNEL_ID` int not null default 0 comment '渠道id',
    `PAY_CHANNEL_ID` int not null default 0 comment '支付方式id',
    `PAY_CARD_NO` varchar(50) not null default '' comment '充值卡帐号',
    `AMOUNT` int not null default 0 comment '充值金额，分为单位',
    `STATUS` int not null default 0 comment '支付状态：\n0表示支付未成功;\n1表示支付成功;\n2表示通知cp处理成功',
    `ORDER_ID` varchar(50) not null default '' comment '云游支付订单流水号',
    `TRADE_NO` varchar(50) not null default '' comment '对方支付订单流水号',
    `DT_CREATED_TIME` datetime not null default '0000-00-00 00:00:00' comment '订单创建时间',
    `DT_RESULT_TIME` datetime not null default '0000-00-00 00:00:00' comment '订单交易完成时间',
    `DT_NOTIFY_CP_TIME` datetime not null default '0000-00-00 00:00:00' comment 'cp收到通知处理时间',
    `RESULT_MSG` varchar(50) not null comment '对方返回支付的信息',
    `EXT_INFO` varchar(50) not null comment '扩展字段',
    `REMARK` varchar(50) not null default '' comment '备注信息',
    primary key(ID, DT_RESULT_TIME),
    key ORDER_ID_IDX(ORDER_ID),
    key PAY_GAME_RESULT_TIME_IDX(GAME_ID, GAME_SERVER_ID, DT_RESULT_TIME)
)engine = innodb
default charset=utf8
comment='订单表'
partition by range columns (DT_RESULT_TIME)(
 partition p0 values less than ('0000-00-01'),
 partition p1 values less than ('2015-01-01'),
 partition p2 values less than ('2015-06-01'),
 partition p3 values less than ('2016-01-01'),
 partition p4 values less than ('2016-06-01'),
 partition p5 values less than ('2017-01-01'),
 partition p6 values less than ('2017-06-01'),
 partition p7 values less than ('2018-01-01'),
 partition p8 values less than ('2018-06-01'),
 partition p9 values less than ('2019-01-01'),
 partition p10 values less than ('2019-06-01'),
 partition p11 values less than ('2020-01-01'),
 partition p12 values less than ('2020-06-01'),
 partition p13 values less than ('2021-01-01'),
 partition p14 values less than ('2021-06-01'),
 partition p15 values less than ('2022-01-01'),
 partition p16 values less than ('2022-06-01'),
 partition p17 values less than ('2023-01-01'),
 partition p18 values less than ('2023-06-01'),
 partition p19 values less than ('2024-01-01'),
 partition p20 values less than ('2024-06-01'),
 partition p21 values less than ('2025-01-01'),
 partition p22 values less than ('2025-06-01'),
 partition p23 values less than ('2026-01-01'),
 partition p24 values less than ('2026-06-01'),
 partition p25 values less than ('2027-01-01'),
 partition p26 values less than ('2027-06-01'),
 partition p27 values less than ('2028-01-01'),
 partition p28 values less than ('2028-06-01'),
 partition p29 values less than MAXVALUE);

表设计思路：
1、通过result data来进行分区，如果没有回掉处理的数据默认是0000-00-00 数据存储到pnull 这个分区下，可以按照一定时间对这个分区数据
进行清理操作 ALTER TABLE T_PAY_ORDER TRUNCATE PARTITION pnull;


drop table if exists `T_PAY_NOTIFY_LOG`;
create table `T_PAY_NOTIFY_LOG`(
    `ID` int not null auto_increment ,
    `ORDER_ID` varchar(50) not null default '' comment '订单号',
    `STATUS` smallint not null default 0 comment '通知cp处理状态：\n0:处理失败；\n1处理成功；',
    `GAME_ID` int not null default 0 comment '游戏id',
    `RETURN_MSG` varchar(50) not null default '' comment 'CP返回信息',
    `NOTIFY_URL` varchar(100) not null default '' comment '通知cp处理的url',
    `NOTIFY_PARAMS` varchar(500) not null default '' comment '通知cp的请求信息',
    `DT_CREATED_TIME` datetime not null default '0000-00-00 00:00:00' comment '创建时间',
    primary key(ID),
    key PAY_NOTIFY_ORDERID_IDX(ORDER_ID)
)engine = myisam
default charset=utf8
comment='发送游戏通知请求记录表';

drop table if exists `T_PAY_NOTIFY_QUEUE`;
create table `T_PAY_NOTIFY_QUEUE`(
    `ORDER_ID` varchar(50) not null default '' comment '订单号',
    `NOTIFY_URL` varchar(100) not null default '' comment '通知cp处理的url',
    `NOTIFY_PARAMS` varchar(500) not null default '' comment '通知cp的请求信息',
    `DT_NOTIFY_LAST_TIME` datetime not null default '0000-00-00 00:00:00' comment '最后一次通知时间',
    `TIMES` smallint not null default 0 comment '通知次数',
    primary key(ORDER_ID)
)engine=innodb
default charset=utf8
comment='通知发放游戏币队列表';



########################game###############################
create schema if not exists `game` default character set utf8;
use game;

drop table if exists `T_GAME_CP`;
create table `T_GAME_CP` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) default '' comment 'cp名称',
  `SECRET_KEY` varchar(20) NOT NULL default '' comment '请求加密使用的密钥',
  `NOTICE_KEY` varchar(20) NOT NULL  default '' comment '通知cp发放游戏的通知密钥',
  `STATUS` smallint(6) NOT NULL default '1' comment '游戏状态:\n0无效；\n1有效',
  `DT_CREATED_TIME` datetime NOT NULL comment '创建时间',
  PRIMARY KEY (`ID`)
) engine=innodb
default charset=utf8
comment='CP信息表';

ALTER TABLE T_GAME_CP AUTO_INCREMENT=260;

insert into T_GAME_CP (ID, NAME, SECRET_KEY, NOTICE_KEY, STATUS, DT_CREATED_TIME)  values(231, '上海方寸', 'MkNPREVyOEo=', 'cGl0ZlRCYWI=', 1, now());

drop table if exists `T_GAME`;
create table `T_GAME`(
    `ID` int not null AUTO_INCREMENT,
    `NAME` varchar(50) not null comment '游戏的名字:格式为\n 地区:游戏的名字',
    `ALIAS` varchar(15) not null comment '游戏别名，拼音或者是游戏英文名',
    `PACKAGE_NAME` varchar(100) not null comment '游戏包名',
    `CP_ID` int not null comment 'CP_ID',
    `SEQ_NUM` smallint not null comment '游戏序号',
    `VERSION` varchar(20) not null default '' comment '当前版本号',
    `COIN` varchar(50) not null comment '游戏货币的名字',
    `COIN_RATIO` int not null default 10 comment '货币兑换游戏币的比例',
    `STATUS` smallint not null DEFAULT '1' comment '游戏状态:\n1:正常;0:测试;\n-1:下线;',
    `DT_CREATED_TIME` datetime not null comment '游戏创建时间',
    `WEB_SITE` varchar(100) comment '游戏官网',
    primary key (`ID`)
)engine=innodb
default charset=utf8
comment='游戏数据表';

insert into T_GAME (NAME, ALIAS, PACKAGE_NAME, CP_ID, SEQ_NUM, VERSION, COIN, COIN_RATIO, STATUS, DT_CREATED_TIME) values (
    '怪物联盟-安卓', 'mxm', 'cn.fangcun', 231, 1, '1.1.0', '水晶币', 100, 1, now()
);

drop table if exists `T_GAME_SERVER`;
create table `T_GAME_SERVER`(
    `ID` int not null AUTO_INCREMENT,
    `NAME` varchar(50) not null comment '游戏大区名字，请根据游戏填写实际名字，方便以后查询',
    `GAME_ID` int not null comment '游戏id',
    `NOTIFY_URL` varchar(100) comment '通知cp发放游戏币的接口地址',
    `SERVER_HOST` varchar(100) not null comment '游戏大区主机ip地址',
    `PORT` int not null comment '游戏大区端口号',
    `LIMIT_USER` int not null comment '游戏大区人数上线',
    `STATUS` smallint not null DEFAULT '1' comment '大区状态：\n1有效生产环境使用;\n2测试环境使用;\n0表示不使用',
    `SEQ_NUM` int not null comment '大区序号,对应cp游戏中的大区id号,测试大区号是9999',
    primary key (`ID`),
    unique key `IND_GAME_SERVER_GAME_ID_SEQ_NUM` (`GAME_ID`,`SEQ_NUM`)
)engine=innodb
default charset=utf8
comment='游戏服务器数据表';

insert into T_GAME_SERVER (NAME, GAME_ID, NOTIFY_URL, SERVER_HOST, PORT, LIMIT_USER, STATUS, SEQ_NUM) values(
    '测试大区', 1, 'http://www.baidu.com/', '192.168.0.201', 8888, 3000, 2, 9999
);

drop table if exists `T_GAME_SDK_CONFIG`;
create table `T_GAME_SDK_CONFIG`(
    `GAME_ID` int not null comment '游戏id',
    `CHANNEL_ID` int not null comment '渠道id',
    `CHANNEL_PRO_ID` varchar(50) not null comment '渠道SDK属性表的id',
    `PROPERY_VALUE` varchar(500) not null comment '渠道SDK属性对应的值',
    key GAME_CONFIG_IDX(`GAME_ID`,`CHANNEL_ID`)
)engine=innodb
default charset=utf8
comment='游戏接入渠道sdk的密钥参数表';

drop table if exists `T_GAME_ROLE`;
create table `T_GAME_ROLE` (
  `ID` int not null auto_increment,
  `GAME_ID` int DEFAULT 0 comment '游戏ID',
  `SERVER_ID` int DEFAULT 0 comment '游戏大区ID',
  `USER_ID` int DEFAULT '0' comment '云游用户ID',
  `ROLE_ID` varchar(50) DEFAULT '' comment '对应游戏中的角色ID',
  `ROLE_NAME` varchar(50) DEFAULT '' comment '角色名称',
  `ROLE_LEVEL` int DEFAULT 0 comment '角色等级',
  `DT_CREATED_TIME` datetime not null comment '角色创建时间,使用服务端创建时间',
  primary key (`ID`)
) engine=innodb
default charset=utf8
comment='游戏角色信息表';

drop table if exists `T_GAME_LOGIN_SERVER_LOG`;
create table `T_GAME_LOGIN_SERVER_LOG` (
  `ID` int not null auto_increment,
  `USER_ID` int not null comment '用户ID',
  `SERVER_ID` int not null comment '充值游戏大区ID',
  `GAME_ID` int not null comment '游戏ID',
  `DT_CREATED_TIME` datetime default NULL comment '创建时间',
  `ROLE_ID` varchar(50) DEFAULT '' comment '对应游戏中的角色ID',
  primary key (`ID`, USER_ID),
  key `GAME_SERVER_ID_CREATED_TIME_IDX`(GAME_ID, DT_CREATED_TIME)
) engine=myisam
default charset=utf8
comment='登录服务器日志表'
partition by key(USER_ID);

drop table if exists `T_GAME_CHANNEL`;
create table `T_GAME_CHANNEL`(
    `ID` int not null auto_increment,
    `NAME` varchar(50) not null default '' comment '渠道名称',
    `STATUS` smallint NOT NULL DEFAULT 1 comment '状态：\n1可用;\n0无效；',
    `PID` int not null default 0 comment '父渠道id',
    `CP_ID` int default 0 comment '渠道属于哪个cp，可以设置属于cp自己的子渠道',
    primary key(ID)
)engine=innodb
default charset=utf8
comment='渠道表';

drop table if exists `T_GAME_CHANNEL_PROPERY`;
create table `T_GAME_CHANNEL_PROPERY`(
    `ID` int not null auto_increment,
    `CHANNEL_ID` int not null comment '渠道id',
    `PROPERY_NAME` varchar(50) not null comment '渠道SDK需要配置的属性名',
    primary key (`ID`)
) engine=innodb
default charset=utf8
comment='渠道属性表';

########################acs###############################
create schema if not exists `acs` default character set utf8;
use acs;

drop table if exists `T_ACS_RESOURCE`;
create table `T_ACS_RESOURCE`(
    `ID` int not null auto_increment comment 'URL资源编号' ,
    `URL` varchar(500) not null comment 'URL资源，以ant目录方式保存' ,
    `DESC` varchar(100) null comment 'URL资源描述' ,
    `PID` int not null default 0 comment '如果是菜单，即目录形式则PID=0',
    primary key(ID),
    key ACS_RESOURCE_URL_IDX(URL)
)engine = innodb default charset=utf8
comment 'URL资源、菜单表';

drop table if exists `T_ACS_ROLE`;
create table `T_ACS_ROLE`(
    `ID` int not null auto_increment comment '角色编号' ,
    `NAME` varchar(50) not null comment '角色名称' ,
    `DESC` varchar(100) null comment '角色描述' ,
    primary key(ID),
    unique key ACS_ROLE_NAME_IDX(NAME)
)engine = innodb default charset=utf8
comment '角色表';

drop table if exists `T_ACS_USER_ROLE`;
create table `T_ACS_USER_ROLE`(
    `ID` int not null auto_increment,
    `USER_ID` int not null comment '用户ID',
    `ROLE_ID` int not null comment '角色ID',
    primary key(ID)
)engine = innodb default charset=utf8
comment '用户角色关系表';

drop table if exists `T_ACS_ROLE_RESOURCE`;
create  table if not exists `T_ACS_ROLE_RESOURCE` (
  `ID` int not null auto_increment ,
  `ROLE_ID` int not null comment '角色ID' ,
  `RESOURCE_ID` int not null comment '资源ID',
  primary key(ID),
  key ACS_ROLE_RES_IDX(RESOURCE_ID)
)engine = innodb
default charset=utf8
comment '角色资源关系表';

drop table if exists `T_ACS_GAMES_PERMISSION`;
create table `T_ACS_PERMISSION_GAMES`(
    `ID` int not null auto_increment ,
    `USER_ID` int not null comment '用户ID',
    `GAME_ID` not null comment '游戏ID',
    `CHANNEL_ID` not null comment '渠道id',
    primary key(ID),
)engine = innodb
default charset=utf8
comment '用户拥有的查看游戏、渠道权限';

drop table if exists `T_ACS_ROLE_PERMISSION`;
create table `T_ACS_ROLE_PERMISSION`(
    `ID` int not null auto_increment ,
    `ROLE_ID` int not null comment '角色ID',
    `PERMISSION_ID` int not null comment '权限id',
    primary key(ID)
)engine = innodb
default charset=utf8
comment '角色拥有的权限';

drop table if exists `T_ACS_PERMISSION`;
create table `T_ACS_PERMISSION`(
    `ID` int not null auto_increment,
    `NAME` varchar(100) not null default '' comment '权限名字',
    primary key(ID),
    unique key `ACS_PERMISSION_NAME_IDX`(NAME)
)engine = innodb
default charset=utf8
comment '权限表';

drop table if exists `T_ACS_USER_PERMISSION`;
create table `T_ACS_USER_PERMISSION`(
    `ID` int not null auto_increment,
    `USER_ID` int not null comment '用户ID',
    `PERMISSION_ID` int not null comment '权限id',
    primary key(ID)
)engine = innodb
default charset=utf8
comment '用户所拥有的权限表';

#########################################################################
