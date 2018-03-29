/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common;

import java.util.Scanner;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Notatie voor een naar Toegangleveringsautorisatie en Dienst.
 *
 * Notatie: X (waarbij X = referentie naar Toegangleveringsautorisatie en X naar Dienst)
 * Notatie: X/Y (waarbij X = referentie naar Toegangleveringsautorisatie en Y naar Dienst)
 */
public class DienstSleutel {

    private final String waarde;
    private final String toegangRef;
    private final String dienstRef;

    /**
     * Constructor.
     * @param waarde de sleutelwaarde
     */
    public DienstSleutel(String waarde) {
        if (waarde == null) {
            throw new NullPointerException("DienstSleutel waarde niet gevuld");
        }
        this.waarde = waarde;
        if (waarde.contains("/")) {
            try (final Scanner scanner = new Scanner(waarde).useDelimiter("/")) {
                toegangRef = scanner.next();
                dienstRef = scanner.next();
            }
        } else {
            toegangRef = dienstRef = waarde;
        }
    }

    public String getToegangRef() {
        return toegangRef;
    }

    public String getDienstRef() {
        return dienstRef;
    }

    public String getWaarde() {
        return waarde;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("waarde", waarde)
                .append("toegangRef", toegangRef)
                .append("dienstRef", dienstRef)
                .toString();
    }
}
