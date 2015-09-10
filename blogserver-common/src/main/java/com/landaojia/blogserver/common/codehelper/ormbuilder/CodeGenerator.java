package com.landaojia.blogserver.common.codehelper.ormbuilder;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author codingwoo <long1795@gmail.com>
 */
public class CodeGenerator {
	Logger log = LoggerFactory.getLogger(this.getClass());

	static final Set<String> ignoreCols = new HashSet<String>();
	static {
		ignoreCols.add("id");
		ignoreCols.add("create_time");
		ignoreCols.add("update_time");
		ignoreCols.add("version");
	}

	private BasicDataSource ds;

	String[] tnames;

	String pack;

	String sql_get_tables;
	String sql_get_columns;

	public CodeGenerator(BasicDataSource ds, String schema, String pack) {
		this.ds = ds;
		this.pack = pack;
		sql_get_tables = "select table_name,table_comment from information_schema.TABLES where table_schema='" + schema + "'";
		sql_get_columns = "select COLUMN_NAME,column_comment from information_schema.COLUMNS where TABLE_SCHEMA='" + schema + "' and table_name='%s'";
	}

	public void generateCode(boolean isUpdate, String output, String tablePrefix, String... tables) throws SQLException {
		tnames = tables;
		Connection conn = ds.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql_get_tables);
		ResultSet rs = ps.executeQuery();
		rs.first();
		while (!rs.isAfterLast()) {
			String tName = rs.getString(1);
			String comment = rs.getString(2);
			if (needGen(tName.toLowerCase())) {
				desc(conn, tName, tablePrefix, comment).write( output);
			}
			rs.next();
		}
		rs.close();
		conn.close();
	}

	/**
	 * 
	 * @param name
	 *            name
	 * @return
	 */
	boolean needGen(String name) {
		if (tnames == null || tnames.length == 0 || "all".equals(tnames[0].toLowerCase())) {
			return true;
		}
		for (String tname : tnames) {
			if (tname.equalsIgnoreCase(name)) {
				return true;
			}
			if (name.matches(tname)) {
				return true;
			}
		}
		return false;
	}

	public Table desc(Connection connection, String tableName, String prefix, String comment) throws SQLException {
		log.info("Fetch table[{}]...", tableName);

		DatabaseMetaData dbmd = connection.getMetaData();
		Statement statement = connection.createStatement();

		Map<String, String> colComments = new HashMap<String, String>();
		ResultSet rs = statement.executeQuery(String.format(sql_get_columns, tableName));
		rs.first();
		while (!rs.isAfterLast()) {
			colComments.put(rs.getString(1).toUpperCase(), rs.getString(2));
			rs.next();
		}

		rs = statement.executeQuery(String.format("select * from %s where 1=2", tableName));
		ResultSetMetaData meta = rs.getMetaData();

		Table t = new Table(pack, tableName, prefix, comment);

		rs = dbmd.getPrimaryKeys(meta.getCatalogName(1), meta.getSchemaName(1), tableName);
		rs.first();
		t.setIdColName(rs.getString(4));

		int cnt = meta.getColumnCount();
		for (int i = 1; i <= cnt; i++) {
			String colName = meta.getColumnName(i);
			if (ignoreCols.contains(colName.toLowerCase())) {
				continue;
			}
			String javaType = meta.getColumnClassName(i);
			log.info("Prasing column[{}] with type[{}].", colName, javaType);
			t.addColumn(colName.toLowerCase(), javaType, meta.getColumnTypeName(i), colComments.get(colName.toUpperCase()));
		}

		return t;
	}
}
