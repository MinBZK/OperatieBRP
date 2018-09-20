/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.basis.ModelRootObject;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;
import nl.bzk.brp.model.logisch.kern.RelatieStandaardGroep;

/**
 * Generieke implementatie van de lokatie bedrijfsregels.
 * Per subklasse is 1 specifieke regel van toepassing.
 */
public abstract class AbstractLocatieRegel implements
        VoorActieRegelMetMomentopname<ModelRootObject, BerichtRootObject>
{

    @Override
    public List<BerichtEntiteit> voerRegelUit(final ModelRootObject huidigeSituatie,
            final BerichtRootObject nieuweSituatie, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> overtreders = new ArrayList<>();
        if (nieuweSituatie instanceof RelatieBericht) {
            checkRelatie((RelatieBericht) nieuweSituatie, overtreders);
        } else if (nieuweSituatie instanceof PersoonBericht) {
            checkPersoon((PersoonBericht) nieuweSituatie, overtreders);
        }

        return overtreders;
    }

    /**
     * Check de relatie op locatie gegevens, nl voor aanvang en einde HGP
     * en alle betrokken personen.
     *
     * @param relatie     de relatie
     * @param overtreders de overtreders
     */
    private void checkRelatie(final RelatieBericht relatie, final List<BerichtEntiteit> overtreders) {
        if (relatie instanceof HuwelijkGeregistreerdPartnerschap) {
            final RelatieStandaardGroep hgp = relatie.getStandaard();
            if (hgp.getDatumAanvang() != null && !voldoetAanRegel(hgp.getLandGebiedAanvang(),
                    hgp.getGemeenteAanvang(),
                    hgp.getWoonplaatsnaamAanvang(),
                    hgp.getBuitenlandsePlaatsAanvang(),
                    hgp.getBuitenlandseRegioAanvang(),
                    hgp.getOmschrijvingLocatieAanvang()))
            {
                overtreders.add(relatie);
            }
            if (hgp.getDatumEinde() != null && !voldoetAanRegel(hgp.getLandGebiedEinde(),
                hgp.getGemeenteEinde(),
                hgp.getWoonplaatsnaamEinde(),
                hgp.getBuitenlandsePlaatsEinde(),
                hgp.getBuitenlandseRegioEinde(),
                hgp.getOmschrijvingLocatieEinde()))
            {
                overtreders.add(relatie);
            }
        }
        for (final BetrokkenheidBericht betrokkenheid : relatie.getBetrokkenheden()) {
            checkPersoon(betrokkenheid.getPersoon(), overtreders);
        }
    }

    /**
     * Check de persoon op locatie gegevens, nl voor geboorte en overlijden.
     *
     * @param persoon     de persoon
     * @param overtreders de overtreders
     */
    private void checkPersoon(final PersoonBericht persoon, final List<BerichtEntiteit> overtreders) {
        if (persoon.getGeboorte() != null) {
            final PersoonGeboorteGroep geboorte = persoon.getGeboorte();
            if (!voldoetAanRegel(geboorte.getLandGebiedGeboorte(),
                    geboorte.getGemeenteGeboorte(),
                    geboorte.getWoonplaatsnaamGeboorte(),
                    geboorte.getBuitenlandsePlaatsGeboorte(),
                    geboorte.getBuitenlandseRegioGeboorte(),
                    geboorte.getOmschrijvingLocatieGeboorte()))
            {
                overtreders.add(persoon);
            }
        }
        if (persoon.getOverlijden() != null) {
            final PersoonOverlijdenGroep overlijden = persoon.getOverlijden();
            if (!voldoetAanRegel(overlijden.getLandGebiedOverlijden(),
                    overlijden.getGemeenteOverlijden(),
                    overlijden.getWoonplaatsnaamOverlijden(),
                    overlijden.getBuitenlandsePlaatsOverlijden(),
                    overlijden.getBuitenlandseRegioOverlijden(),
                    overlijden.getOmschrijvingLocatieOverlijden()))
            {
                overtreders.add(persoon);
            }
        }
    }

    /**
     * Checkt of de gegevens aan de locatie regel voldoen.
     *
     * @param land                land in locatie.
     * @param gemeente            gemeente in locatie.
     * @param plaatsnaam              plaatsnaam in locatie.
     * @param buitenlandsePlaats  buitenlandseplaats in locatie.
     * @param buitenlandseRegio   buitenlandse regio in locatie.
     * @param locatieOmschrijving de locatie omschrijving.
     * @return de overtreden regels.
     */
    protected abstract boolean voldoetAanRegel(final LandGebiedAttribuut land, final GemeenteAttribuut gemeente,
                                               final NaamEnumeratiewaardeAttribuut plaatsnaam,
                                               final BuitenlandsePlaatsAttribuut buitenlandsePlaats,
                                               final BuitenlandseRegioAttribuut buitenlandseRegio,
                                               final LocatieomschrijvingAttribuut locatieOmschrijving);

    /**
     * Is dit land Nederland?
     *
     * @param land het land
     * @return of het Nederland is
     */
    protected boolean isNederland(final LandGebiedAttribuut land) {
        return land != null && land.getWaarde().getCode().equals(LandGebiedCodeAttribuut.NEDERLAND);
    }

}
