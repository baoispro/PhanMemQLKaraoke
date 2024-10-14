package entity;

import java.io.Serializable;
import java.time.LocalTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ChiTietPhieuDatPhong implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4088179366482961481L;
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "MaP")
	private Phong phong;
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "MaPDP")
	private PhieuDatPhong pdp;
	private LocalTime thoiGianSuDung;

	public ChiTietPhieuDatPhong() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChiTietPhieuDatPhong(Phong phong, PhieuDatPhong pdp, LocalTime thoiGianSuDung) {
		super();
		this.phong = phong;
		this.pdp = pdp;
		this.thoiGianSuDung = thoiGianSuDung;
	}

	public Phong getPhong() {
		return phong;
	}

	public void setPhong(Phong phong) {
		this.phong = phong;
	}

	public PhieuDatPhong getPdp() {
		return pdp;
	}

	public void setPdp(PhieuDatPhong pdp) {
		this.pdp = pdp;
	}

	public LocalTime getThoiGianSuDung() {
		return thoiGianSuDung;
	}

	public void setThoiGianSuDung(LocalTime thoiGianSuDung) {
		this.thoiGianSuDung = thoiGianSuDung;
	}

	@Override
	public String toString() {
		return "ChiTietPhieuDatPhong [phong=" + phong + ", pdp=" + pdp + ", thoiGianSuDung=" + thoiGianSuDung + "]";
	}

}
