/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheidAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;


/**
 * HisVolledig klasse voor Betrokkenheid.
 */
@Entity
@Table(schema = "Kern", name = "Betr")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Rol", discriminatorType = DiscriminatorType.INTEGER)
public abstract class BetrokkenheidHisVolledigImpl extends AbstractBetrokkenheidHisVolledigImpl implements
    HisVolledigImpl, BetrokkenheidHisVolledig, ElementIdentificeerbaar
{

    @Transient
    private Verwerkingssoort verwerkingssoort;

    /**
     * Default contructor voor JPA.
     */
    protected BetrokkenheidHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param relatie relatie van Betrokkenheid.
     * @param rol     rol van Betrokkenheid.
     * @param persoon persoon van Betrokkenheid.
     */
    public BetrokkenheidHisVolledigImpl(final RelatieHisVolledigImpl relatie, final SoortBetrokkenheidAttribuut rol,
        final PersoonHisVolledigImpl persoon)
    {
        super(relatie, rol, persoon);
    }

    /**
     * Geef de verwerkingssoort terug.
     *
     * @return de verwerkingssoort
     */
    @Override
    public Verwerkingssoort getVerwerkingssoort() {
        return verwerkingssoort;
    }

    /**
     * Zet de verwerkingssoort.
     *
     * @param verwerkingssoort de verwerkingssoort
     */
    @Override
    public void setVerwerkingssoort(final Verwerkingssoort verwerkingssoort) {
        this.verwerkingssoort = verwerkingssoort;
    }

}
