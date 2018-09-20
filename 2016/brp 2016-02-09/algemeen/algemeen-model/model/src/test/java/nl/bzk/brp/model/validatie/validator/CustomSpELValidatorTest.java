/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.validatie.constraint.CustomSpEL;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;

import org.hibernate.validator.util.annotationfactory.AnnotationDescriptor;
import org.hibernate.validator.util.annotationfactory.AnnotationFactory;
import org.junit.Assert;
import org.junit.Test;


public class CustomSpELValidatorTest {

    private final CustomSpELValidator customSpELValidator = new CustomSpELValidator();

    @Test
    public void testjeMetBRAL2033() {
        initialiseerValidatorMetWaarden("landGebied?.waarde?.code?.waarde == #LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT",
                "!#isBlank(gemeente?.waarde?.code?.waarde)");

        PersoonAdresStandaardGroepBericht adresStandaardGroepBericht = new PersoonAdresStandaardGroepBericht();
        adresStandaardGroepBericht.setLandGebied(StatischeObjecttypeBuilder.LAND_NEDERLAND);

        boolean result = customSpELValidator.isValid(adresStandaardGroepBericht, null);
        Assert.assertFalse(result);

        adresStandaardGroepBericht.setGemeente(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);
        result = customSpELValidator.isValid(adresStandaardGroepBericht, null);
        Assert.assertTrue(result);

        adresStandaardGroepBericht.setLandGebied(StatischeObjecttypeBuilder.LAND_BELGIE);
        adresStandaardGroepBericht.setGemeente(null);
        result = customSpELValidator.isValid(adresStandaardGroepBericht, null);
        Assert.assertTrue(result);
    }

    @Test
    public void testjeMetBRAL2027() {
        initialiseerValidatorMetWaarden("landGebied?.waarde?.code?.waarde == #LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT and "
                        + "redenWijziging?.waarde?.code?.waarde == #RedenWijzigingVerblijfCodeAttribuut.PERSOON_REDEN_WIJZIGING_ADRES_CODE_STRING",
                "!#isBlank(afgekorteNaamOpenbareRuimte?.waarde)");

        PersoonAdresStandaardGroepBericht adresStandaardGroepBericht = new PersoonAdresStandaardGroepBericht();
        adresStandaardGroepBericht.setLandGebied(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        adresStandaardGroepBericht.setRedenWijziging(new RedenWijzigingVerblijfAttribuut(new RedenWijzigingVerblijf(
                new RedenWijzigingVerblijfCodeAttribuut("P"), null)));

        boolean result = customSpELValidator.isValid(adresStandaardGroepBericht, null);
        Assert.assertFalse(result);

        adresStandaardGroepBericht.setLandGebied(null);
        adresStandaardGroepBericht.setRedenWijziging(new RedenWijzigingVerblijfAttribuut(new RedenWijzigingVerblijf(
                new RedenWijzigingVerblijfCodeAttribuut("P"), null)));
        result = customSpELValidator.isValid(adresStandaardGroepBericht, null);
        Assert.assertTrue(result);

        adresStandaardGroepBericht.setLandGebied(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        adresStandaardGroepBericht.setRedenWijziging(null);
        result = customSpELValidator.isValid(adresStandaardGroepBericht, null);
        Assert.assertTrue(result);

        adresStandaardGroepBericht.setLandGebied(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        adresStandaardGroepBericht.setRedenWijziging(new RedenWijzigingVerblijfAttribuut(new RedenWijzigingVerblijf(
                new RedenWijzigingVerblijfCodeAttribuut("P"), null)));
        adresStandaardGroepBericht.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimteAttribuut("ANOR"));
        result = customSpELValidator.isValid(adresStandaardGroepBericht, null);
        Assert.assertTrue(result);
    }

    private void initialiseerValidatorMetWaarden(final String wanneerVeldVoldoetAanRegel,
            final String danMoetVeldVoldoenAanRegel)
    {
        Map<String, Object> annotatieAttributen = new HashMap<String, Object>();
        annotatieAttributen.put("wanneerVeldVoldoetAanRegel", wanneerVeldVoldoetAanRegel);
        annotatieAttributen.put("danMoetVeldVoldoenAanRegel", danMoetVeldVoldoenAanRegel);
        annotatieAttributen.put("code", Regel.ALG0001);
        CustomSpEL annotation =
                AnnotationFactory.create(AnnotationDescriptor.getInstance(CustomSpEL.class, annotatieAttributen));
        this.customSpELValidator.initialize(annotation);
    }
}
