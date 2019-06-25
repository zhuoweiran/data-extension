$(document).ready(function(){

    $("#edit-list > button").click(function () {
        var id = $(this).siblings(".hidden[name='hidden-id']").html();
        var name = $(this).siblings(".hidden[name='hidden-name']").html();
        var btnName = $(this).attr("name");
        if(btnName == "edit"){

            console.log($("#myModal button.close").html());
            $("#myModalLabel").text("编辑");
            //查询数据
            $.get("/msg-producer/msg/findRule/"+id ,function (data) {
                console.log(data);
                fillDate(data.data);
            });
            $('#myModal').show();
            console.log("编辑:" + id);
        }else if(btnName == "delete"){
            console.log("删除" + id);
            $.get("/msg-producer/msg/deleteRule/" + id,function (data) {
                if(data.status.status == 0){
                    showWarn(id,"SUCCESS",name + ":删除成功","alert-success");
                }else{
                    showWarn(id,"FAILD",name + ":删除失败","alert-danger");
                }
            });
        }
    });
    $(".modal-footer > button[name='save']").click(function () {//保存
        var name = $(this).attr("name");
        if(name == "save"){
            console.log(name);
        }
        $.post("/msg-producer/msg/saveRule/",getData(),function (data) {
            console.info("保存成功" + data);
            if(data.status.status == 0) {
                showWarn("save","SUCCESS","保存成功","alert-success");
            }else{
                showWarn("save","FAILD",":保存失败","alert-danger");
            }
        });
        closeEditModal();

    });
    $("#add").click(function () {//新增
        var jobId = $(this).siblings(".hidden").html();
        fillDate({"jobId":jobId});
        $('#myModal').show();
    });

    $(".modal-footer > button[name='close']").click(closeEditModal);//关闭
    function fillDate(data) {
        $(".modal-body #txt_id").val(data.id);
        $(".modal-body #txt_jobId").val(data.jobId);
        $(".modal-body #txt_name").val(data.name);
        $(".modal-body #txt_key").val(data.key);
        $(".modal-body #txt_value").val(data.value);
        $(".modal-body #txt_type").val(data.valueType);
    }
    function closeEditModal(){
        console.log("close Edit Modal");
        $('#myModal').hide();
    }
    function showWarn(id,title,data,styles) {
        console.log($("warn-" + id));
        $("#warn-" + id + ">p").text(data);
        $("#warn-" + id + ">strong").text(title);
        $("#warn-" + id).removeClass("alert-success");
        $("#warn-" + id).removeClass("alert-danger");
        $("#warn-" + id).addClass(styles);
        $("#warn-" + id).removeClass("hide");
    }
    function getData() {
        var id = $(".modal-body #txt_id").val();
        var jobId = $(".modal-body #txt_jobId").val();
        var name = $(".modal-body #txt_name").val();
        var key = $(".modal-body #txt_key").val();
        var value = $(".modal-body #txt_value").val();
        var type = $(".modal-body #txt_type").val();
        return {
            "id":id,
            "jobId":jobId,
            "name":name,
            "key":key,
            "value":value,
            "valueType":type
        }
    }
    $(".close").click(function () {//关闭warn
        console.log("close warning");
        $(this).parent("div.alert").addClass("hide");
        window.location.reload();
    });
})