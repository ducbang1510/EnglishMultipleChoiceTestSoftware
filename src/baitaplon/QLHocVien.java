/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baitaplon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class QLHocVien {

	private List<HocVien> ds;

	public QLHocVien() {
		this.ds = new ArrayList<>();
	}

	/**
	 * Phương thức thêm học viên
	 * 
	 * @param hv: Học viên
	 */
	public void themHocVien(HocVien hv) {
		this.getDs().add(hv);
	}

	/**
	 * Phương thức hiển thị danh sách học viên
	 */
	public void hienThiDs() {
		this.getDs().forEach((HocVien hv) -> hv.hienThi());
	}

	/**
	 * Phương thức tra cứu thông tin theo tên, quê quán, giới tính
	 * 
	 * @param scanner
	 * @param kw:     Từ khóa
	 * @return
	 */
	public void traCuuTheoKw(Scanner scanner, String kw) {
		SimpleDateFormat j = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql = "SELECT id, hoTen, username, gioiTinh, queQuan, "
						+ "ngaySinh, ngayNhapHoc FROM users where hoTen = '" + kw + "' or queQuan = '" + kw + "' or gioiTinh = '" + kw +"';";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				int ms = rs.getInt("id");
				String name = rs.getString("hoTen");
				String user = rs.getString("username");
				String qq = rs.getString("queQuan");
				String gt = rs.getString("gioiTinh");
				String ns = j.format(rs.getDate("ngaySinh"));
				String nnh = j.format(rs.getDate("ngayNhapHoc"));
				System.out.printf(
						"Id: %d - %s\n + Username: %s\n + Hometown: %s\n + Gender: %s\n"
								+ " + Date of birth: %s\n + Registration date: %s \n",
						ms, name, user, qq, gt, ns, nnh);
			}
			stm.close();
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Phương thức tra cứu thhông tin học viên theo ngày sinh
	 * 
	 * @param scanner
	 * @param d:      Ngày tháng năm
	 * @return
	 * @throws ParseException
	 */
	public void traCuuTheoNgaySinh(Scanner scanner, String d) throws ParseException {
		SimpleDateFormat j = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql = "SELECT id, hoTen, username, gioiTinh, queQuan, "
						+ "ngaySinh, ngayNhapHoc FROM users where ngaySinh = '" + d +"';";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				int ms = rs.getInt("id");
				String name = rs.getString("hoTen");
				String user = rs.getString("username");
				String qq = rs.getString("queQuan");
				String gt = rs.getString("gioiTinh");
				String ns = j.format(rs.getDate("ngaySinh"));
				String nnh = j.format(rs.getDate("ngayNhapHoc"));
				System.out.printf(
						"Id: %d - %s\n + Username: %s\n + Hometown: %s\n + Gender: %s\n"
								+ " + Date of birth: %s\n + Registration date: %s \n",
						ms, name, user, qq, gt, ns, nnh);
			}
			stm.close();
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Phương thức xóa học viên
	 * 
	 * @param scanner
	 * @param kw:     Từ khóa
	 */
	public void xoaHocVien(Scanner scanner, String kw) {
		kw = kw.toLowerCase();
		for (int i = 0; i < this.getDs().size(); i++) {
			if (getDs().get(i).getHoTen().toLowerCase().contains(kw) == true) {
				this.getDs().remove(getDs().get(i));
			}
		}
	}

	/**
	 * Phương thức xóa học viên
	 */
	public void xoaHocVien(HocVien hv) {
		this.getDs().remove(hv);
	}

	/**
	 * Phương thức đăng nhập
	 * 
	 * @param scanner
	 * @param user:   Tên đăng nhập
	 * @param mk:     Mật khẩu
	 * @return
	 */
	public boolean dangNhap(Scanner scanner, String user, String mk) {
		boolean flag = true;
		try {
			if (user.equals("") || mk.equals("")) {
				System.out.println(" => Please enter full the information...!!!");
				flag = false;
			} else {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
						"Bang!@#0977477916");
				String sql = "SELECT * FROM users WHERE username=? AND matKhau=?";
				PreparedStatement stm = conn.prepareStatement(sql);
				stm.setString(1, user);
				stm.setString(2, mk);
				ResultSet rs = stm.executeQuery();
				if (rs.next()) {
					flag = true;
				} else {
					flag = false;
				}
				stm.close();
				conn.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			System.exit(0);
		}
		return flag;
	}

	/**
	 * Phương thức xóa học viên
	 * 
	 * @param user: Tên đăng nhập
	 * @param mk:   Mật khẩu
	 */
	public void xoaUser(String user, String mk) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql = "DELETE FROM users WHERE username = '" + user + "' AND matKhau = '" + mk + "';";
			int row = stm.executeUpdate(sql);
			if (row > 0)
				System.out.println("  ==> Delete successful...!!!\\n");
			else
				System.out.println("  ==> Delete failed...!!!");
			stm.close();
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Phương thức cập nhập thông tin học viên
	 * 
	 * @param scanner
	 * @param user:   Tên đăng nhập
	 * @param choice: Chọn lựa
	 * @param change: Dữ liệu thay đổi
	 * @throws ParseException
	 */
	public void capNhatThongTin(Scanner scanner, String user, int choice, String change) throws ParseException {
		SimpleDateFormat j = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql;
			int row = 0;
			switch (choice) {
			case 1:
				sql = "UPDATE users SET hoTen ='" + change + "' WHERE username ='" + user + "';";
				row = stm.executeUpdate(sql);
				break;
			case 2:
				sql = "UPDATE users SET matKhau ='" + change + "' WHERE username ='" + user + "';";
				row = stm.executeUpdate(sql);
				break;
			case 3:
				sql = "UPDATE users SET queQuan ='" + change + "' WHERE username ='" + user + "';";
				row = stm.executeUpdate(sql);
				break;
			case 4:
				sql = "UPDATE users SET gioiTinh ='" + change + "' WHERE username ='" + user + "';";
				row = stm.executeUpdate(sql);
				break;
			case 5:
				Date ns = j.parse(change);
				sql = "UPDATE users SET ngaySinh ='" + j.format(ns) + "' WHERE username ='" + user + "';";
				row = stm.executeUpdate(sql);
				break;
			default:
				System.out.println("==> Out of choices !!!");
			}
			if (row > 0)
				System.out.println("=> Update Information Successfully...!!!");
			else
				System.out.println("=> Update Information Failed");
			stm.close();
			conn.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	public void xemDSHocVien() {
		SimpleDateFormat j = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql = "SELECT id, hoTen, username, gioiTinh, queQuan, ngaySinh, ngayNhapHoc FROM users;";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				int ms = rs.getInt("id");
				String name = rs.getString("hoTen");
				String user = rs.getString("username");
				String qq = rs.getString("queQuan");
				String gt = rs.getString("gioiTinh");
				String ns = j.format(rs.getDate("ngaySinh"));
				String nnh = j.format(rs.getDate("ngayNhapHoc"));
				System.out.printf(
						"Id: %d - %s\n + Username: %s\n + Hometown: %s\n + Gender: %s\n"
								+ " + Date of birth: %s\n + Registration date: %s \n",
						ms, name, user, qq, gt, ns, nnh);
			}
			stm.close();
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * @return the ds
	 */
	public List<HocVien> getDs() {
		return ds;
	}

	/**
	 * @param ds the ds to set
	 */
	public void setDs(List<HocVien> ds) {
		this.ds = ds;
	}

}
