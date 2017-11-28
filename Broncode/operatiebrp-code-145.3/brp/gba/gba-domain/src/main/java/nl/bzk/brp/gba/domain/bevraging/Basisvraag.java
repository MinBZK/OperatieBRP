/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.domain.bevraging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;

/**
 * Basis vraag (voor adres- en persoonvragen).
 */
@JsonInclude(Include.NON_NULL)
public class Basisvraag {

    @JsonProperty
    private String partijCode;

    @JsonProperty
    private List<ZoekCriterium> zoekCriteria;

    @JsonProperty
    private List<String> zoekRubrieken;

    @JsonProperty
    private List<String> gevraagdeRubrieken;

    @JsonProperty
    private SoortDienst soortDienst;

    @JsonIgnore
    private boolean slimZoekenStandaard = false;

    @JsonIgnore
    private boolean zoekenInHistorie = false;

    Basisvraag() {
        // Alleen instantieerbaar door subclasses.
    }

    /**
     * Geef partij code.
     * @return partij code
     */
    public final String getPartijCode() {
        return partijCode;
    }

    /**
     * Zet partij code.
     * @param partijCode partij code
     */
    public final void setPartijCode(final String partijCode) {
        this.partijCode = partijCode;
    }

    /**
     * Geef zoek criteria.
     * @return zoek criteria
     */
    public final List<ZoekCriterium> getZoekCriteria() {
        if (zoekCriteria == null) {
            zoekCriteria = new ArrayList<>();
        }
        return zoekCriteria;
    }

    /**
     * Zet zoek criteria.
     * @param zoekCriteria zoek criteria
     */
    public final void setZoekCriteria(final List<ZoekCriterium> zoekCriteria) {
        this.zoekCriteria = new ArrayList<>(zoekCriteria);
    }

    /**
     * Geef zoek rubrieken (tbv autorisatie).
     */
    public final List<String> getZoekRubrieken() {
        return zoekRubrieken;
    }

    /**
     * Zet zoek rubrieken (tbv autorisatie)
     * @param zoekRubrieken zoek rubrieken (tbv autorisatie)
     */
    public final void setZoekRubrieken(final List<String> zoekRubrieken) {
        this.zoekRubrieken = new ArrayList<>(zoekRubrieken);
    }

    /**
     * Geef gevraagde rubrieken.
     * @return gevraagde rubrieken
     */
    public final List<String> getGevraagdeRubrieken() {
        return gevraagdeRubrieken;
    }

    /**
     * Zet gevraagde rubrieken.
     * @param gevraagdeRubrieken gevraagde rubrieken
     */
    public final void setGevraagdeRubrieken(final List<String> gevraagdeRubrieken) {
        this.gevraagdeRubrieken = new ArrayList<>(gevraagdeRubrieken);
    }

    /**
     * Geef de soort dienst.
     * @return de soort dienst
     */
    public final SoortDienst getSoortDienst() {
        return soortDienst;
    }

    /**
     * Zet de soort dienst.
     * @param soortDienst de te zetten soort dienst
     */
    public final void setSoortDienst(final SoortDienst soortDienst) {
        this.soortDienst = soortDienst;
    }

    /**
     * Geef terug of er standaard slim wordt gezocht.
     * @return boolean of er standaard slim wordt gezocht
     */
    public final boolean isSlimZoekenStandaard() {
        return slimZoekenStandaard;
    }

    /**
     * Zet de indicatie dat slim zoeken standaard is.
     */
    public final void setSlimZoekenStandaard() {
        this.slimZoekenStandaard = true;
    }

    /**
     * Geef indicatie of er in de historie moet worden gezocht.
     * @return indicatie of er in de historie moet worden gezocht
     */
    public boolean isZoekenInHistorie() {
        return zoekenInHistorie;
    }

    /**
     * zet de indicatie dat er in de historie moet worden gezocht
     */
    public void setZoekenInHistorie() {
        zoekenInHistorie = true;
    }

    @Override
    public String toString() {
        return String
                .format("<SoortDienst: %s, PartijCode: %s, GevraagdeRubrieken: %s, ZoekRubrieken: %s, ZoekCriteria: %s>",
                        getSoortDienst(),
                        getPartijCode(),
                        getGevraagdeRubrieken(),
                        getZoekRubrieken(),
                        getZoekCriteria());
    }
}
