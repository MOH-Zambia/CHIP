package com.argusoft.imtecho.systemconstraint.dao.impl;

import com.argusoft.imtecho.database.common.impl.GenericDaoImpl;
import com.argusoft.imtecho.systemconstraint.dao.SystemConstraintFieldValueMasterDao;
import com.argusoft.imtecho.systemconstraint.model.SystemConstraintFieldValueMaster;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class SystemConstraintFieldValueMasterDaoImpl extends GenericDaoImpl<SystemConstraintFieldValueMaster, UUID> implements SystemConstraintFieldValueMasterDao {

    @Override
    public void deleteSystemConstraintFieldValueConfigsByUuids(List<UUID> fieldValueUuidsToBeRemoved) {
        if (CollectionUtils.isEmpty(fieldValueUuidsToBeRemoved)) return;

        String commaSeparateUuids = fieldValueUuidsToBeRemoved.stream().map(String::valueOf).collect(Collectors.joining("', '"));
        String query = "DELETE FROM system_constraint_field_value_master WHERE cast(uuid as text) in ( '" + commaSeparateUuids + "' ) ;";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query).executeUpdate();
    }
}