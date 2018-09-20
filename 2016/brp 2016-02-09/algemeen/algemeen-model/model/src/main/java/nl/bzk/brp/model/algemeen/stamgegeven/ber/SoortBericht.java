/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BurgerzakenModule;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Koppelvlak;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;

/**
 * Een mogelijk Soort van een bericht.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum SoortBericht implements BestaansPeriodeStamgegeven {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY(null, "Dummy", null, null, null, null),
    /**
     * bhg_afsRegistreerGeboorte.
     */
    BHG_AFS_REGISTREER_GEBOORTE("bhg_afsRegistreerGeboorte", "bhg_afsRegistreerGeboorte", BurgerzakenModule.AFSTAMMING, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_afsRegistreerGeboorte_R.
     */
    BHG_AFS_REGISTREER_GEBOORTE_R("bhg_afsRegistreerGeboorte_R", "bhg_afsRegistreerGeboorte_R", BurgerzakenModule.AFSTAMMING, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap.
     */
    BHG_HGP_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP("bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap",
            "bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap", BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R.
     */
    BHG_HGP_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP_R("bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R",
            "bhg_hgpRegistreerHuwelijkGeregistreerdPartnerschap_R", BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_vbaRegistreerVerhuizing.
     */
    BHG_VBA_REGISTREER_VERHUIZING("bhg_vbaRegistreerVerhuizing", "bhg_vbaRegistreerVerhuizing", BurgerzakenModule.VERBLIJF_EN_ADRES,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_vbaRegistreerVerhuizing_R.
     */
    BHG_VBA_REGISTREER_VERHUIZING_R("bhg_vbaRegistreerVerhuizing_R", "bhg_vbaRegistreerVerhuizing_R", BurgerzakenModule.VERBLIJF_EN_ADRES,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_vbaCorrigeerAdres.
     */
    BHG_VBA_CORRIGEER_ADRES("bhg_vbaCorrigeerAdres", "bhg_vbaCorrigeerAdres", BurgerzakenModule.VERBLIJF_EN_ADRES, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_vbaCorrigeerAdres_R.
     */
    BHG_VBA_CORRIGEER_ADRES_R("bhg_vbaCorrigeerAdres_R", "bhg_vbaCorrigeerAdres_R", BurgerzakenModule.VERBLIJF_EN_ADRES, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_ovlRegistreerOverlijden.
     */
    BHG_OVL_REGISTREER_OVERLIJDEN("bhg_ovlRegistreerOverlijden", "bhg_ovlRegistreerOverlijden", BurgerzakenModule.OVERLIJDEN, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_ovlRegistreerOverlijden_R.
     */
    BHG_OVL_REGISTREER_OVERLIJDEN_R("bhg_ovlRegistreerOverlijden_R", "bhg_ovlRegistreerOverlijden_R", BurgerzakenModule.OVERLIJDEN, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_bvgZoekPersoon.
     */
    BHG_BVG_ZOEK_PERSOON("bhg_bvgZoekPersoon", "bhg_bvgZoekPersoon", BurgerzakenModule.BEVRAGING_BIJHOUDING, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_bvgZoekPersoon_R.
     */
    BHG_BVG_ZOEK_PERSOON_R("bhg_bvgZoekPersoon_R", "bhg_bvgZoekPersoon_R", BurgerzakenModule.BEVRAGING_BIJHOUDING, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_bvgGeefDetailsPersoon.
     */
    BHG_BVG_GEEF_DETAILS_PERSOON("bhg_bvgGeefDetailsPersoon", "bhg_bvgGeefDetailsPersoon", BurgerzakenModule.BEVRAGING_BIJHOUDING, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_bvgGeefDetailsPersoon_R.
     */
    BHG_BVG_GEEF_DETAILS_PERSOON_R("bhg_bvgGeefDetailsPersoon_R", "bhg_bvgGeefDetailsPersoon_R", BurgerzakenModule.BEVRAGING_BIJHOUDING,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(9991231)),
    /**
     * bhg_bvgBepaalKandidaatVader.
     */
    BHG_BVG_BEPAAL_KANDIDAAT_VADER("bhg_bvgBepaalKandidaatVader", "bhg_bvgBepaalKandidaatVader", BurgerzakenModule.BEVRAGING_BIJHOUDING,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_bvgBepaalKandidaatVader_R.
     */
    BHG_BVG_BEPAAL_KANDIDAAT_VADER_R("bhg_bvgBepaalKandidaatVader_R", "bhg_bvgBepaalKandidaatVader_R", BurgerzakenModule.BEVRAGING_BIJHOUDING,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_bvgGeefPersonenOpAdresMetBetrokkenheden.
     */
    BHG_BVG_GEEF_PERSONEN_OP_ADRES_MET_BETROKKENHEDEN("bhg_bvgGeefPersonenOpAdresMetBetrokkenheden", "bhg_bvgGeefPersonenOpAdresMetBetrokkenheden",
            BurgerzakenModule.BEVRAGING_BIJHOUDING, Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(
                9991231)),
    /**
     * bhg_bvgGeefPersonenOpAdresMetBetrokkenheden_R.
     */
    BHG_BVG_GEEF_PERSONEN_OP_ADRES_MET_BETROKKENHEDEN_R("bhg_bvgGeefPersonenOpAdresMetBetrokkenheden_R", "bhg_bvgGeefPersonenOpAdresMetBetrokkenheden_R",
            BurgerzakenModule.BEVRAGING_BIJHOUDING, Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(
                99991231)),
    /**
     * bhg_afsRegistreerAdoptie.
     */
    BHG_AFS_REGISTREER_ADOPTIE("bhg_afsRegistreerAdoptie", "bhg_afsRegistreerAdoptie", BurgerzakenModule.AFSTAMMING, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_afsRegistreerAdoptie_R.
     */
    BHG_AFS_REGISTREER_ADOPTIE_R("bhg_afsRegistreerAdoptie_R", "bhg_afsRegistreerAdoptie_R", BurgerzakenModule.AFSTAMMING, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_afsActualiseerAfstamming.
     */
    BHG_AFS_ACTUALISEER_AFSTAMMING("bhg_afsActualiseerAfstamming", "bhg_afsActualiseerAfstamming", BurgerzakenModule.AFSTAMMING, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_afsActualiseerAfstamming_R.
     */
    BHG_AFS_ACTUALISEER_AFSTAMMING_R("bhg_afsActualiseerAfstamming_R", "bhg_afsActualiseerAfstamming_R", BurgerzakenModule.AFSTAMMING,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * lvg_synVerwerkPersoon.
     */
    LVG_SYN_VERWERK_PERSOON("lvg_synVerwerkPersoon", "lvg_synVerwerkPersoon", BurgerzakenModule.SYNCHRONISATIE, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_afsRegistreerErkenning.
     */
    BHG_AFS_REGISTREER_ERKENNING("bhg_afsRegistreerErkenning", "bhg_afsRegistreerErkenning", BurgerzakenModule.AFSTAMMING, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_afsRegistreerErkenning_R.
     */
    BHG_AFS_REGISTREER_ERKENNING_R("bhg_afsRegistreerErkenning_R", "bhg_afsRegistreerErkenning_R", BurgerzakenModule.AFSTAMMING, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_afsCorrigeerAfstamming.
     */
    BHG_AFS_CORRIGEER_AFSTAMMING("bhg_afsCorrigeerAfstamming", "bhg_afsCorrigeerAfstamming", BurgerzakenModule.AFSTAMMING, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_afsCorrigeerAfstamming_R.
     */
    BHG_AFS_CORRIGEER_AFSTAMMING_R("bhg_afsCorrigeerAfstamming_R", "bhg_afsCorrigeerAfstamming_R", BurgerzakenModule.AFSTAMMING, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_vbaRegistreerImmigratie.
     */
    BHG_VBA_REGISTREER_IMMIGRATIE("bhg_vbaRegistreerImmigratie", "bhg_vbaRegistreerImmigratie", BurgerzakenModule.VERBLIJF_EN_ADRES,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_vbaRegistreerImmigratie_R.
     */
    BHG_VBA_REGISTREER_IMMIGRATIE_R("bhg_vbaRegistreerImmigratie_R", "bhg_vbaRegistreerImmigratie_R", BurgerzakenModule.VERBLIJF_EN_ADRES,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_otmRegistreerOnderzoek.
     */
    BHG_OTM_REGISTREER_ONDERZOEK("bhg_otmRegistreerOnderzoek", "bhg_otmRegistreerOnderzoek", BurgerzakenModule.VERBLIJF_EN_ADRES, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_otmRegistreerOnderzoek_R.
     */
    BHG_OTM_REGISTREER_ONDERZOEK_R("bhg_otmRegistreerOnderzoek_R", "bhg_otmRegistreerOnderzoek_R", BurgerzakenModule.VERBLIJF_EN_ADRES,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_otmRegistreerTerugmelding.
     */
    BHG_OTM_REGISTREER_TERUGMELDING("bhg_otmRegistreerTerugmelding", "bhg_otmRegistreerTerugmelding", BurgerzakenModule.VERBLIJF_EN_ADRES,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_otmRegistreerTerugmelding_R.
     */
    BHG_OTM_REGISTREER_TERUGMELDING_R("bhg_otmRegistreerTerugmelding_R", "bhg_otmRegistreerTerugmelding_R", BurgerzakenModule.VERBLIJF_EN_ADRES,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_natRegistreerNationaliteit.
     */
    BHG_NAT_REGISTREER_NATIONALITEIT("bhg_natRegistreerNationaliteit", "bhg_natRegistreerNationaliteit", BurgerzakenModule.NATIONALITEIT,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_natRegistreerNationaliteit_R.
     */
    BHG_NAT_REGISTREER_NATIONALITEIT_R("bhg_natRegistreerNationaliteit_R", "bhg_natRegistreerNationaliteit_R", BurgerzakenModule.NATIONALITEIT,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_natCorrigeerNationaliteit.
     */
    BHG_NAT_CORRIGEER_NATIONALITEIT("bhg_natCorrigeerNationaliteit", "bhg_natCorrigeerNationaliteit", BurgerzakenModule.NATIONALITEIT,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_natCorrigeerNationaliteit_R.
     */
    BHG_NAT_CORRIGEER_NATIONALITEIT_R("bhg_natCorrigeerNationaliteit_R", "bhg_natCorrigeerNationaliteit_R", BurgerzakenModule.NATIONALITEIT,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap.
     */
    BHG_HGP_CORRIGEER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP("bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap",
            "bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap", BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R.
     */
    BHG_HGP_CORRIGEER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP_R("bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R",
            "bhg_hgpCorrigeerHuwelijkGeregistreerdPartnerschap_R", BurgerzakenModule.HUWELIJK_GEREGISTREERD_PARTNERSCHAP, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * lvg_synRegistreerAfnemerindicatie.
     */
    LVG_SYN_REGISTREER_AFNEMERINDICATIE("lvg_synRegistreerAfnemerindicatie", "lvg_synRegistreerAfnemerindicatie", BurgerzakenModule.AFNEMERINDICATIE,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * lvg_synRegistreerAfnemerindicatie_R.
     */
    LVG_SYN_REGISTREER_AFNEMERINDICATIE_R("lvg_synRegistreerAfnemerindicatie_R", "lvg_synRegistreerAfnemerindicatie_R",
            BurgerzakenModule.AFNEMERINDICATIE, Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_ovlCorrigeerOverlijden.
     */
    BHG_OVL_CORRIGEER_OVERLIJDEN("bhg_ovlCorrigeerOverlijden", "bhg_ovlCorrigeerOverlijden", BurgerzakenModule.OVERLIJDEN, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_ovlCorrigeerOverlijden_R.
     */
    BHG_OVL_CORRIGEER_OVERLIJDEN_R("bhg_ovlCorrigeerOverlijden_R", "bhg_ovlCorrigeerOverlijden_R", BurgerzakenModule.OVERLIJDEN, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_nmgRegistreerNaamGeslacht.
     */
    BHG_NMG_REGISTREER_NAAM_GESLACHT("bhg_nmgRegistreerNaamGeslacht", "bhg_nmgRegistreerNaamGeslacht", BurgerzakenModule.NAAM_GESLACHT,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_nmgRegistreerNaamGeslacht_R.
     */
    BHG_NMG_REGISTREER_NAAM_GESLACHT_R("bhg_nmgRegistreerNaamGeslacht_R", "bhg_nmgRegistreerNaamGeslacht_R", BurgerzakenModule.NAAM_GESLACHT,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_nmgCorrigeerNaamGeslacht.
     */
    BHG_NMG_CORRIGEER_NAAM_GESLACHT("bhg_nmgCorrigeerNaamGeslacht", "bhg_nmgCorrigeerNaamGeslacht", BurgerzakenModule.NAAM_GESLACHT,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_nmgCorrigeerNaamGeslacht_R.
     */
    BHG_NMG_CORRIGEER_NAAM_GESLACHT_R("bhg_nmgCorrigeerNaamGeslacht_R", "bhg_nmgCorrigeerNaamGeslacht_R", BurgerzakenModule.NAAM_GESLACHT,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_vbaActualiseerVerblijfAdres.
     */
    BHG_VBA_ACTUALISEER_VERBLIJF_ADRES("bhg_vbaActualiseerVerblijfAdres", "bhg_vbaActualiseerVerblijfAdres", BurgerzakenModule.VERBLIJF_EN_ADRES,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_vbaActualiseerVerblijfAdres_R.
     */
    BHG_VBA_ACTUALISEER_VERBLIJF_ADRES_R("bhg_vbaActualiseerVerblijfAdres_R", "bhg_vbaActualiseerVerblijfAdres_R", BurgerzakenModule.VERBLIJF_EN_ADRES,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_otmCorrigeerOnderzoek.
     */
    BHG_OTM_CORRIGEER_ONDERZOEK("bhg_otmCorrigeerOnderzoek", "bhg_otmCorrigeerOnderzoek", BurgerzakenModule.VERBLIJF_EN_ADRES, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_otmCorrigeerOnderzoek_R.
     */
    BHG_OTM_CORRIGEER_ONDERZOEK_R("bhg_otmCorrigeerOnderzoek_R", "bhg_otmCorrigeerOnderzoek_R", BurgerzakenModule.VERBLIJF_EN_ADRES,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_dvmRegistreerMededelingVerzoek.
     */
    BHG_DVM_REGISTREER_MEDEDELING_VERZOEK("bhg_dvmRegistreerMededelingVerzoek", "bhg_dvmRegistreerMededelingVerzoek",
            BurgerzakenModule.DOCUMENT_VERZOEK_MEDEDELING, Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0),
            new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_dvmRegistreerMededelingVerzoek_R.
     */
    BHG_DVM_REGISTREER_MEDEDELING_VERZOEK_R("bhg_dvmRegistreerMededelingVerzoek_R", "bhg_dvmRegistreerMededelingVerzoek_R",
            BurgerzakenModule.DOCUMENT_VERZOEK_MEDEDELING, Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0),
            new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_dvmCorrigeerMededelingVerzoek.
     */
    BHG_DVM_CORRIGEER_MEDEDELING_VERZOEK("bhg_dvmCorrigeerMededelingVerzoek", "bhg_dvmCorrigeerMededelingVerzoek",
            BurgerzakenModule.DOCUMENT_VERZOEK_MEDEDELING, Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0),
            new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_dvmCorrigeerMededelingVerzoek_R.
     */
    BHG_DVM_CORRIGEER_MEDEDELING_VERZOEK_R("bhg_dvmCorrigeerMededelingVerzoek_R", "bhg_dvmCorrigeerMededelingVerzoek_R",
            BurgerzakenModule.DOCUMENT_VERZOEK_MEDEDELING, Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0),
            new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_rsdRegistreerReisdocument.
     */
    BHG_RSD_REGISTREER_REISDOCUMENT("bhg_rsdRegistreerReisdocument", "bhg_rsdRegistreerReisdocument", BurgerzakenModule.REISDOCUMENTEN,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_rsdRegistreerReisdocument_R.
     */
    BHG_RSD_REGISTREER_REISDOCUMENT_R("bhg_rsdRegistreerReisdocument_R", "bhg_rsdRegistreerReisdocument_R", BurgerzakenModule.REISDOCUMENTEN,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_rsdCorrigeerReisdocument.
     */
    BHG_RSD_CORRIGEER_REISDOCUMENT("bhg_rsdCorrigeerReisdocument", "bhg_rsdCorrigeerReisdocument", BurgerzakenModule.REISDOCUMENTEN,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_rsdCorrigeerReisdocument_R.
     */
    BHG_RSD_CORRIGEER_REISDOCUMENT_R("bhg_rsdCorrigeerReisdocument_R", "bhg_rsdCorrigeerReisdocument_R", BurgerzakenModule.REISDOCUMENTEN,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_vkzRegistreerKiesrecht.
     */
    BHG_VKZ_REGISTREER_KIESRECHT("bhg_vkzRegistreerKiesrecht", "bhg_vkzRegistreerKiesrecht", BurgerzakenModule.VERKIEZINGEN, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_vkzRegistreerKiesrecht_R.
     */
    BHG_VKZ_REGISTREER_KIESRECHT_R("bhg_vkzRegistreerKiesrecht_R", "bhg_vkzRegistreerKiesrecht_R", BurgerzakenModule.VERKIEZINGEN, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_vkzCorrigeerKiesrecht.
     */
    BHG_VKZ_CORRIGEER_KIESRECHT("bhg_vkzCorrigeerKiesrecht", "bhg_vkzCorrigeerKiesrecht", BurgerzakenModule.VERKIEZINGEN, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_vkzCorrigeerKiesrecht_R.
     */
    BHG_VKZ_CORRIGEER_KIESRECHT_R("bhg_vkzCorrigeerKiesrecht_R", "bhg_vkzCorrigeerKiesrecht_R", BurgerzakenModule.VERKIEZINGEN, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * lvg_synGeefSynchronisatiePersoon.
     */
    LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON("lvg_synGeefSynchronisatiePersoon", "lvg_synGeefSynchronisatiePersoon", BurgerzakenModule.SYNCHRONISATIE,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * lvg_synGeefSynchronisatiePersoon_R.
     */
    LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON_R("lvg_synGeefSynchronisatiePersoon_R", "lvg_synGeefSynchronisatiePersoon_R", BurgerzakenModule.SYNCHRONISATIE,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * lvg_synGeefSynchronisatieStamgegeven.
     */
    LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN("lvg_synGeefSynchronisatieStamgegeven", "lvg_synGeefSynchronisatieStamgegeven",
            BurgerzakenModule.SYNCHRONISATIE, Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * lvg_synGeefSynchronisatieStamgegeven_R.
     */
    LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN_R("lvg_synGeefSynchronisatieStamgegeven_R", "lvg_synGeefSynchronisatieStamgegeven_R",
            BurgerzakenModule.SYNCHRONISATIE, Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_ondRegistreerOnderzoek.
     */
    BHG_OND_REGISTREER_ONDERZOEK("bhg_ondRegistreerOnderzoek", "bhg_ondRegistreerOnderzoek", BurgerzakenModule.ONDERZOEK, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_ondRegistreerOnderzoek_R.
     */
    BHG_OND_REGISTREER_ONDERZOEK_R("bhg_ondRegistreerOnderzoek_R", "bhg_ondRegistreerOnderzoek_R", BurgerzakenModule.ONDERZOEK, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * lvg_synVerwerkStamgegeven.
     */
    LVG_SYN_VERWERK_STAMGEGEVEN("lvg_synVerwerkStamgegeven", "lvg_synVerwerkStamgegeven", BurgerzakenModule.SYNCHRONISATIE, Koppelvlak.LEVERING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * lvg_bvgGeefDetailsPersoon.
     */
    LVG_BVG_GEEF_DETAILS_PERSOON("lvg_bvgGeefDetailsPersoon", "lvg_bvgGeefDetailsPersoon", BurgerzakenModule.BEVRAGING, Koppelvlak.LEVERING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * lvg_bvgGeefDetailsPersoon_R.
     */
    LVG_BVG_GEEF_DETAILS_PERSOON_R("lvg_bvgGeefDetailsPersoon_R", "lvg_bvgGeefDetailsPersoon_R", BurgerzakenModule.BEVRAGING, Koppelvlak.LEVERING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * lvg_bvgGeefMedebewoners.
     */
    LVG_BVG_GEEF_MEDEBEWONERS("lvg_bvgGeefMedebewoners", "lvg_bvgGeefMedebewoners", BurgerzakenModule.BEVRAGING, Koppelvlak.LEVERING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * lvg_bvgGeefMedebewoners_R.
     */
    LVG_BVG_GEEF_MEDEBEWONERS_R("lvg_bvgGeefMedebewoners_R", "lvg_bvgGeefMedebewoners_R", BurgerzakenModule.BEVRAGING, Koppelvlak.LEVERING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * lvg_bvgZoekPersoon.
     */
    LVG_BVG_ZOEK_PERSOON("lvg_bvgZoekPersoon", "lvg_bvgZoekPersoon", BurgerzakenModule.BEVRAGING, Koppelvlak.LEVERING, new DatumEvtDeelsOnbekendAttribuut(
        0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * lvg_bvgZoekPersoon_R.
     */
    LVG_BVG_ZOEK_PERSOON_R("lvg_bvgZoekPersoon_R", "lvg_bvgZoekPersoon_R", BurgerzakenModule.BEVRAGING, Koppelvlak.LEVERING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_bvgGeefMedebewoners.
     */
    BHG_BVG_GEEF_MEDEBEWONERS("bhg_bvgGeefMedebewoners", "bhg_bvgGeefMedebewoners", BurgerzakenModule.BEVRAGING_BIJHOUDING, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_bvgGeefMedebewoners_R.
     */
    BHG_BVG_GEEF_MEDEBEWONERS_R("bhg_bvgGeefMedebewoners_R", "bhg_bvgGeefMedebewoners_R", BurgerzakenModule.BEVRAGING_BIJHOUDING, Koppelvlak.BIJHOUDING,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Af11.
     */
    AF11("Af11", "Af11", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Ag01.
     */
    AG01("Ag01", "Ag01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Ag11.
     */
    AG11("Ag11", "Ag11", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Ag21.
     */
    AG21("Ag21", "Ag21", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Ag31.
     */
    AG31("Ag31", "Ag31", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Ap01.
     */
    AP01("Ap01", "Ap01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Av01.
     */
    AV01("Av01", "Av01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Gv01.
     */
    GV01("Gv01", "Gv01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Gv02.
     */
    GV02("Gv02", "Gv02", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Ha01.
     */
    HA01("Ha01", "Ha01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Hf01.
     */
    HF01("Hf01", "Hf01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Hq01.
     */
    HQ01("Hq01", "Hq01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Ib01.
     */
    IB01("Ib01", "Ib01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * If01.
     */
    IF01("If01", "If01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * If21.
     */
    IF21("If21", "If21", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * If31.
     */
    IF31("If31", "If31", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * If41.
     */
    IF41("If41", "If41", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Ii01.
     */
    II01("Ii01", "Ii01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Iv01.
     */
    IV01("Iv01", "Iv01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Iv11.
     */
    IV11("Iv11", "Iv11", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Iv21.
     */
    IV21("Iv21", "Iv21", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * La01.
     */
    LA01("La01", "La01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Lf01.
     */
    LF01("Lf01", "Lf01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Lg01.
     */
    LG01("Lg01", "Lg01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Lq01.
     */
    LQ01("Lq01", "Lq01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Ng01.
     */
    NG01("Ng01", "Ng01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Of11.
     */
    OF11("Of11", "Of11", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Og11.
     */
    OG11("Og11", "Og11", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Pf01.
     */
    PF01("Pf01", "Pf01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Pf02.
     */
    PF02("Pf02", "Pf02", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Pf03.
     */
    PF03("Pf03", "Pf03", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Qf01.
     */
    QF01("Qf01", "Qf01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Qf11.
     */
    QF11("Qf11", "Qf11", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Qs01.
     */
    QS01("Qs01", "Qs01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Qv01.
     */
    QV01("Qv01", "Qv01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Sf01.
     */
    SF01("Sf01", "Sf01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Sv01.
     */
    SV01("Sv01", "Sv01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Sv11.
     */
    SV11("Sv11", "Sv11", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Tb01.
     */
    TB01("Tb01", "Tb01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Tb02.
     */
    TB02("Tb02", "Tb02", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Tf01.
     */
    TF01("Tf01", "Tf01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Tf11.
     */
    TF11("Tf11", "Tf11", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Tf21.
     */
    TF21("Tf21", "Tf21", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Tv01.
     */
    TV01("Tv01", "Tv01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Vb01.
     */
    VB01("Vb01", "Vb01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Wa01.
     */
    WA01("Wa01", "Wa01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Wa11.
     */
    WA11("Wa11", "Wa11", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Wf01.
     */
    WF01("Wf01", "Wf01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Xa01.
     */
    XA01("Xa01", "Xa01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Xf01.
     */
    XF01("Xf01", "Xf01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Xq01.
     */
    XQ01("Xq01", "Xq01", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Null.
     */
    NULL("Null", "Null", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Onbekend.
     */
    ONBEKEND("Onbekend", "Onbekend", null, Koppelvlak.GBA, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_vbaRegistreerVerblijfsrecht.
     */
    BHG_VBA_REGISTREER_VERBLIJFSRECHT("bhg_vbaRegistreerVerblijfsrecht", "bhg_vbaRegistreerVerblijfsrecht", BurgerzakenModule.VERBLIJF_EN_ADRES,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_vbaRegistreerVerblijfsrecht_R.
     */
    BHG_VBA_REGISTREER_VERBLIJFSRECHT_R("bhg_vbaRegistreerVerblijfsrecht_R", "bhg_vbaRegistreerVerblijfsrecht_R", BurgerzakenModule.VERBLIJF_EN_ADRES,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * isc_migRegistreerGeboorte.
     */
    ISC_MIG_REGISTREER_GEBOORTE("isc_migRegistreerGeboorte", "isc_migRegistreerGeboorte", BurgerzakenModule.MIGRATIEVOORZIENINGEN, Koppelvlak.I_S_C,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * isc_migRegistreerGeboorte_R.
     */
    ISC_MIG_REGISTREER_GEBOORTE_R("isc_migRegistreerGeboorte_R", "isc_migRegistreerGeboorte_R", BurgerzakenModule.MIGRATIEVOORZIENINGEN, Koppelvlak.I_S_C,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * isc_migRegistreerOuderschap.
     */
    ISC_MIG_REGISTREER_OUDERSCHAP("isc_migRegistreerOuderschap", "isc_migRegistreerOuderschap", BurgerzakenModule.MIGRATIEVOORZIENINGEN, Koppelvlak.I_S_C,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * isc_migRegistreerOuderschap_R.
     */
    ISC_MIG_REGISTREER_OUDERSCHAP_R("isc_migRegistreerOuderschap_R", "isc_migRegistreerOuderschap_R", BurgerzakenModule.MIGRATIEVOORZIENINGEN,
            Koppelvlak.I_S_C, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * isc_migRegistreerNaamGeslacht.
     */
    ISC_MIG_REGISTREER_NAAM_GESLACHT("isc_migRegistreerNaamGeslacht", "isc_migRegistreerNaamGeslacht", BurgerzakenModule.MIGRATIEVOORZIENINGEN,
            Koppelvlak.I_S_C, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * isc_migRegistreerNaamGeslacht_R.
     */
    ISC_MIG_REGISTREER_NAAM_GESLACHT_R("isc_migRegistreerNaamGeslacht_R", "isc_migRegistreerNaamGeslacht_R", BurgerzakenModule.MIGRATIEVOORZIENINGEN,
            Koppelvlak.I_S_C, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * isc_migRegistreerHuwelijkGeregistreerdPartnerschap.
     */
    ISC_MIG_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP("isc_migRegistreerHuwelijkGeregistreerdPartnerschap",
            "isc_migRegistreerHuwelijkGeregistreerdPartnerschap", BurgerzakenModule.MIGRATIEVOORZIENINGEN, Koppelvlak.I_S_C,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R.
     */
    ISC_MIG_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP_R("isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R",
            "isc_migRegistreerHuwelijkGeregistreerdPartnerschap_R", BurgerzakenModule.MIGRATIEVOORZIENINGEN, Koppelvlak.I_S_C,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * isc_migRegistreerOverlijden.
     */
    ISC_MIG_REGISTREER_OVERLIJDEN("isc_migRegistreerOverlijden", "isc_migRegistreerOverlijden", BurgerzakenModule.MIGRATIEVOORZIENINGEN, Koppelvlak.I_S_C,
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * isc_migRegistreerOverlijden_R.
     */
    ISC_MIG_REGISTREER_OVERLIJDEN_R("isc_migRegistreerOverlijden_R", "isc_migRegistreerOverlijden_R", BurgerzakenModule.MIGRATIEVOORZIENINGEN,
            Koppelvlak.I_S_C, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * bhg_fiaNotificeerBijhoudingsplan.
     */
    BHG_FIA_NOTIFICEER_BIJHOUDINGSPLAN("bhg_fiaNotificeerBijhoudingsplan", "bhg_fiaNotificeerBijhoudingsplan", BurgerzakenModule.FIATTERING,
            Koppelvlak.BIJHOUDING, new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231));

    private final String identifier;
    private final String naam;
    private final BurgerzakenModule module;
    private final Koppelvlak koppelvlak;
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param identifier Identifier voor SoortBericht
     * @param naam Naam voor SoortBericht
     * @param module Module voor SoortBericht
     * @param koppelvlak Koppelvlak voor SoortBericht
     * @param datumAanvangGeldigheid DatumAanvangGeldigheid voor SoortBericht
     * @param datumEindeGeldigheid DatumEindeGeldigheid voor SoortBericht
     */
    private SoortBericht(
        final String identifier,
        final String naam,
        final BurgerzakenModule module,
        final Koppelvlak koppelvlak,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        this.identifier = identifier;
        this.naam = naam;
        this.module = module;
        this.koppelvlak = koppelvlak;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Retourneert Identifier van Soort bericht.
     *
     * @return Identifier.
     */
    public String getIdentifier() {
        return identifier;
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

    /**
     * Retourneert Koppelvlak van Soort bericht.
     *
     * @return Koppelvlak.
     */
    public Koppelvlak getKoppelvlak() {
        return koppelvlak;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Soort bericht.
     *
     * @return Datum aanvang geldigheid voor Soort bericht
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Soort bericht.
     *
     * @return Datum einde geldigheid voor Soort bericht
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

}
