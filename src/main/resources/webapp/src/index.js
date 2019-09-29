/**
 *  入口文件索引
 *  使用说明：将此文件引入到页面中，可在script标签上定义一个data-main=""属性，
 *  此属性指定页面入口文件。
 *
**/
(function () {
    debugger
    var entry,
        // 配置所有应用的入口文件，程序将会按照data-main属性中设置的值进行索引查找
        // 如果你在引入此脚本的script标签上没有设置data-main属性，程序将会默认访问home.js文件
        app = {
            home: '{/}home',
            login: '{/}login'
        };

    (function () {
        debugger
        var dataMain, scripts = document.getElementsByTagName('script'),
            eachScripts = function (el) {
                dataMain = el.getAttribute('data-main');
                if (dataMain) {
                    entry = dataMain;
                }
            };

        [].slice.call(scripts).forEach(eachScripts);

    })();



    //***自定义***
    layui.extend({
        core: "{/}/assets/core/index", //核心模块
        exd: "{/}/assets/extends/extend" //扩展模块
    }).use(["layer", "table", "jquery", "form", 'core', 'exd'], function () {
        debugger
        var $ = layui.jquery,
            element = layui.element,
            global = layui.global;
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

        if (username != "") {
            //1.通过ajax请求获得后台数据
            var param = {};
            param.url = 'user/findModuleByUserId';
            param.condition = { jlbh: '111' };
            param.type = 'dic';
            param.callback = function (sParam) {
                debugger
                var x = active.readMenu(sParam.data);
                // $('#Nav').empty();

                // var aa = '<li class="layui-nav-item">' +
                //     '<a href="javascript:;">' +
                //     '<i class="layui-icon">&#xe609;</i>' +
                //     '<em>主页</em>' +
                //     '</a>' +
                //     '<dl class="layui-nav-child">' +
                //     '<dd><a href="views/system/console.html">控制台</a></dd>' +
                //     '</dl>' +
                //     '</li>';

                // $('#Nav').append(aa);
                // element.init('nav','moduleNav');
                var x = active.readMenu(sParam.data);
                $('#Nav').append(x);

                layui.config({
                    base: 'assets/lay/modules/'
                }).extend(app).use(entry || 'home',function(){
                    debugger;

                });
            }
            global.commonQuery(param);
        }


        var active = {
            //1.读取菜单
            readMenu: function (data) {
                //读取菜单
                var x = '';
                for (i = 0; i < data.length; i++) {
                    x = x + '<li class="layui-nav-item">' +
                        '<a href = "javascript:;" >' +
                        '<i class="layui-icon">&#xe857;</i>' +
                        '<em>' + data[i].name + '</em>' +
                        '<span class="layui-nav-more"></span>'+
                        '</a >';

                    var y = '<dl class="layui-nav-child">';
                    for (j = 0; j < data[i].children.length; j++) {
                        y = y + '<dd><a href="' + data[i].children[j].url + '">' + data[i].children[j].name + '</a></dd>'
                    }

                    y += '</dl>';
                    x +=  y + '</li >';
                }
                return x;
            }
        }

    })


    // layui.config({
    //     base: 'assets/lay/modules/'
    // }).extend(app).use(entry || 'home',function(){
    //     debugger;

    // });




})();