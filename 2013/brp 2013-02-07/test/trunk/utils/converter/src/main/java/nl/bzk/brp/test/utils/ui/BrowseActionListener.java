/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class BrowseActionListener implements ActionListener {

//    private static File lastSelection = new File("D:/Werk/QSD/mGBA/Eclipse Workspace/test-trunk/utils/converter/src/test/resources/bevraging/details-persoon");
    private static File lastSelection = null;
    
    private int selectionMode;
    private JTextField textField;
    
    public BrowseActionListener(final int selectionMode, final JTextField textField) {
        this.selectionMode = selectionMode;
        this.textField = textField;
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        if (BrowseActionListener.lastSelection != null) {
            chooser = new JFileChooser(BrowseActionListener.lastSelection);
        }
        String selecteerEen = "bestand";
        if (this.selectionMode == JFileChooser.DIRECTORIES_ONLY) {
            selecteerEen = "map";
        }
        chooser.setDialogTitle("Selecteer een " + selecteerEen);
        chooser.setFileSelectionMode(this.selectionMode);

        int returnValue = chooser.showDialog(null, "Selecteer");

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            BrowseActionListener.lastSelection = file;
            this.textField.setText(file.getAbsolutePath());
            this.textField.setCaretPosition(this.textField.getText().length());
        } 
    }
    
}
