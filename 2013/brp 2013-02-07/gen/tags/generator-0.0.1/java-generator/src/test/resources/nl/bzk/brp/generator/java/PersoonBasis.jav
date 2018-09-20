package nl.bzk.brp.model.objecttype.logisch.basis;

import java.util.Set;

import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.groep.logisch.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonAfgeleidAdministratiefGroep;
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
import nl.bzk.brp.model.groep.logisch.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.groep.logisch.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.groep.logisch.PersoonskaartGroep;
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
    PersoonIdentificatienummersGroep getPersoonIdentificatienummersGroep();

    /**
     * Gegevens over het geslacht van een Persoon.
     *
     * @return PersoonGeslachtsaanduidingGroep
     */
    PersoonGeslachtsaanduidingGroep getPersoonGeslachtsaanduidingGroep();

    /**
     * De naam zoals die ontstaat door samenvoegen van alle exemplaren van Voornaam en van Geslachtsnaamcomponent van een Persoon.
     *
     * @return PersoonSamengesteldeNaamGroep
     */
    PersoonSamengesteldeNaamGroep getPersoonSamengesteldeNaamGroep();

    /**
     * .
     *
     * @return PersoonAanschrijvingGroep
     */
    PersoonAanschrijvingGroep getPersoonAanschrijvingGroep();

    /**
     * Geboortegevens over een Persoon.
     *
     * @return PersoonGeboorteGroep
     */
    PersoonGeboorteGroep getPersoonGeboorteGroep();

    /**
     * .
     *
     * @return PersoonOverlijdenGroep
     */
    PersoonOverlijdenGroep getPersoonOverlijdenGroep();

    /**
     * .
     *
     * @return PersoonVerblijfsrechtGroep
     */
    PersoonVerblijfsrechtGroep getPersoonVerblijfsrechtGroep();

    /**
     * Gegevens over een eventuele uitsluiting van Nederlandse verkiezingen.
     *
     * @return PersoonUitsluitingNLKiesrechtGroep
     */
    PersoonUitsluitingNLKiesrechtGroep getPersoonUitsluitingNLKiesrechtGroep();

    /**
     * Gegevens over de eventuele deelname aan EU verkiezingen.
     *
     * @return PersoonEUVerkiezingenGroep
     */
    PersoonEUVerkiezingenGroep getPersoonEUVerkiezingenGroep();

    /**
     * Groep gegevens over de bijhouding.
     *
     * @return PersoonBijhoudingsverantwoordelijkheidGroep
     */
    PersoonBijhoudingsverantwoordelijkheidGroep getPersoonBijhoudingsverantwoordelijkheidGroep();

    /**
     * .
     *
     * @return PersoonOpschortingGroep
     */
    PersoonOpschortingGroep getPersoonOpschortingGroep();

    /**
     * .
     *
     * @return PersoonBijhoudingsgemeenteGroep
     */
    PersoonBijhoudingsgemeenteGroep getPersoonBijhoudingsgemeenteGroep();

    /**
     * .
     *
     * @return PersoonskaartGroep
     */
    PersoonskaartGroep getPersoonskaartGroep();

    /**
     * .
     *
     * @return PersoonImmigratieGroep
     */
    PersoonImmigratieGroep getPersoonImmigratieGroep();

    /**
     * Deze verzameling van gegevens geeft weer wanneer de gegevens van een persoon in de BRP (voorheen GBA) zijn ingeschreven, het moment van de laatste actualisering en of er eventuele identificatienummerwijzigingen zijn.
     *
     * @return PersoonInschrijvingGroep
     */
    PersoonInschrijvingGroep getPersoonInschrijvingGroep();

    /**
     * .
     *
     * @return PersoonAfgeleidAdministratiefGroep
     */
    PersoonAfgeleidAdministratiefGroep getPersoonAfgeleidAdministratiefGroep();

    /**
     * Persoon waar de voornaam bijhoort.
     *
     * @return Set<? extends PersoonVoornaam>
     */
    Set<? extends PersoonVoornaam> getVoornamen();

    /**
     * Persoon waar het deel van de geslachtsnaam bijhoort.
     *
     * @return Set<? extends PersoonGeslachtsnaamcomponent>
     */
    Set<? extends PersoonGeslachtsnaamcomponent> getGeslachtsnaamcomponenten();

    /**
     * De Persoon om wiens Nationaliteit het gaat.
     *
     * @return Set<? extends PersoonNationaliteit>
     */
    Set<? extends PersoonNationaliteit> getNationaliteiten();

    /**
     * De persoon wiens adres het betreft.
     *
     * @return Set<? extends PersoonAdres>
     */
    Set<? extends PersoonAdres> getAdressen();

    /**
     * Persoon waar de indicatie betrekking op heeft.
     *
     * @return Set<? extends PersoonIndicatie>
     */
    Set<? extends PersoonIndicatie> getIndicaties();

    /**
     * De Persoon, om wiens Reisdocument het gaat.
     *
     * @return Set<? extends PersoonReisdocument>
     */
    Set<? extends PersoonReisdocument> getReisdocumenten();

    /**
     * De Persoon over wiens Betrokkenheid het gaat.
     *
     * @return Set<? extends Betrokkenheid>
     */
    Set<? extends Betrokkenheid> getBetrokkenheden();

}
