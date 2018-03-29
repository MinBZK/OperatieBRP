/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisOverlijden;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.PersoonControle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Helper klasse voor het inhoudelijk controleren van een naamgeslacht van een persoon tegen de persoon uit de BRP.
 */
@RunWith(PowerMockRunner.class)
public final class PersoonControleTest {

    private static final BrpPartijCode DOEL_GEMEENTE = new BrpPartijCode("060001");
    private static final BrpString AKTE_NUMMER = new BrpString("1H");
    private static final BrpPartijCode REGISTER_GEMEENTE = new BrpPartijCode("059901");
    private static final Integer DATUM_AANVANG_GELDIGHEID = 20160205;
    private static final Integer ACTIE_ID = 23;
    private static final Integer DATUM_TIJD_REGISTRATIE = 20160101;
    private static final Integer DATUM_TIJD_VERVAL = 20160102;
    private static final BrpString ANUMMER = new BrpString("6845135120");
    private static final BrpString BSN = new BrpString("784651634");
    private static final BrpDatum GEBOORTEDATUM = new BrpDatum(19670503, null);
    private static final BrpGemeenteCode GEBOORTE_GEMEENTE = new BrpGemeenteCode("0301");
    private static final BrpLandOfGebiedCode NEDERLAND = new BrpLandOfGebiedCode("6030");
    private static final BrpString VOORNAMEN = new BrpString("Jan");
    private static final BrpString GESLACHTSNAAM = new BrpString("Janssen");
    private static final BrpGeslachtsaanduidingCode GESLACHT = new BrpGeslachtsaanduidingCode("M");

    private final IdentificatieNummerVergelijker identificatieNummerVergelijker = new IdentificatieNummerVergelijker();

    private final SamengesteldeNaamVergelijker samengesteldeNaamVergelijker = new SamengesteldeNaamVergelijker();

    private final GeboorteVergelijker geboorteVergelijker = new GeboorteVergelijker();

    private final GeslachtVergelijker geslachtVergelijker = new GeslachtVergelijker();

    private final PersoonControle subject = new PersoonControle(identificatieNummerVergelijker, samengesteldeNaamVergelijker, geboorteVergelijker, geslachtVergelijker);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGelijk() throws ControleException {
        final boolean identificatienummersGelijk = true;
        final boolean geboorteGelijk = true;
        final boolean samengesteldeNaamGelijk = true;
        final boolean geslachtGelijk = true;
        final BrpPersoonslijst persoon = maakBrpPersoonslijst(false);
        final BrpToevalligeGebeurtenis verzoek =
                maakBrpToevalligeGebeurtenisVerzoek(identificatienummersGelijk, geboorteGelijk, samengesteldeNaamGelijk, geslachtGelijk);
        subject.controleer(persoon, verzoek);
    }

    @Test(expected = ControleException.class)
    public void testOngelijkIdentificatienummers() throws ControleException {
        final boolean identificatienummersGelijk = false;
        final boolean geboorteGelijk = true;
        final boolean samengesteldeNaamGelijk = true;
        final boolean geslachtGelijk = true;
        final BrpPersoonslijst persoon = maakBrpPersoonslijst(false);
        final BrpToevalligeGebeurtenis verzoek =
                maakBrpToevalligeGebeurtenisVerzoek(identificatienummersGelijk, geboorteGelijk, samengesteldeNaamGelijk, geslachtGelijk);
        subject.controleer(persoon, verzoek);
    }

    @Test(expected = ControleException.class)
    public void testOngelijkGeboorte() throws ControleException {
        final boolean identificatienummersGelijk = true;
        final boolean geboorteGelijk = false;
        final boolean samengesteldeNaamGelijk = true;
        final boolean geslachtGelijk = true;
        final BrpPersoonslijst persoon = maakBrpPersoonslijst(false);
        final BrpToevalligeGebeurtenis verzoek =
                maakBrpToevalligeGebeurtenisVerzoek(identificatienummersGelijk, geboorteGelijk, samengesteldeNaamGelijk, geslachtGelijk);
        subject.controleer(persoon, verzoek);
    }

    @Test(expected = ControleException.class)
    public void testOngelijkSamengesteldeNaam() throws ControleException {
        final boolean identificatienummersGelijk = true;
        final boolean geboorteGelijk = true;
        final boolean samengesteldeNaamGelijk = false;
        final boolean geslachtGelijk = true;
        final BrpPersoonslijst persoon = maakBrpPersoonslijst(false);
        final BrpToevalligeGebeurtenis verzoek =
                maakBrpToevalligeGebeurtenisVerzoek(identificatienummersGelijk, geboorteGelijk, samengesteldeNaamGelijk, geslachtGelijk);
        subject.controleer(persoon, verzoek);
    }

    @Test(expected = ControleException.class)
    public void testOngelijkGeslacht() throws ControleException {
        final boolean identificatienummersGelijk = true;
        final boolean geboorteGelijk = true;
        final boolean samengesteldeNaamGelijk = true;
        final boolean geslachtGelijk = false;
        final BrpPersoonslijst persoon = maakBrpPersoonslijst(false);
        final BrpToevalligeGebeurtenis verzoek =
                maakBrpToevalligeGebeurtenisVerzoek(identificatienummersGelijk, geboorteGelijk, samengesteldeNaamGelijk, geslachtGelijk);
        subject.controleer(persoon, verzoek);
    }

    @Test(expected = ControleException.class)
    public void testLegePersoonslijst() throws ControleException {
        final BrpToevalligeGebeurtenis verzoek = maakBrpToevalligeGebeurtenisVerzoek(false, false, false, false);
        final BrpPersoonslijst persoon = maakBrpPersoonslijst(true);
        subject.controleer(persoon, verzoek);
    }

    @SuppressWarnings("unchecked")
    private BrpPersoonslijst maakBrpPersoonslijst(final boolean alleenVervallen) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder();
        final BrpHistorie historie =
                alleenVervallen ? BrpStapelHelper.his(DATUM_TIJD_VERVAL, DATUM_AANVANG_GELDIGHEID, DATUM_TIJD_REGISTRATIE, DATUM_TIJD_VERVAL)
                        : BrpStapelHelper.his(DATUM_AANVANG_GELDIGHEID);
        final BrpActie actieInhoud = BrpStapelHelper.act(ACTIE_ID, DATUM_TIJD_REGISTRATIE);
        builder.identificatienummersStapel(
                BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.identificatie(ANUMMER.getWaarde(), BSN.getWaarde()), historie, actieInhoud)));
        builder.geboorteStapel(
                BrpStapelHelper.stapel(
                        BrpStapelHelper.groep(
                                BrpStapelHelper.geboorte(GEBOORTEDATUM.getWaarde(), GEBOORTE_GEMEENTE.getWaarde()),
                                historie,
                                actieInhoud)));
        builder.samengesteldeNaamStapel(
                BrpStapelHelper.stapel(
                        BrpStapelHelper.groep(BrpStapelHelper.samengesteldnaam(VOORNAMEN.getWaarde(), GESLACHTSNAAM.getWaarde()), historie, actieInhoud)));
        builder.geslachtsaanduidingStapel(
                BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.geslacht(GESLACHT.getWaarde()), historie, actieInhoud)));
        return builder.build();
    }

    private BrpToevalligeGebeurtenis maakBrpToevalligeGebeurtenisVerzoek(
            final boolean identificatieNummersGelijk,
            final boolean geboorteGelijk,
            final boolean samengesteldeNaamGelijk,
            final boolean geslachtGelijk) {

        final BrpToevalligeGebeurtenisPersoon persoonVerzoek =
                new BrpToevalligeGebeurtenisPersoon(
                        identificatieNummersGelijk ? ANUMMER : new BrpString("1234567890"),
                        BSN,
                        null,
                        samengesteldeNaamGelijk ? VOORNAMEN : new BrpString("Henk"),
                        null,
                        null,
                        null,
                        GESLACHTSNAAM,
                        geboorteGelijk ? GEBOORTEDATUM : new BrpDatum(19571212, null),
                        GEBOORTE_GEMEENTE,
                        null,
                        NEDERLAND,
                        null,
                        geslachtGelijk ? GESLACHT : new BrpGeslachtsaanduidingCode("V"));

        return new BrpToevalligeGebeurtenis(
                DOEL_GEMEENTE,
                persoonVerzoek,
                null,
                null,
                null,
                null,
                new BrpToevalligeGebeurtenisOverlijden(
                        new BrpDatum(DATUM_AANVANG_GELDIGHEID, null),
                        new BrpGemeenteCode("0123"),
                        null,
                        NEDERLAND,
                        null),
                null,
                REGISTER_GEMEENTE,
                AKTE_NUMMER,
                new BrpDatum(DATUM_AANVANG_GELDIGHEID, null));
    }

}
