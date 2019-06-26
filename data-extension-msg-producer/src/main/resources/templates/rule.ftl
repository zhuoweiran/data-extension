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
                <li id="task" ><a href="#">Task List</a></li>
                <li id="rule" class="active"><a href="#">Rule List</a></li>
            </ul>
        </nav>
    </div>
</div>

    <!-- Jumbotron -->

<div class="container" style="">
    <div id="rule-table" class="row" >
        <div class="col-lg-12">

            <table data-toggle="table" class="table table-condensed table-bordered table-striped">
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
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <div class="alert alert-dismissible hide" id="warn-save" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>Warning!</strong> <p></p>
                </div>
                <#list rules as rule>
                    <div class="alert alert-dismissible hide" id="warn-${rule.id}" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <strong>Warning!</strong> <p></p>
                    </div>
                    <tr>

                        <td>${rule?index + 1}</td>
                        <td>${rule.id}</td>
                        <td>${rule.jobId}</td>
                        <td>${rule.name}</td>
                        <td>${rule.key}</td>
                        <td>${rule.value}</td>
                        <td>${rule.valueType}</td>
                        <td>${rule.status?then('有效','无效')}</td>
                        <td>
                            <div class="btn-group" id="edit-list" role="group" aria-label="...">
                                <button type="button" class="btn btn-success" name="edit">
                                    <span class="glyphicon glyphicon-pencil"></span>
                                </button>
                                <button type="button" class="btn btn-danger" name="delete">
                                    <span class="glyphicon glyphicon-trash"></span>
                                </button>
                                <div class="hidden" name="hidden-id">${rule.id}</div>
                                <div class="hidden" name="hidden-name">${rule.name}</div>
                            </div>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <div class="btn-group">
                <div class="hidden" name="hidden-jobId">${jobId}</div>
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
                        <label for="txt_jobId">Job ID</label>
                        <input readonly type="text" name="txt_jobId" class="form-control" id="txt_jobId" placeholder="xxxxxx">
                    </div>

                    <div class="form-group">
                        <label for="txt_name">名称</label>
                        <input type="text" name="txt_name" class="form-control" id="txt_name" placeholder="rule_name">
                    </div>
                    <div class="form-group">
                        <label for="txt_key">Key</label>
                        <input type="text" name="txt_key" class="form-control" id="txt_key" placeholder="key">
                    </div>
                    <div class="form-group">
                        <label for="txt_value">Value</label>
                        <input type="text" name="txt_value" class="form-control" id="txt_value" placeholder="123">
                    </div>
                    <div class="form-group">
                        <label for="txt_type">类型</label>
                        <select class="form-control" id="txt_type">
                            <option>String</option>
                            <option>Int</option>
                            <option>Date</option>
                            <option>Array</option>
                            <option>Object</option>
                            <option>Table_Device</option>
                            <option>Table_TmpDevice</option>
                        </select>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" name="close" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" name="save" id="btn_submit" class="btn btn-success" data-dismiss="modal"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
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
<script src="/msg-producer/js/rule-table.js"></script>
</body>
</html>

