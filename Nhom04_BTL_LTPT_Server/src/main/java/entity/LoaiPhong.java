package entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LoaiPhong implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1011757587087612914L;
	@Id
	private String maLP;
	private String tenLP;
	private int soLuongNguoi;
	private double gia;
	private String moTa;
	private String hinhAnh;

	public LoaiPhong() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoaiPhong(String maLP) {
		super();
		this.maLP = maLP;
	}

	public LoaiPhong(String maLP, String tenLP, int soLuongNguoi, double gia, String moTa, String hinhAnh) {
		super();
		this.maLP = maLP;
		this.tenLP = tenLP;
		this.soLuongNguoi = soLuongNguoi;
		this.gia = gia;
		this.moTa = moTa;
		this.hinhAnh = hinhAnh;
	}

	public String getMaLP() {
		return maLP;
	}

	public void setMaLP(String maLP) {
		this.maLP = maLP;
	}

	public String getTenLP() {
		return tenLP;
	}

	public void setTenLP(String tenLP) {
		this.tenLP = tenLP;
	}

	public int getSoLuongNguoi() {
		return soLuongNguoi;
	}

	public void setSoLuongNguoi(int soLuongNguoi) {
		this.soLuongNguoi = soLuongNguoi;
	}

	public double getGia() {
		return gia;
	}

	public void setGia(double gia) {
		this.gia = gia;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public String getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	@Override
	public String toString() {
		return "LoaiPhong [maLP=" + maLP + ", tenLP=" + tenLP + ", soLuongNguoi=" + soLuongNguoi + ", gia=" + gia
				+ ", moTa=" + moTa + ", hinhAnh=" + hinhAnh + "]";
	}

}
