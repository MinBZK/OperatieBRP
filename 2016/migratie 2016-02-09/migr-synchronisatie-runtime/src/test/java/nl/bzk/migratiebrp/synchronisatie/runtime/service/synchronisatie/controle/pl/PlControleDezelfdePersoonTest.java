/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlControleDezelfdePersoonTest {

    private final PlControleDezelfdePersoon subject = new PlControleDezelfdePersoon();

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void test() {
        Assert.assertTrue(subject.controleer(maakContext(bsns(1), geboortes(19770101)), maakPl(bsns(1), geboortes(19770101))));
        Assert.assertTrue(subject.controleer(maakContext(bsns(3, 2, 1), geboortes(19770101)), maakPl(bsns(1), geboortes(19770101))));
        Assert.assertTrue(subject.controleer(maakContext(bsns(1), geboortes(19780101, 19770101)), maakPl(bsns(1), geboortes(19770101))));
        Assert.assertTrue(subject.controleer(maakContext(bsns(3, 2, 1), geboortes(19780101, 19770101)), maakPl(bsns(1), geboortes(19770101))));

        Assert.assertFalse(subject.controleer(maakContext(bsns(3, 2, 1), geboortes(19780101, 19770101)), maakPl(bsns(4), geboortes(19760101))));
        Assert.assertFalse(subject.controleer(maakContext(bsns(3, 2, 1), geboortes(19780101, 19770101)), maakPl(bsns(3), geboortes(19760101))));
        Assert.assertFalse(subject.controleer(maakContext(bsns(3, 2, 1), geboortes(19780101, 19770101)), maakPl(bsns(4), geboortes(19770101))));

    }

    private VerwerkingsContext maakContext(
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel,
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel)
    {
        return new VerwerkingsContext(null, null, null, maakPl(identificatienummersStapel, geboorteStapel));
    }

    private BrpPersoonslijst maakPl(
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel,
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel)
    {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        builder.identificatienummersStapel(identificatienummersStapel);
        builder.geboorteStapel(geboorteStapel);

        return builder.build();
    }

    private BrpStapel<BrpIdentificatienummersInhoud> bsns(final Integer... bsns) {
        final List<BrpGroep<BrpIdentificatienummersInhoud>> groepen = new ArrayList<>();
        for (int i = 0; i < bsns.length; i++) {
            final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(null, new BrpInteger(bsns[i]));
            final BrpHistorie historie = BrpStapelHelper.his(20010131 - i, i == 0 ? null : 20010132 - i, 20010131, null);
            final BrpActie actieInhoud = BrpStapelHelper.act(i, 20010131 - i);
            final BrpActie actieAanpassingGeldigheid = i == 0 ? null : BrpStapelHelper.act(i, 20010132 - i);
            groepen.add(BrpStapelHelper.groep(inhoud, historie, actieInhoud, null, actieAanpassingGeldigheid));
        }
        return new BrpStapel<>(groepen);
    }

    private BrpStapel<BrpGeboorteInhoud> geboortes(final Integer... geboorteDatums) {
        final List<BrpGroep<BrpGeboorteInhoud>> groepen = new ArrayList<>();
        for (int i = 0; i < geboorteDatums.length; i++) {
            final BrpGeboorteInhoud inhoud = BrpStapelHelper.geboorte(geboorteDatums[i], "0516");
            final BrpHistorie historie = BrpStapelHelper.his(20010131 - i, i == 0 ? null : 20010132 - i, 20010131, null);
            final BrpActie actieInhoud = BrpStapelHelper.act(i, 20010131 - i);
            final BrpActie actieAanpassingGeldigheid = i == 0 ? null : BrpStapelHelper.act(i, 20010132 - i);
            groepen.add(BrpStapelHelper.groep(inhoud, historie, actieInhoud, null, actieAanpassingGeldigheid));
        }
        return new BrpStapel<>(groepen);
    }
}
