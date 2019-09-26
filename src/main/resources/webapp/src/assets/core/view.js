/** QuUI-v0.0.1 MIT License By https://www.quui.cn */ ;
layui.define(["laytpl", "layer"], function (e) {
    var l = layui.jquery,
        c = layui.laytpl,
        r = layui.layer,
        a = layui.setter,
        y = (layui.device(), layui.hint()),
        m = function (e) {
            return new n(e)
        },
        t = "LAY_app_body",
        n = function (e) {
            this.id = e, this.container = l("#" + (e || t))
        };
    m.loading = function (e) {
        e.append(this.elemLoad = l('<i class="layui-anim layui-anim-rotate layui-anim-loop layui-icon layui-icon-loading layadmin-loading"></i>'))
    }, m.removeLoad = function () {
        this.elemLoad && this.elemLoad.remove()
    }, m.exit = function () {
        layui.data(a.tableName, {
            key: a.request.tokenName,
            remove: !0
        }), location.hash = "/userauthen/login/login"
    }, m.req = function (n) {
        var r = n.success,
            e = (n.error, a.request),
            o = a.response,
            i = function () {
                return a.debug ? "<br><cite>URL：</cite>" + n.url : ""
            };
        if (n.data = n.data || {}, n.headers = n.headers || {}, e.tokenName) {
            var t = "string" == typeof n.data ? JSON.parse(n.data) : n.data;
            n.data[e.tokenName] = e.tokenName in t ? n.data[e.tokenName] : layui.data(a.tableName)[e.tokenName] || "", n.headers[e.tokenName] = e.tokenName in n.headers ? n.headers[e.tokenName] : layui.data(a.tableName)[e.tokenName] || ""
        }
        return delete n.success, delete n.error, l.ajax(l.extend({
            type: "get",
            dataType: "json",
            success: function (e) {
                var t = o.statusCode;
                if (e[o.statusName] == t.ok) "function" == typeof n.done && n.done(e);
                else if (e[o.statusName] == t.logout) m.exit();
                else {
                    var a = ["<cite>Error：</cite> " + (e[o.msgName] || "返回状态码异常"), i()].join("");
                    m.error(a)
                }
                "function" == typeof r && r(e)
            },
            error: function (e, t) {
                var a = ["请求异常，请重试<br><cite>错误信息：</cite>" + t, i()].join("");
                m.error(a), "function" == typeof a && a(res)
            }
        }, n))
    }, m.popup = function (e) {
        var n = e.success,
            t = e.skin;
        return delete e.success, delete e.skin, r.open(l.extend({
            type: 1,
            title: "提示",
            content: "",
            id: "LAY-system-view-popup",
            skin: "layui-layer-admin" + (t ? " " + t : ""),
            shadeClose: !0,
            closeBtn: !1,
            success: function (e, t) {
                var a = l('<i class="layui-icon" close>&#x1006;</i>');
                e.append(a), a.on("click", function () {
                    r.close(t)
                }), "function" == typeof n && n.apply(this, arguments)
            }
        }, e))
    }, m.error = function (e, t) {
        return m.popup(l.extend({
            content: e,
            maxWidth: 300,
            offset: "t",
            anim: 6,
            id: "LAY_adminError"
        }, t))
    }, n.prototype.render = function (e, n) {
        var r = this;
        layui.router();
        return e = a.views + e + a.engine, l("#" + t).children(".layadmin-loading").remove(), m.loading(r.container), l.ajax({
            url: e,
            type: "get",
            dataType: "html",
            data: {
                v: layui.cache.version
            },
            success: function (e) {
                var t = l(e = "<div>" + e + "</div>").find("title"),
                    a = {
                        title: t.text() || (e.match(/\<title\>([\s\S]*)\<\/title>/) || [])[1],
                        body: e
                    };
                t.remove(), r.params = n || {}, r.then && (r.then(a), delete r.then), r.parse(e), m.removeLoad(), r.done && (r.done(a), delete r.done)
            },
            error: function (e) {
                if (m.removeLoad(), r.render.isError) return m.error("请求视图文件异常，状态：" + e.status);
                404 === e.status ? r.render("userauthen/system/404") : r.render("userauthen/system/error"), r.render.isError = !0
            }
        }), r
    }, n.prototype.parse = function (e, t, n) {
        var a = this,
            r = "object" == typeof e,
            o = r ? e : l(e),
            i = r ? e : o.find("*[template]"),
            s = function (t) {
                var e = c(t.dataElem.html()),
                    a = l.extend({
                        params: d.params
                    }, t.res);
                t.dataElem.after(e.render(a)), "function" == typeof n && n();
                try {
                    t.done && new Function("d", t.done)(a)
                } catch (e) {
                    console.error(t.dataElem[0], "\n存在错误回调脚本\n\n", e)
                }
            },
            d = layui.router();
        o.find("title").remove(), a.container[t ? "after" : "html"](o.children()), d.params = a.params || {};
        for (var u = i.length; 0 < u; u--) ! function () {
            var t = i.eq(u - 1),
                a = t.attr("lay-done") || t.attr("lay-then"),
                e = c(t.attr("lay-url") || "").render(d),
                n = c(t.attr("lay-data") || "").render(d),
                r = c(t.attr("lay-headers") || "").render(d);
            try {
                n = new Function("return " + n + ";")()
            } catch (e) {
                y.error("lay-data: " + e.message), n = {}
            }
            try {
                r = new Function("return " + r + ";")()
            } catch (e) {
                y.error("lay-headers: " + e.message), r = r || {}
            }
            e ? m.req({
                type: t.attr("lay-type") || "get",
                url: e,
                data: n,
                dataType: "json",
                headers: r,
                success: function (e) {
                    s({
                        dataElem: t,
                        res: e,
                        done: a
                    })
                }
            }) : s({
                dataElem: t,
                done: a
            })
        }();
        return a
    }, n.prototype.send = function (e, t) {
        var a = c(e || this.container.html()).render(t || {});
        return this.container.html(a), this
    }, n.prototype.refresh = function (e) {
        var t = this,
            a = t.container.next().attr("lay-templateid");
        return t.id != a || t.parse(t.container, "refresh", function () {
            t.container.siblings('[lay-templateid="' + t.id + '"]:last').remove(), "function" == typeof e && e()
        }), t
    }, n.prototype.then = function (e) {
        return this.then = e, this
    }, n.prototype.done = function (e) {
        return this.done = e, this
    }, e("view", m)
});