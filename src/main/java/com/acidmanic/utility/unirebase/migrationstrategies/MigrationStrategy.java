package com.acidmanic.utility.unirebase.migrationstrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.acidmanic.commandline.utility.CaseConvertor;
import com.acidmanic.utility.unirebase.migration.MigrationCommand;
import com.acidmanic.utility.unirebase.models.MigrationConfig;
import com.acidmanic.utility.unirebase.models.MigrationContext;

public abstract class MigrationStrategy {

    private List<Class<? extends MigrationCommand>> migrationSteps = new ArrayList<>();

    private void printConfigurations(MigrationContext context) {
        MigrationConfig config = context.getConfig();
        
        this.logger.accept("Username: " + config.getUsername());
        
        this.logger.accept("Password: " + config.getPassword()==null?" <no-password>":"******");
        
        this.logger.accept("Sources Subdirectory: " + config.getSourcesDirectory());
        
        this.logger.accept("Destination Repository Service: " + config.getDestinationRepositoryType().getSimpleName());
        
        this.logger.accept("Migration Strategy: " + config.getMigrationStrategy().getClass().getSimpleName());
        
        this.logger.accept("Force Clean: " + config.isForceClean());
    }
    
    protected class StrategyBuilder{
        private List<Class<? extends MigrationCommand>> commands;

        public StrategyBuilder(List<Class<? extends MigrationCommand>> commands) {
            this.commands = commands;
        }
        
        public StrategyBuilder first(Class<? extends MigrationCommand> command){
            
            this.commands.clear();
            
            this.commands.add(command);
            
            return this;
        }
        
        public StrategyBuilder then(Class<? extends MigrationCommand> command){
            this.commands.add(command);
            
            return this;
        }
    }
    
    protected abstract void onConfigureSteps(StrategyBuilder builder);

    private Consumer<String> logger = (text) -> {};

    public void execute(MigrationContext context){
        

        printConfigurations(context);
        
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
        onConfigureSteps(new StrategyBuilder(this.migrationSteps));
    }

    
}