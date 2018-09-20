/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols.solvers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.expressies.literals.StringLiteralExpressie;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonIndicatieStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonHisMoment;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel;

/**
 * Getter voor soort van de verantwoording van indicatie
 * 'indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingVerval.soort' in objecttype 'Persoon'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class IndicatieBijzondereVerblijfsrechtelijkePositieVerantwoordingvervalSoortGetter implements AttributeGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie getAttribuutWaarde(final BrpObject brpObject) {
        Expressie resultaat = NullValue.getInstance();
        if (brpObject instanceof PersoonHisMoment) {
            final Attribuut attribuut = getAttribuut(brpObject);
            if (attribuut != null) {
                resultaat = new StringLiteralExpressie(attribuut.getWaarde());
            }
        } else if (brpObject instanceof PersoonHisVolledig) {
            final List<Attribuut> attributen = getHistorischeAttributen(brpObject);
            if (attributen != null) {
                final List<Expressie> elementen = new ArrayList<Expressie>();
                for (Attribuut attribuut : attributen) {
                    if (attribuut != null) {
                        elementen.add(new StringLiteralExpressie(attribuut.getWaarde()));
                    }
                }
                resultaat = new LijstExpressie(elementen);
            }
        }
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Attribuut getAttribuut(final BrpObject brpObject) {
        Attribuut resultaat = null;
        if (brpObject instanceof PersoonHisMoment) {
            final PersoonHisMoment persoonHisMoment = (PersoonHisMoment) brpObject;
            final PersoonIndicatie indicatie = persoonHisMoment.getIndicatieBijzondereVerblijfsrechtelijkePositie();
            if (indicatie != null) {
                final HisPersoonIndicatieStandaardGroep standaard = (HisPersoonIndicatieStandaardGroep) indicatie.getStandaard();
                if (standaard.getVerantwoordingVerval() != null) {
                    return standaard.getVerantwoordingVerval().getSoort();
                }
            }
        } else if (brpObject instanceof PersoonHisVolledig) {
            final PersoonHisVolledig p = (PersoonHisVolledig) brpObject;
            if (p.getIndicatieBijzondereVerblijfsrechtelijkePositie() != null) {
                final ActieModel actieModel =
                        p.getIndicatieBijzondereVerblijfsrechtelijkePositie().getPersoonIndicatieHistorie().getActueleRecord().getVerantwoordingVerval();
                if (actieModel != null) {
                    resultaat = actieModel.getSoort();
                }
            }
        }
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Attribuut> getHistorischeAttributen(final BrpObject brpObject) {
        final List<Attribuut> attributen = new ArrayList<Attribuut>();
        if (brpObject instanceof PersoonHisVolledig) {
            final PersoonHisVolledig p = (PersoonHisVolledig) brpObject;
            if (p.getIndicatieBijzondereVerblijfsrechtelijkePositie() != null) {
                final Iterator<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel> iterator =
                        p.getIndicatieBijzondereVerblijfsrechtelijkePositie().getPersoonIndicatieHistorie().iterator();
                while (iterator.hasNext()) {
                    final ActieModel actieModel = iterator.next().getVerantwoordingVerval();
                    if (actieModel != null) {
                        attributen.add(actieModel.getSoort());
                    }
                }
            }
        }
        return attributen;
    }

}
