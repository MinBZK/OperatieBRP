/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils.ui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nl.bzk.brp.test.utils.Converter;
import nl.bzk.brp.test.utils.ConverterConfiguratie;
import nl.bzk.brp.test.utils.ConverterResultaat;

/**
 * Created with IntelliJ IDEA.
 * User: Erik
 * Date: 31-12-12
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */
public class ConverterForm extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel mainPanel;
    private JTextField inputFolderTextField;
    private JTextField inputFindReplaceTextField;
    private JTextField templateFileTextField;
    private JTextField templateDefaultsBestaansrechtTextField;
    private JTextField templateDefaultsGeenBestaansrechtTextField;
    private JTextField templateStructuurTextField;
    private JTextField templateMappingTextField;
    private JTextField templateNieuwTextField;
    private JTextField outputFolderTextField;
    private JButton inputFolderBrowse;
    private JButton inputFindReplaceBrowse;
    private JButton templateFileBrowse;
    private JButton templateDefaultsBestaansrechtBrowse;
    private JButton templateDefaultsGeenBestaansrechtBrowse;
    private JButton templateStructuurBrowse;
    private JButton templateMappingBrowse;
    private JButton templateNieuwBrowse;
    private JButton outputFolderBrowse;

    private JButton voerConversieUitButton;
    private JCheckBox geefDebugMeldingenWeerCheckBox;
    private JButton bekijkResultaatButton;
    
    private ConverterResultaat latestResultaat;

    public ConverterForm() {
        this.getContentPane().add(this.mainPanel);
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.initializeComponents();

//        //TEMP: test settings
//        ConverterForm.this.inputFolderTextField.setText("D:/Werk/QSD/mGBA/Eclipse Workspace/test-trunk/utils/converter/src/test/resources/bevraging/details-persoon/input");
//        ConverterForm.this.templateFileTextField.setText("D:/Werk/QSD/mGBA/Eclipse Workspace/test-trunk/utils/converter/src/test/resources/bevraging/details-persoon/template-bevraging-details-persoon-respons-nieuw.xml");
//        ConverterForm.this.inputFindReplaceTextField.setText("D:/Werk/QSD/mGBA/Eclipse Workspace/test-trunk/utils/converter/src/test/resources/bevraging/details-persoon/find-replace-bevraging-details-persoon.txt");
//        ConverterForm.this.templateDefaultsBestaansrechtTextField.setText("D:/Werk/QSD/mGBA/Eclipse Workspace/test-trunk/utils/converter/src/test/resources/bevraging/details-persoon/default-bestaansrecht-bevraging-details-persoon.txt");
//        ConverterForm.this.templateDefaultsGeenBestaansrechtTextField.setText("D:/Werk/QSD/mGBA/Eclipse Workspace/test-trunk/utils/converter/src/test/resources/bevraging/details-persoon/default-geen-bestaansrecht-bevraging-details-persoon.txt");
//        ConverterForm.this.templateStructuurTextField.setText("D:/Werk/QSD/mGBA/Eclipse Workspace/test-trunk/utils/converter/src/test/resources/bevraging/details-persoon/structuur-bevraging-details-persoon.txt");
//        ConverterForm.this.templateMappingTextField.setText("D:/Werk/QSD/mGBA/Eclipse Workspace/test-trunk/utils/converter/src/test/resources/bevraging/details-persoon/mapping-bevraging-details-persoon.txt");
//        ConverterForm.this.templateNieuwTextField.setText("D:/Werk/QSD/mGBA/Eclipse Workspace/test-trunk/utils/converter/src/test/resources/bevraging/details-persoon/nieuw-bevraging-details-persoon.txt");
//        ConverterForm.this.outputFolderTextField.setText("D:/Werk/QSD/mGBA/Eclipse Workspace/test-trunk/utils/converter/src/test/resources/bevraging/details-persoon/output/");
//        //NASTY HACK :)
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (Exception e) {
//                }
//                ConverterForm.this.inputFolderTextField.setCaretPosition(ConverterForm.this.inputFolderTextField.getText().length());
//                ConverterForm.this.templateFileTextField.setCaretPosition(ConverterForm.this.templateFileTextField.getText().length());
//                ConverterForm.this.inputFindReplaceTextField.setCaretPosition(ConverterForm.this.inputFindReplaceTextField.getText().length());
//                ConverterForm.this.templateDefaultsBestaansrechtTextField.setCaretPosition(ConverterForm.this.templateDefaultsBestaansrechtTextField.getText().length());
//                ConverterForm.this.templateDefaultsGeenBestaansrechtTextField.setCaretPosition(ConverterForm.this.templateDefaultsGeenBestaansrechtTextField.getText().length());
//                ConverterForm.this.templateStructuurTextField.setCaretPosition(ConverterForm.this.templateStructuurTextField.getText().length());
//                ConverterForm.this.templateMappingTextField.setCaretPosition(ConverterForm.this.templateMappingTextField.getText().length());
//                ConverterForm.this.templateNieuwTextField.setCaretPosition(ConverterForm.this.templateNieuwTextField.getText().length());
//                ConverterForm.this.outputFolderTextField.setCaretPosition(ConverterForm.this.outputFolderTextField.getText().length());
//            }
//        }).start();
//        //EINDE TEMP: test settings
    }

    private void initializeComponents() {
        this.inputFolderBrowse.addActionListener(new BrowseActionListener(JFileChooser.DIRECTORIES_ONLY, this.inputFolderTextField));
        this.inputFindReplaceBrowse.addActionListener(new BrowseActionListener(JFileChooser.FILES_ONLY, this.inputFindReplaceTextField));
        this.templateFileBrowse.addActionListener(new BrowseActionListener(JFileChooser.FILES_ONLY, this.templateFileTextField));
        this.templateDefaultsBestaansrechtBrowse.addActionListener(new BrowseActionListener(JFileChooser.FILES_ONLY, this.templateDefaultsBestaansrechtTextField));
        this.templateDefaultsGeenBestaansrechtBrowse.addActionListener(new BrowseActionListener(JFileChooser.FILES_ONLY, this.templateDefaultsGeenBestaansrechtTextField));
        this.templateStructuurBrowse.addActionListener(new BrowseActionListener(JFileChooser.FILES_ONLY, this.templateStructuurTextField));
        this.templateMappingBrowse.addActionListener(new BrowseActionListener(JFileChooser.FILES_ONLY, this.templateMappingTextField));
        this.templateNieuwBrowse.addActionListener(new BrowseActionListener(JFileChooser.FILES_ONLY, this.templateNieuwTextField));
        this.outputFolderBrowse.addActionListener(new BrowseActionListener(JFileChooser.DIRECTORIES_ONLY, this.outputFolderTextField));

        this.voerConversieUitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    ConverterConfiguratie configuratie = ConverterConfiguratie.maakConfiguratieUitParameters(
                            ConverterForm.this.geefDebugMeldingenWeerCheckBox.isSelected(),
                            new File(ConverterForm.this.inputFolderTextField.getText()),
                            new File(ConverterForm.this.templateFileTextField.getText()),
                            new File(ConverterForm.this.inputFindReplaceTextField.getText()),
                            new File(ConverterForm.this.templateDefaultsBestaansrechtTextField.getText()),
                            new File(ConverterForm.this.templateDefaultsGeenBestaansrechtTextField.getText()),
                            new File(ConverterForm.this.templateStructuurTextField.getText()),
                            new File(ConverterForm.this.templateMappingTextField.getText()),
                            new File(ConverterForm.this.templateNieuwTextField.getText()),
                            new File(ConverterForm.this.outputFolderTextField.getText()));
                    Converter converter = new Converter(configuratie);
                    converter.startConversie();
                    
                    ConverterForm.this.latestResultaat = converter.getResultaat();

                    JOptionPane.showMessageDialog(ConverterForm.this, "Conversie geslaagd", "Bericht", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ConverterForm.this, "Conversie mislukt", "Bericht", JOptionPane.ERROR_MESSAGE);
                    // TODO: guify
                    System.out.println("Er is een fout opgetreden tijdens het uitvoeren van de conversie tool.");
                    ex.printStackTrace();
                }
            }
        });

        this.bekijkResultaatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ResultaatForm resultaatForm = new ResultaatForm();
                resultaatForm.setVisible(true);
                resultaatForm.vulTabellen(ConverterForm.this.latestResultaat);
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer.
// Code editing allowed - no longer under IDEA control.
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        final JLabel label1 = new JLabel();
        label1.setFont(new Font("Arial", Font.BOLD, 16));
        label1.setText("BRP Testdata Converter Tool");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 6;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label1, gbc);
        final JLabel label2 = new JLabel();
        label2.setText("Folder");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label2, gbc);
        inputFolderBrowse = new JButton();
        inputFolderBrowse.setText("Browse");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(inputFolderBrowse, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("Find/replace");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label3, gbc);
        inputFindReplaceBrowse = new JButton();
        inputFindReplaceBrowse.setText("Browse");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(inputFindReplaceBrowse, gbc);
        inputFindReplaceTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(inputFindReplaceTextField, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("File");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label4, gbc);
        inputFolderTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(inputFolderTextField, gbc);
        templateFileTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(templateFileTextField, gbc);
        templateFileBrowse = new JButton();
        templateFileBrowse.setText("Browse");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(templateFileBrowse, gbc);
        final JLabel label5 = new JLabel();
        label5.setFont(new Font("Arial", Font.BOLD, 14));
        label5.setText("Template");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label5, gbc);
        final JLabel label6 = new JLabel();
        label6.setFont(new Font("Arial", Font.BOLD, 14));
        label6.setText("Input");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label6, gbc);
        
        final JLabel label7 = new JLabel();
        label7.setText("Defaults - MET bestaansrecht");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label7, gbc);
        templateDefaultsBestaansrechtBrowse = new JButton();
        templateDefaultsBestaansrechtBrowse.setText("Browse");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(templateDefaultsBestaansrechtBrowse, gbc);
        templateDefaultsBestaansrechtTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 9;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(templateDefaultsBestaansrechtTextField, gbc);
        
        final JLabel label17 = new JLabel();
        label17.setText("Defaults - ZONDER bestaansrecht");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label17, gbc);
        templateDefaultsGeenBestaansrechtBrowse = new JButton();
        templateDefaultsGeenBestaansrechtBrowse.setText("Browse");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(templateDefaultsGeenBestaansrechtBrowse, gbc);
        templateDefaultsGeenBestaansrechtTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 10;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(templateDefaultsGeenBestaansrechtTextField, gbc);
        
        final JLabel label12 = new JLabel();
        label12.setText("Structuur");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label12, gbc);
        templateStructuurBrowse = new JButton();
        templateStructuurBrowse.setText("Browse");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(templateStructuurBrowse, gbc);
        templateStructuurTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 11;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(templateStructuurTextField, gbc);
        
        templateMappingBrowse = new JButton();
        templateMappingBrowse.setText("Browse");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 12;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(templateMappingBrowse, gbc);
        final JLabel label8 = new JLabel();
        label8.setText("Mapping");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label8, gbc);
        templateMappingTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 12;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(templateMappingTextField, gbc);
        
        final JLabel label9 = new JLabel();
        label9.setText("Folder");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 16;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label9, gbc);
        outputFolderBrowse = new JButton();
        outputFolderBrowse.setText("Browse");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 16;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(outputFolderBrowse, gbc);
        outputFolderTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 16;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(outputFolderTextField, gbc);
        
        voerConversieUitButton = new JButton();
        voerConversieUitButton.setText("Voer conversie uit");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 18;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(voerConversieUitButton, gbc);
        final JLabel label10 = new JLabel();
        label10.setFont(new Font("Arial", Font.BOLD, 14));
        label10.setText("Ouput");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 15;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label10, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 14;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(spacer5, gbc);
        final JLabel label11 = new JLabel();
        label11.setText("Nieuw");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(label11, gbc);
        templateNieuwTextField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 13;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(templateNieuwTextField, gbc);
        templateNieuwBrowse = new JButton();
        templateNieuwBrowse.setText("Browse");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 13;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(templateNieuwBrowse, gbc);
        geefDebugMeldingenWeerCheckBox = new JCheckBox();
        geefDebugMeldingenWeerCheckBox.setText("Geef debug meldingen weer in de terminal");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 17;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(geefDebugMeldingenWeerCheckBox, gbc);
        bekijkResultaatButton = new JButton();
        bekijkResultaatButton.setText("Bekijk resultaat");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 20;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(bekijkResultaatButton, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 19;
        gbc.fill = GridBagConstraints.VERTICAL;
        mainPanel.add(spacer6, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
