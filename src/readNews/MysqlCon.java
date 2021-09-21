package readNews;

import java.sql.Connection;
import java.sql.DriverManager;

import com.mysql.jdbc.PreparedStatement;

public class MysqlCon {

	public static void sqlBirgunInsertFunc(int Ids, String haber_ids, String hash, String titles, String descs,
			String links, String kurum_lists, String yayin_yeri, String yayin_tarihi, String eklenme_tarihi) {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver is Loaded");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/unihaber_db?useUnicode=true&characterEncoding=utf-8", "root", "");
			System.out.println("Database has Connected");
			// INSERT INTO `birgunhaber`(`Id`, `haber_id`, `hash`, `baslik`, `ozet`, `link`,
			// `kurum_list`, `yayin_yeri`, `yayin_tarihi`, `eklenme_tarihi`) VALUES
			// ('[value-1]','[value-2]','[value-3]','[value-4]','[value-5]','[value-6]','[value-7]','[value-8]','[value-9]','[value-10]')
			// '" + 1 + "'
			/*
			 * "INSERT INTO `birgunhaber`(`Id`, `haber_id`, `hash`, `baslik`, `ozet`, `link`, `kurum_list`, `yayin_yeri`, `yayin_tarihi`, `eklenme_tarihi`) "
			 * + "VALUES ('" + 1 + "','" + 2 + "','" + 3 + "','" + 4 + "','" + 5 + "','" + 6
			 * + "','" + 7 + "','" + 8 + "','" + 9 + "','" + 10 + "')"
			 */
			String s = "INSERT INTO  birgunhaber " + "VALUES ('" + Ids + "','" + haber_ids + "','" + hash + "','"
					+ titles + "','" + descs + "','" + links + "','" + kurum_lists + "','" + yayin_yeri + "','"
					+ yayin_tarihi + "','" + eklenme_tarihi + "')";
			PreparedStatement st = (PreparedStatement) con.prepareStatement(s);
			System.out.println("Data has Inserted Successfully");
			st.execute();
			con.close();

		} catch (Exception ex) {

			ex.printStackTrace();

		}

	}

	public static void sqlMilliyetInsertFunc(int Ids, String haber_ids, String hash, String titles, String descs,
			String links, String kurum_lists, String yayin_yeri, String yayin_tarihi, String eklenme_tarihi) {
		try {

			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver is Loaded");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/unihaber_db?useUnicode=true&characterEncoding=utf-8", "root", "");
			System.out.println("Database has Connected");

			/*
			 * INSERT INTO `milliyethaber`(`Id`, `haber_id`, `hash`, `baslik`, `ozet`,
			 * `link`, `kurum_list`, `yayin_yeri`, `yayin_tarihi`, `eklenme_tarihi`) VALUES
			 * ('[value-1]','[value-2]','[value-3]','[value-4]','[value-5]','[value-6]','[
			 * value-7]','[value-8]','[value-9]','[value-10]')
			 */
			String s = "INSERT INTO  milliyethaber " + "VALUES ('" + Ids + "','" + haber_ids + "','" + hash + "','"
					+ titles + "','" + descs + "','" + links + "','" + kurum_lists + "','" + yayin_yeri + "','"
					+ yayin_tarihi + "','" + eklenme_tarihi + "')";
			PreparedStatement st = (PreparedStatement) con.prepareStatement(s);
			System.out.println("Data has Inserted Successfully");
			st.execute();
			con.close();

		} catch (Exception ex) {

			ex.printStackTrace();

		}

	}

	public static void MilliyetNewInsertFunc(String haber_ids, String hash, String titles, String descs, String links,
			String kurum_lists, String yayin_yeri, String yayin_tarihi, String eklenme_tarihi) {
		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/unihaber_db?useUnicode=true&characterEncoding=utf-8", "root", "");

			String sql = "INSERT INTO `milliyethaber`(`haber_id`, `hash`,  `baslik`, "
					+ "`ozet`,  `link`, `kurum_list`,  `yayin_yeri`,   `yayin_tarihi`, `eklenme_tarihi`)"
					+ " VALUES (?,?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);

			preparedStatement.setString(1, haber_ids);
			preparedStatement.setString(2, hash);
			preparedStatement.setString(3, titles);
			preparedStatement.setString(4, descs);
			preparedStatement.setString(5, links);
			preparedStatement.setString(6, kurum_lists);
			preparedStatement.setString(7, yayin_yeri);
			preparedStatement.setString(8, yayin_tarihi);
			preparedStatement.setString(9, eklenme_tarihi);

			preparedStatement.execute();
			System.out.println("haber bilgileri eklendi");

			preparedStatement.close();
			con.close();
		} catch (Exception e) {
			System.out.println("hata=" + e);
		}
	}

	/*
	 * public static void sqlNTVInsertFunc(String haber_ids, String hash, String
	 * titles, String descs, String links, String kurum_lists, String yayin_yeri,
	 * String yayin_tarihi, String eklenme_tarihi) { try {
	 * 
	 * Class.forName("com.mysql.jdbc.Driver");
	 * 
	 * Connection con = DriverManager.getConnection(
	 * "jdbc:mysql://localhost:3306/unihaber_db?useUnicode=true&characterEncoding=utf-8",
	 * "root", "");
	 * 
	 * Statement stmt = (Statement) con.createStatement();
	 * 
	 * int result = stmt.executeUpdate(
	 * "INSERT INTO ntvhaber (haber_id, hash, baslik, ozet, link, kurum_list, yayin_yeri, yayin_tarihi, eklenme_tarihi)"
	 * + " VALUES ('" + haber_ids + "','" + hash + "','" + titles + "','" + descs +
	 * "','" + links + "','" + kurum_lists + "','" + yayin_yeri + "','" +
	 * yayin_tarihi + "','" + eklenme_tarihi + "')");
	 * 
	 * if (result > 0) { System.out.println("Successfully Inserted !"); } else {
	 * System.out.println("UnSuccessful Insertion.."); }
	 * 
	 * con.close();
	 * 
	 * } catch (SQLException ex) {
	 * 
	 * ex.printStackTrace();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */
	public static void NtvNewInsertFunc(String haber_ids, String hash, String titles, String descs, String links,
			String kurum_lists, String yayin_yeri, String yayin_tarihi, String eklenme_tarihi) {
		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/unihaber_db?useUnicode=true&characterEncoding=utf-8", "root", "");

			String sql = "INSERT INTO `ntvhaber`(`haber_id`, `hash`,  `baslik`, "
					+ "`ozet`,  `link`, `kurum_list`,  `yayin_yeri`,   `yayin_tarihi`, `eklenme_tarihi`)"
					+ " VALUES (?,?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);

			preparedStatement.setString(1, haber_ids);
			preparedStatement.setString(2, hash);
			preparedStatement.setString(3, titles);
			preparedStatement.setString(4, descs);
			preparedStatement.setString(5, links);
			preparedStatement.setString(6, kurum_lists);
			preparedStatement.setString(7, yayin_yeri);
			preparedStatement.setString(8, yayin_tarihi);
			preparedStatement.setString(9, eklenme_tarihi);

			preparedStatement.execute();
			System.out.println("haber bilgileri eklendi");

			preparedStatement.close();
			con.close();
		} catch (Exception e) {
			System.out.println("hata=" + e);
		}
	}

	public static void BirgunNewInsertFunc(String haber_ids, String hash, String titles, String descs, String links,
			String kurum_lists, String yayin_yeri, String yayin_tarihi, String eklenme_tarihi) {
		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/unihaber_db?useUnicode=true&characterEncoding=utf-8", "root", "");

			String sql = "INSERT INTO `birgunhaber`(`haber_id`, `hash`,  `baslik`, "
					+ "`ozet`,  `link`, `kurum_list`,  `yayin_yeri`,   `yayin_tarihi`, `eklenme_tarihi`)"
					+ " VALUES (?,?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);

			preparedStatement.setString(1, haber_ids);
			preparedStatement.setString(2, hash);
			preparedStatement.setString(3, titles);
			preparedStatement.setString(4, descs);
			preparedStatement.setString(5, links);
			preparedStatement.setString(6, kurum_lists);
			preparedStatement.setString(7, yayin_yeri);
			preparedStatement.setString(8, yayin_tarihi);
			preparedStatement.setString(9, eklenme_tarihi);

			preparedStatement.execute();
			System.out.println("haber bilgileri eklendi");

			preparedStatement.close();
			con.close();
		} catch (Exception e) {
			System.out.println("hata=" + e);
		}
	}

	public static void InternetNewInsertFunc(String haber_ids, String hash, String titles, String descs, String links,
			String kurum_lists, String yayin_yeri, String yayin_tarihi, String eklenme_tarihi) {
		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/unihaber_db?useUnicode=true&characterEncoding=utf-8", "root", "");

			String sql = "INSERT INTO `internethaber`(`haber_id`, `hash`,  `baslik`, "
					+ "`ozet`,  `link`, `kurum_list`,  `yayin_yeri`,   `yayin_tarihi`, `eklenme_tarihi`)"
					+ " VALUES (?,?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);

			preparedStatement.setString(1, haber_ids);
			preparedStatement.setString(2, hash);
			preparedStatement.setString(3, titles);
			preparedStatement.setString(4, descs);
			preparedStatement.setString(5, links);
			preparedStatement.setString(6, kurum_lists);
			preparedStatement.setString(7, yayin_yeri);
			preparedStatement.setString(8, yayin_tarihi);
			preparedStatement.setString(9, eklenme_tarihi);

			preparedStatement.execute();
			System.out.println("haber bilgileri eklendi");

			preparedStatement.close();
			con.close();
		} catch (Exception e) {
			System.out.println("hata=" + e);
		}
	}

	public static void HaberTurkNewInsertFunc(String haber_ids, String hash, String titles, String descs, String links,
			String kurum_lists, String yayin_yeri, String yayin_tarihi, String eklenme_tarihi) {
		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/unihaber_db?useUnicode=true&characterEncoding=utf-8", "root", "");

			String sql = "INSERT INTO `haberturkhaber`(`haber_id`, `hash`,  `baslik`, "
					+ "`ozet`,  `link`, `kurum_list`,  `yayin_yeri`,   `yayin_tarihi`, `eklenme_tarihi`)"
					+ " VALUES (?,?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);

			preparedStatement.setString(1, haber_ids);
			preparedStatement.setString(2, hash);
			preparedStatement.setString(3, titles);
			preparedStatement.setString(4, descs);
			preparedStatement.setString(5, links);
			preparedStatement.setString(6, kurum_lists);
			preparedStatement.setString(7, yayin_yeri);
			preparedStatement.setString(8, yayin_tarihi);
			preparedStatement.setString(9, eklenme_tarihi);

			preparedStatement.execute();
			System.out.println("haber bilgileri eklendi");

			preparedStatement.close();
			con.close();
		} catch (Exception e) {
			System.out.println("hata=" + e);
		}
	}

	public static void SabahNewInsertFunc(String haber_ids, String hash, String titles, String descs, String links,
			String kurum_lists, String yayin_yeri, String yayin_tarihi, String eklenme_tarihi) {
		try {

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/unihaber_db?useUnicode=true&characterEncoding=utf-8", "root", "");

			String sql = "INSERT INTO `sabahhaber`(`haber_id`, `hash`,  `baslik`, "
					+ "`ozet`,  `link`, `kurum_list`,  `yayin_yeri`,   `yayin_tarihi`, `eklenme_tarihi`)"
					+ " VALUES (?,?,?,?,?,?,?,?,?)";

			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);

			preparedStatement.setString(1, haber_ids);
			preparedStatement.setString(2, hash);
			preparedStatement.setString(3, titles);
			preparedStatement.setString(4, descs);
			preparedStatement.setString(5, links);
			preparedStatement.setString(6, kurum_lists);
			preparedStatement.setString(7, yayin_yeri);
			preparedStatement.setString(8, yayin_tarihi);
			preparedStatement.setString(9, eklenme_tarihi);

			preparedStatement.execute();
			System.out.println("haber bilgileri eklendi");

			preparedStatement.close();
			con.close();
		} catch (Exception e) {
			System.out.println("hata=" + e);
		}
	}

	/*
	 * public static void NtvNewInsertFunc(int Ids, String haber_ids, String hash,
	 * String titles, String descs, String links, String kurum_lists, String
	 * yayin_yeri, String yayin_tarihi, String eklenme_tarihi) { try {
	 * 
	 * Class.forName("com.mysql.jdbc.Driver");
	 * 
	 * Connection con = DriverManager.getConnection(
	 * "jdbc:mysql://localhost:3306/unihaber_db?useUnicode=true&characterEncoding=utf-8",
	 * "root", "");
	 * 
	 * String sql = "INSERT INTO `ntvhaber`(`Id`,`haber_id`, `hash`,  `baslik`, " +
	 * "`ozet`,  `link`, `kurum_list`,  `yayin_yeri`,   `yayin_tarihi`, `eklenme_tarihi`)"
	 * + " VALUES (?,?,?,?,?,?,?,?,?,?)";
	 * 
	 * PreparedStatement preparedStatement = (PreparedStatement)
	 * con.prepareStatement(sql);
	 * 
	 * preparedStatement.setInt(1, Ids); preparedStatement.setString(2, haber_ids);
	 * preparedStatement.setString(3, hash); preparedStatement.setString(4, titles);
	 * preparedStatement.setString(5, descs); preparedStatement.setString(6, links);
	 * preparedStatement.setString(7, kurum_lists); preparedStatement.setString(8,
	 * yayin_yeri); preparedStatement.setString(9, yayin_tarihi);
	 * preparedStatement.setString(10, eklenme_tarihi);
	 * 
	 * preparedStatement.execute(); System.out.println("haber bilgileri eklendi");
	 * 
	 * preparedStatement.close(); con.close(); } catch (Exception e) {
	 * System.out.println("hata=" + e); } }
	 */
	public static void main(String[] args) {
		// sqlInsertFunc(0, null, null, null, null, null, null, null, null, null);
		// sqlNTVInsertFunc(566, "haber_id", "NTV", "basliklar", "açıklamalar",
		// "www.google.com", "Fırat Üniversitesi",
		// "NTV Haber", "16-10-2024", "16-10-2024 18:21:30");
		// NtvNewInsertFunc(55, "haber ID gelecek", "NTV", "Başlık gelecek", "Açıklama
		// gelecek", "Linkler gelecek",
		// "Haberde Geçen Üniversite İsimleri", "NTV Haber", "17-10-2021", "17-10-2021
		// 18:24:30");
	}

}
