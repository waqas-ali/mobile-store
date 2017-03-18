package com.tainosystems.database;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.tainosystems.model.*;
import org.jdom2.Element;

public class StmtHelper {
	private static final String sql_delim = ", ";
	private static final char sql_delim_char = ',';
	private static final String sql_sp = " ";
	private static final String sql_openb = " (";
	private static final String sql_closeb = ") ";
	private static final String sql_eqpar = " = ?";
	private static final String sql_par = " ?";
	private static final String model_package = "com.tainosystems.model.";

	public static String genInsertQuery(Object model, String[] exceptionColumns) {
		StringBuffer buffer = new StringBuffer("INSERT INTO ");
		StringBuffer buffer2 = new StringBuffer(" VALUES (");
		buffer.append(model.getClass().getSimpleName()).append(sql_openb);

		Field[] fieldDefs = model.getClass().getFields();
		int size = fieldDefs.length;
		try {
			for (int i = 0; i < size; i++) {
				if (isNameExist(fieldDefs[i].getName(), exceptionColumns)) {
					continue;
				}
				if (fieldDefs[i].get(model) != null) {
					buffer.append(fieldDefs[i].getName());
					buffer.append(sql_delim);
					buffer2.append(sql_par);
					buffer2.append(sql_delim);
				}
			}
			if (buffer.charAt(buffer.length() - 2) == sql_delim_char) {
				buffer.replace(buffer.length() - 2, buffer.length(), sql_closeb);
				buffer2.replace(buffer2.length() - 2, buffer2.length(),
						sql_closeb);
			} else {
				buffer.append(sql_closeb);
				buffer2.append(sql_closeb);
			}
			buffer.append(buffer2);
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
		return buffer.toString();
	}

	public static void fillStmt(PreparedStatement stmt, Object model,
			String[] exceptionColumns) throws SQLException,
			IllegalArgumentException, ParseException {
		Field[] fieldDefs = model.getClass().getFields();
		int idx = 0;
		try {
			int size = fieldDefs.length;
			for (int i = 0; i < size; i++) {
				if (isNameExist(fieldDefs[i].getName(), exceptionColumns)) {
					continue;
				}
				if (fieldDefs[i].get(model) != null) {
					updateStmt(stmt, fieldDefs[i], fieldDefs[i].get(model),
							++idx);
				}
			}
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
	}


	public static void updateStmt(PreparedStatement stmt, Field field,
			Object value, int idx) throws SQLException, ParseException {
		updateStmt(stmt, field.getType(), value, idx);
	}

	public static void updateStmt(PreparedStatement stmt, Class type,
			Object value, int idx) throws SQLException, ParseException {
		if (type == java.lang.String.class) {
			stmt.setString(idx, (String) value);
		} else if (type == java.lang.Integer.class) {
			if (value == null)
				stmt.setNull(idx, Types.NUMERIC);
			else
				stmt.setInt(idx, ((Integer) value).intValue());
		} else if (type == java.lang.Double.class) {
			if (value == null)
				stmt.setNull(idx, Types.NUMERIC);
			else
				stmt.setDouble(idx, ((Double) value).doubleValue());
		} else if (type == java.lang.Float.class) {
			if (value == null)
				stmt.setNull(idx, Types.NUMERIC);
			else
				stmt.setFloat(idx, ((Float) value).floatValue());
		} else if (type == java.sql.Date.class) {
			if (value == null)
				stmt.setNull(idx, Types.DATE);
			else
				stmt.setDate(idx, (java.sql.Date) value);
		} else if (type == java.sql.Timestamp.class) {
			if (value == null)
				stmt.setNull(idx, Types.TIMESTAMP);
			else
				stmt.setTimestamp(idx, (java.sql.Timestamp) value);
		} else if (type == java.util.Date.class) {
			if (value == null)
				stmt.setNull(idx, Types.DATE);
			else {
				java.util.Date utilDate = null;
				if (value instanceof java.util.Date) {
					utilDate = (java.util.Date) value;
				} else if (value instanceof java.lang.String) {
					SimpleDateFormat f = new SimpleDateFormat(
							"yyyy-MM-dd h:mm a");
					utilDate = f.parse((String) value);
				}
				java.sql.Timestamp sqlDate = new java.sql.Timestamp(
						utilDate.getTime());
				stmt.setTimestamp(idx, sqlDate);
			}
		} else if (type.getName().equalsIgnoreCase("int")) {
			if (value == null)
				stmt.setNull(idx, Types.NUMERIC);
			else
				stmt.setInt(idx, Integer.parseInt(value.toString().trim().replaceAll("\\s", "")));
		} else if (type.getName().equalsIgnoreCase("double")) {
			if (value == null)
				stmt.setNull(idx, Types.NUMERIC);
			else {
				String v = value.toString().replaceAll("\\s", "");
				NumberFormat nf = NumberFormat.getInstance(Locale.US);
				double d = nf.parse(v).doubleValue();
				stmt.setDouble(idx, d);
			}
		} else if (type.getName().equalsIgnoreCase("float")) {
			if (value == null)
				stmt.setNull(idx, Types.NUMERIC);
			else {
				String v = value.toString().replaceAll("\\s", "");
				NumberFormat nf = NumberFormat.getInstance(Locale.US);
				float f = nf.parse(v).floatValue();
				stmt.setFloat(idx, f);
			}
		}
	}

	public static String genUpdateQuery(Object model, ArrayList whereList)
			throws SQLException {
		return genUpdateQuery(model, whereList, null);
	}

	// Generate Update Query Using model
	public static String genUpdateQuery(Object model, ArrayList whereList,
			String[] exceptionColumns) throws SQLException {
		StringBuffer buffer = new StringBuffer("UPDATE "
				+ model.getClass().getSimpleName() + " SET ");
		Field[] fieldDefs = model.getClass().getFields();
		StmtField dbf = null;
		int size = fieldDefs.length;
		for (int i = 0; i < size; i++) {
			if (isNameExist(fieldDefs[i].getName(), exceptionColumns)) {
				continue;
			}
			buffer.append(fieldDefs[i].getName());
			buffer.append(sql_eqpar);
			buffer.append(sql_delim);
		}
		if (buffer.charAt(buffer.length() - 2) == sql_delim_char) {
			buffer.replace(buffer.length() - 2, buffer.length(), sql_sp);
		} else {
			buffer.append(sql_sp);
		}
		if (whereList != null && whereList.size() > 0) {
			buffer.append(" WHERE ");
			for (int i = 0; i < whereList.size(); i++) {
				dbf = (StmtField) whereList.get(i);
				buffer.append(dbf.fieldname);
				buffer.append(dbf.oper);
				buffer.append(sql_par);
				buffer.append(" AND ");
			}
			int index = buffer.toString().lastIndexOf(" AND ");
			buffer.replace(index, buffer.length(), sql_sp);
		}
		return buffer.toString();
	}

	public static int fillStmtUpdate(PreparedStatement stmt, Object model,
			String[] exceptionColumns) throws SQLException,
			IllegalArgumentException, ParseException {
		Field[] fieldDefs = model.getClass().getFields();
		int idx = 0;
		try {
			int size = fieldDefs.length;
			for (int i = 0; i < size; i++) {
				if (isNameExist(fieldDefs[i].getName(), exceptionColumns)) {
					continue;
				}
				updateStmt(stmt, fieldDefs[i], fieldDefs[i].get(model), ++idx);
			}
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
		return idx;
	}


	public static void fillWhere(PreparedStatement stmt, Object model,
			ArrayList whereList, int idx) throws SQLException, ParseException {
		Field field = null;
		StmtField psf = null;
		try {
			if (whereList != null) {
				int size = whereList.size();
				for (int i = 0; i < size; i++) {
					psf = (StmtField) whereList.get(i);
					if (model != null) {
						field = model.getClass().getField(psf.fieldname);
						updateStmt(stmt, field, psf.value, ++idx);
					} else {
						updateStmt(stmt, psf.value.getClass(), psf.value, ++idx);
					}
				}
			}
		} catch (NoSuchFieldException nsfe) {
			nsfe.printStackTrace();
		}
	}

	private static boolean isNameExist(String name, String values[]) {
		if (values != null) {
			for (int index = 0; index < values.length; index++) {
				if (name.equals(values[index])) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String genInsertQuery(Element em, String[] exceptionColumns)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		String name = em.getName().toLowerCase();
		Object model = Class.forName(model_package + name).newInstance();
		StringBuffer buffer = new StringBuffer("INSERT INTO ");
		StringBuffer buffer2 = new StringBuffer(" VALUES (");
		buffer.append(model.getClass().getSimpleName()).append(sql_openb);

		Field[] fieldDefs = model.getClass().getFields();
		int size = fieldDefs.length;
		try {
			for (int i = 0; i < size; i++) {
				if (isNameExist(fieldDefs[i].getName(), exceptionColumns)) {
					continue;
				}
				String fieldName = fieldDefs[i].getName().toUpperCase();
				Element child = em.getChild(fieldName);
				if (child != null && child.getValue() != null
						&& !"".equalsIgnoreCase(child.getValue())) {
					buffer.append(fieldName);
					buffer.append(sql_delim);
					buffer2.append(sql_par);
					buffer2.append(sql_delim);
				}
			}
			if (buffer.charAt(buffer.length() - 2) == sql_delim_char) {
				buffer.replace(buffer.length() - 2, buffer.length(), sql_closeb);
				buffer2.replace(buffer2.length() - 2, buffer2.length(),
						sql_closeb);
			} else {
				buffer.append(sql_closeb);
				buffer2.append(sql_closeb);
			}
			buffer.append(buffer2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	public static void fillStmt(PreparedStatement stmt, Element em,
			String[] exceptionColumns) throws SQLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, ParseException {
		String name = em.getName().toLowerCase();
		Object model = Class.forName(model_package + name).newInstance();
		Field[] fieldDefs = model.getClass().getFields();
		int idx = 0;
		int size = fieldDefs.length;
		for (int i = 0; i < size; i++) {
			if (isNameExist(fieldDefs[i].getName().toUpperCase(), exceptionColumns)) {
				continue;
			}
			String fieldName = fieldDefs[i].getName().toUpperCase();
			Element child = em.getChild(fieldName);
			if (child != null && child.getValue() != null
					&& !"".equalsIgnoreCase(child.getValue())) {
				updateStmt(stmt, fieldDefs[i], child.getValue().trim(), ++idx);
			}
		}
	}
	
	// Generate Update Query Using Element
	public static String genUpdateQuery(Element em,
			String[] exceptionColumns, StmtField where) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		String name = em.getName().toLowerCase();
		Object model = Class.forName(model_package + name).newInstance();

		StringBuffer buffer = new StringBuffer("UPDATE "
				+ model.getClass().getSimpleName().toUpperCase() + " SET ");
		Field[] fieldDefs = model.getClass().getFields();
		int size = fieldDefs.length;
		for (int i = 0; i < size; i++) {
			if (isNameExist(fieldDefs[i].getName().toUpperCase(), exceptionColumns)) {
				continue;
			}
			String fieldName = fieldDefs[i].getName().toUpperCase();
			Element child = em.getChild(fieldName);
			if (child != null && child.getValue() != null
					&& !"".equalsIgnoreCase(child.getValue())) {
				buffer.append(fieldName);
				buffer.append(sql_eqpar);
				buffer.append(sql_delim);
			}
		}
		if (buffer.charAt(buffer.length() - 2) == sql_delim_char) {
			buffer.replace(buffer.length() - 2, buffer.length(), sql_sp);
		} else {
			buffer.append(sql_sp);
		}
		if (where != null) {
			buffer.append(" WHERE ");
			buffer.append(where.fieldname);
			buffer.append(where.oper);
			buffer.append(sql_par);
			buffer.append(" AND ");
			int index = buffer.toString().lastIndexOf(" AND ");
			buffer.replace(index, buffer.length(), sql_sp);
		}
		return buffer.toString();
	}
	
	public static int fillStmtUpdate(PreparedStatement stmt, Element em,
			String[] exceptionColumns) throws SQLException,
			IllegalArgumentException, ParseException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		String name = em.getName().toLowerCase();
		Object model = Class.forName(model_package + name).newInstance();
		Field[] fieldDefs = model.getClass().getFields();
		int idx = 0;
		int size = fieldDefs.length;
		for (int i = 0; i < size; i++) {
			if (isNameExist(fieldDefs[i].getName().toUpperCase(), exceptionColumns)) {
				continue;
			}
			String fieldName = fieldDefs[i].getName().toUpperCase();
			Element child = em.getChild(fieldName);
			if (child != null && child.getValue() != null
					&& !"".equalsIgnoreCase(child.getValue())) {
				updateStmt(stmt, fieldDefs[i], child.getValue(), ++idx);
			}
		}
		return idx;
	}
	
	public static void fillWhere(PreparedStatement stmt, Element e,
			StmtField where, int idx) throws SQLException, ParseException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, NoSuchFieldException, SecurityException {
		String name = e.getName().toLowerCase();
		Object model = Class.forName(model_package + name).newInstance();
		Field field = null;
		if (where != null) {
			field = model.getClass().getField(where.fieldname.toLowerCase());
			updateStmt(stmt, field.getType(), where.value, ++idx);
		}		
	}
}