/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public final class ConfigurationLocator {
    private final List<Configuration.FileMatcher> matchers = arrayList();
    private boolean useJsfActorId;

    private static <T> List<T> arrayList() {
        return new ArrayList<T>();
    }

    private ConfigurationLocator() {
    }

    private static Configuration configuration = null;

    public static Configuration getInstance() {
        synchronized (ConfigurationLocator.class) {
            if (configuration == null) {
                final ConfigurationLocator configurationLocator = new ConfigurationLocator();
                configurationLocator.configure();
                configuration = new Configuration(configurationLocator.matchers, configurationLocator.useJsfActorId);
            }
            return configuration;
        }
    }

    private void configure() throws FacesException {
        searchJars();
        searchWar();
    }

    private static enum Elem {
        JBPM4JSF_CONFIG("jbpm4jsf-config"),
        AUTHENTICATION_SERVICE("authentication-service"),
        USE_JSF_ACTOR("use-jsf-actor"),
        PROCESS_FILE_MATCHER("process-file-matcher"),
        PATTERN("pattern"),
        FILE_NAME("file-name"),
        CONTENT_TYPE("content-type");

        private final String elemName;

        Elem(final String elemName) {
            this.elemName = elemName;
        }

        String getName() {
            return elemName;
        }
    }

    static {
        final Map<String, Elem> map = new HashMap<String, Elem>();
        for (Elem e : Elem.values()) {
            map.put(e.getName(), e);
        }
        elements = Collections.unmodifiableMap(map);
    }

    private static final Map<String, Elem> elements;

    private Elem lookup(Element element) {
        final Elem elem = elements.get(element.getLocalName());
        if (elem == null) {
            throw unexpectedElement(element);
        }
        return elem;
    }

    private Iterable<Element> childrenOf(Element element) {
        return new ElementIterable(element);
    }

    private static final class ElementIterable implements Iterable<Element> {
        private final Element element;

        ElementIterable(final Element element) {
            this.element = element;
        }

        public Iterator<Element> iterator() {
            return new ElementIterator(element);
        }
    }

    private static final class ElementIterator implements Iterator<Element> {

        private Element next;
        private Node position;

        public ElementIterator(final Element element) {
            next = null;
            position = element.getFirstChild();
        }

        public boolean hasNext() {
            if (next == null) {
                spinToNext();
            }
            return next != null;
        }

        private void spinToNext() {
            if (next == null) {
                while (position != null) {
                    try {
                        if (position instanceof Element) {
                            next = (Element) position;
                            return;
                        }
                    } finally {
                        position = position.getNextSibling();
                    }
                }
            }
        }

        public Element next() {
            if (next == null) {
                throw new NoSuchElementException("next() past end");
            }
            try {
                return next;
            } finally {
                next = null;
                spinToNext();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() not allowed");
        }
    }

    private RuntimeException unexpectedElement(final Element element) {
        return new IllegalStateException("Unexpected element \"" + element.getLocalName() + "\"");
    }

    private RuntimeException missingContent(final String elementName) {
        return new IllegalArgumentException("Missing required data for element \"" + elementName + "\"");
    }

    private void handleAuthenticationService(final Element parent) {
        for (Element element : childrenOf(parent)) {
            switch (lookup(element)) {
                case USE_JSF_ACTOR: useJsfActorId = Boolean.valueOf(element.getTextContent()); break;
                default: throw unexpectedElement(element);
            }
        }
    }

    private void handleProcessFileMatcher(final Element parent) {
        String patternText = null;
        String contentType = null;
        String fileName = null;
        for (Element element : childrenOf(parent)) {
            switch (lookup(element)) {
                case PATTERN: patternText = element.getTextContent().trim(); break;
                case CONTENT_TYPE: contentType = element.getTextContent().trim(); break;
                case FILE_NAME: fileName = element.getTextContent().trim(); break;
                default: throw unexpectedElement(element);
            }
        }
        if (patternText == null || patternText.length() == 0) {
            throw missingContent("pattern");
        }
        if (contentType == null || contentType.length() == 0) {
            throw missingContent("content-type");
        }
        if (fileName == null || fileName.length() == 0) {
            throw missingContent("file-name");
        }
        matchers.add(new Configuration.FileMatcher(Pattern.compile(patternText), contentType, fileName));
    }

    private void handleConfiguration(URL resource) {
        final InputStream inputStream;
        try {
            inputStream = resource.openStream();
        } catch (IOException e) {
            FacesException ex = new FacesException("Failed to parse jbpm4jsf configuration file: " + e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setNamespaceAware(true);
            final DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            final Document document = builder.parse(inputStream);
            final Element docElement = document.getDocumentElement();
            switch (lookup(docElement)) {
                case JBPM4JSF_CONFIG: break;
                default: throw unexpectedElement(docElement);
            }
            for (Element element : childrenOf(docElement)) {
                switch (lookup(element)) {
                    case AUTHENTICATION_SERVICE: handleAuthenticationService(element); break;
                    case PROCESS_FILE_MATCHER: handleProcessFileMatcher(element); break;
                    default: throw unexpectedElement(element);
                }
            }
        } catch (Exception e) {
            FacesException ex = new FacesException("Failed to parse jbpm4jsf configuration file: " + e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.log(Level.WARNING, "Failed to close URL input stream: " + e.getMessage());
            }
        }
    }

    private void searchJars() {
        // TODO: alphabetize by jar name
        final String path = "META-INF/jbpm4jsf-config.xml";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = ConfigurationLocator.class.getClassLoader();
        }
        final Enumeration<URL> resources;
        try {
            resources = loader.getResources(path);
        } catch (IOException e) {
            final FacesException ex = new FacesException("Failed to read configuration resource: " + e.getMessage(), e.getCause());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
        while (resources.hasMoreElements()) {
            final URL resource = resources.nextElement();
            handleConfiguration(resource);
        }
    }

    private void searchWar() {
        final String path = "/WEB-INF/jbpm4jsf-config.xml";
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ExternalContext externalContext = facesContext.getExternalContext();
        final ServletContext servletContext = (ServletContext) externalContext.getContext();
        try {
            final URL resource = servletContext.getResource(path);
            handleConfiguration(resource);
        } catch (MalformedURLException e) {
            final FacesException ex = new FacesException("Failed to read configuration resource: " + e.getMessage(), e.getCause());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
    }

    private static final Logger log = Logger.getLogger("org.jbpm.jsf.core.config.ConfigLocator");
}
