/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * Toevallige gebeurtenis.
 */
public final class BrpToevalligeGebeurtenis {

    private final BrpPartijCode doelPartijCode;
    private final BrpToevalligeGebeurtenisPersoon persoon;

    // Choice: min 1, max 1
    private final BrpToevalligeGebeurtenisNaamGeslacht voornaam;
    private final BrpToevalligeGebeurtenisNaamGeslacht geslachtsnaam;
    private final BrpToevalligeGebeurtenisNaamGeslacht geslachtsaanduiding;
    private final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking familierechtelijkeBetrekking;
    private final BrpToevalligeGebeurtenisOverlijden overlijden;
    private final BrpToevalligeGebeurtenisVerbintenis verbintenis;
    //

    private final BrpPartijCode registerGemeente;
    private final BrpString nummerAkte;
    private final BrpDatum datumAanvang;

    /**
     * Constructor.
     * @param doelPartijCode doel gemeente
     * @param persoon persoon
     * @param voornaam voornaam wijziging
     * @param geslachtsnaam geslachtsnaam wijziging
     * @param geslachtsaanduiding geslachtsaanduiding wijziging
     * @param familierechtelijkeBetrekking familierechtelijke betrekking
     * @param overlijden overlijden
     * @param verbintenis verbintenis
     * @param registerGemeente register gemeente
     * @param nummerAkte nummer akte
     * @param datumAanvang datum aanvang
     */
    public BrpToevalligeGebeurtenis(
            final BrpPartijCode doelPartijCode,
            final BrpToevalligeGebeurtenisPersoon persoon,
            final BrpToevalligeGebeurtenisNaamGeslacht voornaam,
            final BrpToevalligeGebeurtenisNaamGeslacht geslachtsnaam,
            final BrpToevalligeGebeurtenisNaamGeslacht geslachtsaanduiding,
            final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking familierechtelijkeBetrekking,
            final BrpToevalligeGebeurtenisOverlijden overlijden,
            final BrpToevalligeGebeurtenisVerbintenis verbintenis,
            final BrpPartijCode registerGemeente,
            final BrpString nummerAkte,
            final BrpDatum datumAanvang) {
        this.doelPartijCode = doelPartijCode;
        this.persoon = persoon;
        this.voornaam = voornaam;
        this.geslachtsnaam = geslachtsnaam;
        this.geslachtsaanduiding = geslachtsaanduiding;
        this.familierechtelijkeBetrekking = familierechtelijkeBetrekking;
        this.overlijden = overlijden;
        this.verbintenis = verbintenis;
        this.registerGemeente = registerGemeente;
        this.nummerAkte = nummerAkte;
        this.datumAanvang = datumAanvang;

        ValidationUtils.checkNietNull("DoelPartijCode mag niet null zijn", this.doelPartijCode);
        ValidationUtils.checkNietNull("Persoon mag niet null zijn", this.persoon);
        ValidationUtils.checkExactEen(
                "Er moet precies 1 van naamGeslacht, familierechtelijkeBetrekking, overlijden of verbintenis gevuld zijn",
                voornaam,
                geslachtsnaam,
                geslachtsaanduiding,
                familierechtelijkeBetrekking,
                overlijden,
                verbintenis);
        ValidationUtils.checkNietNull("RegisterGemeente mag niet null zijn", this.registerGemeente);
        ValidationUtils.checkNietNull("NummerAkte mag niet null zijn", this.nummerAkte);
        ValidationUtils.checkNietNull("Datum aanvang mag niet null zijn", this.datumAanvang);
    }

    /**
     * Geef de waarde van doel gemeente van BrpToevalligeGebeurtenis.
     * @return de waarde van doel gemeente van BrpToevalligeGebeurtenis
     */
    public BrpPartijCode getDoelPartijCode() {
        return doelPartijCode;
    }

    /**
     * Geef de waarde van persoon van BrpToevalligeGebeurtenis.
     * @return de waarde van persoon van BrpToevalligeGebeurtenis
     */
    public BrpToevalligeGebeurtenisPersoon getPersoon() {
        return persoon;
    }

    /**
     * Geef de waarde van voornaam van BrpToevalligeGebeurtenis.
     * @return de waarde van voornaam van BrpToevalligeGebeurtenis
     */
    public BrpToevalligeGebeurtenisNaamGeslacht getVoornaam() {
        return voornaam;
    }

    /**
     * Geef de waarde van geslachtsnaam van BrpToevalligeGebeurtenis.
     * @return de waarde van geslachtsnaam van BrpToevalligeGebeurtenis
     */
    public BrpToevalligeGebeurtenisNaamGeslacht getGeslachtsnaam() {
        return geslachtsnaam;
    }

    /**
     * Geef de waarde van geslachtsaanduiding van BrpToevalligeGebeurtenis.
     * @return de waarde van geslachtsaanduiding van BrpToevalligeGebeurtenis
     */
    public BrpToevalligeGebeurtenisNaamGeslacht getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    /**
     * Geef de waarde van familierechtelijke betrekking van BrpToevalligeGebeurtenis.
     * @return de waarde van familierechtelijke betrekking van BrpToevalligeGebeurtenis
     */
    public BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking getFamilierechtelijkeBetrekking() {
        return familierechtelijkeBetrekking;
    }

    /**
     * Geef de waarde van overlijden van BrpToevalligeGebeurtenis.
     * @return de waarde van overlijden van BrpToevalligeGebeurtenis
     */
    public BrpToevalligeGebeurtenisOverlijden getOverlijden() {
        return overlijden;
    }

    /**
     * Geef de waarde van verbintenis van BrpToevalligeGebeurtenis.
     * @return de waarde van verbintenis van BrpToevalligeGebeurtenis
     */
    public BrpToevalligeGebeurtenisVerbintenis getVerbintenis() {
        return verbintenis;
    }

    /**
     * Geef de waarde van register gemeente van BrpToevalligeGebeurtenis.
     * @return de waarde van register gemeente van BrpToevalligeGebeurtenis
     */
    public BrpPartijCode getRegisterGemeente() {
        return registerGemeente;
    }

    /**
     * Geef de waarde van nummer akte van BrpToevalligeGebeurtenis.
     * @return de waarde van nummer akte van BrpToevalligeGebeurtenis
     */
    public BrpString getNummerAkte() {
        return nummerAkte;
    }

    /**
     * Geef de waarde van datum aanvang van BrpToevalligeGebeurtenis.
     * @return de waarde van datum aanvang van BrpToevalligeGebeurtenis
     */
    public BrpDatum getDatumAanvang() {
        return datumAanvang;
    }

}
