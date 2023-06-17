package top.iotos.synApi.utils.iotos.common;

import java.util.*;

/**
 * 比较两个 list
 */
public class ListCompare {

	/**
	 * 获取不同数据
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<String> getDiffrent(List<String> list1, List<String> list2) {
		Map<String, Integer> map = new HashMap<String, Integer>(list1.size() + list2.size());
		List<String> diff = new ArrayList<String>();
		List<String> maxList = list1;
		List<String> minList = list2;
		if (list2.size() > list1.size()) {
			maxList = list2;
			minList = list1;
		}

		for (String string : maxList) {
			map.put(string, 1);
		}

		for (String string : minList) {
			Integer cc = map.get(string);
			if (cc != null) {
				map.put(string, ++cc);
				continue;
			}
			map.put(string, 1);
		}

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {
				diff.add(entry.getKey());
			}
		}
		return diff;
	}


	/**
	 * 获取相同值
	 * @param list1
	 * @param list2
	 * @param lie
	 * @return
	 */
	public static List<Map<String, String>> getIdentical(List<String> list1, List<String> list2,String lie) {
		Map<String, Integer> map = new HashMap<String, Integer>(list1.size() + list2.size());
		List<Map<String, String>> diff = new ArrayList<Map<String, String>>();
		List<String> maxList = list1;
		List<String> minList = list2;
		if (list2.size() > list1.size()) {
			maxList = list2;
			minList = list1;
		}

		for (String string : maxList) {
			map.put(string, 1);
		}

		for (String string : minList) {
			Integer cc = map.get(string);
			if (cc != null) {
				map.put(string, ++cc);
				continue;
			}
			map.put(string, 1);
		}

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {

			}else{
				Map<String, String> Obj = new HashMap<String, String>();
				Obj.put(lie,entry.getKey());
				diff.add(Obj);
			}
		}
		return diff;
	}

	/**
	 * 获取相同数据
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<String> getIdentical(List<String> list1, List<String> list2) {
		Map<String, Integer> map = new HashMap<String, Integer>(list1.size() + list2.size());
		List<String> diff = new ArrayList<String>();
		List<String> maxList = list1;
		List<String> minList = list2;
		if (list2.size() > list1.size()) {
			maxList = list2;
			minList = list1;
		}

		for (String string : maxList) {
			map.put(string, 1);
		}

		for (String string : minList) {
			Integer cc = map.get(string);
			if (cc != null) {
				map.put(string, ++cc);
				continue;
			}
			map.put(string, 1);
		}

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {

			}else{
				diff.add(entry.getKey());
			}
		}
		return diff;
	}


	/**
	 * 获取不同数据
	 * @param list1
	 * @param list2
	 * @param lie
	 * @return
	 */
	public static List<Map<String, Object>> getDiffrent(List<String> list1, List<String> list2,String lie) {
		Map<String, Integer> map = new HashMap<String, Integer>(list1.size() + list2.size());
		List<Map<String, Object>> diff = new ArrayList<Map<String, Object>>();
		List<String> maxList = list1;
		List<String> minList = list2;
		if (list2.size() > list1.size()) {
			maxList = list2;
			minList = list1;
		}

		for (String string : maxList) {
			map.put(string, 1);
		}

		for (String string : minList) {
			Integer cc = map.get(string);
			if (cc != null) {
				map.put(string, ++cc);
				continue;
			}
			map.put(string, 1);
		}

		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {
				Map<String, Object> Obj = new HashMap<String, Object>();
				Obj.put(lie,entry.getKey());
				diff.add(Obj);
			}
		}
		return diff;
	}


	/**
	 * 删除 字段 相同数据
	 * @param listmap
	 * @param list2
	 * @param lie
	 * @return
	 */
	public static List<Map<String, String>> delIdentical(List<Map<String, String>> listmap, List<String> list2,String lie) {
		List<Map<String, String>> Rmap = new ArrayList<Map<String, String>>();

		for (int i = 0; i < listmap.size(); i++) {
			Map<String, String> map = listmap.get(i);
			String val = map.get(lie);
			boolean bool = false;
			for (int j = 0; j < list2.size(); j++) {
				if(val.equals(list2.get(j))){
					bool = true;
					break;
				}
			}
			if(!bool){
				Rmap.add(map);
			}
		}
		return Rmap;
	}

	/**
	 * 删除 字段 相同数据
	 * @param listmap
	 * @param list2
	 * @param lie
	 * @return
	 */
	public static List<Map<String, Object>> delIdenticalObj(List<Map<String, Object>> listmap, List<String> list2,String lie) {
		List<Map<String, Object>> Rmap = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < listmap.size(); i++) {
			Map<String, Object> map = listmap.get(i);
			String val = map.get(lie)+"";
			boolean bool = false;
			for (int j = 0; j < list2.size(); j++) {
				if(val.equals(list2.get(j))){
					bool = true;
					break;
				}
			}
			if(!bool){
				Rmap.add(map);
			}
		}
		return Rmap;
	}



	/**
	 * 判断 val 是否在  list1 中
	 * @param list1
	 * @param val
	 * @return
	 */
	public static boolean Is_existence(List<String> list1, String val) {
		boolean bool = false;
		for (int i = 0; i <list1.size() ; i++) {
			if(list1.get(i).equals(val)){
				bool = true;
				break;
			}
		}
		return bool;
	}


	/**
	 * 判断 map 的 key 是否在  list1 中
	 * @param list1
	 * @param map
	 * @return
	 */
	public static boolean Is_existence(String []list1, Map<String,Object> map) {
		boolean bool = false;
		for(String key:map.keySet()){
			String val = key;
			for (int i = 0; i <list1.length ; i++) {
				if(list1[i].equals(val)){
					bool = true;
					break;
				}
			}
		}
		return bool;
	}
	
	
	
	/**
	 * 判断 val 是否在  list1 中
	 * @param list1
	 * @param val
	 * @return
	 */
	public static boolean Is_existence(List<Map<String,Object>> list1,String lie, String val) {
		boolean bool = false;
		for (int i = 0; i <list1.size() ; i++) {
			Map<String,Object> obj = list1.get(i);
			if(obj.get(lie).equals(val)){
				bool = true;
				break;
			}
		}
		return bool;
	}



	/**
	 * 判断 val 是否在  arr 中
	 * @param arr
	 * @param val
	 * @return
	 */
	public static boolean Is_existence(String arr[], String val) {
		boolean bool = false;
		for (int i = 0; i <arr.length ; i++) {
			if(arr[i].equals(val)){
				bool = true;
				break;
			}
		}
		return bool;
	}


	public static boolean Is_existenceStr(List<String> list1, String val) {
		boolean bool = false;
		for (int i = 0; i <list1.size() ; i++) {
			if(list1.get(i).equals(val)){
				bool = true;
				break;
			}
		}
		return bool;
	}



	public static boolean Is_existence(List<Integer> list1, Integer val) {
		boolean bool = false;
		for (int i = 0; i <list1.size() ; i++) {
			if(list1.get(i)==val){
				bool = true;
				break;
			}
		}
		return bool;
	}



	public static Object[] StringArrAdd(Object[] custom,Object name) {
		//添加姓名

		if(custom==null) { //若数组为空，定义数组的长度为1

			custom=new Objects[1];

			custom[0]=name;

		} else {
			//若数组不为空，把数组复制出一个新的，在原数组的基础上加1
			Object[] copy= Arrays.copyOf(custom, custom.length+1);
			//把原先数组制空
				custom=null;
			//把新数组给原先这个数组
			custom=copy;
			custom[custom.length-1]=name;
		}
		return  custom;
	}



	/**
	 * 获取 数组去重数据 和 重复值
	 * List<String> list = new LinkedList<String> ();
	 *         list.add ("电视");
	 *         list.add ("电视");
	 *         list.add ("手机");
	 *         list.add ("冰箱");
	 *         list.add ("电视");
	 *         list.add ("空调");
	 *         list.add ("手机");
	 * @param list
	 * @return
	 */
	public static Map<String,Object> getListDifference(List<String> list){
		Map<String,Object> Rmap = new HashMap<>();
		Set<String> set = new HashSet<String> ();
		List<String> arr = new ArrayList<> ();
		String result = "";
		for ( String string : list )
		{
			// 表示存在就加不进去了
			if (!set.add (string))
			{
				if (!result.contains (string))
				{
					result += string + "|";
					arr.add(string);
				}
			}
		}
		List<String> resultArr = new ArrayList<>(set);
		Rmap.put("only",resultArr);//[手机, 空调, 冰箱, 电视]
		Rmap.put("Repeat",arr);//[电视, 手机]
		return Rmap;
	}


	/**
	 * 获取 筛选不重复的某列值 和 重复的
	 * @param list
	 * @param lie
	 * @return
	 */
	public static Map<String, Object> getNotRepeating(List<Map<String, Object>> list,String lie) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> Rlist = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> Repeatlist = new ArrayList<Map<String, Object>>();

		for (int i = 0; i <list.size() ; i++) {
			Map<String, Object> obj = list.get(i);
			if(Rlist.size()>0){
				if(Is_existence(Rlist,lie,obj.get(lie).toString())){
					Repeatlist.add(obj);
				}else{
					Rlist.add(obj);
				}
			}else{
				Rlist.add(obj);
			}

		}
		map.put("Rlist",Rlist);
		map.put("Repeatlist",Repeatlist);
		return map;
	}




	/**
	 * 获取 筛选不重复的某列值 和 重复的
	 * @param list
	 * @param lie
	 * @return
	 */
	public static Map<String, Object> getNotRepeating(List<Map<String, Object>> list,List<String> cList,String lie) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> Rlist = new ArrayList<Map<String, Object>>();//存在
		List<Map<String, Object>> Repeatlist = new ArrayList<Map<String, Object>>();//不存在

		for (int i = 0; i <list.size() ; i++) {
			Map<String, Object> obj = list.get(i);
			if(Is_existenceStr(cList,obj.get(lie).toString())){
				Rlist.add(obj);
			}else{
				Repeatlist.add(obj);
			}
		}
		map.put("Rlist",Rlist);//【已匹对的】
		map.put("Repeatlist",Repeatlist);//【不存在的】
		return map;
	}



	/**
	 * 获取 筛选不重复的某列值 和 重复的
	 * @param list
	 * @return
	 */
	public static Map<String, Object> getNotRepeating(List<String> list,List<String> cList) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> Rlist = new ArrayList<String>();//存在
		List<String> Repeatlist = new ArrayList<String>();//不存在

		for (int i = 0; i <list.size() ; i++) {
			String str = list.get(i);
			if(Is_existenceStr(cList,str)){
				Rlist.add(str);
			}else{
				Repeatlist.add(str);
			}
		}
		map.put("Rlist",Rlist);//【已匹对的】
		map.put("Repeatlist",Repeatlist);//【不存在的】
		return map;
	}


	/**
	 * 判断 val 是否在 StrArr
	 * @param StrArr
	 * @param val
	 * @return
	 */
	public static boolean Val_Is_Arr (List<String> StrArr,String val){
		boolean bool = false;
		for (int i = 0; i < StrArr.size(); i++) {
			if (val.equals(StrArr.get(i))){
				bool = true;
				break;
			}
		}
		return bool;
	}




	
	
}
