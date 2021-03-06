/*
class is called when any day is clicked on
 */
package mypi.eventForm;

import mypi.EventLinkedList;
import mypi.pojo.Events;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @author trav
 */
public class CreateEventForm extends JPanel {

    private JLabel monthLabel, dayLabel, yearLabel;
    private final Box eventBox;
    private JButton okay, cancel;
    private JComboBox billsBox;
    private final String[] bills = {"Events"};
    private final JPanel descriptionPanel;
    private JTextField descriptionTitle;
    private final JTextField manualInput = new JTextField(1);
    private Box horizontal5;//used for setting up manualInput text box in approprite location
    private final JTabbedPane theAppTabs;
    private Events pojo;
    private EmptyBorder padding = new EmptyBorder(10, 0, 10, 0);
    private Font font = new Font("Bookman Old Style", Font.PLAIN, 20);

    /**
     * @param pojo
     * @param theAppTabs is called to control the tabs in CalendarApp
     */

    public CreateEventForm(Events pojo, JTabbedPane theAppTabs) {
        this.theAppTabs = theAppTabs;
        this.pojo = pojo;
        eventBox = Box.createVerticalBox();
        JPanel topLayer = createTopLayer(pojo);

        JPanel secondLayer = createSecondLayer();

        JPanel thirdLayer = createThirdLayer();

        descriptionPanel = new JPanel();
        displayRadioButton();

        JPanel decisionPanel = createOkayAndCancelButtons();

        eventBox.add(topLayer);
        eventBox.add(secondLayer);
        eventBox.add(thirdLayer);
        eventBox.add(descriptionPanel);
        eventBox.add(decisionPanel);

        this.add(eventBox);
        this.setVisible(true);

    }

    /**
     * @return panel containing drop down menu
     */
    private JPanel createThirdLayer() {
        ComboBoxListener comboChanger = new ComboBoxListener();
        JPanel thirdLayer = new JPanel();
        billsBox = new JComboBox(bills);
        billsBox.addActionListener(comboChanger);
        thirdLayer.add(billsBox);
        thirdLayer.setBorder(padding);
        return thirdLayer;
    }

    /**
     * @return panel containing message to user
     */
    private JPanel createSecondLayer() {
        JPanel secondLayer = new JPanel();
        JLabel questionToUser = new JLabel("What Would You Like To Plan?");
        questionToUser.setFont(font);
        secondLayer.add(questionToUser);
        secondLayer.setBorder(padding);
        return secondLayer;
    }

    /**
     * @param pojo1
     * @return panel containing the month day year of which the event is about to be planned on
     */
    private JPanel createTopLayer(Events pojo1) {
        //provide the label of the current day of the event
        JPanel topPanel = new JPanel();
        monthLabel = new JLabel(pojo1.getMonth());
        dayLabel = new JLabel(pojo1.getDay());
        yearLabel = new JLabel(pojo1.getYear());
        monthLabel.setFont(font);
        dayLabel.setFont(font);
        yearLabel.setFont(font);
        topPanel.add(monthLabel);
        topPanel.add(dayLabel);
        topPanel.add(yearLabel);
        return topPanel;
    }

    /**
     * @return panel containing two button 'okay' and 'cancel'
     */
    private JPanel createOkayAndCancelButtons() {
        OkayCancelListener okayCancelChanger = new OkayCancelListener();
        JPanel decisionPanel = new JPanel();
        okay = new JButton("Okay");
        cancel = new JButton("Cancel");
        okay.setFont(font);
        cancel.setFont(font);
        okay.addActionListener(okayCancelChanger);
        cancel.addActionListener(okayCancelChanger);
        decisionPanel.add(cancel);
        decisionPanel.add(okay);
        decisionPanel.setBorder(padding);
        return decisionPanel;
    }

    /**
     * sets up the radio buttons, groups them, and adds padding.
     */
    private void displayRadioButton() {
        Box vertical = Box.createVerticalBox();
        Box horizontal = Box.createHorizontalBox();
        horizontal.setBorder(padding);
        Box horizontal2 = Box.createHorizontalBox();
        horizontal2.setBorder(padding);
        Box horizontal3 = Box.createHorizontalBox();
        horizontal3.setBorder(padding);
        Box horizontal4 = Box.createHorizontalBox();
        horizontal4.setBorder(padding);
        horizontal5 = Box.createHorizontalBox();

        vertical.add(horizontal);
        vertical.add(horizontal2);
        vertical.add(horizontal3);
        vertical.add(horizontal4);
        vertical.add(horizontal5);
        descriptionPanel.add(vertical);
        descriptionPanel.updateUI();
    }

    private class RadioButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AbstractButton radioButton = (AbstractButton) e.getSource();

            if (radioButton.getText().equals("Events")) {
                //manualInput = new JTextField(6);
                horizontal5.add(manualInput);
            } else {
                horizontal5.remove(manualInput);
            }
            updateUI();

        }
    }

    private class OkayCancelListener implements ActionListener {
        //        private final WriteToFile writingToFile = new WriteToFile();
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == okay) {
                //if okay is clicked
                String getBillSelected = billsBox.getSelectedItem().toString();//grabs bills[]

                if ((getBillSelected.equals("Events"))) {//else means that 'Events' was selected.
                    String titleOfDescription = descriptionTitle.getText();
                    pojo.setEvent(titleOfDescription);
                    EventLinkedList.instanceOf().add(pojo);
                }
            } else if (e.getSource() == cancel) {//if cancel is pressed do nothing

            }

            //making the tabs 'clickable' again
            theAppTabs.setEnabledAt(0, true);
            theAppTabs.setEnabledAt(1, true);
            //remove creating an event tab
            theAppTabs.remove(2);
            theAppTabs.setSelectedIndex(0);//when closed put focus on event tab
        }

    }


    /**
     * handles when clicking into text box the text disapears
     * and will repear if you click off of the text box without writing anything into it
     */
    private class textTitleAction implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            if (descriptionTitle.getText().equals("Enter your event title")) {
                descriptionTitle.setText("");
            } else
                ;
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (descriptionTitle.getText().equals("")) {
                descriptionTitle.setText("Enter your event title");
            }
        }

    }//end of text title action

    private class ComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            descriptionPanel.removeAll();
            textTitleAction textTitle = new textTitleAction();
            Box vert = Box.createVerticalBox();
            descriptionTitle = new JTextField(30);
            descriptionTitle.setText("Enter your event title");
            descriptionTitle.addFocusListener(textTitle);
            vert.add(descriptionTitle);
            descriptionPanel.add(vert);
            descriptionPanel.updateUI();


    }

}//end comboBoxListener

}//endEventForm