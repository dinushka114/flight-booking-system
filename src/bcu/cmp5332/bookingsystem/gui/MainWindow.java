package bcu.cmp5332.bookingsystem.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.commands.RemoveBooking;
import bcu.cmp5332.bookingsystem.commands.RemoveCustomer;
import bcu.cmp5332.bookingsystem.commands.RemoveFlight;
import bcu.cmp5332.bookingsystem.commands.UpdateBooking;
import bcu.cmp5332.bookingsystem.data.FlightDataManager;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class MainWindow extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int deleteSelectRow = 0;

	private JMenuBar menuBar;
	private JMenu adminMenu;
	private JMenu flightsMenu;
	private JMenu bookingsMenu;
	private JMenu customersMenu;

	private JMenuItem adminExit;

	private JMenuItem flightsView;
	private JMenuItem flightsAdd;
	private JMenuItem flightsDel;
	private JMenuItem showPassengers;

	private JMenuItem bookingsView;
	private JMenuItem bookingsIssue;
	private JMenuItem bookingsUpdate;
	private JMenuItem bookingsCancel;

	private JMenuItem custView;
	private JMenuItem custAdd;
	private JMenuItem custDel;
	private JMenuItem showBookingDetails;

	private FlightBookingSystem fbs;
	private FlightDataManager fdm;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public MainWindow(FlightBookingSystem fbs) {
		initialize();
		this.fbs = fbs;
		this.fdm = new FlightDataManager();
	}

	public FlightBookingSystem getFlightBookingSystem() {
		return fbs;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {

		}

		setTitle("Flight Booking Management System");

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// adding adminMenu menu and menu items
		adminMenu = new JMenu("Admin");
		menuBar.add(adminMenu);

		adminExit = new JMenuItem("Exit");
		adminMenu.add(adminExit);
		adminExit.addActionListener(this);

		// adding Flights menu and menu items
		flightsMenu = new JMenu("Flights");
		menuBar.add(flightsMenu);

		flightsView = new JMenuItem("View");
		flightsAdd = new JMenuItem("Add");
		flightsDel = new JMenuItem("Delete");
		showPassengers = new JMenuItem("Show Passengers");

		flightsMenu.add(flightsView);
		flightsMenu.add(flightsAdd);
		flightsMenu.add(flightsDel);
		flightsMenu.add(showPassengers);
		// adding action listener for Flights menu items
		for (int i = 0; i < flightsMenu.getItemCount(); i++) {
			flightsMenu.getItem(i).addActionListener(this);
		}

		// adding Bookings menu and menu items
		bookingsMenu = new JMenu("Bookings");

		bookingsView = new JMenuItem("View");
		bookingsIssue = new JMenuItem("Issue");
		bookingsCancel = new JMenuItem("Cancel");
		bookingsUpdate = new JMenuItem("Update");

		bookingsMenu.add(bookingsView);
		bookingsMenu.add(bookingsIssue);
		bookingsMenu.add(bookingsCancel);
		bookingsMenu.add(bookingsUpdate);
		// adding action listener for Bookings menu items
		for (int i = 0; i < bookingsMenu.getItemCount(); i++) {
			bookingsMenu.getItem(i).addActionListener(this);
		}

		// adding Customers menu and menu items
		customersMenu = new JMenu("Customers");
		menuBar.add(customersMenu);
		menuBar.add(bookingsMenu);

		custView = new JMenuItem("View");
		showBookingDetails = new JMenuItem("Show Booking Details");
		custAdd = new JMenuItem("Add");
		custDel = new JMenuItem("Delete");

		customersMenu.add(custView);
		customersMenu.add(custAdd);
		customersMenu.add(custDel);
		customersMenu.add(showBookingDetails);
		// adding action listener for Customers menu items
		custView.addActionListener(this);
		custAdd.addActionListener(this);
		custDel.addActionListener(this);
		showBookingDetails.addActionListener(this);

		setSize(800, 500);

		setVisible(true);
		setAutoRequestFocus(true);
		toFront();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		/*
		 * Uncomment the following line to not terminate the console app when the window
		 * is closed
		 */
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}

	/* Uncomment the following code to run the GUI version directly from the IDE */
	public static void main(String[] args) throws IOException, FlightBookingSystemException {
		FlightBookingSystem fbs = FlightBookingSystemData.load();
		new MainWindow(fbs);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == adminExit) {
			try {
				FlightBookingSystemData.store(fbs);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
			}
			System.exit(0);
		} else if (ae.getSource() == flightsView) {
			displayFlights();

		} else if (ae.getSource() == flightsAdd) {
			new AddFlightWindow(this);

		} else if (ae.getSource() == flightsDel) {
			try {
				deleteSelectedFlight(deleteSelectRow + 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (ae.getSource() == showPassengers) {
			try {
				showPassengers(deleteSelectRow + 1);
			} catch (FlightBookingSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (ae.getSource() == bookingsIssue) {
			new AddBookingWindow(this);

		} else if (ae.getSource() == bookingsUpdate) {
			new UpdateBookingWindow(this , deleteSelectRow + 1);
		}

		else if (ae.getSource() == bookingsCancel) {
			new RemoveBookingWindow(this);

		} else if (ae.getSource() == custView) {
			displayCustomers();

		} else if (ae.getSource() == showBookingDetails) {
			try {
				showBookings(deleteSelectRow + 1);
			} catch (FlightBookingSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else if (ae.getSource() == custAdd) {
			new AddCustomerWindow(this);

		} else if (ae.getSource() == custDel) {
			try {
				deleteSelectedCustomer(deleteSelectRow + 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (ae.getSource() == bookingsView) {
			displayBookings();
		}
	}


	public void showBookings(int row) throws FlightBookingSystemException {
		String bookingDetails = "";

		if (fbs.getCustomerByID(row).getBookings().size() > 0) {
			for (Booking booking : fbs.getCustomerByID(row).getBookings()) {
				bookingDetails += "Flight No: " + booking.getFlight().getFlightNumber() + "\n";
				bookingDetails += "Origin: " + booking.getFlight().getOrigin() + "\n";
				bookingDetails += "Destination: " + booking.getFlight().getDestination() + "\n";
				bookingDetails += "Booking Date: " + booking.getBookingDate() + "\n";
			}
			JOptionPane.showMessageDialog(null, bookingDetails, "Show Booking Details",
					JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public void showPassengers(int row) throws FlightBookingSystemException {
		String customerList = "";
		if (fbs.getFlightByID(row).getPassengers().size() > 0) {
			for (Customer customer : fbs.getFlightByID(row).getPassengers()) {
				customerList += customer.getDetailsShort() + "\n";
			}
			JOptionPane.showMessageDialog(null, customerList, "Show Customers", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public void deleteSelectedCustomer(int row) throws IOException {
		try {
			Command removeCustomer = new RemoveCustomer(fbs, row);
			refreshCustomers(row);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void deleteSelectedFlight(int row) throws IOException {
		try {
			Command removeFlight = new RemoveFlight(fbs, row);
			refreshFlights(row);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void refreshFlights(int id) {
		List<Flight> flightsList = fbs.getFlights();
		// headers for the table
		String[] columns = new String[] { "Flight No", "Origin", "Destination", "Departure Date", "No Of Seats" };

		Object[][] data = new Object[flightsList.size()][6];
		for (int i = 0; i < flightsList.size(); i++) {
			Flight flight = flightsList.get(i);
			if (!flight.getIsDeleted()) {
				data[i][0] = flight.getFlightNumber();
				data[i][1] = flight.getOrigin();
				data[i][2] = flight.getDestination();
				data[i][3] = flight.getDepartureDate();
				data[i][4] = flight.getCapacity();
			}
		}

		JTable table = new JTable(data, columns);
		this.getContentPane().removeAll();
		this.getContentPane().add(new JScrollPane(table));
		this.revalidate();

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				deleteSelectRow = table.rowAtPoint(e.getPoint());
			}
		});
	}

	public void refreshCustomers(int id) throws IOException, FlightBookingSystemException {
		List<Customer> customersList = fbs.getCustomers();
		// headers for the table
		String[] columns = new String[] { "Customer No", "Name", "Phone", "Email", "No of Bookings" };

		Object[][] data = new Object[customersList.size()][6];
		for (int i = 0; i < customersList.size(); i++) {
			Customer customer = customersList.get(i);
			if (!customer.getIsDeleted()) {
				data[i][0] = customer.getId();
				data[i][1] = customer.getName();
				data[i][2] = customer.getPhone();
				data[i][3] = customer.getEmail();
				data[i][4] = customer.getBookings().size();
			}

		}

		JTable table = new JTable(data, columns);
		this.getContentPane().removeAll();
		this.getContentPane().add(new JScrollPane(table));
		this.revalidate();

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				deleteSelectRow = table.rowAtPoint(e.getPoint());
				System.out.println(deleteSelectRow);
			}
		});
	}

	public void refreshBookings() {
		List<Booking> bookingList = fbs.getBookings();
		String[] columns = new String[] { "Customer Name", "Flight Id","Price" ,"Booking Date" , "Cancel/Update Price" };
		
		LocalDate currentDate = new FlightBookingSystem().getSystemDate();
		
		float cancelPayment = 0;	

		Object[][] data = new Object[bookingList.size()][6];
		for (int i = 0; i < bookingList.size(); i++) {
			Booking book = bookingList.get(i);
			if (!book.getFlight().getIsDeleted()) {
				
				int days = Period.between(currentDate, book.getFlight().getDepartureDate()).getDays();
				System.out.println("days book " + days);
				
				if (days < 3) {
					cancelPayment = (float) (book.getPrice() * 0.5);
				} else if (days < 6) {
					cancelPayment = (float) (book.getPrice() * 0.4);
				} else if (days < 10) {
					cancelPayment = (float) (book.getPrice() * 0.3);
				} else if (days < 15) {
					cancelPayment = (float) (book.getPrice() * 0.2);
				} else {
					cancelPayment = (float) (book.getPrice() * 0.1);
				}
				
				
				data[i][0] = book.getCustomer().getName();
				data[i][1] = book.getFlight().getFlightNumber();
				data[i][2] = book.getPrice();
				data[i][3] = book.getBookingDate();
				data[i][4] = cancelPayment;
			}

		}

		JTable table = new JTable(data, columns);
		this.getContentPane().removeAll();
		this.getContentPane().add(new JScrollPane(table));
		this.revalidate();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				deleteSelectRow = table.rowAtPoint(e.getPoint());
			}
		});

	}

	public void displayCustomers() {
		List<Customer> customersList = fbs.getCustomers();
		// headers for the table
		String[] columns = new String[] { "Customer No", "Name", "Phone", "Email", "No of Bookings" };

		Object[][] data = new Object[customersList.size()][6];
		for (int i = 0; i < customersList.size(); i++) {
			Customer customer = customersList.get(i);
			if (!customer.getIsDeleted()) {
				data[i][0] = customer.getId();
				data[i][1] = customer.getName();
				data[i][2] = customer.getPhone();
				data[i][3] = customer.getEmail();
				data[i][4] = customer.getBookings().size();
			}
		}

		JTable table = new JTable(data, columns);
		this.getContentPane().removeAll();
		this.getContentPane().add(new JScrollPane(table));
		this.revalidate();

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				deleteSelectRow = table.rowAtPoint(e.getPoint());
			}
		});

	}

	public void displayFlights() {
		List<Flight> flightsList = fbs.getFlights();
		int actualSize = 1;
		// headers for the table
		String[] columns = new String[] { "Flight No", "Origin", "Destination", "Departure Date", "No Of Seats" };

		Object[][] data = new Object[flightsList.size()][6];

		for (int i = 0; i < flightsList.size(); i++) {

			Flight flight = flightsList.get(i);
			LocalDate systemDate = fbs.getSystemDate();

			if (!flight.getIsDeleted() && systemDate.compareTo(flight.getDepartureDate()) < 0) {
				data[i][0] = flight.getFlightNumber();
				data[i][1] = flight.getOrigin();
				data[i][2] = flight.getDestination();
				data[i][3] = flight.getDepartureDate();
				data[i][4] = flight.getCapacity();
			}

		}

		JTable table = new JTable(data, columns);
		this.getContentPane().removeAll();
		this.getContentPane().add(new JScrollPane(table));
		this.revalidate();

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				deleteSelectRow = table.rowAtPoint(e.getPoint());
			}
		});
	}

	public void displayBookings() {
		List<Booking> bookingList = fbs.getBookings();
		String[] columns = new String[] { "Customer Name", "Flight Id","Price" ,"Booking Date" , "Cancel/Update Price" };
		
		LocalDate currentDate = new FlightBookingSystem().getSystemDate();
		
		float cancelPayment = 0;		

		Object[][] data = new Object[bookingList.size()][6];
		for (int i = 0; i < bookingList.size(); i++) {
			if (bookingList.get(i) != null) {
				Booking book = bookingList.get(i);
				if (!book.getFlight().getIsDeleted()) {
					
					int days = Period.between(currentDate, book.getFlight().getDepartureDate()).getDays();
					
					if (days < 3) {
						cancelPayment = (float) (book.getPrice() * 0.5);
					} else if (days < 6) {
						cancelPayment = (float) (book.getPrice() * 0.4);
					} else if (days < 10) {
						cancelPayment = (float) (book.getPrice() * 0.3);
					} else if (days < 15) {
						cancelPayment = (float) (book.getPrice() * 0.2);
					} else {
						cancelPayment = (float) (book.getPrice() * 0.1);
					}
					
					
					data[i][0] = book.getCustomer().getName();
					data[i][1] = book.getFlight().getFlightNumber();
					data[i][2] = book.getPrice();
					data[i][3] = book.getBookingDate();
					data[i][4] = cancelPayment;
				}
			}

		}

		JTable table = new JTable(data, columns);
		this.getContentPane().removeAll();
		this.getContentPane().add(new JScrollPane(table));
		this.revalidate();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				deleteSelectRow = table.rowAtPoint(e.getPoint());
			}
		});

	}

}
