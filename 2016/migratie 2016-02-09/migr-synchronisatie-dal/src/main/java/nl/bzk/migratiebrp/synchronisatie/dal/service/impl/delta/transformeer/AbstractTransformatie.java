/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractDeltaEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AbstractFormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.MaterieleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.SleutelUtil;

/**
 * Abstracte superclass voor tranformaties, bevat code die voor verschillende concrete transformaties wordt gebruikt.
 */
public abstract class AbstractTransformatie implements Transformatie {

    /**
     * @param verschillen
     *            de verschillen
     * @param verwachteAantalWijzigingen
     *            verwachte aantal verschillen
     * @return true als er twee verschillen zijn, anders false
     */
    protected final boolean zijnHetVerwachtAantalVeldenGevuld(final VerschilGroep verschillen, final int verwachteAantalWijzigingen) {
        return verschillen.size() == verwachteAantalWijzigingen;
    }

    /**
     * Maak een nieuwe sleutel voor het toevoegen/verwijderen van een historie rij, op basis van een reguliere sleutel
     * een aangepaste historie rij. De reguliere sleutels hebben een andere structuur dan de toevoegen/verwijderen
     * sleutels.
     *
     * @param historieRijSleutel
     *            de reguliere historie rij sleutel
     * @return de toevoegen/verwijderen historie rij sleutel
     */
    protected final EntiteitSleutel maakToegevoegdVerwijderdSleutel(final EntiteitSleutel historieRijSleutel) {
        final EntiteitSleutel result = new EntiteitSleutel(historieRijSleutel.getEigenaarSleutel());
        for (final Map.Entry<String, Object> sleutelDeel : historieRijSleutel.getDelen().entrySet()) {
            result.addSleuteldeel(sleutelDeel.getKey(), sleutelDeel.getValue());
        }
        return result;
    }

    /**
     * Zoek de sleutel voor de historie rij op basis van een verschil in die historie rij, of in een onderliggende
     * entiteit zoals een Actie of Documentatie.
     *
     * @param verschil
     *            het verschil
     * @return de sleutel voor de historie rij
     */
    protected final EntiteitSleutel bepaalHistorieRijSleutel(final Verschil verschil) {
        EntiteitSleutel result = (EntiteitSleutel) verschil.getSleutel();

        final Class<? extends FormeleHistorie> historieClass =
                (verschil.getBestaandeHistorieRij() != null ? verschil.getBestaandeHistorieRij() : verschil.getNieuweHistorieRij()).getClass();

        while (result != null) {
            if (historieClass.equals(result.getEntiteit())) {
                break;
            } else {
                result = result.getEigenaarSleutel();
            }
        }

        return result;
    }

    /**
     * Voegt de <code>actieVervalTbvLeveringMuts</code> toe aan de verschilgroep en levert het resultaat als nieuwe
     * verschilgroep.
     *
     * @param verschilGroep
     *            de verschilgroep
     * @param actieVervalTbvLeveringMuts
     *            de actie verval tbv levering mutaties
     * @return een nieuwe VerschilGroep met de oude verschillen plus het verschil voor de actieVervalTbvLeveringMuts
     */
    protected final VerschilGroep voegActieVervalTbvLeveringMutatiesToeAanVerschilGroep(
        final VerschilGroep verschilGroep,
        final BRPActie actieVervalTbvLeveringMuts)
    {
        final List<Verschil> verschillen = new ArrayList<>(verschilGroep.getVerschillen());

        final EntiteitSleutel bestaandeEntiteitSleutel = (EntiteitSleutel) verschillen.get(0).getSleutel();

        final EntiteitSleutel verschilSleutel = new EntiteitSleutel(bestaandeEntiteitSleutel, AbstractFormeleHistorie.ACTIE_VERVAL_TBV_LEVERING_MUTATIES);
        final Verschil actieVervalTbvLeveringMutsVerschil =
                new Verschil(
                    verschilSleutel,
                    null,
                    actieVervalTbvLeveringMuts,
                    VerschilType.ELEMENT_NIEUW,
                    verschilGroep.get(0).getBestaandeHistorieRij(),
                    verschilGroep.get(0).getNieuweHistorieRij());
        verschillen.add(actieVervalTbvLeveringMutsVerschil);

        final VerschilGroep kopieVerschilGroep = VerschilGroep.maakKopieZonderVerschillen(verschilGroep);
        kopieVerschilGroep.addVerschillen(verschillen);
        return kopieVerschilGroep;
    }

    /**
     * Maak van een rij-verwijderd verschil een nieuwe lijst verschillen die de rij tranformeert naar een 'M' rij.
     *
     * @param rijVerwijderdVerschil
     *            het rij-verwijderd verschil
     * @param actieVervalTbvLeveringMuts
     *            de actieVervalTbvLeveringMuts voor de 'M' rij
     * @param deltaBepalingContext
     *            de delta context
     * @return de lijst van verschillen om van de rij een 'M' rij te maken
     */
    protected final List<Verschil> transformeerVerwijderdeRijNaarMRijVerschillen(
        final Verschil rijVerwijderdVerschil,
        final BRPActie actieVervalTbvLeveringMuts,
        final DeltaBepalingContext deltaBepalingContext)
    {
        final FormeleHistorie oudeRij = rijVerwijderdVerschil.getBestaandeHistorieRij();
        final EntiteitSleutel rijVerwijderdSleutel = (EntiteitSleutel) rijVerwijderdVerschil.getSleutel();
        final EntiteitSleutel rijInhoudSleutel = maakRijInhoudSleutel(rijVerwijderdSleutel, oudeRij);

        return maakMRijVerschillen(oudeRij, rijInhoudSleutel, rijVerwijderdVerschil, actieVervalTbvLeveringMuts, deltaBepalingContext);
    }

    /**
     * Maakt voor de gegeven VerschilGroep een M-rij en een nieuwe rij verschil.
     *
     * @param verschilGroep
     *            de bestaande verschillen
     * @param actieVervalTbvLeveringMuts
     *            de actie tbv mutatie leveringen
     * @param setActieVervalTbvLeveringMutsOpNieuweRij
     *            true als actieVervalTbvLeveringMuts ook op de nieuwe rij gezet moet worden, anders false
     * @param vervangActiesOpNieuweRij
     *            true als de actie-inhoud en actie-verval van de oude naar de nieuwe historie rij moeten worden
     *            overgezet
     * @param deltaBepalingContext
     *            de delta context
     * @return de aangepaste verschillen in een nieuwe VerschilGroep object
     */
    protected final VerschilGroep maakMRijEnNieuweRijVerschilVoorVerschilGroep(
        final VerschilGroep verschilGroep,
        final BRPActie actieVervalTbvLeveringMuts,
        final boolean setActieVervalTbvLeveringMutsOpNieuweRij,
        final boolean vervangActiesOpNieuweRij,
        final DeltaBepalingContext deltaBepalingContext)
    {
        final Verschil verschil0 = verschilGroep.get(0);

        final EntiteitSleutel historieRijSleutel = bepaalHistorieRijSleutel(verschil0);
        final EntiteitSleutel toegevoegdVerwijderdSleutel = maakToegevoegdVerwijderdSleutel(historieRijSleutel);

        final List<Verschil> toegevoegdVerwijderd = new ArrayList<>(2);
        final FormeleHistorie nieuweHistorieRij = verschil0.getNieuweHistorieRij();
        final FormeleHistorie oudeHistorieRij = verschil0.getBestaandeHistorieRij();

        toegevoegdVerwijderd.add(new Verschil(toegevoegdVerwijderdSleutel, null, nieuweHistorieRij, VerschilType.RIJ_TOEGEVOEGD, null, nieuweHistorieRij));
        toegevoegdVerwijderd.addAll(transformeerAangepasteRijNaarMRijVerschillen(verschil0, actieVervalTbvLeveringMuts, deltaBepalingContext));

        final EntiteitSleutel nieuweRijSleutel;
        try {
            nieuweRijSleutel = SleutelUtil.maakSleutel(nieuweHistorieRij, historieRijSleutel.getEigenaarSleutel(), "");
        } catch (final ReflectiveOperationException e) {
            throw new IllegalStateException("Kan geen sleutel voor nieuwe rij aanmaken", e);
        }

        toegevoegdVerwijderd.addAll(maakNieuweRijAanpassingVerschillen(
            nieuweRijSleutel,
            actieVervalTbvLeveringMuts,
            setActieVervalTbvLeveringMutsOpNieuweRij,
            vervangActiesOpNieuweRij,
            nieuweHistorieRij,
            oudeHistorieRij,
            deltaBepalingContext));

        final VerschilGroep kopieVerschilGroep = VerschilGroep.maakKopieZonderVerschillen(verschilGroep);
        kopieVerschilGroep.addVerschillen(toegevoegdVerwijderd);
        return kopieVerschilGroep;
    }

    private Collection<Verschil> maakNieuweRijAanpassingVerschillen(
        final EntiteitSleutel historieRijInhoudSleutel,
        final BRPActie actieVervalTbvLeveringMuts,
        final boolean setActieVervalTbvLeveringMutsOpNieuweRij,
        final boolean vervangActiesOpNieuweRij,
        final FormeleHistorie nieuweHistorieRij,
        final FormeleHistorie oudeHistorieRij,
        final DeltaBepalingContext deltaBepalingContext)
    {
        final List<Verschil> nieuweRijVerschillen = new ArrayList<>();

        // hoe vervangacties ongedaan maken als stapel in resync vervalt?
        if (vervangActiesOpNieuweRij) {
            // Vervang de actie-inhoud in de nieuwe historie rij zodat die consistent is met de oude 'M'-rij
            nieuweRijVerschillen.add(new Verschil(
                new EntiteitSleutel(historieRijInhoudSleutel, AbstractFormeleHistorie.ACTIE_INHOUD),
                nieuweHistorieRij.getActieInhoud(),
                oudeHistorieRij.getActieInhoud(),
                VerschilType.NIEUWE_RIJ_ELEMENT_AANGEPAST,
                oudeHistorieRij,
                nieuweHistorieRij));

            if (nieuweHistorieRij.getActieInhoud() == nieuweHistorieRij.getActieVerval()) {
                nieuweRijVerschillen.add(new Verschil(
                    new EntiteitSleutel(historieRijInhoudSleutel, AbstractFormeleHistorie.ACTIE_VERVAL),
                    nieuweHistorieRij.getActieVerval(),
                    oudeHistorieRij.getActieInhoud(),
                    VerschilType.NIEUWE_RIJ_ELEMENT_AANGEPAST,
                    oudeHistorieRij,
                    nieuweHistorieRij));
            }
        }

        if (setActieVervalTbvLeveringMutsOpNieuweRij) {
            nieuweRijVerschillen.add(new Verschil(
                new EntiteitSleutel(historieRijInhoudSleutel, AbstractFormeleHistorie.ACTIE_VERVAL_TBV_LEVERING_MUTATIES),
                null,
                actieVervalTbvLeveringMuts,
                VerschilType.NIEUWE_RIJ_ELEMENT_AANGEPAST,
                oudeHistorieRij,
                nieuweHistorieRij));
        }

        deltaBepalingContext.markeerBestaandeRijAlsMRij(oudeHistorieRij);

        final Collection<Element> elementenInOnderzoek = oudeHistorieRij.getElementenInOnderzoek();
        if (!elementenInOnderzoek.isEmpty()) {
            nieuweRijVerschillen.add(new Verschil(
                new EntiteitSleutel(historieRijInhoudSleutel, AbstractDeltaEntiteit.GEGEVEN_IN_ONDERZOEK),
                null,
                null,
                VerschilType.KOPIEER_ONDERZOEK_NAAR_NIEUWE_RIJ,
                oudeHistorieRij,
                nieuweHistorieRij));
        }

        return nieuweRijVerschillen;
    }

    /**
     * Heeft het verschil betrekking op formele historie.
     *
     * @param verschillen
     *            de verschillen
     * @return true als de wijzigingen betrekking hebben op formele historie
     */
    protected final boolean isWijzigingOpFormeleHistorie(final VerschilGroep verschillen) {
        return !isWijzigingOpMaterieleHistorie(verschillen);
    }

    /**
     * Heeft het verschil betrekking op materiele historie.
     *
     * @param verschillen
     *            de verschillen
     * @return true als de wijzigingen betrekking hebben op materiele historie
     */
    protected final boolean isWijzigingOpMaterieleHistorie(final VerschilGroep verschillen) {
        return verschillen.get(0).getBestaandeHistorieRij() instanceof MaterieleHistorie;
    }

    /**
     * Maak van een inhoudelijk verschil in een rij een nieuwe lijst verschillen die de rij tranformeert naar een 'M'
     * rij.
     *
     * @param rijAangepastVerschil
     *            een verschil dat een rij inhoudelijk aanpast
     * @param actieVervalTbvLeveringMuts
     *            de actieVervalTbvLeveringMuts voor de 'M' rij
     * @param deltaBepalingContext
     *            de delta context
     * @return de lijst van verschillen om van de rij een 'M' rij te maken
     */
    private List<Verschil> transformeerAangepasteRijNaarMRijVerschillen(
        final Verschil rijAangepastVerschil,
        final BRPActie actieVervalTbvLeveringMuts,
        final DeltaBepalingContext deltaBepalingContext)
    {
        final FormeleHistorie oudeRij = rijAangepastVerschil.getBestaandeHistorieRij();
        final EntiteitSleutel rijInhoudSleutel = bepaalHistorieRijSleutel(rijAangepastVerschil);

        return maakMRijVerschillen(oudeRij, rijInhoudSleutel, rijAangepastVerschil, actieVervalTbvLeveringMuts, deltaBepalingContext);
    }

    /**
     * Maak een lijst verschillen waarmee de gegeven rij een 'M'-rij wordt.
     *
     * @param oudeRij
     *            de rij
     * @param rijInhoudSleutel
     *            een sleutel voor de rij inhoud
     * @param historieContextVerschil
     *            een verschil waaruit de historie context voor de nieuwe verschillen wordt gekopieerd.
     * @param actieVervalTbvLeveringMuts
     *            de actie verval t.b.v. levering mutaties
     * @param deltaBepalingContext
     *            de delta context
     * @return de lijst verschillen
     */
    public static List<Verschil> maakMRijVerschillen(
        final FormeleHistorie oudeRij,
        final EntiteitSleutel rijInhoudSleutel,
        final Verschil historieContextVerschil,
        final BRPActie actieVervalTbvLeveringMuts,
        final DeltaBepalingContext deltaBepalingContext)
    {
        final List<Verschil> mRijVerschillen = new ArrayList<>();
        if (historieContextVerschil.isConsolidatieVerschil()) {
            mRijVerschillen.addAll(maakMRijVoorConsolidatie(oudeRij, rijInhoudSleutel, historieContextVerschil, actieVervalTbvLeveringMuts));
        } else {
            mRijVerschillen.addAll(maakMRijVerschillen(oudeRij, rijInhoudSleutel, historieContextVerschil, actieVervalTbvLeveringMuts));
        }

        return mRijVerschillen;
    }

    private static List<Verschil> maakMRijVerschillen(
        final FormeleHistorie oudeRij,
        final EntiteitSleutel rijInhoudSleutel,
        final Verschil historieContextVerschil,
        final BRPActie actieVervalTbvLeveringMuts)
    {
        final List<Verschil> mRijVerschillen = new ArrayList<>();
        mRijVerschillen.add(Verschil.maakVerschil(
            new EntiteitSleutel(rijInhoudSleutel, AbstractFormeleHistorie.DATUM_TIJD_VERVAL),
            oudeRij.getDatumTijdVerval(),
            oudeRij.getDatumTijdRegistratie(),
            historieContextVerschil.getBestaandeHistorieRij(),
            historieContextVerschil.getNieuweHistorieRij()));
        mRijVerschillen.add(Verschil.maakVerschil(
            new EntiteitSleutel(rijInhoudSleutel, AbstractFormeleHistorie.ACTIE_VERVAL),
            oudeRij.getActieVerval(),
            oudeRij.getActieInhoud(),
            historieContextVerschil.getBestaandeHistorieRij(),
            historieContextVerschil.getNieuweHistorieRij()));
        mRijVerschillen.add(Verschil.maakVerschil(
            new EntiteitSleutel(rijInhoudSleutel, AbstractFormeleHistorie.INDICATIE_VOORKOMEN_TBV_LEVERING_MUTATIES),
            oudeRij.getIndicatieVoorkomenTbvLeveringMutaties(),
            Boolean.TRUE,
            historieContextVerschil.getBestaandeHistorieRij(),
            historieContextVerschil.getNieuweHistorieRij()));
        mRijVerschillen.add(Verschil.maakVerschil(
            new EntiteitSleutel(rijInhoudSleutel, AbstractFormeleHistorie.ACTIE_VERVAL_TBV_LEVERING_MUTATIES),
            oudeRij.getActieVervalTbvLeveringMutaties(),
            actieVervalTbvLeveringMuts,
            historieContextVerschil.getBestaandeHistorieRij(),
            historieContextVerschil.getNieuweHistorieRij()));

        return mRijVerschillen;
    }

    private static List<Verschil> maakMRijVoorConsolidatie(
        final FormeleHistorie oudeRij,
        final EntiteitSleutel rijInhoudSleutel,
        final Verschil historieContextVerschil,
        final BRPActie actieVervalTbvLeveringMuts)
    {
        final List<Verschil> mRijVerschillen = new ArrayList<>();

        mRijVerschillen.add(Verschil.maakConsolidatieVerschil(
            new EntiteitSleutel(rijInhoudSleutel, AbstractFormeleHistorie.DATUM_TIJD_VERVAL),
            oudeRij.getDatumTijdVerval(),
            oudeRij.getDatumTijdRegistratie(),
            historieContextVerschil.getBestaandeHistorieRij(),
            historieContextVerschil.getNieuweHistorieRij()));
        mRijVerschillen.add(Verschil.maakConsolidatieVerschil(
            new EntiteitSleutel(rijInhoudSleutel, AbstractFormeleHistorie.ACTIE_VERVAL),
            oudeRij.getActieVerval(),
            oudeRij.getActieInhoud(),
            historieContextVerschil.getBestaandeHistorieRij(),
            historieContextVerschil.getNieuweHistorieRij()));
        mRijVerschillen.add(Verschil.maakConsolidatieVerschil(
            new EntiteitSleutel(rijInhoudSleutel, AbstractFormeleHistorie.INDICATIE_VOORKOMEN_TBV_LEVERING_MUTATIES),
            oudeRij.getIndicatieVoorkomenTbvLeveringMutaties(),
            Boolean.TRUE,
            historieContextVerschil.getBestaandeHistorieRij(),
            historieContextVerschil.getNieuweHistorieRij()));
        mRijVerschillen.add(Verschil.maakConsolidatieVerschil(
            new EntiteitSleutel(rijInhoudSleutel, AbstractFormeleHistorie.ACTIE_VERVAL_TBV_LEVERING_MUTATIES),
            oudeRij.getActieVervalTbvLeveringMutaties(),
            actieVervalTbvLeveringMuts,
            historieContextVerschil.getBestaandeHistorieRij(),
            historieContextVerschil.getNieuweHistorieRij()));

        return mRijVerschillen;
    }

    /**
     * Maak een rij-inhoud sleutel op basis van de rij-verwijderd (of rij-toegevoegd) sleutel en de rij zelf.
     *
     * @param rijSleutel
     *            de rij-verwijderd sleutel
     * @param rij
     *            de rij
     * @return een rij-inhoud sleutel voor de verwijderde rij, met blanco veld
     */
    public static EntiteitSleutel maakRijInhoudSleutel(final EntiteitSleutel rijSleutel, final FormeleHistorie rij) {
        try {
            return SleutelUtil.maakSleutel(rij, rijSleutel, "");
        } catch (final ReflectiveOperationException e) {
            throw new IllegalStateException("Kon geen sleutel aanmaken", e);
        }
    }

    /**
     * Maak een stapel sleutel van een sleutel die een rij identificeert door deze sleutel te kopieren en de
     * identificerende sleuteldelen te verwijderen.
     *
     * @param identificerendeRijSleutel
     *            de rij-verwijderd sleutel
     * @return een rij-inhoud sleutel voor de verwijderde rij, met blanco veld
     */
    public static EntiteitSleutel maakSleutelZonderRijIdentificatie(final EntiteitSleutel identificerendeRijSleutel) {
        final EntiteitSleutel result = new EntiteitSleutel(identificerendeRijSleutel);
        result.removeSleuteldeel(EntiteitSleutel.SLEUTELDEEL_DATUM_TIJD_REGISTRATIE);
        result.removeSleuteldeel(EntiteitSleutel.SLEUTELDEEL_DATUM_AANVANG_GELDIGHEID);
        result.removeSleuteldeel(EntiteitSleutel.SLEUTELDEEL_BIJHOUDING_CATEGORIE);
        return result;
    }
}
