package org.gobiiproject.datatimescope.controller;

@FunctionalInterface
public interface Procedure {
    void run() throws Exception;

    default Procedure andThen(Procedure after){
        return () -> {
            this.run();
            after.run();
        };
    }

    default Procedure compose(Procedure before){
        return () -> {
            before.run();
            this.run();
        };
    }
}