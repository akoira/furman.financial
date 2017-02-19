package by.dak.furman.financial.service.export.xml

import by.dak.furman.financial.Item
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver

import static org.apache.commons.io.IOUtils.closeQuietly

/**
 * User: akoiro
 * Date: 19/2/17
 */
class XMLBuilder {
    List<Item> items
    File file

    File save() {
        OutputStream out = null
        try {
            out = new FileOutputStream(file)
            DomDriver domDriver = new DomDriver("UTF-8")
            XStream xStream = new XStream(domDriver)
            xStream.toXML(items, out)
        } catch (Exception e) {
            throw new IllegalArgumentException(e)
        } finally {
            closeQuietly(out)
        }
    }
}
