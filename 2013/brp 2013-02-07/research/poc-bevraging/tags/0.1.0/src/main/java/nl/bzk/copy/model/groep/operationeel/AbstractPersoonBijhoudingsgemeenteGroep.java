/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.Datum;
import nl.bzk.copy.model.attribuuttype.JaNee;
import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonBijhoudingsgemeenteGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Partij;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * Implementatie voor groep persoon bijhoudingsgemeente.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonBijhoudingsgemeenteGroep extends AbstractGroep implements
        PersoonBijhoudingsgemeenteGroepBasis
{

    @ManyToOne
    @JoinColumn(name = "bijhgem")
    @Fetch(FetchMode.JOIN)
    private Partij bijhoudingsgemeente;

    @Column(name = "indonverwdocaanw")
    @Type(type = "JaNee")
    private JaNee indOnverwerktDocumentAanwezig;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "datinschringem"))
    private Datum datumInschrijvingInGemeente;

    /**
     * copy constructor .
     *
     * @param groep .
     */
    protected AbstractPersoonBijhoudingsgemeenteGroep(
            final PersoonBijhoudingsgemeenteGroepBasis groep)
    {
        bijhoudingsgemeente = groep.getBijhoudingsgemeente();
        indOnverwerktDocumentAanwezig = groep.getIndOnverwerktDocumentAanwezig();
        datumInschrijvingInGemeente = groep.getDatumInschrijvingInGemeente();
    }

    /**
     * default constructor .
     */
    protected AbstractPersoonBijhoudingsgemeenteGroep() {
    }

    @Override
    public Partij getBijhoudingsgemeente() {
        return bijhoudingsgemeente;
    }

    @Override
    public JaNee getIndOnverwerktDocumentAanwezig() {
        return indOnverwerktDocumentAanwezig;
    }

    @Override
    public Datum getDatumInschrijvingInGemeente() {
        return datumInschrijvingInGemeente;
    }

    public void setBijhoudingsgemeente(final Partij bijhoudingsgemeente) {
        this.bijhoudingsgemeente = bijhoudingsgemeente;
    }

    public void setIndOnverwerktDocumentAanwezig(final JaNee indOnverwerktDocumentAanwezig) {
        this.indOnverwerktDocumentAanwezig = indOnverwerktDocumentAanwezig;
    }

    public void setDatumInschrijvingInGemeente(final Datum datumInschrijvingInGemeente) {
        this.datumInschrijvingInGemeente = datumInschrijvingInGemeente;
    }
}
