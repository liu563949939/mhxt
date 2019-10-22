layui.define([], function (exports) {
    //return "<div>{{# var fn = " + fun + " }}" + "{{fn(d." + dicField + ")}}</div>";
    var dictpl = {
        //1.根据字典名称返回字典模板
        render: function (dicName) {
            debugger
            switch (dicName) {
                case '租售状态':
                    return dictpl.fun_001;
                case '装修状态':
                    return dictpl.fun_002;
                case '状态':
                    return dictpl.fun_003;
                case '资料类别':
                    return dictpl.fun_004;
            }
        },

        //2.函数体
        //租售状态
        fun_001: function (value) {
            var str = '';
            switch (value) {
                case '1':
                    str = '租';
                    break;
                case '2':
                    str = '售';
                    break;
            }
            return str;
        },

        //装修状态
        fun_002: function (value) {
            var str = '';
            switch (value) {
                case '1':
                    str = '精装';
                    break;
                case '2':
                    str = '简装';
                    break;
                case '9':
                    str = '无';
                    break;
            }
            return str;
        },

        //状态
        fun_003: function (value) {
            var str = '';
            switch (value) {
                case '1':
                    str = '未租';
                    break;
                case '2':
                    str = '已租';
                    break;
                case '3':
                    str = '未售';
                    break;
                case '4':
                    str = '已售';
                    break;
            }
            return str;
        },

        //资料类别
        fun_004: function (value) {
            var str = '';
            switch (value) {
                case '01':
                    str = 'pmis';
                    break;
                case '02':
                    str = 'delphi';
                    break;
                case '03':
                    str = 'txl';
                    break;
                case '04':
                    str = 'database';
                    break;
                case '05':
                    str = 'java';
                    break;
                case '06':
                    str = 'web';
                    break;
                case '07':
                    str = 'oracle';
                    break;
                case '08':
                    str = 'javascript';
                    break;
                case '09':
                    str = 'html';
                    break;
                case '10':
                    str = 'ftp';
                    break;
                case '11':
                    str = 'managesystem';
                    break;
                case '12':
                    str = 'application';
                    break;
                case '13':
                    str = 'linux';
                    break;
                case '14':
                    str = 'etl';
                    break;
                case '15':
                    str = 'ssh';
                    break;
                case '99':
                    str = 'other';
                    break;
            }
            return str;
        }

    }

    //接口输出
    exports('dictpl', dictpl)
});