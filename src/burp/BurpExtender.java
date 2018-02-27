package burp;

import com.sun.jmx.snmp.InetAddressAcl;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class BurpExtender implements IBurpExtender, IHttpListener, IContextMenuFactory, ITab, ISessionHandlingAction  {
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

//    private BurpActionModel burpActionModel = new BurpActionModel();

    public IBurpExtenderCallbacks getCallbacks() {
        return callbacks;
    }
    public void setCallbacks(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    public IBurpExtenderCallbacks callbacks;
    public IExtensionHelpers helpers;
    private Set<String> actualCallRepSet; /// what to replace in last call
    public PrintWriter stdout;
    private PrintWriter stderr;

    private JPanel panel1;
    private JTabbedPane tabbedPane1;

    private JTable extractTable;
    public static ExtractionModel extractTableModel;

    public JTable replaceTable;
    public static ReplaceModel replaceTableModel;

    private JButton addExtractionButton;
    private JButton removeReplacementButton;
    private JButton editReplacementButton;
    private JButton addReplacementButton;
    private JButton removeExtractionButton;
    private JButton editExtractionButton;

    private DebugUtilities debugUtilities;
    private long lastExtractionTime = 0L;

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


        }
    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo) {
//        if (messageIsRequest) {
//
//            for (Replace r: this.getReplacementModel().getReplaces()) {
//                String newRequest = new String(messageInfo.getRequest());
//                newRequest = r.replaceData(newRequest, helpers);
//                messageInfo.setRequest(newRequest.getBytes());
//
//                IHttpRequestResponse newMsgInfo = callbacks.makeHttpRequest(
//                        messageInfo.getHttpService(), newRequest.getBytes());
//
//                stdout.print(newRequest);
//
//            }
//        }
    }
    private void onAddReplacement(){
        AddReplacement addReplacementForm = new AddReplacement(this);
        addReplacementForm.setTitle("Add replacement...");
        //https://stackoverflow.com/questions/12988896/jframe-fixed-width
        addReplacementForm.setSize(new Dimension (294, 252));
        addReplacementForm.setResizable(false);
        addReplacementForm.setVisible(true);
    }
    private void onAddExtraction(){
            AddExtraction addExtractForm = new AddExtraction(this);

            addExtractForm.setTitle("Add extraction...");
            //https://stackoverflow.com/questions/12988896/jframe-fixed-width
            addExtractForm.setSize(new Dimension (400, 210));
            addExtractForm.setResizable(false);
            addExtractForm.setVisible(true);
    }

    //    private MessagesModel loggerMessagesModel;
    public static void main(String[] args) {

        JFrame frame = new JFrame("BurpExtender");
        frame.setContentPane(new BurpExtender().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void initGui() {
        debugUtilities = new DebugUtilities(this);
    }
    /**
     * Check whether it is possible to create extraction point.
     * @return
     */
    public boolean isValidExtraction() {
        return false;
    }

    @Override
    public java.util.List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
        IHttpRequestResponse[] messages = invocation.getSelectedMessages();
//        stdout.println("[*] processing menu");
//
//        if (messages.length > 0) {
//            List<JMenuItem> menu = new LinkedList<>();
//            JMenuItem sendTo = new JMenuItem("Send to " + EXTENSION_NAME);
//            sendTo.addActionListener(new MenuListener(this, messages, MenuActions.A_SEND_TO_EM, getExtMessagesTable()));
//
//            menu.add(sendTo);
//
//            return menu;
//        }
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


        initGui();
        createUIComponents();

        // register callbacks
        callbacks.registerHttpListener(this);
        callbacks.registerContextMenuFactory(this);
        callbacks.registerSessionHandlingAction(this);
        // init gui callbacks
        callbacks.addSuiteTab(this);

        stdout.println("[*] " + EXTENSION_NAME + " " + VERSION);
        stdout.println("Starting debug utilities");

    }

    @Override
    public String getTabCaption() {
        return EXTENSION_NAME_TAB_NAME;
    }
    @Override
    public Component getUiComponent() {
        return tabbedPane1;
    }

    private void createUIComponents() {
        extractTable = new JTable();
        extractTableModel = new ExtractionModel();
        extractTable.setModel(extractTableModel);

        replaceTable = new JTable();
        replaceTableModel = new ReplaceModel(this);
        replaceTable.setModel(replaceTableModel);
    }
    @Override
    public String getActionName() { return "ENHANCED MACROS"; }


    @Override
    public void performAction(IHttpRequestResponse currentRequest, IHttpRequestResponse[] macroItems) {

    }

    public ExtractionModel getExtractionModel() {
        return extractTableModel;
    }

    public ReplaceModel getReplacementModel() {
        return replaceTableModel;
    }

}
