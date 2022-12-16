package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.DataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class AddFlight implements Command, DataManager {

	private final String flightNumber;
	private final String origin;
	private final String destination;
	private final LocalDate departureDate;
	private final int capacity;
	private final float price;

	public final String RESOURCE = "./resources/data/flights.txt";

	public AddFlight(String flightNumber, String origin, String destination, LocalDate departureDate, int capacity,
			float price) {
		this.flightNumber = flightNumber;
		this.origin = origin;
		this.destination = destination;
		this.departureDate = departureDate;
		this.capacity = capacity;
		this.price = price;
	}

	@Override
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
		int maxId = 0;
		if (flightBookingSystem.getFlights().size() > 0) {
			int lastIndex = flightBookingSystem.getFlights().size() - 1;
			maxId = flightBookingSystem.getFlights().get(lastIndex).getId();
		}

		Flight flight = new Flight(++maxId, flightNumber, origin, destination, departureDate, capacity, price);
		flightBookingSystem.addFlight(flight);

		try {
			this.storeData(flightBookingSystem);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Flight #" + flight.getId() + " added.");
	}

	@Override
	public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
		// TODO Auto-generated method stub

	}

	@Override
	public void storeData(FlightBookingSystem fbs) throws IOException {
		try {
			int lastIndex = fbs.getFlights().size() - 1;
			int id = fbs.getFlights().get(lastIndex).getId();
			File file = new File(RESOURCE);
			FileWriter fr = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(fr);
			PrintWriter out = new PrintWriter(br);
			out.println();
			out.print(id + SEPARATOR);
			out.print(this.flightNumber + SEPARATOR);
			out.print(this.origin + SEPARATOR);
			out.print(this.destination + SEPARATOR);
			out.print(this.departureDate + SEPARATOR);
			out.print(this.capacity + SEPARATOR);
			out.print(this.price + SEPARATOR);
			out.close();
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
