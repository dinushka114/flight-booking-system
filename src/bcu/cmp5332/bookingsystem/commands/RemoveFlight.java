package bcu.cmp5332.bookingsystem.commands;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import bcu.cmp5332.bookingsystem.data.DataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class RemoveFlight implements Command, DataManager{
	
public final String RESOURCE = "./resources/data/flights.txt";
	
	public RemoveFlight(FlightBookingSystem fbs , int row) throws IOException {
		deleteFlight(fbs , row);
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
		// TODO Auto-generated method stub
		
	}
	
	public void deleteFlight(FlightBookingSystem fbs , int id) throws IOException {
		try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
			int temp_id = 1;
            for (Flight flight: fbs.deleteFlight(id)) {
                out.print(temp_id + SEPARATOR);
                out.print(flight.getFlightNumber() + SEPARATOR);
                out.print(flight.getOrigin() + SEPARATOR);
                out.print(flight.getDestination() + SEPARATOR);
                out.print(flight.getDepartureDate() + SEPARATOR);
                out.println();
                temp_id+=1;
            }
        }
	}

}