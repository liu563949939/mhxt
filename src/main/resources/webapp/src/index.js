/**
 *  入口文件索引
 *  使用说明：将此文件引入到页面中，可在script标签上定义一个data-main=""属性，
 *  此属性指定页面入口文件。
 *
**/
(function () {

    var entry,
        // 配置所有应用的入口文件，程序将会按照data-main属性中设置的值进行索引查找
        // 如果你在引入此脚本的script标签上没有设置data-main属性，程序将会默认访问home.js文件
        app = {
            home: '{/}home',
            login: '{/}login'
        };

    (function () {

        var dataMain, scripts = document.getElementsByTagName('script'),
            eachScripts = function (el) {
                dataMain = el.getAttribute('data-main');
                if (dataMain) {
                    entry = dataMain;
                }
            };

        [].slice.call(scripts).forEach(eachScripts);

    })();

    layui.config({
        base: 'assets/lay/modules/'
    }).extend(app).use(entry || 'home');

    //***自定义***
    layui.extend({
        core: "{/}/assets/core/index", //核心模块
        exd: "{/}/assets/extends/extend" //扩展模块
    }).use(["layer", "table", "jquery", "form", 'core', 'exd'], function () {
        var $ = layui.jquery,
            element = layui.element;
        // layui.data('test', {
        //     key: 'nickname',
        //     value: 'ABC'
        // });

        // layui.sessionData('test', {
        //     key: 'nickname',
        //     value: '555'
        // });

        //1.判断用户是否已登陆，如未登录跳转到登录页面
        var localTest = layui.sessionData('login');
        var username = localTest.username;

        //1.1判断是从哪个页面来的
        if (entry != 'login') {
            if (username == null || username == undefined || username == '') {
                window.location.href = "./login.html";
            }
        }

        //2.菜单加载
        element.on('nav(test)', function (elem) {
            debugger
            //数据模拟
            var data = [];
            data.push({name:'菜单一'});
            data.push({name:'菜单二'});
            //读取菜单
            var x = '';
            for(i=0;i<data.length;i++){
                var x = x + '<li class="layui-nav-item">' +
                '<a href = "javascript:;" >' +
                '<i class="layui-icon">&#xe857;</i>' +
                '<em>' + data[i].name + '</em>' +
                '</a >' +
                // '<dl class="layui-nav-child">' +
                //     '<dd><a href="views/mhxt/user/list1.html">用户管理2</a></dd>' +
                //     '<dd><a href="views/mhxt/role/list1.html">角色管理2</a></dd>' +
                //     '<dd><a href="views/mhxt/module/list1.html">资源管理2</a></dd>' +
                //     '<dd><a href="views/users.html1">字典管理2</a></dd>' +
                // '</dl>' +
                '</li >';
            }

            $('#Nav').empty();
            $('#Nav').append(x);
            element.init();
        });
    })



})();