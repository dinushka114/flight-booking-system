package bcu.cmp5332.bookingsystem.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import bcu.cmp5332.bookingsystem.data.DataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class RemoveBooking implements Command  , DataManager{

    private final int customerId;
    private final int flightId;
    
    public final String RESOURCE = "./resources/data/bookings.txt";

	public RemoveBooking(int customerId, int flightId) {
		this.customerId = customerId;
		this.flightId = flightId;
	}


	@Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		Flight flight = flightBookingSystem.getFlightByID(flightId);
		Customer customer = flightBookingSystem.getCustomerByID(customerId);
		//Booking booking = new Booking(customer, flight, flight.getDepartureDate());
    	 customer.cancelBookingForFlight(flight);
    	 flight.removePassenger(customer);
    	 
    	 try {
    		 cancelBooking(flightBookingSystem , flightId , customerId);
    	 }catch(Exception e) {
    		 e.printStackTrace();
    	 }
    	 
         System.out.println("Booking removed.");
    }


	public void cancelBooking(FlightBookingSystem fbs, int flightId , int customerId) throws FlightBookingSystemException, IOException {
		
		Map<Integer, Booking> bookings = fbs.cancelBooking(customerId, flightId);
		
		try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
						
			for(Map.Entry<Integer, Booking> entry:bookings.entrySet()) {
                out.print(entry.getValue().getCustomer().getId() + SEPARATOR);
                out.print(entry.getValue().getFlight().getId() + SEPARATOR);
                out.print(entry.getValue().getBookingDate() + SEPARATOR);
                out.print("\n");
			}
			
        }
	}
	
	@Override
	public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void storeData(FlightBookingSystem fbs) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
