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
import nl.bzk.brp.expressietaal.expressies.literals.GetalLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonIndicatieStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonHisMoment;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBehandeldAlsNederlanderModel;

/**
 * Getter voor partij van de verantwoording van indicatie
 * 'indicatie.behandeld_als_nederlander.verantwoordingAanpassingGeldigheid.partij' in objecttype 'Persoon'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class IndicatieBehandeldAlsNederlanderVerantwoordingaanpassinggeldigheidPartijGetter implements AttributeGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie getAttribuutWaarde(final BrpObject brpObject) {
        Expressie resultaat = NullValue.getInstance();
        if (brpObject instanceof PersoonHisMoment) {
            final Attribuut attribuut = getAttribuut(brpObject);
            if (attribuut != null) {
                resultaat = new GetalLiteralExpressie(((Partij) attribuut.getWaarde()).getCode().getWaarde());
            }
        } else if (brpObject instanceof PersoonHisVolledig) {
            final List<Attribuut> attributen = getHistorischeAttributen(brpObject);
            if (attributen != null) {
                final List<Expressie> elementen = new ArrayList<Expressie>();
                for (Attribuut attribuut : attributen) {
                    if (attribuut != null) {
                        elementen.add(new GetalLiteralExpressie(((Partij) attribuut.getWaarde()).getCode().getWaarde()));
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
            final PersoonIndicatie indicatie = persoonHisMoment.getIndicatieBehandeldAlsNederlander();
            if (indicatie != null) {
                final HisPersoonIndicatieStandaardGroep standaard = (HisPersoonIndicatieStandaardGroep) indicatie.getStandaard();
                if (standaard.getVerantwoordingAanpassingGeldigheid() != null) {
                    return standaard.getVerantwoordingAanpassingGeldigheid().getPartij();
                }
            }
        } else if (brpObject instanceof PersoonHisVolledig) {
            final PersoonHisVolledig p = (PersoonHisVolledig) brpObject;
            if (p.getIndicatieBehandeldAlsNederlander() != null) {
                final ActieModel actieModel =
                        p.getIndicatieBehandeldAlsNederlander().getPersoonIndicatieHistorie().getActueleRecord().getVerantwoordingAanpassingGeldigheid();
                if (actieModel != null) {
                    resultaat = actieModel.getPartij();
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
            if (p.getIndicatieBehandeldAlsNederlander() != null) {
                final Iterator<HisPersoonIndicatieBehandeldAlsNederlanderModel> iterator =
                        p.getIndicatieBehandeldAlsNederlander().getPersoonIndicatieHistorie().iterator();
                while (iterator.hasNext()) {
                    final ActieModel actieModel = iterator.next().getVerantwoordingAanpassingGeldigheid();
                    if (actieModel != null) {
                        attributen.add(actieModel.getPartij());
                    }
                }
            }
        }
        return attributen;
    }

}
