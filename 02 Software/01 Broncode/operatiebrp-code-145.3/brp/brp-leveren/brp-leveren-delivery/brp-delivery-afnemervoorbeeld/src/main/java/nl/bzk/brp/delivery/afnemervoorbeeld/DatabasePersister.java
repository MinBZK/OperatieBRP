/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemervoorbeeld;

import java.io.StringReader;
import java.sql.Timestamp;
import javax.annotation.PostConstruct;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.xml.SimpleNamespaceContext;
import org.xml.sax.InputSource;

/**
 * Databasepersister voor ontvangen berichten aan de afnemer.
 */
@Component
final class DatabasePersister implements Persister {
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private final XPath xPath = XPathFactory.newInstance().newXPath();

    @Value("${brp.afnemervoorbeeld.ispersistent:false}")
    private boolean isDatabasePersistent;

    private JdbcTemplate jdbcTemplate;

    /**
     * Zet xpath namespace ctx & initialiseer database met afnemervoorbeeld schema.
     */
    @PostConstruct
    //heeft shutdown hook
    //
    public void naConstructie() {
        final SimpleNamespaceContext nsContext = new SimpleNamespaceContext();
        nsContext.bindNamespaceUri("brp", "http://www.bzk.nl/brp/brp0200");
        xPath.setNamespaceContext(nsContext);

        LOGGER.warn("Berichten opslaan in database: {}", isDatabasePersistent);

        if (isDatabasePersistent) {
            final ClassPathXmlApplicationContext cp = new ClassPathXmlApplicationContext("afnemervoorbeeld-databasepersist-context.xml");
            cp.registerShutdownHook();
            jdbcTemplate = (JdbcTemplate) cp.getBean("jdbcTemplate");
        }
    }

    @Override
    public void persistPersoonBericht(final String bericht) {
        if (!isDatabasePersistent) {
            LOGGER.warn("Bericht wordt niet gepersisteerd in de database");
            return;
        }
        LOGGER.info("Ontvangen persoonbericht");
        Timestamp tsontv = new Timestamp(System.currentTimeMillis());
        String referentienummer = evalAlsString(bericht, "//brp:stuurgegevens/brp:referentienummer");
        jdbcTemplate.update(
                "INSERT INTO afnemer.leverbericht (bericht, tsontv, referentienummer) VALUES (?, ?, ?)",
                bericht, tsontv, referentienummer);
        LOGGER.info("Leveringbericht naar database geschreven");
    }

    @Override
    public void persistVrijBericht(final String bericht) {
        if (!isDatabasePersistent) {
            LOGGER.warn("Vrij bericht wordt niet gepersisteerd in de database");
            return;
        }
        LOGGER.info("Ontvangen vrij bericht");
        Timestamp tsontv = new Timestamp(System.currentTimeMillis());
        jdbcTemplate.update(
                "INSERT INTO afnemer.vrijbericht (bericht, tsontv) VALUES (?, ?)",
                bericht, tsontv);
        LOGGER.info("Vrij bericht naar database geschreven");
    }

    @Override
    public void persistNotificatieBericht(final String bericht) {
        if (!isDatabasePersistent) {
            LOGGER.warn("Notificatie wordt niet gepersisteerd in de database");
            return;
        }
        LOGGER.info("Ontvangen notificatie");
        Timestamp tsontv = new Timestamp(System.currentTimeMillis());
        jdbcTemplate.update(
                "INSERT INTO afnemer.notificatie (bericht, tsontv) VALUES (?, ?)",
                bericht, tsontv);
        LOGGER.info("Notificatie naar database geschreven");
    }

    private String evalAlsString(final String xml, final String xpathExpressie) {
        final XPathExpression xPathExpression = geefXPathExpressie(xpathExpressie);
        try {
            return (String) xPathExpression.evaluate(new InputSource(new StringReader(xml)), XPathConstants.STRING);

        } catch (XPathExpressionException e) {
            throw new IllegalStateException("Evaluatie voor String mislukt", e);
        }
    }

    private XPathExpression geefXPathExpressie(final String xpathExpressie) {
        try {
            return xPath.compile(xpathExpressie);
        } catch (XPathExpressionException e) {
            throw new IllegalStateException("xPath Expressie niet geldig: " + xpathExpressie, e);
        }
    }

}
