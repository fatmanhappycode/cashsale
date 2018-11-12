package com.cashsale.service;

import java.util.List;
import java.util.Map;

import com.cashsale.bean.PagerDTO;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.ScreenDAO;

public class ScreenService {

	public ResultDTO<PagerDTO> screen(String queryInfo, String strPage){
		int page = 1;
		if( strPage != null && !strPage.equals("") )
		{
			page = Integer.parseInt( strPage );
		}
		List<ProductDO> products =  new ScreenDAO().search(queryInfo, page);
		PagerDTO<ProductDO> product = new PagerDTO<>(page+1,products);
		if( product == null )
		{
			return  new ResultDTO<PagerDTO>(116, null,"没有更多数据了……");
		}else {
			return  new ResultDTO<PagerDTO>(107, product,"查询成功");
		}
	}
}
