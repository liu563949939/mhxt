/** QuUI-v0.0.1 MIT License By https://www.quui.cn */;
layui.extend({
    setter:'{/}/assets/core/config',
    admin:'{/}/assets/core/admin',
    view:'{/}/assets/core/view'
}).define(["setter", "admin"], function (e) {
    var l = layui.setter,
        o = layui.element,
        r = layui.admin,
        u = r.tabsPage,
        s = layui.view,
        d = function () {
            var i = layui.router(),
                e = i.path,
                t = r.correctRouter(i.path.join("/"));

            debugger
            //默认加载页面
            if (e[0] == '') {
                e.splice(0, 1, 'userauthen');
                e.push('userUnit');
                e.push('userinfo');
                e.push('list')
            }
            e.length || (e = [""]), "" === e[e.length - 1] && (e[e.length - 1] = l.entry);
            var a = function (e) {
                d.haveInit && h(".layui-layer").each(function () {
                    var e = h(this),
                        a = e.attr("times");
                    e.hasClass("layui-layim") || layer.close(a)
                }), d.haveInit = !0, h(c).scrollTop(0), delete u.type
            };
            if ("tab" === u.type && ("/" !== t || "/" === t && r.tabsBody().html())) return r.tabsBodyChange(u.index), a(u.type);
            s().render(e.join("/")).then(function (e) {
                var a, n = h("#LAY_app_tabsheader>li");
                n.each(function (e) {
                    h(this).attr("lay-id") === t && (a = !0, u.index = e)
                }), l.pageTabs && "/" !== t && (a || (h(c).append('<div class="layadmin-tabsbody-item layui-show"></div>'), u.index = n.length, o.tabAdd(y, {
                    title: "<span>" + (e.title || "新标签页") + "</span>",
                    id: t,
                    attr: i.href
                }))), this.container = r.tabsBody(u.index), l.pageTabs || this.container.scrollTop(0), o.tabChange(y, t), r.tabsBodyChange(u.index)
            }).done(function () {
                layui.use("common", layui.cache.callback.common), n.on("resize", layui.data.resize), o.render("breadcrumb", "breadcrumb"), r.tabsBody(u.index).on("scroll", function () {
                    var e = h(this),
                        a = h(".layui-laydate"),
                        n = h(".layui-layer")[0];
                    a[0] && (a.each(function () {
                        var e = h(this);
                        e.hasClass("layui-laydate-static") || e.remove()
                    }), e.find("input").blur()), n && layer.closeAll("tips")
                })
            }), a()
        },
        a = function (e) {
            var n, a = layui.router(),
                i = s(l.container),
                t = r.correctRouter(a.path.join("/"));
            if (layui.each(l.indPage, function (e, a) {
                if (t === a) return n = !0
            }), layui.config({
                base: "controller/"
            }), n || "/userauthen/sys/login/login" === t) i.render(a.path.join("/")).done(function () {
                r.pageType = "alone"
            });
            else {
                // debugger
                // if (l.interceptor)
                //     if (!layui.sessionData(l.tableName)[l.request.tokenName]) return location.hash = "/userauthen/sys/login/login/redirect=" + encodeURIComponent(t);
                // "console" === r.pageType ? d() : i.render("admin/layout").done(function () {
                //     d(), layui.element.render(), r.screen() < 2 && r.sideFlexible(), r.pageType = "console"
                // })
            }
        },
        c = "#LAY_app_body",
        y = "layadmin-layout-tabs",
        h = layui.$,
        n = h(window);


    layui.link("/assets/core/themes/default/css/admin.css?v=" + r.v + "-1", function () {
        a()
    }, "layuiAdmin"), window.onhashchange = function () {
        a(), layui.event.call(this, l.MOD_NAME, "hash({*})", layui.router())
    }, layui.each(l.extend, function (e, a) {
        var n = {};
        n[a] = "{/}lib/" + a, layui.extend(n)
    }), e("core", {
        render: d
    })
});