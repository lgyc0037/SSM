package com.houb.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.houb.pojo.SmbmsUser;


public interface UserService {
	/**
	 * 增加用户信息
	 * @param connection
	 * @param user
	 * @return
	 * @
	 */
	public int add(SmbmsUser user);

	/**
	 * 通过userCode获取User
	 * @param connection
	 * @param userCode
	 * @return
	 * @
	 */
	public SmbmsUser getLoginUser(String userCode);

	/**
	 * 通过条件查询-userList
	 * @param connection
	 * @param userName
	 * @param userRole
	 * @return
	 * @
	 */
	public PageInfo<SmbmsUser> getUserList(String userName,int userRole,int currentPageNo, int pageSize);
	/**
	 * 通过条件查询-用户表记录数
	 * @param connection
	 * @param userName
	 * @param userRole
	 * @return
	 * @
	 */
	public int getUserCount(String userName,int userRole);
	
	/**
	 * 通过userId删除user
	 * @param delId
	 * @return
	 * @
	 */
	public int deleteUserById(Integer delId); 
	
	
	/**
	 * 通过userId获取user
	 * @param connection
	 * @param id
	 * @return
	 * @
	 */
	public SmbmsUser getUserById(String id); 
	
	/**
	 * 修改用户信息
	 * @param connection
	 * @param user
	 * @return
	 * @
	 */
	public int modify(SmbmsUser user);
	
	
	/**
	 * 修改当前用户密码
	 * @param connection
	 * @param id
	 * @param pwd
	 * @return
	 * @
	 */
	public int updatePwd(int id,String pwd);
	

	/**
	 * 查询queryname和queryUserRole对应的数据库中数据的总条数
	 * @param queryname
	 * @param _queryUserRole
	 * @return
	 */
	public int getCountByNameAndRole(String queryname, int queryUserRole);
	
	
	/**
	 * 查询queryname和queryUserRole对应的数据库中数据
	 * @param queryname
	 * @return 符合条件的数据集合
	 */
	public PageInfo<SmbmsUser> findUserByNameAndRole(String queryname, int queryUserRole, int pageIndex,int pageSize);
	
	
	/**
	 * 查询用户编码是否存在
	 * @param userCode
	 * @return true:已经存在，false:未使用
	 */
	boolean userCodeIsExits(String userCode);
	
}
