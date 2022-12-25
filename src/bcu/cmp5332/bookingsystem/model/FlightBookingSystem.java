package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.*;

public class FlightBookingSystem {

	private final LocalDate systemDate = LocalDate.parse(LocalDate.now().toString());
	

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
		return Collections.unmodifiableList(out);
	}

	public List<Booking> getBookings() {
		List<Booking> out = new ArrayList<>(bookings.values());
		return Collections.unmodifiableList(out);
	}

	public Flight getFlightByID(int id) throws FlightBookingSystemException {
		if (!flights.containsKey(id)) {
			throw new FlightBookingSystemException("There is no flight with that ID. ");
		}
		return flights.get(id);
	}
	
	public Flight getFlightByFlightNo(String flightNo) throws FlightBookingSystemException {
		Flight flightByNo = null;
		for(Flight flight:flights.values()) {
			if(flight.getFlightNumber().equals(flightNo)) {
				flightByNo =  flight;
			}
		}

		return flightByNo;
	}

	public Customer getCustomerByID(int id) throws FlightBookingSystemException {
		if (!customers.containsKey(id)) {
			throw new FlightBookingSystemException("There is no customer with that ID.");
		}
		return customers.get(id);
	}

	public Booking getBookingById(int id) throws FlightBookingSystemException {
		System.out.println(bookings.size());
		if (!bookings.containsKey(id)) {
			throw new FlightBookingSystemException("There is no bookings with that ID.");
		}
		return bookings.get(id);
	}
	
	public Booking getBookingByCustIdAndFlightId(int custId , int flightId) {
		Booking isExists = null;
		for(Booking booking:bookings.values()) {
			if(booking.getCustomer().getId()==custId && booking.getFlight().getId()==flightId) {
				isExists = booking;
			}
		}
		
		return isExists;
	}
	
	
	public List<Flight> getActualFlights() {
		
		List<Flight> out = new ArrayList<>();
		for(Flight flight:flights.values()) {
			if(flight.getIsDeleted()==false && systemDate.compareTo(flight.getDepartureDate()) < 0) {
				out.add(flight);
				System.out.println(flight.getFlightNumber());
			}
		}
		
		return out;
	}
	

	public void addFlight(Flight flight) throws FlightBookingSystemException {
		if (flights.containsKey(flight.getId())) {
			throw new FlightBookingSystemException("Duplicate flight ID.");
		}
		
		if(this.getFlightByFlightNo(flight.getFlightNumber())!=null) {
			throw new FlightBookingSystemException("Duplicate flight No.");
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
		
		for (Customer existing : customers.values()) {
			if (existing.getEmail().equals(customer.getEmail())) {
				throw new FlightBookingSystemException("There is a customer with same Email");
			}
		}
		
		customers.put(customer.getId(), customer);
	}
	
	public Customer getCustomerByPhone(String phone) {
		Customer selectedCustomer = null;
		
		for(Customer customer:customers.values()) {
				if(customer.getPhone().equals(phone)) {
					selectedCustomer = customer;
				}
		}
		
		return selectedCustomer;
		
	}
	
	public void addBooking(Booking booking) {
		bookings.put(booking.getCustomer().getId(), booking);
	}
	
	public TreeMap<Integer , Customer> deleteCustomer(int row) throws FlightBookingSystemException {
		customers.remove(row);
		return (TreeMap<Integer, Customer>) customers;
	}
	
	
	public TreeMap<Integer , Flight> deleteFlight(int row) {		
		flights.remove(row);
		return (TreeMap<Integer, Flight>) flights;
	}
	
	public TreeMap<Integer , Booking> cancelBooking(int custId , int flightId){
		for(int i = 1 ; i <= bookings.size() ; i ++) {
			if(bookings.get(i).getCustomer().getId()==custId && bookings.get(i).getFlight().getId()==flightId) {
				bookings.remove(i);
				break;
			}
		}
		
		return (TreeMap<Integer, Booking>) bookings;
	}
	
	
	
}
