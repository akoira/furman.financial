package by.dak.furman.financial.swing.category;

import by.dak.common.lang.StringValueAnnotationProcessor;
import by.dak.furman.financial.swing.ATreeTableNode;
import org.jdesktop.swingx.renderer.DefaultTreeRenderer;
import org.jdesktop.swingx.renderer.IconValue;
import org.jdesktop.swingx.renderer.StringValue;

import javax.swing.*;
import java.awt.*;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 1:53 PM
 */
public class DefaultTreeTableRenderer extends DefaultTreeRenderer {

	public DefaultTreeTableRenderer() {
		super(
				new IconValue() {
					@Override
					public Icon getIcon(Object value) {
						if (value instanceof ATreeTableNode) {
							value = ((ATreeTableNode) value).getValue();
						}

						return StringValueAnnotationProcessor.getProcessor().convert2Icon(value);
					}

				},
				new StringValue() {
					@Override
					public String getString(Object value) {
						if (value instanceof ATreeTableNode) {
							value = ((ATreeTableNode) value).getValue();
						}
						return StringValueAnnotationProcessor.getProcessor().convert(value);
					}
				}
		);

	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
	}
}
