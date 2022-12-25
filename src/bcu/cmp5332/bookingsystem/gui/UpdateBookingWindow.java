package bcu.cmp5332.bookingsystem.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.commands.RemoveBooking;
import bcu.cmp5332.bookingsystem.commands.UpdateBooking;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public class UpdateBookingWindow extends JFrame implements ActionListener {

	private MainWindow mw;
	private int bookingId;
	private JTextField flightId = new JTextField();
	
	private JButton addBtn = new JButton("Update");
	private JButton cancelBtn = new JButton("Cancel");

	public UpdateBookingWindow(MainWindow mw , int row) {
		this.mw = mw;
		this.bookingId = row;
		this.initialize();

	}

	public void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {

		}

		setTitle("Update Booking");

		setSize(350, 160);
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 1));
		topPanel.add(new JLabel("Flight Id : "));
		topPanel.add(flightId);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));
		bottomPanel.add(new JLabel("     "));
		bottomPanel.add(addBtn);
		bottomPanel.add(cancelBtn);

		addBtn.addActionListener(this);
		cancelBtn.addActionListener(this);

		this.getContentPane().add(topPanel, BorderLayout.CENTER);
		this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		setLocationRelativeTo(mw);

		setVisible(true);
	}

	public void updateBooking() throws FlightBookingSystemException {

		int flightNoText = Integer.parseInt(flightId.getText());
		
		Booking book = mw.getFlightBookingSystem().getBookingById(this.bookingId);

		LocalDate currentDate = new FlightBookingSystem().getSystemDate();
		float actualPrice = book.getPrice(), cancelPayment = 0;
		Flight flight = mw.getFlightBookingSystem().getFlightByID(flightNoText);
		
		int days = Period.between(currentDate, flight.getDepartureDate()).getDays();
		
		

		if (days < 3) {
			cancelPayment = (float) (actualPrice * 0.5);
		} else if (days < 6) {
			cancelPayment = (float) (actualPrice * 0.4);
		} else if (days < 10) {
			cancelPayment = (float) (actualPrice * 0.3);
		} else if (days < 15) {
			cancelPayment = (float) (actualPrice * 0.2);
		} else {
			cancelPayment = (float) (actualPrice * 0.1);
		}

		
		Command updateBooking = new UpdateBooking(mw.getFlightBookingSystem(), book, flight);
		updateBooking.execute(mw.getFlightBookingSystem());
		JOptionPane.showMessageDialog(this,
				"You just received a  " + (actualPrice - cancelPayment) + " of " + actualPrice, "Info",
				JOptionPane.INFORMATION_MESSAGE);
		mw.refreshBookings();
		this.setVisible(false);

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == addBtn) {
			try {
				updateBooking();
			} catch (FlightBookingSystemException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			}
		} else if (ae.getSource() == cancelBtn) {
			this.setVisible(false);
		}

	}
}
