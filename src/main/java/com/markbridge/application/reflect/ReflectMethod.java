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

    public String methodName() {
        return Thread.currentThread().getStackTrace()[CLIENT_CODE_STACK_INDEX].getMethodName();
    }

    public String callingMethodName() {
        return Thread.currentThread().getStackTrace()[CLIENT_CODE_STACK_INDEX + 1].getMethodName();
    }

    public static void main(String[] args) {
        
        ReflectMethod rm = new ReflectMethod();
        
        System.out.println("methodName() = " + rm.methodName());
        System.out.println("CLIENT_CODE_STACK_INDEX = " + CLIENT_CODE_STACK_INDEX);
        
        rm.callingMethod();
    }
    
    public void callingMethod() {
        calledMethod();
    }
    
    public void calledMethod() {
        System.out.println("this method is: " + methodName());
        System.out.println("the method that called this method is: " 
                + callingMethodName());
    }
}
