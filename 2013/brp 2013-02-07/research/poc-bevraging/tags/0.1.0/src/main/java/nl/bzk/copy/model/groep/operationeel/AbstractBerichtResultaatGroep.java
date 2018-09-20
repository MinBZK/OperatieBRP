/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.*;

import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.BerichtResultaatGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Bijhoudingsresultaat;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Verwerkingsresultaat;

/**
 * .
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
     *
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
