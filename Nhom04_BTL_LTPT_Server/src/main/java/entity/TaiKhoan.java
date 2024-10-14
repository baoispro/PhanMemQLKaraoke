package entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class TaiKhoan implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6567295868146321580L;
	@Id
	private String maTK;
	private String tenDangNhap;
	private String matKhau;
	@OneToOne
	@JoinColumn(name = "MaNV", unique = true, nullable = false)
	private NhanVien nv;

	public TaiKhoan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TaiKhoan(String maTK) {
		super();
		this.maTK = maTK;
	}

	public TaiKhoan(String maTK, String tenDangNhap, String matKhau, NhanVien nv) {
		super();
		this.maTK = maTK;
		this.tenDangNhap = tenDangNhap;
		this.matKhau = matKhau;
		this.nv = nv;
	}

	public String getMaTK() {
		return maTK;
	}

	public void setMaTK(String maTK) {
		this.maTK = maTK;
	}

	public String getTenDangNhap() {
		return tenDangNhap;
	}

	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public NhanVien getNhanVien() {
		return nv;
	}

	public void setNhanVien(NhanVien nv) {
		this.nv = nv;
	}

	@Override
	public String toString() {
		return "TaiKhoan [maTK=" + maTK + ", tenDangNhap=" + tenDangNhap + ", matKhau=" + matKhau + ", nv="
				+ nv + "]";
	}
}
