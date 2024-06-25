
package com.argusoft.imtecho.common.dao.impl;

import com.argusoft.imtecho.common.dao.UserDao;
import com.argusoft.imtecho.common.dto.UserMasterDto;

import com.argusoft.imtecho.common.model.RoleMaster;
import com.argusoft.imtecho.common.model.UserLocation;
import com.argusoft.imtecho.common.model.UserMaster;
import com.argusoft.imtecho.database.common.impl.GenericDaoImpl;
import com.argusoft.imtecho.exception.ImtechoUserException;
import com.argusoft.imtecho.location.model.LocationHierchyCloserDetail;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * Implements methods of UserDao
 * </p>
 *
 * @author vaishali
 * @since 31/08/2020 4:30
 */
@Slf4j
@Repository
@Transactional
public class UserDaoImpl extends GenericDaoImpl<UserMaster, Integer> implements UserDao {

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String USER_NAME = "userName";
    private static final String CONTACT_NUMBER = "contactNumber";

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMaster retrieveByUserName(String userName) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserMaster> cq = cb.createQuery(UserMaster.class);
        Root<UserMaster> root = cq.from(UserMaster.class);

        cq.select(root);
        cq.where(
                cb.and(
                        cb.equal(root.get(UserMaster.Fields.USER_NAME), userName),
                        cb.equal(root.get(UserMaster.Fields.STATE), UserMaster.State.ACTIVE)
                )
        );

        return session.createQuery(cq).uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMaster retrieveUserByUserNAme(String userName) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserMaster> cq = cb.createQuery(UserMaster.class);
        Root<UserMaster> root = cq.from(UserMaster.class);

        cq.select(root);
        cq.where(cb.equal(root.get(UserMaster.Fields.USER_NAME), userName));

        return session.createQuery(cq).uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMaster retrieveByLoginCode(String code) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserMaster> cq = cb.createQuery(UserMaster.class);
        Root<UserMaster> root = cq.from(UserMaster.class);

        cq.select(root);
        cq.where(
                cb.and(
                        cb.equal(root.get(UserMaster.Fields.LOGIN_CODE), code),
                        cb.equal(root.get(UserMaster.Fields.STATE), UserMaster.State.ACTIVE)
                )
        );

        return session.createQuery(cq).uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMaster retrieveById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserMaster> cq = cb.createQuery(UserMaster.class);
        Root<UserMaster> root = cq.from(UserMaster.class);

        cq.select(root);
        cq.where(cb.equal(root.get(UserMaster.Fields.ID), id));

        return session.createQuery(cq).uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserMaster> retrieveAll(Boolean isActive) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserMaster> cq = cb.createQuery(UserMaster.class);
        Root<UserMaster> root = cq.from(UserMaster.class);

        cq.select(root);

        if (Boolean.TRUE.equals(isActive)) {
            cq.where(cb.equal(root.get(UserMaster.Fields.STATE), UserMaster.State.ACTIVE));
        } else {
            cq.where(cb.notEqual(root.get(UserMaster.Fields.STATE), UserMaster.State.ACTIVE));
        }
        return session.createQuery(cq).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserMaster> getUsersByIds(Set<Integer> userIds) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserMaster> cq = cb.createQuery(UserMaster.class);
        Root<UserMaster> root = cq.from(UserMaster.class);

        cq.select(root);
        cq.where(root.get(UserMaster.Fields.ID).in(userIds));

        return session.createQuery(cq).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserMaster> getUsersByLocationsAndRoles(List<Integer> locationIds, List<Integer> roleIds) {
        Session currentSession = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = currentSession.getCriteriaBuilder();
        CriteriaQuery<UserMaster> cq = cb.createQuery(UserMaster.class);
        Root<UserMaster> root = cq.from(UserMaster.class);

        Subquery<UserLocation> userIdSubQuery = cq.subquery(UserLocation.class);
        Root<UserLocation> rootUserId = userIdSubQuery.from(UserLocation.class);

        Subquery<LocationHierchyCloserDetail> parentIdSubQuery = userIdSubQuery.subquery(LocationHierchyCloserDetail.class);
        Root<LocationHierchyCloserDetail> rootParentId = parentIdSubQuery.from(LocationHierchyCloserDetail.class);
        parentIdSubQuery
                .select(rootParentId.get(LocationHierchyCloserDetail.Fields.PARENT_ID))
                .where(rootParentId.get(LocationHierchyCloserDetail.Fields.CHILD_ID).in(locationIds));

        Subquery<LocationHierchyCloserDetail> childIdSubQuery = userIdSubQuery.subquery(LocationHierchyCloserDetail.class);
        Root<LocationHierchyCloserDetail> rootChildId = childIdSubQuery.from(LocationHierchyCloserDetail.class);
        childIdSubQuery
                .select(rootChildId.get(LocationHierchyCloserDetail.Fields.CHILD_ID))
                .where(rootChildId.get(LocationHierchyCloserDetail.Fields.PARENT_ID).in(locationIds));

        userIdSubQuery.select(rootUserId.get(UserLocation.Fields.USER_ID))
                .where(
                        cb.or(
                                rootUserId.get(UserLocation.Fields.LOCATION_ID).in(parentIdSubQuery),
                                rootUserId.get(UserLocation.Fields.LOCATION_ID).in(childIdSubQuery)
                        )
                );

        cq.select(root)
                .where(
                        cb.and(
                                root.get(UserMaster.Fields.ROLE_ID).in(roleIds),
                                root.get(UserMaster.Fields.ID).in(userIdSubQuery)
                        )
                );

        return currentSession.createQuery(cq).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserMaster> getUsersByLocationsAndRolesUsingParentLocation(List<Integer> locationIds, List<Integer> roleIds) {
        Session currentSession = sessionFactory.getCurrentSession();

        CriteriaBuilder cb = currentSession.getCriteriaBuilder();
        CriteriaQuery<UserMaster> cq = cb.createQuery(UserMaster.class);
        Root<UserMaster> root = cq.from(UserMaster.class);

        Subquery<UserLocation> userIdSubQuery = cq.subquery(UserLocation.class);
        Root<UserLocation> rootUserId = userIdSubQuery.from(UserLocation.class);

        Subquery<LocationHierchyCloserDetail> childIdSubQuery = userIdSubQuery.subquery(LocationHierchyCloserDetail.class);
        Root<LocationHierchyCloserDetail> rootChildId = childIdSubQuery.from(LocationHierchyCloserDetail.class);
        childIdSubQuery
                .select(rootChildId.get(LocationHierchyCloserDetail.Fields.CHILD_ID))
                .where(rootChildId.get(LocationHierchyCloserDetail.Fields.PARENT_ID).in(locationIds));

        userIdSubQuery.select(rootUserId.get(UserLocation.Fields.USER_ID))
                .where(
                        cb.and(
                                cb.equal(rootUserId.get(UserLocation.Fields.STATE), UserLocation.State.ACTIVE),
                                rootUserId.get(UserLocation.Fields.LOCATION_ID).in(childIdSubQuery)
                        )
                );

        cq.select(root)
                .where(
                        cb.and(
                                root.get(UserMaster.Fields.ROLE_ID).in(roleIds),
                                root.get(UserMaster.Fields.ID).in(userIdSubQuery)
                        )
                );

        return currentSession.createQuery(cq).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserMasterDto> retrieveByCriteria(Integer userId, List<Integer> roles, Integer locationId, String searchString, String orderBy, Integer limit, Integer offset, String order,String status) {
        if (roles != null && !roles.isEmpty()) {
            if (orderBy == null) {
                orderBy = FIRST_NAME;
            }
            if (order == null) {
                order = "asc";
            }

            String query = "with user_list as(select usr.id as id,usr.title as title,usr.first_name as firstName,usr.last_name as lastName "
                    + ",usr.user_name as userName,usr.contact_number as contactNumber,usr.imei_number as imeiNumber,usr_role.name as roleName,usr.login_code as loginCode, "
                    + "usr.state as displayState,string_agg(loc_name,'<br>') as areaOfIntervention "
                    + "from (select usr.id,u_loc.loc_id,string_agg(lm.english_name,' >' order by lh.depth desc) as loc_name "
                    + "from (select distinct usr.id from um_user usr,um_user_location u_loc,location_hierchy_closer_det lcloser "
                    + "where ('" + roles + "' <> 'null' and usr.role_id IN (:roles)) ";

            if(status.equals("ACTIVE")){
                query += "and ( usr.state = 'ACTIVE')" ;
            } else if (status.equals("INACTIVE")){
                query += "and ( usr.state ='INACTIVE')" ;
            }

            query+= "and ('" + searchString + "' = 'null' or usr.search_text ilike '%" + searchString + "%') "

                    + "and usr.id = u_loc.user_id and u_loc.state = 'ACTIVE' "
                    + "and ((" + locationId + " is not null and lcloser.parent_id = " + locationId + ") "
                    + "or (" + locationId + " is null and lcloser.parent_id in (select loc_id from um_user_location where user_id = " + userId + " and state = 'ACTIVE')) "
                    + "or (" + locationId + " is null and " + userId + " is null)) "
                    + " and lcloser.child_id = u_loc.loc_id "
                    + "limit " + limit + " offset " + offset + ""
                    + ") as usr,um_user_location u_loc,location_hierchy_closer_det lh,location_master lm "
                    + "where usr.id = u_loc.user_id and u_loc.state = 'ACTIVE' and u_loc.loc_id = lh.child_id and lh.parent_id = lm.id "
                    + "group by usr.id,u_loc.loc_id) as user_loc_det,um_user usr,um_role_master usr_role "
                    + "where user_loc_det.id = usr.id and usr.role_id = usr_role.id "
                    + "group by usr.id,usr.title,usr.first_name,usr.last_name,usr.user_name,usr.contact_number,usr_role.name,usr.state,usr.login_code "
                    + "order by " + orderBy + " " + order + ")"
                    + "select usr.*,concat_ws(' ',usr.firstname,usr.lastname) as fullName, string_agg(hd.name, ',<br>') as healthFacilityName from user_list usr "
                    + "left join user_health_infrastructure uhi on uhi.user_id = usr.id and uhi.state ='ACTIVE' "
                    + "left join health_infrastructure_details hd on hd.id = uhi.health_infrastrucutre_id "
                    + "group by usr.id,usr.title, usr.firstname, usr.lastName, usr.userName,usr.contactNumber,"
                    + "usr.roleName,usr.displayState, usr.loginCode, usr.imeinumber, usr.areaofintervention "
                    + "order by " + orderBy + " " + order;

            Session session = sessionFactory.getCurrentSession();
            NativeQuery<UserMasterDto> q = session.createNativeQuery(query);
            q.setParameterList("roles", roles);

            return q.addScalar("id", StandardBasicTypes.INTEGER)
                    .addScalar("title", StandardBasicTypes.STRING)
                    .addScalar(FIRST_NAME, StandardBasicTypes.STRING)
                    .addScalar(LAST_NAME, StandardBasicTypes.STRING)
                    .addScalar(USER_NAME, StandardBasicTypes.STRING)
                    .addScalar(CONTACT_NUMBER, StandardBasicTypes.STRING)
                    .addScalar("imeiNumber", StandardBasicTypes.STRING)
                    .addScalar("roleName", StandardBasicTypes.STRING)
                    .addScalar("displayState", StandardBasicTypes.STRING)
                    .addScalar("loginCode", StandardBasicTypes.STRING)
                    .addScalar("areaOfIntervention", StandardBasicTypes.TEXT)
                    .addScalar("healthFacilityName", StandardBasicTypes.STRING)
                    .setResultTransformer(Transformers.aliasToBean(UserMasterDto.class)).list();

        } else {
            return Collections.emptyList();
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserMaster> retrieveByPhone(String contactNumber, Integer roleId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserMaster> cq = cb.createQuery(UserMaster.class);
        Root<UserMaster> root = cq.from(UserMaster.class);

        cq.select(root);
        if (roleId != null) {
            cq.where(
                    cb.and(
                            cb.equal(root.get(UserMaster.Fields.CONTACT_NUMBER), contactNumber),
                            cb.equal(root.get(UserMaster.Fields.STATE), UserMaster.State.ACTIVE),
                            cb.equal(root.get(UserMaster.Fields.ROLE_ID), roleId)
                    )
            );
        } else {
            cq.where(
                    cb.and(
                            cb.equal(root.get(UserMaster.Fields.CONTACT_NUMBER), contactNumber),
                            cb.equal(root.get(UserMaster.Fields.STATE), UserMaster.State.ACTIVE)
                    )
            );
        }

        return session.createQuery(cq).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserMasterDto> retrieveUsersByRoleAndLocation(Integer roleId, Integer locationId, Integer userId) {

        String query = "SELECT usr.id, usr.first_name as firstName, usr.last_name as lastName, role.max_position as maxPositions,usr.user_name as userName "
                + "FROM um_user  usr "
                + "LEFT JOIN um_role_master role on usr.role_id = role.id "
                + "LEFT JOIN um_user_location loc on usr.id = loc.user_id  "
                + "WHERE usr.role_id = (:roleId) and loc.loc_id = (:locId) "
                + "and usr.state = '" + UserMaster.State.ACTIVE + "' and role.state = '" + RoleMaster.State.ACTIVE + "'"
                + " and loc.state = '" + UserMaster.State.ACTIVE + "' ";
        if (userId != null) {
            query += " and usr.id != " + userId;
        }

        Session session = sessionFactory.getCurrentSession();
        NativeQuery<UserMasterDto> q = session.createNativeQuery(query);
        q.setParameter("roleId", roleId);
        q.setParameter("locId", locationId);

        return q
                .addScalar("id", StandardBasicTypes.INTEGER)
                .addScalar(FIRST_NAME, StandardBasicTypes.STRING)
                .addScalar(LAST_NAME, StandardBasicTypes.STRING)
                .addScalar(USER_NAME, StandardBasicTypes.STRING)
                .addScalar("maxPositions", StandardBasicTypes.INTEGER)
                .setResultTransformer(Transformers.aliasToBean(UserMasterDto.class)).list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserMasterDto> getAllActiveUsers() {
        String query = "select id,concat(first_name,' ',last_name) as \"fullName\",user_name as \"userName\""
                + " from um_user where state = 'ACTIVE' order by 2";
        Session session = sessionFactory.getCurrentSession();
        NativeQuery<UserMasterDto> q = session.createNativeQuery(query);
        return q
                .addScalar("id", StandardBasicTypes.INTEGER)
                .addScalar("fullName", StandardBasicTypes.STRING)
                .addScalar(USER_NAME, StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(UserMasterDto.class)).list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean validateUserName(String userName, Integer userId) {
        String query = "select id from um_user where user_name = :userName";
        Session session = sessionFactory.getCurrentSession();
        NativeQuery<Integer> q = session.createNativeQuery(query);
        q.setParameter(USER_NAME, userName);
        List<Integer> result = q.addScalar("id", StandardBasicTypes.INTEGER).list();
        if (userId != null && result.contains(userId)) {
            return true;
        }
        return CollectionUtils.isEmpty(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserMasterDto> conflictUserOfContactNumber(String contactNumber, Integer userId) {
        String query = "select id,first_name as \"firstName\", last_name \"lastName\", user_name \"userName\" from um_user where contact_number = :contactNumber and id != :userId and state = 'ACTIVE'";

        Session session = sessionFactory.getCurrentSession();
        NativeQuery<UserMasterDto> q = session.createNativeQuery(query);
        q.setParameter(CONTACT_NUMBER, contactNumber);
        q.setParameter("userId", userId);

        List<UserMasterDto> result = q
                .addScalar("id", StandardBasicTypes.INTEGER)
                .addScalar(FIRST_NAME, StandardBasicTypes.STRING)
                .addScalar(LAST_NAME, StandardBasicTypes.STRING)
                .addScalar(USER_NAME, StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(UserMasterDto.class)).list();
        if (result.isEmpty()) {
            return new ArrayList<>();
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserMasterDto> conflictUserOfLocation(Integer userId) {
        String query = "select distinct us.id as \"id\", us.user_name as \"userName\", us.first_name as \"firstName\", us.last_name as \"lastName\", role.max_position as \"maxPositions\" "
                + "from um_user_location ul "
                + "inner join um_user us on ul.user_id = us.id "
                + "inner join um_role_master role on us.role_id = role.id "
                + "where ul.state = 'ACTIVE' AND us.state = 'ACTIVE' AND role.state = 'ACTIVE' "
                + "and us.id != :userId and us.role_id = (select role_id from um_user where id = :userId)"
                + "and ul.loc_id in (select loc_id from um_user_location WHERE user_id = (:userId) and state = 'ACTIVE')";

        Session session = sessionFactory.getCurrentSession();
        NativeQuery<UserMasterDto> q = session.createNativeQuery(query);
        q.setParameter("userId", userId);

        List<UserMasterDto> result = q
                .addScalar("id", StandardBasicTypes.INTEGER)
                .addScalar(USER_NAME, StandardBasicTypes.STRING)
                .addScalar(FIRST_NAME, StandardBasicTypes.STRING)
                .addScalar(LAST_NAME, StandardBasicTypes.STRING)
                .addScalar("maxPositions", StandardBasicTypes.INTEGER)
                .setResultTransformer(Transformers.aliasToBean(UserMasterDto.class)).list();
        if (result.isEmpty()) {
            return new ArrayList<>();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserMaster retrieveUserByRoleCodeNLocation(Integer locationId, String roleCode) {
        String sql = "select first_name as firstName,middle_name as middleName , last_name as lastName , \n"
                + "contact_number as contactNumber\n"
                + "from um_user,um_user_location l \n"
                + "where role_id = (select id from um_role_master where code= :roleCode) \n"
                + "and l.state='ACTIVE'\n"
                + "and um_user.id = l.user_id\n"
                + "and um_user.state = 'ACTIVE'\n"
                + "and loc_id= :locationId\n";

        return (UserMaster) getCurrentSession().createNativeQuery(sql)
                .addScalar(FIRST_NAME, StandardBasicTypes.STRING)
                .addScalar("middleName", StandardBasicTypes.STRING)
                .addScalar(CONTACT_NUMBER, StandardBasicTypes.STRING)
                .addScalar(LAST_NAME, StandardBasicTypes.STRING)
                .setParameter("locationId", locationId)
                .setParameter("roleCode", roleCode)
                .setResultTransformer(Transformers.aliasToBean(UserMaster.class)).uniqueResult();

    }

    @Override
    public List<UserMaster> retrieveEmergencyRespondersForChardhamByRoleAndDiscardedUsers(Integer roleId, List<Integer> discardedUsers) {
        if (roleId == null) {
            return null;
        }
        return super.findByCriteriaList((root, criteriaBuilder, criteriaQuery) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("roleId"), roleId));
            predicates.add(criteriaBuilder.equal(root.get("state"), UserMaster.State.ACTIVE));
            if (!discardedUsers.isEmpty()) {
                predicates.add(criteriaBuilder.not(criteriaBuilder.in(root.get("id")).value(discardedUsers)));
            }
            predicates.add(criteriaBuilder.isNotNull(root.get("latitude")));
            predicates.add(criteriaBuilder.isNotNull(root.get("longitude")));
            return predicates;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserMaster> retrieveByAadharReferenceKey(UUID aadharReferenceKey) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserMaster> cq = cb.createQuery(UserMaster.class);
        Root<UserMaster> root = cq.from(UserMaster.class);

        cq.select(root)
                .where(
                        cb.and(
                                cb.equal(root.get(UserMaster.Fields.AADHAAR_REFERENCE_KEY), aadharReferenceKey),
                                cb.equal(root.get(UserMaster.Fields.STATE), UserMaster.State.ACTIVE)
                        )
                );

        return session.createQuery(cq).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserMaster> retrieveByEncryptedAadhar(String encryptedAadharNumber) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UserMaster> cq = cb.createQuery(UserMaster.class);
        Root<UserMaster> root = cq.from(UserMaster.class);

        cq.select(root);
        cq.where(cb.equal(root.get(UserMaster.Fields.AADHAAR_NUMBER_ENCRYPTED), encryptedAadharNumber));

        return session.createQuery(cq).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePreferredLanguageForInactiveLanguage(String inactiveLanguage, String preferredLanguage) {
        String query = "update um_user set prefered_language =:preferredLanguage, modified_on = now() \n" +
                "where prefered_language =:inactiveLanguage ";
        SQLQuery q = getCurrentSession().createSQLQuery(query);
        q.setParameter("preferredLanguage", preferredLanguage);
        q.setParameter("inactiveLanguage", inactiveLanguage);
        q.executeUpdate();
    }

    @Override
    public List<UserMasterDto> retrieveUsers(Boolean byUsername, Boolean byContactNumber, Boolean byName, Boolean byLocation, Integer locationId, String searchString, Integer roleId, Integer limit, Integer offSet) {
        if (searchString != null) {
            searchString = searchString.replaceAll("'", "''");
        }
        String query = """
                select
                    uu.id,
                    uu.user_name as "userName",
                    concat(uu.first_name,' ',uu.middle_name,' ',uu.last_name) as fullName,
                    uu.contact_number as "contactNumber",
                    string_agg(get_location_hierarchy(uul.loc_id),'<br>') as "assignedLocations"
                from um_user uu
                inner join um_user_location uul on uu.id = uul.user_id and uu.state = 'ACTIVE' and uul.state = 'ACTIVE'""";

        if(roleId != null){
            query = query + " and uu.role_id = " + roleId;
        }

        String endConditions = " group by 1,2,3,4\n"
                + "order by 2\n"
                + "limit " + limit + " offset " + offSet + ";";

        if(Boolean.TRUE.equals(byUsername)){
            query = query
                    + " where uu.user_name = '" + searchString + "'"
                    + endConditions;
        } else if(Boolean.TRUE.equals(byContactNumber)){
            query = query
                    + " where uu.contact_number = '" + searchString + "'"
                    + endConditions;
        } else if(Boolean.TRUE.equals(byName)){
            searchString = Arrays.stream(searchString.split(" ")).map(e -> "%" + e + "%").collect(Collectors.joining());
            query = """
                    with locations as (
                        select child_id as location_id
                        from location_hierchy_closer_det
                        where parent_id = %d
                    )""".formatted(locationId)
                    + query
                    + " where concat(uu.first_name,' ',uu.middle_name,' ',uu.last_name) ilike '" + searchString + "'"
                    + endConditions;
        } else if(Boolean.TRUE.equals(byLocation)){
            query = """
                    with locations as (
                        select child_id as location_id
                        from location_hierchy_closer_det
                        where parent_id = %d
                    )""".formatted(locationId)
                    + query
                    + " inner join locations on uul.loc_id = locations.location_id"
                    + endConditions;
        }

        try {
            Session session = sessionFactory.getCurrentSession();
            NativeQuery<UserMasterDto> q = session.createNativeQuery(query);
            List<UserMasterDto> userEntities = q
                    .addScalar("id",StandardBasicTypes.INTEGER)
                    .addScalar("userName",StandardBasicTypes.STRING)
                    .addScalar("fullName",StandardBasicTypes.STRING)
                    .addScalar("contactNumber",StandardBasicTypes.STRING)
                    .addScalar("assignedLocations",StandardBasicTypes.STRING)
                    .setResultTransformer(Transformers.aliasToBean(UserMasterDto.class)).list();
            if(CollectionUtils.isEmpty(userEntities)){
                return Collections.emptyList();
            }
            return userEntities;
        } catch(Exception e){
            log.error(e.toString());
            throw new ImtechoUserException("Please enter valid value",101);
        }
    }
}