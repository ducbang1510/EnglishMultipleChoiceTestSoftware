/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baitaplon;

/**
 *
 * @author Admin
 */
public class PhuongAn {
	private String noiDung;
	private String ghiChu;
	private boolean dapAn;

	/**
	 * Phương thức khởi tạo không tham số
	 */
	public PhuongAn() {
	}

	/**
	 * Phương thức khởi tạo 2 tham số
	 * 
	 * @param noiDung: Nội dung
	 * @param dapAn:   Đáp án
	 */
	public PhuongAn(String noiDung, boolean dapAn) {
		this.noiDung = noiDung;
		this.dapAn = dapAn;
	}

	/**
	 * Phương thức khởi tạo 3 tham số
	 * 
	 * @param noiDung: Nội dung
	 * @param cm:      Ghi chú(comment)
	 * @param dapAn:   Đáp án
	 */
	public PhuongAn(String noiDung, String cm, boolean dapAn) {
		this.noiDung = noiDung;
		this.dapAn = dapAn;
		this.ghiChu = cm;
	}

	@Override
	public String toString() {
		return this.noiDung;
	}

	/**
	 * @return the noiDung
	 */
	public String getNoiDung() {
		return noiDung;
	}

	/**
	 * @param noiDung the noiDung to set
	 */
	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}

	/**
	 * @return the dapAn
	 */
	public boolean isDapAn() {
		return dapAn;
	}

	/**
	 * @param dapAn the dapAn to set
	 */
	public void setDapAn(boolean dapAn) {
		this.dapAn = dapAn;
	}

	/**
	 * @return the ghiChu
	 */
	public String getGhiChu() {
		return ghiChu;
	}

	/**
	 * @param ghiChu the ghiChu to set
	 */
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
}
