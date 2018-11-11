package com.cashsale.service;

import java.util.List;
import java.util.Map;

import com.cashsale.bean.PagerDTO;
import com.cashsale.bean.ProductDO;
import com.cashsale.bean.ResultDTO;
import com.cashsale.dao.ScreenDAO;

public class ScreenService {
	
	public ResultDTO<PagerDTO> screen(String queryInfo, String strPage){
		Map<String, Object> map = new ScreenDAO().search(queryInfo, strPage);
		int code = (int) map.get("code");
		int page = (int) map.get("page");
		if( code == 115 )
		{
			PagerDTO<ProductDO> product = new PagerDTO<>(page,null);
			return  new ResultDTO<PagerDTO>(115, product,"查询失败！");
		} else if (code == 200) {
			List<ProductDO> queryResult = (List<ProductDO>) map.get("queryResult");
			PagerDTO<ProductDO> queryData = new PagerDTO<>(page,queryResult);
			return new ResultDTO<PagerDTO>(200, queryData, "查询成功");
		} else {
			return new ResultDTO<PagerDTO>(116, null, "没有更多数据了……");
		}
	}
}
