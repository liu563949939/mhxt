//layui模块注册
layui.define(['layer', 'jquery', 'table', 'setter'], function (exports) {
    var $ = layui.jquery,
        //config = layui.userauthen,
        //dtree = layui.dtree,
        //upload = layui.upload,//上传下载
        admin = layui.admin,
        view = layui.view,
        form = layui.form,
        table = layui.table,
        config = layui.setter;

    var obj = {
        //***(1)ajax通用方法***
        //1.通用查询--同步
        commonQuery: function (param) {
            $.ajax({
                //1.请求头(格式)
                headers: { 'Content-Type': 'application/json;charset=utf8' },
                dataType: 'json', //预期服务器返回数据的类型
                //2.请求体
                url: config.datamanage.url + param.url,
                method: 'post', //请求方法
                async: true, //是否异步
                data: JSON.stringify(param.condition), //讲js对象序列化
                //3.回调函数
                success: function (obj) {
                    if (param.type == 'dic') {
                        // debugger
                        param.data = obj.data;
                        param.callback(param);
                    }
                }
            })
        },

        //2.通用新增
        commonAdd: function (param) {
            $.ajax({
                //1.请求地址和格式
                url: config.datamanage.url + param.url,
                method: param.method,
                async: false, //是否异步
                dataType: 'json',
                //2.请求头和请求体
                headers: { 'Content-Type': 'application/json;charset=utf8' },
                data: JSON.stringify(param.entity),
                //3.回调函数
                success: function (obj) {
                    debugger;
                    if (typeof (param.callback) == 'function') {
                        param.callback();
                    }
                }
            })
        },

        //3.通用修改
        commonModify: function (param) {
            $.ajax({
                url: config.datamanage.url + param.url,
                method: param.method,
                headers: {
                    'Content-Type': 'application/json;charset=utf8'
                },
                data: JSON.stringify(param.entity),
                dataType: 'json',
                success: function (obj) {
                    if (typeof (param.callback) == 'function') {
                        param.callback();
                    }
                }
            })
        },

        //4.删除
        commonDel: function (param) {
            $.ajax({
                //1.请求头
                headers: { 'Content-Type': 'application/json;charset=utf8' },
                dataType: 'json',
                //2.请求体
                url: config.datamanage.url + param.url,
                method: param.method,
                data: JSON.stringify(param.entity),
                success: function (obj) {
                    if (typeof (param.callback) == 'function') {
                        param.callback();
                    }
                }
            })
        },



        //***(2)字典处理***
        //1.字典字段初始化(多个--接收的是对象)
        pickDic: function (data) {
            //key字段获取
            var keys = [];
            for (var k in data) {
                if (data.hasOwnProperty(k)) {
                    keys.push(k)
                }
            }

            for (var i = 0; i < keys.length; i++) {
                var param = {};
                param.url = 'dic/query'; //字典请求地址
                param.condition = { name: data[keys[i]] }; //获得字典项目
                param.callback = obj.pickDic_callbak;
                param.type = 'dic'; //字典查询
                param.ele = keys[i]; //DOM对象的ID
                obj.commonQuery(param);
            }
        },

        //1.1.回调函数
        pickDic_callbak: function (data) {
            //select选项创建并赋值
            for (i = -1; i < data.data.length; i++) {
                var option = document.createElement("option");
                // 增加一个默认占位
                if (i == -1) {
                    $(option).val('');
                    $(option).text('请选择');
                } else {
                    $(option).val(data.data[i].code); //字典项
                    $(option).text(data.data[i].item); //字典值
                }
                $('#' + data.ele).append(option);
            }
            form.render('select'); //表单刷新(非常重要！)
        },

        //2.字典字段赋值(多个--接收的是对象)
        set_dicField: function (data) {
            debugger
            //key字段获取
            var keys = [];
            for (var k in data) {
                if (data.hasOwnProperty(k)) {
                    keys.push(k)
                }
            }
            //循环对DOM进行赋初始值
            for (var i = 0; i < keys.length; i++) {
                //$("#" + keys[i]).find("option[value=" + data[keys[i]] + "]").attr("selected",true);
                $("#" + keys[i]).val(data[keys[i]]);
            }
            form.render('select'); //表单刷新(非常重要！) 
        },


        //***(3)util函数***
        //1.日期增加(天数)
        dateCompute: function (sDate, sDayNum) {
            debugger
            var x = sDate.getTime();
            x = x + sDayNum * (24 * 3600 * 1000);
            var y = new Date(x).Format('yyyyMMdd');
            return y;
        },

        //2.得到当前主路径
        getRootUrl: function () {
            debugger
            //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
            var curWwwPath = window.document.location.href;
            //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
            var pathName = window.document.location.pathname;
            var pos = curWwwPath.indexOf(pathName);
            //获取主机地址，如： http://localhost:8083
            var localhostPaht = curWwwPath.substring(0, pos);
            //获取带"/"的项目名，如：/uimcardprj
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            return (localhostPaht + projectName);
        },



        //***(4)admin弹出层***
        pop: function (param) {
            debugger
            var id = param.id, //内容div的id
                url = param.url, //url
                i = param.data; //传入弹出层的参数(让子窗体绑定数据集)
            admin.popup({
                title: param.title, //标题
                area: [param.width, param.height], //宽度和高度
                id: id,
                success: function (layer, index) {
                    $("#" + id).css('padding', '1px');
                    layui.view(id).render(param.view, i).done(function () {
                        form.render();
                        form.on('submit(' + param.button + ')', function (data) {
                            debugger
                            //ajax请求提交数据
                            sparam = {};
                            sparam.url = url;
                            sparam.method = 'post';
                            sparam.entity = data.field;
                            sparam.callback = function () {
                                if(typeof(param.callback) == 'function'){
                                    param.callback();
                                }
                                layui.layer.close(index); //关闭弹层
                            }
                            obj.commonAdd(sparam);
                        });
                    });
                }
            });
        },




        //-----------------------------------------------------------------------------以下方法无效--------------------------------------------------------------
        //***上传下载***
        //1.文件上传
        fileUpload: function (param) {
            upload.render({
                elem: param.elem, //绑定元素
                url: config.datamanage.url + 'upload/save', //上传接口
                size: 0,
                choose: function (obj) {
                    var files = obj.pushFile();
                    obj.preview(function (index, file, result) {

                    })
                },
                //上传成功回调
                done: function (res) {
                    $(param.input).val(res.data.src);
                    //执行带有回调的函数
                    if (typeof (param.callback) == 'function') {
                        param.callback()
                    }
                },
                //请求异常回调
                error: function () {

                }
            })
        },





        //***dtree***
        //1.dtree获取数据
        getTreeData: function (param) {
            var data = [];
            $.ajax({
                url: config.datamanage.url + param.url,
                method: 'post',
                async: false,
                headers: { 'Content-Type': 'application/json;charset=utf8' },
                data: JSON.stringify(param.condition),
                dataType: 'json',
                success: function (obj) {
                    data = obj.data;
                }
            })
            return data;
        },

        //2.dtree同步加载
        dtreeRender: function (param) {
            dtree.render({
                elem: '#' + param.elem,
                data: param.data,
                dataStyle: 'layuiStyle',
                dataFormat: "list",
                initLevel: 2,
                response: param.display,
                ficon: 0,
                icon: 3,
                dot: false,
                checkbar: true
                //checkbarType: "only"
            });
        },

        //3.dtree异步加载
        dtreeRenderAsync: function (param) {
            var elem = '#' + param.elem,
                url = param.url,
                condition = param.condition,
                res = param.response, //转换
                callback = param.callback; //回调函数
            callbackParam = param.callbackParam; //回调函数参数

            dtree.render({
                elem: '#' + param.elem, //显示容器
                url: config.datamanage.url + url, //url
                dataStyle: "layuiStyle", //layui数据格式
                dataFormat: "list",  //配置data的风格为list
                request: { type: "0" }, //条件
                response: { message: "msg", statusCode: 0, title: res.title, treeId: res.id, parentId: res.pid },  //修改response中返回数据的定义
                initLevel: 2,  // 指定初始展开节点级别
                checkbar: true, //开启复选框
                dot: false, //默认小圆点
                icon: '3',
                done: function (data, obj) {
                    if (typeof (callback) == 'function') {
                        callback(callbackParam);
                    }
                }
            })
        },



        //***信息提取***
        //1.页面提取-表格
        pickInfo: function (param) {
            var title = param.style.title, //标题
                width = param.style.width, //宽度
                height = param.style.height, //高度
                id = param.id, //id
                path = param.path, //路径
                data = param.data, //传递的数据
                callback = param.callback; //回调函数


            admin.popup({
                title: title,
                area: [width, height],
                id: id,
                success: function (layer, index) {
                    $("#" + this.id).css('padding', '1px');
                    form.render(); //初始化所有form表单
                    view(this.id).render(path, data).done(function () {
                        //1.监听表格选定(单选)
                        table.on('tool(table-pick)', function (obj) {
                            if (obj.event == 'select') {
                                callback(obj.data, index);
                            }
                        })
                        //2.监听表单选定(多选)
                        form.on('submit(mulSelect)', function () {
                            var checkStatus = table.checkStatus('table-pick');
                            callback(checkStatus.data, index);
                        })
                    });
                },
                end: function () {
                    if (id == 'warnView') {
                        callback();
                    }
                }
            });
        },

        //2.页面提取-tree
        pickTree: function (param) {
            var title = param.title, //标题
                url = param.url; //url
            condition = param.condition; //条件
            callback = param.callback; //回调函数

            //1.查询数据
            var params = {};
            params.url = url;
            params.condition = condition;
            var data = obj.getTreeData(params); //数据返回

            //2.dtree展示(同步)
            layer.open({
                type: 1,
                title: title,
                area: ["400px", "80%"],
                content: '<ul id="dtree" class="dtree" data-id="0"></ul>',
                btn: ['确认选择'],
                success: function (layero, index) {
                    var DTree = dtree.render({
                        elem: "#dtree",
                        data: data,
                        dataStyle: 'layuiStyle',
                        dataFormat: "list",
                        initLevel: 3,
                        dot: false,
                        icon: 4,
                        response: { title: 'name', treeId: 'nodeid', parentId: 'parentid' }
                    });
                },
                yes: function (index, layero) {
                    var param = dtree.getNowParam("dtree"); // 获取当前选中节点
                    callback(param, index);
                }
            })
        },





        //***echarts图表***
        //1.图表渲染
        chartRender: function (param) {
            var eleID = param.id, //渲染区域ID
                title = param.title, //统计名称
                xData = param.xData, //X轴数据(统计项)--数组
                yData = param.yData; //Y轴数据(统计值)--对象(可以多个)

            var seriesArr = []; //定义统计项值

            //1.数据格式处理
            //1.1得到统计key(数组格式)
            var keyArr = obj.objToArray(yData, 'key');
            var valueArr = obj.objToArray(yData, 'value');

            //1.2.根据key获得模型数组
            for (var i = 0; i < keyArr.length; i++) {
                var temp = new Object();
                //树状图
                temp.type = 'bar';
                //最大最小值
                temp.markPoint = {
                    data: [
                        { type: 'max', name: '最大值' },
                        { type: 'min', name: '最小值' }
                    ]
                }
                //平均值
                temp.markLine = {
                    data: [
                        { type: 'average', name: '平均值' }
                    ]
                }
                temp.name = keyArr[i];
                temp.data = valueArr[i];
                seriesArr.push(temp);
            }

            //2.实例声明
            var e = myChart.init(document.getElementById(eleID));

            //3.数据准备
            var option = {
                title: {
                    text: title,  //标题
                    //subtext: '纯属虚构', //子标题
                    //x:'left'  //靠左对其
                },
                legend: {
                    data: keyArr
                },
                toolbox: {
                    show: true,
                    feature: {
                        dataView: { show: true, readOnly: false },
                        magicType: { show: true, type: ['line', 'bar'] },
                        restore: { show: true },
                        saveAsImage: { show: true }
                    }
                },
                xAxis: {
                    //data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]  //x坐标
                    data: xData
                },
                yAxis: {
                    type: 'value'
                },
                series: seriesArr
            }

            //4.方法渲染
            e.setOption(option); //渲染

        },




        //***消息管理***/
        //1.消息发送
        sendMsg: function (param) {
            obj.commonAdd(param);
        },



        //***util***/
        //1.年龄校验
        regAge: function (value) {
            var reg = /^([0-9]|[1-9]{2}|100)$/;
            if (!reg.test(value)) {
                return true;
            } else {
                return false;
            }

        },

        //2.获得对象键数组或值数组
        objToArray: function (obj, lb) {
            var keyArr = [];
            var valueArr = [];
            var result;
            //key字段获取
            for (var k in obj) {
                if (obj.hasOwnProperty(k)) {
                    keyArr.push(k)
                }
            }
            if (lb == 'key') {
                result = keyArr;
            } else {
                //value值获取
                for (var i = 0; i < keyArr.length; i++) {
                    valueArr.push(obj[keyArr[i]])
                }
                result = valueArr;
            }

            return result;
        },

        //3.字符校验
        regNumStr: function (value) {
            var reg = /^[\da-zA-Z]+$/;
            if (!reg.test(value)) {
                return true;
            } else {
                return false;
            }
        },

        // 4.正整数校验
        regInt: function (value) {
            var reg = /^[1-9]\d*$/;
            if (!reg.test(value)) {
                return true;
            } else {
                return false;
            }
        },

        //5.中文校验
        regWord: function (value) {
            var reg = /^[\u4e00-\u9fa5]+$/;
            if (!reg.test(value)) {
                return true;
            } else {
                return false;
            }
        },

        //6.住址校验(中文开头，包含中文字母数字-，不能以-开头结尾)
        regAddress: function (value) {
            var reg = /^[\u4e00-\u9fa5][a-zA-Z0-9-\u4e00-\u9fa5]+$/;
            if (!reg.test(value)) {
                return true;
            } else {
                return false;
            }
        },

        //7.有两位小数的正实数
        regTemperature: function (value) {
            var reg = /^[0-9]+(.[0-9]{2})?$/;
            if (!reg.test(value)) {
                return true;
            } else {
                return false;
            }
        },

        //7.邮箱校验
        regEmail: function (value) {
            var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
            if (!reg.test(value)) {
                return true;
            } else {
                return false;
            }
        },

        //8.日期转字符串
        dateToString: function (date) {
            var year = date.getFullYear();
            var month = (date.getMonth() + 1).toString();
            var day = (date.getDate()).toString();
            if (month.length == 1) {
                month = "0" + month;
            }
            if (day.length == 1) {
                day = "0" + day;
            }
            var dateTime = year + "-" + month + "-" + day;
            return dateTime;
        },

    };



    //模块输出
    exports('global', obj);
});