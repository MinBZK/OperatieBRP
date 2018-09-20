/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ExpressietekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeLangAttribuut;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder voor unit-tests waar we een Element nodig hebben.
 */
public class TestElementBuilder {

    private NaamEnumeratiewaardeLangAttribuut naam;

    private NaamEnumeratiewaardeAttribuut elementNaam;

    private SoortElement soortElement;

    private Integer id;

    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;

    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    private ExpressietekstAttribuut expressietekstAttribuut;

    private Element elementGroep;

    private Element elementObjectType;

    private SoortElementAutorisatie autorisatie;

    /**
     */
    private TestElementBuilder() {
        // instantieren via de statische fabrieksmethode
    }

    public static TestElementBuilder maker() {
        return new TestElementBuilder();
    }

    public TestElementBuilder metNaam(final ElementEnum elementEnum) {
        this.naam = new NaamEnumeratiewaardeLangAttribuut(elementEnum.name());
        return this;
    }

    public TestElementBuilder metElementNaam(final String elementNaam) {
        this.elementNaam = new NaamEnumeratiewaardeAttribuut(elementNaam);
        return this;
    }

    public TestElementBuilder metId(final Integer id) {
        this.id = id;
        return this;
    }

    public TestElementBuilder metSoort(final SoortElement soort) {
        this.soortElement = soort;
        return this;
    }

    public TestElementBuilder metElementGroep(final Element elementGroep) {
        this.elementGroep = elementGroep;
        return this;
    }

    public TestElementBuilder metElementObjectType(final Element elementObjectType) {
        this.elementObjectType = elementObjectType;
        return this;
    }

    public TestElementBuilder metExpressie(final String expressie) {
        if(expressie != null) {
            expressietekstAttribuut = new ExpressietekstAttribuut(expressie);
        }
        return this;
    }

    public TestElementBuilder metDatumAanvangGeldigheid(final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        return this;
    }

    public TestElementBuilder metDatumEindeGeldigheid(final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
        return this;
    }

    public TestElementBuilder metAutorisatie(final SoortElementAutorisatie autorisatie) {
        this.autorisatie = autorisatie;
        return this;
    }

    public Element maak() {
        final Element element = new Element(naam, soortElement, elementNaam, elementObjectType, elementGroep, null, null,
            expressietekstAttribuut, autorisatie, null, null, null, null, null, datumAanvangGeldigheid, datumEindeGeldigheid);
        if (id != null) {
            ReflectionTestUtils.setField(element, "iD", id);
        }
        return element;
    }

}
