package entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DichVu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9176630466407412315L;
	@Id
	private String maDichVu;
	private String tenDichVu;
	private int soLuong;
	private String donVi;
	private double donGia;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MaLDV")
	private LoaiDichVu loaiDichVu;
	private String hinhAnh;

	public DichVu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DichVu(String maDichVu) {
		super();
		this.maDichVu = maDichVu;
	}

	public DichVu(String maDichVu, String tenDichVu, int soLuong, String donVi, double donGia, LoaiDichVu loaiDichVu,
			String hinhAnh) {
		super();
		this.maDichVu = maDichVu;
		this.tenDichVu = tenDichVu;
		this.soLuong = soLuong;
		this.donVi = donVi;
		this.donGia = donGia;
		this.loaiDichVu = loaiDichVu;
		this.hinhAnh = hinhAnh;
	}

	public String getMaDichVu() {
		return maDichVu;
	}

	public void setMaDichVu(String maDichVu) {
		this.maDichVu = maDichVu;
	}

	public String getTenDichVu() {
		return tenDichVu;
	}

	public void setTenDichVu(String tenDichVu) {
		this.tenDichVu = tenDichVu;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public String getDonVi() {
		return donVi;
	}

	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	public LoaiDichVu getLoaiDichVu() {
		return loaiDichVu;
	}

	public void setLoaiDichVu(LoaiDichVu loaiDichVu) {
		this.loaiDichVu = loaiDichVu;
	}

	public String getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	@Override
	public String toString() {
		return "DichVu [maDichVu=" + maDichVu + ", tenDichVu=" + tenDichVu + ", soLuong=" + soLuong + ", donVi=" + donVi
				+ ", donGia=" + donGia + ", loaiDichVu=" + loaiDichVu + ", hinhAnh=" + hinhAnh + "]";
	}

}
