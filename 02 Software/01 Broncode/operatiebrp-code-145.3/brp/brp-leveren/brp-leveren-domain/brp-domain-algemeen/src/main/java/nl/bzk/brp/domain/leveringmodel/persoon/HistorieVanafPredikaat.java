/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import com.google.common.base.Predicate;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import org.springframework.util.Assert;

/**
 * Predikaat dat kan worden toegepast op materiele record en Onderzoek Standaard Groep. Er kan een moment in de tijd worden gegeven waar vanaf wordt
 * gekeken naar de voorkomens van een Persoon.
 */
@Bedrijfsregel(Regel.R1544)
public final class HistorieVanafPredikaat implements Predicate<MetaRecord> {

    private final Integer leverenVanafMoment;

    /**
     * Constructor.
     * @param vanaf tijdstip in het verleden van waaraf voorkomens worden getoond
     */
    private HistorieVanafPredikaat(final Integer vanaf) {
        Assert.notNull(vanaf, "Tijdstip vanaf mag niet null zijn.");
        if (DatumUtil.valtDatumBinnenPeriode(vanaf, DatumUtil.morgen(), null)) {
            throw new GlazenbolException("Kan niet naar een moment in de toekomst kijken");
        }
        this.leverenVanafMoment = vanaf;
    }

    /**
     * Static factory methode. Geeft alle waardes door aan de constructor.
     * @param datumVanaf tijdstip in het verleden van waaraf voorkomens worden getoond
     * @return nieuwe predikaat instantie
     */
    public static HistorieVanafPredikaat geldigOpEnNa(final Integer datumVanaf) {
        return new HistorieVanafPredikaat(datumVanaf);
    }

    @Override
    public boolean apply(final MetaRecord record) {
        final boolean resultaat;
        if (record != null && record.getParentGroep().getGroepElement().isMaterieel()) {
            final Integer datumEindeGeldigheid = record.getDatumEindeGeldigheid();
            resultaat = isMaterieelVanafGeldig(datumEindeGeldigheid);
        } else {
            resultaat = true;
        }
        return resultaat;
    }

    /**
     * Controleert of het record geldig is, dwz. zijn er zichtbare records als deze tegen de datum aanvang materiele periode worden gehouden.
     * @param eindeDatum de einde datum
     * @return true als datum aanvang materiele periode geldig is, anders false.
     */
    private boolean isMaterieelVanafGeldig(final Integer eindeDatum) {
        return eindeDatum == null || DatumUtil.valtDatumBinnenPeriode(leverenVanafMoment, null, eindeDatum);
    }

    /**
     * Geeft het leveren-vanaf-moment terug.
     * @return het leveren vanaf moment
     */
    Integer getLeverenVanafMoment() {
        return leverenVanafMoment;
    }

}
