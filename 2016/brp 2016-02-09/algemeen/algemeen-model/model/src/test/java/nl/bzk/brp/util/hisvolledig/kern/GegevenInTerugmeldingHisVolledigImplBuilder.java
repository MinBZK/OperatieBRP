/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GegevenswaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInTerugmeldingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.TerugmeldingHisVolledigImpl;

/**
 * Builder klasse voor Gegeven in terugmelding.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigBuilderGenerator")
public class GegevenInTerugmeldingHisVolledigImplBuilder {

    private GegevenInTerugmeldingHisVolledigImpl hisVolledigImpl;

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param terugmelding terugmelding van Gegeven in terugmelding.
     * @param element element van Gegeven in terugmelding.
     * @param betwijfeldeWaarde betwijfeldeWaarde van Gegeven in terugmelding.
     * @param verwachteWaarde verwachteWaarde van Gegeven in terugmelding.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public GegevenInTerugmeldingHisVolledigImplBuilder(
        final TerugmeldingHisVolledigImpl terugmelding,
        final Element element,
        final GegevenswaardeAttribuut betwijfeldeWaarde,
        final GegevenswaardeAttribuut verwachteWaarde,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new GegevenInTerugmeldingHisVolledigImpl(terugmelding, new ElementAttribuut(element), betwijfeldeWaarde, verwachteWaarde);
        if (hisVolledigImpl.getElement() != null) {
            hisVolledigImpl.getElement().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getBetwijfeldeWaarde() != null) {
            hisVolledigImpl.getBetwijfeldeWaarde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getVerwachteWaarde() != null) {
            hisVolledigImpl.getVerwachteWaarde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param terugmelding terugmelding van Gegeven in terugmelding.
     * @param element element van Gegeven in terugmelding.
     * @param betwijfeldeWaarde betwijfeldeWaarde van Gegeven in terugmelding.
     * @param verwachteWaarde verwachteWaarde van Gegeven in terugmelding.
     */
    public GegevenInTerugmeldingHisVolledigImplBuilder(
        final TerugmeldingHisVolledigImpl terugmelding,
        final Element element,
        final GegevenswaardeAttribuut betwijfeldeWaarde,
        final GegevenswaardeAttribuut verwachteWaarde)
    {
        this(terugmelding, element, betwijfeldeWaarde, verwachteWaarde, false);
    }

    /**
     * Maak een nieuwe builder aan met de identificerende gegevens.
     *
     * @param element element van Gegeven in terugmelding.
     * @param betwijfeldeWaarde betwijfeldeWaarde van Gegeven in terugmelding.
     * @param verwachteWaarde verwachteWaarde van Gegeven in terugmelding.
     * @param defaultMagGeleverdWordenVoorAttributen waarde voor het vlaggetje isMagGeleverdWorden van alle attributen.
     */
    public GegevenInTerugmeldingHisVolledigImplBuilder(
        final Element element,
        final GegevenswaardeAttribuut betwijfeldeWaarde,
        final GegevenswaardeAttribuut verwachteWaarde,
        final boolean defaultMagGeleverdWordenVoorAttributen)
    {
        this.hisVolledigImpl = new GegevenInTerugmeldingHisVolledigImpl(null, new ElementAttribuut(element), betwijfeldeWaarde, verwachteWaarde);
        if (hisVolledigImpl.getElement() != null) {
            hisVolledigImpl.getElement().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getBetwijfeldeWaarde() != null) {
            hisVolledigImpl.getBetwijfeldeWaarde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
        if (hisVolledigImpl.getVerwachteWaarde() != null) {
            hisVolledigImpl.getVerwachteWaarde().setMagGeleverdWorden(defaultMagGeleverdWordenVoorAttributen);
        }
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param element element van Gegeven in terugmelding.
     * @param betwijfeldeWaarde betwijfeldeWaarde van Gegeven in terugmelding.
     * @param verwachteWaarde verwachteWaarde van Gegeven in terugmelding.
     */
    public GegevenInTerugmeldingHisVolledigImplBuilder(
        final Element element,
        final GegevenswaardeAttribuut betwijfeldeWaarde,
        final GegevenswaardeAttribuut verwachteWaarde)
    {
        this(null, element, betwijfeldeWaarde, verwachteWaarde, false);
    }

    /**
     * Bouw het his volledig object.
     *
     * @return het his volledig object
     */
    public GegevenInTerugmeldingHisVolledigImpl build() {
        return hisVolledigImpl;
    }

}
