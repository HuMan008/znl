/**
 * Created by hypo on 2016/12/6.
 */
layui.define(['element', 'jquery', 'layer', 'form', 'laydate','util'], function (exports) {
    "use strict";

    var layer = layui.layer;
    var $ = layui.jquery;

    (function () {
        var index;
        $.ajaxSetup({
            beforeSend:function () {
                index = layer.load(1)  ;
            } ,
            complete:function (data) {
                layer.close(index);
            } ,
            async:true,
            error:function () {
                layer.closeAll()
            },

        });

        // 设置Ajax请求带上TOKEN
        /*        $.ajaxSetup({
         headers: {
         'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
         }
         });*/
    })();


    // 点击提交按钮之后，防止多次点击
    function lockSubmit(btn) {
        setTimeout(function () {
            var $btn = $(btn);
            if (!$btn.is('[lay-submit]')) {
                $btn = $btn.find('[lay-submit]');
            }
            $btn.addClass('layui-disabled').attr('disabled', '');
        });
    }

    function unlockSubmit(btn) {
        setTimeout(function () {
            var $btn = $(btn);
            if (!$btn.is('[lay-submit]')) {
                $btn = $btn.find('[lay-submit]');
            }
            $btn.removeAttr('disabled').removeClass('layui-disabled');
        });
    }

    // layui.form().on('submit', function (ctx) {
    //     var $elem = $(ctx.elem);
    //     lockSubmit($elem);
    // });

    exports('main', {

        lockSubmit: lockSubmit,
        unlockSubmit: unlockSubmit
    });
});