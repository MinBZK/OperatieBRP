/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen;

import javax.inject.Inject;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.levering.business.toegang.populatie.LeveringinformatieService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.bericht.autaut.PersoonAfnemerindicatieBericht;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtValidatieStap;
import org.springframework.dao.EmptyResultDataAccessException;


/**
 * De stap waarin extra gegevens opgehaald worden.
 */
public class HaalLeveringsautorisatieEnPartijGegevensOpStap
    extends AbstractBerichtValidatieStap<BerichtBericht, RegistreerAfnemerindicatieBericht,
    OnderhoudAfnemerindicatiesBerichtContext, OnderhoudAfnemerindicatiesResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private LeveringinformatieService leveringinformatieService;

    @Inject
    private PartijService partijService;

    @Override
    public final boolean voerStapUit(final RegistreerAfnemerindicatieBericht onderwerp,
        final OnderhoudAfnemerindicatiesBerichtContext context,
        final OnderhoudAfnemerindicatiesResultaat resultaat)
    {
        Boolean stapResultaat = DOORGAAN;
        final ActieBericht hoofdActie = onderwerp.getAdministratieveHandeling().getHoofdActie();

        if (hoofdActie == null) {
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001,
                "Onderhoud van afnemerindicaties moet 1 actie bevatten."));
            return STOPPEN;
        }
        final BerichtRootObject berichtRootObject = hoofdActie.getRootObject();

        if (!(berichtRootObject instanceof PersoonBericht)) {
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001, "Bericht heeft foutieve inhoud."));
            return STOPPEN;
        }
        final PersoonBericht persoonInBericht = (PersoonBericht) berichtRootObject;

        if (null == persoonInBericht.getAfnemerindicaties()
            || persoonInBericht.getAfnemerindicaties().size() != 1)
        {
            //FIXME deze check zit ook in OnderhoudAfnemerindicatiesStap
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001,
                "Onderhoud van afnemerindicaties moet 1 afnemerindicatie onderhouden."));
            return STOPPEN;
        }

        final PersoonAfnemerindicatieBericht afnemerindicatieInBericht = persoonInBericht.getAfnemerindicaties().get(0);
        final Partij zendendePartij = partijService.vindPartijOpCode(Integer.parseInt(onderwerp.getStuurgegevens().getZendendePartijCode()));
        context.setZendendePartij(zendendePartij);
        onderwerp.getStuurgegevens().setZendendePartij(new PartijAttribuut(zendendePartij));
        afnemerindicatieInBericht.setAfnemer(new PartijAttribuut(zendendePartij));

        try {
            // ParseInt van de afnemerpartijcode hoeft niet afgevangen te worden, aangezien deze check al eerder wordt
            // gedaan bij het bepalen van de afnemer.
            final Integer leveringautorisatieId = Integer.parseInt(onderwerp.getParameters().getLeveringsautorisatieID());
            final SoortAdministratieveHandeling soortAdministratieveHandeling = onderwerp.getAdministratieveHandeling().getSoort().getWaarde();
            final SoortDienst soortDienst = bepaalSoortDienst(soortAdministratieveHandeling);
            final Leveringinformatie leveringinformatie =
                leveringinformatieService.geefLeveringinformatie(leveringautorisatieId, Integer.parseInt(onderwerp.getStuurgegevens()
                        .getZendendePartijCode()),
                    soortDienst);
            context.setLeveringinformatie(leveringinformatie);
            // TBV berichtarchivering wordt het gehele leveringsautorisatie aan het bericht meegegeven
            onderwerp.setParameters(new BerichtParametersGroepBericht());
            onderwerp.getParameters()
                .setLeveringsautorisatie(new LeveringsautorisatieAttribuut(leveringinformatie.getToegangLeveringsautorisatie().getLeveringsautorisatie()));
        } catch (final OnbekendeReferentieExceptie e) {
            if (OnbekendeReferentieExceptie.ReferentieVeld.LEVERINGSAUTORISATIEID.getNaam().equals(e.getReferentieVeldNaam())) {
                // Als het leveringsautorisatie niet gevonden kan worden, heeft doorgaan geen nut.
                resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRLV0007));
            } else if (OnbekendeReferentieExceptie.ReferentieVeld.PARTIJCODE.getNaam().equals(e.getReferentieVeldNaam())) {
                resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRAL0220));
            } else {
                LOGGER.warn("Er is een OnbekendeReferentieExceptie gegooid die niet ondersteund wordt voor veldnaam: "
                    + "{} en waarde: {}.", e.getReferentieVeldNaam(), e.getReferentieWaarde());
            }
            stapResultaat = STOPPEN;
        }

        LOGGER.info("Onderhoud afnemerindicatie actie: {}",
            onderwerp.getAdministratieveHandeling().getHoofdActie().getSoort().getWaarde().getNaam());

        LOGGER.debug("HaalLeveringsautorisatieEnPartijGegevensOpStap.voerStapUit");
        return stapResultaat;
    }

    private SoortDienst bepaalSoortDienst(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        SoortDienst soortDienst;
        switch (soortAdministratieveHandeling) {
            case PLAATSING_AFNEMERINDICATIE:
                soortDienst = SoortDienst.PLAATSEN_AFNEMERINDICATIE;
                break;
            case VERWIJDERING_AFNEMERINDICATIE:
                soortDienst = SoortDienst.PLAATSEN_AFNEMERINDICATIE;
                break;
            default:
                throw new IllegalStateException("geen geldige handeling: " + soortAdministratieveHandeling);
        }
        return soortDienst;
    }
}
