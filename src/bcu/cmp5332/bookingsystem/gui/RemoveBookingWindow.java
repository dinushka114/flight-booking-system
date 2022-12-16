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
import bcu.cmp5332.bookingsystem.commands.RemoveBooking;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

public class RemoveBookingWindow extends JFrame implements ActionListener{
	
	private MainWindow mw;
    private JTextField customerId = new JTextField();
    private JTextField flightId = new JTextField();
    private JTextField departureDate = new JTextField();

    private JButton addBtn = new JButton("Remove");
    private JButton cancelBtn = new JButton("Cancel");
    
    
    public RemoveBookingWindow(MainWindow mw) {
    	this.mw = mw;
    	this.initialize();
    }
    
    public void initialize() {
    	  try {
              UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          } catch (Exception ex) {

          }
    	  
    	  setTitle("Cancel Booking");
    	  
    	  setSize(350, 160);
          JPanel topPanel = new JPanel();
          topPanel.setLayout(new GridLayout(2, 2));
          topPanel.add(new JLabel("Customer Id : "));
          topPanel.add(customerId);
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
    
    public void removeBooking() throws FlightBookingSystemException {
    	int customerIdText = Integer.parseInt(customerId.getText());
		int flightNoText = Integer.parseInt(flightId.getText());
		
		Command removeBooking = new RemoveBooking(customerIdText , flightNoText);
		removeBooking.execute(mw.getFlightBookingSystem());
		mw.refreshBookings();
		this.setVisible(false);
		
    }

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == addBtn) {
            try {
				removeBooking();
			} catch (FlightBookingSystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
		
	}
}
