/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ElementObject;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * Wrapper objecten voor een 'gegeven' in onderzoek. Een gegeven in onderzoek wordt aanwezen middels de naam van het element en verfijnd met een
 * voorkomensleutel OF objectsleutel.
 *
 * <table><caption>Opties aanwijzen onderzoek:</caption>
 <tr>
 <td>Element</td>
 <td>Object</td>
 <td>Voorkomen</td>
 <td>Betekenis</td>
 </tr>
 <tr>
 <td>O</td>
 <td>N</td>
 <td>N</td>
 <td>Er wordt verwezen naar een ontbrekend object.</td>
 </tr>
 <tr>
 <td>O</td>
 <td>J</td>
 <td>N</td>
 <td>Er wordt verwezen naar een specifiek
 object.
 </td>
 </tr>
 <tr>
 <td>O</td>
 <td>N</td>
 <td>J</td>
 <td>Er wordt verwezen naar een specifiek voorkomen van een specifiek object.</td>
 </tr>
 <tr>
 <td>G</td>
 <td>N</td>
 <td>N</td>
 <td>Er wordt verwezen naar een ontbrekende groep.</td>
 </tr>
 <tr>
 <td>G</td>
 <td>J</td>
 <td>N</td>
 <td>Er wordt
 verwezen naar een gegevensgroep bij alle voorkomens van een specifiek object.
 </td>
 </tr>
 <tr>
 <td>G</td>
 <td>N</td>
 <td>J</td>
 <td>Er wordt verwezen
 naar een gegevensgroep bij een specifiek voorkomen van een specifiek object.
 </td>
 </tr>
 <tr>
 <td>A</td>
 <td>N</td>
 <td>N</td>
 <td>Er wordt verwezen naar
 een ontbrekend attribuut
 </td>
 </tr>
 <tr>
 <td>A</td>
 <td>J</td>
 <td>N</td>
 <td>Er wordt verwezen naar een attribuut bij alle voorkomens van een specifiek
 object.
 </td>
 </tr>
 <tr>
 <td>A</td>
 <td>N</td>
 <td>J</td>
 <td>Er wordt verwezen naar een attribuut bij een specifiek voorkomen van een specifiek
 object.
 </td>
 </tr>
 </table>
 *
 */
public final class Onderzoekbundel {

    /**
     * Het MetaObject van het gegeven in onderzoek
     */
    private MetaObject gegevenInOnderzoek;

    /**
     * Het element dat in onderzoek staat.
     */
    private ElementObject element;
    /**
     * Indien gevuld, staan alleen attributen binnen het record met dit voorkomensleutel in onderzoek.
     */
    private Long elementVoorkomensleutel;
    /**
     * Indien gevuld, staan alleen elementen binnen objecten met dit objectsleutel in onderzoek.
     */
    private Long elementObjectsleutel;

    /**
     * Constructor voor het gegeven in onderzoek.
     * @param metaRecord het record dat de gegeven in onderzoek data bevat
     */
    Onderzoekbundel(final MetaRecord metaRecord) {

        gegevenInOnderzoek = metaRecord.getParentGroep().getParentObject();

        final MetaAttribuut objectsleutelGegevenAttr = metaRecord.getAttribuut(Element.GEGEVENINONDERZOEK_OBJECTSLEUTELGEGEVEN);
        if (objectsleutelGegevenAttr != null) {
            elementObjectsleutel = objectsleutelGegevenAttr.<Number>getWaarde().longValue();
        }

        final MetaAttribuut voorkomensleutelGegevenAttr = metaRecord.getAttribuut(Element.GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN);
        if (voorkomensleutelGegevenAttr != null) {
            elementVoorkomensleutel = voorkomensleutelGegevenAttr.<Number>getWaarde().longValue();
        }

        final MetaAttribuut elementNaamAttr = metaRecord.getAttribuut(Element.GEGEVENINONDERZOEK_ELEMENTNAAM);
        element = ElementHelper.getElement(elementNaamAttr.getWaarde());
    }

    public MetaObject getOnderzoek() {
        return gegevenInOnderzoek.getParentObject();
    }

    /**
     * @return het onderzoek meta object
     */
    public MetaObject getGegevenInOnderzoek() {
        return gegevenInOnderzoek;
    }

    /**
     * @return de objectsleutel van het onderzoek object
     */
    public Long getObjectsleutelVanOnderzoek() {
        return gegevenInOnderzoek.getParentObject().getParentObject().getObjectsleutel();
    }

    /**
     * @return de objectsleutel van het gegeven in onderzoek object
     */
    public Long getObjectsleutelVanGegegevenInOnderzoek() {
        return gegevenInOnderzoek.getObjectsleutel();
    }

    /**
     * @return het element dat in onderzoek staat. Dit is altijd gevuld.
     */
    public ElementObject getElement() {
        return element;
    }

    /**
     * @return de objectsleutel van het element in onderzoek. Enkel elementen waarvoor geldt dat het object, of bovenliggend object deze objectsleutel heeft
     * staan in onderzoek.
     */
    Long getElementObjectsleutel() {
        return elementObjectsleutel;
    }

    /**
     * @return de voorkomensleutel van het element in onderzoek. Afhankelijk van het element kan enkel een MetaRecord met dit voorkomensleutel in onderzoek
     * staan, of een MetaAttribuut dat zich bevindt in een record met dit voorkomensleutel.
     */
    Long getElementVoorkomensleutel() {
        return elementVoorkomensleutel;
    }

    /**
     * @return indicatie of het onderzoek verwijst naar een ontbrekend gegeven.
     */
    public boolean isOntbrekend() {
        return elementObjectsleutel == null && elementVoorkomensleutel == null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Onderzoekbundel that = (Onderzoekbundel) o;

        return !(gegevenInOnderzoek != null ? !gegevenInOnderzoek.equals(that.gegevenInOnderzoek) : that.gegevenInOnderzoek != null);

    }

    @Override
    public int hashCode() {
        return gegevenInOnderzoek != null ? gegevenInOnderzoek.hashCode() : 0;
    }
}
