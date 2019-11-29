package com.acidmanic.utility.svn2git.migrationstrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.acidmanic.commandline.utility.CaseConvertor;
import com.acidmanic.utility.svn2git.migration.MigrationCommand;
import com.acidmanic.utility.svn2git.models.MigrationContext;
import com.acidmanic.utility.svn2git.services.SourceControlService;

public abstract class MigrationStrategy {

    private List<Class<? extends MigrationCommand>> migrationSteps = new ArrayList<>();

    protected abstract void onConfigureSteps(List<Class<? extends MigrationCommand>> steps);

    private Consumer<String> logger = (text) -> {};

    public void execute(MigrationContext context){
        

        CaseConvertor convertor = new CaseConvertor();

        for(Class<? extends MigrationCommand> step:this.migrationSteps){

            MigrationCommand command = instanciate(step);
            
            logger.accept("Step: " + convertor.pascalToSnake(step.getSimpleName().replace("-", " ")));

            command.execute(context);
        }

    }

    private MigrationCommand instanciate(
            Class<? extends MigrationCommand> step) {
        try {
            return step.newInstance();
        } catch (Exception e) {}

        return new MigrationCommand(){

            @Override
            public void execute(MigrationContext context) {
                        logger.accept("Unable to create object of step: " + step.getSimpleName());
            }

        };
    }

    public Consumer<String> getLogger() {
        return logger;
    }

    public void setLogger(Consumer<String> logger) {
        this.logger = logger;
    }

    public MigrationStrategy() {
        onConfigureSteps(this.migrationSteps);
    }

    
}