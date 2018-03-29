/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.autorisatie;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.springframework.util.Assert;

/**
 */
public final class Partijen {

    private static final List<Partij> PARTIJ_LIST;
    private static final Map<String, Partij> PARTIJ_MAP;
    private static final Map<String, PartijRol> PARTIJ_ROL_MAP;

    static {
        short partijIdIndex = 1;
        final int datumIngang20100101 = 20100101;

        final Map<String, PartijRol> partijRolMap = Maps.newHashMap();
        final Map<String, Partij> partijMap = Maps.newHashMap();

        {
            final Partij partij = new Partij("BRP Voorziening", "199903");
            partij.setId(partijIdIndex++);
            partij.setDatumIngang(datumIngang20100101);
            voegToe(partijMap, partij);
        }
        {
            final Partij partij = new Partij("Gemeente Utrecht", "034401");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647009");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("Gemeente Haarlem", "039201");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001005650000");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("Gemeente Olst", "017401");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001932603000");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("Gemeente Alkmaar", "036101");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001721926000");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("Gemeente Tiel", "028101");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001101857000");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("KUC033-PartijVerstrekkingsbeperking", "502707");
            partij.setId(partijIdIndex++);
            partij.setOin("502707");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            partij.setIndicatieVerstrekkingsbeperkingMogelijk(true);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("Stichting Interkerkelijke Ledenadministratie", "850001");
            partij.setId(partijIdIndex++);
            partij.setOin("850001");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            partij.setIndicatieVerstrekkingsbeperkingMogelijk(true);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("Minister", "199901");
            partij.setId(partijIdIndex++);
            partij.setOin("199901");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addMinisterRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("College", "000002");
            partij.setId(partijIdIndex++);
            partij.setOin("000002");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addCollegeRol(partij, partijRolMap);

        }
        {
            final Partij partij = new Partij("Migratievoorziening", "199902");
            partij.setId(partijIdIndex++);
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            //partijRolMap.put(partij.getNaam(), addAfnemerRol(partij, partijRolMap));

        }
        {
            final Partij partij = new Partij("DatumIngangOngeldigPartij", "000087");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001101857001");
            partij.setDatumIngang(DatumUtil.morgen());
            partij.setDatumEinde(DatumUtil.morgen());
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("DatumEindeVandaagPartij", "000088");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001101857002");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumEinde(DatumUtil.vandaag());
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("DatumEindeGisterenPartij", "000089");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001101857003");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumEinde(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("DatumIngangEnEindeLeegPartij", "000090");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001101857004");
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("DatumIngangGisterenDatumEindeMorgenPartij", "000091");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001101857005");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumEinde(DatumUtil.morgen());
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("DatumIngangVandaagDatumEindeMorgenPartij", "000092");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001101857006");
            partij.setDatumIngang(DatumUtil.vandaag());
            partij.setDatumEinde(DatumUtil.morgen());
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("DatumIngangNulEnEindeLeegPartij", "000093");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001101857007");
            partij.setDatumIngang(0);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("DatumIngangGroterDanDatumEindePartij", "000094");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001101857008");
            partij.setDatumIngang(DatumUtil.vandaag());
            partij.setDatumEinde(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }

        {
            final Partij partij = new Partij("GemeenteDatumEindeVandaag", "085002");
            partij.setId(partijIdIndex++);
            partij.setOin("85002");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumEinde(DatumUtil.vandaag());
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }

        {
            final Partij partij = new Partij("GemeenteDatumIngangDatumEindeInVerleden", "085004");
            partij.setId(partijIdIndex++);
            partij.setOin("85004");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumEinde(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijRolDatumIngangNULLDatumEindeNULL", "085005");
            partij.setId(partijIdIndex++);
            partij.setOin("85005");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addPartijRol(partij, partijRolMap, Rol.AFNEMER, null, null, true);
        }
        {
            final Partij partij = new Partij("PartijRolDatumIngang0DatumEindeNULL", "085006");
            partij.setId(partijIdIndex++);
            partij.setOin("85006");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addPartijRol(partij, partijRolMap, Rol.AFNEMER, 0, null, true);
        }
        {
            final Partij partij = new Partij("PartijRolDatumIngangGisterenDatumEindeNULL", "085007");
            partij.setId(partijIdIndex++);
            partij.setOin("85007");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addPartijRol(partij, partijRolMap, Rol.AFNEMER, DatumUtil.gisteren(), null, true);
        }
        {
            final Partij partij = new Partij("PartijRolDatumIngangVandaagDatumEindeMorgen", "085008");
            partij.setId(partijIdIndex++);
            partij.setOin("85008");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addPartijRol(partij, partijRolMap, Rol.AFNEMER, DatumUtil.vandaag(), DatumUtil.morgen(), true);
        }
        {
            final Partij partij = new Partij("PartijRolDatumIngangMorgenDatumEindeLeeg", "085009");
            partij.setId(partijIdIndex++);
            partij.setOin("85009");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addPartijRol(partij, partijRolMap, Rol.AFNEMER, DatumUtil.morgen(), null, true);
        }
        {
            final Partij partij = new Partij("PartijRolDatumIngangGisterenDatumEindeVandaag", "850050");
            partij.setId(partijIdIndex++);
            partij.setOin("850010");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addPartijRol(partij, partijRolMap, Rol.AFNEMER, DatumUtil.gisteren(), DatumUtil.vandaag(), true);
        }
        {
            final Partij partij = new Partij("PartijRolDatumIngangGisterenDatumEindeGisteren", "850077");
            partij.setId(partijIdIndex++);
            partij.setOin("850010");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addPartijRol(partij, partijRolMap, Rol.AFNEMER, DatumUtil.gisteren(), DatumUtil.gisteren(), true);
        }
        {
            final Partij partij = new Partij("PartijRolDatumIngangMorgenDatumEindeMorgen", "850051");
            partij.setId(partijIdIndex++);
            partij.setOin("850010");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addPartijRol(partij, partijRolMap, Rol.AFNEMER, DatumUtil.morgen(), DatumUtil.morgen(), true);
        }
        {
            final Partij partij = new Partij("PartijRol2DatumIngangGisterenDatumEindeGisteren", "850052");
            partij.setId(partijIdIndex++);
            partij.setOin("850010");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20150101);
            voegToe(partijMap, partij);
            addPartijRol(partij, partijRolMap, Rol.AFNEMER, DatumUtil.gisteren(), DatumUtil.gisteren(), true);
        }
        {
            final Partij partij = new Partij("PartijVrijBerichtDatumIngangGisterenDatumEindeLeeg", "850011");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647000");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijVrijBerichtDatumIngangVandaagDatumEindeMorgen", "850012");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001005650000");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.vandaag());
            partij.setDatumEindeVrijBericht(DatumUtil.morgen());
            partij.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijVrijBerichtDatumIngangVandaagDatumEindeMorgenGeenAfleverpunt", "850111");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001005654000");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.vandaag());
            partij.setDatumEindeVrijBericht(DatumUtil.morgen());
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijVrijBerichtDatumIngangVandaagDatumEindeMorgenGeenAfleverpuntOvergangBRPGroter", "850411");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001005654400");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20660101);
            partij.setDatumIngangVrijBericht(DatumUtil.vandaag());
            partij.setDatumEindeVrijBericht(DatumUtil.morgen());
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijVrijBerichtDatumIngangMorgenDatumEindeLeeg", "850013");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001005650000");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.morgen());
            partij.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijVrijBerichtDatumIngangMorgenDatumEindeLeegOvergangBRPLeeg", "850999");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001005659900");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumIngangVrijBericht(DatumUtil.morgen());
            partij.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijVrijBerichtDatumIngangGisterenDatumEindeVandaag", "850014");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001005650000");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setDatumEindeVrijBericht(DatumUtil.vandaag());
            partij.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijVrijBerichtDatumIngangGisterenDatumEindeGisteren", "850015");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001005650000");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setDatumEindeVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijVrijBerichtAlleenAfleverpuntGeenAutorisatie", "850016");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001932603000");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijVrijBerichtGeenAfleverpunt", "850017");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001721926000");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(datumIngang20100101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijDatumIngangGisterenDatumEindeLeeg", "850018");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647000");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijZelfOndertekenaarTekenaar", "880018");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647000");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setOndertekenaarVrijBericht(partij);
            partij.setTransporteurVrijBericht(partij);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijDatumIngangVandaagDatumEindeMorgen", "850019");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001005650000");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumEinde(DatumUtil.morgen());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijDatumIngangMorgenDatumEindeLeeg", "850020");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001005650000");
            partij.setDatumIngang(DatumUtil.morgen());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijDatumIngangMorgenDatumEindeMorgen", "850020");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001005651000");
            partij.setDatumIngang(DatumUtil.morgen());
            partij.setDatumEinde(DatumUtil.morgen());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijDatumIngangGisterenDatumEindeVandaag", "850021");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001005650000");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumEinde(DatumUtil.vandaag());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijDatumIngangGisterenDatumEindeGisteren", "850022");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001001005650000");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumEinde(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijGeblokkeerd", "850023");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647000");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setVrijBerichtGeblokkeerd(true);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijGeblokkeerdOvergangBRPLeeg", "850888");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220648800");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setVrijBerichtGeblokkeerd(true);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij ondertekenaar = new Partij("Partij2DatumIngangGisterenDatumEindeLeeg", "850018");
            ondertekenaar.setId(partijIdIndex++);
            ondertekenaar.setOin("00000001002220647000");
            ondertekenaar.setDatumIngang(DatumUtil.gisteren());
            ondertekenaar.setDatumOvergangNaarBrp(20160101);
            ondertekenaar.setDatumIngangVrijBericht(DatumUtil.gisteren());
            ondertekenaar.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, ondertekenaar);
            addAfnemerRol(ondertekenaar, partijRolMap);

            final Partij partij = new Partij("PartijOndertekenaar1", "850018");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647000");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setOndertekenaarVrijBericht(ondertekenaar);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij transporteur = new Partij("Partij3DatumIngangGisterenDatumEindeLeeg", "850018");
            transporteur.setId(partijIdIndex++);
            transporteur.setOin("00000001002220647000");
            transporteur.setDatumIngang(DatumUtil.gisteren());
            transporteur.setDatumOvergangNaarBrp(20160101);
            transporteur.setDatumIngangVrijBericht(DatumUtil.gisteren());
            transporteur.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, transporteur);
            addAfnemerRol(transporteur, partijRolMap);

            final Partij partij = new Partij("PartijTransporteur1", "850018");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647000");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setOndertekenaarVrijBericht(transporteur);
            partij.setTransporteurVrijBericht(transporteur);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij ondertekenaar = new Partij("Partij4DatumIngangGisterenDatumEindeLeeg", "850625");
            ondertekenaar.setId(partijIdIndex++);
            ondertekenaar.setOin("00000001002220647000");
            ondertekenaar.setDatumIngang(DatumUtil.gisteren());
            ondertekenaar.setDatumOvergangNaarBrp(20160101);
            ondertekenaar.setDatumIngangVrijBericht(DatumUtil.gisteren());
            ondertekenaar.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, ondertekenaar);
            addAfnemerRol(ondertekenaar, partijRolMap);

            final Partij partij = new Partij("PartijOndertekenaarDatumIngangGisterenDatumEindeLeeg", "850025");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647001");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setOndertekenaarVrijBericht(ondertekenaar);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij ondertekenaar = new Partij("PartijOndDatumIngangGisterenDatumEindeMorgen", "850026");
            ondertekenaar.setId(partijIdIndex++);
            ondertekenaar.setOin("00000001002220647000");
            ondertekenaar.setDatumIngang(DatumUtil.gisteren());
            ondertekenaar.setDatumEinde(DatumUtil.morgen());
            ondertekenaar.setDatumIngangVrijBericht(DatumUtil.gisteren());
            ondertekenaar.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, ondertekenaar);
            addAfnemerRol(ondertekenaar, partijRolMap);

            final Partij partij = new Partij("PartijOndertekenaarDatumIngangGisterenDatumEindeMorgen", "850026");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647001");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setOndertekenaarVrijBericht(ondertekenaar);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij ondertekenaar = new Partij("Partij2DatumIngangMorgenDatumEindeLeeg", "850427");
            ondertekenaar.setId(partijIdIndex++);
            ondertekenaar.setOin("00000001002220647000");
            ondertekenaar.setDatumIngang(DatumUtil.morgen());
            ondertekenaar.setDatumIngangVrijBericht(DatumUtil.gisteren());
            ondertekenaar.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, ondertekenaar);
            addAfnemerRol(ondertekenaar, partijRolMap);

            final Partij partij = new Partij("PartijOndertekenaarDatumIngangMorgenDatumEindeLeeg", "850027");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647001");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setOndertekenaarVrijBericht(ondertekenaar);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij ondertekenaar = new Partij("Partij5DatumIngangGisterenDatumEindeVandaag", "850028");
            ondertekenaar.setId(partijIdIndex++);
            ondertekenaar.setOin("00000001002220647000");
            ondertekenaar.setDatumIngang(DatumUtil.gisteren());
            ondertekenaar.setDatumEinde(DatumUtil.vandaag());
            ondertekenaar.setDatumIngangVrijBericht(DatumUtil.gisteren());
            ondertekenaar.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, ondertekenaar);
            addAfnemerRol(ondertekenaar, partijRolMap);

            final Partij partij = new Partij("PartijOndertekenaarDatumIngangGisterenDatumEindeVandaag", "850028");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647001");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setOndertekenaarVrijBericht(ondertekenaar);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij ondertekenaar = new Partij("Partij7DatumIngangGisterenDatumEindeGisteren", "850029");
            ondertekenaar.setId(partijIdIndex++);
            ondertekenaar.setOin("00000001002220647003");
            ondertekenaar.setDatumIngang(DatumUtil.gisteren());
            ondertekenaar.setDatumEinde(DatumUtil.gisteren());
            ondertekenaar.setDatumIngangVrijBericht(DatumUtil.gisteren());
            ondertekenaar.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, ondertekenaar);
            addAfnemerRol(ondertekenaar, partijRolMap);

            final Partij partij = new Partij("PartijOndertekenaarDatumIngangGisterenDatumEindeGisteren", "850030");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647004");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setOndertekenaarVrijBericht(ondertekenaar);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij transporteur = new Partij("TransporteurVrijBericht", "860031");
            transporteur.setId(partijIdIndex++);
            transporteur.setOin("00000001002220647000");
            transporteur.setDatumIngang(DatumUtil.gisteren());
            transporteur.setDatumIngangVrijBericht(DatumUtil.gisteren());
            transporteur.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, transporteur);
            addAfnemerRol(transporteur, partijRolMap);

            final Partij ondertekenaar = new Partij("OndertekenaarVrijberichtOinLeeg", "860029");
            ondertekenaar.setId(partijIdIndex++);
            //ondertekenaar.setOin("00000001002220647003");
            ondertekenaar.setDatumIngang(DatumUtil.gisteren());
            ondertekenaar.setDatumIngangVrijBericht(DatumUtil.gisteren());
            ondertekenaar.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, ondertekenaar);
            addAfnemerRol(ondertekenaar, partijRolMap);

            final Partij partij = new Partij("ZendePartijVrijBericht", "860018");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647000");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setOndertekenaarVrijBericht(ondertekenaar);
            partij.setTransporteurVrijBericht(transporteur);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }

        {
            final Partij transporteur = new Partij("TransporteurVrijBerichtGeenOin", "870031");
            transporteur.setId(partijIdIndex++);
            //transporteur.setOin("00000001002220647000");
            transporteur.setDatumIngang(DatumUtil.gisteren());
            transporteur.setDatumIngangVrijBericht(DatumUtil.gisteren());
            transporteur.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, transporteur);
            addAfnemerRol(transporteur, partijRolMap);

            final Partij ondertekenaar = new Partij("OndertekenaarVrijbericht", "870029");
            ondertekenaar.setId(partijIdIndex++);
            ondertekenaar.setOin("00000001002220647003");
            ondertekenaar.setDatumIngang(DatumUtil.gisteren());
            ondertekenaar.setDatumIngangVrijBericht(DatumUtil.gisteren());
            ondertekenaar.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, ondertekenaar);
            addAfnemerRol(ondertekenaar, partijRolMap);

            final Partij partij = new Partij("ZendePartijVrijBerichtR2467", "870018");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647000");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setOndertekenaarVrijBericht(ondertekenaar);
            partij.setTransporteurVrijBericht(transporteur);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }

        {
            final Partij transporteur = new Partij("Partij1DatumIngangGisterenDatumEindeLeeg", "850031");
            transporteur.setId(partijIdIndex++);
            transporteur.setOin("00000001002220647000");
            transporteur.setDatumIngang(DatumUtil.gisteren());
            transporteur.setDatumOvergangNaarBrp(20160101);
            transporteur.setDatumIngangVrijBericht(DatumUtil.gisteren());
            transporteur.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, transporteur);
            addAfnemerRol(transporteur, partijRolMap);

            final Partij partij = new Partij("PartijTransporteurDatumIngangGisterenDatumEindeLeeg", "850032");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647001");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setTransporteurVrijBericht(transporteur);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij transporteur = new Partij("PartijTransDatumIngangGisterenDatumEindeMorgen", "850033");
            transporteur.setId(partijIdIndex++);
            transporteur.setOin("00000001002220647000");
            transporteur.setDatumIngang(DatumUtil.gisteren());
            transporteur.setDatumEinde(DatumUtil.morgen());
            transporteur.setDatumIngangVrijBericht(DatumUtil.gisteren());
            transporteur.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, transporteur);
            addAfnemerRol(transporteur, partijRolMap);

            final Partij partij = new Partij("PartijTransporteurDatumIngangGisterenDatumEindeMorgen", "850034");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647001");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setTransporteurVrijBericht(transporteur);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij transporteur = new Partij("Partij3DatumIngangMorgenDatumEindeLeeg", "850035");
            transporteur.setId(partijIdIndex++);
            transporteur.setOin("00000001002220647000");
            transporteur.setDatumIngang(DatumUtil.morgen());
            transporteur.setDatumIngangVrijBericht(DatumUtil.gisteren());
            transporteur.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, transporteur);
            addAfnemerRol(transporteur, partijRolMap);

            final Partij partij = new Partij("PartijTransporteurDatumIngangMorgenDatumEindeLeeg", "850036");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647001");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setTransporteurVrijBericht(transporteur);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij transporteur = new Partij("Partij6DatumIngangGisterenDatumEindeVandaag", "850037");
            transporteur.setId(partijIdIndex++);
            transporteur.setOin("00000001002220647000");
            transporteur.setDatumIngang(DatumUtil.gisteren());
            transporteur.setDatumEinde(DatumUtil.vandaag());
            transporteur.setDatumIngangVrijBericht(DatumUtil.gisteren());
            transporteur.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, transporteur);
            addAfnemerRol(transporteur, partijRolMap);

            final Partij partij = new Partij("PartijTransporteurDatumIngangGisterenDatumEindeVandaag", "850038");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647001");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setTransporteurVrijBericht(transporteur);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij transporteur = new Partij("Partij8DatumIngangGisterenDatumEindeGisteren", "850039");
            transporteur.setId(partijIdIndex++);
            transporteur.setOin("00000001002220647003");
            transporteur.setDatumIngang(DatumUtil.gisteren());
            transporteur.setDatumEinde(DatumUtil.gisteren());
            transporteur.setDatumIngangVrijBericht(DatumUtil.gisteren());
            transporteur.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, transporteur);
            addAfnemerRol(transporteur, partijRolMap);

            final Partij partij = new Partij("PartijTransporteurDatumIngangGisterenDatumEindeGisteren", "850040");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647004");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setTransporteurVrijBericht(transporteur);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij ondertekenaar = new Partij("OndertekenaarGeblokkeerd", "877026");
            ondertekenaar.setId(partijIdIndex++);
            ondertekenaar.setOin("00000001005220647000");
            ondertekenaar.setDatumIngang(DatumUtil.gisteren());
            ondertekenaar.setDatumEinde(DatumUtil.morgen());
            ondertekenaar.setDatumIngangVrijBericht(DatumUtil.gisteren());
            ondertekenaar.setAfleverpuntVrijBericht("http://ergens");
            ondertekenaar.setVrijBerichtGeblokkeerd(true);
            voegToe(partijMap, ondertekenaar);
            addAfnemerRol(ondertekenaar, partijRolMap);

            final Partij partij = new Partij("PartijOndertekenaarGeblokkeerd", "877028");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001005220647001");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setOndertekenaarVrijBericht(ondertekenaar);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij transporteur = new Partij("TransporteurGeblokkeerd", "850139");
            transporteur.setId(partijIdIndex++);
            transporteur.setOin("00000001002220647003");
            transporteur.setDatumIngang(DatumUtil.gisteren());
            transporteur.setDatumEinde(DatumUtil.morgen());
            transporteur.setDatumIngangVrijBericht(DatumUtil.gisteren());
            transporteur.setAfleverpuntVrijBericht("http://ergens");
            transporteur.setVrijBerichtGeblokkeerd(true);
            voegToe(partijMap, transporteur);
            addAfnemerRol(transporteur, partijRolMap);

            final Partij partij = new Partij("PartijTransporteurGeblokkeerd", "850140");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647004");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            partij.setTransporteurVrijBericht(transporteur);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("VerkeerdePartijZelfdeOIN", "850018");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647009");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("VerkeerdePartijZelfdeOIN2", "850119");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647000");
            partij.setDatumIngang(DatumUtil.gisteren());
            partij.setDatumOvergangNaarBrp(20160101);
            partij.setDatumIngangVrijBericht(DatumUtil.gisteren());
            partij.setAfleverpuntVrijBericht("http://ergens");
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("DatumOvergangBRPGisteren", "852222");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647330");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(DatumUtil.gisteren());
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("DatumOvergangBRPVandaag", "852223");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647330");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(DatumUtil.vandaag());
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("DatumOvergangBRPMorgen", "852224");
            partij.setId(partijIdIndex++);
            partij.setOin("00000001002220647330");
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(DatumUtil.morgen());
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("Gemeente GBA", "852225");
            partij.setId(partijIdIndex++);
            partij.setOin("00500001002220647000");
            partij.setDatumIngang(datumIngang20100101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("PartijZonderOIN", "850041");
            partij.setId(partijIdIndex++);
            partij.setOin(null);
            partij.setDatumIngang(datumIngang20100101);
            partij.setDatumOvergangNaarBrp(20160101);
            voegToe(partijMap, partij);
            addAfnemerRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("Gemeente BRP 1", "507013");
            partij.setId(partijIdIndex++);
            partij.setOin("54321");
            partij.setDatumIngang(20160101);
            partij.setDatumOvergangNaarBrp(20160101);
            voegToe(partijMap, partij);
            addCollegeRol(partij, partijRolMap);
        }
        {
            final Partij partij = new Partij("Gemeente BRP 3", "507018");
            partij.setId(partijIdIndex++);
            partij.setOin("54322");
            partij.setDatumIngang(20160101);
            partij.setDatumOvergangNaarBrp(20160101);
            voegToe(partijMap, partij);
            addCollegeRol(partij, partijRolMap);
        }
        for (Partij partij : partijMap.values()) {
            partij.setActueelEnGeldig(true);
            partij.setActueelEnGeldigVoorVrijBericht(true);
        }
        for (PartijRol partijRol : partijRolMap.values()) {
            partijRol.setActueelEnGeldig(true);
        }

        PARTIJ_ROL_MAP = ImmutableMap.copyOf(partijRolMap);
        PARTIJ_MAP = ImmutableMap.copyOf(partijMap);
        PARTIJ_LIST = ImmutableList.copyOf(partijMap.values());

    }

    private static void voegToe(Map<String, Partij> partijMap, Partij partij) {
        final Partij partijInMap = partijMap.put(partij.getNaam(), partij);
        if (partijInMap != null) {
            throw new IllegalArgumentException("geen dubbele toevoegen");
        }
    }

    private Partijen() {
    }


    /**
     * Geeft de {@link PartijRol} voor de gegeven {@link Partij#naam}.
     * @param partijNaam partijNaam
     * @return een {@link PartijRol}
     */
    public static PartijRol getPartijRol(final String partijNaam) {
        final PartijRol partijRol = PARTIJ_ROL_MAP.get(partijNaam);
        Assert.notNull(partijRol, "PartijRol niet gevonden: " + partijNaam);
        return partijRol;
    }

    /**
     * Geeft de {@link Partij} met de gegeven naam.
     * @param partijNaam partijNaam
     * @return een {@link Partij}
     */
    public static Partij getPartij(final String partijNaam) {
        final Partij partij = PARTIJ_MAP.get(partijNaam);
        Assert.notNull(partij, "Partij niet gevonden: " + partijNaam);
        return partij;
    }

    public static List<Partij> getPartijen() {
        return PARTIJ_LIST;
    }

    private static PartijRol addAfnemerRol(final Partij partij, final Map<String, PartijRol> partijRolMap) {
        return addPartijRol(partij, partijRolMap, Rol.AFNEMER);
    }

    private static PartijRol addMinisterRol(final Partij partij, final Map<String, PartijRol> partijRolMap) {
        return addPartijRol(partij, partijRolMap, Rol.BIJHOUDINGSORGAAN_MINISTER);
    }

    private static PartijRol addCollegeRol(final Partij partij, final Map<String, PartijRol> partijRolMap) {
        return addPartijRol(partij, partijRolMap, Rol.BIJHOUDINGSORGAAN_COLLEGE);

    }

    private static PartijRol addPartijRol(final Partij partij, final Map<String, PartijRol> partijRolMap, final Rol afnemer) {
        return addPartijRol(partij, partijRolMap, afnemer, 20160101, null, true);
    }

    private static PartijRol addPartijRol(final Partij partij, final Map<String, PartijRol> partijRolMap, final Rol afnemer, final Integer datumIngang,
                                          final Integer datumEinde, final boolean isActueelEnGeldig) {
        final PartijRol partijRol = new PartijRol(partij, afnemer);
        partijRol.setId(partij.getId().intValue());
        partijRol.setDatumIngang(datumIngang);
        partijRol.setDatumEinde(datumEinde);
        partijRol.setActueelEnGeldig(isActueelEnGeldig);

        partij.addPartijRol(partijRol);
        partijRolMap.put(partij.getNaam(), partijRol);
        return partijRol;
    }

}
