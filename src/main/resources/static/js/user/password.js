layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    /**
     * 表单监听事件
     */
    form.on('submit(saveBtn)', data => {
        console.log(data.field);

        $.ajax({
            type: 'put',
            url: '/user/updatePwd',
            data: {
                oldPwd: data.field.oldPwd,
                newPwd: data.field.newPwd,
                repeatPwd: data.field.repeatPwd,
            },
            success: data => {
                console.log(data);
                if(data.code==200){
                    //修改密码成功后，清空cookie，跳转到首页
                    layer.msg("用户密码修改成功，系统将在3秒钟后退出",()=>{
                        $.removeCookie("userId",{domain: "localhost",path:"/crm"});
                        $.removeCookie("userName",{domain: "localhost",path:"/crm"})
                        $.removeCookie("trueName",{domain: "localhost",path:"/crm"})

                        //父窗口跳转
                        window.parent.location.href=ctx+"/index";
                    });
                }else{
                    layer.msg(data.msg,{icon: 5});
                }
            }
        })
    });

    /**
     * 表单的submit监听
     *      form.on('submit(按钮元素的lay-filter属性值)', function (data) {

            });
     */
   /* form.on('submit(saveBtn)', function (data) {
        // 所有表单元素的值
        console.log(data.field);

        // 发送ajax请求
        $.ajax({
            type:"put",
            url:ctx + "/user/updatePwd",
            data:{
                oldPwd:data.field.oldPwd,
                newPwd:data.field.newPwd,
                repeatPwd:data.field.repeatPwd
            },
            success:function (result) {
                // 判断是否修改成功
                if (result.code == 200) {

                    // 修改密码成功后，清空cookie数据，跳转到登录页面
                    layer.msg("用户密码修改成功，系统将在3秒钟后退出...", function () {
                        // 清空cookie
                        $.removeCookie("userId",{domain:"localhost",path:"/crm"});
                        $.removeCookie("userName",{domain:"localhost",path:"/crm"});
                        $.removeCookie("trueName",{domain:"localhost",path:"/crm"});


                        // 跳转到登录页面(父窗口跳转)
                        window.parent.location.href = ctx + "/index";
                    });

                } else {
                    layer.msg(result.msg, {icon:5});
                }
            }

        });
    });*/


});