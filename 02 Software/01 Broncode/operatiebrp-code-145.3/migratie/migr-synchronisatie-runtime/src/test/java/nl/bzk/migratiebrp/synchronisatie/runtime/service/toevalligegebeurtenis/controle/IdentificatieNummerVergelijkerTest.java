/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class IdentificatieNummerVergelijkerTest {

    private final IdentificatieNummerVergelijker subject = new IdentificatieNummerVergelijker();

    @Test
    public void testVergelijkTrue() {
        final BrpToevalligeGebeurtenisPersoon persoon =
                new BrpToevalligeGebeurtenisPersoon(
                        new BrpString("1"),
                        new BrpString("1"),
                        new BrpPredicaatCode("J"),
                        new BrpString("Hij"),
                        null,
                        new BrpCharacter(' '),
                        new BrpAdellijkeTitelCode("R"),
                        new BrpString("geslachtsnaamstam"),
                        new BrpDatum(19700101, null),
                        new BrpGemeenteCode("0003"),
                        null,
                        BrpLandOfGebiedCode.NEDERLAND,
                        null,
                        BrpGeslachtsaanduidingCode.MAN);

        final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(new BrpString("1"), new BrpString("1"));
        Assert.assertTrue("objecten moeten gelijk zijn", subject.vergelijk(persoon, inhoud));
    }

    @Test
    public void testVergelijkFalse() {
        final BrpToevalligeGebeurtenisPersoon persoon =
                new BrpToevalligeGebeurtenisPersoon(
                        new BrpString("1"),
                        new BrpString("1"),
                        new BrpPredicaatCode("J"),
                        new BrpString("Hij"),
                        null,
                        new BrpCharacter(' '),
                        new BrpAdellijkeTitelCode("R"),
                        new BrpString("geslachtsnaamstam"),
                        new BrpDatum(19700101, null),
                        new BrpGemeenteCode("0003"),
                        null,
                        BrpLandOfGebiedCode.NEDERLAND,
                        null,
                        BrpGeslachtsaanduidingCode.MAN);

        final BrpIdentificatienummersInhoud inhoud = new BrpIdentificatienummersInhoud(new BrpString("2"), new BrpString("2"));
        Assert.assertFalse("objecten moeten ongelijk zijn", subject.vergelijk(persoon, inhoud));
    }
}
