package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.*;

public class FlightBookingSystem {

	private final LocalDate systemDate = LocalDate.parse("2020-11-11");

	private final Map<Integer, Customer> customers = new TreeMap<>();
	private final Map<Integer, Flight> flights = new TreeMap<>();
	private final Map<Integer, Booking> bookings = new TreeMap<>();

	public LocalDate getSystemDate() {
		return systemDate;
	}

	public List<Flight> getFlights() {
		List<Flight> out = new ArrayList<>(flights.values());
		return Collections.unmodifiableList(out);
	}

	public List<Customer> getCustomers() {
		List<Customer> out = new ArrayList<>(customers.values());
		System.out.println(customers.size());
		return Collections.unmodifiableList(out);
	}

	public List<Booking> getBookings() {
		List<Booking> out = new ArrayList<>(bookings.values());
		System.out.println(bookings.size());
		return Collections.unmodifiableList(out);
	}

	public Flight getFlightByID(int id) throws FlightBookingSystemException {
		if (!flights.containsKey(id)) {
			throw new FlightBookingSystemException("There is no flight with that ID.");
		}
		return flights.get(id);
	}

	public Customer getCustomerByID(int id) throws FlightBookingSystemException {
		if (!customers.containsKey(id)) {
			throw new FlightBookingSystemException("There is no customer with that ID.");
		}
		return customers.get(id);
	}

	public Booking getBookingById(int id) throws FlightBookingSystemException {
		if (!bookings.containsKey(id)) {
			throw new FlightBookingSystemException("There is no customer with that ID.");
		}
		return bookings.get(id);
	}

	public void addFlight(Flight flight) throws FlightBookingSystemException {
		if (flights.containsKey(flight.getId())) {
			throw new IllegalArgumentException("Duplicate flight ID.");
		}
		for (Flight existing : flights.values()) {
			if (existing.getFlightNumber().equals(flight.getFlightNumber())
					&& existing.getDepartureDate().isEqual(flight.getDepartureDate())) {
				throw new FlightBookingSystemException(
						"There is a flight with same " + "number and departure date in the system");
			}
		}
		flights.put(flight.getId(), flight);
	}

	public void addCustomer(Customer customer) throws FlightBookingSystemException {
		if (customers.containsKey(customer.getId())) {
			throw new IllegalArgumentException("Duplicate customer ID.");
		}
		for (Customer existing : customers.values()) {
			if (existing.getPhone().equals(customer.getPhone())) {
				throw new FlightBookingSystemException("There is a customer with same phone number");
			}
		}
		customers.put(customer.getId(), customer);
	}
	
	public void addBooking(Booking booking) {
		bookings.put(booking.getCustomer().getId(), booking);
	}
	
	public ArrayList<Customer> deleteCustomer(int row) {
		ArrayList<Customer> newCustomers = new ArrayList<Customer>();
		for(int i = 1 ; i <= customers.size() ; i ++) {
			if(row!=i) {
				newCustomers.add(customers.get(i));
			}
		}
		
		
		
		return newCustomers;
		
	}
}
