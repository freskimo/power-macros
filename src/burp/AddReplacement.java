package burp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddReplacement extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JComboBox cboLinkExtract;

    private JTextField txtName;
    private JComboBox cboType;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton upButton;
    private JButton downButton;
    private JTable extractTable;
    private JButton btnPath;
    private JTextField txtPath;
    private JFormattedTextField fTxtRegex;
    private JLabel lblRegex;
    private JLabel lblPath;

    private LinkedExtractionModel extractionModel;

    private Replace replaceToEdit;
    public AddReplacement(Replace replaceToEdit){
        this();
        this.replaceToEdit = replaceToEdit;

        this.extractionModel = new LinkedExtractionModel(replaceToEdit);
        this.extractTable.setModel(this.extractionModel);
        this.setupEditFields();
        BurpExtender.println("test");
    }

    public void setupEditFields(){
        txtName.setText(replaceToEdit.getId());
        TransformTypes replaceType = replaceToEdit.getType();
        cboType.setSelectedItem(replaceType.text());

        if(replaceType.equals(TransformTypes.REGEX)){
            fTxtRegex.setVisible(true);
            fTxtRegex.setText(replaceToEdit.getExtractReplaceMethod().getExtractionArgument());
        }else if(replaceType.equals(TransformTypes.PYTHON) ||
                 replaceType.equals(TransformTypes.JAVASCRIPT)){
            txtPath.setVisible(true);
            txtPath.setText(replaceToEdit.getExtractReplaceMethod().getExtractionArgument());
        }

    }

    public AddReplacement() {

        this.extractionModel = new LinkedExtractionModel();
        this.extractTable.setModel(extractionModel);

        lblPath.setVisible(false);
        lblRegex.setVisible(false);
        btnPath.setVisible(false);
        txtPath.setVisible(false);
        fTxtRegex.setVisible(false);

        setContentPane(contentPane);
        setModal(true);

        getRootPane().setDefaultButton(buttonOK);
        addReplacementTypesToCombo();


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
        cboType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
    private void addReplacementTypesToCombo(){
        for (TransformTypes type:  TransformTypes.values()) {
            cboType.addItem(type.text());
        }
    }
    private void onOK() {
        String typeArgs[] = {"R"};

        Extraction linkedExtraction =  BurpExtender.getInstance().getExtractionModel().getExtractionById(cboLinkExtract.getSelectedItem().toString());

        Replace newReplacement = new Replace(txtName.getText(), cboType.getSelectedItem().toString(), typeArgs, new Extraction[]{linkedExtraction}); //TODO: LINK to extraction here!!
        BurpExtender.getInstance().getReplacementModel().addReplace(newReplacement);
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
