package bcu.cmp5332.bookingsystem.commands;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import bcu.cmp5332.bookingsystem.data.DataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class RemoveCustomer implements Command, DataManager  {
	
	
	public final String RESOURCE = "./resources/data/customers.txt";
	
	public RemoveCustomer(FlightBookingSystem fbs , String customerId) throws IOException, FlightBookingSystemException {
		deleteCustomer(fbs , customerId);
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
	public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {

		
	}
	
	public void deleteCustomer(FlightBookingSystem flightBookingSystem , String customerId) throws IOException, FlightBookingSystemException {
		
		flightBookingSystem.getCustomerByPhone(customerId).setIsDeleted(true);
		List<Customer> customers = flightBookingSystem.getCustomers();
		
		try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
			int temp_id = 1;

			for(Customer customer:customers) {
				out.print(temp_id+SEPARATOR);
                out.print(customer.getName() + SEPARATOR);
                out.print(customer.getPhone() + SEPARATOR);
                out.print(customer.getEmail() + SEPARATOR);
                out.print(customer.getIsDeleted() + SEPARATOR);
                out.print("\n");
                temp_id++;
			}
            
            
        }

	}
	
}
