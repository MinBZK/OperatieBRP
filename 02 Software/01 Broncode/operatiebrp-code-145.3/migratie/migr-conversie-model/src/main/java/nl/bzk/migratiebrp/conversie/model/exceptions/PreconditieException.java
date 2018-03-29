/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.exceptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementList;
import nl.bzk.algemeenbrp.util.xml.annotation.Root;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Exception class voor Preconditie fouten van BRP -&lt; LO3.
 */
@Root
public final class PreconditieException extends RuntimeException {

    private static final long serialVersionUID = -1009061701852811149L;
    private final String preconditieNaam;
    private final List<String> groepen = new ArrayList<>();

    private PreconditieException(final SoortMeldingCode soortMeldingCode) {
        super(soortMeldingCode.name());
        preconditieNaam = soortMeldingCode.name();
    }

    /**
     * Constructor voor SimpleXML.
     * @param preconditieNaam de naam van de getriggerde preconditie
     * @param groepen de groepen die de preconditie triggerde
     */
    public PreconditieException(
            @Element(name = "naam") final String preconditieNaam,
            @ElementList(name = "groepen", entry = "groep", type = String.class, required = false) final List<String> groepen) {
        this.preconditieNaam = preconditieNaam;
        this.groepen.clear();
        this.groepen.addAll(groepen);
    }

    /**
     * Constructor.
     * @param preconditie de preconditie die getriggered is
     * @param groepNamen de BRP groep(en) die er voor zorgde dat de preconditie triggerde
     */
    public PreconditieException(final SoortMeldingCode preconditie, final String[] groepNamen) {
        this(preconditie);
        Collections.addAll(groepen, groepNamen);
    }

    /**
     * Constructor.
     * @param soortMeldingCode de soortMeldingCode die getriggered is
     * @param groepNaam de BRP groep naam die er voor zorgde dat de soortMeldingCode triggerde
     */
    public PreconditieException(final SoortMeldingCode soortMeldingCode, final String groepNaam) {
        this(soortMeldingCode);
        groepen.add(groepNaam);
    }

    /**
     * Geef de waarde van preconditie naam.
     * @return preconditie naam
     */
    @Element(name = "naam")
    public String getPreconditieNaam() {
        return preconditieNaam;
    }

    /**
     * Geef de waarde van groepen.
     * @return groepen
     */
    @ElementList(name = "groepen", entry = "groep", type = String.class, required = false)
    public List<String> getGroepen() {
        return groepen;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof PreconditieException)) {
            return false;
        }
        final PreconditieException castOther = (PreconditieException) other;
        return new EqualsBuilder().append(preconditieNaam, castOther.preconditieNaam).append(groepen, castOther.groepen).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(preconditieNaam).append(groepen).toHashCode();
    }
}
