/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.dataaccess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import nl.bzk.brp.generatoren.algemeen.common.BmrLaag;
import nl.bzk.brp.generatoren.algemeen.common.BmrTargetPlatform;
import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.BasisType;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;
import nl.bzk.brp.metaregister.model.Regelattribuut;
import nl.bzk.brp.metaregister.model.SoortActieSoortAdmhnd;
import nl.bzk.brp.metaregister.model.Tekst;
import nl.bzk.brp.metaregister.model.Tuple;
import nl.bzk.brp.metaregister.model.VwElement;
import nl.bzk.brp.metaregister.model.WaarderegelWaarde;
import org.springframework.stereotype.Repository;


/** Dit is het Data Access Object voor het BMR. Met dit object kan informatie worden opgehaald uit het BMR. */
@Repository
public class BmrDaoImpl implements BmrDao {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    /**
     * Methode voor het opbouwen van een {@link TypedQuery} op basis van een opgegeven template en een filter met
     * zoek criteria.
     *
     * @param queryTemplate de query Template, waarvan de eis is dat het een String template is met een enkele
     * placeholder waar de extra filter condities in gezet zullen worden.
     * @param filter een filter object met daarin de waardes waarop gefilterd moet worden.
     * @param elementNaam naam van het element dat opgehaald wordt in de query template.
     * @param clazz de class van de objecten die worden geretourneerd door de query.
     * @param <T> het type van de objecten die geretourneerd worden door de {@link TypedQuery}.
     * @return een query.
     */
    private <T> TypedQuery<T> bouwQueryMetTemplateEnFilter(final String queryTemplate,
        final BmrElementFilterObject filter,
        final String elementNaam, final Class<T> clazz)
    {
        final List<String> filterStrings = filter.bouwWhereClauses(elementNaam);
        final StringBuilder whereClause = new StringBuilder();

        if (filterStrings.size() > 0) {
            whereClause.append(filterStrings.get(0));
            for (int i = 1; i < filterStrings.size(); i++) {
                whereClause.append(" AND ");
                whereClause.append(filterStrings.get(i));
            }
            whereClause.append(" ");
        }

        final String queryString = String.format(queryTemplate, whereClause.toString());
        final TypedQuery<T> query = em.createQuery(queryString, clazz);
        final Map<String, Object> parameters = filter.bouwParametersMap();

        for (final Map.Entry<String, Object> param : parameters.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        return query;
    }

    /** {@inheritDoc} */
    @Override
    public List<ObjectType> getObjectTypen() {
        final String sql = "SELECT ot FROM ObjectType ot";
        return em.createQuery(sql, ObjectType.class).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<ObjectType> getObjectTypen(final BmrElementFilterObject filter) {
        final String queryTemplate = "SELECT ot FROM ObjectType ot WHERE %s";
        final TypedQuery<ObjectType> query = bouwQueryMetTemplateEnFilter(queryTemplate, filter, "ot", ObjectType.class);
        return query.getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<Tuple> getTupleEnumeratieVoorAttribuutType(final AttribuutType attribuutType) {
        final String sql = "SELECT tuple "
            + "FROM Tuple tuple, ObjectType objectType, Attribuut attribuut "
            + "WHERE attribuut.type = :attribuutType "
            + "AND attribuut.naam = 'Code' "
            + "AND attribuut.objectType = objectType "
            + "AND objectType.soortInhoud = 'X' "
            + "AND tuple.objectType = objectType "
            + "ORDER BY tuple.volgnummerT";
        return em.createQuery(sql, Tuple.class).
            setParameter("attribuutType", attribuutType).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<Tuple> getTupleEnumeratieVoorObjectType(final ObjectType objectType) {
        final String sql = "SELECT tuple "
                + "FROM Tuple tuple, ObjectType objectType "
                + "WHERE objectType.id = :objectTypeId "
                + "AND tuple.objectType = objectType "
                + "ORDER BY tuple.volgnummerT";
        return em.createQuery(sql, Tuple.class).
                setParameter("objectTypeId", objectType.getId()).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<WaarderegelWaarde> getWaardeEnumeratiesVoorElement(final GeneriekElement element,
        final boolean filterOpInBericht,
        final boolean filterOpInCode)
    {
        String sql = "SELECT waardeRegelWaarde "
            + "FROM WaarderegelWaarde waardeRegelWaarde, Regel regel "
            + "WHERE regel.elementByOuder = :element "
            + "AND waardeRegelWaarde.regel = regel "
            + "AND regel.soortRegel.code = 'WB' ";
        if (filterOpInBericht) {
            sql += "AND (waardeRegelWaarde.inBericht IS NULL OR waardeRegelWaarde.inBericht = 'J') ";
        }
        if (filterOpInCode) {
            sql += "AND (waardeRegelWaarde.inCode IS NULL OR waardeRegelWaarde.inCode = 'J') ";
        }
        sql += "ORDER BY waardeRegelWaarde.volgnummer ";
        return em.createQuery(sql, WaarderegelWaarde.class).
            setParameter("element", element).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<Tekst> getTekstenVoorElement(final GeneriekElement element) {
        final String sql = "SELECT tekst FROM Tekst tekst where element.id = :elId"
            + " ORDER BY tekst.soortTekst.volgnummer";
        return em.createQuery(sql, Tekst.class).setParameter("elId", element.getId()).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<Groep> getGroepenVoorObjectType(final ObjectType objectType) {
        final String sql = "SELECT groep FROM Groep groep WHERE groep.objectType = :objectType "
            + "ORDER BY groep.volgnummerG ASC";
        return em.createQuery(sql, Groep.class).setParameter("objectType", objectType).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<Groep> getGroepenVoorObjectType(final ObjectType objectType,
        final boolean inclusiefGroepenUitSupertypes)
    {
        List<Groep> groepen = getGroepenVoorObjectType(objectType);
        if (inclusiefGroepenUitSupertypes) {
            final ObjectType supertype = objectType.getSuperType();
            if (supertype != null) {
                final List<Groep> supergroepen = getGroepenVoorObjectType(supertype, true);
                supergroepen.addAll(groepen);
                groepen = supergroepen;
            }
        }
        return groepen;
    }

    /** {@inheritDoc} */
    @Override
    public List<Groep> getGroepen() {
        final String sql = "SELECT groep FROM Groep groep";
        return em.createQuery(sql, Groep.class).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<Groep> getGroepenWaarvanHistorieWordtVastgelegd() {
        final String sql = "SELECT groep FROM Groep groep WHERE groep.objectType.soortInhoud IN ('D', 'S') "
            + "AND groep.historieVastleggen IN ('F', 'B', 'P')";
        return em.createQuery(sql, Groep.class).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<Attribuut> getAttributenVanGroep(final Groep groep) {
        final String sql = "SELECT attr FROM Attribuut attr WHERE attr.groep = :groep "
            + "ORDER BY attr.volgnummer ASC";
        return em.createQuery(sql, Attribuut.class).setParameter("groep", groep).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<AttribuutType> getAttribuutTypen() {
        // Filter op laag, want de attribuut typen komen in beide lagen voor. Hiermee voorkomen we dus de generatie
        // van dubbele attribuut typen.
        final String sql = "SELECT attrType FROM AttribuutType attrType WHERE attrType.elementByLaag.id = :laag";
        return em.createQuery(sql, AttribuutType.class)
                 .setParameter("laag", BmrLaag.LOGISCH.getWaardeInBmr()).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<BasisType> getBasisTypen(final BmrTargetPlatform targetPlatform) {
        final String sql = "SELECT basisType FROM BasisType basisType where basisType.platform.id = :platformId";
        return em.createQuery(sql, BasisType.class).
            setParameter("platformId", targetPlatform.getBmrId()).
                     getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public BasisTypeOverrulePaar getBasisTypeVoorAttribuutType(
        final AttribuutType attribuutType, final BmrTargetPlatform platform)
    {
        // Probeer eerst het eventueel aanwezig overrule basistype op te halen.
        BasisTypeOverrulePaar basisTypeOverrulePaar = this.getBasisTypeVoorElementId(attribuutType.getId(),
            attribuutType.getAantalBytes(), platform);
        if (basisTypeOverrulePaar != null) {
            // Als er een overrule gevonden is, markeer dat op het return object.
            basisTypeOverrulePaar.setOverrule(true);
        } else {
            // Als er geen overrule gevonden is, gebruik het generiek gelinkte basistype.
            basisTypeOverrulePaar = this.getBasisTypeVoorElementId(attribuutType.getBasisType().getId(),
                attribuutType.getAantalBytes(), platform);
            // Als er nog steeds geen basis type bepaald kon worden, is er een 'missing link' in het BMR gevonden.
            if (basisTypeOverrulePaar == null) {
                throw new IllegalStateException("Geen basis type kunnen herleiden voor attribuut type: '"
                    + attribuutType.getNaam() + "' en platform: '" + platform + "'.");
            }
        }
        return basisTypeOverrulePaar;
    }

    /**
     * Haalt het platform specifieke basistype op voor een gegeven element id. Dit id kan zijn van een attribuut type
     * of een basis type. Ook het filter aantal bytes wordt meegegeven, aangezien dat van invloed is op de uitkomst.
     * Het filter aantal bytes is echter niet verplicht, dus mag null zijn.
     *
     * @param elementId het id van een attribuut type of een basis type
     * @param filterAantalBytes het aantal bytes voor dit type, mag null zijn indien nvt
     * @param platform het target platform voor het basis type
     * @return het basis type dat hierbij past, in combinatie met het type impl
     */
    private BasisTypeOverrulePaar getBasisTypeVoorElementId(final int elementId,
        final Integer filterAantalBytes,
        final BmrTargetPlatform platform)
    {
        String sql = "SELECT new nl.bzk.brp.generatoren.algemeen.dataaccess.BasisTypeOverrulePaar(basisType, typeImpl) "
            + "FROM BasisType basisType, TypeImpl typeImpl "
            + "WHERE typeImpl.elementByType.id = :elementId "
            + "AND typeImpl.platform.id = :platformId "
            + "AND typeImpl.elementByBasistype.id = basisType.id ";
        // Probeer op aantal bytes te filteren als dat mogelijk is.
        final boolean filterOpAantalBytes = filterAantalBytes != null;
        if (filterOpAantalBytes) {
            // Zelfs als we op aantal bytes filteren, moeten we de optie open houden dat deze niet is ingevuld
            // in de type impl tabel. Dat kan namelijk niet uitmaken, of optioneel zijn.
            // NB: Deze extra subquery is nodig, voor het geval dat het aantal bytes van het attribuut type
            // niet precies 1, 2, 4, of 8 is. Er wordt in dat geval op het eerstvolgende hogere aantal
            // bytes geselecteerd.
            sql += "AND (typeImpl.filterAantalBytes IS NULL OR "
                + "typeImpl.filterAantalBytes = (SELECT min(typeImpl.filterAantalBytes) FROM TypeImpl typeImpl "
                + "WHERE typeImpl.elementByType.id = :elementId AND typeImpl.platform.id = :platformId "
                + "AND typeImpl.filterAantalBytes >= :filterAantalBytes))";
        } else {
            // Deze check wordt expliciet toegevoegd om af te dwingen dat er geen aantal bytes filter
            // in de mapping aanwezig is als deze ook niet bekend is.
            sql += "AND typeImpl.filterAantalBytes IS NULL";
        }
        final TypedQuery<BasisTypeOverrulePaar> query = em.createQuery(sql, BasisTypeOverrulePaar.class).
            setParameter("elementId", elementId).
                                                        setParameter("platformId", platform.getBmrId());
        if (filterOpAantalBytes) {
            query.setParameter("filterAantalBytes", filterAantalBytes);
        }
        final List<BasisTypeOverrulePaar> resultaatParen = query.getResultList();
        if (resultaatParen.size() > 1) {
            throw new IllegalStateException("Ongeldige type mapping: meerdere resultaten gevonden voor input: "
                + "element id: '" + elementId + "', "
                + "filterAantalBytes: '" + filterAantalBytes + ", "
                + "platform: '" + platform + "'.");
        }
        // Geef of null terug of het enige gevonden basis type.
        BasisTypeOverrulePaar basisTypeOverrulePaar = null;
        if (resultaatParen.size() == 1) {
            basisTypeOverrulePaar = resultaatParen.get(0);
        }
        return basisTypeOverrulePaar;
    }

    /** {@inheritDoc} */
    @Override
    public List<ObjectType> getDynamischeStamgegevensObjectTypen() {
        return getObjectTypenMetSoortInhoud('S');
    }

    /** {@inheritDoc} */
    @Override
    public List<ObjectType> getStatischeStamgegevensObjectTypen() {
        return getObjectTypenMetSoortInhoud('X');
    }

    /**
     * Haalt object typen op met een bepaald soort_inhoud kenmerk.
     *
     * @param soortInhoud De soort_inhoud van de op te halen object typen.
     * @return Lijst van object typen.
     */
    private List<ObjectType> getObjectTypenMetSoortInhoud(final Character soortInhoud) {
        final String sql = "SELECT ot FROM ObjectType ot WHERE ot.soortInhoud = :x "
            + "AND ot.elementByLaag.id = :laag";
        return em.createQuery(sql, ObjectType.class)
                 .setParameter("x", soortInhoud)
                 .setParameter("laag", BmrLaag.LOGISCH.getWaardeInBmr())
                 .getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public AttribuutType getAttribuutTypeVoorAttribuut(final Attribuut attr) {
        final String sql = "SELECT attrType FROM AttribuutType attrType WHERE attrType.id = :id";
        return em.createQuery(sql, AttribuutType.class).setParameter("id", attr.getType().getId()).getSingleResult();
    }

    /** {@inheritDoc} */
    @Override
    public <T extends GeneriekElement> T getElement(final Integer id, final Class<T> clazz) {
        final String sql = "SELECT element FROM " + clazz.getName() + " element WHERE element.id = :id";
        return em.createQuery(sql, clazz).setParameter("id", id).getSingleResult();
    }

    /** {@inheritDoc} */
    @Override
    public ObjectType getObjectTypeMetIdentCode(final String identCode) {
        final String sql = "SELECT ot FROM ObjectType ot WHERE ot.identCode = :identcode "
            + "AND ot.elementByLaag.id = :laag";
        return em.createQuery(sql, ObjectType.class)
                 .setParameter("identcode", identCode)
                 .setParameter("laag", BmrLaag.LOGISCH.getWaardeInBmr())
                 .getSingleResult();
    }

    /** {@inheritDoc} */
    @Override
    public List<Attribuut> getInverseAttributenVoorObjectType(final ObjectType objectType) {
        // Deze methode checkt meteen of er wel een inverse associatie ident code is.
        // Dat gaat zowel op NULL als lege string, omdat er ook lege strings in de DB voorkomen.
        final String sql = "SELECT at FROM Attribuut at "
            + "WHERE at.type = :ot "
            + "AND at.inverseAssociatieIdentCode IS NOT NULL "
            + "AND at.inverseAssociatieIdentCode != ''";
        return em.createQuery(sql, Attribuut.class).setParameter("ot", objectType).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<Attribuut> getXsdInverseAttributenVoorObjectType(final ObjectType objectType) {
        // Deze methode checkt meteen of er wel een XSD inverse associatie ident code is.
        // Dat gaat zowel op NULL als lege string, omdat er ook lege strings in de DB voorkomen.
        final String sql = "SELECT at FROM Attribuut at "
                + "WHERE at.type = :ot "
                + "AND at.xsdInverseAssociatieIdentCode IS NOT NULL "
                + "AND at.xsdInverseAssociatieIdentCode != ''";
        return em.createQuery(sql, Attribuut.class).setParameter("ot", objectType).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public ObjectType getOperationeelModelObjectTypeVoorGroep(final Groep groep) {
        try {

            final String sql = "SELECT ot FROM ObjectType ot WHERE ot.syncid = :syncid "
                + "AND ot.id <> :id";
            return em.createQuery(sql, ObjectType.class)
                     .setParameter("syncid", groep.getSyncid())
                     .setParameter("id", groep.getId())
                     .getSingleResult();
        } catch (final NoResultException nre) {
            return null;
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<Attribuut> getLogischeIdentiteitAttributenVoorObjectType(final ObjectType objectType) {
        final String sql = "SELECT attribuut FROM Attribuut attribuut, Regel regel, Regelattribuut regelAttribuut "
            + "WHERE regel.elementByOuder = :objectType "
            + "AND regel.soortRegel = 'LI' "
            + "AND regelAttribuut.elementByRegel = regel "
            + "AND regelAttribuut.elementByAttribuut.id = attribuut.id "
            + "AND (attribuut.inBericht IS NULL OR attribuut.inBericht = 'J') ";
        return em.createQuery(sql, Attribuut.class).setParameter("objectType", objectType).getResultList();
    }

    @Override
    public boolean isLogischIdentiteitAttribuut(final Attribuut attribuut) {
        final String sql = "SELECT regelAttribuut FROM Regelattribuut regelAttribuut, Regel regel "
                + "WHERE regelAttribuut.elementByRegel = regel "
                + "AND regelAttribuut.elementByAttribuut.id = :attribuutid "
                + "AND regel.soortRegel = 'LI'";
        final List<Regelattribuut> regelattributen = em.createQuery(sql, Regelattribuut.class)
                                                  .setParameter("attribuutid", attribuut.getId())
                                                  .setMaxResults(1)
                                                  .getResultList();
        return regelattributen.size() > 0;
    }

    /** {@inheritDoc} */
    @Override
    public ObjectType getDiscriminatorObjectType(final ObjectType objectType) {
        final String sql = "SELECT objectType FROM Attribuut attr, ObjectType objectType "
            + " WHERE attr.objectType = :ouder"
            + " AND attr.naam = :naam "
            + " AND attr.type.id = objectType.id";
        return em.createQuery(sql, ObjectType.class)
                 .setParameter("ouder", objectType)
                 .setParameter("naam", objectType.getDiscriminatorAttribuut())
                 .getSingleResult();
    }

    /** {@inheritDoc} */
    @Override
    public List<SoortActieSoortAdmhnd> getSoortActiesVoorSoortAdministratieveHandeling(final Tuple soortAdmHndl) {
        final String sql = "SELECT srtActieSrtAdmHnd FROM SoortActieSoortAdmhnd srtActieSrtAdmHnd "
            + "WHERE srtActieSrtAdmHnd.elementBySoortAdmhnd.id = :soortAdmHndId "
            + "ORDER BY srtActieSrtAdmHnd.volgnummer";
        return em.createQuery(sql, SoortActieSoortAdmhnd.class)
                 .setParameter("soortAdmHndId", soortAdmHndl.getId())
                 .getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<Attribuut> getAttributenVanObjectType(final ObjectType objectType) {
        final String sql = "SELECT attr FROM Attribuut attr WHERE attr.objectType = :ot "
            + "ORDER BY attr.volgnummer ASC";
        return em.createQuery(sql, Attribuut.class).setParameter("ot", objectType).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<GeneriekElement> getElementenVoorSyncId(final int syncid) {
        final String sql = "SELECT element FROM GeneriekElement element WHERE element.syncid = :syncid";
        return em.createQuery(sql, GeneriekElement.class).setParameter("syncid", syncid).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public <T extends GeneriekElement> T getElementVoorSyncIdVanLaag(
        final int syncid, final BmrLaag laag, final Class<T> type)
    {
        final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        final Root<T> elementRoot = criteriaQuery.from(type);
        criteriaQuery.select(elementRoot);
        criteriaQuery.where(
            criteriaBuilder.equal(elementRoot.get("syncid"), syncid),
            criteriaBuilder.equal(elementRoot.get("elementByLaag").get("id"), laag.getWaardeInBmr()));

        final List<T> results = em.createQuery(criteriaQuery).getResultList();

        T result = null;
        if (results.size() == 1) {
            result = results.get(0);
        } else if (results.size() > 1) {
            throw new IllegalStateException("Onverwacht meerdere results voor ophalen element.");
        }
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public List<Attribuut> getOperationeleLaagAttributenVoorLogischeLaagAttribuut(final Attribuut attribuut) {
        if (attribuut.getElementByLaag().getId() != BmrLaag.LOGISCH.getWaardeInBmr()) {
            throw new IllegalArgumentException("Alleen logische attributen toegestaan.");
        }

        final String jpql =
            "SELECT attr FROM Attribuut attr WHERE attr.orgSyncid = :syncid AND elementByLaag.id = :laag";
        return em.createQuery(jpql, Attribuut.class).setParameter("syncid", attribuut.getSyncid())
                 .setParameter("laag", BmrLaag.OPERATIONEEL.getWaardeInBmr()).getResultList();
    }

    @Override
    public Attribuut getLogischeLaagAttribuutVoorOperationeleLaagAttribuut(final Attribuut ogmAttribuut) {
        if (ogmAttribuut.getElementByLaag().getId() == BmrLaag.LOGISCH.getWaardeInBmr()) {
            throw new IllegalArgumentException("Alleen operationele attributen toegestaan.");
        }

        final String jpql =
            "SELECT attr FROM Attribuut attr WHERE attr.syncid = :syncid AND elementByLaag.id = :laag";
        return em.createQuery(jpql, Attribuut.class).setParameter("syncid", ogmAttribuut.getOrgSyncid())
                 .setParameter("laag", BmrLaag.LOGISCH.getWaardeInBmr()).getSingleResult();
    }

    @Override
    public List<VwElement> getVwElementen() {
        final String sql = "SELECT vwElement FROM VwElement vwElement ORDER BY vwElement.objecttype, vwElement.groep, vwElement.id";
        return em.createQuery(sql, VwElement.class).getResultList();
    }

    @Override
    public Map<Integer, VwElement> getVwElementenAlsMap() {
        final Map<Integer, VwElement> elementMap = new HashMap<>();
        final String sql = "SELECT vwElement FROM VwElement vwElement ORDER BY vwElement.id";
        final List<VwElement> resultList = em.createQuery(sql, VwElement.class).getResultList();
        for (final VwElement vwElement : resultList) {
            elementMap.put(vwElement.getId(), vwElement);
        }
        return elementMap;
    }

    @Override
    public List<VwElement> getVwElementenVanSoort(final String soort) {
        final String sql = "SELECT vwElement FROM VwElement vwElement WHERE vwElement.soort = :soort "
            + "ORDER BY vwElement.objecttype, vwElement.groep, vwElement.id";
        return em.createQuery(sql, VwElement.class).setParameter("soort", soort).getResultList();
    }

    @Override
    public VwElement getVwElementMetId(final int id) {
        final String sql = "SELECT vwElement FROM VwElement vwElement where vwElement.id = :id";
        return em.createQuery(sql, VwElement.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public Map<Integer, ObjectType> getObjectTypesAlsMap() {
        final Map<Integer, ObjectType> elementMap = new HashMap<>();
        final String sql = "SELECT ot FROM ObjectType ot WHERE ot.historieLaag IS NULL ORDER BY ot.syncid";
        final List<ObjectType> resultList = em.createQuery(sql, ObjectType.class).getResultList();
        for (final ObjectType objectType : resultList) {
            elementMap.put(objectType.getSyncid(), objectType);
        }
        return elementMap;
    }

    @Override
    public Map<Integer, Groep> getGroepenAlsMap() {
        final Map<Integer, Groep> elementMap = new HashMap<>();
        final String sql = "SELECT g FROM Groep g ORDER BY g.syncid";
        final List<Groep> resultList = em.createQuery(sql, Groep.class).getResultList();
        for (final Groep groep : resultList) {
            elementMap.put(groep.getSyncid(), groep);
        }
        return elementMap;
    }
}
