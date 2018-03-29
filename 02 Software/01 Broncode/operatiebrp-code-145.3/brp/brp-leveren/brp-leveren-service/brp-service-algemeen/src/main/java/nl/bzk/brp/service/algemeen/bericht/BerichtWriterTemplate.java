/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.bericht;

import java.io.StringWriter;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import nl.bzk.brp.domain.berichtmodel.Bericht;
import nl.bzk.brp.domain.berichtmodel.BerichtParameters;

/**
 * Basis voor BRP bericht writing.
 */
public final class BerichtWriterTemplate {

    public static final BiConsumer<Bericht, BerichtWriter> DEFAULT_RESULTAAT_CONSUMER = (bericht, writer) -> {
        writer.startElement("resultaat");
        writer.element("verwerking", bericht.getBasisBerichtGegevens().getResultaat().getVerwerking());
        writer.element("hoogsteMeldingsniveau", bericht.getBasisBerichtGegevens().getResultaat().getHoogsteMeldingsniveau());
        writer.endElement();
    };

    private final String rootElement;
    private Consumer<BerichtWriter> resultaatConsumer;
    private Consumer<BerichtWriter> dienstSpecifiekWriter;

    /**
     * @param rootElement het root element voor deze writer.
     */
    public BerichtWriterTemplate(final String rootElement) {
        this.rootElement = rootElement;
    }

    /**
     * Zet de consumer voor wegschrijven resultaat element.
     * @param resultaatConsumer een {@link Consumer}
     * @return deze template
     */
    public BerichtWriterTemplate metResultaat(final Consumer<BerichtWriter> resultaatConsumer) {
        this.resultaatConsumer = resultaatConsumer;
        return this;
    }

    /**
     * Zet de consumer voor wegschrijven van het dienstspecifieke deel.
     * @param dienstSpecifiekWriter een {@link Consumer}
     * @return deze template
     */
    public BerichtWriterTemplate metInvullingDienstSpecifiekDeel(final Consumer<BerichtWriter> dienstSpecifiekWriter) {
        this.dienstSpecifiekWriter = dienstSpecifiekWriter;
        return this;
    }

    /**
     * Maakt een xml bericht van het bericht object.
     * @param bericht een {@link Bericht}
     * @return een xml String
     */
    public String toXML(final Bericht bericht) {
        final StringWriter outputWriter = new StringWriter();
        final BerichtWriter writer = new BerichtWriter(outputWriter);

        try {
            writer.writeStartDocument();
            writer.startElement(rootElement);
            writer.writeNamespace();

            BerichtStuurgegevensWriter.write(writer, bericht.getBasisBerichtGegevens().getStuurgegevens());
            if (bericht.getBasisBerichtGegevens().getParameters() != null) {
                write(writer, bericht.getBasisBerichtGegevens().getParameters());
            }
            if (resultaatConsumer != null) {
                resultaatConsumer.accept(writer);
            }
            BerichtMeldingWriter.write(bericht.getBasisBerichtGegevens().getMeldingen(), writer);
            if (dienstSpecifiekWriter != null) {
                dienstSpecifiekWriter.accept(writer);
            }
            writer.endElement();
            writer.flush();
        } finally {
            writer.close();
        }
        return outputWriter.toString();
    }

    static void write(BerichtWriter writer, final BerichtParameters berichtParameters) {
        writer.startElement("parameters");
        writer.element("soortSynchronisatie", berichtParameters.getSoortSynchronisatie().getNaam());
        writer.element("leveringsautorisatieIdentificatie", String.valueOf(
                berichtParameters.getDienst().getDienstbundel().getLeveringsautorisatie().getId()));
        writer.element("dienstIdentificatie", String.valueOf(berichtParameters.getDienst().getId()));
        writer.endElement();
    }
}
