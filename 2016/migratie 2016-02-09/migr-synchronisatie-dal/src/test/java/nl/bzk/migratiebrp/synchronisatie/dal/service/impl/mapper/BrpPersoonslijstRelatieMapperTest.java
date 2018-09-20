/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidOuderHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;

import org.junit.Assert;
import org.junit.Test;

public class BrpPersoonslijstRelatieMapperTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    @Inject
    private BrpPersoonslijstMapper mapper;

    @Test
    public void testIkKindRelatie() {
        final Persoon persoon = maakPersoon(1);
        final Betrokkenheid kind = maakKindBetrokkenheid(persoon);
        final Betrokkenheid ouder2 = maakOuderBetrokkenheid(maakPersoon(2));
        final Betrokkenheid ouder3 = maakOuderBetrokkenheid(maakPersoon(3));
        maakFamRelatie(kind, ouder2, ouder3);

        final BrpPersoonslijst result = mapper.mapNaarMigratie(persoon, brpOnderzoekMapper);

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

        final BrpPersoonslijst result = mapper.mapNaarMigratie(persoon, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        final List<BrpRelatie> relaties = result.getRelaties();
        Assert.assertEquals(1, relaties.size());
        Assert.assertEquals(BrpSoortBetrokkenheidCode.OUDER, relaties.get(0).getRolCode());
        Assert.assertEquals(BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING, relaties.get(0).getSoortRelatieCode());
        Assert.assertEquals(1, relaties.get(0).getBetrokkenheden().size());
    }

    private Relatie maakFamRelatie(final Betrokkenheid... betrokkenheden) {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        for (final Betrokkenheid betrokkenheid : betrokkenheden) {
            relatie.addBetrokkenheid(betrokkenheid);
        }

        return relatie;
    }

    private Persoon maakPersoon(final int id) {
        final Persoon result = new Persoon(SoortPersoon.INGESCHREVENE);
        result.setId(id);

        return result;
    }

    private Betrokkenheid maakKindBetrokkenheid(final Persoon persoon) {
        final Betrokkenheid result = new Betrokkenheid(SoortBetrokkenheid.KIND, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
        result.setId(persoon.getId());
        result.setPersoon(persoon);

        persoon.addBetrokkenheid(result);
        return result;
    }

    private Betrokkenheid maakOuderBetrokkenheid(final Persoon persoon) {
        final Betrokkenheid result = new Betrokkenheid(SoortBetrokkenheid.OUDER, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
        result.setId(persoon.getId());
        result.setPersoon(persoon);

        final Partij partij = new Partij("naam", 1);
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(partij, SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL);
        final Timestamp timestamp = timestamp("20000101000000");

        final BRPActie actie = new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, partij, timestamp);
        actie.setId(1l);

        final BetrokkenheidOuderHistorie betrokkenheidOuderHistorie = new BetrokkenheidOuderHistorie(result, true);
        betrokkenheidOuderHistorie.setDatumTijdRegistratie(timestamp);
        betrokkenheidOuderHistorie.setActieInhoud(actie);

        result.addBetrokkenheidOuderHistorie(betrokkenheidOuderHistorie);

        persoon.addBetrokkenheid(result);
        return result;
    }
}
