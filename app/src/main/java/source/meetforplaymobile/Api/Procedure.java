package source.meetforplaymobile.Api;

import java.util.HashMap;


public class Procedure
{
    final String procedure_name;
    final HashMap parameters;

    public Procedure(String procedure_name, HashMap parameters)
    {
        this.procedure_name = procedure_name;
        this.parameters = parameters;
    }
}
