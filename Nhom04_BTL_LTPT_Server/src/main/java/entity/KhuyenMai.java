package entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class KhuyenMai implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 359062527302261095L;
	@Id
	private String maKM;
	private String tenKM;
	private String dieuKienApDung;
	private float giaTriKhuyenMai;
	private Date ngayBatDau;
	private Date ngayKetThuc;

	public KhuyenMai() {
		super();
		// TODO Auto-generated constructor stub
	}

	public KhuyenMai(String maKM) {
		super();
		this.maKM = maKM;
	}

	public KhuyenMai(String maKM, String tenKM, String dieuKienApDung, float giaTriKhuyenMai, Date ngayBatDau,
			Date ngayKetThuc) {
		super();
		this.maKM = maKM;
		this.tenKM = tenKM;
		this.dieuKienApDung = dieuKienApDung;
		this.giaTriKhuyenMai = giaTriKhuyenMai;
		this.ngayBatDau = ngayBatDau;
		this.ngayKetThuc = ngayKetThuc;
	}

	public String getMaKM() {
		return maKM;
	}

	public void setMaKM(String maKM) {
		this.maKM = maKM;
	}

	public String getTenKM() {
		return tenKM;
	}

	public void setTenKM(String tenKM) {
		this.tenKM = tenKM;
	}

	public String getDieuKienApDung() {
		return dieuKienApDung;
	}

	public void setDieuKienApDung(String dieuKienApDung) {
		this.dieuKienApDung = dieuKienApDung;
	}

	public float getGiaTriKhuyenMai() {
		return giaTriKhuyenMai;
	}

	public void setGiaTriKhuyenMai(float giaTriKhuyenMai) {
		this.giaTriKhuyenMai = giaTriKhuyenMai;
	}

	public Date getNgayBatDau() {
		return ngayBatDau;
	}

	public void setNgayBatDau(Date ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	public Date getNgayKetThuc() {
		return ngayKetThuc;
	}

	public void setNgayKetThuc(Date ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}

	@Override
	public String toString() {
		return "KhuyenMai [maKM=" + maKM + ", tenKM=" + tenKM + ", dieuKienApDung=" + dieuKienApDung
				+ ", giaTriKhuyenMai=" + giaTriKhuyenMai + ", ngayBatDau=" + ngayBatDau + ", ngayKetThuc=" + ngayKetThuc
				+ "]";
	}

}
