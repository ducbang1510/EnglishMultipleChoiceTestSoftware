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
import java.util.List;

/**
 *
 * @author Admin
 */
public class Incomplete extends CauHoi {
	private List<MultipleChoice> questions;

	/**
	 * Phương thức khởi tạo 2 tham số
	 * 
	 * @param noiDung: Nội dung
	 * @param mucDo:   Mức độ
	 */
	public Incomplete(String noiDung, Level mucDo) {
		super(noiDung, mucDo);

		this.questions = new ArrayList<>();
	}

	/**
	 * Phương thức thêm câu hỏi
	 * 
	 * @param qs: Câu hỏi(questions)
	 */
	public void themCauHoi(MultipleChoice qs) {
		this.questions.add(qs);
	}

	@Override
	public String toString() {
		String s = super.toString();
		for (MultipleChoice qs : this.questions) {
			s += "\n" + qs;
		}
		return s;
	}

	/**
	 * Phương thức lấy Id của dạng câu hỏi Incomplete
	 * 
	 * @param content: Nội dung
	 * @return
	 */
	public int layIdIncomplete(String content) {
		int ms = 0;
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql = "SELECT id FROM incomplete WHERE content = '" + content + "';";
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
	 * @return the questions
	 */
	public List<MultipleChoice> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(List<MultipleChoice> questions) {
		this.questions = questions;
	}

}
