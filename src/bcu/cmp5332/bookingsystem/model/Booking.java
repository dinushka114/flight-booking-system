package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;

public class Booking {

	private Customer customer;
	private Flight flight;
	private LocalDate bookingDate;
	private Float price;

	public Booking(Customer customer, Flight flight, LocalDate bookingDate , float price) {
		this.customer = customer;
		this.flight = flight;
		this.bookingDate = bookingDate;
		this.price = price;
	}

	// TODO: implementation of Getter and Setter methods
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public float getPrice() {
		return this.price;
	}
	
}
