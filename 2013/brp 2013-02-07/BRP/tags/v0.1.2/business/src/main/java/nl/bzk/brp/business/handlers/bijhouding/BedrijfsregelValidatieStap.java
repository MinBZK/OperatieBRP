/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManager;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.PersistentRootObject;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.validatie.Melding;


/**
 * De stap in de uitvoering van een bericht waarin wordt gecontroleerd of het binnenkomende bericht valide is volgens
 * een bedrijfsregel. Geconstateerde fouten waardes worden, inclusief bericht melding en zwaarte, toegevoegd aan de
 * lijst van fouten binnen het antwoord.
 */
public class BedrijfsregelValidatieStap extends AbstractBerichtVerwerkingsStap<BijhoudingsBericht, BerichtResultaat> {

    @Inject
    private BedrijfsRegelManager bedrijfsRegelManager;

    @Inject
    private PersoonRepository    persoonRepository;

    /** {@inheritDoc} */
    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final BijhoudingsBericht bericht, final BerichtContext context,
            final BerichtResultaat resultaat)
    {
        for (BRPActie actie : bericht.getBrpActies()) {
            PersistentRootObject huidigeSituatie = haalHuidigeSituatieOp(actie);
            RootObject nieuweSituatie = actie.getRootObjecten().get(0);

            if (null != bedrijfsRegelManager.getUitTeVoerenBedrijfsRegels(actie.getSoort())) {
                for (BedrijfsRegel<PersistentRootObject, RootObject> bedrijfsRegel : bedrijfsRegelManager
                        .getUitTeVoerenBedrijfsRegels(actie.getSoort()))
                {
                    final Melding melding =
                        bedrijfsRegel.executeer(huidigeSituatie, nieuweSituatie, actie.getDatumAanvangGeldigheid());
                    if (melding != null) {
                        resultaat.voegMeldingToe(melding);
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
    private PersistentRootObject haalHuidigeSituatieOp(final BRPActie actie) {
        final PersistentRootObject huidigeSituatie;
        switch (actie.getSoort()) {
            case VERHUIZING:
                huidigeSituatie =
                    persoonRepository.findByBurgerservicenummer(((Persoon) actie.getRootObjecten().get(0))
                            .getIdentificatienummers().getBurgerservicenummer());
                break;
            case REGISTRATIE_NATIONALITEIT:
            case AANGIFTE_GEBOORTE:
                huidigeSituatie = null;
                break;
            default:
                throw new IllegalArgumentException(actie.getSoort()
                    + " wordt nog niet ondersteund door de berichtverwerker");
        }
        return huidigeSituatie;
    }

}
