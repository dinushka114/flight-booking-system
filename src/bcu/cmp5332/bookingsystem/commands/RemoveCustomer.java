package bcu.cmp5332.bookingsystem.commands;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import bcu.cmp5332.bookingsystem.data.DataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class RemoveCustomer implements Command, DataManager  {
	
	
	public final String RESOURCE = "./resources/data/customers.txt";
	
	public RemoveCustomer(FlightBookingSystem fbs , int row) throws IOException {
		deleteCustomer(fbs , row);
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
	
	public void deleteCustomer(FlightBookingSystem flightBookingSystem , int id) throws IOException {
		try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Customer customer : flightBookingSystem.deleteCustomer(id)) {
                out.print(customer.getId() + SEPARATOR);
                out.print(customer.getName() + SEPARATOR);
                out.print(customer.getPhone() + SEPARATOR);
                out.println();
            }
        }

	}
	
}
