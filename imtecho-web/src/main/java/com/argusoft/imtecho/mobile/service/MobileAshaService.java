/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.imtecho.mobile.service;

import com.argusoft.imtecho.mobile.dto.LogInRequestParamDetailDto;
import com.argusoft.imtecho.mobile.dto.LoggedInUserPrincipleDto;

import java.util.concurrent.ExecutionException;

/**
 *
 * @author kunjan
 */
public interface MobileAshaService {
    
    LoggedInUserPrincipleDto getDataForAsha(LogInRequestParamDetailDto paramDetailDto, Integer apkVersion) throws ExecutionException, InterruptedException;
    
}
