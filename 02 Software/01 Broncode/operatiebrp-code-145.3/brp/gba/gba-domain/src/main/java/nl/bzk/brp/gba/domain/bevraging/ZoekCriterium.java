/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.domain.bevraging;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.Normalizer;
import java.util.regex.Pattern;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;

/**
 * Zoek criterium.
 */
@JsonAutoDetect
@JsonInclude(Include.NON_NULL)
public final class ZoekCriterium {

    private String naam;

    @JsonIgnore
    private AttribuutElement attribuut;

    @JsonProperty
    private String waarde;

    @JsonIgnore
    private String slimZoekenWaarde;

    @JsonIgnore
    private boolean exact;

    @JsonIgnore
    private boolean wildcard;

    @JsonIgnore
    private boolean caseInsensitive;

    @JsonIgnore
    private boolean diakriet;

    @JsonProperty
    private ZoekCriterium of;

    /**
     * Constructor.
     * @param naam zoek attribuut element naam
     */
    @JsonCreator
    public ZoekCriterium(@JsonProperty("naam") final String naam) {
        super();
        this.naam = naam;
        this.attribuut = ElementHelper.getAttribuutElement(naam);
    }

    public String getNaam() {
        return naam;
    }

    public String getWaarde() {
        return waarde;
    }

    public void setWaarde(final String waarde) {
        this.waarde = waarde;
        if (waarde == null) {
            this.slimZoekenWaarde = null;
        } else if (waarde.startsWith("\\")) {
            this.exact = true;
            this.slimZoekenWaarde = waarde.substring(1);
        } else if (waarde.endsWith("*")) {
            this.wildcard = true;
            this.slimZoekenWaarde = waarde.substring(0, waarde.length() - 1);
        } else {
            this.slimZoekenWaarde = waarde;
        }

        if (waarde != null && !this.exact) {
            if (!waarde.matches(".*[A-Z].*") && attribuut.isString()) {
                this.caseInsensitive = true;
            }
            String normalizedWaarde = Normalizer.normalize(waarde, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            if (pattern.matcher(normalizedWaarde).find()) {
                this.diakriet = true;
            }
        }
    }

    /**
     * Geef de zoekoptie nodig in brp
     * @param slimzoeken indicatie voor slimzoeken
     * @return de zoekoptie
     */
    public Zoekoptie getZoekOptie(final boolean slimzoeken) {
        Zoekoptie resultaat;
        if (waarde == null) {
            resultaat = Zoekoptie.LEEG;
        } else {
            StringBuilder optieBuilder = new StringBuilder();
            if (isZoekenVanaf(slimzoeken)) {
                optieBuilder.append("VANAF_");
            }
            if (isExactZoeken(slimzoeken)) {
                optieBuilder.append("EXACT");
            } else {
                optieBuilder.append("KLEIN");
            }
            resultaat = Zoekoptie.valueOf(optieBuilder.toString());
        }
        return resultaat;
    }

    private boolean isZoekenVanaf(final boolean slimzoeken) {
        return slimzoeken && wildcard;
    }

    private boolean isExactZoeken(final boolean slimzoeken) {
        return !(slimzoeken && caseInsensitive && !diakriet);
    }

    public String getSlimZoekenWaarde() {
        return slimZoekenWaarde;
    }

    public ZoekCriterium getOf() {
        return of;
    }

    public void setOf(final ZoekCriterium of) {
        this.of = of;
    }

    @Override
    public String toString() {
        return String.format("<Naam: %s, Waarde: %s", getNaam(), getWaarde()) + (getOf() == null ? ">" : String.format(", Of: %s>", getOf()));
    }


}
