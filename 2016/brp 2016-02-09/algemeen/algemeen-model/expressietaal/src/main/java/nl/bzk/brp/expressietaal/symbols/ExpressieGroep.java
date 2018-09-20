/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.symbols.solvers.GroepGetter;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.Groep;

/**
 * Opsomming van alle groepen zoals die in het BMR voorkomen en zoals die gebruikt kunnen worden in expressies.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGroepGenerator")
public enum ExpressieGroep {

    /**
     * Groep PERSOON_STAATLOOS. BMR-groep 'Staatloos' van objecttype 'Persoon'.
     */
    PERSOON_STAATLOOS("staatloos", false, ExpressieType.GROEP, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonStaatloosGroepGetter()),
    /**
     * Groep PERSOON_IDENTIFICATIENUMMERS. BMR-groep 'Identificatienummers' van objecttype 'Persoon'.
     */
    PERSOON_IDENTIFICATIENUMMERS("identificatienummers", false, ExpressieType.GROEP,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersGroepGetter()),
    /**
     * Groep PERSOON_NAAMGEBRUIK. BMR-groep 'Naamgebruik' van objecttype 'Persoon'.
     */
    PERSOON_NAAMGEBRUIK("naamgebruik", false, ExpressieType.GROEP, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikGroepGetter()),
    /**
     * Groep PERSOON_GEBOORTE. BMR-groep 'Geboorte' van objecttype 'Persoon'.
     */
    PERSOON_GEBOORTE("geboorte", false, ExpressieType.GROEP, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteGroepGetter()),
    /**
     * Groep PERSOON_OVERLIJDEN. BMR-groep 'Overlijden' van objecttype 'Persoon'.
     */
    PERSOON_OVERLIJDEN("overlijden", false, ExpressieType.GROEP, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenGroepGetter()),
    /**
     * Groep PERSOON_VERBLIJFSRECHT. BMR-groep 'Verblijfsrecht' van objecttype 'Persoon'.
     */
    PERSOON_VERBLIJFSRECHT("verblijfsrecht", false, ExpressieType.GROEP, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfsrechtGroepGetter()),
    /**
     * Groep PERSOON_DERDE_HEEFT_GEZAG. BMR-groep 'Gezag derde' van objecttype 'Persoon'.
     */
    PERSOON_DERDE_HEEFT_GEZAG("derde.heeft.gezag", false, ExpressieType.GROEP,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonDerdeHeeftGezagGroepGetter()),
    /**
     * Groep PERSOON_UITSLUITING_KIESRECHT. BMR-groep 'Uitsluiting kiesrecht' van objecttype 'Persoon'.
     */
    PERSOON_UITSLUITING_KIESRECHT("uitsluiting_kiesrecht", false, ExpressieType.GROEP,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonUitsluitingKiesrechtGroepGetter()),
    /**
     * Groep PERSOON_INSCHRIJVING. BMR-groep 'Inschrijving' van objecttype 'Persoon'.
     */
    PERSOON_INSCHRIJVING("inschrijving", false, ExpressieType.GROEP, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingGroepGetter()),
    /**
     * Groep PERSOON_GESLACHTSAANDUIDING. BMR-groep 'Geslachtsaanduiding' van objecttype 'Persoon'.
     */
    PERSOON_GESLACHTSAANDUIDING("geslachtsaanduiding", false, ExpressieType.GROEP,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingGroepGetter()),
    /**
     * Groep PERSOON_SAMENGESTELDE_NAAM. BMR-groep 'Samengestelde naam' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDE_NAAM("samengestelde_naam", false, ExpressieType.GROEP,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamGroepGetter()),
    /**
     * Groep PERSOON_PERSOONSKAART. BMR-groep 'Persoonskaart' van objecttype 'Persoon'.
     */
    PERSOON_PERSOONSKAART("persoonskaart", false, ExpressieType.GROEP, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonPersoonskaartGroepGetter()),
    /**
     * Groep PERSOON_BIJHOUDING. BMR-groep 'Bijhouding' van objecttype 'Persoon'.
     */
    PERSOON_BIJHOUDING("bijhouding", false, ExpressieType.GROEP, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingGroepGetter()),
    /**
     * Groep PERSOON_MIGRATIE. BMR-groep 'Migratie' van objecttype 'Persoon'.
     */
    PERSOON_MIGRATIE("migratie", false, ExpressieType.GROEP, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieGroepGetter()),
    /**
     * Groep PERSOON_ONDER_CURATELE. BMR-groep 'Curatele' van objecttype 'Persoon'.
     */
    PERSOON_ONDER_CURATELE("onder.curatele", false, ExpressieType.GROEP, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOnderCurateleGroepGetter()),
    /**
     * Groep PERSOON_DEELNAME_EU_VERKIEZINGEN. BMR-groep 'Deelname EU verkiezingen' van objecttype 'Persoon'.
     */
    PERSOON_DEELNAME_EU_VERKIEZINGEN("deelname_eu_verkiezingen", false, ExpressieType.GROEP,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonDeelnameEuVerkiezingenGroepGetter()),
    /**
     * Groep PERSOON_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE. BMR-groep 'Bijzondere verblijfsrechtelijke positie' van
     * objecttype 'Persoon'.
     */
    PERSOON_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE("bijzondere_verblijfsrechtelijke_positie", false, ExpressieType.GROEP,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijzondereVerblijfsrechtelijkePositieGroepGetter()),
    /**
     * Groep PERSOON_VASTGESTELD_NIET_NEDERLANDER. BMR-groep 'Vastgesteld niet Nederlander' van objecttype 'Persoon'.
     */
    PERSOON_VASTGESTELD_NIET_NEDERLANDER("vastgesteld_niet_nederlander", false, ExpressieType.GROEP,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVastgesteldNietNederlanderGroepGetter()),
    /**
     * Groep PERSOON_BEHANDELD_ALS_NEDERLANDER. BMR-groep 'Behandeld als Nederlander' van objecttype 'Persoon'.
     */
    PERSOON_BEHANDELD_ALS_NEDERLANDER("behandeld_als_nederlander", false, ExpressieType.GROEP,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBehandeldAlsNederlanderGroepGetter()),
    /**
     * Groep PERSOON_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT. BMR-groep 'Signalering met betrekking tot
     * verstrekken reisdocument' van objecttype 'Persoon'.
     */
    PERSOON_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT("signalering_met_betrekking_tot_verstrekken_reisdocument", false, ExpressieType.GROEP,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSignaleringMetBetrekkingTotVerstrekkenReisdocumentGroepGetter()),
    /**
     * Groep PERSOON_ADMINISTRATIEF. BMR-groep 'Afgeleid administratief' van objecttype 'Persoon'.
     */
    PERSOON_ADMINISTRATIEF("administratief", false, ExpressieType.GROEP, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdministratiefGroepGetter()),
    /**
     * Groep PERSOON_NUMMERVERWIJZING. BMR-groep 'Nummerverwijzing' van objecttype 'Persoon'.
     */
    PERSOON_NUMMERVERWIJZING("nummerverwijzing", false, ExpressieType.GROEP,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingGroepGetter());

    private final String syntax;
    private final boolean isLijst;
    private final ExpressieType type;
    private final GroepGetter getter;

    /**
     * Constructor.
     *
     * @param syntax Naam (syntax) van de groep.
     * @param type Type van de groep.
     * @param getter Getter voor de groep.
     */
    private ExpressieGroep(final String syntax, final boolean isLijst, final ExpressieType type, final GroepGetter getter) {
        this.syntax = syntax.toLowerCase();
        this.isLijst = isLijst;
        this.type = type;
        this.getter = getter;
    }

    public String getSyntax() {
        return syntax;
    }

    public boolean isLijst() {
        return isLijst;
    }

    public ExpressieType getType() {
        return type;
    }

    /**
     * Geeft de groep voor het betreffende BRP-object terug.
     *
     * @param brpObject Object waarvan de groep bepaald moet worden.
     * @return De groep of NULL indien niet gevonden.
     */
    public Groep getGroep(final BrpObject brpObject) {
        return getter.getGroep(brpObject);
    }

    /**
     * Geeft de historische groepen voor het betreffende BRP-object terug.
     *
     * @param brpObject Object waarvan de groepen bepaald moeten worden.
     * @return List met groepen of NULL indien niet gevonden.
     */
    public List<Groep> getHistorischeGroepen(final BrpObject brpObject) {
        return getter.getHistorischeGroepen(brpObject);
    }

    @Override
    public String toString() {
        return getSyntax() + " (" + getType().toString() + ")";
    }
}
