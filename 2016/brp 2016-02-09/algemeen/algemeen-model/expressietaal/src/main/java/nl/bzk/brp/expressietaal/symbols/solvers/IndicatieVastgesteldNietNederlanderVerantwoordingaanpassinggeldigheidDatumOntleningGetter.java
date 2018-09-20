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
import nl.bzk.brp.expressietaal.expressies.literals.DatumLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonIndicatieStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonHisMoment;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVastgesteldNietNederlanderModel;

/**
 * Getter voor datum ontlening van de verantwoording van indicatie
 * 'indicatie.vastgesteld_niet_nederlander.verantwoordingAanpassingGeldigheid.datum_ontlening' in objecttype 'Persoon'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class IndicatieVastgesteldNietNederlanderVerantwoordingaanpassinggeldigheidDatumOntleningGetter implements AttributeGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie getAttribuutWaarde(final BrpObject brpObject) {
        Expressie resultaat = NullValue.getInstance();
        if (brpObject instanceof PersoonHisMoment) {
            final Attribuut attribuut = getAttribuut(brpObject);
            if (attribuut != null) {
                resultaat = new DatumLiteralExpressie(attribuut.getWaarde());
            }
        } else if (brpObject instanceof PersoonHisVolledig) {
            final List<Attribuut> attributen = getHistorischeAttributen(brpObject);
            if (attributen != null) {
                final List<Expressie> elementen = new ArrayList<Expressie>();
                for (Attribuut attribuut : attributen) {
                    if (attribuut != null) {
                        elementen.add(new DatumLiteralExpressie(attribuut.getWaarde()));
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
            final PersoonIndicatie indicatie = persoonHisMoment.getIndicatieVastgesteldNietNederlander();
            if (indicatie != null) {
                final HisPersoonIndicatieStandaardGroep standaard = (HisPersoonIndicatieStandaardGroep) indicatie.getStandaard();
                if (standaard.getVerantwoordingAanpassingGeldigheid() != null) {
                    return standaard.getVerantwoordingAanpassingGeldigheid().getDatumOntlening();
                }
            }
        } else if (brpObject instanceof PersoonHisVolledig) {
            final PersoonHisVolledig p = (PersoonHisVolledig) brpObject;
            if (p.getIndicatieVastgesteldNietNederlander() != null) {
                final ActieModel actieModel =
                        p.getIndicatieVastgesteldNietNederlander()
                         .getPersoonIndicatieHistorie()
                         .getActueleRecord()
                         .getVerantwoordingAanpassingGeldigheid();
                if (actieModel != null) {
                    resultaat = actieModel.getDatumOntlening();
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
            if (p.getIndicatieVastgesteldNietNederlander() != null) {
                final Iterator<HisPersoonIndicatieVastgesteldNietNederlanderModel> iterator =
                        p.getIndicatieVastgesteldNietNederlander().getPersoonIndicatieHistorie().iterator();
                while (iterator.hasNext()) {
                    final ActieModel actieModel = iterator.next().getVerantwoordingAanpassingGeldigheid();
                    if (actieModel != null) {
                        attributen.add(actieModel.getDatumOntlening());
                    }
                }
            }
        }
        return attributen;
    }

}
