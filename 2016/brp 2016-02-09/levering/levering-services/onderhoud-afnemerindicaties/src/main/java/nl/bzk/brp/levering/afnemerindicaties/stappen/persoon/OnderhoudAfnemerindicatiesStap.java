/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.levering.afnemerindicaties.model.BewerkAfnemerindicatieResultaat;
import nl.bzk.brp.levering.afnemerindicaties.service.AfnemerindicatiesMetRegelsService;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.levering.business.toegang.leveringsautorisatie.ToegangLeveringsautorisatieService;
import nl.bzk.brp.locking.BrpLockerExceptie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.bericht.autaut.PersoonAfnemerindicatieBericht;
import nl.bzk.brp.model.bericht.autaut.PersoonAfnemerindicatieStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.internbericht.RegelMelding;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;
import org.springframework.util.Assert;


/**
 * Deze stap onderhoud daadwerkelijk de afnemerindicaties.
 *
 * @brp.bedrijfsregel R1987
 */
@Regels(Regel.R1987)
public class OnderhoudAfnemerindicatiesStap extends AbstractBerichtVerwerkingStap<RegistreerAfnemerindicatieBericht,
    OnderhoudAfnemerindicatiesBerichtContext, OnderhoudAfnemerindicatiesResultaat>
{
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private AfnemerindicatiesMetRegelsService afnemerindicatiesService;

    @Inject
    private ToegangLeveringsautorisatieService toegangLeveringsautorisatieService;

    @Override
    public final boolean voerStapUit(final RegistreerAfnemerindicatieBericht bericht,
        final OnderhoudAfnemerindicatiesBerichtContext berichtContext,
        final OnderhoudAfnemerindicatiesResultaat resultaat)
    {
        Boolean returnWaarde = DOORGAAN;
        final boolean berichtIsValide = valideerBericht(bericht, resultaat);
        if (!berichtIsValide) {
            return STOPPEN;
        }

        final SoortAdministratieveHandeling huidigeAdministratieveHandeling =
            bericht.getAdministratieveHandeling().getSoort().getWaarde();
        try {
            final BewerkAfnemerindicatieResultaat serviceResultaat =
                verwerkAfnemerindicatieBericht(huidigeAdministratieveHandeling,
                    bericht.getAdministratieveHandeling().getHoofdActie(),
                    berichtContext);

            voegMeldingenToeAanResultaat(resultaat, serviceResultaat);
        } catch (final BrpLockerExceptie ble) {
            // TODO TEAMBRP-2902 Specifieke regel gebruiken zodra deze beschikbaar is
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001,
                "Onderhoud van afnemerindicaties kon de persoon niet vergrendelen."));
            returnWaarde = STOPPEN;
        } catch (final OnbekendeReferentieExceptie e) {
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.BRLV0007));
        }
        return returnWaarde;
    }

    /**
     * Voegt indien nodig een melding toe aan het resultaat.
     *
     * @param resultaat        resultaat van deze stap(pen verwerker)
     * @param serviceResultaat resultaat uit de (interne) afnemerindicatieservice
     */
    private void voegMeldingenToeAanResultaat(final OnderhoudAfnemerindicatiesResultaat resultaat,
        final BewerkAfnemerindicatieResultaat serviceResultaat)
    {
        if (serviceResultaat != null && !serviceResultaat.getMeldingen().isEmpty()) {
            for (final RegelMelding regelMelding : serviceResultaat.getMeldingen()) {
                resultaat.voegMeldingToe(
                    new Melding(regelMelding.getSoort().getWaarde(), regelMelding.getRegel().getWaarde()));
            }
        }
    }

    /**
     * Valideert informatie uit het ingekomen bericht.
     *
     * @param bericht   het bericht
     * @param resultaat resultaat om eventueel een melding op te zetten
     * @return {@code true}, als de informatie valide is
     */
    private boolean valideerBericht(final RegistreerAfnemerindicatieBericht bericht, final OnderhoudAfnemerindicatiesResultaat resultaat) {

        final List<ActieBericht> acties = bericht.getAdministratieveHandeling().getActies();
        final boolean validateAantalActies = valideerAantalActiesInBericht(acties, resultaat);

        boolean valideAantalAfnemerindicaties = false;
        if (validateAantalActies) {
            final ActieBericht actieBericht = acties.get(0);
            final PersoonBericht persoonInBericht = (PersoonBericht) actieBericht.getRootObject();
            valideAantalAfnemerindicaties = valideerAantalAfnemerindicatiesInBericht(persoonInBericht, resultaat);
        }

        boolean valideOnderhoudEnkelEigenPartij = false;
        if (validateAantalActies && valideAantalAfnemerindicaties) {
            valideOnderhoudEnkelEigenPartij = valideerOnderhoudEnkelEigenPartij(bericht, resultaat);
        }

        if (!validateAantalActies || !valideAantalAfnemerindicaties || !valideOnderhoudEnkelEigenPartij) {
            LOGGER.info("Validatie van het inkomende bericht voor onderhoud afnemerindicaties is niet correct.");
            return STOPPEN;
        }

        return DOORGAAN;
    }

    /**
     * Valideert dat er maar 1 actie is.
     *
     * @param acties    de set van acties uit het bericht
     * @param resultaat resultaat om eventueel een melding op te zetten
     * @return {@code false}, als er niet exact 1 actie in de set zit
     */
    private boolean valideerAantalActiesInBericht(
        final List<ActieBericht> acties,
        final OnderhoudAfnemerindicatiesResultaat resultaat)
    {
        if (null == acties || acties.size() != 1) {
            // TODO TEAMBRP-2902 Specifieke regel gebruiken zodra deze beschikbaar is
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001,
                "Onderhoud van afnemerindicaties ondersteunt maar 1 persoon per bericht."));
            return STOPPEN;
        }

        return DOORGAAN;
    }

    /**
     * Valideert dat er maar 1 afnemerindicatie in het bericht is.
     * <p/>
     * FIXME DEZE check zit ook in HaalLeveringsautorisatieEnPartijGegevensOpStap
     *
     * @param persoon   persoonbericht met indicatie(s)
     * @param resultaat resultaat om eventueel een melding op te zetten
     * @return {@code false}, als er niet exact 1 afnemerindicatie is
     */
    private boolean valideerAantalAfnemerindicatiesInBericht(
        final PersoonBericht persoon,
        final OnderhoudAfnemerindicatiesResultaat resultaat)
    {

        if (null == persoon.getAfnemerindicaties() || persoon.getAfnemerindicaties().size() != 1) {
            // TODO TEAMBRP-2902 Specifieke regel gebruiken zodra deze beschikbaar is
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001,
                "Onderhoud van afnemerindicaties moet 1 afnemerindicatie onderhouden."));
            return STOPPEN;
        }
        return DOORGAAN;
    }

    /**
     * Bericht kan meerdere partijCodes bevatten. Voor plaatsen en verwijderen afnemerindicatie is enkel de zendendePartij in de stuurgegevens relevant.
     * Andere elementen worden genegeerd in de verwerking, maar indien ze meegestuurd worden en ongelijk zijn aan de zendendePartij wordt er een
     * foutmelding gegenereerd.
     */
    @Regels(value = Regel.R2061)
    private boolean valideerOnderhoudEnkelEigenPartij(
        final RegistreerAfnemerindicatieBericht bericht,
        final OnderhoudAfnemerindicatiesResultaat resultaat)
    {

        final String zendendePartij = bericht.getStuurgegevens().getZendendePartijCode();
        final ActieBericht actieBericht = bericht.getAdministratieveHandeling().getActies().get(0);
        final PersoonBericht persoonInBericht = (PersoonBericht) actieBericht.getRootObject();
        final String partijCodeInHandeling = bericht.getAdministratieveHandeling().getPartijCode();
        final String partijCodeInAfnemerindicatie = persoonInBericht.getAfnemerindicaties().get(0).getAfnemerCode();
        if ((partijCodeInHandeling != null && !partijCodeInHandeling.equals(zendendePartij))
            || (partijCodeInAfnemerindicatie != null && !partijCodeInAfnemerindicatie.equals(zendendePartij)))
        {
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.R2061,
                "Een afnemer mag alleen voor zichzelf een indicatie onderhouden"));
            return STOPPEN;
        }
        return DOORGAAN;
    }

    /**
     * Verwerk de persoon voor het plaatsen/verwijderen van de afnemerindicatie uit het bericht voor het gevonden leveringsautorisatie.
     *
     * @param soortAdministratieveHandeling de soort handeling (plaatsen of verwijderen).
     * @param actieBericht                  het bericht waarin het leveringsautorisatie en de persoon worden aangegeven.
     * @param context                       de context van de verwerking
     * @return het bewerk afnemerindicatie resultaat
     * @throws BrpLockerExceptie the brp locker exceptie
     */
    private BewerkAfnemerindicatieResultaat verwerkAfnemerindicatieBericht(final SoortAdministratieveHandeling soortAdministratieveHandeling,
        final ActieBericht actieBericht, final OnderhoudAfnemerindicatiesBerichtContext context) throws BrpLockerExceptie
    {
        final PersoonBericht persoonInBericht = (PersoonBericht) actieBericht.getRootObject();
        final Integer persoonId = context.getPersoonHisVolledig().getID();
        final PersoonAfnemerindicatieBericht afnemerindicatieInBericht = persoonInBericht.getAfnemerindicaties().get(0);

        BewerkAfnemerindicatieResultaat serviceResultaat = null;
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = context.getLeveringinformatie().getToegangLeveringsautorisatie();
        if (SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE == soortAdministratieveHandeling) {
            final PersoonAfnemerindicatieStandaardGroepBericht standaardGroep = afnemerindicatieInBericht.getStandaard();
            final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode = standaardGroep.getDatumAanvangMaterielePeriode();
            context.setDatumAanvangMaterielePeriode(datumAanvangMaterielePeriode);
            final Dienst dienst = toegangLeveringsautorisatie.getLeveringsautorisatie().geefDienst(SoortDienst.PLAATSEN_AFNEMERINDICATIE);
            serviceResultaat = afnemerindicatiesService
                .plaatsAfnemerindicatie(toegangLeveringsautorisatie, persoonId, dienst.getID(), datumAanvangMaterielePeriode,
                    standaardGroep.getDatumEindeVolgen());
        } else if (SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE == soortAdministratieveHandeling) {
            final Dienst dienst = toegangLeveringsautorisatie.getLeveringsautorisatie().geefDienst(SoortDienst.VERWIJDEREN_AFNEMERINDICATIE);
            serviceResultaat = afnemerindicatiesService.verwijderAfnemerindicatie(toegangLeveringsautorisatie, persoonId, dienst.getID());
        }

        return serviceResultaat;
    }
}
