package com.tainosystems.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class DatabaseConnectivity {
	// private static final String CREDENTIAL_FILE_NAME =
	// "C:\\src\\june2013\\mobilestore\\mobilestore\\databaseCredentials.xml";
	// private static final String CREDENTIAL_FILE_NAME =
	// "/var/lib/tomcat6/webapps/mobilestore/WEB-INF/databaseCredentials.xml";
	private static final String CREDENTIAL_FILE_NAME = "E:\\eclipse\\mobilestore\\databaseCredentials.xml";
	private static Connection connection = null;
	private static String driverName = "";
	private static String userName = "";
	private static String userPassword = "";
	private static String url = "";

	private DatabaseConnectivity() {
	}

	/**
	 * 
	 * @throws JDOMException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 * 	
	 *  Read the database credentail from xml and create connection,
	 *  if connection is not already established
	 */
	public static void createConnection() throws JDOMException, IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		if (connection == null) {
			getCredentials();
			Class.forName(driverName).newInstance();
			connection = DriverManager.getConnection(url, userName,
					userPassword);
			
		}
	}
	/**
	 * 
	 * @throws SQLException
	 * 
	 * This function is used to on the auto commit so that
	 * our database connection automatically commit the query
	 * after execution
	 * 
	 */
	public static void onAutoCommit() throws SQLException {
		connection.setAutoCommit(true);
		
	}

	/**
	 * 
	 * @throws SQLException
	 * 
	 * This function is use to off the auto commit due to which
	 * we can have multiple Result Set a time and will have to
	 * call commit() function manually after executing query 
	 * 
	 */
	public static void offAutoCommit() throws SQLException {
		connection.setAutoCommit(false);
	}
	
	/**
	 * 
	 * @throws SQLException
	 * 
	 * close connection with the database and set connection to null
	 * to release all resources
	 */
	public static void closeConnection() throws SQLException {
		if (connection != null) {
			connection.close();
			connection = null;
		}
	}

	/**
	 * 
	 * @throws JDOMException
	 * @throws IOException
	 * 
	 * This method is used to get credentails from the xml file
	 * 
	 */

	private static void getCredentials() throws JDOMException, IOException {
		SAXBuilder saxBuilder = new SAXBuilder();
		Document doc = saxBuilder.build(new File(CREDENTIAL_FILE_NAME));
		Element em = doc.getRootElement();
		driverName = em.getChild("driver-name").getValue();
		userName = em.getChild("user-name").getValue();
		userPassword = em.getChild("user-password").getValue();
		url = em.getChild("url").getValue();
	}

	/**
	 * 
	 * @param tableName name of the table from which we have to read data
	 * @return Result Set
	 * @throws SQLException
	 * 
	 * Select data from the given table name	 
	 * 
	 */

	public static ResultSet select(String tableName) throws SQLException {
		String select = "Select * from " + tableName + " ";
		Statement stm = connection.createStatement();
		ResultSet res = stm.executeQuery(select);
		return res;
	}

	/**
	 * 
	 * @param table This parameter store the name of table in which updation is to
	 *            be made
	 * @throws SQLException
	 * 
	 * This function is called after the data of a table is updated in the
	 * remote database In this function we update the webposted columed value to
	 *            Y
	 **/
	public static void updateTable(String table) throws SQLException {
		String update = "update " + table + " set webposted = 'Y' ";
		Statement stm = connection.createStatement();
		stm.executeUpdate(update);
		stm.close();
		connection.commit();
	}

//	public static ResultSet getInventory(String user) throws SQLException {
//		String query = "SELECT a.NO_DEMANDE, a.NO_LIGNE_DEMANDE, a.CODE_ARTICLE, b.NOM_ARTICLE, a.QUANTITE_DEMANDEE,"
//				+ " b.PRIX_VENTE FROM LIGNES_DEMANDE_TRANSFERT a, ARTICLES b where a.CODE_ARTICLE=b.CODE_ARTICLE and"
//				+ " a.NO_DEMANDE in (SELECT NO_DEMANDE FROM DEMANDES_TRANSFERT where MAGASIN_DESTINATION= ?)";
//		PreparedStatement ps = connection.prepareStatement(query);
//		ps.setString(1, user);
//		ResultSet res = ps.executeQuery();
//		return res;
//	}
	
	/**
	 * 
	 * @param user get inventory of the particular user
	 * @return ResetSet containing data of the inventory
	 * @throws SQLException
	 * 
	 * Get data from a view based on the user
	 */
	
	public static ResultSet getInventory(String user) throws SQLException {
		  String query = " SELECT * from DEMANDESTRANSFERTVU where MAGASIN_DESTINATION= ? ";
		  PreparedStatement ps = connection.prepareStatement(query);
		  ps.setString(1, user);   
		  ResultSet res = ps.executeQuery();
		  return res;
	}

	/**
	 * 
	 * @param table 
	 * 			name of the table for selection
	 * @param orderBy
	 * 			column name to whome result is sorted
	 * @return ResultSet
	 * 			containing data of the table
	 * @throws SQLException
	 * 
	 * Execute a select query having order by clause
	 * 
	 */
	
	public static ResultSet select(String table, String orderBy)
			throws SQLException {
		String query = "SELECT * FROM " + table + " order by " + orderBy;
		PreparedStatement ps = connection.prepareStatement(query,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet res = ps.executeQuery();
		return res;
	}
	
	/**
	 * 
	 * @param user
	 * 			name of the user upon which status of the table DEMANDES_TRANSFERT has to updated 
	 * @throws SQLException
	 * 
	 * Execute a updation query and update the statut_demande cloumn
	 * 
	 */
	
	public static void updateStatus(String user) throws SQLException {
		String query = "UPDATE DEMANDES_TRANSFERT SET STATUT_DEMANDE = 'N' WHERE NO_DEMANDE in (SELECT NO_DEMANDE FROM DEMANDES_TRANSFERT where MAGASIN_DESTINATION = ? )";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, user);
		ps.executeUpdate();
		ps.close();
	}
	
	/**
	 * 
	 * @param table
	 * 			table name for the select query			
	 * @param whereColumn
	 * 			column name for the where clause
	 * @param whereValue
	 * 			value of the where clause
	 * @param orderBy
	 * 			column name at which order by clause has to use
	 * @return ResultSet 
	 * 			containing data of the table after executing query
	 * @throws SQLException
	 * 
	 * Execute the select query having where and order by clause
	 * 
	 */
	public static ResultSet select(String table, String whereColumn,
			String whereValue, String orderBy) throws SQLException {
		String query = "SELECT * FROM " + table + " where " + whereColumn
				+ " <> '" + whereValue + "' order by " + orderBy;
		PreparedStatement ps = connection.prepareStatement(query,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet res = ps.executeQuery();
		return res;
	}
	/**
	 * 
	 * @param codeMagasin
	 * @return ResultSet
	 * @throws SQLException
	 * 
	 * 	Execuate a select query with where claue on LIVRAISON_CLIENTS table
	 */
	public static ResultSet selectLivraisonOrder(String codeMagasin)
			throws SQLException {
		String query = "SELECT * FROM LIVRAISONS_CLIENTS WHERE CODE_MAGASIN= ? and STATUT <> 'DELIVERED' order by NO_LIVRAISON_CLIENT ";
		PreparedStatement ps = connection.prepareStatement(query,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ps.setString(1, codeMagasin);
		ResultSet res = ps.executeQuery();
		return res;
	}
	
	/**
	 * 
	 * @param codeMagasin
	 * @return ResultSet
	 * @throws SQLException
	 * 
	 * Execuate a select query with where claue on LIGNES_LIVRAISON_CLIENTS table
	 * 
	 */
	public static ResultSet selectLignesLivraisonOrder(String codeMagasin)
			throws SQLException {
		String query = "SELECT * FROM LIGNES_LIVRAISON_CLIENT WHERE STATUT<> 'DELIVERED'  order by NO_LIVRAISON_CLIENT ";
		PreparedStatement ps = connection.prepareStatement(query,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		// ps.setString(1, codeMagasin);
		ResultSet res = ps.executeQuery();
		return res;
	}
	/**
	 * 
	 * @return ResultSet
	 * @throws SQLException
	 * 
	 *  Execute the select query having left joing between TRANSFERTS AND LIGNES_TRANSFERTS
	 */
	public static ResultSet getOrder() throws SQLException {
		ResultSet res = null;
		String query = "SELECT a.NO_TRANSFERT, a.CODE_MAGASIN_ORIGINE, a.CODE_MAGASIN_DESTINATION, a.DATE_TRANSFERT, "
				+ "b.NO_LIGNE_TRANSFERT, b.CODE_ARTICLE, b.QUANTITE_TRANSFEREE, a.LIVRE_PAR, a.RECU_PAR, a.STATUT"
				+ "FROM TRANSFERTS a left join LIGNES_TRANSFERTS b on a.NO_TRANSFERT = b.NO_TRANSFERT order by a.NO_TRANSFERT";

		PreparedStatement ps = connection.prepareStatement(query);
		res = ps.executeQuery();
		return res;
	}

	/**
	 * 
	 * @param data
	 * 			An String array containing data having list of code_article 
	 * @return ResultSet
	 * @throws SQLException
	 * 
	 * Execute the select query with where cluase on code_article and code_magasin column of article table
	 * 
	 */
	public static ResultSet getPriceUpdates(String[] data) throws SQLException {
		ResultSet res = null;
		String value = "";
		for (int i = 3; i < data.length; i++) {
			value += data[i] + ",";
		}
		value = value.substring(0, value.length() - 1);
		String query = "SELECT CODE_ARTICLE,PRIX_UNITAIRE FROM ARTICLE WHERE CODE_MAGASIN=? AND CODE_ARTICLE IN("
				+ value + ")";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, data[2]);
		res = ps.executeQuery();
		return res;
	}
	
	/**
	 * 
	 * @param user
	 * 			name of ther user which called the WS
	 * @param password
	 * 			password of the user
	 * @return boolean
	 * @throws SQLException
	 * 
	 * This function is used to verify the user that request for the WS 
	 */
	
	public static boolean verifyUser(String user, String password)
			throws SQLException {
		String query = "select motpass from utilisateurs where code_utilisateur = ? and lower(actif) = 'y' ";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, user);
		ResultSet res = ps.executeQuery();
		if (res.next()) {
			String aPassword = res.getString("motpass");
			ps.close();
			if (password.equals(aPassword)) {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}
	
	/**
	 * 
	 * @param root
	 * 			List of the Element having data of parent table
	 * @param returnKey
	 * 			name of the column that data has to return
	 * @param innerRoot
	 * 			name of the table to which parent table has relation
	 * @param isParent
	 * 			is value that has to returned is parent table column or child table column
	 * @return ArrayList<String> 
	 * 			containg data of the elements that are inserted into database
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 * 
	 * This function insert the data in the tables which have one to many relationship
	 * 
	 */
	
	public static ArrayList<String> insertElements(List<Element> root,
			String returnKey, String innerRoot, boolean isParent)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, ParseException {
		ArrayList<String> data = new ArrayList<String>();
		for (Element e : root) {
			insertElement(e);
			if (innerRoot != null && !"".equals(innerRoot)) {
				List<Element> childern = e.getChildren(innerRoot);
				for (Element em : childern) {
					insertElement(em);
					if (!isParent) {
						data.add(em.getChild(returnKey).getValue());
					}
				}
			}
			if (isParent) {
				data.add(e.getChild(returnKey).getValue());
			}
		}
		return data;
	}
	/**
	 * 
	 * @param e
	 * 		Element that has to be inserted in the database
	 *  
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 * 
	 * Execute the insert query which is generated with the help of StmtHelper class
	 * 
	 */
	public static void insertElement(Element e) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			ParseException {
		String query = StmtHelper.genInsertQuery(e, null);
		PreparedStatement ps = connection.prepareStatement(query);
		StmtHelper.fillStmt(ps, e, null);
		int i = ps.executeUpdate();
		ps.close();
		
	}
	
	/**
	 * 
	 * @param list
	 * 			List of the Element having data of parent table
	 * @param key
	 * 			primary key of the parent table
	 * @param child
	 * 			name of the child table
	 * @param childKey
	 * 			primary key of the child table
	 * 
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws ParseException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * 
	 * This function is used to update the data in the database
	 * if there is no data to updated then new data is inserted
	 * into the database
	 * 
	 */
	
	public static void updateElement(List<Element> list, String key,
			String child, String childKey) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, ParseException, NoSuchFieldException,
			SecurityException {
		String[] exceptionColumns = new String[1];
		exceptionColumns[0] = key;
		StmtField field = new StmtField();
		field.fieldname = key;
		for (Element em : list) {
			field.value = em.getChild(key).getValue();
			String query = StmtHelper.genUpdateQuery(em, exceptionColumns,
					field);
			PreparedStatement ps = connection.prepareStatement(query);
			int index = StmtHelper.fillStmtUpdate(ps, em, exceptionColumns);
			StmtHelper.fillWhere(ps, em, field, index);
			int i = ps.executeUpdate();
			// connection.commit();
			ps.close();
			if (i == 0) {
				insertElement(em);
			}
			if (child != null && !"".equals(child)) {
				List<Element> children = em.getChildren(child);
				updateElement(children, childKey, null, null);
			}
		}
	}
	
	/**
	 * 
	 * @param recipientId
	 * 			id of the recipient that call the WS
	 * @return ResultSet
	 * @throws SQLException
	 * 
	 * Exectue the select query for the LIVRAISON_CLIENT TALBE
	 */
	
	public static ResultSet selectLivraisonOrderBySales(int recipientId)
			throws SQLException {
		String query = " SELECT * FROM LIVRAISONS_CLIENTS WHERE NO_VENTE = ? and STATUT<>'DELIVERED' order by NO_LIVRAISON_CLIENT ";
		PreparedStatement ps = connection.prepareStatement(query,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ps.setInt(1, recipientId);
		ResultSet res = ps.executeQuery();
		return res;

	}
	
	/**
	 * 
	 * @param noVente
	 * @return ResultSet
	 * @throws SQLException
	 * 
	 * Execute the select query for the LIGNES_LIVRAISON_CLIENT
	 * 
	 */
	
	public static ResultSet selectLignesLivraisonOrderBySales(int noVente)
			throws SQLException {
		String query = " SELECT * FROM LIGNES_LIVRAISON_CLIENT WHERE STATUT<>'DELIVERED' "
				+ " AND NO_LIVRAISON_CLIENT in  "
				+ " ( SELECT NO_LIVRAISON_CLIENT  FROM LIVRAISONS_CLIENTS WHERE NO_VENTE=? ) "
				+ " order by NO_LIVRAISON_CLIENT ";
		PreparedStatement ps = connection.prepareStatement(query,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ps.setInt(1, noVente);
		ResultSet res = ps.executeQuery();
		return res;
	}
}
