package bcu.cmp5332.bookingsystem.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

public class AddBookingWindow extends JFrame implements ActionListener{
	
	private MainWindow mw;
    private JTextField customerId = new JTextField();
    private JTextField flightId = new JTextField();
    private JTextField departureDate = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");
    
    
    public AddBookingWindow(MainWindow mw) {
    	this.mw = mw;
    	this.initialize();
    }
    
    public void initialize() {
    	  try {
              UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          } catch (Exception ex) {

          }
    	  
    	  setTitle("Add New Booking");
    	  
    	  setSize(350, 160);
          JPanel topPanel = new JPanel();
          topPanel.setLayout(new GridLayout(3, 2));
          topPanel.add(new JLabel("Customer Id : "));
          topPanel.add(customerId);
          topPanel.add(new JLabel("Flight Id : "));
          topPanel.add(flightId);
          topPanel.add(new JLabel("Depature Date : "));
          topPanel.add(departureDate);
          

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
    
    public void addBooking() {
    	try {
    		int customerIdText = Integer.parseInt(customerId.getText());
    		int flightNoText = Integer.parseInt(flightId.getText());
    		LocalDate departureDateText = null;
    		try {
    			departureDateText = LocalDate.parse(departureDate.getText());
            }
            catch (DateTimeParseException dtpe) {
                throw new FlightBookingSystemException("Date must be in YYYY-DD-MM format");
            }
    		Command AddBooking = new AddBooking(customerIdText , flightNoText , departureDateText);
    		AddBooking.execute(mw.getFlightBookingSystem());
    		mw.displayBookings();
    		this.setVisible(false);
    	}catch(FlightBookingSystemException e) {
    		JOptionPane.showMessageDialog(null, e.getMessage() , "Error", JOptionPane.WARNING_MESSAGE);
    	}
    }

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == addBtn) {
            addBooking();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
		
	}
}
