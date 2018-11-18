<%@ page import="cn.gandan.domain.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
    
    <script>
        function deleteUserID(id) {
            if(confirm("确定删除")){
                location.href = "${pageContext.request.contextPath}/deleteUserServlet?id=" + id;
            }
        }


        window.onload = function () {
            document.getElementById("deleteUser").onclick = function () {
                if(confirm("确定删除选中?")){
                    var flag = false;

                    var userIds = document.getElementsByName("userId");
                    for(var i = 0; i < userIds.length ; i++){
                        if(userIds[i].checked){
                            flag = true;
                            break;
                        }
                    }
                    if(flag){
                        document.getElementById("form").submit();
                    }
                    alert("请标记要删除的数据");
                }
            }

            document.getElementById("firstBox").onclick = function () {
                var userIds = document.getElementsByName("userId");
                for(var i = 0; i < userIds.length ; i++){
                    userIds[i].checked = this.checked;
                }
            }
        }

    </script>
</head>
<body>


<div class="container">
    <h3 style="text-align: center">用户信息列表</h3>


  <div>
      <form class="form-inline" style="float: left" action="${pageContext.request.contextPath}/findUserByServlet" method="post">
          <div class="form-group">
              <label for="name">姓名:</label>
              <input type="text" class="form-control" id="name" name="name" value="${condition.name[0]}">
          </div>
          <div class="form-group">
              <label for="gender">性别:</label>
              <input type="text" class="form-control" id="gender" name="gender" value="${condition.gender[0]}">
          </div>
          <button type="submit" class="btn btn-default">查询</button>
      </form>
  </div>



    <div style="float: right;margin: 5px">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加</a>
        <a class="btn btn-primary" href="javascript:void(0);" id="deleteUser">删除</a>
    </div>

    <form id="form" action="${pageContext.request.contextPath}/deleteServlet" method="post">
        <table border="1" class="table table-bordered table-hover">
            <tr class="success">
                <th><input type="checkbox" id="firstBox"></th>
                <th>编号</th>
                <th>姓名</th>
                <th>性别</th>
                <th>年龄</th>
                <th>籍贯</th>
                <th>QQ</th>
                <th>邮箱</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${pb.list}" var="user" varStatus="s">
                <tr>
                    <td><input type="checkbox" name="userId" value="${user.id}"></td>
                    <td>${s.count}</td>
                    <td>${user.name}</td>
                    <td>${user.gender}</td>
                    <td>${user.age}</td>
                    <td>${user.address}</td>
                    <td>${user.qq}</td>
                    <td>${user.email}</td>
                    <td><a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/findUserServlet?id=${user.id}">修改</a>&nbsp;
                        <a class="btn btn-default btn-sm" href="javascript:deleteUserID(${user.id});" >删除</a></td>
                </tr>

            </c:forEach>
            <%--<tr>--%>
            <%--<td colspan="8" align="center"><a class="btn btn-primary" href="add.html">添加联系人</a></td>--%>
            <%--</tr>--%>
        </table>
    </form>


    <div>
        <nav>
            <ul class="pagination">
                <%--<ul class="pagination">--%>
                    <c:if test="${pb.currentPage == 1}">
                        <li class="disabled">
                    </c:if>

                    <c:if test="${pb.currentPage != 1}">
                        <li>
                    </c:if>
                            <a href="${pageContext.request.contextPath}/findUserByServlet?currentPage=${pb.currentPage - 1}&rows=5&name=${condition.name[0]}&gender=${condition.gender[0]}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                <%--</ul>--%>
                <c:forEach begin="1" end="${pb.totalPage}" var="i">
                <%--<li><a href="${pageContext.request.contextPath}/findUserByServlet?currentPage=${i}&rows=5">${i}</a> </li>--%>
                    <c:if test="${pb.currentPage == i}">
                        <li class="active"><a href="${pageContext.request.contextPath}/findUserByServlet?currentPage=${i}&rows=5&name=${condition.name[0]}&gender=${condition.gender[0]}">${i}</a> </li>
                    </c:if>
                    <c:if test="${pb.currentPage != i}">
                        <li><a href="${pageContext.request.contextPath}/findUserByServlet?currentPage=${i}&rows=5&name=${condition.name[0]}&gender=${condition.gender[0]}">${i}</a> </li>
                    </c:if>
                </c:forEach>
                <%--<ul class="pagination">--%>
                    <c:if test="${pb.currentPage == pb.totalPage}">
                    <li class="disabled">
                        </c:if>
                        <c:if test="${pb.currentPage != pb.totalPage}">
                    <li>
                        </c:if>
                            <a href="${pageContext.request.contextPath}/findUserByServlet?currentPage=${pb.currentPage + 1}&rows=5&name=${condition.name[0]}&gender=${condition.gender[0]}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                    </li>
                <%--</ul>--%>
                <span style="font-size: 25px;margin-left: 5px;font-family: 方正喵呜体">
                    ${pb.totalCont}条记录，共 ${pb.totalPage}页
                </span>
            </ul>
        </nav>
    </div>



</div>
</body>
</html>
