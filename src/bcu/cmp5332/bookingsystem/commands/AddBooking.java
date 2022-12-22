package bcu.cmp5332.bookingsystem.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;

import bcu.cmp5332.bookingsystem.data.DataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class AddBooking implements Command, DataManager {

	private final int customerId;
	private final int flightId;
	private final LocalDate date;
	private float price;

	public final String RESOURCE = "./resources/data/bookings.txt";
	

	public AddBooking(int customerId, int flightId) throws FlightBookingSystemException {
		this.customerId = customerId;
		this.flightId = flightId;
		this.date = new FlightBookingSystem().getSystemDate();
	}

	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		Flight flight = flightBookingSystem.getFlightByID(this.flightId);
		
		this.price = this.calculatePrice(flight);
		
		int noOfBookings = flight.getPassengers().size();
		
		if(flight.getIsDeleted()) {
			throw new FlightBookingSystemException("Sorry...Flight is not available");
		}
		
		if(noOfBookings==flight.getCapacity()) {
			throw new FlightBookingSystemException("Sorry...All seats were booked");
		}
		
		
		Customer customer = flightBookingSystem.getCustomerByID(customerId);
		if(customer.getIsDeleted()) {
			throw new FlightBookingSystemException("Sorry...Customer is not available");
		}
		
		
		Booking booking = new Booking(customer, flight, date , price);
		customer.addBooking(booking);
		flight.addPassenger(customer);
		
		try {
			this.storeData(flightBookingSystem);
			flightBookingSystem.addBooking(booking);
			System.out.println("Booking size = "+flightBookingSystem.getBookings().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Booking added.");
	}
	
	
	public Float calculatePrice(Flight flight) throws FlightBookingSystemException {
		
		FlightBookingSystem fbs = new FlightBookingSystem();
		float flightPrice = 0;
		try {
			LocalDate currentDate = fbs.getSystemDate();
			int days = Period.between(currentDate , flight.getDepartureDate()).getDays();
			
			if(days<3 || flight.getCapacity()<10) {
				flightPrice = 800;
			}else if(days <7 || flight.getCapacity()<20) {
				flightPrice = 700;
			}else if(days < 15 || flight.getCapacity()<30) {
				flightPrice = 550;
			}else if(days < 25 || flight.getCapacity()<70) {
				flightPrice = 400;
			}else if(days<30 || flight.getCapacity()<100) {
				flightPrice = 380;
			}else {
				flightPrice = 350;
			}
			
			
		}catch(Exception e) {
			throw new FlightBookingSystemException("Something went wrong");
		}
		
		
		return flightPrice;
	}

	@Override
	public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeData(FlightBookingSystem fbs) throws IOException {
		
		try {
			File file = new File(RESOURCE);
			FileWriter fr = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(fr);
			PrintWriter out = new PrintWriter(br);
			out.print(this.customerId + SEPARATOR);
			out.print(this.flightId + SEPARATOR);
			out.print(this.date + SEPARATOR);
			out.print(this.price + SEPARATOR);
			out.print("\n");
			out.close();
			br.close();
			fr.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		

	}
}
