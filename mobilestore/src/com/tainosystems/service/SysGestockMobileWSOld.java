package com.tainosystems.service;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.jdom2.Element;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.tainosystems.database.DatabaseConnectivity;
import com.tainosystems.helper.Utility;
import com.tainosystems.helper.XMLParsing;

@Path("/sysgestockmobilewsold")
public class SysGestockMobileWSOld {

	/**
	 * This function is called when the user or client hit the url
	 * /remotedb/sava
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 * 
	 * @return It return the result about data has been saved or not
	 */

	@GET
	@Path("/getinventory")
	@Produces(MediaType.TEXT_PLAIN)
	public String getInventory(@QueryParam("xml") String xml) {
		String result = "";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			String[] data = XMLParsing.getChildernValues(em);
			String userId = data[0];
			String password = data[1];
			DatabaseConnectivity.createConnection();
			if (DatabaseConnectivity.verifyUser(userId, password)) {
				ResultSet res = DatabaseConnectivity.getInventory(userId);
				result = XMLParsing.createXML(res, userId, "inventory", true);
				res.close();
			} else {
				result = "User name or password is incorrect";
			}

		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				DatabaseConnectivity.closeConnection();
			} catch (SQLException e) {
				result += "  " + e.getMessage();
				e.printStackTrace();
			}
		}
		return result;
	}

	@GET
	@Path("/getpriceupdate")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPriceUpdate(@QueryParam("xml") String xml) {
		String result = "";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			String[] data = XMLParsing.getChildernValues(em);
			DatabaseConnectivity.createConnection();
			ResultSet res = DatabaseConnectivity.getPriceUpdates(data);
			result = XMLParsing.createXML(res, data[0], "priceupdate", false);
			res.close();
			return result;
		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				DatabaseConnectivity.closeConnection();
			} catch (SQLException e) {
				result += "  " + e.getMessage();
				e.printStackTrace();
			}
		}
		return result;

	}

	@GET
	@Path("/gettypedepense")
	@Produces(MediaType.TEXT_PLAIN)
	public String getDepenseType(@QueryParam("xml") String xml) {
		String result = "";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			String[] data = XMLParsing.getChildernValues(em);
			String userId = data[0];
			String password = data[1];
			DatabaseConnectivity.createConnection();
			if (DatabaseConnectivity.verifyUser(userId, password)) {
				ResultSet res = DatabaseConnectivity.select("TYPE_DEPENSE");
				result = XMLParsing.createXML(res, userId, "gettypedepense",false);
				res.close();
			} else {
				result = "Either User name/password is incorrect or Your account is deactive";
			}
			return result;

		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				DatabaseConnectivity.closeConnection();
			} catch (SQLException e) {
				result += "  " + e.getMessage();
				e.printStackTrace();
			}
		}
		return result;
	}

	@GET
	@Path("/getsystemusers")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUsers(@QueryParam("xml") String xml) {
		String result = "";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			String[] data = XMLParsing.getChildernValues(em);
			String userId = data[0];
			String password = data[1];
			DatabaseConnectivity.createConnection();
			if (DatabaseConnectivity.verifyUser(userId, password)) {
				ResultSet res = DatabaseConnectivity.select("UTILISATEURS");
				result = XMLParsing.createXML(res, userId, "getsystemusers",false);
				res.close();
			} else {
				result = "Either User name/password is incorrect or Your account is deactive";
			}
			//return result;
			return "<data>d</data>";

		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				DatabaseConnectivity.closeConnection();
			} catch (SQLException e) {
				result += "  " + e.getMessage();
				e.printStackTrace();
			}
		}

		return result;
	}

	@GET
	@Path("/getdeliveryorder")
	@Produces(MediaType.TEXT_PLAIN)
	public String getDeliveryOrder(@QueryParam("xml") String xml) {
		String result = "Error Occured";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			String[] data = XMLParsing.getChildernValues(em);
			String userId = data[0];
			String password = data[1];
			DatabaseConnectivity.createConnection();
			if (DatabaseConnectivity.verifyUser(userId, password)) {
				DatabaseConnectivity.offAutoCommit();
				ResultSet parentTable = DatabaseConnectivity.select(
						"TRANSFERTS", "NO_TRANSFERT");
				ResultSet childTable = DatabaseConnectivity.select(
						"LIGNES_TRANSFERTS", "NO_TRANSFERT");
				result = XMLParsing.createXMLFromJoin(parentTable, childTable,
						"getdeliveryorder", "NO_TRANSFERT");

				parentTable.close();
				childTable.close();
				DatabaseConnectivity.onAutoCommit();
			} else {
				result = "Either User name/password is incorrect or Your account is deactive";
			}
			return result;

		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				DatabaseConnectivity.closeConnection();
			} catch (SQLException e) {
				result += "  " + e.getMessage();
				e.printStackTrace();
			}
		}

		return result;
	}
	
	@GET
	@Path("/getlivraisonorder")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLivraisonOrder(@QueryParam("xml") String xml) {
		String result = "";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			String[] data = XMLParsing.getChildernValues(em);
			String userId = data[0];
			String password = data[1];
			DatabaseConnectivity.createConnection();
			if (DatabaseConnectivity.verifyUser(userId, password)) {
				DatabaseConnectivity.offAutoCommit();
				ResultSet parentTable = DatabaseConnectivity.select("LIVRAISONS_CLIENTS", "STATUT","COMPLETED","NO_LIVRAISON_CLIENT");
				ResultSet childTable = DatabaseConnectivity.select("LIGNES_LIVRAISON_CLIENT", "STATUT","COMPLETED", "NO_LIVRAISON_CLIENT");
				result = XMLParsing.createXMLFromJoin(parentTable, childTable,
						"getdeliveryorder", "NO_LIVRAISON_CLIENT");

				parentTable.close();
				childTable.close();
				DatabaseConnectivity.onAutoCommit();
			} else {
				result = "Either User name/password is incorrect or Your account is deactive";
			}
			return result;

		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				DatabaseConnectivity.closeConnection();
			} catch (SQLException e) {
				result += "  " + e.getMessage();
				e.printStackTrace();
			}
		}

		return result;
	}

	@POST
	@Path("/postsales")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String saveSales(@FormParam("xml") String xml) {
		String result = "Error Occured";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			Element id = em.getChild("storeid");
			// Element date = em.getChild("date_transmission");
			List<Element> list = em.getChildren("VENTE");
			DatabaseConnectivity.createConnection();
			ArrayList<String> data = DatabaseConnectivity.insertElements(list,
					"NO_VENTE", "ITEMVENDU", true);
			result = XMLParsing.createXMLFromArray(id.getValue(),
					"postsalesack", "NO_VENTE", data);
			return result;
		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				DatabaseConnectivity.closeConnection();
			} catch (SQLException e) {
				result += "  " + e.getMessage();
				e.printStackTrace();
			}
		}

		return result;
	}

	@POST
	@Path("/postdepense")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String saveDepense(@FormParam("xml") String xml) {
		String result = "";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			Element id = em.getChild("storeid");
			// Element date = em.getChild("date_transmission");
			List<Element> list = em.getChildren("DEPENSE");
			DatabaseConnectivity.createConnection();
			ArrayList<String> data = DatabaseConnectivity.insertElements(list,
					"NO_DEPENSE", null, true);
			result = XMLParsing.createXMLFromArray(id.getValue(), "depenseack",
					"NO_DEPENSE", data);
			return result;
		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				DatabaseConnectivity.closeConnection();
			} catch (SQLException e) {
				result += "  " + e.getMessage();
				e.printStackTrace();
			}
		}
		return result;
	}

	@POST
	@Path("/postlivraisonstatus")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String saveLivraisonStatus(@FormParam("xml") String xml) {
		String result = "";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			Element id = em.getChild("storeid");
			// Element date = em.getChild("date_transmission");
			List<Element> list = em.getChildren("LIVRAISONS_CLIENTS");
			DatabaseConnectivity.createConnection();
			DatabaseConnectivity.updateElement(list, "NO_LIVRAISON_CLIENT","LIGNES_LIVRAISON_CLIENT","NO_LIGNE_LIVRAISON");
			result = XMLParsing.createXML(id.getValue(),"livraisonstatusack",list,"NO_LIVRAISON_CLIENT");
			return result;
		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				DatabaseConnectivity.closeConnection();
			} catch (SQLException e) {
				result += "  " + e.getMessage();
				e.printStackTrace();
			}
		}
		return result;
	}
//
//	@POST
//	@Path("/postlivsignature")
//	@Produces(MediaType.TEXT_PLAIN)
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	public String saveLivraisonSignature(@FormDataParam("xml") String xml,
//			@FormDataParam("file") InputStream uploadedInputStream,
//			@FormDataParam("file") FormDataContentDisposition fileDetail) {
//		String result = "Error Occured";
//		try {
//			Element em = XMLParsing.getRootFromXMLString(xml);
//			Element id = em.getChild("storeid");
//			Element date = em.getChild("date_transmission");
//			List<Element> list = em.getChildren("LIVRAISONS_CLIENTS");
//			DatabaseConnectivity.createConnection();
//			DatabaseConnectivity.updateElement(list, "NO_LIVRAISON_CLIENT",null,null);
//			String uploadedFileLocation = Utility.createFileName(list.get(0),
//					date.getValue());
//			boolean isSaved = Utility.writeToFile(uploadedInputStream,
//					uploadedFileLocation);
//			if (isSaved) {
//				result = XMLParsing.createSimpleXML(id.getValue(),
//						"livsignatureack",
//						list.get(0).getChild("NO_LIVRAISON_CLIENT"),
//						uploadedFileLocation);
//			}
//			return result;
//		} catch (Exception e) {
//			result = e.getMessage();
//			e.printStackTrace();
//		} finally {
//			try {
//				DatabaseConnectivity.closeConnection();
//			} catch (SQLException e) {
//				result += "  " + e.getMessage();
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}

	@POST
	@Path("/postinventorylevel")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String saveInventoryLevel(@FormParam("xml") String xml) {
		String result = "Error Occured";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			Element id = em.getChild("storeid");
			// Element date = em.getChild("date_transmission");
			List<Element> list = em.getChildren("INVENTAIRE_MAGASIN");
			DatabaseConnectivity.createConnection();
			ArrayList<String> data = DatabaseConnectivity.insertElements(list,
					"CODE_ARTICLE", null, true);
			result = XMLParsing.createXMLFromArray(id.getValue(),
					"inventorylevelack", "CODE_ARTICLE", data);
			return result;
		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				DatabaseConnectivity.closeConnection();
			} catch (SQLException e) {
				result += "  " + e.getMessage();
				e.printStackTrace();
			}
		}
		return result;
	}

	@POST
	@Path("/postremitinventory")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String saveRemitInventory(@FormParam("xml") String xml) {
		String result = "Error Occured";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			Element id = em.getChild("storeid");
			// Element date = em.getChild("date_transmission");
			List<Element> list = em.getChildren("TRANSFERTS");
			String primaryKey = list.get(0).getChild("NO_TRANSFERT").getValue();
			DatabaseConnectivity.createConnection();
			ArrayList<String> data = DatabaseConnectivity.insertElements(list,
					"CODE_ARTICLE", "LIGNES_TRANSFERTS", false);
			data.add(0, primaryKey);
			String[] tagName = new String[data.size()];
			tagName[0] = "NO_TRANSFERT";
			for (int i = 1; i < tagName.length; i++) {
				tagName[i] = "CODE_ARTICLE";
			}
			result = XMLParsing.createXMLFromArray(id.getValue(),
					"remitinventoryack", data, tagName);
			return result;
		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				DatabaseConnectivity.closeConnection();
			} catch (SQLException e) {
				result += "  " + e.getMessage();
				e.printStackTrace();
			}
		}
		return result;
	}

	@POST
	@Path("/postclients")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String saveClients(@FormParam("xml") String xml) {
		String result = "Error Occured";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			String userId = em.getChild("id").getValue().trim();
			String password = em.getChild("password").getValue().trim();
			List<Element> list = em.getChildren("CLIENTS");
			DatabaseConnectivity.createConnection();
			if (DatabaseConnectivity.verifyUser(userId, password)) {
				ArrayList<String> data = DatabaseConnectivity.insertElements(
						list, "CODE_CLIENT", null, true);
				result = XMLParsing.createXMLFromArray(userId,
						"postclientsack", "CODE_CLIENT", data);
			} else {
				result = "Either User name/password is incorrect or Your account is deactive";
			}
			return result;
		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				DatabaseConnectivity.closeConnection();
			} catch (SQLException e) {
				result += "  " + e.getMessage();
				e.printStackTrace();
			}
		}

		return result;
	}

	@POST
	@Path("/postdeliveryorder")
	@Produces(MediaType.TEXT_PLAIN)
	public String saveDeliveryOrder(@FormParam("xml") String xml) {
		String result = "Error Occured";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			String userId = em.getChild("id").getValue().trim();
			String password = em.getChild("password").getValue().trim();
			List<Element> list = em.getChildren("TRANSFERTS");
			DatabaseConnectivity.createConnection();
			if (DatabaseConnectivity.verifyUser(userId, password)) {
				ArrayList<String> data = DatabaseConnectivity.insertElements(
						list, "NO_TRANSFERT", "LIGNES_TRANSFERTS", true);
				result = XMLParsing.createXMLFromArray(userId,
						"postdeliveryorder", "NO_TRANSFERT", data);
			} else {
				result = "Either User name/password is incorrect or Your account is deactive";
			}
			return result;
		} catch (Exception e) {
			result = e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				DatabaseConnectivity.closeConnection();
			} catch (SQLException e) {
				result += "  " + e.getMessage();
				e.printStackTrace();
			}
		}

		return result;
	}

}
