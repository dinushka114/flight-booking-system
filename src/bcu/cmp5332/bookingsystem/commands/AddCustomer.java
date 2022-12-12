package bcu.cmp5332.bookingsystem.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import bcu.cmp5332.bookingsystem.data.DataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class AddCustomer  implements Command , DataManager {

    private final String name;
    private final String phone;
    
    public final String RESOURCE = "./resources/data/customers.txt";

    public AddCustomer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
    	 int maxId = 0;
         if (flightBookingSystem.getCustomers().size() > 0) {
             int lastIndex = flightBookingSystem.getCustomers().size() - 1;
             maxId = flightBookingSystem.getCustomers().get(lastIndex).getId();
         }
         
         Customer customer = new Customer(++maxId, name,phone);
         flightBookingSystem.addCustomer(customer);
         
         try {
        	 this.storeData(flightBookingSystem);
         }catch(Exception e) {
        	 e.printStackTrace();
         }
         
         System.out.println("Customer #" + customer.getId() + " added.");
    }

	@Override
	public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeData(FlightBookingSystem fbs) throws IOException {
		try {
			int lastIndex = fbs.getCustomers().size() - 1;
            int id = fbs.getCustomers().get(lastIndex).getId();
			File file = new File(RESOURCE);
			FileWriter fr = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(fr);
			PrintWriter out = new PrintWriter(br);
			out.println();
			out.print(id+SEPARATOR);
			out.print(this.name + SEPARATOR);
			out.print(this.phone+ SEPARATOR);
			out.close();
			br.close();
			fr.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
