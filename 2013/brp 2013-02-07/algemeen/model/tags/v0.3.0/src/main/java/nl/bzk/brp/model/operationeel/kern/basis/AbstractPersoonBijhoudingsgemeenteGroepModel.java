/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonBijhoudingsgemeenteGroepBasis;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Vorm van historie: beiden.
 * Motivatie voor de materi�le tijdslijn: de bijhoudingsgemeente kan op een eerder moment dan technisch verwerkt de
 * verantwoordelijke gemeente zijn (geworden). Of te wel: formele tijdslijn kan anders liggen dan materi�le tijdslijn.
 * Voor het OOK bestaan van datum inschrijving: zie modelleringsbeslissing aldaar. RvdP 10 jan 2012.
 *
 *
 *
 */
@MappedSuperclass
public abstract class AbstractPersoonBijhoudingsgemeenteGroepModel implements PersoonBijhoudingsgemeenteGroepBasis {

    @ManyToOne
    @JoinColumn(name = "Bijhgem")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij bijhoudingsgemeente;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatInschrInGem"))
    @JsonProperty
    private Datum  datumInschrijvingInGemeente;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndOnverwDocAanw"))
    @JsonProperty
    private JaNee  indicatieOnverwerktDocumentAanwezig;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonBijhoudingsgemeenteGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param bijhoudingsgemeente bijhoudingsgemeente van Bijhoudingsgemeente.
     * @param datumInschrijvingInGemeente datumInschrijvingInGemeente van Bijhoudingsgemeente.
     * @param indicatieOnverwerktDocumentAanwezig indicatieOnverwerktDocumentAanwezig van Bijhoudingsgemeente.
     */
    public AbstractPersoonBijhoudingsgemeenteGroepModel(final Partij bijhoudingsgemeente,
            final Datum datumInschrijvingInGemeente, final JaNee indicatieOnverwerktDocumentAanwezig)
    {
        this.bijhoudingsgemeente = bijhoudingsgemeente;
        this.datumInschrijvingInGemeente = datumInschrijvingInGemeente;
        this.indicatieOnverwerktDocumentAanwezig = indicatieOnverwerktDocumentAanwezig;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonBijhoudingsgemeenteGroep te kopieren groep.
     */
    public AbstractPersoonBijhoudingsgemeenteGroepModel(
            final PersoonBijhoudingsgemeenteGroep persoonBijhoudingsgemeenteGroep)
    {
        this.bijhoudingsgemeente = persoonBijhoudingsgemeenteGroep.getBijhoudingsgemeente();
        this.datumInschrijvingInGemeente = persoonBijhoudingsgemeenteGroep.getDatumInschrijvingInGemeente();
        this.indicatieOnverwerktDocumentAanwezig =
            persoonBijhoudingsgemeenteGroep.getIndicatieOnverwerktDocumentAanwezig();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getBijhoudingsgemeente() {
        return bijhoudingsgemeente;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumInschrijvingInGemeente() {
        return datumInschrijvingInGemeente;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatieOnverwerktDocumentAanwezig() {
        return indicatieOnverwerktDocumentAanwezig;
    }

}
