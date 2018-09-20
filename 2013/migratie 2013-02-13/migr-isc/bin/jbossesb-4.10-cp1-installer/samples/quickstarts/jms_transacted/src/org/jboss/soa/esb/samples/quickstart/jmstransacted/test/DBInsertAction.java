/*
 * JBoss, Home of Professional Open Source Copyright 2006, JBoss Inc., and
 * individual contributors as indicated by the @authors tag. See the
 * copyright.txt in the distribution for a full listing of individual
 * contributors.
 * 
 * This is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this software; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA, or see the FSF
 * site: http://www.fsf.org.
 */
package org.jboss.soa.esb.samples.quickstart.jmstransacted.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.ConfigurationException;
import org.jboss.soa.esb.actions.ActionLifecycle;
import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

/**
 * 
 * @author <a href="mailto:daniel.bevenius@gmail.com">Daniel Bevenius</a>				
 *
 */
public class DBInsertAction implements ActionLifecycle
{
	private Logger log = Logger.getLogger( DBInsertAction.class );
	
	private String sql;
	private String dataSourceName;
	
	private int counter = 1;
	
	public DBInsertAction(final ConfigTree config) throws ConfigurationException 
	{
		dataSourceName = config.getRequiredAttribute( "datasource-name" );
		sql = config.getRequiredAttribute( "db-insert-sql" );
		
	}
	
	public Message process( final Message message ) throws ActionProcessingException
	{
		Connection con = null;
		PreparedStatement stmnt = null;
		final String inputText = message.getBody().get() + " counter[" + counter + "]";
		try
		{
			con = getConnection();
			stmnt = con.prepareStatement( sql );
			stmnt.setString( 1, inputText );
			int executeUpdate = stmnt.executeUpdate();
			
			if ( executeUpdate == 1 )
				log.info("Successfully inserted [" + inputText + "] into jms_transacted_table");
			else 
				log.info("Failed to inserted [" + inputText + "] into jms_transacted_table");
			
			counter++;
		}
		catch (Exception e)
		{
			log.error( "Exception " , e );
			throw new ActionProcessingException( e );
		}
		finally
		{
			if (stmnt != null) 	try { stmnt.close();} 	catch (SQLException e) { log.warn( e ); /* ignore */ }
			if (con != null) 	try { con.close();  } 	catch (SQLException e) { log.warn( e ); /* ignore */ }
		}

		return message;
	}

	public void destroy() throws ActionLifecycleException { }

	public void initialise() throws ActionLifecycleException { }
	
	private Connection getConnection() throws NamingException, SQLException
	{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup( dataSourceName );
		return ds.getConnection();
	}

}
