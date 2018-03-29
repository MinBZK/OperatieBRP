/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Testen voor Regel R2486 in {@link AdministratieveHandelingElement}.
 */
public class AdministratieveHandelingElementR2486Test extends AbstractElementTest {

    private static final String OBJECTSLEUTEL_1 = "1";
    private static final String OBJECTSLEUTEL_2 = "2";

    private ElementBuilder builder;
    private BijhoudingVerzoekBericht bericht;
    private BijhoudingRelatie relatieEntiteit1;
    private BijhoudingRelatie relatieEntiteit2;
    private Partij partij;

    @Before
    public void setup() {
        builder = new ElementBuilder();
        bericht = mock(BijhoudingVerzoekBericht.class);
        relatieEntiteit1 = BijhoudingRelatie.decorate(new Relatie(SoortRelatie.HUWELIJK));
        relatieEntiteit1.setId(1L);
        relatieEntiteit2 = BijhoudingRelatie.decorate(new Relatie(SoortRelatie.HUWELIJK));
        relatieEntiteit2.setId(2L);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, OBJECTSLEUTEL_1)).thenReturn(relatieEntiteit1);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, OBJECTSLEUTEL_2)).thenReturn(relatieEntiteit2);
        partij = new Partij("test", "123456");
        when(bericht.getZendendePartij()).thenReturn(partij);
        when(getDynamischeStamtabelRepository().getPartijByCode(partij.getCode())).thenReturn(partij);
        final StuurgegevensElement
                stuurgegevensElement =
                builder.maakStuurgegevensElement("CI_stuurgegevens_1",
                        new ElementBuilder.StuurgegevensParameters().zendendePartij(partij.getCode()).zendendeSysteem("test").referentienummer("ref1")
                                .tijdstipVerzending(new DatumTijdElement(
                                        DatumUtil.nuAlsZonedDateTime()).toString()));
        when(bericht.getStuurgegevens()).thenReturn(stuurgegevensElement);
        when(bericht.getDatumOntvangst()).thenReturn(new DatumElement(20010101));
    }

    @Test
    public void testVerschillendeRelaties() {
        //setup
        final AdministratieveHandelingElement administratieveHandelingElement = maakAdministratieveHandelingElement(OBJECTSLEUTEL_1, OBJECTSLEUTEL_2);
        //execute
        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        //validate
        controleerRegels(meldingen, Regel.R2486);
    }

    @Test
    public void testGelijkeRelaties() {
        //setup
        final AdministratieveHandelingElement administratieveHandelingElement = maakAdministratieveHandelingElement(OBJECTSLEUTEL_1, OBJECTSLEUTEL_1);
        //execute
        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        //validate
        controleerRegels(meldingen);
    }

    private AdministratieveHandelingElement maakAdministratieveHandelingElement(final String objectSleutelRelatie1, final String objectSleutelRelatie2) {
        final List<ActieElement> acties = new ArrayList<>();
        final RelatieElement relatieElement1 = maakRelatieElement(objectSleutelRelatie1, "1");
        final CorrectieRegistratieRelatieActieElement
                correctieRegistratieRelatie =
                builder.maakCorrectieRegistratieRelatieActieElement("CI_correctie_registratie_relatie_1", relatieElement1);
        final RelatieElement relatieElement2 = maakRelatieElement(objectSleutelRelatie2, "2");
        final CorrectieVervalRelatieActieElement
                correctieVervalRelatie =
                builder.maakCorrectieVervalRelatieActieElement("CI_correctie_verval_relatie_2", relatieElement2, "1", null);
        acties.add(correctieRegistratieRelatie);
        acties.add(correctieVervalRelatie);
        final AdministratieveHandelingElement administratieveHandelingElement = builder.maakAdministratieveHandelingElement("CI_ah_1", new ElementBuilder
                .AdministratieveHandelingParameters().acties(acties).partijCode(partij.getCode())
                .soort(AdministratieveHandelingElementSoort.CORRECTIE_HUWELIJK));
        administratieveHandelingElement.setVerzoekBericht(bericht);
        builder.initialiseerVerzoekBericht(bericht);
        return administratieveHandelingElement;
    }

    private RelatieElement maakRelatieElement(final String objectSleutelRelatie, final String id) {
        final RelatieGroepElement
                relatieGroep =
                builder.maakRelatieGroepElement("CI_relatie_" + id, new ElementBuilder.RelatieGroepParameters().datumAanvang(20000101));
        final HuwelijkElement
                result =
                builder.maakHuwelijkElement("CI_huwelijk_" + id, objectSleutelRelatie, relatieGroep, Collections.emptyList());
        result.setVerzoekBericht(bericht);
        return result;
    }
}
