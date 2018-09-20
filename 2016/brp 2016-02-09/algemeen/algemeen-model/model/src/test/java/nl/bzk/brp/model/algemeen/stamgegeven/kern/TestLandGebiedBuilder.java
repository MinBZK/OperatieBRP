/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ISO31661Alpha2Attribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;

/**
 * Builder voor unittests waar we een LandGebied nodig hebben.
 */
public class TestLandGebiedBuilder {

    private LandGebiedCodeAttribuut landGebiedCodeAttribuut;
    private NaamEnumeratiewaardeAttribuut naamEnumeratiewaardeAttribuut;
    private ISO31661Alpha2Attribuut iso31661Alpha2Attribuut;
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    private TestLandGebiedBuilder() {
        // instantieren via de statische fabrieksmethode
    }

    public static TestLandGebiedBuilder maker() {
        return new TestLandGebiedBuilder();
    }

    public TestLandGebiedBuilder metNaam(final String naam) {
        this.naamEnumeratiewaardeAttribuut = new NaamEnumeratiewaardeAttribuut(naam);
        return this;
    }

    public TestLandGebiedBuilder metCode(final LandGebiedCodeAttribuut code) {
        this.landGebiedCodeAttribuut = code;
        return this;
    }

    public TestLandGebiedBuilder metCode(final int code) {
        return metCode(new LandGebiedCodeAttribuut(Integer.valueOf(code).shortValue()));
    }

    public TestLandGebiedBuilder metAlpha2Naam(final String naam) {
        this.iso31661Alpha2Attribuut = new ISO31661Alpha2Attribuut(naam);
        return this;
    }

    public TestLandGebiedBuilder metAanvangGeldigheid(final Integer datum) {
        this.datumAanvangGeldigheid = new DatumEvtDeelsOnbekendAttribuut(datum);
        return this;
    }

    public TestLandGebiedBuilder metEindeGeldigheid(final Integer datum) {
        this.datumEindeGeldigheid = new DatumEvtDeelsOnbekendAttribuut(datum);
        return this;
    }

    public LandGebied maak() {
        LandGebied landGebied = new LandGebied(
            landGebiedCodeAttribuut, naamEnumeratiewaardeAttribuut, iso31661Alpha2Attribuut,
            datumAanvangGeldigheid, datumEindeGeldigheid);
        return landGebied;
    }

}
