/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;

/**
 * Wrapper class voor DienstbundelGroepAttribuut, benodigd voor de 'kruislijst' om attributen van een groep te
 * selecteren.
 */
public final class DienstbundelGroepAttribuutView extends DienstbundelGroepAttribuut {

    private static final long serialVersionUID = 1L;
    private Boolean actief;

    /**
     * Default constructor.
     *
     * @param dienstbundelGroepAttribuut
     *            Het dienstbundelgroepattribuut
     * @param actief
     *            De indicatie of het attribuut actief is
     */
    public DienstbundelGroepAttribuutView(final DienstbundelGroepAttribuut dienstbundelGroepAttribuut, final Boolean actief) {
        super(dienstbundelGroepAttribuut.getDienstbundelGroep(), dienstbundelGroepAttribuut.getAttribuut());
        setId(dienstbundelGroepAttribuut.getId());
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
    public DienstbundelGroepAttribuut getEntityObject() {
        final DienstbundelGroepAttribuut resultaat = new DienstbundelGroepAttribuut(getDienstbundelGroep(), getAttribuut());
        resultaat.setId(getId());
        return resultaat;
    }
}
