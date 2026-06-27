package com.zidio.nexus_hr.authservice.security;

import com.zidio.nexus_hr.authservice.Enum.Permission;
import com.zidio.nexus_hr.authservice.Enum.Role;

import java.util.*;


public class RoleBasePermission {

    public static Map<Role, Set<Permission>> getRolePermission(){

        Map<Role, Set<Permission>> role_permission = new HashMap<>();

        //Match HR permission
        role_permission.put(
                Role.HR, new HashSet<>(Arrays.asList(
                        Permission.APPLY_LEAVE,
                        Permission.APPROVE_LEAVE,
                        Permission.CREATE_EMPLOYEE,
                        Permission.VIEW_PAYROLE
                ))
        );

        //Match ADMIN permissions
        role_permission.put(
                Role.ADMIN, new HashSet<>(
                        Arrays.asList(
                                Permission.CREATE_EMPLOYEE,
                                Permission.DELETE_EMPLOYEE,
                                Permission.UPDATE_EMPLOYEE,
                                Permission.APPLY_LEAVE,
                                Permission.APPROVE_LEAVE,
                                Permission.RUN_PAYROLE,
                                Permission.VIEW_ANALYTICS,
                                Permission.VIEW_EMPLOYEE,
                                Permission.VIEW_PAYROLE
                        )
                )
        );

        //Match Manager Permission
        role_permission.put(
                Role.MANAGER, new HashSet<>(
                        Arrays.asList(
                                Permission.APPLY_LEAVE,
                                Permission.APPROVE_LEAVE,
                                Permission.VIEW_PAYROLE,
                                Permission.VIEW_ANALYTICS

                        )
                )
        );

        //map employee permission
        role_permission.put(
                Role.EMPLOYEE, new HashSet<>(
                        Arrays.asList(
                                Permission.APPLY_LEAVE
                        )
                )
        );

        return role_permission;
    }
}
