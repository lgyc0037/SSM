package com.houb.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.houb.pojo.SmbmsRole;
import com.houb.pojo.SmbmsUser;
import com.houb.service.RoleService;
import com.houb.service.UserService;
import com.houb.utils.Constants;

@Controller
public class UserController extends BaseController{


	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@RequestMapping("/")
	public String index(){
		System.out.println("/跳转到登录页面！");
		return "login";
	}
	@RequestMapping("/ceSi")
	public String ceSi(){
		System.out.println("/这是个练习github的小测试s");
		return "xxx";
	}

	public String sss(){
		System.out.println("我吐了");
		return "wotule";
	}
	
	@RequestMapping("/toLogin")
	public String toLogin(){
		System.out.println("toLogin跳转到登录页面！");
		return "login";
	}
	
	@RequestMapping("/doLogin")
	public String doLogin(String userCode,String userPassword,HttpServletRequest request,HttpSession session){
		System.err.println("doLogin处理登陆业务！"+userCode+"----"+userPassword);
		try {
			SmbmsUser loginUser = userService.getLoginUser(userCode);
			if(loginUser!=null){
				//密码判断
				if(loginUser.getUserpassword().equals(userPassword)){
					//将登录用户的信息保存到session中
					session.setAttribute(Constants.SESSION_USER, loginUser);
					//跳转页面
					return "frame";
				}else{
					//密码错误
					request.setAttribute("error", "帐号或者密码错误!");
					return "login";
				}
			}else{
				request.setAttribute("error", "帐号或者密码错误!");
				return "login";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login";
		
	}
	
	@RequestMapping("/loginOut")
	public String loginOut(HttpSession session){
//		session.invalidate();//session销毁
		session.removeAttribute(Constants.SESSION_USER);
		return "login";
	}
	
	
	
	
	
	
	/**
	 * 根据用户名模糊查询用户列表并分页
	 * @return 需要展示用户数据的页面的逻辑视图名
	 */
	@RequestMapping("/userlist")
	public String userlist(@RequestParam(value="queryname",required=false)String queryname,
			@RequestParam(value="queryUserRole",required=false) String queryUserRole,
			@RequestParam(value="pageIndex",required=false) String pageIndex,
			Model m){
		
			int _queryUserRole=0;
			if(queryUserRole!=null){
				_queryUserRole=Integer.parseInt(queryUserRole);
			}
		
			int _pageIndex=1;
			if(pageIndex!=null){
				_pageIndex=Integer.parseInt(pageIndex);
			}
		
			//查询queryname对应的数据库中数据的总条数
//			int count=userService.getCountByNameAndRole(queryname,_queryUserRole);
			//每页的页面容量
			int pageSize=Constants.PAGE_SIZE;
			//计算总页数
//			int totalPageCount=count%pageSize==0?count/pageSize:count/pageSize+1;
			
			//查询用户列表List<User> userlist
			PageInfo<SmbmsUser> page=userService.findUserByNameAndRole(queryname,_queryUserRole,_pageIndex,pageSize);
			//查询角色列表
			List<SmbmsRole> roleList = roleService.getRoleList();
		
			m.addAttribute("userlist", page.getList());
			m.addAttribute("roleList", roleList);
			m.addAttribute("totalPageCount", page.getPages());
			m.addAttribute("count", page.getSize());
			m.addAttribute("pageIndex", page.getPageNum());
			m.addAttribute("userRole", _queryUserRole);
			m.addAttribute("queryname", queryname);
		
		return "userlist";
	}
	
	
	/*@RequestMapping("/toAdd")
	public String toAdd(@ModelAttribute("user")User user){
		System.out.println("toAdd跳转到新增页面！");
		return "useradd";
	}
	
	
	@RequestMapping("/doAdd")
	public String doAdd(User user,HttpServletRequest request,Model m,HttpSession session){
		try {
			System.out.println("doAdd跳转到新增页面！"+user);
			//1.接收到前台传递的数据
			User u=(User) session.getAttribute(Constants.SESSION_USER);
			user.setCreatedBy(u.getId());
			user.setCreationDate(new Date());
			
//			2.保存用户
			int res = userService.add(user);
			if(res>0){
				return "redirect:/userlist";
			}else{
				m.addAttribute("error", "新增用户异常");
				return "useradd";
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "新增用户异常");
			return "useradd";
		}
		
	}*/
	
	
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String toAdd(@ModelAttribute("user")SmbmsUser user){
		System.out.println("toAdd跳转到新增页面！"+user);
		return "useradd";
	}
	
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String doAdd(SmbmsUser user,HttpServletRequest request,Model m,HttpSession session,
			@RequestParam(value="a_idPicPath",required=false)MultipartFile upfile){
		try {
			System.out.println("doAdd跳转到新增页面！"+user);
			//1.获取真实长传路径application--->servlet
			String parentPath=request.getSession().getServletContext().getRealPath("statics"+File.separator+"fileupload");
			File pFile=new File(parentPath);
			if(!pFile.exists()){
				pFile.mkdirs();
			}
			
			
//			2.获取文件名，构建文件上传对象
			String fileName =System.currentTimeMillis()+new Random().nextInt(100000)+"_"+upfile.getOriginalFilename();
			
			File file=new File(pFile, fileName);
			System.out.println("上传文件名："+fileName);
			System.out.println("真实文件名："+upfile.getName());
//			
//			3.上传
			upfile.transferTo(file);
			System.out.println("头像上传完成！");
//			存储图片的路径statics/css/style.css
			String idPicPath="statics"+File.separator+"fileupload"+File.separator+fileName;
			//1.接收到前台传递的数据
			SmbmsUser u=(SmbmsUser) session.getAttribute(Constants.SESSION_USER);
//			user.setCreatedBy(u.getId());
//			user.setCreationDate(new Date());
			user.setIdpicpath(idPicPath);
			
//			2.保存用户
			int res = userService.add(user);
			if(res>0){
				return "redirect:/userlist";
			}else{
				m.addAttribute("error", "新增用户异常");
				return "useradd";
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "新增用户异常");
			return "useradd";
		}
		
	}
	
	
	
	
	
	
	@RequestMapping("/view2")
	public String view2(String id,Model m){
		System.out.println("view跳转到chakan页面！");
		SmbmsUser user = userService.getUserById(id);
		System.out.println("view:"+user);
		m.addAttribute("user", user);
		return "userview";
	}
	
	
	@RequestMapping("/toUpdate")
	public String toUpdate(String id,Model m){
		System.out.println("toUpdate跳转到修改页面！");
		SmbmsUser user = userService.getUserById(id);
		m.addAttribute("user", user);
		return "usermodify";
	}
	
	
	@RequestMapping("/doUpdate")
	public String doUpdate(SmbmsUser user,HttpServletRequest request,Model m,HttpSession session){
		try {
			System.out.println("doUpdate执行修改逻辑！"+user);
			//1.接收到前台传递的数据
			SmbmsUser u=(SmbmsUser) session.getAttribute(Constants.SESSION_USER);
			System.out.println("u--->"+u);
			user.setModifyby(u.getId());
			user.setModifydate(new Date());
			
//			2.保存用户
			int res = userService.modify(user);
			if(res>0){
				return "redirect:/userlist";
			}else{
				m.addAttribute("error", "修改用户异常");
				return "usermodify";
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "修改用户异常");
			return "usermodify";
		}
		
	}
	
	
	
	@RequestMapping("/delete")
	public String delete(String id,Model m){
		System.out.println("toAdd跳转到新增页面！");
		int res = userService.deleteUserById(Integer.parseInt(id));
		if(res>0){
			return "redirect:/userlist";
		}else{
			m.addAttribute("error", "删除用户异常");
			return "redirect:/userlist";
		}
	}
	
	
	@RequestMapping("/testException")
	public String testException(String id,Model m){
		if(id.equals("1001")){
//			int s=10/0;
			System.out.println(1111);
			return "redirect:/userlist";
		}else{
			System.out.println(2222);
			throw new RuntimeException("1帐号密码不正确！");
		}
	}
	
	
	//局部异常处理
	/*@ExceptionHandler(value=RuntimeException.class)
	public String exceptionhandle(RuntimeException e,HttpServletRequest request){
		request.setAttribute("err1", e.getMessage());
		return "error";
	}*/
	
	
	@RequestMapping(value="/isExits",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String isExits(String userCode){
		//1.调用底层业务方法判断传入的用户编码是否存在
		boolean res = userService.userCodeIsExits(userCode);
		Map<String, Object> map=new HashMap<>();
		if(res){
			map.put("code", "202");
			map.put("msg", "用户名已被占用!");
		}else{
			map.put("code", "200");
			map.put("msg", "用户名可以使用!");
		}
		//2.调用json转换工具将map转换为json字符串
		String jsonString = JSON.toJSONString(map);
		//"{key:value}"
		//{"code":"202","msg":用户名已被占用!"}
		System.out.println("isExits================"+jsonString);
		return jsonString;
	}
	
	
	
	@RequestMapping("/view")
	@ResponseBody
	public Object view(String id){
		System.out.println("view跳转到chakan页面！"+id);
		SmbmsUser user = userService.getUserById(id);
		System.out.println("view:"+user);
		String str=JSON.toJSONString(user);
		System.out.println("str---"+str);
		return str;
	}
	
	
}
