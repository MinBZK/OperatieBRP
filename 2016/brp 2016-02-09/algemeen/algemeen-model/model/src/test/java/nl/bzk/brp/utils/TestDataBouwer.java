/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.utils;

import java.util.UUID;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;


public final class TestDataBouwer {

    private TestDataBouwer() {

    }

    public static BerichtStuurgegevensGroepBericht getTestBerichtStuurgegevensGroepBericht() {
        final PartijAttribuut organisatie = StatischeObjecttypeBuilder.bouwPartij(012, "Belastingdienst");
        final SysteemNaamAttribuut applicatie = new SysteemNaamAttribuut("BGL");
        final ReferentienummerAttribuut referentienummer = new ReferentienummerAttribuut(UUID.randomUUID().toString());

        BerichtStuurgegevensGroepBericht stuurgegevensGroepBericht = new BerichtStuurgegevensGroepBericht();
        stuurgegevensGroepBericht.setZendendePartij(organisatie);
        stuurgegevensGroepBericht.setZendendeSysteem(applicatie);
        stuurgegevensGroepBericht.setReferentienummer(referentienummer);
        stuurgegevensGroepBericht.setCrossReferentienummer(referentienummer);
        stuurgegevensGroepBericht.setDatumTijdVerzending(DatumTijdAttribuut.nu());

        return stuurgegevensGroepBericht;
    }
}
