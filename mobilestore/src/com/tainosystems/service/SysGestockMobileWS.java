package com.tainosystems.service;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.jdom2.Element;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.tainosystems.database.DatabaseConnectivity;
import com.tainosystems.helper.Utility;
import com.tainosystems.helper.XMLParsing;

@Path("/sysgestockmobilews")
public class SysGestockMobileWS {

	/**
	 * This GET WS is called when the user or client hit the url
	 * /sysgestockmobilews/getinventory
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 * 
	 * @return It return the xml string having the information that data that is demanded
	 * 
	 */

	@GET
	@Path("/getinventory")
	@Produces(MediaType.TEXT_PLAIN)
	public String getInventory(@QueryParam("xml") String xml,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: getinventory");
		Utility.logData("input XML: "+ xml);
		String result = "";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			String[] data = XMLParsing.getChildernValues(em);
			String userId = data[0];
			String password = data[1];
			String deviceid = data[2];
			DatabaseConnectivity.createConnection();
			if (DatabaseConnectivity.verifyUser(userId, password)) {
				ResultSet res = DatabaseConnectivity.getInventory(deviceid);
				result = XMLParsing.createXML(res, deviceid, "inventory", true);
				res.close();
				DatabaseConnectivity.updateStatus(userId);
			} else {
				result = "User name or password is incorrect";
			}
			
		} catch (Exception e) {
			result = e.getMessage();			
			//e.printStackTrace();
		} finally {
			try {
				DatabaseConnectivity.closeConnection();
			} catch (SQLException e) {
				result += "  " + e.getMessage();
				//e.printStackTrace();
			}
		}
		Utility.logData("output XML: "+ result);
		return result;
	}

	/**
	 * This GET WS is called when the user or client hit the url
	 * /sysgestockmobilews/getpriceupdate
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 *            
	 * @param Context
	 * 			This param is used to extract the information about the user agent like IP address
	 * 
	 * @return It return the xml string having the data which is requested
	 * 
	 */
	
	@GET
	@Path("/getpriceupdate")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPriceUpdate(@QueryParam("xml") String xml,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: getpriceupdate");
		Utility.logData("input XML: "+ xml);
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
		Utility.logData("output XML: "+ result);
		return result;

	}

	/**
	 * This GET WS is called when the user or client hit the url
	 * /sysgestockmobilews/gettypedepense
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 *            
	 * @param Context
	 * 			This param is used to extract the information about the user agent like IP address
	 * 
	 * @return It return the xml string having the data which is requested
	 * 
	 */
	
	@GET
	@Path("/gettypedepense")
	@Produces(MediaType.TEXT_PLAIN)
	public String getDepenseType(@QueryParam("xml") String xml,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: gettypedepense");
		Utility.logData("input XML: "+ xml);
		
		String result = "";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			String[] data = XMLParsing.getChildernValues(em);
			String userId = data[0];
			String password = data[1];
			String deviceid = data[2];
			DatabaseConnectivity.createConnection();
			if (DatabaseConnectivity.verifyUser(userId, password)) {
				ResultSet res = DatabaseConnectivity.select("TYPE_DEPENSE");
				result = XMLParsing.createXML(res, deviceid, "gettypedepense",
						false);
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
		Utility.logData("output XML: "+ result);
		return result;
	}

	/**
	 * This GET WS is called when the user or client hit the url
	 * /sysgestockmobilews/getsystemusers
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 *            
	 * @param Context
	 * 			This param is used to extract the information about the user agent like IP address
	 * 
	 * @return It return the xml string having the data which is requested
	 * 
	 */
	
	@GET
	@Path("/getsystemusers")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUsers(@QueryParam("xml") String xml,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: getsystemusers");
		Utility.logData("input XML: "+ xml);
		
		String result = "";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			String[] data = XMLParsing.getChildernValues(em);
			String userId = data[0];
			String password = data[1];
			String deviceid = data[2];
			DatabaseConnectivity.createConnection();
			if (DatabaseConnectivity.verifyUser(userId, password)) {
				ResultSet res = DatabaseConnectivity.select("UTILISATEURS");
				result = XMLParsing.createXML(res, deviceid, "getsystemusers",
						false);
				res.close();
			} else {
				result = "Either User name/password is incorrect or Your account is deactive";
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
		Utility.logData("output XML: "+ result);
		return result;
	}
	
	/**
	 * This GET WS is called when the user or client hit the url
	 * /sysgestockmobilews/getdeliveryorder
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 *            
	 * @param Context
	 * 			This param is used to extract the information about the user agent like IP address
	 * 
	 * @return It return the xml string having the data which is requested
	 * 
	 */
	
	@GET
	@Path("/getdeliveryorder")
	@Produces(MediaType.TEXT_PLAIN)
	public String getDeliveryOrder(@QueryParam("xml") String xml,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: getdeliveryorder");
		Utility.logData("input XML: "+ xml);
		String result = "Error Occured";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			String[] data = XMLParsing.getChildernValues(em);
			String userId = data[0];
			String password = data[1];
			//String deviceid = data[2];
			String recipientType = data[3];
			int recipientId = Integer.parseInt(data[4]);
			DatabaseConnectivity.createConnection();
			ResultSet parentTable = null;
			ResultSet childTable = null;
			if (DatabaseConnectivity.verifyUser(userId, password)) {
				DatabaseConnectivity.offAutoCommit();
				if ("store".equalsIgnoreCase(recipientType)) {
					parentTable = DatabaseConnectivity.select("TRANSFERTS",
							"NO_TRANSFERT");
					childTable = DatabaseConnectivity.select(
							"LIGNES_TRANSFERTS", "NO_TRANSFERT");
					result = XMLParsing.createXMLFromJoin(parentTable,
							childTable, "getdeliveryorder", "NO_TRANSFERT");
				} else if ("client".equalsIgnoreCase(recipientType)) {
					parentTable = DatabaseConnectivity
							.selectLivraisonOrderBySales(recipientId);
					childTable = DatabaseConnectivity
							.selectLignesLivraisonOrderBySales(recipientId);
					result = XMLParsing.createXMLFromJoin(parentTable,
							childTable, "getdeliveryorder",
							"NO_LIVRAISON_CLIENT");
				}
				parentTable.close();
				childTable.close();
				DatabaseConnectivity.onAutoCommit();
			} else {
				result = "Either User name/password is incorrect or Your account is deactive";
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
		Utility.logData("output XML: "+ result);
		return result;
	}

	/**
	 * This GET WS is called when the user or client hit the url
	 * /sysgestockmobilews/getlivraisonorder
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 *            
	 * @param Context
	 * 			This param is used to extract the information about the user agent like IP address
	 * 
	 * @return It return the xml string having the data which is requested
	 * 
	 */
	
	@GET
	@Path("/getlivraisonorder")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLivraisonOrder(@QueryParam("xml") String xml,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: getlivraisonorder");
		Utility.logData("input XML: "+ xml);
		String result = "Error Occured";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			String[] data = XMLParsing.getChildernValues(em);
			String userId = data[0];
			String password = data[1];
			String deviceid = data[2];
			DatabaseConnectivity.createConnection();
			if (DatabaseConnectivity.verifyUser(userId, password)) {
				DatabaseConnectivity.offAutoCommit();
				ResultSet parentTable = DatabaseConnectivity
						.selectLivraisonOrder(deviceid);
				ResultSet childTable = DatabaseConnectivity
						.selectLignesLivraisonOrder(deviceid);
				result = XMLParsing.createXMLFromJoin(parentTable, childTable,
						"getlivraisonorder", "NO_LIVRAISON_CLIENT");

				parentTable.close();
				childTable.close();
				DatabaseConnectivity.onAutoCommit();
			} else {
				result = "Either User name/password is incorrect or Your account is deactive";
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
		Utility.logData("output XML: "+ result);
		return result;
	}
	
	/**
	 * This Post WS is called when the user or client hit the url
	 * /sysgestockmobilews/postsales
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 *            
	 * @param Context
	 * 			This param is used to extract the information about the user agent like IP address
	 * 
	 * @return It return the xml string having the data which confirmed 
	 * 			that data has been inserted
	 * 
	 */
	
	@POST
	@Path("/postsales")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String saveSales(@FormParam("xml") String xml,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: postsales");
		Utility.logData("input XML: "+ xml);
		
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
		Utility.logData("output XML: "+ result);
		return result;
	}

	/**
	 * This Post WS is called when the user or client hit the url
	 * /sysgestockmobilews/postdepense
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 *            
	 * @param Context
	 * 			This param is used to extract the information about the user agent like IP address
	 * 
	 * @return It return the xml string having the data which confirmed 
	 * 			that data has been inserted
	 * 
	 */
	
	@POST
	@Path("/postdepense")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String saveDepense(@FormParam("xml") String xml,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: postdepense");
		Utility.logData("input XML: "+ xml);
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
		Utility.logData("output XML: "+ result);
		return result;
	}

	/**
	 * This Post WS is called when the user or client hit the url
	 * /sysgestockmobilews/postlivraisonstatus
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 *            
	 * @param Context
	 * 			This param is used to extract the information about the user agent like IP address
	 * 
	 * @return It return the xml string having the data which confirmed 
	 * 			that data has been inserted
	 * 
	 */
	
	@POST
	@Path("/postlivraisonstatus")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String saveLivraisonStatus(@FormParam("xml") String xml,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: postlivraisonstatus");
		Utility.logData("input XML: "+ xml);
		
		String result = "";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			Element id = em.getChild("storeid");
			// Element date = em.getChild("date_transmission");
			List<Element> list = em.getChildren("LIVRAISONS_CLIENTS");
			DatabaseConnectivity.createConnection();
			DatabaseConnectivity.updateElement(list, "NO_LIVRAISON_CLIENT",
					"LIGNES_LIVRAISON_CLIENT", "NO_LIGNE_LIVRAISON");
			result = XMLParsing.createXML(id.getValue(), "livraisonstatusack",
					list, "NO_LIVRAISON_CLIENT");
			
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
		Utility.logData("output XML: "+ result);
		return result;
	}

	// @POST
	// @Path("/posttransfertdone")
	// @Produces(MediaType.TEXT_PLAIN)
	// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	// public String saveTransfertDone(@FormParam("xml") String xml) {
	// String result = "";
	// try {
	// Element em = XMLParsing.getRootFromXMLString(xml);
	// String userId = em.getChild("id").getValue().trim();
	// String password = em.getChild("password").getValue().trim();
	// // Element date = em.getChild("date_transmission");
	// List<Element> list = em.getChildren("TRANSFERTS");
	// DatabaseConnectivity.createConnection();
	// if (DatabaseConnectivity.verifyUser(userId, password)) {
	// DatabaseConnectivity.updateElement(list, "NO_TRANSFERT",
	// "LIGNES_TRANSFERTS", "NO_LIGNE_TRANSFERT");
	// result = XMLParsing.createXML(userId, "posttransfertdone",
	// list, "NO_TRANSFERT");
	// } else {
	// result =
	// "Either User name/password is incorrect or Your account is deactive";
	// }
	// return result;
	// } catch (Exception e) {
	// result = e.getMessage();
	// e.printStackTrace();
	// } finally {
	// try {
	// DatabaseConnectivity.closeConnection();
	// } catch (SQLException e) {
	// result += "  " + e.getMessage();
	// e.printStackTrace();
	// }
	// }
	// return result;
	// }

	/**
	 * 
	 * This Post WS is called when the user or client hit the url
	 * /sysgestockmobilews/posttransfertdone
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 *            
	 * @param Context
	 * 			This param is used to extract the information about the user agent like IP address
	 * 
	 * @return It return the xml string having the data which confirmed 
	 * 			that data has been inserted
	 * 
	 */
	
	@POST
	@Path("/posttransfertdone")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String saveTransfertDoneWithFile(@FormDataParam("xml") String xml,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: posttransfertdone");
		Utility.logData("input XML: "+ xml);
		
		String result = "";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			String userId = em.getChild("id").getValue().trim();
			String password = em.getChild("password").getValue().trim();
			Element date = em.getChild("date_transmission");
			List<Element> list = em.getChildren("TRANSFERTS");
			DatabaseConnectivity.createConnection();
			if (DatabaseConnectivity.verifyUser(userId, password)) {
				DatabaseConnectivity.updateElement(list, "NO_TRANSFERT",
						"LIGNES_TRANSFERTS", "NO_LIGNE_TRANSFERT");
				String uploadedFileLocation = Utility.createFileName(list
						.get(0).getChild("NO_TRANSFERT").getValue(), list
						.get(0).getChild("CODE_MAGASIN_ORIGINE").getValue(),
						list.get(0).getChild("CODE_MAGASIN_DESTINATION")
								.getValue(), date.getValue());
				boolean isSaved = Utility.writeToFile(uploadedInputStream,
						uploadedFileLocation);
				if (isSaved) {
					result = XMLParsing.createXML(userId, "posttransfertdone",
							list, "NO_TRANSFERT");
				}
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
		Utility.logData("output XML: "+ result);
		return result;
	}

	/**
	 * 
	 * This Post WS is called when the user or client hit the url
	 * /sysgestockmobilews/postlivsignature
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 *            
	 * @param Context
	 * 			This param is used to extract the information about the user agent like IP address
	 * 
	 * @return It return the xml string having the data which confirmed 
	 * 			that data has been inserted
	 * 
	 */
	
	@POST
	@Path("/postlivsignature")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String saveLivraisonSignature(@FormDataParam("xml") String xml,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: postlivsignature");
		Utility.logData("input XML: "+ xml);
		
		String result = "Error Occured";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			Element id = em.getChild("storeid");
			Element date = em.getChild("date_transmission");
			List<Element> list = em.getChildren("LIVRAISONS_CLIENTS");
			DatabaseConnectivity.createConnection();
			DatabaseConnectivity.updateElement(list, "NO_LIVRAISON_CLIENT",
					null, null);
			String uploadedFileLocation = Utility.createFileName(list.get(0)
					.getChild("CODE_MAGASIN").getValue(),
					list.get(0).getChild("CODE_CLIENT").getValue(), list.get(0)
							.getChild("NO_LIVRAISON_CLIENT").getValue(),
					date.getValue());
			boolean isSaved = Utility.writeToFile(uploadedInputStream,
					uploadedFileLocation);
			if (isSaved) {
				result = XMLParsing.createSimpleXML(id.getValue(),
						"livsignatureack",
						list.get(0).getChild("NO_LIVRAISON_CLIENT"),
						uploadedFileLocation);
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
		Utility.logData("output XML: "+ result);
		return result;
	}

	/**
	 * 
	 * This Post WS is called when the user or client hit the url
	 * /sysgestockmobilews/postinventorylevel
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 *            
	 * @param Context
	 * 			This param is used to extract the information about the user agent like IP address
	 * 
	 * @return It return the xml string having the data which confirmed 
	 * 			that data has been inserted
	 * 
	 */
	
	@POST
	@Path("/postinventorylevel")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String saveInventoryLevel(@FormParam("xml") String xml,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: postinventorylevel");
		Utility.logData("input XML: "+ xml);
		
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
		Utility.logData("output XML: "+ result);
		return result;
	}

	/**
	 * 
	 * This Post WS is called when the user or client hit the url
	 * /sysgestockmobilews/postremitinventory
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 *            
	 * @param Context
	 * 			This param is used to extract the information about the user agent like IP address
	 * 
	 * @return It return the xml string having the data which confirmed 
	 * 			that data has been inserted
	 * 
	 */
	
	@POST
	@Path("/postremitinventory")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String saveRemitInventory(@FormParam("xml") String xml,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: postremitinventory");
		Utility.logData("input XML: "+ xml);
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
		Utility.logData("output XML: "+ result);
		return result;
	}

	/**
	 * 
	 * This Post WS is called when the user or client hit the url
	 * /sysgestockmobilews/postclients
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 *            
	 * @param Context
	 * 			This param is used to extract the information about the user agent like IP address
	 * 
	 * @return It return the xml string having the data which confirmed 
	 * 			that data has been inserted
	 * 
	 */
	
	@POST
	@Path("/postclients")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String saveClients(@FormParam("xml") String xml,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: postclients");
		Utility.logData("input XML: "+ xml);
		String result = "Error Occured";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			String userId = em.getChild("storeid").getValue().trim(); // changes made for http client previous code is String userId = em.getChild("id").getValue().trim(); 
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
		Utility.logData("output XML: "+ result);
		return result;
	}

	/**
	 * 
	 * This Post WS is called when the user or client hit the url
	 * /sysgestockmobilews/postdeliveryorder
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 *            
	 * @param Context
	 * 			This param is used to extract the information about the user agent like IP address
	 * 
	 * @return It return the xml string having the data which confirmed 
	 * 			that data has been inserted
	 * 
	 */
	
	@POST
	@Path("/postdeliveryorder")
	@Produces(MediaType.TEXT_PLAIN)
	public String saveDeliveryOrder(@FormParam("xml") String xml,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: postdeliveryorder");
		Utility.logData("input XML: "+ xml);
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
		Utility.logData("output XML: "+ result);
		return result;
	}

	/**
	 * 
	 * This Post WS is called when the user or client hit the url
	 * /sysgestockmobilews/postdeliverydone
	 * 
	 * @param xml
	 *            This web service method take xml string in the form of query
	 *            parameter
	 *            
	 * @param Context
	 * 			This param is used to extract the information about the user agent like IP address
	 * 
	 * @return It return the xml string having the data which confirmed 
	 * 			that data has been inserted
	 * 
	 */
	
	@POST
	@Path("/postdeliverydone")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String saveDeliveryDone(@FormDataParam("xml") String xml,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@Context HttpServletRequest request) {
		
		Utility.logData("Remote IP : "+ request.getRemoteHost());
		Utility.logData("Date : "+ new Date());
		Utility.logData("WSName: postdeliverydone");
		Utility.logData("input XML: "+ xml);
		String result = "";
		try {
			Element em = XMLParsing.getRootFromXMLString(xml);
			Element id = em.getChild("storeid");
			Element date = em.getChild("date_transmission");
			List<Element> list = em.getChildren("LIVRAISONS_CLIENTS");
			DatabaseConnectivity.createConnection();
			DatabaseConnectivity.updateElement(list, "NO_LIVRAISON_CLIENT",
					"LIGNES_LIVRAISON_CLIENT", "NO_LIGNE_LIVRAISON");
			String uploadedFileLocation = Utility.createFileName(list.get(0)
					.getChild("NO_LIVRAISON_CLIENT").getValue(), list.get(0)
					.getChild("CODE_CLIENT").getValue(),
					list.get(0).getChild("CODE_MAGASIN").getValue(),
					date.getValue());
			boolean isSaved = Utility.writeToFile(uploadedInputStream,
					uploadedFileLocation);
			if (isSaved) {
				result = XMLParsing.createXML(id.getValue(),
						"livraisonstatusack", list, "NO_LIVRAISON_CLIENT");
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
		Utility.logData("output XML: "+ result);
		return result;
	}
}
