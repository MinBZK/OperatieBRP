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
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.logisch.kern.PersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonAfgeleidAdministratiefGroepBasis;


/**
 *
 *
 */
@MappedSuperclass
public abstract class AbstractPersoonAfgeleidAdministratiefGroepModel implements
        PersoonAfgeleidAdministratiefGroepBasis
{

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "TsLaatsteWijz"))
    @JsonProperty
    private DatumTijd tijdstipLaatsteWijziging;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndGegevensInOnderzoek"))
    @JsonProperty
    private JaNee     indicatieGegevensInOnderzoek;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonAfgeleidAdministratiefGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param tijdstipLaatsteWijziging tijdstipLaatsteWijziging van Afgeleid administratief.
     * @param indicatieGegevensInOnderzoek indicatieGegevensInOnderzoek van Afgeleid administratief.
     */
    public AbstractPersoonAfgeleidAdministratiefGroepModel(final DatumTijd tijdstipLaatsteWijziging,
            final JaNee indicatieGegevensInOnderzoek)
    {
        this.tijdstipLaatsteWijziging = tijdstipLaatsteWijziging;
        this.indicatieGegevensInOnderzoek = indicatieGegevensInOnderzoek;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonAfgeleidAdministratiefGroep te kopieren groep.
     */
    public AbstractPersoonAfgeleidAdministratiefGroepModel(
            final PersoonAfgeleidAdministratiefGroep persoonAfgeleidAdministratiefGroep)
    {
        this.tijdstipLaatsteWijziging = persoonAfgeleidAdministratiefGroep.getTijdstipLaatsteWijziging();
        this.indicatieGegevensInOnderzoek = persoonAfgeleidAdministratiefGroep.getIndicatieGegevensInOnderzoek();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijd getTijdstipLaatsteWijziging() {
        return tijdstipLaatsteWijziging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatieGegevensInOnderzoek() {
        return indicatieGegevensInOnderzoek;
    }

}
