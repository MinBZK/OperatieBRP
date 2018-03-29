/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.builder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElement;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Een leesbaar blobrecord welke gebruikt kan worden in test
 */
@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeesbaarBlobRecord {

    private Long parentObjectSleutel;
    private String parentObjectElement;
    private String objectElement;
    private Long objectSleutel;
    private String groepElement;
    private Long voorkomenSleutel;
    private Long dienstInhoud;
    private Long dienstVerval;
    private Long actieInhoud;
    private Long actieVerval;
    private Character nadereAanduidingVerval;
    private Long actieAanpassingGeldigheid;
    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;
    private Boolean indicatieTbvLeveringMutaties;
    private Long actieVervalTbvLeveringMutaties;
    private Map<String, Object> attributen;

    /**
     * @return de objectsleutel van het object dat het historie record bevat.
     */
    public Long getObjectSleutel() {
        return objectSleutel;
    }

    /**
     * Zet de objectsleutel van het object dat het historie record bevat.
     * @param objectSleutel het technisch id van het object
     */
    public void setObjectSleutel(final Long objectSleutel) {
        this.objectSleutel = objectSleutel;
    }

    /**
     * @return de voorkomensleutel van het historie record bevat.
     */
    public Long getVoorkomenSleutel() {
        return voorkomenSleutel;
    }

    /**
     * Zet de voorkomensleutel van het historie record bevat.
     * @param voorkomenSleutel het technisch id van het record
     */
    public void setVoorkomenSleutel(final Long voorkomenSleutel) {
        this.voorkomenSleutel = voorkomenSleutel;
    }

    /**
     * @return het element van de groep dat het record bevat.
     */
    public String getGroepElement() {
        return groepElement;
    }

    /**
     * @param groepElement het element van de groep dat het record bevat.
     */
    public void setGroepElement(final String groepElement) {
        this.groepElement = groepElement;
    }

    /**
     * Zet het element van de groep dat het record bevat.
     * @param groep een groep element
     */
    @JsonIgnore
    public void setGroep(final Element groep) {
        this.groepElement = groep.getNaam();
    }

    /**
     * @return het element id van de object dat het record bevat.
     */
    public String getObjectElement() {
        return objectElement;
    }

    /**
     * Zet het element id van de object dat het record bevat.
     * @param objectElement id van een object element
     */
    public void setObjectElement(final String objectElement) {
        this.objectElement = objectElement;
    }

    /**
     * Zet het element van het object dat het record bevat.
     * @param object een object element
     */
    @JsonIgnore
    public void setObject(final Element object) {
        this.objectElement = object.getNaam();
    }

    /**
     * @return de objectsleutel van het opa object
     */
    public Long getParentObjectSleutel() {
        return parentObjectSleutel;
    }

    /**
     * Zet de objectsleutel van het opa object.
     * @param parentObjectSleutel id van een object
     */
    public void setParentObjectSleutel(final Long parentObjectSleutel) {
        this.parentObjectSleutel = parentObjectSleutel;
    }

    /**
     * @return het element id van het opa object.
     */
    public String getParentObjectElement() {
        return parentObjectElement;
    }

    /**
     * Zet het element id van het opa object.
     * @param parentObjectElement een element id
     */
    public void setParentObjectElement(final String parentObjectElement) {
        this.parentObjectElement = parentObjectElement;
    }

    /**
     * Zet het element van het opa object.
     * @param parentObject een element
     */
    @JsonIgnore
    public void setParentObject(final Element parentObject) {
        this.parentObjectElement = parentObject.getNaam();
    }

    /**
     * @return id van de dienst inhoud
     */
    public Long getDienstInhoud() {
        return dienstInhoud;
    }

    /**
     * Zet het id van de dienst (alleen voor afnemerindicaties).
     * @param dienstInhoud id van de dienst
     */
    public void setDienstInhoud(final Long dienstInhoud) {
        this.dienstInhoud = dienstInhoud;
    }

    /**
     * @return id van de dienst verval
     */
    public Long getDienstVerval() {
        return dienstVerval;
    }

    /**
     * Zet het id van de dienstverval (alleen voor afnemerindicaties).
     * @param dienstVerval id van de dienst
     */
    public void setDienstVerval(final Long dienstVerval) {
        this.dienstVerval = dienstVerval;
    }

    /**
     * @return id van de actie inhoud
     */
    public Long getActieInhoud() {
        return actieInhoud;
    }

    /**
     * Zet het id van de actie inhoud.
     * @param actieInhoud id van de actie
     */
    public void setActieInhoud(final Long actieInhoud) {
        this.actieInhoud = actieInhoud;
    }

    /**
     * @return id van de actie verval
     */
    public Long getActieVerval() {
        return actieVerval;
    }

    /**
     * @param actieVerval id van de actie verval
     */
    public void setActieVerval(final Long actieVerval) {
        this.actieVerval = actieVerval;
    }

    /**
     * @return id van de actie aanpassing geldigheid
     */
    public Long getActieAanpassingGeldigheid() {
        return actieAanpassingGeldigheid;
    }

    /**
     * @param actieAanpassingGeldigheid id van de actie aanpassing geldigheid
     */
    public void setActieAanpassingGeldigheid(final Long actieAanpassingGeldigheid) {
        this.actieAanpassingGeldigheid = actieAanpassingGeldigheid;
    }

    /**
     * @return de nadere aanduiding verval (optioneel indien vervallen)
     */
    public Character getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    /**
     * @param nadereAanduidingVerval de nadere aanduiding verval (optioneel indien vervallen)
     */
    public void setNadereAanduidingVerval(final Character nadereAanduidingVerval) {
        this.nadereAanduidingVerval = nadereAanduidingVerval;
    }

    /**
     * @return de datum aanvang geldigheid
     */
    public Integer getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * @param datumAanvangGeldigheid de datum aanvang geldigheid
     */
    public void setDatumAanvangGeldigheid(final Integer datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * @return de datum einde geldigheid
     */
    public Integer getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * @param datumEindeGeldigheid de datum einde geldigheid
     */
    public void setDatumEindeGeldigheid(final Integer datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * @return indicatie historie aangepast tbv leveren
     */
    public Boolean isIndicatieTbvLeveringMutaties() {
        return indicatieTbvLeveringMutaties;
    }

    /**
     * @param indicatieTbvLeveringMutaties indicatie historie aangepast tbv leveren
     */
    public void setIndicatieTbvLeveringMutaties(final Boolean indicatieTbvLeveringMutaties) {
        this.indicatieTbvLeveringMutaties = indicatieTbvLeveringMutaties;
    }

    /**
     * @return actie id van gecorrigeerde actie verval
     */
    public Long getActieVervalTbvLeveringMutaties() {
        return actieVervalTbvLeveringMutaties;
    }

    /**
     * @param actieId actie id van gecorrigeerde actie verval
     */
    public void setActieVervalTbvLeveringMutaties(final Long actieId) {
        this.actieVervalTbvLeveringMutaties = actieId;
    }

    /**
     * Voegt een attribuut toe.
     * @param element het attribuut element
     * @param value de waarde van het attribuut
     */
    public void addAttribuut(final Element element, final Object value) {
        if (SoortElement.ATTRIBUUT != element.getSoort()) {
            throw new IllegalArgumentException("Ongeldig Attribuut Element: " + element);
        }
        if (value == null || "".equals(value)) {
            return;
        }
        if (attributen == null) {
            this.attributen = Maps.newTreeMap();
        }
        attributen.put(element.getNaam(), value);
    }

    /**
     * Voegt een attribuut toe.
     * @param elementId het attribuut elementId
     * @param value de waarde van het attribuut
     */
    public void addAttribuut(final int elementId, final Object value) {
        addAttribuut(Element.parseId(elementId), value);
    }

    /**
     * @param attributen map met attributen.
     */
    public void setAttributen(final Map<String, Object> attributen) {
        this.attributen = Maps.newTreeMap();
        this.attributen.putAll(attributen);
    }

    /**
     * @return map met attributen
     */
    public Map<String, Object> getAttributen() {
        return attributen;
    }

    @Override
    public boolean equals(final Object obj) {

        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final LeesbaarBlobRecord rhs = (LeesbaarBlobRecord) obj;
        return new EqualsBuilder().append(objectSleutel, rhs.objectSleutel)
                .append(voorkomenSleutel, rhs.voorkomenSleutel)
                .append(groepElement, rhs.groepElement)
                .append(objectElement, rhs.objectElement)
                .append(parentObjectSleutel, rhs.parentObjectSleutel)
                .append(parentObjectElement, rhs.parentObjectElement)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(objectSleutel, voorkomenSleutel, groepElement, objectElement, parentObjectSleutel, parentObjectElement);
    }

    @Override
    public String toString() {
        return "BlobRecord{"
                + "parentObjectSleutel="
                + parentObjectSleutel
                + ", parentObjectElementId="
                + parentObjectElement
                + ", objectElementId="
                + objectElement
                + ", objectSleutel="
                + objectSleutel
                + ", groepElementId="
                + groepElement
                + ", voorkomenSleutel="
                + voorkomenSleutel
                + ", dienstInhoud="
                + dienstInhoud
                + ", dienstVerval="
                + dienstVerval
                + ", actieInhoud="
                + actieInhoud
                + ", actieVerval="
                + actieVerval
                + ", nadereAanduidingVerval="
                + nadereAanduidingVerval
                + ", actieAanpassingGeldigheid="
                + actieAanpassingGeldigheid
                + ", datumAanvangGeldigheid="
                + datumAanvangGeldigheid
                + ", datumEindeGeldigheid="
                + datumEindeGeldigheid
                + ", indicatieTbvLeveringMutaties="
                + indicatieTbvLeveringMutaties
                + ", actieVervalTbvLeveringMutaties="
                + actieVervalTbvLeveringMutaties
                + ", attributen="
                + attributen
                + '}';
    }
}
