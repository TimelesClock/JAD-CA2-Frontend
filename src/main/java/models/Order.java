package models;

import java.sql.Date;
import java.util.ArrayList;

public class Order {
	private int orderId;
	private Date orderDate;
	private float subtotal;
	private ArrayList<OrderItem> orderItems;

	public Order() {
		orderItems = new ArrayList<>();
	}

	// Getters and Setters
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public ArrayList<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(ArrayList<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public void addOrderItem(OrderItem item) {
		orderItems.add(item);
	}
}
