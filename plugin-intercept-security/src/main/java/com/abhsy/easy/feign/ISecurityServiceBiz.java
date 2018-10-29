package com.abhsy.easy.feign;

import com.abhsy.easy.config.Result;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;


public interface ISecurityServiceBiz {
    @POST
    @Path("/verifyUri")
    Result securityUri(@HeaderParam("Authorization")String token, @HeaderParam("uri") String securityUri);

}
