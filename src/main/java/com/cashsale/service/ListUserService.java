package com.cashsale.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cashsale.dao.ListUserCFDAO;

/**
 * @author Sylvia
 * 2018年11月4日
 */
public class ListUserService {
	
	/**
	 * 返回推荐结果
	 * @param username
     *        被推荐用户
	 * @return
     *        推荐结果
	 */
	public String getList(String username){
		Map<String, Double> simUserSimMap = new HashMap<String, Double>();
		Map<String, Map<String, Integer>> userPerfMap = new ListUserCFDAO().getUserScore(username);
		//获得被推荐用户的map
		Map<String, Integer> recommed = userPerfMap.get("1");
		for (Entry<String, Map<String, Integer>> userPerfEn : userPerfMap.entrySet()) {
			String userName = userPerfEn.getKey();
			if (!username.equals(userName)) {
				double sim = getUserSimilar(recommed, userPerfEn.getValue());
				System.out.println("p5与" + userName + "之间的相关系数:" + sim);
				// 将被推荐用户与其它用户的相关系数存进map中
				simUserSimMap.put(userName, sim);
			}
		}
		List<Entry<String, Double>> enList = getSort(simUserSimMap);
		Map<String, Map<String, Integer>> simUserObjMap = new ListUserCFDAO().getSimUserObjMap(username, enList);
		System.out.println("推荐结果:" + getRecommend(simUserObjMap, simUserSimMap));
		return getRecommend(simUserObjMap, simUserSimMap);
	}
	
	/**
	 * 计算皮尔逊相关系数
	 * @param pm1
     *      被推荐用户的map
	 * @param pm2
     *      其它用户的map
	 * @return
     *      其它用户和被推荐用户的皮尔逊系数
	 */
    private static double getUserSimilar(Map<String, Integer> pm1, Map<String, Integer> pm2) {
        // 数量n
    	int n = 0;
        // Σxy=x1*y1+x2*y2+....xn*yn
    	int sxy = 0;
        // Σx=x1+x2+....xn
    	int sx = 0;
        // Σy=y1+y2+...yn
    	int sy = 0;
        // Σx2=(x1)2+(x2)2+....(xn)2
    	int sx2 = 0;
        // Σy2=(y1)2+(y2)2+....(yn)2
    	int sy2 = 0;
        for (Entry<String, Integer> pme : pm1.entrySet()) {
            String key = pme.getKey();
            Integer x = pme.getValue();
            Integer y = pm2.get(key);
            if (x != null && y != null) {
                n++;
                sxy += x * y;
                sx += x;
                sy += y;
                sx2 += Math.pow(x, 2);
                sy2 += Math.pow(y, 2);
            }
        }
        // p=(Σxy-Σx*Σy/n)/Math.sqrt((Σx2-(Σx)2/n)(Σy2-(Σy)2/n));
        double sd = sxy - sx * sy / n;
        double sm = Math.sqrt((sx2 - Math.pow(sx, 2) / n) * (sy2 - Math.pow(sy, 2) / n));
        return Math.abs(sm == 0 ? 1 : sd / sm);
    }
    
    
    /**
     * 加权排序推荐，获取推荐结果
     * @param simUserObjMap
     *          相似用户和他的浏览过的商品
     * @param simUserSimMap
     *          相似用户和他的皮尔逊系数
     * @return
     *        推荐结果
     */
    private static String getRecommend(Map<String, Map<String, Integer>> simUserObjMap,
            Map<String, Double> simUserSimMap) {
        Map<String, Double> objScoreMap = new HashMap<String, Double>();
        for (Entry<String, Map<String, Integer>> simUserEn : simUserObjMap.entrySet()) {
            String user = simUserEn.getKey();
            double sim = simUserSimMap.get(user);
            for (Entry<String, Integer> simObjEn : simUserEn.getValue().entrySet()) {
            	//加权（相似度*评分）
                double objScore = sim * simObjEn.getValue();
                String objName = simObjEn.getKey();
                if (objScoreMap.get(objName) == null) {
                    objScoreMap.put(objName, objScore);
                } else {
                    double totalScore = objScoreMap.get(objName);
                    //将所有用户的加权评分作为最后的推荐结果数据
                    objScoreMap.put(objName, totalScore + objScore);
                }
            }
        }
        
        List<Entry<String, Double>> enList = getSort(objScoreMap);
        
        for (Entry<String, Double> entry : enList) {
            System.out.println(entry.getKey()+"的加权推荐值:"+entry.getValue());
        }
        //返回推荐结果
        return enList.get(enList.size() - 1).getKey();
    }
    
    /**
     * 从小到大排序
     * @param objScoreMap
     *          需排序的map
     * @return
     *      排序后的map
     */
    private static List<Entry<String, Double>> getSort(Map<String, Double> objScoreMap){
    	List<Entry<String, Double>> enList = new ArrayList<Entry<String, Double>>(objScoreMap.entrySet());
         
         
         Collections.sort(enList, new Comparator<Map.Entry<String, Double>>() {
         	
             public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                 Double a = o1.getValue() - o2.getValue();
                 if (a == 0) {
                     return 0;
                 } else if (a > 0) {
                     return 1;
                 } else {
                     return -1;
                 }
             }
         });
         return enList;
    }
}
