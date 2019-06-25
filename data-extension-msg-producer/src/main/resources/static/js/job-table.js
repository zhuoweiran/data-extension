$(document).ready(function(){
    $("#start").click(start);
    $("#get-start").click(start)
    function start(){
        console.log("start");
        $("#start").addClass("active");
        $("#start-table").show();
        $("#task").removeClass("active");
        $("#task-table").hide();
        $("#rule-table").hide();
        $("#rule").removeClass("active");
    }
    $("#task").click(taskclick);
    $("#task-btn").click(taskclick)
    function taskclick(){
        console.log("task");
        $("#start").removeClass("active");
        $("#start-table").hide();
        $("#task").addClass("active");
        $("#task-table").show();
        $("#rule-table").hide();
        $("#rule").removeClass("active");
    }
    $("#rule").click(ruleclick);
    $("#list-rule").click(ruleclick);
    function ruleclick(){
        console.log("rule");
        $("#start").removeClass("active");
        $("#task").removeClass("active");
        $("#rule").addClass("active");
        $("#start-table").hide();
        $("#task-table").hide();
        $("#rule-table").show();
    }


    $("#edit-list > button").click(function () {
        var id = $(this).siblings(".hidden[name='hidden-id']").html();
        var name = $(this).siblings(".hidden[name='hidden-name']").html();
        var btnName = $(this).attr("name");

        if(btnName=="stop"){
            console.log("暂停" + id);
            $.get("/msg-producer/msg/stop/" + id,function (data) {
                if(data.status.status == 0){
                    showWarn(id,"SUCCESS",name + ":暂停成功" ,"alert-success");
                }else{
                    showWarn(id,"FAILD",name + ":暂停失败" , "alert-danger");
                }
            });

        }else if(btnName == "start"){
            console.log("启动" + id);
            $.get("/msg-producer/msg/satrt/" + id,function (data) {
                if(data.status.status == 0){
                    showWarn(id,"SUCCESS",name + ":启动成功","alert-success");
                }else{
                    showWarn(id,"FAILD",name + ":启动失败","alert-danger");
                }
            });

        }else if(btnName == "edit"){

            console.log($("#myModal button.close").html());
            $("#myModalLabel").text("编辑");
            //查询数据
            $.get("/msg-producer/msg/findJob/"+id ,function (data) {
                console.log(data);
                fillDate(data.data);
            });
            $('#myModal').show();
            console.log("编辑:" + id);
        }else if(btnName == "delete"){
            console.log("删除" + id);
            $.get("/msg-producer/msg/delete/" + id,function (data) {
                if(data.status.status == 0){
                    showWarn(id,"SUCCESS",name + ":删除成功","alert-success");
                }else{
                    showWarn(id,"FAILD",name + ":删除失败","alert-danger");
                }
            });
        }else if(btnName == "rule"){
            window.open("/msg-producer/rule/"+id,"_blank");
        }
    });

    $(".modal-footer > button[name='save']").click(function () {//保存
        var name = $(this).attr("name");
        if(name == "save"){
            console.log(name);
        }
        $.post("/msg-producer/msg/save/",getData(),function (data) {
            console.info("保存成功" + data);
            if(data.status.status == 0) {
                window.location.reload();
            }else{
                showWarn(id,"FAILD",name + ":保存失败","alert-danger");
            }
        });
        closeEditModal();

    });
    $(".modal-footer > button[name='close']").click(closeEditModal);//关闭

    $(".close").click(function () {//关闭warn
        console.log("close warning");
        $(this).parent("div.alert").addClass("hide");
        window.location.reload();
    });

    $("#add").click(function () {//新增
        fillDate({});
        $('#myModal').show();
    });

    $("button[name='test']").click(function () {//验证模版
        var id = $(this).siblings("div[name='hidden-id']").html();
        var name = $(this).siblings("div[name='hidden-name']").html();
        $.get("/msg-producer/msg/testTemplate/"+id,function(data){
            console.log(data);
            if(data.status.status == 0) {
                showWarn(id,"SUCCESS",name + ":测试成功,结果为" + data.data ,"alert-success");
            }else{
                showWarn(id,"FAILD",name + ":测试失败,错误为" + data.data,"alert-danger");
            }
        })
    })
    function showWarn(id,title,data,styles) {
        console.log($("warn-" + id));
        $("#warn-" + id + ">p").text(data);
        $("#warn-" + id + ">strong").text(title);
        $("#warn-" + id).removeClass("alert-success");
        $("#warn-" + id).removeClass("alert-danger");
        $("#warn-" + id).addClass(styles);
        $("#warn-" + id).removeClass("hide");
    }
    function closeEditModal(){
        console.log("close Edit Modal");
        $('#myModal').hide();
    }
    function fillDate(data) {
        $(".modal-body #txt_id").val(data.id);
        $(".modal-body #txt_name").val(data.name);
        $(".modal-body #txt_topic").val(data.topic);
        $(".modal-body #txt_template").val(data.template);
        $(".modal-body #txt_window").val(data.window);
        $(".modal-body #txt_msgType").val(data.msgType);
    }
    function getData() {
        var id = $(".modal-body #txt_id").val();
        var name = $(".modal-body #txt_name").val();
        var topic = $(".modal-body #txt_topic").val();
        var template = $(".modal-body #txt_template").val();
        var window = $(".modal-body #txt_window").val();
        var msgType = $(".modal-body #txt_msgType").val();
        return {
            "id":id,
            "name":name,
            "topic":topic,
            "template":template,
            "window":window,
            "msgType":msgType
        }
    }
})