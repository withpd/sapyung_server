package kr.co.sapyung.brand.maje;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import kr.co.sapyung.Constants;
import kr.co.sapyung.db.DbDao;
import kr.co.sapyung.vo.ProductVO;

public class MajeSelectTask_bak extends Thread {

	private final String BRAND = "maje";
	
	private DbDao dao;

	public MajeSelectTask_bak(DbDao dao) {
		this.dao = dao;
	}

	public void run() {
		int start = 0;
		System.out.printf(Constants.LOG_START_FORMAT, this.getClass().getName(), "SELECTTASK", Constants.getDate(Constants.DATE_LOG_FORMAT), BRAND + " : start " + start);	

		for (int i = 0; i < 10; i++) {
			getMajeList(Constants.READ_COUNT, start);
			start += Constants.READ_COUNT;
		}
	}

	private int getMajeList(int size, int start) {

		String url = "https://fr.maje.com/fr/pret-a-porter/collection/tout-voir/?sz=" + size + "&start=" + start;
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
		for (int i = 4; i < prodcutElement.size(); i++) {
			ProductVO vo = new ProductVO();
			vo.setBrand(BRAND);
			Element e = prodcutElement.get(i);
			link = e.attr("href");

			try {
				doc = Jsoup.connect(link).get();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Pattern colorPattern = Pattern.compile("(^\\bselected \\b)");		

//			Elements colorElements = doc.getElementsByAttributeValue("class", "swatches Color");
			Elements colorElements = doc.getElementsByAttributeValueMatching("class", colorPattern);
			Elements sizeElements = doc.getElementsByAttributeValue("class", "swatches Size");
			Elements imgElements = doc.getElementsByAttributeValue("class", "zoomMain");

			colorElements = colorElements.select("a");
			sizeElements = sizeElements.select("a");
			imgElements = imgElements.select("img");

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
		
		int insertCnt = dao.insertList(retList);
		return insertCnt;
	}
}
