package kr.co.sapyung.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.sapyung.vo.ProductVO;

public class JsonTest {
	public static void main(String[] args) throws IOException {
//		ProductVO vo = new ProductVO();
//		vo.setColor("111");
//		vo.setImgUrl("dfsdf");
//		vo.setIsSell("vv");
//		vo.setPrice("11");
//		vo.setSizes("aaaaaaaaa");
//		vo.setTime("2222222");
//		vo.setTitle("bbbbbbbb");
//		vo.setUrl("vvvvvvvvvvvvvvvvvvvvvvvv");
//		
//		ProductVO vo1 = new ProductVO();
//		vo1.setColor("111");
//		vo1.setImgUrl("dfsdf");
//		vo1.setIsSell("vv");
//		vo1.setPrice("11");
//		vo1.setSizes("aaaaaaaaa");
//		vo1.setTime("2222222");
//		vo1.setTitle("bbbbbbbb");
//		vo1.setUrl("vvvvvvvvvvvvvvvvvvvvvvvv");
//
//		List<ProductVO> list = new ArrayList<ProductVO>();
//		list.add(vo1);
//		list.add(vo);
		
		String json = "{\"message\":{\"@type\":\"response\",\"@service\":\"naverservice.nmt.proxy\",\"@version\":\"1.0.0\",\"result\":{\"srcLangType\":\"fr\",\"tarLangType\":\"ko\",\"translatedText\":\"ũ��\"}}}";
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode jsonNode = mapper.readTree(json);
		String msg = jsonNode.get("message").get("result").get("translatedText").asText();	
		System.out.println(msg);
//		System.out.println(json);
		
		
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//	    mapper.writeValue(out, list);
//
//	    final byte[] data = out.toByteArray();
//	    System.out.println(new String(data));
	}
}
