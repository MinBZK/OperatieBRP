/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;

/**
 * Mapping van id's van gegevenselementen uit BMR (sync-id) op BRP-expressies.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public final class ExpressieGroepMap {

    /**
     * Private constructor voor utility class.
     *
     */
    private ExpressieGroepMap() {
    }

    /**
     * Geeft de mapping van id's van gegevenselementen uit BMR op BRP-expressies.
     *
     * @return Mapping van id's van groep-elementen op BRP-expressies
     */
    public static Map<ElementEnum, List<String>> getGroepVerantwoordingMap() {
        Map<ElementEnum, List<String>> expressieGroepVerantwoordingMap = new HashMap<>();
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF).add("$administratief.verantwoordingInhoud.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF).add("$administratief.verantwoordingInhoud.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF).add("$administratief.verantwoordingInhoud.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF).add("$administratief.verantwoordingInhoud.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF).add("$administratief.verantwoordingVerval.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF).add("$administratief.verantwoordingVerval.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF).add("$administratief.verantwoordingVerval.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF).add("$administratief.verantwoordingVerval.datum_ontlening");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_IDENTIFICATIENUMMERS, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS).add("$identificatienummers.verantwoordingInhoud.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS).add("$identificatienummers.verantwoordingInhoud.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS).add(
            "$identificatienummers.verantwoordingInhoud.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS).add("$identificatienummers.verantwoordingInhoud.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS).add("$identificatienummers.verantwoordingVerval.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS).add("$identificatienummers.verantwoordingVerval.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS).add(
            "$identificatienummers.verantwoordingVerval.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS).add("$identificatienummers.verantwoordingVerval.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS)
                                       .add("$identificatienummers.verantwoordingAanpassingGeldigheid.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS).add(
            "$identificatienummers.verantwoordingAanpassingGeldigheid.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS).add(
            "$identificatienummers.verantwoordingAanpassingGeldigheid.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS).add(
            "$identificatienummers.verantwoordingAanpassingGeldigheid.datum_ontlening");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_SAMENGESTELDENAAM, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add("$samengestelde_naam.verantwoordingInhoud.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add("$samengestelde_naam.verantwoordingInhoud.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add("$samengestelde_naam.verantwoordingInhoud.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add("$samengestelde_naam.verantwoordingInhoud.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add("$samengestelde_naam.verantwoordingVerval.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add("$samengestelde_naam.verantwoordingVerval.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add("$samengestelde_naam.verantwoordingVerval.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add("$samengestelde_naam.verantwoordingVerval.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add("$samengestelde_naam.verantwoordingAanpassingGeldigheid.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add("$samengestelde_naam.verantwoordingAanpassingGeldigheid.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add(
            "$samengestelde_naam.verantwoordingAanpassingGeldigheid.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add(
            "$samengestelde_naam.verantwoordingAanpassingGeldigheid.datum_ontlening");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_GEBOORTE, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GEBOORTE).add("$geboorte.verantwoordingInhoud.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GEBOORTE).add("$geboorte.verantwoordingInhoud.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GEBOORTE).add("$geboorte.verantwoordingInhoud.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GEBOORTE).add("$geboorte.verantwoordingInhoud.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GEBOORTE).add("$geboorte.verantwoordingVerval.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GEBOORTE).add("$geboorte.verantwoordingVerval.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GEBOORTE).add("$geboorte.verantwoordingVerval.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GEBOORTE).add("$geboorte.verantwoordingVerval.datum_ontlening");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_GESLACHTSAANDUIDING, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add("$geslachtsaanduiding.verantwoordingInhoud.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add("$geslachtsaanduiding.verantwoordingInhoud.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add("$geslachtsaanduiding.verantwoordingInhoud.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add("$geslachtsaanduiding.verantwoordingInhoud.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add("$geslachtsaanduiding.verantwoordingVerval.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add("$geslachtsaanduiding.verantwoordingVerval.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add("$geslachtsaanduiding.verantwoordingVerval.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add("$geslachtsaanduiding.verantwoordingVerval.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add("$geslachtsaanduiding.verantwoordingAanpassingGeldigheid.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add("$geslachtsaanduiding.verantwoordingAanpassingGeldigheid.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add(
            "$geslachtsaanduiding.verantwoordingAanpassingGeldigheid.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add(
            "$geslachtsaanduiding.verantwoordingAanpassingGeldigheid.datum_ontlening");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_INSCHRIJVING, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_INSCHRIJVING).add("$inschrijving.verantwoordingInhoud.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_INSCHRIJVING).add("$inschrijving.verantwoordingInhoud.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_INSCHRIJVING).add("$inschrijving.verantwoordingInhoud.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_INSCHRIJVING).add("$inschrijving.verantwoordingInhoud.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_INSCHRIJVING).add("$inschrijving.verantwoordingVerval.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_INSCHRIJVING).add("$inschrijving.verantwoordingVerval.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_INSCHRIJVING).add("$inschrijving.verantwoordingVerval.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_INSCHRIJVING).add("$inschrijving.verantwoordingVerval.datum_ontlening");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_NUMMERVERWIJZING, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add("$nummerverwijzing.verantwoordingInhoud.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add("$nummerverwijzing.verantwoordingInhoud.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add("$nummerverwijzing.verantwoordingInhoud.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add("$nummerverwijzing.verantwoordingInhoud.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add("$nummerverwijzing.verantwoordingVerval.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add("$nummerverwijzing.verantwoordingVerval.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add("$nummerverwijzing.verantwoordingVerval.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add("$nummerverwijzing.verantwoordingVerval.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add("$nummerverwijzing.verantwoordingAanpassingGeldigheid.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add("$nummerverwijzing.verantwoordingAanpassingGeldigheid.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add(
            "$nummerverwijzing.verantwoordingAanpassingGeldigheid.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add(
            "$nummerverwijzing.verantwoordingAanpassingGeldigheid.datum_ontlening");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_BIJHOUDING, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.verantwoordingInhoud.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.verantwoordingInhoud.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.verantwoordingInhoud.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.verantwoordingInhoud.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.verantwoordingVerval.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.verantwoordingVerval.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.verantwoordingVerval.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.verantwoordingVerval.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.verantwoordingAanpassingGeldigheid.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.verantwoordingAanpassingGeldigheid.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.verantwoordingAanpassingGeldigheid.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.verantwoordingAanpassingGeldigheid.datum_ontlening");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_OVERLIJDEN, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_OVERLIJDEN).add("$overlijden.verantwoordingInhoud.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_OVERLIJDEN).add("$overlijden.verantwoordingInhoud.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_OVERLIJDEN).add("$overlijden.verantwoordingInhoud.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_OVERLIJDEN).add("$overlijden.verantwoordingInhoud.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_OVERLIJDEN).add("$overlijden.verantwoordingVerval.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_OVERLIJDEN).add("$overlijden.verantwoordingVerval.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_OVERLIJDEN).add("$overlijden.verantwoordingVerval.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_OVERLIJDEN).add("$overlijden.verantwoordingVerval.datum_ontlening");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_NAAMGEBRUIK, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NAAMGEBRUIK).add("$naamgebruik.verantwoordingInhoud.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NAAMGEBRUIK).add("$naamgebruik.verantwoordingInhoud.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NAAMGEBRUIK).add("$naamgebruik.verantwoordingInhoud.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NAAMGEBRUIK).add("$naamgebruik.verantwoordingInhoud.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NAAMGEBRUIK).add("$naamgebruik.verantwoordingVerval.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NAAMGEBRUIK).add("$naamgebruik.verantwoordingVerval.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NAAMGEBRUIK).add("$naamgebruik.verantwoordingVerval.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NAAMGEBRUIK).add("$naamgebruik.verantwoordingVerval.datum_ontlening");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_MIGRATIE, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.verantwoordingInhoud.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.verantwoordingInhoud.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.verantwoordingInhoud.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.verantwoordingInhoud.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.verantwoordingVerval.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.verantwoordingVerval.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.verantwoordingVerval.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.verantwoordingVerval.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.verantwoordingAanpassingGeldigheid.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.verantwoordingAanpassingGeldigheid.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.verantwoordingAanpassingGeldigheid.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.verantwoordingAanpassingGeldigheid.datum_ontlening");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_VERBLIJFSRECHT, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERBLIJFSRECHT).add("$verblijfsrecht.verantwoordingInhoud.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERBLIJFSRECHT).add("$verblijfsrecht.verantwoordingInhoud.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERBLIJFSRECHT).add("$verblijfsrecht.verantwoordingInhoud.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERBLIJFSRECHT).add("$verblijfsrecht.verantwoordingInhoud.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERBLIJFSRECHT).add("$verblijfsrecht.verantwoordingVerval.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERBLIJFSRECHT).add("$verblijfsrecht.verantwoordingVerval.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERBLIJFSRECHT).add("$verblijfsrecht.verantwoordingVerval.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERBLIJFSRECHT).add("$verblijfsrecht.verantwoordingVerval.datum_ontlening");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_UITSLUITINGKIESRECHT, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_UITSLUITINGKIESRECHT).add("$uitsluiting_kiesrecht.verantwoordingInhoud.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_UITSLUITINGKIESRECHT).add("$uitsluiting_kiesrecht.verantwoordingInhoud.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_UITSLUITINGKIESRECHT).add(
            "$uitsluiting_kiesrecht.verantwoordingInhoud.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_UITSLUITINGKIESRECHT).add("$uitsluiting_kiesrecht.verantwoordingInhoud.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_UITSLUITINGKIESRECHT).add("$uitsluiting_kiesrecht.verantwoordingVerval.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_UITSLUITINGKIESRECHT).add("$uitsluiting_kiesrecht.verantwoordingVerval.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_UITSLUITINGKIESRECHT).add(
            "$uitsluiting_kiesrecht.verantwoordingVerval.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_UITSLUITINGKIESRECHT).add("$uitsluiting_kiesrecht.verantwoordingVerval.datum_ontlening");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN).add("$deelname_eu_verkiezingen.verantwoordingInhoud.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN).add("$deelname_eu_verkiezingen.verantwoordingInhoud.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN).add(
            "$deelname_eu_verkiezingen.verantwoordingInhoud.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN).add(
            "$deelname_eu_verkiezingen.verantwoordingInhoud.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN).add("$deelname_eu_verkiezingen.verantwoordingVerval.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN).add("$deelname_eu_verkiezingen.verantwoordingVerval.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN).add(
            "$deelname_eu_verkiezingen.verantwoordingVerval.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN).add(
            "$deelname_eu_verkiezingen.verantwoordingVerval.datum_ontlening");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_PERSOONSKAART, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_PERSOONSKAART).add("$persoonskaart.verantwoordingInhoud.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_PERSOONSKAART).add("$persoonskaart.verantwoordingInhoud.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_PERSOONSKAART).add("$persoonskaart.verantwoordingInhoud.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_PERSOONSKAART).add("$persoonskaart.verantwoordingInhoud.datum_ontlening");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_PERSOONSKAART).add("$persoonskaart.verantwoordingVerval.soort");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_PERSOONSKAART).add("$persoonskaart.verantwoordingVerval.partij");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_PERSOONSKAART).add("$persoonskaart.verantwoordingVerval.tijdstip_registratie");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_PERSOONSKAART).add("$persoonskaart.verantwoordingVerval.datum_ontlening");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_VOORNAAM_STANDAARD, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD).add("RMAP(voornamen, v, $v.verantwoordingInhoud.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD).add("RMAP(voornamen, v, $v.verantwoordingInhoud.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD)
                                       .add("RMAP(voornamen, v, $v.verantwoordingInhoud.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD).add("RMAP(voornamen, v, $v.verantwoordingInhoud.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD).add("RMAP(voornamen, v, $v.verantwoordingVerval.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD).add("RMAP(voornamen, v, $v.verantwoordingVerval.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD)
                                       .add("RMAP(voornamen, v, $v.verantwoordingVerval.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD).add("RMAP(voornamen, v, $v.verantwoordingVerval.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD).add("RMAP(voornamen, v, $v.verantwoordingAanpassingGeldigheid.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD)
                                       .add("RMAP(voornamen, v, $v.verantwoordingAanpassingGeldigheid.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD).add(
            "RMAP(voornamen, v, $v.verantwoordingAanpassingGeldigheid.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD).add(
            "RMAP(voornamen, v, $v.verantwoordingAanpassingGeldigheid.datum_ontlening)");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.verantwoordingInhoud.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.verantwoordingInhoud.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.verantwoordingInhoud.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.verantwoordingInhoud.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.verantwoordingVerval.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.verantwoordingVerval.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.verantwoordingVerval.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.verantwoordingVerval.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.verantwoordingAanpassingGeldigheid.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.verantwoordingAanpassingGeldigheid.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.verantwoordingAanpassingGeldigheid.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.verantwoordingAanpassingGeldigheid.datum_ontlening)");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_VERIFICATIE_STANDAARD, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERIFICATIE_STANDAARD).add("RMAP(verificaties, v, $v.verantwoordingInhoud.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERIFICATIE_STANDAARD).add("RMAP(verificaties, v, $v.verantwoordingInhoud.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERIFICATIE_STANDAARD).add(
            "RMAP(verificaties, v, $v.verantwoordingInhoud.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERIFICATIE_STANDAARD).add(
            "RMAP(verificaties, v, $v.verantwoordingInhoud.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERIFICATIE_STANDAARD).add("RMAP(verificaties, v, $v.verantwoordingVerval.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERIFICATIE_STANDAARD).add("RMAP(verificaties, v, $v.verantwoordingVerval.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERIFICATIE_STANDAARD).add(
            "RMAP(verificaties, v, $v.verantwoordingVerval.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERIFICATIE_STANDAARD).add(
            "RMAP(verificaties, v, $v.verantwoordingVerval.datum_ontlening)");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add("RMAP(nationaliteiten, v, $v.verantwoordingInhoud.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add("RMAP(nationaliteiten, v, $v.verantwoordingInhoud.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add(
            "RMAP(nationaliteiten, v, $v.verantwoordingInhoud.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add(
            "RMAP(nationaliteiten, v, $v.verantwoordingInhoud.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add("RMAP(nationaliteiten, v, $v.verantwoordingVerval.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add("RMAP(nationaliteiten, v, $v.verantwoordingVerval.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add(
            "RMAP(nationaliteiten, v, $v.verantwoordingVerval.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add(
            "RMAP(nationaliteiten, v, $v.verantwoordingVerval.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add(
            "RMAP(nationaliteiten, v, $v.verantwoordingAanpassingGeldigheid.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add(
            "RMAP(nationaliteiten, v, $v.verantwoordingAanpassingGeldigheid.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add(
            "RMAP(nationaliteiten, v, $v.verantwoordingAanpassingGeldigheid.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add(
            "RMAP(nationaliteiten, v, $v.verantwoordingAanpassingGeldigheid.datum_ontlening)");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_ADRES_STANDAARD, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add("RMAP(adressen, v, $v.verantwoordingInhoud.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add("RMAP(adressen, v, $v.verantwoordingInhoud.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add("RMAP(adressen, v, $v.verantwoordingInhoud.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add("RMAP(adressen, v, $v.verantwoordingInhoud.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add("RMAP(adressen, v, $v.verantwoordingVerval.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add("RMAP(adressen, v, $v.verantwoordingVerval.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add("RMAP(adressen, v, $v.verantwoordingVerval.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add("RMAP(adressen, v, $v.verantwoordingVerval.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add("RMAP(adressen, v, $v.verantwoordingAanpassingGeldigheid.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add("RMAP(adressen, v, $v.verantwoordingAanpassingGeldigheid.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add(
            "RMAP(adressen, v, $v.verantwoordingAanpassingGeldigheid.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add(
            "RMAP(adressen, v, $v.verantwoordingAanpassingGeldigheid.datum_ontlening)");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_REISDOCUMENT_STANDAARD, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_REISDOCUMENT_STANDAARD).add("RMAP(reisdocumenten, v, $v.verantwoordingInhoud.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_REISDOCUMENT_STANDAARD).add("RMAP(reisdocumenten, v, $v.verantwoordingInhoud.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_REISDOCUMENT_STANDAARD).add(
            "RMAP(reisdocumenten, v, $v.verantwoordingInhoud.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_REISDOCUMENT_STANDAARD).add(
            "RMAP(reisdocumenten, v, $v.verantwoordingInhoud.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_REISDOCUMENT_STANDAARD).add("RMAP(reisdocumenten, v, $v.verantwoordingVerval.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_REISDOCUMENT_STANDAARD).add("RMAP(reisdocumenten, v, $v.verantwoordingVerval.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_REISDOCUMENT_STANDAARD).add(
            "RMAP(reisdocumenten, v, $v.verantwoordingVerval.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_REISDOCUMENT_STANDAARD).add(
            "RMAP(reisdocumenten, v, $v.verantwoordingVerval.datum_ontlening)");
        expressieGroepVerantwoordingMap.put(ElementEnum.BETROKKENHEID_IDENTITEIT, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.BETROKKENHEID_IDENTITEIT).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.verantwoordingInhoud.soort))");
        expressieGroepVerantwoordingMap.get(ElementEnum.BETROKKENHEID_IDENTITEIT).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.verantwoordingInhoud.partij))");
        expressieGroepVerantwoordingMap.get(ElementEnum.BETROKKENHEID_IDENTITEIT).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.verantwoordingInhoud.tijdstip_registratie))");
        expressieGroepVerantwoordingMap.get(ElementEnum.BETROKKENHEID_IDENTITEIT).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.verantwoordingInhoud.datum_ontlening))");
        expressieGroepVerantwoordingMap.get(ElementEnum.BETROKKENHEID_IDENTITEIT).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.verantwoordingVerval.soort))");
        expressieGroepVerantwoordingMap.get(ElementEnum.BETROKKENHEID_IDENTITEIT).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.verantwoordingVerval.partij))");
        expressieGroepVerantwoordingMap.get(ElementEnum.BETROKKENHEID_IDENTITEIT).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.verantwoordingVerval.tijdstip_registratie))");
        expressieGroepVerantwoordingMap.get(ElementEnum.BETROKKENHEID_IDENTITEIT).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.verantwoordingVerval.datum_ontlening))");
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT).add(
            "RMAP(verstrekkingsbeperkingen, v, $v.verantwoordingInhoud.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT).add(
            "RMAP(verstrekkingsbeperkingen, v, $v.verantwoordingInhoud.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT).add(
            "RMAP(verstrekkingsbeperkingen, v, $v.verantwoordingInhoud.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT).add(
            "RMAP(verstrekkingsbeperkingen, v, $v.verantwoordingInhoud.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT).add(
            "RMAP(verstrekkingsbeperkingen, v, $v.verantwoordingVerval.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT).add(
            "RMAP(verstrekkingsbeperkingen, v, $v.verantwoordingVerval.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT).add(
            "RMAP(verstrekkingsbeperkingen, v, $v.verantwoordingVerval.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT).add(
            "RMAP(verstrekkingsbeperkingen, v, $v.verantwoordingVerval.datum_ontlening)");
        expressieGroepVerantwoordingMap.put(ElementEnum.ONDERZOEK_STANDAARD, new ArrayList<String>());
        expressieGroepVerantwoordingMap.put(ElementEnum.PERSOON_ONDERZOEK_STANDAARD, new ArrayList<String>());
        expressieGroepVerantwoordingMap.put(ElementEnum.OUDER_OUDERSCHAP, new ArrayList<String>());
        expressieGroepVerantwoordingMap.put(ElementEnum.OUDER_OUDERLIJKGEZAG, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.verantwoordingInhoud.soort))");
        expressieGroepVerantwoordingMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.verantwoordingInhoud.partij))");
        expressieGroepVerantwoordingMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.verantwoordingInhoud.tijdstip_registratie))");
        expressieGroepVerantwoordingMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.verantwoordingInhoud.datum_ontlening))");
        expressieGroepVerantwoordingMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.verantwoordingVerval.soort))");
        expressieGroepVerantwoordingMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.verantwoordingVerval.partij))");
        expressieGroepVerantwoordingMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.verantwoordingVerval.tijdstip_registratie))");
        expressieGroepVerantwoordingMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.verantwoordingVerval.datum_ontlening))");
        expressieGroepVerantwoordingMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.verantwoordingAanpassingGeldigheid.soort))");
        expressieGroepVerantwoordingMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.verantwoordingAanpassingGeldigheid.partij))");
        expressieGroepVerantwoordingMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.verantwoordingAanpassingGeldigheid.tijdstip_registratie))");
        expressieGroepVerantwoordingMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.verantwoordingAanpassingGeldigheid.datum_ontlening))");
        expressieGroepVerantwoordingMap.put(ElementEnum.RELATIE_STANDAARD, new ArrayList<String>());
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(HUWELIJKEN(), h, $h.verantwoordingInhoud.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(HUWELIJKEN(), h, $h.verantwoordingInhoud.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(HUWELIJKEN(), h, $h.verantwoordingInhoud.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(HUWELIJKEN(), h, $h.verantwoordingInhoud.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(HUWELIJKEN(), h, $h.verantwoordingVerval.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(HUWELIJKEN(), h, $h.verantwoordingVerval.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(HUWELIJKEN(), h, $h.verantwoordingVerval.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(HUWELIJKEN(), h, $h.verantwoordingVerval.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(PARTNERSCHAPPEN(), h, $h.verantwoordingInhoud.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(PARTNERSCHAPPEN(), h, $h.verantwoordingInhoud.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(PARTNERSCHAPPEN(), h, $h.verantwoordingInhoud.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(PARTNERSCHAPPEN(), h, $h.verantwoordingInhoud.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(PARTNERSCHAPPEN(), h, $h.verantwoordingVerval.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(PARTNERSCHAPPEN(), h, $h.verantwoordingVerval.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(PARTNERSCHAPPEN(), h, $h.verantwoordingVerval.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(PARTNERSCHAPPEN(), h, $h.verantwoordingVerval.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.verantwoordingInhoud.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD)
                                       .add("RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.verantwoordingInhoud.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add(
            "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.verantwoordingInhoud.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add(
            "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.verantwoordingInhoud.datum_ontlening)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add("RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.verantwoordingVerval.soort)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD)
                                       .add("RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.verantwoordingVerval.partij)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add(
            "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.verantwoordingVerval.tijdstip_registratie)");
        expressieGroepVerantwoordingMap.get(ElementEnum.RELATIE_STANDAARD).add(
            "RMAP(FAMILIERECHTELIJKEBETREKKINGEN(), h, $h.verantwoordingVerval.datum_ontlening)");
        return expressieGroepVerantwoordingMap;
    }

    /**
     * Geeft de mapping van id's van gegevenselementen uit BMR op BRP-expressies.
     *
     * @return Mapping van id's van groep-elementen op BRP-expressies
     */
    public static Map<ElementEnum, List<String>> getGroepFormeleHistorieMap() {
        Map<ElementEnum, List<String>> expressieGroepFormeleHistorieMap = new HashMap<>();
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF).add("$administratief.datum_tijd_registratie");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_AFGELEIDADMINISTRATIEF).add("$administratief.datum_tijd_verval");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_IDENTIFICATIENUMMERS, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS).add("$identificatienummers.datum_tijd_registratie");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS).add("$identificatienummers.datum_tijd_verval");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_SAMENGESTELDENAAM, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add("$samengestelde_naam.datum_tijd_registratie");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add("$samengestelde_naam.datum_tijd_verval");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_GEBOORTE, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_GEBOORTE).add("$geboorte.datum_tijd_registratie");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_GEBOORTE).add("$geboorte.datum_tijd_verval");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_GESLACHTSAANDUIDING, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add("$geslachtsaanduiding.datum_tijd_registratie");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add("$geslachtsaanduiding.datum_tijd_verval");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_INSCHRIJVING, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_INSCHRIJVING).add("$inschrijving.datum_tijd_registratie");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_INSCHRIJVING).add("$inschrijving.datum_tijd_verval");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_NUMMERVERWIJZING, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add("$nummerverwijzing.datum_tijd_registratie");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add("$nummerverwijzing.datum_tijd_verval");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_BIJHOUDING, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.datum_tijd_registratie");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.datum_tijd_verval");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_OVERLIJDEN, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_OVERLIJDEN).add("$overlijden.datum_tijd_registratie");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_OVERLIJDEN).add("$overlijden.datum_tijd_verval");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_NAAMGEBRUIK, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_NAAMGEBRUIK).add("$naamgebruik.datum_tijd_registratie");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_NAAMGEBRUIK).add("$naamgebruik.datum_tijd_verval");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_MIGRATIE, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.datum_tijd_registratie");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.datum_tijd_verval");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_VERBLIJFSRECHT, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_VERBLIJFSRECHT).add("$verblijfsrecht.datum_tijd_registratie");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_VERBLIJFSRECHT).add("$verblijfsrecht.datum_tijd_verval");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_UITSLUITINGKIESRECHT, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_UITSLUITINGKIESRECHT).add("$uitsluiting_kiesrecht.datum_tijd_registratie");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_UITSLUITINGKIESRECHT).add("$uitsluiting_kiesrecht.datum_tijd_verval");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN).add("$deelname_eu_verkiezingen.datum_tijd_registratie");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_DEELNAMEEUVERKIEZINGEN).add("$deelname_eu_verkiezingen.datum_tijd_verval");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_PERSOONSKAART, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_PERSOONSKAART).add("$persoonskaart.datum_tijd_registratie");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_PERSOONSKAART).add("$persoonskaart.datum_tijd_verval");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_VOORNAAM_STANDAARD, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD).add("RMAP(voornamen, v, $v.datum_tijd_registratie)");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD).add("RMAP(voornamen, v, $v.datum_tijd_verval)");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.datum_tijd_registratie)");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.datum_tijd_verval)");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_VERIFICATIE_STANDAARD, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_VERIFICATIE_STANDAARD).add("RMAP(verificaties, v, $v.datum_tijd_registratie)");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_VERIFICATIE_STANDAARD).add("RMAP(verificaties, v, $v.datum_tijd_verval)");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add("RMAP(nationaliteiten, v, $v.datum_tijd_registratie)");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add("RMAP(nationaliteiten, v, $v.datum_tijd_verval)");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_ADRES_STANDAARD, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add("RMAP(adressen, v, $v.datum_tijd_registratie)");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add("RMAP(adressen, v, $v.datum_tijd_verval)");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_REISDOCUMENT_STANDAARD, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_REISDOCUMENT_STANDAARD).add("RMAP(reisdocumenten, v, $v.datum_tijd_registratie)");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_REISDOCUMENT_STANDAARD).add("RMAP(reisdocumenten, v, $v.datum_tijd_verval)");
        expressieGroepFormeleHistorieMap.put(ElementEnum.BETROKKENHEID_IDENTITEIT, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.BETROKKENHEID_IDENTITEIT).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.datum_tijd_registratie))");
        expressieGroepFormeleHistorieMap.get(ElementEnum.BETROKKENHEID_IDENTITEIT).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.datum_tijd_verval))");
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT).add(
            "RMAP(verstrekkingsbeperkingen, v, $v.datum_tijd_registratie)");
        expressieGroepFormeleHistorieMap.get(ElementEnum.PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT).add(
            "RMAP(verstrekkingsbeperkingen, v, $v.datum_tijd_verval)");
        expressieGroepFormeleHistorieMap.put(ElementEnum.ONDERZOEK_STANDAARD, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.put(ElementEnum.PERSOON_ONDERZOEK_STANDAARD, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.put(ElementEnum.OUDER_OUDERSCHAP, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.put(ElementEnum.OUDER_OUDERLIJKGEZAG, new ArrayList<String>());
        expressieGroepFormeleHistorieMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.datum_tijd_registratie))");
        expressieGroepFormeleHistorieMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.datum_tijd_verval))");
        return expressieGroepFormeleHistorieMap;
    }

    /**
     * Geeft de mapping van id's van gegevenselementen uit BMR op BRP-expressies.
     *
     * @return Mapping van id's van groep-elementen op BRP-expressies
     */
    public static Map<ElementEnum, List<String>> getGroepMaterieleHistorieMap() {
        Map<ElementEnum, List<String>> expressieGroepMaterieleHistorieMap = new HashMap<>();
        expressieGroepMaterieleHistorieMap.put(ElementEnum.PERSOON_IDENTIFICATIENUMMERS, new ArrayList<String>());
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS).add("$identificatienummers.datum_aanvang_geldigheid");
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_IDENTIFICATIENUMMERS).add("$identificatienummers.datum_einde_geldigheid");
        expressieGroepMaterieleHistorieMap.put(ElementEnum.PERSOON_SAMENGESTELDENAAM, new ArrayList<String>());
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add("$samengestelde_naam.datum_aanvang_geldigheid");
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_SAMENGESTELDENAAM).add("$samengestelde_naam.datum_einde_geldigheid");
        expressieGroepMaterieleHistorieMap.put(ElementEnum.PERSOON_GESLACHTSAANDUIDING, new ArrayList<String>());
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add("$geslachtsaanduiding.datum_aanvang_geldigheid");
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_GESLACHTSAANDUIDING).add("$geslachtsaanduiding.datum_einde_geldigheid");
        expressieGroepMaterieleHistorieMap.put(ElementEnum.PERSOON_NUMMERVERWIJZING, new ArrayList<String>());
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add("$nummerverwijzing.datum_aanvang_geldigheid");
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_NUMMERVERWIJZING).add("$nummerverwijzing.datum_einde_geldigheid");
        expressieGroepMaterieleHistorieMap.put(ElementEnum.PERSOON_BIJHOUDING, new ArrayList<String>());
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.datum_aanvang_geldigheid");
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_BIJHOUDING).add("$bijhouding.datum_einde_geldigheid");
        expressieGroepMaterieleHistorieMap.put(ElementEnum.PERSOON_MIGRATIE, new ArrayList<String>());
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.datum_aanvang_geldigheid");
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_MIGRATIE).add("$migratie.datum_einde_geldigheid");
        expressieGroepMaterieleHistorieMap.put(ElementEnum.PERSOON_VOORNAAM_STANDAARD, new ArrayList<String>());
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD).add("RMAP(voornamen, v, $v.datum_aanvang_geldigheid)");
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_VOORNAAM_STANDAARD).add("RMAP(voornamen, v, $v.datum_einde_geldigheid)");
        expressieGroepMaterieleHistorieMap.put(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD, new ArrayList<String>());
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.datum_aanvang_geldigheid)");
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD).add(
            "RMAP(geslachtsnaamcomponenten, v, $v.datum_einde_geldigheid)");
        expressieGroepMaterieleHistorieMap.put(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD, new ArrayList<String>());
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add("RMAP(nationaliteiten, v, $v.datum_aanvang_geldigheid)");
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_NATIONALITEIT_STANDAARD).add("RMAP(nationaliteiten, v, $v.datum_einde_geldigheid)");
        expressieGroepMaterieleHistorieMap.put(ElementEnum.PERSOON_ADRES_STANDAARD, new ArrayList<String>());
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add("RMAP(adressen, v, $v.datum_aanvang_geldigheid)");
        expressieGroepMaterieleHistorieMap.get(ElementEnum.PERSOON_ADRES_STANDAARD).add("RMAP(adressen, v, $v.datum_einde_geldigheid)");
        expressieGroepMaterieleHistorieMap.put(ElementEnum.OUDER_OUDERSCHAP, new ArrayList<String>());
        expressieGroepMaterieleHistorieMap.put(ElementEnum.OUDER_OUDERLIJKGEZAG, new ArrayList<String>());
        expressieGroepMaterieleHistorieMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.datum_aanvang_geldigheid))");
        expressieGroepMaterieleHistorieMap.get(ElementEnum.OUDER_OUDERLIJKGEZAG).add(
            "PLATTE_LIJST(MAP(persoon.betrokkenheden, b, $b.ouderlijk_gezag.datum_einde_geldigheid))");
        return expressieGroepMaterieleHistorieMap;
    }

}
