$(document).ready(function(){
    $("#start").click(function(){
        console.log("start");
        $("#start").addClass("active");
        $("#start-table").show();
        $("#task").removeClass("active");
        $("#task-table").hide();
        $("#rule-table").hide();
        $("#rule").removeClass("active");
    });
    $("#task").click(function(){
        console.log("task");
        $("#start").removeClass("active");
        $("#start-table").hide();
        $("#task").addClass("active");
        $("#task-table").show();
        $("#rule-table").hide();
        $("#rule").removeClass("active");
    });
    $("#rule").click(function(){
        console.log("rule");
        $("#start").removeClass("active");
        $("#task").removeClass("active");
        $("#rule").addClass("active");
        $("#task-table").hide();
        $("#rule-table").show();
    });

    $("#edit-list > button").click(function () {
        var id = $(this).siblings(".hidden[name='hidden-id']").html();
        var name = $(this).siblings(".hidden[name='hidden-name']").html();
        if($(this).html() == "暂停"){
            console.log("暂停" + id);
            $.get("/msg-producer/msg/stop/" + id,function (data) {
                if(data.status.status == 0){
                    showWarn(id,name + ":暂停成功");
                }else{
                    showWarn(id,name + ":暂停失败");
                }
            });

        }else if($(this).html() == "启动"){
            console.log("启动" + id);
            $.get("/msg-producer/msg/satrt/" + id,function (data) {
                if(data.status.status == 0){
                    showWarn(id,name + ":启动成功");
                }else{
                    showWarn(id,name + ":启动失败");
                }
            });

        }else if($(this).html() == "编辑"){

            console.log($("#myModal button.close").html());
            $("#myModalLabel").text("编辑");
            //查询数据
            $.get("/msg-producer/msg/findRule/"+id ,function (data) {
                console.log(data);
                fillDate(data.data);
            });
            $('#myModal').show();
            console.log("编辑:" + id);
        }else if($(this).html() == "删除"){
            console.log("删除" + id);
            $.get("/msg-producer/msg/satrt/" + id,function (data) {
                if(data.status.status == 0){
                    showWarn(id,name + ":启动成功");
                }else{
                    showWarn(id,name + ":启动失败");
                }
            });
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
                showWarn(id,id + ":保存失败");
            }
        });
        closeEditModal();

    });
    $(".modal-footer > button[name='close']").click(closeEditModal);//关闭

    $(".close").click(function () {
        console.log("close warning");
        $(this).parent("div.alert").addClass("hide");
    });

    $("#add").click(function () {//新增
        fillDate({});
        $('#myModal').show();
    });


    function showWarn(id,data) {
        console.log($("warn-" + id));
        $("#warn-" + id + ">p").text(data);
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
    }
    function getData() {
        var id = $(".modal-body #txt_id").val();
        var name = $(".modal-body #txt_name").val();
        var topic = $(".modal-body #txt_topic").val();
        var template = $(".modal-body #txt_template").val();
        var window = $(".modal-body #txt_window").val();
        return {
            "id":id,
            "name":name,
            "topic":topic,
            "template":template,
            "window":window
        }
    }
})