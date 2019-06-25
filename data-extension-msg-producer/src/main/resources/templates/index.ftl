<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Message Producer</title>

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
                <li id="rule" ><a href="#">Rule List</a></li>
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

            <table data-toggle="table" class="table table-condensed table-bordered table-striped">
                <thead>
                <tr>
                    <th></th>
                    <th>任务名称</th>
                    <th>Topic</th>
                    <th>模版</th>
                    <th>发送频率<br>(秒/条)</th>
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
                        <td>${task.template?substring(0,47)}...</td>
                        <td>${task.window}</td>
                        <td>${task.status?then('Running','Stoped')}</td>
                        <td>${task.lastSuccessTime?string('yyyy-MM-dd HH:mm:ss.SSS')}</td>
                        <td>
                            <div class="btn-group" id="edit-list" role="group" aria-label="...">

                                <button type="button" class="btn btn-success" name="start">
                                    <span class="glyphicon glyphicon-play"></span>
                                </button>
                                <button type="button" class="btn btn-success" name="stop">
                                    <span class="glyphicon glyphicon-pause"></span>
                                </button>
                                <button type="button" class="btn btn-success" name="edit">
                                    <span class="glyphicon glyphicon-pencil"></span>
                                </button>
                                <button type="button" class="btn btn-danger" name="delete">
                                    <span class="glyphicon glyphicon-trash"></span>
                                </button>
                                <button type="button" class="btn btn-success" name="rule">
                                    <span class="glyphicon glyphicon-list"></span>
                                </button>
                                <div class="hidden" name="hidden-id">${task.id}</div>
                                <div class="hidden" name="hidden-name">${task.name}</div>
                            </div>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <div class="btn-group">
                <button id="add" type="button" class="btn btn-success">
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
                        <label for="txt_msgType">消息格式</label>
                        <select class="form-control" id="txt_msgType">
                            <option>EText</option>
                            <option>Json</option>
                            <option>NoHeadJson</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="txt_window">发送频率(秒/条)</label>
                        <input type="text" name="txt_window" class="form-control" id="txt_window" placeholder="10">
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" name="close" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" name="save" id="btn_submit" class="btn btn-success" data-dismiss="modal"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
                </div>
            </div>
        </div>
    </div>
    <div id="rule-table" class="row" style="display: none;">
        <div class="col-lg-12">

            <table data-toggle="table" class="table table-bordered table-condensed table-striped">
                <thead>
                <tr>
                    <th></th>
                    <th>ID</th>
                    <th>Job ID</th>
                    <th>名称</th>
                    <th>Key</th>
                    <th>Value</th>
                    <th>类型</th>
                    <th>状态</th>
                </tr>
                </thead>
                <tbody>
                <#list rules as rule>
                    <tr>

                        <td>${rule?index}</td>
                        <td>${rule.id}</td>
                        <td>${rule.jobId}</td>
                        <td>${rule.name}</td>
                        <td>${rule.key}</td>
                        <td>${rule.value}</td>
                        <td>${rule.valueType}</td>
                        <td>${rule.status?then('有效','无效')}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
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
<script src="/msg-producer/js/job-table.js"></script>
</body>
</html>

