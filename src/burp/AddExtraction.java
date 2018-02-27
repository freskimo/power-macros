package burp;

import javax.swing.*;
import java.awt.event.*;

public class AddExtraction extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtName;
    private JComboBox cboType;
    private JTextField txtPath;
    private JFormattedTextField fTxtRegex;

    private BurpExtender extender;

    public AddExtraction(BurpExtender extender) {
        this.extender = extender;

        addReplaceTypesToCombo();

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
    }

    private void addReplaceTypesToCombo(){
        for (TransformTypes type:  TransformTypes.values()) {
            cboType.addItem(type.text());
        }
    }

    private void onOK() {
        String typeArgs[] = {"R"};
        Extraction newExtraction = new Extraction(txtName.getText(), cboType.getSelectedItem().toString(), typeArgs);
        extender.getExtractionModel().addExtraction(newExtraction);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
//        AddExtraction dialog = new AddExtraction();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
    }
}
