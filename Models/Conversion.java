package Models;

import java.io.Serializable;

public class Conversion implements Serializable {
    private static final Long serialVersionUid = 2L;
    public String Value,Result,ErrorMessage;
    public int SourceBase,TargetBase;
    public Conversion(String value, int SourceBase,int TargetBase){
        this.Value = value;
        this.SourceBase = SourceBase;
        this.TargetBase = TargetBase;
    }
}
