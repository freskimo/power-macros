package burp;

import com.google.gson.Gson;
import powermacros.debug.DebugUtilities;
import powermacros.extract.ExtractManager;
import powermacros.extract.Extraction;
import powermacros.forms.AddExtraction;
import powermacros.forms.MainTab.MainExtractTableModel;
import powermacros.forms.MainTab.MainReplaceTableModel;
import powermacros.forms.AddReplacement.AddReplacement;
import powermacros.replace.Replace;
import powermacros.replace.ReplaceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class BurpExtender implements IBurpExtender, IHttpListener, IContextMenuFactory, ITab  {
    //TODO: Feature idea: Cascading extraction replacements like CSS (sequential replacements in order). Ability to set order
    //TODO: Feature idea: Ability to add post-processing python/ruby/JS script. After replacements are done, the request is passed to the script for final processing
    public static BurpExtender getInstance() {
        return INSTANCE;
    }
    private static  BurpExtender INSTANCE = null;
    private static String EXTENSION_NAME = "EnhancedExtendedMacros";
    private static String EXTENSION_NAME_TAB_NAME = "Enhanced Extended Macro";
    private static String VERSION = "0.0.1";
    public static final String HOST_FROM = "www.example.com";

    public IBurpExtenderCallbacks getCallbacks() {
        return callbacks;
    }
    public IBurpExtenderCallbacks callbacks;
    public IExtensionHelpers helpers;
    private Set<String> actualCallRepSet; /// what to replace in last call
    public PrintWriter stdout;
    private PrintWriter stderr;

    private JPanel panel1;
    private JTabbedPane tabbedPane1;

    private JTable extractTable;
    public static MainExtractTableModel mainExtractTableModel;


    public JTable replaceTable;
    public static MainReplaceTableModel mainReplaceTableModel;

    private JButton addExtractionButton;
    private JButton removeReplacementButton;
    private JButton editReplacementButton;
    private JButton addReplacementButton;
    private JButton removeExtractionButton;
    private JButton editExtractionButton;
    private JButton saveSettingsButton;
    private JButton loadSettingsButton;
    private JPanel tabSettingsConfig;

    private DebugUtilities debugUtilities;
    private long lastExtractionTime = 0L;

    private static JFrame frame;
    public BurpExtender() {
        if(INSTANCE == null){
            addExtractionButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    onAddExtraction();
                }
            });
            addReplacementButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    onAddReplacement();
                }
            });
            INSTANCE = this;
            mainExtractTableModel = new MainExtractTableModel();
            mainReplaceTableModel = new MainReplaceTableModel();

            extractTable.setModel(mainExtractTableModel);
            replaceTable.setModel(mainReplaceTableModel);
            tabbedPane1.remove(tabSettingsConfig);
        }
        editReplacementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onEditReplacement();
            }
        });
        editExtractionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onEditExtraction();
            }
        });
        removeExtractionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onExtractRemove();
            }
        });
        removeReplacementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onReplaceRemove();
            }
        });
        saveSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSaveSettings();
            }
        });
        loadSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onLoadSettings();
            }
        });
    }
    private void onEditExtraction(){
        Extraction extractEdit = ExtractManager.getExtraction(extractTable.getSelectedRow());
        AddExtraction addExtractForm = new AddExtraction(extractEdit);
        addExtractForm.setTitle("Edit extraction...");
        addExtractForm.setSize(new Dimension (400, 210));
        addExtractForm.setResizable(false);
        addExtractForm.setVisible(true);

        BurpExtender.mainExtractTableModel = new MainExtractTableModel();
        this.extractTable.setModel(mainExtractTableModel);
    }
    private void onEditReplacement(){
        Replace replaceEdit = ReplaceManager.getReplace(replaceTable.getSelectedRow());
        AddReplacement addExtractForm = new AddReplacement(replaceEdit);
        addExtractForm.setTitle("Edit replacement...");
        addExtractForm.setSize(new Dimension (400, 454));
        addExtractForm.setResizable(false);
        addExtractForm.setVisible(true);


        BurpExtender.mainReplaceTableModel = new MainReplaceTableModel();
        this.replaceTable.setModel(mainReplaceTableModel);
    }
    private void onSaveSettings(){
        BufferedWriter writer = null;
        Gson gson = new Gson();

        try{
            File settingsFile = new File("settings.json");
            writer = new BufferedWriter(new FileWriter(settingsFile));
            ReplaceManager.save(writer);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                writer.close();
            }catch(Exception e){

            }
        }
    }
    private void onLoadSettings(){

        FileDialog fc = new FileDialog(frame, "Choose settings file", FileDialog.LOAD);
        fc.setVisible(true);
        if(fc.getFile() != null){
//            String path = fc.getDirectory() + fc.getFile();
//            txtPath.setText(path);
        }
    }
    private void onExtractRemove(){
        int index = extractTable.getSelectedRow();
        ExtractManager.removeExtraction(index);
        this.updateExtractTable();
    }
    private void onReplaceRemove(){
        int index = replaceTable.getSelectedRow();
        ReplaceManager.removeReplace(index);
        this.updateReplaceTable();
    }
    private void updateExtractTable(){
        BurpExtender.mainExtractTableModel = new MainExtractTableModel();
        this.extractTable.setModel(mainExtractTableModel);
    }
    private void updateReplaceTable(){
        BurpExtender.mainReplaceTableModel = new MainReplaceTableModel();
        this.replaceTable.setModel(mainReplaceTableModel);
    }
    public static void println(String msg){
        BurpExtender.getInstance().stdout.println(msg);
    }


    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo) {

    }
    private void onAddReplacement(){

        AddReplacement addReplacementForm = new AddReplacement();
        addReplacementForm.setTitle("Add replacement...");
        //https://stackoverflow.com/questions/12988896/jframe-fixed-width
        addReplacementForm.setSize(new Dimension (400, 454));
        addReplacementForm.setResizable(false);
        addReplacementForm.setVisible(true);

        BurpExtender.mainReplaceTableModel = new MainReplaceTableModel();
        this.replaceTable.setModel(mainReplaceTableModel);
    }
    private void onAddExtraction(){
            AddExtraction addExtractForm = new AddExtraction();

            addExtractForm.setTitle("Add extraction...");
            //https://stackoverflow.com/questions/12988896/jframe-fixed-width
            addExtractForm.setSize(new Dimension (400, 210));
            addExtractForm.setResizable(false);
            addExtractForm.setVisible(true);

            BurpExtender.mainExtractTableModel = new MainExtractTableModel();
            this.extractTable.setModel(mainExtractTableModel);
    }

    //    private MessagesModel loggerMessagesModel;
    public static void main(String[] args) {
        frame = new JFrame("BurpExtender");
        frame.setContentPane(new BurpExtender().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
    @Override
    public java.util.List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
        IHttpRequestResponse[] messages = invocation.getSelectedMessages();
        return null;
    }
    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks iBurpExtenderCallbacks) {

        callbacks = iBurpExtenderCallbacks;
        helpers = callbacks.getHelpers();

        actualCallRepSet = new HashSet<>();

        // obtain our output stream
        stdout = new PrintWriter(callbacks.getStdout(), true);
        stderr = new PrintWriter(callbacks.getStderr(), true);

        callbacks.setExtensionName(EXTENSION_NAME);

        // register callbacks
        callbacks.registerHttpListener(this);
        callbacks.registerContextMenuFactory(this);
        // init gui callbacks
        callbacks.addSuiteTab(this);

        stdout.println("[*] " + EXTENSION_NAME + " " + VERSION);
        stdout.println("Starting debug utilities");
        debugUtilities = new DebugUtilities(this);
    }

    @Override
    public String getTabCaption() {
        return EXTENSION_NAME_TAB_NAME;
    }
    @Override
    public Component getUiComponent() {
        return tabbedPane1;
    }
}
