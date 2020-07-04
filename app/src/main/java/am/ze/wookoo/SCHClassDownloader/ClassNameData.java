package am.ze.wookoo.SCHClassDownloader;

public class ClassNameData {
    private String className;
    private String classURL;

    public ClassNameData(String className, String classURL) {
        this.className = className;
        this.classURL = classURL;
    }

    public String getClassName() {
        return className;
    }

    public String getClassURL() {
        return classURL;
    }
}
