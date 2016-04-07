package com.dianrong.common.uniauth.cas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianrong.common.uniauth.common.bean.Info;
import com.dianrong.common.uniauth.common.bean.Response;
import com.dianrong.common.uniauth.common.bean.dto.UserDto;
import com.dianrong.common.uniauth.common.bean.request.LoginParam;
import com.dianrong.common.uniauth.common.bean.request.UserParam;
import com.dianrong.common.uniauth.common.client.UniClientFacade;
import com.dianrong.common.uniauth.common.enm.UserActionEnum;
import com.dianrong.common.uniauth.sharerw.facade.UARWFacade;

@Service
public class UserInfoManageService extends BaseService{

	@Autowired
	private UARWFacade uarwFacade;

	@Autowired
	private UniClientFacade uniClientFacade;

	/**
	 * . 根据邮箱获取用户信息
	 * 
	 * @param email
	 *            邮箱
	 * @return user
	 * @throws Exception 
	 */
	public UserDto findSingleUser(String email) throws Exception {
		UserParam userParam = new UserParam();
		userParam.setEmail(email);
		Response<UserDto> response = uniClientFacade.getUserResource().getSingleUser(userParam);
		List<Info> infoList = response.getInfo();
		checkInfoList(infoList);
		return response.getData();
	}
	
	/**
	 * . 根据用户邮箱或者电话获取用户信息
	 * 
	 * @param userTag 用户信息标识
	 * @return user
	 * @throws Exception 
	 */
	public UserDto getUserDetailInfo(String account) throws Exception {
		LoginParam loginParam = new LoginParam();
		loginParam.setAccount(account);
		Response<UserDto> response = uniClientFacade.getUserResource().getUserInfoByUserTag(loginParam);
		List<Info> infoList = response.getInfo();
		checkInfoList(infoList);
		return response.getData();
	}

	/**
	 * . 根据邮箱获取用户信息
	 * 
	 * @param email
	 *            邮箱
	 * @return user
	 * @throws Exception 
	 */
	public void updateUserInfo(Long id, String email, String name, String phone)
			throws Exception {
		UserParam userParam = new UserParam();
		userParam.setId(id);
		userParam.setEmail(email);
		userParam.setName(name);
		userParam.setPhone(phone);
		userParam.setUserActionEnum(UserActionEnum.UPDATE_INFO);
		Response<UserDto> response = uarwFacade.getUserRWResource().updateUser(userParam);
		List<Info> infoList = response.getInfo();
		checkInfoList(infoList);
	}
	
	/**
	 * . 根据邮箱获取用户信息
	 * 
	 * @param email
	 *            邮箱
	 * @return user 
	 * @throws Exception  Exception
	 */
	public void updateUserPassword(Long id, String newPassword, String originPassword)
			throws Exception {
		UserParam userParam = new UserParam();
		userParam.setId(id);
		userParam.setOriginPassword(originPassword);
		userParam.setPassword(newPassword);
		userParam.setUserActionEnum(UserActionEnum.RESET_PASSWORD_AND_CHECK);
		Response<UserDto> response = uarwFacade.getUserRWResource().updateUser(userParam);
		List<Info> infoList = response.getInfo();
		checkInfoList(infoList);
	}
}
