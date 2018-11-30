package com.ten10.training.javaparsons.sandbox;

import java.security.Permission;

/**
 * SecurityManager which knows about our Sandbox
 */
public class SandboxSecurityManager extends SecurityManager {
    static void install() {
        System.setSecurityManager(new SandboxSecurityManager());
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
        System.out.println("Permission: " + perm + ", Context: " + context);
        boolean success = false;
        try {
            super.checkPermission(perm, context);
            success = true;
        } finally {
            if (success) {
                System.out.println("Succeeded");
            } else {
                System.out.println("Failed");
            }
        }
    }
}