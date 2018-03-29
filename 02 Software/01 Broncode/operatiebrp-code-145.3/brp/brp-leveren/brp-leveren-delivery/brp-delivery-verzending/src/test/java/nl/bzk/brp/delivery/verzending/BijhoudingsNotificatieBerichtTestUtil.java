/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import java.io.IOException;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import javax.xml.transform.stream.StreamSource;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
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
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingAntwoordBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import nl.bzk.brp.bijhouding.bericht.writer.VerwerkBijhoudingsplanBerichtWriter;
import nl.bzk.brp.bijhouding.bericht.writer.WriteException;
import nl.bzk.brp.domain.internbericht.bijhoudingsnotificatie.BijhoudingsplanNotificatieBericht;
import org.springframework.core.io.ClassPathResource;

/**
 * Utility voor het parsen van bijhoudingsverzoek/resultaat + maken van bijhoudings notificatieberichten
 */
public class BijhoudingsNotificatieBerichtTestUtil {

    private final static String VERZOEK_BERICHT = "eenvoudigVoltrekkingHuwelijkNederlandBericht.xml";
    private final static String ANTWOORD_BERICHT = "voltrekkingHuwelijkNederlandAntwoordBericht.xml";
    private static final ZonedDateTime DATUM_TIJD_VERZENDING = ZonedDateTime.now();

    private static final JsonStringSerializer serialiseerderBijhoudingsNotificatieArchiveringsGegevens = new JsonStringSerializer();

    public static final String maakBijhoudingsplanNotificatieBerichtJson() throws ParseException, IOException {
        return serialiseerderBijhoudingsNotificatieArchiveringsGegevens.serialiseerNaarString(maakBijhoudingsplanNotificatieBericht());
    }

    public static final BijhoudingsplanNotificatieBericht maakBijhoudingsplanNotificatieBericht() throws ParseException, IOException {
        final VerwerkBijhoudingsplanBericht notificatieBericht = maakBijhoudingsNotificatieBericht();
        return new BijhoudingsplanNotificatieBericht().setOntvangendePartijId((short) 1)
                .setOntvangendePartijCode(notificatieBericht.getStuurgegevens().getOntvangendePartij().getWaarde())
                .setOntvangendeSysteem("Bijhoudingsysteem")
                .setZendendePartijCode("000123")
                .setZendendeSysteem(notificatieBericht.getStuurgegevens().getZendendeSysteem().getWaarde())
                .setReferentieNummer(notificatieBericht.getStuurgegevens().getReferentienummer().getWaarde())
                .setCrossReferentieNummer(null)
                .setTijdstipVerzending(notificatieBericht.getStuurgegevens().getTijdstipVerzending().toTimestamp())
                .setAdministratieveHandelingId(1L)
                .setVerwerkBijhoudingsplanBericht(maakBijhoudingsNotificatieXmlBericht());
    }

    public static final BijhoudingsplanNotificatieBericht maakBijhoudingsplanNotificatieBerichtZonderLeveringsbericht() throws ParseException,
            IOException {
        final VerwerkBijhoudingsplanBericht notificatieBericht = maakBijhoudingsNotificatieBericht();
        return new BijhoudingsplanNotificatieBericht()
                .setOntvangendePartijId((short) 1)
                .setOntvangendePartijCode(notificatieBericht.getStuurgegevens().getOntvangendePartij().getWaarde())
                .setOntvangendeSysteem("Bijhoudingsysteem")
                .setZendendePartijCode("000123")
                .setZendendeSysteem(notificatieBericht.getStuurgegevens().getZendendeSysteem().getWaarde())
                .setReferentieNummer(notificatieBericht.getStuurgegevens().getReferentienummer().getWaarde())
                .setCrossReferentieNummer(null)
                .setTijdstipVerzending(DatumUtil.vanDateTimeNaarDate(notificatieBericht.getStuurgegevens().getTijdstipVerzending().getWaarde()))
                .setAdministratieveHandelingId(1L)
                .setVerwerkBijhoudingsplanBericht(null);
    }

    public static final String maakBijhoudingsNotificatieXmlBericht() throws ParseException, IOException {
        final VerwerkBijhoudingsplanBericht verwerkBijhoudingsplanBericht = maakBijhoudingsNotificatieBericht();
        try {
            final VerwerkBijhoudingsplanBerichtWriter writer = new VerwerkBijhoudingsplanBerichtWriter();
            final StringWriter stringWriter = new StringWriter();
            writer.write(verwerkBijhoudingsplanBericht, stringWriter);
            return writer.toString();
        } catch (WriteException e) {
            throw new IllegalStateException("De notificatie kan niet worden omgezet in XML.", e);
        }
    }

    public static final VerwerkBijhoudingsplanBericht maakBijhoudingsNotificatieBericht() throws ParseException, IOException {
        final StuurgegevensElement stuurgegevens =
                StuurgegevensElement.getInstance("999901", "BRP", "059901", null, DATUM_TIJD_VERZENDING);
        final BijhoudingVerzoekBericht bijhoudingVerzoekBericht = leesVerzoekBericht();
        final AfgeleidAdministratiefElement afgeleidAdministratief = AfgeleidAdministratiefElement.getInstance(ZonedDateTime.now());
        final IdentificatienummersElement identificatienummers = IdentificatienummersElement.getInstance("123456789", null);
        final BijhoudingElement bijhouding = BijhoudingElement.getInstance("059901");

        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        params.afgeleidAdministratief(afgeleidAdministratief);
        params.identificatienummers(identificatienummers);
        params.bijhouding(bijhouding);

        final Map<String, String> att = new AbstractBmrGroep.AttributenBuilder().objecttype("Persoon").build();
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement(att, params);

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
        return bericht;
    }

    private static final BijhoudingVerzoekBericht leesVerzoekBericht() throws ParseException, IOException {
        return new BijhoudingVerzoekBerichtParser().parse(new StreamSource(new ClassPathResource(VERZOEK_BERICHT).getInputStream()));
    }

    private static final BijhoudingAntwoordBericht leesAntwoordBericht() throws ParseException, IOException {
        return new BijhoudingAntwoordBerichtParser().parse(new StreamSource(new ClassPathResource(ANTWOORD_BERICHT).getInputStream()));
    }
}
