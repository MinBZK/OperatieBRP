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
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.w3c.dom.Element;

/**
 *
 */
public final class BrpRelatieObjectEncoder extends AbstractBrpObjectEncoder implements XmlObject<BrpRelatie> {

    public static final String BRP_BETROKKENHEID = "brpBetrokkenheid";
    private static final String IK_BETROKKENHEID = "ikBetrokkenheid";
    private static final String BETROKKENHEDEN = "betrokkenheden";
    private static final String CLASS = "class";
    public static final String BRP_STAPEL = "brpStapel";

    @Override
    public void encode(final Context context, final Class<?> clazzFromParent, final String nameFromParent, final BrpRelatie value, final Writer writer)
            throws XmlException {
        try {
            final BrpSoortRelatieCode soortRelatieCode = value.getSoortRelatieCode();
            final BrpSoortBetrokkenheidCode rolCode = value.getRolCode();
            writeTag(
                    writer,
                    false,
                    nameFromParent,
                    new ImmutablePair<>("soort", soortRelatieCode.getWaarde()),
                    new ImmutablePair<>("rol", rolCode.getWaarde()));
            writeBetrokkenHeden(writer, value, context);
            writeIkBetrokkenheid(writer, value, context);
            writeRol(writer, value, context);
            writeRelatieStapel(writer, value, context);
            writeSoortRelatie(writer, value, context);
            writeTag(writer, true, nameFromParent);

        } catch (final IOException e) {
            throw new EncodeException(context.getElementStack(), e);
        }
    }

    private void writeSoortRelatie(final Writer writer, final BrpRelatie relatie, final Context context) throws XmlException {
        final XmlObject<BrpSoortRelatieCode> rolConfig = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(BrpSoortRelatieCode.class);
        rolConfig.encode(context, BrpSoortRelatieCode.class, "soortRelatieCode", relatie.getSoortRelatieCode(), writer);
    }

    private void writeRelatieStapel(final Writer writer, final BrpRelatie relatie, final Context context)
            throws XmlException, IOException {
        final BrpStapel<BrpRelatieInhoud> relatieStapel = relatie.getRelatieStapel();

        writeTag(writer, false, "relatieStapel");

        final XmlObject<BrpGroep> rolConfig = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(BrpGroep.class);
        for (BrpGroep<BrpRelatieInhoud> groep : relatieStapel.getGroepen()) {
            writeTag(writer, false, BRP_STAPEL);
            rolConfig.encode(context, BrpGroep.class, "brpGroep", groep, writer);
            writeTag(writer, true, BRP_STAPEL);
        }
        writeTag(writer, true, "relatieStapel");
    }

    private void writeRol(final Writer writer, final BrpRelatie relatie, final Context context) throws XmlException {
        final XmlObject<BrpSoortBetrokkenheidCode> rolConfig =
                ConfigurationHelper.getConfiguration(context).getXmlObjectFor(BrpSoortBetrokkenheidCode.class);
        rolConfig.encode(context, BrpSoortBetrokkenheidCode.class, "rolCode", relatie.getRolCode(), writer);
    }

    private void writeIkBetrokkenheid(final Writer writer, final BrpRelatie relatie, final Context context)
            throws IOException, XmlException {
        writeTag(writer, false, IK_BETROKKENHEID);
        final XmlObject<BrpBetrokkenheid> betrokkenheidConfig = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(BrpBetrokkenheid.class);
        betrokkenheidConfig.encode(context, BrpBetrokkenheid.class, BRP_BETROKKENHEID, relatie.getIkBetrokkenheid(), writer);
        writeTag(writer, true, IK_BETROKKENHEID);

    }

    private void writeBetrokkenHeden(final Writer writer, final BrpRelatie relatie, final Context context)
            throws IOException, XmlException {
        writeTag(writer, false, BETROKKENHEDEN);
        for (final BrpBetrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
            schrijfBetrokkenheid(writer, betrokkenheid, context);
        }
        writeTag(writer, true, BETROKKENHEDEN);
    }

    private void schrijfBetrokkenheid(final Writer writer, final BrpBetrokkenheid betrokkenheid, final Context context)
            throws XmlException {
        final XmlObject<BrpBetrokkenheid> betrokkenheidConfig = ConfigurationHelper.getConfiguration(context).getXmlObjectFor(BrpBetrokkenheid.class);
        betrokkenheidConfig.encode(context, BrpBetrokkenheid.class, BRP_BETROKKENHEID, betrokkenheid, writer);
    }

    @Override
    public BrpRelatie decode(final Context context, final Element element) throws ConfigurationException, DecodeException {
        throw new UnsupportedOperationException("BrpActieBronObject.decode niet ondersteund");
    }
}
