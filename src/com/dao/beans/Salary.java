package com.dao.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="salary")
@PersistenceContext
public class Salary {

	/**
	 * the volume order is exactly the same as follows, new add colume poison before jiaxiang colume
	 * @updated at 2018/1/29
	 */
	private String id;
	private String name;
	private String year;
	private String month;
	private Float gangwei;
	private Float xinji;
	private Float jiaotong;
	private Float liangyou;
	private Float one_child;
	private Float poison;
	private Float jiaxiang;
	private Float increase;
	private Float yanglao;
	private Float gongjijin;
	private Float yiliao;
	private Float shiye;
	private Float annual;
	private Float personal_income_tax;
	private Float gonghui;
	private Float kouxiang;
	private Float decrease;
	private Float total;
	private String remark;
	
	public Salary() {
		super();
	}
	public Salary(String name, String year, String month, Float total,
			String remark) {
		super();
		this.name = name;
		this.year = year;
		this.month = month;
		this.total = total;
		this.remark = remark;
	}
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "id", insertable = true, updatable = false, nullable = false, length = 32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Float getGangwei() {
		return gangwei;
	}
	public void setGangwei(Float gangwei) {
		this.gangwei = gangwei;
	}
	public Float getXinji() {
		return xinji;
	}
	public void setXinji(Float xinji) {
		this.xinji = xinji;
	}
	public Float getJiaotong() {
		return jiaotong;
	}
	public void setJiaotong(Float jiaotong) {
		this.jiaotong = jiaotong;
	}
	public Float getLiangyou() {
		return liangyou;
	}
	public void setLiangyou(Float liangyou) {
		this.liangyou = liangyou;
	}
	public Float getOne_child() {
		return one_child;
	}
	public void setOne_child(Float one_child) {
		this.one_child = one_child;
	}
	
	public Float getPoison() {
		return poison;
	}
	public void setPoison(Float poison) {
		this.poison = poison;
	}
	public Float getJiaxiang() {
		return jiaxiang;
	}
	public void setJiaxiang(Float jiaxiang) {
		this.jiaxiang = jiaxiang;
	}
	public Float getIncrease() {
		return increase;
	}
	public void setIncrease(Float increase) {
		this.increase = increase;
	}
	public Float getYanglao() {
		return yanglao;
	}
	public void setYanglao(Float yanglao) {
		this.yanglao = yanglao;
	}
	public Float getGongjijin() {
		return gongjijin;
	}
	public void setGongjijin(Float gongjijin) {
		this.gongjijin = gongjijin;
	}
	public Float getYiliao() {
		return yiliao;
	}
	public void setYiliao(Float yiliao) {
		this.yiliao = yiliao;
	}
	public Float getShiye() {
		return shiye;
	}
	public void setShiye(Float shiye) {
		this.shiye = shiye;
	}
	public Float getAnnual() {
		return annual;
	}
	public void setAnnual(Float annual) {
		this.annual = annual;
	}
	public Float getPersonal_income_tax() {
		return personal_income_tax;
	}
	public void setPersonal_income_tax(Float personal_income_tax) {
		this.personal_income_tax = personal_income_tax;
	}
	public Float getGonghui() {
		return gonghui;
	}
	public void setGonghui(Float gonghui) {
		this.gonghui = gonghui;
	}
	public Float getKouxiang() {
		return kouxiang;
	}
	public void setKouxiang(Float kouxiang) {
		this.kouxiang = kouxiang;
	}
	public Float getDecrease() {
		return decrease;
	}
	public void setDecrease(Float decrease) {
		this.decrease = decrease;
	}
	public Float getTotal() {
		return total;
	}
	public void setTotal(Float total) {
		this.total = total;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
