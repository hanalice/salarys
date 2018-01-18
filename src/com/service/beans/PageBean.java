package com.service.beans;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>Title:       动态查询子系统_[分页实体类]/p>
 * <p>Description: [封装分页相关信息]</p>
 * @author Administrator
*/
public class PageBean {
	private int pageNo = 1; // 第几页 默认第1页
	private int pageCount; // 总页数
	private long total;// 数据总的条数
	private int pageSize = 10;
	private int cacheSize = 1; // 固定缓存
	private int beginPage = 1;
	private int endPage;
	private List<?> result;    //查询结果
	private Object objectResult;
	private String searchDate;
	private String startDate;
	private String endDate;
	private int successFlag = 200;
	public int getPageNo() {
		return pageNo;
	}
	public PageBean setPageNo(int pageNo) {
		this.pageNo = pageNo;
		return this;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getPageSize() {
		return pageSize;
	}
	public PageBean setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}
	public int getCacheSize() {
		return cacheSize;
	}
	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}
	public int getBeginPage() {
		return beginPage;
	}
	public PageBean setBeginPage(int beginPage) {
		this.beginPage = beginPage;
		return this;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public Object getObjectResult() {
		return objectResult;
	}
	public void setObjectResult(Object objectResult) {
		this.objectResult = objectResult;
	}
	public String getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getSuccessFlag() {
		return successFlag;
	}
	public void setSuccessFlag(int successFlag) {
		this.successFlag = successFlag;
	}
	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result, long total) {
		this.result = result;
		this.total = total;
		Double totaltemp = new BigDecimal(total).doubleValue();
		Double pageSize = new BigDecimal(this.getPageSize()).doubleValue();
		this.setPageCount(new BigDecimal(Math.ceil(totaltemp/(pageSize))).intValue());
	}

}
