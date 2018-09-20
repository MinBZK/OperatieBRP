/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Betrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BetrokkenheidOuderHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MultiRealiteitRegel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MultiRealiteitRegelHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Relatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortMultiRealiteitRegel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortRelatie;

import org.junit.Assert;
import org.junit.Test;

public class BrpPersoonslijstRelatieMapperTest extends BrpAbstractTest {

    @Inject
    private BrpPersoonslijstMapper mapper;

    @Test
    public void testIkKindRelatie() {
        final Persoon persoon = maakPersoon(1);
        final Betrokkenheid kind = maakKindBetrokkenheid(persoon);
        final Betrokkenheid ouder2 = maakOuderBetrokkenheid(maakPersoon(2));
        final Betrokkenheid ouder3 = maakOuderBetrokkenheid(maakPersoon(3));
        final Betrokkenheid ouder4 = maakOuderBetrokkenheid(maakPersoon(4));
        maakFamRelatie(kind, ouder2, ouder3, ouder4);

        // Test met (volledig) MR
        final MultiRealiteitRegel mr = new MultiRealiteitRegel();
        persoon.addMultiRealiteitRegelGeldigVoorPersoon(mr);
        mr.setSoortMultiRealiteitRegel(SoortMultiRealiteitRegel.BETROKKENHEID);
        mr.setBetrokkenheid(ouder4);

        final MultiRealiteitRegelHistorie mrHist = new MultiRealiteitRegelHistorie();
        mrHist.setDatumTijdRegistratie(timestamp("20000101000000"));

        final BrpPersoonslijst result = mapper.mapNaarMigratie(persoon);

        Assert.assertNotNull(result);
        final List<BrpRelatie> relaties = result.getRelaties();
        Assert.assertEquals(1, relaties.size());
        Assert.assertEquals(BrpSoortBetrokkenheidCode.KIND, relaties.get(0).getRolCode());
        Assert.assertEquals(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, relaties.get(0).getSoortRelatieCode());
        Assert.assertEquals(2, relaties.get(0).getBetrokkenheden().size());
    }

    @Test
    public void testIkOuderRelatie() {
        final Persoon persoon = maakPersoon(1);
        final Betrokkenheid kind = maakKindBetrokkenheid(maakPersoon(2));
        final Betrokkenheid ouder2 = maakOuderBetrokkenheid(persoon);
        final Betrokkenheid ouder3 = maakOuderBetrokkenheid(maakPersoon(3));
        final Betrokkenheid ouder4 = maakOuderBetrokkenheid(maakPersoon(4));
        maakFamRelatie(kind, ouder2, ouder3, ouder4);

        final BrpPersoonslijst result = mapper.mapNaarMigratie(persoon);

        Assert.assertNotNull(result);
        final List<BrpRelatie> relaties = result.getRelaties();
        Assert.assertEquals(1, relaties.size());
        Assert.assertEquals(BrpSoortBetrokkenheidCode.OUDER, relaties.get(0).getRolCode());
        Assert.assertEquals(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, relaties.get(0).getSoortRelatieCode());
        Assert.assertEquals(1, relaties.get(0).getBetrokkenheden().size());
    }

    @Test
    public void testIkOuderMRRelatie() {
        final Persoon persoon = maakPersoon(1);
        final Betrokkenheid kind = maakKindBetrokkenheid(maakPersoon(2));
        final Betrokkenheid ouder2 = maakOuderBetrokkenheid(persoon);
        final Betrokkenheid ouder3 = maakOuderBetrokkenheid(maakPersoon(3));
        final Betrokkenheid ouder4 = maakOuderBetrokkenheid(maakPersoon(4));
        maakFamRelatie(kind, ouder2, ouder3, ouder4);

        // Test met (volledig) MR
        final MultiRealiteitRegel mr = new MultiRealiteitRegel();
        persoon.addMultiRealiteitRegelGeldigVoorPersoon(mr);
        mr.setSoortMultiRealiteitRegel(SoortMultiRealiteitRegel.BETROKKENHEID);
        mr.setBetrokkenheid(ouder2);

        final BrpPersoonslijst result = mapper.mapNaarMigratie(persoon);

        Assert.assertNotNull(result);
        final List<BrpRelatie> relaties = result.getRelaties();
        Assert.assertEquals(0, relaties.size());
        // Assert.assertEquals(BrpSoortBetrokkenheidCode.OUDER, relaties.get(0).getRolCode());
        // Assert.assertEquals(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING,
        // relaties.get(0).getSoortRelatieCode());
        // Assert.assertEquals(1, relaties.get(0).getBetrokkenheden().size());
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private Relatie maakFamRelatie(final Betrokkenheid... betrokkenheden) {
        final Relatie relatie = new Relatie();
        relatie.setSoortRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        for (final Betrokkenheid betrokkenheid : betrokkenheden) {
            relatie.addBetrokkenheid(betrokkenheid);
        }

        return relatie;
    }

    private Persoon maakPersoon(final int id) {
        final Persoon result = new Persoon();
        result.setId(0L + id);

        return result;
    }

    private Betrokkenheid maakKindBetrokkenheid(final Persoon persoon) {
        final Betrokkenheid result = new Betrokkenheid();
        result.setId(persoon.getId().intValue());
        result.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        result.setPersoon(persoon);

        persoon.addBetrokkenheid(result);
        return result;
    }

    private Betrokkenheid maakOuderBetrokkenheid(final Persoon persoon) {
        final Betrokkenheid result = new Betrokkenheid();
        result.setId(persoon.getId().intValue());
        result.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);
        result.setPersoon(persoon);

        final BetrokkenheidOuderHistorie betrokkenheidOuderHistorie = new BetrokkenheidOuderHistorie();
        betrokkenheidOuderHistorie.setIndicatieOuder(true);
        betrokkenheidOuderHistorie.setDatumTijdRegistratie(timestamp("20000101000000"));

        result.addBetrokkenheidOuderHistorie(betrokkenheidOuderHistorie);

        persoon.addBetrokkenheid(result);
        return result;
    }
}
