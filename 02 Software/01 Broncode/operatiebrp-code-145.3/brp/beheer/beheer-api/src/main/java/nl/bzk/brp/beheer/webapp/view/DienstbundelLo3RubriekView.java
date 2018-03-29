/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;

/**
 * Wrapper class voor DienstbundelLo3Rubriek, benodigd voor de 'lijst' van LO3 rubrieken.
 */
public final class DienstbundelLo3RubriekView extends DienstbundelLo3Rubriek {

    private static final long serialVersionUID = 1L;
    private Boolean actief;

    /**
     * Default constructor.
     *
     * @param dienstbundelLo3Rubriek
     *            Het dienstbundelgroepattribuut
     * @param actief
     *            De indicatie of het attribuut actief is
     */
    public DienstbundelLo3RubriekView(final DienstbundelLo3Rubriek dienstbundelLo3Rubriek, final Boolean actief) {
        super(dienstbundelLo3Rubriek.getDienstbundel(), dienstbundelLo3Rubriek.getLo3Rubriek());
        setId(dienstbundelLo3Rubriek.getId());
        this.actief = actief;
    }

    /**
     * Zet de actief status.
     *
     * @param actief
     *            Indicatie of dit dienstbundelgroepattribuut actief is.
     */
    public void setActief(final Boolean actief) {
        this.actief = actief;
    }

    /**
     * Geeft de actief status.
     *
     * @return Indicatie of dit dienstbundelgroepattribuut actief is.
     */
    public Boolean isActief() {
        return this.actief;
    }

    /**
     * Convenience methode voor conversie naar JPA entity.
     *
     * @return De JPA entity
     */
    public DienstbundelLo3Rubriek getEntityObject() {
        final DienstbundelLo3Rubriek resultaat = new DienstbundelLo3Rubriek(getDienstbundel(), getLo3Rubriek());
        resultaat.setId(getId());
        return resultaat;
    }
}
