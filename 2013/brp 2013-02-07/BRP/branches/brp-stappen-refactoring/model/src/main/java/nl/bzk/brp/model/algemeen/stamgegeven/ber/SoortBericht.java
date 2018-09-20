/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.ber;

/**
 * Een mogelijk Soort van een bericht.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
public enum SoortBericht {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", null),
    /**
     * AFS_RegistreerGeboorte_B.
     */
    A_F_S_REGISTREER_GEBOORTE_B("AFS_RegistreerGeboorte_B", BurgerzakenModule.AFSTAMMING),
    /**
     * AFS_RegistreerGeboorte_BR.
     */
    A_F_S_REGISTREER_GEBOORTE_B_R("AFS_RegistreerGeboorte_BR", BurgerzakenModule.AFSTAMMING),
    /**
     * HGP_RegistreerHuwelijk_B.
     */
    H_G_P_REGISTREER_HUWELIJK_B("HGP_RegistreerHuwelijk_B", BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * HGP_RegistreerHuwelijk_BR.
     */
    H_G_P_REGISTREER_HUWELIJK_B_R("HGP_RegistreerHuwelijk_BR", BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP),
    /**
     * MIG_RegistreerVerhuizing_B.
     */
    M_I_G_REGISTREER_VERHUIZING_B("MIG_RegistreerVerhuizing_B", BurgerzakenModule.VERBLIJF),
    /**
     * MIG_RegistreerVerhuizing_BR.
     */
    M_I_G_REGISTREER_VERHUIZING_B_R("MIG_RegistreerVerhuizing_BR", BurgerzakenModule.VERBLIJF),
    /**
     * MIG_CorrigeerAdres_B.
     */
    M_I_G_CORRIGEER_ADRES_B("MIG_CorrigeerAdres_B", BurgerzakenModule.VERBLIJF),
    /**
     * MIG_CorrigeerAdres_BR.
     */
    M_I_G_CORRIGEER_ADRES_B_R("MIG_CorrigeerAdres_BR", BurgerzakenModule.VERBLIJF),
    /**
     * OVL_RegistreerOverlijden_B.
     */
    O_V_L_REGISTREER_OVERLIJDEN_B("OVL_RegistreerOverlijden_B", BurgerzakenModule.OVERLIJDEN),
    /**
     * OVL_RegistreerOverlijden_BR.
     */
    O_V_L_REGISTREER_OVERLIJDEN_B_R("OVL_RegistreerOverlijden_BR", BurgerzakenModule.OVERLIJDEN),
    /**
     * ALG_ZoekPersoon_V.
     */
    A_L_G_ZOEK_PERSOON_V("ALG_ZoekPersoon_V", BurgerzakenModule.ALGEMENE_BIJHOUDING),
    /**
     * ALG_ZoekPersoon_VR.
     */
    A_L_G_ZOEK_PERSOON_V_R("ALG_ZoekPersoon_VR", BurgerzakenModule.ALGEMENE_BIJHOUDING),
    /**
     * ALG_GeefDetailsPersoon_V.
     */
    A_L_G_GEEF_DETAILS_PERSOON_V("ALG_GeefDetailsPersoon_V", BurgerzakenModule.ALGEMENE_BIJHOUDING),
    /**
     * ALG_GeefDetailsPersoon_VR.
     */
    A_L_G_GEEF_DETAILS_PERSOON_V_R("ALG_GeefDetailsPersoon_VR", BurgerzakenModule.ALGEMENE_BIJHOUDING),
    /**
     * ALG_BepaalKandidaatVader_Vi.
     */
    A_L_G_BEPAAL_KANDIDAAT_VADER_VI("ALG_BepaalKandidaatVader_Vi", BurgerzakenModule.ALGEMENE_BIJHOUDING),
    /**
     * ALG_BepaalKandidaatVader_ViR.
     */
    A_L_G_BEPAAL_KANDIDAAT_VADER_VI_R("ALG_BepaalKandidaatVader_ViR", BurgerzakenModule.ALGEMENE_BIJHOUDING),
    /**
     * ALG_GeefBewonersOpAdresInclusiefBetrokkenheden_Vi.
     */
    A_L_G_GEEF_BEWONERS_OP_ADRES_INCLUSIEF_BETROKKENHEDEN_VI("ALG_GeefBewonersOpAdresInclusiefBetrokkenheden_Vi",
            BurgerzakenModule.ALGEMENE_BIJHOUDING),
    /**
     * ALG_GeefBewonersOpAdresInclusiefBetrokkenheden_ViR.
     */
    A_L_G_GEEF_BEWONERS_OP_ADRES_INCLUSIEF_BETROKKENHEDEN_VI_R("ALG_GeefBewonersOpAdresInclusiefBetrokkenheden_ViR",
            BurgerzakenModule.ALGEMENE_BIJHOUDING);

    private final String            naam;
    private final BurgerzakenModule module;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortBericht
     * @param module Module voor SoortBericht
     */
    private SoortBericht(final String naam, final BurgerzakenModule module) {
        this.naam = naam;
        this.module = module;
    }

    /**
     * Retourneert Naam van Soort bericht.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Module van Soort bericht.
     *
     * @return Module.
     */
    public BurgerzakenModule getModule() {
        return module;
    }

}
