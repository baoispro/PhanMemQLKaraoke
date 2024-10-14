package entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class KhachHang implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7163512030967577000L;
	@Id
	private String maKH;
	private String tenKH;
	private boolean gioiTinh;
	private String diaChi;
	private String email;
	private String soDienThoai;
	private String cccd;
	private Date ngaySinh;

	public KhachHang() {
		super();
		// TODO Auto-generated constructor stub
	}

	public KhachHang(String maKH) {
		super();
		this.maKH = maKH;
	}

	public KhachHang(String maKH, String tenKH, boolean gioiTinh, String diaChi, String email, String soDienThoai,
			String cccd, Date ngaySinh) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.gioiTinh = gioiTinh;
		this.diaChi = diaChi;
		this.email = email;
		this.soDienThoai = soDienThoai;
		this.cccd = cccd;
		this.ngaySinh = ngaySinh;
	}

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public String getTenKH() {
		return tenKH;
	}

	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getCccd() {
		return cccd;
	}

	public void setCccd(String cccd) {
		this.cccd = cccd;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	@Override
	public String toString() {
		return "KhachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", ngaySinh=" + ngaySinh + ", gioiTinh=" + gioiTinh
				+ ", soDienThoai=" + soDienThoai + ", diaChi=" + diaChi + ", email=" + email + "]";
	}

}
