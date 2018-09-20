package nl.bzk.brp.model.objecttype.logisch.basis;

import java.util.Collection;

import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.groep.logisch.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBijhoudingsverantwoordelijkheidGroep;
import nl.bzk.brp.model.groep.logisch.PersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeboorteGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.groep.logisch.PersoonImmigratieGroep;
import nl.bzk.brp.model.groep.logisch.PersoonInschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonOpschortingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonOverlijdenGroep;
import nl.bzk.brp.model.groep.logisch.PersoonPersoonskaartGroep;
import nl.bzk.brp.model.groep.logisch.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.groep.logisch.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.objecttype.logisch.PersoonIndicatie;
import nl.bzk.brp.model.objecttype.logisch.PersoonNationaliteit;
import nl.bzk.brp.model.objecttype.logisch.PersoonReisdocument;
import nl.bzk.brp.model.objecttype.logisch.PersoonVoornaam;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;


/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 *
 */
public interface PersoonBasis extends ObjectType {

    /**
     * Classificatie van Persoon.
     *
     * @return SoortPersoon
     */
    SoortPersoon getSoort();

    /**
     * Groep identificerende nummers.
     *
     * @return PersoonIdentificatienummersGroep
     */
    PersoonIdentificatienummersGroep getIdentificatienummersGroep();

    /**
     * Gegevens over het geslacht van een Persoon.
     *
     * @return PersoonGeslachtsaanduidingGroep
     */
    PersoonGeslachtsaanduidingGroep getGeslachtsaanduidingGroep();

    /**
     * De naam zoals die ontstaat door samenvoegen van alle exemplaren van Voornaam en van Geslachtsnaamcomponent van een Persoon.
     *
     * @return PersoonSamengesteldeNaamGroep
     */
    PersoonSamengesteldeNaamGroep getSamengesteldeNaamGroep();

    /**
     * .
     *
     * @return PersoonAanschrijvingGroep
     */
    PersoonAanschrijvingGroep getAanschrijvingGroep();

    /**
     * Geboortegevens over een Persoon.
     *
     * @return PersoonGeboorteGroep
     */
    PersoonGeboorteGroep getGeboorteGroep();

    /**
     * .
     *
     * @return PersoonOverlijdenGroep
     */
    PersoonOverlijdenGroep getOverlijdenGroep();

    /**
     * .
     *
     * @return PersoonVerblijfsrechtGroep
     */
    PersoonVerblijfsrechtGroep getVerblijfsrechtGroep();

    /**
     * Gegevens over een eventuele uitsluiting van Nederlandse verkiezingen.
     *
     * @return PersoonUitsluitingNLKiesrechtGroep
     */
    PersoonUitsluitingNLKiesrechtGroep getUitsluitingNLKiesrechtGroep();

    /**
     * Gegevens over de eventuele deelname aan EU verkiezingen.
     *
     * @return PersoonEUVerkiezingenGroep
     */
    PersoonEUVerkiezingenGroep getEUVerkiezingenGroep();

    /**
     * Groep gegevens over de bijhouding.
     *
     * @return PersoonBijhoudingsverantwoordelijkheidGroep
     */
    PersoonBijhoudingsverantwoordelijkheidGroep getBijhoudingsverantwoordelijkheidGroep();

    /**
     * .
     *
     * @return PersoonOpschortingGroep
     */
    PersoonOpschortingGroep getOpschortingGroep();

    /**
     * .
     *
     * @return PersoonBijhoudingsgemeenteGroep
     */
    PersoonBijhoudingsgemeenteGroep getBijhoudingsgemeenteGroep();

    /**
     * .
     *
     * @return PersoonPersoonskaartGroep
     */
    PersoonPersoonskaartGroep getPersoonskaartGroep();

    /**
     * .
     *
     * @return PersoonImmigratieGroep
     */
    PersoonImmigratieGroep getImmigratieGroep();

    /**
     * Deze verzameling van gegevens geeft weer wanneer de gegevens van een persoon in de BRP (voorheen GBA) zijn ingeschreven, het moment van de laatste actualisering en of er eventuele identificatienummerwijzigingen zijn.
     *
     * @return PersoonInschrijvingGroep
     */
    PersoonInschrijvingGroep getInschrijvingGroep();

    /**
     * Datum en tijdstip waarop gegevens van de persoon voor het laatst zijn aangepast. .
     *
     * @return DatumTijd
     */
    DatumTijd getTijdstipLaatsteWijziging();

    /**
     * Indicator die aangeeft dat er gegevens over de persoon in onderzoek zijn.
     *
     * @return JaNee
     */
    JaNee getIndicatieGegevensInOnderzoek();

    /**
     * De collectie van Voornamen van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set
     *
     * @return Collection<? extends PersoonVoornaam>
     */
    Collection<? extends PersoonVoornaam> getVoornamen();

    /**
     * De collectie van Geslachtsnaamcomponenten van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set
     *
     * @return Collection<? extends PersoonGeslachtsnaamcomponent>
     */
    Collection<? extends PersoonGeslachtsnaamcomponent> getGeslachtsnaamcomponenten();

    /**
     * De collectie van Nationaliteiten van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set
     *
     * @return Collection<? extends PersoonNationaliteit>
     */
    Collection<? extends PersoonNationaliteit> getNationaliteiten();

    /**
     * De collectie van Adressen van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set
     *
     * @return Collection<? extends PersoonAdres>
     */
    Collection<? extends PersoonAdres> getAdressen();

    /**
     * De collectie van Indicaties van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set
     *
     * @return Collection<? extends PersoonIndicatie>
     */
    Collection<? extends PersoonIndicatie> getIndicaties();

    /**
     * De collectie van Reisdocumenten van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set
     *
     * @return Collection<? extends PersoonReisdocument>
     */
    Collection<? extends PersoonReisdocument> getReisdocumenten();

    /**
     * De collectie van Betrokkenheden van deze persoon.
     * Let op: in het bericht is dit een list, in het model een set
     *
     * @return Collection<? extends Betrokkenheid>
     */
    Collection<? extends Betrokkenheid> getBetrokkenheden();

}
