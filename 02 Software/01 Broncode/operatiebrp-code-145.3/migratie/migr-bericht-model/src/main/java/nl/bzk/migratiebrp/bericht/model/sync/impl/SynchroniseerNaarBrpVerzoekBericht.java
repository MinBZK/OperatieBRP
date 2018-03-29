/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.util.List;

import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.BeheerdersKeuzeType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpVerzoekType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpVerzoekType.BeheerderKeuze;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpVerzoekType.BeheerderKeuze.Kandidaat;
import nl.bzk.migratiebrp.bericht.model.sync.generated.TypeSynchronisatieBericht;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;
import nl.bzk.migratiebrp.bericht.model.xml.XmlTeletexEncoding;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * Dit bericht wordt verstuurd om een LO3 Persoonslijst (serialized) te valideren op pre-condities, te converteren naar
 * een BRP-persoon en vervolgens op te slaan in de BRP database. Dit bericht wordt beantwoord met een
 * SynchroniseerNaarBrpAntwoordBericht.
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

    //

    /**
     * Convenient constructor.
     * @param lo3PersoonslijstAlsTeletexString De teletext representatie van de Lo3 persoonslijst.
     */
    public SynchroniseerNaarBrpVerzoekBericht(final String lo3PersoonslijstAlsTeletexString) {
        this();
        setLo3PersoonslijstAlsTeletexString(lo3PersoonslijstAlsTeletexString);
    }

    /**
     * Convenient constructor.
     * @param synchroniseerNaarBrpVerzoekType Het synchronisatieverzoek type.
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
     * De LO3 persoonslijst (in Teletex formaat).
     * @return Geeft het Lo3bericht als teletext string terug.
     */
    public String getLo3PersoonslijstAlsTeletexString() {
        return XmlTeletexEncoding.decodeer(synchroniseerNaarBrpVerzoekType.getLo3PersoonslijstAlsTeletexString());
    }

    /**
     * De inhoud van een LO3 PL bericht (in Teletex formaat).
     * @param lo3PersoonslijstAlsTeletexString de inhoud, mag niet null zijn en moet beginnen met de berichtlengte
     */
    public void setLo3PersoonslijstAlsTeletexString(final String lo3PersoonslijstAlsTeletexString) {
        ValidationUtils.controleerOpNullWaarden("lo3PersoonslijstAlsTeletexString mag niet null zijn", lo3PersoonslijstAlsTeletexString);
        synchroniseerNaarBrpVerzoekType.setLo3PersoonslijstAlsTeletexString(XmlTeletexEncoding.codeer(lo3PersoonslijstAlsTeletexString));
    }

    /**
     * Zet het type van het bericht.
     * @param typeBericht het type bericht.
     */
    public void setTypeBericht(final TypeSynchronisatieBericht typeBericht) {
        ValidationUtils.controleerOpNullWaarden("typeBericht mag niet null zijn", typeBericht);
        synchroniseerNaarBrpVerzoekType.setTypeBericht(typeBericht);
    }

    /**
     * Geef het type van het bericht.
     * @return type bericht
     */
    public TypeSynchronisatieBericht getTypeBericht() {
        return synchroniseerNaarBrpVerzoekType.getTypeBericht();
    }

    /**
     * Zet de keuze van de beheerder inclusief de kandidaten gebruikt bij de keuze in het bericht.
     * @param keuze De keuze van de beheerder
     * @param teVervangenPersoonId id van de pl die vervangen moet worden
     * @param kandidaten de kandidaten waarop basis de beheerder zijn keuze heeft gemaakt.
     */
    public void setBeheerderKeuze(
            final BeheerdersKeuzeType keuze,
            final Long teVervangenPersoonId,
            final List<nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType.Kandidaat> kandidaten) {
        synchroniseerNaarBrpVerzoekType.setBeheerderKeuze(new BeheerderKeuze());
        synchroniseerNaarBrpVerzoekType.getBeheerderKeuze().setKeuze(keuze);
        synchroniseerNaarBrpVerzoekType.getBeheerderKeuze().setTeVervangenPersoonId(teVervangenPersoonId);

        if (kandidaten != null && !kandidaten.isEmpty()) {
            for (final nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType.Kandidaat kandidaat : kandidaten) {
                synchroniseerNaarBrpVerzoekType.getBeheerderKeuze().getKandidaat().add(omzettenAntwoordKandidaatNaarVerzoekKandidaat(kandidaat));
            }
        }
    }

    private Kandidaat omzettenAntwoordKandidaatNaarVerzoekKandidaat(
            final nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType.Kandidaat kandidaat) {
        final Kandidaat resultaat = new Kandidaat();
        resultaat.setPersoonId(kandidaat.getPersoonId());
        resultaat.setVersie(kandidaat.getVersie());
        return resultaat;
    }

    /**
     * Geef de keuze van de beheerder terug.
     * @return de beheerder keuze
     */
    public BeheerderKeuze getBeheerderKeuze() {
        return synchroniseerNaarBrpVerzoekType.getBeheerderKeuze();
    }

    /**
     * Geeft de Lo3Persoonslijst terug.
     * @return De Lo3Persoonslijst
     */
    public Lo3Persoonslijst getLo3Persoonslijst() {
        return asLo3Persoonslijst(getLo3PersoonslijstAlsTeletexString());
    }

    /**
     * Zet de Lo3persoonslijst.
     * @param lo3Persoonslijst De te zetten Lo3Persoonslijst.
     */
    public void setLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst) {
        setLo3PersoonslijstAlsTeletexString(asString(lo3Persoonslijst));
    }

    /**
     * Geef de waarde van verzendende gemeente.
     * @return verzendende gemeente
     */
    public String getVerzendendeGemeente() {
        return synchroniseerNaarBrpVerzoekType.getVerzendendeGemeente();
    }

    /**
     * Zet de waarde van de verzendende gemeente.
     * @param verzendendeGemeente De te zetten verzendende gemeente
     */
    public void setVerzendendeGemeente(final String verzendendeGemeente) {
        synchroniseerNaarBrpVerzoekType.setVerzendendeGemeente(verzendendeGemeente);
    }
}
