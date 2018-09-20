/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonAdresBasis;

/**
 * Het adres zoals gedefinieerd in artikel 1.1. van de Wet BRP.
 *
 * Adres is het woonadres, dan wel bij het ontbreken hiervan het briefadres.
 *
 * Het woonadres is het; # adres waar betrokkene woont, waaronder begrepen het adres van een woning die zich in een
 * voertuig of vaartuig bevindt, indien het voertuig of vaartuig een vaste stand- of ligplaats heeft, of, indien
 * betrokkene op meer dan één adres woont, het adres waar hij naar redelijke verwachting gedurende een half jaar de
 * meeste malen zal overnachten; # het adres waar, bij het ontbreken van een adres als bedoeld onder 1, betrokkene naar
 * redelijke verwachting gedurende drie maanden ten minste twee derde van de tijd zal overnachten.
 *
 * Het briefadres is het adres waar voor betrokkene bestemde geschriften in ontvangst worden genomen.
 *
 * Opmerkingen: # Het adres is in essentie de koppeling
 * "van een aanduiding van een bepaalde plek op aarde waarmee die plek kan worden geadresseerd" en de Persoon. #
 * Adresgegevens behoren tot de meest gebruikte gegevens binnen administraties van de overheid en semi-overheid. Bij
 * deze gegevens komt de Basisadministratie Adressen en Gebouwen (BAG) nadrukkelijk in beeld, omdat de BRP verplicht
 * gebruik moet maken van de gegevens in de BAG. # Historische adressen die vanuit de GBA-periode opgenomen (moeten)
 * worden en die in de GBA geen BAG-gegevens bevatten, worden zonder BAG-verwijzing opgenomen. # De adresgegevens worden
 * overgenomen uit de lokale BAG, en niet uit de centrale LV BAG. # In dit objecttype wordt het adres gekoppeld aan de
 * persoon. Dezelfde "plek op aarde", gekoppeld met twee verschillende Personen, heeft dus twee exemplaren van Adres tot
 * gevolg.
 *
 * 1. Definitie van Adres is niet overeenkomstig de wet BRP: daar wordt Adres gedefinieerd als 'hetzij woonadres hetzij
 * briefadres'. In essentie is het Adres echter een 'plaatsaanduiding', in binnen- of buitenland, waar een persoon kan
 * worden bereikt via de post: ""een adresseerbaar object"". Deze term heeft echter weer een speciale betekenis in de
 * BAG. Om die reden gekozen voor de wat losse definitie geïnspireerd door Wikipedia (feb 2011). 2. De beperking tot
 * 'plek op aarde' is wellicht arbitrair; anderzijds zal het nog wel even duren voordat dit deel van de definitie wordt
 * gefalsificeerd (de eerste astronaut die lange duur buiten de aarde, zich op een adresseerbare locatie bevindt, moet
 * maar een briefadres hanteren) 3. Adres is onderkend als objecttype. Hierdoor is de overgang naar 'een Persoon kent
 * (tegelijkertijd) meerdere Adressen' mogelijk. Hierdoor is het model voorbereid op een eventuele uitbreiding naar
 * meerdere adressoorten die wellicht tegelijkertijd kunnen gaan voorkomen. De actuele beperking 'op elk moment in de
 * tijd maar één adres per persoon' zal via een business rule moeten worden bewaakt. 4. De gegevens worden uit de lokale
 * BAG gehaald (door de burgerzakenmodules), en NIET uit de centrale LV BAG. Motivatie: de laatste loopt vaak achter
 * (>24 uur), en is ook formeel NIET leidend t.o.v. de lokale BAG. 5. In eerste instantie waren drie groepen onderkend
 * bij adres: adreshouding, Nederlands adres en Buitenlands adres. Nederlands adres en Buitenlands adres hangen echter
 * 'materieel historisch' samen: een nieuw Nederlands adres impliceert de beëindiging van een huidig Buitenlands adres,
 * en vice versa. Samenvoegen van de twee groepen is dus eenvoudiger. De attributen uit de groep adreshouding hebben
 * altijd betrekking op de corresponderende periode bij Nederlands adres. Conclusie is dat het samenvoegen van de drie
 * groepen tot één (die daarmee de naam 'standaard' krijgt, die standaard in presentaties wordt onderdrukt, maakt de
 * bewaking hiervan, en de via-een-patroon uitwerken hiervan, eenvoudiger. Om die reden zijn de drie groepen
 * samengevoegd. Zie ook documentatie over uitwerking logisch gegevensmodel; daarin staat dit beschreven.
 *
 * 6. Het adres kent zowel een identificatiecode verblijfsplaats als een identificatiecode nummeraanduiding. De
 * motivatie hieruit volgt uit onderstaande (deels geanonymiseerde) mailwisseling. ------------ BEGIN MAILWISSELING
 * ------------ E,
 *
 * Een korte reactie, als er meer nodig is hoor ik het wel.
 *
 * GBA kent de identificatiecode verblijfplaats die naar BRG verwijst en de identificatiecode nummeraanduiding die naar
 * BRA verwijst. Zolang het verschijnsel nevenadres voorkomt zijn beide nodig. De meeste mutaties zullen via de
 * identificatiecode verblijfplaats komen, maar juist in het geval dat er van een nevenadres sprake is, komen mutaties
 * daarin via de identificatiecode nummeraanduiding.
 *
 * In onze invulling van de template zijn relaties nog niet opgenomen, omdat niet duidelijk was of dat al kon.
 *
 * Een en ander hangt samen met de vraag of je bij het aangeven van relaties tussen basisregistraties alleen de
 * hoofdrelatie(s) aangeeft of ook de wat ik dan maar ‘achterdeurrelaties’ noem. Ik opteer voor de tweede mogelijkheid.
 * Tenslotte wil je met een relatie in het model aangeven dat het om hetzelfde begrip gaat, waarvoor je in de
 * ontvangende registratie dan eigenlijk de beschrijvingen e.d. van de leverende registratie over moet nemen.
 *
 * Groet, H
 *
 * ---
 *
 * F, H en J,
 *
 * Ik ben door VROM aangesproken op de plaat 'Relaties in het stelsel van basisregistraties', waarin ik vanuit GBA zowel
 * een relatie naar de BasisGebouwenRegistratie heb getekend, als naar de BR Adressen. Dat heb ik gedaan omdat ik in de
 * GBA LO3.7 zowel een 11:80 identificatiecode verblijfsobject als een 11:90 identificatiecode nummeraanduiding zie
 * staan. VROM vindt dat ik alleen een lijntje naar de BGR moet tekenen, omdat het adres via het adresseerbaar object
 * wordt verkregen. (Ik begrijp wel dat personen in een adresseerbaar object wonen en niet in een adres, maar ik kan me
 * voorstellen dat de brievenbus bij het nevenadres hangt en dat iemand daarom dat adres wil gebruiken).
 *
 * Als ik kijk naar de versie van LO3.7 die jullie hebben opgestuurd in de vorm van het template basisregistraties dan
 * kom ik beide identificatiecodes alleen als attribuut tegen, niet als relatie-attribuut. Bij 2.1.3 Relaties van
 * persoonslijst lees ik: N.V.T. Moet ik hieruit concluderen dat LO3.7 nog geen relaties met de BAG heeft?
 *
 * Wordt dit anders in LO4? Hoe gaat de relatie naar de BAG gelegd worden?
 *
 * Bij deze vraag speelt wellicht een rol of BRA en BGR als aparte BR's functioneren, dan wel als eenheid BAG. Zijn er
 * voor de overgenomen gegevens 2 aparte mutatieleveringen voor Adressen en Gebouwen of één van de BAG waarin zowel
 * vernummeringen van een openbare ruimte geleverd worden als verbouwingen waarbij de hoofdtoegang in een zijstraat komt
 * te liggen. Ik begrijp inmiddels dat het laatste de bedoeling is. Dan nog kan je kiezen tussen één van beide of
 * allebei. Als je alleen voor adres kiest heb je ze ook allebei. Wat gaat het worden? Is dat al bekend?
 *
 * Met vriendelijke groet, ------------ EINDE MAILWISSELING ------------ 7. Adres is gemodelleerd conform het 'status'
 * of 'materiele historie' patroon, en niet als gebeurtenis. Toch bevat het een aantal 'gebeurtenisgegevens': reden
 * wijziging (en, indien van toepassing, hoedanigheid aangever). Overwogen is deze bij 'verantwoording' te modelleren.
 *
 * Voordelen hiervan: bij correcties etc c.q. bij het 'afleiden' van tussenliggende records (voor overbrugging in kader
 * van kop- en staartrecords) is er geen 'onnodige' informatie nodig over deze velden (die moeten worden OVERRULED ten
 * opzichte van de waarde uit het (historische, reeds aanwezige) record.
 *
 * Redenen om het toch niet te doen: - Aanpassing, gevolg dat wellicht(?!) nodig wordt om verantwoording ook apart te
 * autoriseren; - Performance: is voor bijhouding eigenlijk ALTIJD nodig; - De velden waar het om gaat zijn specifiek
 * voor Adres, en passen niet in de algemene (en generieke) verantwoordingsstructuur. Al met al is daarom besloten om
 * het model te laten hoe het is, en dus NIET de gebeurtenis-gegevens apart ergens onder te brengen. Consequentie is dat
 * of het bijhoudingsbericht dan wel de toegepaste bedrijfslogica iets meer specifiek wordt voor adrescorrecties:
 * namelijk het 'overrulen' van de generieke werking voor staart- en koprecords, in dat deze NIET zonder wijziging
 * worden overgenomen uit reeds geregistreerde records in de database.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonAdresBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar,
        PersoonAdresBasis
{

    private static final Integer META_ID = 3237;
    private PersoonBericht persoon;
    private PersoonAdresStandaardGroepBericht standaard;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonBericht getPersoon() {
        return persoon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonAdresStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * Zet Persoon van Persoon \ Adres.
     *
     * @param persoon Persoon.
     */
    public void setPersoon(final PersoonBericht persoon) {
        this.persoon = persoon;
    }

    /**
     * Zet Standaard van Persoon \ Adres.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonAdresStandaardGroepBericht standaard) {
        this.standaard = standaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteit> getBerichtEntiteiten() {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteitGroep> getBerichtEntiteitGroepen() {
        final List<BerichtEntiteitGroep> berichtGroepen = new ArrayList<>();
        berichtGroepen.add(getStandaard());
        return berichtGroepen;
    }

}
