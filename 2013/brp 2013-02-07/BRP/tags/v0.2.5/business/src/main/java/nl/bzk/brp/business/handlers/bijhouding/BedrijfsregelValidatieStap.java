/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManager;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.validatie.Melding;


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
        for (Actie actie : bericht.getBrpActies()) {
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
            case AANGIFTE_OVERLIJDEN:
            case VERHUIZING:
            case CORRECTIE_ADRES_NL:
            case WIJZIGING_NAAMGEBRUIK:
                huidigeSituatie =
                    persoonRepository.findByBurgerservicenummer(((PersoonBericht) actie.getRootObjecten().get(0))
                        .getIdentificatienummers().getBurgerservicenummer());
                break;
            case REGISTRATIE_NATIONALITEIT:
            case AANGIFTE_GEBOORTE:
            case HUWELIJK:
                huidigeSituatie = null;
                break;
            default:
                throw new IllegalArgumentException(actie.getSoort()
                    + " wordt nog niet ondersteund door de berichtverwerker");
        }
        return huidigeSituatie;
    }

}
