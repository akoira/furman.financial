/*
 * Copyright ish group pty ltd. All rights reserved. http://www.ish.com.au No copying or use of this code is allowed without permission in writing from ish.
 */
package by.dak.furman.financial.app;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jdesktop.swingx.error.ErrorInfo;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;

import static javax.swing.SwingUtilities.invokeLater;
import static org.jdesktop.swingx.JXErrorPane.showDialog;

/**
 * User: akoiro
 * Date: 19/2/17
 */
public class UIUtils {
	public static void openFileDir(File file) {
		try {
			Desktop desktop = Desktop.getDesktop();
			desktop.open(file.getParentFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void openWarnDialog(String message, JComponent owner) {
		invokeLater(() -> showDialog(owner,
				new ErrorInfo(message,
						message,
						message,
						message,
						null,
						Level.WARNING, Collections.EMPTY_MAP)));
	}


	public static void openErrorDialog(Throwable throwable, JComponent owner) {
		invokeLater(() -> showDialog(owner,
				new ErrorInfo(throwable.getLocalizedMessage(),
						throwable.getLocalizedMessage(),
						ExceptionUtils.getStackTrace(throwable),
						"Unexpected Exception",
						throwable,
						Level.SEVERE, Collections.EMPTY_MAP)));
	}

	public static void invokeInEventDispatch(Runnable runnable, JComponent owner) {
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(runnable);
			} catch (Exception e) {
				UIUtils.openErrorDialog(e, owner);
			}
		}

	}

}
