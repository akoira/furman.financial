package by.dak.furman.financial.swing.category;

import by.dak.furman.financial.Department;
import by.dak.furman.financial.swing.ATreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

public class DepartmentNode extends ACNode<Department> {

	@Override
	public Department getDepartment() {
		return getValue();
	}

	@Override
	public void setDepartment(Department department) {
		setValue(department);
	}

	@Override
	public String getName() {
		return getValue().getName();
	}

	@Override
	public void setName(String name) {
		getValue().setName(name);
	}


	public ATreeTableNode getCurrentNode() {
		return getCurrentNode(this);
	}

	private ATreeTableNode getCurrentNode(ATreeTableNode node) {
		int count = node.getChildCount();
		for (int i = 0; i < count; i++) {
			TreeTableNode child = node.getChildAt(i);
			if (child instanceof APeriodNode && ((APeriodNode) child).getValue().isCurrent()) {
				return getCurrentNode((APeriodNode) child);
			}
		}
		return node;
	}

	@Override
	public boolean isTransient() {
		return getValue().getId() == null;
	}
}
