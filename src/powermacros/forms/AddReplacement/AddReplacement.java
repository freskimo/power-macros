package powermacros.forms.AddReplacement;

import burp.BurpExtender;
import powermacros.replace.Replace;
import powermacros.replace.ReplaceManager;
import powermacros.transforms.TransformTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class AddReplacement extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

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

    private LinkedExtractsTableModel extractionModel;
    private Replace replaceToEdit;

    public AddReplacement(Replace replaceToEdit){
        this();
        this.replaceToEdit = replaceToEdit;

        this.extractionModel = new LinkedExtractsTableModel(replaceToEdit);
        this.extractTable.setModel(this.extractionModel);
        this.setupEditFields();
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

        this.extractionModel = new LinkedExtractsTableModel();
        this.extractTable.setModel(extractionModel);
        this.replaceToEdit = new Replace();

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
                AddReplacementSetup addExtractForm = new AddReplacementSetup(replaceToEdit);

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
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRemove();
            }
        });
    }
    private void onRemove(){
        int index = extractTable.getSelectedRow();
        replaceToEdit.linkedExtracts.remove(index);
        extractionModel.removeRow(index);


    }
    private void onAdd(){
        AddReplacementSetup addExtractForm = new AddReplacementSetup(replaceToEdit);

        addExtractForm.setTitle("Setup new replacement");
        //https://stackoverflow.com/questions/12988896/jframe-fixed-width
        BurpExtender.getInstance().stdout.println("Linked extracts before: " + replaceToEdit.linkedExtracts.getLinkedExtractMap().size());
        this.replaceToEdit = addExtractForm.showDialog();

        BurpExtender.getInstance().stdout.println("Linked extracts after: " + replaceToEdit.linkedExtracts.getLinkedExtractMap().size());
        this.extractionModel = new LinkedExtractsTableModel(replaceToEdit);
        this.extractTable.setModel(this.extractionModel);
    }
    private void addReplacementTypesToCombo(){

        for (TransformTypes type:  TransformTypes.values()) {
            if(type.isImplemented()){
                cboType.addItem(type.text());
            }
        }
    }
    private void onOK() {

//        MainReplaceTableModel._updateReplace(replaceToEdit);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
    }
}
