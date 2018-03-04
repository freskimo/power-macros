package burp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class AddReplacement extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JComboBox cboLinkExtract;

    private JTextField txtName;
    private JComboBox cboType;
    private JTable table1;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton upButton;
    private JButton downButton;

    private BurpExtender extender;

    public AddReplacement() {
        this.extender = BurpExtender.getInstance();


        addExtractionsToCombo();


        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddReplacementSetup addExtractForm = new AddReplacementSetup();

                addExtractForm.setTitle("Setup new replacement");
                //https://stackoverflow.com/questions/12988896/jframe-fixed-width
                addExtractForm.setSize(new Dimension(400, 210));
                addExtractForm.setResizable(false);
                addExtractForm.setVisible(true);
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddReplacementSetup addExtractForm = new AddReplacementSetup();

                addExtractForm.setTitle("Edit existing replacement");
                //https://stackoverflow.com/questions/12988896/jframe-fixed-width
                addExtractForm.setSize(new Dimension(400, 210));
                addExtractForm.setResizable(false);
                addExtractForm.setVisible(true);
            }
        });
    }
    private void addExtractionsToCombo(){

        Map<String, Extraction> extModelMap = BurpExtender.extractTableModel.getExtModelMap();
//        BurpExtender.getInstance().stdout.println("Total extractions: " + extModelMap.size());

        for (Map.Entry<String, Extraction> entry:  extModelMap.entrySet()) {

            cboLinkExtract.addItem(entry.getKey());
        }
    }

    private void onOK() {
        String typeArgs[] = {"R"};

        Extraction linkedExtraction =  this.extender.getExtractionModel().getExtractionById(cboLinkExtract.getSelectedItem().toString());

        Replace newReplacement = new Replace(txtName.getText(), cboType.getSelectedItem().toString(), typeArgs, new Extraction[]{linkedExtraction}); //TODO: LINK to extraction here!!
        extender.getReplacementModel().addReplace(newReplacement);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
//        AddReplacement dialog = new AddReplacement();
//        dialog.setTitle("Add replacement...");
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
    }
}
