package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.app.AppConfig;
import by.dak.furman.financial.service.ICategoryService;
import by.dak.furman.financial.swing.category.CategoriesPanel;
import by.dak.furman.financial.swing.category.CategoryNode;
import by.dak.furman.financial.swing.category.PeriodNode;
import org.apache.commons.lang3.StringUtils;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 12:27 PM
 */
public class AddCategory extends ACategoryAction
{
    private CategoryNode categoryNode;

    private Category category;

    private ICategoryService categoryService = AppConfig.getAppConfig().getCategoryService();

    @Override
    protected void before()
    {
        category = categoryNode.getValue();
    }

    protected void makeAction()
    {

        categoryService.add(category);
        getModel().removeNodeFromParent(categoryNode);
        addCategoryNode();
        AddNewCategory addNewCategory = new AddNewCategory();
        addNewCategory.setCategoriesPanel(getCategoriesPanel());
        addNewCategory.action();
    }

    protected boolean validate()
    {
        return StringUtils.stripToNull(category.getName()) != null &&
                !category.getName().equals(getResourceMap(AddNewCategory.class).getString(AddNewCategory.RESOURCE_KEY_newCategoryName));
    }

    private void addCategoryNode()
    {
        int count = getRootNode().getChildCount();
        for (int i = 0; i < count; i++)
        {
            if (getRootNode().getChildAt(i) instanceof PeriodNode)
            {
                PeriodNode yearNode = (PeriodNode) getRootNode().getChildAt(i);
                int yCount = yearNode.getChildCount();
                for (int k = 0; k < yCount; k++)
                {
                    PeriodNode monthNode = (PeriodNode) yearNode.getChildAt(k);
                    CategoryNode categoryNode = createCategoryNode(category);
                    getModel().insertNodeInto(categoryNode, monthNode, monthNode.getChildCount());
                }
            }
        }
    }

    public static CategoryNode createCategoryNode(Category category)
    {
        CategoryNode categoryNode = new CategoryNode();
        categoryNode.setProperties(ACategoryAction.createProperties(category));
        categoryNode.setValue(category);
        return categoryNode;
    }


    public CategoryNode getCategoryNode()
    {
        return categoryNode;
    }

    public void setCategoryNode(CategoryNode categoryNode)
    {
        this.categoryNode = categoryNode;
    }

    public static AddCategory valueOf(CategoryNode categoryNode, CategoriesPanel categoriesPanel)
    {
        AddCategory action = new AddCategory();
        action.setCategoryNode(categoryNode);
        action.setCategoriesPanel(categoriesPanel);
        return action;
    }

    public void setCategoryService(ICategoryService categoryService)
    {
        this.categoryService = categoryService;
    }
}
