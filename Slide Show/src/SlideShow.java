import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.Font;

public class SlideShow extends JFrame {

	// Declare Variables
	private JPanel slidePane;
	private JPanel textPane;
	private JPanel buttonPane;
	private JPanel bottomPane;     // container to hold textPane above buttonPane
	private CardLayout card;
	private CardLayout cardText;
	private JButton btnPrev;
	private JButton btnNext;
	private JLabel lblSlide;
	private JLabel lblTextArea;

	/**
	 * Create the application.
	 */
	public SlideShow() throws HeadlessException {
		initComponent();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initComponent() {
		// Initialize variables to empty objects
		card = new CardLayout();
		cardText = new CardLayout();
		slidePane = new JPanel();
		textPane = new JPanel();
		// Set a contrasting background for the text area so text is readable.
		textPane.setBackground(new Color(30, 144, 255)); // DodgerBlue
		textPane.setVisible(true);
		buttonPane = new JPanel();
		bottomPane = new JPanel(new BorderLayout()); // hold textPane above buttons
		btnPrev = new JButton();
		btnNext = new JButton();
		lblSlide = new JLabel();
		lblTextArea = new JLabel();

		// Setup frame attributes
		setSize(800, 600);
		setLocationRelativeTo(null);
		// Updated title to reflect Product Owner change to detox/wellness focus
		setTitle("Top Detox & Wellness Destinations");
		getContentPane().setLayout(new BorderLayout(10, 10));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Setting the layouts for the panels
		slidePane.setLayout(card);
		textPane.setLayout(cardText);
		
		// logic to add each of the slides and text
		for (int i = 1; i <= 5; i++) {
			lblSlide = new JLabel();
			lblTextArea = new JLabel();
			lblSlide.setText(getResizeIcon(i)); // images unchanged; resource files expected in /resources
			lblTextArea.setText(getTextDescription(i)); // updated descriptions below

			// Improve readability: use white text for the labels in the blue background.
			lblTextArea.setForeground(Color.WHITE);
			lblTextArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
			lblTextArea.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12)); // padding

			slidePane.add(lblSlide, "card" + i);
			textPane.add(lblTextArea, "cardText" + i);
		}

		getContentPane().add(slidePane, BorderLayout.CENTER);

		// Put textPane above the buttonPane in bottomPane to avoid both being added to SOUTH.
		bottomPane.add(textPane, BorderLayout.CENTER);

		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		btnPrev.setText("Previous");
		btnPrev.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goPrevious();
			}
		});
		buttonPane.add(btnPrev);

		btnNext.setText("Next");
		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				goNext();
			}
		});
		buttonPane.add(btnNext);

		// Add the button pane below the text pane inside bottomPane.
		bottomPane.add(buttonPane, BorderLayout.SOUTH);

		// Add the combined bottomPane to the frame once.
		getContentPane().add(bottomPane, BorderLayout.SOUTH);
	}

	/**
	 * Previous Button Functionality
	 */
	private void goPrevious() {
		card.previous(slidePane);
		cardText.previous(textPane);
	}
	
	/**
	 * Next Button Functionality
	 */
	private void goNext() {
		card.next(slidePane);
		cardText.next(textPane);
	}

	/**
	 * Method to get the images
	 */
	private String getResizeIcon(int i) {
		String image = ""; 
		if (i==1){
			image = "<html><body><img width= '800' height='500' src='" + getClass().getResource("/resources/slide1.jpg") + "'</body></html>";
		} else if (i==2){
			image = "<html><body><img width= '800' height='500' src='" + getClass().getResource("/resources/slide2.jpg") + "'</body></html>";
		} else if (i==3){
			image = "<html><body><img width= '800' height='500' src='" + getClass().getResource("/resources/slide3.jpg") + "'</body></html>";
		} else if (i==4){
			image = "<html><body><img width= '800' height='500' src='" + getClass().getResource("/resources/slide4.jpg") + "'</body></html>";
		} else if (i==5){
			image = "<html><body><img width= '800' height='500' src='" + getClass().getResource("/resources/slide5.jpg") + "'</body></html>";
		}
		return image;
	}
	
	/**
	 * Method to get the text values
	 * Updated to detox/wellness themed descriptions per Product Owner's new priority.
	 */
	private String getTextDescription(int i) {
		String text = ""; 
		if (i==1){
			text = "<html><body><b>#1 Wellness Spa Retreat</b> — Hot-stone therapy, guided detox programs, and nourishing cuisine for total rejuvenation.</body></html>";
		} else if (i==2){
			text = "<html><body><b>#2 Yoga & Meditation Escape</b> — Daily yoga, mindfulness workshops, and nature walks for mental clarity.</body></html>";
		} else if (i==3){
			text = "<html><body><b>#3 Hot Springs & Thermal Baths</b> — Mineral soaks and thermal treatments to restore body and mind.</body></html>";
		} else if (i==4){
			text = "<html><body><b>#4 Holistic Wellness Resort</b> — Personalized wellness plans, nutrition counseling, and therapeutic treatments.</body></html>";
		} else if (i==5){
			text = "<html><body><b>#5 Off-grid Nature Retreat</b> — Digital detox cabins, forest bathing, and guided breathing sessions for deep restoration.</body></html>";
		}
		return text;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				SlideShow ss = new SlideShow();
				ss.setVisible(true);
			}
		});
	}
}