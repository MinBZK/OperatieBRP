/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkToevalligeGebeurtenisVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Verwerk toevallige gebeurtenis verzoek.
 */
public final class VerwerkToevalligeGebeurtenisVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final VerwerkToevalligeGebeurtenisVerzoekType verwerkToevallligeGebeurtenisVerzoekType;

    /**
     * Default constructor.
     */
    public VerwerkToevalligeGebeurtenisVerzoekBericht() {
        this(new VerwerkToevalligeGebeurtenisVerzoekType());
    }

    /**
     * JAXB constructor.
     * @param verwerkToevallligeGebeurtenisVerzoekType Het verwerk toevallige gebeurtenis verzoek type {@link VerwerkToevallligeGebeurtenisVerzoekType}
     */
    public VerwerkToevalligeGebeurtenisVerzoekBericht(final VerwerkToevalligeGebeurtenisVerzoekType verwerkToevallligeGebeurtenisVerzoekType) {
        super("VerwerkToevalligeGebeurtenisVerzoek");
        this.verwerkToevallligeGebeurtenisVerzoekType = verwerkToevallligeGebeurtenisVerzoekType;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createVerwerkToevalligeGebeurtenisVerzoek(verwerkToevallligeGebeurtenisVerzoekType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van aktenummer.
     * @return aktenummer
     */
    public String getAktenummer() {
        return verwerkToevallligeGebeurtenisVerzoekType.getAktenummer();
    }

    /**
     * Zet de waarde van aktenummer.
     * @param aktenummer Het te zetten aktenummer
     */
    public void setAktenummer(final String aktenummer) {
        verwerkToevallligeGebeurtenisVerzoekType.setAktenummer(aktenummer);
    }

    /**
     * Geef de waarde van de ontvangende gemeente.
     * @return ontvangende gemeente
     */
    public String getOntvangendeGemeente() {
        return verwerkToevallligeGebeurtenisVerzoekType.getOntvangendeGemeente();
    }

    /**
     * Zet de waarde van ontvangende Gemeente.
     * @param ontvangendeGemeente De te zetten ontvangende Gemeente
     */
    public void setOntvangendeGemeente(final String ontvangendeGemeente) {
        verwerkToevallligeGebeurtenisVerzoekType.setOntvangendeGemeente(ontvangendeGemeente);
    }

    /**
     * Geef de waarde van het Tb02 bericht als teletex string.
     * @return inhoud Tb02 bericht als teletex
     */
    public String getTb02InhoudAlsTeletex() {
        return verwerkToevallligeGebeurtenisVerzoekType.getTb02InhoudAlsTeletex();
    }

    /**
     * Zet de waarde van de inhoud van het Tb02 bericht.
     * @param tb02InhoudAlsTeletex De te zetten inhoud van het Tb02 bericht als teletext string
     */
    public void setTb02InhoudAlsTeletex(final String tb02InhoudAlsTeletex) {
        verwerkToevallligeGebeurtenisVerzoekType.setTb02InhoudAlsTeletex(tb02InhoudAlsTeletex);
    }

    /**
     * Geef de waarde van verzendende gemeente.
     * @return verzendende gemeente
     */
    public String getVerzendendeGemeente() {
        return verwerkToevallligeGebeurtenisVerzoekType.getVerzendendeGemeente();
    }

    /**
     * Zet de waarde van de verzendende gemeente.
     * @param verzendendeGemeente De te zetten verzendende gemeente
     */
    public void setVerzendendeGemeente(final String verzendendeGemeente) {
        verwerkToevallligeGebeurtenisVerzoekType.setVerzendendeGemeente(verzendendeGemeente);
    }

}
