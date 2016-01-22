package com.example.friendscircle.bean;

import java.util.List;

public interface IDataSource {

	/**
	 * 说说用户名
	 * @return
	 */
	String getUsername();

	/**
	 * 说说日期
	 * @return
	 */
	long getDate();

	/**
	 * 说说Logo
	 * @return
	 */
	int getmLogo();

	/**
	 * 点赞人的Logo   ResourceID
	 * @return
	 */
	int[] getZanLogos();

	/**
	 * 点赞数
	 * @return
	 */
	int getZanCount();

	/**
	 * 评论内容
	 * @return
	 */
	List<CommonBean> getCommonBeans();

	/**
	 * 评论数
	 * @return
	 */
	int getCommonCount();
	
}
