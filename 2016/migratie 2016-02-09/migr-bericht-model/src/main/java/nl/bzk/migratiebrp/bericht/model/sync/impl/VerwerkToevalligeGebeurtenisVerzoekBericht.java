/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AkteGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.FamilierechtelijkeBetrekkingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeldigheidGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.NaamGeslachtType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.OverlijdenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieType;
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
     *
     * @param verwerkToevallligeGebeurtenisVerzoekType
     *            Het verwerk toevallige gebeurtenis verzoek type {@link VerwerkToevallligeGebeurtenisVerzoekType}
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
     * Geef de waarde van persoon.
     *
     * @return persoon
     */
    public PersoonType getPersoon() {
        return verwerkToevallligeGebeurtenisVerzoekType.getPersoon();
    }

    /**
     * Zet de waarde van persoon.
     *
     * @param persoon
     *            De te zetten persoon
     */
    public void setPersoon(final PersoonType persoon) {
        verwerkToevallligeGebeurtenisVerzoekType.setPersoon(persoon);
    }

    /**
     * Geef de waarde van update persoon.
     *
     * @return update persoon
     */
    public NaamGeslachtType getUpdatePersoon() {
        return verwerkToevallligeGebeurtenisVerzoekType.getNaamGeslacht();
    }

    /**
     * Zet de waarde van update persoon.
     *
     * @param naamGeslacht
     *            De te zetten update persoon
     */
    public void setUpdatePersoon(final NaamGeslachtType naamGeslacht) {
        verwerkToevallligeGebeurtenisVerzoekType.setNaamGeslacht(naamGeslacht);
    }

    /**
     * Geef de waarde van familierechtelijke betrekking.
     *
     * @return familierechtelijke betrekking
     */
    public FamilierechtelijkeBetrekkingType getFamilieRechtelijkeBetrekking() {
        return verwerkToevallligeGebeurtenisVerzoekType.getFamilierechtelijkeBetrekking();
    }

    /**
     * Zet de waarde van de familierechtelijke betrekking.
     *
     * @param familierechtelijkeBetrekking
     *            De te zetten familierechtelijke betrekking
     */
    public void setFamilieRechtelijkeBetrekking(final FamilierechtelijkeBetrekkingType familierechtelijkeBetrekking) {
        verwerkToevallligeGebeurtenisVerzoekType.setFamilierechtelijkeBetrekking(familierechtelijkeBetrekking);
    }

    /**
     * Geef de waarde van overlijden.
     *
     * @return overlijden
     */
    public OverlijdenType getOverlijden() {
        return verwerkToevallligeGebeurtenisVerzoekType.getOverlijden();
    }

    /**
     * Zet de waarde van overlijden.
     *
     * @param overlijden
     *            De te zetten overlijden
     */
    public void setOverlijden(final OverlijdenType overlijden) {
        verwerkToevallligeGebeurtenisVerzoekType.setOverlijden(overlijden);
    }

    /**
     * Geef de waarde van relatie.
     *
     * @return relatie
     */
    public RelatieType getRelatie() {
        return verwerkToevallligeGebeurtenisVerzoekType.getRelatie();
    }

    /**
     * Zet de waarde van relatie.
     *
     * @param relatie
     *            De te zetten relatie
     */
    public void setRelatie(final RelatieType relatie) {
        verwerkToevallligeGebeurtenisVerzoekType.setRelatie(relatie);
    }

    /**
     * Geef de waarde van de groep akte.
     *
     * @return groep akte
     */
    public AkteGroepType getAkte() {
        return verwerkToevallligeGebeurtenisVerzoekType.getAkte();
    }

    /**
     * Zet de groep akte.
     *
     * @param akte
     *            De te zetten groep akte
     */
    public void setAkte(final AkteGroepType akte) {
        verwerkToevallligeGebeurtenisVerzoekType.setAkte(akte);
    }

    /**
     * Geef de groep geldigheid.
     *
     * @return groep geldigheid
     */
    public GeldigheidGroepType getGeldigheid() {
        return verwerkToevallligeGebeurtenisVerzoekType.getGeldigheid();
    }

    /**
     * Zet de groep geldigheid.
     *
     * @param geldigheid
     *            De te zetten groep geldigheid
     */
    public void setGeldigheid(final GeldigheidGroepType geldigheid) {
        verwerkToevallligeGebeurtenisVerzoekType.setGeldigheid(geldigheid);
    }

    /**
     * Geef de doel gemeente.
     *
     * @return doelgemeente
     */
    public String getDoelGemeente() {
        return verwerkToevallligeGebeurtenisVerzoekType.getDoelGemeente();
    }

    /**
     * Zet de groep geldigheid.
     *
     * @param doelGemeente
     *            De te zetten doel gemeente
     */
    public void setDoelGemeente(final String doelGemeente) {
        verwerkToevallligeGebeurtenisVerzoekType.setDoelGemeente(doelGemeente);
    }

}
