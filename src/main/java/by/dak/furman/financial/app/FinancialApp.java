package by.dak.furman.financial.app;


import bibliothek.gui.DockController;
import bibliothek.gui.dock.DefaultDockable;
import bibliothek.gui.dock.SplitDockStation;
import bibliothek.gui.dock.station.split.SplitDockGrid;
import by.dak.furman.financial.swing.category.CategoriesPanel;
import by.dak.furman.financial.swing.item.ItemsPanel;
import org.apache.commons.lang3.StringUtils;
import org.apache.derby.drda.NetworkServerControl;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.swingx.JXFrame;

import java.awt.*;
import java.net.InetAddress;
import java.util.Locale;

public class FinancialApp extends SingleFrameApplication
{

    private JXFrame mainFrame;
    private AppConfig appConfig;

    @Override
    protected void startup()
    {
        Locale.setDefault(new Locale("ru", "RU"));
        initDbServer();
        appConfig = AppConfig.getAppConfig();
        getContext().getResourceManager().setResourceFolder(StringUtils.EMPTY);

        mainFrame = new JXFrame("Financial Manager");

        SplitDockStation splitDockStation = initDocking();

        setMainFrame(mainFrame);

        Container content = mainFrame.getContentPane();
        content.setLayout(new BorderLayout());
        content.add(splitDockStation.getComponent(), BorderLayout.CENTER);
        show(getMainView());
    }

    private SplitDockStation initDocking()
    {
        DockController dockController = new DockController();
        SplitDockStation splitDockStation = new SplitDockStation();
        dockController.add(splitDockStation);

        SplitDockGrid splitDockGrid = new SplitDockGrid();
        splitDockGrid.addDockable(0, 0, 1, 1, new DefaultDockable(new CategoriesPanel().init()));
        splitDockGrid.addDockable(2, 0, 3, 3, new DefaultDockable(new ItemsPanel().init()));
        splitDockStation.dropTree(splitDockGrid.toTree());
        return splitDockStation;
    }

    private void initDbServer()
    {
        try
        {
            NetworkServerControl server = new NetworkServerControl
                    (InetAddress.getByName("localhost"), 1527);
            server.start(null);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e);
        }
    }


    @Override
    protected void shutdown()
    {
        super.shutdown();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public static void main(String[] args)
    {
        Application.launch(FinancialApp.class, args);
    }
}
