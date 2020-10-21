package com.divine.yang.basecomponent.getpermission;

import android.Manifest;

/**
 * Author: Divine
 * CreateDate: 2020/10/12
 * Describe:Android权限清单
 */
public class PermissionList {
    //    需要运行时申请的权限
    public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR;//
    public static final String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;//
    public static final String CAMERA = Manifest.permission.CAMERA;//（相机）
    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;//
    public static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;//
    public static final String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;//
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;//
    public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;//
    public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;//
    public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;//
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;//
    public static final String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;//
    public static final String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;//
    public static final String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;//
    public static final String USE_SIP = Manifest.permission.USE_SIP;//
    public static final String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;//
    public static final String BODY_SENSORS = Manifest.permission.BODY_SENSORS;//
    public static final String SEND_SMS = Manifest.permission.SEND_SMS;//
    public static final String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;//
    public static final String READ_SMS = Manifest.permission.READ_SMS;//
    public static final String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;//
    public static final String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;//
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;//
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;//

    public static final String GROUP_STORAGE = Manifest.permission_group.STORAGE;//（存储卡）
    public static final String GROUP_SMS = Manifest.permission_group.SMS;//（短信）
    public static final String GROUP_SENSORS = Manifest.permission_group.SENSORS;//（传感器）
    public static final String GROUP_PHONE = Manifest.permission_group.PHONE;//（手机）
    public static final String GROUP_MICROPHONE = Manifest.permission_group.MICROPHONE;//（麦克风）
    public static final String GROUP_CALENDAR = Manifest.permission_group.CALENDAR;//（日历）
    public static final String GROUP_CONTACTS = Manifest.permission_group.CONTACTS;//（联系人）
    public static final String GROUP_LOCATION = Manifest.permission_group.LOCATION;//（位置）

    //    普通权限的总结：
    private static final String ACCESS_LOCATION_EXTRA_COMMANDS = Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS; //定位权限
    private static final String ACCESS_NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE; //网络状态权限
    private static final String ACCESS_NOTIFICATION_POLICY = Manifest.permission.ACCESS_NOTIFICATION_POLICY; //通知 APP通知显示在状态栏
    private static final String ACCESS_WIFI_STATE = Manifest.permission.ACCESS_WIFI_STATE; //WiFi状态权限
    private static final String BLUETOOTH = Manifest.permission.BLUETOOTH; //使用蓝牙权限
    private static final String BLUETOOTH_ADMIN = Manifest.permission.BLUETOOTH_ADMIN; // 控制蓝牙开关
    private static final String BROADCAST_STICKY = Manifest.permission.BROADCAST_STICKY; //粘性广播
    private static final String CHANGE_NETWORK_STATE = Manifest.permission.CHANGE_NETWORK_STATE; // 改变网络状态
    private static final String CHANGE_WIFI_MULTICAST_STATE = Manifest.permission.CHANGE_WIFI_MULTICAST_STATE; // 改变WiFi多播状态，应该是控制手机热点（猜测）
    private static final String CHANGE_WIFI_STATE = Manifest.permission.CHANGE_WIFI_STATE; //控制WiFi开关，改变WiFi状态
    private static final String DISABLE_KEYGUARD = Manifest.permission.DISABLE_KEYGUARD; //改变键盘为不可用
    private static final String EXPAND_STATUS_BAR = Manifest.permission.EXPAND_STATUS_BAR; //扩展bar的状态
    private static final String GET_PACKAGE_SIZE = Manifest.permission.GET_PACKAGE_SIZE; //获取应用安装包大小
    private static final String INTERNET = Manifest.permission.INTERNET; //网络权限
    private static final String KILL_BACKGROUND_PROCESSES = Manifest.permission.KILL_BACKGROUND_PROCESSES; //杀死后台进程
    private static final String MODIFY_AUDIO_SETTINGS = Manifest.permission.MODIFY_AUDIO_SETTINGS; // 改变音频输出设置
    private static final String NFC = Manifest.permission.NFC; //支付
    private static final String READ_SYNC_SETTINGS = Manifest.permission.READ_SYNC_SETTINGS; // 获取手机设置信息
    private static final String READ_SYNC_STATS = Manifest.permission.READ_SYNC_STATS; //数据统计
    private static final String RECEIVE_BOOT_COMPLETED = Manifest.permission.RECEIVE_BOOT_COMPLETED; //监听启动广播
    private static final String REORDER_TASKS = Manifest.permission.REORDER_TASKS; //创建新栈
    private static final String REQUEST_INSTALL_PACKAGES = Manifest.permission.REQUEST_INSTALL_PACKAGES; //安装应用程序
    private static final String SET_TIME_ZONE = Manifest.permission.SET_TIME_ZONE; //允许应用程序设置系统时间区域
    private static final String SET_WALLPAPER = Manifest.permission.SET_WALLPAPER; //设置壁纸
    private static final String SET_WALLPAPER_HINTS = Manifest.permission.SET_WALLPAPER_HINTS; //设置壁纸上的提示信息，个性化语言
    private static final String TRANSMIT_IR = Manifest.permission.TRANSMIT_IR; //红外发射
    private static final String USE_FINGERPRINT = Manifest.permission.USE_FINGERPRINT; //指纹识别
    private static final String VIBRATE = Manifest.permission.VIBRATE; //震动
    private static final String WAKE_LOCK = Manifest.permission.WAKE_LOCK; //锁屏
    private static final String WRITE_SYNC_SETTINGS = Manifest.permission.WRITE_SYNC_SETTINGS; // 改变设置
    private static final String SET_ALARM = Manifest.permission.SET_ALARM; //设置警告提示
    private static final String INSTALL_SHORTCUT = Manifest.permission.INSTALL_SHORTCUT; //创建快捷方式
    private static final String UNINSTALL_SHORTCUT = Manifest.permission.UNINSTALL_SHORTCUT; //删除快捷方式
}
