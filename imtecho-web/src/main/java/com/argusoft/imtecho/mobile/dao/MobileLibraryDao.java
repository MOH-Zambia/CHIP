package com.argusoft.imtecho.mobile.dao;

import com.argusoft.imtecho.database.common.GenericDao;
import com.argusoft.imtecho.mobile.model.MobileLibraryMaster;
import java.util.Date;
import java.util.List;

/**
 *
 * @author prateek on 13 Feb, 2019
 */
public interface MobileLibraryDao extends GenericDao<MobileLibraryMaster, Integer> {

    List<MobileLibraryMaster> retrieveMobileLibraryMastersByLastUpdateDate(Integer roleId, Date lastUpdateDate);

    int updateStateStatus(String fileName,String state);

}
