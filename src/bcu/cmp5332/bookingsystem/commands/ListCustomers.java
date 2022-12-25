package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.util.List;

public class ListCustomers implements Command {

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Customer> customers = flightBookingSystem.getCustomers();
        int size = 0;
        for (Customer customer : customers) {
            if(customer.getIsDeleted()==false) {
            	System.out.println(customer.getDetailsShort());
            	size+=1;
            }
        }
        System.out.println(size + " customers(s)");
    }
}
