/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.processors.encoder;

import java.io.IOException;
import java.io.Writer;
import nl.bzk.algemeenbrp.util.xml.context.Context;
import nl.bzk.algemeenbrp.util.xml.exception.ConfigurationException;
import nl.bzk.algemeenbrp.util.xml.exception.DecodeException;
import nl.bzk.algemeenbrp.util.xml.exception.EncodeException;
import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.algemeenbrp.util.xml.model.ConfigurationHelper;
import nl.bzk.algemeenbrp.util.xml.model.XmlObject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.w3c.dom.Element;

/**
 *
 */
public final class BrpActieObjectEncoder extends AbstractBrpObjectEncoder implements XmlObject<BrpActie> {

    private static final String SORTERING = "sortering";
    private static final String ACTIE_BRONNEN = "actieBronnen";

    @Override
    public void encode(final Context context, final Class<?> clazzFromParent, final String nameFromParent, final BrpActie actie, final Writer writer)
            throws XmlException {
        try {
            final Long id = actie.getId();
            if (id == null) {
                throw new NullPointerException("BrpActie.getId() is verplicht voor XML serialisatie.");
            }
            writeTag(writer, false, nameFromParent, new ImmutablePair<>("id", Long.toString(id)));
            writeDatumTijdReg(writer, actie, context);
            writeDatumOntlening(writer, actie, context);
            writeSortering(writer, actie, context);
            writePartijCode(writer, actie.getPartijCode(), context);
            writeSoortActieCode(writer, actie, context);
            writeActieBronnen(writer, actie, context);
            writeLo3Herkomst(writer, actie, context);
            writeTag(writer, true, nameFromParent);
        } catch (final IOException e) {
            throw new EncodeException(context.getElementStack(), e);
        }
    }

    private void writeDatumTijdReg(final Writer writer, final BrpActie actie, final Context context) throws XmlException {
        final XmlObject<BrpDatumTijd> betrokkenheidConfig = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(BrpDatumTijd.class);
        betrokkenheidConfig.encode(context, BrpDatumTijd.class, "datumTijdRegistratie", actie.getDatumTijdRegistratie(), writer);
    }

    private void writeDatumOntlening(final Writer writer, final BrpActie actie, final Context context) throws XmlException {
        final XmlObject<BrpDatum> betrokkenheidConfig = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(BrpDatum.class);
        betrokkenheidConfig.encode(context, BrpDatum.class, "datumOntlening", actie.getDatumOntlening(), writer);
    }

    private void writeSortering(final Writer writer, final BrpActie actie, final Context context)
            throws ConfigurationException, EncodeException, IOException {
        writer.write("<sortering>" + actie.getSortering() + "</sortering>");
    }

    private void writeSoortActieCode(final Writer writer, final BrpActie actie, final Context context) throws XmlException {
        final XmlObject<BrpSoortActieCode> betrokkenheidConfig = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(BrpSoortActieCode.class);
        betrokkenheidConfig.encode(context, BrpSoortActieCode.class, "soortActieCode", actie.getSoortActieCode(), writer);
    }

    private void writeActieBronnen(final Writer writer, final BrpActie actie, final Context context)
            throws XmlException, IOException {
        if (actie.getActieBronnen() != null) {
            writeTag(writer, false, ACTIE_BRONNEN);
            final XmlObject<BrpActieBron> betrokkenheidConfig = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(BrpActieBron.class);

            for (BrpActieBron bron : actie.getActieBronnen()) {
                betrokkenheidConfig.encode(context, BrpActieBron.class, "brpActieBron", bron, writer);
            }
            writeTag(writer, true, ACTIE_BRONNEN);
        }
    }

    private void writeLo3Herkomst(final Writer writer, final BrpActie actie, final Context context) throws XmlException {
        final XmlObject<Lo3Herkomst> betrokkenheidConfig = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(Lo3Herkomst.class);
        betrokkenheidConfig.encode(context, Lo3Herkomst.class, "lo3Herkomst", actie.getLo3Herkomst(), writer);
    }

    @Override
    public BrpActie decode(final Context context, final Element element) throws ConfigurationException, DecodeException {
        throw new UnsupportedOperationException("BrpActieBronObject.decode niet ondersteund");
    }
}
