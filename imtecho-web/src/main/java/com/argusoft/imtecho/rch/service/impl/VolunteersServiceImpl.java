/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.imtecho.rch.service.impl;

import com.argusoft.imtecho.location.dao.HealthInfrastructureDetailsDao;
import com.argusoft.imtecho.rch.dao.VolunteersDao;
import com.argusoft.imtecho.rch.dto.VolunteersDto;
import com.argusoft.imtecho.rch.mapper.VolunteersMapper;
import com.argusoft.imtecho.rch.model.VolunteersMaster;
import com.argusoft.imtecho.rch.service.VolunteersService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * <p>
 *     Define services for volunteers.
 * </p>
 * @author smeet
 * @since 26/08/20 11:00 AM
 *
 */
@Service
@Transactional
public class VolunteersServiceImpl implements VolunteersService {

    @Autowired
    VolunteersDao volunteersDao;

    @Autowired
    HealthInfrastructureDetailsDao healthInfrastructureDetailsDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createOrUpdate(VolunteersDto volunteersDto) {
        VolunteersMaster volunteersMaster;
        if (volunteersDto.getId() != null) {
            volunteersMaster = volunteersDao.retrieveById(volunteersDto.getId());
            volunteersMaster.setNoOfVolunteers(volunteersDto.getNoOfVolunteers());
        } else {
            volunteersMaster = VolunteersMapper.convertDtoToEntity(volunteersDto);
        }
        volunteersDao.createOrUpdate(volunteersMaster);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolunteersDto retrieveData(Integer healthInfrastructureId, Date monthYear) {
        VolunteersMaster volunteersMaster = volunteersDao.retrieveData(healthInfrastructureId, monthYear);
        if (volunteersMaster != null) {
            return VolunteersMapper.convertEntityToDto(volunteersMaster);
        } else {
            return null;
        }
    }

}
