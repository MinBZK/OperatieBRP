/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.decorator;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereAanduidingVerval;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * Record decorator.
 */
public class RecordDecorator {

    private final MetaRecord metaRecord;

    /**
     * Constructor.
     * @param metaRecord te decoreren record
     */
    RecordDecorator(final MetaRecord metaRecord) {
        this.metaRecord = metaRecord;
    }

    /**
     * Geef de waarde van het gegeven attribuut.
     * @param element het attribuut
     * @param <T> type van de waarde van het attribuut
     * @return waarde van het gegeven attribuut
     */
    protected final <T> T getAttribuut(final AttribuutElement element) {
        final MetaAttribuut metaAttribuut = metaRecord.getAttribuut(element);
        return metaAttribuut == null ? null : metaAttribuut.getWaarde();
    }

    /**
     * Geef de voorkomensleutel van het record.
     * @return voorkomensleutel
     */
    public final long getVoorkomenSleutel() {
        return metaRecord.getVoorkomensleutel();
    }

    /**
     * Geef de indicatie tbv levering mutaties.
     * @return indicatie tbv levering mutaties
     */
    public final boolean isIndicatieTbvLeveringMutaties() {
        return Boolean.TRUE.equals(metaRecord.isIndicatieTbvLeveringMutaties());
    }

    /**
     * Geef de datum aanvang geldigheid.
     * @return datum aanvang geldigheid
     */
    public final Integer getDatumAanvangGeldigheid() {
        return metaRecord.getDatumAanvangGeldigheid();
    }

    /**
     * Geef de datum einde geldigheid.
     * @return datum einde geldigheid
     */
    public final Integer getDatumEindeGeldigheid() {
        return metaRecord.getDatumEindeGeldigheid();
    }

    /**
     * Geef het tijdstip registratie.
     * @return tijdstip registratie
     */
    public final ZonedDateTime getTijdstipRegistratie() {
        return metaRecord.getTijdstipRegistratieAttribuut();
    }

    /**
     * Geef het tijdstip verval.
     * @return tijdstip verval
     */
    public final ZonedDateTime getTijdstipVerval() {
        return metaRecord.getDatumTijdVervalAttribuut();
    }

    /**
     * Geef de actie inhoud.
     * @return actie inhoud
     */
    public final Actie getActieInhoud() {
        return metaRecord.getActieInhoud();
    }

    /**
     * Geef de actie aanpassing geldigheid.
     * @return actie aanpassing geldigheid
     */
    public final Actie getActieAanpassingGeldigheid() {
        return metaRecord.getActieAanpassingGeldigheid();
    }

    /**
     * Geef de actie verval.
     * @return actie verval
     */
    public final Actie getActieVerval() {
        return metaRecord.getActieVerval();
    }

    /**
     * Geef de actie verval tbv levering mutaties.
     * @return actie verval tbv levering mutaties
     */
    public final Actie getActieVervalTbvLeveringMutaties() {
        return metaRecord.getActieVervalTbvLeveringMutaties();
    }

    /**
     * Geef de nadere aanduiding verval.
     * @return nadere aanduiding verval
     */
    public final NadereAanduidingVerval getNadereAanduidingVerval() {

        final String nadereAanduidingVervalCode = metaRecord.getNadereAanduidingVerval();
        if (nadereAanduidingVervalCode == null) {
            return null;
        }
        return NadereAanduidingVerval.parseCode(nadereAanduidingVervalCode);
    }

}
