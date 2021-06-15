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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Admin
 */
public class MultipleChoice extends CauHoi {
	private static final String[] a = { "A", "B", "C", "D" };
	private Categories loai;
	private List<PhuongAn> PhuongAn;

	public MultipleChoice() {
		super();
	}

	/**
	 * Phương thức khởi tạo 2 tham số
	 * 
	 * @param noiDung: Nội dung
	 * @param mucDo:   Mức độ
	 */
	public MultipleChoice(String noiDung, Level mucDo) {
		super(noiDung, mucDo);
		this.PhuongAn = new ArrayList<>();
	}

	/**
	 * Phương thức khởi tạo 3 tham số
	 * 
	 * @param noiDung: Nội dung
	 * @param mucDo:   Mức độ
	 * @param loai:    Loại
	 */
	public MultipleChoice(String noiDung, Level mucDo, Categories loai) {
		super(noiDung, mucDo);
		this.loai = loai;
		this.PhuongAn = new ArrayList<>();
	}

	/**
	 * Phương thức thêm phương án
	 * 
	 * @param pa: Phương án
	 */
	public void themPhuongAn(PhuongAn pa) {
		if (this.PhuongAn.size() < a.length)
			this.PhuongAn.add(pa);
	}

	/**
	 * Phương thức kiểm tra đáp án
	 * 
	 * @param as: Đáp án(Answer)
	 * @return
	 */
	public boolean kiemTra(String as) {
		for (int i = 0; i < this.PhuongAn.size(); i++)
			if (this.PhuongAn.get(i).isDapAn() == true && a[i].equals(as.toUpperCase()) == true)
				return true;
		return false;
	}

	/**
	 * Phương thức trộn đáp án
	 */
	public void shuffleChoice() {
		Collections.shuffle(this.PhuongAn);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s = s.append(super.toString()).append("\n");
		for (int i = 0; i < a.length; i++) {
			s = s.append(a[i]).append('.').append(PhuongAn.get(i)).append("\t");
		}
		return s.toString();
	}

	/**
	 * Phương thức lấy Id của dạng câu hỏi Multiplechoice
	 * 
	 * @param content: Nội dung câu hỏi
	 * @return
	 */
	public int layIdMultiplechoice(String content) {
		int ms = 0;
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql = "SELECT id FROM multiplechoice WHERE content = '" + content + "';";
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
	 * Phương thức lấy Id của dạng câu hỏi Conversation & Incomplete
	 * 
	 * @param content: Nội dung
	 * @param loai:    Loại
	 * @param id:      ID
	 * @return
	 */
	public int layIdMultiplechoice(String content, String loai, int id) {
		int ms = 0;
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql = null;
			switch (loai) {
			case "conversation":
				sql = "select multiplechoice.id\n" + "from conversation, multiplechoice\n"
						+ "where conversation.id = multiplechoice.conversation_id and multiplechoice.content = '"
						+ content + "' and\n" + "conversation.id ='" + Integer.toString(id) + "'\n"
						+ "order by multiplechoice.id asc;";
				break;
			case "incomplete":
				sql = "select multiplechoice.id\n" + "from incomplete, multiplechoice\n"
						+ "where incomplete.id = multiplechoice.incomplete_id and multiplechoice.content = '" + content
						+ "' and\n" + "incomplete.id ='" + Integer.toString(id) + "'\n"
						+ "order by multiplechoice.id asc;";
				break;
			}
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

	public List<PhuongAn> getPhuongAn() {
		return PhuongAn;
	}

	public void setPhuongAn(List<PhuongAn> PhuongAn) {
		this.PhuongAn = PhuongAn;
	}

	public Categories getLoai() {
		return loai;
	}

	public void setLoai(Categories loai) {
		this.loai = loai;
	}
}
