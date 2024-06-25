/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.imtecho.timer.dto;

import com.argusoft.imtecho.timer.model.TimerEvent;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * <p>
 *     Used for timer event.
 * </p>
 * @author Harshit
 * @since 26/08/20 11:00 AM
 *
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimerEventDto {

    private Integer id;
    private Integer refId;
    private TimerEvent.TYPE type;
    private boolean processed;
    private TimerEvent.STATUS status;
    private Date systemTriggerOn;
    private Date processedOn;
    private Integer userId;
    private Integer eventConfigId;
    private UUID eventConfigUUID;
    private String notificationConfigId;
    private List<LinkedHashMap<String, Object>> queryDataLists;

    public TimerEventDto(Integer refId, TimerEvent.TYPE type, Date systemTriggerOn, Integer eventConfigId, String notificationConfigId, List<LinkedHashMap<String, Object>> queryDataLists, UUID eventConfigUUID) {
        this.refId = refId;
        this.type = type;
        this.systemTriggerOn = systemTriggerOn;
        this.eventConfigId = eventConfigId;
        this.notificationConfigId = notificationConfigId;
        this.queryDataLists = queryDataLists;
        this.processed = false;
        this.status = TimerEvent.STATUS.NEW;
        this.eventConfigUUID = eventConfigUUID;
    }

}
