layui.define([], function (exports) {
    //return "<div>{{# var fn = " + fun + " }}" + "{{fn(d." + dicField + ")}}</div>";
    var dictpl = {
        //1.根据字典名称返回字典模板
        render: function (dicName) {
            switch (dicName) {
                case '资料类型':
                    return dictpl.fun_001;
                case '协破线索状态':
                    return dictpl.fun_002;
                case '协破查证期限':
                    return dictpl.fun_003;
            }
        },

        //2.函数体
        //资料类型
        fun_001: function (value) {
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
                    str = 'java';
                    break;
                case '05':
                    str = 'database';
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
                    str = 'ssh';
                    break;
                case '15':
                    str = 'etl';
                    break;
                case '99':
                    str = 'other';
                    break;
                default:
                    str = '未知'
            }
            return str;
        },

        //协破线索状态
        fun_002: function (value) {
            var str = '';
            switch (value) {
                case '10':
                    str = '待呈请';
                    break;
                case '20':
                    str = '待侦审民警审核';
                    break;
                case '21':
                    str = '侦审民警审核通过';
                    break;
                case '22':
                    str = '待监管所领导审核';
                    break;
                case '23':
                    str = '监管所领导审核通过';
                    break;
                case '24':
                    str = '待法制大队审核';
                    break;
                case '25':
                    str = '法制大队审核通过';
                    break;
                case '26':
                    str = '待分局合成办审核';
                    break;
                case '27':
                    str = '分局合成办审核通过';
                    break;
                case '30':
                    str = '待办案所领导审核';
                    break;
                case '31':
                    str = '办案所领导审核通过';
                    break;
                case '32':
                    str = '待监管所领导审核';
                    break;
                case '33':
                    str = '监管所领导审核通过';
                    break;
                case '80':
                    str = '待签收';
                    break;
                case '81':
                    str = '已签收';
                    break;
                case '82':
                    str = '超时未签收';
                    break;
                case '83':
                    str = '已反馈';
                    break;
                case '90':
                    str = '已办结(正常办结)';
                    break;
                case '91':
                    str = '已办结(侦审民警审核不通过)';
                    break;
                case '92':
                    str = '已办结(监管所领导审核不通过)';
                    break;
                case '93':
                    str = '已办结(法制大队审核不通过)';
                    break;
                case '94':
                    str = '已办结(办案所领导审核不通过)';
                    break;
                case '95':
                    str = '已办结(分局合成办审核不通过)';
                    break;
            }
            return str;
        },

        //协破查证期限
        fun_003: function (value) {
            var str = '';
            switch (value) {
                case '01':
                    str = '一天';
                    break;
                case '02':
                    str = '两天';
                    break;
                case '03':
                    str = '三天';
                    break;
                case '04':
                    str = '四天';
                    break;
                case '05':
                    str = '五天';
                    break;
                case '06':
                    str = '六天';
                    break;
                case '07':
                    str = '七天';
                    break;
            }
            return str;
        }

    }

    //接口输出
    exports('dictpl', dictpl)
});