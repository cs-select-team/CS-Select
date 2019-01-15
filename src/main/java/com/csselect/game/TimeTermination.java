package com.csselect.game;

import java.time.LocalDate;

public class TimeTermination extends Termination {

    public TimeTermination(LocalDate date){

    }

    public boolean checkTermination() {
        return false;
    }
}
