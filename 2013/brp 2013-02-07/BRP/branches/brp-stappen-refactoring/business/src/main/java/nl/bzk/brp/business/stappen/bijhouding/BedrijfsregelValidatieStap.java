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
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.stappen.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;
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
        for (Actie actie : bericht.getAdministratieveHandeling().getActies()) {
            RootObject huidigeSituatie = haalHuidigeSituatieOp(actie);
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
    }

    /**
     * Omdat bedrijfsregels gebruik maken van de huidige situatie in de BRP database, moet de huidige situatie ook
     * via de DAL laag opgehaald worden. Dat gebeurt in deze functie.
     *
     * @param actie De actie die uitegevoerd dient te worden uit het inkomende bericht.
     * @return Een PersistentRootObject wat in feite een instantie is van een Persoon of Relatie sinds deze 2 objecten
     *         als RootObject worden gezien.
     */
    private RootObject haalHuidigeSituatieOp(final Actie actie) {
        final RootObject huidigeSituatie;
        switch (actie.getSoort()) {
            case REGISTRATIE_OVERLIJDEN:
            case REGISTRATIE_ADRES:
            case CORRECTIE_ADRES:
            case REGISTRATIE_AANSCHRIJVING:
                huidigeSituatie = haalHuidigePersoon((PersoonBericht) actie.getRootObjecten().get(0));
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
    private Persoon haalHuidigePersoon(final PersoonBericht nieuweSituatie) {
        Persoon persoonModel = null;
        if (StringUtils.isNotBlank(nieuweSituatie.getTechnischeSleutel())) {
            // TODO, deze helemaal uitwerken op de huidig persoon op te halen adh. technische sleutel.
            persoonModel =
                    persoonRepository.findByBurgerservicenummer(
                        new Burgerservicenummer(nieuweSituatie.getTechnischeSleutel()));
            // copieer naar de groep (for sake of downward compatible. moet later ook weggehaald worden).
            if (nieuweSituatie.getIdentificatienummers() == null) {
                nieuweSituatie.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
            }
            nieuweSituatie.getIdentificatienummers().setBurgerservicenummer(
                    new Burgerservicenummer(nieuweSituatie.getTechnischeSleutel()));
        } else {
            // TODO deze uitfaseren, omdat de groep nooit meer door gegeven wordt.
            persoonModel =
                    persoonRepository.findByBurgerservicenummer(nieuweSituatie
                        .getIdentificatienummers().getBurgerservicenummer());
        }
        // TODO: als geen record gevonden kan worden, dan throw een exception !!!
        return persoonModel;
    }
}
