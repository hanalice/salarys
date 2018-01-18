package com.service.beans;

import java.util.List;

public class PageVBean{
	private List<?> result;
	private int start;
	private int total;
	private int beginPage;
	private int cacheSize = 1;
	private int endPage = 0;
	private int pageCount;
	private int pageNo;
	private int pageSize;
	private int successFlag = 200;
	
	public PageVBean() {

	}

	public PageVBean(List<?> result, int start, int total) {
		this.result = result;
		this.start = start;
		this.total = total;
	}
	
	public List<?> getResult() {
		return result;
	}
	public void setResult(List<?> result) {
		this.result = result;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getBeginPage() {
		return beginPage;
	}
	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}
	public int getCacheSize() {
		return cacheSize;
	}
	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getSuccessFlag() {
		return successFlag;
	}
	public void setSuccessFlag(int successFlag) {
		this.successFlag = successFlag;
	}
	
	public void setPage(int[] page) {
		this.beginPage = page[1];
		this.pageNo = page[1];
		this.pageSize = page[0];
		this.pageCount = total / pageSize;
		if (total % pageSize > 0) {
			this.pageCount++;
		}
	}
	
}
