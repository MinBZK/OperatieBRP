/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import java.sql.Timestamp;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElement;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Een BlobRecord representeert een historie-rij in de BRP. Een rij bevat de attributen en de
 * context van de groep, het object en het parent object.
 */
@JsonAutoDetect
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class BlobRecord {
    private Long parentObjectSleutel;
    private Integer parentObjectElementId;
    private Integer objectElementId;
    private Long objectSleutel;
    private Integer groepElementId;
    private Long voorkomenSleutel;
    private Long actieInhoud;
    private Long actieVerval;
    private String nadereAanduidingVerval;
    private Long actieAanpassingGeldigheid;
    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;
    private Boolean indicatieTbvLeveringMutaties;
    private Long actieVervalTbvLeveringMutaties;
    private Map<Integer, Object> attributen;

    /**
     * Constructor.
     */
    public BlobRecord() {
        // jackson default constructor
    }

    /**
     * Copy constructor.
     * 
     * @param blobRecord BlobRecord
     */
    public BlobRecord(final BlobRecord blobRecord) {
        parentObjectSleutel = blobRecord.parentObjectSleutel;
        parentObjectElementId = blobRecord.parentObjectElementId;
        objectElementId = blobRecord.objectElementId;
        objectSleutel = blobRecord.objectSleutel;
        groepElementId = blobRecord.groepElementId;
        voorkomenSleutel = blobRecord.voorkomenSleutel;
        actieInhoud = blobRecord.actieInhoud;
        actieVerval = blobRecord.actieVerval;
        nadereAanduidingVerval = blobRecord.nadereAanduidingVerval;
        actieAanpassingGeldigheid = blobRecord.actieAanpassingGeldigheid;
        datumAanvangGeldigheid = blobRecord.datumAanvangGeldigheid;
        datumEindeGeldigheid = blobRecord.datumEindeGeldigheid;
        indicatieTbvLeveringMutaties = blobRecord.indicatieTbvLeveringMutaties;
        actieVervalTbvLeveringMutaties = blobRecord.actieVervalTbvLeveringMutaties;
        if (blobRecord.attributen != null) {
            attributen = Maps.newTreeMap();
            attributen.putAll(blobRecord.attributen);
        }
    }

    /**
     * @return de objectsleutel van het object dat het historie record bevat.
     */
    public Long getObjectSleutel() {
        return objectSleutel;
    }

    /**
     * Zet de objectsleutel van het object dat het historie record bevat.
     * 
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
     * 
     * @param voorkomenSleutel het technisch id van het record
     */
    public void setVoorkomenSleutel(final Long voorkomenSleutel) {
        this.voorkomenSleutel = voorkomenSleutel;
    }

    /**
     * @return het element id van de groep dat het record bevat.
     */
    public Integer getGroepElementId() {
        return groepElementId;
    }

    /**
     * @param groepElementId het element id van de groep dat het record bevat.
     */
    public void setGroepElementId(final int groepElementId) {
        this.groepElementId = groepElementId;
    }

    /**
     * Zet het element van de groep dat het record bevat.
     * 
     * @param groepElement een groep element
     */
    @JsonIgnore
    public void setGroepElement(final Element groepElement) {
        groepElementId = groepElement.getId();
    }

    /**
     * @return het element id van de object dat het record bevat.
     */
    public Integer getObjectElementId() {
        return objectElementId;
    }

    /**
     * Zet het element id van de object dat het record bevat.
     * 
     * @param objectElementId id van een object element
     */
    public void setObjectElementId(final int objectElementId) {
        this.objectElementId = objectElementId;
    }

    /**
     * Zet het element van het object dat het record bevat.
     * 
     * @param objectElement een object element
     */
    @JsonIgnore
    public void setObjectElement(final Element objectElement) {
        objectElementId = objectElement.getId();
    }

    /**
     * @return de objectsleutel van het opa object
     */
    public Long getParentObjectSleutel() {
        return parentObjectSleutel;
    }

    /**
     * Zet de objectsleutel van het opa object.
     * 
     * @param parentObjectSleutel id van een object
     */
    public void setParentObjectSleutel(final Long parentObjectSleutel) {
        this.parentObjectSleutel = parentObjectSleutel;
    }

    /**
     * @return het element id van het opa object.
     */
    public Integer getParentObjectElementId() {
        return parentObjectElementId;
    }

    /**
     * Zet het element id van het opa object.
     * 
     * @param parentObjectElementId een element id
     */
    public void setParentObjectElementId(final int parentObjectElementId) {
        this.parentObjectElementId = parentObjectElementId;
    }

    /**
     * Zet het element van het opa object.
     * 
     * @param parentObjectElement een element
     */
    @JsonIgnore
    public void setParentObjectElement(final Element parentObjectElement) {
        parentObjectElementId = parentObjectElement.getId();
    }

    /**
     * @return id van de actie inhoud
     */
    public Long getActieInhoud() {
        return actieInhoud;
    }

    /**
     * Zet het id van de actie inhoud.
     * 
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
    public String getNadereAanduidingVerval() {
        return nadereAanduidingVerval;
    }

    /**
     * @param nadereAanduidingVerval de nadere aanduiding verval (optioneel indien vervallen)
     */
    public void setNadereAanduidingVerval(final String nadereAanduidingVerval) {
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
        actieVervalTbvLeveringMutaties = actieId;
    }

    /**
     * Voegt een attribuut toe.
     * 
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
            attributen = Maps.newTreeMap();
        }
        final Object valueConverted;
        if (value instanceof Timestamp) {
            valueConverted = ((Timestamp) value).getTime();
        } else {
            valueConverted = value;
        }
        attributen.put(element.getId(), valueConverted);
    }

    /**
     * Voegt een attribuut toe obv een actie.
     * 
     * @param element het attribuut element
     * @param brpActie de acte
     */
    public void addActie(final Element element, final BRPActie brpActie) {
        if (brpActie != null) {
            addAttribuut(element, brpActie.getId());
        }
    }

    /**
     * Voegt een attribuut toe.
     * 
     * @param elementId het attribuut elementId
     * @param value de waarde van het attribuut
     */
    public void addAttribuut(final int elementId, final Object value) {
        addAttribuut(Element.parseId(elementId), value);
    }

    /**
     * @return map met attributen
     */
    public Map<Integer, Object> getAttributen() {
        return attributen;
    }

    /**
     * @param attributen map met attributen.
     */
    public void setAttributen(final Map<Integer, Object> attributen) {
        this.attributen = Maps.newTreeMap();
        this.attributen.putAll(attributen);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof BlobRecord) {
            final BlobRecord rhs = (BlobRecord) obj;
            return new EqualsBuilder().append(objectSleutel, rhs.objectSleutel).append(voorkomenSleutel, rhs.voorkomenSleutel)
                    .append(groepElementId, rhs.groepElementId).append(objectElementId, rhs.objectElementId)
                    .append(parentObjectSleutel, rhs.parentObjectSleutel).append(parentObjectElementId, rhs.parentObjectElementId).isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(objectSleutel, voorkomenSleutel, groepElementId, objectElementId, parentObjectSleutel, parentObjectElementId);
    }

    @Override
    public String toString() {
        return "BlobRecord{" + "parentObjectSleutel=" + parentObjectSleutel + ", parentObjectElementId=" + parentObjectElementId + ", objectElementId="
                + objectElementId + ", objectSleutel=" + objectSleutel + ", groepElementId=" + groepElementId + ", voorkomenSleutel=" + voorkomenSleutel
                + ", actieInhoud=" + actieInhoud + ", actieVerval=" + actieVerval + ", nadereAanduidingVerval=" + nadereAanduidingVerval
                + ", actieAanpassingGeldigheid=" + actieAanpassingGeldigheid + ", datumAanvangGeldigheid=" + datumAanvangGeldigheid + ", datumEindeGeldigheid="
                + datumEindeGeldigheid + ", indicatieTbvLeveringMutaties=" + indicatieTbvLeveringMutaties + ", actieVervalTbvLeveringMutaties="
                + actieVervalTbvLeveringMutaties + ", attributen=" + attributen + '}';
    }
}
