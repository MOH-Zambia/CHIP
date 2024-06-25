/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.imtecho.location.service;

import com.argusoft.imtecho.location.dto.HealthInfrastructureWardDetailsPayloadDto;
import com.argusoft.imtecho.location.dto.LocationDetailDto;
import com.argusoft.imtecho.location.dto.LocationHierchyDto;
import com.argusoft.imtecho.location.dto.LocationMasterDto;
import com.argusoft.imtecho.location.model.LocationMaster;
import com.argusoft.imtecho.location.model.LocationWardsMapping;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * <p>
 *     Define services for location.
 * </p>
 * @author Harshit
 * @since 26/08/20 11:00 AM
 *
 */
public interface LocationService {

    /**
     * Retrieves location hierarchy details by defined criteria like user id, location id, level, language.
     * @param userId User id.
     * @param locationIds Location id.
     * @param level Location level.
     * @param fetchAccordingToUserAOI Fetch according to user id or not.
     * @param languagePreference Preferred language.
     * @return Returns list of location hierarchy details.
     */
    List<LocationHierchyDto> retrieveLocationHierarchyDetailByCriteria(Integer userId, List<Integer> locationIds, Integer level, Boolean fetchAccordingToUserAOI, String languagePreference);

    /**
     * Retrieves location by list of location ids.
     * @param locationIds Set of location ids.
     * @return Returns list of location master details by list of location ids.
     */
    List<LocationDetailDto> getLocationsByIds(Set<Integer> locationIds);

    /**
     * Add/Update location details.
     * @param locationMasterDto Entity of location master.
     * @param userId User id.
     */
    void createOrUpdate(LocationMasterDto locationMasterDto, Integer userId);

    /**
     * Retrieves child location details by parent location id.
     * @param locationId Parent location id.
     * @return Returns list of child location by given location id.
     */
    List<LocationMasterDto> retrieveChildLocationsByLocationId(Integer locationId);

    /**
     * Retrieves location details by location id.
     * @param id Id of location.
     * @return Returns location details of given location id.
     */
    LocationMasterDto retrieveById(Integer id);

    /**
     * Update all active location with worker info.
     */
    void updateAllActiveLocationsWithWorkerInfo();

    /**
     * Retrieves location hierarchy.
     * @param locationId Location id.
     * @return Returns location details which contains hierarchy.
     */
    LocationMasterDto retrieveLocationHierarchyById(Integer locationId);

    /**
     * Retrieves location hierarchy in english.
     * @param locationId Location id.
     * @return Returns location details which contains hierarchy in english.
     */
    LocationMasterDto retrieveEngLocationHierarchyById(Integer locationId);

    /**
     * Retrieves list of active location master details by list of location levels.
     * @param locationLevel List of location levels.
     * @param isActive Is location active or not.
     * @return Returns list of active location master details.
     */
    List<LocationMaster> getLocationsByLocationType(List<String> locationLevel, Boolean isActive);


    /**
     * Retrieves all location for gujarat.
     * @return Returns list of all locations.
     */
    List<LocationMaster> getAllLocationsOfGujarat();

    /**
     * Retrieves all location for State.
     * @return Returns list of all locations.
     */
    List<LocationMaster> getAllLocationsOfState();

    /**
     * Retrieves parent location details by child location.
     * @param locationId Child location id.
     * @param languagePreference Preferred language.
     * @return Returns parent location details.
     */
    LocationDetailDto retrieveParentLocationDetail(Integer locationId, String languagePreference);

    /**
     * Create ward UPHC mapping.
     * @param locationWardsMappings Details of ward UPHC mapping.
     */
    void createWardUphcMapping(List<LocationWardsMapping> locationWardsMappings);

    /**
     * Update ward UPHC mapping.
     * @param wardId ward id.
     * @param locationWardsMappings Details of ward UPHC mapping.
     */
    void updateWardUphcMapping(Integer wardId, List<LocationWardsMapping> locationWardsMappings);

    /**
     * Add/Update health infrastructure ward details.
     * @param healthInfrastructureWardDetailsPayloadDto Details of health infrastructure ward.
     */
    void createOrUpdateHospitalWardDetails(HealthInfrastructureWardDetailsPayloadDto healthInfrastructureWardDetailsPayloadDto);
}
