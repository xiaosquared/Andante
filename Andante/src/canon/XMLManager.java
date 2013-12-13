package canon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLManager {

	// populates ArrayList<Measure>
	public static void readXML(String filename, List<Measure> measures, int note_offset) {

		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(filename);
		
		try {
			 Document document = (Document) builder.build(xmlFile);
			 Element rootNode = document.getRootElement();
			 List list = rootNode.getChildren("measure");
			 
			 // look through each <measure>
			 for (int i = 0; i < list.size(); i++) {
				 Element s = (Element) list.get(i);
				 List notes = s.getChildren("note");
				 
				 // for each <note> in the <measure>
				 int[] noteArray = new int[notes.size()];
				 float[] beatArray = new float[noteArray.length];
				 int[] velocityArray = new int[noteArray.length];
				 
				 // populate note and beat arrays
				 for (int k = 0; k < noteArray.length; k++) {
					 Element n = (Element) notes.get(k);
					 
					 noteArray[k] = Utils.getMidiNumber(n.getAttributeValue("type")) + note_offset;
					 beatArray[k] = Float.parseFloat(n.getAttributeValue("beat"));
					 velocityArray[k] = Integer.parseInt(n.getAttributeValue("vel"));
				 }
				 
				 // create new Measure from populated arrays and put it in measures array
				 Measure m = new Measure(noteArray, beatArray, velocityArray, i);
				 measures.add(m);
			 }
			 
		 } catch (IOException io) {
				System.out.println(io.getMessage());
		 } catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		 }
	}
}
