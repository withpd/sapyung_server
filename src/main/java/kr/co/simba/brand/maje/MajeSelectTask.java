package kr.co.simba.brand.maje;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import kr.co.simba.Constants;
import kr.co.simba.db.DbDao;
import kr.co.simba.impl.I_DataSelector;
import kr.co.simba.utils.RegularExUtils;
import kr.co.simba.vo.ProductVO;

public class MajeSelectTask extends I_DataSelector{

	private DbDao dao;

	public MajeSelectTask(DbDao dao) {
		this.dao = dao;
	}

	@Override
	public void run() {
		int offset = 0;
		System.out.printf(Constants.LOG_START_FORMAT, this.getClass().getName(), "SELECTTASK", Constants.getDate(Constants.DATE_LOG_FORMAT), getBrand() + " : offset " + offset);	

		for (int i = 0; i < 10; i++) {
			getList(Constants.READ_COUNT, offset);
			offset += Constants.READ_COUNT;
		}
	}

	@Override
	public int getList(int size, int offset) {
		String url = "https://fr.maje.com/fr/pret-a-porter/collection/tout-voir/?sz=" + size + "&start=" + offset;
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements prodcutElement = doc.getElementsByAttributeValue("class", "thumb-link");
//		JSONArray resultObj = new JSONArray();
		String link;
		List<ProductVO> retList = new ArrayList<ProductVO>(); 
		int idx = 0;
		for (int i = 4; i < prodcutElement.size(); i++) {
			ProductVO vo = new ProductVO();
			vo.setIdx(offset+idx);
			vo.setBrand(getBrand());
			Element e = prodcutElement.get(i);
			link = e.attr("href");

			try {
				doc = Jsoup.connect(link).get();
			} catch (Exception e1) {
				System.out.printf(Constants.LOG_END_FORMAT, this.getClass().getName(), "getList", Constants.getDate(Constants.DATE_LOG_FORMAT), "Exception : " + url, -1);
				continue;
			}
			Pattern colorPattern = Pattern.compile("(^\\bselected \\b)");	

//			Elements colorElements = doc.getElementsByAttributeValue("class", "swatches Color");
			Elements colorElements = doc.getElementsByAttributeValueMatching("class", colorPattern);
			Elements sizeElements = doc.getElementsByAttributeValue("class", "swatches Size");
			Elements imgElements = doc.getElementsByAttributeValue("class", "zoomMain");
			Elements cateElements = doc.getElementsByAttributeValue("class", "breadcrumb");

			colorElements = colorElements.select("a");
			sizeElements = sizeElements.select("a");
			imgElements = imgElements.select("img");
			cateElements = cateElements.select("li");

			// Category
			try {
				vo.setCate(cateElements.get(0).text().toString());
			} catch(Exception e1) {
			}
			
			// Img Url
//			JSONObject imgObj = new JSONObject();
//			JSONArray imgList = new JSONArray();
			String imgUrl = "";
			for (Element elem : imgElements) {
				imgUrl += elem.attr("src") + Constants.DELIM;
			}
			vo.setImgUrl(imgUrl);
			
			// Color
			String color = "";
			for (Element elem : colorElements) {
				color += elem.attr("title");
			}
			vo.setColor(color);
			
			// Size
			String sizes = "";
			String isSell = "n";
			for (Element elem : sizeElements) {
				if(elem.attr("title").contains("indisponible")) {
					sizes += elem.getElementsByAttributeValue("class", "defaultSize").text()+"_x" + Constants.DELIM;
				} else {
					sizes += elem.getElementsByAttributeValue("class", "defaultSize").text() + Constants.DELIM;
					isSell = "y";	
				}
			}
			if(sizes.length() == 0) continue;
			sizes = sizes.substring(0, sizes.length()-1);
			vo.setSizes(sizes);

			// Price
			String price = doc.getElementsByAttributeValue("itemprop", "price").get(0).text();
			price = RegularExUtils.getNumber(price);
			vo.setPrice(price);

			// Title
			String title = doc.getElementsByAttributeValue("property", "og:title").get(0).attr("content");
			vo.setTitle(title);

			// url
			vo.setUrl(link);

			vo.setTime(Constants.getDate(Constants.DATE_DB_FORMAT));

			vo.setIsSell(isSell);
			
			retList.add(vo);
			idx++;
		}
		
		int insertCnt = dao.insertList(retList);
		return insertCnt;
	}

	@Override
	public String getBrand() {
		return "maje";
	}
	
	public static void main(String[] args) {
		int size = 0;
		int offset = 0;
		String url = "https://fr.maje.com/fr/pret-a-porter/collection/tout-voir/?sz=" + size + "&start=" + offset;
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements prodcutElement = doc.getElementsByAttributeValue("class", "thumb-link");
//		JSONArray resultObj = new JSONArray();
		String link;
		List<ProductVO> retList = new ArrayList<ProductVO>(); 
		int idx = 0;
		for (int i = 4; i < prodcutElement.size(); i++) {
			ProductVO vo = new ProductVO();
			vo.setIdx(offset+idx++);
			vo.setBrand("maje");
			Element e = prodcutElement.get(i);
			link = e.attr("href");

			try {
				if(!link.startsWith("/")) doc = Jsoup.connect(link).get();
			} catch (IOException e1) {
				e1.printStackTrace();
				break;
			}
			Pattern colorPattern = Pattern.compile("(^\\bselected \\b)");		

//			Elements colorElements = doc.getElementsByAttributeValue("class", "swatches Color");
			Elements colorElements = doc.getElementsByAttributeValueMatching("class", colorPattern);
			Elements sizeElements = doc.getElementsByAttributeValue("class", "swatches Size");
			Elements imgElements = doc.getElementsByAttributeValue("class", "zoomMain");
			Elements cateElements = doc.getElementsByAttributeValue("class", "breadcrumb");
			

			colorElements = colorElements.select("a");
			sizeElements = sizeElements.select("a");
			imgElements = imgElements.select("img");
			cateElements = cateElements.select("li");

			System.out.println("카테고리 : " + cateElements.get(0).text().toString());
			
			// Img Url
//			JSONObject imgObj = new JSONObject();
//			JSONArray imgList = new JSONArray();
			String imgUrl = "";
			for (Element elem : imgElements) {
				imgUrl += elem.attr("src") + Constants.DELIM;
			}
			vo.setImgUrl(imgUrl);
			
			// Color
			String color = "";
			for (Element elem : colorElements) {
				color += elem.attr("title");
			}
			vo.setColor(color);
			
			// Size
			String sizes = "";
			String isSell = "n";
			for (Element elem : sizeElements) {
				if(elem.attr("title").contains("indisponible")) {
					sizes += elem.getElementsByAttributeValue("class", "defaultSize").text()+"_x" + Constants.DELIM;
				} else {
					sizes += elem.getElementsByAttributeValue("class", "defaultSize").text() + Constants.DELIM;
					isSell = "y";	
				}
			}
			sizes = sizes.substring(0, sizes.length()-1);
			vo.setSizes(sizes);

			// Price
			String price = doc.getElementsByAttributeValue("itemprop", "price").get(0).text();
			vo.setPrice(price);

			// Title
			String title = doc.getElementsByAttributeValue("property", "og:title").get(0).attr("content");
			vo.setTitle(title);

			// url
			vo.setUrl(link);

			vo.setTime(Constants.getDate(Constants.DATE_DB_FORMAT));

			vo.setIsSell(isSell);
			
			retList.add(vo);
		}
	}
}
