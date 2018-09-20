/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bijhouding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.business.dto.AbstractBRPBericht;
import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.validatie.OverruleMelding;
import nl.bzk.brp.util.AttribuutTypeUtil;


/** Model class voor het xsd type BRPbericht. */
public abstract class AbstractBijhoudingsBericht extends AbstractBRPBericht implements BRPBericht {

    private List<Actie> brpActies = null;
    private List<OverruleMelding> overruledMeldingen = new ArrayList<OverruleMelding>();

    /** default constructor. */
    public AbstractBijhoudingsBericht() {
    }

    public List<Actie> getBrpActies() {
        return brpActies;
    }

    public void setBrpActies(final List<Actie> brpActies) {
        this.brpActies = brpActies;
    }

    /**
     * Voeg een Inschrijving Geboorte actie toe.
     * @param actie de actie.
     */
    public void voegInschrijvingGeboorteActieToe(final ActieBericht actie) {
        voegActieToe(actie, SoortActie.AANGIFTE_GEBOORTE);
    }

    /**
     * Voeg een Registratie Nationaliteit actie toe.
     * @param actie de acie
     */
    public void voegRegistratieNationaliteitActieToe(final ActieBericht actie) {
        voegActieToe(actie, SoortActie.REGISTRATIE_NATIONALITEIT);
    }

    /**
     * Voeg een verhuizing actie toe.
     * @param actie de acie
     */
    public void voegVerhuizingActieToe(final ActieBericht actie) {
        voegActieToe(actie, SoortActie.VERHUIZING);
    }

    /**
     * Voegt een actie toe aan de lijst van BRP acties.
     *
     * @param actie De toe te voegen actie.
     */
    public void voegActieToe(final ActieBericht actie) {
        voegActieToe(actie, null);
    }

    /**
     * Voeg een (generieke) actie toe aan dit bericht.
     * @param actie de actie
     * @param soortActie de actie soort, als deze null is wordt de soort avn de actie niet gezet.
     *  Aanname dat dit al correct is.
     */
    private void voegActieToe(final ActieBericht actie, final SoortActie soortActie) {
        if (actie != null) {
            if (brpActies == null) {
                brpActies = new ArrayList<Actie>();
            }
            if (actie.getSoort() == null && soortActie != null) {
                actie.setSoort(soortActie);
            }
            if (!brpActies.contains(actie)) {
                brpActies.add(actie);
            }
        }
    }

    /**
     * .
     * @param soortActie .
     * @return .
     */
    private Actie getActieVanSoort(final SoortActie soortActie) {
        if (null != brpActies && !brpActies.isEmpty()) {
            for (Actie actie : brpActies) {
                if (actie.getSoort() == soortActie) {
                    return actie;
                }
            }
        }
        return null;
    }

    /**
     * haal een actie soort van het type INSCHRIJVING_GEBOORTE.
     * @return de actie of null als niet gevonden
     */
    public Actie getInschrijvingGeboorteActie() {
        return getActieVanSoort(SoortActie.AANGIFTE_GEBOORTE);
    }

    /**
     * haal een actie soort van het type REGISTRATIE_NATIONALITEIT.
     * @return de actie of null als niet gevonden
     */
    public Actie getRegistratieNationaliteitActie() {
        return getActieVanSoort(SoortActie.REGISTRATIE_NATIONALITEIT);
    }

    /**
     * haal een actie soort van het type VERHUIZING.
     * @return de actie of null als niet gevonden
     */
    public Actie getVerhuizingActie() {
        return getActieVanSoort(SoortActie.VERHUIZING);
    }

    /** {@inheritDoc} */
    @Override
    public Collection<String> getReadBsnLocks() {
        return Collections.emptyList();
    }

    /** {@inheritDoc} */
    @Override
    public Collection<String> getWriteBsnLocks() {
        Collection<String> resultaat = new ArrayList<String>();
        if (getBrpActies() != null) {
            for (Actie actie : getBrpActies()) {
                voegWriteBsnLocksVoorActieToeAanResultaat(actie, resultaat);
            }
        }
        return resultaat;
    }

    /**
     * Voegt de BSNs toe aan de opgegeven collectie van bsns, waarvoor voor opgegeven actie een 'write lock' nodig is.
     *
     * @param actie de actie waarvoor bepaald dient te worden welke BSNs gelockt moeten worden.
     * @param bsns een lijst van BSNs die gelockt dienen te worden.
     */
    private void voegWriteBsnLocksVoorActieToeAanResultaat(final Actie actie, final Collection<String> bsns) {
        if (actie.getRootObjecten() != null && !actie.getRootObjecten().isEmpty()) {
            for (RootObject object : actie.getRootObjecten()) {
                if (object instanceof PersoonBericht) {
                    PersoonBericht persoon = (PersoonBericht) object;
                    if (persoon.getIdentificatieNummers() != null
                        && AttribuutTypeUtil.isNotBlank(persoon.getIdentificatieNummers().getBurgerServiceNummer()))
                    {
                        bsns.add(persoon.getIdentificatieNummers().getBurgerServiceNummer().getWaarde());
                    }
                }
            }
        }
    }

    public List<OverruleMelding> getOverruledMeldingen() {
        return overruledMeldingen;
    }

    public void setOverruledMeldingen(final List<OverruleMelding> overruledMeldingen) {
        this.overruledMeldingen = overruledMeldingen;
    }

}
