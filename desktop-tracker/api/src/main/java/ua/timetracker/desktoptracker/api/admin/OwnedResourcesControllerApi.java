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


import ua.timetracker.desktoptracker.api.admin.model.GroupDto;
import ua.timetracker.desktoptracker.api.admin.model.PathEntryGroupDto;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwnedResourcesControllerApi {
    private ApiClient localVarApiClient;

    public OwnedResourcesControllerApi() {
        this(Configuration.getDefaultApiClient());
    }

    public OwnedResourcesControllerApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for ownedGroups
     * @param owningGroupOrUserId  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> default response </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call ownedGroupsCall(Long owningGroupOrUserId, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/resources/owned-resources/{owning_group_or_user_id}"
            .replaceAll("\\{" + "owning_group_or_user_id" + "\\}", localVarApiClient.escapeString(owningGroupOrUserId.toString()));

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
    private okhttp3.Call ownedGroupsValidateBeforeCall(Long owningGroupOrUserId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'owningGroupOrUserId' is set
        if (owningGroupOrUserId == null) {
            throw new ApiException("Missing the required parameter 'owningGroupOrUserId' when calling ownedGroups(Async)");
        }
        

        okhttp3.Call localVarCall = ownedGroupsCall(owningGroupOrUserId, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param owningGroupOrUserId  (required)
     * @return List&lt;GroupDto&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> default response </td><td>  -  </td></tr>
     </table>
     */
    public List<GroupDto> ownedGroups(Long owningGroupOrUserId) throws ApiException {
        ApiResponse<List<GroupDto>> localVarResp = ownedGroupsWithHttpInfo(owningGroupOrUserId);
        return localVarResp.getData();
    }

    /**
     * 
     * 
     * @param owningGroupOrUserId  (required)
     * @return ApiResponse&lt;List&lt;GroupDto&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> default response </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<GroupDto>> ownedGroupsWithHttpInfo(Long owningGroupOrUserId) throws ApiException {
        okhttp3.Call localVarCall = ownedGroupsValidateBeforeCall(owningGroupOrUserId, null);
        Type localVarReturnType = new TypeToken<List<GroupDto>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * 
     * @param owningGroupOrUserId  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> default response </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call ownedGroupsAsync(Long owningGroupOrUserId, final ApiCallback<List<GroupDto>> _callback) throws ApiException {

        okhttp3.Call localVarCall = ownedGroupsValidateBeforeCall(owningGroupOrUserId, _callback);
        Type localVarReturnType = new TypeToken<List<GroupDto>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for selfOwnedResources
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> default response </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call selfOwnedResourcesCall(final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/resources/owned-resources";

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
    private okhttp3.Call selfOwnedResourcesValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        

        okhttp3.Call localVarCall = selfOwnedResourcesCall(_callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @return List&lt;PathEntryGroupDto&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> default response </td><td>  -  </td></tr>
     </table>
     */
    public List<PathEntryGroupDto> selfOwnedResources() throws ApiException {
        ApiResponse<List<PathEntryGroupDto>> localVarResp = selfOwnedResourcesWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * 
     * 
     * @return ApiResponse&lt;List&lt;PathEntryGroupDto&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> default response </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<PathEntryGroupDto>> selfOwnedResourcesWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = selfOwnedResourcesValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<List<PathEntryGroupDto>>(){}.getType();
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
        <tr><td> 200 </td><td> default response </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call selfOwnedResourcesAsync(final ApiCallback<List<PathEntryGroupDto>> _callback) throws ApiException {

        okhttp3.Call localVarCall = selfOwnedResourcesValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<List<PathEntryGroupDto>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
