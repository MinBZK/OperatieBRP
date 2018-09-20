/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import nl.bzk.migratiebrp.bericht.model.AbstractBericht;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;
import nl.bzk.migratiebrp.bericht.model.xml.XmlTeletexEncoding;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * Dit bericht wordt verstuurd om een LO3 Persoonslijst (serialized) te valideren op pre-condities, te converteren naar
 * een BRP-persoon en vervolgens op te slaan in de BRP database. Dit bericht wordt beantwoord met een
 * SynchroniseerNaarBrpAntwoordBericht.
 *
 * @see SynchroniseerNaarBrpAntwoordBericht
 */
public final class SynchroniseerNaarBrpVerzoekBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {

    private static final long serialVersionUID = 1L;

    private final SynchroniseerNaarBrpVerzoekType synchroniseerNaarBrpVerzoekType;

    // ****************************** Constructors ******************************

    /**
     * Default constructor.
     */
    public SynchroniseerNaarBrpVerzoekBericht() {
        this(new SynchroniseerNaarBrpVerzoekType());
    }

    /**
     * Convenient constructor.
     *
     * @param lo3BerichtAsTeletexString
     *            De teletext representatie van het Lo3 bericht.
     */
    public SynchroniseerNaarBrpVerzoekBericht(final String lo3BerichtAsTeletexString) {
        this();
        setLo3BerichtAsTeletexString(lo3BerichtAsTeletexString);
    }

    /**
     * Convenient constructor.
     *
     * @param lo3BerichtAsTeletexString
     *            De teletext representatie van het Lo3 bericht.
     * @param aNummerTeVervangenPl
     *            Het A-nummer van de te vervangen PL.
     */
    public SynchroniseerNaarBrpVerzoekBericht(final String lo3BerichtAsTeletexString, final Long aNummerTeVervangenPl) {
        this();
        setLo3BerichtAsTeletexString(lo3BerichtAsTeletexString);
        setANummerTeVervangenPl(aNummerTeVervangenPl);
    }

    /**
     * Convenient constructor.
     *
     * @param persoonslijst
     *            De LO3 PL.
     * @param aNummerTeVervangenPl
     *            Het A-nummer van de te vervangen PL.
     */
    public SynchroniseerNaarBrpVerzoekBericht(final Lo3Persoonslijst persoonslijst, final Long aNummerTeVervangenPl) {
        this();
        setLo3Persoonslijst(persoonslijst);
        setANummerTeVervangenPl(aNummerTeVervangenPl);
    }

    /**
     * Convenient constructor.
     *
     * @param synchroniseerNaarBrpVerzoekType
     *            Het synchronisatieverzoek type.
     */
    public SynchroniseerNaarBrpVerzoekBericht(final SynchroniseerNaarBrpVerzoekType synchroniseerNaarBrpVerzoekType) {
        super("SynchroniseerNaarBrpVerzoek");
        this.synchroniseerNaarBrpVerzoekType = synchroniseerNaarBrpVerzoekType;
    }

    // ****************************** Bericht methodes ******************************

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createSynchroniseerNaarBrpVerzoek(synchroniseerNaarBrpVerzoekType));
    }

    // ****************************** Public methodes ******************************

    /**
     * De inhoud van een LO3 PL bericht (in Teletex formaat).
     *
     * @return Geeft het Lo3bericht als teletext string terug.
     */
    public String getLo3BerichtAsTeletexString() {
        return XmlTeletexEncoding.decodeer(synchroniseerNaarBrpVerzoekType.getLo3BerichtAsTeletexString());
    }

    /**
     * De inhoud van een LO3 PL bericht (in Teletex formaat).
     *
     * @param lo3BerichtAsTeletexString
     *            de inhoud, mag niet null zijn en moet beginnen met de berichtlengte
     */
    public void setLo3BerichtAsTeletexString(final String lo3BerichtAsTeletexString) {
        ValidationUtils.controleerOpNullWaarden("lo3BerichtAsTeletexString mag niet null zijn", lo3BerichtAsTeletexString);
        synchroniseerNaarBrpVerzoekType.setLo3BerichtAsTeletexString(XmlTeletexEncoding.codeer(lo3BerichtAsTeletexString));
    }

    /**
     * Geeft de indicatie opnemen als nieuwe pl terug.
     *
     * @return indicatie opnemen als nieuwe pl
     */
    @SuppressFBWarnings(value = "NP_BOOLEAN_RETURN_NULL", justification = "Input die vertaald wordt kan ook NULL zijn")
    public Boolean getOpnemenAlsNieuwePl() {
        return synchroniseerNaarBrpVerzoekType.getStuurgegevens() != null ? asBoolean(synchroniseerNaarBrpVerzoekType.getStuurgegevens()
                                                                                                                     .getOpnemenAlsNieuwePl()) : null;
    }

    /**
     * Geeft de indicatie opnemen als nieuwe pl terug.
     *
     * @return indicatie opnemen als nieuwe pl
     */
    public boolean isOpnemenAlsNieuwePl() {
        return synchroniseerNaarBrpVerzoekType.getStuurgegevens() != null
               && synchroniseerNaarBrpVerzoekType.getStuurgegevens().getOpnemenAlsNieuwePl() != null;
    }

    /**
     * Zet de indicatie opnemen als nieuwe pl.
     *
     * @param opnemenAlsNieuwePl
     *            indicatie opnemen als nieuwe pl
     */
    public void setOpnemenAlsNieuwePl(final Boolean opnemenAlsNieuwePl) {
        if (opnemenAlsNieuwePl == null) {
            return;
        }

        if (synchroniseerNaarBrpVerzoekType.getStuurgegevens() == null) {
            synchroniseerNaarBrpVerzoekType.setStuurgegevens(new SynchroniseerNaarBrpVerzoekType.Stuurgegevens());
        }

        synchroniseerNaarBrpVerzoekType.getStuurgegevens().setOpnemenAlsNieuwePl(asJaType(opnemenAlsNieuwePl));
    }

    /**
     * Geeft het A-nummer van de te vervangen PL terug.
     *
     * @return Het A-nummer van de te vervangen PL.
     */
    public Long getANummerTeVervangenPl() {
        return synchroniseerNaarBrpVerzoekType.getStuurgegevens() != null ? AbstractBericht.asLong(synchroniseerNaarBrpVerzoekType.getStuurgegevens()
                                                                                                                                  .getANummerTeVervangenPl())
                                                                         : null;
    }

    /**
     * Zet het A-nummer van de te vervangen persoonslijst.
     *
     * @param aNummerTeVervangenPl
     *            Het te zetten A-nummer.
     */
    public void setANummerTeVervangenPl(final Long aNummerTeVervangenPl) {
        if (aNummerTeVervangenPl == null) {
            return;
        }

        if (synchroniseerNaarBrpVerzoekType.getStuurgegevens() == null) {
            synchroniseerNaarBrpVerzoekType.setStuurgegevens(new SynchroniseerNaarBrpVerzoekType.Stuurgegevens());
        }
        synchroniseerNaarBrpVerzoekType.getStuurgegevens().setANummerTeVervangenPl(AbstractBericht.asString(aNummerTeVervangenPl));
    }

    /**
     * Geeft de indicatie-gezaghebbende-pl terug.
     *
     * @return indicatie-gezaghebbende-pl
     */
    public boolean isGezaghebbendBericht() {
        if (synchroniseerNaarBrpVerzoekType.getStuurgegevens() != null
            && synchroniseerNaarBrpVerzoekType.getStuurgegevens().getGezaghebbendBericht() != null)
        {
            return asBoolean(synchroniseerNaarBrpVerzoekType.getStuurgegevens().getGezaghebbendBericht());
        } else {
            return false;
        }
    }

    /**
     * Zet de indicatie-gezaghebbende-pl.
     *
     * @param isGezaghebbendePl
     *            indicatie-gezaghebbende-pl
     */
    public void setGezaghebbendBericht(final Boolean isGezaghebbendePl) {
        if (isGezaghebbendePl == null) {
            return;
        }

        if (synchroniseerNaarBrpVerzoekType.getStuurgegevens() == null) {
            synchroniseerNaarBrpVerzoekType.setStuurgegevens(new SynchroniseerNaarBrpVerzoekType.Stuurgegevens());
        }

        synchroniseerNaarBrpVerzoekType.getStuurgegevens().setGezaghebbendBericht(asJaType(isGezaghebbendePl));
    }

    /**
     * Geeft de indicatie anummer wijziging terug.
     *
     * @return indicatie anummerwijziging
     */
    public boolean isAnummerWijziging() {
        return synchroniseerNaarBrpVerzoekType.getStuurgegevens() != null
               && synchroniseerNaarBrpVerzoekType.getStuurgegevens().getAnummerWijziging() != null;
    }

    /**
     * Zet de indicatie anummer wijziging.
     *
     * @param anummerWijziging
     *            indicatie anummer wijziging
     */
    public void setAnummerWijziging(final Boolean anummerWijziging) {
        if (anummerWijziging == null) {
            return;
        }

        if (synchroniseerNaarBrpVerzoekType.getStuurgegevens() == null) {
            synchroniseerNaarBrpVerzoekType.setStuurgegevens(new SynchroniseerNaarBrpVerzoekType.Stuurgegevens());
        }

        synchroniseerNaarBrpVerzoekType.getStuurgegevens().setAnummerWijziging(asJaType(anummerWijziging));
    }

    /**
     * Geeft de Lo3Persoonslijst terug.
     *
     * @return De Lo3Persoonslijst
     *
     */
    public Lo3Persoonslijst getLo3Persoonslijst() {
        return asLo3Persoonslijst(getLo3BerichtAsTeletexString());
    }

    /**
     * Zet de Lo3persoonslijst.
     *
     * @param lo3Persoonslijst
     *            De te zetten Lo3Persoonslijst.
     */
    public void setLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst) {
        setLo3BerichtAsTeletexString(asString(lo3Persoonslijst));
    }

}
