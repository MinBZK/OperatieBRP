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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlControleVolledigGelijkMuvAdressenDatumtijdstempelEnVersienummerTest {

    private final PlControleVolledigGelijkMuvAdressenDatumtijdstempelEnVersienummer subject =
            new PlControleVolledigGelijkMuvAdressenDatumtijdstempelEnVersienummer();

    @Before
    public void setupLogging() {
        SynchronisatieLogging.init();
    }

    @Test
    public void test() {
        Assert.assertTrue(subject.controleer(
            maakContext(versie(1), bsns(1), geboortes(19770101), adressen("1234AA")),
            maakPl(versie(1), bsns(1), geboortes(19770101), adressen("1234AA"))));
        Assert.assertTrue(subject.controleer(
            maakContext(versie(2), bsns(1), geboortes(19770101), adressen("2233AA")),
            maakPl(versie(1), bsns(1), geboortes(19770101), adressen("1234AA"))));

        Assert.assertFalse(subject.controleer(
            maakContext(versie(1), bsns(3, 2, 1), geboortes(19770101), adressen("1234AA")),
            maakPl(versie(1), bsns(1), geboortes(19770101), adressen("1234AA"))));
        Assert.assertFalse(subject.controleer(
            maakContext(versie(1), bsns(1), geboortes(19780101, 19770101), adressen("1234AA")),
            maakPl(versie(1), bsns(1), geboortes(19770101), adressen("1234AA"))));
        Assert.assertFalse(subject.controleer(
            maakContext(versie(1), bsns(3, 2, 1), geboortes(19780101, 19770101), adressen("1234AA")),
            maakPl(versie(1), bsns(1), geboortes(19770101), adressen("1234AA"))));

    }

    private VerwerkingsContext maakContext(
        final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel,
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel,
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel,
        final BrpStapel<BrpAdresInhoud> adresStapel)
    {
        return new VerwerkingsContext(null, null, null, maakPl(inschrijvingStapel, identificatienummersStapel, geboorteStapel, adresStapel));
    }

    private BrpPersoonslijst maakPl(
        final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel,
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel,
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel,
        final BrpStapel<BrpAdresInhoud> adresStapel)
    {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        builder.inschrijvingStapel(inschrijvingStapel);
        builder.identificatienummersStapel(identificatienummersStapel);
        builder.geboorteStapel(geboorteStapel);
        builder.adresStapel(adresStapel);

        return builder.build();
    }

    private BrpStapel<BrpInschrijvingInhoud> versie(final int versie) {
        return BrpStapelHelper.stapel(BrpStapelHelper.groep(
            BrpStapelHelper.inschrijving(19770101, versie, 20000101000000L),
            BrpStapelHelper.his(20000101),
            BrpStapelHelper.act(1, 20000101)));
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

    private BrpStapel<BrpAdresInhoud> adressen(final String... postcodes) {
        final List<BrpGroep<BrpAdresInhoud>> groepen = new ArrayList<>();
        for (int i = 0; i < postcodes.length; i++) {
            final int datumAanvang = 20010131 - i;
            final BrpAdresInhoud inhoud = BrpStapelHelper.adres("W", 'I', 'P', datumAanvang, "0626", "", "", 1, postcodes[i], "Den Haag");
            final BrpHistorie historie = BrpStapelHelper.his(datumAanvang, i == 0 ? null : 20010132 - i, 20010131, null);
            final BrpActie actieInhoud = BrpStapelHelper.act(i, datumAanvang);
            final BrpActie actieAanpassingGeldigheid = i == 0 ? null : BrpStapelHelper.act(i, 20010132 - i);
            groepen.add(BrpStapelHelper.groep(inhoud, historie, actieInhoud, null, actieAanpassingGeldigheid));
        }
        return new BrpStapel<>(groepen);
    }
}
