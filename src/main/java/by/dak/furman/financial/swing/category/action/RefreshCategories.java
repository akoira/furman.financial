package by.dak.furman.financial.swing.category.action;

import by.dak.furman.financial.Category;
import by.dak.furman.financial.Item;
import by.dak.furman.financial.Period;
import by.dak.furman.financial.PeriodType;
import by.dak.furman.financial.app.AppConfig;
import by.dak.furman.financial.service.ICategoryService;
import by.dak.furman.financial.service.IItemService;
import by.dak.furman.financial.swing.category.CategoryNode;
import by.dak.furman.financial.swing.category.PeriodNode;
import by.dak.furman.financial.swing.category.RootNode;

import javax.swing.tree.TreePath;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: akoyro
 * Date: 4/25/13
 * Time: 1:03 PM
 */
public class RefreshCategories extends ACategoryAction
{
    private IItemService itemService = AppConfig.getAppConfig().getItemService();

    private ICategoryService categoryService = AppConfig.getAppConfig().getCategoryService();

    protected void makeAction()
    {
        RootNode root = createRoot();
        root.setAmount(itemService.getSum(Item.PROPERTY_amount));
        getModel().setRoot(root);
        initNewCategoryNode();
        initYearNode(new Date());
        TreePath treePath = root.getCurrentPeriodPath();
        getCategoriesPanel().getTree().getTreeSelectionModel().setSelectionPath(treePath);
    }

    private void initNewCategoryNode()
    {
        AddNewCategory addNewCategory = new AddNewCategory();
        addNewCategory.setCategoriesPanel(getCategoriesPanel());
        addNewCategory.action();
    }

    private RootNode createRoot()
    {

        Period period = new Period();
        period.setPeriodType(PeriodType.ALL);
        period.setStartDate(new Date(0l));
        period.setEndDate(new Date());

        RootNode root = new RootNode();
        root.setProperties(createProperties(period));
        root.setValue(period);
        return root;
    }


    private void initYearNode(Date date)
    {
        Period period = new Period();
        period.setPeriodType(PeriodType.YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        resetTime(calendar);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        period.setStartDate(calendar.getTime());
        period.setCurrent(true);

        calendar.add(Calendar.YEAR, 1);
        period.setEndDate(calendar.getTime());

        PeriodNode node = new PeriodNode();

        node.setValue(period);
        node.setProperties(createProperties(period));

        for (int month = 0; month < 12; month++)
        {
            node.add(initMonthNode(period.getStartDate(), month));
        }
        getModel().insertNodeInto(node, getRootNode(), getRootNode().getChildCount());
    }


    private PeriodNode initMonthNode(Date yearDate, int month)
    {
        Period period = new Period();
        period.setPeriodType(PeriodType.MONTH);

        Calendar calendar = Calendar.getInstance();
        int current = calendar.get(Calendar.MONTH);
        period.setCurrent(current == month);

        calendar.setTime(yearDate);
        resetTime(calendar);
        calendar.set(Calendar.MONTH, month);
        period.setStartDate(calendar.getTime());

        calendar.add(Calendar.MONTH, 1);
        period.setEndDate(calendar.getTime());

        PeriodNode node = new PeriodNode();
        node.setValue(period);
        node.setProperties(createProperties(period));

        List<Category> categories = categoryService.getAll();
        for (Category category : categories)
        {
            CategoryNode cNode = AddCategory.createCategoryNode(category);
            node.add(cNode);
        }
        return node;
    }

    private void resetTime(Calendar calendar)
    {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

}
