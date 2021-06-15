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
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class QLCauHoi {

	private List<CauHoi> ds;
	private double demTrue;

	public QLCauHoi() {
		this.ds = new ArrayList<>();
	}

	/**
	 * Phương thức thêm câu hỏi
	 * 
	 * @param ch
	 */
	public void themCauHoi(CauHoi ch) {
		this.ds.add(ch);
	}

	/**
	 * Phương thức chấm điểm
	 * 
	 * @param kq: kết quả
	 * @return
	 */
	public double chamDiem(List<Boolean> kq) {
		demTrue = 0;
		for (int i = 0; i < kq.size(); i++) {
			if (kq.get(i) == true) {
				demTrue++;
			}
		}
		return (10.0 / kq.size()) * demTrue;
	}

	/**
	 * Phương thức lấy đáp án
	 * 
	 * @param qs: câu hỏi(question)
	 * @return
	 */
	public String layDapAn(MultipleChoice qs) {
		String s = null;
		for (PhuongAn a : qs.getPhuongAn())
			if (a.isDapAn() == true)
				s = a.getNoiDung();
		return s;
	}
	
	public void xuatKetQua(List<Boolean> kq) {
		for (int i = 0; i < kq.size(); i++) {
			if (kq.get(i) == true) {
				System.out.println(i + 1 + ". Correct");
			} else {
				System.out.println(i + 1 + ". Incorrect");
			}
		}
	}

	/**
	 * Phương thức luyện tập dạng câu hỏi Multiplechoice
	 * 
	 * @param scanner
	 * @param username: Tên đăng nhập
	 * @param sl:       Số lượng
	 * @return
	 */
	public List<Boolean> practiceMultipleChoice(Scanner scanner, String username, int sl) {
		HocVien hv = new HocVien();
		String s = "";
		MultipleChoice chM = null;
		PhuongAn pa = null;
		List<Boolean> kq = new ArrayList<>();
		List<CauHoi> ch = new ArrayList<>();
		List<String> a = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql = "select multiplechoice.id as multiplechoice_id, "
					+ "multiplechoice.content as multiplechoice_content, multiplechoice.level_id ,choice.content as choice_content, is_correct, note\n"
					+ "from multiplechoice, choice\n" + "where multiplechoice.id = choice.multiplechoice_id and\n"
					+ "multiplechoice.conversation_id is null and multiplechoice.incomplete_id is null and\n"
					+ "multiplechoice.id not in (select id_multiplechoice from users_questions where id_user = "
					+ hv.layIdHocVien(username) + ")\n" + "order by multiplechoice.id asc, choice.id asc;";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String contentM = rs.getString("multiplechoice_content");
				String contentPA = rs.getString("choice_content");
				Boolean correct = rs.getBoolean("is_correct");
				int level = rs.getInt("level_id");
				Level lv = null;
				switch (level) {
				case 1:
					lv = Level.EASY;
					break;
				case 2:
					lv = Level.MEDIUM;
					break;
				case 3:
					lv = Level.HARD;
					break;
				}
				if (s.equals(contentM) == false) {
					chM = new MultipleChoice(contentM, lv);
					s = contentM;
				}
				pa = new PhuongAn(contentPA, correct);
				chM.themPhuongAn(pa);
				ch.add(chM);
			}
			Collections.shuffle(ch);
			int n = ch.size() > sl ? sl : ch.size();
			for (int i = 0; i < n; i++) {
				MultipleChoice q = (MultipleChoice) ch.get(i);
				a.add(layDapAn(q));
				luuCauDaLuyenTap(hv.layIdHocVien(username), q.layIdMultiplechoice(q.getNoiDung()));
				System.out.print(i + 1 + ". ");
				System.out.println(q);
				System.out.print(" => Your answer: ");
				String as = scanner.next();
				if (q.kiemTra(as) == true) {
					kq.add(Boolean.TRUE);
				} else {
					kq.add(Boolean.FALSE);
				}
			}
			xuatKetQua(kq);
			System.out.println(" == Solution ==");
			for (int i = 0; i < a.size(); i++)
				System.out.println(i + 1 + ". " + a.get(i));
			stm.close();
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return kq;
	}

	/**
	 * Phương thức luyện tập dạng câu hỏi Conversation
	 * 
	 * @param scanner
	 * @param username: Tên đăng nhập
	 * @param lvl:      Cấp độ(level)
	 * @return
	 */
	public List<Boolean> practiceConversation(Scanner scanner, String username, String lvl) {
		HocVien hv = new HocVien();
		String s = "", s1 = "";
		int demsl = 0;
		Conversation chC = null;
		MultipleChoice chM = null;
		PhuongAn pa = null;
		List<Boolean> kq = new ArrayList<>();
		List<CauHoi> ch = new ArrayList<>();
		List<CauHoi> dsqs = new ArrayList<>();
		List<String> a = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql = "select conversation.id, conversation.content as conversation_content, conversation.level_id, multiplechoice.id as multiplechoice_id, "
					+ "multiplechoice.content as multiplechoice_content, choice.content as choice_content, is_correct, note\n"
					+ "from multiplechoice, choice, conversation\n"
					+ "where multiplechoice.id = choice.multiplechoice_id and\n"
					+ "conversation.id = multiplechoice.conversation_id and\n"
					+ "multiplechoice.id not in (select id_multiplechoice from users_questions where id_user = "
					+ hv.layIdHocVien(username) + ")" + "order by conversation.id asc, multiplechoice.id asc;";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String content = rs.getString("conversation_content");
				String contentM = rs.getString("multiplechoice_content");
				String contentPA = rs.getString("choice_content");
				Boolean correct = rs.getBoolean("is_correct");
				int level = rs.getInt("level_id");
				Level lv = null;
				switch (level) {
				case 1:
					lv = Level.EASY;
					break;
				case 2:
					lv = Level.MEDIUM;
					break;
				case 3:
					lv = Level.HARD;
					break;
				}
				if (s.equals(content) == false) {
					chC = new Conversation(content, lv);
					ch.add(chC);
					s = content;
				}
				if (s1.equals(contentM) == false) {
					chM = new MultipleChoice(contentM, lv);
					chC.themCauHoi(chM);
					s1 = contentM;
				}
				pa = new PhuongAn(contentPA, correct);
				chM.themPhuongAn(pa);
			}
			for (CauHoi h : ch) {
				if (h.getMucDo().toString().toLowerCase().equals(lvl) == true) {
					dsqs.add(h);
				}
			}
			Collections.shuffle(dsqs);
			if (dsqs.size() > 0) {
				Conversation q = (Conversation) dsqs.get(0);
				int idC = q.layIdConversation(q.getNoiDung());
				System.out.println(q);
				System.out.println("=> Your answers: ");
				for (MultipleChoice qs : q.getQuestions()) {
					a.add(layDapAn(qs));
					demsl++;
					luuCauDaLuyenTap(hv.layIdHocVien(username),
							qs.layIdMultiplechoice(qs.getNoiDung(), "conversation", idC));
					System.out.printf("%d) %s  ", demsl, qs.getNoiDung());
					String as = scanner.next();
					if (qs.kiemTra(as) == true) {
						kq.add(Boolean.TRUE);
					} else {
						kq.add(Boolean.FALSE);
					}
				}
			}
			xuatKetQua(kq);
			System.out.println(" == Solution ==");
			for (int i = 0; i < a.size(); i++)
				System.out.println(i + 1 + ". " + a.get(i));
			stm.close();
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return kq;
	}

	/**
	 * Phương thức luyện tập dạng câu hỏi Incomplete
	 * 
	 * @param scanner
	 * @param username: Tên đăng nhập
	 * @param lvl:      Cấp độ(level)
	 * @return
	 */
	public List<Boolean> practiceIncomplete(Scanner scanner, String username, String lvl) {
		HocVien hv = new HocVien();
		String s = "", s1 = "";
		Incomplete chI = null;
		MultipleChoice chM = null;
		PhuongAn pa = null;
		List<Boolean> kq = new ArrayList<>();
		List<CauHoi> ch = new ArrayList<>();
		List<CauHoi> dsqs = new ArrayList<>();
		List<String> a = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql = "select incomplete.id, incomplete.content as incomplete_content, incomplete.level_id, multiplechoice.id as multiplechoice_id, "
					+ "multiplechoice.content as multiplechoice_content, choice.content as choice_content, is_correct, note\n"
					+ "from multiplechoice, choice, incomplete\n"
					+ "where multiplechoice.id = choice.multiplechoice_id and\n"
					+ "incomplete.id = multiplechoice.incomplete_id and\n"
					+ "multiplechoice.id not in (select id_multiplechoice from users_questions where id_user = "
					+ hv.layIdHocVien(username) + ")" + "order by incomplete.id asc, multiplechoice.id asc;";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String content = rs.getString("incomplete_content");
				String contentM = rs.getString("multiplechoice_content");
				String contentPA = rs.getString("choice_content");
				Boolean correct = rs.getBoolean("is_correct");
				int level = rs.getInt("level_id");
				Level lv = null;
				switch (level) {
				case 1:
					lv = Level.EASY;
					break;
				case 2:
					lv = Level.MEDIUM;
					break;
				case 3:
					lv = Level.HARD;
					break;
				}
				if (s.equals(content) == false) {
					chI = new Incomplete(content, lv);
					ch.add(chI);
					s = content;
				}
				if (s1.equals(contentM) == false) {
					chM = new MultipleChoice(contentM, lv);
					chI.themCauHoi(chM);
					s1 = contentM;
				}
				pa = new PhuongAn(contentPA, correct);
				chM.themPhuongAn(pa);
			}
			for (CauHoi h : ch) {
				if (h.getMucDo().toString().toLowerCase().equals(lvl) == true) {
					dsqs.add(h);
				}
			}
			Collections.shuffle(dsqs);
			if (dsqs.size() > 0) {
				Incomplete q = (Incomplete) dsqs.get(0);
				int idI = q.layIdIncomplete(q.getNoiDung());
				System.out.println(q);
				System.out.println("=> Your answers: ");
				for (MultipleChoice qs : q.getQuestions()) {
					a.add(layDapAn(qs));
					luuCauDaLuyenTap(hv.layIdHocVien(username),
							qs.layIdMultiplechoice(qs.getNoiDung(), "incomplete", idI));
					System.out.printf("Sentence %s: ", qs.getNoiDung());
					String as = scanner.next();
					if (qs.kiemTra(as) == true) {
						kq.add(Boolean.TRUE);
					} else {
						kq.add(Boolean.FALSE);
					}
				}
			}
			xuatKetQua(kq);
			System.out.println(" == Solution ==");
			for (int i = 0; i < a.size(); i++)
				System.out.println(i + 1 + ". " + a.get(i));
			stm.close();
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return kq;
	}

	/**
	 * Phương thức lưu câu đã luyện tập
	 * 
	 * @param userId:           Id của người dùng
	 * @param multiplechoiceId: Id cảu câu hỏi dạng multiplechoice
	 */
	public void luuCauDaLuyenTap(int userId, int multiplechoiceId) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			String sql = "INSERT INTO users_questions (id_user, id_multiplechoice) VALUES ('" + Integer.toString(userId)
					+ "', '" + Integer.toString(multiplechoiceId) + "');";
			Statement stm = conn.createStatement();
			stm.executeUpdate(sql);
			stm.close();
			conn.close();
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * Phương thức tìm kiếm câu hỏi theo mức độ
	 * 
	 * @param scanner
	 * @param kw:     Từ khóa
	 * @return
	 */
	public List<CauHoi> timKiemCauHoi(Scanner scanner, String kw) {
		String s = "";
		MultipleChoice chM = null;
		List<CauHoi> ch = new ArrayList<>();
		List<CauHoi> kq = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql = "select multiplechoice.id as multiplechoice_id, "
					+ "multiplechoice.content as multiplechoice_content, multiplechoice.level_id\n"
					+ "from multiplechoice\n"
					+ "where multiplechoice.conversation_id is null and multiplechoice.incomplete_id is null\n"
					+ "order by multiplechoice.id asc;";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String contentM = rs.getString("multiplechoice_content");
				int level = rs.getInt("level_id");
				Level lv = null;
				switch (level) {
				case 1:
					lv = Level.EASY;
					break;
				case 2:
					lv = Level.MEDIUM;
					break;
				case 3:
					lv = Level.HARD;
					break;
				}
				if (s.equals(contentM) == false) {
					chM = new MultipleChoice(contentM, lv);
					s = contentM;
				}
				ch.add(chM);
			}
			kw = kw.toLowerCase();
			for (CauHoi h : ch) {
				if (h.getNoiDung().toLowerCase().contains(kw) == true
						|| h.getMucDo().toString().toLowerCase().equals(kw) == true) {
					kq.add(h);
				}
			}
			stm.close();
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return kq;
	}

	/**
	 * Phương thức tìm kiếm câu hỏi theo danh mục câu hỏi
	 * 
	 * @param scanner
	 * @param kw:     Từ khóa
	 * @return
	 */
	public List<CauHoi> timKiemCauHoiTheoDanhMuc(Scanner scanner, String kw) {
		this.ds.clear();
		String s = "";
		MultipleChoice chM = null;
		List<CauHoi> ch = new ArrayList<>();
		List<CauHoi> kq = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/english", "root",
					"Bang!@#0977477916");
			Statement stm = conn.createStatement();
			String sql = "select multiplechoice.id as multiplechoice_id, "
					+ "multiplechoice.content as multiplechoice_content, multiplechoice.level_id, category_id\n"
					+ "from multiplechoice\n"
					+ "where multiplechoice.conversation_id is null and multiplechoice.incomplete_id is null\n"
					+ "order by multiplechoice.id asc;";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				String contentM = rs.getString("multiplechoice_content");
				int level = rs.getInt("level_id");
				int ct = rs.getInt("category_id");
				Level lv = null;
				Categories loai = null;
				switch (level) {
				case 1:
					lv = Level.EASY;
					break;
				case 2:
					lv = Level.MEDIUM;
					break;
				case 3:
					lv = Level.HARD;
					break;
				}
				switch (ct) {
				case 1:
					loai = Categories.Noun;
					break;
				case 2:
					loai = Categories.Adj;
					break;
				case 3:
					loai = Categories.Adv;
					break;
				case 4:
					loai = Categories.Verb;
					break;
				}
				if (s.equals(contentM) == false) {
					chM = new MultipleChoice(contentM, lv, loai);
					s = contentM;
				}
				ch.add(chM);
			}
			kw = kw.toLowerCase();
			for (CauHoi h : ch) {
				MultipleChoice q = (MultipleChoice) h;
				if (q.getLoai().toString().toLowerCase().equals(kw) == true) {
					kq.add(q);
				}
			}
			stm.close();
			conn.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return kq;
	}

	public List<CauHoi> getDs() {
		return ds;
	}

	public void setDs(List<CauHoi> ds) {
		this.ds = ds;
	}
}
