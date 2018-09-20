/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestAangeverBuilder;
import org.junit.Assert;
import org.junit.Test;


public class PersoonMigratieBerichtConstraintTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Validatie voor persoon migratie groep:
     * BRAL1118: De aangever adreshouding is verplicht bij reden adreswijziging P, maar niet toegestaan bij geen of een
     * andere reden
     * BRAL2038: Binnen de buitenlandse adresregels is regel twee verplicht.
     * BRAL2039: Buitenlandse adresregel 1 is verplicht als regel 3 is ingevuld.
     * BRAL9027: Buitenlandse adresregel mag niet uit meer dan 35 karakters bestaan. Zie PersoonBerichtConstraintTest
     */

    @Test
    public void testBRAL2038AlsAnyRegel1Tot6IngevuldDanRegel2Ook() {
        Set<ConstraintViolation<PersoonMigratieGroepBericht>> cv;
        PersoonMigratieGroepBericht groep = new PersoonMigratieGroepBericht();

        // alles leeg is OK
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(0, cv.size());

        // Alleen 2 is OK
        cv = validator.validate(groep, Default.class);
        groep.setBuitenlandsAdresRegel2Migratie(new AdresregelAttribuut("regel2"));
        Assert.assertEquals(0, cv.size());

        // Alles gevuld OK
        groep.setBuitenlandsAdresRegel1Migratie(new AdresregelAttribuut("regel1"));
        groep.setBuitenlandsAdresRegel2Migratie(new AdresregelAttribuut("regel2"));
        groep.setBuitenlandsAdresRegel3Migratie(new AdresregelAttribuut("regel3"));
        groep.setBuitenlandsAdresRegel4Migratie(new AdresregelAttribuut("regel4"));
        groep.setBuitenlandsAdresRegel5Migratie(new AdresregelAttribuut("regel5"));
        groep.setBuitenlandsAdresRegel6Migratie(new AdresregelAttribuut("regel6"));
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(0, cv.size());

        // Alleen 2,4,6 is OK
        groep.setBuitenlandsAdresRegel2Migratie(new AdresregelAttribuut("regel2"));
        groep.setBuitenlandsAdresRegel4Migratie(new AdresregelAttribuut("regel4"));
        groep.setBuitenlandsAdresRegel6Migratie(new AdresregelAttribuut("regel6"));
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(0, cv.size());

        // Alleen 4 is FOUT
        groep = new PersoonMigratieGroepBericht();
        groep.setBuitenlandsAdresRegel4Migratie(new AdresregelAttribuut("regel4"));
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(1, cv.size());

        // Alleen 5 is FOUT
        groep = new PersoonMigratieGroepBericht();
        groep.setBuitenlandsAdresRegel5Migratie(new AdresregelAttribuut("regel5"));
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(1, cv.size());

        // Alleen 6 is FOUT
        groep = new PersoonMigratieGroepBericht();
        groep.setBuitenlandsAdresRegel6Migratie(new AdresregelAttribuut("regel6"));
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(1, cv.size());

        // Alleen 1 is FOUT
        groep = new PersoonMigratieGroepBericht();
        groep.setBuitenlandsAdresRegel1Migratie(new AdresregelAttribuut("regel1"));
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(1, cv.size());

        // Alleen 1 is 2x FOUT (alleen 1 + alleen 3 zonder 1)
        groep = new PersoonMigratieGroepBericht();
        groep.setBuitenlandsAdresRegel3Migratie(new AdresregelAttribuut("regel3"));
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(2, cv.size());

        // Alleen 4,6 = 2x FOUT
        groep = new PersoonMigratieGroepBericht();
        groep.setBuitenlandsAdresRegel4Migratie(new AdresregelAttribuut("regel4"));
        groep.setBuitenlandsAdresRegel6Migratie(new AdresregelAttribuut("regel6"));
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(2, cv.size());

    }

    @Test
    public void testBRAL2039Regel3MagAlsRegel1OokIngevuldIs() {
        Set<ConstraintViolation<PersoonMigratieGroepBericht>> cv;
        PersoonMigratieGroepBericht groep = new PersoonMigratieGroepBericht();
        // 2 is altijd verplicht (BRAL2038)
        groep.setBuitenlandsAdresRegel2Migratie(new AdresregelAttribuut("regel2"));

        // 1 en 3 leeg is OK
        groep.setBuitenlandsAdresRegel1Migratie(null);
        groep.setBuitenlandsAdresRegel3Migratie(null);
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(0, cv.size());

        groep.setBuitenlandsAdresRegel1Migratie(new AdresregelAttribuut("regel1"));
        groep.setBuitenlandsAdresRegel3Migratie(new AdresregelAttribuut("regel3"));
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(0, cv.size());

        // 1 alleen MAG
        groep.setBuitenlandsAdresRegel1Migratie(new AdresregelAttribuut("regel1"));
        groep.setBuitenlandsAdresRegel3Migratie(null);
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(0, cv.size());

        // 3 alleen mag niet bestaan
        groep.setBuitenlandsAdresRegel1Migratie(null);
        groep.setBuitenlandsAdresRegel3Migratie(new AdresregelAttribuut("regel3"));
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(1, cv.size());
    }

    @Test
    public void testBRAL1118AangeverVerplichtBijRedenAdresWijzigingP() {
        Set<ConstraintViolation<PersoonMigratieGroepBericht>> cv;
        PersoonMigratieGroepBericht groep = new PersoonMigratieGroepBericht();

        // fout scenatio: Aangever adreshouding verplicht bij reden adreswijziging P
        groep.setAangeverMigratie(null);
        groep.setRedenWijzigingMigratie(new RedenWijzigingVerblijfAttribuut(new RedenWijzigingVerblijf(
                new RedenWijzigingVerblijfCodeAttribuut("P"),
                new NaamEnumeratiewaardeAttribuut("Aangifte door persoon"))));
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(1, cv.size());

        // fout scenario: Aangever adreshouding niet toegestaan bij andere reden adreswijziging dan P
        groep.setAangeverMigratie(new AangeverAttribuut(TestAangeverBuilder.maker().metCode("H").metNaam("Hoofd instelling").maak()));
        groep.setRedenWijzigingMigratie(new RedenWijzigingVerblijfAttribuut(new RedenWijzigingVerblijf(
                new RedenWijzigingVerblijfCodeAttribuut("A"), new NaamEnumeratiewaardeAttribuut("Ambtshalve"))));
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(1, cv.size());

        // happy flow: Aangever adreshouding niet toegestaan bij andere reden adreswijziging dan P
        groep.setAangeverMigratie(null);
        groep.setRedenWijzigingMigratie(new RedenWijzigingVerblijfAttribuut(new RedenWijzigingVerblijf(
                new RedenWijzigingVerblijfCodeAttribuut("A"), new NaamEnumeratiewaardeAttribuut("Ambtshalve"))));
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(0, cv.size());

        // Happy flown : Aangever ingevuld en reden adreswijziging = P
        groep.setAangeverMigratie(new AangeverAttribuut(TestAangeverBuilder.maker().metCode("H").metNaam("Hoofd instelling").maak()));
        groep.setRedenWijzigingMigratie(new RedenWijzigingVerblijfAttribuut(new RedenWijzigingVerblijf(
                new RedenWijzigingVerblijfCodeAttribuut("P"),
                new NaamEnumeratiewaardeAttribuut("Aangifte door persoon"))));
        cv = validator.validate(groep, Default.class);
        Assert.assertEquals(0, cv.size());
    }
}
