/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.model.AbstractBmrGroep;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingPlanElement;
import nl.bzk.brp.bijhouding.bericht.model.AfgeleidAdministratiefElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingsplanPersoonElement;
import nl.bzk.brp.bijhouding.bericht.model.ElementBuilder;
import nl.bzk.brp.bijhouding.bericht.model.IdentificatienummersElement;
import nl.bzk.brp.bijhouding.bericht.model.NotificatieBijhoudingsplanElement;
import nl.bzk.brp.bijhouding.bericht.model.PersoonGegevensElement;
import nl.bzk.brp.bijhouding.bericht.model.StuurgegevensElement;
import nl.bzk.brp.bijhouding.bericht.model.VerwerkBijhoudingsplanBericht;
import nl.bzk.brp.bijhouding.bericht.model.VerwerkBijhoudingsplanBerichtImpl;
import nl.bzk.brp.bijhouding.bericht.writer.VerwerkBijhoudingsplanBerichtWriter;
import nl.bzk.brp.bijhouding.bericht.writer.WriteException;
import org.junit.Test;

/**
 * Testen voor {@link VerwerkBijhoudingsplanBericht}.
 */
public class VerwerkBijhoudingsplanBerichtTest extends AbstractParserTest {

    private final static String VERZOEK_BERICHT = "eenvoudigVoltrekkingHuwelijkNederlandBericht.xml";
    private final static String ANTWOORD_BERICHT = "voltrekkingHuwelijkNederlandAntwoordBericht.xml";

    @Test
    public void testValidateOutput() throws ParseException, WriteException {
        final StuurgegevensElement stuurgegevens =
                StuurgegevensElement.getInstance("999901", "BRP", "059901", null, DatumUtil.nuAlsZonedDateTime());
        final BijhoudingVerzoekBericht bijhoudingVerzoekBericht = leesVerzoekBericht();
        final AfgeleidAdministratiefElement afgeleidAdministratief = AfgeleidAdministratiefElement.getInstance(DatumUtil.nuAlsZonedDateTime());
        final IdentificatienummersElement identificatienummers = IdentificatienummersElement.getInstance("123456789", null);
        final BijhoudingElement bijhouding = BijhoudingElement.getInstance("059901");
        ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        params.afgeleidAdministratief(afgeleidAdministratief);
        params.identificatienummers(identificatienummers);
        params.bijhouding(bijhouding);
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement(null,null, null, params);

        final BijhoudingsplanPersoonElement bijgehoudenPersoon =
                BijhoudingsplanPersoonElement.getInstance(persoon, BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ.getNaam());
        final NotificatieBijhoudingsplanElement bijhoudingsplan =
                NotificatieBijhoudingsplanElement.getInstance(
                        "059901",
                        null,
                        bijhoudingVerzoekBericht,
                        leesAntwoordBericht(),
                        Collections.singletonList(bijgehoudenPersoon));
        final AdministratieveHandelingPlanElement administratieveHandelingPlan =
                AdministratieveHandelingPlanElement.getInstance(SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, "059901", bijhoudingsplan);
        final VerwerkBijhoudingsplanBericht bericht =
                new VerwerkBijhoudingsplanBerichtImpl(new AbstractBmrGroep.AttributenBuilder().build(), stuurgegevens, administratieveHandelingPlan);
        final VerwerkBijhoudingsplanBerichtWriter writer = new VerwerkBijhoudingsplanBerichtWriter();
        final StringWriter stringWriter = new StringWriter();
        writer.write(bericht, stringWriter);
        valideerTegenSchema(new ByteArrayInputStream(stringWriter.toString().getBytes()));
    }

    private BijhoudingVerzoekBericht leesVerzoekBericht() throws ParseException {
        return new BijhoudingVerzoekBerichtParser().parse(BijhoudingVerzoekBerichtParser.class.getResourceAsStream(VERZOEK_BERICHT));
    }

    private BijhoudingAntwoordBericht leesAntwoordBericht() throws ParseException {
        return new BijhoudingAntwoordBerichtParser().parse(BijhoudingAntwoordBerichtParser.class.getResourceAsStream(ANTWOORD_BERICHT));
    }
}
