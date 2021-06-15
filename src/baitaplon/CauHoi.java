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
public abstract class CauHoi {
	private String noiDung;
	private Level mucDo;

	/**
	 * Phương thức khởi tạo không tham số
	 */
	public CauHoi() {
	}

	/**
	 * Phương thức khởi tạo 2 tham số
	 * 
	 * @param noiDung: Nội dung
	 * @param mucDo:   Mức độ
	 */
	public CauHoi(String noiDung, Level mucDo) {
		this.noiDung = noiDung;
		this.mucDo = mucDo;
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
	 * @return the mucDo
	 */
	public Level getMucDo() {
		return mucDo;
	}

	/**
	 * @param mucDo the mucDo to set
	 */
	public void setMucDo(Level mucDo) {
		this.mucDo = mucDo;
	}

}