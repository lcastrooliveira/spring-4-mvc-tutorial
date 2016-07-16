package guru.springframework.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import guru.springframework.enums.OrderStatus;

@Entity
@Table(name="ORDERS")
public class Order extends AbstractDomainObject {

	@ManyToOne
	private Customer customer;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	private Date dateShipped;
	
	@Embedded
	private Address shippingAddress;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="order", orphanRemoval=true)
	private List<OrderLine> orderLines = new ArrayList<>();
	
	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	
	public void addOrderLineToOrder(OrderLine orderLine) {
		orderLine.setOrder(this);
		orderLines.add(orderLine);
	}
	
	public void removeOrderLine(OrderLine orderLine) {
		orderLine.setOrder(null);
		orderLines.remove(orderLine);
	}

	public Date getDateShipped() {
		return dateShipped;
	}

	public void setDateShipped(Date dateShipped) {
		this.dateShipped = dateShipped;
	}
}
