/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieCorrectieAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;


/**
 * Een helper klasse voor het bouwen/instantieren en vullen van adressen ten behoeve van de unit tests.
 */
public final class PersoonAdresBuilder {

    private static final Map<String, String> OPENBARERUIMTE_AFKORTINGEN;

    static {
        Map<String, String> aanpasbareAfkortingenMap = new HashMap<String, String>();
        aanpasbareAfkortingenMap.put("straat", "str");
        aanpasbareAfkortingenMap.put("plein", "pln");
        aanpasbareAfkortingenMap.put("laan", "ln");
        aanpasbareAfkortingenMap.put("weg", "wg");
        OPENBARERUIMTE_AFKORTINGEN = Collections.unmodifiableMap(aanpasbareAfkortingenMap);
    }

    /**
     * Lege en private constructor daar utility classes niet geinstantieerd dienen te worden.
     */
    private PersoonAdresBuilder() {
    }

    /**
     * Bouwt en retourneert een woon adres in een opgegeven plaats en gemeente, waarbij de opgegeven overige adres
     * gegevens worden gezet op het adres.
     *
     * @param naamOpenbareRuimte       de naam van de openbare ruimte
     * @param huisnummer               het huisnummer
     * @param postcode                 de postcode
     * @param woonplaatsnaam           de woonplaats
     * @param gemeente                 de gemeente
     * @param datumAanvangAdreshouding de datum van aanvang van adreshouding
     * @return een Nederlands woon adres
     */
    public static PersoonAdresBericht bouwWoonadres(final String naamOpenbareRuimte, final Integer huisnummer,
            final String postcode, final String woonplaatsnaam, final Gemeente gemeente,
            final Integer datumAanvangAdreshouding)
    {
        String prefix = PrefixBuilder.getPrefix();

        final PersoonAdresBericht adres = new PersoonAdresBericht();
        adres.setStandaard(new PersoonAdresStandaardGroepBericht());
        adres.setCommunicatieID(prefix + "id.adres");

        final PersoonAdresStandaardGroepBericht adresGegevens = adres.getStandaard();
        adresGegevens.setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));
        if (naamOpenbareRuimte != null) {
            adresGegevens.setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut(naamOpenbareRuimte));
            adresGegevens.setAfgekorteNaamOpenbareRuimte(new AfgekorteNaamOpenbareRuimteAttribuut(
                    kortStraatnaamAf(naamOpenbareRuimte)));
        }
        if (huisnummer != null) {
            adresGegevens.setHuisnummer(new HuisnummerAttribuut(huisnummer));
        }
        if (postcode != null) {
            adresGegevens.setPostcode(new PostcodeAttribuut(postcode));
        }
        if (woonplaatsnaam != null) {
            adresGegevens.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut(woonplaatsnaam));
        }
        if (gemeente != null) {
            adresGegevens.setGemeente(new GemeenteAttribuut(gemeente));
        }
        adresGegevens.setLandGebied(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        if (datumAanvangAdreshouding != null) {
            adresGegevens.setDatumAanvangAdreshouding(new DatumEvtDeelsOnbekendAttribuut(datumAanvangAdreshouding));
        }

        return adres;
    }

    public static PersoonAdresBericht bouwBuitenlandsAdres(final Integer datumVertrekUitNL,
            final LandGebied landGebied, final String regel1, final String regel2, final String regel3,
            final String regel4, final String regel5, final String regel6)
    {
        String prefix = PrefixBuilder.getPrefix();

        final PersoonAdresBericht adres = new PersoonAdresBericht();
        adres.setStandaard(new PersoonAdresStandaardGroepBericht());
        adres.setCommunicatieID(prefix + "id.adres");

        final PersoonAdresStandaardGroepBericht adresGegevens = adres.getStandaard();
        adresGegevens.setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));

        adresGegevens.setRedenWijziging(StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_PERSOON);
        adresGegevens.setAangeverAdreshouding(StatischeObjecttypeBuilder.AANGEVER_INGESCHREVENE);

        if (null != landGebied) {
            adresGegevens.setLandGebiedCode(landGebied.getCode().getWaarde().toString());
            adresGegevens.setLandGebied(new LandGebiedAttribuut(landGebied));
        }
        if (regel1 != null) {
            adresGegevens.setBuitenlandsAdresRegel1(new AdresregelAttribuut(regel1));
        }
        if (regel2 != null) {
            adresGegevens.setBuitenlandsAdresRegel2(new AdresregelAttribuut(regel2));
        }
        if (regel3 != null) {
            adresGegevens.setBuitenlandsAdresRegel3(new AdresregelAttribuut(regel3));
        }
        if (regel4 != null) {
            adresGegevens.setBuitenlandsAdresRegel4(new AdresregelAttribuut(regel4));
        }
        if (regel5 != null) {
            adresGegevens.setBuitenlandsAdresRegel5(new AdresregelAttribuut(regel5));
        }
        if (regel6 != null) {
            adresGegevens.setBuitenlandsAdresRegel6(new AdresregelAttribuut(regel6));
        }

        return adres;
    }

    /**
     * Kort de opgegeven naamOpenbareRuimte af door enkele veel voorkomende eindes van een naamOpenbareRuimte
     * (bijvoorbeeld 'laan') af
     * te korten naar een kortere versie (bijvoorbeeld 'laan' naar 'ln). Indien de opgegeven naamOpenbareRuimte niet
     * eindigt
     * met een bekend deel, dan wordt de gehele naamOpenbareRuimte geretourneerd (en dus niet afgekort).
     *
     * @param straatnaam de naamOpenbareRuimte die afgekort dient te worden.
     * @return de afgekorte naamOpenbareRuimte.
     */
    private static String kortStraatnaamAf(final String straatnaam) {
        String afgekorteStraatnaam = straatnaam;

        for (Map.Entry<String, String> afkorting : OPENBARERUIMTE_AFKORTINGEN.entrySet()) {
            if (straatnaam.endsWith(afkorting.getKey())) {
                afgekorteStraatnaam =
                        straatnaam.substring(0, straatnaam.lastIndexOf(afkorting.getKey())) + afkorting.getValue();
                break;
            }
        }

        return afgekorteStraatnaam;
    }

    /**
     * Bouwt en retourneert een nieuwe adrescorrectie Actie instantie met de opgegeven eigenschappen en enkele
     * standaard waardes voor enkele velden.
     *
     * @param datumAanvangGeldigheid de datum aanvang geldigheid
     * @param datumEindeGeldigheid   de datum einde geldigheid
     * @param persoon                de persoon
     * @param adres                  het adres
     * @return een nieuwe adrescorrectie Actie
     */
    public static ActieBericht bouwAdrescorrectieActie(final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
            final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid, final PersoonBericht persoon,
            final PersoonAdresBericht adres)
    {
        String prefix = PrefixBuilder.getPrefix();

        ActieBericht actie = new ActieCorrectieAdresBericht();
        actie.setCommunicatieID(prefix + "id.act.verh");
        List<PersoonAdresBericht> adressen = new ArrayList<PersoonAdresBericht>();

        actie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        actie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        actie.setRootObject(persoon);

        persoon.setAdressen(adressen);
        adressen.add(adres);

        return actie;
    }

}
