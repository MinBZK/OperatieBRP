/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;

/**
 * De class representeert een uniek verwijzing naar een element uit Tabel 41 (Reden ontbinding/nietig verklaring).
 *
 * Deze class is immutable en threadsafe.
 */
public final class Lo3RedenOntbindingHuwelijkOfGpCode extends AbstractLo3Element {

    /**
     * In BRP worden relaties 'beeindigd' als ze worden omgezet. Deze code wordt daarvoor gebruikt.
     */
    public static final Lo3RedenOntbindingHuwelijkOfGpCode OMZETTING_VERBINTENIS = new Lo3RedenOntbindingHuwelijkOfGpCode("Z");
    /**
     * In BRP worden relaties 'beeindigd' als de persoon is overleden. Deze code wordt daarvoor gebruikt.
     */
    public static final Lo3RedenOntbindingHuwelijkOfGpCode OVERLIJDEN = new Lo3RedenOntbindingHuwelijkOfGpCode("O");

    private static final long serialVersionUID = 1L;

    /**
     * Maakt een nieuw Lo3RedenOntbindingHuwelijkOfGpCode object.
     * @param waarde de unieke verwijzing in tabel 41
     */
    public Lo3RedenOntbindingHuwelijkOfGpCode(final String waarde) {
        this(waarde, null);
    }

    /**
     * Maakt een nieuw Lo3RedenOntbindingHuwelijkOfGpCode object met onderzoek.
     * @param waarde de unieke verwijzing in tabel 41
     * @param onderzoek het onderzoek waar deze code onder valt. Mag NULL zijn.
     */
    public Lo3RedenOntbindingHuwelijkOfGpCode(@Element(name = "waarde", required = false) final String waarde, @Element(name = "onderzoek",
            required = false) final Lo3Onderzoek onderzoek) {
        super(waarde, onderzoek);
    }
}
