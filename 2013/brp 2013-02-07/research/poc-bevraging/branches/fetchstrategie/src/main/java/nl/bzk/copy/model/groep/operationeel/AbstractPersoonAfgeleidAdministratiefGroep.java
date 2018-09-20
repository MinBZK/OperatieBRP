/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.DatumTijd;
import nl.bzk.copy.model.attribuuttype.JaNee;
import nl.bzk.copy.model.basis.AbstractGroep;
import nl.bzk.copy.model.groep.logisch.basis.PersoonAfgeleidAdministratiefGroepBasis;
import org.hibernate.annotations.Type;


/**
 * .
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonAfgeleidAdministratiefGroep extends AbstractGroep implements
        PersoonAfgeleidAdministratiefGroepBasis
{

    @Column(name = "indgegevensinonderzoek")
    @Type(type = "JaNee")
    private JaNee indGegevensInOnderzoek;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "tijdstipLaatsteWijz"))
    private DatumTijd tijdstipLaatsteWijziging;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractPersoonAfgeleidAdministratiefGroep() {
        super();
    }

    /**
     * .
     *
     * @param persoonAfgeleidAdministratiefGroepBasis
     *         PersoonAfgeleidAdministratiefGroepBasis
     */
    protected AbstractPersoonAfgeleidAdministratiefGroep(
            final PersoonAfgeleidAdministratiefGroepBasis persoonAfgeleidAdministratiefGroepBasis)
    {
        indGegevensInOnderzoek = persoonAfgeleidAdministratiefGroepBasis.getIndGegevensInOnderzoek();
        tijdstipLaatsteWijziging = persoonAfgeleidAdministratiefGroepBasis.getTijdstipLaatsteWijziging();
    }

    @Override
    public JaNee getIndGegevensInOnderzoek() {
        return indGegevensInOnderzoek;
    }

    @Override
    public DatumTijd getTijdstipLaatsteWijziging() {
        return tijdstipLaatsteWijziging;
    }

    public void setIndGegevensInOnderzoek(final JaNee indGegevensInOnderzoek) {
        this.indGegevensInOnderzoek = indGegevensInOnderzoek;
    }

    public void setTijdstipLaatsteWijziging(final DatumTijd tijdstipLaatsteWijziging) {
        this.tijdstipLaatsteWijziging = tijdstipLaatsteWijziging;
    }
}
