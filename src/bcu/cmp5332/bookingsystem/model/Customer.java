package bcu.cmp5332.bookingsystem.model;


import java.util.ArrayList;
import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

public class Customer {
    
    private int id;
    private String name;
    private String phone;
    private final List<Booking> bookings = new ArrayList<>();
    
    
    public Customer(int id, String name, String phone) {
		this.id = id;
		this.name = name;
		this.phone = phone;
	}
    
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public List<Booking> getBookings() {
		return bookings;
	}
	
	
	public void cancelBookingForFlight(Flight flight) throws FlightBookingSystemException {
		for(Booking b:bookings) {
			if(b.getFlight().getId() == flight.getId()) {
				bookings.remove(b);
				return;
			}
		}
		throw new FlightBookingSystemException("This flight not consists of this customer.");
		
	}
	 public void addBooking(Booking booking) throws FlightBookingSystemException {
		 for(Booking b:bookings) {
			 if(b.getCustomer().getId() == booking.getCustomer().getId()) {
				 throw new FlightBookingSystemException("Customer already booked with same flight.");
			 }
		 }
		 bookings.add(booking);
	    }
	 
	 public String getDetailsShort() {
	        return "Customer #" + id + " - " + name + " - " + phone;
	    }
	 public String getDetailsLong() {
		 String s =  "Customer #"+id+"\n"+
	        		"Name: "+name+"\n"+
	        		"Phone: "+phone+"\n"+
	        		"---------------------------\n"+
	        		"Bookings:\n";
	        for(Booking b:bookings) {
	        	s = s + " * Booking date: "+b.getBookingDate()+" for "+b.getFlight().getDetailsShort()+"\n";
	        }
	        
	        s = s + bookings.size()+" booking(s)";
	        return s;
	    }

	
}
