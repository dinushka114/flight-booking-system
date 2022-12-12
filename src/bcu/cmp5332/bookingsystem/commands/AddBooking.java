package bcu.cmp5332.bookingsystem.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

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

	public final String RESOURCE = "./resources/data/bookings.txt";

	public AddBooking(int customerId, int flightId, LocalDate date) {
		this.customerId = customerId;
		this.flightId = flightId;
		this.date = date;
	}

	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		Flight flight = flightBookingSystem.getFlightByID(flightId);
		Customer customer = flightBookingSystem.getCustomerByID(customerId);
		Booking booking = new Booking(customer, flight, date);
		customer.addBooking(booking);
		flight.addPassenger(customer);
		try {
			this.storeData(flightBookingSystem);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Booking added.");
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
			out.println();
			out.print(this.customerId + SEPARATOR);
			out.print(this.flightId + SEPARATOR);
			out.print(this.date + SEPARATOR);
			out.close();
			br.close();
			fr.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		

	}
}
