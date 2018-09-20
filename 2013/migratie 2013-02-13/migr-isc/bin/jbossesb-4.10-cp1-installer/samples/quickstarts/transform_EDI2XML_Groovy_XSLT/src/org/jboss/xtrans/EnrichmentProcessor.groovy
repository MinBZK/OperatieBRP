/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and others contributors as indicated 
 * by the @authors tag. All rights reserved. 
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors. 
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 * 
 * (C) 2005-2006
 */
package org.jboss.xtrans;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.milyn.cdr.SmooksConfigurationException;
import org.milyn.cdr.SmooksResourceConfiguration;
import org.milyn.container.ExecutionContext;
import org.milyn.delivery.dom.DOMElementVisitor;
import org.milyn.resource.URIResourceLocator;
import org.milyn.xml.DomUtils;
import org.w3c.dom.Element;

/**
 * Sample "simple" enrichment Java based processing unit.
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 */
public class EnrichmentProcessor implements DOMElementVisitor {

	private static final String ENRICHMENTS_URI = "enrichments-uri";
	private Properties enrichments = new Properties();
	private String attributeName = null;
	
	public void setConfiguration(SmooksResourceConfiguration config) {
		String enrichmentURI = config.getStringParameter(ENRICHMENTS_URI);
		InputStream enrichmentDataStream;
		
		// Load the enrichments table...
		if(enrichmentURI == null) {
			throw new SmooksConfigurationException("'" + ENRICHMENTS_URI + "' parameter not specified.");
		}
		try {
			enrichmentDataStream = (new URIResourceLocator()).getResource(enrichmentURI);
			enrichments.load(enrichmentDataStream);
		} catch (IOException e) {
			throw new SmooksConfigurationException("Error loading 'enrichments' table from URI '" + enrichmentURI + "'.");
		}
		// Get the attribute name - if not specified, assuming element text...
		attributeName = config.getStringParameter("attribute-name");
	}

	public void visitBefore(Element element, ExecutionContext execContext) {
		// We'll perfom the enrichment process as soon as we encouter the element
		// and before we process the element's child nodes.

		if(attributeName != null) {
			String enrichmentKey = element.getAttribute(attributeName);
			if(enrichmentKey != null) {
				String enrichment = enrichments.getProperty(enrichmentKey.trim());
				if(enrichment != null) {
					element.setAttribute(attributeName, enrichment);
				}
			}
		} else {
			String enrichmentKey = element.getTextContent();
			if(enrichmentKey != null) {
				String enrichment = enrichments.getProperty(enrichmentKey.trim());
				if(enrichment != null) {
					DomUtils.removeChildren(element);
					DomUtils.addLiteral(element, enrichment);
				}
			}
		}
	}

	public void visitAfter(Element element, ExecutionContext execContext) {
	}
}
