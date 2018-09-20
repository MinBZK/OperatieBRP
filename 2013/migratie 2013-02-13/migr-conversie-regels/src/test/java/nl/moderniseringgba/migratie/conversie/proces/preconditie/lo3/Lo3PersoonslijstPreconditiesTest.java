/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;

import org.junit.Test;

public class Lo3PersoonslijstPreconditiesTest extends AbstractPreconditieTest {

    protected static final Lo3Historie GEBOORTE_HISTORIE = Lo3StapelHelper.lo3His(19770101);
    protected static final Lo3Documentatie GEBOORTE_AKTE = Lo3StapelHelper.lo3Akt(1);

    @Inject
    private Lo3PersoonslijstPrecondities precondities;

    @Test
    public void testHappy() {

        precondities.controleerPersoonslijst(builder().build());
        assertAantalErrors(0);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Test
    public void testContr001() {
        final Lo3Persoonslijst pl = builder().persoonStapel(null).build();

        precondities.controleerPersoonslijst(pl);
        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE047);
    }

    @Test
    public void testContr004() {
        final Lo3Persoonslijst pl = builder().inschrijvingStapel(null).build();

        precondities.controleerPersoonslijst(pl);
        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE032);
    }

    @Test
    public void testContr005() {
        final Lo3Persoonslijst pl = builder().verblijfplaatsStapel(null).build();

        precondities.controleerPersoonslijst(pl);
        assertAantalErrors(1);
        assertPreconditie(Precondities.PRE033);
    }

    public Lo3PersoonslijstBuilder builder() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();
        builder.persoonStapel(buildPersoonstapel());
        builder.ouder1Stapel(buildOuder1Stapel());
        builder.ouder2Stapel(buildOuder2Stapel());
        builder.inschrijvingStapel(buildInschrijvingStapel());
        builder.verblijfplaatsStapel(buildVerblijfplaatsStapel());

        return builder;
    }

    //@formatter:off - Geen formatting voor builders.
    
    private Lo3Stapel<Lo3PersoonInhoud> buildPersoonstapel() {
        return buildPersoonstapel("6030");
    }

    @SuppressWarnings("unchecked")
    private Lo3Stapel<Lo3PersoonInhoud> buildPersoonstapel(final String landcode) {
        return Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Persoon(3450924321L, "Scarlet", "Simpspoon", 19770101, "0518", landcode, "V"), GEBOORTE_HISTORIE, GEBOORTE_AKTE, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 0, 0)));
    }

    @SuppressWarnings("unchecked")
    private Lo3Stapel<Lo3OuderInhoud> buildOuder1Stapel() {
        return Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(3450924321L, "Jessica", "Geller", 19170101, "0518", "6030", "V", 19770101), GEBOORTE_HISTORIE, GEBOORTE_AKTE, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0)));
    }

    @SuppressWarnings("unchecked")
    private Lo3Stapel<Lo3OuderInhoud> buildOuder2Stapel() {
        return Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Ouder(3450924321L, "Jaap", "Jansen", 19160101, "0518", "6030", "M", 19770101), GEBOORTE_HISTORIE, GEBOORTE_AKTE, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 0, 0)));
    }

    @SuppressWarnings("unchecked")
    private Lo3Stapel<Lo3InschrijvingInhoud> buildInschrijvingStapel() {
        return Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Inschrijving(null,  null, null, 19770202, "0518", 0, 1, 19770102123014000L , true), Lo3Historie.NULL_HISTORIE, null, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0)));
    }

    @SuppressWarnings("unchecked")
    private Lo3Stapel<Lo3VerblijfplaatsInhoud> buildVerblijfplaatsStapel() {
        return Lo3StapelHelper.lo3Stapel(
                Lo3StapelHelper.lo3Cat(
                        Lo3StapelHelper.lo3Verblijfplaats("0518", 19770101, 19770101, "Straat", 14, "9654AA", "O"), GEBOORTE_HISTORIE, null, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_08, 0, 0)));
    }

}
