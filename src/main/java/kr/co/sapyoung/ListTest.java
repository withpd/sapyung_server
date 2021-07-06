package kr.co.sapyoung;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListTest {
	
	static int[] list1 = new int[3];
	static ArrayList<Integer> list2 = new ArrayList<Integer>();
	
	static Map<String, Integer> list3 = new HashMap<String, Integer>(); 

	public static void main(String[] args) {
		
		list1[0] = 1;
		list1[1] = 2;
		list1[2] = 3;
		
		list2.add(4);
		list2.add(5);
		list2.add(6);
		
		list2.get(0);	// 4
		list2.get(1);	// 5
		list2.get(2);	// 6
		
		list3.put("key1", 7);
		list3.put("key2", 8);
		list3.put("key3", 9);
	}
}


