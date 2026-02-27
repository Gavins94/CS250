import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;

// Main class that launches the application
public class TopFiveDestinationList {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater ensures the GUI is created on the Event Dispatch Thread (EDT)
        // This is important for thread safety in Swing applications
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create an instance of the main frame
                TopDestinationListFrame topDestinationListFrame = new TopDestinationListFrame();
                // Set the window title
                topDestinationListFrame.setTitle("Top 5 Destination List");
                // Make the window visible to the user
                topDestinationListFrame.setVisible(true);
            }
        });
    }
}

// Main frame class that extends JFrame to create the application window
class TopDestinationListFrame extends JFrame {
    // Model that holds the list data (destination text and icons)
    private DefaultListModel<TextAndIcon> listModel;
    // Constants defining the standard size for all destination images
    private static final int ICON_WIDTH = 160;
    private static final int ICON_HEIGHT = 100;

    // Constructor that sets up the entire GUI
    public TopDestinationListFrame() {
        // Call the parent constructor with the window title
        super("Top Five Destination List");

        // Set the application to exit when the window is closed
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Set the initial window size (width x height)
        setSize(900, 750);

        // Initialize the list model that will store destination data
        listModel = new DefaultListModel<>();

        // Create a header label centered at the top of the window
        JLabel header = new JLabel("Top 5 Destinations", SwingConstants.CENTER);
        // Make the header text bold and increase font size to 22
        header.setFont(header.getFont().deriveFont(Font.BOLD, 22f));
        // Add padding around the header (10 pixels on all sides)
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Add the header to the top of the frame using BorderLayout
        getContentPane().add(header, BorderLayout.NORTH);

        // Add each destination with its description and image
        // Each call loads and scales the image, then adds it to the list model
        addDestinationNameAndPicture(
            "1. Key Largo: The first and largest of the Florida Keys, known for its stunning coral reefs and vibrant marine life.",
            loadAndScaleIcon("/resources/keylargo.jpg")
        );
        addDestinationNameAndPicture(
            "2. Bahamas: A tropical paradise with crystal-clear waters, white sandy beaches, and a rich culture to explore.",
            loadAndScaleIcon("/resources/bahamas.jpg")
        );
        addDestinationNameAndPicture(
            "3. Jamaica: An island nation renowned for its reggae music, lush rainforests, and breathtaking waterfalls.",
            loadAndScaleIcon("/resources/jamaica.jpg")
        );
        addDestinationNameAndPicture(
            "4. Aruba: A sunny Caribbean island famous for its beautiful beaches, friendly locals, and vibrant nightlife.",
            loadAndScaleIcon("/resources/aruba.jpg")
        );
        addDestinationNameAndPicture(
            "5. Bermuda: A stunning Atlantic island known for its pink sand beaches, crystal-clear waters, and charming colonial architecture.",
            loadAndScaleIcon("/resources/Bermuda.jpg")
        );

        // Create a JList component using the populated list model
        JList<TextAndIcon> list = new JList<>(listModel);
        // Wrap the list in a scroll pane to enable scrolling if content exceeds window size
        JScrollPane scrollPane = new JScrollPane(list);

        // Create a custom cell renderer with 10 pixels of padding
        TextAndIconListCellRenderer renderer = new TextAndIconListCellRenderer(10);
        // Set the gap between icon and text to 12 pixels
        renderer.setIconTextGap(12);

        // Apply the custom renderer to the list
        list.setCellRenderer(renderer);

        // Add the scroll pane (containing the list) to the center of the frame
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    // Method to load an image from resources and scale it to the standard size
    private ImageIcon loadAndScaleIcon(String resourcePath) {
        // Attempt to load the image resource from the specified path
        java.net.URL imgURL = getClass().getResource(resourcePath);
        
        // If the image file is not found, create a placeholder image
        if (imgURL == null) {
            // Create a blank image with the standard dimensions
            BufferedImage placeholder = new BufferedImage(ICON_WIDTH, ICON_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            // Get the graphics context to draw on the image
            Graphics2D g = placeholder.createGraphics();
            // Fill the background with light gray
            g.setPaint(Color.LIGHT_GRAY);
            g.fillRect(0, 0, ICON_WIDTH, ICON_HEIGHT);
            // Draw "No Image" text in dark gray
            g.setPaint(Color.DARK_GRAY);
            g.drawString("No Image", 10, ICON_HEIGHT / 2);
            // Release graphics resources
            g.dispose();
            // Return the placeholder as an ImageIcon
            return new ImageIcon(placeholder);
        }
        
        // If the image was found, load it into an ImageIcon
        ImageIcon original = new ImageIcon(imgURL);
        // Scale the image to the standard size using smooth scaling for better quality
        Image scaled = original.getImage().getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH);
        // Return the scaled image as an ImageIcon
        return new ImageIcon(scaled);
    }

    // Helper method to add a destination entry to the list model
    private void addDestinationNameAndPicture(String text, Icon icon) {
        // Create a TextAndIcon object combining the description and image
        TextAndIcon tai = new TextAndIcon(text, icon);
        // Add the object to the list model (which updates the displayed list)
        listModel.addElement(tai);
    }
}

// Data class that holds both text and icon for each list item
class TextAndIcon {
    private String text;  // The destination description
    private Icon icon;    // The destination image

    // Constructor to initialize both fields
    public TextAndIcon(String text, Icon icon) {
        this.text = text;
        this.icon = icon;
    }

    // Getter methods to retrieve the stored values
    public String getText() { return text; }
    public Icon getIcon() { return icon; }
    
    // Setter methods to update the stored values
    public void setText(String text) { this.text = text; }
    public void setIcon(Icon icon) { this.icon = icon; }
}

// Custom renderer class that defines how each list item is displayed
// Extends JLabel to use label functionality and implements ListCellRenderer interface
class TextAndIconListCellRenderer extends JLabel implements ListCellRenderer<TextAndIcon> {
    // Border used when the cell doesn't have focus
    private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    // Border for internal padding around cell content
    private Border insideBorder;

    // Default constructor with no padding
    public TextAndIconListCellRenderer() { this(0,0,0,0); }
    
    // Constructor with uniform padding on all sides
    public TextAndIconListCellRenderer(int padding) { this(padding,padding,padding,padding); }
    
    // Constructor with individual padding for each side
    public TextAndIconListCellRenderer(int topPadding, int rightPadding, int bottomPadding, int leftPadding) {
        // Create an empty border with the specified padding
        insideBorder = BorderFactory.createEmptyBorder(topPadding, leftPadding, bottomPadding, rightPadding);
        // Make the label opaque so background colors are visible
        setOpaque(true);
        // Center the content vertically within the cell
        setVerticalAlignment(CENTER);
    }

    // Method called by JList to render each cell
    // Returns a Component (in this case, a configured JLabel) to display
    public Component getListCellRendererComponent(JList<? extends TextAndIcon> list, TextAndIcon value,
                                                  int index, boolean isSelected, boolean hasFocus) {
        // Wrap the text in HTML to enable text wrapping and styling
        // The width constraint ensures long text wraps to multiple lines
        String htmlText = "<html><body style='width:500px; font-family: sans-serif;'>" + value.getText() + "</body></html>";
        setText(htmlText);
        // Set the icon for this cell
        setIcon(value.getIcon());

        // Set colors based on whether the cell is selected
        if (isSelected) {
            // Use the list's default selection colors
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            // Create alternating row colors for better readability
            Color evenColor = new Color(245, 245, 245);  // Light gray
            Color oddColor = Color.WHITE;                 // White
            // Even-indexed rows get the even color, odd-indexed rows get the odd color
            setBackground((index % 2 == 0) ? evenColor : oddColor);
            setForeground(list.getForeground());
        }

        // Set the border: focus border on outside, padding border on inside
        Border outsideBorder = hasFocus ? UIManager.getBorder("List.focusCellHighlightBorder") : NO_FOCUS_BORDER;
        setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
        
        // Match the list's component orientation (for right-to-left languages)
        setComponentOrientation(list.getComponentOrientation());
        // Match the list's enabled state
        setEnabled(list.isEnabled());
        // Set the font to plain style with size 14
        setFont(list.getFont().deriveFont(Font.PLAIN, 14f));

        // Return this configured JLabel to be displayed as the cell
        return this;
    }

    // The following methods are overridden to do nothing for performance optimization
    // Since the renderer is reused for each cell, we don't need these expensive operations
    public void validate() {}
    public void invalidate() {}
    public void repaint() {}
    public void revalidate() {}
    public void repaint(long tm, int x, int y, int width, int height) {}
    public void repaint(Rectangle r) {}
}