package edu.nps.moves.examples;


import java.awt.event.*;
import java.text.*;
import javax.swing.Timer;

/**
 * <em>Used in support of  PduByteBufferTester</em><br />
 * Frame to display amount of free memory in the running application.
* <P>
* Handy for use with NetBeans Developer's internal execution. Then the statistic
* of free memory in the whole environment is displayed.
*
* @version 1.0
*/
public class MemoryView extends javax.swing.JPanel {
    /** bundle to use */
    //private static ResourceBundle bundle = ResourceBundle.getBundle ("examples.advanced.MemoryViewLocale");
    /** message of free memory */
    private static MessageFormat msgMemory = new MessageFormat ("Used {2} of {0} {3} allocated memory");

    private static String HOW_TO_TOOLTIP = "With the memoryview.jar file in your classpath, use this tool by calling: new MemoryView()";

    private final static String[] UNITS_TEXT =
        { "bytes",  "KB",   "MB",       "GB" };
    private final static double[] UNITS_DIVISOR =
        { 1,        1024,   1024*1024,  1024*1024*1024 };

    private int unitsIndexCounter = 2; // Default ot MB
    private NumberFormat nf;

  /** default update time */
  private static final int UPDATE_TIME = 1000;
  /** timer to invoke updating */
  private Timer timer;


  /** Initializes the Form */
  public MemoryView() {
    initComponents ();

    setName ("Memory View");
    doGarbage.setText ("Collect Garbage");
    doRefresh.setText ("Refresh");
    //doClose.setText ("Close");

    txtTime.setText ("Refresh millis");
    doTime.setText ("Set refresh");
    time.setText (String.valueOf (UPDATE_TIME));
    time.selectAll ();
    time.requestFocus ();

    updateStatus ();

    timer = new Timer (UPDATE_TIME, new ActionListener () {
      public void actionPerformed (ActionEvent ev) {
        updateStatus ();
      }
    });
    timer.setRepeats (true);

    /*Dimension d = Toolkit.getDefaultToolkit ().getScreenSize ();
    Dimension m = this.getSize ();
    d.width -= m.width;
    d.height -= m.height;
    d.width /= 2;
    d.height /= 2;
    this.setLocation (d.width, d.height);
    this.setVisible (true);
    */
  }

  /** Starts the timer.
  */
  public void addNotify () {
    super.addNotify ();
    timer.start ();
  }

  /** Stops the timer.
  */
  public void removeNotify () {
    try
    {
        super.removeNotify ();
        timer.stop ();
        timer = null;
    }   // end try
    catch(Exception e){}
  }

  public void setRefresh(int delay){
      timer.setDelay(delay);
      time.setText(""+delay);
  }


  /** Updates the status of all components */
  private void updateStatus () {
    Runtime r = Runtime.getRuntime ();
    long free = r.freeMemory ();
    long total = r.totalMemory ();
    long taken = total - free;


    // Divide by necessary amount for units
    //free /= UNITS_DIVISOR[ unitsIndexCounter%UNITS_DIVISOR.length ];
    //total/= UNITS_DIVISOR[ unitsIndexCounter%UNITS_DIVISOR.length ];

    // when bigger than integer then divide by two
    long liTotal = total;
    long liFree = free;
    while (liTotal > Integer.MAX_VALUE) {
      liTotal = liTotal >> 1;
      liFree = liFree >> 1;
    }
    long liTaken = (int) (liTotal - liFree);

    status.setMaximum ((int)liTotal);
    status.setValue ((int)liTaken);

    text.setText (msgMemory.format (new Object[] {
      nf.format(total / UNITS_DIVISOR[ unitsIndexCounter ]),
      nf.format(free / UNITS_DIVISOR[ unitsIndexCounter ]),
      nf.format(taken / UNITS_DIVISOR[ unitsIndexCounter ]),
      UNITS_TEXT[ unitsIndexCounter%UNITS_TEXT.length ]
    }));
    text.invalidate ();
    validate ();
  }

    /** This method is called from within the constructor to
    * initialize the form.
    */
    private void initComponents ()
    {
        nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumIntegerDigits(1);

        jPanel1 = new javax.swing.JPanel ();
        text = new javax.swing.JLabel ();
        status = new javax.swing.JProgressBar ();
        jPanel2 = new javax.swing.JPanel ();
        doGarbage = new javax.swing.JButton ();
        doRefresh = new javax.swing.JButton ();
        //doClose = new javax.swing.JButton ();
        jPanel3 = new javax.swing.JPanel ();
        jPanel3.setToolTipText( HOW_TO_TOOLTIP );
        txtTime = new javax.swing.JLabel ();
        time = new javax.swing.JTextField ();
        time.setColumns(10);
        doTime = new javax.swing.JButton ();
        /*addWindowListener (new java.awt.event.WindowAdapter ()
        {   public void windowClosing (java.awt.event.WindowEvent evt)
            {   exitForm (evt);
            }
        });   // end windowadapter
        */

        //Listen for clicks to label
        text.addMouseListener( new MouseAdapter()
        {   public void mouseClicked(MouseEvent e)
            {   unitsIndexCounter = (unitsIndexCounter+1)%UNITS_DIVISOR.length;
            }   // end mouseClicked
        });
        text.setToolTipText( "Click to change units" );

    jPanel1.setLayout (new java.awt.BorderLayout ());


      jPanel1.add (text, java.awt.BorderLayout.SOUTH);


      jPanel1.add (status, java.awt.BorderLayout.CENTER);


      this.setLayout( new java.awt.BorderLayout() );
      this.add (jPanel1, java.awt.BorderLayout.CENTER);


      doGarbage.addActionListener (new java.awt.event.ActionListener () {
        public void actionPerformed (java.awt.event.ActionEvent evt) {
          doGarbageActionPerformed (evt);
        }
      }
      );

      jPanel2.add (doGarbage);

      doRefresh.addActionListener (new java.awt.event.ActionListener () {
        public void actionPerformed (java.awt.event.ActionEvent evt) {
          doRefreshActionPerformed (evt);
        }
      }
      );

      jPanel2.add (doRefresh);

      //doClose.addActionListener (new java.awt.event.ActionListener () {
      //  public void actionPerformed (java.awt.event.ActionEvent evt) {
      //    doCloseActionPerformed (evt);
      //  }
      //}
      //);
      //jPanel2.add (doClose);


    this.add (jPanel2, java.awt.BorderLayout.SOUTH);

    jPanel3.setLayout (new java.awt.BorderLayout (0, 20));


      jPanel3.add (txtTime, java.awt.BorderLayout.WEST);


      jPanel3.add (time, java.awt.BorderLayout.CENTER);

      doTime.addActionListener (new java.awt.event.ActionListener () {
        public void actionPerformed (java.awt.event.ActionEvent evt) {
          setRefreshTime (evt);
        }
      }
      );

      jPanel3.add (doTime, java.awt.BorderLayout.EAST);


    this.add (jPanel3, java.awt.BorderLayout.NORTH);

  }

  /** Exit the form */
  //private void exitForm (java.awt.event.WindowEvent evt) {
  //      removeNotify();
  //  //System.exit( 0 );
  //}

  private void setRefreshTime (java.awt.event.ActionEvent evt) {
    try {
      int rate = Integer.valueOf (time.getText ()).intValue ();
      timer.setDelay (rate);
    } catch (NumberFormatException ex) {
      time.setText (String.valueOf (timer.getDelay ()));
    }
    time.selectAll ();
    time.requestFocus ();
  }


  //private void doCloseActionPerformed (java.awt.event.ActionEvent evt) {
    //exitForm (null);
  //}


  private void doRefreshActionPerformed (java.awt.event.ActionEvent evt) {
    updateStatus ();
  }

  private void doGarbageActionPerformed (java.awt.event.ActionEvent evt) {
    System.gc ();
    updateStatus ();
  }


  // Variables declaration - do not modify
  private javax.swing.JPanel jPanel1;
  private javax.swing.JLabel text;
  private javax.swing.JProgressBar status;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JButton doGarbage;
  private javax.swing.JButton doRefresh;
  private javax.swing.JButton doClose;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JLabel txtTime;
  private javax.swing.JTextField time;
  private javax.swing.JButton doTime;
  // End of variables declaration

}
