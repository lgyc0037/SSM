package com.houb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.houb.mapper.SmbmsRoleMapper;
import com.houb.pojo.SmbmsRole;
import com.houb.pojo.SmbmsRoleExample;
import com.houb.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	SmbmsRoleMapper smbmsRoleMapper;

	@Override
	public List<SmbmsRole> getRoleList() {
		SmbmsRoleExample example = new SmbmsRoleExample();
		return smbmsRoleMapper.selectByExample(example);
	}

}
