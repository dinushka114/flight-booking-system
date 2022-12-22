package bcu.cmp5332.bookingsystem.commands;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import bcu.cmp5332.bookingsystem.data.DataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class UpdateBooking implements Command, DataManager {
	
	private final FlightBookingSystem fbs;
	private final Booking booking;
	private final Flight flight;
	
	public final String RESOURCE = "./resources/data/bookings.txt";
	
	public UpdateBooking(FlightBookingSystem fbs , Booking book , Flight flight) throws FlightBookingSystemException {
		this.fbs = fbs;
		this.booking = book;
		this.flight = flight;
	}

	@Override
	public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeData(FlightBookingSystem fbs) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
		
		for(Booking current:fbs.getBookings()) {
			if(current==booking) {
				current.setFlight(flight);
			}
		}
		try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {

			for (Booking entry : fbs.getBookings()) {
				out.print(entry.getCustomer().getId() + SEPARATOR);
				out.print(entry.getFlight().getId() + SEPARATOR);
				out.print(entry.getBookingDate() + SEPARATOR);
				out.print(entry.getPrice() + SEPARATOR);
				out.print("\n");
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(booking.getCustomer().getName());
		
	}
	

	
	
}
