package com.cashsale.service;

import java.util.List;
import java.util.Map;

import com.cashsale.bean.PagerDTO;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.ScreenDAO;
import com.cashsale.enums.ResultEnum;

public class ScreenService {

	public ResultDTO<PagerDTO> screen(String queryInfo, String strPage){
		int page = 1;
		if( strPage != null && !strPage.equals("") )
		{
			page = Integer.parseInt( strPage );
		}
		List<ProductDO> products =  new ScreenDAO().search(queryInfo, page);
		PagerDTO<ProductDO> product = new PagerDTO<>(page+1,products);
		if( products.isEmpty() )
		{
			return  new ResultDTO<PagerDTO>(ResultEnum.NO_MORE_DATA.getCode(), null,ResultEnum.NO_MORE_DATA.getMsg());
		}else {
			return  new ResultDTO<PagerDTO>(ResultEnum.SEARCH_SUCCESS.getCode(), product,ResultEnum.SEARCH_SUCCESS.getMsg());
		}
	}
}
