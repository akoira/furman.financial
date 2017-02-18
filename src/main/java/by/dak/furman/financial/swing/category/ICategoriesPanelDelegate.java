package by.dak.furman.financial.swing.category;

import java.util.List;

/**
 * User: akoyro
 * Date: 4/26/13
 * Time: 5:26 PM
 */
public interface ICategoriesPanelDelegate {
	void selectNode(ACNode node);

	void selectNodes(List<ACNode> nodes);

	void cleanSelection();
}
