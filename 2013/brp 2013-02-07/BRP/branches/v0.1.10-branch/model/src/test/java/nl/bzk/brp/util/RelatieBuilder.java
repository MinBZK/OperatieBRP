/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.ArrayList;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderschapGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;

/**
 * Relatie builder om gemakkelijk relaties te kunnen maken voor unit tests.
 */
public final class RelatieBuilder {

    private RelatieBericht relatie;

    public RelatieBuilder() {

    }

    public RelatieBuilder bouwHuwlijkRelatie() {
        relatie = new RelatieBericht();
        relatie.setSoort(SoortRelatie.HUWELIJK);
        relatie.setGegevens(new RelatieStandaardGroepBericht());
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        return this;
    }

    public RelatieBuilder setDatumAanvang(final Integer datum) {
        relatie.getGegevens().setDatumAanvang(new Datum(datum));
        return this;
    }

    public RelatieBuilder bouwFamilieRechtelijkeBetrekkingRelatie() {
        relatie = new RelatieBericht();
        relatie.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        return this;
    }

    public RelatieBuilder voegPartnerToe(final PersoonBericht partner) {
        checkRelatieIsNietNull();
        if (SoortRelatie.FAMILIERECHTELIJKE_BETREKKING == relatie.getSoort()) {
            throw new IllegalArgumentException("Kan geen partner betrokkenheid toevoegen aan familierechtelijke betrekking");
        }
        final BetrokkenheidBericht betr = new BetrokkenheidBericht();
        betr.setRol(SoortBetrokkenheid.PARTNER);
        betr.setBetrokkene(partner);
        betr.setRelatie(relatie);
        relatie.getBetrokkenheden().add(betr);
        return this;
    }

    public RelatieBuilder voegKindToe(final PersoonBericht kind) {
        checkRelatieIsNietNull();
        if (!(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING == relatie.getSoort())) {
            throw new IllegalArgumentException("Kind betrokkenheid kan alleen toegevoegd worden aan een familierechtelijke betrekking.");
        }
        final BetrokkenheidBericht betr = new BetrokkenheidBericht();
        betr.setRol(SoortBetrokkenheid.KIND);
        betr.setBetrokkene(kind);
        betr.setRelatie(relatie);
        relatie.getBetrokkenheden().add(betr);
        return this;
    }

    public RelatieBuilder voegOuderToe(final PersoonBericht ouder) {
        checkRelatieIsNietNull();
        if (!(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING == relatie.getSoort())) {
            throw new IllegalArgumentException("Ouder betrokkenheid kan alleen toegevoegd worden aan een familierechtelijke betrekking.");
        }
        final BetrokkenheidBericht betr = new BetrokkenheidBericht();
        betr.setRol(SoortBetrokkenheid.OUDER);
        betr.setBetrokkene(ouder);
        betr.setRelatie(relatie);
        betr.setBetrokkenheidOuderlijkGezag(new BetrokkenheidOuderlijkGezagGroepBericht());
        betr.setBetrokkenheidOuderschap(new BetrokkenheidOuderschapGroepBericht());
        relatie.getBetrokkenheden().add(betr);
        return this;
    }

    private void checkRelatieIsNietNull() {
        if (relatie == null) {
            throw new IllegalStateException("Relatie is nog niet geinstantieerd, roep eerst bouwXXXRelatie() aan.");
        }
    }

    public RelatieBericht getRelatie() {
        return relatie;
    }
}
