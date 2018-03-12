package burp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

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
    public static GlobalExtractionModel extractTableModel;

    public JTable replaceTable;
    public static GlobalReplaceModel replaceTableModel;

    private JButton addExtractionButton;
    private JButton removeReplacementButton;
    private JButton editReplacementButton;
    private JButton addReplacementButton;
    private JButton removeExtractionButton;
    private JButton editExtractionButton;
    private JButton getSelectedButton;
    private JButton getExtractSelectedButton;

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

            extractTableModel = new GlobalExtractionModel();
            replaceTableModel = new GlobalReplaceModel();

            extractTable.setModel(extractTableModel);
            replaceTable.setModel(replaceTableModel);
        }
        editReplacementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Replace replaceEdit = replaceTableModel.getReplace(replaceTable.getSelectedRow());

                BurpExtender.getInstance().stdout.println
                        (replaceEdit.getId() + ": " + replaceEdit.getLinkedExtractMap().size());
                AddReplacement addExtractForm = new AddReplacement
                        (replaceTableModel.getReplace(replaceTable.getSelectedRow()));
                addExtractForm.setTitle("Edit replacement...");
                addExtractForm.setSize(new Dimension (400, 454));
                addExtractForm.setResizable(false);
                addExtractForm.setVisible(true);
                replaceEdit.addLinkedExtraction(GlobalExtractionModel._getExtraction(3));
            }
        });
        editExtractionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddExtraction addExtractForm = new AddExtraction();

                addExtractForm.setTitle("Edit extraction...");
                //https://stackoverflow.com/questions/12988896/jframe-fixed-width
                addExtractForm.setSize(new Dimension (400, 210));
                addExtractForm.setResizable(false);
                addExtractForm.setVisible(true);
            }
        });
        getSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stdout.println(replaceTable.getSelectedRow());
            }
        });
        getExtractSelectedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stdout.println(extractTable.getSelectedRow());
            }
        });
    }

    private void dbgTable(){
        TableTest dbg = new TableTest();

        dbg.setSize(new Dimension(400,400));
        dbg.setVisible(true);

    }

    public static void println(String msg){
        BurpExtender.getInstance().stdout.println(msg);
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

        AddReplacement addReplacementForm = new AddReplacement();
        addReplacementForm.setTitle("Add replacement...");
        //https://stackoverflow.com/questions/12988896/jframe-fixed-width
        addReplacementForm.setSize(new Dimension (400, 454));
        addReplacementForm.setResizable(false);
        addReplacementForm.setVisible(true);
    }
    private void onAddExtraction(){
            AddExtraction addExtractForm = new AddExtraction();

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

        // register callbacks
        callbacks.registerHttpListener(this);
        callbacks.registerContextMenuFactory(this);
        callbacks.registerSessionHandlingAction(this);
        // init gui callbacks
        callbacks.addSuiteTab(this);

        stdout.println("[*] " + EXTENSION_NAME + " " + VERSION);
        stdout.println("Starting debug utilities");

//        dbgTable();
    }

    @Override
    public String getTabCaption() {
        return EXTENSION_NAME_TAB_NAME;
    }
//    @Override
    public Component getUiComponent() {
        return tabbedPane1;
    }


    @Override
    public String getActionName() { return "ENHANCED MACROS"; }


    @Override
    public void performAction(IHttpRequestResponse currentRequest, IHttpRequestResponse[] macroItems) {

    }

    public GlobalExtractionModel getExtractionModel() {
        return extractTableModel;
    }

    public GlobalReplaceModel getReplacementModel() {
        return replaceTableModel;
    }

}
