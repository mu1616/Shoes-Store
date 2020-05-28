package com.example.store.model.stock.dto;

public class StockDTO {
	private int size;
	private int count;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "StockDTO [size=" + size + ", count=" + count + "]";
	}
	
	
	
}
