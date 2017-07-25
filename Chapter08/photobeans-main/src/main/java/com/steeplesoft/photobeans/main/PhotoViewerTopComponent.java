package com.steeplesoft.photobeans.main;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.Lookups;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.steeplesoft.photobeans.main//PhotoViewer//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "PhotoViewerTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "com.steeplesoft.photobeans.main.PhotoViewerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_PhotoViewerAction",
        preferredID = "PhotoViewerTopComponent"
)
@Messages({
    "CTL_PhotoViewerAction=PhotoViewer",
    "CTL_PhotoViewerTopComponent=PhotoViewer Window",
    "HINT_PhotoViewerTopComponent=This is a PhotoViewer window"
})
public final class PhotoViewerTopComponent extends TopComponent {

    private final String photo;
    private JFXPanel fxPanel;
    private PhotoViewerController controller;
    private static final Logger LOGGER = Logger.getLogger(PhotoViewerTopComponent.class.getName());

    public PhotoViewerTopComponent() {
        this("");
    }

    public PhotoViewerTopComponent(String photo) {
        initComponents();
        this.photo = photo;
        File file = new File(photo);
        setName(file.getName());
        setToolTipText(photo);
        associateLookup(Lookups.singleton(photo));
        setLayout(new BorderLayout());
        init();
    }

    private void init() {
        fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);
        Platform.setImplicitExit(false);
        Platform.runLater(this::createScene);
    }
    
    private void createScene() {
        try {
            URL location = getClass().getResource("/fxml/photoviewer.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

            Parent root = (Parent) fxmlLoader.load(location.openStream());
            Scene scene = new Scene(root);
            fxPanel.setScene(scene);
            controller = (PhotoViewerController) fxmlLoader.getController();
            controller.setPhoto(photo);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of
     * this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
