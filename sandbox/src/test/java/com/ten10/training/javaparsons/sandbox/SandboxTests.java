package com.ten10.training.javaparsons.sandbox;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;

import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class SandboxTests {

    private static class X {
    }

    static class WhitelistClassLoader extends ClassLoader {

        WhitelistClassLoader(ClassLoader parent, Set<String> whitelist) {
            super(parent);
        }


//        @Override
//        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
//            Class<?> klass;
//
//            // Assumption: findLoadedClass will only find classes we've loaded, and so we can defer the check
//            klass = findLoadedClass(name);
//            if (klass != null) {
//                if (resolve) {
//                    resolveClass(klass);
//                }
//                LOGGER.debug("Reusing pre-existing class {}", name);
//                return klass;
//            }
//
//            if (whitelist.contains(name)) {
//                LOGGER.debug("Looking up class {}", name);
//                try {
//                    klass = findClass(name);
//                    LOGGER.debug("Success");
//                    return klass;
//                } catch (ClassNotFoundException e) {
//                    LOGGER.debug("Failed");
//                    throw e;
//                }
//            }
//            throw new ClassNotFoundException(name);
//        }
    }

    @Test
    @Ignore
    void classLoaderExperiments() throws ClassNotFoundException {
        assertNull(System.getSecurityManager());

        ClassLoader c1 = ClassLoader.getSystemClassLoader();

        assertNotNull(c1);
        Class<?> x1 = c1.loadClass("com.ten10.training.javaparsons.SandboxTests$X");
        assertNotNull(x1);
        assert x1.getClassLoader() == c1;
        Class<?> x2 = c1.loadClass("com.ten10.training.javaparsons.SandboxTests$X");
        assertNotNull(x2);
        assert x1 == x2;
        assert x2.getClassLoader() == c1;
        ClassLoader c2 = new WhitelistClassLoader(
                c1, Collections.singleton("SandboxTests$X"));

        Class<?> x3 = c2.loadClass("com.ten10.training.javaparsons.SandboxTests$X");
        assert x1 != x3;
    }

    @Test
    void securityManagerExperiments() {
        SecurityManager securityManager = System.getSecurityManager();
        assertNull(securityManager);
        RemovableSecurityManager manager = new RemovableSecurityManager();
        System.setSecurityManager(manager);
        //assertNotNull(System.getSecurityManager());
        //String[] list = new File("c:\\tmp\\").list();
        manager.remove();
        assertNull(System.getSecurityManager());
    }

    static class RemovableSecurityManager extends SecurityManager {


        void remove() {
            AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
                System.setSecurityManager(null);
                return null;
            });
        }

        @Override
        public void checkPermission(Permission perm) {
            System.out.println("Checking Permission: " + perm);
            System.out.println("Returning successfully");

        }

        @Override
        public void checkPermission(Permission perm, Object context) {
            System.out.println("Checking Permission: " + perm + ", Context: " + context);
            super.checkPermission(perm, context);
            System.out.println("Returning");
        }
    }
}
