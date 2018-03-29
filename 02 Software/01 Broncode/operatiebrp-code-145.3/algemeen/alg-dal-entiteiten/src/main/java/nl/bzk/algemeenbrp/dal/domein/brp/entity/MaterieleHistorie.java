/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.sql.Timestamp;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import nl.bzk.algemeenbrp.util.common.DatumUtil;

/**
 * Interface voor rijen in BRP met materiële historie (datum aanvang/einde geldigheid).
 */
public interface MaterieleHistorie extends FormeleHistorie {

    /**
     * Geef de waarde van datum aanvang geldigheid van MaterieleHistorie.
     * @return de waarde van datum aanvang geldigheid van MaterieleHistorie
     */
    Integer getDatumAanvangGeldigheid();

    /**
     * Zet de waarden voor datum aanvang geldigheid van MaterieleHistorie.
     * @param datumAanvangGeldigheid de nieuwe waarde voor datum aanvang geldigheid van MaterieleHistorie
     */
    void setDatumAanvangGeldigheid(Integer datumAanvangGeldigheid);

    /**
     * Geef de waarde van datum einde geldigheid van MaterieleHistorie.
     * @return de waarde van datum einde geldigheid van MaterieleHistorie
     */
    Integer getDatumEindeGeldigheid();

    /**
     * Zet de waarden voor datum einde geldigheid van MaterieleHistorie.
     * @param datumEindeGeldigheid de nieuwe waarde voor datum einde geldigheid van MaterieleHistorie
     */
    void setDatumEindeGeldigheid(Integer datumEindeGeldigheid);

    /**
     * Geef de waarde van actie aanpassing geldigheid van MaterieleHistorie.
     * @return de waarde van actie aanpassing geldigheid van MaterieleHistorie
     */
    BRPActie getActieAanpassingGeldigheid();

    /**
     * Zet de waarden voor actie aanpassing geldigheid van MaterieleHistorie.
     * @param actieAanpassingGeldigheid de nieuwe waarde voor actie aanpassing geldigheid van MaterieleHistorie
     */
    void setActieAanpassingGeldigheid(BRPActie actieAanpassingGeldigheid);

    /**
     * Maakt een (shallow) kopie van deze materiele historie entiteit. De id waarde van de kopie moet null zijn.
     * @return de kopie
     */
    <T extends MaterieleHistorie> T kopieer();

    /**
     * Geef de persoon waar dit materieel voorkomen bij hoort of null als dit voorkomen niet tot een specifiek persoon te herleiden is.
     * @return de persoon
     */
    Persoon getPersoon();

    /**
     * Voegt een nieuw actueel voorkomen met materiele historie toe aan de gegeven lijst met voorkomens. Dit nieuwVoorkomen dient aan de voorwaarden te voldoen
     * die worden gecontroleerd door {@link MaterieleHistorie#valideerActueelVoorkomen}.
     * @param nieuwVoorkomen het nieuwVoorkomen dat moet worden toegevoegd
     * @param voorkomens de lijst met voorkomens dat moet worden uitgebreid met het gegeven nieuwVoorkomen
     */
    static <T extends MaterieleHistorie> void voegNieuweActueleToe(final T nieuwVoorkomen, final Set<T> voorkomens) {
        valideerActueelVoorkomen(nieuwVoorkomen);

        final MaterieleHistorie bestaandActueelVoorkomen = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(voorkomens);
        if (bestaandActueelVoorkomen != null) {
            valideerActualisatie(nieuwVoorkomen, bestaandActueelVoorkomen);
            beeindigActueelVoorkomen(voorkomens, nieuwVoorkomen.getActieInhoud(), nieuwVoorkomen.getDatumTijdRegistratie(),
                    nieuwVoorkomen.getDatumAanvangGeldigheid());
        }
        voorkomens.add(nieuwVoorkomen);
    }

    /**
     * Voegt een nieuw voorkomen met materiele historie toe aan de gegeven lijst met voorkomens. Dit voorkomen wordt zonder controles toegevoegd aan de lijst
     * van bestaande voorkomens en zal niet het bestaande actuele voorkomen beeindigen.
     * @param voorkomen het voorkomen dat moet worden toegevoegd
     * @param voorkomens de lijst met voorkomens dat moet worden uitgebreid met het gegeven nieuwVoorkomen
     */
    static <T extends MaterieleHistorie> void voegVoorkomenToe(final T voorkomen, final Set<T> voorkomens) {
        ValidationUtils.controleerOpNullWaarden("voorkomen mag niet null zijn", voorkomen);
        voorkomens.add(voorkomen);
    }

    /**
     * Beeindigd een actueel voorkomen.
     * @param voorkomens de lijst met voorkomens dat moet worden uitgebreid met het gegeven nieuwVoorkomen
     * @param actie de actie die nodig is om de beeindiging te doen
     * @param datumTijdRegistratie de datum/tijd registratie van de beeindiging
     * @param datumEindeGeldigheid de datum einde geldigheid
     * @return <E> het beeindigde voorkomen van type historie
     */
    static <T extends MaterieleHistorie> T beeindigActueelVoorkomen(final Set<T> voorkomens, final BRPActie actie, final Timestamp datumTijdRegistratie,
                                                                    final Integer datumEindeGeldigheid) {
        final MaterieleHistorie bestaandActueelVoorkomen = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(voorkomens);
        if (bestaandActueelVoorkomen != null) {
            final T voorkomenMetAangepastGeldigheid = bestaandActueelVoorkomen.kopieer();
            voorkomenMetAangepastGeldigheid.setDatumTijdRegistratie(datumTijdRegistratie);
            voorkomenMetAangepastGeldigheid.setDatumEindeGeldigheid(datumEindeGeldigheid);
            voorkomenMetAangepastGeldigheid.setActieAanpassingGeldigheid(actie);
            voorkomens.add(voorkomenMetAangepastGeldigheid);

            bestaandActueelVoorkomen.setDatumTijdVerval(datumTijdRegistratie);
            bestaandActueelVoorkomen.setActieVerval(actie);
            for (final Onderzoek onderzoek : bestaandActueelVoorkomen.getPersoon().getOnderzoeken()) {
                onderzoek.kopieerGegevenInOnderzoekVoorNieuwGegeven(bestaandActueelVoorkomen, voorkomenMetAangepastGeldigheid, actie);
            }
            return voorkomenMetAangepastGeldigheid;
        }
        return null;
    }

    /**
     * Deze methode controleert of het hier een actualisatie betreft. In het geval van een correctie wordt een fout gegooid.
     * @param nieuwVoorkomen het nieuwVoorkomen dat moet worden toegevoegd
     * @param bestaandActueelVoorkomen het bestaande actuele voorkomen
     * @param <E> het type historie
     */
    static <E extends MaterieleHistorie> void valideerActualisatie(final E nieuwVoorkomen, final E bestaandActueelVoorkomen) {
        final Integer nieuweDatumAanvangGeldigheid = nieuwVoorkomen.getDatumAanvangGeldigheid();
        final Integer bestaandeDatumAanvangGeldigheid = bestaandActueelVoorkomen.getDatumAanvangGeldigheid();
        if (!DatumUtil.valtDatumBinnenPeriodeStreng(nieuweDatumAanvangGeldigheid, bestaandeDatumAanvangGeldigheid, null)) {
            throw new UnsupportedOperationException("Impliciete correcties zijn niet toegestaan.");
        }
    }

    /**
     * Controleert of het nieuwe voorkomen valide is. Dit voorkomen dient aan de volgende voorwaarden te voldoen: <ul> <li>het nieuwVoorkomen is niet null</li>
     * <li>het nieuwVoorkomen is actueel, dus datumTijdRegistratie en actieInhoud moeten gevuld zijn en tsVerval en actieVerval moeten null zijn</li> <li>datun
     * aanvang geldigheid mag niet null zijn</li> <li>actie aanpassing geldigheid moet null zijn</li> <li>datum einde geldigheid mag niet kleiner zijn dan datum
     * aanvang geldigheid</li> </ul>
     * @param nieuwVoorkomen het nieuwe voorkomen
     * @param <E> het type historie
     */
    static <E extends MaterieleHistorie> void valideerActueelVoorkomen(final E nieuwVoorkomen) {
        FormeleHistorie.valideerActueelVoorkomen(nieuwVoorkomen);
        ValidationUtils.controleerOpNullWaarden("nieuwVoorkomen.datumAanvangGeldigheid mag niet null zijn", nieuwVoorkomen.getDatumAanvangGeldigheid());
        if (nieuwVoorkomen.getActieAanpassingGeldigheid() != null) {
            throw new IllegalArgumentException("nieuwVoorkomen.actieAanpassingGeldigheid moet leeg zijn voor een actueel nieuwVoorkomen");
        }
        if (nieuwVoorkomen.getDatumEindeGeldigheid() != null && nieuwVoorkomen.getDatumEindeGeldigheid() < nieuwVoorkomen.getDatumAanvangGeldigheid()) {
            throw new IllegalArgumentException("Datum einde geldigheid mag niet kleiner zijn dan datum aanvang geldigheid");
        }
    }

    /**
     * geeft het niet vervallen, op peildatum actuele, voorkomen terug. voor het bepalen van de periode wordt de 'soepele' vergelijking gebruikt.
     * @param voorkomens lijst met voorkomens waarin gezocht moet worden
     * @param peildatum peildatum waarop actuele moet worden bepaald
     * @param <E> het Type Historie
     * @return het geldige voorkomen of null als deze niet gevonden wordt
     */
    static <E extends MaterieleHistorie> E getGeldigVoorkomenOpPeildatum(final Set<E> voorkomens, final int peildatum) {
        for (E voorkomen : FormeleHistorieZonderVerantwoording.getNietVervallenVoorkomens(voorkomens)) {
            if (DatumUtil.valtDatumBinnenPeriode(peildatum, voorkomen.getDatumAanvangGeldigheid(), voorkomen.getDatumEindeGeldigheid())) {
                return voorkomen;
            }
        }
        return null;
    }

    /**
     * Geeft aan of voor deze groep de datum einde geldigheid (DEG) gelijk mag zijn aan de datum aanvang geldigheid (DAG). De standaardwaarde is dat de DEG niet
     * gelijk aan de DAG mag zijn (false). Deze methode wordt overriden in de groep implementaties waar deze conditie wel is toegestaan.
     * @return false, tenzij het in de  implementerende groep wel is toegestaan
     */
    default boolean isDegGelijkAanDagToegestaan() {
        return false;
    }

    /**
     * Geeft aan of voor deze groep de historie aaneengesloten moet zijn. De standaardwaarde is dat er gaten mogen ontstaan dmv correcties (false).
     * @return false tenzij de implementerende groep wel een aaneengesloten historie nodig heeft
     */
    default boolean moetHistorieAaneengeslotenZijn() {
        // Vanuit BMR kunnen er 3 waarden zijn (1: Ja, 2: Nee, gaten kunnen alléén door correcties aan het begin voorkomen,  3: Nee, gaten kunnen overal in
        // tijdlijn voor komen). Op moment van maken worden alleen opties 2 en 3 gebruikt bij de materiele groepen. Bij het specificeren is er niet
        // aangegeven hoe optie 2 gecontroleerd kan worden, waardoor optie 1 en 2 nagenoeg hetzelfde zijn. Hierdoor is er besloten om een boolean te
        // gebruiken ipv bv een enumeratie.
        return false;
    }
}
