layui.extend({
    global:'{/}/assets/extends/global',  //公用方法
    jutil:'{/}/assets/extends/jsutil',  //工具(json处理等)
    dictpl:'{/}/assets/extends/dictpl',  //字典显示处理
    dtree:'{/}/assets/extends/dtree/dtree',  //菜单树
    IconFonts:'{/}/assets/extends/icon/iconFonts'  //图标选择
}).define(['global','jutil','dictpl','dtree','IconFonts'],function (e) {
    e('exd',function(){})
})