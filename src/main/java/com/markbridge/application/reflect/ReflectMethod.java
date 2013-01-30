/*
 * Mark Bridge (markbridge.com), San Francisco CA 94102, j2eewebtier@gmail.com 
 * Copyright (c) 2012 Mark Bridge All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.markbridge.application.reflect;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author mbridge
 */
public class ReflectMethod {
    
    private static Boolean INITIALIZE = Boolean.TRUE;
    
    /**
     * Variable and initialization of it from virgo47's response to the question at
     * http://stackoverflow.com/questions/442747/getting-the-name-of-the-current-executing-method/8592871#8592871
     * Accounts for stacktrace position variation after jdk5
     */
    private static int CLIENT_CODE_STACK_INDEX;
    
    public ReflectMethod() {
        
    }
    
    public static void init() {
        if(INITIALIZE) {
            // Finds out the index of "this code" in the returned stack trace - funny but it differs in JDK 1.5 and 1.6
            
            try {
                int i = 0;
                for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                    i++;
                    if (ste.getClassName().equals(ReflectMethod.class.getName())) {
                        break;
                    }
                }
                CLIENT_CODE_STACK_INDEX = i;
            } catch(Exception ex) {
                
            }
            
            INITIALIZE = Boolean.FALSE;
        }
    }

    public static void main(String[] args) {
        
        init();
        
        System.out.println("methodName() = " + methodName());
        System.out.println("CLIENT_CODE_STACK_INDEX = " + CLIENT_CODE_STACK_INDEX);
        
        ReflectMethod test = new ReflectMethod();
        test.test();
    }

    public static String methodName() {
        return Thread.currentThread().getStackTrace()[CLIENT_CODE_STACK_INDEX].getMethodName();
    }
    
    public void test() {
        nowThis();
    }
    
    public void nowThis() {
        System.out.println(methodName());
    }
}
