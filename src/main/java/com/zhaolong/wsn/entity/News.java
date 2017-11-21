package com.zhaolong.wsn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "News")
public class News {
	@Id
    @GeneratedValue
    private Long id;

    @Column(name = "created")
    private Long created = System.currentTimeMillis();

    @Column(name = "createTime", nullable=true)
    private String createTime;
    
    @Column(name = "title", nullable=true)
    private String title;
    

	@Column(name = "desc", nullable=true)
    private String desc;

	@Column(name = "headImg", nullable=true)
    private String headImg;

    @Column(name = "readCount", nullable=true)
    private int readCount;

    @Column(name = "isDeleted", nullable=true)
    private int isDeleted;
    
    @Column(name = "author", nullable=true)
    private String author;
    
    @Column(name = "isHot", nullable=true)
    private int isHot;
    
    @Column(name = "newsAddress", nullable=true)
    private String newsAddress;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getIsHot() {
		return isHot;
	}

	public void setIsHot(int isHot) {
		this.isHot = isHot;
	}

	public String getNewsAddress() {
		return newsAddress;
	}

	public void setNewsAddress(String newsAddress) {
		this.newsAddress = newsAddress;
	}
    
    
}
