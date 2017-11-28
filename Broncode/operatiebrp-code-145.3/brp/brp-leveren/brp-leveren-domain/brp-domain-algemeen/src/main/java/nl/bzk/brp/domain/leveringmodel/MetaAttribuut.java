/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;


import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * Realisatie van een BMR attribuut in het persoonsmodel.
 */
public final class MetaAttribuut extends MetaModel {
    /**
     * Het voorkomen waartoe dit attribuut behoort
     */
    private MetaRecord parentRecord;

    /**
     * Het type attribuut
     */
    private AttribuutElement attribuutElement;

    /**
     * De waarde van het attribuut. Typering is abstract, gebruiker moet casten adhv type element
     */
    private Object waarde;

    /**
     * De geformatteerde waarde.
     */
    private String geformatteerdeWaarde;

    /**
     * Private constructor voor een MetaAttribuut.
     */
    private MetaAttribuut() {
    }

    @Override
    public MetaModel getParent() {
        return parentRecord;
    }

    /**
     * @return het parent voorkomen
     */
    public MetaRecord getParentRecord() {
        return parentRecord;
    }

    /**
     * Geeft de waarde van het attribuut. Het type waarde is in overeenstemming met de waarde zoals geduid in de element tabel. Waarden wordt automatisch
     * gecast naar dit verwachtte type.
     * @param <T> het type waarde
     * @return de waarde
     */
    public <T> T getWaarde() {
        return (T) waarde;
    }

    /**
     * @return de geformatteerde waarde.
     */
    public String getGeformatteerdeWaarde() {
        return geformatteerdeWaarde;
    }

    @Override
    public void accept(final ModelVisitor visitor) {
        visitor.visit(this);
    }

    public AttribuutElement getAttribuutElement() {
        return attribuutElement;
    }

    @Override
    public String toString() {
        return attribuutElement.toString();
    }

    /**
     * @return een attribuut builder
     */
    public static Builder maakBuilder() {
        return new Builder();
    }

    /**
     * Builder om een MetaAttribuut te construeren.
     */
    public static final class Builder {

        private final MetaRecord.Builder recordBuilder;
        private Object waarde;
        private AttribuutElement attribuutElement;

        /**
         * @param recordBuilder de builder voor het record
         */
        public Builder(final MetaRecord.Builder recordBuilder) {
            this.recordBuilder = recordBuilder;
        }

        /**
         * voor bouw in filters of voor isolatie.
         */
        public Builder() {
            this.recordBuilder = null;
        }

        /**
         * Zet de attribuutwaarde.
         * @param waardeParam object waarde.
         * @return deze builder
         */
        public Builder metWaarde(final Object waardeParam) {
            this.waarde = waardeParam;
            return this;
        }

        /**
         * Zet het type attribuut.
         * @param id id van het element attribuut
         * @return deze builder
         */
        public Builder metType(final int id) {
            return metType(ElementHelper.<AttribuutElement>getAttribuutElement(id));
        }

        /**
         * Zet het type attribuut.
         * @param element het element
         * @return deze builder
         */
        public Builder metType(final AttribuutElement element) {
            this.attribuutElement = element;
            return this;
        }

        /**
         * Zet het type attribuut.
         * @param element het element
         * @return deze builder
         */
        public Builder metType(final Element element) {
            return metType(ElementHelper.getAttribuutElement(element));
        }

        /**
         * Maakt het MetaAttribuut.
         * @return de parent record builder
         */
        public MetaRecord.Builder eindeAttribuut() {
            return recordBuilder;
        }

        /**
         * Maakt het MetaAttribuut.
         * @param gebouwdRecord het parent record
         * @return de parent record builder
         */
        public MetaAttribuut build(final MetaRecord gebouwdRecord) {
            final MetaAttribuut attribuut = new MetaAttribuut();
            attribuut.parentRecord = gebouwdRecord;
            attribuut.waarde = waarde;
            attribuut.attribuutElement = attribuutElement;
            attribuut.geformatteerdeWaarde = maakDisplayWaarde();
            return attribuut;
        }

        private String maakDisplayWaarde() {
            if (waarde instanceof Actie || waarde == null) {
                return null;
            }

            final String displayWaarde;
            switch (attribuutElement.getDatatype()) {
                case BOOLEAN:
                    final Boolean value = (Boolean) waarde;
                    displayWaarde = value ? "J" : "N";
                    break;
                case DATUM:
                    displayWaarde = DatumFormatterUtil.datumAlsGetalNaarDatumAlsString((Integer) waarde);
                    break;
                case DATUMTIJD:
                    displayWaarde = DatumFormatterUtil.vanZonedDateTimeNaarXsdDateTime((ZonedDateTime) waarde);
                    break;
                case GETAL:
                    displayWaarde = getDisplaywaardeVoorGetal();
                    break;
                default:
                    displayWaarde = waarde.toString();
            }
            return displayWaarde;
        }

        private String getDisplaywaardeVoorGetal() {
            final String displayWaarde;
            final Integer minimumLengte = attribuutElement.getMininumLengte();
            if (minimumLengte != null) {
                displayWaarde = StringUtils.leftPad(waarde.toString(), minimumLengte, "0");
            } else {
                displayWaarde = waarde.toString();
            }
            return displayWaarde;
        }
    }
}
