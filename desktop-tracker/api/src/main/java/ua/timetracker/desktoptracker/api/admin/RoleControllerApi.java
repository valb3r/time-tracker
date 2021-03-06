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


import ua.timetracker.desktoptracker.api.admin.model.ProjectActorDto;
import ua.timetracker.desktoptracker.api.admin.model.ProjectDto;
import ua.timetracker.desktoptracker.api.admin.model.RoleDetailsDto;
import java.util.Set;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleControllerApi {
    private ApiClient localVarApiClient;

    public RoleControllerApi() {
        this(Configuration.getDefaultApiClient());
    }

    public RoleControllerApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for actorsOnProjects
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
    public okhttp3.Call actorsOnProjectsCall(Long id, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/resources/roles/in/project/{id}"
            .replaceAll("\\{" + "id" + "\\}", localVarApiClient.escapeString(id.toString()));

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
    private okhttp3.Call actorsOnProjectsValidateBeforeCall(Long id, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling actorsOnProjects(Async)");
        }
        

        okhttp3.Call localVarCall = actorsOnProjectsCall(id, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param id  (required)
     * @return List&lt;ProjectActorDto&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public List<ProjectActorDto> actorsOnProjects(Long id) throws ApiException {
        ApiResponse<List<ProjectActorDto>> localVarResp = actorsOnProjectsWithHttpInfo(id);
        return localVarResp.getData();
    }

    /**
     * 
     * 
     * @param id  (required)
     * @return ApiResponse&lt;List&lt;ProjectActorDto&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<ProjectActorDto>> actorsOnProjectsWithHttpInfo(Long id) throws ApiException {
        okhttp3.Call localVarCall = actorsOnProjectsValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<List<ProjectActorDto>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
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
    public okhttp3.Call actorsOnProjectsAsync(Long id, final ApiCallback<List<ProjectActorDto>> _callback) throws ApiException {

        okhttp3.Call localVarCall = actorsOnProjectsValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<List<ProjectActorDto>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for assignUserRoles
     * @param role  (required)
     * @param userOrGroupIds  (required)
     * @param projectOrGroupIds  (required)
     * @param roleDetailsDto  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call assignUserRolesCall(String role, Set<Long> userOrGroupIds, Set<Long> projectOrGroupIds, RoleDetailsDto roleDetailsDto, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = roleDetailsDto;

        // create path and map variables
        String localVarPath = "/v1/resources/roles/{role}/actors/{user_or_group_ids}/in/{project_or_group_ids}"
            .replaceAll("\\{" + "role" + "\\}", localVarApiClient.escapeString(role.toString()))
            .replaceAll("\\{" + "user_or_group_ids" + "\\}", localVarApiClient.escapeString(localVarApiClient.collectionPathParameterToString("csv", userOrGroupIds)))
            .replaceAll("\\{" + "project_or_group_ids" + "\\}", localVarApiClient.escapeString(localVarApiClient.collectionPathParameterToString("csv", projectOrGroupIds)));

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
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call assignUserRolesValidateBeforeCall(String role, Set<Long> userOrGroupIds, Set<Long> projectOrGroupIds, RoleDetailsDto roleDetailsDto, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'role' is set
        if (role == null) {
            throw new ApiException("Missing the required parameter 'role' when calling assignUserRoles(Async)");
        }
        
        // verify the required parameter 'userOrGroupIds' is set
        if (userOrGroupIds == null) {
            throw new ApiException("Missing the required parameter 'userOrGroupIds' when calling assignUserRoles(Async)");
        }
        
        // verify the required parameter 'projectOrGroupIds' is set
        if (projectOrGroupIds == null) {
            throw new ApiException("Missing the required parameter 'projectOrGroupIds' when calling assignUserRoles(Async)");
        }
        
        // verify the required parameter 'roleDetailsDto' is set
        if (roleDetailsDto == null) {
            throw new ApiException("Missing the required parameter 'roleDetailsDto' when calling assignUserRoles(Async)");
        }
        

        okhttp3.Call localVarCall = assignUserRolesCall(role, userOrGroupIds, projectOrGroupIds, roleDetailsDto, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param role  (required)
     * @param userOrGroupIds  (required)
     * @param projectOrGroupIds  (required)
     * @param roleDetailsDto  (required)
     * @return Long
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public Long assignUserRoles(String role, Set<Long> userOrGroupIds, Set<Long> projectOrGroupIds, RoleDetailsDto roleDetailsDto) throws ApiException {
        ApiResponse<Long> localVarResp = assignUserRolesWithHttpInfo(role, userOrGroupIds, projectOrGroupIds, roleDetailsDto);
        return localVarResp.getData();
    }

    /**
     * 
     * 
     * @param role  (required)
     * @param userOrGroupIds  (required)
     * @param projectOrGroupIds  (required)
     * @param roleDetailsDto  (required)
     * @return ApiResponse&lt;Long&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Long> assignUserRolesWithHttpInfo(String role, Set<Long> userOrGroupIds, Set<Long> projectOrGroupIds, RoleDetailsDto roleDetailsDto) throws ApiException {
        okhttp3.Call localVarCall = assignUserRolesValidateBeforeCall(role, userOrGroupIds, projectOrGroupIds, roleDetailsDto, null);
        Type localVarReturnType = new TypeToken<Long>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * 
     * @param role  (required)
     * @param userOrGroupIds  (required)
     * @param projectOrGroupIds  (required)
     * @param roleDetailsDto  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call assignUserRolesAsync(String role, Set<Long> userOrGroupIds, Set<Long> projectOrGroupIds, RoleDetailsDto roleDetailsDto, final ApiCallback<Long> _callback) throws ApiException {

        okhttp3.Call localVarCall = assignUserRolesValidateBeforeCall(role, userOrGroupIds, projectOrGroupIds, roleDetailsDto, _callback);
        Type localVarReturnType = new TypeToken<Long>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for removeGroupRoles
     * @param role  (required)
     * @param userOrGroupIds  (required)
     * @param projectOrGroupIds  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call removeGroupRolesCall(String role, Set<Long> userOrGroupIds, Set<Long> projectOrGroupIds, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/resources/roles/{role}/actors/{user_or_group_ids}/in/{project_or_group_ids}"
            .replaceAll("\\{" + "role" + "\\}", localVarApiClient.escapeString(role.toString()))
            .replaceAll("\\{" + "user_or_group_ids" + "\\}", localVarApiClient.escapeString(localVarApiClient.collectionPathParameterToString("csv", userOrGroupIds)))
            .replaceAll("\\{" + "project_or_group_ids" + "\\}", localVarApiClient.escapeString(localVarApiClient.collectionPathParameterToString("csv", projectOrGroupIds)));

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
        return localVarApiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call removeGroupRolesValidateBeforeCall(String role, Set<Long> userOrGroupIds, Set<Long> projectOrGroupIds, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'role' is set
        if (role == null) {
            throw new ApiException("Missing the required parameter 'role' when calling removeGroupRoles(Async)");
        }
        
        // verify the required parameter 'userOrGroupIds' is set
        if (userOrGroupIds == null) {
            throw new ApiException("Missing the required parameter 'userOrGroupIds' when calling removeGroupRoles(Async)");
        }
        
        // verify the required parameter 'projectOrGroupIds' is set
        if (projectOrGroupIds == null) {
            throw new ApiException("Missing the required parameter 'projectOrGroupIds' when calling removeGroupRoles(Async)");
        }
        

        okhttp3.Call localVarCall = removeGroupRolesCall(role, userOrGroupIds, projectOrGroupIds, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param role  (required)
     * @param userOrGroupIds  (required)
     * @param projectOrGroupIds  (required)
     * @return Long
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public Long removeGroupRoles(String role, Set<Long> userOrGroupIds, Set<Long> projectOrGroupIds) throws ApiException {
        ApiResponse<Long> localVarResp = removeGroupRolesWithHttpInfo(role, userOrGroupIds, projectOrGroupIds);
        return localVarResp.getData();
    }

    /**
     * 
     * 
     * @param role  (required)
     * @param userOrGroupIds  (required)
     * @param projectOrGroupIds  (required)
     * @return ApiResponse&lt;Long&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Long> removeGroupRolesWithHttpInfo(String role, Set<Long> userOrGroupIds, Set<Long> projectOrGroupIds) throws ApiException {
        okhttp3.Call localVarCall = removeGroupRolesValidateBeforeCall(role, userOrGroupIds, projectOrGroupIds, null);
        Type localVarReturnType = new TypeToken<Long>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * 
     * @param role  (required)
     * @param userOrGroupIds  (required)
     * @param projectOrGroupIds  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call removeGroupRolesAsync(String role, Set<Long> userOrGroupIds, Set<Long> projectOrGroupIds, final ApiCallback<Long> _callback) throws ApiException {

        okhttp3.Call localVarCall = removeGroupRolesValidateBeforeCall(role, userOrGroupIds, projectOrGroupIds, _callback);
        Type localVarReturnType = new TypeToken<Long>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateUserRolesInProject
     * @param roleId  (required)
     * @param roleDetailsDto  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateUserRolesInProjectCall(Long roleId, RoleDetailsDto roleDetailsDto, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = roleDetailsDto;

        // create path and map variables
        String localVarPath = "/v1/resources/roles/{role_id}"
            .replaceAll("\\{" + "role_id" + "\\}", localVarApiClient.escapeString(roleId.toString()));

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
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {  };
        return localVarApiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateUserRolesInProjectValidateBeforeCall(Long roleId, RoleDetailsDto roleDetailsDto, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'roleId' is set
        if (roleId == null) {
            throw new ApiException("Missing the required parameter 'roleId' when calling updateUserRolesInProject(Async)");
        }
        
        // verify the required parameter 'roleDetailsDto' is set
        if (roleDetailsDto == null) {
            throw new ApiException("Missing the required parameter 'roleDetailsDto' when calling updateUserRolesInProject(Async)");
        }
        

        okhttp3.Call localVarCall = updateUserRolesInProjectCall(roleId, roleDetailsDto, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param roleId  (required)
     * @param roleDetailsDto  (required)
     * @return ProjectDto
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ProjectDto updateUserRolesInProject(Long roleId, RoleDetailsDto roleDetailsDto) throws ApiException {
        ApiResponse<ProjectDto> localVarResp = updateUserRolesInProjectWithHttpInfo(roleId, roleDetailsDto);
        return localVarResp.getData();
    }

    /**
     * 
     * 
     * @param roleId  (required)
     * @param roleDetailsDto  (required)
     * @return ApiResponse&lt;ProjectDto&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ProjectDto> updateUserRolesInProjectWithHttpInfo(Long roleId, RoleDetailsDto roleDetailsDto) throws ApiException {
        okhttp3.Call localVarCall = updateUserRolesInProjectValidateBeforeCall(roleId, roleDetailsDto, null);
        Type localVarReturnType = new TypeToken<ProjectDto>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * 
     * @param roleId  (required)
     * @param roleDetailsDto  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateUserRolesInProjectAsync(Long roleId, RoleDetailsDto roleDetailsDto, final ApiCallback<ProjectDto> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateUserRolesInProjectValidateBeforeCall(roleId, roleDetailsDto, _callback);
        Type localVarReturnType = new TypeToken<ProjectDto>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateUserRolesInProject1
     * @param roleId  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateUserRolesInProject1Call(Long roleId, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/v1/resources/roles/{role_id}"
            .replaceAll("\\{" + "role_id" + "\\}", localVarApiClient.escapeString(roleId.toString()));

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
    private okhttp3.Call updateUserRolesInProject1ValidateBeforeCall(Long roleId, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'roleId' is set
        if (roleId == null) {
            throw new ApiException("Missing the required parameter 'roleId' when calling updateUserRolesInProject1(Async)");
        }
        

        okhttp3.Call localVarCall = updateUserRolesInProject1Call(roleId, _callback);
        return localVarCall;

    }

    /**
     * 
     * 
     * @param roleId  (required)
     * @return RoleDetailsDto
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public RoleDetailsDto updateUserRolesInProject1(Long roleId) throws ApiException {
        ApiResponse<RoleDetailsDto> localVarResp = updateUserRolesInProject1WithHttpInfo(roleId);
        return localVarResp.getData();
    }

    /**
     * 
     * 
     * @param roleId  (required)
     * @return ApiResponse&lt;RoleDetailsDto&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<RoleDetailsDto> updateUserRolesInProject1WithHttpInfo(Long roleId) throws ApiException {
        okhttp3.Call localVarCall = updateUserRolesInProject1ValidateBeforeCall(roleId, null);
        Type localVarReturnType = new TypeToken<RoleDetailsDto>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     *  (asynchronously)
     * 
     * @param roleId  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> OK </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateUserRolesInProject1Async(Long roleId, final ApiCallback<RoleDetailsDto> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateUserRolesInProject1ValidateBeforeCall(roleId, _callback);
        Type localVarReturnType = new TypeToken<RoleDetailsDto>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
