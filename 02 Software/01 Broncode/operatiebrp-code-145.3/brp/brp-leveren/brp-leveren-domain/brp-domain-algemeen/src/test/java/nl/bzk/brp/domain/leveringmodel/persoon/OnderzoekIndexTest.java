/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;
import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;
import static nl.bzk.brp.domain.element.ElementHelper.getObjectElement;

import com.google.common.collect.Multimap;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class OnderzoekIndexTest {
    private static final Long VOORKOMEN_SLEUTEL = 13L;
    private static final Long OBJECT_SLEUTEL = 14L;
    private static final Long GEGEVEN_OBJECT_SLEUTEL = 7L;
    private static final Long OBJECT_SLEUTEL_ONDERZOEK = 100L;

    @Test
    public void testAttribuutInOnderzoekMetVoorkomenSleutel() {
        //bouw testgegevens
        final String naamVanElement = (Element.PERSOON_ADRES_HUISNUMMER.getNaam());
        final MetaObject persoon = maakDummyPersoonObject(VOORKOMEN_SLEUTEL, OBJECT_SLEUTEL, VOORKOMEN_SLEUTEL, GEGEVEN_OBJECT_SLEUTEL, true,
                naamVanElement);
        final OnderzoekIndex onderzoekIndex = new OnderzoekIndex(new ModelIndex(persoon));
        final Multimap<Onderzoekbundel, MetaModel> modelObjectenInOnderzoek = onderzoekIndex.getGegevensInOnderzoek();
        Assert.assertEquals(2, modelObjectenInOnderzoek.values().size());

        final MetaAttribuut metaAttribuut = (MetaAttribuut) modelObjectenInOnderzoek.values().iterator().next();
        Assert.assertEquals(naamVanElement, metaAttribuut.getAttribuutElement().getNaam());
        Assert.assertEquals(VOORKOMEN_SLEUTEL.longValue(), metaAttribuut.getParentRecord().getVoorkomensleutel());
    }

    @Test
    public void testOnderzoekAttribuutElementMetOnbekendeVoorkomenSleutel() {
        //bouw testgegevens
        final String elementNaam = Element.PERSOON_ADRES_HUISNUMMER.getNaam();
        final MetaObject persoon = maakDummyPersoonObject(VOORKOMEN_SLEUTEL, OBJECT_SLEUTEL, 99L, GEGEVEN_OBJECT_SLEUTEL, true, elementNaam);
        final OnderzoekIndex onderzoekIndex = new OnderzoekIndex(new ModelIndex(persoon));
        final Multimap<Onderzoekbundel, MetaModel> modelObjectenInOnderzoek = onderzoekIndex.getGegevensInOnderzoek();
        //assert
        Assert.assertEquals(1, modelObjectenInOnderzoek.values().size());
    }

    @Test
    public void testOnderzoekAttribuutElementMetObjectSleutel() {
        //bouw testgegevens
        final String elementNaam = Element.PERSOON_ADRES_HUISNUMMER.getNaam();
        final MetaObject persoon = maakDummyPersoonObject(VOORKOMEN_SLEUTEL, OBJECT_SLEUTEL, OBJECT_SLEUTEL, GEGEVEN_OBJECT_SLEUTEL, false, elementNaam);
        final OnderzoekIndex onderzoekIndex = new OnderzoekIndex(new ModelIndex(persoon));
        final Multimap<Onderzoekbundel, MetaModel> modelObjectenInOnderzoek = onderzoekIndex.getGegevensInOnderzoek();
        //assert
        Assert.assertEquals(2, modelObjectenInOnderzoek.values().size());
    }

    @Test
    public void testOnderzoekGroepElementMetObjectSleutel() {
        //bouw testgegevens
        final String elementNaam = Element.PERSOON_ADRES_STANDAARD.getNaam();
        final MetaObject persoon = maakDummyPersoonObject(VOORKOMEN_SLEUTEL, OBJECT_SLEUTEL, OBJECT_SLEUTEL, GEGEVEN_OBJECT_SLEUTEL, false, elementNaam);
        final OnderzoekIndex onderzoekIndex = new OnderzoekIndex(new ModelIndex(persoon));
        final Multimap<Onderzoekbundel, MetaModel> modelObjectenInOnderzoek = onderzoekIndex.getGegevensInOnderzoek();
        //assert
        Assert.assertEquals(2, modelObjectenInOnderzoek.values().size());
        final MetaGroep metaGroep = (MetaGroep) modelObjectenInOnderzoek.values().iterator().next();
        Assert.assertEquals(elementNaam, metaGroep.getGroepElement().getNaam());
    }

    @Test
    public void testGroepMetVoorkomensleutel() {
        //bouw testgegevens
        final String elementNaam = Element.PERSOON_ADRES_STANDAARD.getNaam();
        final MetaObject persoon = maakDummyPersoonObject(VOORKOMEN_SLEUTEL, OBJECT_SLEUTEL, VOORKOMEN_SLEUTEL, GEGEVEN_OBJECT_SLEUTEL, true, elementNaam);
        final OnderzoekIndex onderzoekIndex = new OnderzoekIndex(new ModelIndex(persoon));
        final Multimap<Onderzoekbundel, MetaModel> modelObjectenInOnderzoek = onderzoekIndex.getGegevensInOnderzoek();
        //assert
        Assert.assertEquals(2, modelObjectenInOnderzoek.values().size());
        final MetaRecord metaRecord = (MetaRecord) modelObjectenInOnderzoek.values().iterator().next();
        Assert.assertEquals(VOORKOMEN_SLEUTEL.longValue(), metaRecord.getVoorkomensleutel());
    }

    @Test
    public void testObjectMetObjectsleutel() {
        //bouw testgegevens
        final String elementNaam = Element.PERSOON.getNaam();
        final MetaObject persoon = maakDummyPersoonObject(VOORKOMEN_SLEUTEL, OBJECT_SLEUTEL, OBJECT_SLEUTEL, GEGEVEN_OBJECT_SLEUTEL, false, elementNaam);
        final OnderzoekIndex onderzoekIndex = new OnderzoekIndex(new ModelIndex(persoon));
        final Multimap<Onderzoekbundel, MetaModel> modelObjectenInOnderzoek = onderzoekIndex.getGegevensInOnderzoek();
        //assert
        Assert.assertEquals(2, modelObjectenInOnderzoek.values().size());
        final MetaObject metaObject = (MetaObject) modelObjectenInOnderzoek.values().iterator().next();
        Assert.assertEquals(OBJECT_SLEUTEL.longValue(), metaObject.getObjectsleutel());
    }


    @Test
    public void testOnderzoekAttribuutElementMetGeenSleutel() {
        //onderzoeken naar ontbrekende gegevens worden ook geindexeerd
        //bouw testgegevens
        final String elementNaam = Element.PERSOON_ADRES_HUISNUMMER.getNaam();
        final MetaObject persoon = maakDummyPersoonObject(VOORKOMEN_SLEUTEL, OBJECT_SLEUTEL, -1, GEGEVEN_OBJECT_SLEUTEL, true, elementNaam);
        final OnderzoekIndex onderzoekIndex = new OnderzoekIndex(new ModelIndex(persoon));
        final Multimap<Onderzoekbundel, MetaModel> modelObjectenInOnderzoek = onderzoekIndex.getGegevensInOnderzoek();
        //assert
        Assert.assertEquals(2, modelObjectenInOnderzoek.values().size());
        final MetaAttribuut metaAttribuut = (MetaAttribuut) modelObjectenInOnderzoek.values().iterator().next();
        Assert.assertEquals(elementNaam, metaAttribuut.getAttribuutElement().getNaam());
    }

    @Test(expected = IllegalStateException.class)
    public void testOnderzoekAttribuutElementMetOnbekendElement() {
        //bouw testgegevens
        final String elementNaam = "onbekend";
        final MetaObject persoon = maakDummyPersoonObject(VOORKOMEN_SLEUTEL, OBJECT_SLEUTEL, VOORKOMEN_SLEUTEL, GEGEVEN_OBJECT_SLEUTEL, true, elementNaam);
        //persoonsgegevens
        final OnderzoekIndex onderzoekIndex = new OnderzoekIndex(new ModelIndex(persoon));
        final Multimap<Onderzoekbundel, MetaModel> modelObjectenInOnderzoek = onderzoekIndex.getGegevensInOnderzoek();
    }

    private MetaObject maakDummyPersoonObject(long voorkomenSleutel, long objectSleutel, final long sleutelReferentie, final long gegevenObjectSleutel,
                                              boolean voorkomen, String elementNaam) {
        final long persoonOnderzoekObjectSleutel = 50;
        final AttribuutElement sleutelElement;
        if (sleutelReferentie > 0) {
            if (voorkomen) {
                sleutelElement = getAttribuutElement(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN.getId());
            } else {
                sleutelElement = getAttribuutElement(Element.GEGEVENINONDERZOEK_OBJECTSLEUTELGEGEVEN.getId());
            }
        } else {
            //dummy
            sleutelElement = getAttribuutElement(Element.ADELLIJKETITEL_CODE.getId());
        }

        final MetaRecord.Builder gegevenInOnderzoekRecord = MetaRecord.maakBuilder();
        gegevenInOnderzoekRecord.metAttribuut(getAttribuutElement(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId()), elementNaam);
        gegevenInOnderzoekRecord.metAttribuut(sleutelElement, sleutelReferentie);

        //@formatter:off
        final MetaObject metaObject = TestBuilders.maakLeegPersoon((int) objectSleutel)
                .metObject()
                    .metObjectElement(Element.PERSOON_ADRES.getId())
                    .metId(objectSleutel)
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId()))
                        .metRecord()
                            .metId(voorkomenSleutel)
                            .metAttribuut(getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId()), "3")
                        .eindeRecord()
                    .eindeGroep()
                .eindeObject()

                .metGroep()
                    .metGroepElement(getGroepElement(Element.PERSOON_GEBOORTE.getId()))
                    .metRecord()
                        .metId(4)
                        .metAttribuut(getAttribuutElement(Element.PERSOON_GEBOORTE_DATUM.getId()), 20100101)
                    .eindeRecord()
                .eindeGroep()

                .metObject()
                    .metObjectElement(getObjectElement(Element.ONDERZOEK.getId()))
                    .metId(OBJECT_SLEUTEL_ONDERZOEK)
                    .metGroep()
                        .metGroepElement(getGroepElement(Element.ONDERZOEK_STANDAARD.getId()))
                        .metRecord().eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metId(gegevenObjectSleutel)
                        .metObjectElement(getObjectElement(Element.GEGEVENINONDERZOEK.getId()))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId()))
                            .metRecord(gegevenInOnderzoekRecord)
                        .eindeGroep()
                    .eindeObject()
                    .metObject()
                        .metId(gegevenObjectSleutel)
                        .metObjectElement(getObjectElement(Element.GEGEVENINONDERZOEK.getId()))
                        .metGroep()
                            .metGroepElement(getGroepElement(Element.GEGEVENINONDERZOEK_IDENTITEIT.getId()))
                            .metRecord()
                                .metAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM.getId(), Element.PERSOON.getNaam())
                                .metAttribuut(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN.getId(), 4)
                            .eindeRecord()
                        .eindeGroep()
                    .eindeObject()
            .eindeObject()
        .build();
        //@formatter:on
        return metaObject;
    }
}
