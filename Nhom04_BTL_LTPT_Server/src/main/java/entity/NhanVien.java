package entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class NhanVien implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5277309192375883642L;
	@Id
	private String maNV;
	private String tenNV;
	private Date ngaySinh;
	private boolean gioiTinh;
	private String soDienThoai;
	private String diaChi;
	private String chucVu;
	private String trangThai;
	private String hinhAnh;

	public NhanVien() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NhanVien(String maNV) {
		super();
		this.maNV = maNV;
	}

	public NhanVien(String maNV, String tenNV, Date ngaySinh, boolean gioiTinh, String soDienThoai, String diaChi,
			String chucVu, String trangThai, String hinhAnh) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.ngaySinh = ngaySinh;
		this.gioiTinh = gioiTinh;
		this.soDienThoai = soDienThoai;
		this.diaChi = diaChi;
		this.chucVu = chucVu;
		this.trangThai = trangThai;
		this.hinhAnh = hinhAnh;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getTenNV() {
		return tenNV;
	}

	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	@Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", tenNV=" + tenNV + ", ngaySinh=" + ngaySinh + ", gioiTinh=" + gioiTinh
				+ ", soDienThoai=" + soDienThoai + ", diaChi=" + diaChi + ", chucVu=" + chucVu + ", trangThai="
				+ trangThai + ", hinhAnh=" + hinhAnh + "]";
	}
}
