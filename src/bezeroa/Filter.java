package bezeroa;

import java.io.File;
import java.io.FilenameFilter;

public class Filter implements  FilenameFilter{
    String extension;
    Filter(String extension){
        this.extension=extension;
    }
    public boolean accept(File dir, String name){
        return name.endsWith(extension);
    }
}

