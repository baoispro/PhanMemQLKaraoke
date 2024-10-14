package entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Phong implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8002735357507373611L;
	@Id	
	private String maPhong;
	private String tenPhong;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MaLP")
	private LoaiPhong lp;
	private String tinhTrang;

	public Phong() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Phong(String maPhong) {
		super();
		this.maPhong = maPhong;
	}

	public Phong(String maPhong, String tenPhong, LoaiPhong lp, String tinhTrang) {
		super();
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
		this.lp = lp;
		this.tinhTrang = tinhTrang;
	}

	public String getMaPhong() {
		return maPhong;
	}

	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}

	public String getTenPhong() {
		return tenPhong;
	}

	public void setTenPhong(String tenPhong) {
		this.tenPhong = tenPhong;
	}

	public LoaiPhong getLp() {
		return lp;
	}

	public void setLp(LoaiPhong lp) {
		this.lp = lp;
	}

	public String getTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}

	@Override
	public String toString() {
		return "Phong [maPhong=" + maPhong + ", tenPhong=" + tenPhong + ", lp=" + lp + ", tinhTrang=" + tinhTrang + "]";
	}

}
