<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/page/common/head.jsp"%>
        <div class="right">
            <div class="location">
                <strong>你现在所在的位置是:</strong>
                <span>用户管理页面</span>
            </div>
            <div class="search">
           		<form method="post" action="${pageContext.request.contextPath }/userlist">
					<input name="method" value="query" class="input-text" type="hidden">
					 <span>用户名：</span>
					 <input name="queryname" class="input-text"	type="text" value="${queryname}">
					 
					 <span>用户角色：</span>
					 <select name="queryUserRole">
						<c:if test="${roleList != null }">
						   <option value="0">--请选择--</option>
						   <c:forEach var="role" items="${roleList}">
						   		<option <c:if test="${role.id == userRole }">selected="selected"</c:if>
						   		value="${role.id}">${role.rolename}</option>
						   </c:forEach>
						</c:if>
	        		</select>
					 
					 <input type="hidden" name="pageIndex" value="1"/>
					 <input	value="查 询" type="submit" id="searchbutton">
					 <a href="${pageContext.request.contextPath}/add" >添加用户</a>
				</form>
            </div>
            <!--用户-->
            <table class="providerTable" cellpadding="0" cellspacing="0">
                <tr class="firstTr">
                    <th width="10%">用户编码</th>
                    <th width="20%">用户名称</th>
                    <th width="10%">性别</th>
                    <th width="10%">年龄</th>
                    <th width="10%">电话</th>
                    <th width="10%">用户角色</th>
                    <th width="30%">操作</th>
                </tr>
                   <c:forEach var="user" items="${userlist }" varStatus="status">
					<tr>
						<td>
						<span>${user.usercode }</span>
						</td>
						<td>
						<span>${user.username }</span>
						</td>
						<td>
							<span>
								<c:if test="${user.gender==1}">男</c:if>
								<c:if test="${user.gender==2}">女</c:if>
							</span>
						</td>
						<td>
						<span>${user.age}</span>
						</td>
						<td>
						<span>${user.phone}</span>
						</td>
						<td>
							<span></span>
						</td>
						<td>
						<span><a class="viewUser" href="javascript:void(0)" userid=${user.id } username=${user.username }><img src="${pageContext.request.contextPath }/statics/images/read.png" alt="查看" title="查看"/></a></span>
						<span><a class="modifyUser" href="toUpdate?id=${user.id}" userid=${user.id } username=${user.username }><img src="${pageContext.request.contextPath }/statics/images/xiugai.png" alt="修改" title="修改"/></a></span>
						<span><a class="deleteUser" href="delete?id=${user.id}" userid=${user.id } username=${user.username }><img src="${pageContext.request.contextPath }/statics/images/schu.png" alt="删除" title="删除"/></a></span>
						</td>
					</tr>
				</c:forEach>
			</table>
			<input type="hidden" id="totalPageCount" value="${totalPageCount}"/>
		  	<c:import url="rollpage.jsp">
	          	<c:param name="totalCount" value="${count}"/>
	          	<c:param name="currentPageNo" value="${pageIndex}"/>
	          	<c:param name="totalPageCount" value="${totalPageCount}"/>
          	</c:import>
         <div class="providerAdd">
			    <div>
					<label>用户编码：</label>
					<input type="text" id="v_userCode" value="" readonly="readonly">
				</div>
				<div>
					<label>用户名称：</label>
					<input type="text" id="v_userName" value="" readonly="readonly">
				</div>
				<div>
					<label>用户性别：</label>
					<input type="text" id="v_gender" value="" readonly="readonly">
				</div>
				<div>
					<label>出生日期：</label>
					<input type="text" Class="Wdate" id="v_birthday" value=""
					readonly="readonly" onclick="WdatePicker();">
				</div>
				<div>
					<label>用户电话：</label>
					<input type="text" id="v_phone" value="" readonly="readonly">
				</div>
				<div>
					<label>用户角色：</label>
					<input type="text" id="v_userRoleName" value="" readonly="readonly">
				</div>
				<div>
					<label>用户地址：</label>
					<input type="text" id="v_address" value="" readonly="readonly">
				</div>
				<div>
					<label>创建日期：</label>
					<input type="text" Class="Wdate" id="v_creationDate" value=""
					readonly="readonly" onclick="WdatePicker();">
				</div>
			</div>
        </div>
    </section>
 
<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该用户吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/page/common/foot.jsp" %>
<script type="text/javascript">
	$(function() {
		
		
		$(".viewUser").click(function() {
			
			//获取到要查看的用户id
			var uid=$(this).attr("userid");
			alert(uid);
			$.ajax({
				url:"view",
				type:"GET",
				data:"id="+uid,
				success:function(result){//回调函数
			//得到后台返回数据后对返回结果进行处理
					/* alert("1-1"+typeof obj1);
					alert("1-2"+obj1);
					var jsonStr=JSON.stringify(obj1);
					alert("2-1"+typeof jsonStr);
					alert("2-2"+jsonStr);
					//将json字符串转换为json对象
					var obj=eval('('+jsonStr+')');
					alert("3-1"+typeof obj);
					alert("3-2"+obj);
					alert(obj.code); */
					alert("1-1"+typeof result);
					alert("1-2"+result);
					var obj=JSON.parse(result);
					alert("2-1"+typeof obj);
					alert("2-2"+obj);
					$("#v_userCode").val(obj.userCode);
					
					$("#v_birthday").val(obj.birthday);
					
				},
				error:function(){
					alert("出错了！");
				}
			});	
			
			
			
		});
		
		
		
		
		
	});
</script>
