/*
	Originally written by Bruce A. Maxwell a long time ago.
	Updated by Brian Eastwood and Stephanie Taylor more recently

	Creates a window using the JFrame class.

	Creates a drawable area in the window using the JPanel class.

	The JPanel calls the Landscape's draw method to fill in content, so the
	Landscape class needs a draw method.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Displays a Board graphically using Swing. In this version, we use a Board
 * class rather than a Landscape) and we do not make the assumption that
 * we are displaying a grid.
 * @author bseastwo
 */
 public class LandscapeDisplay
{
    JFrame win;
    protected Board scape; 
    private LandscapePanel canvas;
    private int gridScale; // width (and height) of each square in the grid
    private JLabel theme, title, result;
    private JProgressBar progressBar;
    private JComboBox<String> themeButton;
    private JButton resetButton, exitButton, solveButton;
    private static String themeName = "Material";
    private static boolean resetState = false;
    private static boolean exitState = false;
    private static boolean solveState = false;
    private static int stepCounts = 0;
    private static String message = "";

    /**
     * Initializes a display window for a Landscape.
     * @param scape the Landscape to display
     * @param scale controls the relative size of the display
     */
    public LandscapeDisplay(Board scape, int scale)
    {
        // setup the window
        this.win = new JFrame("Sudoku");
        this.win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.win.setResizable(false);

        this.scape = scape;
        this.gridScale = scale;

        // create a panel in which to display the Landscape
        this.canvas = new LandscapePanel( (int) (this.scape.getCols()+1) * this.gridScale,
																					(int) (this.scape.getRows()+3) * this.gridScale);

        /* Add Theme Button */
        String[] themes = new String[]{"Ocean", "Peach", "Rainbow", "Modern", "Material"};
        this.themeButton = new JComboBox<>(themes);
        this.themeButton.setSelectedIndex(4);

        this.themeButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    LandscapeDisplay.setThemeName((String)themeButton.getSelectedItem());
                    repaint();
                }
            }
        });

        /* Add ProgressBar */
        progressBar = new JProgressBar();
        progressBar.setMaximum(81);
        progressBar.setMinimum(scape.numLocked());
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 12));

        /* Add Reset Button */
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LandscapeDisplay.setResetState(true);
                progressBar.setValue(progressBar.getMinimum());
            }
        });

        /* Add Exit Button */
        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LandscapeDisplay.setExitState(true);
                win.dispose();
                System.exit(0);
            }
        });

        /* Add Solve Button */
        solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LandscapeDisplay.setSolveState(true);
            }
        });

        /* Add Labels */
        theme = new JLabel("Themes: ");
        theme.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 15));
        title = new JLabel("Sudoku Simulation by Ricky");
        title.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 27));
        result = new JLabel("");
        result.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 13));

        this.canvas.setLayout(null);

        this.canvas.add(themeButton);
        this.canvas.add(resetButton);
        this.canvas.add(solveButton);
        this.canvas.add(exitButton);
        this.canvas.add(progressBar);
        this.canvas.add(theme);
        this.canvas.add(title);
        this.canvas.add(result);

        solveButton.setBounds((int)(7.5 * scale), (10 * scale), 2*scale, scale/2);
        resetButton.setBounds((int)(7.5 * scale), (int)(10.5 * scale), 2*scale, scale/2);
        exitButton.setBounds((int)(7.5 * scale), (int)(11.2 * scale), 2*scale, scale/2);
        progressBar.setBounds((int)(4.6 * scale), 11 * scale, (int)(2.5 * scale), scale);
        themeButton.setBounds((int)(1.7 * scale), 11 * scale, (int)(scale * 2.5), scale);
        theme.setBounds(scale / 2, 11 * scale, 2* scale, scale);
        title.setBounds((int)(scale / 1.6), (int)(9 * scale), 7 * scale, 3 * scale);
        result.setBounds(2 * scale, (int)(10.5 * scale), 6 * scale, scale);

        // add the panel to the window, layout, and display
        this.win.add(this.canvas, BorderLayout.CENTER);
        this.win.pack();
        this.win.setVisible(true);
    }

    public void exit() {
        this.win.dispose();
    }

    public static void setResetState(boolean state) {
        resetState = state;
    }

    public static boolean getResetState() {
        System.out.print("");
        return resetState;
    }

    public static void setExitState(boolean state) {
        exitState = state;
    }

    public static boolean getExitState() {
        System.out.print("");
        return exitState;
    }

    public static void setSolveState(boolean state) {
        solveState = state;
    }

    public static boolean getSolveState() {
        System.out.print("");
        return solveState;
    }

    public static int getStepCounts() {
        return stepCounts;
    }

    public static void setStepCounts(int stepCounts) {
        LandscapeDisplay.stepCounts = stepCounts;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        LandscapeDisplay.message = message;
    }

    public void printResult() {
        this.result.setText(getMessage() + "Step counts: " + getStepCounts());
    }

    public void resetResult() {
        this.result.setText("");
    }

    public void setProgressBarValue(int value) {
        this.progressBar.setValue(value);
    }

    /**
     * Saves an image of the display contents to a file.  The supplied
     * filename should have an extension supported by javax.imageio, e.g.
     * "png" or "jpg".
     *
     * @param filename  the name of the file to save
     */
    public void saveImage(String filename)
    {
        // get the file extension from the filename
        String ext = filename.substring(filename.lastIndexOf('.') + 1, filename.length());

        // create an image buffer to save this component
        Component tosave = this.win.getRootPane();
        BufferedImage image = new BufferedImage(tosave.getWidth(), tosave.getHeight(), 
                                                BufferedImage.TYPE_INT_RGB);

        // paint the component to the image buffer
        Graphics g = image.createGraphics();
        tosave.paint(g);
        g.dispose();

        // save the image
        try
        {
            ImageIO.write(image, ext, new File(filename));
        }
        catch (IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
    }

    /**
     * This inner class provides the panel on which Landscape elements
     * are drawn.
     */
    private class LandscapePanel extends JPanel
    {
        /**
         * Creates the panel.
         * @param width the width of the panel in pixels
         * @param height the height of the panel in pixels
         */
        public LandscapePanel(int width, int height)
        {
            super();
            this.setPreferredSize(new Dimension(width, height));
            this.setBackground(Color.white);
        }

        /**
         * Method overridden from JComponent that is responsible for
         * drawing components on the screen.  The supplied Graphics
         * object is used to draw.
         * 
         * @param g the Graphics object used for drawing
         */
        public void paintComponent(Graphics g)
        {

            super.paintComponent(g);
            scape.draw( g, gridScale, LandscapeDisplay.getThemeName());
        } // end paintComponent
        
    } // end LandscapePanel

    public void repaint() {
	this.win.repaint();
    }

    public static String getThemeName() {
        return themeName;
    }

    public static void setThemeName(String name) {
        themeName = name;
    }

    public static void main(String[] args) throws InterruptedException {
        Board scape = new Board();
            // scape.read( args[0] );
        scape.read("./src/resources/board_sp_20.txt");
        LandscapeDisplay display = new LandscapeDisplay(scape, 40);
    }
}
