/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.jvm.HsqlConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.util.LiquibaseUtil;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dumping the database met DB-unit
 * 
 */
public class DbUnitDump {
	private static Logger logger = LoggerFactory.getLogger(DbUnitDump.class);

	private static String[] tableNames = { "AFKORTING", "APPLICATIE", "ASPECT",
			"BEDRIJFSREGELATTRIBUUT", "BEDRIJFSREGEL_EXT",
			"BEDRIJFSREGEL_IMPLEMENTATIE", "BERICHTSJABLOON_EIGENSCHAPPEN",
			"BERICHTSJABLOON_ITEM", "BRON", "BRONATTRIBUUT", "CONTROLE",
			"CONTROLE_RESULTAAT", "EA_CPINSTANCE", "EA_ELEMENT",
			"EA_FOUTMELDING", "EA_REQUIREMENT", "EA_TRACE", "ELEMENT",
			"EXPORTREGEL", "FORMULIER", "FRAME",
			"FRAMEVELD",
			"INSTELLING",
			"OBJECTTYPE_STATISTIEK",
			"OM_RESYNC_REGEL",
			"PLAATS_IN_ARCHITECTUUR", "RAPPORTAGE", "SOORT_BEDRIJFSREGEL",
			"SOORT_BERICHTSJABLOON", "SOORT_ELEMENT", "SOORT_EXPORT",
			"SOORT_TEKST", "STATUS_INTAKE", "TAG_IN_CODE", "TEKST", "TUPLE",
			"VERSIE_TAG", "VERWIJZING", "XSD_CODE_FRAGMENT" };

	public DbUnitDump() {

	}

	public void export() {
		IDatabaseConnection connection = null;
		try {
			connection = getBMRExportConnection();

			if (connection == null) {
				return;
			}
			// partial database export
			QueryDataSet partialDataSet = new QueryDataSet(connection);
			logger.info("Dataset created, now adding tables.");
			// partialDataSet.addTable("ELEMENT", "SELECT * FROM ELEMENT");
			for (String tableName : tableNames) {
				try {
					partialDataSet.addTable(tableName);
					logger.info("Added " + tableName);
				} catch (DataSetException ex) {
					logger.error("Could not add table " + tableName
							+ " to the export set.", ex);
				}
			}
			FlatXmlDataSet.write(partialDataSet, new FileOutputStream(
					"bmr-export.xml"));
			
			
			logger.info("Done writing the export xml file");
			org.dbunit.dataset.csv.CsvDataSetWriter.write(partialDataSet,
					new File("bmr-export.csv"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("POEF",e);
		} catch (DatabaseUnitException e) {
			// TODO Auto-generated catch block
			logger.error("POEF",e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("POEF",e);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("POEF",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("POEF",e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("POEF",e);
			}
		}
	}
	
	
	
	private void createSchema() throws LiquibaseException, SQLException, ClassNotFoundException {
	    ResourceAccessor opener = new FileSystemResourceAccessor();
	    Database db = new HsqlDatabase();
	    liquibase.database.DatabaseConnection connection = new HsqlConnection(getJDBCConnectionImportDatabase());
	    Liquibase base = new Liquibase("src/main/resources/bmr-initial-changelog.xml", opener, connection);
	    logger.info("Liquibase version:" + LiquibaseUtil.getBuildVersion());
	    
	    base.dropAll("clear");
	    base.update("optuigen");
	}

	public void importMetaRegister(File file)
			throws ClassNotFoundException, DatabaseUnitException, IOException,
			SQLException {
		IDatabaseConnection connection = getCodeGenerationImportConnection();
		connection.getConfig().setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
		//IDataSet dataSet = new FlatXmlDataSet(file, true);
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);
		FlatXmlDataSet dataSet = builder.build(file);
		//prepareDatabase();
		try {
            createSchema();
        } catch (LiquibaseException e) {
           logger.error("LIQUIBASE",e);
        }
		dumpCurrentTables();
		disableForeignKeyConstraints();
		
		try {
		//TODO can we loop over the tables and do this per dataset? catching the org.dbunit.dataset.NoSuchTableException
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
		}catch(org.dbunit.dataset.NoSuchTableException ex) {
		    logger.error("Tabel niet gevonden", ex);
		}
		enableForeignKeyConstraints();
		dumpCurrentTables();
	}

	private void prepareDatabase() throws SQLException, ClassNotFoundException {
	    Connection connection = getJDBCConnectionImportDatabase();
	    connection.prepareStatement("SET DATABASE DEFAULT TABLE TYPE CACHED").execute();
	    //connection.prepareStatement("CREATE SCHEMA BMR AUTHORIZATION DBA").execute();
	    //connection.prepareStatement("CREATE USER generator PASSWORD generator").execute();
	    connection.prepareStatement("ALTER USER generator SET INITIAL SCHEMA BMR").execute();
	    connection.prepareStatement("SET SCHEMA BMR").execute();
	    connection.commit();
	    connection.close();
	}
	
	
	private void dumpCurrentTables() throws SQLException, ClassNotFoundException {
	    Connection connection = getJDBCConnectionImportDatabase();

	    PreparedStatement showTables = connection.prepareStatement("SELECT * FROM INFORMATION_SCHEMA.SYSTEM_TABLES where TABLE_TYPE='TABLE'");
	    ResultSet tables = showTables.executeQuery();
	    while (tables.next()) {
	        logger.info("Table:" + tables.getString("TABLE_NAME") + ", schema " + tables.getString("TABLE_SCHEM"));
	        ResultSet nrOfRecords = connection.prepareStatement("SELECT COUNT(*) FROM " + tables.getString("TABLE_NAME") + "").executeQuery();
	        nrOfRecords.next();
	        int rowCount = nrOfRecords.getInt(1);
	        logger.info("Number of records in table " + tables.getString("TABLE_NAME") + " = " + rowCount);
	    }
        connection.close();
    }

    private void disableForeignKeyConstraints() throws SQLException, ClassNotFoundException {
        Connection connection = getJDBCConnectionImportDatabase();
        PreparedStatement disableFK = connection.prepareStatement("SET DATABASE REFERENTIAL INTEGRITY FALSE");
        disableFK.execute();
 
        connection.close();  

    }
    

    private void enableForeignKeyConstraints() throws SQLException, ClassNotFoundException {
        Connection connection = getJDBCConnectionImportDatabase();
        connection.prepareStatement("SET DATABASE REFERENTIAL INTEGRITY TRUE").execute();

        connection.close();  

    }


    private void createHsqldbTables(IDataSet dataSet, Connection connection) throws DataSetException, SQLException {
	    String[] tableNames = dataSet.getTableNames();

	    String sql = "";
	    for (String tableName : tableNames) {
	      ITable table = dataSet.getTable(tableName);
	      ITableMetaData metadata = table.getTableMetaData();
	      Column[] columns = metadata.getColumns();

	      sql += "create table " + tableName + "( ";
	      boolean first = true;
	      for (Column column : columns) {
	        if (!first) {
	          sql += ", ";
	        }
	        String columnName = column.getColumnName();
	        String type = resolveType((String) table.getValue(0, columnName));
	        if(type.equalsIgnoreCase("varchar")) {
	        	type = type +"(255)";
	        }
	        sql += columnName + " " + type;
	        if (first) {
	          sql += " primary key";
	          first = false;
	        }
	      }
	      sql += "); ";
	    }
	    PreparedStatement pp = connection.prepareStatement(sql);
	    pp.executeUpdate();
	}

	private String resolveType(String str) {
	  try {
	    if (new Double(str).toString().equals(str)) {
	      return "double";
	    }
	  } catch (Exception e) {}

	  try {
	    if (new Integer(str).toString().equals(str)) {
	      return "int";
	    }
	  } catch (Exception e) {}

	  return "varchar";
	}

	private static Connection getJDBCConnectionImportDatabase() throws SQLException, ClassNotFoundException {
		Class driverClass = Class.forName("org.hsqldb.jdbcDriver");
		Connection jdbcConnection = DriverManager.getConnection(
				"jdbc:hsqldb:file:database/brpcodegeneration.db;create=true;user=generator;password=generator;shutdown=true", "generator", "generator");
		return jdbcConnection;
	}
	
	private static IDatabaseConnection getCodeGenerationImportConnection() throws ClassNotFoundException, DatabaseUnitException, SQLException {
		
		
		return new DatabaseConnection(getJDBCConnectionImportDatabase());
	}

	private static IDatabaseConnection getBMRExportConnection()
			throws SQLException, DatabaseUnitException, ClassNotFoundException {
		Class driverClass = Class.forName("org.firebirdsql.jdbc.FBDriver");
		Connection jdbcConnection = DriverManager.getConnection(
				"jdbc:firebirdsql://brp-metaregister.modernodam.nl:3050/BRP",
				"GEN", "gen");

		return new DatabaseConnection(jdbcConnection);
	}

	public static void main(String[] args) throws Exception {

		DbUnitDump dumper = new DbUnitDump();
		//dumper.export();
		dumper.importMetaRegister(new File("bmr-export.xml"));
		// database connection

		// ITableFilter filter = new DatabaseSequenceFilter(connection);
		// filter.accept("Laag");
		// IDataSet filteredDataSet = new FilteredDataSet(filter,
		// connection.createDataSet());
		// FlatXmlDataSet.write(filteredDataSet, new
		// FileOutputStream("bmr-export-filtered.xml"));

		org.dbunit.util.FileHelper.createInputSource(new File("test-input"));
		// org.dbunit.util.SQLHelper.printAllTables(metaData, outputStream);

		// write DTD file
		// FlatDtdDataSet.write(connection.createDataSet(), new
		// FileOutputStream("bmr-schema.dtd"));

		// full database export
		/*
		 * IDataSet fullDataSet = connection.createDataSet();
		 * FlatXmlDataSet.write(fullDataSet, new FileOutputStream("full.xml"));
		 */
		// dependent tables database export: export table X and all tables that
		// have a PK which is a FK on X, in the right order for insertion
		/*
		 * String[] depTableNames =
		 * TablesDependencyHelper.getAllDependentTables( connection, "X" );
		 * IDataSet depDataset = connection.createDataSet( depTableNames );
		 * FlatXmlDataSet.write(depDataset, new
		 * FileOutputStream("dependents.xml"));
		 */

	}


}
