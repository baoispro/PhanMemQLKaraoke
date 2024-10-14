package entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class HoaDon implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1144303849894693296L;
	@Id
	private String maHoaDon;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MaNV")
	private NhanVien nv;
	private String tenNV;
	private String tenKH;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MaKH")
	private KhachHang kh;
	private Date ngayLapHD;
	private LocalTime thoiGianTraPhong;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MaKM")
	private KhuyenMai km;

	public HoaDon() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HoaDon(String maHoaDon) {
		super();
		this.maHoaDon = maHoaDon;
	}

	public HoaDon(String maHoaDon, NhanVien nv, String tenNV, String tenKH, KhachHang kh, Date ngayLapHD,
			LocalTime thoiGianTraPhong, KhuyenMai km) {
		super();
		this.maHoaDon = maHoaDon;
		this.nv = nv;
		this.tenNV = tenNV;
		this.tenKH = tenKH;
		this.kh = kh;
		this.ngayLapHD = ngayLapHD;
		this.thoiGianTraPhong = thoiGianTraPhong;
		this.km = km;
	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public NhanVien getNv() {
		return nv;
	}

	public void setNv(NhanVien nv) {
		this.nv = nv;
	}

	public String getTenNV() {
		return tenNV;
	}

	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}

	public String getTenKH() {
		return tenKH;
	}

	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}

	public KhachHang getKh() {
		return kh;
	}

	public void setKh(KhachHang kh) {
		this.kh = kh;
	}

	public Date getNgayLapHD() {
		return ngayLapHD;
	}

	public void setNgayLapHD(Date ngayLapHD) {
		this.ngayLapHD = ngayLapHD;
	}

	public LocalTime getThoiGianTraPhong() {
		return thoiGianTraPhong;
	}

	public void setThoiGianTraPhong(LocalTime thoiGianTraPhong) {
		this.thoiGianTraPhong = thoiGianTraPhong;
	}

	public KhuyenMai getKm() {
		return km;
	}

	public void setKm(KhuyenMai km) {
		this.km = km;
	}

	@Override
	public String toString() {
		return "HoaDon [maHoaDon=" + maHoaDon + ", nv=" + nv + ", tenNV=" + tenNV + ", tenKH=" + tenKH + ", kh=" + kh
				+ ", ngayLapHD=" + ngayLapHD + ", thoiGianTraPhong=" + thoiGianTraPhong + ", km=" + km + "]";
	}

}
