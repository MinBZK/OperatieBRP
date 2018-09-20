/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.logisch.basis.BerichtResultaatGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Bijhoudingsresultaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Verwerkingsresultaat;

/**
 *.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractBerichtResultaatGroep extends AbstractGroep implements BerichtResultaatGroepBasis {

    @Column(name = "Verwerking")
    @Enumerated
    private Verwerkingsresultaat verwerkingsresultaat;

    @Column(name = "Bijhouding")
    @Enumerated
    private Bijhoudingsresultaat bijhoudingsresultaat;

    @Column(name = "HoogsteMeldingsniveau")
    @Enumerated
    private Soortmelding hoogsteMeldingNiveau;

    /**
     * Copy constructor.
     * @param berichtResultaatGroepBasis Bericht resultaat waarvan gekopieerd dient te worden.
     */
    public AbstractBerichtResultaatGroep(final BerichtResultaatGroepBasis berichtResultaatGroepBasis) {
        verwerkingsresultaat = berichtResultaatGroepBasis.getVerwerkingsresultaat();
        bijhoudingsresultaat = berichtResultaatGroepBasis.getBijhoudingsresultaat();
        hoogsteMeldingNiveau = berichtResultaatGroepBasis.getHoogsteMeldingNiveau();
    }

    /**
     * Default constructor.
     */
    public AbstractBerichtResultaatGroep() {
    }

    public Verwerkingsresultaat getVerwerkingsresultaat() {
        return verwerkingsresultaat;
    }

    public Bijhoudingsresultaat getBijhoudingsresultaat() {
        return bijhoudingsresultaat;
    }

    public Soortmelding getHoogsteMeldingNiveau() {
        return hoogsteMeldingNiveau;
    }
}
