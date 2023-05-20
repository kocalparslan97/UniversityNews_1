package readNews;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.InputSource;

import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndLinkImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class ReadAtomFeed {

	public static ArrayList<String> all_uni = new ArrayList<String>();
	public static ArrayList<String> milliyetkurum_list = new ArrayList<String>();
	public static ArrayList<String> birgunkurum_list = new ArrayList<String>();
	public static ArrayList<String> ntvkurum_list = new ArrayList<String>();
	public static ArrayList<String> inthaberkurum_list = new ArrayList<String>();
	public static ArrayList<String> haberturkkurum_list = new ArrayList<String>();
	public static ArrayList<String> sabahkurum_list = new ArrayList<String>();

	public static HashMap<String, List<String>> birgunMap = new HashMap<String, List<String>>();
	public static HashMap<String, List<String>> intHaberMap = new HashMap<String, List<String>>();
	public static HashMap<String, List<String>> milliyetMap = new HashMap<String, List<String>>();
	public static HashMap<String, List<String>> ntvMap = new HashMap<String, List<String>>();
	public static HashMap<String, List<String>> haberturkMap = new HashMap<String, List<String>>();
	public static HashMap<String, List<String>> sabahMap = new HashMap<String, List<String>>();
	public static HashMap<String, List<String>> ulusalKanalMap = new HashMap<String, List<String>>();

	public static List<String> birgunList = new ArrayList<String>();
	public static List<String> intHaberList = new ArrayList<String>();
	public static List<String> milliyetList = new ArrayList<String>();
	public static List<String> ntvList = new ArrayList<String>();
	public static List<String> haberturkList = new ArrayList<String>();
	public static List<String> sabahList = new ArrayList<String>();
	public static List<String> ulusalKanalList = new ArrayList<String>();

	public MysqlCon mysc = new MysqlCon();

	public static SyndFeed getSyndFeedForUrl(String url) throws Exception {

		SyndFeed feed = null;
		InputStream is = null;

		try {

			URLConnection openConnection = new URL(url).openConnection();
			is = new URL(url).openConnection().getInputStream();
			if ("gzip".equals(openConnection.getContentEncoding())) {
				is = new GZIPInputStream(is);
			}
			InputSource source = new InputSource(is);
			SyndFeedInput input = new SyndFeedInput();
			feed = input.build(source);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
		}

		return feed;
	}

	public static ArrayList<String> getAllUni() {
		
		final String NAME = "C:\\Users\\ALPARSLAN\\Desktop\\deneme.xlsx";
		ArrayList<String> strArray = new ArrayList<String>();
		try {
			FileInputStream file = new FileInputStream(new File(NAME));
			Workbook workbook = new XSSFWorkbook(file);
			DataFormatter dataFormatter = new DataFormatter();
			Iterator<Sheet> sheets = workbook.sheetIterator();

			while (sheets.hasNext()) {
				Sheet sh = sheets.next();
				Iterator<Row> iterator = sh.iterator();

				while (iterator.hasNext()) {
					Row row = iterator.next();
					Iterator<Cell> cellIterator = row.iterator();

					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						if (cell.getCellType() == CellType.STRING) {
							strArray.add(cellValue);
						}
					}
				}
			}

			workbook.close();

		} catch (Exception e) {

		}
		return strArray;
	}

	// get only rows where cell values contain search string
	public static List<Row> getRows(Sheet sheet, DataFormatter formatter, FormulaEvaluator evaluator,
			String searchValue) {
		List<Row> result = new ArrayList<Row>();
		String cellValue = "";
		for (Row row : sheet) {
			for (Cell cell : row) {
				cellValue = formatter.formatCellValue(cell, evaluator);
				if (cellValue.contains(searchValue)) {
					result.add(row);
					break;
				}
			}
		}
		return result;
	}

	public static void ReadInvoices() {
		
		final String NAME = "C:\\Users\\ALPARSLAN\\Desktop\\deneme.xlsx";
		try {
			FileInputStream file = new FileInputStream(new File(NAME));
			Workbook workbook = new XSSFWorkbook(file);
			DataFormatter dataFormatter = new DataFormatter();
			Iterator<Sheet> sheets = workbook.sheetIterator();

			

			while (sheets.hasNext()) {
				Sheet sh = sheets.next();
				System.out.println("Sheet name is : " + sh.getSheetName());
				System.out.println("-------");
				Iterator<Row> iterator = sh.iterator();

				while (iterator.hasNext()) {
					Row row = iterator.next();
					Iterator<Cell> cellIterator = row.iterator();

					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						
						System.out.println(cellValue + "");
						all_uni.add(cellValue);
					}
					
				}
				System.out.println("----------ArrayList Content-----------");
				for (int i = 0; i < all_uni.size(); i++) {
					System.out.println(all_uni.get(i));
				}
			}

			workbook.close();
		} catch (Exception e) {

		}
	}

	public static void convertDateToString() {
		LocalDateTime myDateObj = LocalDateTime.of(2021, 8, 27, 16, 20);

		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = myDateObj.format(myFormatObj);

		System.out.println("After Formatting :" + formattedDate);
	}

	public static String convertDateToString1(String newsDate) throws ParseException {
		
		String dateInString = "07/06-2013";
		Date date = DateUtils.parseDate(dateInString, new String[] { "yyyy-MM-dd HH:mm:ss", "dd/MM-yyyy" });
		return "";
		
	}

	public static void main(String[] args) throws IllegalArgumentException, FeedException, IOException, ParseException {
		/*
		 * haber kanalları veri çekerken :
		 * https://www.ntv.com.tr/turkiye.rss (Tüm verileri alabilyoruz)
		 * https://www.milliyet.com.tr/rss/rssnew/dunyarss.xml (Tüm verilerini
		 * alabiliyoruz) https://www.internethaber.com/rss (GuId için linki parse edip
		 * sondaki sayısal degeri al) https://www.birgun.net/xml/rss.xml(GuId ile Link
		 * değerleri aynı geliyor) (Bu sorunu çözdük ve Tüm verileri alabiliyoruz)
		 * https://www.haberturk.com/rss(Tüm verileri alabiliyoruz)
		 * https://www.ulusal.com.tr/rss/?kategori=gundem
		 * 
		 * birgun haber -> temp , temp1 , temp2 milliyet haber -> temp3, temp4 , temp5
		 * ntv haber -> temp6, temp7 , temp8 internet haber -> temp9, temp10 , temp11
		 * haberturk haber -> temp12, temp13,temp14 sabah haber -> temp15, temp16,
		 * temp17
		 * 
		 */
		
		System.out.println("Deneme : " + convertDateToString1("Mon, 24 Sep 2021 15:24:57 Z"));

		System.out.println("---Birgun Haber---");
		SyndFeed feed1;
		try {
			feed1 = getSyndFeedForUrl("https://www.birgun.net/xml/rss.xml");
			List res1 = feed1.getEntries();
			for (Object o : res1) {
				BirgunNewsFeed birgunnewsf = new BirgunNewsFeed();

				String guId1 = ((SyndEntryImpl) o).getUri().substring(0);
				String title1 = ((SyndEntryImpl) o).getTitle();
				String desc1 = ((SyndEntryImpl) o).getDescription().getValue().replaceAll("<p>", "")
						.replaceAll("<strong>", "").replaceAll("</strong>", "").replaceAll("</p>", " ")
						.replaceAll("<img src=", " ").replaceAll("/>", "");
				String link1 = ((SyndEntryImpl) o).getLink();

				Date pubDate1 = ((SyndEntryImpl) o).getPublishedDate();
				String strTitle1 = StringUtils.substring(title1, 0);
				String strDesc1 = StringUtils.substring(desc1, 0);
				String[] strTitlePar1 = strTitle1.split(" ");
				String[] strDescPar1 = strDesc1.split(" ");

				birgunnewsf.setGuId(guId1);
				birgunnewsf.setTitle(title1);
				birgunnewsf.setContent(desc1);
				birgunnewsf.setLink(link1);
				birgunnewsf.setPubDate(pubDate1);

				all_uni = getAllUni();

				

				
				System.out.println("---------------------------------------------");
				System.out.println("GuId :" + birgunnewsf.getGuId());
				System.out.println("Title :" + birgunnewsf.getTitle()); // ((SyndEntryImpl) o).getTitle()
				System.out.println("Desc :" + birgunnewsf.getContent());
				System.out.println("Link :" + birgunnewsf.getLink());
				System.out.println("PubDate :" + birgunnewsf.getPubDate());
				System.out.println("---------------------------------------------");

				String newstring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(birgunnewsf.getPubDate());
				System.out.println("Edited Date Format : " + newstring);

				String newstring_tarihi = new SimpleDateFormat("dd/MM/yyyy").format(birgunnewsf.getPubDate());
				System.out.println("Edited Date Format : " + newstring_tarihi);

				for (int u = 0; u < all_uni.size(); u++) {
					String temp = all_uni.get(u);
					boolean isTitleContain1 = StringUtils.containsIgnoreCase(birgunnewsf.getTitle(), temp);
					boolean isDescContain1 = StringUtils.containsIgnoreCase(birgunnewsf.getContent(), temp);
					if (isTitleContain1 == true || isDescContain1 == true) {
						if (!birgunList.contains(temp)) {
							birgunkurum_list.add(temp);
							birgunList.add(temp);
							birgunMap.put("birgun", birgunList);
							System.out.println("---SQL Insert---");
							Random rand = new Random();
							int randIds = u + rand.nextInt(100);

							MysqlCon.BirgunNewInsertFunc(birgunnewsf.getGuId(), "birgun", birgunnewsf.getTitle(),
									birgunnewsf.getContent(), birgunnewsf.getLink(), temp, "birgun haber",
									newstring_tarihi, newstring);

						}


						// Bu temp1 değişkeninde "birgunkurumList" in içeriği yer aliyor.
						String temp1 = temp + ",";
						// temp1.substring(temp1.length() - 1, temp1.length())
						System.out.println("Haber içeriğindeki Kurumlar : " + temp1.replace(",", ""));
						System.out.println("Önceki Hali ->" + temp1);

						System.out.println("Kurum List :");
						String sonEleman = temp1.substring(temp1.length() - 1, temp1.length());
						String temp2 = temp1.substring(0, temp1.lastIndexOf(sonEleman));
						if (temp1.contains(sonEleman)) {
							System.out.println("->" + temp2);
						}

						System.out.println("---------------------------------------------");

					} else {
						// System.out.println("Eşleşmedi..");
					}
				}

			}

			System.out.println("-----------------------------------");
			System.out.println("--Birgun Haber--");
			Set s1 = birgunMap.entrySet();
			Iterator i1 = s1.iterator();
			while (i1.hasNext()) {
				Map.Entry item = (Map.Entry) i1.next();
				System.out.println(item.getKey() + " -> " + item.getValue());
			}
			System.out.println("-----------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("---NTV Haber---"); // NTV İÇİNDE JSOUP PARSE KULLAN

		try {
			URL feedUrl = new URL("https://www.ntv.com.tr/turkiye.rss");
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed3 = input.build(new XmlReader(feedUrl));

			System.out.println("Feed Title : " + feed3.getTitle());

			// Get the entry items...
			for (SyndEntry entry : (List<SyndEntry>) feed3.getEntries()) {
				NtvNewsFeed ntvnewsf = new NtvNewsFeed();

				ntvnewsf.setTitle(entry.getTitle().replaceAll("'", ""));
				ntvnewsf.setGuId(entry.getUri());
				ntvnewsf.setPubDate(entry.getUpdatedDate());
				System.out.println("Title : " + ntvnewsf.getTitle());
				System.out.println("GuID :" + ntvnewsf.getGuId());
				System.out.println("pubDate : " + ntvnewsf.getPubDate());

				String newstring = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(ntvnewsf.getPubDate());
				System.out.println("Edited Date Format : " + newstring);

				String newstring_tarihi = new SimpleDateFormat("dd/MM/yyyy").format(ntvnewsf.getPubDate());
				System.out.println("Edited Date Format : " + newstring_tarihi);

				// Get the Links
				for (SyndLinkImpl link : (List<SyndLinkImpl>) entry.getLinks()) {
					ntvnewsf.setLink(entry.getUri());// link.getHref()
					System.out.println("Link : " + ntvnewsf.getLink());
				}

				// Get the Contents
				for (SyndContentImpl content : (List<SyndContentImpl>) entry.getContents()) {
					ntvnewsf.setContent(content.getValue().replaceAll("<img src=", "").replaceAll("<p>", " ")
							.replaceAll("<br>", "").replaceAll("</br>", "").replaceAll("/>", ""));
					System.out.println("Content : " + ntvnewsf.getContent());

					System.out.println("------------------------------------------------------------------");
					String desc3 = ntvnewsf.getContent();

					String strDesc3 = StringUtils.substring(desc3, 0);
					String[] strDescPar3 = strDesc3.split(" ");

					for (int u = 0; u < all_uni.size(); u++) {
						String temp6 = all_uni.get(u);
						boolean isDescContain3 = StringUtils.containsIgnoreCase(strDesc3, temp6);
						if (isDescContain3 == true) {
							if (!ntvList.contains(temp6)) {
								ntvkurum_list.add(temp6);
								ntvList.add(temp6);
								ntvMap.put("ntv", ntvList);

								System.out.println("---SQL Insert---");
								int abc = 3;
								MysqlCon.NtvNewInsertFunc(ntvnewsf.getGuId(), "NTV", ntvnewsf.getTitle(),
										ntvnewsf.getContent(), ntvnewsf.getLink(), temp6, "ntv haber", newstring_tarihi,
										newstring);
								abc++;
							}
							// Bu temp1 değişkeninde "birgunkurumList" in içeriği yer aliyor.
							String temp7 = temp6 + ",";
							// temp1.substring(temp1.length() - 1, temp1.length())
							System.out.println("Haber içeriğindeki Kurumlar : " + temp7.replace(",", ""));
							System.out.println("Önceki Hali ->" + temp7);

							System.out.println("Kurum List :");
							String sonEleman = temp7.substring(temp7.length() - 1, temp7.length());
							if (temp7.contains(sonEleman)) {
								String temp8 = temp7.substring(0, temp7.lastIndexOf(sonEleman));
								System.out.println("->" + temp8);
							}
						} else {
							// System.out.println("Eşleşmedi..");
						}
					}
				}

				System.out.println("------------------------------------------------------------------");
				String title3 = entry.getTitle();

				String strTitle3 = StringUtils.substring(title3, 0);

				String[] strTitlePar3 = strTitle3.split(" ");

				all_uni = getAllUni();
				for (int u = 0; u < all_uni.size(); u++) {
					String temp6 = all_uni.get(u);
					boolean isTitleContain3 = StringUtils.containsIgnoreCase(strTitle3, temp6);
					if (isTitleContain3 == true) {
						if (!ntvList.contains(temp6)) {
							ntvkurum_list.add(temp6);
							ntvList.add(temp6);
							ntvMap.put("ntv", ntvList);

							System.out.println("---SQL Insert---");
							int abc = 3;
							MysqlCon.NtvNewInsertFunc(ntvnewsf.getGuId(), "NTV", ntvnewsf.getTitle(),
									ntvnewsf.getContent(), ntvnewsf.getLink(), temp6, "ntv haber", newstring_tarihi,
									newstring);
							abc++;

						}

						// Bu temp1 değişkeninde "birgunkurumList" in içeriği yer aliyor.
						String temp7 = temp6 + ",";
						// temp1.substring(temp1.length() - 1, temp1.length())
						System.out.println("Haber içeriğindeki Kurumlar : " + temp7.replace(",", ""));
						System.out.println("Önceki Hali ->" + temp7);

						System.out.println("Kurum List :");
						String sonEleman = temp7.substring(temp7.length() - 1, temp7.length());
						if (temp7.contains(sonEleman)) {
							String temp8 = temp7.substring(0, temp7.lastIndexOf(sonEleman));
							System.out.println("->" + temp8);
						}
					} else {
						// System.out.println("Eşleşmedi..");
					}
				}
			}

			System.out.println("-----------------------------------");
			System.out.println("--NTV Haber--");
			Set s3 = ntvMap.entrySet();
			Iterator i3 = s3.iterator();
			while (i3.hasNext()) {
				Map.Entry item = (Map.Entry) i3.next();
				System.out.println(item.getKey() + " -> " + item.getValue());
			}
			System.out.println("-----------------------------------");
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}

		System.out.println("---Milliyet Haber---");
		Document doc2 = Jsoup.connect("https://www.milliyet.com.tr/rss/rssnew/dunyarss.xml").get();
		Elements items2 = doc2.getElementsByTag("item");

		for (Element item2 : items2) {
			MilliyetNewsFeed milliyetnewf = new MilliyetNewsFeed();

			Document parseDecs2 = Jsoup.parse(item2.getElementsByTag("description").text().trim());
			String title2 = item2.getElementsByTag("title").text().trim();
			String desc2 = parseDecs2.getElementsByTag("h4").text().trim();

			String strTitle2 = StringUtils.substring(title2, 0);
			String strDesc2 = StringUtils.substring(desc2, 0);
			String Desc2_1 = parseDecs2.text().trim();
			String[] strTitlePar2 = strTitle2.split(" ");
			String[] strDescPar2 = strDesc2.split(" ");
			String pubDate2 = item2.getElementsByTag("pubDate").text();
			String[] pubDatePar2;

			milliyetnewf.setGuId(item2.getElementsByTag("guid").text().trim());
			milliyetnewf.setTitle(title2);
			milliyetnewf.setContent(parseDecs2.text().trim());
			milliyetnewf.setLink(item2.childNode(4).attr("href"));
			milliyetnewf.setPubDate(pubDate2);
			all_uni = getAllUni();

			System.out.println("GuId :" + milliyetnewf.getGuId());
			System.out.println("Title :" + milliyetnewf.getTitle());
			System.out.println("Desc : " + milliyetnewf.getContent());
			System.out.println("Link : " + milliyetnewf.getLink());
			System.out.println("PubDate : " + milliyetnewf.getPubDate());
			System.out.println("------------------------------");
			
			for (int u = 0; u < all_uni.size(); u++) {
				String temp3 = all_uni.get(u);
				boolean isTitleContain2 = StringUtils.containsIgnoreCase(milliyetnewf.getTitle(), temp3);
				boolean isDescContain2 = StringUtils.containsIgnoreCase(milliyetnewf.getContent(), temp3);
				if (isTitleContain2 == true || isDescContain2 == true) {
					if (!milliyetList.contains(temp3)) {
						milliyetkurum_list.add(temp3);
						milliyetList.add(temp3);
						milliyetMap.put("milliyet", milliyetList);

						System.out.println("---SQL Insert---");

						MysqlCon.MilliyetNewInsertFunc(milliyetnewf.getGuId(), "milliyet", milliyetnewf.getTitle(),
								milliyetnewf.getContent(), milliyetnewf.getLink(), temp3, "milliyet haber",
								milliyetnewf.getPubDate(), milliyetnewf.getPubDate());

					}

					// Bu temp1 değişkeninde "birgunkurumList" in içeriği yer aliyor.
					String temp4 = temp3 + ",";
					// temp1.substring(temp1.length() - 1, temp1.length())
					System.out.println("Haber içeriğindeki Kurumlar : " + temp4.replace(",", ""));
					System.out.println("Önceki Hali ->" + temp4);

					System.out.println("Kurum List :");
					String sonEleman = temp4.substring(temp4.length() - 1, temp4.length());
					if (temp4.contains(sonEleman)) {
						String temp5 = temp4.substring(0, temp4.lastIndexOf(sonEleman));
						System.out.println("->" + temp5);
					}
					System.out.println("---------------------------------------------");

				} else {
					// System.out.println("Eşleşmedi..");
				}
			}
		}
		System.out.println("-----------------------------------");
		System.out.println("--Milliyet Haber--");
		Set s2 = milliyetMap.entrySet();
		Iterator i2 = s2.iterator();
		while (i2.hasNext()) {
			Map.Entry item = (Map.Entry) i2.next();
			System.out.println(item.getKey() + " -> " + item.getValue());
		}
		System.out.println("-----------------------------------");

		System.out.println("---İnternet Haber---");
		Document doc4 = Jsoup.connect("https://www.internethaber.com/rss").get();
		Elements items4 = doc4.getElementsByTag("item");

		for (Element item4 : items4) {
			InternetHNewsFeed internetnewf = new InternetHNewsFeed();

			Document parseDecs4 = Jsoup.parse(item4.getElementsByTag("description").text().trim());
			String title4 = item4.getElementsByTag("title").text();
			String desc4 = parseDecs4.getElementsByTag("h4").text();
			String guId4 = item4.getElementsByTag("link").text();
			String pubDate4 = item4.getElementsByTag("pubDate").text();

			String strTitle4 = StringUtils.substring(title4, 0);
			String strDesc4 = StringUtils.substring(desc4, 0);
			String[] strTitlePar4 = strTitle4.split(" ");
			String[] strDescPar4 = strDesc4.split(" ");

			internetnewf.setGuId(guId4);
			internetnewf.setTitle(title4);
			internetnewf.setContent(desc4);
			internetnewf.setLink(item4.getElementsByTag("link").text());
			internetnewf.setPubDate(pubDate4);

			all_uni = getAllUni();
			
			System.out.println("GuId :" + internetnewf.getGuId());
			System.out.println("Title :" + internetnewf.getTitle());
			System.out.println("Desc : " + internetnewf.getContent());
			System.out.println("Link : " + internetnewf.getLink());
			System.out.println("PubDate : " + internetnewf.getPubDate());
			System.out.println("------------------------------");

			for (int u = 0; u < all_uni.size(); u++) {
				String temp9 = all_uni.get(u);
				boolean isTitleContain4 = StringUtils.containsIgnoreCase(internetnewf.getTitle(), temp9);
				boolean isDescContain4 = StringUtils.containsIgnoreCase(internetnewf.getContent(), temp9);
				// System.out.println(isTitleContain4 + " | " + isDescContain4);
				if (isTitleContain4 == true || isDescContain4 == true) {
					if (!intHaberList.contains(temp9)) {
						inthaberkurum_list.add(temp9);
						intHaberList.add(temp9);
						intHaberMap.put("internethaber", intHaberList);

						System.out.println("---SQL Insert---");

						MysqlCon.InternetNewInsertFunc(internetnewf.getGuId(), "internethaber", internetnewf.getTitle(),
								internetnewf.getContent(), internetnewf.getLink(), temp9, "Internet haber",
								internetnewf.getPubDate(), internetnewf.getPubDate());
					}

					// Bu temp1 değişkeninde "birgunkurumList" in içeriği yer aliyor.
					String temp10 = temp9 + ",";
					// temp1.substring(temp1.length() - 1, temp1.length())
					System.out.println("Haber içeriğindeki Kurumlar : " + temp10.replace(",", ""));
					System.out.println("Önceki Hali ->" + temp10);

					System.out.println("Kurum List :");
					String sonEleman = temp10.substring(temp10.length() - 1, temp10.length());
					if (temp10.contains(sonEleman)) {
						String temp11 = temp10.substring(0, temp10.lastIndexOf(sonEleman));
						System.out.println("->" + temp11);
					}
				} else {
					// System.out.println("Eşleşmedi..");
				}
			}
		}
		System.out.println("-----------------------------------");
		System.out.println("--İnternet Haber--");
		Set s4 = intHaberMap.entrySet();
		Iterator i4 = s4.iterator();
		while (i4.hasNext()) {
			Map.Entry item = (Map.Entry) i4.next();
			System.out.println(item.getKey() + " -> " + item.getValue());
		}
		System.out.println("-----------------------------------");

		System.out.println("---HaberTürk Haber---");
		// "https://www.haberturk.com/rss"
		Document doc5 = Jsoup.connect("https://www.haberturk.com/rss").get();
		Elements items5 = doc5.getElementsByTag("item");

		for (Element item5 : items5) {
			HaberTurkNewsFeed haberturknewf = new HaberTurkNewsFeed();

			Document parseDecs5 = Jsoup.parse(item5.getElementsByTag("description").text().trim());
			String guId5 = item5.getElementsByTag("guid").text().trim();
			String title5 = item5.getElementsByTag("title").text().trim();
			String desc5 = parseDecs5.getElementsByTag("img").text();

			String strTitle5 = StringUtils.substring(title5, 0);
			String strDesc5 = StringUtils.substring(desc5, 0);
			String[] strTitlePar5 = strTitle5.split(" ");
			String[] strDescPar5 = strDesc5.split(" ");
			String descNew = parseDecs5.text().trim();

			haberturknewf.setGuId(guId5);
			haberturknewf.setTitle(item5.getElementsByTag("title").text());
			haberturknewf.setContent(parseDecs5.text().trim());
			haberturknewf.setLink(item5.getElementsByTag("link").text());
			haberturknewf.setPubDate(item5.getElementsByTag("pubDate").text());

			all_uni = getAllUni();

			System.out.println("GuId :" + haberturknewf.getGuId());
			System.out.println("Title :" + haberturknewf.getTitle());
			System.out.println("Desc : " + haberturknewf.getContent());
			System.out.println("Link : " + haberturknewf.getLink());
			System.out.println("PubDate : " + haberturknewf.getPubDate());
			System.out.println("------------------------------");

			for (int u = 0; u < all_uni.size(); u++) {
				String temp12 = all_uni.get(u);
				boolean isTitleContain5 = StringUtils.containsIgnoreCase(haberturknewf.getTitle(), temp12);
				boolean isDescContain5 = StringUtils.containsIgnoreCase(haberturknewf.getContent(), temp12);
				if (isTitleContain5 == true || isDescContain5 == true) {
					if (!haberturkList.contains(temp12)) {
						haberturkkurum_list.add(temp12);
						haberturkList.add(temp12);
						haberturkMap.put("haberturk", haberturkList);

						System.out.println("---SQL Insert---");

						MysqlCon.HaberTurkNewInsertFunc(haberturknewf.getGuId(), "haberturk", haberturknewf.getTitle(),
								haberturknewf.getContent(), haberturknewf.getLink(), temp12, "HaberTurk haber",
								haberturknewf.getPubDate(), haberturknewf.getPubDate());
					}

					// Bu temp1 değişkeninde "birgunkurumList" in içeriği yer aliyor.
					String temp13 = temp12 + ",";
					// temp1.substring(temp1.length() - 1, temp1.length())
					System.out.println("Haber içeriğindeki Kurumlar : " + temp13.replace(",", ""));
					System.out.println("Önceki Hali ->" + temp13);

					System.out.println("Kurum List :");
					String sonEleman = temp13.substring(temp13.length() - 1, temp13.length());
					if (temp13.contains(sonEleman)) {
						String temp14 = temp13.substring(0, temp13.lastIndexOf(sonEleman));
						System.out.println("->" + temp14);
					}
				} else {
					// System.out.println("Eşleşmedi..");
				}
			}
			System.out.println("------------------------------");

		}
		System.out.println("-----------------------------------");
		System.out.println("--Haber Türk Haber--");
		Set s5 = haberturkMap.entrySet();
		Iterator i5 = s5.iterator();
		while (i5.hasNext()) {
			Map.Entry item = (Map.Entry) i5.next();
			System.out.println(item.getKey() + " -> " + item.getValue());
		}
		System.out.println("-----------------------------------");

		System.out.println("-----------------------------------");
		System.out.println("---Sabah Haber---");
		// "https://www.sabah.com.tr/rss/anasayfa.xml"
		Document doc6 = Jsoup.connect("https://www.sabah.com.tr/rss/anasayfa.xml").get();
		Elements items6 = doc6.getElementsByTag("item");

		for (Element item6 : items6) {
			SabahNewsFeed sabahnewf = new SabahNewsFeed();

			String parseDecs6 = Jsoup.parse(item6.getElementsByTag("description").text().trim()).text().trim();
			String title6 = item6.getElementsByTag("title").text().trim();
			String desc6 = parseDecs6.trim();

			String strTitle6 = StringUtils.substring(title6, 0);
			String strDesc6 = StringUtils.substring(desc6, 0);
			String[] strTitlePar6 = strTitle6.split(" ");
			String[] strDescPar4 = strDesc6.split(" ");

			sabahnewf.setGuId(item6.getElementsByTag("link").text());
			sabahnewf.setTitle(item6.getElementsByTag("title").text());
			sabahnewf.setContent(desc6);
			sabahnewf.setLink(item6.getElementsByTag("link").text());
			sabahnewf.setPubDate(item6.getElementsByTag("pubDate").text());

			all_uni = getAllUni();

			System.out.println("GuId :" + sabahnewf.getGuId());
			System.out.println("Title :" + sabahnewf.getTitle());
			System.out.println("Desc : " + sabahnewf.getContent());
			System.out.println("Link : " + sabahnewf.getLink());
			System.out.println("PubDate : " + sabahnewf.getPubDate());
			System.out.println("------------------------------");
			for (int u = 0; u < all_uni.size(); u++) {
				String temp15 = all_uni.get(u);
				boolean isTitleContain6 = StringUtils.containsIgnoreCase(sabahnewf.getTitle(), temp15);
				boolean isDescContain6 = StringUtils.containsIgnoreCase(sabahnewf.getContent(), temp15);
				if (isTitleContain6 == true || isDescContain6 == true) {
					if (!sabahList.contains(temp15)) {
						sabahkurum_list.add(temp15);
						sabahList.add(temp15);
						sabahMap.put("sabah", sabahList);

						System.out.println("---SQL Insert---");

						MysqlCon.SabahNewInsertFunc(sabahnewf.getGuId(), "sabah", sabahnewf.getTitle(),
								sabahnewf.getContent(), sabahnewf.getLink(), temp15, "Sabah Haber",
								sabahnewf.getPubDate(), sabahnewf.getPubDate());
					}

					// Bu temp1 değişkeninde "birgunkurumList" in içeriği yer aliyor.
					String temp16 = temp15 + ",";
					// temp1.substring(temp1.length() - 1, temp1.length())
					System.out.println("Haber içeriğindeki Kurumlar : " + temp16.replace(",", ""));
					System.out.println("Önceki Hali ->" + temp16);

					System.out.println("Kurum List :");
					String sonEleman = temp16.substring(temp16.length() - 1, temp16.length());
					if (temp16.contains(sonEleman)) {
						String temp17 = temp16.substring(0, temp16.lastIndexOf(sonEleman));
						System.out.println("->" + temp17);
					}
				} else {
					// System.out.println("Eşleşmedi..");
				}
			}
		}
		System.out.println("-----------------------------------");
		System.out.println("--Sabah Haber--");
		Set s6 = sabahMap.entrySet();
		Iterator i6 = s6.iterator();
		while (i6.hasNext()) {
			Map.Entry item = (Map.Entry) i6.next();
			System.out.println(item.getKey() + " -> " + item.getValue());
		}
		System.out.println("-----------------------------------");

	}
}
