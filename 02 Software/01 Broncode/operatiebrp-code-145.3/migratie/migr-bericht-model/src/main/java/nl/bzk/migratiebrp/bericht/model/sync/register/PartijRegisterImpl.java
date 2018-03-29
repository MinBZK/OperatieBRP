/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementatie van Parij register
 */
public class PartijRegisterImpl implements PartijRegister {

    private static final long serialVersionUID = 1L;
    private final Map<String, Partij> partijenOpPartijCode = new HashMap<>();
    private final Map<String, Partij> partijenOpGemeenteCode = new HashMap<>();

    /**
     * Maak een partijregister op basis van lijst met partijen.
     * @param partijen partijen voor register
     */
    public PartijRegisterImpl(final List<Partij> partijen) {
        partijen.forEach(partij -> {
            this.partijenOpPartijCode.put(partij.getPartijCode(), partij);
            if (partij.getGemeenteCode() != null) {
                this.partijenOpGemeenteCode.put(partij.getGemeenteCode(), partij);
            }
        });
    }

    @Override
    public List<Partij> geefAllePartijen() {
        return new ArrayList<>(partijenOpPartijCode.values());
    }

    @Override
    public Partij zoekPartijOpPartijCode(final String partijCode) {
        return partijenOpPartijCode.get(partijCode);
    }

    @Override
    public Partij zoekPartijOpGemeenteCode(final String gemeenteCode) {
        return partijenOpGemeenteCode.get(gemeenteCode);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        partijenOpPartijCode.values().forEach(partij -> builder.append(partij.toString()));
        return builder.toString();
    }
}
