/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AntwoordFormaatType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesUitBrpVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Lees uit BRP verzoek bericht.
 */
public final class LeesUitBrpVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {

    private static final long serialVersionUID = 1L;

    private final LeesUitBrpVerzoekType leesUitBrpVerzoekType;

    /**
     * Default constructor.
     */
    public LeesUitBrpVerzoekBericht() {
        this(new LeesUitBrpVerzoekType());

    }

    /**
     * JAXB constructor.
     * @param leesUitBrpVerzoekType Het lees uit BRP verzoek type {@link LeesUitBrpVerzoekType}
     */
    public LeesUitBrpVerzoekBericht(final LeesUitBrpVerzoekType leesUitBrpVerzoekType) {
        super("LeesUitBrpVerzoek");
        this.leesUitBrpVerzoekType = leesUitBrpVerzoekType;
        if (leesUitBrpVerzoekType.getAntwoordFormaat() == null) {
            leesUitBrpVerzoekType.setAntwoordFormaat(AntwoordFormaatType.LO_3);
        }
    }

    /**
     * Convenience constructor.
     * @param aNummer anummer
     */
    public LeesUitBrpVerzoekBericht(final String aNummer) {
        this();
        setANummer(aNummer);
    }

    /**
     * Convenience constructor.
     * @param aNummer anummer
     * @param antwoordFormaat antwoordformaat
     */
    public LeesUitBrpVerzoekBericht(final String aNummer, final AntwoordFormaatType antwoordFormaat) {
        this();
        setANummer(aNummer);
        setAntwoordFormaat(antwoordFormaat);
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createLeesUitBrpVerzoek(leesUitBrpVerzoekType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft het A-nummer op het bericht terug.
     * @return Het A-nummer op het bericht.
     */
    public String getANummer() {
        return leesUitBrpVerzoekType.getANummer();
    }

    /**
     * Zet de waarde van a nummer.
     * @param aNummer a-nummer
     */
    public void setANummer(final String aNummer) {
        leesUitBrpVerzoekType.setANummer(aNummer);
    }

    /**
     * Geeft het gewenste antwoordFormaat terug.
     * @return Het AntwoordFormaatType.
     */
    public AntwoordFormaatType getAntwoordFormaat() {
        return leesUitBrpVerzoekType.getAntwoordFormaat();
    }

    /**
     * Zet de waarde van antwoord formaat.
     * @param antwoordFormaat antwoord formaat
     */
    public void setAntwoordFormaat(final AntwoordFormaatType antwoordFormaat) {
        leesUitBrpVerzoekType.setAntwoordFormaat(antwoordFormaat);
    }

    /**
     * Geef de waarde van technische sleutel.
     * @return technische sleutel
     */
    public String getTechnischeSleutel() {
        return leesUitBrpVerzoekType.getTechnischeSleutel();
    }

    /**
     * Zet de waarde van technische sleutel.
     * @param technischeSleutel technische sleutel
     */
    public void setTechnischeSleutel(final String technischeSleutel) {
        leesUitBrpVerzoekType.setTechnischeSleutel(technischeSleutel);
    }

}
