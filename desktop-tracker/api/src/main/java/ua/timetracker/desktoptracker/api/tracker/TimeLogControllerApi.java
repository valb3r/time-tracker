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


package ua.timetracker.desktoptracker.api.tracker;

import ua.timetracker.desktoptracker.api.tracker.invoker.ApiCallback;
import ua.timetracker.desktoptracker.api.tracker.invoker.ApiClient;
import ua.timetracker.desktoptracker.api.tracker.invoker.ApiException;
import ua.timetracker.desktoptracker.api.tracker.invoker.ApiResponse;
import ua.timetracker.desktoptracker.api.tracker.invoker.Configuration;
import ua.timetracker.desktoptracker.api.tracker.invoker.Pair;
import ua.timetracker.desktoptracker.api.tracker.invoker.ProgressRequestBody;
import ua.timetracker.desktoptracker.api.tracker.invoker.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import java.io.File;
import java.time.LocalDateTime;
import ua.timetracker.desktoptracker.api.tracker.model.ProjectDto;
import ua.timetracker.desktoptracker.api.tracker.model.TimeLogCreateOrUpdate;
import ua.timetracker.desktoptracker.api.tracker.model.TimeLogDto;
import ua.timetracker.desktoptracker.api.tracker.model.TimeLogImageDto;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeLogControllerApi {
    private ApiClient localVarApiClient;

    public TimeLogControllerApi() {
        this(Configuration.getDefaultApiClient());
    }

    public TimeLogControllerApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for availableProjects
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call availableProjectsCall(final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/resources/timelogs/projects";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
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
    private okhttp3.Call availableProjectsValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = availableProjectsCall(_callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @return List&lt;ProjectDto&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public List<ProjectDto> availableProjects() throws ApiException {
        ApiResponse<List<ProjectDto>> localVarResp = availableProjectsWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * 
     * 
     * @return ApiResponse&lt;List&lt;ProjectDto&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<ProjectDto>> availableProjectsWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = availableProjectsValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<List<ProjectDto>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * 
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call availableProjectsAsync(final ApiCallback<List<ProjectDto>> _callback) throws ApiException {

        okhttp3.Call localVarCall = availableProjectsValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<List<ProjectDto>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteTimeLog
     * @param id  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteTimeLogCall(Long id, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/resources/timelogs/{id}"
            .replaceAll("\\{" + "id" + "\\}", localVarApiClient.escapeString(id.toString()));

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
        return localVarApiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteTimeLogValidateBeforeCall(Long id, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling deleteTimeLog(Async)");
        }
        

        okhttp3.Call localVarCall = deleteTimeLogCall(id, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param id  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public void deleteTimeLog(Long id) throws ApiException {
        deleteTimeLogWithHttpInfo(id);
    }

    /**
     * 
     * 
     * @param id  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteTimeLogWithHttpInfo(Long id) throws ApiException {
        okhttp3.Call localVarCall = deleteTimeLogValidateBeforeCall(id, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     *  (asynchronously)
     * 
     * @param id  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteTimeLogAsync(Long id, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteTimeLogValidateBeforeCall(id, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateTimeLog
     * @param id  (required)
     * @param timeLogCreateOrUpdate  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateTimeLogCall(Long id, TimeLogCreateOrUpdate timeLogCreateOrUpdate, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = timeLogCreateOrUpdate;

        // create path and map variables
        String localVarPath = "/v1/resources/timelogs/{id}"
            .replaceAll("\\{" + "id" + "\\}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateTimeLogValidateBeforeCall(Long id, TimeLogCreateOrUpdate timeLogCreateOrUpdate, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling updateTimeLog(Async)");
        }
        
        // verify the required parameter 'timeLogCreateOrUpdate' is set
        if (timeLogCreateOrUpdate == null) {
            throw new ApiException("Missing the required parameter 'timeLogCreateOrUpdate' when calling updateTimeLog(Async)");
        }
        

        okhttp3.Call localVarCall = updateTimeLogCall(id, timeLogCreateOrUpdate, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param id  (required)
     * @param timeLogCreateOrUpdate  (required)
     * @return TimeLogDto
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public TimeLogDto updateTimeLog(Long id, TimeLogCreateOrUpdate timeLogCreateOrUpdate) throws ApiException {
        ApiResponse<TimeLogDto> localVarResp = updateTimeLogWithHttpInfo(id, timeLogCreateOrUpdate);
        return localVarResp.getData();
    }

    /**
     * 
     * 
     * @param id  (required)
     * @param timeLogCreateOrUpdate  (required)
     * @return ApiResponse&lt;TimeLogDto&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<TimeLogDto> updateTimeLogWithHttpInfo(Long id, TimeLogCreateOrUpdate timeLogCreateOrUpdate) throws ApiException {
        okhttp3.Call localVarCall = updateTimeLogValidateBeforeCall(id, timeLogCreateOrUpdate, null);
        Type localVarReturnType = new TypeToken<TimeLogDto>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * 
     * @param id  (required)
     * @param timeLogCreateOrUpdate  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateTimeLogAsync(Long id, TimeLogCreateOrUpdate timeLogCreateOrUpdate, final ApiCallback<TimeLogDto> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateTimeLogValidateBeforeCall(id, timeLogCreateOrUpdate, _callback);
        Type localVarReturnType = new TypeToken<TimeLogDto>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for uploadTimelog
     * @param timeLogCreateOrUpdate  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call uploadTimelogCall(TimeLogCreateOrUpdate timeLogCreateOrUpdate, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = timeLogCreateOrUpdate;

        // create path and map variables
        String localVarPath = "/v1/resources/timelogs";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call uploadTimelogValidateBeforeCall(TimeLogCreateOrUpdate timeLogCreateOrUpdate, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'timeLogCreateOrUpdate' is set
        if (timeLogCreateOrUpdate == null) {
            throw new ApiException("Missing the required parameter 'timeLogCreateOrUpdate' when calling uploadTimelog(Async)");
        }
        

        okhttp3.Call localVarCall = uploadTimelogCall(timeLogCreateOrUpdate, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param timeLogCreateOrUpdate  (required)
     * @return TimeLogDto
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public TimeLogDto uploadTimelog(TimeLogCreateOrUpdate timeLogCreateOrUpdate) throws ApiException {
        ApiResponse<TimeLogDto> localVarResp = uploadTimelogWithHttpInfo(timeLogCreateOrUpdate);
        return localVarResp.getData();
    }

    /**
     * 
     * 
     * @param timeLogCreateOrUpdate  (required)
     * @return ApiResponse&lt;TimeLogDto&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<TimeLogDto> uploadTimelogWithHttpInfo(TimeLogCreateOrUpdate timeLogCreateOrUpdate) throws ApiException {
        okhttp3.Call localVarCall = uploadTimelogValidateBeforeCall(timeLogCreateOrUpdate, null);
        Type localVarReturnType = new TypeToken<TimeLogDto>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * 
     * @param timeLogCreateOrUpdate  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call uploadTimelogAsync(TimeLogCreateOrUpdate timeLogCreateOrUpdate, final ApiCallback<TimeLogDto> _callback) throws ApiException {

        okhttp3.Call localVarCall = uploadTimelogValidateBeforeCall(timeLogCreateOrUpdate, _callback);
        Type localVarReturnType = new TypeToken<TimeLogDto>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for uploadTimelogImage
     * @param id  (required)
     * @param tag  (required)
     * @param file  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call uploadTimelogImageCall(Long id, String tag, File file, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/resources/timelogs/{id}/images/{tag}"
            .replaceAll("\\{" + "id" + "\\}", localVarApiClient.escapeString(id.toString()))
            .replaceAll("\\{" + "tag" + "\\}", localVarApiClient.escapeString(tag.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (file != null) {
            localVarFormParams.put("file", file);
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "multipart/form-data"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call uploadTimelogImageValidateBeforeCall(Long id, String tag, File file, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling uploadTimelogImage(Async)");
        }
        
        // verify the required parameter 'tag' is set
        if (tag == null) {
            throw new ApiException("Missing the required parameter 'tag' when calling uploadTimelogImage(Async)");
        }
        
        // verify the required parameter 'file' is set
        if (file == null) {
            throw new ApiException("Missing the required parameter 'file' when calling uploadTimelogImage(Async)");
        }
        

        okhttp3.Call localVarCall = uploadTimelogImageCall(id, tag, file, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param id  (required)
     * @param tag  (required)
     * @param file  (required)
     * @return TimeLogImageDto
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public TimeLogImageDto uploadTimelogImage(Long id, String tag, File file) throws ApiException {
        ApiResponse<TimeLogImageDto> localVarResp = uploadTimelogImageWithHttpInfo(id, tag, file);
        return localVarResp.getData();
    }

    /**
     * 
     * 
     * @param id  (required)
     * @param tag  (required)
     * @param file  (required)
     * @return ApiResponse&lt;TimeLogImageDto&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<TimeLogImageDto> uploadTimelogImageWithHttpInfo(Long id, String tag, File file) throws ApiException {
        okhttp3.Call localVarCall = uploadTimelogImageValidateBeforeCall(id, tag, file, null);
        Type localVarReturnType = new TypeToken<TimeLogImageDto>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * 
     * @param id  (required)
     * @param tag  (required)
     * @param file  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call uploadTimelogImageAsync(Long id, String tag, File file, final ApiCallback<TimeLogImageDto> _callback) throws ApiException {

        okhttp3.Call localVarCall = uploadTimelogImageValidateBeforeCall(id, tag, file, _callback);
        Type localVarReturnType = new TypeToken<TimeLogImageDto>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for uploadedTimeCards
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
    public okhttp3.Call uploadedTimeCardsCall(LocalDateTime from, LocalDateTime to, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/resources/timelogs";

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
            "application/json"
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
    private okhttp3.Call uploadedTimeCardsValidateBeforeCall(LocalDateTime from, LocalDateTime to, final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = uploadedTimeCardsCall(from, to, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
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
    public List<TimeLogDto> uploadedTimeCards(LocalDateTime from, LocalDateTime to) throws ApiException {
        ApiResponse<List<TimeLogDto>> localVarResp = uploadedTimeCardsWithHttpInfo(from, to);
        return localVarResp.getData();
    }

    /**
     * 
     * 
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
    public ApiResponse<List<TimeLogDto>> uploadedTimeCardsWithHttpInfo(LocalDateTime from, LocalDateTime to) throws ApiException {
        okhttp3.Call localVarCall = uploadedTimeCardsValidateBeforeCall(from, to, null);
        Type localVarReturnType = new TypeToken<List<TimeLogDto>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * 
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
    public okhttp3.Call uploadedTimeCardsAsync(LocalDateTime from, LocalDateTime to, final ApiCallback<List<TimeLogDto>> _callback) throws ApiException {

        okhttp3.Call localVarCall = uploadedTimeCardsValidateBeforeCall(from, to, _callback);
        Type localVarReturnType = new TypeToken<List<TimeLogDto>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
