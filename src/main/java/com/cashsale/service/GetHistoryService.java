package com.cashsale.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cashsale.bean.MessageDTO;
import com.cashsale.bean.PagerDTO;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.SocketDAO;
import com.cashsale.enums.ResultEnum;
import sun.plugin2.message.Message;

public class GetHistoryService {

	/**
	 * 返回与name用户相关的聊天记录
	 * @param senderName
	 * @param receiverName
	 * @param strPage
	 * @return
	 */
	public ResultDTO<PagerDTO> getHistory(String senderName, String receiverName, String strPage){
		int page = 1;
		if( strPage != null && !strPage.equals("") )
		{
			page = Integer.parseInt( strPage );
		}
		Map<String, Object> mapSum = new SocketDAO().getMessage(senderName, receiverName,page);
		List<MessageDTO> list = (List<MessageDTO>)mapSum.get(senderName);
		PagerDTO<MessageDTO> message = new PagerDTO<>(page+1,list);
		if(mapSum.get("sender") == null){
			return  new ResultDTO<PagerDTO>(ResultEnum.NO_MORE_DATA.getCode(), null,ResultEnum.NO_MORE_DATA.getMsg());
		}
		return new ResultDTO<PagerDTO>(ResultEnum.SEARCH_SUCCESS.getCode(), message,ResultEnum.SEARCH_SUCCESS.getMsg());
	}
}
