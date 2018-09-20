/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.bericht.MarshallService;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.bijhouding.business.util.BijhoudingsplanUtil;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingssituatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.BijhoudingssituatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingNotificatieBijhoudingsplanBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.NotificeerBijhoudingsplanBericht;
import org.jibx.runtime.JiBXException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * De stap waarin het uitgaande bericht wordt gemaakt.
 */
@Component
public class MaakNotificatieBerichtStap {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    @Qualifier("bijhMarshallService")
    private MarshallService marshallService;

    /**
     * Voer de stap uit.
     * @param bericht het bijhoudingsbericht
     * @param context de bijhoudingberichtcontext
     * @return meldinglijst
     */
    public Resultaat voerStapUit(final BijhoudingsBericht bericht, final BijhoudingBerichtContext context) {
        Resultaat resultaat = Resultaat.LEEG;

        if (moetNotificatieSturen(bericht)) {
            Bijhoudingssituatie bijhoudingssituatie;
            if (BerichtUtils.isHuwelijksberichtMetHandmatigFiatterendePartners(bericht, context))
            {
                bijhoudingssituatie = Bijhoudingssituatie.OPNIEUW_INDIENEN;
            } else {
                bijhoudingssituatie = Bijhoudingssituatie.AUTOMATISCHE_FIAT;
            }
            final NotificeerBijhoudingsplanBericht notificeerBijhoudingsplanBericht = maakNotificatieBericht(context, bijhoudingssituatie);
            try {
                final String xmlBericht = marshallService.maakBericht(notificeerBijhoudingsplanBericht);
                context.setXmlBericht(xmlBericht);
            } catch (final JiBXException e) {
                LOGGER.error("Fout bij het marshallen van het bericht. ", e);
                resultaat = resultaat.voegToe(
                    ResultaatMelding.builder().withMeldingTekst("Het uitgaande notificatiebericht kon niet worden gemaakt").build());
            }
        }

        return resultaat;
    }

    /**
     * package private i.v.m. unittest.
     *
     * @param context             context
     * @param bijhoudingssituatie bijhoudingssituatie
     * @return notificeerBijhoudingsplanBericht
     */
    static NotificeerBijhoudingsplanBericht maakNotificatieBericht(final BijhoudingBerichtContext context, final Bijhoudingssituatie bijhoudingssituatie) {
        // TODO POC-Bijhouding hier een zinnig notificatiebericht maken
        final NotificeerBijhoudingsplanBericht notificeerBijhoudingsplanBericht = new NotificeerBijhoudingsplanBericht();

        notificeerBijhoudingsplanBericht.setStuurgegevens(maakStuurgegevens(context));

        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setRedenNotificatie(new BijhoudingssituatieAttribuut(bijhoudingssituatie));
        notificeerBijhoudingsplanBericht.setParameters(parameters);

        notificeerBijhoudingsplanBericht.setAdministratieveHandelingPlan(maakAdministratieveHandelingPlan(context));
        return notificeerBijhoudingsplanBericht;
    }

    private static BerichtStuurgegevensGroepBericht maakStuurgegevens(final BijhoudingBerichtContext context) {
        final BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
        final String partijCode = "28101";
        stuurgegevens.setDatumTijdVerzending(DatumTijdAttribuut.nu());
        stuurgegevens.setOntvangendePartij(context.getPartij());
        stuurgegevens.setOntvangendePartijCode(partijCode);
        stuurgegevens.setOntvangendeSysteem(SysteemNaamAttribuut.BRP);
        stuurgegevens.setZendendePartij(context.getPartij());
        stuurgegevens.setZendendePartijCode(partijCode);
        stuurgegevens.setZendendeSysteem(SysteemNaamAttribuut.BRP);
        final ReferentienummerAttribuut referentienummer = new ReferentienummerAttribuut(context.getBerichtReferentieNummer());
        stuurgegevens.setReferentienummer(referentienummer);

        return stuurgegevens;
    }

    private static HandelingNotificatieBijhoudingsplanBericht maakAdministratieveHandelingPlan(final BijhoudingBerichtContext context) {
        final HandelingNotificatieBijhoudingsplanBericht handelingNotificatieBijhoudingsplanBericht = new HandelingNotificatieBijhoudingsplanBericht();
        handelingNotificatieBijhoudingsplanBericht.setCode(SoortAdministratieveHandeling.G_B_A_SLUITING_HUWELIJK_GEREGISTREERD_PARTNERSCHAP.getCode());
        handelingNotificatieBijhoudingsplanBericht.setNaam(SoortAdministratieveHandeling.G_B_A_SLUITING_HUWELIJK_GEREGISTREERD_PARTNERSCHAP.getNaam());
        handelingNotificatieBijhoudingsplanBericht.setCategorie(SoortAdministratieveHandeling.G_B_A_SLUITING_HUWELIJK_GEREGISTREERD_PARTNERSCHAP
            .getCategorieAdministratieveHandeling().getNaam());
        handelingNotificatieBijhoudingsplanBericht.setPartijCode(context.getPartij().getWaarde().getCode().toString());

        handelingNotificatieBijhoudingsplanBericht.setBijhoudingsplan(BijhoudingsplanUtil.maakBijhoudingsplan(context));

        return handelingNotificatieBijhoudingsplanBericht;
    }


    private static boolean moetNotificatieSturen(final BijhoudingsBericht bericht) {
        return SoortBericht.ISC_MIG_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP.equals(bericht.getSoort().getWaarde());
    }

}
