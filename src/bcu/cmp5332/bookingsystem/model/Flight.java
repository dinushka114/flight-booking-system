package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

public class Flight {
    
    private int id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDate departureDate;

    private final Set<Customer> passengers;

    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        
        passengers = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    public String getOrigin() {
        return origin;
    }
    
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public List<Customer> getPassengers() {
        return new ArrayList<>(passengers);
    }
	
    public String getDetailsShort() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return "Flight #" + id + " - " + flightNumber + " - " + origin + " to " 
                + destination + " on " + departureDate.format(dtf);
    }

    public String getDetailsLong() {
        // TODO: implementation here
        String s =  "Flight #"+id+"\n"+
        		"Flight No: "+flightNumber+"\n"+
        		"Origin: "+origin+"\n"+
        		"Destination: "+destination+"\n"+
        		"Departure Date: "+departureDate+"\n"+
        		"---------------------------\n"+
        		"Passengers:\n";
        for(Customer c:passengers) {
        	s = s + " * Id: "+c.getId()+" - "+c.getName()+" - "+c.getPhone()+"\n";
        }
        
        s = s + passengers.size()+" passenger(s)";
        return s;
    }
    
    public void removePassenger(Customer passenger) throws FlightBookingSystemException {
    	for(Customer c:passengers) {
    		if(c.getId() == passenger.getId()) {
    			passengers.remove(passenger);
    			return;
    		}
    	}
    	throw new FlightBookingSystemException("Customer not booked in flight with this Id.");
    	
    }
    public void addPassenger(Customer passenger) throws FlightBookingSystemException {
    	for(Customer c:passengers) {
			 if(c.getId() == passenger.getId()) {
				 throw new FlightBookingSystemException("Customer already in flight.");
			 }
		 }
		 passengers.add(passenger);
        
    }
}
