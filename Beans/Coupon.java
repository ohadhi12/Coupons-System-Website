package com.Project3.Beans;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Coupons")
public class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int couponId;
	//@JsonIgnore
	@ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Company companyId;
	
	@Column(nullable = false)
	private CategoryType category;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private Date startDate;
	
	@Column(nullable = false)
	private Date endDate;
	
	@Column(nullable = false)
	private int amount;
	
	@Column(nullable = false)
	private double price;
	
	@Column(nullable = false)
	private String image;
	
	public Coupon() {}

	public Coupon(Company companyId, CategoryType category, String title, String description, Date startDate,
			Date endDate, int amount, double price, String image) {
		super();
		this.companyId = companyId;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}
	
	public int getCouponId() {
		return couponId;
	}
	
	public void setCompany(Company companyId) {
		this.companyId = companyId;
	}
	
	public Company getCompany() {
		return companyId;
	}

	public CategoryType getCategory() {
		return category;
	}

	public void setCategory(CategoryType category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Coupon))
			return false;
		if(this.getCouponId() == ((Coupon) obj).getCouponId())
			return true;
		return false;
			
	}

	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", companyId=" + companyId + ", category=" + category + ", title="
				+ title + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", price=" + price + ", image=" + image + "]";
	}
		}