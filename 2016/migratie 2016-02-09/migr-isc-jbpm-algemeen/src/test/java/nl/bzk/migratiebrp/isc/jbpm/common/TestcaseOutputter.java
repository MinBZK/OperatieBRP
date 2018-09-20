/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.brp.BrpBericht;
import nl.bzk.migratiebrp.bericht.model.isc.IscBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Vb01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRegisterType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRegisterVoorkomenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GemeenteRegisterType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GemeenteType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesAutorisatieRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesGemeenteRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesAutorisatieRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesGemeenteRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Autorisatie;
import nl.bzk.migratiebrp.bericht.model.sync.register.AutorisatieRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.Gemeente;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegister;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.FileUtils;

/**
 * Helper om van een unittest gebaseerd op een AbstractJbpmTest een migr-test-isc test te maken.
 */
public class TestcaseOutputter {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final DecimalFormat NR_FORMAT = new DecimalFormat("000");

    private final Map<String, Integer> correlaties = new HashMap<>();

    private String baseDirectory;
    private File testDirectory;
    private boolean output;
    private int counter;

    public void enableOutput(final String outputDirectory) {
        baseDirectory = outputDirectory;
        output = true;
    }

    public void disableOutput() {
        output = false;
    }

    /**
     * Geef de outputting.
     *
     * @return outputting
     */
    public boolean isOutputting() {
        return output;
    }

    public void startTestcase(final String testName) {
        testDirectory = new File(baseDirectory, testName);

        if (output && testDirectory.exists()) {
            try {
                FileUtils.deleteDirectory(testDirectory);
            } catch (final IOException e) {
                LOG.error("Kon output directory niet opschonen", e);
            }
        }

        counter = 10;
        correlaties.clear();
    }

    private void output(final String naam, final String inhoud) {
        if (!output) {
            return;
        }
        testDirectory.mkdirs();
        final File outputFile = new File(testDirectory, naam);
        LOG.info("Outputting: {}", outputFile.getPath());

        try (final Writer writer = new FileWriter(outputFile)) {
            writer.append(inhoud);
        } catch (final IOException e) {
            LOG.error("Probleem met schrijven output bestand", e);
        }
    }

    private String bepaalExtension(final String kanaal) {
        if ("vospg".equals(kanaal)) {
            return "txt";
        }

        return "xml";
    }

    private String bepaalKanaal(final Bericht bericht) {
        final String kanaal;
        if (bericht instanceof BrpBericht) {
            kanaal = "brp";
        } else if (bericht instanceof Lo3Bericht) {
            kanaal = "vospg";
        } else if (bericht instanceof SyncBericht) {
            kanaal = "sync";
        } else if (bericht instanceof IscBericht) {
            kanaal = "isc";
        } else {
            throw new AssertionError("Onbekend bericht type.");
        }
        return kanaal;
    }

    private void outputBericht(final String kanaal, final String inUit, final Bericht bericht) {
        final int volgnummer = ++counter;
        if (bericht.getMessageId() != null) {
            LOG.info("OUTPUTTER: Put message id {} op volgnumer {}", bericht.getMessageId(), volgnummer);
            correlaties.put(bericht.getMessageId(), volgnummer);
        }

        final Integer correlatie;
        if (bericht.getCorrelationId() == null) {
            correlatie = null;
        } else {
            correlatie = correlaties.get(bericht.getCorrelationId());
            LOG.info("OUTPUTTER: Get volgnummer {} obv correlatie id {}", correlatie, bericht.getCorrelationId());
        }

        final String bestandsNaam = bepaalBestandsnaam(volgnummer, inUit, correlatie, kanaal, bepaalExtension(kanaal), bericht.getBerichtType());

        if ("vospg".equals(kanaal)) {
            outputPropertiesVoorLo3(inUit, bericht, bestandsNaam);
        }

        try {
            output(bestandsNaam, formatBericht(bericht));
        } catch (final BerichtInhoudException e) {
            LOG.error("Probleem met schrijven output bestand", e);
        }
    }

    private void outputPropertiesVoorLo3(final String inUit, final Bericht bericht, final String bestandsNaam) {
        if (!output) {
            return;
        }

        final Lo3Bericht lo3Bericht = (Lo3Bericht) bericht;
        final String verzendendePartij = lo3Bericht.getBronGemeente() == null ? "" : lo3Bericht.getBronGemeente();
        final String ontvangendePartij = lo3Bericht.getDoelGemeente() == null ? "" : lo3Bericht.getDoelGemeente();
        // Output .properties bestand met bron en doelgemeente
        final StringBuilder properties = new StringBuilder();
        properties.append("in".equals(inUit) ? "ontvangendePartij=" : "verzendendePartij=")
                  .append("in".equals(inUit) ? ontvangendePartij : verzendendePartij);
        properties.append("\n");
        properties.append("uit".equals(inUit) ? "ontvangendePartij=" : "verzendendePartij=")
                  .append("uit".equals(inUit) ? ontvangendePartij : verzendendePartij);
        output(bestandsNaam + ".properties", properties.toString());
    }

    public void outputSignalBericht(final Bericht bericht) {
        outputBericht(bepaalKanaal(bericht), "uit", bericht);
    }

    public void outputGetBericht(final Bericht bericht) {
        outputBericht(bepaalKanaal(bericht), "in", bericht);
    }

    public void outputHumanTask(final String transition) {
        output(bepaalBestandsnaam(++counter, "uit", null, "hand", "txt", transition), transition);
    }

    public void outputTransition(final String transition) {
        output(bepaalBestandsnaam(++counter, "uit", null, "trans", "txt", transition), transition);
    }

    /**
     * &lt;nr>-&lt;in/uit>-&lt;kanaal>.ext
     *
     * @param kanaal
     * @param omschrijving
     * @return
     */
    private String bepaalBestandsnaam(
        final int volgnummer,
        final String inUit,
        final Integer correlatie,
        final String kanaal,
        final String extension,
        final String omschrijving)
    {
        final StringBuilder result = new StringBuilder();

        result.append(NR_FORMAT.format(volgnummer)).append("0-").append(inUit);
        if (correlatie != null) {
            result.append(NR_FORMAT.format(correlatie)).append("0");
        }

        result.append("-").append(kanaal);

        if (omschrijving != null && !"".equals(omschrijving)) {
            result.append("-").append(omschrijving);
        }

        result.append(".").append(extension);

        return result.toString();
    }

    private String formatBericht(final Bericht bericht) throws BerichtInhoudException {
        String result = bericht.format();

        if (bericht instanceof Vb01Bericht) {
            // Speciale nabewerking voor Vb01 omdat daar een eref referentie in zit.
            for (final String messageId : correlaties.keySet()) {
                result = result.replace(messageId, "{eref}");
            }
        }

        return result;
    }

    public void outputAutorisatieRegister(final AutorisatieRegister autorisatieRegister) {
        if (!output) {
            return;
        }

        output("0020-uit-jmx_isc-autorisatieregister cache legen.txt", "nl.bzk.migratiebrp.isc:name=AUTORISATIE\nclearRegister");
        output("0021-uit-jmx_isc-autorisatieregister cache refresh.txt", "nl.bzk.migratiebrp.isc:name=AUTORISATIE\nrefreshRegister");
        output(
            "0022-in-afnemer-LeesAutorisatieRegisterVerzoek.xml",
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><leesAutorisatieRegisterVerzoek xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\"/>");

        final LeesAutorisatieRegisterAntwoordType jaxb = new LeesAutorisatieRegisterAntwoordType();
        jaxb.setStatus(StatusType.OK);
        jaxb.setAutorisatieRegister(new AutorisatieRegisterType());
        for (final Autorisatie autorisatie : autorisatieRegister.geefAlleAutorisaties()) {
            final AutorisatieRegisterVoorkomenType autorisatieType = new AutorisatieRegisterVoorkomenType();
            autorisatieType.setPartijCode(autorisatie.getPartijCode());
            autorisatieType.setToegangLeveringsautorisatieId(autorisatie.getToegangLeveringsautorisatieId());
            autorisatieType.setPlaatsenAfnemersindicatiesDienstId(autorisatie.getPlaatsenAfnemersindicatieDienstId());
            autorisatieType.setVerwijderenAfnemersindicatiesDienstId(autorisatie.getVerwijderenAfnemersindicatieDienstId());
            autorisatieType.setBevragenPersoonDienstId(autorisatie.getBevragenPersoonDienstId());
            autorisatieType.setBevragenAdresDienstId(autorisatie.getBevragenAdresDienstId());
            jaxb.getAutorisatieRegister().getAutorisatie().add(autorisatieType);
        }

        output("0023-uit-afnemer-LeesAutorisatieRegisterAntwoord.xml", new LeesAutorisatieRegisterAntwoordBericht(jaxb).format());
    }

    public void outputGemeenteRegister(final GemeenteRegister gemeenteRegister) {
        if (!output) {
            return;
        }

        output("0010-uit-jmx_isc-gemeenteregister cache legen.txt", "nl.bzk.migratiebrp.isc:name=GEMEENTE\nclearRegister");
        output("0011-uit-jmx_isc-gemeenteregister cache refresh.txt", "nl.bzk.migratiebrp.isc:name=GEMEENTE\nrefreshRegister");
        output(
            "0012-in-gemeente-LeesGemeenteRegisterVerzoek.xml",
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><leesGemeenteRegisterVerzoek xmlns=\"http://www.bzk.nl/migratiebrp/SYNC/0001\"/>");

        final LeesGemeenteRegisterAntwoordType jaxb = new LeesGemeenteRegisterAntwoordType();
        jaxb.setStatus(StatusType.OK);
        jaxb.setGemeenteRegister(new GemeenteRegisterType());
        for (final Gemeente gemeente : gemeenteRegister.geefAlleGemeenten()) {
            final GemeenteType gemeenteType = new GemeenteType();
            gemeenteType.setGemeenteCode(gemeente.getGemeenteCode());
            gemeenteType.setPartijCode(gemeente.getPartijCode());
            gemeenteType.setDatumBrp(converteerDateNaarBigInteger(gemeente.getDatumOvergangNaarBrp()));

            jaxb.getGemeenteRegister().getGemeente().add(gemeenteType);
        }

        output("0013-uit-gemeente-LeesGemeenteRegisterAntwoord.xml", new LeesGemeenteRegisterAntwoordBericht(jaxb).format());
    }

    private BigInteger converteerDateNaarBigInteger(final Date date) {
        if (date == null) {
            return null;
        }

        return BigInteger.valueOf(Long.parseLong(new SimpleDateFormat("yyyyMMdd").format(date)));
    }
}
