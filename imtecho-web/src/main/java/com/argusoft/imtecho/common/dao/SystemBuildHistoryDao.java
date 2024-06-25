package com.argusoft.imtecho.common.dao;

import com.argusoft.imtecho.common.model.SystemBuildHistory;
import com.argusoft.imtecho.database.common.GenericDao;

/**
 * <p>Defines database method for system build history</p>
 * @author smeet
 * @since 31/08/2020 10:30
 */
public interface SystemBuildHistoryDao extends GenericDao<SystemBuildHistory, Integer> {
    /**
     * Returns a instance of last build history
     * @return An instance of SystemBuildHistory
     */
    SystemBuildHistory retrieveLastSystemBuild();

}
