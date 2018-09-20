/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

import java.math.BigDecimal;
import java.util.Arrays;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpWijzeGebruikGeslachtsnaamCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAanschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAdresInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class BrpPersoonslijstBuilderTest {

    private final BrpHistorie historie = new BrpHistorie(new BrpDatum(20120203), null,
            BrpDatumTijd.fromDatum(20120203), null);
    private final BrpStapel<BrpIdentificatienummersInhoud> idNummers = new BrpStapel<BrpIdentificatienummersInhoud>(
            Arrays.asList(new BrpGroep<BrpIdentificatienummersInhoud>(new BrpIdentificatienummersInhoud(1234567890L,
                    123456789L), historie, null, null, null)));
    private final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsAanduiding =
            new BrpStapel<BrpGeslachtsaanduidingInhoud>(Arrays.asList(new BrpGroep<BrpGeslachtsaanduidingInhoud>(
                    new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN), historie, null, null, null)));
    private final BrpStapel<BrpGeboorteInhoud> geboorte = new BrpStapel<BrpGeboorteInhoud>(
            Arrays.asList(new BrpGroep<BrpGeboorteInhoud>(new BrpGeboorteInhoud(new BrpDatum(20120203),
                    new BrpGemeenteCode(new BigDecimal("1234")), null, null, null, new BrpLandCode(Integer
                            .valueOf("6030")), null), historie, null, null, null)));
    private final BrpStapel<BrpAanschrijvingInhoud> aanschrijving = new BrpStapel<BrpAanschrijvingInhoud>(
            Arrays.asList(new BrpGroep<BrpAanschrijvingInhoud>(new BrpAanschrijvingInhoud(
                    BrpWijzeGebruikGeslachtsnaamCode.E, null, null, null, null, null, null, null, null), historie,
                    null, null, null)));
    private final BrpStapel<BrpInschrijvingInhoud> inschrijving = new BrpStapel<BrpInschrijvingInhoud>(
            Arrays.asList(new BrpGroep<BrpInschrijvingInhoud>(new BrpInschrijvingInhoud(1234567890L, 9876543211L,
                    new BrpDatum(20120203), 1), historie, null, null, null)));
    private final BrpStapel<BrpAdresInhoud> adres = new BrpStapel<BrpAdresInhoud>(
            Arrays.asList(new BrpGroep<BrpAdresInhoud>(new BrpAdresInhoud(null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                    null, null), historie, null, null, null)));

    //
    // @Test(expected = InputValidationException.class)
    // public void testCreateFailure() throws InputValidationException {
    // final List<BrpStapel<?>> brpStapels = new ArrayList<BrpStapel<?>>();
    // BrpPersoonslijstBuilder.create(brpStapels);
    // }
    //
    // @Test(expected = InputValidationException.class)
    // public void testGeslachtsaanduidingNull() throws InputValidationException {
    // final List<BrpStapel<?>> brpStapels = new ArrayList<BrpStapel<?>>();
    // brpStapels.add(idNummers);
    // BrpPersoonslijstBuilder.create(brpStapels);
    // }
    //
    // @Test(expected = InputValidationException.class)
    // public void testGeboorteNull() throws InputValidationException {
    // final List<BrpStapel<?>> brpStapels = new ArrayList<BrpStapel<?>>();
    // brpStapels.add(idNummers);
    // brpStapels.add(geslachtsAanduiding);
    // BrpPersoonslijstBuilder.create(brpStapels);
    // }
    //
    // @Test(expected = InputValidationException.class)
    // public void testAanschrijvingNull() throws InputValidationException {
    // final List<BrpStapel<?>> brpStapels = new ArrayList<BrpStapel<?>>();
    // brpStapels.add(idNummers);
    // brpStapels.add(geslachtsAanduiding);
    // brpStapels.add(geboorte);
    // BrpPersoonslijstBuilder.create(brpStapels);
    // }
    //
    // @Test(expected = InputValidationException.class)
    // public void testInschrijvingNull() throws InputValidationException {
    // final List<BrpStapel<?>> brpStapels = new ArrayList<BrpStapel<?>>();
    // brpStapels.add(idNummers);
    // brpStapels.add(geslachtsAanduiding);
    // brpStapels.add(geboorte);
    // brpStapels.add(aanschrijving);
    // BrpPersoonslijstBuilder.create(brpStapels);
    // }
    //
    // @Test(expected = InputValidationException.class)
    // public void testAdresStapelNull() throws InputValidationException {
    // final List<BrpStapel<?>> brpStapels = new ArrayList<BrpStapel<?>>();
    // brpStapels.add(idNummers);
    // brpStapels.add(geslachtsAanduiding);
    // brpStapels.add(geboorte);
    // brpStapels.add(aanschrijving);
    // brpStapels.add(inschrijving);
    // BrpPersoonslijstBuilder.create(brpStapels);
    // }

    // @Test
    // public void testVerplichteStapelsGevuld() throws InputValidationException {
    // final List<BrpStapel<?>> brpStapels = new ArrayList<BrpStapel<?>>();
    // brpStapels.add(idNummers);
    // brpStapels.add(geslachtsAanduiding);
    // brpStapels.add(geboorte);
    // brpStapels.add(aanschrijving);
    // brpStapels.add(inschrijving);
    // brpStapels.add(adres);
    // BrpPersoonslijstBuilder.create(brpStapels);
    // }

    @Test
    public void testBuilderCompleet() {
        maakMinimaleBuilder();
    }

    //
    // @Test(expected = NullPointerException.class)
    // public void testAanschrijvingStapelNull() throws InputValidationException {
    // maakMinimaleBuilder().aanschrijvingStapel(null);
    // }
    //
    // @Test(expected = NullPointerException.class)
    // public void testAdresStapelNPE() throws InputValidationException {
    // maakMinimaleBuilder().adresStapel(null);
    // }

    private BrpPersoonslijstBuilder maakMinimaleBuilder() {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();

        builder.identificatienummersStapel(idNummers);
        builder.geslachtsaanduidingStapel(geslachtsAanduiding);
        builder.geboorteStapel(geboorte);
        builder.aanschrijvingStapel(aanschrijving);
        builder.inschrijvingStapel(inschrijving);
        builder.adresStapel(adres);

        final BrpStapel<BrpGeslachtsnaamcomponentInhoud> geslachtsnaamComp =
                new BrpStapel<BrpGeslachtsnaamcomponentInhoud>(
                        Arrays.asList(new BrpGroep<BrpGeslachtsnaamcomponentInhoud>(
                                new BrpGeslachtsnaamcomponentInhoud("van", ' ', "HorenZeggen", null, null, 1),
                                historie, null, null, null)));
        builder.geslachtsnaamcomponentStapels(Arrays.asList(geslachtsnaamComp));
        return builder;
    }
}
