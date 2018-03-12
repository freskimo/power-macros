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
    private JLabel lblRegex;
    private JLabel lblPath;
    private JButton btnPath;

    private BurpExtender extender;

    public AddExtraction() {
        this.extender = BurpExtender.getInstance();


        lblPath.setVisible(false);
        lblRegex.setVisible(false);
        btnPath.setVisible(false);
        txtPath.setVisible(false);
        fTxtRegex.setVisible(false);

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
        cboType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if(cboType.getSelectedItem().toString());
                if (cboType.getSelectedItem().toString().equals(TransformTypes.REGEX.text())){
                    lblPath.setVisible(false);
                    txtPath.setVisible(false);
                    btnPath.setVisible(false);
                    lblRegex.setVisible(true);
                    fTxtRegex.setVisible(true);
                }else if (  cboType.getSelectedItem().toString()
                                .equals(TransformTypes.JAVASCRIPT.text())
                            ||
                            cboType.getSelectedItem().toString()
                                .equals(TransformTypes.PYTHON.text())){

                    lblPath.setVisible(true);
                    txtPath.setVisible(true);
                    btnPath.setVisible(true);
                    lblRegex.setVisible(false);
                    fTxtRegex.setVisible(false);
                }
            }
        });
    }

    private void addReplaceTypesToCombo(){
        cboType.addItem(" ...");
        for (TransformTypes type:  TransformTypes.values()) {
            cboType.addItem(type.text());
        }
    }

    private void onOK() {
        String typeArgs[] = {"R"};
        Extraction newExtraction = new Extraction(txtName.getText(), cboType.getSelectedItem().toString(), typeArgs);
        ExtractManager.addExtraction(newExtraction);
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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
