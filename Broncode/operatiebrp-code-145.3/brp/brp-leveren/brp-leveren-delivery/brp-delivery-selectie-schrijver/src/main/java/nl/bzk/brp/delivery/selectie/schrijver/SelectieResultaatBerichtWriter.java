/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.schrijver;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.algemeen.bericht.BerichtException;
import nl.bzk.brp.service.algemeen.bericht.BerichtStuurgegevensWriter;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriter;
import nl.bzk.brp.delivery.selectie.schrijver.gba.SelectieResultaatGbaBerichtWriter;
import nl.bzk.brp.domain.berichtmodel.SelectieResultaatBericht;
import nl.bzk.brp.service.selectie.schrijver.SelectieResultaatVerwerkException;
import nl.bzk.brp.service.selectie.schrijver.SelectieResultaatWriterFactory;
import org.apache.commons.io.IOUtils;

/**
 * SelectieResultaatBerichtWriter.
 */
final class SelectieResultaatBerichtWriter {
    private static final Logger LOG = LoggerFactory.getLogger();
    public static final String FOUTMELDING = "Fout bij het verwerken van selectieresultaat voor selectie met id %d.";

    private OutputStream out;
    private OutputStreamWriter outputStreamWriter;
    private BerichtWriter writer;

    /**
     * @param path path
     * @throws IOException io fout
     */
    private SelectieResultaatBerichtWriter(final Path path, final SelectieResultaatBericht bericht) throws IOException {
        final OpenOption[] openOptions = {StandardOpenOption.CREATE, StandardOpenOption.APPEND};
        out = new BufferedOutputStream(Files.newOutputStream(path, openOptions));
        outputStreamWriter = new OutputStreamWriter(out, StandardCharsets.UTF_8);
        try {
            writer = new BerichtWriter(outputStreamWriter);
            doStart(bericht);
        } catch (BerichtException e) {
            // Misschien een specifieke Exception.
            throw new IOException(e);
        }

    }

    private void doStart(final SelectieResultaatBericht bericht) {
        final String rootElement = SoortBericht.LVG_SEL_VERWERK_SELECTIERESULTAAT_SET.getIdentifier();
        writer.writeStartDocument();
        writer.startElement(rootElement);
        writer.writeNamespace();
        writer.flush();
        BerichtStuurgegevensWriter.write(writer, bericht.getBasisBerichtGegevens().getStuurgegevens());
        BerichtSelectieKenmerkenWriter.write(writer, bericht.getSelectieKenmerken());
        writer.flush();

    }

    private void schrijf(String berichtInhoud) throws SelectieResultaatVerwerkException {
        try {
            outputStreamWriter.write(berichtInhoud);
        } catch (IOException e) {
            throw new SelectieResultaatVerwerkException(e);
        }
    }


    private void doClose() throws SelectieResultaatVerwerkException {
        try {
            if (writer != null) {
                writer.endElement();
                writer.flush();
            }
        } catch (BerichtException e) {
            throw new SelectieResultaatVerwerkException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (BerichtException e) {
                    LOG.warn("Fout bij afsluiten BerichtWriter: " + e);
                }
            }
            IOUtils.closeQuietly(outputStreamWriter);
            IOUtils.closeQuietly(out);
        }
    }

    static final class GbaPersoonWriter implements SelectieResultaatWriterFactory.PersoonBestandWriter {

        private final int selectieId;
        private int persoonCounter;
        private final SelectieResultaatGbaBerichtWriter writer;
        private boolean bevatPersonen;

        GbaPersoonWriter(final Path path, final SelectieResultaatBericht bericht) throws SelectieResultaatVerwerkException {
            selectieId = bericht.getSelectieKenmerken().getSelectietaakId();
            LOG.info("Nieuw GBA bestand: {}", path);
            try {
                // TODO: Bepalen mailbox obv partijen (gegevens zitten nu in VOISC, moeten blijkbaar ook verplaatst worden naar BRP)
                final String originator = "3000200";
                final String recipient = bericht.getBasisBerichtGegevens().getStuurgegevens().getOntvangendePartij().getCode() + "0";

                // Open bestand
                writer = new SelectieResultaatGbaBerichtWriter(path, originator, recipient);

            } catch (IOException e) {
                throw new SelectieResultaatVerwerkException(
                        String.format(FOUTMELDING, selectieId), e);
            }
        }

        @Override
        public void eindeBericht() throws SelectieResultaatVerwerkException {
            LOG.info("Eind GBA bestand");
            if (!bevatPersonen) {
                // Schrijf Sv11
            }

            try {
                writer.schrijfAfsluitRecord();
            } catch (IOException e) {
                throw new SelectieResultaatVerwerkException(
                        String.format(FOUTMELDING, selectieId), e);
            }
        }

        @Override
        public void voegPersoonToe(String persoon) throws SelectieResultaatVerwerkException {
            LOG.info("Schrijf GBA persoon (fragment)");

            try {
                // Schrijf Sv01
                writer.schrijfDataRecord(genereerMessageId(persoonCounter++), persoon);
            } catch (IOException e) {
                throw new SelectieResultaatVerwerkException(
                        String.format(FOUTMELDING, selectieId), e);
            }

            bevatPersonen = true;
        }

        private String genereerMessageId(int persoonCounter) {
            // TODO: Betere message id generatie
            return "SL" + new DecimalFormat("00000").format(selectieId % 100000) + new DecimalFormat("00000").format(persoonCounter);
        }
    }

    static final class BrpPersoonWriter implements SelectieResultaatWriterFactory.PersoonBestandWriter {

        private final SelectieResultaatBerichtWriter writer;

        BrpPersoonWriter(Path path, final SelectieResultaatBericht bericht) throws SelectieResultaatVerwerkException {
            try {
                writer = new SelectieResultaatBerichtWriter(path, bericht);
                writer.outputStreamWriter.write("<brp:geselecteerdePersonen>");
            } catch (IOException e) {
                throw new SelectieResultaatVerwerkException(
                        String.format(FOUTMELDING, bericht.getSelectieKenmerken().getSelectietaakId()), e);
            }
        }

        @Override
        public void eindeBericht() throws SelectieResultaatVerwerkException {
            writer.schrijf("</brp:geselecteerdePersonen>");
            writer.doClose();
        }

        @Override
        public void voegPersoonToe(final String persoon) throws SelectieResultaatVerwerkException {
            writer.schrijf(persoon);
        }
    }

    static final class GbaTotalenWriter implements SelectieResultaatWriterFactory.TotalenBestandWriter {

        GbaTotalenWriter(Path path, final SelectieResultaatBericht bericht) {
            LOG.info("Schrijf GBA totalen bestand (taak={}): {}", bericht.getSelectieKenmerken().getSelectietaakId(), path);
            // Het GBA Stelsel kent geen totalen bestand
        }

        @Override
        public void eindeBericht() throws SelectieResultaatVerwerkException {
            LOG.info("Eind GBA totalen bestand");
            // Het GBA Stelsel kent geen totalen bestand
        }

        @Override
        public void schrijfTotalen(int totaalPersonen, int aantalSelectieresultaatSets) {
            LOG.info("GBA totalen: personen={}, sets={}", totaalPersonen, aantalSelectieresultaatSets);
            // Het GBA Stelsel kent geen totalen bestand
        }
    }

    static final class BrpTotalenWriter implements SelectieResultaatWriterFactory.TotalenBestandWriter {

        private final SelectieResultaatBerichtWriter writer;

        BrpTotalenWriter(Path path, final SelectieResultaatBericht bericht) throws SelectieResultaatVerwerkException {
            try {
                writer = new SelectieResultaatBerichtWriter(path, bericht);
            } catch (IOException e) {
                throw new SelectieResultaatVerwerkException(
                        String.format(FOUTMELDING, bericht.getSelectieKenmerken().getSelectietaakId()), e);
            }
        }

        @Override
        public void schrijfTotalen(final int totaalPersonen, final int aantalSelectieresultaatSets) throws SelectieResultaatVerwerkException {
            writer.schrijf("<brp:resultaat>");
            writer.schrijf("<brp:aantalSelectieresultaatPersonen>" + totaalPersonen + "</brp:aantalSelectieresultaatPersonen>");
            writer.schrijf("<brp:aantalSelectieresultaatSets>" + aantalSelectieresultaatSets + "</brp:aantalSelectieresultaatSets>");
            writer.schrijf("</brp:resultaat>");

            try {
                writer.outputStreamWriter.flush();
            } catch (IOException e) {
                throw new SelectieResultaatVerwerkException(e);
            }
        }

        @Override
        public void eindeBericht() throws SelectieResultaatVerwerkException {
            writer.doClose();
        }
    }


}
