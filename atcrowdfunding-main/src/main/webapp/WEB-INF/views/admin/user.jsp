<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%@ include file="/WEB-INF/include/base_css.jsp" %>

    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }

        table tbody tr:nth-child(odd) {
            background: #F4F4F4;
        }

        table tbody td:nth-child(even) {
            color: #C00;
        }
    </style>
</head>

<body>

<%@include file="/WEB-INF/include/manager_hearder.jsp" %>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">

            <%@include file="/WEB-INF/include/manager_menu.jsp" %>

        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" action="${PATH}/static/admin/index.html" method="post" role="form"
                          style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" name="condition" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="deleteAdmins" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary" style="float:right;"
                            onclick="window.location='${PATH}/admin/add.html'"><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:choose>
                                <c:when test="${empty PageInfo.list}">
                                    <tr>
                                        <td clospan="6">没有管理员</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${PageInfo.list}" var="admin" varStatus="vs">
                                        <tr>
                                            <td>${vs.count}</td>
                                            <td><input id="${admin.id}" type="checkbox"></td>
                                            <td>${admin.loginacct}</td>
                                            <td>${admin.username}</td>
                                            <td>${admin.email}</td>
                                            <td>
                                                <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>
                                                <button adminId="${admin.id}" type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>
                                                <button adminid="${admin.id}" type="button" class="btn btn-danger btn-xs deleteAdminBtn"><i class=" glyphicon glyphicon-remove" ></i>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <ul class="pagination">
                                        <c:choose>
                                            <c:when test="${pageInfo.hasPreviousPage }">
                                                <!-- 有上一页 : 点击时跳转到当前页码的上一页-->
                                                <li>
                                                    <a href="${PATH }//static/admin/index.html?pageNum=${PageInfo.pageNum-1 }&condition=${param.condition}">上一页</a>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <!-- 没有上一页 -->
                                                <li class="disabled"><a href="javascript:void(0);">上一页</a></li>
                                            </c:otherwise>
                                        </c:choose>

                                        <!-- 中间页码 -->
                                        <c:forEach items="${PageInfo.navigatepageNums }" var="index">
                                            <c:choose>
                                                <c:when test="${index==PageInfo.pageNum }">
                                                    <!-- 当前页码 -->
                                                    <li class="active"><a href="Javascript:void(0);">${index}<span
                                                            class="sr-only">(current)</span></a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li>
                                                        <a href="${PATH}/static/admin/index.html?pageNum=${index}&condition=${param.condition}">${index}</a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <!-- <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
                                        <li><a href="#">2</a></li>
                                        <li><a href="#">3</a></li>
                                        <li><a href="#">4</a></li>
                                        <li><a href="#">5</a></li> -->
                                        <c:choose>
                                            <c:when test="${PageInfo.hasNextPage }">
                                                <!-- 有下一页 : 点击时跳转到当前页码的下一页-->
                                                <li>
                                                    <a href="${PATH }//static/admin/index.html?pageNum=${PageInfo.pageNum+1 }&condition=${param.condition}">下一页</a>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <!-- 没有下一页 -->
                                                <li class="disabled"><a href="javascript:void(0);">下一页</a></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

    <%@ include file="/WEB-INF/include/base_js.jsp" %>
        <script type="text/javascript">
            $(function () {
                $(".list-group-item").click(function () {
                    if ($(this).find("ul")) {
                        $(this).toggleClass("tree-closed");
                        if ($(this).hasClass("tree-closed")) {
                            $("ul", this).hide("fast");
                        } else {
                            $("ul", this).show("fast");
                        }
                    }
                });
            });
            $("tbody .btn-success").click(function () {
                window.location.href = "assignRole.html";
            });

            $("tbody .btn-primary").click(function () {
                <!--100行的adminId-->
              var adminIds= $(this).attr("adminId");
                window.location.href = "${PATH}/admin/edit.html?myId="+adminIds;
            });

            //删除管理员
            $(".deleteAdminBtn").click(function(){
                //获取要删除的管理员的ID
                var adminid=$(this).attr("adminid")
                console.log(adminid)
                //上传相应上的id，发送请求到服务器完成删除操作
                //加上一个提示弹窗
                layer.confirm("你确定要删除："+ $(this).parents("tr").children("td:eq(3)").text() +"吗？",
                    {title:"删除提示",icon:3},function(a){
                        layer.close(a);
                        window.location="${PATH}/admin/deleteAdmin?id="+adminid;
                    });
            });

            //全选和全不选
            $("table thead :checkbox").click(function(){
                    var flag=this.checked;
                $("table tbody :checkbox").prop("checked",flag)
            });
            $("table tbody :checkbox").click(function(){
                var totalCount=$("table tbody :checkbox").length;
                var checkedCount=$("table tbody :checkbox:checked").length;
                $("table thead :checkbox").prop("checked",totalCount==checkedCount);
            });


            //批量删除,拿到复选框中被选中的id删除
            $("#deleteAdmins").click(function () {
                alter("gsq");
                console.log("gsq");
                var $checkedBth=$("table tbody :checkbox:checked");
                var ar=new Array();
                $.each($checkedBth ,function () {
                    ar.push(this.id);//因为这个id是原生的，不用转成$("this").attr("id")去拿id值
                });
                var idstr=ar.join(",");//以,分割

                window.location="${PATH}/admin/deleteAdmins?ids="+idstr;
            });

        </script>
<script type="text/javascript" src="${PATH}/static/include/admin_js.js"></script>
</body>
</html>
