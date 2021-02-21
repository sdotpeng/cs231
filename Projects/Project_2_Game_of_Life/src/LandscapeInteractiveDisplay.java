/*
  Originally written by Bruce A. Maxwell a long time ago.
  Updated by Brian Eastwood and Stephanie Taylor more recently
  Updated by Bruce again in Fall 2018

  Creates a window using the JFrame class.

  Creates a drawable area in the window using the JPanel class.

  The JPanel calls the Landscape's draw method to fill in content, so the
  Landscape class needs a draw method.
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Displays a Landscape graphically using Swing. The Landscape
 * contains a grid which can be displayed at any scale factor.
 * @author bseastwo
 */
public class LandscapeInteractiveDisplay {

    JFrame win;
    JButton simulateButton, nextButton, backButton, resetButton, exitButton;
    protected Landscape scape;
    private LandscapePanel canvas;
    private int gridScale; // width (and height) of each square in the grid

    /**
     * Initializes a display window for a Landscape.
     * @param scape the Landscape to display
     * @param scale controls the relative size of the display
     */
    public LandscapeInteractiveDisplay(Landscape scape, int scale) {
        // setup the window
        this.win = new JFrame("Game of Life Implemented by Ricky Peng");
        this.win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.scape = scape;
        this.gridScale = scale;

        // create a panel in which to display the Landscape
        // put a buffer of two rows around the display grid
        this.canvas = new LandscapePanel( this.scape.getCols() * this.gridScale,
                (this.scape.getRows() + 4) * this.gridScale);

        this.initializeButtons();

        this.canvas.setLayout(null);

        this.canvas.add(simulateButton);
        this.canvas.add(nextButton);
        this.canvas.add(backButton);
        this.canvas.add(resetButton);
        this.canvas.add(exitButton);

        // add the panel to the window, layout, and display
        this.win.add(this.canvas, BorderLayout.CENTER);
        this.win.pack();
        this.win.setVisible(true);
    }

    public void initializeButtons() {

        int width = this.scape.getCols() * this.gridScale;
        int height = this.scape.getRows() * this.gridScale;

        int startX = 0;
        int startY = height + 3;

        int buttonWidth = width/5;
        int buttonHeight = 30;

        this.simulateButton = new JButton("Simulate") {
            {
                setBounds(startX,startY,buttonWidth,buttonHeight);
                setBackground(new Color(180, 225, 192));
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { LifeSimulation.setInteractive(1); }
                });
            }
        };

        this.backButton = new JButton("Previous") {
            {
                setBounds(startX + buttonWidth, startY, buttonWidth, buttonHeight);
                setBackground(new Color(180, 225, 192));
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { LifeSimulation.setInteractive(2); }
                });
            }
        };

        this.nextButton = new JButton("Next") {
            {
                setBounds(startX + 2 * buttonWidth,startY,buttonWidth,buttonHeight);
                setBackground(new Color(180, 225, 192));
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        LifeSimulation.setInteractive(3);
                    }
                });
            }
        };
        this.resetButton = new JButton("Reset") {
            {
                setBounds(startX + 3 * buttonWidth, startY, buttonWidth, buttonHeight);
                setBackground(new Color(180, 225, 192));
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        LifeSimulation.setInteractive(4);
                    }
                });
            }
        };

        this.exitButton = new JButton("Exit") {
            {
                setBounds(startX + 4 * buttonWidth, startY, buttonWidth, buttonHeight);
                setBackground(new Color(27, 67, 50));
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        win.dispose();
                    }
                });
            }
        };
    }

    /**
     * Saves an image of the display contents to a file.
     *
     * The supplied filename should have an extension supported by javax.imageio, e.g. "png" or "jpg".
     *
     * @param filename the name of the file to save
     */
    public void saveImage(String filename) {
        // get the file extension from the filename
        String ext = filename.substring(filename.lastIndexOf('.') + 1);

        // create an image buffer to save this component
        Component toSave = this.win.getRootPane();
        BufferedImage image = new BufferedImage(toSave.getWidth(), toSave.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        // paint the component to the image buffer
        Graphics g = image.createGraphics();
        toSave.paint(g);
        g.dispose();

        // save the image
        try {
            ImageIO.write(image, ext, new File(filename));
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    /**
     * This inner class provides the panel on which Landscape elements are drawn
     */
    private class LandscapePanel extends JPanel {
        /**
         * Creates the panel.
         * @param width the width of the panel in pixels
         * @param height the height of the panel in pixels
         */
        public LandscapePanel(int width, int height) {
            super();
            this.setPreferredSize(new Dimension(width, height));
            this.setBackground(Color.lightGray);
        }

        /**
         * Method overridden from JComponent that is responsible for drawing components on the screen.
         * The supplied Graphics object is used to draw
         *
         * @param g the Graphics object used for drawing
         */
        public void paintComponent(Graphics g) {
            // take care of housekeeping by calling parent paintComponent
            super.paintComponent(g);

            // call the Landscape draw method here
            scape.draw( g, gridScale );
        } // end paintComponent

    } // end LandscapePanel

    public void repaint() {
        this.win.repaint();
    }
}
