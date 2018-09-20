package nl.bzk.brp.model.objecttype.logisch.basis;

import java.util.Collection;

import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.groep.logisch.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBehandeldAlsNederlanderGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBelemmeringVerstrekkingReisdocumentGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBezitBuitenlandsReisdocumentGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBijhoudingsgemeenteGroep;
import nl.bzk.brp.model.groep.logisch.PersoonBijhoudingsverantwoordelijkheidGroep;
import nl.bzk.brp.model.groep.logisch.PersoonCurateleGroep;
import nl.bzk.brp.model.groep.logisch.PersoonEuVerkiezingenGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeboorteGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeprivilegieerdeGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGezagDerdeGroep;
import nl.bzk.brp.model.groep.logisch.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.groep.logisch.PersoonImmigratieGroep;
import nl.bzk.brp.model.groep.logisch.PersoonInschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonOpschortingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonOverlijdenGroep;
import nl.bzk.brp.model.groep.logisch.PersoonPersoonskaartGroep;
import nl.bzk.brp.model.groep.logisch.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.groep.logisch.PersoonStatenloosGroep;
import nl.bzk.brp.model.groep.logisch.PersoonUitsluitingNlKiesrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonVastgesteldNietNederlanderGroep;
import nl.bzk.brp.model.groep.logisch.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonVerstrekkingsbeperkingGroep;
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
 */
public interface PersoonBasis extends ObjectType {

    /**
     * Classificatie van Persoon.
     *
     * @return SoortPersoon
     */
    SoortPersoon getSoort();

    /**
     * Gegevens met betrekking tot het bezit van een buitenlands reisdocument.
     *
     * @return PersoonBezitBuitenlandsReisdocumentGroep
     */
    PersoonBezitBuitenlandsReisdocumentGroep getPersoonBezitBuitenlandsReisdocumentGroep();

    /**
     * Gegevens met betrekking tot een eventuele belemmering bij het verstrekken van Reisdocumenten.
     *
     * @return PersoonBelemmeringVerstrekkingReisdocumentGroep
     */
    PersoonBelemmeringVerstrekkingReisdocumentGroep getPersoonBelemmeringVerstrekkingReisdocumentGroep();

    /**
     * Gegevens met betrekking tot het behandelen als Nederlander.
     *
     * @return PersoonBehandeldAlsNederlanderGroep
     */
    PersoonBehandeldAlsNederlanderGroep getPersoonBehandeldAlsNederlanderGroep();

    /**
     * Gegevens over een mogelijke vaststelling van het feit dat een persoon niet een Nederlander is.
     *
     * @return PersoonVastgesteldNietNederlanderGroep
     */
    PersoonVastgesteldNietNederlanderGroep getPersoonVastgesteldNietNederlanderGroep();

    /**
     * Gegevens over het geprivilegieerd zijn van een persoon.
     *
     * @return PersoonGeprivilegieerdeGroep
     */
    PersoonGeprivilegieerdeGroep getPersoonGeprivilegieerdeGroep();

    /**
     * .
     *
     * @return PersoonVerstrekkingsbeperkingGroep
     */
    PersoonVerstrekkingsbeperkingGroep getPersoonVerstrekkingsbeperkingGroep();

    /**
     * Gegevens over de eventuele deelname aan EU verkiezingen.
     *
     * @return PersoonEuVerkiezingenGroep
     */
    PersoonEuVerkiezingenGroep getPersoonEuVerkiezingenGroep();

    /**
     * Gegevens over het onder curatele staan van de persoon.
     *
     * @return PersoonCurateleGroep
     */
    PersoonCurateleGroep getPersoonCurateleGroep();

    /**
     * .
     *
     * @return PersoonImmigratieGroep
     */
    PersoonImmigratieGroep getPersoonImmigratieGroep();

    /**
     * .
     *
     * @return PersoonBijhoudingsgemeenteGroep
     */
    PersoonBijhoudingsgemeenteGroep getPersoonBijhoudingsgemeenteGroep();

    /**
     * Groep gegevens over de bijhouding.
     *
     * @return PersoonBijhoudingsverantwoordelijkheidGroep
     */
    PersoonBijhoudingsverantwoordelijkheidGroep getPersoonBijhoudingsverantwoordelijkheidGroep();

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
     * .
     *
     * @return PersoonPersoonskaartGroep
     */
    PersoonPersoonskaartGroep getPersoonPersoonskaartGroep();

    /**
     * Gegevens over het geslacht van een Persoon.
     *
     * @return PersoonGeslachtsaanduidingGroep
     */
    PersoonGeslachtsaanduidingGroep getPersoonGeslachtsaanduidingGroep();

    /**
     * Deze verzameling van gegevens geeft weer wanneer de gegevens van een persoon in de BRP (voorheen GBA) zijn ingeschreven, het moment van de laatste actualisering en of er eventuele identificatienummerwijzigingen zijn.
     *
     * @return PersoonInschrijvingGroep
     */
    PersoonInschrijvingGroep getPersoonInschrijvingGroep();

    /**
     * Gegevens over een eventuele uitsluiting van Nederlandse verkiezingen.
     *
     * @return PersoonUitsluitingNlKiesrechtGroep
     */
    PersoonUitsluitingNlKiesrechtGroep getPersoonUitsluitingNlKiesrechtGroep();

    /**
     * Gegevens over het onder het gezag van een derde staan.
     *
     * @return PersoonGezagDerdeGroep
     */
    PersoonGezagDerdeGroep getPersoonGezagDerdeGroep();

    /**
     * .
     *
     * @return PersoonVerblijfsrechtGroep
     */
    PersoonVerblijfsrechtGroep getPersoonVerblijfsrechtGroep();

    /**
     * .
     *
     * @return PersoonOverlijdenGroep
     */
    PersoonOverlijdenGroep getPersoonOverlijdenGroep();

    /**
     * Geboortegevens over een Persoon.
     *
     * @return PersoonGeboorteGroep
     */
    PersoonGeboorteGroep getPersoonGeboorteGroep();

    /**
     * .
     *
     * @return PersoonAanschrijvingGroep
     */
    PersoonAanschrijvingGroep getPersoonAanschrijvingGroep();

    /**
     * Groep identificerende nummers.
     *
     * @return PersoonIdentificatienummersGroep
     */
    PersoonIdentificatienummersGroep getPersoonIdentificatienummersGroep();

    /**
     * .
     *
     * @return PersoonOpschortingGroep
     */
    PersoonOpschortingGroep getPersoonOpschortingGroep();

    /**
     * De naam zoals die ontstaat door samenvoegen van alle exemplaren van Voornaam en van Geslachtsnaamcomponent van een Persoon.
     *
     * @return PersoonSamengesteldeNaamGroep
     */
    PersoonSamengesteldeNaamGroep getPersoonSamengesteldeNaamGroep();

    /**
     * .
     *
     * @return PersoonStatenloosGroep
     */
    PersoonStatenloosGroep getPersoonStatenloosGroep();

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
