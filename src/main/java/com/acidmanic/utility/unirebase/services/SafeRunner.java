/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.utility.unirebase.services;

import java.util.function.Consumer;

/**
 *
 * @author 80116
 */
public class SafeRunner {

    private boolean doLog;
    private Consumer<String> logger;
    private String description = "";

    public SafeRunner() {
        this.doLog = false;
        this.logger = (text) -> System.out.println(text);
    }

    public SafeRunner describe(String description) {

        this.description = description;

        return this;
    }

    public SafeRunner log() {
        this.doLog = true;

        return this;
    }

    public SafeRunner log(Consumer<String> logger) {
        this.doLog = true;

        this.logger = logger;

        return this;
    }
    
    public void run(ExceptionThrowerRunnable r) {
        try {
            r.run();
        } catch (Exception e) {
            log(e);
        }
    }

    private void log(Exception e) {
        if (this.doLog) {
                this.logger.accept(
                        this.description + ": " + e.getClass().getSimpleName()
                );
            }
    }

}
