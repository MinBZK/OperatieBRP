/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.VerantwoordingComparator;


/**
 * HisVolledig klasse voor Persoon.
 */
@Entity
@Table(schema = "Kern", name = "Pers")
public class PersoonHisVolledigImpl extends AbstractPersoonHisVolledigImpl implements HisVolledigImpl,
    PersoonHisVolledig, ALaagAfleidbaar, ElementIdentificeerbaar
{

    @Transient
    @JsonProperty
    private List<AdministratieveHandelingHisVolledigImpl> administratieveHandelingen;

    @Transient
    private Verwerkingssoort verwerkingssoort;

    /**
     * Default contructor voor JPA.
     */
    protected PersoonHisVolledigImpl() {
        super();
        administratieveHandelingen = new ArrayList<>();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Persoon.
     */
    public PersoonHisVolledigImpl(final SoortPersoonAttribuut soort) {
        super(soort);
        administratieveHandelingen = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<AdministratieveHandelingHisVolledigImpl> getAdministratieveHandelingen() {
        if (administratieveHandelingen != null) {
            Collections.sort(administratieveHandelingen, new VerantwoordingComparator());
        }
        return administratieveHandelingen;
    }

    /**
     * Voegt de verantwoordingsinfo toe. Dit gebruiken we omdat we dit in de blob opslaan. LET OP! We maken hier altijd een nieuwe lijst aan. Dit is omdat
     * hibernate vanuit zijn cache entiteiten op kan halen die de verantwoordingsinfo al zouden kunnen hebben. Om de verantwoording niet dubbel toe te
     * voegen beginnen we dus altijd met een lege lijst.
     *
     * @param handelingen De adinistratieve handelingen.
     */
    public final void vulAanMetAdministratieveHandelingen(final List<AdministratieveHandelingHisVolledigImpl> handelingen) {
        administratieveHandelingen = new ArrayList<>(handelingen);
    }

    @Override
    public final KindHisVolledigImpl getKindBetrokkenheid() {
        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledigImpl : getBetrokkenheden()) {
            if (betrokkenheidHisVolledigImpl instanceof KindHisVolledigImpl) {
                return (KindHisVolledigImpl) betrokkenheidHisVolledigImpl;
            }
        }
        return null;
    }

    @Override
    public final Set<OuderHisVolledigImpl> getOuderBetrokkenheden() {
        final Set<OuderHisVolledigImpl> resultaat = new HashSet<>();
        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledigImpl : getBetrokkenheden()) {
            if (betrokkenheidHisVolledigImpl instanceof OuderHisVolledigImpl) {
                resultaat.add((OuderHisVolledigImpl) betrokkenheidHisVolledigImpl);
            }
        }
        return resultaat;
    }

    @Override
    public final Set<PartnerHisVolledigImpl> getPartnerBetrokkenheden() {
        final Set<PartnerHisVolledigImpl> resultaat = new HashSet<>();
        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledigImpl : getBetrokkenheden()) {
            if (betrokkenheidHisVolledigImpl instanceof PartnerHisVolledigImpl) {
                resultaat.add((PartnerHisVolledigImpl) betrokkenheidHisVolledigImpl);
            }
        }
        return resultaat;
    }

    @Override
    public final Set<HuwelijkGeregistreerdPartnerschapHisVolledigImpl> getHuwelijkGeregistreerdPartnerschappen() {
        final Set<HuwelijkGeregistreerdPartnerschapHisVolledigImpl> hgps = new HashSet<>();
        for (final PartnerHisVolledigImpl partnerHisVolledig : getPartnerBetrokkenheden()) {
            hgps.add((HuwelijkGeregistreerdPartnerschapHisVolledigImpl) partnerHisVolledig.getRelatie());
        }

        return hgps;
    }

    @Override
    public Verwerkingssoort getVerwerkingssoort() {
        return verwerkingssoort;
    }


    @Override
    public void setVerwerkingssoort(final Verwerkingssoort verwerkingssoort) {
        this.verwerkingssoort = verwerkingssoort;
    }

}
