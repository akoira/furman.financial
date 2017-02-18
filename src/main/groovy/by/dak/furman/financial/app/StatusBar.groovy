package by.dak.furman.financial.app

import groovy.transform.CompileStatic
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.ProgressBar
import org.jdesktop.swingx.JXStatusBar

import static javafx.scene.paint.Color.ALICEBLUE

/**
 * Created by pervoliner on 18.02.2017.
 */
@CompileStatic
class StatusBar {

    JXStatusBar statusBar

    private JFXPanel panel

    //javafx
    private Group root
    private Scene scene
    private ProgressBar progressBar

    private void init() {
        statusBar = new JXStatusBar()
        panel = new JFXPanel()
        statusBar.add(panel)
        Platform.runLater({ initProgressBar() })
    }

    private void initProgressBar() {
        root = new Group()
        scene = new Scene(root, ALICEBLUE)
        progressBar = new ProgressBar(0)
        root.getChildren().add(progressBar)
        panel.setScene(scene)
    }

    static StatusBar instanceOf() {
        StatusBar statusBar = new StatusBar()
        statusBar.init()
        return statusBar
    }
}
