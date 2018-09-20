/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;


/**
 * HisVolledig klasse voor Relatie.
 */
@Entity
@Table(schema = "Kern", name = "Relatie")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Srt", discriminatorType = DiscriminatorType.INTEGER)
public abstract class RelatieHisVolledigImpl extends AbstractRelatieHisVolledigImpl implements HisVolledigImpl,
    RelatieHisVolledig, ALaagAfleidbaar, ElementIdentificeerbaar
{

    @Transient
    private Verwerkingssoort verwerkingssoort;

    /**
     * Default contructor voor JPA.
     */
    protected RelatieHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Relatie.
     */
    public RelatieHisVolledigImpl(final SoortRelatieAttribuut soort) {
        super(soort);
    }

    @Override
    public KindHisVolledigImpl getKindBetrokkenheid() {
        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : getBetrokkenheden()) {
            if (betrokkenheidHisVolledig instanceof KindHisVolledigImpl) {
                return (KindHisVolledigImpl) betrokkenheidHisVolledig;
            }
        }

        return null;
    }

    @Override
    public Set<OuderHisVolledigImpl> getOuderBetrokkenheden() {
        final Set<OuderHisVolledigImpl> ouders = new HashSet<>();
        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : getBetrokkenheden()) {
            if (betrokkenheidHisVolledig instanceof OuderHisVolledigImpl) {
                ouders.add((OuderHisVolledigImpl) betrokkenheidHisVolledig);
            }
        }

        return ouders;
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
