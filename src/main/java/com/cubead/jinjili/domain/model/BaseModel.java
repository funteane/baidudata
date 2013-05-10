package com.cubead.jinjili.domain.model;

import java.util.Date;

import com.cubead.jinjili.index.indexer.IndexMangerFactory.IndexType;

public class BaseModel {
	
	private String key;
	
	private Date createDate;
	
	private String deleted;
	
	private IndexType indexType;
	
	
	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public IndexType getIndexType() {
		return indexType;
	}

	public void setIndexType(IndexType indexType) {
		this.indexType = indexType;
	}
	
	
	

}
