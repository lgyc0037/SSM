<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/page/common/head.jsp"%>

<div class="right">
     <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>用户管理页面 >> 用户添加页面</span>
        </div>
        <div class="providerAdd">
            <form id="userForm" enctype="multipart/form-data" name="userForm" method="post" action="${pageContext.request.contextPath }/add">
                <!--div的class 为error是验证错误，ok是验证成功-->
                <div>
                    <label for="userCode">用户编码：</label>
                    <input type="text" name="usercode" id="usercode" value=""> 
					<!-- 放置提示信息 -->
					<font color="red"></font>
                </div>
                <div>
                    <label for="userName">用户名称：</label>
                    <input type="text" name="username" id="username" value=""> 
					<font color="red"></font>
                </div>
                <div>
                    <label for="userPassword">用户密码：</label>
                    <input type="password" name="userpassword" id="userpassword" value=""> 
					<font color="red"></font>
                </div>
                <div>
                    <label for="ruserPassword">确认密码：</label>
                    <input type="password" name="ruserpassword" id="ruserpassword" value=""> 
					<font color="red"></font>
                </div>
                <div>
                    <label >用户性别：</label>
					<select name="gender" id="gender">
					    <option value="1" selected="selected">男</option>
					    <option value="2">女</option>
					 </select>
                </div>
                <div>
                    <label for="birthday">出生日期：</label>
                    <input type="text" Class="Wdate" id="birthday" name="birthday" 
					readonly="readonly" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd'});">
					<font color="red"></font>
                </div>
                <div>
                    <label for="phone">用户电话：</label>
                    <input type="text" name="phone" id="phone" value=""> 
					<font color="red"></font>
                </div>
                <div>
                    <label for="address">用户地址：</label>
                   <input name="address" id="address"  value="">
                </div>
                <div>
                    <label >用户角色：</label>
                    <!-- 列出所有的角色分类 -->
					<!-- <select name="userRole" id="userRole"></select> -->
					<select name="userrole" id="userRole">
						<option value="1">系统管理员</option>
			    		<option value="2">经理</option>
			    		<option value="3" selected="selected">普通用户</option>
					</select>
	        		<font color="red"></font>
                </div>
                <div>
                	<input type="hidden" id="errorinfo" value="${uploadFileError}"/>
                    <label for="a_idPicPath">证件照</label>
                   	<input type="file" name="a_idPicPath" id="a_idPicPath"/>
                    <font color="red"></font>
                </div>
                <div class="providerAddBtn">
                    <input type="submit" name="add" id="add" value="保存" >
					<input type="button" id="back" name="back" value="返回" >
                </div>
            </form>
        </div>   
       
</div>
</section>
<%@include file="/WEB-INF/page/common/foot.jsp" %>
<script type="text/javascript">
        	$(function() {
        		$("#back").click(function(){
        			//window.history.back();
        			window.history.go(-1);
        		});
        		
        		//失去焦点事件
        		
        		$("#usercode").blur(function() {
					//获取到userCode的value
					var userCode=$("#usercode").val();
					//将该值以ajax方式发送到后台
					$.ajax({
						url:"isExits",
						type:"POST",
						data:"userCode="+userCode,
						success:function(obj1){//回调函数
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
							if(obj.code==200){
								$("#usercode").next().html(obj.msg);
								$("#usercode").next().css("color","green");
							}else{
								$("#usercode").next().html(obj.msg);
								$("#usercode").next().css("color","red");
							}
						},
						error:function(){
							alert("出错了！");
						}
					});
        			
        		
        		});
        		
        		
        		
			});
</script>

