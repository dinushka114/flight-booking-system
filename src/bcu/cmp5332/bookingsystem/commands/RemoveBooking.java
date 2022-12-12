package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class RemoveBooking implements Command {

    private final int customerId;
    private final int flightId;
    

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
         System.out.println("Booking removed.");
    }
}
