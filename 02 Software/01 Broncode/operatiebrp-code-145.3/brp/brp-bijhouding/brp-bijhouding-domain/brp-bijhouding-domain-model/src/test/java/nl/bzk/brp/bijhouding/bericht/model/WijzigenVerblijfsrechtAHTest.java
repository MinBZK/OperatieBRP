/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor {@link AdministratieveHandelingElementSoort#WIJZIGING_VERBLIJFSRECHT}.
 */
public class WijzigenVerblijfsrechtAHTest extends AbstractElementTest {

    private BijhoudingVerzoekBericht bericht;
    private ElementBuilder elementBuilder;
    private Map<String, String> attributen;
    private BijhoudingPersoon persoon;

    @Before
    public void setUpTests() throws OngeldigeObjectSleutelException {
        bericht = mock(BijhoudingVerzoekBericht.class);
        elementBuilder = new ElementBuilder();
        attributen = new LinkedHashMap<>();
        attributen.put(OBJECTTYPE_ATTRIBUUT.toString(), "objecttype");
        attributen.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "ci_test");

        final Partij partij = new Partij("partij", "000001");
        partij.addPartijRol(new PartijRol(partij, Rol.BIJHOUDINGSORGAAN_COLLEGE));

        persoon = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));
        persoon.setId(1L);

        final PersoonBijhoudingHistorie
                bijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        bijhoudingHistorie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        persoon.addPersoonBijhoudingHistorie(bijhoudingHistorie);

        when(bericht.getStuurgegevens()).thenReturn(getStuurgegevensElement());

        when(bericht.getEntiteitVoorObjectSleutel(any(Class.class), anyString())).thenReturn(persoon);
        when(bericht.getDatumOntvangst()).thenReturn(new DatumElement(DatumUtil.vandaag()));
        when(bericht.getZendendePartij()).thenReturn(partij);

    }


    @Test
    public void testR1610RegistratiePartijIsNietBijhoudingspartij() {
        final Partij anderePartij = new Partij("Andere Partij", "000002");
        anderePartij.addPartijRol(new PartijRol(anderePartij, Rol.BIJHOUDINGSORGAAN_COLLEGE));
        when(bericht.getZendendePartij()).thenReturn(anderePartij);
        when(getDynamischeStamtabelRepository().getPartijByCode("053001")).thenReturn(anderePartij);
        final RegistratieVerblijfsrechtActieElement registratieActie = maakRegistratieVerblijfsrechtActieElement();

        final AdministratieveHandelingElement
                administratieveHandelingElement =
                maakAdministratieveHandelingElement(Collections.singletonList(registratieActie));

        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1610, meldingen.get(0).getRegel());
    }

    @Test
    public void testR1610RegistratiePartijIsNietBijhoudingspartijAndereRol() {
        final Partij andereRolPartij = new Partij("partij", "000002");
        andereRolPartij.addPartijRol(new PartijRol(andereRolPartij, Rol.BIJHOUDINGSORGAAN_MINISTER));
        when(bericht.getZendendePartij()).thenReturn(andereRolPartij);
        when(getDynamischeStamtabelRepository().getPartijByCode("053001")).thenReturn(andereRolPartij);

        final RegistratieVerblijfsrechtActieElement registratieActie = maakRegistratieVerblijfsrechtActieElement();

        final AdministratieveHandelingElement
                administratieveHandelingElement =
                maakAdministratieveHandelingElement(Collections.singletonList(registratieActie));

        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    private RegistratieVerblijfsrechtActieElement maakRegistratieVerblijfsrechtActieElement() {
        final ElementBuilder.PersoonParameters registreerPersoonParameters = new ElementBuilder.PersoonParameters();
        registreerPersoonParameters.verblijfsrecht(maakVerblijfsrecht());

        PersoonGegevensElement registreerPersoon = elementBuilder.maakPersoonGegevensElement("ci_persoon_1", "object", null, registreerPersoonParameters);

        RegistratieVerblijfsrechtActieElement
                registreerActie =
                new RegistratieVerblijfsrechtActieElement(attributen, null, null, Collections.emptyList(), registreerPersoon);
        registreerPersoon.setVerzoekBericht(bericht);
        registreerActie.setVerzoekBericht(bericht);
        return registreerActie;
    }

    private AdministratieveHandelingElement maakAdministratieveHandelingElement(final List<ActieElement> acties) {
        final AdministratieveHandelingElement administratieveHandelingElement =
                elementBuilder
                        .maakAdministratieveHandelingElement("com_ah", new ElementBuilder.AdministratieveHandelingParameters().acties(acties)
                                .soort(AdministratieveHandelingElementSoort.WIJZIGING_VERBLIJFSRECHT).partijCode("053001")
                                .bronnen(Collections.emptyList()));

        administratieveHandelingElement.setVerzoekBericht(bericht);
        when(bericht.getAdministratieveHandeling()).thenReturn(administratieveHandelingElement);
        return administratieveHandelingElement;
    }


    private VerblijfsrechtElement maakVerblijfsrecht() {
        return elementBuilder.maakVerblijfsrechtElement("comm_id",
                new ElementBuilder.VerblijfsrechtParameters().aanduidingCode("21").datumAanvang(20160101).datumMededeling(20160101));
    }
}
