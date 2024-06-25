/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.imtecho.fcm.dao;

import com.argusoft.imtecho.database.common.GenericDao;
import com.argusoft.imtecho.fcm.model.TechoPushNotificationLocationDetail;

import java.util.List;

/**
 * @author nihar
 */
public interface TechoPushNotificationLocationDetailDao extends
        GenericDao<TechoPushNotificationLocationDetail, Integer> {

    List<TechoPushNotificationLocationDetail> findByNotificationConfigId(Integer id);

    void deleteByConfigId(Integer id);
}
