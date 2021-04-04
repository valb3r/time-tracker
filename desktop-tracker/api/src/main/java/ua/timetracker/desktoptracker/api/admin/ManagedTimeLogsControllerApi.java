/*
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package ua.timetracker.desktoptracker.api.admin;

import ua.timetracker.desktoptracker.api.admin.invoker.ApiCallback;
import ua.timetracker.desktoptracker.api.admin.invoker.ApiClient;
import ua.timetracker.desktoptracker.api.admin.invoker.ApiException;
import ua.timetracker.desktoptracker.api.admin.invoker.ApiResponse;
import ua.timetracker.desktoptracker.api.admin.invoker.Configuration;
import ua.timetracker.desktoptracker.api.admin.invoker.Pair;
import ua.timetracker.desktoptracker.api.admin.invoker.ProgressRequestBody;
import ua.timetracker.desktoptracker.api.admin.invoker.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import java.time.LocalDateTime;
import java.util.Set;
import ua.timetracker.desktoptracker.api.admin.model.TimeLogDto;
import ua.timetracker.desktoptracker.api.admin.model.TimeLogImageDto;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagedTimeLogsControllerApi {
    private ApiClient localVarApiClient;

    public ManagedTimeLogsControllerApi() {
        this(Configuration.getDefaultApiClient());
    }

    public ManagedTimeLogsControllerApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for downloadTimecardImage
     * @param path  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call downloadTimecardImageCall(String path, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/resources/managed/timelogs/cards/images/{path}"
            .replaceAll("\\{" + "path" + "\\}", localVarApiClient.escapeString(path.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call downloadTimecardImageValidateBeforeCall(String path, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'path' is set
        if (path == null) {
            throw new ApiException("Missing the required parameter 'path' when calling downloadTimecardImage(Async)");
        }
        

        okhttp3.Call localVarCall = downloadTimecardImageCall(path, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param path  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public void downloadTimecardImage(String path) throws ApiException {
        downloadTimecardImageWithHttpInfo(path);
    }

    /**
     * 
     * 
     * @param path  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> downloadTimecardImageWithHttpInfo(String path) throws ApiException {
        okhttp3.Call localVarCall = downloadTimecardImageValidateBeforeCall(path, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     *  (asynchronously)
     * 
     * @param path  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call downloadTimecardImageAsync(String path, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = downloadTimecardImageValidateBeforeCall(path, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for timeCardImagesForTimeLogs
     * @param projectIds  (required)
     * @param timeLogIds  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call timeCardImagesForTimeLogsCall(Set<Long> projectIds, Set<Long> timeLogIds, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/resources/managed/timelogs/projects/{project_ids}/cards/{time_log_ids}"
            .replaceAll("\\{" + "project_ids" + "\\}", localVarApiClient.escapeString(localVarApiClient.collectionPathParameterToString("csv", projectIds)))
            .replaceAll("\\{" + "time_log_ids" + "\\}", localVarApiClient.escapeString(localVarApiClient.collectionPathParameterToString("csv", timeLogIds)));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "*/*"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call timeCardImagesForTimeLogsValidateBeforeCall(Set<Long> projectIds, Set<Long> timeLogIds, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectIds' is set
        if (projectIds == null) {
            throw new ApiException("Missing the required parameter 'projectIds' when calling timeCardImagesForTimeLogs(Async)");
        }
        
        // verify the required parameter 'timeLogIds' is set
        if (timeLogIds == null) {
            throw new ApiException("Missing the required parameter 'timeLogIds' when calling timeCardImagesForTimeLogs(Async)");
        }
        

        okhttp3.Call localVarCall = timeCardImagesForTimeLogsCall(projectIds, timeLogIds, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param projectIds  (required)
     * @param timeLogIds  (required)
     * @return List&lt;TimeLogImageDto&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public List<TimeLogImageDto> timeCardImagesForTimeLogs(Set<Long> projectIds, Set<Long> timeLogIds) throws ApiException {
        ApiResponse<List<TimeLogImageDto>> localVarResp = timeCardImagesForTimeLogsWithHttpInfo(projectIds, timeLogIds);
        return localVarResp.getData();
    }

    /**
     * 
     * 
     * @param projectIds  (required)
     * @param timeLogIds  (required)
     * @return ApiResponse&lt;List&lt;TimeLogImageDto&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<TimeLogImageDto>> timeCardImagesForTimeLogsWithHttpInfo(Set<Long> projectIds, Set<Long> timeLogIds) throws ApiException {
        okhttp3.Call localVarCall = timeCardImagesForTimeLogsValidateBeforeCall(projectIds, timeLogIds, null);
        Type localVarReturnType = new TypeToken<List<TimeLogImageDto>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * 
     * @param projectIds  (required)
     * @param timeLogIds  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call timeCardImagesForTimeLogsAsync(Set<Long> projectIds, Set<Long> timeLogIds, final ApiCallback<List<TimeLogImageDto>> _callback) throws ApiException {

        okhttp3.Call localVarCall = timeCardImagesForTimeLogsValidateBeforeCall(projectIds, timeLogIds, _callback);
        Type localVarReturnType = new TypeToken<List<TimeLogImageDto>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for timeCardsForManagedProjects
     * @param projectIds  (required)
     * @param from  (optional)
     * @param to  (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call timeCardsForManagedProjectsCall(Set<Long> projectIds, LocalDateTime from, LocalDateTime to, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/resources/managed/timelogs/projects/{project_ids}"
            .replaceAll("\\{" + "project_ids" + "\\}", localVarApiClient.escapeString(localVarApiClient.collectionPathParameterToString("csv", projectIds)));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (from != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("from", from));
        }

        if (to != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("to", to));
        }

        final String[] localVarAccepts = {
            "*/*"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call timeCardsForManagedProjectsValidateBeforeCall(Set<Long> projectIds, LocalDateTime from, LocalDateTime to, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'projectIds' is set
        if (projectIds == null) {
            throw new ApiException("Missing the required parameter 'projectIds' when calling timeCardsForManagedProjects(Async)");
        }
        

        okhttp3.Call localVarCall = timeCardsForManagedProjectsCall(projectIds, from, to, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param projectIds  (required)
     * @param from  (optional)
     * @param to  (optional)
     * @return List&lt;TimeLogDto&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public List<TimeLogDto> timeCardsForManagedProjects(Set<Long> projectIds, LocalDateTime from, LocalDateTime to) throws ApiException {
        ApiResponse<List<TimeLogDto>> localVarResp = timeCardsForManagedProjectsWithHttpInfo(projectIds, from, to);
        return localVarResp.getData();
    }

    /**
     * 
     * 
     * @param projectIds  (required)
     * @param from  (optional)
     * @param to  (optional)
     * @return ApiResponse&lt;List&lt;TimeLogDto&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<TimeLogDto>> timeCardsForManagedProjectsWithHttpInfo(Set<Long> projectIds, LocalDateTime from, LocalDateTime to) throws ApiException {
        okhttp3.Call localVarCall = timeCardsForManagedProjectsValidateBeforeCall(projectIds, from, to, null);
        Type localVarReturnType = new TypeToken<List<TimeLogDto>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * 
     * @param projectIds  (required)
     * @param from  (optional)
     * @param to  (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call timeCardsForManagedProjectsAsync(Set<Long> projectIds, LocalDateTime from, LocalDateTime to, final ApiCallback<List<TimeLogDto>> _callback) throws ApiException {

        okhttp3.Call localVarCall = timeCardsForManagedProjectsValidateBeforeCall(projectIds, from, to, _callback);
        Type localVarReturnType = new TypeToken<List<TimeLogDto>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
