package it.polito.tdp.genes.model;

import java.util.Objects;

public class Adiacenza {
	private String li;
	private String l2;
	private String geneId1;
	private String geneId2;
	private String type;
	public Adiacenza(String li, String l2, String geneId1, String geneId2, String type) {
		super();
		this.li = li;
		this.l2 = l2;
		this.geneId1 = geneId1;
		this.geneId2 = geneId2;
		this.type = type;
	}
	public String getLi() {
		return li;
	}
	public void setLi(String li) {
		this.li = li;
	}
	public String getL2() {
		return l2;
	}
	public void setL2(String l2) {
		this.l2 = l2;
	}
	public String getGeneId1() {
		return geneId1;
	}
	public void setGeneId1(String geneId1) {
		this.geneId1 = geneId1;
	}
	public String getGeneId2() {
		return geneId2;
	}
	public void setGeneId2(String geneId2) {
		this.geneId2 = geneId2;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public int hashCode() {
		return Objects.hash(l2, li, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adiacenza other = (Adiacenza) obj;
		return Objects.equals(l2, other.l2) && Objects.equals(li, other.li) && Objects.equals(type, other.type);
	}
	
}
