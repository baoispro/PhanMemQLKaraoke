package entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LoaiDichVu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6694217165188703533L;
	@Id
	private String maLoaiDichVu;
	private String tenLoaiDichVu;

	public LoaiDichVu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoaiDichVu(String maLoaiDichVu) {
		super();
		this.maLoaiDichVu = maLoaiDichVu;
	}

	public LoaiDichVu(String maLoaiDichVu, String tenLoaiDichVu) {
		super();
		this.maLoaiDichVu = maLoaiDichVu;
		this.tenLoaiDichVu = tenLoaiDichVu;
	}

	public String getMaLoaiDichVu() {
		return maLoaiDichVu;
	}

	public void setMaLoaiDichVu(String maLoaiDichVu) {
		this.maLoaiDichVu = maLoaiDichVu;
	}

	public String getTenLoaiDichVu() {
		return tenLoaiDichVu;
	}

	public void setTenLoaiDichVu(String tenLoaiDichVu) {
		this.tenLoaiDichVu = tenLoaiDichVu;
	}

	@Override
	public String toString() {
		return "LoaiDichVu [maLoaiDichVu=" + maLoaiDichVu + ", tenLoaiDichVu=" + tenLoaiDichVu + "]";
	}

}
