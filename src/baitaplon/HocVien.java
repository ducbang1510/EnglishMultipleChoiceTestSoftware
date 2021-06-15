/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baitaplon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class HocVien {

	private String hoTen;
	private String username;
	private String mk;
	private String queQuan;
	private String gioiTinh;
	private Date ngaySinh;
	private final Date ngayNhapHoc = new Date();
	private double diem;

	/**
	 * Phương thức khởi tạo không tham số
	 */
	public HocVien() {
	}

	/**
	 * Phương thức khởi tạo có tham số
	 * 
	 * @param ht: Họ tên
	 * @param qq: Quê quán
	 * @param gt: Giới tính
	 * @param ns: Ngày sinh
	 */
	public HocVien(String ht, String us, String qq, String gt, Date ns) {
		this.hoTen = ht;
		this.username = us;
		this.queQuan = qq;
		this.gioiTinh = gt;
		this.ngaySinh = ns;
	}

	/**
	 * Phương thức đăng kí
	 * 
	 * @param scanner
	 * @param username: Tên đăng nhập
	 * @param mk:       Mật khẩu
	 * @throws ParseException
	 */
	public void dangKi(Scanner scanner, String username, String mk) throws ParseException {
		SimpleDateFormat j = new SimpleDateFormat("yyyy-MM-dd");

		this.username = username;
		this.mk = mk;
		System.out.print(" + Full name: ");
		this.hoTen = scanner.nextLine();
		System.out.print(" + Hometown: ");
		this.queQuan = scanner.nextLine();
		System.out.print(" + Gender: ");
		this.gioiTinh = scanner.nextLine();
		System.out.print(" + Date of birth(yyyy-MM-dd): ");
		String ns = scanner.nextLine();
		this.ngaySinh = j.parse(ns);
		try {
			if (this.hoTen.equals("") || this.username.equals("") || this.mk.equals("") || this.queQuan.equals("")
					|| this.gioiTinh.equals("") || ns.equals("")) {
				System.out.println("==> Please enter full information");
			} else {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
						"Bang!@#0977477916");
				Statement stm = conn.createStatement();
				String sql = "INSERT INTO users (hoTen, username, matKhau, queQuan, gioiTinh, ngaySinh, ngayNhapHoc) VALUES ('"
						+ this.hoTen + "', '" + this.username + "', '" + this.mk + "', '" + this.queQuan + "', '"
						+ this.gioiTinh + "', '" + j.format(this.ngaySinh) + "', '" + j.format(this.ngayNhapHoc)
						+ "');";
				int rows = stm.executeUpdate(sql);
				if (rows > 0) {
					System.out.println("=== Registered successfully ===");
				} else {
					System.out.println("=== Registered failed ===");
				}
				stm.close();
				conn.close();
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * Phương thức xem thông tin học viên
	 * 
	 * @param username: Tên đăng nhập
	 */
	public void xemThongTinHocVien(String username) {
		SimpleDateFormat j = new SimpleDateFormat("dd-MM-yyyy");
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql = "SELECT * FROM users WHERE username = '" + username + "';";
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				int ms = rs.getInt("id");
				String name = rs.getString("hoTen");
				String user = rs.getString("username");
				String pass = rs.getString("matKhau");
				String qq = rs.getString("queQuan");
				String gt = rs.getString("gioiTinh");
				String ns = j.format(rs.getDate("ngaySinh"));
				String nnh = j.format(rs.getDate("ngayNhapHoc"));
				System.out.printf(
						"Id: %d - %s\n + Username: %s\n + Password: %s\n + Hometown: %s\n + Gender: %s\n"
								+ " + Date of birth: %s\n + Registration date: %s \n",
						ms, name, user, pass, qq, gt, ns, nnh);
			}
			stm.close();
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Phương thức lấy Id của học viên
	 * 
	 * @param username: Tên đăng nhập
	 * @return
	 */
	public int layIdHocVien(String username) {
		int ms = 0;
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql = "SELECT id FROM users WHERE username = '" + username + "';";
			ResultSet rs = stm.executeQuery(sql);
			if (rs.next()) {
				ms = rs.getInt("id");
			}
			stm.close();
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return ms;
	}

	/**
	 * Phương thức luu thông tin luyện tập của học viên
	 * 
	 * @param username: Tên đăng nhập
	 * @param sl:       Số lượng
	 * @param loai:     Loại
	 * @param diem:     Điểm
	 */
	public void luuThongTinLuyenTap(String username, int sl, String loai, double diem) {
		SimpleDateFormat j = new SimpleDateFormat("yyyy-MM-dd");
		Date nlt = new Date();
		int ms = layIdHocVien(username);
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			String sql = "INSERT INTO practice (ngayLuyenTap, soLuongCau, loaiCauHoi, id_user, diem) VALUES ('"
					+ j.format(nlt) + "', '" + Integer.toString(sl) + "', '" + loai + "', '" + Integer.toString(ms)
					+ "', '" + Double.toString(diem) + "');";
			Statement stm = conn.createStatement();
			stm.executeUpdate(sql);
			stm.close();
			conn.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * Phương thức thống kê theo tháng
	 * 
	 * @param username: Tên đăng nhập
	 */
	public void thongKeTheoThang(String username) {
		int ms = layIdHocVien(username);
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql = "select users.id, Month(practice.ngayLuyenTap) as Thang, "
					+ "avg(practice.diem) as diemTrungBinh, count(users.id) as soLan\n" + "from users, practice\n"
					+ "where users.id = practice.id_user and \n" + "users.id =" + Integer.toString(ms) + "\n"
					+ "group by Month(practice.ngayLuyenTap)\n" + "order by Month(practice.ngayLuyenTap) asc;";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				int thang = rs.getInt("Thang");
				double diemTB = rs.getDouble("diemTrungBinh");
				int soLan = rs.getInt("soLan");
				System.out.printf(" + Month: %d\n + Number of practice times: %d\n + Average score: %.2f\n", thang,
						soLan, diemTB);
			}
			stm.close();
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Phương thức hiển thị thông tin của học viên
	 */
	public void hienThi() {
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");

		System.out.printf(" + Full name: %s\n + Hometown: %s\n + Gender: %s\n"
						+ " + Date of birth: %s\n",
				this.hoTen, this.queQuan, this.gioiTinh, f.format(this.ngaySinh));
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getQueQuan() {
		return queQuan;
	}

	public void setQueQuan(String queQuan) {
		this.queQuan = queQuan;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public Date getNgayNhapHoc() {
		return ngayNhapHoc;
	}

	public double getDiem() {
		return diem;
	}

	public void setDiem(double diem) {
		this.diem = diem;
	}
}
