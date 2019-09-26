layui.extend({
    global:'{/}/assets/extends/global',  //公用方法
    jutil:'{/}/assets/extends/jsutil',  //工具(json处理等)
    dictpl:'{/}/assets/extends/dictpl',  //字典显示处理
    dtree:'{/}/assets/extends/dtree/dtree'  //字典显示处理
}).define(['global','jutil','dictpl','dtree'],function (e) {
    e('exd',function(){})
})