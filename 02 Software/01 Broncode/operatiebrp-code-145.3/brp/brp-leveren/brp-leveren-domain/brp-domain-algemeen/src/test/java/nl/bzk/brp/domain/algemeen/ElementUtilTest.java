/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import static nl.bzk.brp.domain.element.ElementHelper.getAttribuutElement;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.AttribuutElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * ElementUtilTest.
 */
public class ElementUtilTest {

    private static final Integer DATUM_TIJD_GELDIG_VOOR_LEVERING = 20130101;
    private static final Integer DATUM_TIJD_NIET_GELDIG_VOOR_LEVERING = 20100101;

    private static final AttribuutElement ELEMENT_SOORTAUTORISATIE_NIETVERSTREKKEN = getAttribuutElement(Element
            .PERSOON_AFGELEIDADMINISTRATIEF_SORTEERVOLGORDE);
    private static final AttribuutElement ELEMENT_SOORTAUTORISATIE_STRUCTUUR = getAttribuutElement(Element
            .PERSOON_AFGELEIDADMINISTRATIEF_ADMINISTRATIEVEHANDELING);
    private static final AttribuutElement ELEMENT_SOORTAUTORISATIE_VIAGROEPSAUTORISATIE = getAttribuutElement(Element
            .PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD);
    private static final AttribuutElement ELEMENT_SOORTAUTORISATIE_GELDIG = getAttribuutElement(Element
            .PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID);
    private static final AttribuutElement ELEMENT_SOORTAUTORISATIE_NULL = getAttribuutElement(Element
            .PERSOON_INDICATIE_INDICATIEACTUEELENGELDIG);

    private static final AttribuutElement ELEMENT_SOORTAUTORISATIE_GELDIG_GROEP = getAttribuutElement(Element
            .PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD);
    private static final AttribuutElement ELEMENT_SOORTAUTORISATIE_NIET_GELDIG_GROEP = getAttribuutElement(Element
            .PERSOON_INDICATIE_INDICATIEACTUEELENGELDIG);


    @Test
    public void elementGeldigVoorGroepAutorisatie() {
        Assert.assertTrue(
                ElementUtil.isElementGeldigVoorGroepAutorisatie(ELEMENT_SOORTAUTORISATIE_GELDIG_GROEP, DATUM_TIJD_GELDIG_VOOR_LEVERING));
    }

    @Test
    public void elementNietGeldigVoorGroepAutorisatie() {
        Assert.assertFalse(
                ElementUtil.isElementGeldigVoorGroepAutorisatie(ELEMENT_SOORTAUTORISATIE_NIET_GELDIG_GROEP, DATUM_TIJD_GELDIG_VOOR_LEVERING));
    }

    @Test
    public void elementGeldigVoorLeveren() {
        Assert.assertTrue(
                ElementUtil.isElementGeldigVoorAttribuutAutorisatie(ELEMENT_SOORTAUTORISATIE_GELDIG, DATUM_TIJD_GELDIG_VOOR_LEVERING));
    }

    @Test
    public void elementGeldigVoorLeverenDatumTijdNull() {
        Assert.assertTrue(
                ElementUtil.isElementGeldigVoorAttribuutAutorisatie(ELEMENT_SOORTAUTORISATIE_GELDIG, null));
    }

    @Test
    public void elementNietGeldigVoorLeverenIvmDatumAanvangGeldigheid() {
        Assert.assertFalse(
                ElementUtil.isElementGeldigVoorAttribuutAutorisatie(ELEMENT_SOORTAUTORISATIE_GELDIG, DATUM_TIJD_NIET_GELDIG_VOOR_LEVERING));
    }

    @Test
    public void elementNietGeldigVoorLeverenAutorisatieIsNull() {
        Assert.assertFalse(
                ElementUtil.isElementGeldigVoorAttribuutAutorisatie(ELEMENT_SOORTAUTORISATIE_NULL, DATUM_TIJD_NIET_GELDIG_VOOR_LEVERING));
    }


    @Test
    public void elementNietGeldigVoorLeverenSrtAutNietVerstrekken() {
        Assert.assertFalse(
                ElementUtil.isElementGeldigVoorAttribuutAutorisatie(ELEMENT_SOORTAUTORISATIE_NIETVERSTREKKEN, DATUM_TIJD_GELDIG_VOOR_LEVERING));

    }


    @Test
    public void elementNietGeldigVoorLeverenSrtAutStructuur() {
        Assert.assertFalse(
                ElementUtil.isElementGeldigVoorAttribuutAutorisatie(ELEMENT_SOORTAUTORISATIE_STRUCTUUR, DATUM_TIJD_GELDIG_VOOR_LEVERING));
    }


    @Test
    public void elementNietGeldigVoorLeverenSrtAutViaGroepsautorisatie() {
        Assert.assertFalse(
                ElementUtil.isElementGeldigVoorAttribuutAutorisatie(ELEMENT_SOORTAUTORISATIE_VIAGROEPSAUTORISATIE, DATUM_TIJD_GELDIG_VOOR_LEVERING));
    }
}
