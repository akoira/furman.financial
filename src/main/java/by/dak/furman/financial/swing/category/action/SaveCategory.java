package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.swing.category.ACNode;
import by.dak.furman.financial.swing.category.APeriodNode;
import by.dak.furman.financial.swing.category.CategoryNode;
import by.dak.furman.financial.swing.category.RootNode;
import org.apache.commons.lang3.StringUtils;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 12:27 PM
 */
public class SaveCategory extends ACAction<CategoryNode>
{
    private Category category;

    @Override
    protected void before()
    {
        category = getNode().getValue();
    }

    protected void makeAction()
    {
        if (category.getId() == null)
        {
            getCategoryService().add(category);
            getModel().removeNodeFromParent(getNode());

            AddNewCategory addNewCategory = new AddNewCategory();
            addNewCategory.setNode((RootNode) getRootNode());
            addNewCategory.setPanel(getPanel());
            addNewCategory.action();

            addCategoryNode();
        }
        else
        {
            getCategoryService().save(category);
        }

    }

    protected boolean validate()
    {
        return StringUtils.stripToNull(category.getName()) != null && getCategoryService().getByName(category.getName()) == null;
    }

    private void addCategoryNode()
    {
        int count = getRootNode().getChildCount();
        for (int i = 0; i < count; i++)
        {
            if (getRootNode().getChildAt(i) instanceof APeriodNode)
            {
                APeriodNode yearNode = (APeriodNode) getRootNode().getChildAt(i);
                int yCount = yearNode.getChildCount();
                for (int k = 0; k < yCount; k++)
                {
                    APeriodNode monthNode = (APeriodNode) yearNode.getChildAt(k);
                    CategoryNode categoryNode = createCategoryNode(category, monthNode);
                    getModel().insertNodeInto(categoryNode, monthNode, monthNode.getChildCount());

                    if (monthNode.getPeriod().isCurrent())
                    {
                        ExpandNode expandNode = new ExpandNode();
                        expandNode.setPanel(getPanel());
                        expandNode.setNode(categoryNode);
                        expandNode.action();
                    }
                }
            }
        }
    }

    public static CategoryNode createCategoryNode(Category category, ACNode parent)
    {
        CategoryNode categoryNode = new CategoryNode();
        categoryNode.setValue(category);
        parent.fillChildNode(categoryNode);
        return categoryNode;
    }
}
