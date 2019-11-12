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



    //***自定义***
    layui.extend({
        core: "{/}/assets/core/index", //核心模块
        exd: "{/}/assets/extends/extend" //扩展模块
    }).use(["layer", "table", "jquery", "form", 'core', 'exd'], function () {
        var $ = layui.jquery,
            global = layui.global;

        //1.获取session
        var login = layui.sessionData('login'),
            userInfo = login.userInfo;
        if (userInfo != undefined && login != null) {
            var userId = userInfo[0].userId,
                name = userInfo[1].name,
                unitCode = userInfo[2].unitCode,
                unitName = userInfo[3].unitName;
        }



        //2.判断是否为登陆页面，如是，则清除session
        if (entry != 'login') {
            if (userId == null || userId == undefined || userId == '') {
                window.location.href = "./login.html";
            } else {
                //通过ajax请求获得后台数据
                var param = {};
                param.url = 'user/findModuleByUserId';
                param.condition = { jlbh: userId };
                param.type = 'dic';
                param.callback = function (sParam) {
                    debugger
                    //1.菜单信息展示
                    $('#Nav').empty();
                    var x = active.readMenu(sParam.data);
                    $('#Nav').append(x);
                    //2.用户信息展示
                    $('#user').text('当前用户【' + name + '】' + ', 所属单位【' + unitName + '】');
                    //3.菜单刷新控制
                    layui.config({
                        base: 'assets/lay/modules/'
                    }).extend(app).use(entry || 'home');
                }
                global.commonQuery(param);
            }
        } else {
            //清空session值
            layui.sessionData('login', null);
        }

        //3.方法定义
        var active = {
            //1.读取菜单
            readMenu: function (data) {
                var x = '';
                for (i = 0; i < data.length; i++) {
                    x += '<li class="layui-nav-item">' +
                        '<a href = "javascript:;" >' +
                        '<i class="layui-icon ' + data[i].icon + '"></i>' +
                        '<em>' + data[i].name + '</em>' +
                        '<span class="layui-nav-more"></span>' +
                        '</a >';

                    var y = '<dl class="layui-nav-child">';
                    for (j = 0; j < data[i].children.length; j++) {
                        y += '<dd><a href="' + data[i].children[j].url + '">' + data[i].children[j].name + '</a></dd>'
                    }

                    y += '</dl>';
                    x += y + '</li >';
                }
                return x;
            }
        }
    })


})();