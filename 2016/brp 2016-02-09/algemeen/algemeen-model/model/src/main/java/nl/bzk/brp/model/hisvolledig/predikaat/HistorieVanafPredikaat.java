/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieleHistorieModel;
import nl.bzk.brp.model.hisvolledig.GlazenbolException;
import nl.bzk.brp.model.logisch.kern.HisOnderzoekStandaardGroep;
import org.apache.commons.collections.Predicate;

/**
 * Predikaat dat kan worden toegepast op instanties van {@link MaterieelHistorisch} en {@link HisOnderzoekStandaardGroep}.
 * Er kan een moment in de tijd worden gegeven waar vanaf wordt gekeken naar de voorkomens van een Persoon.
 *
 * @brp.bedrijfsregel R1544
 */
@Regels(Regel.R1544)
public final class HistorieVanafPredikaat implements Predicate {

    private final DatumAttribuut leverenVanafMoment;

    /**
     * Constructor.
     *
     * @param vanaf tijdstip in het verleden van waaraf voorkomens worden getoond
     */
    public HistorieVanafPredikaat(final DatumAttribuut vanaf) {
        if (vanaf != null && vanaf.na(DatumAttribuut.vandaag())) {
            throw new GlazenbolException("Kan niet naar een moment in de toekomst kijken");
        }
        this.leverenVanafMoment = vanaf;
    }

    /**
     * Static factory methode. Geeft alle waardes door aan de constructor.
     *
     * @param datumVanaf tijdstip in het verleden van waaraf voorkomens worden getoond
     * @return nieuwe predikaat instantie
     */
    public static HistorieVanafPredikaat geldigOpEnNa(final DatumAttribuut datumVanaf)
    {
        return new HistorieVanafPredikaat(datumVanaf);
    }

    @Override
    public boolean evaluate(final Object object) {
        final boolean resultaat;
        if (leverenVanafMoment != null) {
            if (object instanceof MaterieelHistorisch) {
                final MaterieelHistorisch entiteit = (MaterieelHistorisch) object;
                final MaterieleHistorieModel materieleHistorie = entiteit.getMaterieleHistorie();
                final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid = materieleHistorie.getDatumEindeGeldigheid();
                resultaat = isMaterieelVanafGeldig(datumEindeGeldigheid);
            } else if (object instanceof HisOnderzoekStandaardGroep) {
                final HisOnderzoekStandaardGroep formeelHistorisch = (HisOnderzoekStandaardGroep) object;
                final DatumTijdAttribuut vervalDatum = formeelHistorisch.getDatumTijdVerval();
                final DatumEvtDeelsOnbekendAttribuut datumEinde = formeelHistorisch.getDatumEinde();
                resultaat = isOnderzoekGeldig(vervalDatum, datumEinde);
            } else {
                resultaat = true;
            }
        } else {
            resultaat = true;
        }
        return resultaat;
    }

    /**
     * Controleer of het onderzoek geldig is in relatie tot het leverenVanafMoment.
     *
     * @param vervalDatum verval datum van record
     * @param datumEinde datum einde van record
     * @return true als record geldig is, anders false
     */
    private boolean isOnderzoekGeldig(final DatumTijdAttribuut vervalDatum, final DatumEvtDeelsOnbekendAttribuut datumEinde) {
        final boolean vervalGeldig = vervalDatum == null || leverenVanafMoment.voorDatumSoepel(
            new DatumEvtDeelsOnbekendAttribuut(vervalDatum.naarDatum()));
        final boolean eindeGeldig = datumEinde == null || leverenVanafMoment.voorDatumSoepel(datumEinde);
        return vervalGeldig && eindeGeldig;
    }

    /**
     * Controleert of het record geldig is, dwz. zijn er zichtbare records als deze tegen de datum aanvang materiele periode worden gehouden.
     *
     * @param eindeDatum de einde datum
     * @return true als datum aanvang materiele periode geldig is, anders false.
     */
    private boolean isMaterieelVanafGeldig(final DatumEvtDeelsOnbekendAttribuut eindeDatum) {
        return eindeDatum == null || leverenVanafMoment.voorDatumSoepel(eindeDatum);
    }

    /**
     * Geeft het leveren-vanaf-moment terug.
     *
     * @return het leveren vanaf moment
     */
    public DatumAttribuut getLeverenVanafMoment() {
        return leverenVanafMoment;
    }

}
