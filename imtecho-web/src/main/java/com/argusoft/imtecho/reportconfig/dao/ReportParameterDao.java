/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.imtecho.reportconfig.dao;

import com.argusoft.imtecho.database.common.GenericDao;
import com.argusoft.imtecho.reportconfig.model.ReportParameterMaster;

/**
 *
 * <p>
 * Define methods for report parameter.
 * </p>
 *
 * @author vaishali
 * @since 26/08/20 10:19 AM
 */
public interface ReportParameterDao {
    /**
     *
     * <p>
     * Define methods for report parameter master.
     * </p>
     *
     * @author vaishali
     * @since 26/08/20 10:19 AM
     */
    interface ReportParameterMasterDao extends GenericDao<ReportParameterMaster, Integer> {
    }
}
