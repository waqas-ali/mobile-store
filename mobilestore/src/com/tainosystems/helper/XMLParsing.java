package com.tainosystems.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.tainosystems.database.DatabaseConnectivity;

public class XMLParsing {

	/**
	 * This function read data from the xml file XML data format should be
	 * simple All of the data store in the xml format is like the array format
	 * 
	 * @param file
	 *            This parameter is used to specify the file name to which we
	 *            have to read data
	 * 
	 * @return data return the xml data in the form of the array having type
	 *         string
	 */
	public static Element getRootFromXMLFile(String file) throws JDOMException,
			IOException {
		SAXBuilder saxBuilder = new SAXBuilder();
		Document doc = saxBuilder.build(new File(file));
		Element em = doc.getRootElement();
		return em;
	}

	/**
	 * 
	 * @param root
	 * 			Root element of the xml
	 * @return String[]
	 * 			An String array having value of all children of root element
	 * 
	 * This function extract all elements within the root element and 
	 * return the vlaues of inner elements
	 */
	public static String[] getChildernValues(Element root) {
		List<Element> list = root.getChildren();
		String[] data = new String[list.size()];
		int i = 0;
		for (Element e : list) {
			data[i] = e.getValue();
			i++;
		}
		return data;
	}

	/**
	 * This method read data from the resultset and parse it into the xml format
	 * 
	 * @param table
	 *            This parameter specify the table name from which resultset is
	 *            associated
	 * 
	 * @param res
	 *            This parameter specify the resultset which contain all of the
	 *            data
	 * 
	 * @param rows
	 *            This parameter is used to specify the maximum rows that are
	 *            converted into the xml at a time
	 * 
	 * @return xml This method return the xml format string of the rows of the
	 *         resultset
	 */

	public static String createXMLFromTable(String db, String table,
			ResultSet res, int rows) throws Exception {
		Object model = Class.forName(table).newInstance();
		table = model.getClass().getSimpleName();
		Field[] f = model.getClass().getFields();
		int size = f.length;
		String xml = "<" + db + ">\n";
		int count = 0;
		do {
			xml += "<" + table + ">\n";
			for (int j = 0; j < size; j++) {
				xml = xml + "<" + f[j].getName() + ">" + res.getString(j + 1)
						+ "</" + f[j].getName() + ">\n";
			}
			xml += "\n</" + table + ">\n";
			count++;
			if (count % rows == 0) {
				break;
			}
		} while (res.next());
		xml += "</" + db + ">\n";
		return xml;
	}

	/**
	 * This method save the xml string into a file. this method is created for
	 * just test purpose
	 * 
	 * @param file
	 *            This parameter is used to specify file name in which data is
	 *            to be saved
	 * 
	 * @param xml
	 *            This parameter is the xml string to which we have to save
	 */
	public static void saveXML(String file, String xml) throws IOException {
		FileWriter fw = new FileWriter(new File(file), true);
		fw.write(xml);
		fw.flush();
		fw.close();
	}

	/**
	 * 
	 * @param res
	 * 			ResultSet containing data of the table 
	 * @param userId
	 * 			Name of the user
	 * @param parent
	 * 			Root element name of the xml
	 * @param isJoin
	 * 			Is result set is made of join or not
	 * @return XML String
	 * @throws Exception
	 * 
	 * This function is used to create xml withe the help of 
	 * result set which is created with the select query
	 */
	public static String createXML(ResultSet res, String userId, String parent,
			boolean isJoin) throws Exception {
		Date d = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ResultSetMetaData rsmd = res.getMetaData();
		int count = rsmd.getColumnCount();
		String tableName = "data";
		if (!isJoin) {
			tableName = rsmd.getTableName(1);
		}
		String xml = "<" + parent + ">\n";
		xml += "<mobileuserid>" + userId + "</mobileuserid>\n";
		xml += "<date-reply>" + f.format(d) + "</date-reply>\n";

		while (res.next()) {
			xml = xml + "<" + tableName + ">\n";
			for (int i = 1; i <= count; i++) {
				String columnName = rsmd.getColumnName(i);
				xml = xml + "<" + columnName + ">" + res.getString(i) + "</"
						+ columnName + ">\n";
			}
			xml = xml + "</" + tableName + ">\n\n";
		}
		xml += "</" + parent + ">";
		return xml;
	}
	/**
	 * 
	 * @param xml
	 * 			XML String that need to be parsed
	 * @return Element
	 * @throws JDOMException
	 * @throws IOException
	 * 
	 * This function is used to get the root element from the xml string
	 * 
	 */
	public static Element getRootFromXMLString(String xml) throws JDOMException, IOException {
		SAXBuilder saxBuilder = new SAXBuilder();
		StringReader reader = new StringReader(xml);
		Document doc = saxBuilder.build(reader);
		Element em = doc.getRootElement();
		return em;
	}

	/**
	 * 
	 * @param userId
	 * 			name of the user
	 * @param parent
	 * 			parent tag name of the xml
	 * @param innerElement
	 * 			inner tag element name
	 * @param data
	 * 			A String array containing value of the inner tag
	 * @return XML String
	 * 
	 * This function is used to create xml from the array which contain the 
	 * value of the inner tag element
	 */
	
	public static String createXMLFromArray(String userId, String parent,
			String innerElement, ArrayList<String> data) {
		String xml = createXMLPortion(userId, parent);
		for (int i = 0; i < data.size(); i++) {
			xml += "<" + innerElement + ">" + data.get(i) + "</" + innerElement
					+ ">\n";
		}
		xml += "</" + parent + ">";
		return xml;
	}

	/**
	 * 
	 * @param userId
	 * 			name of the user
	 * @param parent
	 * 			parent tag name of the xml
	 * @param list
	 * 			list of column that
	 * @param data
	 * 			A String array containing value of the inner tag
	 * @return XML String
	 * 
	 * This function is used to create xml from the array which contain the 
	 * value of the inner tag element
	 */
	
	public static String createXMLFromArray(String userId, String parent,
			ArrayList<String> data, String[] list) {
		String xml = createXMLPortion(userId, parent);
		for (int i = 0; i < data.size(); i++) {
			xml += "<" + list[i] + ">" + data.get(i) + "</" + list[i] + ">\n";
		}
		xml += "</" + parent + ">";
		return xml;
	}

	/**
	 * 
	 * @param userId
	 * @param parent
	 * @return XML String
	 * 
	 * Create only upper part of the xml
	 */
	private static String createXMLPortion(String userId, String parent) {
		String xml = "";
		Date d = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd h:mm a");
		xml += "<" + parent + ">\n";
		xml += "<storeid>" + userId + "</storeid>\n";
		xml += "<date_received>" + f.format(d) + "</date_received>\n";
		return xml;
	}
	/**
	 * 
	 * @param userId
	 * @param parent
	 * @param e1
	 * @param fileName
	 * @return XML String
	 * 
	 * This function is used to create simple xml string
	 * having just one inner element
	 */
	public static String createSimpleXML(String userId, String parent,
			Element e1, String fileName) {
		String xml = createXMLPortion(userId, parent);
		xml += "<" + e1.getName() + ">" + e1.getValue() + "</" + e1.getName()
				+ ">\n";
		xml += "<sigfile>" + fileName + "</sigfile>\n";
		return xml;
	}
	/**
	 * 
	 * @param resP
	 * 			Result Set of Parent Table
	 * @param resC
	 * 			Result Set of Child Table
	 * @param parent
	 * 			root element tag name
	 * @param joinColumn
	 * 			join column name between parent and child
	 * @return	XML String
	 * @throws SQLException
	 * 
	 * This function is used to create xml for parent and child table
	 */
	public static String createXMLFromJoin(ResultSet resP, ResultSet resC,
			String parent, String joinColumn) throws SQLException {
		String xml = "";
		Date d = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ResultSetMetaData rsmdP = resP.getMetaData();
		int count = rsmdP.getColumnCount();
		String tableNameP = rsmdP.getTableName(1);

		xml = "<" + parent + ">\n";

		xml += "<date-reply>" + f.format(d) + "</date-reply>\n";

		while (resP.next()) {
			xml += "<" + tableNameP + ">\n\n";
			String id = resP.getString(joinColumn);
			for (int i = 1; i <= count; i++) {
				String columnName = rsmdP.getColumnName(i);
				xml = xml + "<" + columnName + ">" + resP.getString(i) + "</"
						+ columnName + ">\n";
			}
			xml += "\n" + createXMLFromChildTable(resC, id, joinColumn);
			xml = xml + "</" + tableNameP + ">\n\n";
		}
		xml += "</" + parent + ">";
		return xml;
	}
	
	/**
	 * 
	 * @param res
	 * @param id
	 * @param column
	 * @return
	 * @throws SQLException
	 * 
	 * This function is called for the creating of
	 * child table xml in the parent table xml
	 */
	
	private static String createXMLFromChildTable(ResultSet res, String id,
			String column) throws SQLException {
		String xml = "";
		ResultSetMetaData rsmd = res.getMetaData();
		int count = rsmd.getColumnCount();
		String tableName = rsmd.getTableName(1);
		while (res.next()) {
			if (!id.trim().equalsIgnoreCase(res.getString(column).trim())) {
				continue;
			}
			xml = xml + "<" + tableName + ">\n";
			for (int i = 1; i <= count; i++) {
				String columnName = rsmd.getColumnName(i);
				xml = xml + "<" + columnName + ">" + res.getString(i) + "</"
						+ columnName + ">\n";
			}
			xml = xml + "</" + tableName + ">\n\n";
		}
		res.beforeFirst();
		return xml;
	}

	/**
	 * 
	 * @param id
	 * 			name of the user
	 * @param parent
	 * 			parent tag name of the xml
	 * @param primarykey
	 * 			inner tag element name
	 * @param list
	 * 			A list containing value of the inner element
	 * @return XML String
	 * 
	 * This function is used to create xml from the array which contain the 
	 * value of the inner tag element
	 */
	
	public static String createXML(String id, String parent, List<Element> list, String primaryKey) {
		String xml = createXMLPortion(id, parent);
		for ( Element em : list) {
			xml += "<" + primaryKey + ">" + em.getChild(primaryKey).getValue() + "</"+ primaryKey + ">\n";
		}
		xml = xml + "</" + parent + ">\n\n";
		return xml;
	}

//	public static void main(String args[]) throws Exception {
//		try {
//			DatabaseConnectivity.createConnection();
//			ResultSet res = DatabaseConnectivity.select("TRANSFERTS");
//			ResultSetMetaData rsmd = res.getMetaData();
//			res.close();
//			String tableName = rsmd.getTableName(1);
//			
//			System.out.println(tableName+" "+rsmd.getColumnCount());
//			ResultSet parentTable = DatabaseConnectivity.selectAll(
//					"TRANSFERTS", "NO_TRANSFERT");
//			ResultSet childTable = DatabaseConnectivity.selectAll(
//					"LIGNES_TRANSFERTS", "NO_TRANSFERT");
//			String result = XMLParsing.createXMLFromJoin(parentTable,
//					childTable, "getdeliveryorder", "NO_TRANSFERT");
//			System.out.println(result);
//		} finally {
//			DatabaseConnectivity.closeConnection();
//		}
//	}
}
