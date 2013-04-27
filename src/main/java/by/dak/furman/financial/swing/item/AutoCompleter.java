package by.dak.furman.financial.swing.item;

import com.jgoodies.common.collect.ArrayListModel;
import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/26/13
 * Time: 2:14 PM
 */
public class AutoCompleter<V>
{
    private DefaultCellEditor cellEditor;
    private Window parentWindow;
    private List<V> values;

    private JTextField textField;
    private JWindow popupWindow;
    private JXPanel popupPanel;
    private JXList popupList;

    private Action actionShow;
    private Action actionHide;
    private Action actionNext;
    private Action actionPrev;
    private float opacity;

    private DocumentListener documentListener;

    private boolean registrated = false;


    public void init()
    {
        textField = (JTextField) cellEditor.getComponent();

        cellEditor.addCellEditorListener(new CellEditorListener()
        {
            @Override
            public void editingStopped(ChangeEvent e)
            {
                hide();
            }

            @Override
            public void editingCanceled(ChangeEvent e)
            {
                hide();
            }
        });

        ArrayListModel<V> model = new ArrayListModel<V>(getValues());
        popupList = new JXList(model);
        popupList.setFocusable(false);

        popupPanel = new JXPanel(new BorderLayout());
        popupPanel.add(new JScrollPane(popupList), BorderLayout.CENTER);

        popupWindow = new JWindow(parentWindow);
        popupWindow.setAlwaysOnTop(true);
        popupWindow.getContentPane().add(popupPanel);
        popupWindow.setOpacity(getOpacity());
        popupWindow.pack();

        popupList.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                Object value = popupList.getSelectedValue();
                try
                {
                    textField.getDocument().remove(0, textField.getText().length());
                    textField.getDocument().insertString(0, value.toString(), null);
                    textField.selectAll();
                }
                catch (Exception e1)
                {
                }
            }
        });
        actionShow = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                show(true);
            }
        };

        actionHide = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                hide();
            }
        };

        actionNext = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int index = popupList.getSelectedIndex() + 1;
                if (index == values.size())
                    index = 0;
                popupList.setSelectedIndex(index);
            }
        };
        actionPrev = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int index = popupList.getSelectedIndex() - 1;
                if (index < 0)
                    index = values.size() - 1;
                popupList.setSelectedIndex(index);
            }
        };

        documentListener = new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                show(false);
                updateSelection();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                show(false);
                updateSelection();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                show(false);
                updateSelection();
            }
        };

        textField.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                if (!registrated)
                    registrate();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                if (!registrated)
                    registrate();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                if (!registrated)
                    registrate();
            }
        });
    }

    private void registrate()
    {
        textField.getDocument().addDocumentListener(documentListener);

        textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "showPopup");
        textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "hidePopup");

        textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "selectNextValue");
        textField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "selectPrevValue");

        textField.getActionMap().put("showPopup", actionShow);
        textField.getActionMap().put("hidePopup", actionHide);
        textField.getActionMap().put("selectNextValue", actionNext);
        textField.getActionMap().put("selectPrevValue", actionPrev);


        popupList.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "hidePopup");
        popupList.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "hidePopup");
        popupList.getActionMap().put("hidePopup", actionHide);
        registrated = true;
    }

    private void unregistrate()
    {
        textField.getDocument().removeDocumentListener(documentListener);

        textField.getInputMap(JComponent.WHEN_FOCUSED).remove(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true));
        textField.getInputMap(JComponent.WHEN_FOCUSED).remove(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true));
        textField.getInputMap(JComponent.WHEN_FOCUSED).remove(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true));
        textField.getInputMap(JComponent.WHEN_FOCUSED).remove(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true));
        textField.getActionMap().remove("showPopup");
        textField.getActionMap().remove("hidePopup");

        popupList.getInputMap(JComponent.WHEN_FOCUSED).remove(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true));
        popupList.getActionMap().remove("hidePopup");
        registrated = false;
    }

    private void updateSelection()
    {
        String string = textField.getText();
        int index = popupList.getSearchable().search(string);
        if (index == -1)
            popupList.clearSelection();
        else
            popupList.setSelectedIndex(index);
    }

    private void show(boolean requestFocus)
    {
        if (!popupWindow.isVisible())
        {
            popupWindow.pack();
            popupWindow.setLocation(textField.getLocationOnScreen().x, textField.getLocationOnScreen().y + textField.getHeight());
            popupWindow.setSize(textField.getWidth(), popupWindow.getHeight());
            popupWindow.setVisible(true);
        }

        if (requestFocus)
            popupList.requestFocus();
    }

    private void hide()
    {
        popupWindow.setVisible(false);
        textField.getDocument().addDocumentListener(documentListener);
        if (registrated)
            unregistrate();
    }

    public Window getParentWindow()
    {
        return parentWindow;
    }

    public void setParentWindow(Window parentWindow)
    {
        this.parentWindow = parentWindow;
    }

    public void setOpacity(float opacity)
    {
        this.opacity = opacity;
    }

    public float getOpacity()
    {
        return opacity;
    }

    public List<V> getValues()
    {
        return values;
    }

    public void setValues(List<V> values)
    {
        this.values = values;
    }

    public DefaultCellEditor getCellEditor()
    {
        return cellEditor;
    }

    public void setCellEditor(DefaultCellEditor cellEditor)
    {
        this.cellEditor = cellEditor;
    }
}
