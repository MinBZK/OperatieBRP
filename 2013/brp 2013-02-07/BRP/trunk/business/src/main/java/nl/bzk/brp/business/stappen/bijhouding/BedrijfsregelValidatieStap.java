/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bijhouding;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManager;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.stappen.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.dataaccess.exceptie.VerplichteDataNietAanwezigExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.apache.commons.lang.StringUtils;


/**
 * De stap in de uitvoering van een bericht waarin wordt gecontroleerd of het binnenkomende bericht valide is volgens
 * een bedrijfsregel. Geconstateerde fouten waardes worden, inclusief bericht melding en zwaarte, toegevoegd aan de
 * lijst van fouten binnen het antwoord.
 */
public class BedrijfsregelValidatieStap extends
    AbstractBerichtVerwerkingsStap<AbstractBijhoudingsBericht, BerichtVerwerkingsResultaat>
{

    // private static final Logger LOGGER = LoggerFactory.getLogger(BerichtUitvoerStap.class);

    @Inject
    private BedrijfsRegelManager bedrijfsRegelManager;

    @Inject
    private PersoonRepository persoonRepository;

    /** {@inheritDoc} */
    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht,
        final BerichtContext context, final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            for (Actie actie : bericht.getAdministratieveHandeling().getActies()) {
                RootObject huidigeSituatie = haalHuidigeSituatieOp(actie, bericht);
                RootObject nieuweSituatie = actie.getRootObjecten().get(0);


                if (null != bedrijfsRegelManager.getUitTeVoerenActieBedrijfsRegels(actie.getSoort())) {
                    for (ActieBedrijfsRegel<RootObject> bedrijfsRegel : bedrijfsRegelManager
                        .getUitTeVoerenActieBedrijfsRegels(actie.getSoort()))
                    {
                        final List<Melding> meldingen =
                            bedrijfsRegel.executeer(huidigeSituatie, nieuweSituatie, actie);
                        if (meldingen.size() > 0) {
                            resultaat.voegMeldingenToe(meldingen);
                        }
                    }
                }
            }

            return DOORGAAN_MET_VERWERKING;
        } catch (VerplichteDataNietAanwezigExceptie e) {
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, MeldingCode.ALG0001,
                    "Kan geen huidig situatie vinden (persoon dan wel relatie).",
                    bericht.getAdministratieveHandeling(), ""));
            return STOP_VERWERKING;
        }
    }

    /**
     * Omdat bedrijfsregels gebruik maken van de huidige situatie in de BRP database, moet de huidige situatie ook
     * via de DAL laag opgehaald worden. Dat gebeurt in deze functie.
     *
     * @param actie De actie die uitegevoerd dient te worden uit het inkomende bericht.
     * @return Een PersistentRootObject wat in feite een instantie is van een Persoon of Relatie sinds deze 2 objecten
     *         als RootObject worden gezien.
     */
    private RootObject haalHuidigeSituatieOp(final Actie actie, final AbstractBijhoudingsBericht bericht) {
        final RootObject huidigeSituatie;
        switch (actie.getSoort()) {
            case REGISTRATIE_OVERLIJDEN:
            case REGISTRATIE_ADRES:
            case CORRECTIE_ADRES:
            case REGISTRATIE_AANSCHRIJVING:
                huidigeSituatie = haalHuidigePersoon((PersoonBericht) actie.getRootObjecten().get(0), bericht);
                break;
            case REGISTRATIE_NATIONALITEIT:
            case REGISTRATIE_GEBOORTE:
            case REGISTRATIE_HUWELIJK:
                huidigeSituatie = null;
                break;
            default:
                throw new IllegalArgumentException(actie.getSoort()
                    + " wordt nog niet ondersteund door de berichtverwerker");
        }
        return huidigeSituatie;
    }

    /**
     * In de toekomst gaat dit beter werken met de technische sleutels .
     * @param nieuweSituatie .
     * @return .
     */
    private Persoon haalHuidigePersoon(final PersoonBericht nieuweSituatie, final AbstractBijhoudingsBericht bericht) {
        Persoon persoonModel = null;
        String technischeSleutel = null;
        if (StringUtils.isNotBlank(nieuweSituatie.getTechnischeSleutel())) {
            technischeSleutel = nieuweSituatie.getTechnischeSleutel();
        } else {
            // test of deze persoon een referentie heeft naar een ander persoon, zo ja haal daar de tecnische sleutel
            // of in geval van inschriving geboorte, zijn 'nieuwe' bsn.
            if (StringUtils.isNotBlank(nieuweSituatie.getReferentieID())) {
                // we gaan vanuit dat alles al goed is gechecked en alle referentie kloppen en dat alle ID's uniek zijn.
                List<Identificeerbaar> idents = bericht.getIdentificeerbaarObjectIndex().get(nieuweSituatie.getReferentieID());
                PersoonBericht persoonGerefereerd = (PersoonBericht) idents.get(0);
                if (StringUtils.isNotBlank(persoonGerefereerd.getTechnischeSleutel())) {
                    technischeSleutel = persoonGerefereerd.getTechnischeSleutel();
                } else if (null != persoonGerefereerd.getIdentificatienummers()
                        && null != persoonGerefereerd.getIdentificatienummers().getBurgerservicenummer()) {
                    persoonModel = haalopPersoonViaBsn(
                            persoonGerefereerd.getIdentificatienummers().getBurgerservicenummer());
                }
            }
        }
        if (technischeSleutel != null) {
            persoonModel = haalopPersoonViaTechnischeSleutel(technischeSleutel);
        }
        if (null == persoonModel) {
            // TODO: als geen record gevonden kan worden, dan throw een exception !!!
            throw new VerplichteDataNietAanwezigExceptie(String.format(
                    "Kan geen persoon vinden gereferrerd met %s", technischeSleutel));

        }
        return persoonModel;
    }

    /***
     * Haal een persoon model op aan de hand van zijn technische sleutel.
     * @param technischeSleutel de sleutel.
     * @return de persoon.
     */
    private PersoonModel haalopPersoonViaTechnischeSleutel(final String technischeSleutel) {
        // TODO, deze helemaal uitwerken op de huidig persoon op te halen adh. technische sleutel.
        // technische sleutel kan een normale bsn nummer zijn of een db_id
        PersoonModel persoonModel = null;
        if (technischeSleutel.startsWith("db")) {
            String id = technischeSleutel.substring("db".length());
            // 10 == radix, string beginnend met '0' wordt geconverteerd naar octal !
            persoonModel = persoonRepository.findPersoonMetId(Integer.parseInt(id, 10));
        } else {
            persoonModel = persoonRepository.findByBurgerservicenummer(new Burgerservicenummer(technischeSleutel));
        }
        return persoonModel;
    }

    /***
     * Haal een persoon model op aan de hand van een bsn nummer.
     * @param bsn de bsn.
     * @return de persoon.
     */
    private PersoonModel haalopPersoonViaBsn(final Burgerservicenummer bsn) {
        // dit is een speciefiek methode; op dit ogenblik lijkt dit heel veel op haalopPersoonViaTechnischeSleutel
        // maar is functioneel anders. Deze wordt gebruikt voor ophalen van 'kind' die net ingeschreven is.
        // Het bericht refereert naar een persoon die net ingeschreven is;
        // de client heeft dus nog geen technische sleutel van deze persoon.
        PersoonModel persoonModel =
                persoonRepository.findByBurgerservicenummer(bsn);
        if (null == persoonModel) {
            // TODO: als geen record gevonden kan worden, dan throw een exception !!!
            throw new VerplichteDataNietAanwezigExceptie(String.format(
                    "Kan geen persoon vinden gereferrerd met %s", bsn.getWaarde().toString()));

        }
        return persoonModel;
    }

    @Override
    public boolean voerStapUit(final AbstractBijhoudingsBericht onderwerp, final BerichtContext context,
                               final BerichtVerwerkingsResultaat resultaat)
    {
        return voerVerwerkingsStapUitVoorBericht(onderwerp, context, resultaat);
    }

}
