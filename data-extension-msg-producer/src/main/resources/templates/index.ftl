<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Justified Nav Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="/msg-producer/css/bootstrap.min.css" rel="stylesheet">
    <link href="/msg-producer/css/bootstrap-table.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/msg-producer/css/justified-nav.css" rel="stylesheet">

</head>

<body>

<div class="container">

    <!-- The justified navigation menu is meant for single line per list item.
         Multiple lines will require custom code not provided by Bootstrap. -->
    <div class="masthead">
        <h3 class="text-muted">Message Producer</h3>
        <nav>
            <ul class="nav nav-justified">
                <li id="start"><a href="#">Get Start</a></li>
                <li id="task" class="active"><a href="#">Task List</a></li>
                <li id="rule"><a href="#">Rule List</a></li>
            </ul>
        </nav>
    </div>
</div>

    <!-- Jumbotron -->

<div class="container" style="">
    <!-- Example row of columns -->
    <div id="start-table" class="row" style="display: none;">
        <div class="jumbotron">
            <h1>start-table!</h1>
            <p class="lead">Cras justo odio, dapibus ac facilisis in, egestas eget quam. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet.</p>
        </div>
        <div class="col-lg-4">
            <h2>Safari bug warning!</h2>
            <p class="text-danger">As of v9.1.2, Safari exhibits a bug in which resizing your browser horizontally causes rendering errors in the justified nav that are cleared upon refreshing.</p>
            <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
            <p><a class="btn btn-primary" href="#" role="button">View details &raquo;</a></p>
        </div>
        <div class="col-lg-4">
            <h2>Heading</h2>
            <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis euismod. Donec sed odio dui. </p>
            <p><a class="btn btn-primary" href="#" role="button">View details &raquo;</a></p>
        </div>
        <div class="col-lg-4">
            <h2>Heading</h2>
            <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa.</p>
            <p><a class="btn btn-primary" href="#" role="button">View details &raquo;</a></p>
        </div>
    </div>
    <div id="task-table" class="row" style="display: block;">

        <div class="col-lg-12">

        <table data-toggle="table">
            <thead>
            <tr>
                <th></th>
                <th>任务名称</th>
                <th>Topic</th>
                <th>模版</th>
                <th>发送频率(秒/条)</th>
                <th>状态</th>
                <th>最后执行时间</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list tasks as task>
                <div class="alert alert-warning alert-dismissible hide" id="warn-${task.id}" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>Warning!</strong> <p></p>
                </div>
            <tr>

                <td>${task?index}</td>
                <td>${task.name}</td>
                <td>${task.topic}</td>
                <td>${task.template}</td>
                <td>${task.window}</td>
                <td>${task.status?then('Running','Stoped')}</td>
                <td>${task.lastSuccessTime?string('yyyy-MM-dd HH:mm:ss.SSS')}</td>
                <td>
                    <div class="btn-group" id="edit-list" role="group" aria-label="...">
                        <button type="button" class="btn btn-default">暂停</button>
                        <button type="button" class="btn btn-default">启动</button>
                        <button type="button" class="btn btn-default">编辑</button>
                        <button type="button" class="btn btn-default">删除</button>
                        <div class="hidden" name="hidden-id">${task.id}</div>
                        <div class="hidden" name="hidden-name">${task.name}</div>
                    </div>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
            <div class="btn-group">
                <button id="add" type="button" class="btn btn-default">
                    新增
                </button>
            </div>
        </div>
    </div>
    <div class="modal fade in" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">新增</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="txt_id">ID</label>
                        <input readonly type="text" name="txt_id" class="form-control" id="txt_id" placeholder="xxxxxx">
                    </div>

                    <div class="form-group">
                        <label for="txt_name">任务名称</label>
                        <input type="text" name="txt_name" class="form-control" id="txt_name" placeholder="task_name">
                    </div>
                    <div class="form-group">
                        <label for="txt_topic">Topic</label>
                        <input type="text" name="txt_topic" class="form-control" id="txt_topic" placeholder="test">
                    </div>
                    <div class="form-group">
                        <label for="txt_template">模板</label>
                        <input type="text" name="txt_template" class="form-control" id="txt_template" placeholder="xxxxxxxx">
                    </div>
                    <div class="form-group">
                        <label for="txt_window">发送频率(秒/条)</label>
                        <input type="text" name="txt_window" class="form-control" id="txt_window" placeholder="10">
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" name="close" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" name="save" id="btn_submit" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Site footer -->
    <footer class="footer">
        <p>&copy; 2016 csg, Inc.</p>
    </footer>

</div> <!-- /container -->
<script src="/msg-producer/js/bootstrap.js"></script>
<script src="/msg-producer/js/jquery.min.js"></script>
<script src="/msg-producer/js/bootstrap-table.min.js"></script>
<script>
    $(document).ready(function(){
        $("#start").click(function(){
            console.log("start");
            $("#start").addClass("active");
            $("#start-table").show();
            $("#task").removeClass("active");
            $("#task-table").hide();
            $("#rule").removeClass("active");
        });
        $("#task").click(function(){
            console.log("task");
            $("#start").removeClass("active");
            $("#start-table").hide();
            $("#task").addClass("active");
            $("#task-table").show();
            $("#rule").removeClass("active");
        });
        $("#rule").click(function(){
            console.log("rule");
            $("#start").removeClass("active");
            $("#task").removeClass("active");
            $("#rule").addClass("active");
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
                $.get("/msg-producer/msg/findJob/"+id ,function (data) {
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
</script>

</body>
</html>

