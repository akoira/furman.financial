package by.dak.furman.financial.swing.category.action;

import by.dak.common.swing.treetable.Property;
import by.dak.furman.financial.AObject;
import by.dak.furman.financial.Category;
import by.dak.furman.financial.Period;
import by.dak.furman.financial.service.ICategoryService;
import by.dak.furman.financial.swing.category.AAmountNode;
import by.dak.furman.financial.swing.category.CategoriesPanel;
import by.dak.furman.financial.swing.category.RootNode;
import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:09 PM
 */
public abstract class ACategoryAction
{

    private CategoriesPanel categoriesPanel;
    private ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(getClass());

    @Autowired
    private ICategoryService categoryService;


    public void action()
    {
        before();
        if (validate())
            makeAction();
        after();
    }

    protected void after()
    {
    }

    protected void before()
    {
    }

    protected boolean validate()
    {
        return true;
    }

    protected abstract void makeAction();

    public DefaultTreeTableModel getModel()
    {
        return categoriesPanel.getModel();
    }

    public RootNode getRootNode()
    {
        return (RootNode) getModel().getRoot();
    }

    public static List<Property> createProperties(Object value)
    {
        ArrayList<Property> properties = new ArrayList<Property>();
        if (value instanceof Period)
        {
            properties.add(Property.valueOf(AObject.PROPERTY_name, false));
            properties.add(Property.valueOf(AAmountNode.PROPERTY_amount, false));
        }
        else if (value instanceof Category)
        {
            properties.add(Property.valueOf(AObject.PROPERTY_name, true));
            properties.add(Property.valueOf(AAmountNode.PROPERTY_amount, false));
        }
        else
            throw new IllegalArgumentException();
        return properties;
    }

    public CategoriesPanel getCategoriesPanel()
    {
        return categoriesPanel;
    }

    public void setCategoriesPanel(CategoriesPanel categoriesPanel)
    {
        this.categoriesPanel = categoriesPanel;
    }

    public ResourceMap getResourceMap()
    {
        return resourceMap;
    }

    public ResourceMap getResourceMap(Class aClass)
    {
        return Application.getInstance().getContext().getResourceMap(aClass);
    }
}
