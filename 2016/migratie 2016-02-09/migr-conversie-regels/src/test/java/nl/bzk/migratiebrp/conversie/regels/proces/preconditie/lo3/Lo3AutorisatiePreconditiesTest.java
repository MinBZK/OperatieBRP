/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import org.junit.Test;

/**
 * Preconditie tests voor categorie 11: gezagsverhouding.
 */
public class Lo3AutorisatiePreconditiesTest extends AbstractPreconditieTest {

    private static final String AFNEMER = "Belastingdienst";
    @Inject
    private Lo3AutorisatiePrecondities precondities;

    @Test
    public void testHappyAutorisatie() {

        final Lo3AutorisatieInhoud autorisatieInhoud = new Lo3AutorisatieInhoud();
        autorisatieInhoud.setAfnemersindicatie(841230);
        autorisatieInhoud.setAfnemernaam(AFNEMER);
        autorisatieInhoud.setVerstrekkingsbeperking(1);
        autorisatieInhoud.setDatumIngang(new Lo3Datum(20130101));
        final Lo3Herkomst herkomst = Lo3StapelHelper.lo3Her(35, 1, 1);
        final Lo3Historie historie = Lo3StapelHelper.lo3His(20130101);
        final Lo3Autorisatie autorisatie = new Lo3Autorisatie(Lo3StapelHelper.lo3Stapel(new Lo3Categorie<>(autorisatieInhoud, null, historie, herkomst)));

        precondities.controleerAutorisatie(autorisatie);

        assertAantalErrors(0);
    }

    @Test
    public void testAutorisatiePreconditieAUT001() {

        final Lo3AutorisatieInhoud autorisatieInhoud = new Lo3AutorisatieInhoud();
        autorisatieInhoud.setAfnemernaam(AFNEMER);
        autorisatieInhoud.setVerstrekkingsbeperking(1);
        autorisatieInhoud.setDatumIngang(new Lo3Datum(20130101));

        final Lo3Herkomst herkomst = Lo3StapelHelper.lo3Her(35, 1, 1);
        final Lo3Historie historie = Lo3StapelHelper.lo3His(20130101);
        final Lo3Autorisatie autorisatie = new Lo3Autorisatie(Lo3StapelHelper.lo3Stapel(new Lo3Categorie<>(autorisatieInhoud, null, historie, herkomst)));

        precondities.controleerAutorisatie(autorisatie);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.AUT001, 1);
    }

    @Test
    public void testAutorisatiePreconditieAUT002() {

        final Lo3AutorisatieInhoud autorisatieInhoud = new Lo3AutorisatieInhoud();
        autorisatieInhoud.setAfnemersindicatie(841230);
        autorisatieInhoud.setAfnemernaam(AFNEMER);
        autorisatieInhoud.setVerstrekkingsbeperking(1);
        autorisatieInhoud.setDatumIngang(new Lo3Datum(20000400));

        final Lo3Herkomst herkomst = Lo3StapelHelper.lo3Her(35, 1, 1);
        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(20000400), null);
        final Lo3Autorisatie autorisatie = new Lo3Autorisatie(Lo3StapelHelper.lo3Stapel(new Lo3Categorie<>(autorisatieInhoud, null, historie, herkomst)));

        precondities.controleerAutorisatie(autorisatie);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.AUT002, 1);
    }

    @Test
    public void testAutorisatiePreconditieAUT003() {

        final Lo3AutorisatieInhoud autorisatieInhoud = new Lo3AutorisatieInhoud();
        autorisatieInhoud.setAfnemersindicatie(841230);
        autorisatieInhoud.setAfnemernaam(AFNEMER);
        autorisatieInhoud.setVerstrekkingsbeperking(1);
        final Lo3Herkomst herkomst = Lo3StapelHelper.lo3Her(35, 1, 1);
        final Lo3Historie historie = Lo3StapelHelper.lo3His(201301);
        final Lo3Autorisatie autorisatie = new Lo3Autorisatie(Lo3StapelHelper.lo3Stapel(new Lo3Categorie<>(autorisatieInhoud, null, historie, herkomst)));

        precondities.controleerAutorisatie(autorisatie);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.AUT003, 1);
    }

    @Test
    public void testAutorisatiePreconditieAUT003DeelOnbekend() {

        final Lo3AutorisatieInhoud autorisatieInhoud = new Lo3AutorisatieInhoud();
        autorisatieInhoud.setAfnemersindicatie(841230);
        autorisatieInhoud.setAfnemernaam(AFNEMER);
        autorisatieInhoud.setVerstrekkingsbeperking(1);
        final Lo3Herkomst herkomst = Lo3StapelHelper.lo3Her(35, 1, 1);
        final Lo3Historie historie = Lo3StapelHelper.lo3His(1301);
        final Lo3Autorisatie autorisatie = new Lo3Autorisatie(Lo3StapelHelper.lo3Stapel(new Lo3Categorie<>(autorisatieInhoud, null, historie, herkomst)));

        precondities.controleerAutorisatie(autorisatie);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.AUT003, 1);
    }

    @Test
    public void testAutorisatiePreconditieAUT004() {

        final Lo3AutorisatieInhoud autorisatieInhoud = new Lo3AutorisatieInhoud();
        autorisatieInhoud.setAfnemersindicatie(841230);
        autorisatieInhoud.setVerstrekkingsbeperking(1);
        autorisatieInhoud.setDatumIngang(new Lo3Datum(20130101));
        final Lo3Herkomst herkomst = Lo3StapelHelper.lo3Her(35, 1, 1);
        final Lo3Historie historie = Lo3StapelHelper.lo3His(20130101);
        final Lo3Autorisatie autorisatie = new Lo3Autorisatie(Lo3StapelHelper.lo3Stapel(new Lo3Categorie<>(autorisatieInhoud, null, historie, herkomst)));

        precondities.controleerAutorisatie(autorisatie);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.AUT004, 1);
    }

    @Test
    public void testAutorisatiePreconditieAUT005() {

        final Lo3AutorisatieInhoud autorisatieInhoud = new Lo3AutorisatieInhoud();
        autorisatieInhoud.setAfnemersindicatie(841230);
        autorisatieInhoud.setAfnemernaam(AFNEMER);
        autorisatieInhoud.setDatumIngang(new Lo3Datum(20130101));
        final Lo3Herkomst herkomst = Lo3StapelHelper.lo3Her(35, 1, 1);
        final Lo3Historie historie = Lo3StapelHelper.lo3His(20130101);
        final Lo3Autorisatie autorisatie = new Lo3Autorisatie(Lo3StapelHelper.lo3Stapel(new Lo3Categorie<>(autorisatieInhoud, null, historie, herkomst)));

        precondities.controleerAutorisatie(autorisatie);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.AUT005, 1);
    }

    @Test
    public void testAutorisatiePreconditieAUT006() {

        final Lo3AutorisatieInhoud autorisatieInhoud = new Lo3AutorisatieInhoud();
        autorisatieInhoud.setAfnemersindicatie(841230);
        autorisatieInhoud.setAfnemernaam(AFNEMER);
        autorisatieInhoud.setVerstrekkingsbeperking(1);
        autorisatieInhoud.setDatumIngang(new Lo3Datum(19900102));
        autorisatieInhoud.setDatumEinde(new Lo3Datum(19900101));
        final Lo3Herkomst herkomst = Lo3StapelHelper.lo3Her(35, 1, 1);
        final Lo3Historie historie = Lo3StapelHelper.lo3His(19900102);

        final Lo3Categorie<Lo3AutorisatieInhoud> categorie = new Lo3Categorie<>(autorisatieInhoud, null, historie, herkomst);

        final Lo3Autorisatie autorisatie = new Lo3Autorisatie(Lo3StapelHelper.lo3Stapel(categorie));

        precondities.controleerAutorisatie(autorisatie);

        assertAantalErrors(1);
        assertSoortMeldingCode(SoortMeldingCode.AUT006, 1);
    }

    @Test
    public void testAutorisatieMeerderePrecondities() {

        final Lo3AutorisatieInhoud autorisatieInhoud = new Lo3AutorisatieInhoud();
        autorisatieInhoud.setAfnemersindicatie(841230);
        autorisatieInhoud.setDatumIngang(new Lo3Datum(20130101));
        autorisatieInhoud.setDatumEinde(new Lo3Datum(20120101));
        final Lo3Herkomst herkomst = Lo3StapelHelper.lo3Her(35, 1, 1);
        final Lo3Historie historie = Lo3StapelHelper.lo3His(20130101);

        final Lo3Categorie<Lo3AutorisatieInhoud> categorie = new Lo3Categorie<>(autorisatieInhoud, null, historie, herkomst);

        final Lo3Autorisatie autorisatie = new Lo3Autorisatie(Lo3StapelHelper.lo3Stapel(categorie));

        precondities.controleerAutorisatie(autorisatie);

        assertAantalErrors(3);
        assertSoortMeldingCode(SoortMeldingCode.AUT004, 1);
        assertSoortMeldingCode(SoortMeldingCode.AUT005, 1);
        assertSoortMeldingCode(SoortMeldingCode.AUT006, 1);
    }
}
