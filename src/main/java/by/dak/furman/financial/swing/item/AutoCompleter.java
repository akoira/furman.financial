package by.dak.furman.financial.swing.item;

import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * User: akoyro
 * Date: 4/26/13
 * Time: 2:14 PM
 */
public class AutoCompleter<V>
{
    private JTextField textField;
    private Window parentWindow;
    private ListModel<V> model;

    private JWindow popupWindow;
    private JXPanel popupPanel;
    private JXList popupList;

    private Action actionShow;
    private Action actionHide;


    public void init()
    {
        popupList = new JXList(model);

        popupPanel = new JXPanel(new BorderLayout());
        popupPanel.add(new JScrollPane(popupList), BorderLayout.CENTER);

        popupWindow = new JWindow(parentWindow);
        popupWindow.setAlwaysOnTop(true);
        popupWindow.getContentPane().add(popupPanel);
        popupWindow.pack();

        actionShow = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!popupWindow.isVisible())
                    show();
            }
        };

        actionShow = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (popupWindow.isVisible())
                    hide();
            }
        };

        textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "show");
        textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "hide");
        textField.get


    }

    private void show()
    {
        popupWindow.setLocation(textField.getLocationOnScreen());
        popupWindow.setVisible(true);
    }

    private void hide()
    {
        popupWindow.setVisible(false);
    }


    public JTextField getTextField()
    {
        return textField;
    }

    public void setTextField(JTextField textField)
    {
        this.textField = textField;
    }

    public Window getParentWindow()
    {
        return parentWindow;
    }

    public void setParentWindow(Window parentWindow)
    {
        this.parentWindow = parentWindow;
    }

    public ListModel<V> getModel()
    {
        return model;
    }

    public void setModel(ListModel<V> model)
    {
        this.model = model;
    }
}
