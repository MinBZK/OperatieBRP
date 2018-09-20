/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.dataaccess.repository.jpa.RegelRepositoryImpl;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.attribuuttype.brm.RegelCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test voor de {@link RegelRepositoryImpl} klasse.
 */
public class RegelRepositoryImplTest {

    private final RegelRepository regelRepository = new RegelRepositoryImpl();

    @Test
    public void testEnkeleRegelsOpParameters() {
        RegelParameters parameters;

        parameters = regelRepository.getRegelParametersVoorRegel(new RegelCodeAttribuut("BRBY9906"));
        Assert.assertNotNull(parameters);
        Assert.assertEquals(Regel.BRBY9906, parameters.getRegelCode());
        Assert.assertEquals(Regel.BRBY9906.getOmschrijving(), parameters.getMeldingTekst().getWaarde());
        Assert.assertNull(parameters.getDatabaseObject());

        parameters = regelRepository.getRegelParametersVoorRegel(new RegelCodeAttribuut("BRBY0521"));
        Assert.assertNotNull(parameters);
        Assert.assertEquals(Regel.BRBY0521, parameters.getRegelCode());
        Assert.assertEquals(Regel.BRBY0521.getOmschrijving(), parameters.getMeldingTekst().getWaarde());
        Assert.assertEquals(DatabaseObjectKern.PERSOON_ADRES__DATUM_AANVANG_ADRESHOUDING, parameters.getDatabaseObject());
    }

    @Test
    public void testParametersVoorNietBestaandeRegels() {
        RegelParameters parameters;

        parameters = regelRepository.getRegelParametersVoorRegel(null);
        Assert.assertNull(parameters);

        parameters = regelRepository.getRegelParametersVoorRegel(new RegelCodeAttribuut("ONBEKEND"));
        Assert.assertNull(parameters);
    }
}
