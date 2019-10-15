layui.define([], function (exports) {
    //return "<div>{{# var fn = " + fun + " }}" + "{{fn(d." + dicField + ")}}</div>";
    var dictpl = {
        //1.根据字典名称返回字典模板
        render: function (dicName) {
            switch (dicName) {
                case '租售状态':
                    return dictpl.fun_001;
                case '装修状态':
                    return dictpl.fun_002;
                case '状态':
                    return dictpl.fun_003;
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
        }

    }

    //接口输出
    exports('dictpl', dictpl)
});