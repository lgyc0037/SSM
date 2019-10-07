package com.houb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.houb.mapper.SmbmsUserMapper;
import com.houb.pojo.SmbmsUser;
import com.houb.pojo.SmbmsUserExample;
import com.houb.pojo.SmbmsUserExample.Criteria;
import com.houb.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	SmbmsUserMapper smbmsUserMapper;

	@Override
	public int add(SmbmsUser user) {
		return smbmsUserMapper.insertSelective(user);
	}

	@Override
	public SmbmsUser getLoginUser(String userCode) {
		SmbmsUserExample example = new SmbmsUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsercodeEqualTo(userCode);
		List<SmbmsUser> list = smbmsUserMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	// 分页逻辑实现
	@Override
	public PageInfo<SmbmsUser> getUserList(String userName, int userRole, int currentPageNo, int pageSize) {
		// 1.PageHelper设置当前页和页面容量
		PageHelper.startPage(currentPageNo, pageSize);

		// 查询条件添加
		SmbmsUserExample example = new SmbmsUserExample();
		Criteria criteria = example.createCriteria();
		
		if(userName!=null && userName.length()!=0){
			criteria.andUsernameLike("%" + userName + "%");
		}
		
		if(userRole!=0){
			criteria.andUserroleEqualTo((long) userRole);
		}
		
		List<SmbmsUser> list = smbmsUserMapper.selectByExample(example);
		System.out.println("getUserList--page---"+list);
		// 2.创建PageInfo<SmbmsUser>对象。返回该对象
		PageInfo<SmbmsUser> page = new PageInfo<>(list);

		return page;
	}

	@Override
	public int getUserCount(String userName, int userRole) {

		SmbmsUserExample example = new SmbmsUserExample();
		Criteria criteria = example.createCriteria();
		if(userName!=null && userName.length()!=0){
			criteria.andUsernameLike("%" + userName + "%");
		}
		
		if(userRole!=0){
			criteria.andUserroleEqualTo((long) userRole);
		}

		return smbmsUserMapper.countByExample(example);
	}

	@Override
	public int deleteUserById(Integer delId) {
		return smbmsUserMapper.deleteByPrimaryKey(delId.longValue());
	}

	@Override
	public SmbmsUser getUserById(String id) {
		return smbmsUserMapper.selectByPrimaryKey(new Long(id));
	}

	@Override
	public int modify(SmbmsUser user) {
		return smbmsUserMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public int updatePwd(int id, String pwd) {
		SmbmsUser user = new SmbmsUser();
		user.setUserpassword(pwd);
		user.setId((long) id);
		return smbmsUserMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public int getCountByNameAndRole(String queryname, int queryUserRole) {
		
		System.out.println("我被执行了============================================");
		SmbmsUserExample example = new SmbmsUserExample();
		Criteria criteria = example.createCriteria();
		if(queryname!=null && queryname.length()!=0){
			criteria.andUsernameLike("%" + queryname + "%");
		}
		
		if(queryUserRole!=0){
			criteria.andUserroleEqualTo((long) queryUserRole);
		}

		return smbmsUserMapper.countByExample(example);
	}

	@Override
	public PageInfo<SmbmsUser> findUserByNameAndRole(String queryname, int queryUserRole, int pageIndex, int pageSize) {
		// 1.PageHelper设置当前页和页面容量
		PageHelper.startPage(pageIndex, pageSize);

		// 查询条件添加
		SmbmsUserExample example = new SmbmsUserExample();
		Criteria criteria = example.createCriteria();
		if(queryname!=null && queryname.length()!=0){
			criteria.andUsernameLike("%" + queryname + "%");
		}
		
		if(queryUserRole!=0){
			criteria.andUserroleEqualTo((long) queryUserRole);
		}

		List<SmbmsUser> list = smbmsUserMapper.selectByExample(example);
		System.out.println("getUserList--page---"+list);
		// 2.创建PageInfo<SmbmsUser>对象。返回该对象
		PageInfo<SmbmsUser> page = new PageInfo<>(list);

		return page;
	}

	@Override
	public boolean userCodeIsExits(String userCode) {
		// 查询条件添加
		SmbmsUserExample example = new SmbmsUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsercodeEqualTo(userCode);

		List<SmbmsUser> list = smbmsUserMapper.selectByExample(example);

		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
