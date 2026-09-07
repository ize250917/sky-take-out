package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.jwt")
@Data
public class JwtProperties {

    /**
     * 管理端员工生成jwt令牌相关配置
     */
    private String adminSecretKey;      // 管理端员工生成jwt令牌的密钥
    private long adminTtl;              // 管理端员工生成jwt令牌的过期时间（秒）
    private String adminTokenName;      // 管理端员工生成jwt令牌的名称

    /**
     * 用户端微信用户生成jwt令牌相关配置
     */
    private String userSecretKey;       // 用户端微信用户生成jwt令牌的密钥
    private long userTtl;               // 用户端微信用户生成jwt令牌的过期时间（秒）
    private String userTokenName;       // 用户端微信用户生成jwt令牌的名称

}
