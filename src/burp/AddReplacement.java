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

    private LocalExtractionModel extractionModel;

    private Replace oldReplaceToEdit;
    private Replace newReplaceToEdit;

    public AddReplacement(Replace replaceToEdit){
        this();
        this.oldReplaceToEdit = replaceToEdit;
        this.newReplaceToEdit = oldReplaceToEdit;

        this.extractionModel = new LocalExtractionModel(replaceToEdit);
        this.extractTable.setModel(this.extractionModel);
        this.setupEditFields();
        BurpExtender.println("test");
    }
    public void setupEditFields(){
        txtName.setText(oldReplaceToEdit.getId());
        TransformTypes replaceType = oldReplaceToEdit.getType();
        cboType.setSelectedItem(replaceType.text());

        if(replaceType.equals(TransformTypes.REGEX)){
            fTxtRegex.setVisible(true);
            fTxtRegex.setText(oldReplaceToEdit.getExtractReplaceMethod().getExtractionArgument());
        }else if(replaceType.equals(TransformTypes.PYTHON) ||
                replaceType.equals(TransformTypes.JAVASCRIPT)){
            txtPath.setVisible(true);
            txtPath.setText(oldReplaceToEdit.getExtractReplaceMethod().getExtractionArgument());
        }

    }

    public AddReplacement() {

        this.extractionModel = new LocalExtractionModel();
        this.extractTable.setModel(extractionModel);
        this.oldReplaceToEdit = new Replace();

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
                dispose();
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
                onAdd();

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddReplacementSetup addExtractForm = new AddReplacementSetup(oldReplaceToEdit);

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
    private void onAdd(){
        AddReplacementSetup addExtractForm = new AddReplacementSetup(oldReplaceToEdit);

        addExtractForm.setTitle("Setup new replacement");
        //https://stackoverflow.com/questions/12988896/jframe-fixed-width
        BurpExtender.getInstance().stdout.println("Linked extracts before: " + oldReplaceToEdit.getLinkedExtractMap().size());
        this.oldReplaceToEdit = addExtractForm.showDialog();

        BurpExtender.getInstance().stdout.println("Linked extracts after: " + oldReplaceToEdit.getLinkedExtractMap().size());
        this.extractionModel = new LocalExtractionModel(oldReplaceToEdit);
        this.extractTable.setModel(this.extractionModel);
    }
    private void addReplacementTypesToCombo(){
        for (TransformTypes type:  TransformTypes.values()) {
            cboType.addItem(type.text());
        }
    }
    private void onOK() {

//        GlobalReplaceModel._updateReplace(oldReplaceToEdit);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
    }
}
