package com.dao.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="salary_subsidy")
@PersistenceContext
public class SalarySubsidy {
	
	/**
	 * the volume order is exactly the same as follows, new add colume holiday before increase colume
	 * @updated at 2018/1/29
	 */
	private String id;
	private String name;
	private String year;
	private String month;
	private Float gangwei;
	private Float gongzuoliang;
	private Float jiaxiang1;
	private Float jiaxiang2;
	private Float holiday;
	private Float increase;
	private Float personal_income_tax;
	private Float kouxiang1;
	private Float kouxiang2;
	private Float decrease;
	private Float total;
	private String remark;
	
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
	public Float getGongzuoliang() {
		return gongzuoliang;
	}
	public void setGongzuoliang(Float gongzuoliang) {
		this.gongzuoliang = gongzuoliang;
	}
	public Float getJiaxiang1() {
		return jiaxiang1;
	}
	public void setJiaxiang1(Float jiaxiang1) {
		this.jiaxiang1 = jiaxiang1;
	}
	public Float getJiaxiang2() {
		return jiaxiang2;
	}
	public void setJiaxiang2(Float jiaxiang2) {
		this.jiaxiang2 = jiaxiang2;
	}
	public Float getHoliday() {
		return holiday;
	}
	public void setHoliday(Float holiday) {
		this.holiday = holiday;
	}
	public Float getIncrease() {
		return increase;
	}
	public void setIncrease(Float increase) {
		this.increase = increase;
	}
	public Float getPersonal_income_tax() {
		return personal_income_tax;
	}
	public void setPersonal_income_tax(Float personal_income_tax) {
		this.personal_income_tax = personal_income_tax;
	}
	public Float getKouxiang1() {
		return kouxiang1;
	}
	public void setKouxiang1(Float kouxiang1) {
		this.kouxiang1 = kouxiang1;
	}
	public Float getKouxiang2() {
		return kouxiang2;
	}
	public void setKouxiang2(Float kouxiang2) {
		this.kouxiang2 = kouxiang2;
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
